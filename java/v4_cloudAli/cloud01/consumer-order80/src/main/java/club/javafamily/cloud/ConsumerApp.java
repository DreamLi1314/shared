package club.javafamily.cloud;

import club.javafamily.irule.conf.IRuleConf;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author Jack Li
 * @date 2021/7/31 2:26 下午
 * @description
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "PROVIDER-PAYMENT", configuration = IRuleConf.class)
public class ConsumerApp {
   public static void main(String[] args) {
      SpringApplication.run(ConsumerApp.class, args);
   }
}
