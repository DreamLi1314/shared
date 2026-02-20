package com.geoviswtx;

import com.geoviswtx.common.Tool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * @author Jack Li
 * @date 2021/8/27 12:06 上午
 * @description
 */
@SpringBootApplication
public class GridEngineCoding27610 {
   public static void main(String[] args) {
      // setting default time zone
      TimeZone.setDefault(Tool.DEFAULT_TIME_ZONE);
      SpringApplication.run(GridEngineCoding27610.class, args);
   }
}
