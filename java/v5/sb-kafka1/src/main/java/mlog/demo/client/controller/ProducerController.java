package mlog.demo.client.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jack Li
 * @date 2023/9/12 下午4:38
 * @description
 */
@RestController
public class ProducerController {

   private final KafkaTemplate<String, String> kafka;

   public ProducerController(KafkaTemplate<String, String> kafka) {
      this.kafka = kafka;
   }

   @GetMapping("/send")
   public String send(String msg) {
      kafka.send("first", msg);

      return msg;
   }

}
