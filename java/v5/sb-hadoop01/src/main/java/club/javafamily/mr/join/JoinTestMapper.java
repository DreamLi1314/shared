package club.javafamily.mr.join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class JoinTestMapper extends Mapper<LongWritable, Text, Text, ProductBean>  {

    private String fileName;

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, ProductBean>.Context context) throws IOException, InterruptedException {
        super.setup(context);
        FileSplit inputSplit = (FileSplit) context.getInputSplit();
        fileName = inputSplit.getPath().getName();
    }

    private Text outKey = new Text();
    private ProductBean outV = new ProductBean();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, ProductBean>.Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String[] split = line.split("\t");

        if (fileName.contains("order")) {
            outV.setOrderId(split[0]);
            outV.setProdId(split[1]);
            outV.setSales(Integer.parseInt(split[2]));
        }
        else {
            outV.setProdId(split[0]);
            outV.setProdName(split[1]);
            outV.setProd(true);
        }

        outKey.set(outV.getProdId());

        context.write(outKey, outV);
    }
}
