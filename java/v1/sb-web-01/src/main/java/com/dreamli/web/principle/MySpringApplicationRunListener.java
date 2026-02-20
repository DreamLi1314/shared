package com.dreamli.web.principle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    public MySpringApplicationRunListener(SpringApplication application, String[] args) {
        ;
    }

    @Override
    public void starting() {
        System.out.println("SpringApplicationRunListener====starting=============");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        Object os = environment.getSystemEnvironment().get("os.name");
        System.out.println("SpringApplicationRunListener====environmentPrepared=====" + os);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener====contextPrepared======" + context);
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener====contextLoaded======" + context);
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener====started======" + context);
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener====running======" + context);
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("SpringApplicationRunListener====failed======" + context + "==" + exception);
    }
}
