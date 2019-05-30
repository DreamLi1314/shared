package com.qianfeng.cas.shiro.realm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

   @GetMapping("/test/get")
   public String testGet(@RequestParam("id") int id) {
      System.out.println("==========id======" + id);
      return "success";
   }

   @GetMapping("/test/get")
   public String testPost(int id) {
      System.out.println("==========id======" + id);

      return "success...";
   }

}
