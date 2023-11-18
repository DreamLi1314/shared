package com.geoviswtx.hive;

import com.cedarsoftware.util.DateUtilities;
import com.geoviswtx.common.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class InsertHdfsTests {
    @Test
    public void insertGfs() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date baseTime = dateFormat
                .parse("2023-11-01 08:00:00");

        Configuration configuration = new Configuration();

        FileSystem fs = null;

        try {
            fs = FileSystem.get(new URI("hdfs://10.1.109.140:8020"), configuration,"hadoop");

            File file = new File("C:\\Users\\javaf\\Desktop\\GFS");

            File[] files = file.listFiles();
            Pattern pattern = Pattern.compile("^gfs.t12z.pgrb2.0p25.f(\\d+)$");

            for (int i = 0; i < files.length; i++) {
                File nc = files[i];
                Matcher matcher = pattern.matcher(nc.getName());

                if (!matcher.matches()) {
                    log.info("{} not match nc！", nc.getName());
                    continue;
                }

                int hourOffset = Integer.parseInt(matcher.group(1));

                log.info("Process offset {} nc", hourOffset);

                NetcdfFile netcdfFile = NetcdfFile.openInMemory(nc.getAbsolutePath());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(baseTime);
                calendar.add(Calendar.HOUR_OF_DAY, hourOffset);

                Variable tem = netcdfFile.findVariable("Temperature_surface");

                float[] data = (float[]) tem.read().copyTo1DJavaArray();

                StringJoiner sj = new StringJoiner("\t");

                sj.add(i+100 + "");
                sj.add(177537570766708736L + "");
                sj.add("tem");
                sj.add(dateFormat.format(baseTime));
                sj.add(dateFormat.format(calendar.getTime()));
                sj.add(convertToString(data));

                String fileName = "000000_1" + i;
                String tempPath = "C:\\Workspace\\shared\\java\\v5\\sb-hive01\\hive-jdbc01\\target\\" + fileName;

                try(FileOutputStream out = new FileOutputStream(tempPath);
                    OutputStreamWriter osw = new OutputStreamWriter(out);
                    BufferedWriter bw = new BufferedWriter(osw))
                {
                    bw.write(sj.toString());
                }

                fs.copyFromLocalFile(true, true,
                        new Path(tempPath),
                        new Path("/user/hive/warehouse/db_etl.db/t_grid2/" + fileName));
            }
        }
        finally {
            if(fs != null) {
                fs.close();
            }
        }
    }

    public static String convertToString(float[] array) {
        return Arrays.toString(array)
                .replaceAll("\\[", "")  // 去掉左括号
                .replaceAll("]", "")    // 去掉右括号
                .replaceAll("\\s+", ""); // 去掉空格
    }
}
