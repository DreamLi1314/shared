package club.javafamily.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class ConfigBean {
	
	@Bean
	public IRule iRule() {
		return new RandomRule();
//		return new RetryRule();
	}
}
