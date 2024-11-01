/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.MonthlyProductionTrendReportEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeeklyDateEntity;
import com.nwm.api.services.BuiltInReportService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.DocumentHelper;
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
			BuiltInReportService service = new BuiltInReportService();
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			List<ViewReportEntity> summarizedList = service.summarizeReport(dataObjList, WeeklyDateEntity.class);
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, summarizedList, summarizedList.size());

		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
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
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			BuiltInReportService service = new BuiltInReportService();
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			List<ViewReportEntity> summarizedList = service.summarizeReport(dataObjList, WeeklyDateEntity.class);
			
			// insert logo image
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			ClientAnchor logoAnchor = new XSSFClientAnchor(10 * Units.EMU_PER_PIXEL, -10 * Units.EMU_PER_PIXEL, 0, 10 * Units.EMU_PER_PIXEL, 7, 1, 8, 4);
			
			for (int s = 0; s < summarizedList.size(); s++) {
				ViewReportEntity dataObj = summarizedList.get(s);
				
				if (dataObj != null) {
					List<WeeklyDateEntity> dataExports = dataObj.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() : 0;
					
					XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName(dataObj.getSite_name()));
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
					
					writeHeaderWeeklyReport(sheet, dataObj);
					
					if (numOfPoints > 0) {
						// chart
						ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, -5 * Units.EMU_PER_PIXEL, 0, 0, numOfPoints + 8, 8, numOfPoints + 8 + 24);
						XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, "Performance");
                        chart.getCTChart().getTitle().getTx().getRich().getPArray(0).getRArray(0).getRPr().setSz(1400);
						
						// data sources
						XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 0, 0));
						XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 1, 1));
						XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 2, 2));
						XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 3, 3));
						XDDFNumericalDataSource<Double> valuesData4 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 5, 5));
						XDDFNumericalDataSource<Double> valuesData5 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 6, 6));
						
						// category axis
						XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);
						
						// left value axis
						XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, "GENERATION (KWH)");
						
						XDDFChartData data = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActualGeneration() == null), data, categoriesData, valuesData1, "Actual Generation (kWh)", XDDFColor.from(new byte[] {(byte) 70, (byte) 130, (byte) 180}), null);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getExpectedGeneration() == null), data, categoriesData, valuesData2, "Expected Generation (kWh)", XDDFColor.from(new byte[] {(byte) 166, (byte) 166, (byte) 166}), null);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getModeledGeneration() == null), data, categoriesData, valuesData3, "Modeled Generation (kWh)", XDDFColor.from(new byte[] {(byte) 176, (byte) 196, (byte) 222}), null);
						
						chart.plot(data);
						
						// right value axis
						XDDFValueAxis rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, "PERFORMANCE INDEX (%)");
						
						data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						Series lineSeries = DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getExpectedGenerationIndex() == null), data, categoriesData, valuesData4, "Expected Generation Index (%)", XDDFColor.from(new byte[] {(byte) 112, (byte) 173, (byte) 71}), null);
						DocumentHelper.solidFillLineMarker(chart, lineSeries, 0, MarkerStyle.CIRCLE, XDDFColor.from(new byte[] {(byte) 112, (byte) 173, (byte) 71}));
						lineSeries = DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getModeledGenerationIndex() == null), data, categoriesData, valuesData5, "Modeled Generation Index (%)", XDDFColor.from(new byte[] {(byte) 255, (byte) 192, (byte) 0}), null);
						DocumentHelper.solidFillLineMarker(chart, lineSeries, 1, MarkerStyle.CIRCLE, XDDFColor.from(new byte[] {(byte) 255, (byte) 192, (byte) 0}));
						
						chart.plot(data);
					}
				}
			}
			
			sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), "Annual Production Trend Report (Monthly Interval)");
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
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
			BuiltInReportService service = new BuiltInReportService();
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			List<ViewReportEntity> summarizedList = service.summarizeReport(dataObjList, MonthlyProductionTrendReportEntity.class);
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, summarizedList, summarizedList.size());

		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
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
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			BuiltInReportService service = new BuiltInReportService();
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			List<ViewReportEntity> summarizedList = service.summarizeReport(dataObjList, MonthlyProductionTrendReportEntity.class);
			
			// insert logo image
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			
			if(dataObjList.get(0).getData_intervals() == 2) {
				ClientAnchor logoAnchor = new XSSFClientAnchor(0, 0, 0, 10 * Units.EMU_PER_PIXEL, 4, 1, 5, 4);
				for (int s = 0; s < summarizedList.size(); s++) {
					ViewReportEntity dataObj = summarizedList.get(s);
					
					if (dataObj != null) {
						XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName(dataObj.getSite_name()));
						DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
						
						writeHeaderMonthTrendReport(sheet, dataObj);
					}
				}
			} else {
				XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName("Monthly Interval"));
				ClientAnchor logoAnchor = new XSSFClientAnchor(50 * Units.EMU_PER_PIXEL, -20 * Units.EMU_PER_PIXEL, 0, 20 * Units.EMU_PER_PIXEL, 4, 1, 5, 4);
				DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
				
				writeHeaderMonthTrendReportMonthlyInterval(sheet, summarizedList);
			}
					
			String reportInterval = "";
			if (dataObjList.get(0).getData_intervals() == 2) reportInterval = "(15-minute Interval)";
			else if (dataObjList.get(0).getData_intervals() == 6) reportInterval = "(Monthly Interval)";

			sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), "Monthly Production Trend Report " + reportInterval);
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		}
	}
	
	
	// Write header with format
	private static void writeHeaderMonthTrendReportMonthlyInterval(Sheet sheet, List<ViewReportEntity> dataObjList) {
		try {
			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 25 * 256);
			sheet.setColumnWidth(1, 25 * 256);
			sheet.setColumnWidth(2, 25 * 256);
			sheet.setColumnWidth(3, 25 * 256);
			sheet.setColumnWidth(4, 25 * 256);
			sheet.setDefaultRowHeight((short) 500);
			sheet.setDisplayGridlines(false);
			
			CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 14, true);
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowLeftAlignCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			tableRowLeftAlignCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			
			Row row = sheet.createRow(0);
			row.setHeight((short) 600);
			Cell cell = row.createCell(1);
			cell.setCellStyle(reportTitleCellStyle);
			cell.setCellValue("MONTHLY PRODUCTION TREND REPORT (MONTHLY INTERVAL)");
			sheet.addMergedRegion(new CellRangeAddress(0, 4, 1, 3));
			
			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Site Name");
			cell = row.createCell(1);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
			
			cell = row.createCell(2);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Start Date");
			
			cell = row.createCell(3);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("End Date");
			
			cell = row.createCell(4);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Monthly Production (kWh)");
			
			if(dataObjList.size() > 0) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				
				for (int i = 0; i < dataObjList.size(); i++) {
					ViewReportEntity item = dataObjList.get(i);
					
					Row row6f = sheet.createRow(6+i);
					Cell cell60f = row6f.createCell(0);
					cell60f.setCellStyle(tableRowLeftAlignCellStyle);
					cell60f.setCellValue(item.getSite_name());
					Cell cell61f = row6f.createCell(1);
					cell61f.setCellStyle(tableRowLeftAlignCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(6+i, 6+i, 0, 1));
					
					Cell cell62f = row6f.createCell(2);
					cell62f.setCellStyle(tableRowCellStyle);
					cell62f.setCellValue(format.format(dateFormat.parse(item.getStart_date())));
					
					Cell cell63f = row6f.createCell(3);
					cell63f.setCellStyle(tableRowCellStyle);
					cell63f.setCellValue(format.format(dateFormat.parse(item.getEnd_date())));
					
					Cell cell64f = row6f.createCell(4);
					cell64f.setCellStyle(tableRowNoDecimalCellStyle);
					List<MonthlyProductionTrendReportEntity> dataReport = item.getDataReports();
					if(dataReport != null && dataReport.size() > 0 && dataReport.get(0).getMonthlyProduction() != null) cell64f.setCellValue(dataReport.get(0).getMonthlyProduction());
				}
			}
		} catch (Exception e) {
		}
	}
	
	
	// Write header with format monthly portfolio production report 
	private static void writeHeaderMonthTrendReport(Sheet sheet, ViewReportEntity dataObj) {
		try {
			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 20 * 256);
			sheet.setColumnWidth(1, 30 * 256);
			sheet.setColumnWidth(2, 30 * 256);
			sheet.setColumnWidth(3, 20 * 256);
			sheet.setColumnWidth(4, 13 * 256);
			sheet.setDefaultRowHeight((short) 400);
			sheet.setDisplayGridlines(false);
			
			CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 14, true);
			CellStyle reportSubTitleBoldCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 12, true);
			CellStyle reportSubTitleCelStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 12, false);
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			
			Row row = sheet.createRow(1);
			row.setHeight((short) 450);
			Cell cell = row.createCell(1);
			cell.setCellStyle(reportTitleCellStyle);
			cell.setCellValue("MONTHLY PRODUCTION TREND REPORT (15-MINUTE INTERVAL)");
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));
						
			row = sheet.createRow(2);
			row.setHeight((short) 450);
			cell = row.createCell(1);
			cell.setCellStyle(reportSubTitleBoldCellStyle);
			cell.setCellValue(dataObj.getSite_name().toUpperCase());
			
			row = sheet.createRow(3);
			row.setHeight((short) 450);
			cell = row.createCell(1);
			cell.setCellStyle(reportSubTitleCelStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			cell.setCellValue(format.format(dateFormat.parse(dataObj.getStart_date())) + " - " +  format.format(dateFormat.parse(dataObj.getEnd_date())));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 3));
			
			row = sheet.createRow(5);
			row.setHeight((short) 400);
			cell = row.createCell(0);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Timestamp");
			cell = row.createCell(1);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(2);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 2));

			cell = row.createCell(3);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Monthly Production (kWh)");
			cell = row.createCell(4);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 3, 4));
			
			List<MonthlyProductionTrendReportEntity> dataExports = dataObj.getDataReports();
			
			if(dataExports != null && dataExports.size() > 0) {
				for (int i = 0; i < dataExports.size(); i++) {
					MonthlyProductionTrendReportEntity item = dataExports.get(i);
					
					Row row5 = sheet.createRow(6 + i);
					row5.setHeight((short) 315);
					Cell cell5 = row5.createCell(0);
					cell5.setCellStyle(tableRowCellStyle);
					cell5.setCellValue(item.getCategories_time());
					cell5 = row5.createCell(1);
					cell5.setCellStyle(tableRowCellStyle);
					cell5 = row5.createCell(2);
					cell5.setCellStyle(tableRowCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(i + 6, i + 6, 0, 2));
					
					Cell cell51 = row5.createCell(3);
					cell51.setCellStyle(tableRowNoDecimalCellStyle);
					cell51.setCellValue(item.getMonthlyProduction());
					cell51 = row5.createCell(4);
					cell51.setCellStyle(tableRowNoDecimalCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(i + 6, i + 6, 3, 4));
				}
			}
		} catch (Exception e) {
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
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			List<ViewReportEntity> summarizedList = service.summarizeReport(dataObjList, WeeklyDateEntity.class);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, summarizedList, summarizedList.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
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
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			BuiltInReportService service = new BuiltInReportService();
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			List<ViewReportEntity> summarizedList = service.summarizeReport(dataObjList, WeeklyDateEntity.class);
			
			// insert logo image
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			ClientAnchor logoAnchor = new XSSFClientAnchor(10 * Units.EMU_PER_PIXEL, -10 * Units.EMU_PER_PIXEL, 0, 10 * Units.EMU_PER_PIXEL, 7, 1, 8, 4);
			
			for (int s = 0; s < summarizedList.size(); s++) {
				ViewReportEntity dataObj = summarizedList.get(s);
				
				if (dataObj != null) {
					List<WeeklyDateEntity> dataExports = dataObj.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() : 0;
					
					XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName(dataObj.getSite_name()));
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
					
					writeHeaderWeeklyReport(sheet, dataObj);
					
					if (numOfPoints > 0) {
						// chart
						ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, -5 * Units.EMU_PER_PIXEL, 0, 0, numOfPoints + 8, 8, numOfPoints + 8 + 24);
						XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, "Performance");
						chart.getCTChart().getTitle().getTx().getRich().getPArray(0).getRArray(0).getRPr().setSz(1400);
						
						// data sources
						XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 0, 0));
						XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 1, 1));
						XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 2, 2));
						XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 3, 3));
						XDDFNumericalDataSource<Double> valuesData4 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 5, 5));
						XDDFNumericalDataSource<Double> valuesData5 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(6, numOfPoints + 5, 6, 6));
						
						// category axis
						XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);
						
						// left value axis
						XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, "GENERATION (KWH)");
						
						XDDFChartData data = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActualGeneration() == null), data, categoriesData, valuesData1, "Actual Generation (kWh)", XDDFColor.from(new byte[] {(byte) 70, (byte) 130, (byte) 180}), null);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getExpectedGeneration() == null), data, categoriesData, valuesData2, "Expected Generation (kWh)", XDDFColor.from(new byte[] {(byte) 166, (byte) 166, (byte) 166}), null);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getModeledGeneration() == null), data, categoriesData, valuesData3, "Modeled Generation (kWh)", XDDFColor.from(new byte[] {(byte) 176, (byte) 196, (byte) 222}), null);
						
						chart.plot(data);
						
						// right value axis
						XDDFValueAxis rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, "PERFORMANCE INDEX (%)");
						
						data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						Series lineSeries = DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getExpectedGenerationIndex() == null), data, categoriesData, valuesData4, "Expected Generation Index (%)", XDDFColor.from(new byte[] {(byte) 112, (byte) 173, (byte) 71}), null);
						DocumentHelper.solidFillLineMarker(chart, lineSeries, 0, MarkerStyle.CIRCLE, XDDFColor.from(new byte[] {(byte) 112, (byte) 173, (byte) 71}));
						lineSeries = DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getModeledGenerationIndex() == null), data, categoriesData, valuesData5, "Modeled Generation Index (%)", XDDFColor.from(new byte[] {(byte) 255, (byte) 192, (byte) 0}), null);
						DocumentHelper.solidFillLineMarker(chart, lineSeries, 1, MarkerStyle.CIRCLE, XDDFColor.from(new byte[] {(byte) 255, (byte) 192, (byte) 0}));
						
						chart.plot(data);
					}
				}
			}
			
			sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), "Weekly Production Trend Report (Daily Interval)");
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		}
	}
	
	// Write header with format
		private static void writeHeaderWeeklyReport(Sheet sheet, ViewReportEntity dataObj) {
			try {
				sheet.setDefaultColumnWidth(16);
				sheet.setColumnWidth(0, 15 * 256);
				sheet.setColumnWidth(1, 30 * 256);
				sheet.setColumnWidth(2, 30 * 256);
				sheet.setColumnWidth(3, 30 * 256);
				sheet.setColumnWidth(4, 30 * 256);
				sheet.setColumnWidth(5, 30 * 256);
				sheet.setColumnWidth(6, 15 * 256);
				sheet.setColumnWidth(7, 15 * 256);
				sheet.setDefaultRowHeight((short) 315);
				sheet.setDisplayGridlines(false);
				
				CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 18, true);
				CellStyle reportSubTitleBoldCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 14, true);
				CellStyle reportSubTitleCelStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 12, false);
				CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
				CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
				CellStyle tableRowBoldCellStyle = DocumentHelper.createStyleForTableRow(sheet, true);
				CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
				CellStyle tableRowNoDecimalBoldCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, true, null);
				CellStyle tableRowOneDecimalPlaceCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceDataFormat);
				CellStyle tableRowOneDecimalPlaceBoldCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, true, DocumentHelper.oneDecimalPlaceDataFormat);
				CellStyle tableRowTwoDecimalPlaceCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.twoDecimalPlaceDataFormat);

				Row row = sheet.createRow(1);
				row.setHeight((short) 500);
				Cell cell = row.createCell(1);
				cell.setCellStyle(reportTitleCellStyle);
				String title = dataObj.getCadence_range() == 4 ? "ANNUAL PRODUCTION TREND REPORT (MONTHLY INTERVAL)" : "WEEKLY PRODUCTION TREND REPORT (DAILY INTERVAL)";
				cell.setCellValue(title);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 6));
							
				row = sheet.createRow(2);
				row.setHeight((short) 400);
				cell = row.createCell(1);
				cell.setCellStyle(reportSubTitleBoldCellStyle);
				cell.setCellValue(dataObj.getSite_name().toUpperCase());
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 6));

				row = sheet.createRow(3);
				row.setHeight((short) 400);
				cell = row.createCell(1);
				cell.setCellStyle(reportSubTitleCelStyle);
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				cell.setCellValue(format.format(dateFormat.parse(dataObj.getStart_date())) + " - " +  format.format(dateFormat.parse(dataObj.getEnd_date())));
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 6));
				
				row = sheet.createRow(5);
				cell = row.createCell(0);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue(dataObj.getCadence_range() == 4 ? "" : "Date");

				cell = row.createCell(1);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("Actual Generation (kWh)");
				
				cell = row.createCell(2);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("Expected Generation (kWh)");
				
				cell = row.createCell(3);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("Modeled Generation (kWh)");
				
				cell = row.createCell(4);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("POA (W/m2)");
				
				cell = row.createCell(5);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("Expected Generation Index (%)");
				
				cell = row.createCell(6);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("Modeled Generation Index (%)");
				cell = row.createCell(7);
				cell.setCellStyle(tableHeaderCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 7));
				
				List<WeeklyDateEntity> dataExports = dataObj.getDataReports();
				Double totalActualGeneration = null;
				Double totalExpectedGeneration = null;
				Double totalModeledGeneration = null;
				
				if(dataExports != null && dataExports.size() > 0) {
					for (int i = 0; i < dataExports.size(); i++) {
						WeeklyDateEntity item = dataExports.get(i);
						
						Row row5 = sheet.createRow(6 + i);
						Cell cell5 = row5.createCell(0);
						cell5.setCellStyle(tableRowCellStyle);
						cell5.setCellValue(item.getCategories_time());
						
						Cell cell51 = row5.createCell(1);
						cell51.setCellStyle(tableRowNoDecimalCellStyle);
						if (item.getActualGeneration() != null) cell51.setCellValue(item.getActualGeneration());
						
						Cell cell52 = row5.createCell(2);
						cell52.setCellStyle(tableRowNoDecimalCellStyle);
						if (item.getExpectedGeneration() != null) cell52.setCellValue(item.getExpectedGeneration());
						
						Cell cell53 = row5.createCell(3);
						cell53.setCellStyle(tableRowNoDecimalCellStyle);
						if (item.getModeledGeneration() != null) cell53.setCellValue(item.getModeledGeneration());
						
						Cell cell54 = row5.createCell(4);
						cell54.setCellStyle(tableRowTwoDecimalPlaceCellStyle);
						if (item.getPoa() != null) cell54.setCellValue(item.getPoa());
						
						Cell cell55 = row5.createCell(5);
						cell55.setCellStyle(tableRowOneDecimalPlaceCellStyle);
						if (item.getExpectedGeneration() != null) cell55.setCellValue(item.getExpectedGenerationIndex());
						
						Cell cell56 = row5.createCell(6);
						cell56.setCellStyle(tableRowOneDecimalPlaceCellStyle);
						if (item.getModeledGenerationIndex() != null) cell56.setCellValue(item.getModeledGenerationIndex());
						Cell cell57 = row5.createCell(7);
						cell57.setCellStyle(tableRowOneDecimalPlaceCellStyle);
						sheet.addMergedRegion(new CellRangeAddress(6 + i, 6 + i, 6, 7));
						
						if(item.getActualGeneration() != null) totalActualGeneration = (totalActualGeneration != null ? totalActualGeneration : 0) + item.getActualGeneration();
						if(item.getExpectedGeneration() != null) totalExpectedGeneration = (totalExpectedGeneration != null ? totalExpectedGeneration : 0) + item.getExpectedGeneration();
						if(item.getModeledGeneration() != null) totalModeledGeneration = (totalModeledGeneration != null ? totalModeledGeneration : 0) + item.getModeledGeneration();
					}
				}
				
				Row row6 = sheet.createRow(6 + dataExports.size());
				Cell cell6 = row6.createCell(0);
				cell6.setCellStyle(tableRowBoldCellStyle);
				cell6.setCellValue("Total");
				
				Cell cell61 = row6.createCell(1);
				cell61.setCellStyle(tableRowNoDecimalBoldCellStyle);
				if (totalActualGeneration != null) cell61.setCellValue(totalActualGeneration);
				
				Cell cell62 = row6.createCell(2);
				cell62.setCellStyle(tableRowNoDecimalBoldCellStyle);
				if (totalExpectedGeneration != null) cell62.setCellValue(totalExpectedGeneration);
				
				Cell cell63 = row6.createCell(3);
				cell63.setCellStyle(tableRowNoDecimalBoldCellStyle);
				if (totalModeledGeneration != null) cell63.setCellValue(totalModeledGeneration);
				
				Cell cell64 = row6.createCell(4);
				cell64.setCellStyle(tableRowOneDecimalPlaceBoldCellStyle);
				
				Cell cell65 = row6.createCell(5);
				cell65.setCellStyle(tableRowOneDecimalPlaceBoldCellStyle);
				if (totalActualGeneration > 0 && totalExpectedGeneration > 0) cell65.setCellValue(totalActualGeneration / totalExpectedGeneration * 100);
				
				Cell cell66 = row6.createCell(6);
				cell66.setCellStyle(tableRowOneDecimalPlaceBoldCellStyle);
				if (totalActualGeneration > 0 && totalModeledGeneration > 0) cell66.setCellValue(totalActualGeneration / totalModeledGeneration * 100);
				Cell cell67 = row6.createCell(7);
				cell67.setCellStyle(tableRowOneDecimalPlaceBoldCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(6 + dataExports.size(), 6 + dataExports.size(), 6, 7));
				
				// add Note
				Font fontRowBg = sheet.getWorkbook().createFont();
				fontRowBg.setFontName("Times New Roman");
				fontRowBg.setBold(true);
				fontRowBg.setFontHeightInPoints((short) 11); // font size
				fontRowBg.setColor(IndexedColors.WHITE.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleBg = sheet.getWorkbook().createCellStyle();
				cellStyleBg.setFont(fontRowBg);
				cellStyleBg.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleBg.setAlignment(HorizontalAlignment.LEFT);
				cellStyleBg.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.index);
				cellStyleBg.setFillPattern(FillPatternType.BIG_SPOTS);
				cellStyleBg.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				
				
				sheet.addMergedRegion(new CellRangeAddress(33 + dataExports.size(), 33 + dataExports.size(), 0, 1));
				sheet.addMergedRegion(new CellRangeAddress(34 + dataExports.size(), 34 + dataExports.size(), 0, 5));
				sheet.addMergedRegion(new CellRangeAddress(35 + dataExports.size(), 35 + dataExports.size(), 0, 5));
				sheet.addMergedRegion(new CellRangeAddress(36 + dataExports.size(), 36 + dataExports.size(), 0, 5));
				
				Row row7 = sheet.createRow(33 + dataExports.size());
				Cell cell7 = row7.createCell(0);
				cell7.setCellStyle(cellStyleBg);
				cell7.setCellValue("Actual, Expected and Modeled Generation");
				
				
				Font fontRowNote = sheet.getWorkbook().createFont();
				fontRowNote.setFontName("Times New Roman");
				fontRowNote.setFontHeightInPoints((short) 11); // font size
				fontRowNote.setColor(IndexedColors.BLACK.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleNote = sheet.getWorkbook().createCellStyle();
				cellStyleNote.setFont(fontRowNote);
				cellStyleNote.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleNote.setAlignment(HorizontalAlignment.LEFT);
				
				
				Row row8 = sheet.createRow(34 + dataExports.size());
				Cell cell8 = row8.createCell(0);
				cell8.setCellStyle(cellStyleNote);
				cell8.setCellValue("The Actual Generation is the energy reported by the production meters.");
				
				
				Row row9 = sheet.createRow(35 + dataExports.size());
				Cell cell9 = row9.createCell(0);
				cell9.setCellStyle(cellStyleNote);
				cell9.setCellValue("The Expected Generation is calculated based on measured irradiance and module temperature.");
				
				
				Row row10 = sheet.createRow(36 + dataExports.size());
				Cell cell10 = row10.createCell(0);
				cell10.setCellStyle(cellStyleNote);
				cell10.setCellValue("The Modeled Generation is predicted by PVWatts Calculator.");
				
			} catch (Exception e) {
			}

		}
	
	/**
	 * @description sent excel report by mail in template
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param document, subscribers, cadenceRange
	 */
	private void sentExcelReportByMail(XSSFWorkbook document, String subscribers, String cadenceRangeName) throws Exception {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		String dir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
		String fileName = dir + "/" + cadenceRangeName + "_" + timeStamp + ".xlsx";
		
		try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
			// Write the output to a file
			document.write(fileOut);
			String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
			String msgTemplate = Constants.getMailTempleteByState(18);
			String body = String.format(msgTemplate, "Customer", cadenceRangeName.toUpperCase() + " ", "", "");
			String mailTo = subscribers;
			String subject = Constants.getMailSubjectByState(18);

			String tags = "report_" + cadenceRangeName.toLowerCase();
			String fromName = "NEXT WAVE ENERGY MONITORING INC";
			boolean flagSent = SendMail.SendGmailTLSAttachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
			if (!flagSent) {
				throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
			}
		}
	}
}
