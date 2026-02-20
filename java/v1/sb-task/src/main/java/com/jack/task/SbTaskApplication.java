package com.jack.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 开启基于注解的定时任务
@EnableAsync // 开启异步任务
@SpringBootApplication
public class SbTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbTaskApplication.class, args);
    }

}
