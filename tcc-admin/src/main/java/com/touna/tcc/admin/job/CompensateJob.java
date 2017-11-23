package com.touna.tcc.admin.job;

import com.touna.tcc.admin.service.CompensateService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenchaojian on 17/9/2.
 */

@Component
@Configurable
@EnableScheduling
public class CompensateJob {
    @Autowired
    CompensateService compensateService;

    //每隔15s 跑下job,需要单线程读取
    @Scheduled(fixedRate = 1000 * 15)
    public void run(){
        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date()));

        compensateService.load();
    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat("HH:mm:ss");
    }
}
