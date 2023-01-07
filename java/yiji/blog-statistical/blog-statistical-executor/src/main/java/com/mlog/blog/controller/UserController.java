package com.mlog.blog.controller;

import com.mlog.blog.entity.SysUser;
import com.mlog.blog.service.IBlogStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jack Li
 * @date 2022/1/27 6:41 下午
 * @description
 */
@Api("用户 Api")
@RestController
public class UserController {
   private final IBlogStatisticService blogStatisticService;

   public UserController(IBlogStatisticService blogStatisticService) {
      this.blogStatisticService = blogStatisticService;
   }

   @ApiOperation("获取所有用户信息")
   @GetMapping("/user/list")
   public List<SysUser> allUsers() {
      return blogStatisticService.findAllUsers();
   }
}
