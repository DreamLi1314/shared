package club.javafamily;

import club.javafamily.utils.FileConvertUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.io.*;

public class UtilsTest {

    @Test
    void test() throws Exception {
        File outFile = new File("./target/aaa.pdf");

        if(outFile.exists()) {
            if(outFile.delete()) {
                outFile.createNewFile();
            }
        }

        StopWatch stopWatch = new StopWatch();

        try(InputStream in = new FileInputStream("./target/aaa.doc");
            FileOutputStream out = new FileOutputStream(outFile))
        {
            stopWatch.start();

            FileConvertUtil.docToPdf(in, out);
        }

        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeMillis() + "ms");

        System.out.println();
    }

}
