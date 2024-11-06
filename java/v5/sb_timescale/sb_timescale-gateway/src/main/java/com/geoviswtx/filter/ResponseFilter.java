package com.geoviswtx.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geoviswtx.common.common.MessageException;
import com.geoviswtx.common.common.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.*;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author Jack Li
 * @date 2021/12/27 9:45 下午
 * @description
 */
@Slf4j
@Component
public class ResponseFilter implements GlobalFilter, Ordered {

   private final ObjectMapper objectMapper;

   public ResponseFilter(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
   }

   @Override
   public Mono<Void> filter(ServerWebExchange exchange,
                            GatewayFilterChain chain)
   {
      // skip paths
      ServerHttpRequest request = exchange.getRequest();

      if(request.getPath().toString().endsWith("/v2/api-docs")) {
         return chain
                 .filter(exchange
                         .mutate()
                         .build());
      }

      final ServerHttpResponse res = exchange.getResponse();
      ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(res) {
         @Override
         public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            MediaType contentType = getDelegate().getHeaders().getContentType();

            boolean needWrap = MediaType.APPLICATION_JSON.equals(contentType)
                    || MediaType.APPLICATION_JSON_UTF8.equals(contentType);

            if(needWrap && body instanceof Flux) {
               Flux<DataBuffer> fluxBody = (Flux<DataBuffer>) body;

               return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                  DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                  DataBuffer join = dataBufferFactory.join(dataBuffers);

                  byte[] content = new byte[join.readableByteCount()];
                  join.read(content);
                  DataBufferUtils.release(join);// 释放掉内存

                  String bodyStr = new String(content, StandardCharsets.UTF_8);

                  //修改响应体
                  bodyStr = modifyBody(bodyStr);

                  getDelegate().getHeaders().setContentLength(bodyStr.getBytes().length);
                  getDelegate().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                  return bufferFactory().wrap(bodyStr.getBytes());
               }));
            }

            return super.writeWith(body);
         }
      };

      return chain
         .filter(exchange
            .mutate()
            .response(decoratedResponse)
            .build());
   }

   private String modifyBody(String bodyStr) {
      Object result = bodyStr;

      try {
         result = JSONObject.parse(bodyStr);

         // TODO 这是不必要的, 业务修改好移除
         if((result instanceof JSONObject)
            && ((JSONObject) result).containsKey("code")
            && ((JSONObject) result).containsKey("msg")
            && ((JSONObject) result).containsKey("data"))
         {
            return bodyStr;
         }
      }
      catch (Exception ignore) {
         result = bodyStr;
      }

      ResultMsg<Object> resultMsg = new ResultMsg<>(result);

      try {
         return objectMapper.writeValueAsString(resultMsg);
      }
      catch (Exception e) {
         throw new MessageException("网关出错!");
      }
   }

   @Override
   public int getOrder() {
      return -300;
   }
}
