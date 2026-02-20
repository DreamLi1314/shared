package com.sunny.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration {
   @Bean
   public ServerEndpointExporter serverEndpointExporter() {
      return new ServerEndpointExporter();
   }

}
