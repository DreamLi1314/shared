package mlog.demo.client.consumers;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * @author Jack Li
 * @date 2023/9/8 上午10:42
 * @description
 */
public class CustomerConsumerOffsetByTime {

   public static void main(String[] args) {
      // 0 配置
      final Properties properties = new Properties();

      // 连接集群
      properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.1.109.140:9092,10.1.109.141:9092");
      // 指定 Key 及 Value 的反序列化类型
      properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

      // 配置 groupId
      properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");

      // 1. 创建 Consumer
      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

      // 2. 订阅主题
      consumer.subscribe(Collections.singletonList("first"));

      // 指定位置消费
      // @warn: 异步
      Set<TopicPartition> assignment = consumer.assignment();

      // 解决获取分区异步问题: 保证分区分配方案已经制定完毕
      while (assignment.size() == 0) {
         consumer.poll(Duration.ofSeconds(1));

         assignment = consumer.assignment();
      }

      // 将时间转化为 offset
      Map<TopicPartition, Long> times = new HashMap<>();

      for (TopicPartition topicPartition : assignment) {
         // 一天前的数据
         times.put(topicPartition, System.currentTimeMillis()
            - 1 * 24 * 60 * 60 * 1000);
      }

      final Map<TopicPartition, OffsetAndTimestamp> partitionOffsetAndTimestampMap
         = consumer.offsetsForTimes(times);


      // 指定消费位置
      for (TopicPartition topicPartition : assignment) {
         final OffsetAndTimestamp offsetAndTimestamp
            = partitionOffsetAndTimestampMap.get(topicPartition);

         if(offsetAndTimestamp != null) {
            consumer.seek(topicPartition, offsetAndTimestamp.offset());
         }
      }

      // 3. 消费数据
      while (true) {
         final ConsumerRecords<String, String> records
            = consumer.poll(Duration.ofSeconds(1));

         for (ConsumerRecord<String, String> record : records) {
            System.out.println(record);
         }
      }

      // 3. 释放资源
   }

}