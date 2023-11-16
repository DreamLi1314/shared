package com.geoviswtx.hive;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import javax.sql.rowset.serial.SerialArray;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class InsertTests {

    @Test
    void insertGfs() {
        // JDBC连接URL，需要根据你的Hive配置进行修改
        String jdbcURL = "jdbc:hive2://10.1.109.140:10000/db_etl";

        // Hive用户名和密码（可选）
        String user = "root";
        String password = "geoViswtx0605!";

        try {
            Date baseTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse("2023-11-01 08:00:00");

            // 加载Hive JDBC驱动程序
            Class.forName("org.apache.hive.jdbc.HiveDriver");

            // 创建连接
//            Connection connection = DriverManager.getConnection(jdbcURL);
            Connection connection = DriverManager.getConnection(jdbcURL, user, password);

            // 创建Statement
            String sql = "insert into t_grid values(?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

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

                float[][][] data = (float[][][]) tem.read().copyToNDJavaArray();

                // 设置变量信息
                statement.setLong(1, i + 10);
                statement.setLong(2, 177537570766708736L);
                statement.setString(3, "tem");
                statement.setTimestamp(4, new Timestamp(baseTime.getTime()));
                statement.setTimestamp(5, new Timestamp(baseTime.getTime()));
                statement.setString(6, "ARRAY(ARRAY(1, 2, 3), ARRAY(4, 5, 6))");

                // 执行 Hive SQL
                int result = statement.executeUpdate();

                System.out.println("data: " + result);
            }

            // 关闭连接
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
