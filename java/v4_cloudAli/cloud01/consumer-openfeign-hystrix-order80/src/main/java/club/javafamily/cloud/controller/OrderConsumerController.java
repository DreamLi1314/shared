package club.javafamily.cloud.controller;

import club.javafamily.cloud.service.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jack Li
 * @date 2021/7/31 3:33 下午
 * @description
 */
@RestController
@DefaultProperties(
   defaultFallback = "global_Timeout_Fallback",
   commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
   }
)
public class OrderConsumerController {

   private final PaymentFeignService paymentFeignService;

   public OrderConsumerController(PaymentFeignService paymentFeignService)
   {
      this.paymentFeignService = paymentFeignService;
   }

   @GetMapping("/consumer/hystrix/payment/ok/{id}")
   public String ok(@PathVariable("id") Long id) {
      return paymentFeignService.ok(id);
   }

   @GetMapping("/consumer/hystrix/payment/timeout/{id}")
//   @HystrixCommand(
//      fallbackMethod = "getById_Timeout_Fallback",
//      commandProperties = {
////         规定服务 3 秒内返回正常返回, 超过 3 秒出错走 fallback 服务降级处理
//         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//      }
//   )
   @HystrixCommand
   public String timeout(@PathVariable("id") Long id) {
      return paymentFeignService.timeout(id);
   }

   public String getById_Timeout_Fallback(Long id) {
      return "客户端-服务器繁忙, 请稍后再试!";
   }

   public String global_Timeout_Fallback() {
      return "Global--客户端-服务器繁忙, 请稍后再试!";
   }

   // ------- 服务熔断

   @GetMapping("/consumer/hystrix/payment/circuit/{id}")
   public String paymentCircuitBreaker(@PathVariable("id") Long id) {
      return paymentFeignService.paymentCircuitBreaker(id);
   }

}
