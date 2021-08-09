package club.javafamily.cloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;

/**
 * @author Jack Li
 * @date 2021/8/9 9:06 上午
 * @description
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardApp9001 {
   public static void main(String[] args) {
      SpringApplication.run(HystrixDashboardApp9001.class, args);
   }

   /**
    * spring boot 2.0.2之后需要注册HystrixMetricsStreamServlet。
    * 启动类中添加如下代码：用来向监控中心Dashboard发送stream信息
    */
   @Bean
   public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
      HystrixMetricsStreamServlet streamServlet
         = new HystrixMetricsStreamServlet();
      ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean
         = new ServletRegistrationBean<>(streamServlet);
      registrationBean.setLoadOnStartup(1);
      registrationBean.addUrlMappings("/hystrix.stream");
      registrationBean.setName("HystrixMetricsStreamServlet");

      return registrationBean;
   }

}
