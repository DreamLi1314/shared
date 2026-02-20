package com.jack.rabbitmq;

import com.jack.rabbitmq.service.TopicService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqTopicTests {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布消息
     */
    @Test
    public void publishNotifyMsg() {
        String exchange = TopicService.EXCHANGE_POINT_TOPIC;
        String routerKey = "point.add.notify";
        Map<String, Object> message = new HashMap<>();
        message.put("id", 1);
        message.put("date", new Date());
        message.put("local", true);
        message.put("array", new Date[] {new Date()});
        message.put("list", Arrays.asList("1", "2", "3"));

        // 对象默认被序列化(JDK 序列化)后发送到 RabbitMQ.
        rabbitTemplate.convertAndSend(exchange, routerKey, message);

        System.out.println("Send message success...");
    }

    /**
     * 接收消息
     */
    @Test
    public void receiveNotifyMessage() {
        Object o = rabbitTemplate.receiveAndConvert(TopicService.QUEUE_POINT_NOTIFY);

        if(o != null) {
            System.out.println("Class: " + o.getClass() + ", Value: " + o);
        }
        else {
            System.out.println("Empty queue...");
        }
    }

    /**
     * 发布消息
     */
    @Test
    public void publishDataMsg() {
        // Send 以 routerKey 发送 Message 对象到 exchange.
        // Message 需要自己构造消息头和消息体
        //        rabbitTemplate.send(exchange, routerKey, message);

        String exchange = TopicService.EXCHANGE_POINT_TOPIC;
        String routerKey = "point.data";
        Map<String, Object> message = new HashMap<>();
        message.put("id", 1);
        message.put("date", new Date());
        message.put("local", true);
        message.put("array", new Date[] {new Date()});
        message.put("list", Arrays.asList("1", "2", "3"));

        // 对象默认被序列化(JDK 序列化)后发送到 RabbitMQ.
        rabbitTemplate.convertAndSend(exchange, routerKey, message);

        System.out.println("Send message success...");
    }

    /**
     * 接收消息
     */
    @Test
    public void receiveDataMessage() {
        Object o = rabbitTemplate.receiveAndConvert(TopicService.QUEUE_POINT_NOTIFY);

        if(o != null) {
            System.out.println("Class: " + o.getClass() + ", Value: " + o);
        }
        else {
            System.out.println("Empty queue...");
        }
    }

}
