package club.javafamily.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Jack Li
 * @date 2021/7/31 2:36 下午
 * @description payment app
 */
@SpringBootApplication
@EnableEurekaClient
public class Provider8002App {

   public static void main(String[] args) {
      SpringApplication.run(Provider8002App.class, args);
      System.out.println("App provider payment running....");
   }

}
