package com.jack.task.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Scheduled(cron = "0 * * * * MON-FRI")
    public void sayHello() {
        System.out.println("Hello.....");
    }

}
