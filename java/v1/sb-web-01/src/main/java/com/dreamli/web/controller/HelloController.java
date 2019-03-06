package com.dreamli.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

   @GetMapping("/hello")
   String hello(Map<String, Object> params) {
      params.put("hello", "Test");

      return "Hello World.......\n";
   }
}
