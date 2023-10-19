package com.example.cimbexcel.job.scheduler;

import java.io.IOException;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.example.cimbexcel.model.MapNonSwift;
import com.example.cimbexcel.model.MasterOverseasBank;
import com.example.cimbexcel.util.CountryMappingUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountryMappingScheduler implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.error("Running CountryMappingScheduler");
        try {
            List<MasterOverseasBank> banks = CountryMappingUtil.readBanksAndSaveToDB("src/main/resources/SpeedSendCountryMapping.xlsx");
            List<MapNonSwift> mapNonSwifts = CountryMappingUtil.readCountriesAndSaveToDB("src/main/resources/SpeedSendCountryMapping.xlsx");
            for (MasterOverseasBank bank : banks) {
                log.info(bank.toString());
            }
            for (MapNonSwift mapNonSwift : mapNonSwifts) {
                log.info(mapNonSwift.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
