package com.geoviswtx.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("app.ksh")
@Configuration
@Data
@ToString
public class AppConfProperties {

    private String basePath;

    private String id;

    private String secret;
}
