package club.javafamily.irule.conf;

import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jack Li
 * @date 2021/8/2 9:37 下午
 * @description
 */
@Configuration
public class IRuleConf {
   @Bean
   public IRule iRule() {
      return new BestAvailableRule();
   }
}
