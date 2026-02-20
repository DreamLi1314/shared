package club.javafamily.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Jack Li
 * @date 2021/7/31 2:36 下午
 * @description payment app
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Provider8006App {

   public static void main(String[] args) {
      SpringApplication.run(Provider8006App.class, args);
      System.out.println("App provider payment running....");
   }

}
