package org.example.yiji.dolphin.org.examplemon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanglin
 * @date 2020/5/11 18:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMsg {

  private Integer code;
  private String msg;
  private Object data;

  // 默认返回成功状态码，数据对象
  public ResultMsg(Object data) {
    this.code = ResultCode.SUCCESS.getCode();
    this.msg = ResultCode.SUCCESS.getMsg();
    this.data = data;
  }

  // 返回指定状态码，数据对象
  public ResultMsg(ResultCode resultCode, Object data) {
    this.code = resultCode.getCode();
    this.msg = resultCode.getMsg();
    this.data = data;
  }

  public static ResultMsg success(){
    return new ResultMsg(null);
  }

  public static ResultMsg success(Object data){
    return new ResultMsg(data);
  }

  public static ResultMsg fail(ResultCode resultCode, Object data){
    return new ResultMsg(resultCode,data);
  }

}
