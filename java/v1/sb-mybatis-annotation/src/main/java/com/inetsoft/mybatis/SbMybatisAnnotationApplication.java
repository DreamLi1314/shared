package com.inetsoft.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.inetsoft.mybatis.mapper")
@SpringBootApplication
public class SbMybatisAnnotationApplication {
    public static void main(String[] args) {
        SpringApplication.run(SbMybatisAnnotationApplication.class, args);
    }
}
