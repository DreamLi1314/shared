package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // 启用 Websocket message
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 启用一个基于内存的消息代理, 在以前缀为 "/topic" 的目标上将消息返回客户端
		registry.enableSimpleBroker("/topic");
		// 为所有映射地址(MessageMapping)添加前缀
		registry.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 注册“/ gs-guide-websocket”端点，启用SockJS后备选项，以便在WebSocket不可用时
		// 可以使用备用传输。 SockJS客户端将尝试连接到“/swt”并使用可用的最佳传输
		//（websocket，xhr-streaming，xhr-polling等）。
		registry.addEndpoint("/swt").withSockJS();
	}

	
}
