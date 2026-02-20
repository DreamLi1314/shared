package club.javafamily;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;

/**
 * @author Jack Li
 * @date 2023/4/11 下午4:38
 * @description
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class Application {

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

   @Scheduled(fixedRate = 5000)
   public void test() {
      final long id = new Random().nextLong();

      if(id % 7 != 0) {
         log.info("这是一个测试Info日志信息: id is {}", id);
      }
      else {
         log.error("这是一个测试错误日志信息!", new Exception("id 不存在"));
      }

   }

}
