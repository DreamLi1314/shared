package club.javafamily.cloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Jack Li
 * @date 2021/8/2 11:02 下午
 * @description
 */
@FeignClient(
   value = "PROVIDER-HYSTRIX-PAYMENT",
   fallback = FallbackPaymentFeignService.class
)
public interface PaymentFeignService {
   @GetMapping("/provider/hystrix/payment/ok/{id}")
   String ok(@PathVariable("id") Long id);

   @GetMapping("/provider/hystrix/payment/timeout/{id}")
   String timeout(@PathVariable("id") Long id);

   @GetMapping("/provider/hystrix/payment/circuit/{id}")
   String paymentCircuitBreaker(@PathVariable("id") Long id);
}
