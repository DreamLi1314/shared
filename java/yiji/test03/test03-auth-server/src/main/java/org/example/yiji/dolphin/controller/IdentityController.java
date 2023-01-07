package org.example.yiji.dolphin.controller;

import org.example.alibaba.fastjson.JSONObject;
import org.example.yiji.dolphin.org.examplemon.CommonException;
import org.example.yiji.dolphin.org.examplemon.ResultCode;
import org.example.yiji.dolphin.config.SystemAspect;
import org.example.yiji.dolphin.model.PO.*;
import org.example.yiji.dolphin.model.VO.SimpleUserVo;
import org.example.yiji.dolphin.model.VO.UserLoginVo;
import org.example.yiji.dolphin.model.primary.*;
import org.example.yiji.dolphin.service.*;
import org.example.yiji.dolphin.util.VerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登陆/注册/找回密码相关
 */
@RestController
@RequestMapping("/identity")
@Validated
@Slf4j
public class IdentityController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;

    /**
     * 注册
     * @param po
     * @return
     */
    @PostMapping("/register")
    public void register(@Validated @RequestBody UserRegisterPo po, HttpServletRequest request) {
        HttpSession session=request.getSession();
        if(session.getAttribute("imageCode")==null){
            throw new CommonException(ResultCode.FAIL,"请重新获取验证码");
        }
        if(!session.getAttribute("imageCode").toString().equalsIgnoreCase(po.getCode())){
            throw new CommonException(ResultCode.FAIL,"验证码错误");
        }
        User byUsername = userService.findByUsername(po.getUsername());
        if(byUsername!=null){
            throw new CommonException(ResultCode.FAIL,"该用户名已被注册");
        }
        User byPhone = userService.findByPhone(po.getPhone());
        if(byPhone!=null){
            throw new CommonException(ResultCode.FAIL,"该手机号已被注册");
        }
        User byMailbox = userService.findByMailbox(po.getMailbox());
        if(byMailbox!=null){
            throw new CommonException(ResultCode.FAIL,"该邮箱已被注册");
        }
        User entity = new User();
        BeanUtils.copyProperties(po,entity);
        if(po.getDepartmentId()!=null){
            Department department = departmentService.selectByKey(po.getDepartmentId());
            entity.setDepartment(department);
        }
        if(po.getRoleId()!=null){
            Role role = roleService.selectByKey(po.getRoleId());
            entity.setRole(role);
        }
        userService.save(entity);
    }

    /**
     * 登录
     * @param po
     * @return
     */
    @PostMapping("/login")
    public UserLoginVo login(
       @Validated @RequestBody UserLoginPo po,
       @RequestParam(value = "skipCode", defaultValue = "false") Boolean skipCode,
       HttpServletRequest request)
    {
        if(!skipCode) {
            if(po.getCode() == null) {
                throw new RuntimeException("验证码不能为空");
            }

            HttpSession session=request.getSession();
            if(session.getAttribute("imageCode")==null){
                throw new CommonException(ResultCode.FAIL,"请重新获取验证码");
            }
            if(!session.getAttribute("imageCode").toString().equalsIgnoreCase(po.getCode())){
                throw new CommonException(ResultCode.FAIL,"验证码错误");
            }
        }

        User entity = new User();
        BeanUtils.copyProperties(po,entity);
        return userService.login(entity,request);
    }

    /**
     * 生成验证码(base64)
     * @return
     * @throws Exception
     */
    @GetMapping("/code")
    public String code(HttpServletRequest request) throws Exception{
        HttpSession session=request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode",objs[0]);
        log.info("IP地址 ==> "+ SystemAspect.getClientIP(request)+" <== 用户的验证码为 ==> "+objs[0]);

//    //将图片输出给浏览器
//    BufferedImage image = (BufferedImage) objs[1];
//    response.setContentType("image/png");
//    OutputStream os = response.getOutputStream();
//    ImageIO.write(image, "png", os);
        return (String)objs[1];
    }

    /**
     * 找回密码(填写账号/手机号/邮箱)
     * @param info 账号/手机号/邮箱
     * @return
     */
    @GetMapping("/check")
    public SimpleUserVo check(@RequestParam String info, HttpServletRequest request) {
        return userService.check(info,request);
    }

    /**
     * 发送邮件的接口
     */
    @Autowired
    private MailService mailService;

    /**
     * 找回密码(获取手机/邮箱验证码)
     * @param type 类型(1:短信(暂不支持), 2:邮箱)
     */
    @GetMapping("/sendMsg")
    public void sendMsg(@RequestParam Integer type, HttpServletRequest request) {
        JSONObject jsonUser = checkUserInfo(request);
        if(type!=1&&type!=2)
            throw new CommonException(ResultCode.VALIDATE_ERROR,"参数无效");
        if(type==1){
            if(StringUtils.isEmpty(jsonUser.getString("phone")))
                throw new CommonException(ResultCode.FAIL,"用户未登记手机号");
            //发短信
            String code = VerifyUtil.createNumber();
            HttpSession session = request.getSession();
            session.setAttribute("resetCode",code);



            log.info("IP地址 ==> "+ SystemAspect.getClientIP(request)+" <== 用户的手机验证码为 ==> "+code);



//            throw new CommonException(ResultCode.FAIL,"暂不支持");
        }
        if(type==2){
            if(StringUtils.isEmpty(jsonUser.getString("mailbox")))
                throw new CommonException(ResultCode.FAIL,"用户未登记邮箱");
            //发邮箱
            String code = VerifyUtil.createCode(6);
            HttpSession session = request.getSession();
            session.setAttribute("resetCode",code);
            mailService.sendSimpleMail(jsonUser.getString("mailbox"),"易辑科技","请使用 "+code+" 来进行后续操作。");
            log.info("IP地址 ==> "+ SystemAspect.getClientIP(request)+" <== 用户的邮箱验证码为 ==> "+code);
        }
    }

    /**
     * 找回密码(验证码校验)
     * @param code 验证码
     * @return
     */
    @GetMapping("/verify")
    public void verify(@RequestParam String code,HttpServletRequest request) {
        checkUserInfo(request);
        //验证码校验
        HttpSession session = request.getSession();
        if(session.getAttribute("resetCode")==null){
            throw new CommonException(ResultCode.FAIL,"请重新获取验证码");
        }
        if(!session.getAttribute("resetCode").toString().equalsIgnoreCase(code)){
            throw new CommonException(ResultCode.FAIL,"验证码错误");
        }
    }

    /**
     * 找回密码(设置新密码)
     * @param po
     * @return
     */
    @PostMapping("/reset")
    public void reset(@RequestBody PasswordResetPo po, HttpServletRequest request) {
        JSONObject jsonUser = checkUserInfo(request);
        User user = userService.findByUsername(jsonUser.getString("username"));
        user.setPassword(po.getPassword());
        userService.update(user);
    }

    private JSONObject checkUserInfo(HttpServletRequest request){
        Object obj = request.getSession().getAttribute("user");
        if(obj==null){
            throw new CommonException(ResultCode.NO_USERINFO,"请重新输入用户信息");
        }
        return (JSONObject)obj;
    }

}
