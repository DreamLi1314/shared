package org.example.conf;

import com.mlog.utils.net.NetUtil;
import com.mlog.utils.web.WebMvcUtil;
import org.example.constant.LogConstant;
import org.example.service.RequestInfoHolder;
import org.example.service.SnowFlakeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jack Li
 * @date 2022/7/2 下午2:56
 * @description
 */
@Component
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

   private final SnowFlakeService snowFlakeService;

   public LogInterceptor(SnowFlakeService snowFlakeService) {
      this.snowFlakeService = snowFlakeService;
   }

   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      //如果有上层调用就用上层的ID
      String traceId = request.getHeader(LogConstant.REQUEST_ID);

      if (traceId == null) {
         traceId = RequestInfoHolder.getRequestId();
      }

      try {
         MDC.put(LogConstant.REQUEST_ID, traceId);

         MDC.put(LogConstant.TRACE_IP, WebMvcUtil.getIP());

         MDC.put(LogConstant.TRACE_PID, NetUtil.getPid() + "");

         MDC.put(MLogInterceptor.TOKEN_HEADER, RequestInfoHolder.getToken());
      }
      catch (Exception e) {
         e.printStackTrace();
      }

      return true;
   }

   @Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
      MDC.clear();
   }
}
