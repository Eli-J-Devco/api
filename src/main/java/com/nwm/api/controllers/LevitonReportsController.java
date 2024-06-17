/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.services.LevitonReportsService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/leviton")
public class LevitonReportsController extends BaseController {

	public static final int COLUMN_INDEX_ID = 0;
	public static final int COLUMN_INDEX_TITLE = 1;
	public static final int COLUMN_INDEX_PRICE = 2;
	public static final int COLUMN_INDEX_QUANTITY = 3;
	public static final int COLUMN_INDEX_TOTAL = 4;
	
	
	/**
	 * @description Get daily report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-report")
	public Object getDailyReport(@RequestBody ViewReportEntity obj) {
		try {
			LevitonReportsService service = new LevitonReportsService();
			ViewReportEntity dataObj = (ViewReportEntity) service.getDataReport(obj);
			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	// Write header with format
	private static void writeHeaderLevitonReport(Sheet sheet, int rowIndex, ViewReportEntity dataObj) {
		try {
			DecimalFormat format = new DecimalFormat("###,###.0");
			sheet.setDisplayGridlines(false);
			DecimalFormat df = new DecimalFormat("###,###.#");
			DecimalFormat dfs = new DecimalFormat("###,###");
			// create CellStyle
			
			Font fontDef = sheet.getWorkbook().createFont();
			fontDef.setFontName("Times New Roman");
			fontDef.setFontHeightInPoints((short) 12); // font size
			
			CellStyle cellStyle = createStyleForHeader(sheet);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setFont(fontDef);
			
			// create CellStyle title
			CellStyle cellStyleTitle = createStyleForHeader(sheet);
			cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleTitle.setAlignment(HorizontalAlignment.LEFT);

			// Create style row
			Font fontRow = sheet.getWorkbook().createFont();
			fontRow.setFontName("Times New Roman");
			fontRow.setFontHeightInPoints((short) 12); // font size
			fontRow.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleItem = sheet.getWorkbook().createCellStyle();
			cellStyleItem.setFont(fontRow);
			cellStyleItem.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleItem.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleItem.setAlignment(HorizontalAlignment.CENTER);

			// Create font
			Font fontBold = sheet.getWorkbook().createFont();
			fontBold.setFontName("Times New Roman");
			fontBold.setBold(true);
			fontBold.setFontHeightInPoints((short) 22); // font size
			CellStyle cellStyleFontBold = sheet.getWorkbook().createCellStyle();
			cellStyleFontBold.setFont(fontBold);
			cellStyleFontBold.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleFontBold.setAlignment(HorizontalAlignment.CENTER);

			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 15 * 256);
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 15 * 256);
			sheet.setColumnWidth(3, 15 * 256);
			sheet.setColumnWidth(4, 15 * 256);
			sheet.setColumnWidth(5, 15 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 15 * 256);
			sheet.setColumnWidth(8, 15 * 256);
			sheet.setColumnWidth(9, 15 * 256);
			sheet.setColumnWidth(10, 15 * 256);
			sheet.setColumnWidth(11, 15 * 256);
			sheet.setColumnWidth(12, 15 * 256);
			sheet.setColumnWidth(13, 15 * 256);
			sheet.setDefaultRowHeight((short) 500);
			
			
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
			
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 4));
			
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 4));
			
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 4));
			
			
			
			// Create font
			Font styleLeft = sheet.getWorkbook().createFont();
			styleLeft.setFontName("Times New Roman");
			styleLeft.setBold(true);
			styleLeft.setFontHeightInPoints((short) 12); // font size
			CellStyle cellStyleLeft = sheet.getWorkbook().createCellStyle();
			cellStyleLeft.setFont(styleLeft);
			cellStyleLeft.setWrapText(true);
			cellStyleLeft.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleLeft.setAlignment(HorizontalAlignment.LEFT);
			cellStyleLeft.setBorderBottom(BorderStyle.THIN);
			cellStyleLeft.setBorderTop(BorderStyle.THIN);
			cellStyleLeft.setBorderRight(BorderStyle.THIN);
			cellStyleLeft.setBorderLeft(BorderStyle.THIN);
			cellStyleLeft.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleLeft.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleLeft.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleLeft.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			
			// Create font
			Font styleCenter = sheet.getWorkbook().createFont();
			styleCenter.setFontName("Times New Roman");
			styleCenter.setBold(false);
			styleCenter.setFontHeightInPoints((short) 12); // font size
			CellStyle cellStyleCenter = sheet.getWorkbook().createCellStyle();
			cellStyleCenter.setFont(styleCenter);
			cellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleCenter.setAlignment(HorizontalAlignment.LEFT);
			
			cellStyleCenter.setBorderBottom(BorderStyle.THIN);
			cellStyleCenter.setBorderTop(BorderStyle.THIN);
			cellStyleCenter.setBorderRight(BorderStyle.THIN);
			cellStyleCenter.setBorderLeft(BorderStyle.THIN);
			
			cellStyleCenter.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCenter.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCenter.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCenter.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			Row row5 = sheet.createRow(0);
			Cell cel5 = row5.createCell(0);
			cel5.setCellStyle(cellStyleLeft);
			cel5.setCellValue("Site Name");
			
			Cell cel51 = row5.createCell(1);
			cel51.setCellStyle(cellStyleLeft);
			cel51.setCellValue("");
			
			
			Cell cel52 = row5.createCell(2);
			row5.setHeight((short) 600);
			cel52.setCellStyle(cellStyleLeft);
			cel52.setCellValue(dataObj.getSite_name());

			
			Cell cel53 = row5.createCell(3);
			cel53.setCellStyle(cellStyleCenter);
			cel53.setCellValue("");
			
			Cell cel54 = row5.createCell(4);
			cel54.setCellStyle(cellStyleCenter);
			cel54.setCellValue("");
			
			Row row6 = sheet.createRow(1);
			Cell cel6 = row6.createCell(0);
			row6.setHeight((short) 600);
			cel6.setCellStyle(cellStyleLeft);
			cel6.setCellValue("Report Date");
			
			Cell cel61 = row6.createCell(1);
			cel61.setCellStyle(cellStyleLeft);
			cel61.setCellValue("");
			
			Cell cel62 = row6.createCell(2);
			cel62.setCellStyle(cellStyleCenter);
			cel62.setCellValue(dataObj.getReport_date());
			
			Cell cel63 = row6.createCell(3);
			cel63.setCellStyle(cellStyleCenter);
			cel63.setCellValue("");
			
			Cell cel64 = row6.createCell(4);
			cel64.setCellStyle(cellStyleCenter);
			cel64.setCellValue("");
			
			
			// Create font
			Font stylepr = sheet.getWorkbook().createFont();
			stylepr.setFontName("Times New Roman");
			stylepr.setBold(true);
			stylepr.setFontHeightInPoints((short) 12); // font size
			stylepr.setColor(IndexedColors.WHITE.index);
			CellStyle cellStylepr = sheet.getWorkbook().createCellStyle();
			cellStylepr.setFont(stylepr);
			
			cellStylepr.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStylepr.setAlignment(HorizontalAlignment.LEFT);
			
			cellStylepr.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.index);
			cellStylepr.setFillPattern(FillPatternType.BIG_SPOTS);
			cellStylepr.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			
			
			
			Row row8 = sheet.createRow(2);
			row8.setHeight((short) 600);
			Cell cel8 = row8.createCell(0);
			cel8.setCellStyle(cellStyleLeft);
			cel8.setCellValue("Covered Period");
			
			Cell cel81 = row8.createCell(1);
			cel81.setCellStyle(cellStyleLeft);
			cel81.setCellValue("");
			String cadenceRange = null;
			switch(dataObj.getCadence_range()){
			case 1:
				cadenceRange = "DAILY ";
				break;
			case 2:
				cadenceRange = "MONTHLY ";
				break;
			case 4:
				cadenceRange = "ANNUAL ";
				break;
			case 6:
				cadenceRange = "WEEKLY ";
				break;
			}
			
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 14));	
			Cell cell = row8.createCell(5);
			cell.setCellStyle(cellStyleFontBold);
			cell.setCellValue(cadenceRange +"PRODUCTION REPORT");
			
			Cell cel12d = row8.createCell(2);
			cel12d.setCellStyle(cellStyleCenter);
			cel12d.setCellValue(dataObj.getStart_date() + " - " + dataObj.getEnd_date());
			
			Cell cel12e = row8.createCell(3);
			cel12e.setCellStyle(cellStyleCenter);
			cel12e.setCellValue("");
			
			Cell cel12f = row8.createCell(4);
			cel12f.setCellStyle(cellStyleCenter);
			cel12f.setCellValue("");
			
			
			Row row9 = sheet.createRow(3);
			row9.setHeight((short) 600);
			Cell cel9 = row9.createCell(0);
			cel9.setCellStyle(cellStyleLeft);
			cel9.setCellValue("System Size (kW DC)");
			
			Cell cel91 = row9.createCell(1);
			cel91.setCellStyle(cellStyleLeft);
			cel91.setCellValue("");
			
			
			Cell celd = row9.createCell(2);
			celd.setCellStyle(cellStyleCenter);
			celd.setCellValue( df.format(dataObj.getDc_capacity() ) );
			
			Cell cele = row9.createCell(3);
			cele.setCellStyle(cellStyleCenter);
			cele.setCellValue("");
			
			Cell celf = row9.createCell(4);
			celf.setCellStyle(cellStyleCenter);
			celf.setCellValue("");
			
			
			
			// Create font
			Font styleH = sheet.getWorkbook().createFont();
			styleH.setFontName("Times New Roman");
			styleH.setBold(true);
			styleH.setColor(IndexedColors.WHITE.index);
			styleH.setFontHeightInPoints((short) 12); // font size
			CellStyle cellStyleH = sheet.getWorkbook().createCellStyle();
			cellStyleH.setFont(styleH);
			cellStyleH.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleH.setAlignment(HorizontalAlignment.CENTER);
			cellStyleH.setBorderBottom(BorderStyle.THIN);
			cellStyleH.setBorderTop(BorderStyle.THIN);
			cellStyleH.setBorderRight(BorderStyle.THIN);
			cellStyleH.setBorderLeft(BorderStyle.THIN);
			
			cellStyleH.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleH.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleH.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleH.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleH.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.index);
			
			cellStyleH.setFillPattern(FillPatternType.BIG_SPOTS);
			cellStyleH.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
			
			
			// Create font
			Font styleR = sheet.getWorkbook().createFont();
			styleR.setFontName("Times New Roman");
			styleR.setBold(false);
			
			styleR.setFontHeightInPoints((short) 12); // font size
			CellStyle cellStyleR = sheet.getWorkbook().createCellStyle();
			cellStyleR.setFont(styleR);
			cellStyleR.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleR.setAlignment(HorizontalAlignment.CENTER);
			
			
			cellStyleR.setBorderBottom(BorderStyle.THIN);
			cellStyleR.setBorderTop(BorderStyle.THIN);
			cellStyleR.setBorderRight(BorderStyle.THIN);
			cellStyleR.setBorderLeft(BorderStyle.THIN);
			
			cellStyleR.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleR.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleR.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleR.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 4, 5));
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 6, 7));
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 9));
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 10, 11));
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 12, 13));
			
			Row row28 = sheet.createRow(6);
			row28.setHeight((short) 500);
			Cell cel28 = row28.createCell(0);
			cel28.setCellStyle(cellStyleH);
			cel28.setCellValue("Site Name");
			
			Cell cel281 = row28.createCell(1);
			cel281.setCellStyle(cellStyleH);
			cel281.setCellValue("");
			
			
			Cell cel29 = row28.createCell(2);
			cel29.setCellStyle(cellStyleH);
			cel29.setCellValue("Device Type");
			
			Cell cel294 = row28.createCell(3);
			cel294.setCellStyle(cellStyleH);
			cel294.setCellValue("");
			
			Cell cel30 = row28.createCell(4);
			cel30.setCellStyle(cellStyleH);
			cel30.setCellValue("Device Name");
			
			Cell cel307 = row28.createCell(5);
			cel307.setCellStyle(cellStyleH);
			cel307.setCellValue("");
			
			Cell cel31 = row28.createCell(6);
			cel31.setCellStyle(cellStyleH);
			cel31.setCellValue("Start Read Date");
			
			Cell cel3110 = row28.createCell(7);
			cel3110.setCellStyle(cellStyleH);
			cel3110.setCellValue("");
			
			
			Cell cel32 = row28.createCell(8);
			cel32.setCellStyle(cellStyleH);
			cel32.setCellValue("Start Read");
			
			Cell cel3210 = row28.createCell(9);
			cel3210.setCellStyle(cellStyleH);
			cel3210.setCellValue("");
			
			Cell cel33 = row28.createCell(10);
			cel33.setCellStyle(cellStyleH);
			cel33.setCellValue("End Read Date");
			
			Cell cel3310 = row28.createCell(11);
			cel3310.setCellStyle(cellStyleH);
			cel3310.setCellValue("");
			
			Cell cel34 = row28.createCell(12);
			cel34.setCellStyle(cellStyleH);
			cel34.setCellValue("End Read");
			
			Cell cel3410 = row28.createCell(13);
			cel3410.setCellStyle(cellStyleH);
			cel3410.setCellValue("");
			
			
			Cell cel35 = row28.createCell(14);
			cel35.setCellStyle(cellStyleH);
			cel35.setCellValue("Consumption");
			
			
			Cell cel36 = row28.createCell(15);
			cel36.setCellStyle(cellStyleH);
			cel36.setCellValue("Unit");
			
			
			Cell cel37 = row28.createCell(16);
			cel37.setCellStyle(cellStyleH);
			cel37.setCellValue("Cost / Unit");
			
			
			Cell cel38 = row28.createCell(17);
			cel38.setCellStyle(cellStyleH);
			cel38.setCellValue("Total");
			
			
			
			
			List <?> dataExports = dataObj.getDataReports();
			if(dataExports.size() > 0) {
				for(int i = 0 ;i < dataExports.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) dataExports.get(i);
					int t = 7 + i;
					
					sheet.addMergedRegion(new CellRangeAddress(t, t, 0, 1));
					sheet.addMergedRegion(new CellRangeAddress(t, t, 2, 3));
					sheet.addMergedRegion(new CellRangeAddress(t, t, 4, 5));
					sheet.addMergedRegion(new CellRangeAddress(t, t, 6, 7));
					sheet.addMergedRegion(new CellRangeAddress(t, t, 8, 9));
					sheet.addMergedRegion(new CellRangeAddress(t, t, 10, 11));
					sheet.addMergedRegion(new CellRangeAddress(t, t, 12, 13));
					
					Row row28r = sheet.createRow(t);
					row28r.setHeight((short) 500);
					Cell cel28r = row28r.createCell(0);
					cel28r.setCellStyle(cellStyleR);
					cel28r.setCellValue( (String) item.get("site_name") );
					
					Cell cel28r1 = row28r.createCell(1);
					cel28r1.setCellStyle(cellStyleR);
					cel28r1.setCellValue("");
					
					Cell cel29r = row28r.createCell(2);
					cel29r.setCellStyle(cellStyleR);
					cel29r.setCellValue( (String) item.get("device_type")  );
					
					Cell cel29r4 = row28r.createCell(3);
					cel29r4.setCellStyle(cellStyleR);
					cel29r4.setCellValue("");
					
					
					Cell cel30r = row28r.createCell(4);
					cel30r.setCellStyle(cellStyleR);
					cel30r.setCellValue((String) item.get("device_name") );
					
					Cell cel30r7 = row28r.createCell(5);
					cel30r7.setCellStyle(cellStyleR);
					cel30r7.setCellValue("");
					
					Cell cel31r = row28r.createCell(6);
					cel31r.setCellStyle(cellStyleR);
					cel31r.setCellValue((String) item.get("date_from") );
					
					Cell cel31r10 = row28r.createCell(7);
					cel31r10.setCellStyle(cellStyleR);
					cel31r10.setCellValue("");
					
					Cell cel32r = row28r.createCell(8);
					cel32r.setCellStyle(cellStyleR);
					cel32r.setCellValue(format.format((Double) item.get("start_read"))  );
					
					Cell cel32r10 = row28r.createCell(9);
					cel32r10.setCellStyle(cellStyleR);
					cel32r10.setCellValue("");
					
					Cell cel33r = row28r.createCell(10);
					cel33r.setCellStyle(cellStyleR);
					cel33r.setCellValue((String) item.get("date_to") );
					
					Cell cel33r10 = row28r.createCell(11);
					cel33r10.setCellStyle(cellStyleR);
					cel33r10.setCellValue("");
					
					Cell cel34r = row28r.createCell(12);
					cel34r.setCellStyle(cellStyleR);
					cel34r.setCellValue(format.format((Double) item.get("end_read")) );
					
					Cell cel34r10 = row28r.createCell(13);
					cel34r10.setCellStyle(cellStyleR);
					cel34r10.setCellValue("");
					
					Cell cel35r = row28r.createCell(14);
					cel35r.setCellStyle(cellStyleR);
					cel35r.setCellValue(format.format((Double) item.get("consumption")) );
					
					
					Cell cel36r = row28r.createCell(15);
					cel36r.setCellStyle(cellStyleR);
					cel36r.setCellValue((String) item.get("consumption_unit") );
					
					
					Cell cel37r = row28r.createCell(16);
					cel37r.setCellStyle(cellStyleR);
					cel37r.setCellValue(item.get("cost_unit") +" "+ format.format((Double) item.get("cost")) );
					
					
					Cell cel38r = row28r.createCell(17);
					cel38r.setCellStyle(cellStyleR);
					cel38r.setCellValue(item.get("cost_unit") +" "+ format.format((Double) item.get("total")) );
					
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	
	// Create CellStyle for header
	private static CellStyle createStyleForHeader(Sheet sheet) {
		// Create font
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 12); // font size
		font.setColor(IndexedColors.BLACK.getIndex()); // text color

		// Create CellStyle
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		return cellStyle;
	}

	// Write footer
	private static void writeFooter(Sheet sheet, int rowIndex) {
		// Create row
		Row row = sheet.createRow(rowIndex);
		Cell cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
		cell.setCellFormula("SUM(E2:E6)");
	}

	// Auto resize column width
	private static void autosizeColumn(Sheet sheet, int lastColumn) {
		for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
			sheet.autoSizeColumn(columnIndex);
		}
	}

	// Create output file
	private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
		try (OutputStream os = new FileOutputStream(excelFilePath)) {
			workbook.write(os);
		}
	}
		
	
	
	@PostMapping("/sent-email-excel-leviton-report")
	public Object sentMailDailyReport(@RequestBody ViewReportEntity obj) {
		try {
			try (XSSFWorkbook document = new XSSFWorkbook()) {
				LevitonReportsService service = new LevitonReportsService();
				ViewReportEntity dataObj = (ViewReportEntity) service.getDataReport(obj);
				if (dataObj != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					Date startDate = dateFormat.parse(obj.getStart_date());
					Date endDate = dateFormat.parse(obj.getEnd_date());

					Calendar calQ = Calendar.getInstance();
					dataObj.setReport_date(format.format(calQ.getTime()));
					calQ.setTime(startDate);
					dataObj.setStart_date(format.format(calQ.getTime()));
					calQ.setTime(endDate);
					dataObj.setEnd_date(format.format(calQ.getTime()));
					
					XSSFSheet chartSheet = document.createSheet("Daily Production Report");
					XSSFSheet dataSheet = document.createSheet("data");
					// FileInputStream obtains input bytes from the image file
					InputStream inputStreamImage = new FileInputStream(uploadRootPath() + "/reports/logo-report.jpg");
					// Get the contents of an InputStream as a byte[].
					byte[] bytes = IOUtils.toByteArray(inputStreamImage);
					// Adds a picture to the workbook
					int pictureIdx = document.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
					// close the input stream
					inputStreamImage.close();

					// Returns an object that handles instantiating concrete classes
					CreationHelper helper = document.getCreationHelper();
					// Creates the top-level drawing patriarch.
					Drawing drawing = chartSheet.createDrawingPatriarch();

					// Create an anchor that is attached to the worksheet
					ClientAnchor anchor = helper.createClientAnchor();
					// set top-left corner for the image
					anchor.setCol1(16);
					anchor.setRow1(1);

					// Creates a picture
					Picture pict = drawing.createPicture(anchor, pictureIdx);
					// Reset the image to the original size
					pict.resize(1.45, 3.5);
					
					writeHeaderLevitonReport(chartSheet, 0, dataObj);
					
					// Write the output to a file
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String dir = uploadRootPath() + "/"
							+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
					String fileName = dir + "/bmo-consumption-report-" + timeStamp + ".xlsx";
					
					try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
						document.write(fileOut);
						String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
								Constants.mailFromContact);

						String msgTemplate = Constants.getMailTempleteByState(16);
						String cadenceRange = null;
						switch(dataObj.getCadence_range()){
						case 1:
							cadenceRange = "Daily ";
							break;
						case 2:
							cadenceRange = "Monthly ";
							break;
						case 4:
							cadenceRange = "Annual ";
							break;
						case 6:
							cadenceRange = "Weekly ";
							break;
						}
						
						String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", cadenceRange, "", "");
						String mailTo = dataObj.getSubscribers();
						String subject = Constants.getMailSubjectByState(16);

						String tags = "leviton_report";
						String fromName = "NEXT WAVE ENERGY MONITORING INC";
						boolean flagSent = SendMail.SendGmailTLSAttachmentattachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
						if (!flagSent) {
							throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
						}
					}
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, dataObj, 1);
				} else {
					return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				}
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description sent mail Leviton report in pdf
	 * @author Long.Pham
	 * @since 2024-06-16
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-email-pdf-leviton-report")
	public Object sentMailPdfDailyReport(@RequestBody ViewReportEntity obj) {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
			String dir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
			String fileName = dir + "/bmo-consumption-report-" + timeStamp + ".pdf";
			File file = new File(fileName);
			
			try (
					PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
					Document document = new Document(pdfDocument, PageSize.A3.rotate());
					) {
				
				LevitonReportsService service = new LevitonReportsService();
				ViewReportEntity dataObj = (ViewReportEntity) service.getDataReport(obj);

				if (dataObj != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					Date startDate = dateFormat.parse(obj.getStart_date());
					Date endDate = dateFormat.parse(obj.getEnd_date());
					
					Calendar calQ = Calendar.getInstance();
					dataObj.setReport_date(format.format(calQ.getTime()));
					calQ.setTime(startDate);
					dataObj.setStart_date(format.format(calQ.getTime()));
					calQ.setTime(endDate);
					dataObj.setEnd_date(format.format(calQ.getTime()));
					List<?> dataExports = dataObj.getDataReports();
					
					// total column: 12
					Table table = new Table(UnitValue.createPercentArray(12)).useAllAvailableWidth();
					table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
					table.setFontSize(8);
					table.setTextAlignment(TextAlignment.CENTER);
					
					Image logoImage = new Image(ImageDataFactory.create(uploadRootPath() + "/reports/logo-report.jpg"));
					logoImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT).scaleToFit(100, 100);
					
					String cadenceRange = null;
					switch(dataObj.getCadence_range()){
					case 1:
						cadenceRange = "DAILY ";
						break;
					case 2:
						cadenceRange = "MONTHLY ";
						break;
					case 4:
						cadenceRange = "ANNUAL ";
						break;
					case 6:
						cadenceRange = "WEEKLY ";
						break;
					}
				
					//====== table ============================================================
					// header and logo
					table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setHeight(14).setBorder(Border.NO_BORDER));
					table.addCell(new com.itextpdf.layout.element.Cell(6, 5).add(new Paragraph(cadenceRange + "PRODUCTION REPORT")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
					table.addCell(new com.itextpdf.layout.element.Cell(6, 3).add(logoImage).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Site Name").setBold().setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataObj.getSite_name()).setBold().setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Report Date").setBold().setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataObj.getReport_date()).setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Covered Period").setBold().setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataObj.getStart_date() + " - " + dataObj.getEnd_date()).setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("System Size (kW DC)").setBold().setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(String.valueOf(dataObj.getDc_capacity())).setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setHeight(14).setBorder(Border.NO_BORDER));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
					
					// chart
					com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(16, 12);
					table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
					// empty row
					table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
					
					// header of data table
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Site Name").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Device Type").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Device Name").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Start Read Date").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Start Read").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("End Read Date").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("End Read").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Consumption").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Unit").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Cost / Unit").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Total").setBold()));
					
																						
					
					// data table
					DecimalFormat df = new DecimalFormat("###,###.##");
					for (int i = 0; i < dataExports.size(); i++) {
						Map<String, Object> item = (Map<String, Object>) dataExports.get(i);
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph((String)item.get("site_name"))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph((String)item.get("device_type"))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph((String)item.get("device_name"))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph((String)item.get("date_from"))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph(df.format((Double)item.get("start_read")))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph((String)item.get("date_to"))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph(df.format((Double)item.get("end_read")))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph(df.format((Double)item.get("consumption")))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph((String)item.get("consumption_unit"))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph(item.get("cost_unit") +" "+ item.get("cost"))));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph(item.get("cost_unit") +" "+ df.format((Double)item.get("total")))));
					}
					
					// Write the output to a file
					document.add(table);
					// It must be closed before attach to mail
					document.close();
					
				    String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
				    String msgTemplate = Constants.getMailTempleteByState(16);
				    String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", cadenceRange.toLowerCase(), "", "");
				    String mailTo = dataObj.getSubscribers();
				    String subject = Constants.getMailSubjectByState(16);
				    
				    String tags = "report_leviton";
				    String fromName = "NEXT WAVE ENERGY MONITORING INC";
				    boolean flagSent = SendMail.SendGmailTLSAttachmentattachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
				    if (!flagSent) {
				    	throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
				    }
				    
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, dataObj, 1);
				} else {
					return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				}
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
}
