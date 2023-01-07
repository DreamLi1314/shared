package org.example.service;

import com.mlog.utils.common.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

import static org.example.conf.MLogInterceptor.TOKEN_HEADER;

/**
 * @author Jack Li
 * @date 2022/3/30 9:33 上午
 * @description
 */
public class RequestInfoHolder {

   public static String getTokenOrElseThrow() {
      return Optional.ofNullable(TOKEN.get())
         .orElseThrow(() -> new MessageException(
            "缺少 " + TOKEN_HEADER + " 请求头!"));
   }

   public static String getToken() {
      return TOKEN.get();
   }

   /**
    * 获取 Request Id
    * @return 请求 id
    */
   public static String getRequestId() {
      return REQUEST_ID.get();
   }

   public static void clear() {
      TOKEN.remove();
   }

   public static void set() {
      // 微信 session key
      TOKEN.set(getTokenValue());
   }

   private static String getTokenValue() {
      final HttpServletRequest request = getRequest();

      return request.getHeader(TOKEN_HEADER);
   }

   public static HttpServletRequest getRequest() {
      return ((ServletRequestAttributes) Objects.requireNonNull(
         RequestContextHolder.getRequestAttributes(), "Request 不能为 null"))
         .getRequest();
   }

   private static final ThreadLocal<String> TOKEN = new InheritableThreadLocal<>();

   private static final ThreadLocal<String> REQUEST_ID = InheritableThreadLocal.withInitial(
      () -> SnowFlakeService.getBean().nextIdStr());

   private static final Logger LOGGER = LoggerFactory.getLogger(RequestInfoHolder.class);
}
