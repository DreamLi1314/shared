package club.javafamily;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;

/**
 * @author Jack Li
 * @date 2023/9/30 下午10:06
 * @description
 */
public class HdfsClientDemo {
   public static void main(String[] args) throws Exception {
      // 1 获取文件系统
      Configuration configuration = new Configuration();

      FileSystem fs = FileSystem.get(new URI("hdfs://10.1.109.140:8020"), configuration,"hadoop");

      // 2 创建目录
      fs.mkdirs(new Path("/demo/huaguoshan/"));

      // 3 关闭资源
      fs.close();
   }
}
