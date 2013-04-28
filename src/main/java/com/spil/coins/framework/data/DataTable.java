package com.spil.coins.framework.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.spil.coins.framework.helper.IOHelper;

public class DataTable {
	private String dataTablePath;
	private final String GENERAL_SHEET = "general";

	public DataTable(String dataTablePath) {
		this.dataTablePath = dataTablePath;
	}

	/**
	 * Gets data from the 'General' sheet where each item in the list could be
	 * used as single hash table. First row from excel file represents keys to
	 * access test data.
	 * 
	 * @return list of HashMaps that represents Excel file where keys are first
	 *         row
	 */
	public List<HashMap<String, String>> getGeneralTestTable() {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		Workbook workbook = getWorkbook(dataTablePath);
		Sheet sheet = workbook.getSheet(GENERAL_SHEET);
		if (sheet == null) {
			Assert.fail("DataFile Exception: " + dataTablePath + " doesn't has " + GENERAL_SHEET + " sheet.");
		}

		Row keysRow = sheet.getRow(0);
		int keysCount = keysRow.getPhysicalNumberOfCells();
		int rowsCount = sheet.getPhysicalNumberOfRows();

		for (int i = 1; i < rowsCount; i++) {
			HashMap<String, String> iteration = new HashMap<String, String>();
			Row row = sheet.getRow(i);
			for (int j = 0; j < keysCount; j++) {
				Cell cell = row.getCell(j);
				String stringCellValue = "";

				if (cell == null) {
					iteration.put(keysRow.getCell(j).getStringCellValue(), "");
				} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					stringCellValue = String.valueOf(cell.getNumericCellValue());
				} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					stringCellValue = String.valueOf(cell.getBooleanCellValue());
				} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
					stringCellValue = cell.getCellFormula();
				} else {
					stringCellValue = cell.getStringCellValue();
				}

				iteration.put(keysRow.getCell(j).getStringCellValue(), stringCellValue);
			}
			result.add(iteration);
		}
		return result;
	}

	/**
	 * Returns workbook to work with.
	 */
	private Workbook getWorkbook(String dataTablePath) {
		Workbook workbook = null;
		try {
			InputStream is = new FileInputStream(new File(dataTablePath));
			if (IOHelper.fileExtensionEquals(dataTablePath, ".xls")) {
				workbook = new HSSFWorkbook(is);
			} else {
				workbook = new XSSFWorkbook(is);
			}
			is.close();
		} catch (Exception e) {
			Assert.fail("DataFile Exception: " + e.getMessage());
		}
		return workbook;
	}
}