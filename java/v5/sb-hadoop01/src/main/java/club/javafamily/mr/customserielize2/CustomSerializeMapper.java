package club.javafamily.mr.customserielize2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CustomSerializeMapper extends Mapper<LongWritable, Text, Text, FlowBean>  {

    private Text text = new Text();

    FlowBean flowBean = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String[] split = line.split("\t");
        int length = split.length;

        text.set(split[1]);
        flowBean.setUpFlow(Double.parseDouble(split[length - 3]));
        flowBean.setDownFlow(Double.parseDouble(split[length - 2]));
        flowBean.setSumFlow();

        context.write(text, flowBean);
    }
}
