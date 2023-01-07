package org.example.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Li
 * @date 2022/4/25 10:29 上午
 * @description
 */
@Data
public class BaseIdsParamVo implements Serializable {
   @ApiModelProperty("批量导出 ID 数组")
   private Long[] ids;
}
