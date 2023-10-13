package club.javafamily.mr.join2;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class JoinTestMapper extends Mapper<LongWritable, Text, Text, ProductBean>  {

    private Map<String, String> map = new HashMap<>();

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, ProductBean>.Context context) throws IOException, InterruptedException {
        super.setup(context);
        URI[] cacheFiles = context.getCacheFiles();

        FileSystem fs = FileSystem.get(context.getConfiguration());

        try(FSDataInputStream fis = fs.open(new Path(cacheFiles[0]));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr))
        {
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split("\t");

                map.put(split[0], split[1]);
            }
        }
    }

    private Text outKey = new Text();
    private ProductBean outV = new ProductBean();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, ProductBean>.Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String[] split = line.split("\t");
        String prodId = split[1];

        outV.setOrderId(split[0]);
        outV.setProdId(prodId);
        outV.setSales(Integer.parseInt(split[2]));
        outV.setProdName(map.get(prodId));

        outKey.set(prodId);

        context.write(outKey, outV);
    }
}
