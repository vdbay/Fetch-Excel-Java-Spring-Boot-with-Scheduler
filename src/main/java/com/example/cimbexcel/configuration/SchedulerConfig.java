package com.example.cimbexcel.configuration;

import java.util.TimeZone;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.cimbexcel.job.scheduler.CountryMappingScheduler;

@Configuration
public class SchedulerConfig {
    
    @Bean
    public JobDetail myScheduledJob1Detail() {
        return JobBuilder.newJob(CountryMappingScheduler.class)
                .withIdentity("fetchCountryMappingJob")
                .storeDurably(true)
                .build();
    }

    @Bean
    public Trigger myScheduledTrigger1() {
        return TriggerBuilder.newTrigger()
                .forJob(myScheduledJob1Detail())
                .withIdentity("fetchCountryMappingTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 00 17 * * ?")
                .inTimeZone(TimeZone.getTimeZone("Asia/Jakarta"))) 
                .build();
    }
}

