package club.javafamily.cloud.controller;

import club.javafamily.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jack Li
 * @date 2021/7/31 12:51 下午
 * @description
 */
@RestController
@Slf4j
public class PaymentController {

   private final PaymentService service;

   @Value("${server.port}")
   public Integer port;

   public PaymentController(PaymentService service) {
      this.service = service;
   }

   @GetMapping("/provider/hystrix/payment/ok/{id}")
   public String ok(@PathVariable("id") Long id) {
      return port + ">>>" + service.getById_OK(id);
   }

   @GetMapping("/provider/hystrix/payment/timeout/{id}")
   public String timeout(@PathVariable("id") Long id) {
      return port + ">>>" + service.getById_Timeout(id);
   }

   @GetMapping("/provider/hystrix/payment/circuit/{id}")
   public String paymentCircuitBreaker(@PathVariable("id") Long id) {
      return service.paymentCircuitBreaker(id);
   }

}
