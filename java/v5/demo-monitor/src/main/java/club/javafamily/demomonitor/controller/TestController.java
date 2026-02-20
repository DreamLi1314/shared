package club.javafamily.demomonitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jack Li
 * @date 2022/8/8 上午1:52
 * @description
 */
@RestController
public class TestController {

   @GetMapping("/test1")
   public String hello() {
      return "hello";
   }

   @GetMapping("/test2")
   public String hello2() {

      // 模拟未预见的线上接口报错
      int i = 1 / 0;

      return "hello2";
   }

}
