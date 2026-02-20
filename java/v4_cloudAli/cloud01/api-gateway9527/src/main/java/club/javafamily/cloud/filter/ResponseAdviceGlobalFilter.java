package club.javafamily.cloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Jack Li
 * @date 2021/11/28 5:04 下午
 * @description
 */
@Component
@Slf4j
public class ResponseAdviceGlobalFilter implements GlobalFilter, Ordered {

   @Override
   public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
      ServerHttpResponse originalResponse = exchange.getResponse();
      DataBufferFactory bufferFactory = originalResponse.bufferFactory();
      ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
         @Override
         public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            AtomicReference<String> bodyRef = new AtomicReference<>();

            if (body instanceof Flux) {
               Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

               return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                  DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                  DataBuffer join = dataBufferFactory.join(dataBuffers);

                  byte[] content = new byte[join.readableByteCount()];

                  join.read(content);
                  // 释放掉内存
                  DataBufferUtils.release(join);
                  String str = new String(content, Charset.forName("UTF-8"));

                  originalResponse.getHeaders().setContentLength(str.getBytes().length);
                  log.error("gateway catch service exception error:"+ str);

//                  JsonResult result = new JsonResult();
//                  result.setCode(ErrorCode.SYS_EXCEPTION.getCode());
//                  result.setMessage(ErrorCode.SYS_EXCEPTION.getMsg());

                  return bufferFactory.wrap(str.getBytes());
               }));

            }
            // if body is not a flux. never got there.
            return super.writeWith(body);
         }
      };
      // replace response with decorator
      return chain.filter(exchange.mutate().response(decoratedResponse).build());
   }

   @Override
   public int getOrder() {
      return 1;
   }
}
