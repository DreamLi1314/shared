package com.geoviswtx.conf;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.geoviswtx.common.common.*;
import com.geoviswtx.common.date.DateUtil;
import com.geoviswtx.service.RequestInfoHolder;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 全局异常处理器
 */
@RestControllerAdvice(basePackages = {"com.geoviswtx.controller"})
public class GlobalExceptionHandler {

//    private final QyWechatNotifyHandler notifyHandler;

//    public GlobalExceptionHandler(QyWechatNotifyHandler notifyHandler) {
//        this.notifyHandler = notifyHandler;
//    }

    /**
     * 参数异常
     *
     * @param e 异常
     * @return response error
     */
    @ExceptionHandler({IllegalArgumentException.class, InvalidFormatException.class})
    @ResponseBody
    public ResultMsg<?> illegalArgumentExceptionHandler(IllegalArgumentException e,
                                                        HttpServletRequest request)
    {
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
        LOGGER.error("服务异常:", e);
        String uri = request.getRequestURI();
        final String requestId = RequestInfoHolder.getRequestId();

        String content = "**接口报错提醒**"
           + "\n请求接口: " + uri
           + "\n请求编号: " + requestId
           + "\n请求时间: " + DateUtil.formatNormal(new Date())
           + "\n错误信息: **" + e.getMessage() + "**";

//        final QyWechatMarkdownNotifyRequest notifyRequest
//           = QyWechatMarkdownNotifyRequest.of(content);

//        final String response = notifyHandler.notify(notifyRequest);
//
//        LOGGER.info("报错企微通知响应: " + response);

        return new ResultMsg<>(ResultCode.SYSTEM_ERROR.getCode(),
                e.getMessage(), null);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(
        GlobalExceptionHandler.class);
}
