package club.javafamily.cloud.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Jack Li
 * @date 2021/7/31 12:41 下午
 * @description
 */
@Data
@Entity(name = "t_payment")
public class Payment implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String serial;
}
