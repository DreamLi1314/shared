package club.javafamily.mr.customserielize;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean implements Writable {
    private double upFlow;
    private double downFlow;
    private double sumFlow;

    public FlowBean() {
    }

    public FlowBean(double upFlow, double downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.setSumFlow();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeDouble(upFlow);
        out.writeDouble(downFlow);
        out.writeDouble(sumFlow);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        upFlow = in.readDouble();
        downFlow = in.readDouble();
        sumFlow = in.readDouble();
    }

    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }

    public double getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(double upFlow) {
        this.upFlow = upFlow;
    }

    public double getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(double downFlow) {
        this.downFlow = downFlow;
    }

    public double getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(double sumFlow) {
        this.sumFlow = sumFlow;
    }

    public void setSumFlow() {
        this.sumFlow = upFlow + downFlow;
    }
}
