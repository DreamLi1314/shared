package com.inetsoft.test.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class BeanInitedLisner implements ApplicationListener<ContextRefreshedEvent> {

   @Autowired
   public BeanInitedLisner() {

   }

   @Override
   public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
      System.out.println("======onApplicationEvent=========applicationEvent========" + applicationEvent);
      System.out.println("======user.dir=====" + System.getProperty("user.dir"));
   }

   private static final Logger LOG = LoggerFactory.getLogger(BeanInitedLisner.class);
}
