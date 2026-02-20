package club.javafamily.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Jack Li
 * @date 2021/7/31 4:54 下午
 * @description
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaMain7001 {
   public static void main(String[] args) {
      SpringApplication.run(EurekaMain7001.class, args);
   }
}
