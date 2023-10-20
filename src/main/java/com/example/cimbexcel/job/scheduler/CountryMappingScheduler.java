package com.example.cimbexcel.job.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.example.cimbexcel.util.CountryMappingUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountryMappingScheduler implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.error("Running CountryMappingScheduler");
        try {
            CountryMappingUtil.readMapNonSwiftAndSaveToDB("src/main/resources/SpeedSendCountryMapping.xlsx");
        } catch (Exception e) {
            log.error("Error running CountryMappingScheduler: {}", e.getMessage());
        }

    }
}
