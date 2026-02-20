package com.dreamli.testcustomstarter.controller;

import com.dreamli.greeting.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @GetMapping("/greeting/{name}")
    public String greeting(@PathVariable String name) {
        return greetingService.greeting(name);
    }


}
