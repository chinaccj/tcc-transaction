package com.touna.tcc.admin.service;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import java.util.Date;

/**
 * Created by chenchaojian on 17/8/13.
 */
public class QuartzTest {
    public static void main(String args[]){
//        Trigger trigger = new Trigger().withIdentity("trigger1", "group1")
//                .startNow()
//                .withSchedule(simpleSchedule()
//                        .withIntervalInSeconds(40)
//                        .repeatForever())
//                .build();
    }
}
