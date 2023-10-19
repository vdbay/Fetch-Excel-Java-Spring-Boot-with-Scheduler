package com.example.cimbexcel.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.cimbexcel.model.MapNonSwift;
import com.example.cimbexcel.model.MasterOverseasBank;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CountryMappingUtil {
    private static MapNonSwift mapNonSwiftRepository;
    private static MasterOverseasBank masterOverseasBankRepository;

    public CountryMappingUtil(MapNonSwift mapNonSwiftRepository, MasterOverseasBank masterOverseasBankRepository) {
        this.mapNonSwiftRepository = mapNonSwiftRepository;
        this.masterOverseasBankRepository = masterOverseasBankRepository;
    }

    @Transactional
    public static List<MapNonSwift> readCountriesAndSaveToDB(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();

            List<MapNonSwift> mapNonSwifts = new ArrayList<>();

            // Menandai apakah ini baris pertama
            boolean isFirstRow = true;

            while (rowIterator.hasNext()) {
                XSSFRow row = (XSSFRow) rowIterator.next();

                // Memeriksa apakah ini baris pertama
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Lewatkan baris pertama
                }

                Iterator<Cell> cellIterator = row.cellIterator();

                MapNonSwift mapNonSwift = new MapNonSwift();

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

                // // Cek apakah data sudah ada dalam tabel
                // if
                // (!mapNonSwiftRepository.existsByCountryCodeAndCountryName(mapNonSwift.getCountryCode(),
                // mapNonSwift.getCountryName())) {
                // // Simpan ke database jika belum ada
                // mapNonSwiftRepository.save(mapNonSwift);
                // mapNonSwifts.add(mapNonSwift);
                // }
            }
            fis.close();

            return mapNonSwifts;
        } catch (Exception e) {
            log.error("Error while reading excel file", e);
            return Collections.emptyList();
        }
    }

    @Transactional
    public static List<MasterOverseasBank> readBanksAndSaveToDB(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();

            List<MasterOverseasBank> masterOverseasBanks = new ArrayList<>();

            // Menandai apakah ini baris pertama
            boolean isFirstRow = true;

            while (rowIterator.hasNext()) {
                XSSFRow row = (XSSFRow) rowIterator.next();

                // Memeriksa apakah ini baris pertama
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Lewatkan baris pertama
                }

                Iterator<Cell> cellIterator = row.cellIterator();

                MasterOverseasBank masterOverseasBank = new MasterOverseasBank();

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

                // // Cek apakah data sudah ada dalam tabel
                // if
                // (!masterOverseasBankRepository.existsBySwiftCodeAndId(masterOverseasBank.getSwiftCode(),
                // masterOverseasBank.getId())) {
                // // Simpan ke database jika belum ada
                // masterOverseasBankRepository.save(masterOverseasBank);
                // masterOverseasBanks.add(masterOverseasBank);
                // }
            }
            fis.close();

            return masterOverseasBanks;
        } catch (Exception e) {
            log.error("Error while reading excel file", e);
            return Collections.emptyList();
        }
    }
}
