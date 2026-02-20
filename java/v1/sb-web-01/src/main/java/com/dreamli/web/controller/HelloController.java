package com.dreamli.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;

@Controller
public class HelloController {

   @GetMapping("/hello")
   @ResponseBody
   String hello() {
      return "Hello World...\n";
   }

   @GetMapping("/test/thymeleaf")
   String testThymeleaf(Map<String, Object> params, HttpServletRequest request) {
      params.put("hello", "Test");
      params.put("intV", 12);
      params.put("booleanV", true);
      params.put("name", " 来捣乱的....");
      params.put("list", Arrays.asList("dramli", "aaa", "bbb"));

      HttpSession session = request.getSession();
      ServletContext servletContext = request.getServletContext();
      System.out.println(request.getAttribute("hello"));
      System.out.println(session.getAttribute("hello"));
      System.out.println(servletContext.getAttribute("hello"));

      /**
       * 返回的字符串如果需要映射到模板页面, 则该请求的 controller 上不能有 @ResponseBody 注解,
       * 因为该注解会把内容写入到 response 的 body 域中, 这样就直接显示到页面上了.
       *
       * 跳转到 /success.html 页面
       */

      return "success";
   }

   @GetMapping("/to-static")
   String toStatic() {
      return "redirect:/static.html";
   }
}
