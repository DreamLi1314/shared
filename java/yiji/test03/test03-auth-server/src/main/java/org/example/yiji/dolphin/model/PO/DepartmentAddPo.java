package org.example.yiji.dolphin.model.PO;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author wanglin
 * @date 2019/11/20 11:26
 */
@Data
public class DepartmentAddPo {

  @NotBlank(message = "部门名称不能为空")
  private String name;//部门名称
  private Long departmentId;//上级部门编号

}
