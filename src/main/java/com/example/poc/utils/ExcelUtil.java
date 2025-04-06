package com.example.poc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Factory;

public class ExcelUtil {
	private XSSFSheet sheet;
	private int rowNumber;
	private String excelFilePath;

	public ExcelUtil() {

	}

	public HashMap<Integer, Double> getExcelData(String heading) throws IOException {

		System.err.println("Trying");
		excelFilePath = getResourcePath(heading);
		System.err.println("excelFilePath:- " + excelFilePath);
		HashMap<Integer, Double> resultMap = new HashMap<>();
		var fis = new FileInputStream(new File(excelFilePath));
		var sheet = new XSSFWorkbook(fis).getSheet("Disaggregate");
		setSheet(sheet);
		int maxRows = sheet.getLastRowNum();
		boolean flag = heading.contains("$6.85/day");
		System.err.println("maxrows: " + maxRows);
		for (int i = 1; i <= maxRows; i++) {
			setRowNumber(i);
			if (getValue("Geography_Code").equals("WLD")) {
				if (heading.contains("$6.85/day") && getValue("Indicator_Name").toString().contains("at $6.85/day")) {
					System.err.println(getValue("Time_Period") + " : " + getValue("Value"));
					resultMap.put(Integer.parseInt((String) getValue("Time_Period")), (Double) getValue("Value"));
				} else if (heading.contains("$2.15/day")
						&& getValue("Indicator_Name").toString().contains("at $2.15/day")) {
					System.err.println(getValue("Time_Period") + " : " + getValue("Value"));
					resultMap.put(Integer.parseInt((String) getValue("Time_Period")), (Double) getValue("Value"));
				} else {
					System.err.println(getValue("Time_Period") + " : " + getValue("Value"));
					resultMap.put(Integer.parseInt((String) getValue("Time_Period")), (Double) getValue("Value"));
				}
			}

		}
		return resultMap;
	}

	private String getResourcePath(String heading) {
		System.err.println("Heading:- " + heading);
		String result = null;
		if (heading.contains("Percentage of population living in poverty"))
			result = "src/main/resources/Vision_Summary/SI_POV_DDAY_TO.xlsx";
		else if (heading.contains("Average factor by which "))
			result = "src/main/resources/Vision_Summary/SI_POV_PROS.xlsx";
		else
			result = "src/main/resources/Vision_Summary/SI_DST_INEQ.xlsx";
		return result;
	}

	private Object getValue(String colHead) {
		var sheetParam = getSheet();
		if (getRowNumber() > 0) {
			var row = sheetParam.getRow(getRowNumber());
			var colNum = getCellNum(sheetParam, colHead);
			return getCellValue(row.getCell(colNum));
		}
		return null;
	}

	private static int getCellNum(Sheet sheet, String colHead) {
		var row = sheet.getRow(0);
		for (var i = 0; i < row.getLastCellNum(); i++) {
			if (getCellValue(row.getCell(i)).equals(colHead)) {
				return i;
			}
		}
		return 0;
	}

	private static Object getCellValue(Cell cell) {
		var cellType = cell.getCellType();
		switch (cellType) {
		case 0 -> {
			if (DateUtil.isCellDateFormatted(cell)) {
				var date = cell.getDateCellValue();
				return date;
			} else {
				return cell.getNumericCellValue();
			}
		}
		case 1 -> {
			return cell.getStringCellValue();
		}
		case 2 -> {
			switch (cell.getCachedFormulaResultType()) {
			case 4:
				return cell.getBooleanCellValue();
			case 0:
				return cell.getNumericCellValue();
			case 1:
				return cell.getRichStringCellValue();
			}
		}
		case 4 -> {
			return cell.getBooleanCellValue();
		}
		}
		return null;
	}

	private XSSFSheet getSheet(String sheetName) throws FileNotFoundException, IOException {
		var fis = new FileInputStream(new File(excelFilePath));
		var sheet = new XSSFWorkbook(fis).getSheet(sheetName);
		return sheet;
	}

	public int getRowNumber() {
		return this.rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public XSSFSheet getSheet() {
		return this.sheet;
	}

	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}
}
