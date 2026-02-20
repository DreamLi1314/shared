package club.javafamily.test;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ContextTests {

    @Test
    public void test() throws Exception {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.MONTH, 8);
        instance.set(Calendar.DAY_OF_MONTH, 27);
        instance.set(Calendar.HOUR_OF_DAY, 20);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");

        try(FileOutputStream out = new FileOutputStream("target/input.txt");
            OutputStreamWriter bos = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(bos))
        {
            for (int i = 0; i < 30; i++) {
                instance.add(Calendar.DAY_OF_MONTH, 1);

                String baseTime = dateFormat.format(instance.getTime());
                bw.write(baseTime);
                bw.newLine();
            }
        }
    }

}
