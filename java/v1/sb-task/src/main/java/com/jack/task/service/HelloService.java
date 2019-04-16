package com.jack.task.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Async
    public void sayHello() throws Exception {
        System.out.println("Handle data....");
        Thread.sleep(3000);
    }
}
