package club.javafamily.mr.ncprocess2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class NcProcessReducer extends Reducer<Text, ForecastData, Text, ForecastData> {

    ForecastData outV = new ForecastData();

    @Override
    protected void reduce(Text key, Iterable<ForecastData> values, Reducer<Text, ForecastData, Text, ForecastData>.Context context) throws IOException, InterruptedException {
        for (ForecastData value : values) {
            if(value.getData() > outV.getData()) {
                outV.setData(value.getData());
                outV.setBaseTime(value.getBaseTime());
                outV.setTime(value.getTime());
            }
        }

        context.write(key, outV);
    }
}
