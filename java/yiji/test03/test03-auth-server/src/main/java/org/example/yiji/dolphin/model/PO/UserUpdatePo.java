package org.example.yiji.dolphin.model.PO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wanglin
 * @date 2019/11/22 17:17
 */
@Data
public class UserUpdatePo {

  @NotNull(message = "用户id不能为空")
  private Long id;//编号
  @NotBlank(message = "姓名不能为空")
  private String nickName;//用户名
  private String password;//密码
  private String phone;//手机
  private String mailbox;//邮箱
  private Long departmentId;//部门编号
  private String dept;//部门
  private Long roleId;//角色编号

}
