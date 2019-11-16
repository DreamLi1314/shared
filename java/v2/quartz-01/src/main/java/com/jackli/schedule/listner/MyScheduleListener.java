package com.jackli.schedule.listner;

import org.quartz.*;

public class MyScheduleListener implements SchedulerListener {
   @Override
   public void jobScheduled(Trigger trigger) {
      System.out.println("jobScheduled...部署 JobDetail 时调用...");
   }

   @Override
   public void jobUnscheduled(TriggerKey triggerKey) {
      System.out.println("jobUnscheduled...卸载 JobDetail 时调用..." + triggerKey.getName());
   }

   @Override
   public void triggerFinalized(Trigger trigger) {
      System.out.println("triggerFinalized...当这个 trigger 再也不会被触发时调用, " +
         "除非对应的 JobDetail 被设置成了持久化的, 否则他就会从 Schedule 中移除..."
         + trigger.getKey().getName());
   }

   @Override
   public void triggerPaused(TriggerKey triggerKey) {
      System.out.println("triggerPaused....当一个 Trigger 被暂停时调用..." +
        triggerKey.getName());
   }

   @Override
   public void triggersPaused(String triggerGroup) {
      System.out.println("triggersPaused....当一个 Trigger 组被暂停时调用..." +
         "如果是 Trigger 组, 那么 key 的 name 将为 null.." + triggerGroup);
   }

   @Override
   public void triggerResumed(TriggerKey triggerKey) {
      System.out.println("triggerResumed....Trigger 被恢复执行时触发..." + triggerKey.getName());
   }

   @Override
   public void triggersResumed(String triggerGroup) {
      System.out.println("triggersResumed... Trigger 组被恢复执行时触发..." + triggerGroup);
   }

   @Override
   public void jobAdded(JobDetail jobDetail) {
      System.out.println("jobAdded... Job 被添加到 Schedule 时执行..." + jobDetail.getKey());
   }

   @Override
   public void jobDeleted(JobKey jobKey) {
      System.out.println("jobDeleted... Job 从 Schedule 移除时执行..." + jobKey);
   }

   @Override
   public void jobPaused(JobKey jobKey) {
      System.out.println("jobPaused....job 被暂停执行时触发..." + jobKey);
   }

   @Override
   public void jobsPaused(String jobGroup) {
      System.out.println("jobsPaused....job 组被暂停执行时触发..." + jobGroup);
   }

   @Override
   public void jobResumed(JobKey jobKey) {
      System.out.println("jobResumed....job 被恢复执行时触发..." + jobKey);
   }

   @Override
   public void jobsResumed(String jobGroup) {
      System.out.println("jobsResumed....job 组被恢复执行时触发..." + jobGroup);
   }

   @Override
   public void schedulerError(String msg, SchedulerException cause) {
      System.out.println("schedulerError...Schedule 发生错误时执行...msg: " + msg + ", cause: " + cause);
   }

   @Override
   public void schedulerInStandbyMode() {
      System.out.println("schedulerInStandbyMode...当 Schedule 处于 StandBy(等待或者备用) 模式时调用...");
   }

   @Override
   public void schedulerStarted() {
      System.out.println("schedulerStarted...Schedule 已经启动时调用...");
   }

   @Override
   public void schedulerStarting() {
      System.out.println("schedulerStarting...Schedule 正在启动时调用...");
   }

   @Override
   public void schedulerShutdown() {
      System.out.println("schedulerShutdown....Schedule 停止时调用...");
   }

   @Override
   public void schedulerShuttingdown() {
      System.out.println("schedulerShuttingdown...Schedule 正在关闭时调用...");
   }

   @Override
   public void schedulingDataCleared() {
      System.out.println("schedulingDataCleared...当 Schedule 中的数据被清空时调用该方法...");
   }
}
