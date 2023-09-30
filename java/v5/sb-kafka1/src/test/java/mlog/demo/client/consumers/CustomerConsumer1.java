package mlog.demo.client.consumers;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author Jack Li
 * @date 2023/9/8 上午10:42
 * @description
 */
public class CustomerConsumer1 {

   public static void main(String[] args) {
      // 0 配置
      final Properties properties = new Properties();

      // 连接集群
      properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.1.109.140:9092");
      // 指定 Key 及 Value 的反序列化类型
      properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

      // 配置 groupId
      properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");

      // 1. 创建 Consumer
      KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

      // 2. 订阅主题
      consumer.subscribe(Collections.singletonList("first"));

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
