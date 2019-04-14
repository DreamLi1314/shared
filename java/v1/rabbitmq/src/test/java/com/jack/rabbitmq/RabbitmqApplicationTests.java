package com.jack.rabbitmq;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        Assert.assertNotNull("Connect rabbitmq server failed...", rabbitAdmin);
        Assert.assertNotNull("Connect rabbitmq server failed...", rabbitTemplate);
    }

    /**
     * 使用 rabbitAdmin 创建 RabbitMQ 的交换器.
     */
    @Test
    public void addExchanges() {
        // create a direct exchange
        DirectExchange exchange = new DirectExchange("sb-direct", false, false);
        // 添加一个 Exchange, 如果添加的 Exchange 已经存在则不创建.
        rabbitAdmin.declareExchange(exchange);
        System.out.println("add direct exchange success...");
    }

    /**
     * 添加一个消息队列
     */
    @Test
    public void addQueue() {
        Queue queue = new Queue("sb-queue", false);

        // 如果 Queue 已经存在则不创建.
        rabbitAdmin.declareQueue(queue);

        System.out.println("add queue success...");
    }

    /**
     * 将 Queue 绑定到交换器.
     * <b>Exchange 可以与 Queue 或者 Exchange 绑定.</b>
     * <b>Queue 只能与 Exchange 绑定.</b>
     */
    @Test
    public void queueBindingExchange() {
        // 将 sb-queue 这个 QUEUE 绑定在 sb-direct 这个 exchange 上,
        // routerKey 为 sb-direct
        // DestinationType 指定 destination 的 type.
        Binding binding = new Binding("sb-queue",
                Binding.DestinationType.QUEUE,
                "sb-direct", "sb-direct", null);

        rabbitAdmin.declareBinding(binding);

        System.out.println("Binding success...");
    }

    /**
     * 发布消息
     */
    @Test
    public void publishMsg() {
        // Send 以 routerKey 发送 Message 对象到 exchange.
        // Message 需要自己构造消息头和消息体
    //        rabbitTemplate.send(exchange, routerKey, message);

        String exchange = "sb-direct";
        String routerKey = "sb-direct";
        Map<String, Object> message = new HashMap<>();
        message.put("id", 1);
        message.put("date", new Date());
        message.put("local", true);

        // 对象默认被序列化(JDK 序列化)后发送到 RabbitMQ.
        rabbitTemplate.convertAndSend(exchange, routerKey, message);

        System.out.println("Send message success...");
    }

    /**
     * 接收消息
     */
    @Test
    public void receiveMessage() {
        Object o = rabbitTemplate.receiveAndConvert("sb-queue");

        if(o != null) {
            System.out.println("Class: " + o.getClass() + ", Value: " + o);
        }
        else {
            System.out.println("Empty queue...");
        }
    }

}
