package org.example.yiji.dolphin.model.VO;

import lombok.Data;

/**
 * @author wanglin
 * @date 2020/2/25 11:23
 */
@Data
public class DepartmentVo {

  private Long id;//编号
  private String name;//部门名称
  private String departmentName;//上级部门名称

}
