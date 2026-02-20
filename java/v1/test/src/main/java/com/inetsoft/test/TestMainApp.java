package com.inetsoft.test;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TestMainApp {
   public static void main(String[] args) throws InterruptedException {
      Runtime runtime = Runtime.getRuntime();
      OperatingSystemMXBean osMXBean
         = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
      MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
      ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

      for(int i = 0; i < 50; i++) {
         new Thread(() -> {
            List<Object> data = new ArrayList<>();
            String str = "";

            for (int j = 0; j < 1000; j++) {
               data.add(new String[] {"data" + j});
               data.add(new Random().nextDouble());
               str += j + "";
            }

         }).start();

//         System.out.println("threadMXBean.getThreadCount: "
//            + threadMXBean.getThreadCount());
      }

      TimeUnit.MILLISECONDS.sleep(2000);

      // heap
      System.out.println("runtime.totalMemory: " + runtime.totalMemory());
      System.out.println("runtime.freeMemory: " + runtime.freeMemory());
      System.out.println("memoryMXBean.getHeapMemoryUsage: " + memoryMXBean.getHeapMemoryUsage().getUsed());

      // memory
      System.out.println("osMXBean.getTotalPhysicalMemorySize: "
         + osMXBean.getTotalPhysicalMemorySize());
      System.out.println("osMXBean.getFreePhysicalMemorySize: "
         + osMXBean.getFreePhysicalMemorySize());

      // thread
      System.out.println("threadMXBean.getThreadCount: "
         + threadMXBean.getThreadCount());
      System.out.println("threadMXBean.getDaemonThreadCount: "
         + threadMXBean.getDaemonThreadCount());

      // cpu
      System.out.println("runtime.availableProcessors: " + runtime.availableProcessors());

      for (int i = 0; i < 5; i++) {
         TimeUnit.MILLISECONDS.sleep(1000);
         System.out.println("osMXBean.getSystemCpuLoad: " + (osMXBean.getSystemCpuLoad() * 100));
      }

      Scanner scanner = new Scanner(System.in);
      scanner.next();
   }
}
