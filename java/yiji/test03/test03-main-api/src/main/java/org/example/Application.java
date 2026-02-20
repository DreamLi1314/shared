package org.example;

import com.mlog.utils.Tool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

/**
 * @author Jack Li
 * @date 2022/6/28 1:21 下午
 * @description 危化品(雷电) Api
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class Application {
   public static void main(String[] args) {
      // setting default time zone
      TimeZone.setDefault(Tool.DEFAULT_TIME_ZONE);

      SpringApplication.run(Application.class, args);
   }
}
