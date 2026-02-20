package club.javafamilyrule;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

/**
* @Description: 自定义负载均衡算法
*
* @Warning:自定义 Rule 配置类必须在 ComponentScan 包之外, 否则将为共享, 失去了定制
* 化的意义
* @Author: Jack Li
* @Package: cloud-consumer80 - MySelfRule
* @Date: May 29, 2020 12:21:14 AM
* @Version: 1.0.0
 */
@Configuration
public class MySelfRule {

	@Bean
	public IRule iRule() {
//		return new RandomRule();
		return new RoundRobin5Rule();
	}
	
}
