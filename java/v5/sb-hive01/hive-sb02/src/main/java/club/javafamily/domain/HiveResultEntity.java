package club.javafamily.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Jack Li
 * @date 2023/10/25 下午7:30
 * @description
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_test")
public class HiveResultEntity {
   @Id
   private String id;
   private String name;
}
