package mlog.demo.client.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jack Li
 * @date 2023/9/12 下午4:38
 * @description
 */
@RestController
public class KafkaConsumer {

   @KafkaListener(topics = "first")
   public void receiveMsg(String msg) {
      System.out.println(msg);
   }

}
