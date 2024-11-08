/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.poi.ss.usermodel.BorderStyle;
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
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.nwm.api.config.ReportTaskScheduler;
import com.nwm.api.entities.AssetManagementAndOperationPerformanceDataEntity;
import com.nwm.api.entities.AssetManagementAndOperationPerformanceReportEntity;
import com.nwm.api.entities.CustomReportDataEntity;
import com.nwm.api.entities.DailyDateEntity;
import com.nwm.api.entities.MonthlyDateEntity;
import com.nwm.api.entities.QuarterlyDateEntity;
import com.nwm.api.entities.ReportsEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.services.ReportsService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.DocumentHelper;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;
import com.opencsv.CSVWriter;

import springfox.documentation.annotations.ApiIgnore;
//import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/reports")
public class ReportsController extends BaseController {

	private static final Color BLUE_COLOR = new Color(49, 119, 168);
	private static final Color LIGHT_BLUE_COLOR = new Color(109, 189, 246);
	private static final Color ORANGE_COLOR = new Color(255, 129, 39);
	
	// Write header with format
		private static void writeHeaderDailyReport(Sheet sheet, ViewReportEntity report, ViewReportEntity dataObj) {
			try {
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
				sheet.setDisplayGridlines(false);
				
				CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 22, true);
				CellStyle reportInfoCellStyle = DocumentHelper.createStyleForReportInfo(sheet, false);
				CellStyle reportInfoBoldCellStyle = DocumentHelper.createStyleForReportInfo(sheet, true);
				CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
				CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
				CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
				
				Row row = sheet.createRow(0);
				Cell cell = row.createCell(0);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("Site Name");
				cell = row.createCell(1);
				cell.setCellStyle(reportInfoBoldCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
				
				cell = row.createCell(2);
				row.setHeight((short) 600);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue(dataObj.getSite_name());
				cell = row.createCell(3);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell = row.createCell(4);
				cell.setCellStyle(reportInfoBoldCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
				
				row = sheet.createRow(1);
				cell = row.createCell(0);
				row.setHeight((short) 600);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("Report Date");
				cell = row.createCell(1);
				cell.setCellStyle(reportInfoBoldCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
				
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(dataObj.getReport_date());
				cell = row.createCell(3);
				cell.setCellStyle(reportInfoCellStyle);
				cell = row.createCell(4);
				cell.setCellStyle(reportInfoCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 4));
				
				row = sheet.createRow(2);
				row.setHeight((short) 600);
				cell = row.createCell(0);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("Covered Period");
				cell = row.createCell(1);
				cell.setCellStyle(reportInfoBoldCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(format.format(dateFormat.parse(report.getStart_date())) + " - " + format.format(dateFormat.parse(report.getEnd_date())));
				cell = row.createCell(3);
				cell.setCellStyle(reportInfoCellStyle);
				cell = row.createCell(4);
				cell.setCellStyle(reportInfoCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 4));
				
				row = sheet.createRow(3);
				row.setHeight((short) 600);
				cell = row.createCell(0);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("System Size (kW DC)");
				cell = row.createCell(1);
				cell.setCellStyle(reportInfoBoldCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
				
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(dataObj.getDc_capacity());
				cell = row.createCell(3);
				cell.setCellStyle(reportInfoCellStyle);
				cell = row.createCell(4);
				cell.setCellStyle(reportInfoCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 4));
				
				for (int i = 0; i <= 3; i++) {
					row = sheet.getRow(i) != null ? sheet.getRow(i) : sheet.createRow(i);
					for (int j = 5; j <= 10; j++) {
						cell = row.createCell(j);
						cell.setCellStyle(reportTitleCellStyle);
						if(i == 0 && j == 5) cell.setCellValue("DAILY PRODUCTION REPORT");
					}
				}
				sheet.addMergedRegion(new CellRangeAddress(0, 3, 5, 10));	
				
				row = sheet.createRow(24);
				cell = row.createCell(0);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("Time");
				cell = row.createCell(1);
				cell.setCellStyle(tableHeaderCellStyle);
				cell = row.createCell(2);
				cell.setCellStyle(tableHeaderCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(24, 24, 0, 2));
				
				cell = row.createCell(3);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("Actual Power (kW)");
				cell = row.createCell(4);
				cell.setCellStyle(tableHeaderCellStyle);
				cell = row.createCell(5);
				cell.setCellStyle(tableHeaderCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(24, 24, 3, 5));
				
				cell = row.createCell(6);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("Estimate Energy (kWh)");
				cell = row.createCell(7);
				cell.setCellStyle(tableHeaderCellStyle);
				cell = row.createCell(8);
				cell.setCellStyle(tableHeaderCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(24, 24, 6, 8));
				
				cell = row.createCell(9);
				cell.setCellStyle(tableHeaderCellStyle);
				cell.setCellValue("Irradiance (W/m2)");
				cell = row.createCell(10);
				cell.setCellStyle(tableHeaderCellStyle);
				cell = row.createCell(11);
				cell.setCellStyle(tableHeaderCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(24, 24, 9, 11));
				
				List<DailyDateEntity> dataExports = dataObj.getDataReports();
				if(dataExports != null && dataExports.size() > 0) {
					for(int i = 0 ;i < dataExports.size(); i++) {
						DailyDateEntity item = (DailyDateEntity) dataExports.get(i); 
						int t = 25 + i;
						
						Row row28r = sheet.createRow(t);
						Cell cel28r = row28r.createCell(0);
						cel28r.setCellStyle(tableRowCellStyle);
						cel28r.setCellValue(item.getCategories_time());
						Cell cel28r1 = row28r.createCell(1);
						cel28r1.setCellStyle(tableRowCellStyle);
						Cell cel28r2 = row28r.createCell(2);
						cel28r2.setCellStyle(tableRowCellStyle);
						sheet.addMergedRegion(new CellRangeAddress(t, t, 0, 2));
						
						Cell cel29r = row28r.createCell(3);
						cel29r.setCellStyle(tableRowNoDecimalCellStyle);
						if(item.getPower() != null) cel29r.setCellValue(item.getPower());
						Cell cel29r4 = row28r.createCell(4);
						cel29r4.setCellStyle(tableRowNoDecimalCellStyle);
						Cell cel29r5 = row28r.createCell(5);
						cel29r5.setCellStyle(tableRowNoDecimalCellStyle);
						sheet.addMergedRegion(new CellRangeAddress(t, t, 3, 5));
						
						Cell cel30r = row28r.createCell(6);
						cel30r.setCellStyle(tableRowNoDecimalCellStyle);
						if(item.getEnergy() != null) cel30r.setCellValue(item.getEnergy());
						Cell cel30r7 = row28r.createCell(7);
						cel30r7.setCellStyle(tableRowNoDecimalCellStyle);
						Cell cel30r8 = row28r.createCell(8);
						cel30r8.setCellStyle(tableRowNoDecimalCellStyle);
						sheet.addMergedRegion(new CellRangeAddress(t, t, 6, 8));
						
						Cell cel31r = row28r.createCell(9);
						cel31r.setCellStyle(tableRowNoDecimalCellStyle);
						if(item.getIrradiance() != null) cel31r.setCellValue(item.getIrradiance());
						Cell cel31r10 = row28r.createCell(10);
						cel31r10.setCellStyle(tableRowNoDecimalCellStyle);
						Cell cel31r11 = row28r.createCell(11);
						cel31r11.setCellStyle(tableRowNoDecimalCellStyle);
						sheet.addMergedRegion(new CellRangeAddress(t, t, 9, 11));
					}
				}
			} catch (Exception e) {
			}

		}
				
		/**
		 * @description sent mail monthly report
		 * @author long.pham
		 * @since 2021-12-28
		 * @param id
		 * @return data (status, message, array, total_row
		 */
		@PostMapping("/sent-mail-excel-daily-report")
		public Object sentMailDailyReport(@RequestBody ViewReportEntity obj) {
			try (XSSFWorkbook document = new XSSFWorkbook()) {
				List<ViewReportEntity> dataObjList = getReportDataList(obj);
				if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				int pictureIdx = DocumentHelper.readLogoImageFile(document);
				
				for (int i = 0; i < dataObjList.size(); i++) {
					ViewReportEntity dataObj = dataObjList.get(i);
					
					if (dataObj != null) {
						List<DailyDateEntity> dataExports = dataObj.getDataReports();
						int numOfPoints = dataExports != null ? dataExports.size() : 0;
						
						XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName((i + 1) + "_" + dataObj.getSite_name()));
						
						// insert logo image
						ClientAnchor logoAnchor = new XSSFClientAnchor(-20 * Units.EMU_PER_PIXEL, 10 * Units.EMU_PER_PIXEL, 0, -10 * Units.EMU_PER_PIXEL, 11, 0, 12, 4);
						DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
						
						// report information and table
						writeHeaderDailyReport(sheet, obj, dataObj);
						
						// chart
						if (numOfPoints > 0) {
							ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, 0, 0, 0, 6, 12, 22);
							XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, null);
							
							// data sources
							XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 0, 0));
							XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 3, 3));
							XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 6, 6));
							XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 9, 9));
							
							// category axis
							XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);
							// adjust tick mark position based on data intervals
							int dataIntervals = 1;
							if (dataObj.getData_intervals() == 1) dataIntervals = 288;
							else if (dataObj.getData_intervals() == 2) dataIntervals = 96;
							else if (dataObj.getData_intervals() == 3) dataIntervals = 24;
	                        chart.getCTChart().getPlotArea().getCatAxArray(0).addNewTickLblSkip().setVal(dataIntervals);
	                        chart.getCTChart().getPlotArea().getCatAxArray(0).addNewTickMarkSkip().setVal(dataIntervals);
							
							// left value axis
							XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, "kW");
							
							XDDFChartData data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, leftAxis);
							DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getPower() == null), data, categoriesData, valuesData1, "Actual Power (kW)", XDDFColor.from(PresetColor.STEEL_BLUE), null);
							
							chart.plot(data);
							
							// right value axis
							XDDFValueAxis rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, "kWh");
							
							data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
							DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getEnergy() == null), data, categoriesData, valuesData2, "Estimate Energy (kWh)", XDDFColor.from(PresetColor.LIGHT_SKY_BLUE), null);
							
							chart.plot(data);
							
							if (dataObj.isHave_poa()) {
								// 2nd right value axis
								rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, "W/m2");
								
								data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
								DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getIrradiance() == null), data, categoriesData, valuesData3, "Irradiance (W/m2)", XDDFColor.from(PresetColor.DARK_ORANGE), null);
								
								chart.plot(data);
							}
						}
					}
				}
				
				sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name());
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
			} catch (Exception e) {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
			}
		}
		
	/**
	 * @description sent mail daily report in pdf
	 * @author Hung.Bui
	 * @since 2022-11-29
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-pdf-daily-report")
	public Object sentMailPdfDailyReport(@RequestBody ViewReportEntity obj) {
		try {
			File file = createPdfFile(obj.getCadence_range_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				List<ViewReportEntity> dataObjList = getReportDataList(obj);
				if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
					ViewReportEntity dataObj = dataObjList.get(l);
					
					if (dataObj != null) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
						SimpleDateFormat categoryFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
						Date startDate = dateFormat.parse(obj.getStart_date());
						Date endDate = dateFormat.parse(obj.getEnd_date());
						dataObj.setStart_date(format.format(startDate));
						dataObj.setEnd_date(format.format(endDate));
						List<DailyDateEntity> dataExports = dataObj.getDataReports() != null ? dataObj.getDataReports() : new ArrayList<>();
						
						// total column: 12
						Table table = new Table(12).useAllAvailableWidth();
						table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
						table.setFontSize(8);
						table.setTextAlignment(TextAlignment.CENTER);
					
						//====== table ============================================================
						// header and logo
						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setHeight(14).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(6, 5).add(new Paragraph("DAILY PRODUCTION REPORT")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
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
						table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
						// empty row
						table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
						
						// header of data table
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Time").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Actual Power (kW)").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Estimated Energy (kWh)").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Irradiance (W/m2)").setBold()));
						
						// data table
						DecimalFormat dfs = new DecimalFormat(DocumentHelper.noDecimalDataFormat);
						for (int i = 0; i < dataExports.size(); i++) {
							DailyDateEntity item = (DailyDateEntity) dataExports.get(i);
							
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getCategories_time())));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getPower() != null ? dfs.format(item.getPower()).replaceAll( "^-(?=0(\\.0*)?$)", "") : "")));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getEnergy() != null ? dfs.format(item.getEnergy()) : "")));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getIrradiance() != null ? dfs.format(item.getIrradiance()) : "")));						
						}
						
						//====== chart ============================================================
						JFreeChart chart = DocumentHelper.createJFreeChart(null);
						XYPlot plot = chart.getXYPlot();
						
						// data source
						TimeSeriesCollection powerDataset = DocumentHelper.createJFreeChartLineDataset(0, plot);
						TimeSeries powerSeries = new TimeSeries("Actual Power (kW)");
						powerDataset.addSeries(powerSeries);
						plot.getRendererForDataset(powerDataset).setSeriesPaint(0, BLUE_COLOR);
						
						TimeSeriesCollection energyDataset = DocumentHelper.createJFreeChartLineDataset(1, plot);
						TimeSeries energySeries = new TimeSeries("Estimate Energy (kWh)");
						energyDataset.addSeries(energySeries);
						plot.getRendererForDataset(energyDataset).setSeriesPaint(0, LIGHT_BLUE_COLOR);
						
						TimeSeriesCollection irradianceDataset = DocumentHelper.createJFreeChartLineDataset(2, plot);
						TimeSeries irradianceSeries = new TimeSeries("Irradiance (W/m2)");
						irradianceDataset.addSeries(irradianceSeries);
						plot.getRendererForDataset(irradianceDataset).setSeriesPaint(0, ORANGE_COLOR);
						plot.getRendererForDataset(irradianceDataset).setSeriesVisible(0, dataObj.isHave_poa());
						
						for (int i = 0; i < dataExports.size(); i++) {
							DailyDateEntity item = dataExports.get(i);
							RegularTimePeriod period = new Minute(categoryFormat.parse(item.getCategories_time()));
							
							powerSeries.add(period, item.getPower());
							energySeries.add(period, item.getEnergy());
							irradianceSeries.add(period, item.getIrradiance());
						}
						
						// category axis
						DocumentHelper.createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.HOUR, 24, categoryFormat), startDate, endDate).setTickMarkPosition(DateTickMarkPosition.START);
						// left axis
						DocumentHelper.createJFreeChartNumberAxis("kW", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
						// right axis
						DocumentHelper.createJFreeChartNumberAxis("kWh", AxisLocation.BOTTOM_OR_RIGHT, 1, 1, plot);
						// 2nd right axis
						if (dataObj.isHave_poa()) DocumentHelper.createJFreeChartNumberAxis("W/m2", AxisLocation.BOTTOM_OR_RIGHT, 2, 2, plot);
						
						chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1800, 700), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 700));
						document.add(table);
						if (l < dataObjList.size() - 1) document.add(new AreaBreak());
					}
				}
				
				// It must be closed before attach to mail
				document.close();
				
				sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
		
	/**
	 * @description Get daily report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/daily-report")
	public Object getDailyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			ViewReportEntity dataObj = (ViewReportEntity) service.getDailyReport(obj);
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
	
	// end daily report ===================================================================================
	
	// Write header with format
	private static void writeHeaderAnnuallyReport(Sheet sheet, ViewReportEntity report, ViewReportEntity dataObj) {
		try {
			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 35 * 256);
			sheet.setColumnWidth(1, 35 * 256);
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
			sheet.setDefaultRowHeight((short) 500);
			sheet.setDisplayGridlines(false);
			
			CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 22, true);
			CellStyle reportInfoCellStyle = DocumentHelper.createStyleForReportInfo(sheet, false);
			CellStyle reportInfoBoldCellStyle = DocumentHelper.createStyleForReportInfo(sheet, true);
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableHeaderLeftAlignCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			tableHeaderLeftAlignCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowLeftAlignCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			tableRowLeftAlignCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			CellStyle tableRowOneDecimalPlaceCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceDataFormat);
			CellStyle tableTitleCellStyle = DocumentHelper.createStyleForTableTitle(sheet);

			Row row = sheet.createRow(0);
			row.setHeight((short) 600);
			Cell cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("Site Name");

			cell = row.createCell(1);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue(dataObj.getSite_name());

			row = sheet.createRow(1);
			row.setHeight((short) 600);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("Report Date");

			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(dataObj.getReport_date());

			row = sheet.createRow(2);
			row.setHeight((short) 600);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("Covered Period");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(format.format(dateFormat.parse(report.getStart_date())) + " - " + format.format(dateFormat.parse(report.getEnd_date())));

			row = sheet.createRow(3);
			row.setHeight((short) 600);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("System Size (kW DC)");

			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(dataObj.getDc_capacity());

			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellStyle(tableTitleCellStyle);
			cell.setCellValue("Performance Reporting");
			cell = row.createCell(1);
			cell.setCellStyle(tableTitleCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
			
			for (int i = 0; i <= 5; i++) {
				row = sheet.getRow(i) != null ? sheet.getRow(i) : sheet.createRow(i);
				for (int j = 2; j <= 10; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(reportTitleCellStyle);
					if(i == 0 && j == 2) cell.setCellValue("ANNUAL PRODUCTION REPORT");
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(0, 5, 2, 10));

			Row row7 = sheet.createRow(7);
			Cell cell7 = row7.createCell(0);
			cell7.setCellStyle(tableHeaderLeftAlignCellStyle);
			cell7.setCellValue("Monthly Data");
			Cell cell71 = row7.createCell(1);
			cell71.setCellStyle(tableHeaderLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 1));
			
			Row row8 = sheet.createRow(8);
			Cell cell8 = row8.createCell(0);
			cell8.setCellStyle(tableRowLeftAlignCellStyle);
			cell8.setCellValue("Actual Generation (kWh)");
			Cell cell81 = row8.createCell(1);
			cell81.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 1));
			
			Row row9 = sheet.createRow(9);
			Cell cell9 = row9.createCell(0);
			cell9.setCellStyle(tableRowLeftAlignCellStyle);
			cell9.setCellValue("Estimated Generation (kWh)");
			Cell cell91 = row9.createCell(1);
			cell91.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 1));
			
			Row row10 = sheet.createRow(10);
			Cell cell10 = row10.createCell(0);
			cell10.setCellStyle(tableRowLeftAlignCellStyle);
			cell10.setCellValue("Estimated Generation Index (%)");
			Cell cell101 = row10.createCell(1);
			cell101.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(10, 10, 0, 1));
			
			Row row11 = sheet.createRow(11);
			Cell cell11 = row11.createCell(0);
			cell11.setCellStyle(tableHeaderLeftAlignCellStyle);
			cell11.setCellValue("Trailing Twelve Month Generation");
			Cell cell111 = row11.createCell(1);
			cell111.setCellStyle(tableHeaderLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(11, 11, 2, 13));

			Row row12 = sheet.createRow(12);
			Cell cell12 = row12.createCell(0);
			cell12.setCellStyle(tableRowLeftAlignCellStyle);
			cell12.setCellValue("Actual Generation (kWh)");
			sheet.addMergedRegion(new CellRangeAddress(12, 12, 0, 1));
			
			Row row14 = sheet.createRow(14);
			Cell cell14 = row14.createCell(0);
			cell14.setCellStyle(tableRowLeftAlignCellStyle);
			cell14.setCellValue("Estimated Generation Index (%)");
			Cell cell141 = row14.createCell(1);
			cell141.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 1));
			
			Row row13 = sheet.createRow(13);
			Cell cell13 = row13.createCell(0);
			cell13.setCellStyle(tableRowLeftAlignCellStyle);
			cell13.setCellValue("Estimated Generation (kWh)");
			Cell cell131 = row13.createCell(1);
			cell131.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 1));
			
			Row row15 = sheet.createRow(15);
			Cell cell15 = row15.createCell(0);
			cell15.setCellStyle(tableRowLeftAlignCellStyle);
			cell15.setCellValue(dataObj.getDeviceType() == "meter" ? "Site Availability (%)" : "Inverter Availability (%)");
			Cell cell151 = row15.createCell(1);
			cell151.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 1));
			
			List<QuarterlyDateEntity> dataExports = dataObj.getDataReports();
			if(dataExports != null && dataExports.size() > 0) {
				int r = 2;
				for (int i = 0; i < dataExports.size(); i++) {
					QuarterlyDateEntity item = dataExports.get(i);
					
					// Monthly Categories
					cell7 = row7.createCell(r + i);
					cell7.setCellStyle(tableHeaderCellStyle);
					cell7.setCellValue(item.getCategories_time());
					
					// Actual Generation (kWh)
					cell8 = row8.createCell(r + i);
					cell8.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getActual() != null) cell8.setCellValue(item.getActual());
					
					// Estimated Generation (kWh)
					cell9 = row9.createCell(r + i);
					cell9.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getEstimated() != null) cell9.setCellValue(item.getEstimated());
					
					// Estimated Generation Index (%)
					cell10 = row10.createCell(r + i);
					cell10.setCellStyle(tableRowOneDecimalPlaceCellStyle);
					if(item.getDifferencePercentage() != null) cell10.setCellValue(item.getDifferencePercentage());
					
					// Trailing Twelve Month Actual Generation
					// Actual Generation
					cell12 = row12.createCell(r + i);
					cell12.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getActualCumulative() != null) cell12.setCellValue(item.getActualCumulative());
					
					// Estimated Generation (kWh)
					cell13 = row13.createCell(r + i);
					cell13.setCellStyle(tableRowNoDecimalCellStyle);
					if (item.getEstimatedCumulative() != null) cell13.setCellValue(item.getEstimatedCumulative());
					
					// Estimated Generation Index (%)
					cell14 = row14.createCell(r + i);
					cell14.setCellStyle(tableRowOneDecimalPlaceCellStyle);
					if (item.getCumulativeDifferencePercentage() != null) cell14.setCellValue(item.getCumulativeDifferencePercentage());
					
					// Inverter Availability (%)
					cell15 = row15.createCell(r + i);
					cell15.setCellStyle(tableRowOneDecimalPlaceCellStyle);
					if (item.getInverterAvailability() != null) cell15.setCellValue(item.getInverterAvailability());
				}
			}
		} catch (Exception e) {
		}

	}
			
	/**
	 * @description sent mail monthly report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-annually-report")
	public Object sentMailAnnuallyReport(@RequestBody ViewReportEntity obj) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
				
			for (int i = 0; i < dataObjList.size(); i++) {
				ViewReportEntity dataObj = dataObjList.get(i);
				
				if (dataObj != null) {
					List<QuarterlyDateEntity> dataExports = dataObj.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() : 0;
					XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName((i + 1) + "_" + dataObj.getSite_name()));
					sheet.setZoom(95);

					// insert logo image
					ClientAnchor logoAnchor = new XSSFClientAnchor(0, 0, 20 * Units.EMU_PER_PIXEL, 15 * Units.EMU_PER_PIXEL, 12, 1, 13, 4);
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);

					// report information and table
					writeHeaderAnnuallyReport(sheet, obj, dataObj);

					// chart
					ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, 0, 0, 0, 18, 14, 40);
					XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, "Annual Performance");
					
					// data sources
					XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(7, 7, 2, 2 + numOfPoints - 1));
					XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(8, 8, 2, 2 + numOfPoints - 1));
					XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(9, 9, 2, 2 + numOfPoints - 1));
					XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(10, 10, 2, 2 + numOfPoints - 1));

					// category axis
					XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);
					
					// left value axis
					XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, "GENERATION (KWH)");

					XDDFChartData chartData = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
					DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActual() == null), chartData, categoriesData, valuesData1, "Actual Generation (kWh)", XDDFColor.from(PresetColor.STEEL_BLUE), null);
					DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getEstimated() == null), chartData, categoriesData, valuesData2, "Estimated Generation (kWh)", XDDFColor.from(PresetColor.LIGHT_SKY_BLUE), null);
					
					chart.plot(chartData);

					// right value axis
					XDDFValueAxis rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, "PERFORMANCE INDEX (%)");

					chartData = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
					DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getDifferencePercentage() == null), chartData, categoriesData, valuesData3, "Estimated Generation Index (%)", XDDFColor.from(PresetColor.GRAY), null);
					
					chart.plot(chartData);
				}
			}
				
			sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name());
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * @description sent mail annually report in pdf
	 * @author Hung.Bui
	 * @since 2022-11-29
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-pdf-annually-report")
	public Object sentMailPdfAnnuallyReport(@RequestBody ViewReportEntity obj) {
		try {
			File file = createPdfFile(obj.getCadence_range_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				List<ViewReportEntity> dataObjList = getReportDataList(obj);
				if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
					ViewReportEntity dataObj = dataObjList.get(l);
				
					if (dataObj != null) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date startDate = dateFormat.parse(obj.getStart_date());
						Date endDate = dateFormat.parse(obj.getEnd_date());
						dataObj.setStart_date(new SimpleDateFormat("MM/dd/yyyy").format(startDate));
						dataObj.setEnd_date(new SimpleDateFormat("MM/dd/yyyy").format(endDate));
						
						List<QuarterlyDateEntity> dataExports = dataObj.getDataReports();
						
						// total column: 14
						Table table = new Table(14).useAllAvailableWidth();
						table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
						table.setFontSize(8);
						table.setTextAlignment(TextAlignment.CENTER);
					
						//====== table ============================================================
						// header and logo
						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setHeight(14).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(6, 6).add(new Paragraph("ANNUAL PRODUCTION REPORT")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell(6, 4).add(logoImage).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Site Name").setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getSite_name()).setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Report Date").setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getReport_date()).setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Covered Period").setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getStart_date() + " - " + dataObj.getEnd_date()).setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("System Size (kW DC)").setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(String.valueOf(dataObj.getDc_capacity())).setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setHeight(14).setBorder(Border.NO_BORDER));
						
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Performance Reporting")).setBold().setBackgroundColor(new DeviceRgb(117, 117, 117)).setFontColor(DeviceGray.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 14).setHeight(14).setBorder(Border.NO_BORDER));
						
						
						// data table
						DecimalFormat df = new DecimalFormat(DocumentHelper.noDecimalDataFormat);
						DecimalFormat dfp = new DecimalFormat(DocumentHelper.oneDecimalPlaceDataFormat);
	
						// Monthly Data
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Monthly Data").setBold()).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = dataExports.get(i);
							table.addCell(new Paragraph(item.getCategories_time()).setBold());
						}
						
						// Actual Generation (kWh)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Actual Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = dataExports.get(i);
							table.addCell(item.getActual() != null ? df.format(item.getActual()) : "");
						}
						
						// Estimated Generation (kWh)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = dataExports.get(i);
							table.addCell(item.getEstimated() != null ? df.format(item.getEstimated()) : "");
						}
						
						// Estimated Generation Index (%)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation Index (%)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = dataExports.get(i);
							table.addCell(item.getDifferencePercentage() != null ? dfp.format(item.getDifferencePercentage()) : "");
						}
						
						// Trailing Twelve Month Generation
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Trailing Twelve Month Generation").setBold()).setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 12));
						
						// Actual Generation (kWh)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Actual Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = dataExports.get(i);
							table.addCell(item.getActualCumulative() != null ? df.format(item.getActualCumulative()) : "");
						}
						
						// Estimated Generation (kWh)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = dataExports.get(i);
							table.addCell(item.getEstimatedCumulative() != null ? df.format(item.getEstimatedCumulative()) : "");
						}
						
						// Baseline Generation Index (%)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation Index (%)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = dataExports.get(i);
							table.addCell(item.getCumulativeDifferencePercentage() != null ? dfp.format(item.getCumulativeDifferencePercentage()) : "");
						}
						
						// Inverter Availability (%)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getDeviceType() == "meter" ? "Site Availability (%)" : "Inverter Availability (%)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = dataExports.get(i);
							table.addCell(item.getInverterAvailability() != null ? dfp.format(item.getInverterAvailability()) : "");
						}
	
						// empty row: gap between data table and chart
						table.addCell(new com.itextpdf.layout.element.Cell(1, 14).setHeight(14).setBorder(Border.NO_BORDER));
						
						// chart
						com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(22, 14);
						table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
						
						//====== chart ============================================================
						JFreeChart chart = DocumentHelper.createJFreeChart("Annual Performance");
						XYPlot plot = chart.getXYPlot();
						
						// data source
						TimeSeriesCollection barDataset = DocumentHelper.createJFreeChartBarDataset(0, plot);
						TimeSeries actualSeries = new TimeSeries("Actual Generation (kWh)");
						barDataset.addSeries(actualSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(0, BLUE_COLOR);
						TimeSeries estimateSeries = new TimeSeries("Estimate Generation (kWh)");
						barDataset.addSeries(estimateSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(1, LIGHT_BLUE_COLOR);
						
						TimeSeriesCollection lineDataset = DocumentHelper.createJFreeChartLineDataset(1, plot);
						TimeSeries estimateIndexSeries = new TimeSeries("Estimate Generation Index (%)");
						lineDataset.addSeries(estimateIndexSeries);
						plot.getRendererForDataset(lineDataset).setSeriesPaint(0, Color.gray);
						
						SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
						SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
						SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM-yyyy");
						for (int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = dataExports.get(i);
							Month period = new Month(monthYearFormat.parse(item.getCategories_time() + '-' + yearFormat.format(endDate)));
							
							actualSeries.add(period, item.getActual());
							estimateSeries.add(period, item.getEstimated());
							estimateIndexSeries.add(period, item.getDifferencePercentage());
						}
						
						// category axis
						DocumentHelper.createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.MONTH, 1, monthFormat), startDate, endDate);
						// left axis
						DocumentHelper.createJFreeChartNumberAxis("GENERATION (KWH)", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
						// right axis
						DocumentHelper.createJFreeChartNumberAxis("PERFORMANCE INDEX (%)", AxisLocation.BOTTOM_OR_RIGHT, 1, 1, plot);
						
						chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1650, 600), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 400));
						document.add(table);
						if (l < dataObjList.size() - 1) document.add(new AreaBreak());
					}
				}
				
				// It must be closed before attach to mail
				document.close();
				
				sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
		
	/**
	 * @description Get annually report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/annually-report")
	public Object getAnnuallyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();
			ViewReportEntity dataObj = (ViewReportEntity) service.getAnnuallyReport(obj);
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
	
	// end annually report ===================================================================================

	
	
	
	// Write header with format
		private static void writeHeaderQuarterlyReport(Sheet sheet, ViewReportEntity dataObj) {
			try {
				sheet.setDefaultColumnWidth(16);
				sheet.setColumnWidth(0, 25 * 256);
				sheet.setColumnWidth(1, 20 * 256);
				sheet.setColumnWidth(2, 20 * 256);
				sheet.setColumnWidth(3, 20 * 256);
				sheet.setColumnWidth(4, 20 * 256);
				sheet.setColumnWidth(5, 10 * 256);
				sheet.setColumnWidth(6, 20 * 256);
				sheet.setColumnWidth(7, 20 * 256);
				sheet.setColumnWidth(8, 20 * 256);
				sheet.setColumnWidth(9, 20 * 256);
				sheet.setColumnWidth(10, 20 * 256);
				sheet.setDisplayGridlines(false);
				
				CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 22, true);
				CellStyle reportInfoCellStyle = DocumentHelper.createStyleForReportInfo(sheet, false);
				CellStyle reportInfoBoldCellStyle = DocumentHelper.createStyleForReportInfo(sheet, true);
				CellStyle tableTitleCellStyle = DocumentHelper.createStyleForTableTitle(sheet);
				CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
				CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
				CellStyle tableRowOneDecimalPlaceCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceDataFormat);
				CellStyle tableRowFourDecimalPlaceCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.fourDecimalPlaceDataFormat);
				CellStyle tableRowNoDecimalBoldCellStyle = DocumentHelper.createStyleForNoBorderTableRowNumber(sheet, true, null);
				tableRowNoDecimalBoldCellStyle.setBorderTop(BorderStyle.MEDIUM);
				tableRowNoDecimalBoldCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				CellStyle tableRowOneDecimalPlaceBoldCellStyle = DocumentHelper.createStyleForNoBorderTableRowNumber(sheet, true, DocumentHelper.oneDecimalPlaceDataFormat);
				tableRowOneDecimalPlaceBoldCellStyle.setBorderTop(BorderStyle.MEDIUM);
				tableRowOneDecimalPlaceBoldCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				CellStyle tableRowNoDecimalBlueBgCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
				tableRowNoDecimalBlueBgCellStyle.setFillBackgroundColor(IndexedColors.PALE_BLUE.index);
				tableRowNoDecimalBlueBgCellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
				tableRowNoDecimalBlueBgCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
				CellStyle tableRowNoDecimalRedTextCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
				Font redFont = sheet.getWorkbook().createFont();
				redFont.setFontName("Times New Roman");
				redFont.setFontHeightInPoints((short) 12);
				redFont.setColor(IndexedColors.RED.getIndex());
				tableRowNoDecimalRedTextCellStyle.setFont(redFont);
				CellStyle tableRowOneDecimalPlaceRedTextCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceDataFormat);
				tableRowOneDecimalPlaceRedTextCellStyle.setFont(redFont);
				
				boolean quarterlyReportByMonth = dataObj.getData_intervals() == Constants.MONTHLY_INTERVAL;

				Row row = sheet.createRow(0);
				row.setHeight((short) 600);
				Cell cell = row.createCell(0);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("Site Name");

				cell = row.createCell(1);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue(dataObj.getSite_name());
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoBoldCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));

				row = sheet.createRow(1);
				row.setHeight((short) 600);
				cell = row.createCell(0);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("Report Date");

				cell = row.createCell(1);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(dataObj.getReport_date());
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));

				row = sheet.createRow(2);
				row.setHeight((short) 600);
				cell = row.createCell(0);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("Covered Period");

				cell = row.createCell(1);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(dataObj.getStart_date() + " - " + dataObj.getEnd_date());
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));

				row = sheet.createRow(3);
				row.setHeight((short) 600);
				cell = row.createCell(0);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("System Size (kW DC)");

				cell = row.createCell(1);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(dataObj.getDc_capacity());
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 2));
				
				for (int i = 0; i <= 5; i++) {
					row = sheet.getRow(i) != null ? sheet.getRow(i) : sheet.createRow(i);
					for (int j = 3; j <= (quarterlyReportByMonth ? 10 : 8); j++) {
						cell = row.createCell(j);
						cell.setCellStyle(reportTitleCellStyle);
						if(i == 0 && j == 3) cell.setCellValue("QUARTERLY PRODUCTION REPORT - " + LocalDate.parse(dataObj.getStart_date(), DateTimeFormatter.ofPattern("MM/dd/yyyy")).format(DateTimeFormatter.ofPattern("QQQ/yyyy")));
					}
				}
				sheet.addMergedRegion(new CellRangeAddress(0, 5, 3, (quarterlyReportByMonth ? 10 : 8)));
				
				row = sheet.createRow(6);
				cell = row.createCell(0);
				cell.setCellStyle(tableTitleCellStyle);
				cell.setCellValue("Performance Reporting");
				cell = row.createCell(1);
				cell.setCellStyle(tableTitleCellStyle);
				cell = row.createCell(2);
				cell.setCellStyle(tableTitleCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 2));
				
				List<QuarterlyDateEntity> dataExports = dataObj.getDataReports();
				if (quarterlyReportByMonth) {
					row = sheet.createRow(9);
					cell = row.createCell(0);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Monthly kWh Production");
					for (int i = 1; i <= 4; i++) {
						cell = row.createCell(i);
						cell.setCellStyle(tableHeaderCellStyle);
					}
					sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 4));
					
					row = sheet.createRow(10);
					row.setHeight((short) 800);
					cell = row.createCell(0);
					cell.setCellStyle(tableHeaderCellStyle);
					
					cell = row.createCell(1);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Estimated Generation (kWh)");
					
					cell = row.createCell(2);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Actual Generation (kWh)");
					
					cell = row.createCell(3);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Difference (kWh)");
					
					cell = row.createCell(4);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Difference (%)");
					
					
					row = sheet.createRow(26);
					cell = row.createCell(0);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Cumulative kWh Production");
					for (int i = 1; i <= 4; i++) {
						cell = row.createCell(i);
						cell.setCellStyle(tableHeaderCellStyle);
					}
					sheet.addMergedRegion(new CellRangeAddress(26, 26, 0, 4));
					
					row = sheet.createRow(27);
					row.setHeight((short) 800);
					cell = row.createCell(0);
					cell.setCellStyle(tableHeaderCellStyle);
					
					cell = row.createCell(1);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Estimated Generation (kWh)");
					
					cell = row.createCell(2);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Actual Generation (kWh)");
					
					cell = row.createCell(3);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Difference (kWh)");
					
					cell = row.createCell(4);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Difference (%)");
					
					if(dataExports != null && dataExports.size() > 0) {
						for(int i = 0; i < dataExports.size(); i++) {
							QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
							
							// monthly
							row = sheet.createRow(11 + i);
							cell = row.createCell(0);
							cell.setCellStyle(tableHeaderCellStyle);
							cell.setCellValue(item.getCategories_time());
							
							cell = row.createCell(1);
							cell.setCellStyle(tableRowNoDecimalCellStyle);
							if(item.getEstimated() != null) cell.setCellValue(item.getEstimated());
							
							cell = row.createCell(2);
							cell.setCellStyle(tableRowNoDecimalBlueBgCellStyle);
							if(item.getActual() != null) cell.setCellValue(item.getActual());
		
							cell = row.createCell(3);
							cell.setCellStyle(item.getDifference() != null && item.getDifference() < 0 ? tableRowNoDecimalRedTextCellStyle : tableRowNoDecimalCellStyle);
							if(item.getDifference() != null) cell.setCellValue(item.getDifference());
							
							cell = row.createCell(4);
							cell.setCellStyle(item.getDifferencePercentage() != null && item.getDifferencePercentage() < 0 ? tableRowOneDecimalPlaceRedTextCellStyle : tableRowOneDecimalPlaceCellStyle);
							if(item.getDifferencePercentage() != null) cell.setCellValue(item.getDifferencePercentage());
							
							// quarterly total
							if (item.getActualCumulative() != null) {
								row = sheet.createRow(22);
								cell = row.createCell(0);
								cell.setCellStyle(tableRowNoDecimalBoldCellStyle);
								cell.setCellValue("Total");
								
								cell = row.createCell(1);
								cell.setCellStyle(tableRowNoDecimalBoldCellStyle);
								if(item.getEstimatedCumulative() != null) cell.setCellValue(item.getEstimatedCumulative());
								
								cell = row.createCell(2);
								cell.setCellStyle(tableRowNoDecimalBoldCellStyle);
								if(item.getActualCumulative() != null) cell.setCellValue(item.getActualCumulative());
								
								cell = row.createCell(3);
								cell.setCellStyle(tableRowNoDecimalBoldCellStyle);
								if(item.getCumulativeDifference() != null) cell.setCellValue(item.getCumulativeDifference());
								
								cell = row.createCell(4);
								cell.setCellStyle(tableRowOneDecimalPlaceBoldCellStyle);
								if(item.getCumulativeDifferencePercentage() != null) cell.setCellValue(item.getCumulativeDifferencePercentage());
							}

							// cumulative
							row = sheet.createRow(28 + i);
							cell = row.createCell(0);
							cell.setCellStyle(tableHeaderCellStyle);
							cell.setCellValue(item.getCategories_time());
							
							cell = row.createCell(1);
							cell.setCellStyle(tableRowNoDecimalCellStyle);
							if(item.getEstimatedCumulative() != null) cell.setCellValue(item.getEstimatedCumulative());
							
							cell = row.createCell(2);
							cell.setCellStyle(tableRowNoDecimalBlueBgCellStyle);
							if(item.getActual() != null) cell.setCellValue(item.getActualCumulative());
							
							cell = row.createCell(3);
							cell.setCellStyle(item.getCumulativeDifference() != null && item.getCumulativeDifference() < 0 ? tableRowNoDecimalRedTextCellStyle : tableRowNoDecimalCellStyle);
							if(item.getCumulativeDifference() != null) cell.setCellValue(item.getCumulativeDifference());
							
							cell = row.createCell(4);
							cell.setCellStyle(item.getCumulativeDifferencePercentage() != null && item.getCumulativeDifferencePercentage() < 0 ? tableRowOneDecimalPlaceRedTextCellStyle : tableRowOneDecimalPlaceCellStyle);
							if(item.getCumulativeDifferencePercentage() != null) cell.setCellValue(item.getCumulativeDifferencePercentage());
						}
					}
				} else {
					row = sheet.createRow(8);
					cell = row.createCell(0);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Date");
					
					cell = row.createCell(1);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Daily System Production (kWh)");
					cell = row.createCell(2);
					cell.setCellStyle(tableHeaderCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(8, 8, 1, 2));
					
					cell = row.createCell(3);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Daily POA Insolation (kWh/m²)");
					cell = row.createCell(4);
					cell.setCellStyle(tableHeaderCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(8, 8, 3, 4));
					
					cell = row.createCell(5);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("TCell (°C)");
					cell = row.createCell(6);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("");
					sheet.addMergedRegion(new CellRangeAddress(8, 8, 5, 6));
					
					cell = row.createCell(7);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Temperature Corrected PR (%)");
					cell = row.createCell(8);
					cell.setCellStyle(tableHeaderCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(8, 8, 7, 8));
					
					cell = row.createCell(9);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Inverter Availability (%)");
					cell = row.createCell(10);
					cell.setCellStyle(tableHeaderCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(8, 8, 9, 10));
					
					if(dataExports != null && dataExports.size() > 0) {
						for(int i = 0; i < dataExports.size(); i++ ) {
							QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
							
							row = sheet.createRow(9 + i);
							cell = row.createCell(0);
							cell.setCellStyle(tableHeaderCellStyle);
							cell.setCellValue(item.getCategories_time());
							
							cell = row.createCell(1);
							cell.setCellStyle(tableRowNoDecimalCellStyle);
							if(item.getActual() != null) cell.setCellValue(item.getActual());
							cell = row.createCell(2);
							cell.setCellStyle(tableRowNoDecimalCellStyle);
							sheet.addMergedRegion(new CellRangeAddress(9 + i, 9 + i, 1, 2));
							
							cell = row.createCell(3);
							cell.setCellStyle(tableRowFourDecimalPlaceCellStyle);
							if(item.getPOAInsolation() != null) cell.setCellValue(item.getPOAInsolation());
							cell = row.createCell(4);
							cell.setCellStyle(tableRowFourDecimalPlaceCellStyle);
							sheet.addMergedRegion(new CellRangeAddress(9 + i, 9 + i, 3, 4));
							
							cell = row.createCell(5);
							cell.setCellStyle(tableRowOneDecimalPlaceCellStyle);
							if(item.getTCellAVG() != null) cell.setCellValue(item.getTCellAVG());
							cell = row.createCell(6);
							cell.setCellStyle(tableRowOneDecimalPlaceCellStyle);
							sheet.addMergedRegion(new CellRangeAddress(9 + i, 9 + i, 5, 6));
							
							cell = row.createCell(7);
							cell.setCellStyle(tableRowOneDecimalPlaceCellStyle);
							if(item.getTemperatureCorrected() != null) cell.setCellValue(item.getTemperatureCorrected());
							cell = row.createCell(8);
							cell.setCellStyle(tableRowOneDecimalPlaceCellStyle);
							sheet.addMergedRegion(new CellRangeAddress(9 + i, 9 + i, 7, 8));
							
							cell = row.createCell(9);
							cell.setCellStyle(tableRowOneDecimalPlaceCellStyle);
							if(item.getInverterAvailability() != null) cell.setCellValue(item.getInverterAvailability());
							cell = row.createCell(10);
							cell.setCellStyle(tableRowOneDecimalPlaceCellStyle);
							sheet.addMergedRegion(new CellRangeAddress(9 + i, 9 + i, 9, 10));
						}
					}
				}
			} catch (Exception e) {
			}

		}
		
	/**
	 * @description sent mail monthly report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-quarterly-report")
	public Object sentMailQuarterlyReport(@RequestBody ViewReportEntity obj) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
				
			for (int i = 0; i < dataObjList.size(); i++) {
				ViewReportEntity dataObj = dataObjList.get(i);
					
				if (dataObj != null) {
					boolean quarterlyReportByMonth = dataObj.getData_intervals() == Constants.MONTHLY_INTERVAL;

					SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					dataObj.setStart_date(dateFormat.format(dateTimeFormat.parse(obj.getStart_date())));
					dataObj.setEnd_date(dateFormat.format(dateTimeFormat.parse(obj.getEnd_date())));
					List<QuarterlyDateEntity> dataExports = dataObj.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() : 0;

					XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName((i + 1) + "_" + dataObj.getSite_name()));
					
					// insert logo image
					ClientAnchor logoAnchor = quarterlyReportByMonth ? new XSSFClientAnchor(0, -15 * Units.EMU_PER_PIXEL, 20 * Units.EMU_PER_PIXEL, 15 * Units.EMU_PER_PIXEL, 11, 1, 12, 4) : new XSSFClientAnchor(0, -15 * Units.EMU_PER_PIXEL, 0, 15 * Units.EMU_PER_PIXEL, 9, 1, 10, 4);
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);

					// report information and table
					writeHeaderQuarterlyReport(sheet, dataObj);
					
					if (quarterlyReportByMonth && numOfPoints > 0) {
						// 1st chart
						ClientAnchor chartAnchor = new XSSFClientAnchor(6, 6, 6, 6, 6, 9, 13, 23);
						XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, null);
						
						// data source
						XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(11, 13, 0, 0));
						XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(11, 13, 1, 1));
						XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(11, 13, 2, 2));
	
						// category axis
						XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);
						
						// left value axis
						XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, null);
						
						XDDFChartData data = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getEstimated() == null), data, categoriesData, valuesData1, "Estimated Generation (kWh)", XDDFColor.from(PresetColor.STEEL_BLUE), null);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActual() == null), data, categoriesData, valuesData2, "Actual Generation (kWh)", XDDFColor.from(PresetColor.LIGHT_SKY_BLUE), null);
						
						chart.plot(data);
					
						// 2nd chart
						chartAnchor = new XSSFClientAnchor(6, 6, 6, 6, 6, 26, 13, 39);
						chart = DocumentHelper.insertChart(sheet, chartAnchor, null);
					    
						XDDFDataSource<String> categoriesData2 = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(28, 30, 0, 0));
						XDDFNumericalDataSource<Double> valuesData12 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(28, 30, 1, 1));
						XDDFNumericalDataSource<Double> valuesData22 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(28, 30, 2, 2));
					    
						// category axis
						bottomAxis = DocumentHelper.createCategoryAxis(chart);
						
						// left value axis
						leftAxis = DocumentHelper.createLeftValueAxis(chart, null);
						
						data = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getEstimatedCumulative() == null), data, categoriesData2, valuesData12, "Estimated Generation (kWh)", XDDFColor.from(PresetColor.STEEL_BLUE), null);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActualCumulative() == null), data, categoriesData2, valuesData22, "Actual Generation (kWh)", XDDFColor.from(PresetColor.LIGHT_SKY_BLUE), null);
	
						chart.plot(data);
					}
				}
			}
					
			sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name());
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * @description sent mail quarterly report in pdf
	 * @author Hung.Bui
	 * @since 2022-12-05
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-pdf-quarterly-report")
	public Object sentMailPdfQuarterlyReport(@RequestBody ViewReportEntity obj) {
		try {
			File file = createPdfFile(obj.getCadence_range_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				List<ViewReportEntity> dataObjList = getReportDataList(obj);
				if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
					ViewReportEntity dataObj = dataObjList.get(l);
					
					if (dataObj != null) {
						boolean quarterlyReportByMonth = dataObj.getData_intervals() == Constants.MONTHLY_INTERVAL;
	
						SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM-yyyy");
						Date startDate = dateTimeFormat.parse(obj.getStart_date());
						Date endDate = dateTimeFormat.parse(obj.getEnd_date());
						dataObj.setStart_date(dateFormat.format(startDate));
						dataObj.setEnd_date(dateFormat.format(endDate));
						DecimalFormat df = new DecimalFormat(DocumentHelper.noDecimalDataFormat);
						DecimalFormat dfp = new DecimalFormat(DocumentHelper.oneDecimalPlaceDataFormat);
						DecimalFormat dfp4 = new DecimalFormat(DocumentHelper.fourDecimalPlaceDataFormat);
						
						List<QuarterlyDateEntity> dataExports = dataObj.getDataReports() != null ? dataObj.getDataReports() : new ArrayList<>();
						
						// total column: 13
						final float[] columnWidths = {15, 15, 15, 15, 15, 3, 3, 3, 3, 3, 3, 3, 4};
						Table table = quarterlyReportByMonth ? new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth() : new Table(13).useAllAvailableWidth();
						table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
						table.setFontSize(8);
						table.setTextAlignment(TextAlignment.CENTER);
						table.setMarginTop(quarterlyReportByMonth ? 75 : 0); // align table in middle of page
						
						//====== table ============================================================
						// header and logo
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(6, 8).add(new Paragraph("QUARTERLY PRODUCTION REPORT - " + LocalDate.parse(dataObj.getStart_date(), DateTimeFormatter.ofPattern("MM/dd/yyyy")).format(DateTimeFormatter.ofPattern("QQQ/yyyy")))).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell(6, 2).add(logoImage).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
						table.addCell(new Paragraph("Site Name").setBold().setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getSite_name()).setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new Paragraph("Report Date").setBold().setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getReport_date()).setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new Paragraph("Covered Period").setBold().setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getStart_date() + " - " + dataObj.getEnd_date()).setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new Paragraph("System Size (kW DC)").setBold().setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(String.valueOf(dataObj.getDc_capacity())).setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
						
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Performance Reporting")).setBold().setBackgroundColor(new DeviceRgb(117, 117, 117)).setFontColor(DeviceGray.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 10).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 13).setHeight(14).setBorder(Border.NO_BORDER));
						
						if (quarterlyReportByMonth) {
							// first table
							table.addCell(new com.itextpdf.layout.element.Cell(1, 5).add(new Paragraph("Monthly kWh Production").setBold()));
							
							// empty column: gap between data table and chart
							table.addCell(new com.itextpdf.layout.element.Cell(14, 1).setBorder(Border.NO_BORDER));
							
							// first chart
							com.itextpdf.layout.element.Cell chartCell1 = new com.itextpdf.layout.element.Cell(14, 7);
							table.addCell(chartCell1.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
							
							table.addCell("");
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Estimated Generation\n(kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Actual Generation\n(kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Difference\n(kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Difference\n(%)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							
							// data table
							QuarterlyDateEntity total = null;
							for(int i = 0; i < dataExports.size(); i++ ) {
								QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
								
								table.addCell(new Paragraph(item.getCategories_time()).setBold());
								table.addCell(item.getEstimated() != null ? df.format(item.getEstimated()) : "");
								table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(item.getActual() != null ? df.format(item.getActual()) : "")).setBackgroundColor(new DeviceRgb(133, 197, 251)));
								table.addCell(new Paragraph(item.getDifference() != null ? df.format(item.getDifference()) : "").setFontColor(item.getDifference() != null && item.getDifference() < 0 ? new DeviceRgb(255, 0, 0) : null));
								table.addCell(new Paragraph(item.getDifferencePercentage() != null ? dfp.format(item.getDifferencePercentage()) : "").setFontColor(item.getDifferencePercentage() != null && item.getDifferencePercentage() < 0 ? new DeviceRgb(255, 0, 0) : null));
								
								if(item.getActualCumulative() != null) total = item;
							}
							if(dataExports.size() == 0) for(int i = 0; i < 3; i++) for(int j = 0; j < 5; j++) table.addCell("");
							
							// total
							table.addCell(new com.itextpdf.layout.element.Cell(8, 5).add(new Paragraph("\n\n\n\n\n\n\n\n\n\n")).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Total")).setBold().setBorder(Border.NO_BORDER));
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(total != null && total.getEstimatedCumulative() != null ? df.format(total.getEstimatedCumulative()) : "")).setBold().setBorder(Border.NO_BORDER));
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(total != null && total.getActualCumulative() != null ? df.format(total.getActualCumulative()) : "")).setBold().setBorder(Border.NO_BORDER));
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(total != null && total.getCumulativeDifference() != null ? df.format(total.getCumulativeDifference()) : "")).setBold().setBorder(Border.NO_BORDER));
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(total != null && total.getCumulativeDifferencePercentage() != null ? dfp.format(total.getCumulativeDifferencePercentage()) : "")).setBold().setBorder(Border.NO_BORDER));
							
							// gap between 2 table
							table.addCell(new com.itextpdf.layout.element.Cell(1, 13).setHeight(14).setBorder(Border.NO_BORDER));
							
							// second table
							table.addCell(new com.itextpdf.layout.element.Cell(1, 5).add(new Paragraph("Cumulative kWh Production").setBold()));
							
							// empty column: gap between data table and chart
							table.addCell(new com.itextpdf.layout.element.Cell(14, 1).setBorder(Border.NO_BORDER));
							
							// second chart
							com.itextpdf.layout.element.Cell chartCell2 = new com.itextpdf.layout.element.Cell(14, 7);
							table.addCell(chartCell2.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
							
							table.addCell("");
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Estimated Generation\n(kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Actual Generation\n(kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Difference\n(kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Difference\n(%)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							
							for(int i = 0; i < dataExports.size(); i++ ) {
								QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
								
								table.addCell(new Paragraph(item.getCategories_time()).setBold());
								table.addCell(item.getEstimatedCumulative() != null ? df.format(item.getEstimatedCumulative()) : "");
								table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(item.getActualCumulative() != null ? df.format(item.getActualCumulative()) : "")).setBackgroundColor(new DeviceRgb(133, 197, 251)));
								table.addCell(new Paragraph(item.getCumulativeDifference() != null ? df.format(item.getCumulativeDifference()) : "").setFontColor(item.getCumulativeDifference() != null && item.getCumulativeDifference() < 0 ? new DeviceRgb(255, 0, 0) : null));
								table.addCell(new Paragraph(item.getCumulativeDifferencePercentage() != null ? dfp.format(item.getCumulativeDifferencePercentage()) : "").setFontColor(item.getCumulativeDifferencePercentage() != null && item.getCumulativeDifferencePercentage() < 0 ? new DeviceRgb(255, 0, 0) : null));
							}
							if(dataExports.size() == 0) for(int i = 0; i < 3; i++) for(int j = 0; j < 5; j++) table.addCell("");
		
							// empty rows
							table.addCell(new com.itextpdf.layout.element.Cell(8, 5).add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n")).setBorder(Border.NO_BORDER));
							
							//====== first chart ============================================================
							JFreeChart chart = DocumentHelper.createJFreeChart(null);
							XYPlot plot = chart.getXYPlot();
							
							// data source
							TimeSeriesCollection barDataset = DocumentHelper.createJFreeChartBarDataset(0, plot);
							TimeSeries estimateSeries = new TimeSeries("Estimate Generation (kWh)");
							barDataset.addSeries(estimateSeries);
							plot.getRendererForDataset(barDataset).setSeriesPaint(0, BLUE_COLOR);
							TimeSeries actualSeries = new TimeSeries("Actual Generation (kWh)");
							barDataset.addSeries(actualSeries);
							plot.getRendererForDataset(barDataset).setSeriesPaint(1, LIGHT_BLUE_COLOR);
							
							for (int i = 0; i < dataExports.size(); i++) {
								QuarterlyDateEntity item = dataExports.get(i);
								RegularTimePeriod period = new Month(monthYearFormat.parse(item.getCategories_time()));
								
								estimateSeries.add(period, item.getEstimated());
								actualSeries.add(period, item.getActual());
							}
							
							// category axis
							DocumentHelper.createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.MONTH, 1, monthYearFormat), startDate, endDate);
							// left axis
							DocumentHelper.createJFreeChartNumberAxis(null, AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
							
							chartCell1.add(new Image(ImageDataFactory.create(chart.createBufferedImage(900, 300), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 300));
							
							//====== second chart ============================================================
							JFreeChart chart2 = DocumentHelper.createJFreeChart(null);
							XYPlot plot2 = chart2.getXYPlot();
							
							// data source
							TimeSeriesCollection barDataset2 = DocumentHelper.createJFreeChartBarDataset(0, plot2);
							TimeSeries estimatedCumulativeSeries = new TimeSeries("Estimate Generation (kWh)");
							barDataset2.addSeries(estimatedCumulativeSeries);
							plot2.getRendererForDataset(barDataset2).setSeriesPaint(0, BLUE_COLOR);
							TimeSeries actualCumulativeSeries = new TimeSeries("Actual Generation (kWh)");
							barDataset2.addSeries(actualCumulativeSeries);
							plot2.getRendererForDataset(barDataset2).setSeriesPaint(1, LIGHT_BLUE_COLOR);
							
							for (int i = 0; i < dataExports.size(); i++) {
								QuarterlyDateEntity item = dataExports.get(i);
								RegularTimePeriod period = new Month(monthYearFormat.parse(item.getCategories_time()));
								
								estimatedCumulativeSeries.add(period, item.getEstimatedCumulative());
								actualCumulativeSeries.add(period, item.getActualCumulative());
							}
							
							// category axis
							DocumentHelper.createJFreeChartDomainAxis(plot2, new DateTickUnit(DateTickUnitType.MONTH, 1, monthYearFormat), startDate, endDate);
							// left axis
							DocumentHelper.createJFreeChartNumberAxis(null, AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot2);
							
							chartCell2.add(new Image(ImageDataFactory.create(chart2.createBufferedImage(900, 300), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 300));
						} else {
							table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Date")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Daily System Production (kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Daily POA Insolation (kWh/m²)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("TCell (°C)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Temperature Corrected PR (%)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Inverter Availability (%)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
							
							// data table
							for(int i = 0; i < dataExports.size(); i++) {
								QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
								
								table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph(item.getCategories_time()).setBold()));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(item.getActual() != null ? df.format(item.getActual()) : "")));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getPOAInsolation() != null ? dfp4.format(item.getPOAInsolation()) : "")));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(item.getTCellAVG() != null ? dfp.format(item.getTCellAVG()) : "")));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getTemperatureCorrected() != null ? dfp.format(item.getTemperatureCorrected()) : "")));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(item.getInverterAvailability() != null ? dfp.format(item.getInverterAvailability()) : "")));
							}
						}
						
						document.add(table);
						if (l < dataObjList.size() - 1) document.add(new AreaBreak());
					}
				}
				
				// It must be closed before attach to mail
				document.close();

				sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * @description Get customer view chart data
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/quarterly-report")
	public Object getQuarterlyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();

			ViewReportEntity dataObj = (ViewReportEntity) service.getQuarterlyReport(obj);

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
	 * @description Get asset management and performance report
	 * @author Hung.Bui
	 * @since 2024-06-10
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/asset-management-and-operation-performance-report")
	public Object getAssetManagementAndOperationPerformanceReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();

			AssetManagementAndOperationPerformanceReportEntity dataObj = (AssetManagementAndOperationPerformanceReportEntity) service.getAssetManagementAndOperationPerformanceReport(obj);

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
	 * @description Get data list in multi threads
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param ids, id, data_intervals, start_date, end_date
	 * @return data list
	 */
	private List<ViewReportEntity> getReportDataList(ViewReportEntity reportObj) {
		try {
			ReportsService service = new ReportsService();
			List<CompletableFuture<ViewReportEntity>> list = new ArrayList<CompletableFuture<ViewReportEntity>>();
			
			if (reportObj.getIds() != null && reportObj.getIds().size() > 0) {
				for (int i = 0; i < reportObj.getIds().size(); i++) {
					ViewReportEntity siteObj = new ViewReportEntity();
					siteObj.setId_site((int) reportObj.getIds().get(i));
					siteObj.setId(reportObj.getId());
					siteObj.setData_intervals(reportObj.getData_intervals());
					siteObj.setCadence_range(reportObj.getCadence_range());
					siteObj.setStart_date(reportObj.getStart_date());
					siteObj.setEnd_date(reportObj.getEnd_date());
					
					CompletableFuture<ViewReportEntity> future = CompletableFuture.supplyAsync(() -> {
						try {
							ViewReportEntity data = null;
							if (reportObj.getCadence_range() == 1) data = (ViewReportEntity) service.getDailyReport(siteObj);
							else if (reportObj.getCadence_range() == 2) data = (ViewReportEntity) service.getMonthlyReport(siteObj);
							else if (reportObj.getCadence_range() == 3) data = (ViewReportEntity) service.getQuarterlyReport(siteObj);
							else if (reportObj.getCadence_range() == 4) data = (ViewReportEntity) service.getAnnuallyReport(siteObj);
							else if (reportObj.getCadence_range() == 5) data = (ViewReportEntity) service.getCustomReport(siteObj);
							
							return data;
						} catch (Exception ex) {
							log.error(ex);
							return null;
						}
					});
					
					list.add(future);
				}
			}
			
			CompletableFuture<Void> combinedFutures = CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
			return combinedFutures.thenApply(__ -> list.stream().map(future -> future.join()).collect(Collectors.toList())).get();
		} catch (Exception e) {
			return new ArrayList<>();
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
		String fileName = dir + "/" + cadenceRangeName + "-report-" + timeStamp + ".xlsx";
		
		try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
			// Write the output to a file
			document.write(fileOut);
			String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
			String msgTemplate = Constants.getMailTempleteByState(16);
			String body = String.format(msgTemplate, "", "", "Customer", cadenceRangeName + " ", "", "");
			String mailTo = subscribers;
			String subject = Constants.getMailSubjectByState(16);

			String tags = "report_" + cadenceRangeName.toLowerCase();
			String fromName = "NEXT WAVE ENERGY MONITORING INC";
			boolean flagSent = SendMail.SendGmailTLSAttachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
			if (!flagSent) {
				throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
			}
		}
	}
	
	/**
	 * @description create pdf file
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param cadenceRange
	 */
	private File createPdfFile(String cadenceRangeName) throws Exception {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		String dir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
		String fileName = dir + "/" + cadenceRangeName + "-report-" + timeStamp + ".pdf";
		return new File(fileName);
	}
	
	/**
	 * @description sent pdf report by mail in template
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param  subscribers, cadenceRange, file
	 */
	private void sentPdfReportByMail(String subscribers, String cadenceRangeName, File file) throws Exception {
		String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
		String msgTemplate = Constants.getMailTempleteByState(16);
		String body = String.format(msgTemplate, "", "", "Customer", cadenceRangeName + " ", "", "");
		String mailTo = subscribers;
		String subject = Constants.getMailSubjectByState(16);

		String tags = "report_" + cadenceRangeName.toLowerCase();
		String fromName = "NEXT WAVE ENERGY MONITORING INC";
		boolean flagSent = SendMail.SendGmailTLSAttachment(mailFromContact, fromName, mailTo, subject, body, tags, file.getAbsolutePath());
		if (!flagSent) {
			throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
		}
	}
	
	/**
	 * @description sent asset management and performance report
	 * @author Hung.Bui
	 * @since 2024-06-15
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-asset-management-and-operation-performance-report")
	public Object sentMailAssetManagementAndOperationPerformanceReport(@RequestBody ViewReportEntity obj) {
		try {
			try (XSSFWorkbook document = new XSSFWorkbook()) {
				ReportsService service = new ReportsService();
				AssetManagementAndOperationPerformanceReportEntity dataObj = (AssetManagementAndOperationPerformanceReportEntity) service.getAssetManagementAndOperationPerformanceReport(obj);
				if (dataObj == null) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				
				int pictureIdx = DocumentHelper.readLogoImageFile(document);
				
				/* === operation performance === */
				if (dataObj != null) {
					List<AssetManagementAndOperationPerformanceDataEntity> data = dataObj.getOperationPerformanceData();
					int numOfPoints = data != null ? data.size() : 0;
					XSSFSheet sheet = document.createSheet("Operation Performance");
					
					// insert logo image
					ClientAnchor logoAnchor = new XSSFClientAnchor(25 * Units.EMU_PER_PIXEL, 0, 0, 0, 11, 0, 12, 5);
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
					
					// report information
					writeHeaderAssetManagementAndOperationPerformanceReport(sheet, dataObj.getReportDetail(), 12, "OPERATION PERFORMANCE SUMMARY");
					
					// report table
					writeTableOperationPerformanceReport(sheet, data);
					
					if (numOfPoints > 0) {
						// --- energy generation chart ---
						ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, 0, 0, 0, numOfPoints + 11, 12, numOfPoints + 11 + 16);
						XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, "Energy Generation");
						
						// data sources
						int firstRow = 9;
						XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 0, 0));
						XDDFNumericalDataSource<Double> actualEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 1, 1));
						XDDFNumericalDataSource<Double> modeledEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 2, 2));
						XDDFNumericalDataSource<Double> expectedEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 3, 3));
						XDDFNumericalDataSource<Double> actualIrradiance = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 4, 4));
						XDDFNumericalDataSource<Double> modeledIrradiance = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 5, 5));
						
						// category axis
						XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);
						
						// left value axis
						XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, null);
						
						XDDFChartData chartData = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getActualEnergy() == null), chartData, categories, actualEnergy, "Actual Energy", XDDFColor.from(PresetColor.STEEL_BLUE), XDDFColor.from(PresetColor.BLACK));
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getModeledEnergy() == null), chartData, categories, modeledEnergy, "Modeled Energy", XDDFColor.from(PresetColor.DARK_GRAY), XDDFColor.from(PresetColor.BLACK));
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getExpectedEnergy() == null), chartData, categories, expectedEnergy, "Actual Energy", XDDFColor.from(PresetColor.LIGHT_STEEL_BLUE), XDDFColor.from(PresetColor.BLACK));
						
						chart.plot(chartData);
						
						// right value axis
						XDDFValueAxis rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, null);
						
						chartData = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getActualPOA() == null), chartData, categories, actualIrradiance, "Actual Irradiance", XDDFColor.from(PresetColor.DARK_ORANGE), null);
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getModeledPOA() == null), chartData, categories, modeledIrradiance, "Modeled Irradiance", XDDFColor.from(PresetColor.GRAY), null);
						
						chart.plot(chartData);
						
						// --- performance ratio chart ---
						chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, -10 * Units.EMU_PER_PIXEL, 0, 0, numOfPoints + 29, 6, numOfPoints + 29 + 15);
						chart = DocumentHelper.insertChart(sheet, chartAnchor, "Performance Ratio");
						
						// chart data sources
						XDDFNumericalDataSource<Double> actualPerformanceRatio = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 6, 6));
						XDDFNumericalDataSource<Double> modeledPerformanceRatio = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 7, 7));
						
						// category axis
						bottomAxis = DocumentHelper.createCategoryAxis(chart);
						
						// left value axis
						leftAxis = DocumentHelper.createLeftValueAxis(chart, null);
						
						chartData = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getActualPerformanceRatio() == null), chartData, categories, actualPerformanceRatio, "Actual Generation", XDDFColor.from(PresetColor.STEEL_BLUE), XDDFColor.from(PresetColor.BLACK));
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getModeledPerformanceRatio() == null), chartData, categories, modeledPerformanceRatio, "Modeled Generation", XDDFColor.from(PresetColor.DARK_GRAY), XDDFColor.from(PresetColor.BLACK));
						
						chart.plot(chartData);
						
						// --- key performance indicators chart ---
						chartAnchor = new XSSFClientAnchor(10 * Units.EMU_PER_PIXEL, 0, 0, 0, 6, numOfPoints + 29, 12, numOfPoints + 29 + 15);
						chart = DocumentHelper.insertChart(sheet, chartAnchor, "Key Performance Indicators");
						
						// chart data sources
						XDDFNumericalDataSource<Double> energyIndex = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 8, 8));
						XDDFNumericalDataSource<Double> weatherIndex = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 9, 9));
						XDDFNumericalDataSource<Double> weatherAdjustedIndex = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 10, 10));
						XDDFNumericalDataSource<Double> inverterAvailability = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 11, 11));
						
						// category axis
						bottomAxis = DocumentHelper.createCategoryAxis(chart);
						
						// left value axis
						leftAxis = DocumentHelper.createLeftValueAxis(chart, null);
						
						chartData = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getEnergyIndex() == null), chartData, categories, energyIndex, "Energy Index", XDDFColor.from(PresetColor.STEEL_BLUE), XDDFColor.from(PresetColor.BLACK));
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getWeatherAdjustedIndex() == null), chartData, categories, weatherAdjustedIndex, "Weather Adjusted Index", XDDFColor.from(PresetColor.DARK_GRAY), XDDFColor.from(PresetColor.BLACK));
						
						chart.plot(chartData);
						
						// right value axis
						rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, null);
						
						chartData = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getWeatherIndex() == null), chartData, categories, weatherIndex, "Weather Index", XDDFColor.from(PresetColor.GRAY), null);
						DocumentHelper.addSeries(data.stream().allMatch(item -> item.getInverterAvailability() == null), chartData, categories, inverterAvailability, "Inverter Availability", XDDFColor.from(PresetColor.DARK_ORANGE), null);
						
						chart.plot(chartData);
					}
				}
				
				/* === monthly performance === */
				if (dataObj != null) {
					List<AssetManagementAndOperationPerformanceDataEntity> data = (List<AssetManagementAndOperationPerformanceDataEntity>) dataObj.getMonthlyPerformanceData().get("data");
					AssetManagementAndOperationPerformanceDataEntity total = (AssetManagementAndOperationPerformanceDataEntity) dataObj.getMonthlyPerformanceData().get("total");
					
					if (data != null && total != null) {
						int numOfPoints = data != null ? data.size() : 0;
						XSSFSheet sheet = document.createSheet("Monthly Performance");
						sheet.setColumnWidth(6, 256 * 5);
						
						// insert logo image
						ClientAnchor logoAnchor = new XSSFClientAnchor(0, 0, 0, 0, 13, 0, 14, 5);
						DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
						
						// report information
						writeHeaderAssetManagementAndOperationPerformanceReport(sheet, dataObj.getReportDetail(), 14, "MONTHLY PERFORMANCE");
						
						// report table
						writeTableMonthlyPerformanceReport(sheet, data, total);
						
						// report summary
						writeSummaryMonthlyPerformanceReport(sheet, "Expected Generation Index", total.getExpectedGenerationIndex(), numOfPoints + 9 - 16 - 3 - 7, 7);
						writeSummaryMonthlyPerformanceReport(sheet, "Modeled Generation Index", total.getModeledGenerationIndex(), numOfPoints + 9 - 16 - 3 - 7, 11);
						writeSummaryMonthlyPerformanceReport(sheet, "Weather Index", total.getWeatherIndex(), numOfPoints + 9 - 16 - 3, 7);
						writeSummaryMonthlyPerformanceReport(sheet, "Inverter Availability", total.getInverterAvailability(), numOfPoints + 9 - 16 - 3, 11);
						
						if (numOfPoints > 0) {
							// --- monthly performance chart ---
							ClientAnchor chartAnchor = new XSSFClientAnchor(0, 0, 0, 0, 7, numOfPoints + 9 - 16, 14, numOfPoints + 9);
							XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, null);
							
							// chart data sources
							int firstRow = 9;
							XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 0, 0));
							XDDFNumericalDataSource<Double> actualEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 1, 1));
							XDDFNumericalDataSource<Double> expectedEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 2, 2));
							XDDFNumericalDataSource<Double> modeledEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 3, 3));
							
							// category axis
							XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);
							
							// left value axis
							XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, null);
							
							XDDFChartData chartData = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
							
							DocumentHelper.addSeries(data.stream().allMatch(item -> item.getActualEnergy() == null), chartData, categories, actualEnergy, "Actual", XDDFColor.from(PresetColor.STEEL_BLUE), XDDFColor.from(PresetColor.BLACK));
							DocumentHelper.addSeries(data.stream().allMatch(item -> item.getExpectedEnergy() == null), chartData, categories, expectedEnergy, "Expected*", XDDFColor.from(PresetColor.DARK_GRAY), XDDFColor.from(PresetColor.BLACK));

							chart.plot(chartData);
							
							chartData = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, leftAxis);
							DocumentHelper.addSeries(data.stream().allMatch(item -> item.getModeledEnergy() == null), chartData, categories, modeledEnergy, "Modeled**", XDDFColor.from(PresetColor.DARK_ORANGE), null);

							chart.plot(chartData);
						}
					}
				}
				
				/* === monthly asset performance === */
				if (dataObj != null) {
					List<AssetManagementAndOperationPerformanceDataEntity> data = dataObj.getMonthlyAssetManagementData();
					XSSFSheet sheet = document.createSheet("Monthly Asset Management");
					
					// insert logo image
					ClientAnchor logoAnchor = new XSSFClientAnchor(0, 0, 0, 0, 6, 0, 7, 5);
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
					
					// report information
					writeHeaderAssetManagementAndOperationPerformanceReport(sheet, dataObj.getReportDetail(), 7, "ASSET MANAGEMENT");
					
					// report table
					writeTableMonthlyAssetManagementReport(sheet, data);
				}
				
				/* === estimated loss by event === */
				if (dataObj != null) {
					List<AssetManagementAndOperationPerformanceDataEntity> data = dataObj.getEstimatedLossByEventData();
					XSSFSheet sheet = document.createSheet("Estimated Loss By Event");
					
					// insert logo image
					ClientAnchor logoAnchor = new XSSFClientAnchor(50 * Units.EMU_PER_PIXEL, 0, 0, 10 * Units.EMU_PER_PIXEL, 7, 0, 8, 5);
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
					
					// report information
					writeHeaderAssetManagementAndOperationPerformanceReport(sheet, dataObj.getReportDetail(), 8, "ESTIMATED LOSS BY EVENT");
					
					// report table
					writeTableEstimatedLossByEventReport(sheet, data);
				}
								
				// Write the output to a file
				String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
				String dir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
				String fileName = dir + "/asset-management-and-operation-performance-report-" + timeStamp + ".xlsx";
				
				try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
					document.write(fileOut);
					String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
					String msgTemplate = Constants.getMailTempleteByState(21);
					String body = String.format(msgTemplate, dataObj.getReportDetail().getSite_name(), dataObj.getReportDetail().getId_site(), "Customer");
					String mailTo = dataObj.getReportDetail().getSubscribers();
					String subject = Constants.getMailSubjectByState(21);

					String tags = "asset-management-and-operation-performance-report";
					String fromName = "NEXT WAVE ENERGY MONITORING INC";
					boolean flagSent = SendMail.SendGmailTLSAttachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
					if (!flagSent) {
						throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
					}
				}
				
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, dataObj, 1);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	
	/**
	 * @description update site status
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-site-rec-id")
	public Object updateStatus(@RequestBody SiteEntity obj) {
		try {
			ReportsService service = new ReportsService();
			service.updateRECID(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description update gu_id
	 * @author long.pham
	 * @since 2023-03-27
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-site-gu-id")
	public Object updateGUID(@RequestBody SiteEntity obj) {
		try {
			ReportsService service = new ReportsService();
			service.updateGUID(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list site by employee
	 * @author long.pham
	 * @since 2021-01-20
	 * @param array id_site
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-site-rec")
	public Object getListREC(@RequestBody ReportsEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			ReportsService service = new ReportsService();
			List data = service.getListREC(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	@PostMapping("/render-excel-renewable-month")
	public Object excelRenewableMonth(@RequestBody ReportsEntity obj) {
		try {
			String[] header = {"REU ID", "GUID", "Vintage", "Begin Date", "End Date", "Generation (MWh)"};
	        List<String[]> list = new ArrayList<>();
//	        list.add(header);
	     
	        ReportsService service = new ReportsService();
			List data = service.getListREC(obj);
			if(data.size() > 0) {
				for (int i = 0; i < data.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) data.get(i);
					String[] record = { 
							obj.getRecVersion() == 2 ? "" : (item.get("name") != null ? item.get("name").toString() : "") + " - " + (item.get("devicename") != null ? item.get("devicename").toString() : ""),
							" "+(item.get("ru_id") != null ? item.get("ru_id").toString() : ""),
							" "+(item.get("gu_id") != null ? item.get("gu_id").toString() : ""),
							" "+(item.get("vintage_date") != null ? item.get("vintage_date").toString() : ""),
							" "+(item.get("start_date") != null ? item.get("start_date").toString() : ""),
							" "+(item.get("end_date") != null ? item.get("end_date").toString() : ""),
							" "+(item.get("energy_this_month") != null ? item.get("energy_this_month").toString() : "")
							};
					list.add(record);
				}
				
				String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
				String dir = uploadRootPath() + "/"
						+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
				String fileName = dir + "/Renewable-energy-credits-" + timeStamp + ".csv";
				try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
		            writer.writeAll(list, false);
		            writer.flush();
		        }
				
				
				
				 

				String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
						Constants.mailFromContact);
				String msgTemplate = Constants.getMailTempleteByState(15);
				String body = String.format(msgTemplate, "Customer", "Renewable Energy Credits", "", "", "", "");
				String mailTo = obj.getSubscribers();
				String subject = Constants.getMailSubjectByState(15);
				String tags = "report_REC";
				String fromName = "NEXT WAVE ENERGY MONITORING INC";
				if(mailTo != null) {
					boolean flagSent = SendMail.SendGmailTLSAttachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
					if (!flagSent) {
						throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
					}
				}
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, data, 1);
			} else {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * @description Get list site by employee
	 * @author long.pham
	 * @since 2021-12-27
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-site-by-customer")
	public Object getListSiteByEmployee(@RequestBody ReportsEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			ReportsService service = new ReportsService();
			List data = service.getListSiteByEmployee(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list site sub-group by employee
	 * @author Hung.Bui
	 * @since 2023-07-24
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-site-sub-group-by-customer")
	public Object getListSiteSubGroupByEmployee(@RequestBody ReportsEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			ReportsService service = new ReportsService();
			List data = service.getListSiteSubGroupByEmployee(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description save customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param screen_mode = 0:add, 1:edit
	 */
	@Autowired
	private ReportTaskScheduler reportTaskScheduler;

	@PostMapping("/save")
	public Object save(@Valid @RequestBody ReportsEntity obj) {
		try {
			ReportsService service = new ReportsService();
			if (obj.getScreen_mode() == 1) {
				ReportsEntity data = service.insertReports(obj);
				if (data != null) {
					// update scheduled task
					reportTaskScheduler.changeReportSchedule(obj.getId());
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					boolean insert = service.updateReports(obj);
					if (insert == true) {
						// update scheduled task
						reportTaskScheduler.changeReportSchedule(obj.getId());
						return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
					} else {
						return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
					}
				} else {
					return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
				}
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description Get list report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody ReportsEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			ReportsService service = new ReportsService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description delete site
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return obj
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody ReportsEntity obj) {
		ReportsService service = new ReportsService();
		try {
			boolean result = service.deleteReports(obj);
			if (result) {
				// update scheduled task
				reportTaskScheduler.changeReportSchedule(obj.getId());
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description Get customer view chart data
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/monthly-report")
	public Object getMonthlyReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();

			ViewReportEntity dataObj = (ViewReportEntity) service.getMonthlyReport(obj);

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
	 * @description sent mail monthly report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-monthly-report")
	public Object sentMailMonthlyReport(@RequestBody ViewReportEntity obj) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			
			for (int i = 0; i < dataObjList.size(); i++) {
				ViewReportEntity dataObj = dataObjList.get(i);
				
				if (dataObj != null) {
					List<MonthlyDateEntity> dataExports = dataObj.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() : 0;
					
					XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName((i + 1) + "_" + dataObj.getSite_name()));
					sheet.setZoom(85);
					
					// insert logo image
					ClientAnchor logoAnchor = new XSSFClientAnchor(0, 0, 20 * Units.EMU_PER_PIXEL, 20 * Units.EMU_PER_PIXEL, 12, 1, 13, 4);
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
					
					// report information and table
					writeHeaderMonthlyReport(sheet, obj, dataObj);
					
					if (numOfPoints > 0) {
						// 1st chart
						ClientAnchor chartAnchor = new XSSFClientAnchor(5, 5, 5, 5, 5, 8, 14, 23);
						XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, "Monthly Performance");
						
						// data sources
						XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(9, 9 + numOfPoints - 1, 0, 0));
						XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(9, 9 + numOfPoints - 1, 1, 1));
						XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(9, 9 + numOfPoints - 1, 2, 2));
						XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(9, 9 + numOfPoints - 1, 3, 3));
						
						// category axis
						XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);
						
						// left value axis
						XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, "GENERATION (KWH)");
						
						XDDFChartData data = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActual() == null), data, categoriesData, valuesData1, "Actual Generation (kWh)", XDDFColor.from(PresetColor.STEEL_BLUE), null);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getEstimated() == null), data, categoriesData, valuesData2, "Estimated Generation (kWh)", XDDFColor.from(PresetColor.LIGHT_SKY_BLUE), null);
						
						chart.plot(data);
						
						// right value axis
						XDDFValueAxis rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, "PERFORMANCE INDEX (%)");
						
						data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActual() == null || item.getEstimated() == null), data, categoriesData, valuesData3, "Estimated Generation Index (%)", XDDFColor.from(PresetColor.GRAY), null);
						
						chart.plot(data);
						
						// 2nd chart
						chartAnchor = new XSSFClientAnchor(5, 5, 5, 5, 5, 25, 14, 41);
						SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");
						chart = DocumentHelper.insertChart(sheet, chartAnchor, monthYearFormat.format(datetimeFormat.parse(obj.getStart_date())));
						
						// data sources
						XDDFDataSource<String> categoriesData2 = XDDFDataSourcesFactory.fromArray(new String[] {""});
						XDDFNumericalDataSource<Double> valuesData12 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(41, 41, 1, 1));
						XDDFNumericalDataSource<Double> valuesData22 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(41, 41, 2, 2));
						
						// category axis
						bottomAxis = DocumentHelper.createCategoryAxis(chart);
						
						// left value axis
						leftAxis = DocumentHelper.createLeftValueAxis(chart, "GENERATION (KWH)");
						
						data = DocumentHelper.createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						((XDDFBarChartData) data).setOverlap((byte) -24);
						((XDDFBarChartData) data).setGapWidth(400);
						
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActual() == null), data, categoriesData2, valuesData12, "Actual Generation (kWh)", XDDFColor.from(PresetColor.STEEL_BLUE), null);
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActual() == null || item.getEstimated() == null), data, categoriesData2, valuesData22, "Estimated Generation (kWh)", XDDFColor.from(PresetColor.LIGHT_SKY_BLUE), null);
						
						chart.plot(data);
					}
				}
			}
				
			sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name());
			return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * @description sent mail monthly report in pdf
	 * @author Hung.Bui
	 * @since 2022-11-29
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-pdf-monthly-report")
	public Object sentMailPdfMonthlyReport(@RequestBody ViewReportEntity obj) {
		try {
			File file = createPdfFile(obj.getCadence_range_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				List<ViewReportEntity> dataObjList = getReportDataList(obj);
				if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
					ViewReportEntity dataObj = dataObjList.get(l);
				
					if (dataObj != null) {
						SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");
						Date startDate = dateTimeFormat.parse(obj.getStart_date());
						Date endDate = dateTimeFormat.parse(obj.getEnd_date());
						dataObj.setStart_date(dateFormat.format(startDate));
						dataObj.setEnd_date(dateFormat.format(endDate));
						List<MonthlyDateEntity> dataExports = dataObj.getDataReports() != null ? dataObj.getDataReports() : new ArrayList<>();
						
						// total column: 14
						final float[] columnWidths = {4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
						Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
						table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
						table.setFontSize(8);
						table.setTextAlignment(TextAlignment.CENTER);
					
						//====== table ============================================================
						// header and logo
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).setHeight(14).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(6, 10).add(new Paragraph("MONTHLY PRODUCTION REPORT")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell(6, 2).add(logoImage).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
						table.addCell(new Paragraph("Site Name").setBold().setTextAlignment(TextAlignment.LEFT));
						table.addCell(new Paragraph(dataObj.getSite_name()).setBold().setTextAlignment(TextAlignment.LEFT));
						table.addCell(new Paragraph("Report Date").setBold().setTextAlignment(TextAlignment.LEFT));
						table.addCell(new Paragraph(dataObj.getReport_date()).setTextAlignment(TextAlignment.LEFT));
						table.addCell(new Paragraph("Covered Period").setBold().setTextAlignment(TextAlignment.LEFT));
						table.addCell(new Paragraph(dataObj.getStart_date() + " - " + dataObj.getEnd_date()).setTextAlignment(TextAlignment.LEFT));
						table.addCell(new Paragraph("System Size (kW DC)").setBold().setTextAlignment(TextAlignment.LEFT));
						table.addCell(new Paragraph(String.valueOf(dataObj.getDc_capacity())).setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).setHeight(14).setBorder(Border.NO_BORDER));
						
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Performance Reporting")).setBold().setBackgroundColor(new DeviceRgb(117, 117, 117)).setFontColor(DeviceGray.WHITE).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 13).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 14).setHeight(14).setBorder(Border.NO_BORDER));
						
						table.addCell(new Paragraph("Date").setBold());
						table.addCell(new Paragraph("Actual Generation (kWh)").setBold());
						table.addCell(new Paragraph("Estimated Generation (kWh)").setBold());
						table.addCell(new Paragraph("Estimated Generation Index (%)").setBold());
						
						// empty column: gap between data table and chart
						table.addCell(new com.itextpdf.layout.element.Cell(dataExports.size() + 3, 1).setBorder(Border.NO_BORDER));
						
						// chart
						// add inner table into chart cell
						Table innerTable = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
						com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(dataExports.size() + 3, 9);
						table.addCell(chartCell.add(innerTable).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setBorder(Border.NO_BORDER).setPadding(0));
						
						// data table
						Double totalActual = null;
						Double totalEstimated = null;
						DecimalFormat dfa = new DecimalFormat(DocumentHelper.noDecimalDataFormat);
						DecimalFormat df = new DecimalFormat(DocumentHelper.oneDecimalPlaceDataFormat);
						for (int i = 0; i < dataExports.size(); i++) {
							MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(i);
							
							table.addCell(item.getCategories_time());
							table.addCell(item.getActual() != null ? dfa.format(item.getActual()) : "");
							table.addCell(item.getEstimated() != null ? dfa.format(item.getEstimated()) : "");
							table.addCell(item.getPercent() != null ? df.format(item.getPercent()) : "");
							
							if(item.getActual() != null) totalActual = (totalActual != null ? totalActual : 0) + item.getActual();
							if(item.getEstimated() != null) totalEstimated = (totalEstimated != null ? totalEstimated : 0) + item.getEstimated();
						}
						
						// total
						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setHeight(14).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Total")).setBold().setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(totalActual != null ? dfa.format(totalActual) : "")).setBold().setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(totalEstimated != null ? dfa.format(totalEstimated) : "")).setBold().setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(totalActual != null && totalEstimated != null && totalEstimated > 0 ? dfa.format((totalActual / totalEstimated) * 100) : "")).setBold().setBorder(Border.NO_BORDER));
						
						//====== first chart ============================================================
						JFreeChart chart = DocumentHelper.createJFreeChart("Monthly Performance");
						XYPlot plot = chart.getXYPlot();
						
						// data source
						TimeSeriesCollection barDataset = DocumentHelper.createJFreeChartBarDataset(0, plot);
						TimeSeries actualSeries = new TimeSeries("Actual Generation (kWh)");
						barDataset.addSeries(actualSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(0, BLUE_COLOR);
						TimeSeries estimateSeries = new TimeSeries("Estimate Generation (kWh)");
						barDataset.addSeries(estimateSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(1, LIGHT_BLUE_COLOR);
						
						TimeSeriesCollection lineDataset = DocumentHelper.createJFreeChartLineDataset(1, plot);
						TimeSeries estimateIndexSeries = new TimeSeries("Estimate Generation Index (%)");
						lineDataset.addSeries(estimateIndexSeries);
						plot.getRendererForDataset(lineDataset).setSeriesPaint(0, Color.gray);
						
						for (int i = 0; i < dataExports.size(); i++) {
							MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(i);
							RegularTimePeriod period = new Day(dateFormat.parse(item.getCategories_time()));
							
							actualSeries.add(period, item.getActual());
							estimateSeries.add(period, item.getEstimated());
							estimateIndexSeries.add(period, item.getPercent());
						}
						
						// category axis
						DocumentHelper.createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.DAY, 1, dateFormat), startDate, endDate).setVerticalTickLabels(true);
						// left axis
						DocumentHelper.createJFreeChartNumberAxis("GENERATION (KWH)", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
						// right axis
						DocumentHelper.createJFreeChartNumberAxis("PERFORMANCE INDEX (%)", AxisLocation.BOTTOM_OR_RIGHT, 1, 1, plot);
						
						innerTable.addCell(new Image(ImageDataFactory.create(chart.createBufferedImage(900, 400), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 400));
						// gap between charts
						innerTable.addCell(new com.itextpdf.layout.element.Cell().setHeight(18 * (dataExports.size() + 1 - 30)).setBorder(Border.NO_BORDER));
						
						//====== second chart ============================================================
						JFreeChart chart2 = DocumentHelper.createJFreeChart(monthYearFormat.format(startDate));
						XYPlot plot2 = chart2.getXYPlot();
						
						// data source
						TimeSeriesCollection barDataset2 = DocumentHelper.createJFreeChartBarDataset(0, plot2);
						((ClusteredXYBarRenderer) plot2.getRendererForDataset(barDataset2)).setMargin(0.7);
						TimeSeries totalActualSeries = new TimeSeries("Actual Generation (kWh)");
						barDataset2.addSeries(totalActualSeries);
						plot2.getRendererForDataset(barDataset2).setSeriesPaint(0, BLUE_COLOR);
						TimeSeries totalEstimateSeries = new TimeSeries("Estimate Generation (kWh)");
						barDataset2.addSeries(totalEstimateSeries);
						plot2.getRendererForDataset(barDataset2).setSeriesPaint(1, LIGHT_BLUE_COLOR);
						
						totalActualSeries.add(new Month(startDate), totalActual);
						totalEstimateSeries.add(new Month(startDate), totalEstimated);
						
						// category axis
						DocumentHelper.createJFreeChartDomainAxis(plot2, new DateTickUnit(DateTickUnitType.MONTH, 1, monthYearFormat), startDate, endDate).setVisible(false);
						// left axis
						DocumentHelper.createJFreeChartNumberAxis("GENERATION (KWH)", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot2);
						
						innerTable.addCell(new Image(ImageDataFactory.create(chart2.createBufferedImage(900, 350), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 350));
						document.add(table);
						if (l < dataObjList.size() - 1) document.add(new AreaBreak());
					}
				}
					
				// It must be closed before attach to mail
				document.close();

				sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	/**
	 * @description Get custom report
	 * @author Hung.Bui
	 * @since 2022-12-15
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/custom-report")
	public Object getCustomReport(@RequestBody ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObjList, dataObjList.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	// Write header with format
			private static void writeHeaderCustomReport(Sheet sheet, ViewReportEntity report, List<ViewReportEntity> dataList) {
				try {
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
					sheet.setColumnWidth(11, 18 * 256);
					sheet.setDefaultRowHeight((short) 500);
					sheet.setDisplayGridlines(false);
					
					CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 22, true);
					CellStyle reportInfoCellStyle = DocumentHelper.createStyleForReportInfo(sheet, false);
					CellStyle reportInfoBoldCellStyle = DocumentHelper.createStyleForReportInfo(sheet, true);
					CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
					CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
					CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);

					Row row = sheet.createRow(0);
					Cell cell = row.createCell(0);
//					cell.setCellStyle(reportInfoBoldCellStyle);
//					cell.setCellValue("Site Name");
					cell = row.createCell(1);
//					cell.setCellStyle(reportInfoBoldCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
					
					cell = row.createCell(2);
					row.setHeight((short) 600);
//					cell.setCellStyle(reportInfoBoldCellStyle);
//					cell.setCellValue(dataObj.getSite_name());
					cell = row.createCell(3);
//					cell.setCellStyle(reportInfoBoldCellStyle);
					cell = row.createCell(4);
//					cell.setCellStyle(reportInfoBoldCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 4));
					
					row = sheet.createRow(1);
					cell = row.createCell(0);
					row.setHeight((short) 600);
					cell.setCellStyle(reportInfoBoldCellStyle);
					cell.setCellValue("Report Date");
					cell = row.createCell(1);
					cell.setCellStyle(reportInfoBoldCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
					
					cell = row.createCell(2);
					cell.setCellStyle(reportInfoCellStyle);
					cell.setCellValue(dataList.get(0).getReport_date());
					cell = row.createCell(3);
					cell.setCellStyle(reportInfoCellStyle);
					cell = row.createCell(4);
					cell.setCellStyle(reportInfoCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 4));
					
					row = sheet.createRow(2);
					row.setHeight((short) 600);
					cell = row.createCell(0);
					cell.setCellStyle(reportInfoBoldCellStyle);
					cell.setCellValue("Covered Period");
					cell = row.createCell(1);
					cell.setCellStyle(reportInfoBoldCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
					if (report.getData_intervals() == Constants.DAILY_INTERVAL) format = new SimpleDateFormat("MM/dd/yyyy");
					else if (report.getData_intervals() == Constants.MONTHLY_INTERVAL) format = new SimpleDateFormat("MM/yyyy");
					else if (report.getData_intervals() == Constants.ANNUALLY_INTERVAL) format = new SimpleDateFormat("yyyy");
					
					cell = row.createCell(2);
					cell.setCellStyle(reportInfoCellStyle);
					cell.setCellValue(format.format(dateFormat.parse(report.getStart_date())) + " - " + format.format(dateFormat.parse(report.getEnd_date())));
					cell = row.createCell(3);
					cell.setCellStyle(reportInfoCellStyle);
					cell = row.createCell(4);
					cell.setCellStyle(reportInfoCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 4));
					
					row = sheet.createRow(3);
					row.setHeight((short) 600);
					cell = row.createCell(0);
//					cell.setCellStyle(reportInfoBoldCellStyle);
//					cell.setCellValue("System Size (kW DC)");
					cell = row.createCell(1);
//					cell.setCellStyle(reportInfoBoldCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
					
					cell = row.createCell(2);
//					cell.setCellStyle(reportInfoCellStyle);
//					cell.setCellValue(dataObj.getDc_capacity());
					cell = row.createCell(3);
//					cell.setCellStyle(reportInfoCellStyle);
					cell = row.createCell(4);
//					cell.setCellStyle(reportInfoCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 4));
					
					for (int i = 0; i <= 3; i++) {
						row = sheet.getRow(i) != null ? sheet.getRow(i) : sheet.createRow(i);
						for (int j = 5; j <= 10; j++) {
							cell = row.createCell(j);
							cell.setCellStyle(reportTitleCellStyle);
							if(i == 0 && j == 5) cell.setCellValue("PRODUCTION REPORT");
						}
					}
					sheet.addMergedRegion(new CellRangeAddress(0, 3, 5, 10));	
					
					row = sheet.createRow(24);
					cell = row.createCell(0);
					cell.setCellStyle(tableHeaderCellStyle);
					cell.setCellValue("Timestamp");
					cell = row.createCell(1);
					cell.setCellStyle(tableHeaderCellStyle);
					cell = row.createCell(2);
					cell.setCellStyle(tableHeaderCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(24, 24, 0, 2));
					
					if (report.isTransposed()) {
						for (int i = 0; i < dataList.size(); i++) {
							ViewReportEntity dataObj = dataList.get(i);
							
							cell = row.createCell(3 + 3*i);
							cell.setCellStyle(tableHeaderCellStyle);
							cell.setCellValue(dataObj.getSite_name());
							cell = row.createCell(4 + 3*i);
							cell.setCellStyle(tableHeaderCellStyle);
							cell = row.createCell(5 + 3*i);
							cell.setCellStyle(tableHeaderCellStyle);
							sheet.addMergedRegion(new CellRangeAddress(24, 24, 3 + 3*i, 5 + 3*i));
							
							List<CustomReportDataEntity> dataExports = dataObj.getDataReports();
							
							if(dataExports != null && dataExports.size() > 0) {
								for(int j = 0 ; j < dataExports.size(); j++) {
									CustomReportDataEntity item = dataExports.get(j);
									if (item.getCategories_time().equals("Total") && !report.isShowTotal()) continue;
									int t = 25 + j;
									
									Row row26 = sheet.getRow(t) != null ? sheet.getRow(t) : sheet.createRow(t);
									if (i == 0) {
										Cell cel26D = row26.createCell(0);
										cel26D.setCellStyle(tableRowCellStyle);
										cel26D.setCellValue(item.getCategories_time());
										cel26D = row26.createCell(1);
										cel26D.setCellStyle(tableRowCellStyle);
										cel26D = row26.createCell(2);
										cel26D.setCellStyle(tableRowCellStyle);
										sheet.addMergedRegion(new CellRangeAddress(t, t, 0, 2));
									}
									
									Cell cel26G = row26.createCell(3 + 3*i);
									cel26G.setCellStyle(tableRowNoDecimalCellStyle);
									if(item.getActual() != null) cel26G.setCellValue(item.getActual());
									Cell cel26H = row26.createCell(4 + 3*i);
									cel26H.setCellStyle(tableRowNoDecimalCellStyle);
									Cell cel26I = row26.createCell(5 + 3*i);
									cel26I.setCellStyle(tableRowNoDecimalCellStyle);
									sheet.addMergedRegion(new CellRangeAddress(t, t, 3 + 3*i, 5 + 3*i));
								}
							}
						}
					} else {
						for (int i = 0; i < dataList.size(); i++) {
							ViewReportEntity dataObj = dataList.get(i);
							
							int t = 25 + i;
							Row row26 = sheet.createRow(t);
							Cell cel26D = row26.createCell(0);
							cel26D.setCellStyle(tableRowCellStyle);
							cel26D.setCellValue(dataObj.getSite_name());
							cel26D = row26.createCell(1);
							cel26D.setCellStyle(tableRowCellStyle);
							cel26D = row26.createCell(2);
							cel26D.setCellStyle(tableRowCellStyle);
							sheet.addMergedRegion(new CellRangeAddress(t, t, 0, 2));
							
							List<CustomReportDataEntity> dataExports = dataObj.getDataReports();
							
							if(dataExports != null && dataExports.size() > 0) {
								for(int j = 0 ; j < dataExports.size(); j++) {
									CustomReportDataEntity item = dataExports.get(j);
									if (item.getCategories_time().equals("Total") && !report.isShowTotal()) continue;
									
									if (i == 0) {
										cell = row.createCell(3 + j);
										cell.setCellStyle(tableHeaderCellStyle);
										cell.setCellValue(item.getCategories_time());
									}
									
									cel26D = row26.createCell(3 + j);
									cel26D.setCellStyle(tableRowNoDecimalCellStyle);
									if(item.getActual() != null) cel26D.setCellValue(item.getActual());
								}
							}
						}
					}
				} catch (Exception e) {
				}

			}
					
			/**
			 * @description sent mail custom report in excel
			 * @author Hung.Bui
			 * @since 2022-12-20
			 * @param id
			 * @return data (status, message, array, total_row
			 */
			@PostMapping("/sent-mail-excel-custom-report")
			public Object sentMailCustomReport(@RequestBody ViewReportEntity obj) {
				try (XSSFWorkbook document = new XSSFWorkbook()) {
					List<ViewReportEntity> dataObjList = getReportDataList(obj);
					if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
					
					XSSFSheet sheet = document.createSheet("Production Report");
					
					// insert logo image
					int pictureIdx = DocumentHelper.readLogoImageFile(document);
					ClientAnchor logoAnchor = new XSSFClientAnchor(0, 10 * Units.EMU_PER_PIXEL, 0, -10 * Units.EMU_PER_PIXEL, 11, 0, 12, 4);
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
					
					// chart
					ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, 0, 0, 0, 6, 12, 22);
					XDDFChart chart = DocumentHelper.insertChart(sheet, chartAnchor, "Actual Generation (kWh)");
					
					// category axis
					XDDFCategoryAxis bottomAxis = DocumentHelper.createCategoryAxis(chart);
					
					// left value axis
					XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, "kWh");
					
					// report information and table
					writeHeaderCustomReport(sheet, obj, dataObjList);
					
					for (int i = 0; i < dataObjList.size(); i++) {
						ViewReportEntity dataObj = dataObjList.get(i);
						
						if (dataObj != null) {
							List<CustomReportDataEntity> dataExports = dataObj.getDataReports();
							int numOfPoints = dataExports != null ? dataExports.size() - 1 : 0; // exclude total row
							
							if (numOfPoints > 0) {
								// data sources
								XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, obj.isTransposed() ? new CellRangeAddress(25, 25 + numOfPoints - 1, 0, 0) : new CellRangeAddress(24, 24, 3, 3 + numOfPoints - 1));
								XDDFNumericalDataSource<Double> valuesData = XDDFDataSourcesFactory.fromNumericCellRange(sheet, obj.isTransposed() ? new CellRangeAddress(25, 25 + numOfPoints - 1, 3 + 3*i, 3 + 3*i) : new CellRangeAddress(25 + i, 25 + i, 3, 3 + numOfPoints - 1));
								
								XDDFChartData data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, leftAxis);
								DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getActual() == null), data, categoriesData, valuesData, dataObj.getSite_name());
								
								chart.plot(data);
							}
						}
					}
					
					sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name());
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				} catch (Exception e) {
					return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
				}
			}
	
	/**
	 * @description sent mail daily report in pdf
	 * @author Hung.Bui
	 * @since 2022-12-19
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-pdf-custom-report")
	public Object sentMailPdfCustomReport(@RequestBody ViewReportEntity obj) {
		try {
			File file = createPdfFile(obj.getCadence_range_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				List<ViewReportEntity> dataObjList = getReportDataList(obj);
				if (dataObjList == null || dataObjList.size() == 0) return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				
				// total column: 12
				Table table = new Table(12).useAllAvailableWidth();
				table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
				table.setFontSize(8);
				table.setTextAlignment(TextAlignment.CENTER);
				
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat format  = new SimpleDateFormat("MM/yyyy");
				DateTickUnitType dateTickUnitType = DateTickUnitType.MONTH;
				
				// select format based on intervals
				switch (obj.getData_intervals()) {
					case Constants.DAILY_INTERVAL:
						format = new SimpleDateFormat("MM/dd/yyyy");
						dateTickUnitType = DateTickUnitType.DAY;
						break;
						
					case Constants.MONTHLY_INTERVAL:
						format = new SimpleDateFormat("MM/yyyy");
						dateTickUnitType = DateTickUnitType.MONTH;
						break;
						
					case Constants.ANNUALLY_INTERVAL:
						format = new SimpleDateFormat("yyyy");
						dateTickUnitType = DateTickUnitType.YEAR;
						break;
				}
				
				Date startDate = dateFormat.parse(obj.getStart_date());
				Date endDate = dateFormat.parse(obj.getEnd_date());
			
				//====== table ============================================================
				// header and logo
				table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(6, 7).add(new Paragraph("PRODUCTION REPORT")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
				table.addCell(new com.itextpdf.layout.element.Cell(6, 2).add(logoImage).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 1).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 2).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Report Date").setBold().setTextAlignment(TextAlignment.LEFT)));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObjList.get(0).getReport_date()).setTextAlignment(TextAlignment.LEFT)));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Covered Period").setBold().setTextAlignment(TextAlignment.LEFT)));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(format.format(startDate) + " - " + format.format(endDate)).setTextAlignment(TextAlignment.LEFT)));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 1).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 2).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
				
				// chart
				com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(16, 12);
				table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
				
				//====== chart ============================================================
				JFreeChart chart = DocumentHelper.createJFreeChart("Actual Generation (kWh)");
				XYPlot plot = chart.getXYPlot();
				
				// category axis
				DocumentHelper.createJFreeChartDomainAxis(plot, new DateTickUnit(dateTickUnitType, (int) Math.ceil((double) dataObjList.get(0).getDataReports().size() / 15), format), startDate, endDate);
				// left axis
				DocumentHelper.createJFreeChartNumberAxis("kWh", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
				
				DecimalFormat dfs = new DecimalFormat(DocumentHelper.noDecimalDataFormat);
				
				for (int l = 0; l < dataObjList.size(); l++) {
					ViewReportEntity dataObj = dataObjList.get(l);

					if (dataObj != null) {
						List<CustomReportDataEntity> dataExports = dataObj.getDataReports() != null ? dataObj.getDataReports() : new ArrayList<>();
						
						// empty row
						table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
						
						// header of data table
						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Timestamp").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataObj.getSite_name()).setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setBorder(Border.NO_BORDER));
						
						// data source
						TimeSeriesCollection lineDataset = DocumentHelper.createJFreeChartLineDataset(l, plot);
						TimeSeries series = new TimeSeries(dataObj.getSite_name());
						lineDataset.addSeries(series);
						
						// data table
						for (int i = 0; i < dataExports.size(); i++) {
							CustomReportDataEntity item = dataExports.get(i);
							String itemCategoryTime = item.getCategories_time();
							Double itemActual = item.getActual();
							
							if (itemCategoryTime.equals("Total") && !obj.isShowTotal()) continue;
							table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setBorder(Border.NO_BORDER));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(itemCategoryTime)));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(itemActual != null ? dfs.format(itemActual).toString() : "")));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setBorder(Border.NO_BORDER));
							
							if (itemCategoryTime.equals("Total")) continue;
							RegularTimePeriod period = new Month(format.parse(itemCategoryTime));
							if (obj.getData_intervals() == Constants.DAILY_INTERVAL) period = new Day(format.parse(itemCategoryTime));
							else if (obj.getData_intervals() == Constants.MONTHLY_INTERVAL) period = new Month(format.parse(itemCategoryTime));
							else if (obj.getData_intervals() == Constants.ANNUALLY_INTERVAL) period = new Year(format.parse(itemCategoryTime));
							
							series.add(period, itemActual);
						}
					}
				}
				
				chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1800, 700), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 700));
				document.add(table);
				// It must be closed before attach to mail
				document.close();
					
				sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}

	/**
	 * @description Get customer view chart data
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/view-report")
	public Object viewReport(@RequestBody ViewReportEntity obj) {
		try {
			ReportsService service = new ReportsService();

			ViewReportEntity dataObj = (ViewReportEntity) service.getSiteDetail(obj);

			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

//			ViewReportEntity dataObj = queryForObject("CustomerViewTypeA.getCustomerViewInfo", obj);
//			
//			
//			switch (filterBy) {
//			case "today":
//				List dataEnergy = service.getChartDataEnergy(obj);
//				obj.setEnergy(dataEnergy);
//				break;
//			case "last_month":
//			case "this_month":
//				List dataThisMonthEnergy = service.getChartDataEnergy(obj);
//				obj.setEnergy(dataThisMonthEnergy);
//				break;
//			case "12_month":
//				List data12MonthEnergy = service.getChartDataEnergy(obj);
//				obj.setEnergy(data12MonthEnergy);
//				break;
//			case "lifetime":
//				  List dataLifetimeEnergy = service.getChartDataEnergy(obj);
//				obj.setEnergy(dataLifetimeEnergy);
//				break;
//			}

//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}


	// Write header with format
	private static void writeHeaderMonthlyReport(Sheet sheet, ViewReportEntity report, ViewReportEntity dataObj ) {
		try {
			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 30 * 256);
			sheet.setColumnWidth(1, 35 * 256);
			sheet.setColumnWidth(2, 35 * 256);
			sheet.setColumnWidth(3, 35 * 256);
			sheet.setColumnWidth(4, 10 * 256);
			sheet.setColumnWidth(5, 15 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 15 * 256);
			sheet.setColumnWidth(8, 15 * 256);
			sheet.setColumnWidth(9, 15 * 256);
			sheet.setColumnWidth(10, 15 * 256);
			sheet.setColumnWidth(11, 15 * 256);
			sheet.setColumnWidth(12, 15 * 256);
			sheet.setDefaultRowHeight((short) 500);
			sheet.setDisplayGridlines(false);
			
			CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 22, true);
			CellStyle reportInfoCellStyle = DocumentHelper.createStyleForReportInfo(sheet, false);
			CellStyle reportInfoBoldCellStyle = DocumentHelper.createStyleForReportInfo(sheet, true);
			CellStyle tableTitleCellStyle = DocumentHelper.createStyleForTableTitle(sheet);
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			CellStyle tableRowOneDecimalPlaceCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceDataFormat);
			CellStyle tableRowNoDecimalBoldCellStyle = DocumentHelper.createStyleForNoBorderTableRowNumber(sheet, true, null);
			tableRowNoDecimalBoldCellStyle.setBorderTop(BorderStyle.DOUBLE);
			tableRowNoDecimalBoldCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			CellStyle tableRowOneDecimalPlaceBoldCellStyle = DocumentHelper.createStyleForNoBorderTableRowNumber(sheet, true, DocumentHelper.oneDecimalPlaceDataFormat);
			tableRowOneDecimalPlaceBoldCellStyle.setBorderTop(BorderStyle.DOUBLE);
			tableRowOneDecimalPlaceBoldCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

			Row row = sheet.createRow(0);
			row.setHeight((short) 600);
			Cell cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("Site Name");

			cell = row.createCell(1);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue(dataObj.getSite_name());

			row = sheet.createRow(1);
			row.setHeight((short) 600);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("Report Date");

			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(dataObj.getReport_date());

			row = sheet.createRow(2);
			row.setHeight((short) 600);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("Covered Period");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(format.format(dateFormat.parse(report.getStart_date())) + " - " + format.format(dateFormat.parse(report.getEnd_date())));

			row = sheet.createRow(3);
			row.setHeight((short) 600);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("System Size (kW DC)");

			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(dataObj.getDc_capacity());
			
			for (int i = 0; i <= 5; i++) {
				row = sheet.getRow(i) != null ? sheet.getRow(i) : sheet.createRow(i);
				for (int j = 2; j <= 10; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(reportTitleCellStyle);
					if(i == 0 && j == 2) cell.setCellValue("MONTHLY PRODUCTION REPORT");
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(0, 5, 2, 10));

			row = sheet.createRow(6);
			cell = row.createCell(0);
			cell.setCellStyle(tableTitleCellStyle);
			cell.setCellValue("Performance Reporting");
			cell = row.createCell(1);
			cell.setCellStyle(tableTitleCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));

			row = sheet.createRow(8);
			row.setHeight((short) 1000);
			cell = row.createCell(0);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Date");
			
			cell = row.createCell(1);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Actual Generation (kWh)");
			
			cell = row.createCell(2);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Estimated Generation (kWh)");
			
			cell = row.createCell(3);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Estimated Generation Index (%)");
			
			List<MonthlyDateEntity> dataExports = dataObj.getDataReports();
			Double totalActual = null;
			Double totalEstimated = null;
			if(dataExports != null && dataExports.size() > 0) {
				int r = 9;
				for( int j = 0; j < dataExports.size(); j++){
					MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(j);
					
					Row row7f = sheet.createRow(r+j);
					Cell cell7f = row7f.createCell(0);
					cell7f.setCellStyle(tableRowCellStyle);
					cell7f.setCellValue(item.getCategories_time());
					
					Cell cell71f = row7f.createCell(1);
					cell71f.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getActual() != null) cell71f.setCellValue(item.getActual());
					
					Cell cell72f = row7f.createCell(2);
					cell72f.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getEstimated() != null) cell72f.setCellValue(item.getEstimated());
					
					Cell cell73f = row7f.createCell(3);
					cell73f.setCellStyle(tableRowOneDecimalPlaceCellStyle);
					if(item.getPercent() != null) cell73f.setCellValue(item.getPercent());
					
					if(item.getActual() != null) totalActual = (totalActual != null ? totalActual : 0) + item.getActual();
					if(item.getEstimated() != null) totalEstimated = (totalEstimated != null ? totalEstimated : 0) + item.getEstimated();
				}
			}
			
			Row row8 = sheet.createRow(41);
			Cell cell8 = row8.createCell(0);
			cell8.setCellStyle(tableRowNoDecimalBoldCellStyle);
			cell8.setCellValue("Total");
			
			Cell cell81 = row8.createCell(1);
			cell81.setCellStyle(tableRowNoDecimalBoldCellStyle);
			if(totalActual != null) cell81.setCellValue(totalActual);
			
			Cell cell82 = row8.createCell(2);
			cell82.setCellStyle(tableRowNoDecimalBoldCellStyle);
			if(totalEstimated != null) cell82.setCellValue(totalEstimated);
			
			Cell cell83 = row8.createCell(3);
			cell83.setCellStyle(tableRowOneDecimalPlaceBoldCellStyle);
			if(totalActual != null && totalEstimated != null && totalEstimated > 0) cell83.setCellValue(totalActual / totalEstimated * 100);
			
		} catch (Exception e) {
		}

	}
	
	private static void writeHeaderAssetManagementAndOperationPerformanceReport(Sheet sheet, ViewReportEntity dataObj, int totalColumns, String title) {
		try {
			sheet.setDefaultColumnWidth(16);
			sheet.setDisplayGridlines(false);
			
			CellStyle reportTitleCellStyle = DocumentHelper.createStyleForReportTitle(sheet, (short) 22, true);
			CellStyle reportInfoCellStyle = DocumentHelper.createStyleForReportInfo(sheet, false);
			CellStyle reportInfoBoldCellStyle = DocumentHelper.createStyleForReportInfo(sheet, true);
			
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellStyle(reportTitleCellStyle);
			cell.setCellValue(title);
			for (int i = 1; i <= totalColumns - 1; i++) {
				row.createCell(i).setCellStyle(reportTitleCellStyle);
			}
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, totalColumns - 1));

			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("Site Name");
			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));

			cell = row.createCell(2);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue(dataObj.getSite_name());
			cell = row.createCell(3);
			cell.setCellStyle(reportInfoBoldCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 3));
			
			row = sheet.createRow(3);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("System Size (kW DC)");
			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
			
			cell = row.createCell(2);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(dataObj.getDc_capacity());
			cell = row.createCell(3);
			cell.setCellStyle(reportInfoCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));
			
			row = sheet.createRow(4);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoBoldCellStyle);
			cell.setCellValue("Performance Period");
			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));

			cell = row.createCell(2);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(LocalDateTime.parse(dataObj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).format(DateTimeFormatter.ofPattern("MMM-yy")));
			cell = row.createCell(3);
			cell.setCellStyle(reportInfoCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 3));
		} catch (Exception e) {
		}
	}
	
	private static void writeTableOperationPerformanceReport(Sheet sheet, List<AssetManagementAndOperationPerformanceDataEntity> data) {
		try {
			sheet.setColumnWidth(10, 23 * 256);
			sheet.setColumnWidth(11, 20 * 256);
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			CellStyle tableRowOneDecimalPlaceCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceDataFormat);
			CellStyle tableRowOneDecimalPlaceWithPercentageCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceWithPercentageDataFormat);
			
			Row tableHeaderRow = sheet.createRow(7);
			Cell tableHeaderCell = tableHeaderRow.createCell(0);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			
			tableHeaderCell = tableHeaderRow.createCell(1);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Energy Generation (kWh)");
			tableHeaderCell = tableHeaderRow.createCell(2);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell = tableHeaderRow.createCell(3);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 3));
			
			tableHeaderCell = tableHeaderRow.createCell(4);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("POA Irradiance (W/m2)");
			tableHeaderCell = tableHeaderRow.createCell(5);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 4, 5));
			
			tableHeaderCell = tableHeaderRow.createCell(6);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Performance Ratio (%)");
			tableHeaderCell = tableHeaderRow.createCell(7);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 7));
			
			tableHeaderCell = tableHeaderRow.createCell(8);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Key Performance Indicators (%)");
			tableHeaderCell = tableHeaderRow.createCell(9);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell = tableHeaderRow.createCell(10);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell = tableHeaderRow.createCell(11);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 8, 11));
			
			tableHeaderRow = sheet.createRow(8);
			tableHeaderCell = tableHeaderRow.createCell(0);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Month");
			
			tableHeaderCell = tableHeaderRow.createCell(1);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Actual");
			
			tableHeaderCell = tableHeaderRow.createCell(2);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Modeled");
			
			tableHeaderCell = tableHeaderRow.createCell(3);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Expected");
			
			tableHeaderCell = tableHeaderRow.createCell(4);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Actual");
			
			tableHeaderCell = tableHeaderRow.createCell(5);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Modeled");
			
			tableHeaderCell = tableHeaderRow.createCell(6);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Actual");
			
			tableHeaderCell = tableHeaderRow.createCell(7);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Modeled");
			
			tableHeaderCell = tableHeaderRow.createCell(8);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Energy Index");
			
			tableHeaderCell = tableHeaderRow.createCell(9);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Weather Index");
			
			tableHeaderCell = tableHeaderRow.createCell(10);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Weather Adjusted Index");
			
			tableHeaderCell = tableHeaderRow.createCell(11);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Inverter Availability");
			
			if(data != null && data.size() > 0) {
				int r = 9;
				for( int j = 0; j < data.size(); j++){
					AssetManagementAndOperationPerformanceDataEntity item = data.get(j);
					
					Row tableRow = sheet.createRow(r+j);
					Cell tableCell = tableRow.createCell(0);
					tableCell.setCellStyle(tableRowCellStyle);
					tableCell.setCellValue(item.getTime_full());
					
					tableCell = tableRow.createCell(1);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getActualEnergy() != null) tableCell.setCellValue(item.getActualEnergy());
					
					tableCell = tableRow.createCell(2);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getModeledEnergy() != null) tableCell.setCellValue(item.getModeledEnergy());
					
					tableCell = tableRow.createCell(3);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getExpectedEnergy() != null) tableCell.setCellValue(item.getExpectedEnergy());
					
					tableCell = tableRow.createCell(4);
					tableCell.setCellStyle(tableRowOneDecimalPlaceCellStyle);
					if(item.getActualPOA() != null) tableCell.setCellValue(item.getActualPOA());
					
					tableCell = tableRow.createCell(5);
					tableCell.setCellStyle(tableRowOneDecimalPlaceCellStyle);
					if(item.getModeledPOA() != null) tableCell.setCellValue(item.getModeledPOA());
					
					tableCell = tableRow.createCell(6);
					tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
					if(item.getActualPerformanceRatio() != null) tableCell.setCellValue(item.getActualPerformanceRatio());
					
					tableCell = tableRow.createCell(7);
					tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
					if(item.getModeledPerformanceRatio() != null) tableCell.setCellValue(item.getModeledPerformanceRatio());
					
					tableCell = tableRow.createCell(8);
					tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
					if(item.getEnergyIndex() != null) tableCell.setCellValue(item.getEnergyIndex());
					
					tableCell = tableRow.createCell(9);
					tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
					if(item.getWeatherIndex() != null) tableCell.setCellValue(item.getWeatherIndex());
					
					tableCell = tableRow.createCell(10);
					tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
					if(item.getWeatherAdjustedIndex() != null) tableCell.setCellValue(item.getWeatherAdjustedIndex());
					
					tableCell = tableRow.createCell(11);
					tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
					if(item.getInverterAvailability() != null) tableCell.setCellValue(item.getInverterAvailability());
				}
			}
		} catch (Exception e) {
		}
	}
	
	private static void writeTableMonthlyPerformanceReport(Sheet sheet, List<AssetManagementAndOperationPerformanceDataEntity> data, AssetManagementAndOperationPerformanceDataEntity total) {
		try {
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			CellStyle tableRowOneDecimalPlaceWithPercentageCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceWithPercentageDataFormat);
			CellStyle tableRowNoDecimalNoBorderCellStyle = DocumentHelper.createStyleForNoBorderTableRowNumber(sheet, true, null);
			CellStyle tableRowOneDecimalPlaceWithPercentageNoBorderCellStyle = DocumentHelper.createStyleForNoBorderTableRowNumber(sheet, true, DocumentHelper.oneDecimalPlaceWithPercentageDataFormat);
			
			Row tableHeaderRow = sheet.createRow(7);
			Cell tableHeaderCell = tableHeaderRow.createCell(0);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			
			tableHeaderCell = tableHeaderRow.createCell(1);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Energy Generation (kWh)");
			tableHeaderCell = tableHeaderRow.createCell(2);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell = tableHeaderRow.createCell(3);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 3));
			
			tableHeaderCell = tableHeaderRow.createCell(4);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Generation Index (%)");
			tableHeaderCell = tableHeaderRow.createCell(5);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 4, 5));
			
			tableHeaderRow = sheet.createRow(8);
			tableHeaderCell = tableHeaderRow.createCell(0);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Date");
			
			tableHeaderCell = tableHeaderRow.createCell(1);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Actual");
			
			tableHeaderCell = tableHeaderRow.createCell(2);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Expected*");
			
			tableHeaderCell = tableHeaderRow.createCell(3);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Modeled**");
			
			tableHeaderCell = tableHeaderRow.createCell(4);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Expected Generation Index");
			
			tableHeaderCell = tableHeaderRow.createCell(5);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Modeled Generation Index");
			
			if(data != null && data.size() > 0) {
				int r = 9;
				for( int j = 0; j < data.size(); j++){
					AssetManagementAndOperationPerformanceDataEntity item = data.get(j);
					
					Row tableRow = sheet.createRow(r+j);
					Cell tableCell = tableRow.createCell(0);
					tableCell.setCellStyle(tableRowCellStyle);
					tableCell.setCellValue(item.getTime_full());
					
					tableCell = tableRow.createCell(1);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getActualEnergy() != null) tableCell.setCellValue(item.getActualEnergy());
					
					tableCell = tableRow.createCell(2);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getExpectedEnergy() != null) tableCell.setCellValue(item.getExpectedEnergy());
					
					tableCell = tableRow.createCell(3);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getModeledEnergy() != null) tableCell.setCellValue(item.getModeledEnergy());
					
					tableCell = tableRow.createCell(4);
					tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
					if(item.getExpectedGenerationIndex() != null) tableCell.setCellValue(item.getExpectedGenerationIndex());
					
					tableCell = tableRow.createCell(5);
					tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
					if(item.getExpectedGenerationIndex() != null) tableCell.setCellValue(item.getExpectedGenerationIndex());
				}
				
				// total
				Row tableRow = sheet.createRow(r + data.size());
				Cell tableCell = tableRow.createCell(0);
				tableCell.setCellStyle(tableRowNoDecimalNoBorderCellStyle);
				tableCell.setCellValue("Total");
				
				tableCell = tableRow.createCell(1);
				tableCell.setCellStyle(tableRowNoDecimalNoBorderCellStyle);
				if(total.getActualEnergy() != null) tableCell.setCellValue(total.getActualEnergy());
				
				tableCell = tableRow.createCell(2);
				tableCell.setCellStyle(tableRowNoDecimalNoBorderCellStyle);
				if(total.getExpectedEnergy() != null) tableCell.setCellValue(total.getExpectedEnergy());
				
				tableCell = tableRow.createCell(3);
				tableCell.setCellStyle(tableRowNoDecimalNoBorderCellStyle);
				if(total.getModeledEnergy() != null) tableCell.setCellValue(total.getModeledEnergy());
				
				tableCell = tableRow.createCell(4);
				tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageNoBorderCellStyle);
				if(total.getExpectedGenerationIndex() != null) tableCell.setCellValue(total.getExpectedGenerationIndex());
				
				tableCell = tableRow.createCell(5);
				tableCell.setCellStyle(tableRowOneDecimalPlaceWithPercentageNoBorderCellStyle);
				if(total.getModeledGenerationIndex() != null) tableCell.setCellValue(total.getModeledGenerationIndex());
				
				// note
				XSSFFont boldFont = (XSSFFont) sheet.getWorkbook().createFont();
				boldFont.setFontName("Times New Roman");
				boldFont.setBold(true);
				boldFont.setFontHeightInPoints((short) 12);
				boldFont.setColor(IndexedColors.BLACK.getIndex());
				
				XSSFRichTextString noteString = new XSSFRichTextString();
				noteString.append("* Expected Generation", boldFont);
				noteString.append(" is calculated based on measured irradiance and module temperature");
				tableRow = sheet.createRow(r + data.size() + 3);
				tableCell = tableRow.createCell(0);
				tableCell.setCellValue(noteString);
				
				noteString = new XSSFRichTextString();
				noteString.append("** Modeled Generation", boldFont);
				noteString.append(" is predicted by PVWatts or similar programs");
				tableRow = sheet.createRow(r + data.size() + 4);
				tableCell = tableRow.createCell(0);
				tableCell.setCellValue(noteString);
			}
		} catch (Exception e) {
		}
	}
	
	private static void writeSummaryMonthlyPerformanceReport(Sheet sheet, String name, Double value, int lastRow, int firstColumn) {
		try {
			Font valueFont = sheet.getWorkbook().createFont();
			valueFont.setFontName("Times New Roman");
			valueFont.setBold(true);
			valueFont.setFontHeightInPoints((short) 22);
			valueFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
			
			CellStyle valueCellStyle = sheet.getWorkbook().createCellStyle();
			valueCellStyle.setFont(valueFont);
			valueCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			valueCellStyle.setAlignment(HorizontalAlignment.CENTER);
			valueCellStyle.setBorderBottom(BorderStyle.THIN);
			valueCellStyle.setBorderTop(BorderStyle.THIN);
			valueCellStyle.setBorderRight(BorderStyle.THIN);
			valueCellStyle.setBorderLeft(BorderStyle.THIN);
			valueCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			valueCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			valueCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			valueCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			valueCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(DocumentHelper.oneDecimalPlaceWithPercentageDataFormat));
			
			Font nameFont = sheet.getWorkbook().createFont();
			nameFont.setFontName("Times New Roman");
			nameFont.setFontHeightInPoints((short) 14);
			nameFont.setColor(IndexedColors.BLACK.getIndex());
			
			CellStyle nameCellStyle = sheet.getWorkbook().createCellStyle();
			nameCellStyle.cloneStyleFrom(valueCellStyle);
			nameCellStyle.setFont(nameFont);
			nameCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			nameCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			nameCellStyle.setDataFormat((short) 0x31);
			
			Row row;
			Cell cell;
			for (int i = 2; i < 5; i++) {
				row = sheet.getRow(lastRow - i);
				for (int j = 0; j < 3; j++) {
					cell = row.createCell(firstColumn + j);
					cell.setCellStyle(valueCellStyle);
					if(i == 4 && j == 0 && value != null) cell.setCellValue(value);
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(lastRow - 4, lastRow - 2, firstColumn, firstColumn + 2));
			
			for (int i = 0; i < 2; i++) {
				row = sheet.getRow(lastRow - i);
				for (int j = 0; j < 3; j++) {
					cell = row.createCell(firstColumn + j);
					cell.setCellStyle(nameCellStyle);
					if(i == 1 && j == 0 && name != null) cell.setCellValue(name);
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(lastRow - 1, lastRow, firstColumn, firstColumn + 2));
		} catch (Exception e) {
		}
	}
	
	private static void writeTableMonthlyAssetManagementReport(Sheet sheet, List<AssetManagementAndOperationPerformanceDataEntity> data) {
		try {
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			CellStyle tableRowNoDecimaLeftAlignlCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			tableRowNoDecimaLeftAlignlCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCurrencyCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.noDecimalCurrencyDataFormat);
			
			Row tableHeaderRow = sheet.createRow(7);
			Cell tableHeaderCell = tableHeaderRow.createCell(0);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			
			tableHeaderCell = tableHeaderRow.createCell(1);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Energy Delivered (kWh)");
			tableHeaderCell = tableHeaderRow.createCell(2);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell = tableHeaderRow.createCell(3);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 3));
			
			tableHeaderCell = tableHeaderRow.createCell(4);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Energy Revenue");
			tableHeaderCell = tableHeaderRow.createCell(5);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell = tableHeaderRow.createCell(6);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 4, 6));
			
			tableHeaderRow = sheet.createRow(8);
			tableHeaderCell = tableHeaderRow.createCell(0);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			
			tableHeaderCell = tableHeaderRow.createCell(1);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Actual");
			
			tableHeaderCell = tableHeaderRow.createCell(2);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Modeled");
			
			tableHeaderCell = tableHeaderRow.createCell(3);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Difference");
			
			tableHeaderCell = tableHeaderRow.createCell(4);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Actual");
			
			tableHeaderCell = tableHeaderRow.createCell(5);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Estimated");
			
			tableHeaderCell = tableHeaderRow.createCell(6);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Difference");
			
			if(data != null && data.size() > 0) {
				int r = 9;
				for( int j = 0; j < data.size(); j++){
					AssetManagementAndOperationPerformanceDataEntity item = data.get(j);
					
					Row tableRow = sheet.createRow(r+j);
					Cell tableCell = tableRow.createCell(0);
					tableCell.setCellStyle(tableRowCellStyle);
					tableCell.setCellValue(item.getTime_full());
					
					tableCell = tableRow.createCell(1);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getActualEnergy() != null) tableCell.setCellValue(item.getActualEnergy());
					
					tableCell = tableRow.createCell(2);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getModeledEnergy() != null) tableCell.setCellValue(item.getModeledEnergy());
					
					tableCell = tableRow.createCell(3);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getEnergyDifference() != null) tableCell.setCellValue(item.getEnergyDifference());
					
					tableCell = tableRow.createCell(4);
					tableCell.setCellStyle(tableRowNoDecimalCurrencyCellStyle);
					if(item.getActualEnergyRevenue() != null) tableCell.setCellValue(item.getActualEnergyRevenue());
					
					tableCell = tableRow.createCell(5);
					tableCell.setCellStyle(tableRowNoDecimalCurrencyCellStyle);
					if(item.getEstimatedEnergyRevenue() != null) tableCell.setCellValue(item.getEstimatedEnergyRevenue());
					
					tableCell = tableRow.createCell(6);
					tableCell.setCellStyle(tableRowNoDecimalCurrencyCellStyle);
					if(item.getEnergyRevenueDifference() != null) tableCell.setCellValue(item.getEnergyRevenueDifference());
				}
			}
		} catch (Exception e) {
		}
	}
	
	private static void writeTableEstimatedLossByEventReport(Sheet sheet, List<AssetManagementAndOperationPerformanceDataEntity> data) {
		try {
			sheet.setColumnWidth(4, 256 * 25);
			sheet.setColumnWidth(5, 256 * 25);
			sheet.setColumnWidth(7, 256 * 25);
			CellStyle tableHeaderCellStyle = DocumentHelper.createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			CellStyle tableRowNoDecimaLeftAlignlCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			tableRowNoDecimaLeftAlignlCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCurrencyCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.noDecimalCurrencyDataFormat);
			
			Row tableHeaderRow = sheet.createRow(7);
			Cell tableHeaderCell = tableHeaderRow.createCell(0);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Event");
			tableHeaderCell = tableHeaderRow.createCell(1);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 1));
			
			tableHeaderCell = tableHeaderRow.createCell(2);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Device");
			tableHeaderCell = tableHeaderRow.createCell(3);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 2, 3));
			
			tableHeaderCell = tableHeaderRow.createCell(4);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Start Time");
			
			tableHeaderCell = tableHeaderRow.createCell(5);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("End Time");
			
			tableHeaderCell = tableHeaderRow.createCell(6);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Duration (day)");
			
			tableHeaderCell = tableHeaderRow.createCell(7);
			tableHeaderCell.setCellStyle(tableHeaderCellStyle);
			tableHeaderCell.setCellValue("Estimated Loss (kWh)");
			
			if(data != null && data.size() > 0) {
				int r = 8;
				for( int j = 0; j < data.size(); j++){
					AssetManagementAndOperationPerformanceDataEntity item = data.get(j);
					
					Row tableRow = sheet.createRow(r+j);
					Cell tableCell = tableRow.createCell(0);
					tableCell.setCellStyle(tableRowCellStyle);
					if(item.getEvent() != null) tableCell.setCellValue(item.getEvent());
					tableCell = tableRow.createCell(1);
					tableCell.setCellStyle(tableRowCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(r+j, r+j, 0, 1));
					
					tableCell = tableRow.createCell(2);
					tableCell.setCellStyle(tableRowCellStyle);
					if(item.getDevicename() != null) tableCell.setCellValue(item.getDevicename());
					tableCell = tableRow.createCell(3);
					tableCell.setCellStyle(tableRowCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(r+j, r+j, 2, 3));
					
					tableCell = tableRow.createCell(4);
					tableCell.setCellStyle(tableRowCellStyle);
					if(item.getStartTime() != null) tableCell.setCellValue(item.getStartTime());
					
					tableCell = tableRow.createCell(5);
					tableCell.setCellStyle(tableRowCellStyle);
					if(item.getEndTime() != null) tableCell.setCellValue(item.getEndTime());
					
					tableCell = tableRow.createCell(6);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getDuration() != null) tableCell.setCellValue(item.getDuration());
					
					tableCell = tableRow.createCell(7);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getEstimatedLoss() != null) tableCell.setCellValue(item.getEstimatedLoss());
				}
			}
		} catch (Exception e) {
		}
	}

}
