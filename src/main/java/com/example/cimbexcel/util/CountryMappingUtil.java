package com.example.cimbexcel.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.cimbexcel.model.MapNonSwift;
import com.example.cimbexcel.model.MasterOverseasBank;
import com.example.cimbexcel.repository.MapNonSwiftRepository;
import com.example.cimbexcel.repository.MasterOverseasBankRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CountryMappingUtil {
    private static MapNonSwiftRepository mapNonSwiftRepository;
    private static MasterOverseasBankRepository masterOverseasBankRepository;

    public CountryMappingUtil(MapNonSwiftRepository mapNonSwiftRepository,
            MasterOverseasBankRepository masterOverseasBankRepository) {
        this.mapNonSwiftRepository = mapNonSwiftRepository;
        this.masterOverseasBankRepository = masterOverseasBankRepository;
    }

    @Transactional
    public static void readExcelAndSave(String filepath) throws Exception {
        boolean isMapNonSwiftRepositoryEmpty = mapNonSwiftRepository.count() == 0;
        boolean isMasterOverseasBankRepositoryEmpty = masterOverseasBankRepository.count() == 0;
        if (isMapNonSwiftRepositoryEmpty) {
            log.info("isMapNonSwiftRepositoryEmpty: {}", isMapNonSwiftRepositoryEmpty);
            List<MapNonSwift> currentExcelList = getMapNonSwiftFromExcel(filepath);
            for (MapNonSwift currentExcel : currentExcelList) {
                currentExcel.setIsDelete(false);
                currentExcel.setCreatedBy("SCHEDULER");
                currentExcel.setCreatedDate(new Date());
                currentExcel.setModifiedBy("SCHEDULER");
                currentExcel.setModifiedDate(new Date());
                mapNonSwiftRepository.save(currentExcel);
            }
        } else {
            log.info("isMapNonSwiftRepositoryEmpty: {}", isMapNonSwiftRepositoryEmpty);
            List<MapNonSwift> currentExcelList = getMapNonSwiftFromExcel(filepath);
            List<MapNonSwift> currentRepoList = mapNonSwiftRepository.findAll();

            // existing
            Iterator<MapNonSwift> excelIterator = currentExcelList.iterator();
            while (excelIterator.hasNext()) {
                MapNonSwift currentExcel = excelIterator.next();

                Iterator<MapNonSwift> repoIterator = currentRepoList.iterator();
                while (repoIterator.hasNext()) {
                    MapNonSwift currentRepo = repoIterator.next();

                    if (currentExcel.getCountryCode().equals(currentRepo.getCountryCode())
                            && currentExcel.getCurrency().equals(currentRepo.getCurrency())) {
                        log.info("currentExcel.getCountryCode(): {}", currentExcel.getCountryCode());
                        if (Boolean.TRUE.equals(currentRepo.getIsDelete())) {
                            log.info("currentRepo.getIsDelete(): {}", currentRepo.getIsDelete());
                            excelIterator.remove();
                        } else {
                            log.info("currentRepo.getIsDelete(): {}", currentRepo.getIsDelete());
                            excelIterator.remove();
                            repoIterator.remove();
                        }
                    }
                }
            }

            // new
            if (!currentExcelList.isEmpty()) {
                log.info("currentExcelList.isEmpty(): {}", currentExcelList.isEmpty());
                for (MapNonSwift currentExcel : currentExcelList) {
                    currentExcel.setIsDelete(false);
                    currentExcel.setCreatedBy("SCHEDULER");
                    currentExcel.setCreatedDate(new Date());
                    currentExcel.setModifiedBy("SCHEDULER");
                    currentExcel.setModifiedDate(new Date());
                    mapNonSwiftRepository.save(currentExcel);
                }
            }

            // update
            if (!currentRepoList.isEmpty()) {
                log.info("currentRepoList.isEmpty(): {}", currentRepoList.isEmpty());
                for (MapNonSwift currentRepo : currentRepoList) {
                    currentRepo.setIsDelete(!currentRepo.getIsDelete());
                    currentRepo.setModifiedBy("SCHEDULER");
                    currentRepo.setModifiedDate(new Date());
                    mapNonSwiftRepository.save(currentRepo);
                }
            }
        }

        if (isMasterOverseasBankRepositoryEmpty) {
            log.info("isMasterOverseasBankRepositoryEmpty: {}", isMasterOverseasBankRepositoryEmpty);
            List<MasterOverseasBank> currentExcelList = getMasterOverseasBankFromExcel(filepath);
            for (MasterOverseasBank currentExcel : currentExcelList) {
                currentExcel.setIsDelete(false);
                currentExcel.setCreatedBy("SCHEDULER");
                currentExcel.setCreatedDate(new Date());
                currentExcel.setModifiedBy("SCHEDULER");
                currentExcel.setModifiedDate(new Date());
                currentExcel.setSpeedsendFlag(true);
                masterOverseasBankRepository.save(currentExcel);
            }
        } else {
            log.info("isMasterOverseasBankRepositoryEmpty: {}", isMasterOverseasBankRepositoryEmpty);
            List<MasterOverseasBank> currentExcelList = getMasterOverseasBankFromExcel(filepath);
            List<MasterOverseasBank> currentRepoList = masterOverseasBankRepository.findAll();

            // existing
            Iterator<MasterOverseasBank> excelIterator = currentExcelList.iterator();
            while (excelIterator.hasNext()) {
                MasterOverseasBank currentExcel = excelIterator.next();

                Iterator<MasterOverseasBank> repoIterator = currentRepoList.iterator();
                while (repoIterator.hasNext()) {
                    MasterOverseasBank currentRepo = repoIterator.next();

                    if (currentExcel.getSpeedsendCode().equals(currentRepo.getSpeedsendCode())) {
                        log.info("currentExcel.getSpeedsendCode(): {}", currentExcel.getSpeedsendCode());
                        if (Boolean.TRUE.equals(currentRepo.getIsDelete())) {
                            log.info("currentRepo.getIsDelete(): {}", currentRepo.getIsDelete());
                            excelIterator.remove();
                        } else {
                            log.info("currentRepo.getIsDelete(): {}", currentRepo.getIsDelete());
                            excelIterator.remove();
                            repoIterator.remove();
                        }
                    }
                }
            }

            // new
            if (!currentExcelList.isEmpty()) {
                log.info("currentExcelList.isEmpty(): {}", currentExcelList.isEmpty());
                for (MasterOverseasBank currentExcel : currentExcelList) {
                    currentExcel.setSpeedsendFlag(true);
                    currentExcel.setIsDelete(false);
                    currentExcel.setCreatedBy("SCHEDULER");
                    currentExcel.setCreatedDate(new Date());
                    currentExcel.setModifiedBy("SCHEDULER");
                    currentExcel.setModifiedDate(new Date());
                    masterOverseasBankRepository.save(currentExcel);
                }
            }

            // update
            if (!currentRepoList.isEmpty()) {
                log.info("currentRepoList.isEmpty(): {}", currentRepoList.isEmpty());
                for (MasterOverseasBank currentRepo : currentRepoList) {
                    currentRepo.setIsDelete(!currentRepo.getIsDelete());
                    currentRepo.setModifiedBy("SCHEDULER");
                    currentRepo.setModifiedDate(new Date());
                    masterOverseasBankRepository.save(currentRepo);
                }
            }
        }
    }

    public static List<MapNonSwift> getMapNonSwiftFromExcel(String filepath) throws Exception {
        List<MapNonSwift> allList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(filepath));
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();
            boolean isFirstRow = true;
            while (rowIterator.hasNext()) {
                XSSFRow row = (XSSFRow) rowIterator.next();
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                MapNonSwift mapNonSwift = new MapNonSwift();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 3:
                            mapNonSwift.setCorridorCode(cell.getStringCellValue());
                            break;
                        case 4:
                            mapNonSwift.setCountryCode(cell.getStringCellValue());
                            break;
                        case 6:
                            mapNonSwift.setCurrency(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                boolean isCountryCodeAndCurrencyHasText = StringUtils.hasText(mapNonSwift.getCountryCode())
                        && StringUtils.hasText(mapNonSwift.getCurrency());
                // empty string
                if (isCountryCodeAndCurrencyHasText) {
                    allList.add(mapNonSwift);
                }
            }
            workbook.close();
            return allList;
        } catch (Exception e) {
            log.error(e.toString());
            return Collections.emptyList();
        }
    }

    public static List<MasterOverseasBank> getMasterOverseasBankFromExcel(String filepath) throws Exception {
        List<MasterOverseasBank> allList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(filepath));
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();
            boolean isFirstRow = true;
            while (rowIterator.hasNext()) {
                XSSFRow row = (XSSFRow) rowIterator.next();
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                MasterOverseasBank masterOverseasBank = new MasterOverseasBank();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 1:
                            masterOverseasBank.setSpeedsendCode(cell.getStringCellValue());
                            break;
                        case 2:
                            masterOverseasBank.setBankName(cell.getStringCellValue());
                            break;
                        case 4:
                            masterOverseasBank.setCountryCode(cell.getStringCellValue());
                            break;
                        case 6:
                            masterOverseasBank.setCurrency(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                boolean isSpeedsendCodeHasText = StringUtils.hasText(masterOverseasBank.getSpeedsendCode());
                // empty string
                if (isSpeedsendCodeHasText) {
                    allList.add(masterOverseasBank);
                }
            }
            workbook.close();
            return allList;
        } catch (Exception e) {
            log.error(e.toString());
            return Collections.emptyList();
        }
    }

}
