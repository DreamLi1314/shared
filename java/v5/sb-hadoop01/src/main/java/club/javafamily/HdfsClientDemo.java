package club.javafamily;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

/**
 * @author Jack Li
 * @date 2023/9/30 下午10:06
 * @description
 */
public class HdfsClientDemo {

   private FileSystem fs;

   @Before
   public void init() throws Exception {
      // 1 获取文件系统
      Configuration configuration = new Configuration();

      fs = FileSystem.get(new URI("hdfs://10.1.109.140:8020"), configuration,"hadoop");
   }

   @After
   public void destroy() throws IOException {
      // 3 关闭资源
      fs.close();
   }

   @Test
   public void mkdir() throws Exception {
      // 2 创建目录
      fs.mkdirs(new Path("/demo/huaguoshan/"));

      System.out.println();
   }

   @Test
   public void copyFromLocale() throws Exception {
      fs.copyFromLocalFile(false, true,
              new Path("C:\\Users\\javaf\\Desktop\\Hycom\\runs_GLBy0.nc4"),
              new Path("/tmp/"));

      System.out.println();
   }

   @Test
   public void checkFileAndDir() throws Exception {
      FileStatus[] fileStatuses = fs.listStatus(new Path("/tmp"));

      for (FileStatus fileStatus : fileStatuses) {
         if (fileStatus.isFile()) {
            System.out.println(fileStatus.getPath().getName() + " is file!");
         }
         else {
            System.out.println(fileStatus.getPath().getName() + " is dir!");
         }
      }
   }
}
