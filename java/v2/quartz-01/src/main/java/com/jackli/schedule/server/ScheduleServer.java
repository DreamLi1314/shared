package com.jackli.schedule.server;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleServer {

   private ScheduleServer() {
      init();
   }

   private void init() {
      try {
         scheduler = StdSchedulerFactory.getDefaultScheduler();
      } catch (SchedulerException e) {
         e.printStackTrace();
      }
   }

   public void start() throws SchedulerException {
      if(!scheduler.isStarted()) {
         scheduler.start();
      }

      LOGGER.warn("Schedule server is running.");
   }

   public void shutdown() throws SchedulerException {
      if(!scheduler.isShutdown()) {
         scheduler.shutdown();
      }

      LOGGER.warn("Schedule server is shutdown.");
   }

   public void addJob(JobDetail job) throws SchedulerException {
      addJob(job, false);
   }

   public void addJob(JobDetail job, boolean replace) throws SchedulerException {
      if(scheduler.isStarted()) {
         scheduler.addJob(job, replace);
      }
      else {
         LOGGER.warn("Schedule server is not running.");
      }
   }

   /**
    * 立即执行一个 Job
    */
   public void executeJob(JobKey jobKey) throws SchedulerException {
      executeJob(jobKey, null);
   }

   public void executeJob(JobKey jobKey, JobDataMap data) throws SchedulerException {
      if(scheduler.isStarted()) {
         scheduler.triggerJob(jobKey, data);
      }
      else {
         LOGGER.warn("Schedule server is not running.");
      }
   }

   public void scheduleJob(Trigger trigger) throws SchedulerException {
      scheduleJob(null, trigger);
   }

   public void scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
      assert trigger != null;

      if(scheduler.isStarted()) {
         if(jobDetail == null) {
            if(LOGGER.isDebugEnabled()) {
               if(scheduler.getJobDetail(trigger.getJobKey()) == null) {
                  LOGGER.debug("The jobKey of trigger setting miss or error. jobKey is " + trigger.getJobKey());
               }
            }

            scheduler.scheduleJob(trigger);
         }
         else {
            scheduler.scheduleJob(jobDetail, trigger);
         }
      }
      else {
         LOGGER.warn("Schedule server is not running.");
      }
   }

   public static ScheduleServer getInstance() {
      if(scheduleServer == null) {
         synchronized (ScheduleServer.class) {
            if(scheduleServer == null) {
               scheduleServer = new ScheduleServer();
            }
         }
      }

      return scheduleServer;
   }

   private volatile static ScheduleServer scheduleServer;
   private Scheduler scheduler;

   private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleServer.class);
}
