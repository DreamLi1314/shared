package com.mlog.ftptests;

import com.mlog.utils.ftp.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

class FtpTestsApplicationTests {

    @Test
    void testWrite() throws IOException {
        final Instant startTime = Instant.now();

        ClassPathResource resource = new ClassPathResource(".");
        final File root = resource.getFile();
        String basePath = "/test";

        FTPClient client = FtpUtil.createClient("lyzh.yijiweather.com",
           9004, "emlog", "yiJi123!@#", basePath);
        System.out.println("Work dir: " + client.printWorkingDirectory());

        try {
            for (File dir : root.listFiles()) {
                if(!dir.getName().startsWith("2022")) {
                    continue;
                }

                if(!FtpUtil.changeDirectory(client, basePath)
                   || !FtpUtil.changeDirectory(client, dir.getName()))
                {
                    System.err.println("创建目录失败: " + dir.getName());
                    return;
                }

                System.out.println("开始上传目录: " + dir.getName());

                for (File file : dir.listFiles()) {
                    final String fileName = file.getName();
                    System.out.println("开始上传: " + fileName);

                    try(final FileInputStream in = new FileInputStream(file)) {
                        final boolean success = client.storeFile(fileName, in);

                        if(!success) {
                            System.err.println(fileName + " 上传失败!");
                        }
                    }
                }
            }
        }
        finally {
            client.disconnect();
            System.out.println("程序执行共用时: " + startTime.until(
               Instant.now(), ChronoUnit.SECONDS) + " 秒");
        }
    }
}
