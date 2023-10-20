package com.example.cimbexcel.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    public static List<MapNonSwift> readMapNonSwiftAndSaveToDB(String filePath) throws IOException {
        List<MapNonSwift> mapNonSwifts = new ArrayList<>();
    
        // Check if the repository is empty
        boolean isRepositoryEmpty = mapNonSwiftRepository.count() == 0;
    
        // If the repository is not empty, set isDelete to true for all existing records
        if (!isRepositoryEmpty) {
            List<MapNonSwift> listCurrentRepo = mapNonSwiftRepository.findAll();
            for (MapNonSwift currentRepo : listCurrentRepo) {
                currentRepo.setIsDelete(true);
                mapNonSwiftRepository.save(currentRepo);
            }
        }
    
        FileInputStream fis = new FileInputStream(new File(filePath));
        try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();
    
            // Skip the first row
            boolean isFirstRow = true;
    
            while (rowIterator.hasNext()) {
                XSSFRow row = (XSSFRow) rowIterator.next();
    
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip the first row
                }
    
                Iterator<Cell> cellIterator = row.cellIterator();
    
                MapNonSwift mapNonSwift = new MapNonSwift();
    
                // If the repository is empty, set isDelete to false
                if (isRepositoryEmpty) {
                    mapNonSwift.setIsDelete(false);
                }
    
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
    
                    switch (cell.getColumnIndex()) {
                        case 4:
                            mapNonSwift.setCountryCode(cell.getStringCellValue());
                            break;
                        case 6:
                            mapNonSwift.setCurrency(cell.getStringCellValue());
                            break;
                        case 3:
                            mapNonSwift.setCorridorCode(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
    
                // Check if the data already exists in the repository
                boolean dataExists = mapNonSwiftRepository.existsByCountryCodeAndCurrency(mapNonSwift.getCountryCode(), mapNonSwift.getCurrency());
    
                // If data exists and the repository is not empty, set isDelete to false
                if (!isRepositoryEmpty && dataExists) {
                    mapNonSwift.setIsDelete(false);
                }
    
                // Check if CountryCode and Currency have text before saving
                boolean isCountryCodeAndCurrencyHasText = StringUtils.hasText(mapNonSwift.getCountryCode()) && StringUtils.hasText(mapNonSwift.getCurrency());
    
                if (isCountryCodeAndCurrencyHasText) {
                    mapNonSwiftRepository.save(mapNonSwift);
                    mapNonSwifts.add(mapNonSwift);
                }
            }
            fis.close();
        } catch (Exception e) {
            log.error("Error while reading excel file", e);
            return Collections.emptyList();
        }
    
        return mapNonSwifts;
    }

    @Transactional
    public static List<MasterOverseasBank> readMasterOverseasBankAndSaveToDB(String filePath) throws IOException {
        List<MasterOverseasBank> masterOverseasBanks = new ArrayList<>();
    
        // Check if the repository is empty
        boolean isRepositoryEmpty = masterOverseasBankRepository.count() == 0;
    
        // If the repository is not empty, set isDelete to true for all existing records
        if (!isRepositoryEmpty) {
            List<MasterOverseasBank> listCurrentRepo = masterOverseasBankRepository.findAll();
            for (MasterOverseasBank currentRepo : listCurrentRepo) {
                currentRepo.setIsDelete(true);
                masterOverseasBankRepository.save(currentRepo);
            }
        }
    
        FileInputStream fis = new FileInputStream(new File(filePath));
        try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();
    
            // Skip the first row
            boolean isFirstRow = true;
    
            while (rowIterator.hasNext()) {
                XSSFRow row = (XSSFRow) rowIterator.next();
    
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip the first row
                }
    
                Iterator<Cell> cellIterator = row.cellIterator();
    
                MasterOverseasBank masterOverseasBank = new MasterOverseasBank();
    
                // If the repository is empty, set isDelete to false
                if (isRepositoryEmpty) {
                    masterOverseasBank.setIsDelete(false);
                } else {
                    masterOverseasBank.setIsDelete(true);
                }
    
                masterOverseasBank.setSpeedsendFlag(true);
    
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
    
                    switch (cell.getColumnIndex()) {
                        case 4:
                            masterOverseasBank.setCountryCode(cell.getStringCellValue());
                            break;
                        case 6:
                            masterOverseasBank.setCurrency(cell.getStringCellValue());
                            break;
                        case 1:
                            masterOverseasBank.setSpeedsendCode(cell.getStringCellValue());
                            break;
                        case 2:
                            masterOverseasBank.setBankName(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
    
                // Check if the data already exists in the repository
                boolean dataExists = masterOverseasBankRepository.existsBySpeedsendCode(masterOverseasBank.getSpeedsendCode());
    
                // If data exists and the repository is not empty, set isDelete to false
                if (!isRepositoryEmpty && dataExists) {
                    masterOverseasBank.setIsDelete(false);
                }
    
                // Check if SpeedsendCode has text before saving
                boolean isSpeedsendCodeHasText = StringUtils.hasText(masterOverseasBank.getSpeedsendCode());
    
                if (isSpeedsendCodeHasText) {
                    masterOverseasBankRepository.save(masterOverseasBank);
                    masterOverseasBanks.add(masterOverseasBank);
                }
            }
            fis.close();
        } catch (Exception e) {
            log.error("Error while reading excel file", e);
            return Collections.emptyList();
        }
    
        return masterOverseasBanks;
    }
    

}
