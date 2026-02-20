package club.javafamily.yarn.wordCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;

/**
 * @author Jack Li
 * @date 2023/10/17 下午8:10
 * @description
 */
public class WordCountDriver {

   private static Tool tool;

   public static void main(String[] args) throws Exception {
      Configuration configuration = new Configuration();

      switch (args[0]) {
         case "wordcount":
            tool = new WordCount();
            break;
         default:
            throw new RuntimeException("没有对应的 tool !");
      }

      final int run = ToolRunner.run(
         configuration, tool, Arrays.copyOfRange(args, 1, args.length));

      System.exit(run);
   }
}
