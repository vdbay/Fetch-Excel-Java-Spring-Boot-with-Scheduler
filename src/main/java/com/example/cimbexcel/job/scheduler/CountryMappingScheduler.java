package com.example.cimbexcel.job.scheduler;

import java.io.IOException;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.example.cimbexcel.model.CountryMapping;
import com.example.cimbexcel.util.CountryMappingUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountryMappingScheduler implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.error("Running CountryMappingScheduler");
        try {
            List<CountryMapping> countryMappings = CountryMappingUtil.readCountryMappingsAndSaveToDB("src/main/resources/SpeedSendCountryMapping.xlsx");
            for (CountryMapping countryMapping : countryMappings) {
                log.info(countryMapping.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
