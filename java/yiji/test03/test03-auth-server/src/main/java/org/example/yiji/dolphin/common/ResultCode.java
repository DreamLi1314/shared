package org.example.yiji.dolphin.org.examplemon;

import lombok.Getter;

/**
 * @author wanglin
 * @date 2020/5/11 18:53
 */
@Getter
public enum ResultCode {

  SUCCESS(200,"请求成功"),
  FAIL(400,"请求失败"),
  SYSTEM_ERROR(500,"系统异常"),
  VALIDATE_ERROR(400,"参数校验失败"),
  RESPONSE_PACK_ERROR(500, "response返回包装失败"),
  TOKEN_NULL(40301, "token为空"),
  TOKEN_ERROR(40301,"token认证失败"),
  LOGIN_ERROR(40302, "登录失败!"),
  NO_PERMISSION(400,"权限未开启"),
  NO_USERINFO(40101,"无用户信息");

  private Integer code;
  private String msg;

  ResultCode(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

}
