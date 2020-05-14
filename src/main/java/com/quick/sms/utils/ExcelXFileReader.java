package com.quick.sms.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author srinivasP
 */
public class ExcelXFileReader implements PanelFileReader {

	// ArrayList<ArrayList<Object>> fileData = new
	// ArrayList<ArrayList<Object>>();
	public ExcelXFileReader() {
	}

	FileInputStream fis = null;
	Iterator<Row> rowIterator = null;
	XSSFSheet sheet;

	public void openFile(File file, boolean firstLineHeader) throws FileNotFoundException, IOException, Exception {
		XSSFWorkbook wb = null;
		try {
			System.out.println("file==>" + file.getAbsolutePath());
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException ex) {
			System.out.println("Excep IS=" + ex);
		} catch (Exception ex) {
			// ex.printStackTrace();
			System.out.println("Excep IS=" + ex);
		} finally {
			if (wb != null) {
				sheet = wb.getSheetAt(0);
				rowIterator = sheet.rowIterator();
				if (firstLineHeader) {
					if (rowIterator.hasNext()) {
						rowIterator.next();
					}
				}
			}

		}
	}
	
	public void openFile(InputStream file, boolean firstLineHeader) throws FileNotFoundException, IOException, Exception {
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(file);
		} catch (FileNotFoundException ex) {
			System.out.println("Excep IS=" + ex);
		} catch (Exception ex) {
			// ex.printStackTrace();
			System.out.println("Excep IS=" + ex);
		} finally {
			if (wb != null) {
				sheet = wb.getSheetAt(0);
				rowIterator = sheet.rowIterator();
				if (firstLineHeader) {
					if (rowIterator.hasNext()) {
						rowIterator.next();
					}
				}
			}

		}
	}

	public void closeFile() {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException ex) {
				Logger.getLogger(ExcelXFileReader.class.getName()).log(Level.SEVERE, null, ex);
			}
			fis = null;
		}
	}

	int lineNumner = 0;

	public int getLineNumber() {
		return lineNumner;
	}

	public int getLastLineNumber() {
		return sheet.getLastRowNum();
	}

	public Object getNextLine(int colnumber) throws Exception {
		Object linedata = null;
		XSSFRow row = null;
		// Iterator cellIterator = null;
		if (rowIterator.hasNext()) {
			lineNumner++;

			row = (XSSFRow) rowIterator.next();
			// int cellcount = row.getLastCellNum();
			// cellIterator = row.cellIterator();
			XSSFCell cell;
			// System.out.println("-----" + row.getLastCellNum());
			int cellNum = row.getLastCellNum();
			for (short i = 0; i < cellNum; i++) {

				cell = row.getCell(i);
				if (cell != null) {
					if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
						linedata = ("");
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
						linedata = Boolean.valueOf((cell.getBooleanCellValue()));
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						linedata = (new BigDecimal(cell.getNumericCellValue()).toPlainString());
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						linedata = (cell.getStringCellValue());
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_ERROR) {
						linedata = ("");
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
						linedata = ("");
					} else {
						linedata = (cell.getDateCellValue());
					}
				} else {
					linedata = ("");
				}
				if (i == colnumber) {
					break;
				}
			}

			// System.out.println("data::" + linedata);
		}
		return linedata;
	}

	public ArrayList<Object> getNextLine() throws Exception {
		ArrayList linedata = null;
		XSSFRow row = null;
		// Iterator cellIterator = null;
		if (rowIterator.hasNext()) {
			lineNumner++;
			linedata = new ArrayList<>();
			row = (XSSFRow) rowIterator.next();
			// int cellcount = row.getLastCellNum();
			// cellIterator = row.cellIterator();
			XSSFCell cell;
			System.out.println(row.getFirstCellNum() + "    " + row.getLastCellNum());
			System.out.println("-----" + row.getPhysicalNumberOfCells());
			int cellNum = row.getPhysicalNumberOfCells();
			Iterator<Cell> cellIterator = row.cellIterator();
			// while (cellIterator.hasNext()) {
			// cell = (XSSFCell) cellIterator.next();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);// (XSSFCell) cellIterator.next();
				if (cell != null) {
					int i = cell.getColumnIndex();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
						linedata.add("");
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
						linedata.add(cell.getBooleanCellValue());
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						double dv = cell.getNumericCellValue();
						if (DateUtil.isCellDateFormatted(cell)) {
							Date date = DateUtil.getJavaDate(dv);

							String dateFmt = cell.getCellStyle().getDataFormatString();
							/*
							 * strValue = new
							 * SimpleDateFormat(dateFmt).format(date); - won't
							 * work as Java fmt differs from Excel fmt. If Excel
							 * date format is mm/dd/yyyy, Java will always be 00
							 * for date since "m" is minutes of the hour.
							 */

							linedata.add(new CellDateFormatter(dateFmt).format(date));
							// takes care of idiosyncrasies of Excel
							System.out.println(i + "====" + new CellDateFormatter(dateFmt).format(date));
						} else {
							cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							linedata.add(StringUtils.isBlank(cell.getRichStringCellValue().getString()) ? ""
									: cell.getRichStringCellValue().getString());
							System.out.println(i + "====" + cell.getRichStringCellValue());
						}

						// System.out.println(i + "====" +
						// cell.getRichStringCellValue() + "===" +
						// nf.format(cell.getNumericCellValue()));
						// linedata.add(nf.format(cell.getNumericCellValue()));
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						linedata.add(StringUtils.isBlank(cell.getStringCellValue()) ? "" : cell.getStringCellValue());
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_ERROR) {
						linedata.add("");
					} else if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
						linedata.add("");
					} else {
						linedata.add(cell.getDateCellValue());
					}
				} else {
					linedata.add("");
				}
			}
			NumberFormat nf = new java.text.DecimalFormat("#.#");
			for (int i = 0; i < cellNum; i++) {
				cell = row.getCell(i);

			}

			// System.out.println("data::" + linedata);
		}
		return linedata;
	}
}
