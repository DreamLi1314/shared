package com.inetsoft.test.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Jack Li
 * @date 2021/7/31 10:55 上午
 * @description
 */
@Entity(name = "t_test2")
@Data
@Builder
public class Test2Entity implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String stationId;

   private Integer value;

   @Tolerate
   public Test2Entity() {
   }
}
