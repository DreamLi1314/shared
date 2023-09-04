package com.geoviswtx.entity.cassandra;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

/**
 * @author Jack Li
 * @date 2023/9/4 下午1:35
 * @description
 */
@Data
@SuperBuilder(toBuilder = true)
@Table("db_grid_data")
@ToString
@NoArgsConstructor
public class GridDataEntity {

   @PrimaryKey
   protected GridDataKey gridDataKey;

//   private Integer chuckIdx;

   protected List<Float> ds;
}
