package club.javafamily.cloud.controller;

import club.javafamily.cloud.entity.Payment;
import club.javafamily.cloud.service.PaymentService;
import com.mlog.utils.common.ResultCode;
import com.mlog.utils.common.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Jack Li
 * @date 2021/7/31 12:51 下午
 * @description
 */
@RestController
@Slf4j
public class PaymentController {

   private final PaymentService service;

   private final DiscoveryClient discoveryClient;

   @Value("${server.port}")
   public Integer port;

   public PaymentController(PaymentService service, DiscoveryClient discoveryClient) {
      this.service = service;
      this.discoveryClient = discoveryClient;
   }

   @GetMapping("/provider/payment/{id}")
   public ResultMsg<Payment> get(@PathVariable("id") Long id) {
      final ResultMsg<Payment> msg = new ResultMsg<>(ResultCode.SUCCESS, service.getById(id));

      msg.setMsg(msg.getMsg() + "----" + port);

      return msg;
   }

   @GetMapping("/provider/payment/timeout/{id}")
   public ResultMsg<Payment> getAndTimeout(@PathVariable("id") Long id) throws InterruptedException {

      // feign 客户端默认等待 1 秒钟, 请求没有返回就报错, 这里模拟复杂业务 3 秒钟
      TimeUnit.SECONDS.sleep(3);

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

   @GetMapping("/provider/payment/discovery")
   public Object discovery() {
      final List<String> services = discoveryClient.getServices();

      for (String service : services) {
         log.info("======element service: " + service);
      }

      final List<ServiceInstance> instances = discoveryClient.getInstances("PROVIDER-PAYMENT");

      for (ServiceInstance instance : instances) {
         log.info("********* instance: " + instance.getServiceId()
            + "\t" + instance.getHost()
            + "\t" + instance.getPort()
            + "\t" + instance.getUri()
         );
      }

      return discoveryClient;
   }
}
