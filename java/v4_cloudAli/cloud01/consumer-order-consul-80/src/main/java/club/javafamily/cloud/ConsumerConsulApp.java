package club.javafamily.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Jack Li
 * @date 2021/7/31 2:26 下午
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerConsulApp {
   public static void main(String[] args) {
      SpringApplication.run(ConsumerConsulApp.class, args);
   }
}
