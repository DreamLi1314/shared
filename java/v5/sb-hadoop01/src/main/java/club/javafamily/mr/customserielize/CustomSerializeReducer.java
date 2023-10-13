package club.javafamily.mr.customserielize;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CustomSerializeReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

    FlowBean flowBean = new FlowBean();

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Reducer<Text, FlowBean, Text, FlowBean>.Context context) throws IOException, InterruptedException {
        double sumUp = 0, sumDown = 0;

        for (FlowBean value : values) {
            sumUp += value.getUpFlow();
            sumDown += value.getDownFlow();
        }

        flowBean.setUpFlow(sumUp);
        flowBean.setDownFlow(sumDown);
        flowBean.setSumFlow();

        context.write(key, flowBean);
    }
}
