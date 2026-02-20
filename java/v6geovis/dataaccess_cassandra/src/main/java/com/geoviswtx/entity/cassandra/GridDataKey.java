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
   private Long metaId;

   @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
   private Integer chunkId;

   @Tolerate
   public GridDataKey() {
   }
}
