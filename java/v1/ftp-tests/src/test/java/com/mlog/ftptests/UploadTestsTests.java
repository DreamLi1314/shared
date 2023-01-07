package com.mlog.ftptests;

import com.mlog.utils.date.DateUtil;
import com.mlog.utils.ftp.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

class UploadTestsTests {

    @Test
    void testRead() throws IOException {
        FTPClient client = FtpUtil.createClient("lyzh.yijiweather.com",
           9004, "emlog", "yiJi123!@#", "/");

        final FTPFile[] files = client.listFiles();

        for (FTPFile file : files) {
            if(file.isDirectory()) {
                System.out.println("目录: " + file.getName());
                continue;
            }

            System.out.println("Name: " + file.getName()
               + "Size: " + file.getSize());

            try(FastByteArrayOutputStream out
                   = FtpUtil.receiveFileOutputStream(client, file.getName(), 2))
            {
                final byte[] bytes = out.toByteArray();

                System.out.println("---------------\n"
                   + new String(bytes, StandardCharsets.UTF_8)
                   + "\n------------");
            }
        }
    }

    @Test
    void testWrite() throws IOException {
        FTPClient client = FtpUtil.createClient("lyzh.yijiweather.com",
           9004, "emlog", "yiJi123!@#", "/");

        System.out.println("Work dir: " + client.printWorkingDirectory());

        String fileName = "test-file-" + DateUtil.formatCmaDate(new Date());
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();

        // 写数据
        out.write((fileName + " ---- by 李帅!").getBytes(StandardCharsets.UTF_8));

        try(InputStream in = out.getInputStream()) {
            final boolean success = client.storeFile(fileName, in);

            Assertions.assertTrue(success, "上传失败!");

            System.out.println("写文件成功" + fileName);
        }
        finally {
            out.close();
        }
    }
}
