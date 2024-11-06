package com.geoviswtx.producer;

import com.geoviswtx.dto.DefaultMessage;
import com.geoviswtx.service.SnowFlakeService;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class TestProducer {

    @Autowired
    private MQProducerService producerService;

    private static DefaultMessage msg;

    @BeforeAll
    static void init() {
        long seq = System.currentTimeMillis();

        msg = new DefaultMessage();
    }

    @Test
    void testTag1() {
        producerService.send(msg);

        System.out.println("success!");
    }

    @Test
    void testTag2() {
        SendResult sendResult = producerService.sendTagMsg(msg);

        System.out.println("success!" + sendResult);
    }

}
