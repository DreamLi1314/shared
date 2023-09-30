package club.javafamily;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.*;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author Jack Li
 * @date 2023/9/23 下午1:49
 * @description 批处理, 本地文本
 */
public class WorldCountBatchDemo {

   public static void main(String[] args) throws Exception {
      // 1. 创建环境
      ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

      // 2.  读取数据
      final DataSource<String> ds = env.readTextFile("dat/word_count.txt");

      // 3. 切分转换 --> (word, 1)
      final FlatMapOperator<String, Tuple2<String, Long>> wordCountMap = ds.flatMap(new FlatMapFunction<String, Tuple2<String, Long>>() {
         public void flatMap(String word, Collector<Tuple2<String, Long>> collector) throws Exception {
            final String[] split = word.split(" ");

            for (String w : split) {
               final Tuple2<String, Long> tuple2 = Tuple2.of(w, 1L);

               collector.collect(tuple2);
            }
         }
      });

      // 4. 分组
      final UnsortedGrouping<Tuple2<String, Long>> group = wordCountMap.groupBy(0);

      // 5. 分组内聚合
      final AggregateOperator<Tuple2<String, Long>> agg = group.sum(1);

      // 6. 输出
      agg.print();
   }

}
