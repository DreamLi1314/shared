package org.example;

import com.mlog.utils.Tool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

/**
 * @author Jack Li
 * @date 2021/9/9 8:15 下午
 * @description
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class Application9079 {
   public static void main(String[] args) {
      // setting default time zone
      TimeZone.setDefault(Tool.DEFAULT_TIME_ZONE);

      SpringApplication.run(Application9079.class, args);
   }
}
