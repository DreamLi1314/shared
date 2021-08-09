package club.javafamily.cloud.controller;

import club.javafamily.cloud.entity.Payment;
import com.mlog.utils.common.ResultMsg;
import org.springframework.http.ResponseEntity;
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
//   private static final String BASE_URI = "http://localhost:8001";
   private static final String BASE_URI = "http://PROVIDER-PAYMENT";

   public OrderConsumerController(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   @GetMapping("/provider/payment/{id}")
   public ResultMsg<LinkedHashMap> get(@PathVariable("id") Long id) {
      ResultMsg<LinkedHashMap> response = restTemplate.getForObject(
         BASE_URI + "/provider/payment/" + id, ResultMsg.class);

      return response;
   }

   @GetMapping("/provider/payment2/{id}")
   public ResultMsg getForEntity(@PathVariable("id") Long id) {
      ResponseEntity<ResultMsg> response = restTemplate.getForEntity(
         BASE_URI + "/provider/payment/" + id, ResultMsg.class);

      System.out.println(response);

      if(response.getStatusCode().is2xxSuccessful()) {
         return response.getBody();
      }

      return new ResultMsg();
   }

   @PostMapping("/provider/payment")
   public ResultMsg<LinkedHashMap> create(@RequestBody Payment payment) {
      final ResultMsg<LinkedHashMap> response = restTemplate.postForObject(
         BASE_URI + "/provider/payment", payment, ResultMsg.class);

      return response;
   }
}
