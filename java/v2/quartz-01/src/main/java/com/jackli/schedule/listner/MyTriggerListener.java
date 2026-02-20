package com.jackli.schedule.listner;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import java.util.Random;

public class MyTriggerListener implements TriggerListener {
   @Override
   public String getName() {
      // Listener Name. 不能为空.
      return this.getClass().getSimpleName();
   }

   @Override
   public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
      // 与监听器相关联的 Trigger 被触发, Job 的 execute 方法将要执行时调用
      System.out.println("triggerFired....");
   }

   @Override
   public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
      // 阻止 Job 执行. 当 Job 将要执行时如果该方法返回 true, 则 Job 将不会执行本次触发.
      boolean result = new Random().nextInt(10) > 5;
      System.out.println("vetoJobExecution....." + result);

      return result;
   }

   @Override
   public void triggerMisfired(Trigger trigger) {
      // 在 Trigger 错过触发时执行.
      System.out.println("triggerMisfired....");
   }

   @Override
   public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
      /**
       * Trigger 被触发, 并且完成了 Job 的执行时触发.
       * 该方法在 JobListener 的 jobWasExecuted 方法执行后执行.
       * 如果 Job 被 vetoJobExecution 阻止, 则该方法不会执行.
       */
      System.out.println("triggerComplete...");
   }
}
