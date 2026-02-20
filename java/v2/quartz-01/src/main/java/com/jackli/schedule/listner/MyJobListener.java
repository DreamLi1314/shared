package com.jackli.schedule.listner;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MyJobListener implements JobListener {
   @Override
   public String getName() {
      // Listener Name. 不能为空.
      return this.getClass().getSimpleName();
   }

   @Override
   public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
      System.out.println("Job 将要被执行...." + jobExecutionContext.getJobDetail().getKey());
   }

   @Override
   public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
      System.out.println("Job 被阻止执行时调用...");
   }

   @Override
   public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
      System.out.println("Job 被执行完后调用...");
   }
}
