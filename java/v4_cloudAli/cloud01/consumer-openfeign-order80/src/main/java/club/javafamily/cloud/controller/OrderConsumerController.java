package club.javafamily.cloud.controller;

import club.javafamily.cloud.entity.Payment;
import club.javafamily.cloud.service.PaymentFeignService;
import com.mlog.utils.common.ResultMsg;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

/**
 * @author Jack Li
 * @date 2021/7/31 3:33 下午
 * @description
 */
@RestController
public class OrderConsumerController {

   private final RestTemplate restTemplate;
   private static final String BASE_URI = "http://PROVIDER-PAYMENT";
   private final PaymentFeignService paymentFeignService;

   public OrderConsumerController(RestTemplate restTemplate,
                                  PaymentFeignService paymentFeignService)
   {
      this.restTemplate = restTemplate;
      this.paymentFeignService = paymentFeignService;
   }

   @GetMapping("/consumer/payment/{id}")
   public ResultMsg<LinkedHashMap> get(@PathVariable("id") Long id) {
      ResultMsg<LinkedHashMap> response = restTemplate.getForObject(
         BASE_URI + "/provider/payment/" + id, ResultMsg.class);

      return response;
   }

   @GetMapping("/consumer/payment/feign/{id}")
   public ResultMsg<Payment> get2(@PathVariable("id") Long id) {
      ResultMsg<Payment> response = paymentFeignService.get(id);

      return response;
   }

   @GetMapping("/consumer/payment/feign/timeout/{id}")
   public ResultMsg<Payment> getAndTimeout(@PathVariable("id") Long id) {
      ResultMsg<Payment> response = paymentFeignService.getAndTimeout(id);

      return response;
   }
}
