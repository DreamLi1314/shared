package org.example.yiji.dolphin.service.impl;

import org.example.alibaba.fastjson.JSONObject;
import org.example.yiji.dolphin.org.examplemon.*;
import org.example.yiji.dolphin.dao.primary.UserRepository;
import org.example.yiji.dolphin.dao.secondary.SaltRepository;
import org.example.yiji.dolphin.model.PO.PasswordUpdatePo;
import org.example.yiji.dolphin.model.VO.SimpleUserVo;
import org.example.yiji.dolphin.model.VO.UserLoginVo;
import org.example.yiji.dolphin.model.primary.*;
import org.example.yiji.dolphin.model.secondary.Salt;
import org.example.yiji.dolphin.service.UserService;
import org.apache.org.examplemons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {

  private static final String chili = "zawaluduo";

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SaltRepository saltRepository;

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
  public User findByPhone(String phone) {
    return userRepository.findByPhone(phone);
  }
  public User findByMailbox(String mailbox) {
    return userRepository.findByMailbox(mailbox);
  }

  public PageResponse<User> findBySearch(String str, int pageNum, int pageSize) {
    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
    Page<User> result;
    if(str==null||"".equals(str)){
      result = userRepository.findAll(pageable);
    }else {
      result = userRepository.findAll(new Specification<User>() {

        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq,
            CriteriaBuilder cb) {
          ArrayList<Predicate> list = new ArrayList<>();
          list.add(cb.like(root.get("username").as(String.class),"%"+str+"%"));
          list.add(cb.like(root.get("role").get("name").as(String.class),"%"+str+"%"));
          list.add(cb.like(root.get("department").get("name").as(String.class),"%"+str+"%"));
          Predicate[] predicate = new Predicate[list.size()];
          return cb.or(list.toArray(predicate));
        }
      },pageable);
    }
    return new PageResponse<>(result);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public User save(User user) {
    Date now = new Date();
    user.setCreateTime(now);
    user.setLastTime(now);
    //加盐处理
    Salt salt = new Salt();
    salt.setDetail(getUUID());
    user.setPassword(MD5(user.getPassword(),salt.getDetail()));
    User save = userRepository.save(user);
    salt.setId(save.getId());
    saltRepository.save(salt);
    return save;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public User update(User user) {
    User object = userRepository.getOne(user.getId());
    Salt salt = null;
    if(user.getPassword()!=null&&!"".equals(user.getPassword())){
      salt = new Salt();
      salt.setId(user.getId());
      salt.setDetail(getUUID());
      user.setPassword(MD5(user.getPassword(),salt.getDetail()));
    }
    if(salt!=null){
      saltRepository.save(salt);
    }
    //将object复制给entity 忽略的属性是有值的属性（将object复制到entity属性为null）
    BeanUtils.copyProperties(object, user, getNoNullProperties(user));
    return userRepository.save(user);
  }

  @Override
  public UserLoginVo login(User user, HttpServletRequest request) {
    User data = userRepository.findByUsername(user.getUsername());

//    if(data == null) {
//      data = userRepository.findByPhone(user.getUsername());
//    }

    if (data != null && verifyMD5(user.getPassword(),
       saltRepository.getOne(data.getId()).getDetail(),
        data.getPassword()))
    {
      String uuid = getUUID();
      HttpSession session = request.getSession();
      session.setAttribute("token",uuid);
      session.setAttribute("user",JSONObject.toJSON(data));
      data.setLastTime(new Date());
      update(data,data.getId());
      UserLoginVo vo = new UserLoginVo();
      vo.setToken(uuid);
      vo.setId(data.getId());
      vo.setNickName(data.getNickName());
      vo.setUsername(data.getUsername());
      HashSet<String> set = new HashSet<>();
      if(data.getRole()!=null){

        vo.setRoleName(data.getRole().getName());

        Set<Function> functions = data.getRole().getFunctions();
        for (Function function : functions) {
          addKey(set,function);
        }
      }
      vo.setFunctions(set);

      final Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.DAY_OF_MONTH, 10);
      vo.setExpireDate(calendar.getTime());

      vo.setDept(Optional.ofNullable(data.getDepartment())
         .orElse(new Department(data.getDept())).getName());

      return vo;
    } else {
      throw new CommonException(ResultCode.LOGIN_ERROR, "账号或密码有误,请重新输入");
    }
  }

  public void addKey(Set<String> set,Function function){
    if(function!=null){
      set.add(function.getKey());
      addKey(set,function.getParentFunction());
    }
  }

  @Override
  public SimpleUserVo check(String info, HttpServletRequest request) {
    User data = userRepository.findByUsernameOrPhoneOrMailbox(info,info,info);
    if(data==null){
      throw  new CommonException(ResultCode.FAIL,"用户不存在");
    }
    HttpSession session = request.getSession();
    session.setAttribute("user",JSONObject.toJSON(data));
    return new SimpleUserVo(data);
  }

  @Override
  public User reset(PasswordUpdatePo po, HttpServletRequest request) {
    Object obj = request.getSession().getAttribute("user");
    if(obj==null){
      throw new CommonException(ResultCode.NO_USERINFO,"请重新登录");
    }
    User user = selectByKey(((JSONObject) obj).getLong("id"));
//    User user = JSON.toJavaObject((JSONObject)obj,User.class);
    Salt salt = saltRepository.getOne(user.getId());
    if (!verifyMD5(po.getOld(),salt.getDetail(),user.getPassword())) {
      throw  new CommonException(ResultCode.FAIL,"密码错误，请重新输入");
    }
    user.setPassword(po.getPassword());
    User update = update(user);
    request.getSession().setAttribute("user",JSONObject.toJSON(update));
    return update;
  }

  @Override
  public boolean resetPwd(Long userId, HttpServletRequest request) {
    Object obj = request.getSession().getAttribute("user");

    if(!(obj instanceof JSONObject)){
      throw new CommonException(ResultCode.NO_USERINFO,"请重新登录");
    }

    JSONObject userJson = (JSONObject) obj;
    User currentUser = JSONObject.parseObject(userJson.toJSONString(), User.class);

    if(Optional.ofNullable(currentUser.getRole().getFunctions())
       .orElse(new HashSet<>())
       .contains("user"))
    {
      throw new CommonException(ResultCode.NO_PERMISSION,"没有重置密码的权限");
    }

    User user = userRepository.getOne(userId);

    user.setPassword(DigestUtils.md5Hex("123456"));

    update(user);

    return true;
  }

  @Override
  public List<User> findAllByIds(Long[] ids) {
    return userRepository.findAllById(Arrays.asList(ids));
  }

  @Override
  public List<User> findAllByDept(Long deptId) {
    return deptId != null
      ? userRepository.findAllByDept(deptId)
       : userRepository.findAllByDepartmentIsNull();
  }

  private String getUUID() {
    String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
    return uuid;
  }

  /**
   * md5加密
   * @param text 明文
   * @param salt 密文
   * @return 加密字符串
   */
  public String MD5(String text,String salt) {
    return DigestUtils.md5Hex(text+chili+salt);
  }

  /**
   * md5验证
   * @param text 明文
   * @param salt 密文
   * @param md5 加密字符串
   * @return true/false
   */
  public boolean verifyMD5(String text,String salt,String md5) {
    String md5text = MD5(text,salt);
    if(md5text.equalsIgnoreCase(md5)){
      return true;
    }else {
      return false;
    }
  }

}
