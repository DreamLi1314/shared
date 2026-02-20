package club.javafamily.conf;

import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jack Li
 * @date 2022/1/18 11:26 上午
 * @description
 */
@Configuration(proxyBeanMethods = false)
public class GlobalInterceptorConfiguration {

   @GrpcGlobalServerInterceptor
   LogGrpcInterceptor logServerInterceptor() {
      return new LogGrpcInterceptor();
   }

}
