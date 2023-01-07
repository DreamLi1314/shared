package org.example.yiji.dolphin.model.PO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author wanglin
 * @date 2019/11/20 11:26
 */
@Data
public class DepartmentUpdatePo {

  @NotNull(message = "部门id不能为空")
  private Long id;//编号
  @NotBlank(message = "部门名称不能为空")
  private String name;//部门名称
  private Long departmentId;//上级部门编号

}
