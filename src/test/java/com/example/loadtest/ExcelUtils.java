package com.example.loadtest;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {
	  private String filePath;
	    private Workbook workbook;

	    public ExcelUtils(String filePath) throws IOException {
	        this.filePath = filePath;
	        FileInputStream inputStream = new FileInputStream(filePath);
	        this.workbook = new XSSFWorkbook(inputStream);
	    }

	    public Map<String, String> getRowData(String sheetName, int rowIndex) {
	        Sheet sheet = workbook.getSheet(sheetName);
	        Row row = sheet.getRow(rowIndex);
	        Map<String, String> rowData = new HashMap<String, String>();

	        for (int i = 0; i < row.getLastCellNum(); i++) {
	            Cell cell = row.getCell(i);
	            String cellValue = "";

	            if (cell != null) {
	                switch (cell.getCellType()) {
	                    case STRING:
	                        cellValue = cell.getStringCellValue();
	                        break;
	                    case NUMERIC:
	                        if (DateUtil.isCellDateFormatted(cell)) {
	                            cellValue = cell.getDateCellValue().toString(); // Handle date values if needed
	                        } else {
	                            cellValue = String.valueOf((int) cell.getNumericCellValue()); // Convert numeric to integer string
	                        }
	                        break;
	                    case BOOLEAN:
	                        cellValue = String.valueOf(cell.getBooleanCellValue());
	                        break;
	                    case FORMULA:
	                        switch (cell.getCachedFormulaResultType()) {
	                            case NUMERIC:
	                                cellValue = String.valueOf((int) cell.getNumericCellValue());
	                                break;
	                            case STRING:
	                                cellValue = cell.getRichStringCellValue().toString();
	                                break;
	                        }
	                        break;
	                    default:
	                        cellValue = "";
	                }
	            }

	            rowData.put(sheet.getRow(0).getCell(i).getStringCellValue(), cellValue);
	        }

	        return rowData;
	    }
	    public void close() throws IOException {
	        workbook.close();
	    }
	}