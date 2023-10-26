package club.javafamily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Jack Li
 * @date 2023/10/25 下午7:21
 * @description
 */
@SpringBootApplication
@EnableJpaRepositories
public class Application {

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

}
