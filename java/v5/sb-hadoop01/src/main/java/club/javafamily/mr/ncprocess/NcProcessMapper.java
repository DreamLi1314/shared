package club.javafamily.mr.ncprocess;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class NcProcessMapper extends Mapper<LongWritable, Text, Text, ForecastData>  {

    private final Text text = new Text();

    ForecastData dataOut = new ForecastData();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, ForecastData>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        log.info("line is: " + line);
        Configuration configuration = new Configuration();

        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH");
            Date baseTime = formater.parse(line);

            SimpleDateFormat pathFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
            String pathSuffix = pathFormat.format(baseTime);

            log.info("pathSuffix is: " + pathSuffix);

            FileSystem fs = FileSystem.get(new URI("hdfs://10.1.109.140:8020"), configuration,"hadoop");

            FileStatus[] statuses = fs.listStatus(new Path("/hsl-data/gfs/" + pathSuffix));

            for (FileStatus status : statuses) {
//                NetcdfFile netcdfFile = NetcdfFile.openInMemory(
//                        new URI("hdfs://10.1.109.140:8020/" + status.getPath().toString()));

                NetcdfFile netcdfFile;

                try(FSDataInputStream ncIn = fs.open(status.getPath());
                    ByteArrayOutputStream bos = new ByteArrayOutputStream())
                {
                    IOUtils.copy(ncIn, bos);
                    bos.flush();
                    log.info("---------------bos.size(): " + bos.size());

                    netcdfFile = NetcdfFile.openInMemory(line + status.getPath().getName(), bos.toByteArray());
                }

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
}
