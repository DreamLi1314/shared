package org.example.yiji.dolphin.config;

import org.example.yiji.dolphin.model.primary.*;
import org.example.yiji.dolphin.service.*;
import org.apache.org.examplemons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author wanglin
 * @date 2020/5/14 11:37
 */
//系统启动后执行的任务
@Component
//@Order(1)
public class MlogCommandLineRunner implements CommandLineRunner {

  @Autowired
  UserService userService;
  @Autowired
  PermissionService permissionService;
  @Autowired
  FunctionService functionService;
  @Autowired
  RoleService roleService;

  @Override
  public void run(String... args) throws Exception {
    List<Permission> permissions = permissionService.selectAll();
    HashSet<Permission> fuser = new HashSet<>();
    HashSet<Permission> flog = new HashSet<>();
    HashSet<Permission> frole = new HashSet<>();
    HashSet<Permission> fdepartment = new HashSet<>();
    HashSet<Permission> ffunction = new HashSet<>();

    if(permissions.isEmpty()){

      //用户管理
      fuser.add(permissionService.save(new Permission("新增用户", "/user/add")));
      fuser.add(permissionService.save(new Permission("删除用户", "/user/delete")));
      fuser.add(permissionService.save(new Permission("修改用户", "/user/update")));
      fuser.add(permissionService.save(new Permission("查询单个用户", "/user/id")));
      fuser.add(permissionService.save(new Permission("查询全部用户", "/user/all")));
      fuser.add(permissionService.save(new Permission("分页查询全部用户", "/user/search")));
      fuser.add(permissionService.save(new Permission("重设密码", "/user/reset")));

      //日志查看
      flog.add(permissionService.save(new Permission("查询全部日志", "/log/all")));
      flog.add(permissionService.save(new Permission("分页查询全部日志", "/log/search")));

      //角色管理
      frole.add(permissionService.save(new Permission("新增角色", "/role/add")));
      frole.add(permissionService.save(new Permission("删除角色", "/role/delete")));
      frole.add(permissionService.save(new Permission("修改角色", "/role/update")));
      frole.add(permissionService.save(new Permission("查询单个角色", "/role/id")));
      frole.add(permissionService.save(new Permission("查询全部角色", "/role/all")));
      frole.add(permissionService.save(new Permission("分页查询全部角色", "/role/search")));

      //部门管理
      fdepartment.add(permissionService.save(new Permission("新增部门", "/department/add")));
      fdepartment.add(permissionService.save(new Permission("删除部门", "/department/delete")));
      fdepartment.add(permissionService.save(new Permission("修改部门", "/department/update")));
      fdepartment.add(permissionService.save(new Permission("查询单个部门", "/department/id")));
      fdepartment.add(permissionService.save(new Permission("查询全部部门", "/department/all")));
      fdepartment.add(permissionService.save(new Permission("分页查询全部部门", "/department/search")));

      //权限展示
      ffunction.add(permissionService.save(new Permission("获取权限树状结构", "/function/tree")));
    }

    List<Function> functions = functionService.selectAll();
    Function projectFunc = null, thresholdFunc = null,
       userFunc = null, userManagement = null;

    if(functions.isEmpty()) {
      Function systemManagement = functionService.save(new Function("系统管理","systemManagement", true,null,null));
      userManagement = functionService.save(new Function("用户管理","userManagement", false,systemManagement,null));
      userFunc = new Function("用户信息管理","user", false,userManagement,fuser);
      functionService.save(userFunc);
      functionService.save(new Function("用户角色管理","role", false,systemManagement,frole));
      functionService.save(new Function("部门管理","dept", false,systemManagement,fdepartment));

      projectFunc = functionService.save(new Function("项目管理","project-config", false,systemManagement,null));
      thresholdFunc = functionService.save(new Function("阈值管理","threshold-config", false,systemManagement,null));
    }

    List<Role> roles = roleService.selectAll();
    Role adminRole = null, cAdminRole = null, businessRole = null, guestRole = null;

    if(roles.isEmpty()){
//      adminRole = roleService.save(new Role("超级管理员", new HashSet<>(functionService.selectAll())));

      final Set<Function> funcs = new HashSet<>();
      funcs.add(projectFunc);
      funcs.add(thresholdFunc);
      funcs.add(userManagement);
      funcs.add(userFunc);

      cAdminRole = roleService.save(new Role("管理员", funcs));
      guestRole = roleService.save(new Role("普通用户", Collections.emptySet()));
    }else {
      adminRole = roleService.selectAll().get(0);
    }

    List<User> users = userService.selectAll();

    if(users.isEmpty()){
      Date date = new Date();

//      userService.save(new User("超级管理员", "root", DigestUtils.md5Hex("root2022"),"13012341221","root@qq.org.example",date,date,adminRole));
      userService.save(new User("管理员", "admin", DigestUtils.md5Hex("admin2022"),"13012341222","admin@qq.org.example",date,date,cAdminRole));

      userService.save(new User("普通用户 1", "user1", DigestUtils.md5Hex("user12022"),"13012341231","user1@qq.org.example",date,date,guestRole));
      userService.save(new User("普通用户 2", "user2", DigestUtils.md5Hex("user22022"),"13012341232","user2@qq.org.example",date,date,guestRole));
      userService.save(new User("普通用户 3", "user3", DigestUtils.md5Hex("user32022"),"13012341233","user3@qq.org.example",date,date,guestRole));
      userService.save(new User("普通用户 4", "user4", DigestUtils.md5Hex("user42022"),"13012341234","user4@qq.org.example",date,date,guestRole));
      userService.save(new User("普通用户 5", "user5", DigestUtils.md5Hex("user52022"),"13012341235","user5@qq.org.example",date,date,guestRole));
    }
  }

  public static void main(String[] args) {
    System.out.println(DigestUtils.md5Hex("user12021"));//9f85c3b3e7084aabf98323e9173aae41
    System.out.println(DigestUtils.md5Hex("user22021"));//f01f8fc15a9f105e4d2e419127471223
    System.out.println(DigestUtils.md5Hex("user32021"));//6ee6610d6b9fb39c48bfccf67a9d3b69
    System.out.println(DigestUtils.md5Hex("user42021"));//6274fc85771eefca93f3fd0545eb1ce3
    System.out.println(DigestUtils.md5Hex("user52021"));//48cab2aa2332c25517fd729f5fc7c64e
    System.out.println(DigestUtils.md5Hex("123456"));//e10adc3949ba59abbe56e057f20f883e
  }

}
