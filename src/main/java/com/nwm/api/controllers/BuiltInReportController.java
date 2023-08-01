/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFShapeProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.AxisCrossBetween;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.STMarkerStyle;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeeklyDateEntity;
import com.nwm.api.services.BuiltInReportService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@ApiIgnore
@RequestMapping("/built-in-reports")
public class BuiltInReportController extends BaseController {

	/**
	 * @description Sent Mail Annual Production Trend Report
	 * @author long.pham
	 * @since 2023-07-25
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/annual-production-trend-report")
	public Object annualProductionTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			// Get list data site render report
			BuiltInReportService service = new BuiltInReportService();
			ViewReportEntity dataObj = (ViewReportEntity) service.getAnnuallyBuitInReport(obj);	
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, dataObj, 1);

		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
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
	
	// Write header with format
	private static void writeHeaderAnnuallyReport(Sheet sheet, int rowIndex, ArrayList categories, ArrayList actualGeneration, ViewReportEntity dataObj, ArrayList dataExpectedGeneration, ArrayList dataModeledGeneration, ArrayList dataPOA, ArrayList dataExpectedGenerationIndex, ArrayList dataModeledGenerationIndex, double totalActualGeneration, double totalExpectedGeneration, double totalModeledGeneration, double totalExpectedGenerationIndex, double totalModeledGenerationIndex) {
		try {
			DecimalFormat df = new DecimalFormat("###,###");
			DecimalFormat df1p = new DecimalFormat("###,###.#");
			DecimalFormat df2p = new DecimalFormat("###,###.##");
			// create CellStyle
			Font fonDef = sheet.getWorkbook().createFont();
			fonDef.setFontName("Calibri (Body)");
			fonDef.setFontHeightInPoints((short) 12); // font size
			
			CellStyle cellStyle = createStyleForHeader(sheet);
			cellStyle.setFont(fonDef);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			
			
			
			// create CellStyle title
			CellStyle cellStyleTitle = createStyleForHeader(sheet);
			cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleTitle.setAlignment(HorizontalAlignment.LEFT);
			
			cellStyleTitle.setBorderBottom(BorderStyle.THIN);
			cellStyleTitle.setBorderTop(BorderStyle.THIN);
			cellStyleTitle.setBorderRight(BorderStyle.THIN);
			cellStyleTitle.setBorderLeft(BorderStyle.THIN);
			cellStyleTitle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleTitle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleTitle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleTitle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			

			// Create font
			Font fontBold = sheet.getWorkbook().createFont();
			fontBold.setFontName("Calibri (Body)");
			fontBold.setBold(true);
			fontBold.setFontHeightInPoints((short) 12); // font size
			CellStyle cellStyleFontBold = sheet.getWorkbook().createCellStyle();
			cellStyleFontBold.setFont(fontBold);
			cellStyleFontBold.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleFontBold.setAlignment(HorizontalAlignment.CENTER);
			cellStyleFontBold.setBorderBottom(BorderStyle.THIN);
			cellStyleFontBold.setBorderTop(BorderStyle.THIN);
			cellStyleFontBold.setBorderRight(BorderStyle.THIN);
			cellStyleFontBold.setBorderLeft(BorderStyle.THIN);
			cellStyleFontBold.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleFontBold.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleFontBold.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleFontBold.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 30 * 256);
			sheet.setColumnWidth(1, 30 * 256);
			sheet.setColumnWidth(2, 30 * 256);
			sheet.setColumnWidth(3, 30 * 256);
			sheet.setColumnWidth(4, 30 * 256);
			sheet.setColumnWidth(5, 30 * 256);
			
			sheet.setDefaultRowHeight((short) 500);
			sheet.setDisplayGridlines(false);
			
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));

			Row row1 = sheet.createRow(1);
			row1.setHeight((short) 600);
			Cell cell = row1.createCell(0);

			// Create font
			Font font = sheet.getWorkbook().createFont();
			font.setFontName("Calibri (Body)");
			font.setBold(true);
			font.setFontHeightInPoints((short) 18); // font size
			font.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleHeader = sheet.getWorkbook().createCellStyle();
			cellStyleHeader.setFont(font);
			cellStyleHeader.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
			
			cell.setCellStyle(cellStyleHeader);
			cell.setCellValue("ANNUAL PRODUCTION TREND REPORT (MONTHLY INTERVAL)");
			
			
			
			// Create font
			Font font1 = sheet.getWorkbook().createFont();
			font1.setFontName("Calibri (Body)");
			font1.setBold(true);
			font1.setFontHeightInPoints((short) 14); // font size
			font1.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleSubTitle = sheet.getWorkbook().createCellStyle();
			cellStyleSubTitle.setFont(font1);
			cellStyleSubTitle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleSubTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleSubTitle.setAlignment(HorizontalAlignment.CENTER);
						
			Row row2 = sheet.createRow(2);
			row2.setHeight((short) 500);
			Cell cell2 = row2.createCell(0);
			cell2.setCellStyle(cellStyleSubTitle);
			cell2.setCellValue(dataObj.getSite_name().toUpperCase());
			
			
			
			Font font3 = sheet.getWorkbook().createFont();
			font3.setFontName("Calibri (Body)");
			font3.setBold(false);
			font3.setFontHeightInPoints((short) 14); // font size
			font3.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleDate = sheet.getWorkbook().createCellStyle();
			cellStyleDate.setFont(font3);
			cellStyleDate.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleDate.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleDate.setAlignment(HorizontalAlignment.CENTER);
			Row row3 = sheet.createRow(3);
			row3.setHeight((short) 500);
			Cell cell3 = row3.createCell(0);
			cell3.setCellStyle(cellStyleDate);
			
			SimpleDateFormat dt = new SimpleDateFormat("mm/dd/yyyy"); 
			Date startDate = dt.parse(dataObj.getStart_date());
			Date endDate = dt.parse(dataObj.getEnd_date()); 
			cell3.setCellValue(dt.format(startDate) + " - " +  dt.format(endDate));
			
			
			
			Font fonDefB = sheet.getWorkbook().createFont();
			fonDefB.setFontName("Calibri (Body)");
			fonDefB.setBold(true);
			fonDefB.setFontHeightInPoints((short) 12); // font size
			
			CellStyle cellStyleB = createStyleForHeader(sheet);
			cellStyleB.setFont(fonDefB);
			cellStyleB.setWrapText(true);
			cellStyleB.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleB.setAlignment(HorizontalAlignment.CENTER);
			cellStyleB.setBorderBottom(BorderStyle.THIN);
			cellStyleB.setBorderTop(BorderStyle.THIN);
			cellStyleB.setBorderRight(BorderStyle.THIN);
			cellStyleB.setBorderLeft(BorderStyle.THIN);
			cellStyleB.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleB.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleB.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleB.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			
			Row row4 = sheet.createRow(5);
			row4.setHeight((short) 500);
			Cell cell4 = row4.createCell(0);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("");

			cell4 = row4.createCell(1);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("Actual Generation (kWh)");
			
			cell4 = row4.createCell(2);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("Expected Generation (kWh)");
			
			cell4 = row4.createCell(3);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("Modeled Generation (kWh)");
			
			cell4 = row4.createCell(4);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("POA (W/m2)");
			
			cell4 = row4.createCell(5);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("Expected Generation Index (%)");
			
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 7));
			cell4 = row4.createCell(6);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("Modeled Generation Index (%)");
			
			cell4 = row4.createCell(7);
			cell4.setCellStyle(cellStyleB);

			
			
			// Create style row
			Font fontRow = sheet.getWorkbook().createFont();
			fontRow.setFontName("Calibri (Body)");
			fontRow.setFontHeightInPoints((short) 12); // font size
			fontRow.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleItem = sheet.getWorkbook().createCellStyle();
			cellStyleItem.setFont(fontRow);
			cellStyleItem.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleItem.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleItem.setAlignment(HorizontalAlignment.CENTER);
			
			cellStyleItem.setBorderBottom(BorderStyle.THIN);
			cellStyleItem.setBorderTop(BorderStyle.THIN);
			cellStyleItem.setBorderRight(BorderStyle.THIN);
			cellStyleItem.setBorderLeft(BorderStyle.THIN);
			cellStyleItem.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleItem.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleItem.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleItem.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			

			Row row5 = sheet.createRow(6);
			
			for (int i = 0; i < categories.size(); i++) {
				row5 = sheet.createRow(i + 6);
				row5.setHeight((short) 500);
				Cell cell5 = row5.createCell(0);
				cell5.setCellStyle(cellStyleItem);
				cell5.setCellValue((String) categories.get(i));
				
				Cell cell51 = row5.createCell(1);
				cell51.setCellStyle(cellStyleItem);
				cell51.setCellValue(df.format(actualGeneration.get(i)));
				
				
				Cell cell52 = row5.createCell(2);
				cell52.setCellStyle(cellStyleItem);
				cell52.setCellValue(df.format(dataExpectedGeneration.get(i)));
				
				
				Cell cell53 = row5.createCell(3);
				cell53.setCellStyle(cellStyleItem);
				cell53.setCellValue(df.format(dataModeledGeneration.get(i)));
				
				
				Cell cell54 = row5.createCell(4);
				cell54.setCellStyle(cellStyleItem);
				cell54.setCellValue(df2p.format(dataPOA.get(i)));
				
				
				Cell cell55 = row5.createCell(5);
				cell55.setCellStyle(cellStyleItem);
				cell55.setCellValue(df1p.format(dataExpectedGenerationIndex.get(i)));
				
				
				sheet.addMergedRegion(new CellRangeAddress(6 + i, 6 + i, 6, 7));
				Cell cell56 = row5.createCell(6);
				cell56.setCellStyle(cellStyleItem);
				cell56.setCellValue(df1p.format(dataModeledGenerationIndex.get(i)));
				
				Cell cell57 = row5.createCell(7);
				cell57.setCellStyle(cellStyleItem);
			}
			
			
			
			
			Row row6 = sheet.createRow(6 + categories.size());
			row6.setHeight((short) 500);
			Cell cell6 = row6.createCell(0);
			cell6.setCellStyle(cellStyleB);
			cell6.setCellValue("Total");
			
			
			Cell cell61 = row6.createCell(1);
			cell61.setCellStyle(cellStyleB);
			cell61.setCellValue(df.format(totalActualGeneration));
			
			
			Cell cell62 = row6.createCell(2);
			cell62.setCellStyle(cellStyleB);
			cell62.setCellValue(df.format(totalExpectedGeneration));
			
			Cell cell63 = row6.createCell(3);
			cell63.setCellStyle(cellStyleB);
			cell63.setCellValue(df.format(totalModeledGeneration));
			
			Cell cell64 = row6.createCell(4);
			cell64.setCellStyle(cellStyleB);
			cell64.setCellValue("");
			
			Cell cell65 = row6.createCell(5);
			cell65.setCellStyle(cellStyleB);
			cell65.setCellValue(df1p.format(totalExpectedGenerationIndex));
			
			
			sheet.addMergedRegion(new CellRangeAddress(6 + categories.size(), 6 + categories.size(), 6, 7));
			Cell cell66 = row6.createCell(6);
			cell66.setCellStyle(cellStyleB);
			cell66.setCellValue(df1p.format(totalModeledGenerationIndex));
			
			Cell cell67 = row6.createCell(7);
			cell67.setCellStyle(cellStyleB);
			
			
			
			
			// add Note
			Font fontRowBg = sheet.getWorkbook().createFont();
			fontRowBg.setFontName("Calibri (Body)");
			fontRowBg.setFontHeightInPoints((short) 14); // font size
			fontRowBg.setColor(IndexedColors.WHITE.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleBg = sheet.getWorkbook().createCellStyle();
			cellStyleBg.setFont(fontRowBg);
			cellStyleBg.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleBg.setAlignment(HorizontalAlignment.LEFT);
			cellStyleBg.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.index);
			cellStyleBg.setFillPattern(FillPatternType.BIG_SPOTS);
			cellStyleBg.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			
			
			sheet.addMergedRegion(new CellRangeAddress(22 + categories.size(), 22 + categories.size(), 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(23 + categories.size(), 23 + categories.size(), 0, 5));
			sheet.addMergedRegion(new CellRangeAddress(24 + categories.size(), 24 + categories.size(), 0, 5));
			sheet.addMergedRegion(new CellRangeAddress(25 + categories.size(), 25 + categories.size(), 0, 5));
			
			Row row7 = sheet.createRow(22 + categories.size());
			row7.setHeight((short) 600);
			Cell cell7 = row7.createCell(0);
			cell7.setCellStyle(cellStyleBg);
			cell7.setCellValue("Actual, Expected and Modeled Generation");
			
			
			Font fontRowNote = sheet.getWorkbook().createFont();
			fontRowNote.setFontName("Calibri (Body)");
			fontRowNote.setFontHeightInPoints((short) 14); // font size
			fontRowNote.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleNote = sheet.getWorkbook().createCellStyle();
			cellStyleNote.setFont(fontRowNote);
			cellStyleNote.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleNote.setAlignment(HorizontalAlignment.LEFT);
			
			
			Row row8 = sheet.createRow(23 + categories.size());
			row8.setHeight((short) 600);
			Cell cell8 = row8.createCell(0);
			cell8.setCellStyle(cellStyleNote);
			cell8.setCellValue("The Actual Generation is the energy reported by the production meters.");
			
			
			Row row9 = sheet.createRow(24 + categories.size());
			row8.setHeight((short) 600);
			Cell cell9 = row9.createCell(0);
			cell9.setCellStyle(cellStyleNote);
			cell9.setCellValue("The Expected Generation is calculated based on measured irradiance and module temperature.");
			
			
			Row row10 = sheet.createRow(25 + categories.size());
			row10.setHeight((short) 600);
			Cell cell10 = row10.createCell(0);
			cell10.setCellStyle(cellStyleNote);
			cell10.setCellValue("The Modeled Generation is predicted by PVWatts Calculator.");
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	
	/**
	 * @description Sent Mail Annual Production Trend Report
	 * @author long.pham
	 * @since 2023-07-25
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-annual-production-trend-report")
	public Object sentMailAnnualTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			String idSites = obj.getId_sites();
			
			if(idSites == null) {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
			
			
			List<String> ids = new ArrayList<String>(Arrays.asList(idSites.split(",")));
			obj.setIds(ids);
			
			// Get list data site render report
			BuiltInReportService service = new BuiltInReportService();
			List sites  = service.getListSiteInReport(obj);
			obj.setStart_date(obj.getStart_date());
			obj.setEnd_date(obj.getEnd_date());
			if(sites.size() > 0) {
				try (XSSFWorkbook document = new XSSFWorkbook()) {
					int count = 1;
					for (int s = 0; s < sites.size(); s++) {
						SiteEntity siteItem = (SiteEntity) sites.get(s);
						obj.setId_site(siteItem.getId());
						ViewReportEntity dataObj = (ViewReportEntity) service.getAnnuallyBuitInReport(obj);
						List dataExports = dataObj.getDataReports();
						
						if (dataObj != null) {
							XSSFSheet chartSheet = document.createSheet(siteItem.getName());
							XSSFSheet dataSheet = document.createSheet("data"+s);
							document.setSheetHidden( count, true);
							count = count + 2;
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
							anchor.setCol1(7);
							anchor.setRow1(1);

							// Creates a picture
							Picture pict = drawing.createPicture(anchor, pictureIdx);
							// Reset the image to the original size
							pict.resize(1.0, 3.8);
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date convertedDate = dateFormat.parse(obj.getEnd_date());
							Date startDate = dateFormat.parse(obj.getStart_date());
							dataObj.setStart_date( new SimpleDateFormat("MM/dd/yyyy").format(startDate) );
							dataObj.setEnd_date( new SimpleDateFormat("MM/dd/yyyy").format(convertedDate) );
							dataObj.setSite_name(siteItem.getName());
							
							
							ArrayList<String> categories = new ArrayList<String>();
							ArrayList<Double> actualGeneration = new ArrayList<Double>();
							ArrayList<Double> dataExpectedGeneration = new ArrayList<Double>();
							ArrayList<Double> dataModeledGeneration = new ArrayList<Double>();
							ArrayList<Double> dataPOA = new ArrayList<Double>();
							ArrayList<Double> dataExpectedGenerationIndex = new ArrayList<Double>();
							ArrayList<Double> dataModeledGenerationIndex = new ArrayList<Double>();
						
							SimpleDateFormat dateFormatCategories = new SimpleDateFormat("MMM-yyyy");
							double totalActualGeneration = 0;
							double totalExpectedGeneration = 0;
							double totalModeledGeneration = 0;
							double totalExpectedGenerationIndex = 0;
							double totalModeledGenerationIndex = 0;
							
							for (int i = 0; i < 12; i++) {
								Calendar c = Calendar.getInstance();
								c.setTime(startDate);
								c.add(Calendar.MONTH, i);
								categories.add(dateFormatCategories.format(c.getTime()));
								Double v = 0d;
								Double ex = 0d;
								Double mo = 0d;
								Double poa = 0d;
								Double exi = 0d;
								Double moi = 0d;
								
								if(dataExports != null && dataExports.size() > 0) {
									for( int j = 0; j < dataExports.size(); j++){
										Map<String, Object> item = (Map<String, Object>) dataExports.get(j);
										String date = (String) item.get("categories_time");

										if(date.equals(dateFormatCategories.format(c.getTime()) )) {
											v = (Double)item.get("ActualGeneration");
											ex = (Double)item.get("ExpectedGeneration");
											mo = (Double)item.get("ModeledGeneration");
											poa = (Double)item.get("POA");
											exi = (Double)item.get("ExpectedGenerationIndex");
											moi = (Double)item.get("ModeledGenerationIndex");
																			
											totalActualGeneration = totalActualGeneration + v;
											totalExpectedGeneration =  totalExpectedGeneration + ex;
											totalModeledGeneration = totalModeledGeneration + mo;
										}
									}
								}
								
								
								actualGeneration.add(v);
								dataExpectedGeneration.add(ex);
								dataModeledGeneration.add(mo);
								
								dataPOA.add(poa);
								dataExpectedGenerationIndex.add(exi);
								dataModeledGenerationIndex.add(moi);
							}
							
							
							if(totalActualGeneration > 0 && totalExpectedGeneration > 0) {
								totalExpectedGenerationIndex = (totalActualGeneration / totalExpectedGeneration) * 100;
							}
							if(totalActualGeneration > 0 && totalModeledGeneration > 0) {
								totalModeledGenerationIndex = (totalActualGeneration / totalModeledGeneration) * 100;
							}
							
							writeHeaderAnnuallyReport(chartSheet, 0, categories, actualGeneration, dataObj, dataExpectedGeneration, dataModeledGeneration, dataPOA, dataExpectedGenerationIndex, dataModeledGenerationIndex, totalActualGeneration, totalExpectedGeneration, totalModeledGeneration, totalExpectedGenerationIndex, totalModeledGenerationIndex);
							// create the data
							if(categories.size() > 0) {
								for(int i = 0; i< categories.size(); i++) {
									dataSheet.createRow(i).createCell(0).setCellValue((String) categories.get(i));
									dataSheet.getRow(i).createCell(1).setCellValue(actualGeneration.get(i));
									dataSheet.getRow(i).createCell(2).setCellValue(dataExpectedGeneration.get(i));
									dataSheet.getRow(i).createCell(3).setCellValue(dataModeledGeneration.get(i));
									dataSheet.getRow(i).createCell(4).setCellValue(dataPOA.get(i));
									dataSheet.getRow(i).createCell(5).setCellValue(dataExpectedGenerationIndex.get(i));
									dataSheet.getRow(i).createCell(6).setCellValue(dataModeledGenerationIndex.get(i));
								}
							}
							
							
							XSSFClientAnchor anchor1;
							XSSFChart chart;
							// create the chart 
						    XSSFDrawing drawing1 = chartSheet.createDrawingPatriarch();
							
							//====== first line chart============================================================
							anchor1 = drawing1.createAnchor(0, 0, 0, 0, 0, 20, 8, 32);
							chart = drawing1.createChart(anchor1);
							chart.setTitleText("Performance");
							chart.setTitleOverlay(false);
		
							// create data sources
							int numOfPoints = categories.size();
							// dummy 0-values for the pad data source
							Double[] dummyValuesForPad = new Double[numOfPoints];
							for (int i = 0; i < numOfPoints; i++) {
								dummyValuesForPad[i] = 0d;
							}
							XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 0, 0));
							XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 1, 1));
							XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 2, 2));
							XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 3, 3));
							
							XDDFNumericalDataSource<Double> valuesData4 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 5, 5));
							
							XDDFNumericalDataSource<Double> valuesData5 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 6, 6));
							
							for (int i = 0; i < numOfPoints; i++) {
								XSSFRow row = dataSheet.getRow(i);
								if (row == null)
									row = dataSheet.createRow(i);
								XSSFCell cell = row.createCell(255);
								cell.setCellValue(0);
							}
							
							
							
							// first bar chart
							XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
							XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
							leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
							leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
							leftAxis.setTitle("kWh");
		
							XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
							XDDFBarChartData bar = (XDDFBarChartData) data;
							bar.setBarDirection(BarDirection.COL);
		
							CTPlotArea plotArea = chart.getCTChart().getPlotArea();
							plotArea.getValAxArray()[0].addNewMajorGridlines();
							
		
							XDDFChartData.Series series = data.addSeries(categoriesData, valuesData1);
							series.setTitle("Actual Generation (kWh)",
									new CellReference(chartSheet.getSheetName(), 5, 1, true, true));
							
							
							
							series = data.addSeries(categoriesData, valuesData2);
							series.setTitle("Expected Generation (kWh)",
									new CellReference(chartSheet.getSheetName(), 5, 2, true, true));
							
							
							
							series = data.addSeries(categoriesData, valuesData3);
							series.setTitle("Modeled Generation (kWh)",
									new CellReference(chartSheet.getSheetName(), 5, 3, true, true));
							
						
							chart.plot(data);
							// set bar colors
							solidFillSeries(data, 0, PresetColor.STEEL_BLUE);
							solidFillSeries(data, 1, PresetColor.LIGHT_STEEL_BLUE);
							
		
							// second bar chart
							// bottom axis must be there but must not be visible
							bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
							bottomAxis.setVisible(false);
		
							XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
							rightAxis.setCrosses(AxisCrosses.MAX);
							rightAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
							rightAxis.setTitle("%"); // comment
		
							// set correct cross axis
							bottomAxis.crossAxis(rightAxis);
							rightAxis.crossAxis(bottomAxis);
							
		
							data = chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
							bar.setBarDirection(BarDirection.COL);
		
		
						    
							series = data.addSeries(categoriesData, valuesData4);
							series.setTitle("Expected Generation Index (%)",
									new CellReference(chartSheet.getSheetName(), 5, 5, true, true));
							
							series = data.addSeries(categoriesData, valuesData5);
							series.setTitle("Expected Generation Index (%)",
									new CellReference(chartSheet.getSheetName(), 5, 6, true, true));
							
							chart.plot(data);
		
		
							// this must occur after the call to chart.plot above
							CTPlotArea plotAreaLine = chart.getCTChart().getPlotArea();
						    for (CTLineChart ch : plotAreaLine.getLineChartList()) {
						        for (CTLineSer ser : ch.getSerList()) {
						            CTBoolean ctBool = CTBoolean.Factory.newInstance();
						            ctBool.setVal(false);
						            ser.setSmooth(ctBool);
						            ser.addNewMarker().addNewSymbol().setVal(STMarkerStyle.CIRCLE);
						        }
						    }
							
							// set legend
							XDDFChartLegend legend = chart.getOrAddLegend();
							legend.setPosition(LegendPosition.BOTTOM);
						}
						
					}
					
					// Write the output to a file
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String dir = uploadRootPath() + "/"
							+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
					String fileName = dir + "/Annual-production-trend-report-" + timeStamp + ".xlsx";
					
					try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
						document.write(fileOut);
						String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
								Constants.mailFromContact);

						String msgTemplate = Constants.getMailTempleteByState(18);
						String body = String.format(msgTemplate, "Customer", "ANNUAL PRODUCTION TREND REPORT (MONTHLY INTERVAL) ", "", "");
						String mailTo = obj.getSubscribers();
						String subject = Constants.getMailSubjectByState(18);

						String tags = "report_annual";
						String fromName = "NEXT WAVE ENERGY MONITORING INC";
						boolean flagSent = SendMail.SendGmailTLSAttachmentattachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
						if (!flagSent) {
							throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
						}
					}
					
					
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				}
			} else {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
			
			
			
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	
	
	
	
	
	/**
	 * @description Sent Mail monthly Production Trend Report
	 * @author long.pham
	 * @since 2023-07-25
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/monthly-production-trend-report")
	public Object monthlyProductionTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			// Get list data site render report
			BuiltInReportService service = new BuiltInReportService();
			ViewReportEntity dataObj = (ViewReportEntity) service.getMonthlyTrendBuitInReport(obj);	
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, dataObj, 1);

		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	
	
	/**
	 * @description Sent Mail monthly portfolio Production Report
	 * @author long.pham
	 * @since 2023-07-25
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-monthly-production-trend-report")
	public Object sentMailMonthlyTrendReport(@RequestBody ViewReportEntity obj) {
		try {

			String idSites = obj.getId_sites();
			
			if(idSites == null) {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
			
			List<String> ids = new ArrayList<String>(Arrays.asList(idSites.split(",")));
			obj.setIds(ids);
			
			// Get list data site render report
			BuiltInReportService service = new BuiltInReportService();
			List sites  = service.getListSiteInReport(obj);
			
			if(sites.size() > 0) {
				try (XSSFWorkbook document = new XSSFWorkbook()) {
					int count = 1;
					for (int s = 0; s < sites.size(); s++) {
						SiteEntity siteItem = (SiteEntity) sites.get(s);
						obj.setId_site(siteItem.getId());
						ViewReportEntity dataObj = (ViewReportEntity) service.getMonthlyTrendBuitInReport(obj);
						dataObj.setSite_name(siteItem.getName());
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date convertedDate = dateFormat.parse(obj.getEnd_date());
						Date start = dateFormat.parse(obj.getStart_date());
						String lastOfMonth = new SimpleDateFormat("dd").format(convertedDate);
						
						dataObj.setStart_date( new SimpleDateFormat("MM/dd/yyyy").format(start) );
						dataObj.setEnd_date( new SimpleDateFormat("MM/dd/yyyy").format(convertedDate) );
						dataObj.setData_intervals(obj.getData_intervals());
						
						List dataExports = dataObj.getDataReports();
						
						if (dataObj != null) {
							XSSFSheet chartSheet = document.createSheet(siteItem.getName());
							XSSFSheet dataSheet = document.createSheet("data"+s);
							document.setSheetHidden( count, true);
							count = count + 2;
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
							anchor.setCol1(4);
							anchor.setRow1(1);

							// Creates a picture
							Picture pict = drawing.createPicture(anchor, pictureIdx);
							// Reset the image to the original size
							pict.resize(1.0, 3.8);

							ArrayList<String> categories = new ArrayList<String>();
							SimpleDateFormat dateFormatCategories = new SimpleDateFormat("MM/dd/yyyy HH:mm:00");
							
							if(Integer.parseInt(lastOfMonth) > 0) {
								for(int i = 0; i < Integer.parseInt(lastOfMonth); i++) {
									Date startDate = dateFormat.parse(obj.getStart_date());
									Calendar newCalendar = Calendar.getInstance();
									newCalendar.setTime(startDate);
									newCalendar.add(Calendar.DATE, i);
									
									if(obj.getData_intervals() == 2) {
										for(int j = 0; j < 96; j++) {
											Calendar itemCalendar = Calendar.getInstance();
											itemCalendar.setTime(newCalendar.getTime());
											itemCalendar.add(Calendar.MINUTE, j * 15);
											categories.add(dateFormatCategories.format(itemCalendar.getTime()));
										}
									} else if(obj.getData_intervals() == 1) {
										for(int j = 0; j < 288; j++) {
											Calendar itemCalendar = Calendar.getInstance();
											itemCalendar.setTime(newCalendar.getTime());
											itemCalendar.add(Calendar.MINUTE, j * 5);
											categories.add(dateFormatCategories.format(itemCalendar.getTime()));
										}
									}
								}
							}
							
							
							ArrayList<Double> dataGeneration = new ArrayList<Double>();
							for(int i = 0; i < categories.size(); i++) {
								if(dataExports.size() > 0) {
									Double dataMonthly = 0.0;
									for( int j = 0; j < dataExports.size(); j++){
										Map<String, Object> item = (Map<String, Object>) dataExports.get(j);
										String date = (String) item.get("time_full");
										if(date.equals( categories.get(i))) {
											dataMonthly = (Double) item.get("monthlyProduction");
										}
									}
									
									dataGeneration.add(dataMonthly);
								} else {
									dataGeneration.add(0.0);
								}
							}
							
							writeHeaderMonthTrendReport(chartSheet, 0, categories, dataGeneration, dataObj);
						}
						
					}
					
					// Write the output to a file
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String dir = uploadRootPath() + "/"
							+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
					String fileName = dir + "/Monthly-portfolio-production-report-" + timeStamp + ".xlsx";
					
					try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
						document.write(fileOut);
						String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
								Constants.mailFromContact);

						String msgTemplate = Constants.getMailTempleteByState(19);
						String body = String.format(msgTemplate, "Customer", "Monthly Portfolio Production Report", "", "");
						String mailTo = obj.getSubscribers();
						String subject = Constants.getMailSubjectByState(19);

						String tags = "report_monthly_portfolio";
						String fromName = "NEXT WAVE ENERGY MONITORING INC";
						boolean flagSent = SendMail.SendGmailTLSAttachmentattachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
						if (!flagSent) {
							throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
						}
					}
					
					
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				}
			} else {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
			
			
			
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	
	
	// Write header with format monthly portfolio production report 
	private static void writeHeaderMonthTrendReport(Sheet sheet, int rowIndex, ArrayList categories, ArrayList dataGeneration, ViewReportEntity dataObj) {
		try {
			DecimalFormat df = new DecimalFormat("###,###");
			DecimalFormat dfp = new DecimalFormat("###,###.0");
			// create CellStyle
			Font fonDef = sheet.getWorkbook().createFont();
			fonDef.setFontName("Calibri (Body)");
			fonDef.setFontHeightInPoints((short) 12); // font size
			
			CellStyle cellStyle = createStyleForHeader(sheet);
			cellStyle.setFont(fonDef);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			
			
			
			// create CellStyle title
			CellStyle cellStyleTitle = createStyleForHeader(sheet);
			cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleTitle.setAlignment(HorizontalAlignment.LEFT);
			
			cellStyleTitle.setBorderBottom(BorderStyle.THIN);
			cellStyleTitle.setBorderTop(BorderStyle.THIN);
			cellStyleTitle.setBorderRight(BorderStyle.THIN);
			cellStyleTitle.setBorderLeft(BorderStyle.THIN);
			cellStyleTitle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleTitle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleTitle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleTitle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			

			// Create font
			Font fontBold = sheet.getWorkbook().createFont();
			fontBold.setFontName("Calibri (Body)");
			fontBold.setBold(true);
			fontBold.setFontHeightInPoints((short) 12); // font size
			CellStyle cellStyleFontBold = sheet.getWorkbook().createCellStyle();
			cellStyleFontBold.setFont(fontBold);
			cellStyleFontBold.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleFontBold.setAlignment(HorizontalAlignment.CENTER);
			cellStyleFontBold.setBorderBottom(BorderStyle.THIN);
			cellStyleFontBold.setBorderTop(BorderStyle.THIN);
			cellStyleFontBold.setBorderRight(BorderStyle.THIN);
			cellStyleFontBold.setBorderLeft(BorderStyle.THIN);
			cellStyleFontBold.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleFontBold.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleFontBold.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleFontBold.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 30 * 256);
			sheet.setColumnWidth(1, 30 * 256);
			sheet.setColumnWidth(2, 30 * 256);
			
			sheet.setDefaultRowHeight((short) 500);
			sheet.setDisplayGridlines(false);
			
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));

			Row row1 = sheet.createRow(1);
			row1.setHeight((short) 600);
			Cell cell = row1.createCell(0);

			// Create font
			Font font = sheet.getWorkbook().createFont();
			font.setFontName("Calibri (Body)");
			font.setBold(true);
			font.setFontHeightInPoints((short) 18); // font size
			font.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleHeader = sheet.getWorkbook().createCellStyle();
			cellStyleHeader.setFont(font);
			cellStyleHeader.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
			
			cell.setCellStyle(cellStyleHeader);
			if(dataObj.getData_intervals() == 1) {
				cell.setCellValue("Monthly Portfolio Production Report (5min Interval)");
			} else if(dataObj.getData_intervals() == 2) {
				cell.setCellValue("Monthly Portfolio Production Report (15min Interval)");
			}
							
			// Create font
			Font font1 = sheet.getWorkbook().createFont();
			font1.setFontName("Calibri (Body)");
			font1.setBold(true);
			font1.setFontHeightInPoints((short) 14); // font size
			font1.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleSubTitle = sheet.getWorkbook().createCellStyle();
			cellStyleSubTitle.setFont(font1);
			cellStyleSubTitle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleSubTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleSubTitle.setAlignment(HorizontalAlignment.CENTER);
						
			Row row2 = sheet.createRow(2);
			row2.setHeight((short) 500);
			Cell cell2 = row2.createCell(0);
			cell2.setCellStyle(cellStyleSubTitle);
			cell2.setCellValue(dataObj.getSite_name());
			
			
			
			Font font3 = sheet.getWorkbook().createFont();
			font3.setFontName("Calibri (Body)");
			font3.setBold(false);
			font3.setFontHeightInPoints((short) 14); // font size
			font3.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleDate = sheet.getWorkbook().createCellStyle();
			cellStyleDate.setFont(font3);
			cellStyleDate.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleDate.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleDate.setAlignment(HorizontalAlignment.CENTER);
			Row row3 = sheet.createRow(3);
			row3.setHeight((short) 500);
			Cell cell3 = row3.createCell(0);
			cell3.setCellStyle(cellStyleDate);
			
			SimpleDateFormat dt = new SimpleDateFormat("mm/dd/yyyy"); 
			Date startDate = dt.parse(dataObj.getStart_date());
			Date endDate = dt.parse(dataObj.getEnd_date()); 
			cell3.setCellValue(dt.format(startDate) + " - " +  dt.format(endDate));
			
			
			
			Font fonDefB = sheet.getWorkbook().createFont();
			fonDefB.setFontName("Calibri (Body)");
			fonDefB.setBold(true);
			fonDefB.setFontHeightInPoints((short) 12); // font size
			
			CellStyle cellStyleB = createStyleForHeader(sheet);
			cellStyleB.setFont(fonDefB);
			cellStyleB.setWrapText(true);
			cellStyleB.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleB.setAlignment(HorizontalAlignment.CENTER);
			cellStyleB.setBorderBottom(BorderStyle.THIN);
			cellStyleB.setBorderTop(BorderStyle.THIN);
			cellStyleB.setBorderRight(BorderStyle.THIN);
			cellStyleB.setBorderLeft(BorderStyle.THIN);
			cellStyleB.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleB.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleB.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleB.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			
			Row row4 = sheet.createRow(5);
			row4.setHeight((short) 500);
			Cell cell4 = row4.createCell(0);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 2));
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("Timestamp");
			
			cell4 = row4.createCell(1);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("");
			
			cell4 = row4.createCell(2);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("");

			cell4 = row4.createCell(3);
			cell4.setCellStyle(cellStyleB);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 3, 4));
			cell4.setCellValue("Monthly Production (kWh)");
			
			cell4 = row4.createCell(4);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("");
			
			
			// Create style row
			Font fontRow = sheet.getWorkbook().createFont();
			fontRow.setFontName("Calibri (Body)");
			fontRow.setFontHeightInPoints((short) 12); // font size
			fontRow.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleItem = sheet.getWorkbook().createCellStyle();
			cellStyleItem.setFont(fontRow);
			cellStyleItem.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleItem.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleItem.setAlignment(HorizontalAlignment.CENTER);
			
			cellStyleItem.setBorderBottom(BorderStyle.THIN);
			cellStyleItem.setBorderTop(BorderStyle.THIN);
			cellStyleItem.setBorderRight(BorderStyle.THIN);
			cellStyleItem.setBorderLeft(BorderStyle.THIN);
			cellStyleItem.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleItem.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleItem.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleItem.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			

			Row row5 = sheet.createRow(6);
			for (int i = 0; i < categories.size(); i++) {
				row5 = sheet.createRow(i + 6);
				row5.setHeight((short) 500);
				sheet.addMergedRegion(new CellRangeAddress(i + 6, i + 6, 0, 2));
				Cell cell5 = row5.createCell(0);
				cell5.setCellStyle(cellStyleItem);
				cell5.setCellValue((String) categories.get(i));
				
				cell5 = row5.createCell(1);
				cell5.setCellStyle(cellStyleItem);
				cell5.setCellValue("");
				
				cell5 = row5.createCell(2);
				cell5.setCellStyle(cellStyleItem);
				cell5.setCellValue("");
				
				
				Cell cell51 = row5.createCell(3);
				sheet.addMergedRegion(new CellRangeAddress(i + 6, i + 6, 3, 4));
				cell51.setCellStyle(cellStyleItem);
				cell51.setCellValue( dataGeneration.get(i) == null ? "0.0" : df.format(dataGeneration.get(i)));
				
				cell51 = row5.createCell(4);
				cell51.setCellStyle(cellStyleItem);
				cell51.setCellValue("");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}
		
		
	/**
	 * @description get weekly production trend report 
	 * @author Hung.Bui
	 * @since 2023-07-31
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/weekly-production-trend-report")
	public Object weeklyProductionTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			BuiltInReportService service = new BuiltInReportService();

			ViewReportEntity dataObj = (ViewReportEntity) service.getWeeklyBuiltInReport(obj);

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
	
	/**
	 * @description Sent Mail Weekly Production Trend Report
	 * @author Hung.Bui
	 * @since 2023-07-31
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-weekly-production-trend-report")
	public Object sentMailWeeklyTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			String idSites = obj.getId_sites();
			
			if(idSites == null) {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
			
			
			List<String> ids = new ArrayList<String>(Arrays.asList(idSites.split(",")));
			obj.setIds(ids);
			
			// Get list data site render report
			BuiltInReportService service = new BuiltInReportService();
			List sites  = service.getListSiteInReport(obj);
			obj.setStart_date(obj.getStart_date());
			obj.setEnd_date(obj.getEnd_date());
			if(sites.size() > 0) {
				try (XSSFWorkbook document = new XSSFWorkbook()) {
					int count = 1;
					for (int s = 0; s < sites.size(); s++) {
						SiteEntity siteItem = (SiteEntity) sites.get(s);
						obj.setId_site(siteItem.getId());
						ViewReportEntity dataObj = (ViewReportEntity) service.getWeeklyBuiltInReport(obj);
						List<WeeklyDateEntity> dataExports = dataObj.getDataReports();
						
						if (dataObj != null) {
							XSSFSheet chartSheet = document.createSheet(siteItem.getName());
							XSSFSheet dataSheet = document.createSheet("data"+s);
							document.setSheetHidden( count, true);
							count = count + 2;
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
							anchor.setCol1(7);
							anchor.setRow1(1);

							// Creates a picture
							Picture pict = drawing.createPicture(anchor, pictureIdx);
							// Reset the image to the original size
							pict.resize(1.0, 3.8);
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date convertedDate = dateFormat.parse(obj.getEnd_date());
							Date startDate = dateFormat.parse(obj.getStart_date());
							dataObj.setStart_date( new SimpleDateFormat("MM/dd/yyyy").format(startDate) );
							dataObj.setEnd_date( new SimpleDateFormat("MM/dd/yyyy").format(convertedDate) );
							dataObj.setSite_name(siteItem.getName());
							
							
							ArrayList<String> categories = new ArrayList<String>();
							ArrayList<Double> dataActualGeneration = new ArrayList<Double>();
							ArrayList<Double> dataExpectedGeneration = new ArrayList<Double>();
							ArrayList<Double> dataModeledGeneration = new ArrayList<Double>();
							ArrayList<Double> dataPOA = new ArrayList<Double>();
							ArrayList<Double> dataExpectedGenerationIndex = new ArrayList<Double>();
							ArrayList<Double> dataModeledGenerationIndex = new ArrayList<Double>();
						
							double totalActualGeneration = 0;
							double totalExpectedGeneration = 0;
							double totalModeledGeneration = 0;
							double totalExpectedGenerationIndex = 0;
							double totalModeledGenerationIndex = 0;
							
							if(dataExports != null && dataExports.size() > 0) {
								for( int i = 0; i < dataExports.size(); i++){
									WeeklyDateEntity item = (WeeklyDateEntity) dataExports.get(i);

									categories.add(item.getCategories_time());
									Double v = (Double) item.getActualGeneration();
									Double ex = (Double) item.getExpectedGeneration();
									Double mo = (Double) item.getModeledGeneration();
									Double poa = (Double) item.getPoa();
									Double exi = (Double) item.getExpectedGenerationIndex();
									Double moi = (Double) item.getModeledGenerationIndex();
																	
									totalActualGeneration = totalActualGeneration + v;
									totalExpectedGeneration =  totalExpectedGeneration + ex;
									totalModeledGeneration = totalModeledGeneration + mo;
									
									dataActualGeneration.add(v);
									dataExpectedGeneration.add(ex);
									dataModeledGeneration.add(mo);
									
									dataPOA.add(poa);
									dataExpectedGenerationIndex.add(exi);
									dataModeledGenerationIndex.add(moi);
								}
							}
							
							if(totalActualGeneration > 0 && totalExpectedGeneration > 0) {
								totalExpectedGenerationIndex = (totalActualGeneration / totalExpectedGeneration) * 100;
							}
							if(totalActualGeneration > 0 && totalModeledGeneration > 0) {
								totalModeledGenerationIndex = (totalActualGeneration / totalModeledGeneration) * 100;
							}
							
							writeHeaderWeeklyReport(chartSheet, 0, categories, dataActualGeneration, dataObj, dataExpectedGeneration, dataModeledGeneration, dataPOA, dataExpectedGenerationIndex, dataModeledGenerationIndex, totalActualGeneration, totalExpectedGeneration, totalModeledGeneration, totalExpectedGenerationIndex, totalModeledGenerationIndex);
							// create the data
							if(categories.size() > 0) {
								for(int i = 0; i< categories.size(); i++) {
									dataSheet.createRow(i).createCell(0).setCellValue((String) categories.get(i));
									dataSheet.getRow(i).createCell(1).setCellValue(dataActualGeneration.get(i));
									dataSheet.getRow(i).createCell(2).setCellValue(dataExpectedGeneration.get(i));
									dataSheet.getRow(i).createCell(3).setCellValue(dataModeledGeneration.get(i));
									dataSheet.getRow(i).createCell(4).setCellValue(dataPOA.get(i));
									dataSheet.getRow(i).createCell(5).setCellValue(dataExpectedGenerationIndex.get(i));
									dataSheet.getRow(i).createCell(6).setCellValue(dataModeledGenerationIndex.get(i));
								}
							}
							
							
							XSSFClientAnchor anchor1;
							XSSFChart chart;
							// create the chart 
						    XSSFDrawing drawing1 = chartSheet.createDrawingPatriarch();
							
							//====== first line chart============================================================
							anchor1 = drawing1.createAnchor(0, 0, 0, 0, 0, 15, 8, 27);
							chart = drawing1.createChart(anchor1);
							chart.setTitleText("Performance");
							chart.setTitleOverlay(false);
		
							// create data sources
							int numOfPoints = categories.size();
							// dummy 0-values for the pad data source
							Double[] dummyValuesForPad = new Double[numOfPoints];
							for (int i = 0; i < numOfPoints; i++) {
								dummyValuesForPad[i] = 0d;
							}
							XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 0, 0));
							XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 1, 1));
							XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 2, 2));
							XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 3, 3));
							
							XDDFNumericalDataSource<Double> valuesData4 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 5, 5));
							
							XDDFNumericalDataSource<Double> valuesData5 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 6, 6));
							
							for (int i = 0; i < numOfPoints; i++) {
								XSSFRow row = dataSheet.getRow(i);
								if (row == null)
									row = dataSheet.createRow(i);
								XSSFCell cell = row.createCell(255);
								cell.setCellValue(0);
							}
							
							
							
							// first bar chart
							XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
							XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
							leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
							leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
							leftAxis.setTitle("kWh");
		
							XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
							XDDFBarChartData bar = (XDDFBarChartData) data;
							bar.setBarDirection(BarDirection.COL);
		
							CTPlotArea plotArea = chart.getCTChart().getPlotArea();
							plotArea.getValAxArray()[0].addNewMajorGridlines();
							
		
							XDDFChartData.Series series = data.addSeries(categoriesData, valuesData1);
							series.setTitle("Actual Generation (kWh)",
									new CellReference(chartSheet.getSheetName(), 5, 1, true, true));
							
							
							
							series = data.addSeries(categoriesData, valuesData2);
							series.setTitle("Expected Generation (kWh)",
									new CellReference(chartSheet.getSheetName(), 5, 2, true, true));
							
							
							
							series = data.addSeries(categoriesData, valuesData3);
							series.setTitle("Modeled Generation (kWh)",
									new CellReference(chartSheet.getSheetName(), 5, 3, true, true));
							
						
							chart.plot(data);
							// set bar colors
							solidFillSeries(data, 0, PresetColor.STEEL_BLUE);
							solidFillSeries(data, 1, PresetColor.LIGHT_STEEL_BLUE);
							
		
							// second bar chart
							// bottom axis must be there but must not be visible
							bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
							bottomAxis.setVisible(false);
		
							XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
							rightAxis.setCrosses(AxisCrosses.MAX);
							rightAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
							rightAxis.setTitle("%"); // comment
		
							// set correct cross axis
							bottomAxis.crossAxis(rightAxis);
							rightAxis.crossAxis(bottomAxis);
							
		
							data = chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
							bar.setBarDirection(BarDirection.COL);
		
		
						    
							series = data.addSeries(categoriesData, valuesData4);
							series.setTitle("Expected Generation Index (%)",
									new CellReference(chartSheet.getSheetName(), 5, 5, true, true));
							
							series = data.addSeries(categoriesData, valuesData5);
							series.setTitle("Expected Generation Index (%)",
									new CellReference(chartSheet.getSheetName(), 5, 6, true, true));
							
							chart.plot(data);
		
		
							// this must occur after the call to chart.plot above
							CTPlotArea plotAreaLine = chart.getCTChart().getPlotArea();
						    for (CTLineChart ch : plotAreaLine.getLineChartList()) {
						        for (CTLineSer ser : ch.getSerList()) {
						            CTBoolean ctBool = CTBoolean.Factory.newInstance();
						            ctBool.setVal(false);
						            ser.setSmooth(ctBool);
						            ser.addNewMarker().addNewSymbol().setVal(STMarkerStyle.CIRCLE);
						        }
						    }
							
							// set legend
							XDDFChartLegend legend = chart.getOrAddLegend();
							legend.setPosition(LegendPosition.BOTTOM);
						}
						
					}
					
					// Write the output to a file
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String dir = uploadRootPath() + "/"
							+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
					String fileName = dir + "/Weekly-production-trend-report-daily-interval-" + timeStamp + ".xlsx";
					
					try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
						document.write(fileOut);
						String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
								Constants.mailFromContact);

						String msgTemplate = Constants.getMailTempleteByState(18);
						String body = String.format(msgTemplate, "Customer", "WEEKLY PRODUCTION TREND REPORT (DAILY INTERVAL) ", "", "");
						String mailTo = obj.getSubscribers();
						String subject = Constants.getMailSubjectByState(18);

						String tags = "report_weekly";
						String fromName = "NEXT WAVE ENERGY MONITORING INC";
						boolean flagSent = SendMail.SendGmailTLSAttachmentattachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
						if (!flagSent) {
							throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
						}
					}
					
					
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				}
			} else {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
			
			
			
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	// Write header with format
		private static void writeHeaderWeeklyReport(Sheet sheet, int rowIndex, ArrayList categories, ArrayList actualGeneration, ViewReportEntity dataObj, ArrayList dataExpectedGeneration, ArrayList dataModeledGeneration, ArrayList dataPOA, ArrayList dataExpectedGenerationIndex, ArrayList dataModeledGenerationIndex, double totalActualGeneration, double totalExpectedGeneration, double totalModeledGeneration, double totalExpectedGenerationIndex, double totalModeledGenerationIndex) {
			try {
				DecimalFormat df = new DecimalFormat("###,###");
				DecimalFormat df1p = new DecimalFormat("###,###.#");
				DecimalFormat df2p = new DecimalFormat("###,###.##");
				// create CellStyle
				Font fonDef = sheet.getWorkbook().createFont();
				fonDef.setFontName("Calibri (Body)");
				fonDef.setFontHeightInPoints((short) 12); // font size
				
				CellStyle cellStyle = createStyleForHeader(sheet);
				cellStyle.setFont(fonDef);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				
				
				
				// create CellStyle title
				CellStyle cellStyleTitle = createStyleForHeader(sheet);
				cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleTitle.setAlignment(HorizontalAlignment.LEFT);
				
				cellStyleTitle.setBorderBottom(BorderStyle.THIN);
				cellStyleTitle.setBorderTop(BorderStyle.THIN);
				cellStyleTitle.setBorderRight(BorderStyle.THIN);
				cellStyleTitle.setBorderLeft(BorderStyle.THIN);
				cellStyleTitle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleTitle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleTitle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleTitle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				

				// Create font
				Font fontBold = sheet.getWorkbook().createFont();
				fontBold.setFontName("Calibri (Body)");
				fontBold.setBold(true);
				fontBold.setFontHeightInPoints((short) 12); // font size
				CellStyle cellStyleFontBold = sheet.getWorkbook().createCellStyle();
				cellStyleFontBold.setFont(fontBold);
				cellStyleFontBold.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleFontBold.setAlignment(HorizontalAlignment.CENTER);
				cellStyleFontBold.setBorderBottom(BorderStyle.THIN);
				cellStyleFontBold.setBorderTop(BorderStyle.THIN);
				cellStyleFontBold.setBorderRight(BorderStyle.THIN);
				cellStyleFontBold.setBorderLeft(BorderStyle.THIN);
				cellStyleFontBold.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleFontBold.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleFontBold.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleFontBold.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

				sheet.setDefaultColumnWidth(16);
				sheet.setColumnWidth(0, 30 * 256);
				sheet.setColumnWidth(1, 30 * 256);
				sheet.setColumnWidth(2, 30 * 256);
				sheet.setColumnWidth(3, 30 * 256);
				sheet.setColumnWidth(4, 30 * 256);
				sheet.setColumnWidth(5, 30 * 256);
				
				sheet.setDefaultRowHeight((short) 500);
				sheet.setDisplayGridlines(false);
				
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 5));

				Row row1 = sheet.createRow(1);
				row1.setHeight((short) 600);
				Cell cell = row1.createCell(0);

				// Create font
				Font font = sheet.getWorkbook().createFont();
				font.setFontName("Calibri (Body)");
				font.setBold(true);
				font.setFontHeightInPoints((short) 18); // font size
				font.setColor(IndexedColors.BLACK.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleHeader = sheet.getWorkbook().createCellStyle();
				cellStyleHeader.setFont(font);
				cellStyleHeader.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
				
				cell.setCellStyle(cellStyleHeader);
				cell.setCellValue("WEEKLY PRODUCTION TREND REPORT (DAILY INTERVAL)");
				
				
				
				// Create font
				Font font1 = sheet.getWorkbook().createFont();
				font1.setFontName("Calibri (Body)");
				font1.setBold(true);
				font1.setFontHeightInPoints((short) 14); // font size
				font1.setColor(IndexedColors.BLACK.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleSubTitle = sheet.getWorkbook().createCellStyle();
				cellStyleSubTitle.setFont(font1);
				cellStyleSubTitle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				cellStyleSubTitle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleSubTitle.setAlignment(HorizontalAlignment.CENTER);
							
				Row row2 = sheet.createRow(2);
				row2.setHeight((short) 500);
				Cell cell2 = row2.createCell(0);
				cell2.setCellStyle(cellStyleSubTitle);
				cell2.setCellValue(dataObj.getSite_name().toUpperCase());
				
				
				
				Font font3 = sheet.getWorkbook().createFont();
				font3.setFontName("Calibri (Body)");
				font3.setBold(false);
				font3.setFontHeightInPoints((short) 14); // font size
				font3.setColor(IndexedColors.BLACK.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleDate = sheet.getWorkbook().createCellStyle();
				cellStyleDate.setFont(font3);
				cellStyleDate.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				cellStyleDate.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleDate.setAlignment(HorizontalAlignment.CENTER);
				Row row3 = sheet.createRow(3);
				row3.setHeight((short) 500);
				Cell cell3 = row3.createCell(0);
				cell3.setCellStyle(cellStyleDate);
				
				SimpleDateFormat dt = new SimpleDateFormat("mm/dd/yyyy"); 
				Date startDate = dt.parse(dataObj.getStart_date());
				Date endDate = dt.parse(dataObj.getEnd_date()); 
				cell3.setCellValue(dt.format(startDate) + " - " +  dt.format(endDate));
				
				
				
				Font fonDefB = sheet.getWorkbook().createFont();
				fonDefB.setFontName("Calibri (Body)");
				fonDefB.setBold(true);
				fonDefB.setFontHeightInPoints((short) 12); // font size
				
				CellStyle cellStyleB = createStyleForHeader(sheet);
				cellStyleB.setFont(fonDefB);
				cellStyleB.setWrapText(true);
				cellStyleB.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleB.setAlignment(HorizontalAlignment.CENTER);
				cellStyleB.setBorderBottom(BorderStyle.THIN);
				cellStyleB.setBorderTop(BorderStyle.THIN);
				cellStyleB.setBorderRight(BorderStyle.THIN);
				cellStyleB.setBorderLeft(BorderStyle.THIN);
				cellStyleB.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleB.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleB.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleB.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				
				Row row4 = sheet.createRow(5);
				row4.setHeight((short) 500);
				Cell cell4 = row4.createCell(0);
				cell4.setCellStyle(cellStyleB);
				cell4.setCellValue("");

				cell4 = row4.createCell(1);
				cell4.setCellStyle(cellStyleB);
				cell4.setCellValue("Actual Generation (kWh)");
				
				cell4 = row4.createCell(2);
				cell4.setCellStyle(cellStyleB);
				cell4.setCellValue("Expected Generation (kWh)");
				
				cell4 = row4.createCell(3);
				cell4.setCellStyle(cellStyleB);
				cell4.setCellValue("Modeled Generation (kWh)");
				
				cell4 = row4.createCell(4);
				cell4.setCellStyle(cellStyleB);
				cell4.setCellValue("POA (W/m2)");
				
				cell4 = row4.createCell(5);
				cell4.setCellStyle(cellStyleB);
				cell4.setCellValue("Expected Generation Index (%)");
				
				sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 7));
				cell4 = row4.createCell(6);
				cell4.setCellStyle(cellStyleB);
				cell4.setCellValue("Modeled Generation Index (%)");
				
				cell4 = row4.createCell(7);
				cell4.setCellStyle(cellStyleB);

				
				
				// Create style row
				Font fontRow = sheet.getWorkbook().createFont();
				fontRow.setFontName("Calibri (Body)");
				fontRow.setFontHeightInPoints((short) 12); // font size
				fontRow.setColor(IndexedColors.BLACK.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleItem = sheet.getWorkbook().createCellStyle();
				cellStyleItem.setFont(fontRow);
				cellStyleItem.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				cellStyleItem.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleItem.setAlignment(HorizontalAlignment.CENTER);
				
				cellStyleItem.setBorderBottom(BorderStyle.THIN);
				cellStyleItem.setBorderTop(BorderStyle.THIN);
				cellStyleItem.setBorderRight(BorderStyle.THIN);
				cellStyleItem.setBorderLeft(BorderStyle.THIN);
				cellStyleItem.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleItem.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleItem.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleItem.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				

				Row row5 = sheet.createRow(6);
				
				for (int i = 0; i < categories.size(); i++) {
					row5 = sheet.createRow(i + 6);
					row5.setHeight((short) 500);
					Cell cell5 = row5.createCell(0);
					cell5.setCellStyle(cellStyleItem);
					cell5.setCellValue((String) categories.get(i));
					
					Cell cell51 = row5.createCell(1);
					cell51.setCellStyle(cellStyleItem);
					cell51.setCellValue(df.format(actualGeneration.get(i)));
					
					
					Cell cell52 = row5.createCell(2);
					cell52.setCellStyle(cellStyleItem);
					cell52.setCellValue(df.format(dataExpectedGeneration.get(i)));
					
					
					Cell cell53 = row5.createCell(3);
					cell53.setCellStyle(cellStyleItem);
					cell53.setCellValue(df.format(dataModeledGeneration.get(i)));
					
					
					Cell cell54 = row5.createCell(4);
					cell54.setCellStyle(cellStyleItem);
					cell54.setCellValue(df2p.format(dataPOA.get(i)));
					
					
					Cell cell55 = row5.createCell(5);
					cell55.setCellStyle(cellStyleItem);
					cell55.setCellValue(df1p.format(dataExpectedGenerationIndex.get(i)));
					
					
					sheet.addMergedRegion(new CellRangeAddress(6 + i, 6 + i, 6, 7));
					Cell cell56 = row5.createCell(6);
					cell56.setCellStyle(cellStyleItem);
					cell56.setCellValue(df1p.format(dataModeledGenerationIndex.get(i)));
					
					Cell cell57 = row5.createCell(7);
					cell57.setCellStyle(cellStyleItem);
				}
				
				
				
				
				Row row6 = sheet.createRow(6 + categories.size());
				row6.setHeight((short) 500);
				Cell cell6 = row6.createCell(0);
				cell6.setCellStyle(cellStyleB);
				cell6.setCellValue("Total");
				
				
				Cell cell61 = row6.createCell(1);
				cell61.setCellStyle(cellStyleB);
				cell61.setCellValue(df.format(totalActualGeneration));
				
				
				Cell cell62 = row6.createCell(2);
				cell62.setCellStyle(cellStyleB);
				cell62.setCellValue(df.format(totalExpectedGeneration));
				
				Cell cell63 = row6.createCell(3);
				cell63.setCellStyle(cellStyleB);
				cell63.setCellValue(df.format(totalModeledGeneration));
				
				Cell cell64 = row6.createCell(4);
				cell64.setCellStyle(cellStyleB);
				cell64.setCellValue("");
				
				Cell cell65 = row6.createCell(5);
				cell65.setCellStyle(cellStyleB);
				cell65.setCellValue(df1p.format(totalExpectedGenerationIndex));
				
				
				sheet.addMergedRegion(new CellRangeAddress(6 + categories.size(), 6 + categories.size(), 6, 7));
				Cell cell66 = row6.createCell(6);
				cell66.setCellStyle(cellStyleB);
				cell66.setCellValue(df1p.format(totalModeledGenerationIndex));
				
				Cell cell67 = row6.createCell(7);
				cell67.setCellStyle(cellStyleB);
				
				
				
				
				// add Note
				Font fontRowBg = sheet.getWorkbook().createFont();
				fontRowBg.setFontName("Calibri (Body)");
				fontRowBg.setFontHeightInPoints((short) 14); // font size
				fontRowBg.setColor(IndexedColors.WHITE.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleBg = sheet.getWorkbook().createCellStyle();
				cellStyleBg.setFont(fontRowBg);
				cellStyleBg.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleBg.setAlignment(HorizontalAlignment.LEFT);
				cellStyleBg.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.index);
				cellStyleBg.setFillPattern(FillPatternType.BIG_SPOTS);
				cellStyleBg.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				
				
				sheet.addMergedRegion(new CellRangeAddress(22 + categories.size(), 22 + categories.size(), 0, 1));
				sheet.addMergedRegion(new CellRangeAddress(23 + categories.size(), 23 + categories.size(), 0, 5));
				sheet.addMergedRegion(new CellRangeAddress(24 + categories.size(), 24 + categories.size(), 0, 5));
				sheet.addMergedRegion(new CellRangeAddress(25 + categories.size(), 25 + categories.size(), 0, 5));
				
				Row row7 = sheet.createRow(22 + categories.size());
				row7.setHeight((short) 600);
				Cell cell7 = row7.createCell(0);
				cell7.setCellStyle(cellStyleBg);
				cell7.setCellValue("Actual, Expected and Modeled Generation");
				
				
				Font fontRowNote = sheet.getWorkbook().createFont();
				fontRowNote.setFontName("Calibri (Body)");
				fontRowNote.setFontHeightInPoints((short) 14); // font size
				fontRowNote.setColor(IndexedColors.BLACK.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleNote = sheet.getWorkbook().createCellStyle();
				cellStyleNote.setFont(fontRowNote);
				cellStyleNote.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleNote.setAlignment(HorizontalAlignment.LEFT);
				
				
				Row row8 = sheet.createRow(23 + categories.size());
				row8.setHeight((short) 600);
				Cell cell8 = row8.createCell(0);
				cell8.setCellStyle(cellStyleNote);
				cell8.setCellValue("The Actual Generation is the energy reported by the production meters.");
				
				
				Row row9 = sheet.createRow(24 + categories.size());
				row8.setHeight((short) 600);
				Cell cell9 = row9.createCell(0);
				cell9.setCellStyle(cellStyleNote);
				cell9.setCellValue("The Expected Generation is calculated based on measured irradiance and module temperature.");
				
				
				Row row10 = sheet.createRow(25 + categories.size());
				row10.setHeight((short) 600);
				Cell cell10 = row10.createCell(0);
				cell10.setCellStyle(cellStyleNote);
				cell10.setCellValue("The Modeled Generation is predicted by PVWatts Calculator.");
				
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	
	private static void solidLineSeries(XDDFChartData data, int index, PresetColor color) {
		XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
		XDDFLineProperties line = new XDDFLineProperties();
		line.setFillProperties(fill);
		XDDFChartData.Series series = data.getSeries().get(index);
		XDDFShapeProperties properties = series.getShapeProperties();
		if (properties == null) {
			properties = new XDDFShapeProperties();
		}
		properties.setLineProperties(line);
		series.setShapeProperties(properties);
	}
	
	
	private static void solidFillSeries(XDDFChartData data, int index, PresetColor color) {
		XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
		XDDFChartData.Series series = data.getSeries().get(index);
		XDDFShapeProperties properties = series.getShapeProperties();
		if (properties == null) {
			properties = new XDDFShapeProperties();
		}
		properties.setFillProperties(fill);
		series.setShapeProperties(properties);
	}
}
