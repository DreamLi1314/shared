package club.javafamily.mr.join2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;

public class JoinTestDriver {

    public static void main(String[] args) throws Exception {
        // 1. 获取 Job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2. 设置jar包路径
        job.setJarByClass(JoinTestDriver.class);

        // 3. 关联 Mapper 和 Reducer
        job.setMapperClass(JoinTestMapper.class);
        job.setReducerClass(JoinTestReducer.class);

        // 4. 设置map输出类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(ProductBean.class);

        // 5. 设置最终输出的kv类型
        job.setOutputKeyClass(ProductBean.class);
        job.setOutputValueClass(NullWritable.class);

        // 添加缓存
        job.addCacheFile(new URI("file:///C:/Workspace/shared/java/v5/sb-hadoop01/dat/04join/cache/pd.txt"));
        // 不需要Reduce阶段
//        job.setNumReduceTasks(0);

        // 6. 设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("dat/04join/input"));
        FileOutputFormat.setOutputPath(job, new Path("target/output11"));

        // 7. 提交 Job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }

}
