package com.inetsoft.test;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TestApplication3 {
   public static void main(String[] args) throws Exception {
      MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();

      MyMB annoMXBean = new MyMBAnnoImpl();

      beanServer.registerMBean(annoMXBean, new ObjectName("annoMXBean:type=annoMXBean"));

      while(true) {
         TimeUnit.SECONDS.sleep(3);
         System.out.println("getTestAttr"
            + annoMXBean.sayHello(new Random().nextDouble() + ""));
      }
   }
}
