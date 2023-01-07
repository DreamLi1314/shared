package club.javafamily.demomonitor.controller;

import club.javafamily.nf.request.FeiShuTextNotifyRequest;
import club.javafamily.nf.service.FeiShuNotifyHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jack Li
 * @date 2022/8/8 上午1:52
 * @description
 */
@RestController
public class NotifyController {

   private final FeiShuNotifyHandler feiShuNotifyHandler;

   private final RestTemplate restTemplate;

   public NotifyController(FeiShuNotifyHandler feiShuNotifyHandler,
                           RestTemplate restTemplate)
   {
      this.feiShuNotifyHandler = feiShuNotifyHandler;
      this.restTemplate = restTemplate;
   }

   @GetMapping("/notify")
   public String hello() {
      return feiShuNotifyHandler.notify(FeiShuTextNotifyRequest.of("报警了!!!"));
   }

   @GetMapping("/getIp")
   public String hello2() {
      return restTemplate.getForObject("http://httpbin.org/ip", String.class);
   }

}
