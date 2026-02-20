package club.javafamily.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Jack Li
 * @date 2023/6/8 上午9:40
 * @description
 */
@Slf4j
@Component
public class DeadLetterConsumer {

   @RabbitListener(queues = "QD")
   public void receiveD(Message message){
      String msg = new String(message.getBody());
      log.info("当前时间{}，收到死信队列的消息：{}", new Date(), msg);
   }
}
