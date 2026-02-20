package club.javafamily.cloud.dao;

import club.javafamily.cloud.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Jack Li
 * @date 2021/7/31 12:48 下午
 * @description
 */
public interface PaymentRepository  extends JpaRepository<Payment, Long>,
   JpaSpecificationExecutor<Payment>
{
}
