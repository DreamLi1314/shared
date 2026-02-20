package com.jack.rabbitmq.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class DirectService {

    /**
     * 以 Message 对象接收消息.
     * @param message 封装了消息体和消息头的 Message 对象.
     */
    @RabbitListener(queues = "point.queue", ackMode = "MANUAL")
    public void receiveMessage(Message message, Channel channel) throws Exception {
        System.out.println("Receive Message Body: " + message.getBody());
        System.out.println("Receive Message Header: " + message.getMessageProperties());
//        double test = 1 / 0;
        System.out.println(channel);

        // 确认消息
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 监听 queue, 只要 Queue 中有消息, 该方法就会收到, 并自动转化为参数类型.
     * @param msg 消息
     */
//    @RabbitListener(queues = {"point.queue"})
//    public void receive(Map<String, Object> msg) {
//        System.out.println("Receive Map: " + msg);
//    }

    /**
     * 监听 queue, 只要 Queue 中有消息, 该方法就会收到, 并自动转化为参数类型.
     * @param msg
     */
//    @RabbitListener(queues = {"point.queue"})
//    public void receiveObject(Object msg) {
//        System.out.println("Receive Object: " + msg);
//    }

//    @RabbitListener(queues = {"point.queue"})
//    public void receive(byte[] msg) throws UnsupportedEncodingException {
//        System.out.println("Receive String: " + new String(msg, "utf-8"));
//    }
}
