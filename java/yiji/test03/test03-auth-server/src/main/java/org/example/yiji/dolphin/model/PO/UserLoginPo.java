package org.example.yiji.dolphin.model.PO;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wanglin
 * @date 2019/11/22 16:12
 */
@Data
public class UserLoginPo {

  @NotBlank(message = "用户名不能为空")
  private String username;//用户名
  @NotBlank(message = "密码不能为空")
  private String password;//密码
  private String code;//验证码

}
