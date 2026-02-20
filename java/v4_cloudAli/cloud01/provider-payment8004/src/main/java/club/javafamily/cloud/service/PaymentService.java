package club.javafamily.cloud.service;

import club.javafamily.cloud.dao.PaymentRepository;
import club.javafamily.cloud.entity.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jack Li
 * @date 2021/7/31 12:49 下午
 * @description
 */
@Service
public class PaymentService {
   private final PaymentRepository repository;

   public PaymentService(PaymentRepository repository) {
      this.repository = repository;
   }

   @Transactional
   public Payment create(Payment payment) {
      return repository.save(payment);
   }

   public Payment getById(Long id) {
      return repository.findById(id).orElse(null);
   }
}
