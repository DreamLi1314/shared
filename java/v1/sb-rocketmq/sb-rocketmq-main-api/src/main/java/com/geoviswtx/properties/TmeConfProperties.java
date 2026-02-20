package com.geoviswtx.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("app.tme")
@Configuration
@Data
@ToString
public class TmeConfProperties {

    private String basePath;

    private String id;

    private String secret;
}
