package club.javafamily.controller;

import club.javafamily.service.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jack Li
 * @date 2022/1/18 11:38 上午
 * @description
 */
@RestController
public class GrpcClientController {

   @Autowired
   private GrpcClientService grpcClientService;

   @RequestMapping("/client")
   public String printMessage(@RequestParam(defaultValue = "JackLi") String name) {
      return grpcClientService.sendMessage(name);
   }

}
