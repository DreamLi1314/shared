package org.example.yiji.dolphin.config;

import org.example.alibaba.fastjson.JSONObject;
import org.example.yiji.dolphin.model.primary.SysLog;
import org.example.yiji.dolphin.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author wanglin
 * @date 2020/5/12 17:23
 */
@Aspect
@Component
@Slf4j
public class SystemAspect {

  @Autowired
  private SysLogService sysLogService;

  @Pointcut("execution(* org.example.yiji.dolphin.controller..*.*(..)) && !execution(* org.example.yiji.dolphin.controller.SysLogController.*(..))")
  public void controllerLogAspect(){}

  @AfterReturning("controllerLogAspect()")
  public void log2sql() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if(attributes!=null){
      HttpServletRequest request = attributes.getRequest();
      SysLog sysLog = new SysLog();
      Object obj = request.getSession().getAttribute("user");

      if(!(obj instanceof JSONObject)) {
        return;
      }

      JSONObject user = (JSONObject)obj;
      sysLog.setUsername(user==null?"":user.getString("username"));
      sysLog.setTime(new Date());
      sysLog.setUrl(request.getRequestURI());
      sysLog.setType(request.getMethod());
      sysLog.setIp(getClientIP(request));
      sysLogService.save(sysLog);
    }
  }

  public static String getClientIP(HttpServletRequest request){
    if(request.getHeader("x-forwarded-for")==null){
      return request.getRemoteAddr();
    }else {
      if(request.getHeader("x-forwarded-for").contains(",")){
        return request.getHeader("x-forwarded-for").split(",")[0];
      }else {
        return request.getHeader("x-forwarded-for");
      }
    }
  }

}
