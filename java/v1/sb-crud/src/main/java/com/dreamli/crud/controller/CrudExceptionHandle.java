package com.dreamli.crud.controller;

import com.dreamli.crud.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CrudExceptionHandle {

    /**
     * 浏览器和 HTTP 客户端都会返回 Json 数据, 没有自适应.
     * @param e
     * @param request
     * @return
     */
//    @ResponseBody
//    @ExceptionHandler(UserNotFoundException.class)
//    public Map<String, Object> handleUserNotFoundException(UserNotFoundException e,
//                                                           HttpServletRequest request)
//    {
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("code", "user.notfound");
//        map.put("msg", e.getMessage());
//
//        // 跳转到 /error 页面, 让 SpringBoot 为我们渲染错误页面
//        // return "forward:/error";
//        return map;
//    }

    /**
     * 浏览器返回自定义错误页面, HTTP 客户端返回 Json 数据, 有自适应效果
     * 但是不能定制 Json 数据类型.
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e,
                                                           HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap<>();

        // 传入我们自己的错误状态码  4xx 5xx，否则就不会进入定制错误页面的解析流程
        // 不传默认为 200, 请求成功就不会跳转到自定义的错误页面.
        /**
         * Integer statusCode = (Integer) request
         *   .getAttribute("javax.servlet.error.status_code");
         */
        request.setAttribute("javax.servlet.error.status_code",
                HttpStatus.NOT_FOUND.value());

        map.put("code", "user.notfound");
        map.put("msg", e.getMessage());

        // map 不能发送给客户端.

        // 跳转到 /error 页面, 让 SpringBoot 为我们渲染错误页面
         return "forward:/error";
    }

}
