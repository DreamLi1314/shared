package org.example.yiji.dolphin.model.VO;

import java.util.Set;

import org.example.yiji.dolphin.model.primary.Function;
import lombok.Data;

/**
 * @author wanglin
 * @date 2020/2/25 0:37
 */
@Data
public class RoleVo {

  private Long id;//编号
  private String name;//名称
  private Set<Function> functions;//菜单

}
