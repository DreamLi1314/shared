package club.javafamily.mr.join;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ProductBean implements Writable {

    private String orderId;

    private String prodId;

    private String prodName;

    private int sales;

    private boolean prod = false;

    public ProductBean() {
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId == null ? "" : orderId);
        out.writeUTF(prodId);
        out.writeUTF(prodName == null ? "" : prodName);
        out.writeInt(sales);
        out.writeBoolean(prod);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        orderId = in.readUTF();
        prodId = in.readUTF();
        prodName = in.readUTF();
        sales = in.readInt();
        prod = in.readBoolean();
    }

    @Override
    public String toString() {
        return prodId + '\t' + prodName + '\t' + sales;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public boolean isProd() {
        return prod;
    }

    public void setProd(boolean prod) {
        this.prod = prod;
    }
}
