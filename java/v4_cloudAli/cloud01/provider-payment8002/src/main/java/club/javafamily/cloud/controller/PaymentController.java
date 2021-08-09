package club.javafamily.cloud.controller;

import club.javafamily.cloud.entity.Payment;
import club.javafamily.cloud.service.PaymentService;
import com.mlog.utils.common.ResultCode;
import com.mlog.utils.common.ResultMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jack Li
 * @date 2021/7/31 12:51 下午
 * @description
 */
@RestController
public class PaymentController {

   private final PaymentService service;

   @Value("${server.port}")
   public Integer port;

   public PaymentController(PaymentService service) {
      this.service = service;
   }

   @GetMapping("/provider/payment/{id}")
   public ResultMsg<Payment> get(@PathVariable("id") Long id) {
      final ResultMsg<Payment> msg = new ResultMsg<>(ResultCode.SUCCESS, service.getById(id));

      msg.setMsg(msg.getMsg() + "----" + port);

      return msg;
   }

   @PostMapping("/provider/payment")
   public ResultMsg<Payment> create(@RequestBody Payment payment) {
      final ResultMsg<Payment> msg = new ResultMsg<>(ResultCode.SUCCESS, service.create(payment));

      msg.setMsg(msg.getMsg() + "----" + port);

      return msg;
   }
}
