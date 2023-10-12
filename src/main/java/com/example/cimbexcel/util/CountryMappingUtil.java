package com.example.cimbexcel.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.cimbexcel.model.CountryMapping;
import com.example.cimbexcel.repository.CountryMappingRepository;

@Component
public class CountryMappingUtil {
    private static CountryMappingRepository countryMappingRepository;

    public CountryMappingUtil(CountryMappingRepository countryMappingRepository) {
        this.countryMappingRepository = countryMappingRepository;
    }

    @Transactional
    public static List<CountryMapping> readCountryMappingsAndSaveToDB(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = spreadsheet.iterator();
    
        List<CountryMapping> countryMappings = new ArrayList<>();
    
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
    
            CountryMapping countryMapping = new CountryMapping();
    
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
    
                switch (cell.getColumnIndex()) {
                    case 0:
                        countryMapping.setId(cell.getStringCellValue());
                        break;
    
                    case 1:
                        countryMapping.setBankName(cell.getStringCellValue());
                        break;
    
                    case 2:
                        countryMapping.setCorridorCode(cell.getStringCellValue());
                        break;
    
                    case 3:
                        countryMapping.setCountryName(cell.getStringCellValue());
                        break;
    
                    case 4:
                        countryMapping.setCurrency(cell.getStringCellValue());
                        break;
                }
            }
    
            // Simpan ke database menggunakan repositori
            countryMappingRepository.save(countryMapping);
            countryMappings.add(countryMapping);
        }
        fis.close();
    
        return countryMappings;
    }
    
}
