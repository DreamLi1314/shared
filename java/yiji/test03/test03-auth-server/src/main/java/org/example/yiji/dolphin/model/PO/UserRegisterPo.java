package org.example.yiji.dolphin.model.PO;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author wanglin
 * @date 2019/11/22 17:08
 */
@Data
public class UserRegisterPo extends UserAddPo {

  @NotBlank(message = "验证码不能为空")
  private String code;//验证码

}
