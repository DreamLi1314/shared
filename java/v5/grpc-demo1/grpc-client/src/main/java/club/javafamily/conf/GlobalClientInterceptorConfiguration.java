package club.javafamily.conf;

import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author Jack Li
 * @date 2022/1/18 11:26 上午
 * @description
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
public class GlobalClientInterceptorConfiguration {

   @GrpcGlobalClientInterceptor
   LogGrpcInterceptor logServerInterceptor() {
      return new LogGrpcInterceptor();
   }

}
