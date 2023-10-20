package com.example.cimbexcel.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
    public static void readMapNonSwiftAndSaveToDB(String filePath) throws IOException {

        // Check if the repository is empty
        boolean isMapRepositoryEmpty = mapNonSwiftRepository.count() == 0;
        boolean isMasterRepositoryEmpty = masterOverseasBankRepository.count() == 0;

        // If the repository is not empty, set isDelete to true for all existing records
        if (!isMapRepositoryEmpty) {
            List<MapNonSwift> listCurrentMapRepo = mapNonSwiftRepository.findAll();
            for (MapNonSwift currentMapRepo : listCurrentMapRepo) {
                currentMapRepo.setIsDelete(true);
                mapNonSwiftRepository.save(currentMapRepo);
            }
        }

        if (!isMasterRepositoryEmpty) {
            List<MasterOverseasBank> listCurrentMasterRepo = masterOverseasBankRepository.findAll();
            for (MasterOverseasBank currentMasterRepo : listCurrentMasterRepo) {
                currentMasterRepo.setIsDelete(true);
                masterOverseasBankRepository.save(currentMasterRepo);
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
                MasterOverseasBank masterOverseasBank = new MasterOverseasBank();

                // If the repository is empty, set isDelete to false
                if (isMapRepositoryEmpty) {
                    mapNonSwift.setIsDelete(false);
                } else {
                    mapNonSwift.setIsDelete(true);
                }

                if (isMasterRepositoryEmpty) {
                    masterOverseasBank.setIsDelete(false);
                } else {
                    masterOverseasBank.setIsDelete(true);
                }

                masterOverseasBank.setSpeedsendFlag(true);

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getColumnIndex()) {
                        case 1:
                            masterOverseasBank.setSpeedsendCode(cell.getStringCellValue());
                            break;
                        case 2:
                            masterOverseasBank.setBankName(cell.getStringCellValue());
                            break;
                        case 3:
                            mapNonSwift.setCorridorCode(cell.getStringCellValue());
                            break;
                        case 4:
                            mapNonSwift.setCountryCode(cell.getStringCellValue());
                            masterOverseasBank.setCountryCode(cell.getStringCellValue());
                            break;
                        case 6:
                            mapNonSwift.setCurrency(cell.getStringCellValue());
                            masterOverseasBank.setCurrency(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }

                // Check if the data already exists in the repository
                boolean dataMapExists = mapNonSwiftRepository.existsByCountryCodeAndCurrency(
                        mapNonSwift.getCountryCode(),
                        mapNonSwift.getCurrency());

                boolean dataMasterExists = masterOverseasBankRepository
                        .existsBySpeedsendCode(masterOverseasBank.getSpeedsendCode());

                // If data exists and the repository is not empty, set isDelete to false
                if (!isMapRepositoryEmpty && dataMapExists) {
                    mapNonSwift.setIsDelete(false);
                }

                if (!isMasterRepositoryEmpty && dataMasterExists) {
                    masterOverseasBank.setIsDelete(false);
                }

                // Check if CountryCode and Currency have text before saving
                boolean isCountryCodeAndCurrencyHasText = StringUtils.hasText(mapNonSwift.getCountryCode())
                        && StringUtils.hasText(mapNonSwift.getCurrency());
                boolean isSpeedsendCodeHasText = StringUtils.hasText(masterOverseasBank.getSpeedsendCode());

                if (isCountryCodeAndCurrencyHasText) {
                    mapNonSwiftRepository.save(mapNonSwift);
                }

                if (isSpeedsendCodeHasText) {
                    masterOverseasBankRepository.save(masterOverseasBank);
                }
            }
            fis.close();
        } catch (Exception e) {
            log.error("Error while reading excel file", e);
        }
    }

}
