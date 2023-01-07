package org.example.yiji.dolphin.model.VO;

import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @author wanglin
 * @date 2019/11/22 16:12
 */
@Data
public class UserLoginVo {

  private Long id; // 用户 ID
  private String username;//用户名
  private String nickName; // 姓名
  private String token;//token
  private Set<String> functions;//菜单
  private Date expireDate;// 过期时间
  private String dept;// 部门信息
  private String enterprise; // 企业代号
  private String roleName; // 角色名

}
