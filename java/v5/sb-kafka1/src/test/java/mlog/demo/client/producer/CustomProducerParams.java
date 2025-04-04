package mlog.demo.client.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author Jack Li
 * @date 2023/9/6 下午10:55
 * @description
 */
public class CustomProducerParams {
   public static void main(String[] args) {
      // 0 配置
      final Properties properties = new Properties();

      // 连接集群
      properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.1.109.140:9092");
      // 指定 Key 及 Value 的序列化类型
      properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

      // 提高 Kafka 生产者吞吐量
      // 缓冲区大小
      properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

      // 批次大小 batch size
      properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

      // 压缩
      properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

      // acks
      properties.put(ProducerConfig.ACKS_CONFIG, "1");

      // 重试次数
      properties.put(ProducerConfig.RETRIES_CONFIG, 3);

      // 1. 创建 Producer
      final KafkaProducer<String, String> producer
         = new KafkaProducer<>(properties);

      // 2. 发送数据
      for (int i = 0; i < 10; i++) {
         producer.send(new ProducerRecord<>("first", "Hello Kafka + " + i), (meta, exp) -> {
            if(exp == null ) {
               System.out.println(meta.topic() + "---" + meta.partition());
            }
         });
      }

      // 3. 释放资源
      producer.close();
   }
}
