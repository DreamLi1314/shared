package com.geoviswtx.consumer;

import com.alibaba.fastjson.JSONObject;
import com.geoviswtx.dto.TmeCallbackDto;
import com.geoviswtx.producer.MQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "consumer.enabled", name = "tme", havingValue = "true")
public class MQConsumerService {

    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
    @Service
    @ConditionalOnProperty(prefix = "consumer.enabled", name = "tme", havingValue = "true")
    @RocketMQMessageListener(topic = MQProducerService.topic, consumerGroup = "Con_Group_One")
    public static class ConsumerSend implements RocketMQListener<TmeCallbackDto> {
        @Override
        public void onMessage(TmeCallbackDto user) {
            log.info("监听到消息：{}", JSONObject.toJSONString(user));
        }
    }

}

