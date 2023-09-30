package mlog.demo.client.partitions;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @author Jack Li
 * @date 2023/9/7 上午9:43
 * @description
 */
public class MyPartitioner implements Partitioner {
   @Override
   public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

      System.out.println(value.toString());

      System.out.println(key);

      System.out.println(cluster.toString());

      return 0;
   }

   @Override
   public void close() {

   }

   @Override
   public void configure(Map<String, ?> configs) {

   }
}
