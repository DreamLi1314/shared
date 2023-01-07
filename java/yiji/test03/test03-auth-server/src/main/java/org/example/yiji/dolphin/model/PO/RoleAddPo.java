package org.example.yiji.dolphin.model.PO;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author wanglin
 * @date 2019/12/24 16:53
 */
@Data
public class RoleAddPo {
  @NotBlank(message = "角色名称不能为空")
  private String name;//角色名称
  private Set<String> functionIds;//所拥有的所有权限的id
}
