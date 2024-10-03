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
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
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
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFFillProperties;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFNoFillProperties;
import org.apache.poi.xddf.usermodel.XDDFShapeProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.AxisCrossBetween;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.AxisTickMark;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFLegendEntry;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDispBlanksAs;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.chart.STDispBlanksAs;
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
import com.nwm.api.entities.Book;
import com.nwm.api.entities.DailyDateEntity;
import com.nwm.api.entities.MonthlyDateEntity;
import com.nwm.api.entities.QuarterlyDateEntity;
import com.nwm.api.entities.ReportsEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.services.ReportsService;
import com.nwm.api.utils.Constants;
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

	public static final int COLUMN_INDEX_ID = 0;
	public static final int COLUMN_INDEX_TITLE = 1;
	public static final int COLUMN_INDEX_PRICE = 2;
	public static final int COLUMN_INDEX_QUANTITY = 3;
	public static final int COLUMN_INDEX_TOTAL = 4;
	private static CellStyle cellStyleFormatNumber = null;
	private static final String noDecimalDataFormat = "###,##0";
	private static final String oneDecimalPlaceDataFormat = "###,##0.0";
	private static final String fourDecimalPlaceDataFormat = "###,##0.0000";
	private static final String oneDecimalPlaceWithPercentageDataFormat = "###,##0.0%";
	private static final String noDecimalCurrencyDataFormat = "$###,##0";
	private static final Color BLUE_COLOR = new Color(49, 119, 168);
	private static final Color LIGHT_BLUE_COLOR = new Color(109, 189, 246);
	private static final Color ORANGE_COLOR = new Color(255, 129, 39);
	
	// Write header with format
		private static void writeHeaderDailyReport(Sheet sheet, ViewReportEntity dataObj) {
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
				
				CellStyle reportTitleCellStyle = createStyleForReportTitle(sheet);
				CellStyle reportInfoCellStyle = createStyleForReportInfo(sheet);
				CellStyle reportInfoBoldCellStyle = createStyleForReportInfoBold(sheet);
				CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
				CellStyle tableRowCellStyle = createStyleForTableRow(sheet);
				CellStyle tableRowNoDecimalCellStyle = createStyleForTableRowNumber(sheet);
				
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
				
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(dataObj.getStart_date() + " - " + dataObj.getEnd_date());
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
				int pictureIdx = readLogoImageFile(document);
				
				for (int i = 0; i < dataObjList.size(); i++) {
					ViewReportEntity dataObj = dataObjList.get(i);
					
					if (dataObj != null) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
						dataObj.setStart_date(format.format(dateFormat.parse(obj.getStart_date())));
						dataObj.setEnd_date(format.format(dateFormat.parse(obj.getEnd_date())));
						List<DailyDateEntity> dataExports = dataObj.getDataReports();
						int numOfPoints = dataExports != null ? dataExports.size() : 0;
						
						XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName((i + 1) + "_" + dataObj.getSite_name()));
						
						// insert logo image
						ClientAnchor logoAnchor = new XSSFClientAnchor(-20 * Units.EMU_PER_PIXEL, 10 * Units.EMU_PER_PIXEL, 0, -10 * Units.EMU_PER_PIXEL, 11, 0, 12, 4);
						insertLogo(sheet, logoAnchor, pictureIdx);
						
						// report information and table
						writeHeaderDailyReport(sheet, dataObj);
						
						// chart
						if (numOfPoints > 0) {
							ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, 0, 0, 0, 6, 12, 22);
							XDDFChart chart = insertChart(sheet, chartAnchor, null);
							
							// data sources
							XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 0, 0));
							XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 3, 3));
							XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 6, 6));
							XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 9, 9));
							
							// category axis
							XDDFCategoryAxis bottomAxis = createCategoryAxis(chart);
							// adjust tick mark position based on data intervals
							int dataIntervals = 1;
							if (dataObj.getData_intervals() == 1) dataIntervals = 288;
							else if (dataObj.getData_intervals() == 2) dataIntervals = 96;
							else if (dataObj.getData_intervals() == 3) dataIntervals = 24;
	                        chart.getCTChart().getPlotArea().getCatAxArray(0).addNewTickLblSkip().setVal(dataIntervals);
	                        chart.getCTChart().getPlotArea().getCatAxArray(0).addNewTickMarkSkip().setVal(dataIntervals);
							
							// left value axis
							XDDFValueAxis leftAxis = createLeftValueAxis(chart, "kW");
							
							XDDFChartData data = createChartData(chart, ChartTypes.LINE, bottomAxis, leftAxis);
							addSeries(dataExports.stream().allMatch(item -> item.getPower() == null), data, categoriesData, valuesData1, "Actual Power (kW)", PresetColor.STEEL_BLUE, null);
							
							chart.plot(data);
							
							// right value axis
							XDDFValueAxis rightAxis = createRightValueAxis(chart, bottomAxis, "kWh");
							
							data = createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
							addSeries(dataExports.stream().allMatch(item -> item.getEnergy() == null), data, categoriesData, valuesData2, "Estimate Energy (kWh)",  PresetColor.LIGHT_SKY_BLUE, null);
							
							chart.plot(data);
							
							if (dataObj.isHave_poa()) {
								// 2nd right value axis
								rightAxis = createRightValueAxis(chart, bottomAxis, "W/m2");
								
								data = createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
								addSeries(dataExports.stream().allMatch(item -> item.getIrradiance() == null), data, categoriesData, valuesData3, "Irradiance (W/m2)",  PresetColor.DARK_ORANGE, null);
								
								chart.plot(data);
							}
						}
					}
				}
				
				if (dataObjList.stream().anyMatch(item -> item != null)) {
					sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name());
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				} else {
					return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				}
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
				Image logoImage = readLogoImageFile();
				
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
						DecimalFormat dfs = new DecimalFormat(noDecimalDataFormat);
						for (int i = 0; i < dataExports.size(); i++) {
							DailyDateEntity item = (DailyDateEntity) dataExports.get(i);
							
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getCategories_time())));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getPower() != null ? dfs.format(item.getPower()).replaceAll( "^-(?=0(\\.0*)?$)", "") : "")));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getEnergy() != null ? dfs.format(item.getEnergy()) : "")));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getIrradiance() != null ? dfs.format(item.getIrradiance()) : "")));						
						}
						
						//====== chart ============================================================
						JFreeChart chart = createJFreeChart(null);
						XYPlot plot = chart.getXYPlot();
						
						// data source
						TimeSeriesCollection powerDataset = createJFreeChartLineDataset(0, plot);
						TimeSeries powerSeries = new TimeSeries("Actual Power (kW)");
						powerDataset.addSeries(powerSeries);
						plot.getRendererForDataset(powerDataset).setSeriesPaint(0, BLUE_COLOR);
						
						TimeSeriesCollection energyDataset = createJFreeChartLineDataset(1, plot);
						TimeSeries energySeries = new TimeSeries("Estimate Energy (kWh)");
						energyDataset.addSeries(energySeries);
						plot.getRendererForDataset(energyDataset).setSeriesPaint(0, LIGHT_BLUE_COLOR);
						
						TimeSeriesCollection irradianceDataset = createJFreeChartLineDataset(2, plot);
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
						createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.HOUR, 24, categoryFormat), startDate, endDate).setTickMarkPosition(DateTickMarkPosition.START);
						// left axis
						createJFreeChartNumberAxis("kW", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
						// right axis
						createJFreeChartNumberAxis("kWh", AxisLocation.BOTTOM_OR_RIGHT, 1, 1, plot);
						// 2nd right axis
						if (dataObj.isHave_poa()) createJFreeChartNumberAxis("W/m2", AxisLocation.BOTTOM_OR_RIGHT, 2, 2, plot);
						
						chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1800, 700), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 700));
						document.add(table);
						if (l < dataObjList.size() - 1) document.add(new AreaBreak());
					}
				}
				
				// It must be closed before attach to mail
				document.close();
				
				if (dataObjList.stream().anyMatch(item -> item != null)) {
					sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				} else {
					return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				}
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
	private static void writeHeaderAnnuallyReport(Sheet sheet, ArrayList<String> categories, ArrayList<Double> actualGeneration, ViewReportEntity dataObj, ArrayList<Double> baselineGeneration, ArrayList<Double> baselineGenerationIndex, ArrayList<Double> actualGenerationTrailing, ArrayList<Double> baselineGenerationTrailing, ArrayList<Double> baselineGenerationIndexTrailing, ArrayList<Double> INVAvailability) {
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
			
			CellStyle reportTitleCellStyle = createStyleForReportTitle(sheet);
			CellStyle reportInfoCellStyle = createStyleForReportInfo(sheet);
			CellStyle reportInfoBoldCellStyle = createStyleForReportInfoBold(sheet);
			CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
			CellStyle tableHeaderLeftAlignCellStyle = createStyleForTableHeader(sheet);
			tableHeaderLeftAlignCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowLeftAlignCellStyle = createStyleForTableRow(sheet);
			tableRowLeftAlignCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRowNumber(sheet);
			CellStyle tableRowOneDecimalPlaceCellStyle = createStyleForTableRowNumber(sheet);
			tableRowOneDecimalPlaceCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceDataFormat));
			CellStyle tableTitleCellStyle = createStyleForTableTitle(sheet);

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

			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(dataObj.getStart_date() + " - " + dataObj.getEnd_date());

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

			// Monthly Data
			Row row7 = sheet.createRow(7);
			Cell cell7 = row7.createCell(0);
			cell7.setCellStyle(tableHeaderLeftAlignCellStyle);
			cell7.setCellValue("Monthly Data");
			Cell cell71 = row7.createCell(1);
			cell71.setCellStyle(tableHeaderLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 1));

			int r = 2;
			for (int i = 0; i < categories.size(); i++) {
				cell7 = row7.createCell(r + i);
				cell7.setCellStyle(tableHeaderCellStyle);
				cell7.setCellValue(categories.get(i));
			}
			

			// Actual Generation (kWh)
			Row row8 = sheet.createRow(8);
			Cell cell8 = row8.createCell(0);
			cell8.setCellStyle(tableRowLeftAlignCellStyle);
			cell8.setCellValue("Actual Generation (kWh)");
			Cell cell81 = row8.createCell(1);
			cell81.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 1));
			
			int v = 2;
			for (int i = 0; i < actualGeneration.size(); i++) {
				cell8 = row8.createCell(v + i);
				cell8.setCellStyle(tableRowNoDecimalCellStyle);
				if(actualGeneration.get(i) != null) cell8.setCellValue(actualGeneration.get(i));
			}
			

			// Baseline Generation (kWh)
			Row row9 = sheet.createRow(9);
			Cell cell9 = row9.createCell(0);
			cell9.setCellStyle(tableRowLeftAlignCellStyle);
			cell9.setCellValue("Estimated Generation (kWh)");
			Cell cell91 = row9.createCell(1);
			cell91.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 1));
			
			int u = 2;
			for (int i = 0; i < baselineGeneration.size(); i++) {
				cell9 = row9.createCell(u + i);
				cell9.setCellStyle(tableRowNoDecimalCellStyle);
				if(baselineGeneration.get(i) != null) cell9.setCellValue(baselineGeneration.get(i));
			}


			// Baseline Generation Index (%)
			Row row10 = sheet.createRow(10);
			Cell cell10 = row10.createCell(0);
			cell10.setCellStyle(tableRowLeftAlignCellStyle);
			cell10.setCellValue("Estimated Generation Index (%)");
			Cell cell101 = row10.createCell(1);
			cell101.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(10, 10, 0, 1));
			
			int n = 2;
			for (int i = 0; i < baselineGenerationIndex.size(); i++) {
				cell10 = row10.createCell(n + i);
				cell10.setCellStyle(tableRowOneDecimalPlaceCellStyle);
				if(baselineGenerationIndex.get(i) != null) cell10.setCellValue(baselineGenerationIndex.get(i));
			}


			// Trailing Twelve Month Generation
			Row row11 = sheet.createRow(11);
			Cell cell11 = row11.createCell(0);
			cell11.setCellStyle(tableHeaderLeftAlignCellStyle);
			cell11.setCellValue("Trailing Twelve Month Generation");
			Cell cell111 = row11.createCell(1);
			cell111.setCellStyle(tableHeaderLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(11, 11, 2, 13));
			
			// Actual Generation (kWh)
			Row row12 = sheet.createRow(12);
			Cell cell12 = row12.createCell(0);
			cell12.setCellStyle(tableRowLeftAlignCellStyle);
			cell12.setCellValue("Actual Generation (kWh)");
			sheet.addMergedRegion(new CellRangeAddress(12, 12, 0, 1));
			
			int z = 2;
			for (int i = 0; i < actualGenerationTrailing.size(); i++) {
				cell12 = row12.createCell(z + i);
				cell12.setCellStyle(tableRowNoDecimalCellStyle);
				if(actualGenerationTrailing.get(i) != null) cell12.setCellValue(actualGenerationTrailing.get(i));
			}

			// Baseline Generation (kWh)
			Row row13 = sheet.createRow(13);
			Cell cell13 = row13.createCell(0);
			cell13.setCellStyle(tableRowLeftAlignCellStyle);
			cell13.setCellValue("Estimated Generation (kWh)");
			Cell cell131 = row13.createCell(1);
			cell131.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 1));
			
			int m = 2;
			for (int i = 0; i < baselineGenerationTrailing.size(); i++) {
				cell13 = row13.createCell(m + i);
				cell13.setCellStyle(tableRowNoDecimalCellStyle);
				if (baselineGenerationTrailing.get(i) != null) cell13.setCellValue(baselineGenerationTrailing.get(i));
			}

			// Baseline Generation Index (%)
			Row row14 = sheet.createRow(14);
			Cell cell14 = row14.createCell(0);
			cell14.setCellStyle(tableRowLeftAlignCellStyle);
			cell14.setCellValue("Estimated Generation Index (%)");
			Cell cell141 = row14.createCell(1);
			cell141.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 1));
			
			int g = 2;
			for (int i = 0; i < baselineGenerationIndexTrailing.size(); i++) {
				cell14 = row14.createCell(g + i);
				cell14.setCellStyle(tableRowOneDecimalPlaceCellStyle);
				if (baselineGenerationIndexTrailing.get(i) != null) cell14.setCellValue(baselineGenerationIndexTrailing.get(i));
			}


			// Inverter Availability (%)
			Row row15 = sheet.createRow(15);
			Cell cell15 = row15.createCell(0);
			cell15.setCellStyle(tableRowLeftAlignCellStyle);
			cell15.setCellValue(dataObj.getDeviceType() == "meter" ? "Site Availability (%)" : "Inverter Availability (%)");
			Cell cell151 = row15.createCell(1);
			cell151.setCellStyle(tableRowLeftAlignCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 1));

			int h = 2;
			for (int i = 0; i < INVAvailability.size(); i++) {
				cell15 = row15.createCell(h + i);
				cell15.setCellStyle(tableRowOneDecimalPlaceCellStyle);
				if (INVAvailability.get(i) != null) cell15.setCellValue(INVAvailability.get(i));
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
			int pictureIdx = readLogoImageFile(document);
				
			for (int i = 0; i < dataObjList.size(); i++) {
				ViewReportEntity dataObj = dataObjList.get(i);
				
				if (dataObj != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					Date endDate = dateFormat.parse(obj.getEnd_date());
					Date startDate = dateFormat.parse(obj.getStart_date());
					dataObj.setStart_date(format.format(startDate));
					dataObj.setEnd_date(format.format(endDate));
					
					ArrayList<String> categories = new ArrayList<String>();
					ArrayList<Double> actualGeneration = new ArrayList<Double>();
					ArrayList<Double> baselineGeneration = new ArrayList<Double>();
					ArrayList<Double> baselineGenerationIndex = new ArrayList<Double>();
					
					ArrayList<Double> actualGenerationTrailing = new ArrayList<Double>();
					ArrayList<Double> baselineGenerationTrailing = new ArrayList<Double>();
					ArrayList<Double> baselineGenerationIndexTrailing = new ArrayList<Double>();
					ArrayList<Double> INVAvailability = new ArrayList<Double>();
				
					SimpleDateFormat dateFormatCategories = new SimpleDateFormat("MMM-yyyy");
					SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
					SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
					List dataExports = dataObj.getDataReports();
					List dataAvailability = dataObj.getDataAvailability();
					List dataExpectations = dataObj.getDataExpectations();
					Double totalGeneration = null;
					Double totalExpectations = null;
					
					for (int k = 0; k < 12; k++) {
						Calendar c = Calendar.getInstance();
						c.setTime(startDate);
						c.add(Calendar.MONTH, k);
						categories.add(monthFormat.format(c.getTime()));
						Double v = null;
						
						if(dataExports != null) {
							for( int j = 0; j < dataExports.size(); j++){
								Map<String, Object> item = (Map<String, Object>) dataExports.get(j);
								String date = (String) item.get("categories_time");
								if(date.equals(dateFormatCategories.format(c.getTime()) )) {
									v = (Double)item.get("chart_energy_kwh");
									if(v != null) totalGeneration = totalGeneration != null ? totalGeneration + v : v;
									break;
								}
							}
						}
						
						actualGeneration.add(v);
						actualGenerationTrailing.add(totalGeneration);
						
						Double availability = null;
						
						if(dataAvailability.size() > 0) {
							for( int j = 0; j < dataAvailability.size(); j++){
								Map<String, Object> item = (Map<String, Object>) dataAvailability.get(j);
								String date = (String) item.get("time_full");
								if(date.equals(dateFormatCategories.format(c.getTime()) )) {
									availability = (Double)item.get("InverterAvailability");
									break;
								}
							}
						}
						
						INVAvailability.add(availability);
						
						// baseline Generation
						Double baseline = null;
						if(dataExpectations.size() > 0) {
							for( int j = 0; j < dataExpectations.size(); j++){
								Map<String, Object> itemEx = (Map<String, Object>) dataExpectations.get(j);
								String year = itemEx.get("year").toString();
								if(year.equals(yearFormat.format(c.getTime()))) {
									Double monthValue = Double.parseDouble(itemEx.get((monthFormat.format(c.getTime())).toLowerCase()).toString());
									baseline = monthValue;
									if(baseline != null) totalExpectations = totalExpectations != null ? totalExpectations + baseline : baseline;
									break;
								}
							}
						}
						
						baselineGeneration.add(baseline);
						baselineGenerationIndex.add(v != null && baseline != null && baseline != 0 ? (v/baseline) * 100 : null);
						baselineGenerationTrailing.add(totalExpectations);
						baselineGenerationIndexTrailing.add(totalGeneration != null && totalExpectations != null && totalExpectations != 0 ? (totalGeneration/totalExpectations) * 100 : null);
					}
					
					XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName((i + 1) + "_" + dataObj.getSite_name()));
					sheet.setZoom(95);

					// insert logo image
					ClientAnchor logoAnchor = new XSSFClientAnchor(0, 0, 20 * Units.EMU_PER_PIXEL, 15 * Units.EMU_PER_PIXEL, 12, 1, 13, 4);
					insertLogo(sheet, logoAnchor, pictureIdx);

					// report information and table
					writeHeaderAnnuallyReport(sheet, categories, actualGeneration, dataObj, baselineGeneration, baselineGenerationIndex, actualGenerationTrailing, baselineGenerationTrailing, baselineGenerationIndexTrailing, INVAvailability );

					// chart
					ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, 0, 0, 0, 18, 14, 40);
					XDDFChart chart = insertChart(sheet, chartAnchor, "Annual Performance");
					
					// data sources
					int numOfPoints = categories.size();
					XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(7, 7, 2, 2 + numOfPoints - 1));
					XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(8, 8, 2, 2 + numOfPoints - 1));
					XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(9, 9, 2, 2 + numOfPoints - 1));
					XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(10, 10, 2, 2 + numOfPoints - 1));

					// category axis
					XDDFCategoryAxis bottomAxis = createCategoryAxis(chart);
					
					// left value axis
					XDDFValueAxis leftAxis = createLeftValueAxis(chart, "GENERATION (KWH)");

					XDDFChartData chartData = createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
					addSeries(actualGeneration.stream().allMatch(item -> item == null), chartData, categoriesData, valuesData1, "Actual Generation (kWh)", PresetColor.STEEL_BLUE, null);
					addSeries(baselineGeneration.stream().allMatch(item -> item == null), chartData, categoriesData, valuesData2, "Estimated Generation (kWh)", PresetColor.LIGHT_SKY_BLUE, null);
					
					chart.plot(chartData);

					// right value axis
					XDDFValueAxis rightAxis = createRightValueAxis(chart, bottomAxis, "PERFORMANCE INDEX (%)");

					chartData = createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
					addSeries(baselineGenerationIndex.stream().allMatch(item -> item == null), chartData, categoriesData, valuesData3, "Estimated Generation Index (%)", PresetColor.GRAY, null);
					
					chart.plot(chartData);
				}
			}
				
			if (dataObjList.stream().anyMatch(item -> item != null)) {
				sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name());
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
			} else {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
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
				Image logoImage = readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
					ViewReportEntity dataObj = dataObjList.get(l);
				
					if (dataObj != null) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date startDate = dateFormat.parse(obj.getStart_date());
						Date endDate = dateFormat.parse(obj.getEnd_date());
						dataObj.setStart_date(new SimpleDateFormat("MM/dd/yyyy").format(startDate));
						dataObj.setEnd_date(new SimpleDateFormat("MM/dd/yyyy").format(endDate));
						
						List<?> dataExports = dataObj.getDataReports();
						List<?> dataAvailability = dataObj.getDataAvailability();
						List<?> dataExpectations = dataObj.getDataExpectations();
						
						// calculate for data of table
						ArrayList<String> categories = new ArrayList<String>();
						ArrayList<String> monthYearCategories = new ArrayList<String>();
						ArrayList<Double> actualGeneration = new ArrayList<Double>();
						ArrayList<Double> baselineGeneration = new ArrayList<Double>();
						ArrayList<Double> baselineGenerationIndex = new ArrayList<Double>();
						
						ArrayList<Double> actualGenerationTrailing = new ArrayList<Double>();
						ArrayList<Double> baselineGenerationTrailing = new ArrayList<Double>();
						ArrayList<Double> baselineGenerationIndexTrailing = new ArrayList<Double>();
						ArrayList<Double> INVAvailability = new ArrayList<Double>();
						
						SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMM-yyyy");
						SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
						SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
						
						Double totalGeneration = null;
						Double totalExpectations = null;
						
						for (int i = 0; i < 12; i++) {
							Calendar c = Calendar.getInstance();
							c.setTime(startDate);
							c.add(Calendar.MONTH, i);
							categories.add(monthFormat.format(c.getTime()));
							monthYearCategories.add(monthYearFormat.format(c.getTime()));
							Double v = null;
							
							if(dataExports != null) {
								for( int j = 0; j < dataExports.size(); j++){
									Map<String, Object> item = (Map<String, Object>) dataExports.get(j);
									String date = (String) item.get("categories_time");
									if(date.equals(monthYearFormat.format(c.getTime()) )) {
										v = (Double)item.get("chart_energy_kwh");
										if(v != null) totalGeneration = totalGeneration != null ? totalGeneration + v : v;
										break;
									}
								}
							}
							
							actualGeneration.add(v);
							actualGenerationTrailing.add(totalGeneration);
							
							Double availability = null;
							
							if(dataAvailability.size() > 0) {
								for( int j = 0; j < dataAvailability.size(); j++){
									Map<String, Object> item = (Map<String, Object>) dataAvailability.get(j);
									String date = (String) item.get("time_full");
									if(date.equals(monthYearFormat.format(c.getTime()) )) {
										availability = (Double)item.get("InverterAvailability");
										break;
									}
								}
							}
	
							INVAvailability.add(availability);
							
							// baseline Generation
							Double baseline = null;
							if(dataExpectations.size() > 0) {
								for( int k = 0; k < dataExpectations.size(); k++){
									Map<String, Object> itemEx = (Map<String, Object>) dataExpectations.get(k);
									String year = itemEx.get("year").toString();
									if(year.equals(yearFormat.format(c.getTime()))) {
										Double monthValue = Double.parseDouble(itemEx.get((monthFormat.format(c.getTime())).toLowerCase()).toString());
										baseline = monthValue;
										if(baseline != null) totalExpectations = totalExpectations != null ? totalExpectations + baseline : baseline;
									}
								}
							}
							
							baselineGeneration.add(baseline);
							baselineGenerationIndex.add(v != null && baseline != null && baseline != 0 ? (v/baseline) * 100 : null);
							baselineGenerationTrailing.add(totalExpectations);
							baselineGenerationIndexTrailing.add(totalGeneration != null && totalExpectations != null && totalExpectations != 0 ? (totalGeneration/totalExpectations) * 100 : null);
						}
	
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
						DecimalFormat df = new DecimalFormat(noDecimalDataFormat);
						DecimalFormat dfp = new DecimalFormat(oneDecimalPlaceDataFormat);
	
						// Monthly Data
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Monthly Data").setBold()).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < categories.size(); i++) {
							table.addCell(new Paragraph(categories.get(i)).setBold());
						}
						
						// Actual Generation (kWh)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Actual Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < actualGeneration.size(); i++) {
							table.addCell(actualGeneration.get(i) != null ? df.format(actualGeneration.get(i)) : "");
						}
						
						// Baseline Generation (kWh)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < baselineGeneration.size(); i++) {
							table.addCell(baselineGeneration.get(i) != null ? df.format(baselineGeneration.get(i)) : "");
						}
						
						// Baseline Generation Index (%)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation Index (%)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < baselineGenerationIndex.size(); i++) {
							table.addCell(baselineGenerationIndex.get(i) != null ? dfp.format(baselineGenerationIndex.get(i)) : "");
						}
						
						// Trailing Twelve Month Generation
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Trailing Twelve Month Generation").setBold()).setTextAlignment(TextAlignment.LEFT));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 12));
						
						// Actual Generation (kWh)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Actual Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < actualGenerationTrailing.size(); i++) {
							table.addCell(actualGenerationTrailing.get(i) != null ? df.format(actualGenerationTrailing.get(i)) : "");
						}
						
						// Baseline Generation (kWh)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < baselineGenerationTrailing.size(); i++) {
							table.addCell(baselineGenerationTrailing.get(i) != null ? df.format(baselineGenerationTrailing.get(i)) : "");
						}
						
						// Baseline Generation Index (%)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation Index (%)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < baselineGenerationIndexTrailing.size(); i++) {
							table.addCell(baselineGenerationIndexTrailing.get(i) != null ? dfp.format(baselineGenerationIndexTrailing.get(i)) : "");
						}
						
						// Inverter Availability (%)
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getDeviceType() == "meter" ? "Site Availability (%)" : "Inverter Availability (%)")).setTextAlignment(TextAlignment.LEFT));
						for (int i = 0; i < INVAvailability.size(); i++) {
							table.addCell(INVAvailability.get(i) != null ? dfp.format(INVAvailability.get(i)) : "");
						}
	
						// empty row: gap between data table and chart
						table.addCell(new com.itextpdf.layout.element.Cell(1, 14).setHeight(14).setBorder(Border.NO_BORDER));
						
						// chart
						com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(22, 14);
						table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
						
						//====== chart ============================================================
						JFreeChart chart = createJFreeChart("Annual Performance");
						XYPlot plot = chart.getXYPlot();
						
						// data source
						TimeSeriesCollection barDataset = createJFreeChartBarDataset(0, plot);
						TimeSeries actualSeries = new TimeSeries("Actual Generation (kWh)");
						barDataset.addSeries(actualSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(0, BLUE_COLOR);
						TimeSeries estimateSeries = new TimeSeries("Estimate Generation (kWh)");
						barDataset.addSeries(estimateSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(1, LIGHT_BLUE_COLOR);
						
						TimeSeriesCollection lineDataset = createJFreeChartLineDataset(1, plot);
						TimeSeries estimateIndexSeries = new TimeSeries("Estimate Generation Index (%)");
						lineDataset.addSeries(estimateIndexSeries);
						plot.getRendererForDataset(lineDataset).setSeriesPaint(0, Color.gray);
						
						for (int i = 0; i < monthYearCategories.size(); i++) {
							Month period = new Month(monthYearFormat.parse(monthYearCategories.get(i)));
							
							actualSeries.add(period, actualGeneration.get(i));
							estimateSeries.add(period, baselineGeneration.get(i));
							estimateIndexSeries.add(period, baselineGenerationIndex.get(i));
						}
						
						// category axis
						createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.MONTH, 1, monthFormat), startDate, endDate);
						// left axis
						createJFreeChartNumberAxis("GENERATION (KWH)", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
						// right axis
						createJFreeChartNumberAxis("PERFORMANCE INDEX (%)", AxisLocation.BOTTOM_OR_RIGHT, 1, 1, plot);
						
						chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1650, 600), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 400));
						document.add(table);
						if (l < dataObjList.size() - 1) document.add(new AreaBreak());
					}
				}
				
				// It must be closed before attach to mail
				document.close();
				
				if (dataObjList.stream().anyMatch(item -> item != null)) {
					sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				} else {
					return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				}
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
			
			SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
			SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = dateFormat.parse(obj.getStart_date());
			Date endDate = dateFormat.parse(obj.getEnd_date());
			
			Calendar calQ = Calendar.getInstance();
			calQ.setTime(startDate);
			calQ.setTime(endDate);
			
			List categoriesHeader = new ArrayList ();
			
			SimpleDateFormat catFormatMonth = new SimpleDateFormat("MMM-yyyy");
			SimpleDateFormat timeFullFormat = new SimpleDateFormat("MM-yyyy");
			Calendar cal = Calendar.getInstance();
			
			for(int t = 0; t <12; t++) {
				cal.setTime(startDate);
				QuarterlyDateEntity headerDate = new QuarterlyDateEntity();
				cal.add(Calendar.MONTH, t);
				String timeFull = timeFullFormat.format(cal.getTime());
				
				headerDate.setMonth(monthFormat.format(cal.getTime()).toString());
				headerDate.setYear(yearFormat.format(cal.getTime()).toString().toUpperCase());
				headerDate.setTime_full(timeFull.toString());
				String categoriesTime = catFormatMonth.format(cal.getTime());
				headerDate.setCategories_time(categoriesTime);
				headerDate.setTime_format(timeFull.toString());
				headerDate.setMonth_number(1 + t);
				categoriesHeader.add(headerDate);	
			}
			

			List dataExports = dataObj.getDataReports();
			List dataNewExports = new ArrayList();
			
			for( int k = 0; k < categoriesHeader.size(); k++){
				QuarterlyDateEntity itemC = (QuarterlyDateEntity) categoriesHeader.get(k);
				String fullTime = itemC.getTime_full();
				
				boolean flag = false;
				Map<String, Object> itemObj = new HashMap<String, Object>();
				
				if (dataExports != null && dataExports.size() > 0) {
					for( int v = 0; v < dataExports.size(); v++){
						Map<String, Object> itemT = (Map<String, Object>) dataExports.get(v);
						if(fullTime.equals(itemT.get("time_full"))) {
							flag = true;
							itemObj = (Map<String, Object>) dataExports.get(v);
						}
					}
				}
				
				if(flag == false) {
					Map<String, Object> mapItem = new HashMap<String, Object>();
					mapItem.put("year", itemC.getYear());
					mapItem.put("month", itemC.getMonth());
					
					mapItem.put("time", "");
					mapItem.put("time_format", itemC.getMonth());
					mapItem.put("categories_time", itemC.getCategories_time());
					mapItem.put("chart_energy_kwh", null);
					mapItem.put("time_full", itemC.getTime_full());
					mapItem.put("baseline", (double)0);
					mapItem.put("month_number", itemC.getMonth_number());
					dataNewExports.add(mapItem);
				} else {
					dataNewExports.add(itemObj);
				}
			}
			
			dataObj.setDataReports(dataNewExports);

			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
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
				
				CellStyle reportTitleCellStyle = createStyleForReportTitle(sheet);
				CellStyle reportInfoCellStyle = createStyleForReportInfo(sheet);
				CellStyle reportInfoBoldCellStyle = createStyleForReportInfoBold(sheet);
				CellStyle tableTitleCellStyle = createStyleForTableTitle(sheet);
				CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
				CellStyle tableRowNoDecimalCellStyle = createStyleForTableRowNumber(sheet);
				CellStyle tableRowOneDecimalPlaceCellStyle = createStyleForTableRowNumber(sheet);
				tableRowOneDecimalPlaceCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceDataFormat));
				CellStyle tableRowFourDecimalPlaceCellStyle = createStyleForTableRowNumber(sheet);
				tableRowFourDecimalPlaceCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(fourDecimalPlaceDataFormat));
				CellStyle tableRowNoDecimalBoldCellStyle = createStyleForNoBorderTableRowNumber(sheet);
				tableRowNoDecimalBoldCellStyle.setBorderTop(BorderStyle.MEDIUM);
				tableRowNoDecimalBoldCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				CellStyle tableRowOneDecimalPlaceBoldCellStyle = createStyleForNoBorderTableRowNumber(sheet);
				tableRowOneDecimalPlaceBoldCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceDataFormat));
				tableRowOneDecimalPlaceBoldCellStyle.setBorderTop(BorderStyle.MEDIUM);
				tableRowOneDecimalPlaceBoldCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				CellStyle tableRowNoDecimalBlueBgCellStyle = createStyleForTableRowNumber(sheet);
				tableRowNoDecimalBlueBgCellStyle.setFillBackgroundColor(IndexedColors.PALE_BLUE.index);
				tableRowNoDecimalBlueBgCellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
				tableRowNoDecimalBlueBgCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
				CellStyle tableRowNoDecimalRedTextCellStyle = createStyleForTableRowNumber(sheet);
				Font redFont = sheet.getWorkbook().createFont();
				redFont.setFontName("Times New Roman");
				redFont.setFontHeightInPoints((short) 12);
				redFont.setColor(IndexedColors.RED.getIndex());
				tableRowNoDecimalRedTextCellStyle.setFont(redFont);
				CellStyle tableRowOneDecimalPlaceRedTextCellStyle = createStyleForTableRowNumber(sheet);
				tableRowOneDecimalPlaceRedTextCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceDataFormat));
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
			int pictureIdx = readLogoImageFile(document);
				
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
					insertLogo(sheet, logoAnchor, pictureIdx);

					// report information and table
					writeHeaderQuarterlyReport(sheet, dataObj);
					
					if (quarterlyReportByMonth && numOfPoints > 0) {
						// 1st chart
						ClientAnchor chartAnchor = new XSSFClientAnchor(6, 6, 6, 6, 6, 9, 13, 23);
						XDDFChart chart = insertChart(sheet, chartAnchor, null);
						
						// data source
						XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(11, 13, 0, 0));
						XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(11, 13, 1, 1));
						XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(11, 13, 2, 2));
	
						// category axis
						XDDFCategoryAxis bottomAxis = createCategoryAxis(chart);
						
						// left value axis
						XDDFValueAxis leftAxis = createLeftValueAxis(chart, null);
						
						XDDFChartData data = createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						addSeries(dataExports.stream().allMatch(item -> item.getEstimated() == null), data, categoriesData, valuesData1, "Estimated Generation (kWh)", PresetColor.STEEL_BLUE, null);
						addSeries(dataExports.stream().allMatch(item -> item.getActual() == null), data, categoriesData, valuesData2, "Actual Generation (kWh)", PresetColor.LIGHT_SKY_BLUE, null);
						
						chart.plot(data);
					
						// 2nd chart
						chartAnchor = new XSSFClientAnchor(6, 6, 6, 6, 6, 26, 13, 39);
						chart = insertChart(sheet, chartAnchor, null);
					    
						XDDFDataSource<String> categoriesData2 = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(28, 30, 0, 0));
						XDDFNumericalDataSource<Double> valuesData12 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(28, 30, 1, 1));
						XDDFNumericalDataSource<Double> valuesData22 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(28, 30, 2, 2));
					    
						// category axis
						bottomAxis = createCategoryAxis(chart);
						
						// left value axis
						leftAxis = createLeftValueAxis(chart, null);
						
						data = createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						addSeries(dataExports.stream().allMatch(item -> item.getEstimatedCumulative() == null), data, categoriesData2, valuesData12, "Estimated Generation (kWh)", PresetColor.STEEL_BLUE, null);
						addSeries(dataExports.stream().allMatch(item -> item.getActualCumulative() == null), data, categoriesData2, valuesData22, "Actual Generation (kWh)", PresetColor.LIGHT_SKY_BLUE, null);
	
						chart.plot(data);
					}
				}
			}
					
			if (dataObjList.stream().anyMatch(item -> item != null)) {
				sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name());
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
			} else {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
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
				Image logoImage = readLogoImageFile();
				
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
						DecimalFormat df = new DecimalFormat(noDecimalDataFormat);
						DecimalFormat dfp = new DecimalFormat(oneDecimalPlaceDataFormat);
						DecimalFormat dfp4 = new DecimalFormat(fourDecimalPlaceDataFormat);
						
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
							JFreeChart chart = createJFreeChart(null);
							XYPlot plot = chart.getXYPlot();
							
							// data source
							TimeSeriesCollection barDataset = createJFreeChartBarDataset(0, plot);
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
							createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.MONTH, 1, monthYearFormat), startDate, endDate);
							// left axis
							createJFreeChartNumberAxis(null, AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
							
							chartCell1.add(new Image(ImageDataFactory.create(chart.createBufferedImage(900, 300), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 300));
							
							//====== second chart ============================================================
							JFreeChart chart2 = createJFreeChart(null);
							XYPlot plot2 = chart2.getXYPlot();
							
							// data source
							TimeSeriesCollection barDataset2 = createJFreeChartBarDataset(0, plot2);
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
							createJFreeChartDomainAxis(plot2, new DateTickUnit(DateTickUnitType.MONTH, 1, monthYearFormat), startDate, endDate);
							// left axis
							createJFreeChartNumberAxis(null, AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot2);
							
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

				if (dataObjList.stream().anyMatch(item -> item != null)) {
					sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				} else {
					return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				}
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
	
	private byte[] hex2Rgb(String colorStr) {
        int r = Integer.valueOf(colorStr.substring(1, 3), 16);
        int g = Integer.valueOf(colorStr.substring(3, 5), 16);
        int b = Integer.valueOf(colorStr.substring(5, 7), 16);      
        return new byte[]{(byte) r, (byte) g, (byte) b};
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
				
				int pictureIdx = readLogoImageFile(document);
				
				/* === operation performance === */
				if (dataObj != null) {
					List<AssetManagementAndOperationPerformanceDataEntity> data = dataObj.getOperationPerformanceData();
					int numOfPoints = data != null ? data.size() : 0;
					XSSFSheet sheet = document.createSheet("Operation Performance");
					
					// insert logo image
					ClientAnchor logoAnchor = new XSSFClientAnchor(25 * Units.EMU_PER_PIXEL, 0, 0, 0, 11, 0, 12, 5);
					insertLogo(sheet, logoAnchor, pictureIdx);
					
					// report information
					writeHeaderAssetManagementAndOperationPerformanceReport(sheet, dataObj.getReportDetail(), 12, "OPERATION PERFORMANCE SUMMARY");
					
					// report table
					writeTableOperationPerformanceReport(sheet, data);
					
					if (numOfPoints > 0) {
						// --- energy generation chart ---
						ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, 0, 0, 0, numOfPoints + 11, 12, numOfPoints + 11 + 16);
						XDDFChart chart = insertChart(sheet, chartAnchor, "Energy Generation");
						
						// data sources
						int firstRow = 9;
						XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 0, 0));
						XDDFNumericalDataSource<Double> actualEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 1, 1));
						XDDFNumericalDataSource<Double> modeledEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 2, 2));
						XDDFNumericalDataSource<Double> expectedEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 3, 3));
						XDDFNumericalDataSource<Double> actualIrradiance = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 4, 4));
						XDDFNumericalDataSource<Double> modeledIrradiance = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 5, 5));
						
						// category axis
						XDDFCategoryAxis bottomAxis = createCategoryAxis(chart);
						
						// left value axis
						XDDFValueAxis leftAxis = createLeftValueAxis(chart, null);
						
						XDDFChartData chartData = createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						addSeries(data.stream().allMatch(item -> item.getActualEnergy() == null), chartData, categories, actualEnergy, "Actual Energy", PresetColor.STEEL_BLUE, PresetColor.BLACK);
						addSeries(data.stream().allMatch(item -> item.getModeledEnergy() == null), chartData, categories, modeledEnergy, "Modeled Energy", PresetColor.DARK_GRAY, PresetColor.BLACK);
						addSeries(data.stream().allMatch(item -> item.getExpectedEnergy() == null), chartData, categories, expectedEnergy, "Actual Energy", PresetColor.LIGHT_STEEL_BLUE, PresetColor.BLACK);
						
						chart.plot(chartData);
						
						// right value axis
						XDDFValueAxis rightAxis = createRightValueAxis(chart, bottomAxis, null);
						
						chartData = createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						addSeries(data.stream().allMatch(item -> item.getActualPOA() == null), chartData, categories, actualIrradiance, "Actual Irradiance", PresetColor.DARK_ORANGE, null);
						addSeries(data.stream().allMatch(item -> item.getModeledPOA() == null), chartData, categories, modeledIrradiance, "Modeled Irradiance", PresetColor.GRAY, null);
						
						chart.plot(chartData);
						
						// --- performance ratio chart ---
						chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, -10 * Units.EMU_PER_PIXEL, 0, 0, numOfPoints + 29, 6, numOfPoints + 29 + 15);
						chart = insertChart(sheet, chartAnchor, "Performance Ratio");
						
						// chart data sources
						XDDFNumericalDataSource<Double> actualPerformanceRatio = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 6, 6));
						XDDFNumericalDataSource<Double> modeledPerformanceRatio = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 7, 7));
						
						// category axis
						bottomAxis = createCategoryAxis(chart);
						
						// left value axis
						leftAxis = createLeftValueAxis(chart, null);
						
						chartData = createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						addSeries(data.stream().allMatch(item -> item.getActualPerformanceRatio() == null), chartData, categories, actualPerformanceRatio, "Actual Generation", PresetColor.STEEL_BLUE, PresetColor.BLACK);
						addSeries(data.stream().allMatch(item -> item.getModeledPerformanceRatio() == null), chartData, categories, modeledPerformanceRatio, "Modeled Generation", PresetColor.DARK_GRAY, PresetColor.BLACK);
						
						chart.plot(chartData);
						
						// --- key performance indicators chart ---
						chartAnchor = new XSSFClientAnchor(10 * Units.EMU_PER_PIXEL, 0, 0, 0, 6, numOfPoints + 29, 12, numOfPoints + 29 + 15);
						chart = insertChart(sheet, chartAnchor, "Key Performance Indicators");
						
						// chart data sources
						XDDFNumericalDataSource<Double> energyIndex = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 8, 8));
						XDDFNumericalDataSource<Double> weatherIndex = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 9, 9));
						XDDFNumericalDataSource<Double> weatherAdjustedIndex = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 10, 10));
						XDDFNumericalDataSource<Double> inverterAvailability = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 11, 11));
						
						// category axis
						bottomAxis = createCategoryAxis(chart);
						
						// left value axis
						leftAxis = createLeftValueAxis(chart, null);
						
						chartData = createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						addSeries(data.stream().allMatch(item -> item.getEnergyIndex() == null), chartData, categories, actualPerformanceRatio, "Energy Index", PresetColor.STEEL_BLUE, PresetColor.BLACK);
						addSeries(data.stream().allMatch(item -> item.getWeatherAdjustedIndex() == null), chartData, categories, actualPerformanceRatio, "Weather Adjusted Index", PresetColor.DARK_GRAY, PresetColor.BLACK);
						
						chart.plot(chartData);
						
						// right value axis
						rightAxis = createRightValueAxis(chart, bottomAxis, null);
						
						chartData = createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						addSeries(data.stream().allMatch(item -> item.getWeatherIndex() == null), chartData, categories, weatherIndex, "Weather Index", PresetColor.GRAY, null);
						addSeries(data.stream().allMatch(item -> item.getInverterAvailability() == null), chartData, categories, inverterAvailability, "Inverter Availability", PresetColor.DARK_ORANGE, null);
						
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
						insertLogo(sheet, logoAnchor, pictureIdx);
						
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
							XDDFChart chart = insertChart(sheet, chartAnchor, null);
							
							// chart data sources
							int firstRow = 9;
							XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 0, 0));
							XDDFNumericalDataSource<Double> actualEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 1, 1));
							XDDFNumericalDataSource<Double> expectedEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 2, 2));
							XDDFNumericalDataSource<Double> modeledEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 3, 3));
							
							// category axis
							XDDFCategoryAxis bottomAxis = createCategoryAxis(chart);
							
							// left value axis
							XDDFValueAxis leftAxis = createLeftValueAxis(chart, null);
							
							XDDFChartData chartData = createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
							
							addSeries(data.stream().allMatch(item -> item.getActualEnergy() == null), chartData, categories, actualEnergy, "Actual", PresetColor.STEEL_BLUE, PresetColor.BLACK);
							addSeries(data.stream().allMatch(item -> item.getExpectedEnergy() == null), chartData, categories, expectedEnergy, "Expected*", PresetColor.DARK_GRAY, PresetColor.BLACK);

							chart.plot(chartData);
							
							chartData = createChartData(chart, ChartTypes.LINE, bottomAxis, leftAxis);
							addSeries(data.stream().allMatch(item -> item.getModeledEnergy() == null), chartData, categories, modeledEnergy, "Modeled**", PresetColor.DARK_ORANGE, null);

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
					insertLogo(sheet, logoAnchor, pictureIdx);
					
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
					insertLogo(sheet, logoAnchor, pictureIdx);
					
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
	
	
	// Write header with format
	private static void writeHeaderRenewable(Sheet sheet, int rowIndex, List data) {
		try {
			// create CellStyle
			CellStyle cellStyle = createStyleForHeader(sheet);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setAlignment(HorizontalAlignment.LEFT);

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
			fontBold.setFontHeightInPoints((short) 12); // font size
			CellStyle cellStyleFontBold = sheet.getWorkbook().createCellStyle();
			cellStyleFontBold.setFont(fontBold);
			cellStyleFontBold.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleFontBold.setAlignment(HorizontalAlignment.CENTER);

			sheet.setDefaultColumnWidth(16);
			sheet.setColumnWidth(0, 30 * 256);
			sheet.setColumnWidth(1, 30 * 256);
			sheet.setColumnWidth(2, 30 * 256);
			sheet.setColumnWidth(3, 30 * 256);
			sheet.setColumnWidth(4, 30 * 256);
			
			sheet.setDefaultRowHeight((short) 500);

			// Create font
			Font font = sheet.getWorkbook().createFont();
			font.setFontName("Times New Roman");
			font.setBold(true);
			font.setFontHeightInPoints((short) 12); // font size
			font.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleCustom = sheet.getWorkbook().createCellStyle();
			cellStyleCustom.setFont(font);
			sheet.addMergedRegion(new CellRangeAddress(0, 5, 0, 2));
			cellStyleCustom.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleCustom.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleCustom.setAlignment(HorizontalAlignment.CENTER);
			
			Row row1 = sheet.createRow(0);
			Cell cell = row1.createCell(0);
			cell.setCellStyle(cellStyleCustom);
			cell.setCellValue("RENEWABLE ENERGY CREDITS");
			
			

			// Create CellStyle image
			CellStyle cellStyleImage = sheet.getWorkbook().createCellStyle();
			sheet.addMergedRegion(new CellRangeAddress(0, 5, 3, 4));

			cellStyleImage.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleImage.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleImage.setAlignment(HorizontalAlignment.CENTER);
			
			// Create font
			Font font6 = sheet.getWorkbook().createFont();
			font6.setFontName("Times New Roman");
			font6.setBold(true);
			font6.setFontHeightInPoints((short) 12); // font size
			font6.setColor(IndexedColors.WHITE.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyle6 = sheet.getWorkbook().createCellStyle();
			cellStyle6.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle6.setFont(font6);
			cellStyle6.setFillForegroundColor(IndexedColors.BLACK.getIndex());
			cellStyle6.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			Font font71 = sheet.getWorkbook().createFont();
			font71.setFontName("Times New Roman");
			font71.setBold(true);
			font71.setFontHeightInPoints((short) 12); // font size
			font71.setColor(IndexedColors.BLACK.getIndex()); // text color
			CellStyle cellStyle71 = sheet.getWorkbook().createCellStyle();
			cellStyle71.setFont(font71);
			cellStyle71.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle71.setAlignment(HorizontalAlignment.CENTER);

			// Monthly Data
			Row row8 = sheet.createRow(7);
			Cell cell8 = row8.createCell(0);
			cell8.setCellStyle(cellStyle71);
			cell8.setCellValue("REU ID");
			
			cell8 = row8.createCell(1);
			cell8.setCellStyle(cellStyle71);
			cell8.setCellValue("Vintage");
			
			cell8 = row8.createCell(2);
			cell8.setCellStyle(cellStyle71);
			cell8.setCellValue("Begin Date");
			
			cell8 = row8.createCell(3);
			cell8.setCellStyle(cellStyle71);
			cell8.setCellValue("End Date");
			
			cell8 = row8.createCell(4);
			cell8.setCellStyle(cellStyle71);
			cell8.setCellValue("Total MWh");
			
			int r = 8;
			for (int i = 0; i < data.size(); i++) {
				Map<String, Object> item = (Map<String, Object>) data.get(i);
				Row row9 = sheet.createRow(r + i);
				Cell cell9 = row9.createCell(0);
				cell9.setCellStyle(cellStyleItem);
				cell9.setCellValue( (String)item.get("rec_id") );
				
				cell9 = row9.createCell(1);
				cell9.setCellStyle(cellStyleItem);
				cell9.setCellValue( (String)item.get("gu_id") );
				
				cell9 = row9.createCell(2);
				cell9.setCellStyle(cellStyleItem);
				cell9.setCellValue( (String)item.get("vintage_date") );
				
				cell9 = row9.createCell(3);
				cell9.setCellStyle(cellStyleItem);
				cell9.setCellValue( (String)item.get("start_date") );
				
				cell9 = row9.createCell(4);
				cell9.setCellStyle(cellStyleItem);
				cell9.setCellValue( (String)item.get("end_date") );
				
				cell9 = row9.createCell(5);
				cell9.setCellStyle(cellStyleItem);
				cell9.setCellValue( (double)item.get("energy_this_month")  );
			}
		} catch (Exception e) {
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
			int pictureIdx = readLogoImageFile(document);
			
			for (int i = 0; i < dataObjList.size(); i++) {
				ViewReportEntity dataObj = dataObjList.get(i);
				
				if (dataObj != null) {
					SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					Date endDate = datetimeFormat.parse(obj.getEnd_date());
					Date startDate = datetimeFormat.parse(obj.getStart_date());
					dataObj.setStart_date(dateFormat.format(startDate));
					dataObj.setEnd_date(dateFormat.format(endDate));
					List<MonthlyDateEntity> dataExports = dataObj.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() : 0;
					
					XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName((i + 1) + "_" + dataObj.getSite_name()));
					sheet.setZoom(85);
					
					// insert logo image
					ClientAnchor logoAnchor = new XSSFClientAnchor(0, 0, 20 * Units.EMU_PER_PIXEL, 20 * Units.EMU_PER_PIXEL, 12, 1, 13, 4);
					insertLogo(sheet, logoAnchor, pictureIdx);
					
					// report information and table
					writeHeaderMonthlyReport(sheet, dataObj);
					
					if (numOfPoints > 0) {
						// 1st chart
						ClientAnchor chartAnchor = new XSSFClientAnchor(5, 5, 5, 5, 5, 8, 14, 23);
						XDDFChart chart = insertChart(sheet, chartAnchor, "Monthly Performance");
						
						// data sources
						XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(9, 9 + numOfPoints - 1, 0, 0));
						XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(9, 9 + numOfPoints - 1, 1, 1));
						XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(9, 9 + numOfPoints - 1, 2, 2));
						XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(9, 9 + numOfPoints - 1, 3, 3));
						
						// category axis
						XDDFCategoryAxis bottomAxis = createCategoryAxis(chart);
						
						// left value axis
						XDDFValueAxis leftAxis = createLeftValueAxis(chart, "GENERATION (KWH)");
						
						XDDFChartData data = createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						addSeries(dataExports.stream().allMatch(item -> item.getActual() == null), data, categoriesData, valuesData1, "Actual Generation (kWh)", PresetColor.STEEL_BLUE, null);
						addSeries(dataExports.stream().allMatch(item -> item.getEstimated() == null), data, categoriesData, valuesData2, "Estimated Generation (kWh)", PresetColor.LIGHT_SKY_BLUE, null);
						
						chart.plot(data);
						
						// right value axis
						XDDFValueAxis rightAxis = createRightValueAxis(chart, bottomAxis, "PERFORMANCE INDEX (%)");
						
						data = createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						addSeries(dataExports.stream().allMatch(item -> item.getActual() == null || item.getEstimated() == null), data, categoriesData, valuesData3, "Estimated Generation Index (%)", PresetColor.GRAY, null);
						
						chart.plot(data);
						
						// 2nd chart
						chartAnchor = new XSSFClientAnchor(5, 5, 5, 5, 5, 25, 14, 41);
						chart = insertChart(sheet, chartAnchor, monthYearFormat.format(startDate));
						
						// data sources
						XDDFDataSource<String> categoriesData2 = XDDFDataSourcesFactory.fromArray(new String[] {""});
						XDDFNumericalDataSource<Double> valuesData12 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(41, 41, 1, 1));
						XDDFNumericalDataSource<Double> valuesData22 = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(41, 41, 2, 2));
						
						// category axis
						bottomAxis = createCategoryAxis(chart);
						
						// left value axis
						leftAxis = createLeftValueAxis(chart, "GENERATION (KWH)");
						
						data = createChartData(chart, ChartTypes.BAR, bottomAxis, leftAxis);
						((XDDFBarChartData) data).setOverlap((byte) -24);
						((XDDFBarChartData) data).setGapWidth(400);
						
						addSeries(dataExports.stream().allMatch(item -> item.getActual() == null), data, categoriesData2, valuesData12, "Actual Generation (kWh)", PresetColor.STEEL_BLUE, null);
						addSeries(dataExports.stream().allMatch(item -> item.getActual() == null || item.getEstimated() == null), data, categoriesData2, valuesData22, "Estimated Generation (kWh)", PresetColor.LIGHT_SKY_BLUE, null);
						
						chart.plot(data);
					}
				}
			}
				
			if (dataObjList.stream().anyMatch(item -> item != null)) {
				sentExcelReportByMail(document, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name());
				return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
			} else {
				return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
			}
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
				Image logoImage = readLogoImageFile();
				
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
						DecimalFormat dfa = new DecimalFormat(noDecimalDataFormat);
						DecimalFormat df = new DecimalFormat(oneDecimalPlaceDataFormat);
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
						JFreeChart chart = createJFreeChart("Monthly Performance");
						XYPlot plot = chart.getXYPlot();
						
						// data source
						TimeSeriesCollection barDataset = createJFreeChartBarDataset(0, plot);
						TimeSeries actualSeries = new TimeSeries("Actual Generation (kWh)");
						barDataset.addSeries(actualSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(0, BLUE_COLOR);
						TimeSeries estimateSeries = new TimeSeries("Estimate Generation (kWh)");
						barDataset.addSeries(estimateSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(1, LIGHT_BLUE_COLOR);
						
						TimeSeriesCollection lineDataset = createJFreeChartLineDataset(1, plot);
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
						createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.DAY, 1, dateFormat), startDate, endDate).setVerticalTickLabels(true);
						// left axis
						createJFreeChartNumberAxis("GENERATION (KWH)", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
						// right axis
						createJFreeChartNumberAxis("PERFORMANCE INDEX (%)", AxisLocation.BOTTOM_OR_RIGHT, 1, 1, plot);
						
						innerTable.addCell(new Image(ImageDataFactory.create(chart.createBufferedImage(900, 400), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 400));
						// gap between charts
						innerTable.addCell(new com.itextpdf.layout.element.Cell().setHeight(18 * (dataExports.size() + 1 - 30)).setBorder(Border.NO_BORDER));
						
						//====== second chart ============================================================
						JFreeChart chart2 = createJFreeChart(monthYearFormat.format(startDate));
						XYPlot plot2 = chart2.getXYPlot();
						
						// data source
						TimeSeriesCollection barDataset2 = createJFreeChartBarDataset(0, plot2);
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
						createJFreeChartDomainAxis(plot2, new DateTickUnit(DateTickUnitType.MONTH, 1, monthYearFormat), startDate, endDate).setVisible(false);
						// left axis
						createJFreeChartNumberAxis("GENERATION (KWH)", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot2);
						
						innerTable.addCell(new Image(ImageDataFactory.create(chart2.createBufferedImage(900, 350), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 350));
						document.add(table);
						if (l < dataObjList.size() - 1) document.add(new AreaBreak());
					}
				}
					
				// It must be closed before attach to mail
				document.close();

				if (dataObjList.stream().anyMatch(item -> item != null)) {
					sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				} else {
					return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				}
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
			ReportsService service = new ReportsService();
			ViewReportEntity dataObj = (ViewReportEntity) service.getCustomReport(obj);
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
					
					CellStyle reportTitleCellStyle = createStyleForReportTitle(sheet);
					CellStyle reportInfoCellStyle = createStyleForReportInfo(sheet);
					CellStyle reportInfoBoldCellStyle = createStyleForReportInfoBold(sheet);
					CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
					CellStyle tableRowCellStyle = createStyleForTableRow(sheet);
					CellStyle tableRowNoDecimalCellStyle = createStyleForTableRowNumber(sheet);

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
						
						List<Map<String, Object>> dataExports = dataObj.getDataReports();
						
						if(dataExports != null && dataExports.size() > 0) {
							for(int j = 0 ;j < dataExports.size(); j++) {
								Map<String, Object> item = (Map<String, Object>) dataExports.get(j);
								int t = 25 + j;
								
								Row row26 = sheet.getRow(t) != null ? sheet.getRow(t) : sheet.createRow(t);
								if (i == 0) {
									Cell cel26D = row26.createCell(0);
									cel26D.setCellStyle(tableRowCellStyle);
									cel26D.setCellValue(item.get("categories_time").toString());
									Cell cel26E = row26.createCell(1);
									cel26E.setCellStyle(tableRowCellStyle);
									Cell cel26F = row26.createCell(2);
									cel26F.setCellStyle(tableRowCellStyle);
									sheet.addMergedRegion(new CellRangeAddress(t, t, 0, 2));
								}
								
								Cell cel26G = row26.createCell(3 + 3*i);
								cel26G.setCellStyle(tableRowNoDecimalCellStyle);
								if(item.get("actual") != null) cel26G.setCellValue(Double.parseDouble(item.get("actual").toString()));
								Cell cel26H = row26.createCell(4 + 3*i);
								cel26H.setCellStyle(tableRowNoDecimalCellStyle);
								Cell cel26I = row26.createCell(5 + 3*i);
								cel26I.setCellStyle(tableRowNoDecimalCellStyle);
								sheet.addMergedRegion(new CellRangeAddress(t, t, 3 + 3*i, 5 + 3*i));
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
					int pictureIdx = readLogoImageFile(document);
					ClientAnchor logoAnchor = new XSSFClientAnchor(0, 10 * Units.EMU_PER_PIXEL, 0, -10 * Units.EMU_PER_PIXEL, 11, 0, 12, 4);
					insertLogo(sheet, logoAnchor, pictureIdx);
					
					// chart
					ClientAnchor chartAnchor = new XSSFClientAnchor(5 * Units.EMU_PER_PIXEL, 0, 0, 0, 0, 6, 12, 22);
					XDDFChart chart = insertChart(sheet, chartAnchor, "Actual Generation (kWh)");
					
					// category axis
					XDDFCategoryAxis bottomAxis = createCategoryAxis(chart);
					
					// left value axis
					XDDFValueAxis leftAxis = createLeftValueAxis(chart, "kWh");
					
					// report information and table
					writeHeaderCustomReport(sheet, obj, dataObjList);
					
					for (int i = 0; i < dataObjList.size(); i++) {
						ViewReportEntity dataObj = dataObjList.get(i);
						
						if (dataObj != null) {
							List<Map<String, Object>> dataExports = dataObj.getDataReports();
							int numOfPoints = dataExports != null ? dataExports.size() : 0;
							
							if (numOfPoints > 0) {
								// data sources
								XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 0, 0));
								XDDFNumericalDataSource<Double> valuesData = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(25, 25 + numOfPoints - 1, 3 + 3*i, 3 + 3*i));
								
								XDDFChartData data = createChartData(chart, ChartTypes.LINE, bottomAxis, leftAxis);
								addSeries(dataExports.stream().allMatch(item -> item.get("actual") == null), data, categoriesData, valuesData, dataObj.getSite_name());
								
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
				Image logoImage = readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
					ViewReportEntity dataObj = dataObjList.get(l);

					if (dataObj != null) {
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
						dataObj.setStart_date(format.format(startDate));
						dataObj.setEnd_date(format.format(endDate));
						
						List<Map<String, Object>> dataExports = dataObj.getDataReports() != null ? dataObj.getDataReports() : new ArrayList<>();
						
						// total column: 12
						Table table = new Table(12).useAllAvailableWidth();
						table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
						table.setFontSize(8);
						table.setTextAlignment(TextAlignment.CENTER);
					
						//====== table ============================================================
						// header and logo
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(6, 7).add(new Paragraph("PRODUCTION REPORT")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell(6, 2).add(logoImage).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Site Name").setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getSite_name()).setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Report Date").setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getReport_date()).setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Covered Period").setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getStart_date() + " - " + dataObj.getEnd_date()).setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("System Size (kW DC)").setBold().setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(String.valueOf(dataObj.getDc_capacity())).setTextAlignment(TextAlignment.LEFT)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
						
						// chart
						com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(16, 12);
						table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
						// empty row
						table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
						
						// header of data table
						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Timestamp").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Actual Generation (kWh)").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setBorder(Border.NO_BORDER));
						
						// data table
						DecimalFormat dfs = new DecimalFormat(noDecimalDataFormat);
						for (int i = 0; i < dataExports.size(); i++) {
							Map<String, Object> item = (Map<String, Object>) dataExports.get(i);
							String itemCategoryTime = item.get("categories_time").toString();
							Double itemActual = item.get("actual") != null ? Double.parseDouble(item.get("actual").toString()) : null;
							
							table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setBorder(Border.NO_BORDER));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(itemCategoryTime)));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(itemActual != null ? dfs.format(itemActual).toString() : "")));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setBorder(Border.NO_BORDER));
						}
						
						//====== chart ============================================================
						JFreeChart chart = createJFreeChart(null);
						XYPlot plot = chart.getXYPlot();
						
						// data source
						TimeSeriesCollection lineDataset = createJFreeChartLineDataset(0, plot);
						TimeSeries series = new TimeSeries("Actual Generation (kWh)");
						lineDataset.addSeries(series);
						plot.getRendererForDataset(lineDataset).setSeriesPaint(0, BLUE_COLOR);
						
						for (int i = 0; i < dataExports.size(); i++) {
							Map<String, Object> item = (Map<String, Object>) dataExports.get(i);
							String itemCategoryTime = item.get("categories_time").toString();
							Double itemActual = item.get("actual") != null ? Double.parseDouble(item.get("actual").toString()) : null;
							
							RegularTimePeriod period = new Month(format.parse(itemCategoryTime));
							if (obj.getData_intervals() == Constants.DAILY_INTERVAL) period = new Day(format.parse(itemCategoryTime));
							else if (obj.getData_intervals() == Constants.MONTHLY_INTERVAL) period = new Month(format.parse(itemCategoryTime));
							else if (obj.getData_intervals() == Constants.ANNUALLY_INTERVAL) period = new Year(format.parse(itemCategoryTime));
							
							series.add(period, itemActual);
						}
						
						// category axis
						createJFreeChartDomainAxis(plot, new DateTickUnit(dateTickUnitType, (int) Math.ceil(dataExports.size() > 0 ? (double) dataExports.size() / 15 : 1), format), startDate, endDate);
						// left axis
						createJFreeChartNumberAxis("kWh", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
						
						chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1800, 700), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 700));
						document.add(table);
						if (l < dataObjList.size() - 1) document.add(new AreaBreak());
					}
				}
				
				// It must be closed before attach to mail
				document.close();
					
				if (dataObjList.stream().anyMatch(item -> item != null)) {
					sentPdfReportByMail(dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), file);
					return this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1);
				} else {
					return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
				}
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}

	private static XDDFLegendEntry getOrAddLegendEntry(XDDFChartLegend legend, long index) {
		XDDFLegendEntry legendEntry = null;
		for (XDDFLegendEntry storedLegendEntry : legend.getEntries()) {
			if (storedLegendEntry.getIndex() == index) {
				legendEntry = storedLegendEntry;
				break;
			}
		}
		if (legendEntry == null) {
			legendEntry = legend.addEntry();
			legendEntry.setIndex(index);
		}
		return legendEntry;
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

	/**
	 * @description Get customer view chart data
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/pdf-render")
	public Object pdfRender(@RequestBody ReportsEntity obj) {
//		try {
//			XSSFWorkbook wb = new XSSFWorkbook();
//			XSSFSheet sheet = wb.createSheet("linechart");
//			// create data
//			Row row;
//			Cell cell;
//			for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
//				row = sheet.createRow((short) rowIndex);
//				if (rowIndex == 0) {
//					cell = row.createCell(0);
//					cell.setCellValue("CatA");
//					for (int colIndex = 1; colIndex < 32; colIndex++) {
//						cell = row.createCell((short) colIndex);
//						cell.setCellValue(colIndex + (colIndex - 1) * 11);
//					}
//				} else if (rowIndex == 1) {
//					cell = row.createCell(0);
//					cell.setCellValue("ValA");
//					for (int colIndex = 1; colIndex < 32; colIndex++) {
//						cell = row.createCell((short) colIndex);
//						cell.setCellValue(new java.util.Random().nextDouble() * 8 + 1);
//					}
//				} else if (rowIndex == 2) {
//					cell = row.createCell(0);
//					cell.setCellValue("CatB");
//					for (int colIndex = 1; colIndex < 14; colIndex++) {
//						cell = row.createCell((short) colIndex);
//						cell.setCellValue(colIndex);
//					}
//				} else if (rowIndex == 3) {
//					cell = row.createCell(0);
//					cell.setCellValue("ValB");
//					for (int colIndex = 1; colIndex < 14; colIndex++) {
//						cell = row.createCell((short) colIndex);
//						cell.setCellValue(new java.util.Random().nextDouble() * 8 + 1);
//					}
//				}
//			}
//
//			// creata anchor
//			XSSFDrawing drawing = sheet.createDrawingPatriarch();
//			XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 15, 25);
//
//			// create chart
//			XSSFChart chart = drawing.createChart(anchor);
//
//			// create data sources
//			XDDFDataSource<Double> cat1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
//					new CellRangeAddress(0, 0, 1, 31));
//			XDDFNumericalDataSource<Double> val1 = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
//					new CellRangeAddress(1, 1, 1, 31));
//			XDDFDataSource<Double> cat2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
//					new CellRangeAddress(2, 2, 1, 13));
//			XDDFNumericalDataSource<Double> val2 = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
//					new CellRangeAddress(3, 3, 1, 13));
//
//			// first line chart
//
//			// create axis
//			XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
//			XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
//			rightAxis.setCrosses(AxisCrosses.MAX);
//
//			// create data and series
//			XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
//			XDDFLineChartData.Series series = (XDDFLineChartData.Series) data.addSeries(cat1, val1);
//			series.setTitle("CatA", new CellReference(sheet.getSheetName(), 0, 0, true, true));
//			series.setSmooth(false);
//			series.setMarkerStyle(MarkerStyle.DASH);
//
//			chart.plot(data);
//
//			solidLineSeries(data, 0, PresetColor.BLUE);
//
//			// second line chart
//
//			// create axis
//			XDDFCategoryAxis topAxis = chart.createCategoryAxis(AxisPosition.TOP);
//			topAxis.setCrosses(AxisCrosses.MAX);
//			rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
//			rightAxis.setVisible(false); // right axis must be there but can be invisible
//			rightAxis.setCrosses(AxisCrosses.MAX);
//
//			// set correct cross axis
//			topAxis.crossAxis(rightAxis);
//			rightAxis.crossAxis(topAxis);
//
//			// create data and series
//			data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, topAxis, rightAxis);
//			series = (XDDFLineChartData.Series) data.addSeries(cat2, val2);
//			series.setTitle("CatB", new CellReference(sheet.getSheetName(), 2, 0, true, true));
//			series.setSmooth(false);
//			series.setMarkerStyle(MarkerStyle.DASH);
//
//			chart.plot(data);
//
//			// correct the id and order, must not be 0 again because there is a series
//			// already in the other chart
//			chart.getCTChart().getPlotArea().getLineChartArray(1).getSerArray(0).getIdx().setVal(1);
//			chart.getCTChart().getPlotArea().getLineChartArray(1).getSerArray(0).getOrder().setVal(1);
//
//			solidLineSeries(data, 0, PresetColor.RED);
//
//			// create legend
//			XDDFChartLegend legend = chart.getOrAddLegend();
//			legend.setPosition(LegendPosition.BOTTOM);
//			legend.setOverlay(false);
//
//			// Write the output to a file
//			try (FileOutputStream fileOut = new FileOutputStream("/Volumes/Data/ooxml-line-chart.xlsx")) {
//				wb.write(fileOut);
//			}
//
//			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
//		} catch (Exception e) {
//			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
//		}

		// ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----
		// -----

//		ReportsService service = new ReportsService();
		try {
//			XSSFWorkbook document = new XSSFWorkbook();
//			XSSFSheet chartSheet = document.createSheet("Monthly Performance");
//			XSSFSheet dataSheet = document.createSheet("data");
//
//			// FileInputStream obtains input bytes from the image file
//			InputStream inputStreamImage = new FileInputStream("/Volumes/Data/Sources/nextwavemonitoring/api/uploads/reports/logo-report.png");
//			// Get the contents of an InputStream as a byte[].
//			byte[] bytes = IOUtils.toByteArray(inputStreamImage);
//			// Adds a picture to the workbook
//			int pictureIdx = document.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
//			// close the input stream
//			inputStreamImage.close();
//
//			// Returns an object that handles instantiating concrete classes
//			CreationHelper helper = document.getCreationHelper();
//
//			// Creates the top-level drawing patriarch.
//			Drawing drawing = chartSheet.createDrawingPatriarch();
//
//			// Create an anchor that is attached to the worksheet
//			ClientAnchor anchor = helper.createClientAnchor();
//			// set top-left corner for the image
//			anchor.setCol1(11);
//			anchor.setRow1();
//
//			// Creates a picture
//			Picture pict = drawing.createPicture(anchor, pictureIdx);
//			// Reset the image to the original size
//			pict.resize();
//
//			writeHeader(chartSheet, 0);
//
//			// Write the output to a file
//			try (FileOutputStream fileOut = new FileOutputStream("/Volumes/Data/CreateExcelXDDFChart.xlsx")) {
//				document.write(fileOut);
//			}

			try (XSSFWorkbook document = new XSSFWorkbook()) {
				XSSFSheet chartSheet = document.createSheet("Monthly Performance");
				XSSFSheet dataSheet = document.createSheet("data");

				// FileInputStream obtains input bytes from the image file
				InputStream inputStreamImage = new FileInputStream(
						"/Volumes/Data/Sources/nextwavemonitoring/api/uploads/reports/logo-report.png");
				// Get the contents of an InputStream as a byte[].
				byte[] bytes = IOUtils.toByteArray(inputStreamImage);
				// Adds a picture to the workbook
				int pictureIdx = document.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
				// close the input stream
				inputStreamImage.close();

				// Returns an object that handles instantiating concrete classes
				CreationHelper helper = document.getCreationHelper();

				// Creates the top-level drawing patriarch.
				Drawing drawing = chartSheet.createDrawingPatriarch();

				// Create an anchor that is attached to the worksheet
				ClientAnchor anchor = helper.createClientAnchor();
				anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
				// set top-left corner for the image
				anchor.setCol1(11);
				anchor.setRow1(2);

				// Creates a picture
				Picture pict = drawing.createPicture(anchor, pictureIdx);
				// Reset the image to the original size
				pict.resize();

				writeHeader(chartSheet, 0);

				// create the data
				String[] categories = new String[] { "Jan. 2021", "Feb. 2021", "Mar. 2021", "Apr. 2021", "May. 2021",
						"Jun. 2021", "Jul. 2021", "Aug. 2021", "Sep. 2021", "Oct. 2021", "Now. 2021", "Dec. 2021", };
				Double[] values1 = new Double[] { 178430d, 123830d, 151260d, 121930d, 259760d, 256960d, 229980d,
						302020d, 322530d, 341930d, 356010d, 263050d, 208360d };
				Double[] values2 = new Double[] { 247483d, 196543d, 167831d, 184124d, 193292d, 294580d, 316716d,
						359408d, 364149d, 391644d, 373282d, 303803d, 246245d };
				Double[] values3 = new Double[] { 90d, 80d, 110d, 120d, 80d, 70d, 80d, 90d, 96d, 65d, 80d, 105d };
				int r = 0;
				for (String cat : categories) {
					dataSheet.createRow(r).createCell(0).setCellValue(cat);
					dataSheet.getRow(r).createCell(1).setCellValue(values1[r]);
					dataSheet.getRow(r).createCell(2).setCellValue(values2[r]);
					dataSheet.getRow(r).createCell(3).setCellValue(values3[r]);
					r++;
				}

				// create the chart
				XSSFDrawing drawing1 = chartSheet.createDrawingPatriarch();
				XSSFClientAnchor anchor1 = drawing1.createAnchor(0, 0, 0, 0, 0, 22, 13, 50);
				XDDFChart chart = drawing1.createChart(anchor1);
				chart.setTitleText("Monthly Performance");
				chart.setTitleOverlay(false);

				// create data sources
				int numOfPoints = categories.length;
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

				// data source for the pad series
				XDDFNumericalDataSource<Double> pad = XDDFDataSourcesFactory.fromArray(dummyValuesForPad);

				// first bar chart
				XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
				XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
				leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
				leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
				leftAxis.setTitle("GENERATION (KWH)");

				XDDFChartLegend legend = chart.getOrAddLegend();
				legend.setPosition(LegendPosition.BOTTOM);

				XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);

//				XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
				XDDFBarChartData bar = (XDDFBarChartData) data;
				bar.setBarDirection(BarDirection.COL);

				XDDFChartData.Series series = (XDDFChartData.Series) data.addSeries(categoriesData, valuesData1);
				series.setTitle("Actual Generation (kWh)",
						new CellReference(chartSheet.getSheetName(), 8, 0, true, true));

				CTPlotArea plotArea = chart.getCTChart().getPlotArea();
//				plotArea.getCatAxArray()[0].addNewMajorGridlines();
				plotArea.getValAxArray()[0].addNewMajorGridlines();
//				plotArea.getLineChartArray()[0].getSmooth();
				chart.plot(data);
//				solidFillSeries(data, 0, PresetColor.CHARTREUSE);
//				series.setTitle("a", setTitleInDataSheet(chart, "A", 1));

//				XDDFSolidFillProperties fill = new XDDFSolidFillProperties( XDDFColor.from( PresetColor.BLUE ) );
//			    XDDFLineProperties line = new XDDFLineProperties();
//			    line.setFillProperties( fill );
//			    for ( XDDFChartData.Series series1 : data.getSeries() ) {
//			        XDDFShapeProperties properties = series1.getShapeProperties();
//			        if ( properties == null ) {
//			            properties = new XDDFShapeProperties();
//			        }
//			        properties.setLineProperties( line );
//			        series1.setShapeProperties( properties );
//			    }

//				series.setSmooth(false);
//			    series.setMarkerStyle(MarkerStyle.NONE);

				// additional pad series - takes space at right side for primary axis
//				series = data.addSeries(categoriesData, pad);
//				series.setTitle("pad", null);
//				chart.plot(data);
//				solidLineSeries(data, 1, PresetColor.BLUE);

				// correct the id and order, must not be 0 again because there is a series
				// already in the other chart
//			   chart.getCTChart().getPlotArea().getLineChartArray(1).getSerArray(0).getIdx().setVal(1);
//			   chart.getCTChart().getPlotArea().getLineChartArray(1).getSerArray(0).getOrder().setVal(1);
//
//			   solidLineSeries(data, 1, PresetColor.GREEN);

				// create legend
//			   XDDFChartLegend legend = chart.getOrAddLegend();
//			   legend.setPosition(LegendPosition.BOTTOM);
//			   legend.setOverlay(true);

				// second bar chart
				// bottom axis must be there but must not be visible
				bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
				bottomAxis.setVisible(false);

				XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
				rightAxis.setCrosses(AxisCrosses.MAX);
				rightAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
				rightAxis.setTitle("PERFORMANCE INDEX (%)");

				// set correct cross axis
				bottomAxis.crossAxis(rightAxis);
				rightAxis.crossAxis(bottomAxis);

				data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
				bar = (XDDFBarChartData) data;
				bar.setBarDirection(BarDirection.COL);
//				bar.setBarGrouping(BarGrouping.STACKED);
//				bar.setGapWidth(1);
//				bar.setVaryColors(true);

				series = data.addSeries(categoriesData, valuesData2);
				series.setTitle("Baseline Generation (kWh)",
						new CellReference(chartSheet.getSheetName(), 9, 0, true, true));
				chart.plot(data);

				// three line chart
				data = chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
				series = data.addSeries(categoriesData, valuesData3);
				series.setTitle("Baseline Generation Index (%)",
						new CellReference(chartSheet.getSheetName(), 8, 0, true, true));
				chart.plot(data);

				// additional pad series - takes space at left side for secondary axis
//				series = data.addSeries(categoriesData, pad);
//				series.setTitle("a", null);
//				chart.plot(data);

//				XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);

				// Write the output to a file
				try (FileOutputStream fileOut = new FileOutputStream("/Volumes/Data/CreateExcelXDDFChart.xlsx")) {
					document.write(fileOut);
				}
			}

//			final List<Book> books = getBooks();
//			final String excelFilePath = "/Volumes/Data/books.xlsx";
//			writeExcel(books, excelFilePath);

//			boolean result = service.deleteReports(obj);

//			HttpPost post = new HttpPost("http://export.highcharts.com");
//			HttpClient client = HttpClientBuilder.create().build();
//			
////			String str="{'xAxis': {'categories': ['Jan', 'Feb', 'Mar']},'series': [{'data': [29.9, 71.5, 106.4]}]};";
//			String str ="{\n"
//					+ "    \"xAxis\": {\n"
//					+ "        \"categories\": [\n"
//					+ "            \"Jan\",\n"
//					+ "            \"Feb\",\n"
//					+ "            \"Mar\",\n"
//					+ "            \"Apr\",\n"
//					+ "            \"May\",\n"
//					+ "            \"Jun\",\n"
//					+ "            \"Jul\",\n"
//					+ "            \"Aug\",\n"
//					+ "            \"Sep\",\n"
//					+ "            \"Oct\",\n"
//					+ "            \"Nov\",\n"
//					+ "            \"Dec\"\n"
//					+ "        ]\n"
//					+ "    },\n"
//					+ "    \"series\": [\n"
//					+ "        {\n"
//					+ "            \"data\": [1,3,2,4],\n"
//					+ "            \"type\": \"line\"\n"
//					+ "        },\n"
//					+ "        {\n"
//					+ "            \"data\": [5,3,4,2],\n"
//					+ "            \"type\":\"line\"\n"
//					+ "        }\n"
//					+ "    ]\n"
//					+ "}\n"
//					+ "";
//            String dataString = "{type:image/png, options:" + str+"}";
//             post.setEntity(new StringEntity(dataString, ContentType.create("application/json")));
//	
//	        HttpResponse response = client.execute(post);
//	
//	        InputStream is = response.getEntity().getContent();
//	
//	
//	        String filePath = "/Volumes/Data/test.png";
//	        FileOutputStream fos = new FileOutputStream(new File(filePath));
//	
//	        int inByte;
//	        while((inByte = is.read()) != -1) fos.write(inByte);
//	        is.close();
//	        fos.close();
//	

//			if (result) {
//				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
//			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	private int readLogoImageFile(XSSFWorkbook workbook) throws IOException {
		// FileInputStream obtains input bytes from the image file
		InputStream inputStreamImage = new FileInputStream(uploadRootPath() + "/reports/logo-report.jpg");
		// Get the contents of an InputStream as a byte[].
		byte[] bytes = IOUtils.toByteArray(inputStreamImage);
		// Adds a picture to the workbook
		int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
		// close the input stream
		inputStreamImage.close();
		
		return pictureIdx;
	}
	
	private Image readLogoImageFile() throws Exception {
		Image logoImage = new Image(ImageDataFactory.create(uploadRootPath() + "/reports/logo-report.jpg"));
		logoImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT).scaleToFit(100, 100);
		
		return logoImage;
	}
	
	private void insertLogo(XSSFSheet sheet, ClientAnchor anchor, int pictureIndex) {
		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
		drawing.createPicture(anchor, pictureIndex);
	}
	
	private XDDFChart insertChart(XSSFSheet sheet, ClientAnchor anchor, String title) {
		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		XDDFChart chart = drawing.createChart(anchor);
		chart.setTitleText(title);
		chart.setTitleOverlay(false);
		XDDFChartLegend legend = chart.getOrAddLegend();
		legend.setPosition(LegendPosition.BOTTOM);
		legend.setOverlay(false);
		
		CTDispBlanksAs disp = CTDispBlanksAs.Factory.newInstance();
		disp.setVal(STDispBlanksAs.GAP);
		chart.getCTChart().setDispBlanksAs(disp);
		
		chart.getCTChart().getTitle().getTx().getRich().getPArray(0).getRArray(0).getRPr().setSz(1200);
		
		return chart;
	}
	
	private static XDDFCategoryAxis createCategoryAxis(XDDFChart chart) {
		XDDFCategoryAxis axis = chart.createCategoryAxis(AxisPosition.BOTTOM);
		axis.setMajorTickMark(AxisTickMark.OUT);
		if (axis.hasNumberFormat()) axis.setNumberFormat("@");
		
		return axis;
	}
	
	private static XDDFValueAxis createLeftValueAxis(XDDFChart chart, String title) {
		XDDFValueAxis axis = chart.createValueAxis(AxisPosition.LEFT);
		axis.setTitle(title);
		axis.setCrosses(AxisCrosses.AUTO_ZERO);
		axis.setCrossBetween(AxisCrossBetween.BETWEEN);
		axis.setMajorTickMark(AxisTickMark.NONE);
		axis.setMinimum(0);
		
		XDDFLineProperties properties = new XDDFLineProperties();
		properties.setFillProperties(new XDDFSolidFillProperties(XDDFColor.from(PresetColor.LIGHT_GRAY)));
		axis.getOrAddMajorGridProperties().setLineProperties(properties);
		
		properties.setFillProperties(new XDDFNoFillProperties());
		axis.getOrAddShapeProperties().setLineProperties(properties);
		
		if (axis.hasNumberFormat()) axis.setNumberFormat("#,##0.00");
		
		return axis;
	}
	
	private static XDDFValueAxis createRightValueAxis(XDDFChart chart, XDDFCategoryAxis bottomAxis, String title) {
		XDDFValueAxis axis = chart.createValueAxis(AxisPosition.RIGHT);
		axis.setTitle(title);
		axis.setCrosses(AxisCrosses.MAX);
		axis.setCrossBetween(AxisCrossBetween.BETWEEN);
		axis.setMajorTickMark(AxisTickMark.NONE);
		axis.setMinimum(0);
		
		XDDFLineProperties properties = new XDDFLineProperties(new XDDFNoFillProperties());
		axis.getOrAddShapeProperties().setLineProperties(properties);
		
		// set correct cross axis
		bottomAxis.crossAxis(axis);
		axis.crossAxis(bottomAxis);
		
		if (axis.hasNumberFormat()) axis.setNumberFormat("#,##0.00");
		
		return axis;
	}
	
	private static XDDFChartData createChartData(XDDFChart chart, ChartTypes type, XDDFCategoryAxis categoryAxis, XDDFValueAxis valueAxis) {
		XDDFChartData chartData = chart.createData(type, categoryAxis, valueAxis);
		chartData.setVaryColors(false);
		if (type == ChartTypes.BAR) ((XDDFBarChartData) chartData).setBarDirection(BarDirection.COL);
		
		return chartData;
	}
	
	private static void addSeries(boolean isDataEmpty, XDDFChartData chartData, XDDFDataSource<String> categories, XDDFNumericalDataSource<Double> value, String name, PresetColor color, PresetColor borderColor) {
		if (categories == null || value == null) return;
		
		Series series = chartData.addSeries(categories, value);
		series.setTitle(name, null);
		
		if (isDataEmpty) {
			// If data is empty, chart plot will thrown error. So we need to add dummy data and set color to transparent.
			Double[] dummyData = new Double[value.getPointCount()];
			Arrays.fill(dummyData, 0d);
			series.replaceData(categories, XDDFDataSourcesFactory.fromArray(dummyData));
			solidFillSeries(series, null, null);
		} else {
			solidFillSeries(series, color, borderColor);
		}
	}
	
	private static void addSeries(boolean isDataEmpty, XDDFChartData chartData, XDDFDataSource<String> categories, XDDFNumericalDataSource<Double> value, String name) {
		if (categories == null || value == null) return;
		
		Series series = chartData.addSeries(categories, value);
		series.setTitle(name, null);
		
		if (isDataEmpty) {
			// If data is empty, chart plot will thrown error. So we need to add dummy data and set color to transparent.
			Double[] dummyData = new Double[value.getPointCount()];
			Arrays.fill(dummyData, 0d);
			series.replaceData(categories, XDDFDataSourcesFactory.fromArray(dummyData));
		}
		
		if (series.getClass() == XDDFLineChartData.Series.class) {
			((XDDFLineChartData.Series) series).setSmooth(false);
			((XDDFLineChartData.Series) series).setMarkerStyle(MarkerStyle.NONE);
		}
	}

	private static void solidFillSeries(Series series, PresetColor color, PresetColor borderColor) {
		XDDFFillProperties fill = color != null ? new XDDFSolidFillProperties(XDDFColor.from(color)) : new XDDFNoFillProperties();
		XDDFShapeProperties properties = new XDDFShapeProperties();
		
		if (series.getClass() == XDDFLineChartData.Series.class) {
			XDDFLineProperties lineProperties = new XDDFLineProperties(fill);
			properties.setLineProperties(lineProperties);
			
			((XDDFLineChartData.Series) series).setSmooth(false);
			((XDDFLineChartData.Series) series).setMarkerStyle(MarkerStyle.NONE);
		} else if (series.getClass() == XDDFBarChartData.Series.class) {
			properties.setFillProperties(fill);
			
			if (borderColor != null) {
				XDDFFillProperties borderFill = new XDDFSolidFillProperties(XDDFColor.from(borderColor));
				XDDFLineProperties borderProperties = new XDDFLineProperties(borderFill);
				properties.setLineProperties(borderProperties);
			}
		}
		
		series.setShapeProperties(properties);
	}

	static CellReference setTitleInDataSheet(XDDFChart chart, String title, int column) throws Exception {
		XSSFWorkbook workbook = chart.getWorkbook();
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row = sheet.getRow(0);
		if (row == null)
			row = sheet.createRow(0);
		XSSFCell cell = row.getCell(column);
		if (cell == null)
			cell = row.createCell(column);
		cell.setCellValue(title);
		return new CellReference(sheet.getSheetName(), 0, column, true, true);
	}

	private JFreeChart createJFreeChart(String title) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, null, null, null);
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.gray);
		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlineStroke(new BasicStroke(0.2f));
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		plot.setOutlinePaint(null);
		plot.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		
		return chart;
	}
	
	private TimeSeriesCollection createJFreeChartBarDataset(int datasetIndex, XYPlot plot) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.setXPosition(TimePeriodAnchor.MIDDLE);
		plot.setDataset(datasetIndex, dataset);
		
		ClusteredXYBarRenderer barRenderer = new ClusteredXYBarRenderer();
		barRenderer.setBarPainter(new StandardXYBarPainter());
		barRenderer.setShadowVisible(false);
		barRenderer.setDrawBarOutline(false);
		barRenderer.setMargin(0.3);
		plot.setRenderer(datasetIndex, barRenderer);
		
		return dataset;
	}
	
	private TimeSeriesCollection createJFreeChartLineDataset(int datasetIndex, XYPlot plot) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.setXPosition(TimePeriodAnchor.MIDDLE);
		plot.setDataset(datasetIndex, dataset);
		
		XYLineAndShapeRenderer lineRenderer = new XYLineAndShapeRenderer(true, false);
		lineRenderer.setSeriesStroke(0, new BasicStroke(2));
		plot.setRenderer(datasetIndex, lineRenderer);
		
		return dataset;
	}
	
	private static DateAxis createJFreeChartDomainAxis(XYPlot plot, DateTickUnit tickUnit, Date minDate, Date maxDate) {
		DateAxis domainAxis = new DateAxis();
		domainAxis.setTickMarkOutsideLength(5);
		domainAxis.setTickMarkStroke(new BasicStroke(0.5f));
		domainAxis.setTickUnit(tickUnit);
		domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		domainAxis.setMinimumDate(minDate);
		domainAxis.setMaximumDate(maxDate);
		plot.setDomainAxis(domainAxis);
		
		return domainAxis;
	}
	
	private static NumberAxis createJFreeChartNumberAxis(String title, AxisLocation location, int axisIndex, int datasetIndex, XYPlot plot) {
		NumberAxis numberAxis = new NumberAxis(title);
		numberAxis.setTickMarksVisible(false);
		numberAxis.setAxisLineVisible(false);
		plot.setRangeAxis(axisIndex, numberAxis);
		plot.setRangeAxisLocation(axisIndex, location);
		plot.mapDatasetToRangeAxis(datasetIndex, axisIndex);
		
		return numberAxis;
	}

	public static void writeExcel(List<Book> books, String excelFilePath) throws IOException {
		// Create Workbook
		Workbook workbook = getWorkbook(excelFilePath);

		// Create sheet
		Sheet sheet = workbook.createSheet("Books"); // Create sheet with sheet name

		int rowIndex = 0;

		// Write header
		writeHeader(sheet, rowIndex);

		// Write data
//        rowIndex++;
//        for (Book book : books) {
//            // Create row
//            Row row = sheet.createRow(rowIndex);
//            // Write data on row
//            writeBook(book, row);
//            rowIndex++;
//        }

		// Write footer
//        writeFooter(sheet, rowIndex);

		// Auto resize column witdth
//        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
//        autosizeColumn(sheet, numberOfColumn);

		// Create file excel
		createOutputFile(workbook, excelFilePath);
	}

	// Create workbook
	private static Workbook getWorkbook(String excelFilePath) throws IOException {
		Workbook workbook = null;

		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook();
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	// Write header with format
	private static void writeHeader(Sheet sheet, int rowIndex) {
		try {
			// create CellStyle
			CellStyle cellStyle = createStyleForHeader(sheet);

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
			fontBold.setFontHeightInPoints((short) 12); // font size
			CellStyle cellStyleFontBold = sheet.getWorkbook().createCellStyle();
			cellStyleFontBold.setFont(fontBold);
			cellStyleFontBold.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleFontBold.setAlignment(HorizontalAlignment.CENTER);

			// Create row
//	        Row row = sheet.createRow(rowIndex);

			sheet.setDefaultColumnWidth(16);
			Row row1 = sheet.createRow(0);
			Cell cell = row1.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("Site Name");

			cell = row1.createCell(1);
			cell.setCellStyle(cellStyle);
			cell.setCellValue("Golden Springs Development Co.");

			// Create font
			Font font = sheet.getWorkbook().createFont();
			font.setFontName("Times New Roman");
			font.setBold(true);
			font.setFontHeightInPoints((short) 22); // font size
			font.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleCustom = sheet.getWorkbook().createCellStyle();
			cellStyleCustom.setFont(font);
			sheet.addMergedRegion(new CellRangeAddress(0, 5, 2, 10));
			cellStyleCustom.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleCustom.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleCustom.setAlignment(HorizontalAlignment.CENTER);
			cell = row1.createCell(2);
			cell.setCellStyle(cellStyleCustom);
			cell.setCellValue("MONTHLY PRODUCTION REPORT");

//	        sheet.addMergedRegion(new CellRangeAddress(3, 7, 3, 11));
			// Create CellStyle image
			CellStyle cellStyleImage = sheet.getWorkbook().createCellStyle();
//	        sheet.addMergedRegion(new CellRangeAddress(12, 7, 14, 18));
			sheet.addMergedRegion(new CellRangeAddress(0, 5, 11, 12));

			cellStyleImage.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleImage.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleImage.setAlignment(HorizontalAlignment.CENTER);

			// Create font
			Font font11 = sheet.getWorkbook().createFont();
			font11.setFontName("Times New Roman");
			font11.setBold(true);
			font11.setFontHeightInPoints((short) 12); // font size
			font11.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleCustom11 = sheet.getWorkbook().createCellStyle();
			cellStyleCustom11.setFont(font11);
//				        sheet.addMergedRegion(new CellRangeAddress(14, 20, 14, 20));
			sheet.addMergedRegion(new CellRangeAddress(11, 11, 2, 12));
			cellStyleCustom11.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//				        cellStyleCustom.setVerticalAlignment(VerticalAlignment.CENTER);
//				        cellStyleCustom11.setAlignment(HorizontalAlignment.CENTER);

//			Workbook wb = new XSSFWorkbook();
//			String saveDir = "";
//			saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyLogoReport);
////			fileName = randomAlphabetic(16) + "-" + new Date().getTime();
//			String saveFileName = Lib.uploadFromBase64(objFile.get("file_upload").toString(), fileName,
//					saveDir);
//			objFile.put("file_name", saveFileName);

//			Path pathLogo = Paths.get(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey) + "/reports/logo-report.png");
//			
//			InputStream inputStreamImage = new FileInputStream(pathLogo.toString());
//			// Get the contents of an InputStream as a byte[].
//			byte[] bytes = IOUtils.toByteArray(inputStreamImage);
//			// Adds a picture to the workbook
//			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
//			// close the input stream
//			inputStreamImage.close();
//
//			// Returns an object that handles instantiating concrete classes
//			CreationHelper helper = wb.getCreationHelper();
//
//			// Creates the top-level drawing patriarch.
//			Drawing drawing = sheet.createDrawingPatriarch();
//
//			// Create an anchor that is attached to the worksheet
//			ClientAnchor anchor = helper.createClientAnchor();
//			// set top-left corner for the image
//			anchor.setCol1(1);
//			anchor.setRow1(2);
//
//			// Creates a picture
//			Picture pict = drawing.createPicture(anchor, pictureIdx);
//			// Reset the image to the original size
////			pict.resize();
//			
//			FileOutputStream fileOut = null;
//			   fileOut = new FileOutputStream("/Volumes/Data/myFile.xlsx");
//			   wb.write(fileOut);
//			   fileOut.close();

			cell = row1.createCell(12);
			cell.setCellStyle(cellStyleImage);
			cell.setCellValue("LOGO");

			Row row2 = sheet.createRow(1);
			Cell cell2 = row2.createCell(0);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("Report Date");

			cell2 = row2.createCell(1);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("11/4/2021");

			Row row3 = sheet.createRow(2);
			Cell cell3 = row3.createCell(0);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("Covered Period");

			cell3 = row3.createCell(1);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("10/01/2020 - 10/31/2021");

			Row row4 = sheet.createRow(2);
			Cell cell4 = row4.createCell(0);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("System Size (kW DC)");

			cell4 = row4.createCell(1);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("123,123,123");

			Row row5 = sheet.createRow(3);
			Cell cell5 = row5.createCell(0);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("Current System Status");

			cell5 = row5.createCell(1);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("Normal");

			// Performance Reporting
			// Create font
			Font font6 = sheet.getWorkbook().createFont();
			font6.setFontName("Times New Roman");
			font6.setBold(true);
			font6.setFontHeightInPoints((short) 12); // font size
			font6.setColor(IndexedColors.WHITE.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyle6 = sheet.getWorkbook().createCellStyle();
			cellStyle6.setFont(font6);
			sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
			cellStyle6.setFillForegroundColor(IndexedColors.BLACK.getIndex());
			cellStyle6.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			Row row6 = sheet.createRow(6);
			Cell cell6 = row6.createCell(0);
			cell6.setCellStyle(cellStyle6);
			cell6.setCellValue("Performance Reporting");

			// Monthly Data
			Row row7 = sheet.createRow(7);
			Cell cell7 = row7.createCell(0);
			cell7.setCellStyle(cellStyleCustom11);
			cell7.setCellValue("Monthly Data");

			cell7 = row7.createCell(1);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(2);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(3);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(4);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(5);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(6);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(7);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(8);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(9);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(10);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(11);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			cell7 = row7.createCell(12);
			cell7.setCellStyle(cellStyleFontBold);
			cell7.setCellValue("Jan. 2021");

			// Actual Generation (kWh)
			Row row8 = sheet.createRow(8);
			Cell cell8 = row8.createCell(0);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("Actual Generation (kWh)");

			cell8 = row8.createCell(1);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(2);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(3);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(4);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(5);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(6);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(7);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(8);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(9);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(10);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(11);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);
			// -----
			cell8 = row8.createCell(12);
			cell8.setCellStyle(cellStyleItem);
			cell8.setCellValue(178430);

			// Baseline Generation (kWh)
			Row row9 = sheet.createRow(9);
			Cell cell9 = row9.createCell(0);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("Baseline Generation (kWh)");
			// -----
			cell9 = row9.createCell(1);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(2);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(3);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(4);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(5);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(6);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(7);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(8);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(9);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(10);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(11);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);
			// -----
			cell9 = row9.createCell(12);
			cell9.setCellStyle(cellStyleItem);
			cell9.setCellValue(178430);

			// Baseline Generation Index (%)
			Row row10 = sheet.createRow(10);
			Cell cell10 = row10.createCell(0);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("Baseline Generation Index (%)");
			// -----
			cell10 = row10.createCell(1);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(2);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(3);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(4);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(5);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(6);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(7);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(8);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(9);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(10);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(11);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");
			// -----
			cell10 = row10.createCell(12);
			cell10.setCellStyle(cellStyleItem);
			cell10.setCellValue(80 + "%");

			// Trailing Twelve Month Generation

			Row row11 = sheet.createRow(11);
			Cell cell11 = row11.createCell(0);
			cell11.setCellStyle(cellStyleCustom11);
			cell11.setCellValue("Trailing Twelve Month Generation");

			// Actual Generation (kWh)
			Row row12 = sheet.createRow(12);
			Cell cell12 = row12.createCell(0);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("Actual Generation (kWh)");
			// -----
			cell12 = row12.createCell(1);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(2);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(3);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(4);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(5);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(6);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(7);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(8);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(9);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(10);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(11);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);
			// -----
			cell12 = row12.createCell(12);
			cell12.setCellStyle(cellStyleItem);
			cell12.setCellValue(178430);

			// Baseline Generation (kWh)
			Row row13 = sheet.createRow(13);
			Cell cell13 = row13.createCell(0);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue("Baseline Generation (kWh)");
			// -----
			cell13 = row13.createCell(1);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(2);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(3);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(4);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(5);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(6);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(7);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(8);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(9);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(10);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(11);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);
			// -----
			cell13 = row13.createCell(12);
			cell13.setCellStyle(cellStyleItem);
			cell13.setCellValue(178430);

			// Baseline Generation Index (%)
			Row row14 = sheet.createRow(14);
			Cell cell14 = row14.createCell(0);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("Baseline Generation Index (%)");
			// -----
			cell14 = row14.createCell(1);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(2);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(3);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(4);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(5);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(6);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(7);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(8);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(9);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(10);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(11);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");
			// -----
			cell14 = row14.createCell(12);
			cell14.setCellStyle(cellStyleItem);
			cell14.setCellValue(79 + "%");

			// Inverter Availability (%)
			Row row15 = sheet.createRow(15);
			Cell cell15 = row15.createCell(0);
			cell15.setCellStyle(cellStyle);
			cell15.setCellValue("Inverter Availability (%)");
			// -----
			cell15 = row15.createCell(1);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(2);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(3);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(4);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(5);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(6);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(7);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(8);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(9);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(10);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(11);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");
			// -----
			cell15 = row15.createCell(12);
			cell15.setCellStyle(cellStyleItem);
			cell15.setCellValue(90 + "%");

			// draw chart

			// Create cells
//	        Cell cell = row.createCell(COLUMN_INDEX_ID);
//	        cell.setCellStyle(cellStyle);
//	        cell.setCellValue("Id");
			//
//	        cell = row.createCell(COLUMN_INDEX_TITLE);
//	        cell.setCellStyle(cellStyle);
//	        cell.setCellValue("Title");
			//
//	        cell = row.createCell(COLUMN_INDEX_PRICE);
//	        cell.setCellStyle(cellStyle);
//	        cell.setCellValue("Price");
			//
//	        cell = row.createCell(COLUMN_INDEX_QUANTITY);
//	        cell.setCellStyle(cellStyle);
//	        cell.setCellValue("Quantity");
			//
//	        cell = row.createCell(COLUMN_INDEX_TOTAL);
//	        cell.setCellStyle(cellStyle);
//	        cell.setCellValue("Total money");
		} catch (Exception e) {
		}

	}

	// Write data
	private static void writeBook(Book book, Row row) {
		if (cellStyleFormatNumber == null) {
			// Format number
			short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
			// DataFormat df = workbook.createDataFormat();
			// short format = df.getFormat("#,##0");

			// Create CellStyle
			Workbook workbook = row.getSheet().getWorkbook();
			cellStyleFormatNumber = workbook.createCellStyle();
			cellStyleFormatNumber.setDataFormat(format);
		}

		Cell cell = row.createCell(COLUMN_INDEX_ID);
		cell.setCellValue(book.getId());

		cell = row.createCell(COLUMN_INDEX_TITLE);
		cell.setCellValue(book.getTitle());

		cell = row.createCell(COLUMN_INDEX_PRICE);
		cell.setCellValue(book.getPrice());
		cell.setCellStyle(cellStyleFormatNumber);

		cell = row.createCell(COLUMN_INDEX_QUANTITY);
		cell.setCellValue(book.getQuantity());

		// Create cell formula
		// totalMoney = price * quantity
		cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
		cell.setCellStyle(cellStyleFormatNumber);
		int currentRow = row.getRowNum() + 1;
		String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
		String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);
		cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
	}

	// Create CellStyle for header
	private static CellStyle createStyleForHeader(Sheet sheet) {
		// Create font
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
//        font.setBold(true);
		font.setFontHeightInPoints((short) 12); // font size
		font.setColor(IndexedColors.BLACK.getIndex()); // text color

		// Create CellStyle
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
//        sheet.setDefaultColumnWidth(20);
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setColumnWidth(3, 25 * 256);
//        cellStyle.setDefaultColumnWidth(10);
		return cellStyle;
	}
	
	// Create CellStyle for report title
	private static CellStyle createStyleForReportTitle(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(true);
		font.setFontHeightInPoints((short) 22);
		font.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setDataFormat((short) 0x31);
		
		return cellStyle;
	}
	
	// Create CellStyle for report info
	private static CellStyle createStyleForReportInfo(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setDataFormat((short) 0x31);
		
		return cellStyle;
	}
	
	// Create CellStyle for report info (bold)
	private static CellStyle createStyleForReportInfoBold(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBold(true);
		
		CellStyle cellStyle = createStyleForReportInfo(sheet);
		cellStyle.setFont(font);
		
		return cellStyle;
	}
	
	// Create CellStyle for table title
	private static CellStyle createStyleForTableTitle(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.WHITE.getIndex());
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setDataFormat((short) 0x31);
		
		return cellStyle;
	}
	
	// Create CellStyle for table header
	private static CellStyle createStyleForTableHeader(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setDataFormat((short) 0x31);
		
		return cellStyle;
	}
	
	// Create CellStyle for table row (text)
	private static CellStyle createStyleForTableRow(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setDataFormat((short) 0x31);
		
		return cellStyle;
	}
	
	// Create CellStyle for table row (number)
	private static CellStyle createStyleForTableRowNumber(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
		cellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(noDecimalDataFormat));
		
		return cellStyle;
	}
	
	// Create CellStyle for no border table row (number)
	private static CellStyle createStyleForNoBorderTableRowNumber(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(noDecimalDataFormat));
		
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

	// Write header with format
	private static void writeHeaderMonthlyReport(Sheet sheet, ViewReportEntity dataObj ) {
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
			
			CellStyle reportTitleCellStyle = createStyleForReportTitle(sheet);
			CellStyle reportInfoCellStyle = createStyleForReportInfo(sheet);
			CellStyle reportInfoBoldCellStyle = createStyleForReportInfoBold(sheet);
			CellStyle tableTitleCellStyle = createStyleForTableTitle(sheet);
			CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = createStyleForTableRow(sheet);
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRowNumber(sheet);
			CellStyle tableRowOneDecimalPlaceCellStyle = createStyleForTableRowNumber(sheet);
			tableRowOneDecimalPlaceCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceDataFormat));
			CellStyle tableRowNoDecimalBoldCellStyle = createStyleForNoBorderTableRowNumber(sheet);
			tableRowNoDecimalBoldCellStyle.setBorderTop(BorderStyle.DOUBLE);
			tableRowNoDecimalBoldCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			CellStyle tableRowOneDecimalPlaceBoldCellStyle = createStyleForNoBorderTableRowNumber(sheet);
			tableRowOneDecimalPlaceBoldCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceDataFormat));
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

			cell = row.createCell(1);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(dataObj.getStart_date() + " - " + dataObj.getEnd_date());

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
			
			CellStyle reportTitleCellStyle = createStyleForReportTitle(sheet);
			CellStyle reportInfoCellStyle = createStyleForReportInfo(sheet);
			CellStyle reportInfoBoldCellStyle = createStyleForReportInfoBold(sheet);
			
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
			CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = createStyleForTableRow(sheet);
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRowNumber(sheet);
			CellStyle tableRowOneDecimalPlaceCellStyle = createStyleForTableRowNumber(sheet);
			tableRowOneDecimalPlaceCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceDataFormat));
			CellStyle tableRowOneDecimalPlaceWithPercentageCellStyle = createStyleForTableRowNumber(sheet);
			tableRowOneDecimalPlaceWithPercentageCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceWithPercentageDataFormat));
			
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
			CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = createStyleForTableRow(sheet);
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRowNumber(sheet);
			CellStyle tableRowOneDecimalPlaceWithPercentageCellStyle = createStyleForTableRowNumber(sheet);
			tableRowOneDecimalPlaceWithPercentageCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceWithPercentageDataFormat));
			CellStyle tableRowNoDecimalNoBorderCellStyle = createStyleForNoBorderTableRowNumber(sheet);
			CellStyle tableRowOneDecimalPlaceWithPercentageNoBorderCellStyle = createStyleForNoBorderTableRowNumber(sheet);
			tableRowOneDecimalPlaceWithPercentageNoBorderCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceWithPercentageDataFormat));
			
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
			valueCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceWithPercentageDataFormat));
			
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
			CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = createStyleForTableRow(sheet);
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRowNumber(sheet);
			CellStyle tableRowNoDecimaLeftAlignlCellStyle = createStyleForTableRowNumber(sheet);
			tableRowNoDecimaLeftAlignlCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCurrencyCellStyle = createStyleForTableRowNumber(sheet);
			tableRowNoDecimalCurrencyCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(noDecimalCurrencyDataFormat));
			
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
			CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
			CellStyle tableRowCellStyle = createStyleForTableRow(sheet);
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRowNumber(sheet);
			CellStyle tableRowNoDecimaLeftAlignlCellStyle = createStyleForTableRowNumber(sheet);
			tableRowNoDecimaLeftAlignlCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCurrencyCellStyle = createStyleForTableRowNumber(sheet);
			tableRowNoDecimalCurrencyCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(noDecimalCurrencyDataFormat));
			
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
