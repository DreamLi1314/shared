package club.javafamily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CloudConfig_3344_APP {
	public static void main(String[] args) {
		SpringApplication.run(CloudConfig_3344_APP.class, args);
	}
}
