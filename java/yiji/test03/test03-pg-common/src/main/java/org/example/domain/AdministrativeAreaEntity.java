package org.example.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.locationtech.jts.geom.MultiPolygon;

import javax.persistence.*;

/**
 * @author Jack Li
 * @date 2022/8/23 下午5:31
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_administrative")
@ApiModel("行政区划数据")
public class AdministrativeAreaEntity extends BaseEntity {

   @ApiModelProperty(value = "区域名称", name = "name")
   private String name;

   @ApiModelProperty(value = "区域代码", name = "code")
   private String code;

   @ApiModelProperty(value = "父级区域代码", name = "parentCode")
   private String parentCode;

   @ApiModelProperty(value = "区域边界", name = "geometry")
   private MultiPolygon geometry;

   @Transient
   @ApiModelProperty(value = "雷电次数 Vo", name = "num")
   @Builder.Default
   private Integer num = 0;

}
