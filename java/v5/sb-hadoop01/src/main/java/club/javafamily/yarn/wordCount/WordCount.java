package club.javafamily.yarn.wordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

import java.io.IOException;

/**
 * @author Jack Li
 * @date 2023/10/16 下午11:30
 * @description
 */
public class WordCount implements Tool {

   private Configuration configuration;

   @Override
   public int run(String[] args) throws Exception {
      // 1. 获取 Job
      Configuration conf = new Configuration();
      Job job = Job.getInstance(conf);

      // 2. 设置jar包路径
      job.setJarByClass(WordCountDriver.class);

      // 3. 关联 Mapper 和 Reducer
      job.setMapperClass(WordCountMapper.class);
      job.setReducerClass(WordCountReducer.class);

      // 4. 设置map输出类型
      job.setMapOutputKeyClass(Text.class);
      job.setMapOutputValueClass(IntWritable.class);

      // 5. 设置最终输出的kv类型
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(IntWritable.class);

      // 6. 设置输入路径和输出路径
      FileInputFormat.setInputPaths(job, new Path(args[0]));
      FileOutputFormat.setOutputPath(job, new Path(args[1]));

      // 7. 提交 Job
      boolean result = job.waitForCompletion(true);

      return result ? 0 : 1;
   }

   @Override
   public void setConf(Configuration configuration) {
      this.configuration = configuration;
   }

   @Override
   public Configuration getConf() {
      return configuration;
   }

   public static class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
      private Text text = new Text();
      private IntWritable count = new IntWritable(1);

      @Override
      protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
         String line = value.toString();

         String[] split = line.split(" ");

         for (String word : split) {
            text.set(word);

            context.write(text, count);
         }
      }
   }

   public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

      private IntWritable sumInt = new IntWritable(0);

      @Override
      protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
         int sum = 0;

         for (IntWritable value : values) {
            sum += value.get();
         }

         sumInt.set(sum);

         context.write(key, sumInt);
      }
   }

}
