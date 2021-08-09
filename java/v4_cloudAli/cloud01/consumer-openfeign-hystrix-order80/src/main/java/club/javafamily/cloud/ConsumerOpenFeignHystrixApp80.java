package club.javafamily.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Jack Li
 * @date 2021/7/31 2:26 下午
 * @description
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class ConsumerOpenFeignHystrixApp80 {
   public static void main(String[] args) {
      SpringApplication.run(ConsumerOpenFeignHystrixApp80.class, args);
   }
}
