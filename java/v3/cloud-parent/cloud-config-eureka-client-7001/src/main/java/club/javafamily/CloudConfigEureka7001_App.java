package club.javafamily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CloudConfigEureka7001_App {

	public static void main(String[] args) {
		SpringApplication.run(CloudConfigEureka7001_App.class, args);
	}
}
