package club.javafamily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient // 开启服务发现
@EnableCircuitBreaker // 对 Hystrix 熔断器进行支持
public class DeptProvider8001_Hystrix_APP {
	
	public static void main(String[] args) {
		SpringApplication.run(DeptProvider8001_Hystrix_APP.class, args);
	}
}
