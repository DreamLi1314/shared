package com.inetsoft.mybatis.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return (configuration) -> {
            // 设置自动映射驼峰命名 department_name(表) ---> departmentName(domain)
//            configuration.setMapUnderscoreToCamelCase(true);
        };
    }
}
