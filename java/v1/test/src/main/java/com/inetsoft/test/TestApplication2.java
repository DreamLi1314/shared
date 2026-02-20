package com.inetsoft.test;

import com.sun.management.OperatingSystemMXBean;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TestApplication2 {
   public static void main(String[] args) throws Exception {
      MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
      Scanner scanner = new Scanner(System.in);

      MyMXBean myMXBean = new MyImpl(100);

      beanServer.registerMBean(myMXBean, new ObjectName("myMXBean:type=myMXBean"));

      while(true) {
         TimeUnit.SECONDS.sleep(3);
         System.out.println("getTestAttr" + myMXBean.getTestAttr());
      }

//      scanner.next();
   }
}
