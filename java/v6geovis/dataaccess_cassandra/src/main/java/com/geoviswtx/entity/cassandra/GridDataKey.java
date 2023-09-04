package com.geoviswtx.entity.cassandra;

import lombok.*;
import lombok.experimental.Tolerate;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@PrimaryKeyClass
@ToString
public class GridDataKey implements Serializable {

   @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
   private Long dsId;

   @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.PARTITIONED)
   private String elem;

   @PrimaryKeyColumn(ordinal = 3, type = PrimaryKeyType.PARTITIONED)
   private Double z;

   @PrimaryKeyColumn(ordinal = 4, type = PrimaryKeyType.PARTITIONED)
   private LocalDateTime baseTime;

   @PrimaryKeyColumn(ordinal = 5, type = PrimaryKeyType.CLUSTERED)
   private LocalDateTime forecastTime;

   @Tolerate
   public GridDataKey() {
   }
}
