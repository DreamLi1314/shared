package club.javafamily.mr.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountDriver  {

    public static void main(String[] args) throws Exception {
        // 1. 获取 Job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2. 设置jar包路径
        job.setJarByClass(WordCountDriver.class);

        // 3. 关联 Mapper 和 Reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 设置输入格式
//        job.setInputFormatClass(CombineTextInputFormat.class);
//        CombineTextInputFormat.getMaxSplitSize(4096);

        // 4. 设置map输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5. 设置最终输出的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 6. 设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("dat"));
        FileOutputFormat.setOutputPath(job, new Path("target\\output1"));

        // 7. 提交 Job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }

}
