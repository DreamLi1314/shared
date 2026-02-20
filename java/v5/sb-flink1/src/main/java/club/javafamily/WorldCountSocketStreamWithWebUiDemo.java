package club.javafamily;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author Jack Li
 * @date 2023/9/23 下午1:49
 * @description 流处理无界流
 */
public class WorldCountSocketStreamWithWebUiDemo {

   public static void main(String[] args) throws Exception {
      // 1. 创建环境
      StreamExecutionEnvironment env
         = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

      env.setParallelism(3);

      // 2.  读取数据
      final DataStreamSource<String> ds = env.socketTextStream("10.1.109.141", 11000);

      // 3. 切分转换 --> (word, 1)
      final SingleOutputStreamOperator<Tuple2<String, Long>> wordCountMap = ds.flatMap(new FlatMapFunction<String, Tuple2<String, Long>>() {
         public void flatMap(String word, Collector<Tuple2<String, Long>> collector) throws Exception {
            final String[] split = word.split(" ");

            for (String w : split) {
               final Tuple2<String, Long> tuple2 = Tuple2.of(w, 1L);

               collector.collect(tuple2);
            }
         }
      })
//         .setParallelism(2)
         ;

      // 4. 分组
      final KeyedStream<Tuple2<String, Long>, String> group = wordCountMap.keyBy(new KeySelector<Tuple2<String, Long>, String>() {
         public String getKey(Tuple2<String, Long> stringLongTuple2) throws Exception {
            return stringLongTuple2.f0;
         }
      });

      // 5. 分组内聚合
      final SingleOutputStreamOperator<Tuple2<String, Long>> agg = group.sum(1);

      // 6. 输出
      agg.print();

      // 7. 执行
      env.execute();
   }

}
