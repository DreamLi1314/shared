package club.javafamily.mr.ncprocess2;

import lombok.*;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForecastData implements Writable {

    private Date baseTime;

    private Date time;

    private double data;

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(baseTime.getTime());
        out.writeLong(time.getTime());
        out.writeDouble(data);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        baseTime = new Date(in.readLong());
        time = new Date(in.readLong());
        data = in.readDouble();
    }
}
