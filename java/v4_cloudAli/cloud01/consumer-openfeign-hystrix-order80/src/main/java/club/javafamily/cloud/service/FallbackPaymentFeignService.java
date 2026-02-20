package club.javafamily.cloud.service;

import org.springframework.stereotype.Component;

/**
 * @author Jack Li
 * @date 2021/8/8 10:30 下午
 * @description
 */
@Component
public class FallbackPaymentFeignService implements PaymentFeignService {
   @Override
   public String ok(Long id) {
      return "服务器异常, 请一会再试!";
   }

   @Override
   public String timeout(Long id) {
      return "服务器异常, 请一会再试!";
   }

   @Override
   public String paymentCircuitBreaker(Long id) {
      return "paymentCircuitBreaker Fallback";
   }
}
