package com.geoviswtx.hive;

import org.junit.jupiter.api.Test;

import java.io.*;

public class StationTests {

    @Test
    void test() throws Exception {
        File file = new File("C:\\Workspace\\shared\\java\\v5\\sb-hive01\\asset\\202310302330MF05003.dat.xml");

        try(FileInputStream in = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(in, "utf-8");
            BufferedReader br = new BufferedReader(isr))
        {
            System.out.println(br.readLine());
        }

    }

}
