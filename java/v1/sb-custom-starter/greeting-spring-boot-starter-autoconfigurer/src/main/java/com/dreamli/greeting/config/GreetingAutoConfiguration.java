package com.dreamli.greeting.config;

import com.dreamli.greeting.properties.GreetingProperties;
import com.dreamli.greeting.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "sb.greeting", name = "enable", havingValue = "true")
@EnableConfigurationProperties(GreetingProperties.class)
public class GreetingAutoConfiguration {

    @Autowired
    private GreetingProperties greetingProperties;

    @Bean
    @ConditionalOnMissingBean
    public GreetingService greetingService() {
        GreetingService greetingService = new GreetingService();
        greetingService.setGreetingProperties(greetingProperties);

        return greetingService;
    }
}
