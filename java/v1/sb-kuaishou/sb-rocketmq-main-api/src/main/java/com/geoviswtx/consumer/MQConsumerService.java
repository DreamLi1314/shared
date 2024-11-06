//package com.geoviswtx.consumer;
//
//import com.alibaba.fastjson.JSONObject;
//import com.geoviswtx.dto.AppCallbackDto;
//import com.geoviswtx.producer.MQProducerService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@Component
//@ConditionalOnProperty(prefix = "consumer.enabled", name = "ksh", havingValue = "true")
//public class MQConsumerService {
//
//    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
//    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
//    @Service
//    @ConditionalOnProperty(prefix = "consumer.enabled", name = "ksh", havingValue = "true")
//    @RocketMQMessageListener(topic = MQProducerService.topic, selectorExpression = "like", consumerGroup = "ksh_Comsumer_Like")
//    public class ConsumerLike implements RocketMQListener<AppCallbackDto> {
//        @Override
//        @Transactional(rollbackFor = Exception.class)
//        public void onMessage(AppCallbackDto user) {
//            log.info("监听到点赞消息：{}", JSONObject.toJSONString(user));
//        }
//    }
//
//    @Service
//    @ConditionalOnProperty(prefix = "consumer.enabled", name = "ksh", havingValue = "true")
//    @RocketMQMessageListener(topic = MQProducerService.topic, selectorExpression = "comment", consumerGroup = "ksh_Comsumer_Comment")
//    public static class ConsumerComment implements RocketMQListener<AppCallbackDto> {
//        @Override
//        public void onMessage(AppCallbackDto user) {
//            log.info("监听到评论消息：{}", JSONObject.toJSONString(user));
//        }
//    }
//
//    @Service
//    @ConditionalOnProperty(prefix = "consumer.enabled", name = "ksh", havingValue = "true")
//    @RocketMQMessageListener(topic = MQProducerService.topic, selectorExpression = "gift", consumerGroup = "ksh_Comsumer_Gift")
//    public static class ConsumerGift implements RocketMQListener<AppCallbackDto> {
//        @Override
//        public void onMessage(AppCallbackDto user) {
//            log.info("监听到礼物消息：{}", JSONObject.toJSONString(user));
//        }
//    }
//
//    @ConditionalOnProperty(prefix = "consumer.enabled", name = "ksh", havingValue = "true")
//    @RocketMQMessageListener(topic = MQProducerService.topic, selectorExpression = "follow", consumerGroup = "ksh_Comsumer_Follow")
//    public static class ConsumerFollow implements RocketMQListener<AppCallbackDto> {
//        @Override
//        public void onMessage(AppCallbackDto user) {
//            log.info("监听到关注消息：{}", JSONObject.toJSONString(user));
//        }
//    }
//
//}
//
