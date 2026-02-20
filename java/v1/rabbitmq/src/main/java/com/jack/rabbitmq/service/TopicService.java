package com.jack.rabbitmq.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Jack Li
 * @date 2022/7/25 上午10:45
 * @description
 */
@Service
public class TopicService {

   public static final String EXCHANGE_POINT_TOPIC = "point.topic";

   public static final String QUEUE_POINT_NOTIFY = "point.notify";

   public static final String QUEUE_POINT_DATA = "point.data";

   public static final String POINT_TOPIC_ROUTER_KEY = "point.*";

   @RabbitListener(bindings = {
      @QueueBinding(
         value = @Queue(name = QUEUE_POINT_NOTIFY, durable = "true"),
         exchange = @Exchange(name = EXCHANGE_POINT_TOPIC, type = "topic"),
         key = "#.notify"
      )
   })
   public void receiveMessageNotify(Map<String, Object> dto, Message message, Channel channel) throws Exception {
      System.out.println("receiveMessageNotify Receive Message Body: " + message.getBody());
      System.out.println("receiveMessageNotify Receive Message Header: " + message.getMessageProperties());

      // 确认消息
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
   }

   @RabbitListener(bindings = {
      @QueueBinding(
         value = @Queue(name = QUEUE_POINT_DATA, durable = "true"),
         exchange = @Exchange(name = EXCHANGE_POINT_TOPIC, type = "topic"),
         key = "point.add.#"
      )
   })
   public void receiveMessageData(Message message, Channel channel) throws Exception {
      System.out.println("receiveMessageData Receive Message Body: " + message.getBody());
      System.out.println("receiveMessageData Receive Message Header: " + message.getMessageProperties());

      // 确认消息
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
   }

}
