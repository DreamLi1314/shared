package org.example.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlog.utils.common.*;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Jack Li
 * @date 2021/8/2 11:49 上午
 * @description
 */
@RestControllerAdvice(basePackages = {"org.example.controller"})
public class MLogRestControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * type not is ResultMsg type.
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return !methodParameter.getParameterType().isAssignableFrom(ResultMsg.class);
    }

    /**
     * wrapper to ResultMsg
     */
    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // String类型不能直接包装
        if (returnType.getGenericParameterType().equals(String.class)) {
            return writeString(data);
        }
//    else if(data instanceof TableLens) {
//      final JSONObject json = LensTool.toJsonTable((TableLens) data);
//      return new ResultMsg<>(json);
//    }

        // 否则直接包装成ResultMsg返回
        return new ResultMsg<>(data);
    }

    private Object writeString(Object data) {
//        return new ResultMsg<>(data);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(new ResultMsg<>(data));
        } catch (Exception e) {
            throw new CommonException(ResultCode.RESPONSE_PACK_ERROR, e.getMessage());
        }
    }
}
