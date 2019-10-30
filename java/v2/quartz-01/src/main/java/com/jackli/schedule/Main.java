package com.jackli.schedule;

import com.jackli.schedule.job.PrintDateTimeJob;
import com.jackli.schedule.server.ScheduleServer;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Main {
   public static void main(String[] args) throws Exception {
      ScheduleServer scheduleServer = ScheduleServer.getInstance();
      scheduleServer.start();

      JobDetail jobDetail = PrintDateTimeJob.buildJob("jack-dateTime");

      scheduleServer.addJob(jobDetail);

      Thread.sleep(3000);

//      SimpleTrigger trigger = newTrigger()
//         .withIdentity("jack-dateTime", PrintDateTimeJob.JOB_GROUP)
//         .forJob(jobDetail.getKey())
//         .startNow()
//         .withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
//         .build();
//
//      scheduleServer.scheduleJob(trigger); // 仅用 trigger 执行 job, trigger 必须指定 jobKey.

      scheduleServer.executeJob(jobDetail.getKey()); // 立即执行

      Thread.sleep(20000);

      scheduleServer.shutdown();
   }
}
