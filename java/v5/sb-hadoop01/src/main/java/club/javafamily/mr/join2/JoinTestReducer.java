package club.javafamily.mr.join2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class JoinTestReducer extends Reducer<Text, ProductBean, ProductBean, NullWritable> {

    private ProductBean outKey = new ProductBean();

    @Override
    protected void reduce(Text key, Iterable<ProductBean> values, Reducer<Text, ProductBean, ProductBean, NullWritable>.Context context) throws IOException, InterruptedException {
        outKey.setSales(0);

        for (ProductBean item : values) {
            outKey.setProdId(item.getProdId());
            outKey.setProdName(item.getProdName());
            outKey.setSales(outKey.getSales() + item.getSales());
        }

        context.write(outKey, NullWritable.get());
    }
}
