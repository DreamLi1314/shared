package com.geoviswtx;

import com.geoviswtx.common.Tool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.TimeZone;

/**
 * @author Jack Li
 * @date 2023/3/22 下午3:01
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application {
   public static void main(String[] args) {
      // setting default time zone
      TimeZone.setDefault(Tool.DEFAULT_TIME_ZONE);
      SpringApplication.run(Application.class, args);
   }
}
