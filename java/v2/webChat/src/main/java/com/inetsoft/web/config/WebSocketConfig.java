package com.inetsoft.web.config;

import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;

public class WebSocketConfig implements ServerApplicationConfig {

	/**
	 *  编程式 WebSocket
	 */
	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
		return null;
	}

	/**
	 * 注解式 WebSocket
	 */
	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		System.out.println("==getAnnotatedEndpointClasses====scan size=" + scanned.size());
		return scanned;
	}

}
