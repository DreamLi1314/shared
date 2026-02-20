package org.example.conf;

import club.javafamily.nf.request.FeiShuCardNotifyRequest;
import club.javafamily.nf.service.FeiShuNotifyHandler;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mlog.utils.common.*;
import com.mlog.utils.date.DateUtil;
import org.example.constant.LogConstant;
import org.example.service.RequestInfoHolder;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

/**
 * 全局异常处理器
 */
@RestControllerAdvice(basePackages = {"org.example.controller"})
public class GlobalExceptionHandler {

    private final FeiShuNotifyHandler feiShuNotifyHandler;

    public GlobalExceptionHandler(FeiShuNotifyHandler feiShuNotifyHandler) {
        this.feiShuNotifyHandler = feiShuNotifyHandler;
    }

    /**
     * 参数异常
     *
     * @param e 异常
     * @return response error
     */
    @ExceptionHandler({IllegalArgumentException.class, InvalidFormatException.class})
    @ResponseBody
    public ResultMsg<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResultMsg<>(ResultCode.VALIDATE_ERROR, null);
    }

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
        return new ResultMsg(ResultCode.FAIL.getCode(),ResultCode.FAIL.getMsg(),exception.getMessage());
    }

    /**
     * 异常消息
     */
    @ExceptionHandler(MessageException.class)
    public ResultMsg<?> handleMessageException(MessageException e) {
        return new ResultMsg<>(e.getResultCode().getCode(),
            e.getMessage(), null);
    }

    /**
     * 客户端取消请求异常
     */
    @ExceptionHandler(ClientAbortException.class)
    public ResultMsg<?> handleMessageException(ClientAbortException e) {
        return new ResultMsg<>(ResultCode.CLIENT_ABORT, null);
    }

    /**
     * 未预见的异常
     */
    @ExceptionHandler(Exception.class)
    public ResultMsg<?> handleException(HttpServletRequest request,
        Exception e)
    {
        String uri = request.getRequestURI();
        final String requestId = RequestInfoHolder.getRequestId();

        String content = "请求接口: " + uri
            + "\n请求编号: " + requestId
            + "\n请求时间: " + DateUtil.formatNormal(new Date())
            + "\n错误信息: **" + e.getMessage() + "**"
            ;

        final FeiShuCardNotifyRequest notifyRequest
            = FeiShuCardNotifyRequest.of("接口报错提醒", content,
            "点击查看受影响用户 :玫瑰:️",
            "http://localhost"
                + RequestInfoHolder.getToken());

        final String response = feiShuNotifyHandler.notify(notifyRequest);

        LOGGER.info("报错飞书通知响应: " + response);

        return new ResultMsg<>(ResultCode.SYSTEM_ERROR.getCode(),
            e.getMessage(), null);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(
        GlobalExceptionHandler.class);
}
