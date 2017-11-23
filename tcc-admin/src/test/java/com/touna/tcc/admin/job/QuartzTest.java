package com.touna.tcc.admin.job;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/**
 * Created by chenchaojian on 17/9/2.
 */


public class QuartzTest {
    public static void main(String args[])throws Exception{
// Grab the Scheduler instance from the Factory
//        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//
//        // define the job and tie it to our MyJob class
//        JobDetail job = newJob(CompensateJob.class)
//                .withIdentity("job1", "group1")
//                .build();
//
//        // Trigger the job to run now, and then repeat every 40 seconds
//        Trigger trigger = newTrigger()
//                .withIdentity("trigger1", "group1")
//                .startNow()
//                .withSchedule(simpleSchedule()
//                        .withIntervalInSeconds(15)
//                        .repeatForever())
//                .build();
//
//
//        // Tell quartz to schedule the job using our trigger
//        scheduler.scheduleJob(job, trigger);
//
//        // and start it off
//        scheduler.start();
    }
}
