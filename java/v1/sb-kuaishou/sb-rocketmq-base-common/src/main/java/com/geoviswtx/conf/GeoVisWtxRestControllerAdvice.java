package com.geoviswtx.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geoviswtx.common.common.CommonException;
import com.geoviswtx.common.common.ResultCode;
import com.geoviswtx.common.date.DateUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * @author Jack Li
 * @date 2023/3/24 下午11:16
 * @description 处理 Date 返回携带双引号的问题
 */
@RestControllerAdvice(basePackages = {"com.geoviswtx.controller"})
public class GeoVisWtxRestControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * type not is ResultMsg type.
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> converterType)
    {
        return !methodParameter.getParameterType().isAssignableFrom(String.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response)
    {
        if (returnType.getGenericParameterType().equals(Date.class)) {
            return DateUtil.formatSystemNormalDateTime((Date) data);
        }

        return data;
    }

    private Object writeString(Object data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new CommonException(ResultCode.RESPONSE_PACK_ERROR, e.getMessage());
        }
    }
}
