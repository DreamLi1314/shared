package com.jack.rabbitmq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DirectService {

    /**
     * 以 Message 对象接收消息.
     * @param message 封装了消息体和消息头的 Message 对象.
     */
    @RabbitListener(queues = "sb-queue")
    public void reveiveMessage(Message message) {
        System.out.println("Receive Message Body: " + message.getBody());
        System.out.println("Receive Message Header: " + message.getMessageProperties());
    }

    /**
     * 监听 sb-queue, 只要 Queue 中有消息, 该方法就会收到, 并自动转化为参数类型.
     * @param msg
     */
    @RabbitListener(queues = {"sb-queue"})
    public void receiveObject(Object msg) {
        System.out.println("Receive Object: " + msg);
    }

    /**
     * 监听 sb-queue, 只要 Queue 中有消息, 该方法就会收到, 并自动转化为参数类型.
     * @param msg
     */
    @RabbitListener(queues = {"sb-queue"})
    public void receive(Map<String, Object> msg) {
        System.out.println("Receive Map: " + msg);
    }

}
