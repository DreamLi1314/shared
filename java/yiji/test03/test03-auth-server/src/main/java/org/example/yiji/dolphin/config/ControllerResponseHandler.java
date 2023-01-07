package org.example.yiji.dolphin.config;

import org.example.fasterxml.jackson.core.JsonProcessingException;
import org.example.fasterxml.jackson.databind.ObjectMapper;
import org.example.yiji.dolphin.org.examplemon.CommonException;
import org.example.yiji.dolphin.org.examplemon.ResultCode;
import org.example.yiji.dolphin.org.examplemon.ResultMsg;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author wanglin
 * @date 2020/5/13 18:15
 */
//对controller进行增强
@RestControllerAdvice(basePackages = {"org.example.yiji.dolphin"})
public class ControllerResponseHandler implements ResponseBodyAdvice<Object> {

  //如果不是ResultMsg类就进行封装
  @Override
  public boolean supports(MethodParameter methodParameter,
      Class<? extends HttpMessageConverter<?>> aClass) {
    return !methodParameter.getParameterType().isAssignableFrom(ResultMsg.class);
  }

  //将返回信息封装进ResultMsg类
  @Override
  public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType,
      Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request,
      ServerHttpResponse response) {
    // String类型不能直接包装
    if (returnType.getGenericParameterType().equals(String.class)) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        // 将数据包装在ResultMsg里后转换为json串进行返回
        return objectMapper.writeValueAsString(new ResultMsg(data));
      } catch (JsonProcessingException e) {
        throw new CommonException(ResultCode.RESPONSE_PACK_ERROR, e.getMessage());
      }
    }
    // 否则直接包装成ResultMsg返回
    return new ResultMsg(data);
  }
}
