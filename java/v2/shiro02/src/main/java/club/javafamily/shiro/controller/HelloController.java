package club.javafamily.shiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

   @GetMapping("/ping")
   public String ping() {
      return "pong";
   }
}
