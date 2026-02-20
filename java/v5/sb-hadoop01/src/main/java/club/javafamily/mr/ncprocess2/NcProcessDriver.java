package club.javafamily.mr.ncprocess2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class NcProcessDriver {

    public static void main(String[] args) throws Exception {
        // 1. 获取 Job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2. 设置jar包路径
        job.setJarByClass(NcProcessDriver.class);

        // 3. 关联 Mapper 和 Reducer
        job.setMapperClass(NcProcessMapper.class);
        job.setReducerClass(NcProcessReducer.class);

        // 4. 设置map输出类型: 要素-数据（时次）
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(ForecastData.class);

        // 5. 设置最终输出的kv类型: 要素-List<数据（时次）>
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(ForecastData.class);

        // 6. 设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 7. 提交 Job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }

}
