package com.inetsoft.mybatisxml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.inetsoft.mybatisxml.mapper")
public class SbMybatisXmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbMybatisXmlApplication.class, args);
    }

}
