package com.example.automate;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
	private String filePath;
	private Workbook workbook;

	public ExcelUtils(String filePath) throws IOException {
		this.filePath = filePath;
		FileInputStream inputStream = new FileInputStream(filePath);
		this.workbook = new XSSFWorkbook(inputStream);
	}

	public static void writeToExcel(List<TestResult> results, String filePath) {
		Workbook workbook;
		File file = new File(filePath);

		try {
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				workbook = new XSSFWorkbook(fis);
				fis.close();
			} else {
				workbook = new XSSFWorkbook();
			}

			// Remove the main sheet if it exists
			Sheet mainSheet = workbook.getSheet("Test Results");
			if (mainSheet != null) {
				workbook.removeSheetAt(workbook.getSheetIndex(mainSheet));
			}

			// Create a new main sheet
			mainSheet = workbook.createSheet("Test Results");
			populateSheet(mainSheet, results);

			// Create backup sheet with date and time
			String baseBackupName = createBaseBackupSheetName();
			Sheet backupSheet = createUniqueBackupSheet(workbook, baseBackupName);
			populateSheet(backupSheet, results);

			// Write to file
			try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
				workbook.write(outputStream);
			}

			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void populateSheet(Sheet sheet, List<TestResult> results) {
		// Create header row
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Sequence");
		headerRow.createCell(1).setCellValue("Test Name");
		headerRow.createCell(2).setCellValue("Status");
		headerRow.createCell(3).setCellValue("Duration (ms)");
		headerRow.createCell(4).setCellValue("Comments");

		// Write data rows with sequential numbers
		int rowNum = 1;
		for (TestResult result : results) {
			Row row = sheet.createRow(rowNum);

			// Add sequence number
			row.createCell(0).setCellValue(rowNum);

			// Add other data
			row.createCell(1).setCellValue(result.getTestName());
			row.createCell(2).setCellValue(result.getStatus());
			row.createCell(3).setCellValue(result.getDuration());
			row.createCell(4).setCellValue(result.getErrorMessage());

			rowNum++;
		}

		// Auto-size columns
		for (int i = 0; i <= 4; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	private static String createBaseBackupSheetName() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd_HH-mm-ss");
		String dateTime = now.format(formatter);
		return "Backup_" + dateTime;
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
						cellValue = String.valueOf((int) cell.getNumericCellValue()); // Convert numeric to integer
																						// string
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

	private static Sheet createUniqueBackupSheet(Workbook workbook, String baseBackupName) {
		String backupName = baseBackupName;
		int counter = 1;

		while (workbook.getSheet(backupName) != null) {
			backupName = baseBackupName + "_" + counter;
			counter++;

			// Ensure we don't exceed the 31-character limit
			if (backupName.length() > 31) {
				backupName = backupName.substring(0, 31);
			}
		}

		return workbook.createSheet(backupName);
	}
}

class TestResult {
	private String testName;
	private String status;
	private long duration;
	private String errorMessage;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public TestResult(String testName, String status, long duration, String errorMessage) {
		super();
		this.testName = testName;
		this.status = status;
		this.duration = duration;
		this.errorMessage = errorMessage;
	}

	public TestResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TestResult [testName=" + testName + ", status=" + status + ", duration=" + duration + ", errorMessage="
				+ errorMessage + "]";
	}

}