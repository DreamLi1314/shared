package org.example.yiji.dolphin.controller;

import org.example.mlog.export.vo.BaseExportParamVo;
import org.example.mlog.lensexporter.service.ExportService;
import org.example.yiji.dolphin.org.examplemon.*;
import org.example.yiji.dolphin.model.PO.*;
import org.example.yiji.dolphin.model.VO.UserVo;
import org.example.yiji.dolphin.model.primary.*;
import org.example.yiji.dolphin.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

  private final UserService userService;
  private final DepartmentService departmentService;
  private final RoleService roleService;
  private final ExportService exportService;

  public UserController(UserService userService,
                        RoleService roleService,
                        ExportService exportService,
                        DepartmentService departmentService)
  {

    this.userService = userService;
    this.roleService = roleService;
    this.exportService = exportService;
    this.departmentService = departmentService;
  }

  /**
   * 新增
   * @param po
   */
  @PostMapping("/add")
  public void add(@Validated @RequestBody UserAddPo po) {
    User byUsername = userService.findByUsername(po.getUsername());
    if(byUsername!=null){
      throw new CommonException(ResultCode.FAIL,"该用户名已被注册");
    }

//    User byPhone = userService.findByPhone(po.getPhone());
//    if(byPhone!=null){
//      throw new CommonException(ResultCode.FAIL,"该手机号已被注册");
//    }
//    User byMailbox = userService.findByMailbox(po.getMailbox());
//    if(byMailbox!=null){
//      throw new CommonException(ResultCode.FAIL,"该邮箱已被注册");
//    }
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
   * 通过id删除
   * @param id
   */
  @DeleteMapping("/delete")
  public void delete(@RequestParam Long id) {
    userService.deleteById(id);
  }

  /**
   * 修改
   * @param po
   */
  @PutMapping("/update")
  public void update(@Validated @RequestBody UserUpdatePo po) {
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
    userService.update(entity);
  }

  /**
   * 通过id查询
   * @param id
   * @return
   */
  @GetMapping("/id")
  public UserVo findById(@RequestParam Long id) {
    return data2vo(userService.selectByKey(id));
  }

  @PostMapping("/find-by-ids")
  public List<UserVo> findUsersByIds(@RequestBody Long[] ids) {
    return data2vo(userService.findAllByIds(ids));
  }

  @ApiOperation("导出用户接口: ids 如果为 null 导出系统所有用户")
  @PostMapping("/export/find-by-ids")
  public void findUsersByIds(@RequestBody BaseExportParamVo vo,
                             HttpServletResponse response)
     throws Exception
  {
    List<UserVo> users;

    if(ObjectUtils.isEmpty(vo.getIds())) {
      users = findAll();
    }
    else {
      users = findUsersByIds(vo.getIds());
    }

    exportService.export(users, UserVo.class, response);
  }

  @GetMapping("/find-by-dept")
  public List<UserVo> findAllByDept(@RequestParam(value = "deptId", required = false) Long deptId) {
    return data2vo(userService.findAllByDept(deptId));
  }

  /**
   * 查询全部
   * @return
   */
  @GetMapping("/all")
  public List<UserVo> findAll() {
    return data2vo(userService.selectAll());
  }

  /**
   * 分页查询
   * @param str 关键字
   * @param pageNum 页码
   * @param pageSize 页面大小
   * @return
   */
  @GetMapping("/search")
  public PageResponse<UserVo> search(@RequestParam(required = false) String str,
      @Min(value = 1,message = "请输入≥1的页码值") @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
    return data2vo(userService.findBySearch(str,pageNum,pageSize));
  }

  /**
   * 重设密码(旧密码修改新密码)
   * @param po
   * @return
   */
  @PostMapping("/reset")
  public void reset(@RequestBody PasswordUpdatePo po, HttpServletRequest request) {
    userService.reset(po, request);
  }

  @GetMapping("/reset-pwd")
  public boolean resetPwd(@RequestParam("userId") Long userId, HttpServletRequest request) {
    return userService.resetPwd(userId, request);
  }

  @PostMapping("/find-admin")
  public List<String> findAdmins(@RequestBody Long[] ids) {
    List<User> users = userService.findAllByIds(ids);

    List<String> adminPhones = new ArrayList<>();

    for (User user : users) {
      if("管理员".equals(user.getRole().getName())
         || "超级管理员".equals(user.getRole().getName()))
      {
        adminPhones.add(user.getPhone());
      }
    }

    return adminPhones;
  }

  private UserVo data2vo(User data){
    UserVo vo = new UserVo();
    BeanUtils.copyProperties(data,vo);
    if(data.getDepartment()!=null){
      vo.setDepartmentName(data.getDepartment().getName());
      vo.setDepartmentId(data.getDepartment().getId());
    }
    if(data.getRole()!=null){
      vo.setRoleName(data.getRole().getName());
      vo.setRoleId(data.getRole().getId());
    }
    return vo;
  }

  private List<UserVo> data2vo(List<User> users){
    ArrayList<UserVo> data = new ArrayList<>();
    for (User user : users) {
      data.add(data2vo(user));
    }
    return data;
  }

  private PageResponse<UserVo> data2vo(PageResponse<User> page){
    ArrayList<UserVo> list = new ArrayList<>();
    List<User> data = page.getContent();
    for (User user : data) {
      list.add(data2vo(user));
    }
    return new PageResponse<>(page,list);
  }

}
