package mlog.demo.client.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author Jack Li
 * @date 2023/9/6 下午10:55
 * @description
 */
public class CustomProducerTransaction {
   public static void main(String[] args) {
      // 0 配置
      final Properties properties = new Properties();

      // 连接集群
      properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.1.109.140:9092");
      // 指定 Key 及 Value 的序列化类型
      properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

      // 必须配置事务 ID
      properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "uniqueID");

      // 1. 创建 Producer
      final KafkaProducer<String, String> producer
         = new KafkaProducer<>(properties);

      // 初始化事务
      producer.initTransactions();

      // 开启事务
      producer.beginTransaction();

      try {
         // 2. 发送数据
         for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("first", "Hello Kafka + " + i), (meta, exp) -> {
               if(exp == null ) {
                  System.out.println(meta.topic() + "---" + meta.partition());
               }
            });
         }

//         int err = 1 / 0;

         producer.commitTransaction();
      }
      catch (Exception e) {
         producer.abortTransaction();
      }
      finally {
         // 3. 释放资源
         producer.close();
      }
   }
}
