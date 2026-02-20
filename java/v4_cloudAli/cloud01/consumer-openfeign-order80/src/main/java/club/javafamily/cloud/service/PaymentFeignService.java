package club.javafamily.cloud.service;

import club.javafamily.cloud.entity.Payment;
import com.mlog.utils.common.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Jack Li
 * @date 2021/8/2 11:02 下午
 * @description
 */
@FeignClient("PROVIDER-PAYMENT")
public interface PaymentFeignService {
   @GetMapping("/provider/payment/{id}")
   ResultMsg<Payment> get(@PathVariable("id") Long id);

   @GetMapping("/provider/payment/timeout/{id}")
   ResultMsg<Payment> getAndTimeout(@PathVariable("id") Long id);
}
