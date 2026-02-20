package com.geoviswtx.consumer;

import com.alibaba.fastjson.JSONObject;
import com.geoviswtx.dto.AppCallbackDto;
import com.geoviswtx.service.DispatcherMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class MQConsumerService {

    public static final String topic = "ksh";
    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
    @Service
    @RocketMQMessageListener(topic = topic, selectorExpression = "like", consumerGroup = "ksh_Comsumer_Like")
    public static class ConsumerLike implements RocketMQListener<AppCallbackDto> {

        @Override
        public void onMessage(AppCallbackDto user) {
            String msg = JSONObject.toJSONString(user);
            log.info("监听到点赞消息：{}", msg);

            // 发送 user 到 websocket
            DispatcherMessageService.sendOneMessage(user.getData().getRoom_code(), msg);
        }
    }

    @Service
    @ConditionalOnProperty(prefix = "consumer.enabled", name = "ksh", havingValue = "true")
    @RocketMQMessageListener(topic = topic, selectorExpression = "comment", consumerGroup = "ksh_Comsumer_Comment")
    public static class ConsumerComment implements RocketMQListener<AppCallbackDto> {
        @Override
        public void onMessage(AppCallbackDto user) {
            String msg = JSONObject.toJSONString(user);
            log.info("监听到评论消息：{}", msg);
            DispatcherMessageService.sendOneMessage(user.getData().getRoom_code(), msg);
        }
    }

    @Service
    @RocketMQMessageListener(topic = topic, selectorExpression = "gift", consumerGroup = "ksh_Comsumer_Gift")
    public static class ConsumerGift implements RocketMQListener<AppCallbackDto> {
        @Override
        public void onMessage(AppCallbackDto user) {
            String msg = JSONObject.toJSONString(user);
            log.info("监听到礼物消息：{}", msg);
            DispatcherMessageService.sendOneMessage(user.getData().getRoom_code(), msg);
        }
    }

    @RocketMQMessageListener(topic = topic, selectorExpression = "follow", consumerGroup = "ksh_Comsumer_Follow")
    public static class ConsumerFollow implements RocketMQListener<AppCallbackDto> {
        @Override
        public void onMessage(AppCallbackDto user) {
            String msg = JSONObject.toJSONString(user);
            log.info("监听到关注消息：{}", msg);
            DispatcherMessageService.sendOneMessage(user.getData().getRoom_code(), msg);
        }
    }

}

