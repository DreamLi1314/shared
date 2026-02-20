package mlog.demo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

/**
 * @author Jack Li
 * @date 2021/8/27 12:06 上午
 * @description
 */
@SpringBootApplication
public class App9411 {
   public static void main(String[] args) {
      SpringApplication.run(App9411.class, args);
   }

   @Autowired
   private DataSource dataSource;
}
