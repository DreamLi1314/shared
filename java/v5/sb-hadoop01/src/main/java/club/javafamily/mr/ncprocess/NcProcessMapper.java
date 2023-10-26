package club.javafamily.mr.ncprocess;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NcProcessMapper extends Mapper<LongWritable, Text, Text, ForecastData>  {

    private FileSystem fs;

    private final Text text = new Text();
    ForecastData dataOut = new ForecastData();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);

        Configuration configuration = new Configuration();

        try {
            fs = FileSystem.get(
               new URI("hdfs://10.1.109.140:8020"), configuration, "hadoop");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, ForecastData>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        log.info("line is: " + line);

        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH");
            Date baseTime = formater.parse(line);

            SimpleDateFormat pathFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
            String pathSuffix = pathFormat.format(baseTime);

            log.info("pathSuffix is: " + pathSuffix);

            FileStatus[] statuses = fs.listStatus(new Path("/hsl-data/gfs/" + pathSuffix));

            for (FileStatus status : statuses) {
//                NetcdfFile netcdfFile = NetcdfFile.openInMemory(
//                        new URI("hdfs://10.1.109.140:8020/" + status.getPath().toString()));

                NetcdfFile netcdfFile;

                log.info("prepared copy stream: {}", status.getPath().getName());

                byte[] ncData;

                try(FSDataInputStream ncIn = fs.open(status.getPath());
                    ByteArrayOutputStream bos = new ByteArrayOutputStream())
                {
                    long count = IOUtils.copy(ncIn, bos);
//                    bos.flush();
//                    log.info("---------------bos.size(): " + count);
                    ncData = bos.toByteArray();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }

                netcdfFile = NetcdfFile.openInMemory(
                   UUID.randomUUID().toString(), ncData);

                String elem = "Temperature_surface";
                Variable variable = netcdfFile.findVariable(elem);
                Array array = variable.read();
                float[] data = (float[]) array.copyTo1DJavaArray();

                String name = status.getPath().getName();
                String[] split = name.split("\\.");
                String str = split[split.length - 1];
                str = str.substring(1, str.length());

                float max = data[10];

                Calendar instance = Calendar.getInstance();
                instance.setTime(baseTime);
                instance.add(Calendar.HOUR_OF_DAY, Integer.parseInt(str));

                text.set(elem);
                dataOut.setTime(instance.getTime());
                dataOut.setData(max);

                context.write(text, dataOut);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Logger log = LoggerFactory.getLogger(this.getClass());
}
