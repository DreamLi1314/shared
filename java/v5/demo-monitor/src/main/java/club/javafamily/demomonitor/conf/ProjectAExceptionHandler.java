package club.javafamily.demomonitor.conf;

import club.javafamily.nf.request.FeiShuTextNotifyRequest;
import club.javafamily.nf.service.FeiShuNotifyHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Jack Li
 * @date 2022/8/8 上午1:54
 * @description
 */
@RestControllerAdvice(basePackages = {"club.javafamily.demomonitor.controller"})
public class ProjectAExceptionHandler {

   private final FeiShuNotifyHandler feiShuNotifyHandler;

   public ProjectAExceptionHandler(FeiShuNotifyHandler feiShuNotifyHandler) {
      this.feiShuNotifyHandler = feiShuNotifyHandler;
   }

   /**
    * 未预见的异常
    */
   @ExceptionHandler(Exception.class)
   public Object handleException(Exception e) {

      feiShuNotifyHandler.notify(FeiShuTextNotifyRequest.of(
         "接口报错报警: " + e.getMessage()));

      return e;
   }

}
