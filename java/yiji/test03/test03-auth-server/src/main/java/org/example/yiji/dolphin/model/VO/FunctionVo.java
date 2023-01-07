package org.example.yiji.dolphin.model.VO;

import org.example.yiji.dolphin.model.primary.Function;
import lombok.Data;

/**
 * @author wanglin
 * @date 2020/2/25 11:30
 */
@Data
public class FunctionVo {

  private Long id;//编号
  private String name;//系统名
  private String key;//模块名
  private Boolean head;//级别(1: 系统, 2: 模块, 3: 功能)
  private Function parentFunction;//父权限

}
