package club.javafamily.demomonitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jack Li
 * @date 2022/8/11 下午6:10
 * @description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTemplateTest {

   @Autowired
   private RestTemplate restTemplate;

   @Test
   public void test() {
      final String response = restTemplate.getForObject("http://httpbin.org/ip", String.class);

      System.out.println(response);
   }

}
