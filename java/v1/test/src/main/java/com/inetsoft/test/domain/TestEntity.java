package com.inetsoft.test.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "t_test")
@Data
@Builder
public class TestEntity implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String stationId;

   private Integer year;
   private Integer month;
   private Integer day;

   private Integer lightning;
   private Integer wind;
   private Integer snow;
   private Integer thunder;
   private Integer dustStorm;
   private Integer hail;
   private Integer rain;
   private Integer daySnow;

   @Tolerate
   public TestEntity() {
   }
}
