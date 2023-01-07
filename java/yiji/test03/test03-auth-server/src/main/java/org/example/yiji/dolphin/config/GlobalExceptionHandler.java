package org.example.yiji.dolphin.config;

import org.example.yiji.dolphin.org.examplemon.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author wanglin
 * @date 2020/5/13 11:21
 */
//异常处理
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * 处理请求对象属性不满足校验规则的异常信息
   *
   * @param request
   * @param exception
   * @return
   * @throws Exception
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResultMsg MethodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException exception) {
    List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
    StringBuilder sb = new StringBuilder();
    int x = 0;
    for (ObjectError error : allErrors) {
      sb.append(error.getDefaultMessage());
      x++;
      if(allErrors.size()!=x){
        sb.append("\n");
      }
    }

    final ResultMsg resultMsg = new ResultMsg(ResultCode.VALIDATE_ERROR, null);

    resultMsg.setMsg(sb.toString());

    return resultMsg;
  }

  /**
   * 处理请求单个参数不满足校验规则的异常信息
   *
   * @param request
   * @param exception
   * @return
   * @throws Exception
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResultMsg ConstraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException exception) {
    return new ResultMsg(ResultCode.FAIL.getCode(), exception.getMessage(), null);
  }

  /**
   * 处理自定义的异常信息
   *
   * @param request
   * @param exception
   * @return
   * @throws Exception
   */
  @ExceptionHandler(value = CommonException.class)
  public ResultMsg APIExceptionHandler(HttpServletRequest request, CommonException exception) {
    return new ResultMsg(exception.getCode(),exception.getMessage(),null);
  }

  /**
   * 处理未定义的其他异常信息
   *
   * @param request
   * @param exception
   * @return
   */
  @ExceptionHandler(value = Exception.class)
  public ResultMsg ExceptionHandler(HttpServletRequest request, Exception exception) {
    return new ResultMsg(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg(),exception.getMessage());
  }

}
