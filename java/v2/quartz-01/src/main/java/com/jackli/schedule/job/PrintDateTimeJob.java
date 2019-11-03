package com.jackli.schedule.job;

import org.quartz.*;

import java.time.LocalDateTime;

@PersistJobDataAfterExecution // 标记当前 Job 为一个有状态的 Job
public class PrintDateTimeJob implements Job {

   public PrintDateTimeJob() {
      System.out.println("======PrintDateTimeJob=======");
   }

   public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      String dateTime = LocalDateTime.now().toString();

      System.out.println("$$The current dateTime is " + dateTime);

      try {
         Thread.sleep(2000);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      System.out.println("$$PrintDateTimeJob execute success.");
   }

   public static JobDetail buildJob(String key) {
      return JobBuilder.newJob(PrintDateTimeJob.class)
         .withIdentity(key, JOB_GROUP)
         .storeDurably() // 如果添加 Job 时没有指定 trigger 需要指定 durably
         .build();
   }

   public static final String JOB_GROUP = "print";
}
