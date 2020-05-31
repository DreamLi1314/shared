package club.javafamily.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloudClientConfigRest {
	
	@GetMapping("/config")
	public String getConfig() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("*******************applicationName: ")
			.append(application)
			.append(", eurake servers: ")
			.append(eurekaServers)
			.append(", server port: ")
			.append(port);
		
		String config = sb.toString();
		
		System.out.println(config);
		
		return config;
	}

	@Value("${spring.application.name}")
	private String application;
	@Value("${eureka.client.service-url.defaultZone}")
	private String eurekaServers;
	@Value("${server.port}")
	private int port;
}
