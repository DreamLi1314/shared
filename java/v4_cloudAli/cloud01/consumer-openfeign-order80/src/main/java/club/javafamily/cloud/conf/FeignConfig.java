package club.javafamily.cloud.conf;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jack Li
 * @date 2021/8/8 12:17 下午
 * @description
 */
@Configuration
public class FeignConfig {
   @Bean
   public Logger.Level loggerLevel() {
      return Logger.Level.FULL;
   }
}
