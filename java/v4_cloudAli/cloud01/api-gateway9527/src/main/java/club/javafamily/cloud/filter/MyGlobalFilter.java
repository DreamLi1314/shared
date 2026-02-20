package club.javafamily.cloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author Jack Li
 * @date 2021/8/11 10:12 上午
 * @description
 */
@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {
   @Override
   public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
      log.info("Come in MyGlobalFilter at {}", new Date());

      final String user = exchange.getRequest().getQueryParams().getFirst("user");

      if(!"admin".equals(user))  {
         exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);

         return exchange.getResponse().setComplete();
      }

      return chain.filter(exchange);
   }

   @Override
   public int getOrder() {
      return 0;
   }
}
