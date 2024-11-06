package com.geoviswtx.consumer;

import com.alibaba.fastjson.JSONObject;
import com.geoviswtx.common.date.DateUtil;
import com.geoviswtx.common.date.DateUtils;
import com.geoviswtx.dto.AppCallbackDto;
import com.geoviswtx.service.DispatcherMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Component
public class MQConsumerService {

    public static final String topic = "ksh";
    private static final long OVER_TIME = 5 * 60 * 60 * 1000;
    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
    @Service
    @RocketMQMessageListener(topic = topic, selectorExpression = "like",
            consumerGroup = "${rocketmq.consumer.group}-like")
    public static class ConsumerLike implements RocketMQListener<AppCallbackDto>, RocketMQPushConsumerLifecycleListener {

        @Override
        public void onMessage(AppCallbackDto user) {
            try {
                if(user == null || user.getData() == null || user.getTimestamp() == null) {
                    log.info("无效消息:{}", user);
                    return;
                }

                if(System.currentTimeMillis() - user.getTimestamp() > OVER_TIME) {
                    // 历史消息丢掉
                    log.info("历史消息丢弃: currentTimeMillis: {}, timestamp(): {}. Message: {}",
                            System.currentTimeMillis(), user.getTimestamp(), user);
                    return;
                }

                String msg = JSONObject.toJSONString(user);
                log.info("监听到点赞消息：{}", msg);

                // 发送 user 到 websocket
                DispatcherMessageService.sendOneMessage(user.getData().getRoom_code(), msg);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void prepareStart(DefaultMQPushConsumer consumer) {
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
            consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
        }
    }

    @Service
    @ConditionalOnProperty(prefix = "consumer.enabled", name = "ksh", havingValue = "true")
    @RocketMQMessageListener(topic = topic, selectorExpression = "comment",
            consumerGroup = "${rocketmq.consumer.group}-comment")
    public static class ConsumerComment implements RocketMQListener<AppCallbackDto>, RocketMQPushConsumerLifecycleListener {
        @Override
        public void onMessage(AppCallbackDto user) {
            try {
                if(user == null || user.getData() == null || user.getTimestamp() == null) {
                    log.info("无效消息:{}", user);
                    return;
                }

                if(System.currentTimeMillis() - user.getTimestamp() > OVER_TIME) {
                    // 历史消息丢掉
                    log.info("历史消息丢弃: currentTimeMillis: {}, timestamp(): {}. Message: {}",
                            System.currentTimeMillis(), user.getTimestamp(), user);
                    return;
                }

                String msg = JSONObject.toJSONString(user);
                log.info("监听到评论消息：{}", msg);
                DispatcherMessageService.sendOneMessage(user.getData().getRoom_code(), msg);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void prepareStart(DefaultMQPushConsumer consumer) {
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
            consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
        }
    }

    @Service
    @RocketMQMessageListener(topic = topic, selectorExpression = "gift",
            consumerGroup = "${rocketmq.consumer.group}-gift")
    public static class ConsumerGift implements RocketMQListener<AppCallbackDto>, RocketMQPushConsumerLifecycleListener {

        @Override
        public void prepareStart(DefaultMQPushConsumer consumer) {
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
            consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
        }

        @Override
        public void onMessage(AppCallbackDto user) {
            try {
                if(user == null || user.getData() == null || user.getTimestamp() == null) {
                    log.info("无效消息:{}", user);
                    return;
                }

                if(System.currentTimeMillis() - user.getTimestamp() > OVER_TIME) {
                    // 历史消息丢掉
                    log.info("历史消息丢弃: currentTimeMillis: {}, timestamp(): {}. Message: {}",
                            System.currentTimeMillis(), user.getTimestamp(), user);
                    return;
                }

                String msg = JSONObject.toJSONString(user);
                log.info("监听到礼物消息：{}", msg);
                DispatcherMessageService.sendOneMessage(user.getData().getRoom_code(), msg);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Service
    @RocketMQMessageListener(topic = topic, selectorExpression = "follow",
            consumerGroup = "${rocketmq.consumer.group}-follow")
    public static class ConsumerFollow implements RocketMQListener<AppCallbackDto> , RocketMQPushConsumerLifecycleListener {

        @Override
        public void prepareStart(DefaultMQPushConsumer consumer) {
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
            consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
        }

        @Override
        public void onMessage(AppCallbackDto user) {
            try {
                if(user == null || user.getData() == null || user.getTimestamp() == null) {
                    log.info("无效消息:{}", user);
                    return;
                }

                if(System.currentTimeMillis() - user.getTimestamp() > OVER_TIME) {
                    // 历史消息丢掉
                    log.info("历史消息丢弃: currentTimeMillis: {}, timestamp(): {}. Message: {}",
                            System.currentTimeMillis(), user.getTimestamp(), user);
                    return;
                }

                String msg = JSONObject.toJSONString(user);
                log.info("监听到关注消息：{}", msg);
                DispatcherMessageService.sendOneMessage(user.getData().getRoom_code(), msg);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

