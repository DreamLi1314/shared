package club.javafamily.cloud.service;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Jack Li
 * @date 2021/7/31 12:49 下午
 * @description
 */
@Service
public class PaymentService {

   public String getById_OK(Long id) {
      return "OK--线程: " + Thread.currentThread().getName() + ", Id: " + id;
   }

   @HystrixCommand(
      fallbackMethod = "getById_Timeout_Fallback",
      commandProperties = {
//         规定服务 3 秒内返回正常返回, 超过 3 秒出错走 fallback 服务降级处理
         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
      }
   )
   public String getById_Timeout(Long id) {
//      int test = 10 / 0;

      try {
         TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
      }

      return "Timeout--线程: " + Thread.currentThread().getName() + ", Id: " + id;
   }

   public String getById_Timeout_Fallback(Long id) {
      return "服务器繁忙, 请稍后再试!";
   }

   // ---------------------------------------------------
   /**
    * 服务熔断
    * 所有的 commandProperties 都可以在 {@link HystrixCommandProperties} 找到
    */
   @HystrixCommand(
      fallbackMethod = "paymentCircuitBreakerFallback",
      // 开启服务容错, 对于 10 次请求在 10 s 内失败率达到 60% 时熔断服务
      commandProperties = {
         @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
         // 请求次数
         @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
         // 时间窗口期
         @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
         // 错误率
         @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
      }
   )
   public String paymentCircuitBreaker(@PathVariable("id") Long id) {
      if(id < 0) {
         throw new RuntimeException("ID 不能为负.");
      }

      final String serial = UUID.randomUUID().toString();

      return "服务熔断正常返回. " + Thread.currentThread().getName() + "---" + serial;
   }

   public String paymentCircuitBreakerFallback(@PathVariable("id") Long id) {
      return "ID 不能为负数哦, 亲...";
   }

}
