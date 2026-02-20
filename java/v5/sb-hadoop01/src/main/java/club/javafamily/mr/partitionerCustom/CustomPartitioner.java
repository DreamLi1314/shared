package club.javafamily.mr.partitionerCustom;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.mapreduce.Partitioner;

@InterfaceAudience.Public
@InterfaceStability.Stable
public class CustomPartitioner<K, V> extends Partitioner<K, V> {
    @Override
    public int getPartition(K k, V v, int numPartitions) {
        String phone = k.toString();

        if(ObjectUtils.isEmpty(phone)) {
            return 4;
        }
        else if (phone.startsWith("136")) {
            return 0;
        }
        else if (phone.startsWith("137")) {
            return 1;
        }
        else if (phone.startsWith("138")) {
            return 2;
        }
        else if (phone.startsWith("139")) {
            return 3;
        }
        else {
            return 4;
        }
    }
}
