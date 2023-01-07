package org.example.yiji.dolphin.org.examplemon;

import lombok.Data;

/**
 * @author wanglin
 * @date 2020/5/13 18:40
 */
@Data
public class CommonException extends RuntimeException {

  private int code;
  private String msg;

  // 手动设置异常
  public CommonException(ResultCode resultCode, String message) {
    // message用于用户设置抛出错误详情
    super(message);
    // 状态码
    this.code = resultCode.getCode();
    // 状态码配套的msg
    this.msg = resultCode.getMsg();
  }

}
