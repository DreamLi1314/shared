package club.javafamily.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Jack Li
 * @date 2023/6/8 上午9:41
 * @description
 */
@Slf4j
@RestController
public class SendMsgController {

   private final RabbitTemplate rabbitTemplate;

   public SendMsgController(RabbitTemplate rabbitTemplate) {
      this.rabbitTemplate = rabbitTemplate;
   }

   @GetMapping("/ttl/sendMsg/{message}")
   public String sendMsg(@PathVariable String message){
      log.info("当前时间：{}发送一条消息{}给两个队列", new Date(), message);
      rabbitTemplate.convertAndSend("X", "XA", "消息来自TTL为10s队列QA："+message);
      rabbitTemplate.convertAndSend("X", "XB", "消息来自TTL为40s队列QB："+message);
      return "发送成功";
   }
}
