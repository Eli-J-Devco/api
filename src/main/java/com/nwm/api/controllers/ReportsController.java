/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;
import java.util.Map;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFColorPreset;
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
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.ShapeUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
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
	private static final String oneDecimalPlaceWithPercentageDataFormat = "###,##0.0%";
	private static final String noDecimalCurrencyDataFormat = "$###,##0";
	
	// Write header with format
		private static void writeHeaderDailyReport(Sheet sheet, int rowIndex, ViewReportEntity dataObj) {
			try {
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
				
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 10));	
				Cell cell = row8.createCell(5);
				cell.setCellStyle(cellStyleFontBold);
				cell.setCellValue("DAILY PRODUCTION REPORT");
				
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
				
				sheet.addMergedRegion(new CellRangeAddress(24, 24, 0, 2));
				sheet.addMergedRegion(new CellRangeAddress(24, 24, 3, 5));
				sheet.addMergedRegion(new CellRangeAddress(24, 24, 6, 8));
				sheet.addMergedRegion(new CellRangeAddress(24, 24, 9, 11));
				
				Row row28 = sheet.createRow(24);
				Cell cel28 = row28.createCell(0);
				cel28.setCellStyle(cellStyleH);
				cel28.setCellValue("Time");
				
				Cell cel281 = row28.createCell(1);
				cel281.setCellStyle(cellStyleH);
				cel281.setCellValue("");
				
				Cell cel282 = row28.createCell(2);
				cel282.setCellStyle(cellStyleH);
				cel282.setCellValue("");
				
				Cell cel29 = row28.createCell(3);
				cel29.setCellStyle(cellStyleH);
				cel29.setCellValue("Actual Power (kW)");
				
				Cell cel294 = row28.createCell(4);
				cel294.setCellStyle(cellStyleH);
				cel294.setCellValue("");
				
				Cell cel295 = row28.createCell(5);
				cel295.setCellStyle(cellStyleH);
				cel295.setCellValue("");
				
				
				
				Cell cel30 = row28.createCell(6);
				cel30.setCellStyle(cellStyleH);
				cel30.setCellValue("Estimate Energy (kWh)");
				
				Cell cel307 = row28.createCell(7);
				cel307.setCellStyle(cellStyleH);
				cel307.setCellValue("");
				
				Cell cel308 = row28.createCell(8);
				cel308.setCellStyle(cellStyleH);
				cel308.setCellValue("");
				
				
				Cell cel31 = row28.createCell(9);
				cel31.setCellStyle(cellStyleH);
				cel31.setCellValue("Irradiance (W/m2)");
				
				Cell cel3110 = row28.createCell(10);
				cel3110.setCellStyle(cellStyleH);
				cel3110.setCellValue("");
				
				Cell cel3111 = row28.createCell(11);
				cel3111.setCellStyle(cellStyleH);
				cel3111.setCellValue("");
				
				
				List dataExports = dataObj.getDataReports();
				if(dataExports.size() > 0) {
					for(int i = 0 ;i < dataExports.size(); i++) {
						DailyDateEntity item = (DailyDateEntity) dataExports.get(i); 
						int t = 25 + i;
						
						sheet.addMergedRegion(new CellRangeAddress(t, t, 0, 2));
						sheet.addMergedRegion(new CellRangeAddress(t, t, 3, 5));
						sheet.addMergedRegion(new CellRangeAddress(t, t, 6, 8));
						sheet.addMergedRegion(new CellRangeAddress(t, t, 9, 11));
						
						Row row28r = sheet.createRow(t);
						Cell cel28r = row28r.createCell(0);
						cel28r.setCellStyle(cellStyleR);
						cel28r.setCellValue(item.getCategories_time());
						
						Cell cel28r1 = row28r.createCell(1);
						cel28r1.setCellStyle(cellStyleR);
						cel28r1.setCellValue("");
						
						Cell cel28r2 = row28r.createCell(2);
						cel28r2.setCellStyle(cellStyleR);
						cel28r2.setCellValue("");
						
						Cell cel29r = row28r.createCell(3);
						cel29r.setCellStyle(cellStyleR);
						String power = item.getPower() < 0 ? "0" : (item.getPower() == 0.001 ? null : dfs.format(item.getPower()));
						cel29r.setCellValue( power );
						
						Cell cel29r4 = row28r.createCell(4);
						cel29r4.setCellStyle(cellStyleR);
						cel29r4.setCellValue("");
						
						Cell cel29r5 = row28r.createCell(5);
						cel29r5.setCellStyle(cellStyleR);
						cel29r5.setCellValue("");
						
						Cell cel30r = row28r.createCell(6);
						cel30r.setCellStyle(cellStyleR);
						String energy = item.getEnergy() < 0 ? "0" : (item.getEnergy() == 0.001 ? null : dfs.format(item.getEnergy()));
						cel30r.setCellValue(energy);
						
						Cell cel30r7 = row28r.createCell(7);
						cel30r7.setCellStyle(cellStyleR);
						cel30r7.setCellValue("");
						
						Cell cel30r8 = row28r.createCell(8);
						cel30r8.setCellStyle(cellStyleR);
						cel30r8.setCellValue("");
						
						Cell cel31r = row28r.createCell(9);
						cel31r.setCellStyle(cellStyleR);
						String irradiance = item.getIrradiance() < 0 ? "0" : (item.getIrradiance() == 0.001 ? null : dfs.format(item.getIrradiance()));
						cel31r.setCellValue(irradiance);
						
						Cell cel31r10 = row28r.createCell(10);
						cel31r10.setCellStyle(cellStyleR);
						cel31r10.setCellValue("");
						
						Cell cel31r11 = row28r.createCell(11);
						cel31r11.setCellStyle(cellStyleR);
						cel31r11.setCellValue("");
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
			try {
				try (XSSFWorkbook document = new XSSFWorkbook()) {
					ReportsService service = new ReportsService();
					ViewReportEntity dataObj = (ViewReportEntity) service.getDailyReport(obj);
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
//						anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
						// set top-left corner for the image
						anchor.setCol1(11);
						anchor.setRow1(1);
//						anchor.setCol2(3);
//						anchor.setRow2(4);

						// Creates a picture
						Picture pict = drawing.createPicture(anchor, pictureIdx);
						// Reset the image to the original size
						pict.resize(1.45, 3.5);
						
						writeHeaderDailyReport(chartSheet, 0, dataObj);
						// create the data
						List dataExports = dataObj.getDataReports();
						if(dataExports.size() > 0) {
							for(int i = 0; i< dataExports.size(); i++) {
								
								DailyDateEntity item = (DailyDateEntity) dataExports.get(i); 
								Double power = item.getPower() <= 0 ? 0 : item.getPower(); 
								Double energy = item.getEnergy() <= 0 ? 0 :item.getEnergy(); 
								Double irradiance = item.getIrradiance() <= 0 ? 0 : item.getIrradiance();
								
								dataSheet.createRow(i).createCell(0).setCellValue(item.getCategories_time());
								dataSheet.getRow(i).createCell(1).setCellValue(((power == 0.001) ? 0 : power));
								dataSheet.getRow(i).createCell(2).setCellValue(energy == 0.001 ? 0: energy);
								dataSheet.getRow(i).createCell(3).setCellValue(irradiance == 0.001 ? 0: irradiance);
							}
						}
						
						XSSFClientAnchor anchor1;
						XSSFChart chart;
						// create the chart 
					    XSSFDrawing drawing1 = chartSheet.createDrawingPatriarch();
						
						//====== first line chart============================================================
						anchor1 = drawing1.createAnchor(0, 0, 0, 0, 0, 6, 12, 22);
						chart = drawing1.createChart(anchor1);
						chart.setTitleText("");
						chart.setTitleOverlay(false);

						// create data sources
						int numOfPoints = dataExports.size();
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
						
						for (int i = 0; i < numOfPoints; i++) {
							XSSFRow row = dataSheet.getRow(i);
							if (row == null)
								row = dataSheet.createRow(i);
							XSSFCell cell = row.createCell(255);
							cell.setCellValue(0);
						}

						
						// create axis
						XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
						if (bottomAxis.hasNumberFormat()) bottomAxis.setNumberFormat("@");
						
						XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
						leftAxis.setTitle("kW");
						leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
						leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
						leftAxis.setMinimum(0);
						if (leftAxis.hasNumberFormat()) leftAxis.setNumberFormat("#,##0.00");
			
						// create data and series
						XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
						XDDFLineChartData.Series series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData1);
						series.setTitle("Actual Power (kW)", new CellReference(chartSheet.getSheetName(), 24, 3, true, true));


						chart.plot(data);
						solidLineSeries(data, 0, PresetColor.STEEL_BLUE);
						
			
						// second line chart
						XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
						rightAxis.setCrosses(AxisCrosses.MAX);
						rightAxis.setTitle("kWh");
			
						// set correct cross axis
						bottomAxis.crossAxis(rightAxis);
						rightAxis.crossAxis(bottomAxis);
						rightAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
						rightAxis.setMinimum(0);
						if (rightAxis.hasNumberFormat()) rightAxis.setNumberFormat("#,##0.00");

						// create data and series
						data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
						series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData2);
						series.setTitle("Estimate Energy (kWh)", new CellReference(chartSheet.getSheetName(), 24, 6, true, true));
						chart.plot(data);
			
						solidLineSeries(data, 0, PresetColor.LIGHT_STEEL_BLUE);
						
						if (dataObj.isHave_poa()) {
							// three line chart
							XDDFValueAxis rightAxis3 = chart.createValueAxis(AxisPosition.RIGHT);
							rightAxis3.setCrosses(AxisCrosses.MAX);
							rightAxis3.setTitle("W/m2");
							rightAxis3.setCrossBetween(AxisCrossBetween.BETWEEN);
				
							// set correct cross axis
							bottomAxis.crossAxis(rightAxis3);
							rightAxis3.crossAxis(bottomAxis);
							rightAxis3.setMinimum(0);
							if (rightAxis3.hasNumberFormat()) rightAxis3.setNumberFormat("#,##0.00");
							
							// create data and series
							data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis3);
							series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData3);
							series.setTitle("Irradiance (W/m2)", new CellReference(chartSheet.getSheetName(), 24, 9, true, true));
	
							chart.plot(data);
							solidLineSeries(data, 0, PresetColor.DARK_ORANGE);
						}
						
						// create legend
						XDDFChartLegend legend = chart.getOrAddLegend();
						legend.setPosition(LegendPosition.BOTTOM);
						legend.setOverlay(false);
						
						// Write the output to a file
						String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
						String dir = uploadRootPath() + "/"
								+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
						String fileName = dir + "/daily-production-report-" + timeStamp + ".xlsx";
						
						try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
							document.write(fileOut);
							String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
									Constants.mailFromContact);

							String msgTemplate = Constants.getMailTempleteByState(16);
							String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "Daily ", "", "");
							String mailTo = dataObj.getSubscribers();
							String subject = Constants.getMailSubjectByState(16);

							String tags = "report_daily";
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
		 * @description sent mail daily report in pdf
		 * @author Hung.Bui
		 * @since 2022-11-29
		 * @param id
		 * @return data (status, message, array, total_row
		 */
		@PostMapping("/sent-mail-pdf-daily-report")
		public Object sentMailPdfDailyReport(@RequestBody ViewReportEntity obj) {
			try {
				String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
				String dir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
				String fileName = dir + "/daily-production-report-" + timeStamp + ".pdf";
				File file = new File(fileName);
				
				try (
						PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
						Document document = new Document(pdfDocument, PageSize.A3.rotate());
						) {
					
					ReportsService service = new ReportsService();
					ViewReportEntity dataObj = (ViewReportEntity) service.getDailyReport(obj);

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
						table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
						// empty row
						table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
						
						// header of data table
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Time").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Actual Power (kW)").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Estimated Energy (kWh)").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Irradiance (W/m2)").setBold()));
						// data table
						DecimalFormat df = new DecimalFormat("###,###.#");
						DecimalFormat dfs = new DecimalFormat("###,###");
						for (int i = 0; i < dataExports.size(); i++) {
							DailyDateEntity item = (DailyDateEntity) dataExports.get(i);
							
							String power = item.getPower() < 0 ? "0" : (item.getPower() == 0.001 ? "" : dfs.format(item.getPower()));
							String energy = item.getEnergy() < 0 ? "0" : (item.getEnergy() == 0.001 ? "" : dfs.format(item.getEnergy()));
							String irradiance = item.getIrradiance() < 0 ? "0" : (item.getIrradiance() == 0.001 ? "" : dfs.format(item.getIrradiance()));

							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getCategories_time())));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(power)));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(energy)));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(irradiance)));						
						}
						
						//====== chart ============================================================
						final float tickMarkLength = 5;
						final float tickMarkStroke = 1;
						final float seriesStroke = 2;
						final double domainAxisMargin = 0;
						
						TimeSeries powerSeries = new TimeSeries("Actual Power (kW)");
						TimeSeries energySeries = new TimeSeries("Estimate Energy (kWh)");
						TimeSeries irradianceSeries = new TimeSeries("Irradiance (W/m2)");
						
						TimeSeriesCollection powerDataset = new TimeSeriesCollection(powerSeries);
						TimeSeriesCollection energyDataset = new TimeSeriesCollection(energySeries);
						TimeSeriesCollection irradianceDataset = new TimeSeriesCollection(irradianceSeries);
						
						JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "", powerDataset);
						
						// configure plot
						XYPlot plot = chart.getXYPlot();
						plot.setBackgroundPaint(Color.white);
						plot.setRangeGridlinePaint(Color.gray);
						// remove gap between plot and axis
						plot.setAxisOffset(new RectangleInsets(0,0,0,0));
						
						// configure horizontal axis
						DateAxis domainAxis= (DateAxis) plot.getDomainAxis();
						domainAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd/yyy HH-mm a"));
						domainAxis.setTickMarkInsideLength(tickMarkLength);
						domainAxis.setTickMarkOutsideLength(tickMarkLength);
						domainAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
						domainAxis.setLowerMargin(domainAxisMargin);
						domainAxis.setUpperMargin(domainAxisMargin);
						domainAxis.setVerticalTickLabels(true);
						
						// dataset
						for ( int i = 0; i < dataExports.size(); i++ ) {
							DailyDateEntity item = (DailyDateEntity) dataExports.get(i);
							
							double power = item.getPower() <= 0 ? 0 : (item.getPower() == 0.001 ? 0 : item.getPower());
							double energy = item.getEnergy() <= 0 ? 0 : (item.getEnergy() == 0.001 ? 0 : item.getEnergy());
							double irradiance = item.getIrradiance() <= 0 ? 0 : (item.getIrradiance() == 0.001 ? 0 : item.getIrradiance());
							
							powerSeries.add(new Minute(new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(item.getCategories_time())), power);
							energySeries.add(new Minute(new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(item.getCategories_time())), energy);
							irradianceSeries.add(new Minute(new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(item.getCategories_time())), irradiance);
						}
						
						// power line chart
						XYLineAndShapeRenderer powerRenderer = new XYLineAndShapeRenderer(true, false);
						powerRenderer.setSeriesPaint(0, new Color(49, 119, 168));
						powerRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));
						
						NumberAxis powerAxis = new NumberAxis("kW");
						powerAxis.setTickMarkInsideLength(tickMarkLength);
						powerAxis.setTickMarkOutsideLength(tickMarkLength);
						powerAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
						
						plot.setRenderer(0, powerRenderer);
						plot.setRangeAxis(0, powerAxis);
						plot.setDataset(0, powerDataset);
						plot.mapDatasetToRangeAxis(0, 0);
						
						// energy line chart
						XYLineAndShapeRenderer energyRenderer = new XYLineAndShapeRenderer(true, false);
						energyRenderer.setSeriesPaint(0, new Color(163, 188, 215));
						energyRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));

						NumberAxis energyAxis = new NumberAxis("kWh");
						energyAxis.setTickMarkInsideLength(tickMarkLength);
						energyAxis.setTickMarkOutsideLength(tickMarkLength);
						energyAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
						
						plot.setRenderer(1, energyRenderer);
						plot.setRangeAxis(1, energyAxis);
						plot.setDataset(1, energyDataset);
						plot.mapDatasetToRangeAxis(1, 1);
						
						// irradiance line chart
						if (dataObj.isHave_poa()) {
							XYLineAndShapeRenderer irradianceRenderer = new XYLineAndShapeRenderer(true, false);
							irradianceRenderer.setSeriesPaint(0, new Color(255, 129, 39));
							irradianceRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));
	
							NumberAxis irradianceAxis = new NumberAxis("W/m2");
							irradianceAxis.setTickMarkInsideLength(tickMarkLength);
							irradianceAxis.setTickMarkOutsideLength(tickMarkLength);
							irradianceAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
							
							plot.setRenderer(2, irradianceRenderer);
							plot.setRangeAxis(2, irradianceAxis);
							plot.setDataset(2, irradianceDataset);
							plot.mapDatasetToRangeAxis(2, 2);
						}
						
						// plot and return image
						chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1800, 700), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 700));

						// Write the output to a file
						document.add(table);
						// It must be closed before attach to mail
						document.close();
						
					    String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
					    String msgTemplate = Constants.getMailTempleteByState(16);
					    String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "Daily ", "", "");
					    String mailTo = dataObj.getSubscribers();
					    String subject = Constants.getMailSubjectByState(16);
					    
					    String tags = "report_daily";
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
	private static void writeHeaderAnnuallyReport(Sheet sheet, int rowIndex, ArrayList categories, ArrayList actualGeneration, ViewReportEntity dataObj, ArrayList baselineGeneration, ArrayList baselineGenerationIndex, ArrayList actualGenerationTrailing, ArrayList baselineGenerationTrailing, ArrayList baselineGenerationIndexTrailing, ArrayList INVAvailability) {
		try {
			DecimalFormat df = new DecimalFormat("###,###");
			DecimalFormat dfp = new DecimalFormat("###,###.0");
			// create CellStyle
			Font fonDef = sheet.getWorkbook().createFont();
			fonDef.setFontName("Times New Roman");
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
			
			
			Font fonDefB = sheet.getWorkbook().createFont();
			fonDefB.setFontName("Times New Roman");
			fonDefB.setBold(true);
			fonDefB.setFontHeightInPoints((short) 12); // font size
			
			CellStyle cellStyleB = createStyleForHeader(sheet);
			cellStyleB.setFont(fonDefB);
			cellStyleB.setWrapText(true);
			cellStyleB.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleB.setBorderBottom(BorderStyle.THIN);
			cellStyleB.setBorderTop(BorderStyle.THIN);
			cellStyleB.setBorderRight(BorderStyle.THIN);
			cellStyleB.setBorderLeft(BorderStyle.THIN);
			cellStyleB.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleB.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleB.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleB.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
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
			
			cellStyleItem.setBorderBottom(BorderStyle.THIN);
			cellStyleItem.setBorderTop(BorderStyle.THIN);
			cellStyleItem.setBorderRight(BorderStyle.THIN);
			cellStyleItem.setBorderLeft(BorderStyle.THIN);
			cellStyleItem.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleItem.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleItem.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleItem.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			

			// Create font
			Font fontBold = sheet.getWorkbook().createFont();
			fontBold.setFontName("Times New Roman");
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

			Row row1 = sheet.createRow(0);
			row1.setHeight((short) 600);
			Cell cell = row1.createCell(0);
			cell.setCellStyle(cellStyleB);
			cell.setCellValue("Site Name");

			cell = row1.createCell(1);
			cell.setCellStyle(cellStyleB);
			cell.setCellValue(dataObj.getSite_name());

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
			
			cellStyleCustom.setBorderBottom(BorderStyle.THIN);
			cellStyleCustom.setBorderTop(BorderStyle.THIN);
			cellStyleCustom.setBorderRight(BorderStyle.THIN);
			cellStyleCustom.setBorderLeft(BorderStyle.THIN);
			cellStyleCustom.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCustom.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCustom.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCustom.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			cell = row1.createCell(2);
			cell.setCellStyle(cellStyleCustom);
			cell.setCellValue("ANNUAL PRODUCTION REPORT");

			// Create CellStyle image

			// Create font
			Font font11 = sheet.getWorkbook().createFont();
			font11.setFontName("Times New Roman");
			font11.setBold(true);
			font11.setFontHeightInPoints((short) 12); // font size
			font11.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleCustom11 = sheet.getWorkbook().createCellStyle();
			cellStyleCustom11.setFont(font11);
			sheet.addMergedRegion(new CellRangeAddress(11, 11, 2, 13));
			cellStyleCustom11.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleCustom11.setVerticalAlignment(VerticalAlignment.CENTER);
			
			cellStyleCustom11.setBorderBottom(BorderStyle.THIN);
			cellStyleCustom11.setBorderTop(BorderStyle.THIN);
			cellStyleCustom11.setBorderRight(BorderStyle.THIN);
			cellStyleCustom11.setBorderLeft(BorderStyle.THIN);
			cellStyleCustom11.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCustom11.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCustom11.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCustom11.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			

			Row row2 = sheet.createRow(1);
			row2.setHeight((short) 600);
			Cell cell2 = row2.createCell(0);
			cell2.setCellStyle(cellStyleB);
			cell2.setCellValue("Report Date");

			cell2 = row2.createCell(1);
			cell2.setCellStyle(cellStyleTitle);
			cell2.setCellValue(dataObj.getReport_date());

			Row row3 = sheet.createRow(2);
			row3.setHeight((short) 600);
			Cell cell3 = row3.createCell(0);
			cell3.setCellStyle(cellStyleB);
			cell3.setCellValue("Covered Period");

			cell3 = row3.createCell(1);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue(dataObj.getStart_date() + " - " + dataObj.getEnd_date());

			Row row4 = sheet.createRow(3);
			row4.setHeight((short) 600);
			Cell cell4 = row4.createCell(0);
			cell4.setCellStyle(cellStyleB);
			cell4.setCellValue("System Size (kW DC)");

			cell4 = row4.createCell(1);
			cell4.setCellStyle(cellStyleTitle);
			cell4.setCellValue(dataObj.getDc_capacity());


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
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(10, 10, 0, 1));

			sheet.addMergedRegion(new CellRangeAddress(11, 11, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(12, 12, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(14, 14, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(15, 15, 0, 1));
			cellStyle6.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyle6.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle6.setVerticalAlignment(VerticalAlignment.CENTER);
			
			cellStyle6.setBorderBottom(BorderStyle.THIN);
			cellStyle6.setBorderTop(BorderStyle.THIN);
			cellStyle6.setBorderRight(BorderStyle.THIN);
			cellStyle6.setBorderLeft(BorderStyle.THIN);
			cellStyle6.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle6.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle6.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle6.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

			Row row6 = sheet.createRow(5);
			Cell cell6 = row6.createCell(0);
			cell6.setCellStyle(cellStyle6);
			cell6.setCellValue("Performance Reporting");
			
			Cell cell61 = row6.createCell(1);
			cell61.setCellStyle(cellStyle6);
			cell61.setCellValue("");

			// Monthly Data
			Row row7 = sheet.createRow(7);
			Cell cell7 = row7.createCell(0);
			cell7.setCellStyle(cellStyleCustom11);
			cell7.setCellValue("Monthly Data");
			
			Cell cell71 = row7.createCell(1);
			cell71.setCellStyle(cellStyleCustom11);
			cell71.setCellValue("");

			int r = 2;
			for (int i = 0; i < categories.size(); i++) {
				cell7 = row7.createCell(r + i);
				cell7.setCellStyle(cellStyleFontBold);
				cell7.setCellValue((String) categories.get(i));
			}
			

			// Actual Generation (kWh)
			Row row8 = sheet.createRow(8);
			Cell cell8 = row8.createCell(0);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("Actual Generation (kWh)");
			
			Cell cell81 = row8.createCell(1);
			cell81.setCellStyle(cellStyle);
			cell81.setCellValue("");
			
			int v = 2;
			for (int i = 0; i < actualGeneration.size(); i++) {
				cell8 = row8.createCell(v + i);
				cell8.setCellStyle(cellStyleItem);
				cell8.setCellValue(df.format(actualGeneration.get(i)));
			}
			

			// Baseline Generation (kWh)
			Row row9 = sheet.createRow(9);
			Cell cell9 = row9.createCell(0);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("Estimated Generation (kWh)");
			
			Cell cell91 = row9.createCell(1);
			cell91.setCellStyle(cellStyle);
			cell91.setCellValue("");
			
			int u = 2;
			for (int i = 0; i < baselineGeneration.size(); i++) {
				cell9 = row9.createCell(u + i);
				cell9.setCellStyle(cellStyleItem);
				cell9.setCellValue(df.format(baselineGeneration.get(i)));
			}


			// Baseline Generation Index (%)
			Row row10 = sheet.createRow(10);
			Cell cell10 = row10.createCell(0);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("Estimated Generation Index (%)");
			
			Cell cell101 = row10.createCell(1);
			cell101.setCellStyle(cellStyle);
			cell101.setCellValue("");
			
			
			int n = 2;
			for (int i = 0; i < baselineGenerationIndex.size(); i++) {
				cell10 = row10.createCell(n + i);
				cell10.setCellStyle(cellStyleItem);
			
				if((double)baselineGenerationIndex.get(i) == 0) {
					cell10.setCellValue(baselineGenerationIndex.get(i).toString());
				} else {
					cell10.setCellValue(dfp.format(baselineGenerationIndex.get(i)));
				}
				
				
			}


			// Trailing Twelve Month Generation

			Row row11 = sheet.createRow(11);
			Cell cell11 = row11.createCell(0);
			cell11.setCellStyle(cellStyleCustom11);
			cell11.setCellValue("Trailing Twelve Month Generation");
			
			Cell cell111 = row11.createCell(1);
			cell111.setCellStyle(cellStyleCustom11);
			cell111.setCellValue("");

			// Actual Generation (kWh)
			Row row12 = sheet.createRow(12);
			Cell cell12 = row12.createCell(0);
			cell12.setCellStyle(cellStyle);
			cell12.setCellValue("Actual Generation (kWh)");

			
			int z = 2;
			for (int i = 0; i < actualGenerationTrailing.size(); i++) {
				cell12 = row12.createCell(z + i);
				cell12.setCellStyle(cellStyleItem);
				cell12.setCellValue(df.format(actualGenerationTrailing.get(i)));
				
				
			}

			// Baseline Generation (kWh)
			Row row13 = sheet.createRow(13);
			Cell cell13 = row13.createCell(0);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue("Estimated Generation (kWh)");
			
			Cell cell131 = row13.createCell(1);
			cell131.setCellStyle(cellStyle);
			cell131.setCellValue("");
			
			int m = 2;
			for (int i = 0; i < baselineGenerationTrailing.size(); i++) {
				cell13 = row13.createCell(m + i);
				cell13.setCellStyle(cellStyleItem);
				cell13.setCellValue(df.format(baselineGenerationTrailing.get(i)));
				
			}

			// Baseline Generation Index (%)
			Row row14 = sheet.createRow(14);
			Cell cell14 = row14.createCell(0);
			cell14.setCellStyle(cellStyle);
			cell14.setCellValue("Estimated Generation Index (%)");

			Cell cell141 = row14.createCell(1);
			cell141.setCellStyle(cellStyle);
			cell141.setCellValue("");
			
			int g = 2;
			for (int i = 0; i < baselineGenerationIndexTrailing.size(); i++) {
				cell14 = row14.createCell(g + i);
				cell14.setCellStyle(cellStyleItem);
				if((double)baselineGenerationIndexTrailing.get(i) == 0) {
					cell14.setCellValue(baselineGenerationIndexTrailing.get(i).toString());
				} else {
					cell14.setCellValue( dfp.format(baselineGenerationIndexTrailing.get(i)));
				}
				
				
			}


			// Inverter Availability (%)
			Row row15 = sheet.createRow(15);
			Cell cell15 = row15.createCell(0);
			cell15.setCellStyle(cellStyle);
			if(dataObj.getDeviceType() == "meter") {
				cell15.setCellValue("Site Availability (%)");
			} else {
				cell15.setCellValue("Inverter Availability (%)");
			}
			
			
			Cell cell151 = row15.createCell(1);
			cell151.setCellStyle(cellStyle);
			cell151.setCellValue("");

			int h = 2;
			for (int i = 0; i < INVAvailability.size(); i++) {
				cell15 = row15.createCell(h + i);
				cell15.setCellStyle(cellStyleItem);
				cell15.setCellValue(INVAvailability.get(i).toString());
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
		try {
			try (XSSFWorkbook document = new XSSFWorkbook()) {
				ReportsService service = new ReportsService();
				ViewReportEntity dataObj = (ViewReportEntity) service.getAnnuallyReport(obj);
				if (dataObj != null) {
					double totalMWH = 0;
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = dateFormat.parse(obj.getEnd_date());
					String lastOfMonth = new SimpleDateFormat("dd").format(convertedDate);
					Date startDate = dateFormat.parse(obj.getStart_date());
					dataObj.setStart_date( new SimpleDateFormat("MM/dd/yyyy").format(startDate) );
					dataObj.setEnd_date( new SimpleDateFormat("MM/dd/yyyy").format(convertedDate) );
					
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
					double totalMWHTmp = 0;
					double totalGeneration = 0;
					double totalExpectations = 0;
					for (int i = 0; i < 12; i++) {
						Calendar c = Calendar.getInstance();
						c.setTime(startDate);
						c.add(Calendar.MONTH, i);
						categories.add(monthFormat.format(c.getTime()));
						Double v = 0d;
						for( int j = 0; j < dataExports.size(); j++){
							Map<String, Object> item = (Map<String, Object>) dataExports.get(j);
							String date = (String) item.get("categories_time");
							if(date.equals(dateFormatCategories.format(c.getTime()) )) {
								v = (Double)item.get("chart_energy_kwh");
								totalMWHTmp = v;
								totalGeneration = totalGeneration + v;
							}
						}
						
						actualGeneration.add(v);
						totalMWH = totalMWH + totalMWHTmp;
						actualGenerationTrailing.add(totalGeneration);
						
						Double availability = 0.0;
						
						if(dataAvailability.size() > 0) {
							for( int j = 0; j < dataAvailability.size(); j++){
								Map<String, Object> item = (Map<String, Object>) dataAvailability.get(j);
								String date = (String) item.get("time_full");
								if(date.equals(dateFormatCategories.format(c.getTime()) )) {
									availability = (Double)item.get("InverterAvailability");
								}
							}
						}
						
						INVAvailability.add(availability);
						
						// baseline Generation
						Double baseline = 0.0;
						if(dataExpectations.size() > 0) {
							for( int k = 0; k < dataExpectations.size(); k++){
								Map<String, Object> itemEx = (Map<String, Object>) dataExpectations.get(k);
								String year = itemEx.get("year").toString();
								if(year.equals(yearFormat.format(c.getTime()))) {
									Double monthValue = Double.parseDouble(itemEx.get((monthFormat.format(c.getTime())).toLowerCase()).toString());
									baseline = monthValue;
									totalExpectations = totalExpectations + baseline;
								}
							}
						}
						
						baselineGeneration.add(baseline);
						baselineGenerationIndex.add( (v/baseline) * 100);
						baselineGenerationTrailing.add(totalExpectations);
						baselineGenerationIndexTrailing.add((double) (totalGeneration/totalExpectations) * 100 );
					}
					
					XSSFSheet chartSheet = document.createSheet("Annual Performance");
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
					anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
					// set top-left corner for the image
					anchor.setCol1(12);
					anchor.setRow1(1);
					//anchor.setCol2(3);
					//anchor.setRow2(4);

					// Creates a picture
					Picture pict = drawing.createPicture(anchor, pictureIdx);
					// Reset the image to the original size
//					pict.resize(1.0, 1.0);
					pict.resize(1.45, 3.5);

					writeHeaderAnnuallyReport(chartSheet, 0, categories, actualGeneration, dataObj, baselineGeneration, baselineGenerationIndex, actualGenerationTrailing, baselineGenerationTrailing, baselineGenerationIndexTrailing, INVAvailability );
					// create the data
					int r = 0;
					for (String cat : categories) {
						dataSheet.createRow(r).createCell(0).setCellValue(cat);
						dataSheet.getRow(r).createCell(1).setCellValue(actualGeneration.get(r));
						dataSheet.getRow(r).createCell(2).setCellValue(baselineGeneration.get(r));
						dataSheet.getRow(r).createCell(3).setCellValue(baselineGenerationIndex.get(r));
						r++;
					}

					// create the chart
					XSSFDrawing drawing1 = chartSheet.createDrawingPatriarch();
					XSSFClientAnchor anchor1 = drawing1.createAnchor(0, 0, 0, 0, 0, 18, 14, 40);
					XDDFChart chart = drawing1.createChart(anchor1);
					chart.setTitleText("Annual Performance");
					chart.setTitleOverlay(false);
					chart.getCTChart().getTitle().getTx().getRich().getPArray(0).getRArray(0).getRPr().setSz(1200);

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

					for (int i = 0; i < numOfPoints; i++) {
						XSSFRow row = dataSheet.getRow(i);
						if (row == null)
							row = dataSheet.createRow(i);
						XSSFCell cell = row.createCell(255);
						cell.setCellValue(0);
					}


					// first bar chart
					XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
					if (bottomAxis.hasNumberFormat()) bottomAxis.setNumberFormat("@");
					
					XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
					leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
					leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
					leftAxis.setTitle("GENERATION (KWH)");
					if (leftAxis.hasNumberFormat()) leftAxis.setNumberFormat("#,##0.00");

					XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
					XDDFBarChartData bar = (XDDFBarChartData) data;
					bar.setBarDirection(BarDirection.COL);

					CTPlotArea plotArea = chart.getCTChart().getPlotArea();
					plotArea.getValAxArray()[0].addNewMajorGridlines();
					

					XDDFChartData.Series series = data.addSeries(categoriesData, valuesData1);
					series.setTitle("Actual Generation (kWh)",
							new CellReference(chartSheet.getSheetName(), 8, 0, true, true));
					series = data.addSeries(categoriesData, valuesData2);
					
					
					
					series.setTitle("Baseline Generation (kWh)",
							new CellReference(chartSheet.getSheetName(), 9, 0, true, true));
					// additional pad series - takes space at right side for primary axis
					chart.plot(data);

					// set bar colors
					solidFillSeries(data, 0, PresetColor.STEEL_BLUE);
					solidFillSeries(data, 1, PresetColor.LIGHT_STEEL_BLUE);
					

					// second bar chart
					// bottom axis must be there but must not be visible
					bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
					bottomAxis.setVisible(false);
					if (bottomAxis.hasNumberFormat()) bottomAxis.setNumberFormat("@");

					XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
					rightAxis.setCrosses(AxisCrosses.MAX);
					rightAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
					rightAxis.setTitle("PERFORMANCE INDEX (%)"); // comment
					if (rightAxis.hasNumberFormat()) rightAxis.setNumberFormat("#,##0.00");

					// set correct cross axis
					bottomAxis.crossAxis(rightAxis);
					rightAxis.crossAxis(bottomAxis);
					

					data = chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
					data.setVaryColors(false);
					bar.setBarDirection(BarDirection.COL);


				    
					XDDFLineChartData.Series seriesLine = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData3);

					seriesLine.setTitle("Estimated Generation Index (%)", new CellReference(chartSheet.getSheetName(), 10, 0, true, true));
					chart.plot(data);			
					// set line colors				
					solidLineSeries(data, 0, PresetColor.GRAY);



					// this must occur after the call to chart.plot above
//					CTPlotArea plotAreaLine = chart.getCTChart().getPlotArea();
//				    for (CTLineChart ch : plotAreaLine.getLineChartList()) {
//				        for (CTLineSer ser : ch.getSerList()) {
//				            CTBoolean ctBool = CTBoolean.Factory.newInstance();
//				            ser.setSmooth(ctBool);				            
//				            ser.addNewMarker().addNewSymbol().setVal(STMarkerStyle.NONE);
//				        }
//				    }
				    
					
					// set legend
					XDDFChartLegend legend = chart.getOrAddLegend();
					legend.setPosition(LegendPosition.BOTTOM);

					// Write the output to a file
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String dir = uploadRootPath() + "/"
							+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
					String fileName = dir + "/Annual-report-" + timeStamp + ".xlsx";

					try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
						document.write(fileOut);
						String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
								Constants.mailFromContact);

						String msgTemplate = Constants.getMailTempleteByState(16);
						String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "Annual ", "", "");
						String mailTo = dataObj.getSubscribers();
						String subject = Constants.getMailSubjectByState(16);

						String tags = "report_annually";
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
	 * @description sent mail annually report in pdf
	 * @author Hung.Bui
	 * @since 2022-11-29
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-pdf-annually-report")
	public Object sentMailPdfAnnuallyReport(@RequestBody ViewReportEntity obj) {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
			String dir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
			String fileName = dir + "/Annual-report-" + timeStamp + ".pdf";
			File file = new File(fileName);
			
			try (
					PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
					Document document = new Document(pdfDocument, PageSize.A3.rotate());
					) {
				
				ReportsService service = new ReportsService();
				ViewReportEntity dataObj = (ViewReportEntity) service.getAnnuallyReport(obj);
				
				if (dataObj != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = dateFormat.parse(obj.getEnd_date());
					String lastOfMonth = new SimpleDateFormat("dd").format(convertedDate);
					Date startDate = dateFormat.parse(obj.getStart_date());
					dataObj.setStart_date( new SimpleDateFormat("MM/dd/yyyy").format(startDate) );
					dataObj.setEnd_date( new SimpleDateFormat("MM/dd/yyyy").format(convertedDate) );
					
					List<?> dataExports = dataObj.getDataReports();
					List<?> dataAvailability = dataObj.getDataAvailability();
					List<?> dataExpectations = dataObj.getDataExpectations();
					
					// calculate for data of table
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
					
					double totalMWH = 0;
					double totalMWHTmp = 0;
					double totalGeneration = 0;
					double totalExpectations = 0;
					
					for (int i = 0; i < 12; i++) {
						Calendar c = Calendar.getInstance();
						c.setTime(startDate);
						c.add(Calendar.MONTH, i);
						categories.add(monthFormat.format(c.getTime()));
						Double v = 0d;
						for( int j = 0; j < dataExports.size(); j++){
							Map<String, Object> item = (Map<String, Object>) dataExports.get(j);
							String date = (String) item.get("categories_time");
							if(date.equals(dateFormatCategories.format(c.getTime()) )) {
								v = (Double)item.get("chart_energy_kwh");
								totalMWHTmp = v;
								totalGeneration = totalGeneration + v;
							}
						}
						
						actualGeneration.add(v);
						totalMWH = totalMWH + totalMWHTmp;
						actualGenerationTrailing.add(totalGeneration);
						
						Double availability = 0.0;
						
						if(dataAvailability.size() > 0) {
							for( int j = 0; j < dataAvailability.size(); j++){
								Map<String, Object> item = (Map<String, Object>) dataAvailability.get(j);
								String date = (String) item.get("time_full");
								if(date.equals(dateFormatCategories.format(c.getTime()) )) {
									availability = (Double)item.get("InverterAvailability");
								}
							}
						}

						INVAvailability.add(availability);
						
						// baseline Generation
						Double baseline = 0.0;
						if(dataExpectations.size() > 0) {
							for( int k = 0; k < dataExpectations.size(); k++){
								Map<String, Object> itemEx = (Map<String, Object>) dataExpectations.get(k);
								String year = itemEx.get("year").toString();
								if(year.equals(yearFormat.format(c.getTime()))) {
									Double monthValue = Double.parseDouble(itemEx.get((monthFormat.format(c.getTime())).toLowerCase()).toString());
									baseline = monthValue;
									totalExpectations = totalExpectations + baseline;
								}
							}
						}
						
						baselineGeneration.add(baseline);
						baselineGenerationIndex.add( (v/baseline) * 100);
						baselineGenerationTrailing.add(totalExpectations);
						baselineGenerationIndexTrailing.add((double) (totalGeneration/totalExpectations) * 100 );
					}

					// total column: 14
					Table table = new Table(UnitValue.createPercentArray(14)).useAllAvailableWidth();
					table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
					table.setFontSize(8);
					table.setTextAlignment(TextAlignment.CENTER);
					
					Image logoImage = new Image(ImageDataFactory.create(uploadRootPath() + "/reports/logo-report.jpg"));
					logoImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT).scaleToFit(100, 100);
				
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
					DecimalFormat df = new DecimalFormat("###,###");
					DecimalFormat dfp = new DecimalFormat("###,###.0");

					// Monthly Data
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Monthly Data").setBold()).setTextAlignment(TextAlignment.LEFT));
					for (int i = 0; i < categories.size(); i++) {
						table.addCell(new Paragraph(categories.get(i)).setBold());
					}
					
					// Actual Generation (kWh)
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Actual Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
					for (int i = 0; i < actualGeneration.size(); i++) {
						table.addCell(df.format(actualGeneration.get(i)));
					}
					
					// Baseline Generation (kWh)
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
					for (int i = 0; i < baselineGeneration.size(); i++) {
						table.addCell(df.format(baselineGeneration.get(i)));
					}
					
					// Baseline Generation Index (%)
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation Index (%)")).setTextAlignment(TextAlignment.LEFT));
					for (int i = 0; i < baselineGenerationIndex.size(); i++) {
						table.addCell(baselineGenerationIndex.get(i) == 0 ? baselineGenerationIndex.get(i).toString() : dfp.format(baselineGenerationIndex.get(i)));
					}
					
					// Trailing Twelve Month Generation
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Trailing Twelve Month Generation").setBold()).setTextAlignment(TextAlignment.LEFT));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 12));
					
					// Actual Generation (kWh)
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Actual Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
					for (int i = 0; i < actualGenerationTrailing.size(); i++) {
						table.addCell(df.format(actualGenerationTrailing.get(i)));
					}
					
					// Baseline Generation (kWh)
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation (kWh)")).setTextAlignment(TextAlignment.LEFT));
					for (int i = 0; i < baselineGenerationTrailing.size(); i++) {
						table.addCell(df.format(baselineGenerationTrailing.get(i)));
					}
					
					// Baseline Generation Index (%)
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Estimated Generation Index (%)")).setTextAlignment(TextAlignment.LEFT));
					for (int i = 0; i < baselineGenerationIndexTrailing.size(); i++) {
						table.addCell(baselineGenerationIndexTrailing.get(i) == 0 ? baselineGenerationIndexTrailing.get(i).toString() : dfp.format(baselineGenerationIndexTrailing.get(i)));
					}
					
					// Inverter Availability (%)
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getDeviceType() == "meter" ? "Site Availability (%)" : "Inverter Availability (%)")).setTextAlignment(TextAlignment.LEFT));
					for (int i = 0; i < INVAvailability.size(); i++) {
						table.addCell(INVAvailability.get(i).toString());
					}

					// empty row: gap between data table and chart
					table.addCell(new com.itextpdf.layout.element.Cell(1, 14).setHeight(14).setBorder(Border.NO_BORDER));
					
					// chart
					com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(22, 14);
					table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
					
					//====== chart ============================================================
					final float tickMarkLength = 5;
					final float tickMarkStroke = 1;
					final double domainAxisMargin = 0.01;
					CategoryPlot plot = new CategoryPlot();
					
					// configure plot
					plot.setRangeGridlineStroke(new BasicStroke(tickMarkStroke));
					plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
					
					// configure horizontal axis
					CategoryAxis domainAxis = new CategoryAxis();
					domainAxis.setTickMarkInsideLength(tickMarkLength);
					domainAxis.setTickMarkOutsideLength(tickMarkLength);
					domainAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					domainAxis.setLowerMargin(domainAxisMargin);
					domainAxis.setUpperMargin(domainAxisMargin);
					domainAxis.setCategoryMargin(0.25);
					
					plot.setDomainAxis(domainAxis);
					
					// configure bar chart
					final DefaultCategoryDataset barChartDataset = new DefaultCategoryDataset();
					for ( int i = 0; i < categories.size(); i++ ) {
						barChartDataset.addValue(actualGeneration.get(i), "Actual Generation (kWh)", categories.get(i));
						barChartDataset.addValue(baselineGeneration.get(i), "Estimate Generation (kWh)", categories.get(i));
					}
					
					BarRenderer barRenderer = new BarRenderer();
					barRenderer.setShadowVisible(false);
					barRenderer.setBarPainter(new StandardBarPainter());
					barRenderer.setSeriesPaint(0, new Color(49, 119, 168));
					barRenderer.setSeriesPaint(1, new Color(163, 188, 215));
					barRenderer.setItemMargin(0);
					
					NumberAxis leftAxis = new NumberAxis("GENERATION (KWH)");
					leftAxis.setTickMarkInsideLength(tickMarkLength);
					leftAxis.setTickMarkOutsideLength(tickMarkLength);
					leftAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					
					plot.setRenderer(0, barRenderer);
					plot.setRangeAxis(0, leftAxis);
					plot.setDataset(0, barChartDataset);
					plot.mapDatasetToRangeAxis(0, 0);
					
					// configure line chart
					final DefaultCategoryDataset lineChartDataset = new DefaultCategoryDataset();
					for ( int i = 0; i < categories.size(); i++ ) {
						lineChartDataset.addValue(baselineGenerationIndex.get(i), "Estimate Generation Index (%)", categories.get(i));
					}
					
					LineAndShapeRenderer lineAndShapeRenderer = new LineAndShapeRenderer();
					lineAndShapeRenderer.setSeriesPaint(0, Color.gray);
					lineAndShapeRenderer.setSeriesShape(0, ShapeUtils.createDiagonalCross(0, 2));
					
					NumberAxis rightAxis = new NumberAxis("PERFORMANCE INDEX (%)");
					rightAxis.setTickMarkInsideLength(tickMarkLength);
					rightAxis.setTickMarkOutsideLength(tickMarkLength);
					rightAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					
					plot.setRenderer(1, lineAndShapeRenderer);
					plot.setRangeAxis(1, rightAxis);
					plot.setDataset(1, lineChartDataset);
					plot.mapDatasetToRangeAxis(1, 1);
					
					// plot and return image
					JFreeChart chart = new JFreeChart(plot);
					chart.setBackgroundPaint(Color.white);
					chart.setTitle("Annual Performance");
					chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1650, 600), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 400));

					// Write the output to a file
					document.add(table);
					// It must be closed before attach to mail
					document.close();

				    String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
				    String msgTemplate = Constants.getMailTempleteByState(16);
				    String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "Annual ", "", "");
				    String mailTo = dataObj.getSubscribers();
				    String subject = Constants.getMailSubjectByState(16);
				    
				    String tags = "report_annually";
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
					mapItem.put("month", itemC.getMonth().toLowerCase());
					
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
		private static void writeHeaderQuarterlyReport(Sheet sheet, int rowIndex, ViewReportEntity dataObj) {
			try {				
				DecimalFormat df = new DecimalFormat("###,###");
				DecimalFormat dfp = new DecimalFormat("###,##0.0");
				DecimalFormat dfp4 = new DecimalFormat("###,##0.0000");
				boolean quarterlyReportByDay = dataObj.getData_intervals() == Constants.DAILY_INTERVAL;
				// create CellStyle
				CellStyle cellStyle = createStyleForHeader(sheet);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				
				// create CellStyle title
				CellStyle cellStyleTitle = createStyleForHeader(sheet);
				cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleTitle.setAlignment(HorizontalAlignment.LEFT);
				cellStyleTitle.setWrapText(true);
				
				cellStyleTitle.setBorderBottom(BorderStyle.THIN);
				cellStyleTitle.setBorderTop(BorderStyle.THIN);
				cellStyleTitle.setBorderRight(BorderStyle.THIN);
				cellStyleTitle.setBorderLeft(BorderStyle.THIN);
				cellStyleTitle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				cellStyleTitle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				cellStyleTitle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				cellStyleTitle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				
				
				// Create style 
				Font fontRowB = sheet.getWorkbook().createFont();
				fontRowB.setFontName("Times New Roman");
				fontRowB.setBold(true);
				fontRowB.setFontHeightInPoints((short) 12); // font size
				fontRowB.setColor(IndexedColors.BLACK.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleItemB = sheet.getWorkbook().createCellStyle();
				cellStyleItemB.setFont(fontRowB);
				cellStyleItemB.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				cellStyleItemB.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleItemB.setAlignment(HorizontalAlignment.LEFT);
				cellStyleItemB.setBorderBottom(BorderStyle.THIN);
				cellStyleItemB.setBorderTop(BorderStyle.THIN);
				cellStyleItemB.setBorderRight(BorderStyle.THIN);
				cellStyleItemB.setBorderLeft(BorderStyle.THIN);
				cellStyleItemB.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				cellStyleItemB.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				cellStyleItemB.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				cellStyleItemB.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				

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

				Row row1 = sheet.createRow(0);
				row1.setHeight((short) 600);
				Cell cell = row1.createCell(0);
				cell.setCellStyle(cellStyleItemB);
				cell.setCellValue("Site Name");

				cell = row1.createCell(1);
				cell.setCellStyle(cellStyleItemB);
				cell.setCellValue(dataObj.getSite_name());
				
				cell = row1.createCell(2);
				cell.setCellStyle(cellStyleItemB);
				cell.setCellValue("");

				// Create font
				Font font = sheet.getWorkbook().createFont();
				font.setFontName("Times New Roman");
				font.setBold(true);
				font.setFontHeightInPoints((short) 22); // font size
				font.setColor(IndexedColors.BLACK.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleCustom = sheet.getWorkbook().createCellStyle();
				cellStyleCustom.setFont(font);
				sheet.addMergedRegion(new CellRangeAddress(0, 5, 3, 10));
				
				cellStyleCustom.setFillForegroundColor(IndexedColors.WHITE.getIndex());
				cellStyleCustom.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleCustom.setAlignment(HorizontalAlignment.CENTER);
				cell = row1.createCell(3);
				cell.setCellStyle(cellStyleCustom);
				cell.setCellValue("QUARTERLY PRODUCTION REPORT - "+ dataObj.getQuarterly_month());

				// Create CellStyle image
				CellStyle cellStyleImage = sheet.getWorkbook().createCellStyle();
				sheet.addMergedRegion(new CellRangeAddress(0, 5, 11, 13));

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
				
				cellStyleCustom11.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleCustom11.setAlignment(HorizontalAlignment.CENTER);
				cellStyleCustom11.setWrapText(true);
				
				cellStyleCustom11.setBorderBottom(BorderStyle.THIN);
				cellStyleCustom11.setBorderTop(BorderStyle.THIN);
				cellStyleCustom11.setBorderRight(BorderStyle.THIN);
				cellStyleCustom11.setBorderLeft(BorderStyle.THIN);
				cellStyleCustom11.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleCustom11.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleCustom11.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleCustom11.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				

				Row row2 = sheet.createRow(1);
				row2.setHeight((short) 600);
				Cell cell2 = row2.createCell(0);
				cell2.setCellStyle(cellStyleItemB);
				cell2.setCellValue("Report Date");

				cell2 = row2.createCell(1);
				cell2.setCellStyle(cellStyleTitle);
				cell2.setCellValue(dataObj.getReport_date());
				
				cell2 = row2.createCell(2);
				cell2.setCellStyle(cellStyleTitle);
				cell2.setCellValue("");
				
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 2));

				Row row3 = sheet.createRow(2);
				row3.setHeight((short) 600);
				Cell cell3 = row3.createCell(0);
				cell3.setCellStyle(cellStyleItemB);
				cell3.setCellValue("Covered Period");

				cell3 = row3.createCell(1);
				cell3.setCellStyle(cellStyleTitle);
				cell3.setCellValue(dataObj.getStart_date() + " - " + dataObj.getEnd_date());
				
				cell3 = row3.createCell(2);
				cell3.setCellStyle(cellStyleTitle);
				cell3.setCellValue("");

				Row row4 = sheet.createRow(3);
				row4.setHeight((short) 600);
				Cell cell4 = row4.createCell(0);
				cell4.setCellStyle(cellStyleItemB);
				cell4.setCellValue("System Size (kW DC)");

				cell4 = row4.createCell(1);
				cell4.setCellStyle(cellStyleTitle);
				cell4.setCellValue(dataObj.getDc_capacity());
				
				cell4 = row4.createCell(2);
				cell4.setCellStyle(cellStyleTitle);
				cell4.setCellValue("");
				


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
				sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 2));
				cellStyle6.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
				cellStyle6.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				cellStyle6.setVerticalAlignment(VerticalAlignment.CENTER);

				Row row6 = sheet.createRow(6);
				Cell cell6 = row6.createCell(0);
				cell6.setCellStyle(cellStyle6);
				cell6.setCellValue("Performance Reporting");
				// Create font
				Font styleBorderTable = sheet.getWorkbook().createFont();
				styleBorderTable.setFontName("Times New Roman");
				styleBorderTable.setBold(true);
				styleBorderTable.setFontHeightInPoints((short) 12); // font size
				CellStyle cellStyleBorderTable = sheet.getWorkbook().createCellStyle();
				cellStyleBorderTable.setFont(styleBorderTable);
				cellStyleBorderTable.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleBorderTable.setAlignment(HorizontalAlignment.CENTER);
				
				
				cellStyleBorderTable.setBorderBottom(BorderStyle.THIN);
				cellStyleBorderTable.setBorderTop(BorderStyle.THIN);
				cellStyleBorderTable.setBorderRight(BorderStyle.THIN);
				cellStyleBorderTable.setBorderLeft(BorderStyle.THIN);
				
				cellStyleBorderTable.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleBorderTable.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleBorderTable.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleBorderTable.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				
				if (!quarterlyReportByDay) {
					sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 4));
					Row row22 = sheet.createRow(9);
					Cell cel22 = row22.createCell(0);
					cel22.setCellStyle(cellStyleBorderTable);
					cel22.setCellValue("Monthly kWh Production");
					
					Cell cel23 = row22.createCell(1);
					cel23.setCellStyle(cellStyleBorderTable);
					cel23.setCellValue("");
					
					Cell cel24 = row22.createCell(2);
					cel24.setCellStyle(cellStyleBorderTable);
					cel24.setCellValue("");
					
					Cell cel25 = row22.createCell(3);
					cel25.setCellStyle(cellStyleBorderTable);
					cel25.setCellValue("");
					
					Cell cel26 = row22.createCell(4);
					cel26.setCellStyle(cellStyleBorderTable);
					cel26.setCellValue("");
				}
				
				
				// Create font
				Font styleH = sheet.getWorkbook().createFont();
				styleH.setFontName("Times New Roman");
				styleH.setBold(true);
				styleH.setFontHeightInPoints((short) 12); // font size
				CellStyle cellStyleH = sheet.getWorkbook().createCellStyle();
				cellStyleH.setFont(styleH);
				cellStyleH.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleH.setAlignment(HorizontalAlignment.CENTER);
				cellStyleH.setWrapText(true);
				
				cellStyleH.setBorderBottom(BorderStyle.THIN);
				cellStyleH.setBorderTop(BorderStyle.THIN);
				cellStyleH.setBorderRight(BorderStyle.THIN);
				cellStyleH.setBorderLeft(BorderStyle.THIN);

				
				cellStyleH.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleH.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleH.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleH.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				
				// Create font
				Font styleYellow = sheet.getWorkbook().createFont();
				styleYellow.setFontName("Times New Roman");
				styleYellow.setBold(false);
				
				styleYellow.setFontHeightInPoints((short) 12); // font size
				CellStyle cellStyleYellow = sheet.getWorkbook().createCellStyle();
				cellStyleYellow.setFont(styleYellow);
				cellStyleYellow.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleYellow.setAlignment(HorizontalAlignment.CENTER);
				
				cellStyleYellow.setBorderBottom(BorderStyle.THIN);
				cellStyleYellow.setBorderTop(BorderStyle.THIN);
				cellStyleYellow.setBorderRight(BorderStyle.THIN);
				cellStyleYellow.setBorderLeft(BorderStyle.THIN);
				
				cellStyleYellow.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleYellow.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleYellow.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleYellow.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				
				// Create font
				Font styleYellowR = sheet.getWorkbook().createFont();
				styleYellowR.setFontName("Times New Roman");
				styleYellowR.setBold(false);
				
				styleYellowR.setFontHeightInPoints((short) 12); // font size
				CellStyle cellStyleYellowR = sheet.getWorkbook().createCellStyle();
				cellStyleYellowR.setFont(styleYellowR);
				styleYellowR.setColor(IndexedColors.RED.index);
				cellStyleYellowR.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleYellowR.setAlignment(HorizontalAlignment.CENTER);
				
				cellStyleYellowR.setBorderBottom(BorderStyle.THIN);
				cellStyleYellowR.setBorderTop(BorderStyle.THIN);
				cellStyleYellowR.setBorderRight(BorderStyle.THIN);
				cellStyleYellowR.setBorderLeft(BorderStyle.THIN);
				
				cellStyleYellowR.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleYellowR.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleYellowR.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleYellowR.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				
				// Create font
				Font styleBrown = sheet.getWorkbook().createFont();
				styleBrown.setFontName("Times New Roman");
				styleBrown.setBold(false);
				styleBrown.setFontHeightInPoints((short) 12); // font size
				CellStyle cellStyleBrown = sheet.getWorkbook().createCellStyle();
				cellStyleBrown.setFont(styleBrown);
				cellStyleBrown.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleBrown.setAlignment(HorizontalAlignment.CENTER);
				
				cellStyleBrown.setFillBackgroundColor(IndexedColors.PALE_BLUE.index);
				cellStyleBrown.setFillPattern(FillPatternType.BIG_SPOTS);
				cellStyleBrown.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
				
				cellStyleBrown.setBorderBottom(BorderStyle.THIN);
				cellStyleBrown.setBorderTop(BorderStyle.THIN);
				cellStyleBrown.setBorderRight(BorderStyle.THIN);
				cellStyleBrown.setBorderLeft(BorderStyle.THIN);
				
				cellStyleBrown.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleBrown.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleBrown.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleBrown.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				Row row28 = sheet.createRow(quarterlyReportByDay ? 47 : 10);
				
				if (!quarterlyReportByDay) {
					row28.setHeight((short) 800);
					
					Cell cel280 = row28.createCell(0);
					cel280.setCellStyle(cellStyleH);
					cel280.setCellValue("");
					
					Cell cel28 = row28.createCell(1);
					cel28.setCellStyle(cellStyleH);
					cel28.setCellValue("Estimated Generation (kWh)");
					
					Cell cel29 = row28.createCell(2);
					cel29.setCellStyle(cellStyleH);
					cel29.setCellValue("Actual Generation (kWh)");
					
					Cell cel30 = row28.createCell(3);
					cel30.setCellStyle(cellStyleH);
					cel30.setCellValue("Difference (kWh)");
					
					Cell cel31 = row28.createCell(4);
					cel31.setCellStyle(cellStyleH);
					cel31.setCellValue("Difference (%)");
				} else {
					Cell cel280 = row28.createCell(0);
					cel280.setCellStyle(cellStyleH);
					cel280.setCellValue("Date");
					
					sheet.addMergedRegion(new CellRangeAddress(47, 47, 1, 2));
					Cell cel28 = row28.createCell(1);
					cel28.setCellStyle(cellStyleH);
					cel28.setCellValue("Daily System Production (kWh)");
					cel28 = row28.createCell(2);
					cel28.setCellStyle(cellStyleH);
					cel28.setCellValue("");
					
					sheet.addMergedRegion(new CellRangeAddress(47, 47, 3, 4));
					Cell cel29 = row28.createCell(3);
					cel29.setCellStyle(cellStyleH);
					cel29.setCellValue("Daily POA (W/m²)");
					cel29 = row28.createCell(4);
					cel29.setCellStyle(cellStyleH);
					cel29.setCellValue("");
					
					sheet.addMergedRegion(new CellRangeAddress(47, 47, 5, 6));
					Cell cel30 = row28.createCell(5);
					cel30.setCellStyle(cellStyleH);
					cel30.setCellValue("Daily POA Insolation (kWh/m²)");
					cel30 = row28.createCell(6);
					cel30.setCellStyle(cellStyleH);
					cel30.setCellValue("");
					
					sheet.addMergedRegion(new CellRangeAddress(47, 47, 7, 8));
					Cell cel31 = row28.createCell(7);
					cel31.setCellStyle(cellStyleH);
					cel31.setCellValue("TCell (°C)");
					cel31 = row28.createCell(8);
					cel31.setCellStyle(cellStyleH);
					cel31.setCellValue("");
					
					sheet.addMergedRegion(new CellRangeAddress(47, 47, 9, 10));
					Cell cel32 = row28.createCell(9);
					cel32.setCellStyle(cellStyleH);
					cel32.setCellValue("Temperature Corrected PR (%)");
					cel32 = row28.createCell(10);
					cel32.setCellStyle(cellStyleH);
					cel32.setCellValue("");
					
					sheet.addMergedRegion(new CellRangeAddress(47, 47, 11, 12));
					Cell cel33 = row28.createCell(11);
					cel33.setCellStyle(cellStyleH);
					cel33.setCellValue("Inverter Availability (%)");
					cel33 = row28.createCell(12);
					cel33.setCellStyle(cellStyleH);
					cel33.setCellValue("");
					
					sheet.groupRow(8, 46);
					sheet.setRowGroupCollapsed(29, true);
					sheet.groupColumn(3, 4);
					sheet.setColumnGroupCollapsed(3, true);
				}
				
				Double totalBaseline = (double) 0;
				Double totalActual = (double) 0;
				
				List<?> dataExports = dataObj.getDataReports();
				List<?> dataWeatherStation = dataObj.getDataWeatherStation();
				List<?> dataInverterAvailability = dataObj.getDataAvailability();
				if(dataExports.size() > 0) {
					for(int i = 0; i < dataExports.size(); i++ ) {
						QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
						totalBaseline = item.getEstimated() != null ? totalBaseline + item.getEstimated() : totalBaseline;
						totalActual = item.getActual() != null ? totalActual + item.getActual() : totalActual;
						if (!quarterlyReportByDay) {
							// start ----------------------------
							Row row32 = sheet.createRow(11 + i);
							Cell cel32 = row32.createCell(0);
							cel32.setCellStyle(cellStyleH);
							cel32.setCellValue(item.getCategories_time());
							
							Cell cel34 = row32.createCell(1);
							cel34.setCellStyle(cellStyleYellow);
							cel34.setCellValue(item.getEstimated() != null ? df.format(item.getEstimated()) : "");
							
							Cell cel35 = row32.createCell(2);
							cel35.setCellStyle(cellStyleBrown);
							cel35.setCellValue(item.getActual() != null ? df.format(item.getActual()) : "");
	
							Cell cel36 = row32.createCell(3);
							if (item.getActual() != null && item.getEstimated() != null) {
								double difference = item.getActual() - item.getEstimated();
								cel36.setCellStyle(difference < 0 ? cellStyleYellowR : cellStyleYellow);
								cel36.setCellValue(df.format(difference));
							} else {
								cel36.setCellStyle(cellStyleYellow);
								cel36.setCellValue("");
							}
							
							Cell cel37 = row32.createCell(4);
							if (item.getActual() != null && item.getEstimated() != null) {
								double differencePercent = ((item.getActual() - item.getEstimated()) / item.getEstimated()) * 100;
								differencePercent = differencePercent < 0 && differencePercent > -0.4 ? 0 : differencePercent;
								cel37.setCellStyle(differencePercent < 0 ? cellStyleYellowR : cellStyleYellow);
								cel37.setCellValue(dfp.format(differencePercent));
							} else {
								cel37.setCellStyle(cellStyleYellow);
								cel37.setCellValue("");
							}
							
							
							// end ----------------------------
							
							// start ----------------------------
							Row row321 = sheet.createRow(28 + i);
							Cell cel321 = row321.createCell(0);
							cel321.setCellStyle(cellStyleH);
							cel321.setCellValue(item.getCategories_time());
							
							Cell cel343 = row321.createCell(1);
							cel343.setCellStyle(cellStyleYellow);
							cel343.setCellValue(item.getEstimated() != null ? df.format(totalBaseline) : "");
							
							Cell cel354 = row321.createCell(2);
							cel354.setCellStyle(cellStyleBrown);
							cel354.setCellValue(item.getActual() != null ? df.format(totalActual) : "");
							
							Cell cel365 = row321.createCell(3);
							cel365.setCellStyle((totalActual - totalBaseline) < 0 ? cellStyleYellowR : cellStyleYellow);
							cel365.setCellValue(item.getActual() != null & item.getEstimated() != null ? df.format(totalActual - totalBaseline) : "");
							
							Cell cel376 = row321.createCell(4);
							if (item.getActual() != null & item.getEstimated() != null) {
								double percent = ((totalActual - totalBaseline) / totalBaseline) * 100;
								percent = percent < 0 && percent > -0.4 ? 0: percent;
								cel376.setCellStyle(percent < 0 ? cellStyleYellowR : cellStyleYellow);
								cel376.setCellValue(dfp.format(percent));
							} else {
								cel376.setCellStyle(cellStyleYellow);
								cel376.setCellValue("");
							}
							
							
							// end ----------------------------
							
						} else {
							QuarterlyDateEntity itemWeatherStation = dataWeatherStation != null ? (QuarterlyDateEntity) dataWeatherStation.get(i) : null;
							QuarterlyDateEntity itemInverterAvailabilty = dataInverterAvailability != null ? (QuarterlyDateEntity) dataInverterAvailability.get(i) : null;
							
							Row row32 = sheet.createRow(48 + i);
							Cell cel32 = row32.createCell(0);
							cel32.setCellStyle(cellStyleH);
							cel32.setCellValue(item.getCategories_time());

							sheet.addMergedRegion(new CellRangeAddress(48 + i, 48 + i, 1, 2));
							Cell cel33 = row32.createCell(1);
							cel33.setCellStyle(cellStyleYellow);
							cel33.setCellValue(item.getActual() != null ? df.format(item.getActual()) : "");
							cel33 = row32.createCell(2);
							cel33.setCellStyle(cellStyleYellow);
							cel33.setCellValue("");
							
							sheet.addMergedRegion(new CellRangeAddress(48 + i, 48 + i, 3, 4));
							Cell cel34 = row32.createCell(3);
							cel34.setCellStyle(cellStyleYellow);
							cel34.setCellValue(dataWeatherStation != null && itemWeatherStation.getPOAAVG() != null ? df.format(itemWeatherStation.getPOAAVG()) : "");
							cel34 = row32.createCell(4);
							cel34.setCellStyle(cellStyleYellow);
							cel34.setCellValue("");
							
							sheet.addMergedRegion(new CellRangeAddress(48 + i, 48 + i, 5, 6));
							Cell cel35 = row32.createCell(5);
							cel35.setCellStyle(cellStyleYellow);
							cel35.setCellValue(dataWeatherStation != null && itemWeatherStation.getPOAAVG() != null ? dfp4.format(itemWeatherStation.getPOAAVG() * 24 /1000) : "");
							cel35 = row32.createCell(6);
							cel35.setCellStyle(cellStyleYellow);
							cel35.setCellValue("");
							
							sheet.addMergedRegion(new CellRangeAddress(48 + i, 48 + i, 7, 8));
							Cell cel36 = row32.createCell(7);
							cel36.setCellStyle(cellStyleYellow);
							cel36.setCellValue(dataWeatherStation != null && itemWeatherStation.getTCellAVG() != null ? dfp.format(itemWeatherStation.getTCellAVG()) : "");
							cel36 = row32.createCell(8);
							cel36.setCellStyle(cellStyleYellow);
							cel36.setCellValue("");
							
							sheet.addMergedRegion(new CellRangeAddress(48 + i, 48 + i, 9, 10));
							Cell cel37 = row32.createCell(9);
							cel37.setCellStyle(cellStyleYellow);
							cel37.setCellValue(dataWeatherStation != null && item.getActual() != null && itemWeatherStation.getPOAAVG() != null && itemWeatherStation.getTCellAVG() != null && itemWeatherStation.getPOAAVG() > 0 ? dfp.format(item.getActual() / ((dataObj.getDc_capacity() * itemWeatherStation.getPOAAVG() * 24 / 1000) * (1 - (-0.47 / 100) * (25 - itemWeatherStation.getTCellAVG()))) * 100) : "");
							cel37 = row32.createCell(10);
							cel37.setCellStyle(cellStyleYellow);
							cel37.setCellValue("");
							
							sheet.addMergedRegion(new CellRangeAddress(48 + i, 48 + i, 11, 12));
							Cell cel38 = row32.createCell(11);
							cel38.setCellStyle(cellStyleYellow);
							cel38.setCellValue(dataInverterAvailability != null && itemInverterAvailabilty.getInverterAvailability() != null ? dfp.format(itemInverterAvailabilty.getInverterAvailability()) : "");
							cel38 = row32.createCell(12);
							cel38.setCellStyle(cellStyleYellow);
							cel38.setCellValue("");
						}
					}
				}
				
				// collapse rows that use for second chart in case this chart is hidden
//				if (dataWeatherStation == null) {
//					sheet.groupRow(29, 46);
//					sheet.setRowGroupCollapsed(29, true);
//				}				

				// Create font
				Font styleTotal = sheet.getWorkbook().createFont();
				styleTotal.setFontName("Times New Roman");
				styleTotal.setBold(true);
				styleTotal.setFontHeightInPoints((short) 12); // font size
				CellStyle cellStyleTotal = sheet.getWorkbook().createCellStyle();
				cellStyleTotal.setFont(styleTotal);
				cellStyleTotal.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleTotal.setAlignment(HorizontalAlignment.CENTER);
				
				cellStyleTotal.setBorderTop(BorderStyle.MEDIUM);
				cellStyleTotal.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				if (!quarterlyReportByDay) {
					Row row32T = sheet.createRow(22);
					Cell cel32T = row32T.createCell(0);
					cel32T.setCellStyle(cellStyleTotal);
					cel32T.setCellValue("Total");
					
					
					Cell cel34T = row32T.createCell(1);
					cel34T.setCellStyle(cellStyleTotal);
					cel34T.setCellValue(df.format(totalBaseline));
					
					Cell cel35T = row32T.createCell(2);
					cel35T.setCellStyle(cellStyleTotal);
					cel35T.setCellValue(df.format(totalActual));
					
					Cell cel36T = row32T.createCell(3);
					cel36T.setCellStyle(cellStyleTotal);
					cel36T.setCellValue(df.format(totalActual - totalBaseline));
					
					Cell cel37T = row32T.createCell(4);
					cel37T.setCellStyle(cellStyleTotal);
					cel37T.setCellValue(dfp.format( ((totalActual - totalBaseline) / totalBaseline ) * 100 ));
					
					
					sheet.addMergedRegion(new CellRangeAddress(26, 26, 0, 4));
					Row row0 = sheet.createRow(26);
					Cell cel0 = row0.createCell(0);
					cel0.setCellStyle(cellStyleBorderTable);
					cel0.setCellValue("Cumulative kWh Production");
					
					Cell celN1 = row0.createCell(1);
					celN1.setCellStyle(cellStyleBorderTable);
					celN1.setCellValue("");
					
					Cell celN3 = row0.createCell(2);
					celN3.setCellStyle(cellStyleBorderTable);
					celN3.setCellValue("");
					
					Cell celN4 = row0.createCell(3);
					celN4.setCellStyle(cellStyleBorderTable);
					celN4.setCellValue("");
					
					Cell celN5 = row0.createCell(4);
					celN5.setCellStyle(cellStyleBorderTable);
					celN5.setCellValue("");
				
					
					Row row01 = sheet.createRow(27);
					row01.setHeight((short) 800);
					Cell cel01 = row01.createCell(1);
					cel01.setCellStyle(cellStyleH);
					cel01.setCellValue("Estimated Generation (kWh)");
					
					Cell cel02 = row01.createCell(2);
					cel02.setCellStyle(cellStyleH);
					cel02.setCellValue("Actual Generation (kWh)");
					
					Cell cel03 = row01.createCell(3);
					cel03.setCellStyle(cellStyleH);
					cel03.setCellValue("Difference (kWh)");
					
					Cell cel04 = row01.createCell(4);
					cel04.setCellStyle(cellStyleH);
					cel04.setCellValue("Difference (%)");
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
		try {
			try (XSSFWorkbook document = new XSSFWorkbook()) {
				ReportsService service = new ReportsService();
				ViewReportEntity dataObj = (ViewReportEntity) service.getQuarterlyReport(obj);
				if (dataObj != null) {
					boolean quarterlyReportByDay = dataObj.getData_intervals() == Constants.DAILY_INTERVAL;

					SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date startDate = dateFormat.parse(obj.getStart_date());
					Date endDate = dateFormat.parse(obj.getEnd_date());
					dataObj.setStart_date( new SimpleDateFormat("MM/dd/yyyy").format(startDate) );
					dataObj.setEnd_date( new SimpleDateFormat("MM/dd/yyyy").format(endDate) );
					
					Calendar calQ = Calendar.getInstance();
					calQ.setTime(startDate);
					int month = calQ.get(Calendar.MONTH);
					int  quarter = (int) (Math.floor(month /3)+1); 
					dataObj.setQuarterly_month("Q"+ quarter + "/" + yearFormat.format(calQ.getTime())); 

					List dataExports = dataObj.getDataReports();
					List dataWeatherStation = dataObj.getDataWeatherStation();
					List dataInverterAvailability = dataObj.getDataAvailability();
					
					XSSFSheet chartSheet = document.createSheet("Quarterly Production Report");
					XSSFSheet dataSheet = null;
					XSSFSheet dataSheetCumulative = null;
					
					if (!quarterlyReportByDay) {
						dataSheet = document.createSheet("data");
						dataSheetCumulative = document.createSheet("cumulative");
					}
					
					// FileInputStream obtains input bytes from the image file
					InputStream inputStreamImage = new FileInputStream(uploadRootPath() + "/reports/logo-report.jpg" );
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
					anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
					// set top-left corner for the image
					anchor.setCol1(11);
					anchor.setRow1(1);
					// anchor.setCol2(3);
					// anchor.setRow2(5);

					// Creates a picture
					Picture pict = drawing.createPicture(anchor, pictureIdx);
					// Reset the image to the original size
					pict.resize(1.45, 4.2);

					writeHeaderQuarterlyReport(chartSheet, 0, dataObj);
					// create the data
					double totalBaseline = 0;
					double totalActual = 0;
					
					for (int i = 0; i < dataExports.size(); i++ ) {
						QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
						
						if (!quarterlyReportByDay) {
							dataSheet.createRow(i).createCell(0).setCellValue(item.getCategories_time());
							dataSheet.getRow(i).createCell(1).setCellValue(item.getEstimated() != null ? item.getEstimated() : 0);
							dataSheet.getRow(i).createCell(2).setCellValue(item.getActual() != null ? item.getActual() : 0);
							
							dataSheetCumulative.createRow(i).createCell(0).setCellValue(item.getCategories_time());
							totalBaseline = item.getEstimated() != null ? totalBaseline + item.getEstimated() : totalBaseline;
							dataSheetCumulative.getRow(i).createCell(1).setCellValue(totalBaseline);
							totalActual = item.getActual() != null ? totalActual + item.getActual() : totalActual;
							dataSheetCumulative.getRow(i).createCell(2).setCellValue(totalActual);
						} else {
//							if (item.getActual() != null) {
//								dataSheet.getRow(i).createCell(1).setCellValue(item.getActual());
//							}
//							if (dataWeatherStation != null) {
//								QuarterlyDateEntity itemWeatherStation = (QuarterlyDateEntity) dataWeatherStation.get(i);
//								
//								if (itemWeatherStation.getPOAAVG() != null) {
//									dataSheet.getRow(i).createCell(2).setCellValue(itemWeatherStation.getPOAAVG());
//								}
//								if (itemWeatherStation.getPOAAVG() != null) {
//									dataSheet.getRow(i).createCell(3).setCellValue(itemWeatherStation.getPOAAVG() * 24 /1000);
//								}
//								if (itemWeatherStation.getTCellAVG() != null) {
//									dataSheet.getRow(i).createCell(4).setCellValue(itemWeatherStation.getTCellAVG());
//								}
//								if (item.getActual() != null && itemWeatherStation.getPOAAVG() != null && itemWeatherStation.getTCellAVG() != null && itemWeatherStation.getPOAAVG() > 0) {
//									dataSheet.getRow(i).createCell(5).setCellValue(item.getActual() / ((dataObj.getDc_capacity() * itemWeatherStation.getPOAAVG() * 24 / 1000) * (1 - (-0.47 / 100) * (25 - itemWeatherStation.getTCellAVG()))) * 100);
//								}
//							}
//							if (dataInverterAvailability != null) {
//								QuarterlyDateEntity itemInverterAvailability = (QuarterlyDateEntity) dataInverterAvailability.get(i);
//								
//								if (itemInverterAvailability.getInverterAvailability() != null) {
//									dataSheet.getRow(i).createCell(6).setCellValue(itemInverterAvailability.getInverterAvailability());
//								}
//							}
						}
					}

					
					XSSFClientAnchor anchor1;
					XDDFChart chart;
				    XDDFChartLegend legend;
				    XDDFCategoryAxis bottomAxis;
				    XDDFValueAxis leftAxis;
					// create the chart 1
					XSSFDrawing drawing1 = chartSheet.createDrawingPatriarch();
					
					if (!quarterlyReportByDay) {
						XDDFChartData data;
						XDDFChartData.Series series;
						
						//====== first line chart============================================================
						anchor1 = drawing1.createAnchor(6, 6, 6, 6, 6, 9, 13, 23);
						chart = drawing1.createChart(anchor1);
						chart.setTitleText("");
						chart.setTitleOverlay(false);
	
						// create data sources
						int numOfPoints = dataExports.size();
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
						
						for (int i = 0; i < numOfPoints; i++) {
							XSSFRow row = dataSheet.getRow(i);
							if (row == null)
								row = dataSheet.createRow(i);
							XSSFCell cell = row.createCell(255);
							cell.setCellValue(0);
						}
	
						// data source for the pad series
						XDDFNumericalDataSource<Double> pad = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
								new CellRangeAddress(0, numOfPoints - 1, 255, 255));
	
						// first bar chart
						bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
						bottomAxis.setMajorTickMark(AxisTickMark.NONE);
						if (bottomAxis.hasNumberFormat()) bottomAxis.setNumberFormat("@");
						
						leftAxis = chart.createValueAxis(AxisPosition.LEFT);
						leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
						leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
						leftAxis.setTitle("");
						leftAxis.setMajorTickMark(AxisTickMark.NONE);
						if (leftAxis.hasNumberFormat()) leftAxis.setNumberFormat("#,##0.00");
	
						data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
						XDDFBarChartData bar = (XDDFBarChartData) data;
						bar.setBarDirection(BarDirection.COL);
						bar.setGapWidth(400);
						
	
						CTPlotArea plotArea = chart.getCTChart().getPlotArea();
						plotArea.getValAxArray()[0].addNewMajorGridlines();
	
						series = data.addSeries(categoriesData, valuesData1);
						series.setTitle("Estimated Generation (kWh)",
								new CellReference(chartSheet.getSheetName(), 10, 1, true, true));
						
						series = data.addSeries(categoriesData, valuesData2);
						series.setTitle("Actual Generation (kWh)",
								new CellReference(chartSheet.getSheetName(), 10, 2, true, true));
						chart.plot(data);
	
						// set bar colors
						solidFillSeries(data, 0, PresetColor.STEEL_BLUE);
						solidFillSeries(data, 1, PresetColor.LIGHT_SKY_BLUE);
						// set legend
						legend = chart.getOrAddLegend();
						legend.setPosition(LegendPosition.BOTTOM);
					
					
						//======second line chart============================================================
						anchor = drawing1.createAnchor(6, 6, 6, 6, 6, 26, 13, 39);
						chart = drawing1.createChart(anchor);
						chart.setTitleText("");
						chart.setTitleOverlay(false);
					    
					    // create the axes
					    bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
					    bottomAxis.setMajorTickMark(AxisTickMark.NONE);
					    if (bottomAxis.hasNumberFormat()) bottomAxis.setNumberFormat("@");
					    
					    leftAxis = chart.createValueAxis(AxisPosition.LEFT);
					    leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
					    leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
						leftAxis.setTitle("");
						leftAxis.setMajorTickMark(AxisTickMark.NONE);
						if (leftAxis.hasNumberFormat()) leftAxis.setNumberFormat("#,##0.00");
					    
					   // create chart data
					    data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
					    
	
						// create data sources
						int numOfPoints2 = dataExports.size();
						// dummy 0-values for the pad data source
						Double[] dummyValuesForPad2 = new Double[numOfPoints2];
						for (int i = 0; i < numOfPoints2; i++) {
							dummyValuesForPad2[i] = 0d;
						}
						
						XDDFDataSource<String> categoriesData2 = XDDFDataSourcesFactory.fromStringCellRange(dataSheetCumulative,
								new CellRangeAddress(0, numOfPoints2 - 1, 0, 0));
						XDDFNumericalDataSource<Double> valuesData12 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheetCumulative,
								new CellRangeAddress(0, numOfPoints2 - 1, 1, 1));
						XDDFNumericalDataSource<Double> valuesData22 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheetCumulative,
								new CellRangeAddress(0, numOfPoints2 - 1, 2, 2));
						
						for (int i = 0; i < numOfPoints2; i++) {
							XSSFRow row2 = dataSheetCumulative.getRow(i);
							if (row2 == null)
								row2 = dataSheetCumulative.createRow(i);
							XSSFCell cell2 = row2.createCell(255);
							cell2.setCellValue(0);
						}
	
						XDDFBarChartData bar2 = (XDDFBarChartData) data;
						bar2.setBarDirection(BarDirection.COL);
						bar2.setGapWidth(400);
	
						CTPlotArea plotArea2 = chart.getCTChart().getPlotArea();
						plotArea2.getValAxArray()[0].addNewMajorGridlines();
	
						series = data.addSeries(categoriesData2, valuesData12);
						series.setTitle("Estimated Generation (kWh)",
								new CellReference(chartSheet.getSheetName(), 10, 1, true, true));
						
						series = data.addSeries(categoriesData2, valuesData22);
						series.setTitle("Actual Generation (kWh)",
								new CellReference(chartSheet.getSheetName(), 10, 2, true, true));
						// additional pad series - takes space at right side for primary axis
						chart.plot(data);
	
						// set bar colors					
						solidFillSeries(data, 0, PresetColor.STEEL_BLUE);
						solidFillSeries(data, 1, PresetColor.LIGHT_SKY_BLUE);
	
						// set legend
						legend = chart.getOrAddLegend();
					    legend.setPosition(LegendPosition.BOTTOM);
					} else {
//						XDDFLineChartData data;
//						XDDFLineChartData.Series series;
//						
//						//====== first line chart============================================================
//						anchor1 = drawing1.createAnchor(0, 0, 0, 0, 0, 9, 13, 26);
//						chart = drawing1.createChart(anchor1);
//						chart.setTitleText("");
//						chart.setTitleOverlay(false);
//
//						// create data sources
//						int numOfPoints = dataExports.size();
//						// dummy 0-values for the pad data source
//						Double[] dummyValuesForPad = new Double[numOfPoints];
//						for (int i = 0; i < numOfPoints; i++) {
//							dummyValuesForPad[i] = 0d;
//						}
//						XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(dataSheet,
//								new CellRangeAddress(0, numOfPoints - 1, 0, 0));
//						XDDFNumericalDataSource<Double> valuesData1 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
//								new CellRangeAddress(0, numOfPoints - 1, 1, 1));
//						XDDFNumericalDataSource<Double> valuesData2 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
//								new CellRangeAddress(0, numOfPoints - 1, 2, 2));
//						XDDFNumericalDataSource<Double> valuesData3 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
//								new CellRangeAddress(0, numOfPoints - 1, 3, 3));
//						XDDFNumericalDataSource<Double> valuesData4 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
//								new CellRangeAddress(0, numOfPoints - 1, 4, 4));
//						XDDFNumericalDataSource<Double> valuesData5 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
//								new CellRangeAddress(0, numOfPoints - 1, 5, 5));
//						XDDFNumericalDataSource<Double> valuesData6 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
//								new CellRangeAddress(0, numOfPoints - 1, 6, 6));
//						
//						for (int i = 0; i < numOfPoints; i++) {
//							XSSFRow row = dataSheet.getRow(i);
//							if (row == null)
//								row = dataSheet.createRow(i);
//							XSSFCell cell = row.createCell(255);
//							cell.setCellValue(0);
//						}
//						
//						// create axis
//						bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
//						
//						// first series
//						leftAxis = chart.createValueAxis(AxisPosition.LEFT);
//						leftAxis.setTitle("kWh");
//						leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
//						leftAxis.setCrossBetween(AxisCrossBetween.MIDPOINT_CATEGORY);
//			
//						// create data and series
//						data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
//						data.setVaryColors(false);
//						series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData1);
//						
//						series.setTitle("Daily System Production (kWh)", new CellReference(chartSheet.getSheetName(), 47, 1, true, true));
//						series.setSmooth(true);
//						series.setMarkerStyle(MarkerStyle.NONE);
//						
//						chart.plot(data);
//						solidLineSeries(data, 0, PresetColor.STEEL_BLUE);
//						
//						// second series
//						if (dataWeatherStation != null) {
//							XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
//							rightAxis.setTitle("W/m²");
//							rightAxis.setCrosses(AxisCrosses.MAX);
//							rightAxis.setCrossBetween(AxisCrossBetween.MIDPOINT_CATEGORY);
//							
//							// create data and series
//							data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
//							data.setVaryColors(false);
//							series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData2);
//							series.setTitle("Daily POA (W/m²)", new CellReference(chartSheet.getSheetName(), 47, 3, true, true));
//							series.setSmooth(true);
//							series.setMarkerStyle(MarkerStyle.NONE);
//							
//							chart.plot(data);
//							solidLineSeries(data, 0, PresetColor.LIGHT_STEEL_BLUE);
//						}
//						
//						
//						// third series
//						if (dataWeatherStation != null) {
//							XDDFValueAxis rightAxis1 = chart.createValueAxis(AxisPosition.RIGHT);
//							rightAxis1.setTitle("kWh/m²");
//							rightAxis1.setCrosses(AxisCrosses.MAX);
//							rightAxis1.setCrossBetween(AxisCrossBetween.MIDPOINT_CATEGORY);
//							
//							// create data and series
//							data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis1);
//							data.setVaryColors(false);
//							series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData3);
//							series.setTitle("Daily POA Insolation (kWh/m²)	", new CellReference(chartSheet.getSheetName(), 47, 5, true, true));
//							series.setSmooth(true);
//							series.setMarkerStyle(MarkerStyle.NONE);
//							
//							chart.plot(data);
//							solidLineSeries(data, 0, PresetColor.ORANGE);
//						}
//						
//						CTPlotArea plotArea = chart.getCTChart().getPlotArea();
//						plotArea.getValAxArray()[0].addNewMajorGridlines().addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[] {(byte) 240, (byte) 240, (byte) 240});
//						plotArea.getCatAxArray(0).addNewTickLblSkip().setVal(2);
//					    CTDispBlanksAs disp = CTDispBlanksAs.Factory.newInstance();
//					    disp.setVal(STDispBlanksAs.GAP);
//						chart.getCTChart().setDispBlanksAs(disp);
//
//						
//						// create legend
//						legend = chart.getOrAddLegend();
//						legend.setPosition(LegendPosition.BOTTOM);
//						legend.setOverlay(false);
//						
//			
//						//====== second line chart============================================================
//						if (dataWeatherStation != null) {
//							anchor1 = drawing1.createAnchor(0, 0, 0, 0, 0, 27, 13, 44);
//							chart = drawing1.createChart(anchor1);
//							// create axis
//							bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
//						}
//						
//						// fourth series
//						if (dataWeatherStation != null) {
//							XDDFValueAxis leftAxis1 = chart.createValueAxis(AxisPosition.LEFT);
//							leftAxis1.setTitle("°C");
//							leftAxis1.setCrosses(AxisCrosses.AUTO_ZERO);
//							leftAxis1.setCrossBetween(AxisCrossBetween.MIDPOINT_CATEGORY);
//						
//							// create data and series
//							data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis1);
//							data.setVaryColors(false);
//							series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData4);
//							series.setTitle("TCell (°C)", new CellReference(chartSheet.getSheetName(), 47, 7, true, true));
//							series.setSmooth(true);
//							series.setMarkerStyle(MarkerStyle.NONE);
//							
//							chart.plot(data);
//							solidLineSeries(data, 0, PresetColor.SADDLE_BROWN);
//						}
//						
//						
//						// fifth series
//						XDDFValueAxis rightAxis2;
//						rightAxis2 = chart.createValueAxis(AxisPosition.RIGHT);
//						rightAxis2.setTitle("%");
//						rightAxis2.setCrosses(AxisCrosses.MAX);
//						rightAxis2.setCrossBetween(AxisCrossBetween.MIDPOINT_CATEGORY);
//						
//						if (dataWeatherStation != null) {
//							// create data and series
//							data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis2);
//							data.setVaryColors(false);
//							series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData5);
//							series.setTitle("Temperature Corrected PR (%)", new CellReference(chartSheet.getSheetName(), 47, 9, true, true));
//							series.setSmooth(true);
//							series.setMarkerStyle(MarkerStyle.NONE);
//	
//							chart.plot(data);
//							solidLineSeries(data, 0, PresetColor.GREEN);
//						}
//						
//						
//						// sixth series
//						if (dataInverterAvailability != null) { 
//							// create data and series
//							data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis2);
//							data.setVaryColors(false);
//							series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData6);
//							series.setTitle("Inverter Availability (%)", new CellReference(chartSheet.getSheetName(), 47, 11, true, true));
//							series.setSmooth(true);
//							series.setMarkerStyle(MarkerStyle.NONE);
//							
//							chart.plot(data);
//							solidLineSeries(data, 0, PresetColor.DARK_ORANGE);
//						}
//						
//						if (dataWeatherStation != null) {
//							chart.setTitleText("");
//							chart.setTitleOverlay(false);
//							
//							plotArea = chart.getCTChart().getPlotArea();
//							plotArea.getValAxArray()[0].addNewMajorGridlines().addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[] {(byte) 240, (byte) 240, (byte) 240});
//							plotArea.getCatAxArray(0).addNewTickLblSkip().setVal(2);
//							disp = CTDispBlanksAs.Factory.newInstance();
//							disp.setVal(STDispBlanksAs.GAP);
//							chart.getCTChart().setDispBlanksAs(disp);
//							
//							// create legend
//							legend = chart.getOrAddLegend();
//							legend.setPosition(LegendPosition.BOTTOM);
//							legend.setOverlay(false);
//						}
					}
					
					// Write the output to a file
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String dir = uploadRootPath() + "/"
							+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
					String fileName = dir + "/Quarterly-report-" + timeStamp + ".xlsx";
					
					try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
						document.write(fileOut);
						String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
								Constants.mailFromContact);

						String msgTemplate = Constants.getMailTempleteByState(16);
						String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "Quarterly ", "", "");
						String mailTo = dataObj.getSubscribers();
						String subject = Constants.getMailSubjectByState(16);

						String tags = "report_quarterly";
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
	 * @description sent mail quarterly report in pdf
	 * @author Hung.Bui
	 * @since 2022-12-05
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-pdf-quarterly-report")
	public Object sentMailPdfQuarterlyReport(@RequestBody ViewReportEntity obj) {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
			String dir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
			String fileName = dir + "/Quarterly-report-" + timeStamp + ".pdf";
			File file = new File(fileName);
			
			try (
					PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
					Document document = new Document(pdfDocument, PageSize.A3.rotate());
					) {
				
				ReportsService service = new ReportsService();
				ViewReportEntity dataObj = (ViewReportEntity) service.getQuarterlyReport(obj);
				
				if (dataObj != null) {
					boolean quarterlyReportByDay = dataObj.getData_intervals() == Constants.DAILY_INTERVAL;

					SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date startDate = dateFormat.parse(obj.getStart_date());
					Date endDate = dateFormat.parse(obj.getEnd_date());
					dataObj.setStart_date( new SimpleDateFormat("MM/dd/yyyy").format(startDate) );
					dataObj.setEnd_date( new SimpleDateFormat("MM/dd/yyyy").format(endDate) );
					
					Calendar calQ = Calendar.getInstance();
					calQ.setTime(startDate);
					int month = calQ.get(Calendar.MONTH);
					int  quarter = (int) (Math.floor(month /3)+1); 
					dataObj.setQuarterly_month("Q"+ quarter + "/" + yearFormat.format(calQ.getTime())); 
					
					List dataExports = dataObj.getDataReports();
					List dataWeatherStation = dataObj.getDataWeatherStation();
					List dataInverterAvailability = dataObj.getDataAvailability();
					double totalBaseline = 0;
					double totalActual = 0;
					
					// total column: 13
					final float[] columnWidths = {2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1};

					Table table = new Table(quarterlyReportByDay ? UnitValue.createPercentArray(13) : UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
					table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
					table.setFontSize(8);
					table.setTextAlignment(TextAlignment.CENTER);
					table.setMarginTop(quarterlyReportByDay ? 0 : 75); // align table in middle of page
					
					Image logoImage = new Image(ImageDataFactory.create(uploadRootPath() + "/reports/logo-report.jpg"));
					logoImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT).scaleToFit(100, 100);
				
					//====== table ============================================================
					// header and logo
					table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
					table.addCell(new com.itextpdf.layout.element.Cell(6, 8).add(new Paragraph("QUARTERLY PRODUCTION REPORT - " + dataObj.getQuarterly_month())).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
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
					
					com.itextpdf.layout.element.Cell chartCell1 = null;
					com.itextpdf.layout.element.Cell chartCell2 = null;

					if (quarterlyReportByDay) {
						// first chart
//						chartCell1 = new com.itextpdf.layout.element.Cell(14, 13);
//						table.addCell(chartCell1.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
						
						// gap between data table and chart
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 13).setHeight(14).setBorder(Border.NO_BORDER));

						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Date")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Daily System Production (kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Daily POA (W/m²)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Daily POA Insolation (kWh/m²)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("TCell (°C)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Temperature Corrected PR (%)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Inverter Availability (%)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());

						// data table
						DecimalFormat df = new DecimalFormat("###,###");
						DecimalFormat dfp = new DecimalFormat("###,##0.0");
						DecimalFormat dfp4 = new DecimalFormat("###,##0.0000");
						
						if(dataExports.size() > 0) {
							for(int i = 0; i < dataExports.size(); i++ ) {
								QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
								QuarterlyDateEntity itemWeatherStation = dataWeatherStation != null ? (QuarterlyDateEntity) dataWeatherStation.get(i) : null;
								QuarterlyDateEntity itemInverterAvailabilty = dataInverterAvailability != null ? (QuarterlyDateEntity) dataInverterAvailability.get(i) : null;
								
								table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph(item.getCategories_time()).setBold()));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(item.getActual() != null ? df.format(item.getActual()) : "")));
//								table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataWeatherStation != null && itemWeatherStation.getPOAAVG() != null ? df.format(itemWeatherStation.getPOAAVG()) : "")));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataWeatherStation != null && itemWeatherStation.getPOAAVG() != null ? dfp4.format(itemWeatherStation.getPOAAVG() * 24 /1000) : "")));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataWeatherStation != null && itemWeatherStation.getTCellAVG() != null ? dfp.format(itemWeatherStation.getTCellAVG()) : "")));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataWeatherStation != null && item.getActual() != null && itemWeatherStation.getPOAAVG() != null && itemWeatherStation.getTCellAVG() != null && itemWeatherStation.getPOAAVG() > 0 ? dfp.format(item.getActual() / ((dataObj.getDc_capacity() * itemWeatherStation.getPOAAVG() * 24 / 1000) * (1 - (-0.47 / 100) * (25 - itemWeatherStation.getTCellAVG()))) * 100) : "")));
								table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataInverterAvailability != null && itemInverterAvailabilty.getInverterAvailability() != null ? dfp.format(itemInverterAvailabilty.getInverterAvailability()) : "")));
							}
						}
						
					} else {
						// first table
						table.addCell(new com.itextpdf.layout.element.Cell(1, 5).add(new Paragraph("Monthly kWh Production").setBold()));
						
						// empty column: gap between data table and chart
						table.addCell(new com.itextpdf.layout.element.Cell(14, 1).setBorder(Border.NO_BORDER));
						
						// first chart
						chartCell1 = new com.itextpdf.layout.element.Cell(14, 7);
						table.addCell(chartCell1.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
						
						table.addCell("");
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Estimated Generation (kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Actual Generation\n(kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Difference (kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Difference (%)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						
						// data table
						DecimalFormat df = new DecimalFormat("###,###");
						DecimalFormat dfp = new DecimalFormat("###,##0.0");
						
						totalBaseline = (double) 0;
						totalActual = (double) 0;
						
						if(dataExports.size() > 0) {
							for(int i = 0; i < dataExports.size(); i++ ) {
								QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
								totalBaseline = item.getEstimated() != null ? totalBaseline + item.getEstimated() : totalBaseline;
								totalActual = item.getActual() != null ? totalActual + item.getActual() : totalActual;
								
								table.addCell(new Paragraph(item.getCategories_time()).setBold());
								table.addCell(item.getEstimated() != null ? df.format(item.getEstimated()) : "");
								table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(item.getActual() != null ? df.format(item.getActual()) : "")).setBackgroundColor(new DeviceRgb(133, 197, 251)));
	
								if(item.getActual() != null && item.getEstimated() != null) {
									double difference = item.getActual() - item.getEstimated();
									table.addCell(new Paragraph(df.format(difference)).setFontColor(difference < 0 ? new DeviceRgb(255, 0, 0) : null));
								} else {
									table.addCell("");
								}
								
								if(item.getActual() != null && item.getEstimated() != null) {
									double differencePercent = ((item.getActual() - item.getEstimated()) / item.getEstimated()) * 100;
									differencePercent = differencePercent < 0 && differencePercent > -0.4 ? 0: differencePercent;
									table.addCell(new Paragraph(dfp.format(differencePercent)).setFontColor(differencePercent < 0 ? new DeviceRgb(255, 0, 0) : null));
								} else {
									table.addCell("");
								}
							}
						}
						
						// total
						table.addCell(new com.itextpdf.layout.element.Cell(8, 5).add(new Paragraph("\n\n\n\n\n\n\n\n\n\n")).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Total")).setBold().setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(df.format(totalBaseline))).setBold().setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(df.format(totalActual))).setBold().setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(df.format(totalActual - totalBaseline))).setBold().setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(dfp.format(((totalActual - totalBaseline) / totalBaseline) * 100))).setBold().setBorder(Border.NO_BORDER));
						
						// gap between 2 table
						table.addCell(new com.itextpdf.layout.element.Cell(1, 13).setHeight(14).setBorder(Border.NO_BORDER));
						
						// second table
						table.addCell(new com.itextpdf.layout.element.Cell(1, 5).add(new Paragraph("Cumulative kWh Production").setBold()));
						
						// empty column: gap between data table and chart
						table.addCell(new com.itextpdf.layout.element.Cell(14, 1).setBorder(Border.NO_BORDER));
						
						// second chart
						chartCell2 = new com.itextpdf.layout.element.Cell(14, 7);
						table.addCell(chartCell2.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
						
						table.addCell("");
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Estimated Generation (kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Actual Generation\n(kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Difference (kWh)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Difference (%)")).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBold());
						
						totalBaseline = (double) 0;
						totalActual = (double) 0;
						
						if(dataExports.size() > 0) {
							for(int i = 0; i < dataExports.size(); i++ ) {
								QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
								totalBaseline = item.getEstimated() != null ? totalBaseline + item.getEstimated() : totalBaseline;
								totalActual = item.getActual() != null ? totalActual + item.getActual() : totalActual;
								
								table.addCell(new Paragraph(item.getCategories_time()).setBold());
								table.addCell(item.getEstimated() != null ? df.format(totalBaseline) : "");
								table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(item.getActual() != null ? df.format(totalActual) : "")).setBackgroundColor(new DeviceRgb(133, 197, 251)));
								
								table.addCell(new Paragraph(item.getActual() != null & item.getEstimated() != null ? df.format(totalActual - totalBaseline) : "").setFontColor((totalActual - totalBaseline) < 0 ? new DeviceRgb(255, 0, 0) : null));
								
								if (item.getActual() != null & item.getEstimated() != null) {
									double percent = ((totalActual - totalBaseline) / totalBaseline) * 100;
									percent = percent < 0 && percent > -0.4 ? 0: percent;
									table.addCell(new Paragraph(dfp.format(percent)).setFontColor(percent < 0 ? new DeviceRgb(255, 0, 0) : null));
								} else {
									table.addCell("");
								}
							}
						}
	
						// empty rows
						table.addCell(new com.itextpdf.layout.element.Cell(8, 5).add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n")).setBorder(Border.NO_BORDER));
					}
					
					//====== chart ============================================================
					if (quarterlyReportByDay) {
//						final float tickMarkLength = 5;
//						final float tickMarkStroke = 1;
//						final float seriesStroke = 2;
//						final double domainAxisMargin = 0.005;
//						final double rangeAxisMargin = 0.2;
//						
//						TimeSeries powerSeries = new TimeSeries("Daily System Production (kWh)");
//						TimeSeries poaSeries = new TimeSeries("Daily POA (W/m²)");
//						TimeSeries poaInsolationSeries = new TimeSeries("Daily POA Insolation (kWh/m²)");
//						TimeSeries tCellSeries = new TimeSeries("TCell (°C)");
//						TimeSeries temperatureCorrectedSeries = new TimeSeries("Temperature Corrected PR (%)");
//						TimeSeries inverterAvailabilitySeries = new TimeSeries("Inverter Availability (%)");
//						
//						TimeSeriesCollection actualDataset = new TimeSeriesCollection(powerSeries);
//						TimeSeriesCollection poaDataset = new TimeSeriesCollection(poaSeries);
//						TimeSeriesCollection poaInsolationDataset = new TimeSeriesCollection(poaInsolationSeries);
//						TimeSeriesCollection tCellDataset = new TimeSeriesCollection(tCellSeries);
//						TimeSeriesCollection temperatureCorrectedDataset = new TimeSeriesCollection(temperatureCorrectedSeries);
//						TimeSeriesCollection inverterAvailabilityDataset = new TimeSeriesCollection(inverterAvailabilitySeries);
//						
//						JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "", actualDataset);
//						
//						// configure plot
//						XYPlot plot = chart.getXYPlot();
//						plot.setBackgroundPaint(Color.white);
//						plot.setRangeGridlinePaint(Color.gray);
//						// remove gap between plot and axis
//						plot.setAxisOffset(new RectangleInsets(0,0,0,0));
//						
//						// configure horizontal axis
//						DateAxis domainAxis= (DateAxis) plot.getDomainAxis();
//						domainAxis.setDateFormatOverride(new SimpleDateFormat("MM/dd/yyy"));
//						domainAxis.setTickMarkInsideLength(tickMarkLength);
//						domainAxis.setTickMarkOutsideLength(tickMarkLength);
//						domainAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
//						domainAxis.setLowerMargin(domainAxisMargin);
//						domainAxis.setUpperMargin(domainAxisMargin);
//						domainAxis.setVerticalTickLabels(true);
//						
//						// dataset
//						for (int i = 0; i < dataExports.size(); i++) {
//							QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
//							QuarterlyDateEntity itemWeatherStation = dataWeatherStation != null ? (QuarterlyDateEntity) dataWeatherStation.get(i) : null;
//							QuarterlyDateEntity itemInverterAvailabilty = dataInverterAvailability != null ? (QuarterlyDateEntity) dataInverterAvailability.get(i) : null;
//							
//							Double actual = item.getActual() != null ? item.getActual() : null;
//							Double poa = dataWeatherStation != null && itemWeatherStation.getPOAAVG() != null ? itemWeatherStation.getPOAAVG() : null;
//							Double poaInsolation = dataWeatherStation != null && itemWeatherStation.getPOAAVG() != null ? itemWeatherStation.getPOAAVG() * 24 /1000 : null;
//							Double tCell = dataWeatherStation != null && itemWeatherStation.getTCellAVG() != null ? itemWeatherStation.getTCellAVG() : null;
//							Double temperatureCorrected = dataWeatherStation != null && item.getActual() != null && itemWeatherStation.getPOAAVG() != null && itemWeatherStation.getTCellAVG() != null && itemWeatherStation.getPOAAVG() > 0 ? item.getActual() / ((dataObj.getDc_capacity() * itemWeatherStation.getPOAAVG() * 24 / 1000) * (1 - (-0.47 / 100) * (25 - itemWeatherStation.getTCellAVG()))) * 100 : null;
//							Double inverterAvailability = dataInverterAvailability != null && itemInverterAvailabilty.getInverterAvailability() != null ? itemInverterAvailabilty.getInverterAvailability() : null;
//							
//							powerSeries.add(new Day(new SimpleDateFormat("MM/dd/yyyy").parse(item.getCategories_time())), actual);
//							poaSeries.add(new Day(new SimpleDateFormat("MM/dd/yyyy").parse(item.getCategories_time())), poa);
//							poaInsolationSeries.add(new Day(new SimpleDateFormat("MM/dd/yyyy").parse(item.getCategories_time())), poaInsolation);
//							tCellSeries.add(new Day(new SimpleDateFormat("MM/dd/yyyy").parse(item.getCategories_time())), tCell);
//							temperatureCorrectedSeries.add(new Day(new SimpleDateFormat("MM/dd/yyyy").parse(item.getCategories_time())), temperatureCorrected);
//							inverterAvailabilitySeries.add(new Day(new SimpleDateFormat("MM/dd/yyyy").parse(item.getCategories_time())), inverterAvailability);
//						}
//						
//						// first line chart
//						XYSplineRenderer actualRenderer = new XYSplineRenderer();
//						actualRenderer.setSeriesPaint(0, new Color(49, 119, 168));
//						actualRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));
//						actualRenderer.setSeriesShapesVisible(0, false);
//						
//						NumberAxis actualAxis = new NumberAxis("kWh");
//						actualAxis.setTickMarkInsideLength(tickMarkLength);
//						actualAxis.setTickMarkOutsideLength(tickMarkLength);
//						actualAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
//						actualAxis.setLowerMargin(rangeAxisMargin);
//						actualAxis.setUpperMargin(rangeAxisMargin);
//
//						plot.setRenderer(0, actualRenderer);
//						plot.setRangeAxis(0, actualAxis);
//						plot.setDataset(0, actualDataset);
//						plot.mapDatasetToRangeAxis(0, 0);
//						plot.setRangeAxisLocation(0, AxisLocation.TOP_OR_LEFT);
//						
//						// second line chart
//						if (dataWeatherStation != null) {
//							XYSplineRenderer poaRenderer = new XYSplineRenderer();
//							poaRenderer.setSeriesPaint(0, new Color(163, 188, 215));
//							poaRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));
//							poaRenderer.setSeriesShapesVisible(0, false);
//	
//							NumberAxis poaAxis = new NumberAxis("W/m²");
//							poaAxis.setTickMarkInsideLength(tickMarkLength);
//							poaAxis.setTickMarkOutsideLength(tickMarkLength);
//							poaAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
//							poaAxis.setLowerMargin(rangeAxisMargin);
//							poaAxis.setUpperMargin(rangeAxisMargin);
//							
//							plot.setRenderer(1, poaRenderer);
//							plot.setRangeAxis(1, poaAxis);
//							plot.setDataset(1, poaDataset);
//							plot.mapDatasetToRangeAxis(1, 1);
//							plot.setRangeAxisLocation(1, AxisLocation.TOP_OR_LEFT);
//						}
//
//						// third line chart
//						if (dataWeatherStation != null) {
//							XYSplineRenderer poaInsolationRenderer = new XYSplineRenderer();
//							poaInsolationRenderer.setSeriesPaint(0, new Color(255, 129, 39));
//							poaInsolationRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));
//							poaInsolationRenderer.setSeriesShapesVisible(0, false);
//	
//							NumberAxis poaInsolationAxis = new NumberAxis("kWh/m²");
//							poaInsolationAxis.setTickMarkInsideLength(tickMarkLength);
//							poaInsolationAxis.setTickMarkOutsideLength(tickMarkLength);
//							poaInsolationAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
//							poaInsolationAxis.setLowerMargin(rangeAxisMargin);
//							poaInsolationAxis.setUpperMargin(rangeAxisMargin);
//	
//							plot.setRenderer(2, poaInsolationRenderer);
//							plot.setRangeAxis(2, poaInsolationAxis);
//							plot.setDataset(2, poaInsolationDataset);
//							plot.mapDatasetToRangeAxis(2, 2);
//							plot.setRangeAxisLocation(2, AxisLocation.TOP_OR_LEFT);
//						}
//
//						// fourth line chart
//						if (dataWeatherStation != null) {
//							XYSplineRenderer tCellRenderer = new XYSplineRenderer();
//							tCellRenderer.setSeriesPaint(0, new Color(165, 42, 42));
//							tCellRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));
//							tCellRenderer.setSeriesShapesVisible(0, false);
//	
//							NumberAxis tCellAxis = new NumberAxis("°C");
//							tCellAxis.setTickMarkInsideLength(tickMarkLength);
//							tCellAxis.setTickMarkOutsideLength(tickMarkLength);
//							tCellAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
//							tCellAxis.setLowerMargin(rangeAxisMargin);
//							tCellAxis.setUpperMargin(rangeAxisMargin);
//	
//							plot.setRenderer(3, tCellRenderer);
//							plot.setRangeAxis(3, tCellAxis);
//							plot.setDataset(3, tCellDataset);
//							plot.mapDatasetToRangeAxis(3, 3);
//							plot.setRangeAxisLocation(3, AxisLocation.TOP_OR_RIGHT);
//						}
//
//						// fifth line chart
//						NumberAxis temperatureCorrectedAxis = new NumberAxis("%");
//						temperatureCorrectedAxis.setTickMarkInsideLength(tickMarkLength);
//						temperatureCorrectedAxis.setTickMarkOutsideLength(tickMarkLength);
//						temperatureCorrectedAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
//						temperatureCorrectedAxis.setLowerMargin(rangeAxisMargin);
//						temperatureCorrectedAxis.setUpperMargin(rangeAxisMargin);
//						
//						plot.setRangeAxis(4, temperatureCorrectedAxis);
//						plot.setRangeAxisLocation(4, AxisLocation.TOP_OR_RIGHT);
//						
//						if (dataWeatherStation != null) {
//							XYSplineRenderer temperatureCorrectedRenderer = new XYSplineRenderer();
//							temperatureCorrectedRenderer.setSeriesPaint(0, new Color(27, 176, 96));
//							temperatureCorrectedRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));
//							temperatureCorrectedRenderer.setSeriesShapesVisible(0, false);
//	
//							
//							plot.setRenderer(4, temperatureCorrectedRenderer);
//							plot.setDataset(4, temperatureCorrectedDataset);
//							plot.mapDatasetToRangeAxis(4, 4);
//						}
//
//						// sixth line chart
//						if (dataInverterAvailability != null) {
//							XYSplineRenderer inverterAvailabilityRenderer = new XYSplineRenderer();
//							inverterAvailabilityRenderer.setSeriesPaint(0, new Color(253, 183, 52));
//							inverterAvailabilityRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));
//							inverterAvailabilityRenderer.setSeriesShapesVisible(0, false);
//	
//							plot.setRenderer(5, inverterAvailabilityRenderer);
//							plot.setDataset(5, inverterAvailabilityDataset);
//							plot.mapDatasetToRangeAxis(5, 4);
//						}
//						
//						// plot and return image
//						chartCell1.add(new Image(ImageDataFactory.create(chart.createBufferedImage(2000, 400), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 500));
					} else {
						final float tickMarkStroke = 1;
						CategoryPlot plot = new CategoryPlot();
						CategoryPlot plot2 = new CategoryPlot();
						
						// configure plot
						plot.setRangeGridlineStroke(new BasicStroke(tickMarkStroke));
						plot2.setRangeGridlineStroke(new BasicStroke(tickMarkStroke));
						
						// configure horizontal axis
						CategoryAxis domainAxis = new CategoryAxis();
						domainAxis.setCategoryMargin(0.5);
						domainAxis.setUpperMargin(0.1);
						domainAxis.setLowerMargin(0.1);
						
						// configure bar chart
						final DefaultCategoryDataset barChartDataset = new DefaultCategoryDataset();
						for ( int i = 0; i < dataExports.size(); i++ ) {
							QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
							barChartDataset.addValue(item.getEstimated() != null ? item.getEstimated() : 0, "Estimate Generation (kWh)", item.getCategories_time());
							barChartDataset.addValue(item.getActual() != null ? item.getActual() : 0, "Actual Generation (kWh)", item.getCategories_time());
						}
						
						totalBaseline = (double) 0;
						totalActual = (double) 0;
						
						final DefaultCategoryDataset barChartDataset2 = new DefaultCategoryDataset();
						for ( int i = 0; i < dataExports.size(); i++ ) {
							QuarterlyDateEntity item = (QuarterlyDateEntity) dataExports.get(i);
							totalBaseline = item.getEstimated() != null ? totalBaseline + item.getEstimated() : totalBaseline;
							totalActual = item.getActual() != null ? totalActual + item.getActual() : totalActual;
							barChartDataset2.addValue(item.getEstimated() != null ? totalBaseline : 0, "Estimate Generation (kWh)", item.getCategories_time());
							barChartDataset2.addValue(item.getActual() != null ? totalActual : 0, "Actual Generation (kWh)", item.getCategories_time());
						}
						
						BarRenderer barRenderer = new BarRenderer();
						barRenderer.setShadowVisible(false);
						barRenderer.setBarPainter(new StandardBarPainter());
						barRenderer.setSeriesPaint(0, new Color(49, 119, 168));
						barRenderer.setSeriesPaint(1, new Color(163, 188, 215));
						barRenderer.setItemMargin(0);
						
						NumberAxis leftAxis = new NumberAxis();
						
						plot.setDomainAxis(domainAxis);
						plot.setRenderer(0, barRenderer);
						plot.setRangeAxis(0, leftAxis);
						plot.setDataset(0, barChartDataset);
						plot.mapDatasetToRangeAxis(0, 0);
						
						// plot and return image
						JFreeChart chart = new JFreeChart(plot);
						chart.setBackgroundPaint(Color.white);
						chartCell1.add(new Image(ImageDataFactory.create(chart.createBufferedImage(900, 300), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 300));
						
						plot2.setDomainAxis((CategoryAxis) domainAxis.clone());
						plot2.setRenderer(0, barRenderer);
						plot2.setRangeAxis(0, leftAxis);
						plot2.setDataset(0, barChartDataset2);
						plot2.mapDatasetToRangeAxis(0, 0);
						
						JFreeChart chart2 = new JFreeChart(plot2);
						chart2.setBackgroundPaint(Color.white);
						chartCell2.add(new Image(ImageDataFactory.create(chart.createBufferedImage(900, 300), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 300));
					}
					
					// Write the output to a file
					document.add(table);
					// It must be closed before attach to mail
					document.close();

				    String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
				    String msgTemplate = Constants.getMailTempleteByState(16);
				    String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "Quarterly ", "", "");
				    String mailTo = dataObj.getSubscribers();
				    String subject = Constants.getMailSubjectByState(16);
				    
				    String tags = "report_quarterly";
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
				
				// FileInputStream obtains input bytes from the image file
				InputStream inputStreamImage = new FileInputStream(uploadRootPath() + "/reports/logo-report.jpg");
				// Get the contents of an InputStream as a byte[].
				byte[] bytes = IOUtils.toByteArray(inputStreamImage);
				// Adds a picture to the workbook
				int pictureIdx = document.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
				// close the input stream
				inputStreamImage.close();
				
				/* === operation performance === */
				if (dataObj != null) {
					List<AssetManagementAndOperationPerformanceDataEntity> data = dataObj.getOperationPerformanceData();
					int numOfPoints = data != null ? data.size() : 0;
					XSSFSheet sheet = document.createSheet("Operation Performance");
					
					// insert logo image
					Drawing<?> logoDrawing = sheet.createDrawingPatriarch();
					ClientAnchor logoAnchor = logoDrawing.createAnchor(0, 0, 0, 0, 11, 0, 12, 5);
					logoAnchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
					Picture pict = logoDrawing.createPicture(logoAnchor, pictureIdx);
					pict.resize(1, 1);
					
					// report information
					writeHeaderAssetManagementAndOperationPerformanceReport(sheet, dataObj.getReportDetail(), 12, "OPERATION PERFORMANCE SUMMARY");
					
					// report table
					writeTableOperationPerformanceReport(sheet, data);
					
					if (numOfPoints > 0) {
						// --- energy generation chart ---
						XSSFDrawing chartDrawing = sheet.createDrawingPatriarch();
						XSSFClientAnchor chartAnchor = chartDrawing.createAnchor(5 * Units.EMU_PER_PIXEL, 0, 0, 0, 0, numOfPoints + 11, 12, numOfPoints + 11 + 16);
						XDDFChart chart = chartDrawing.createChart(chartAnchor);
						chart.setTitleText("Energy Generation");
						chart.setTitleOverlay(false);
						CTDispBlanksAs disp = CTDispBlanksAs.Factory.newInstance();
					    disp.setVal(STDispBlanksAs.GAP);
					    chart.getCTChart().setDispBlanksAs(disp);
						
						// chart data sources
						int firstRow = 9;
						XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 0, 0));
						XDDFNumericalDataSource<Double> actualEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 1, 1));
						XDDFNumericalDataSource<Double> modeledEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 2, 2));
						XDDFNumericalDataSource<Double> expectedEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 3, 3));
						XDDFNumericalDataSource<Double> actualIrradiance = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 4, 4));
						XDDFNumericalDataSource<Double> modeledIrradiance = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 5, 5));
						
						// chart legend
						XDDFChartLegend legend = chart.getOrAddLegend();
						legend.setPosition(LegendPosition.BOTTOM);
						
						// category axis
						XDDFCategoryAxis bottomAxis = createCategoryAxis(chart, true);
						
						// left value axis
						XDDFValueAxis leftAxis = createLeftValueAxis(chart);
						
						XDDFBarChartData barChartData = (XDDFBarChartData) chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
						
						barChartData.setBarDirection(BarDirection.COL);
						barChartData.addSeries(categories, actualEnergy).setTitle("Actual Energy", new CellReference(sheet.getSheetName(), 8, 1, true, true));
						barChartData.addSeries(categories, modeledEnergy).setTitle("Modeled Energy", new CellReference(sheet.getSheetName(), 8, 2, true, true));
						barChartData.addSeries(categories, expectedEnergy).setTitle("Expected Energy", new CellReference(sheet.getSheetName(), 8, 3, true, true));
						
						// set bar colors
						solidFillSeries(barChartData, 0, PresetColor.STEEL_BLUE, PresetColor.BLACK);
						solidFillSeries(barChartData, 1, PresetColor.DARK_GRAY, PresetColor.BLACK);
						solidFillSeries(barChartData, 2, PresetColor.LIGHT_STEEL_BLUE, PresetColor.BLACK);
						
						chart.plot(barChartData);
						
						// 2nd category axis must be there but must not be visible
						bottomAxis = createCategoryAxis(chart, false);
						
						// right value axis
						XDDFValueAxis rightAxis = createRightValueAxis(chart, bottomAxis);
						
						XDDFLineChartData lineChartData = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
						lineChartData.setVaryColors(false);
						
						lineChartData.addSeries(categories, actualIrradiance).setTitle("Actual Irradiance", new CellReference(sheet.getSheetName(), 8, 4, true, true));
						lineChartData.addSeries(categories, modeledIrradiance).setTitle("Modeled Irradiance", new CellReference(sheet.getSheetName(), 8, 5, true, true));
						
						// set line colors				
						solidLineSeries(lineChartData, 0, PresetColor.GRAY);
						solidLineSeries(lineChartData, 1, PresetColor.DARK_ORANGE);
						
						chart.plot(lineChartData);
						
						// --- performance ratio chart ---
						chartDrawing = sheet.createDrawingPatriarch();
						chartAnchor = chartDrawing.createAnchor(5 * Units.EMU_PER_PIXEL, 0, -10 * Units.EMU_PER_PIXEL, 0, 0, numOfPoints + 29, 6, numOfPoints + 29 + 15);
						chart = chartDrawing.createChart(chartAnchor);
						chart.setTitleText("Performance Ratio");
						chart.setTitleOverlay(false);
						
						// chart data sources
						XDDFNumericalDataSource<Double> actualPerformanceRatio = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 6, 6));
						XDDFNumericalDataSource<Double> modeledPerformanceRatio = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 7, 7));
						
						// chart legend
						legend = chart.getOrAddLegend();
						legend.setPosition(LegendPosition.BOTTOM);
						
						// category axis
						bottomAxis = createCategoryAxis(chart, true);
						
						// left value axis
						leftAxis = createLeftValueAxis(chart);
						
						barChartData = (XDDFBarChartData) chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
						
						barChartData.setBarDirection(BarDirection.COL);
						barChartData.addSeries(categories, actualPerformanceRatio).setTitle("Actual Generation", new CellReference(sheet.getSheetName(), 8, 6, true, true));
						barChartData.addSeries(categories, modeledPerformanceRatio).setTitle("Modeled Generation", new CellReference(sheet.getSheetName(), 8, 7, true, true));
						
						// set bar colors
						solidFillSeries(barChartData, 0, PresetColor.STEEL_BLUE, PresetColor.BLACK);
						solidFillSeries(barChartData, 1, PresetColor.DARK_GRAY, PresetColor.BLACK);
						
						chart.plot(barChartData);
						
						// --- key performance indicators chart ---
						chartDrawing = sheet.createDrawingPatriarch();
						chartAnchor = chartDrawing.createAnchor(10 * Units.EMU_PER_PIXEL, 0, 0, 0, 6, numOfPoints + 29, 12, numOfPoints + 29 + 15);
						chart = chartDrawing.createChart(chartAnchor);
						chart.setTitleText("Key Performance Indicators");
						chart.setTitleOverlay(false);
						
						// chart data sources
						XDDFNumericalDataSource<Double> energyIndex = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 8, 8));
						XDDFNumericalDataSource<Double> weatherIndex = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 9, 9));
						XDDFNumericalDataSource<Double> weatherAdjustedIndex = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 10, 10));
						XDDFNumericalDataSource<Double> inverterAvailability = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 11, 11));
						
						// chart legend
						legend = chart.getOrAddLegend();
						legend.setPosition(LegendPosition.BOTTOM);
						
						// category axis
						bottomAxis = createCategoryAxis(chart, true);
						
						// left value axis
						leftAxis = createLeftValueAxis(chart);
						
						barChartData = (XDDFBarChartData) chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
						
						barChartData.setBarDirection(BarDirection.COL);
						barChartData.addSeries(categories, energyIndex).setTitle("Energy Index", new CellReference(sheet.getSheetName(), 8, 8, true, true));
						barChartData.addSeries(categories, weatherAdjustedIndex).setTitle("Weather Adjusted Index", new CellReference(sheet.getSheetName(), 8, 10, true, true));
						
						// set bar colors
						solidFillSeries(barChartData, 0, PresetColor.STEEL_BLUE, PresetColor.BLACK);
						solidFillSeries(barChartData, 1, PresetColor.DARK_GRAY, PresetColor.BLACK);
						
						chart.plot(barChartData);
						
						// 2nd category axis must be there but must not be visible
						bottomAxis = createCategoryAxis(chart, false);
						
						// right value axis
						rightAxis = createRightValueAxis(chart, bottomAxis);
						
						lineChartData = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
						lineChartData.setVaryColors(false);
						
						lineChartData.addSeries(categories, weatherIndex).setTitle("Weather Index", new CellReference(sheet.getSheetName(), 8, 9, true, true));
						lineChartData.addSeries(categories, inverterAvailability).setTitle("Inverter Availability", new CellReference(sheet.getSheetName(), 8, 11, true, true));
						
						// set line colors				
						solidLineSeries(lineChartData, 0, PresetColor.GRAY);
						solidLineSeries(lineChartData, 1, PresetColor.DARK_ORANGE);
						
						chart.plot(lineChartData);
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
						Drawing<?> logoDrawing = sheet.createDrawingPatriarch();
						ClientAnchor logoAnchor = logoDrawing.createAnchor(0, 0, 0, 0, 13, 0, 14, 5);
						logoAnchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
						Picture pict = logoDrawing.createPicture(logoAnchor, pictureIdx);
						pict.resize(1, 1);
						
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
							XSSFDrawing chartDrawing = sheet.createDrawingPatriarch();
							XSSFClientAnchor chartAnchor = chartDrawing.createAnchor(0, 0, 0, 0, 7, numOfPoints + 9 - 16, 14, numOfPoints + 9);
							XDDFChart chart = chartDrawing.createChart(chartAnchor);
							CTDispBlanksAs disp = CTDispBlanksAs.Factory.newInstance();
						    disp.setVal(STDispBlanksAs.GAP);
						    chart.getCTChart().setDispBlanksAs(disp);
							
							// chart data sources
							int firstRow = 9;
							XDDFDataSource<String> categories = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 0, 0));
							XDDFNumericalDataSource<Double> actualEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 1, 1));
							XDDFNumericalDataSource<Double> expectedEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 2, 2));
							XDDFNumericalDataSource<Double> modeledEnergy = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(firstRow, firstRow + numOfPoints - 1, 3, 3));
							
							// chart legend
							XDDFChartLegend legend = chart.getOrAddLegend();
							legend.setPosition(LegendPosition.BOTTOM);
							
							// category axis
							XDDFCategoryAxis bottomAxis = createCategoryAxis(chart, true);
							
							// left value axis
							XDDFValueAxis leftAxis = createLeftValueAxis(chart);
							
							XDDFBarChartData barChartData = (XDDFBarChartData) chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
							
							barChartData.setBarDirection(BarDirection.COL);
							barChartData.addSeries(categories, actualEnergy).setTitle("Actual", new CellReference(sheet.getSheetName(), 8, 1, true, true));
							barChartData.addSeries(categories, expectedEnergy).setTitle("Expected*", new CellReference(sheet.getSheetName(), 8, 2, true, true));
							
							// set bar colors
							solidFillSeries(barChartData, 0, PresetColor.STEEL_BLUE, PresetColor.BLACK);
							solidFillSeries(barChartData, 1, PresetColor.DARK_GRAY, PresetColor.BLACK);
							
							chart.plot(barChartData);
							
							XDDFLineChartData lineChartData = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
							lineChartData.setVaryColors(false);
							
							lineChartData.addSeries(categories, modeledEnergy).setTitle("Modeled**", new CellReference(sheet.getSheetName(), 8, 3, true, true));
							
							// set line colors				
							solidLineSeries(lineChartData, 0, PresetColor.DARK_ORANGE);
							
							chart.plot(lineChartData);
						}
					}
				}
				
				/* === monthly asset performance === */
				if (dataObj != null) {
					List<AssetManagementAndOperationPerformanceDataEntity> data = dataObj.getMonthlyAssetManagementData();
					XSSFSheet sheet = document.createSheet("Monthly Asset Management");
					
					// insert logo image
					Drawing<?> logoDrawing = sheet.createDrawingPatriarch();
					ClientAnchor logoAnchor = logoDrawing.createAnchor(0, 0, 0, 0, 6, 0, 7, 5);
					logoAnchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
					Picture pict = logoDrawing.createPicture(logoAnchor, pictureIdx);
					pict.resize(1, 1);
					
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
					Drawing<?> logoDrawing = sheet.createDrawingPatriarch();
					ClientAnchor logoAnchor = logoDrawing.createAnchor(50 * Units.EMU_PER_PIXEL, 0, 0, 10 * Units.EMU_PER_PIXEL, 7, 0, 8, 5);
					logoAnchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
					Picture pict = logoDrawing.createPicture(logoAnchor, pictureIdx);
					pict.resize(1, 1);
					
					// report information
					writeHeaderAssetManagementAndOperationPerformanceReport(sheet, dataObj.getReportDetail(), 8, "ESTIMATED LOSS BY EVENT");
					
					// report table
					writeTableEstimatedLossByEventReport(sheet, data);
				}
								
				// Write the output to a file
				String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
				String dir = uploadRootPath() + "/"
						+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
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
					boolean flagSent = SendMail.SendGmailTLSAttachmentattachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
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
							item.get("name").toString() + " - " + item.get("devicename").toString(),
							" "+item.get("ru_id").toString(),
							" "+item.get("gu_id").toString(),
							" "+item.get("vintage_date").toString(),
							" "+item.get("start_date").toString(),
							" "+item.get("end_date").toString(),
							" "+item.get("energy_this_month").toString()
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
					boolean flagSent = SendMail.SendGmailTLSAttachmentattachment(mailFromContact, fromName, mailTo, subject, body, tags, fileName);
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
		try {
			try (XSSFWorkbook document = new XSSFWorkbook()) {
				
				
				
				ReportsService service = new ReportsService();
				ViewReportEntity dataObj = (ViewReportEntity) service.getMonthlyReport(obj);
				if (dataObj != null) {
					String chartTitle = "";
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat dateFormatString = new SimpleDateFormat("MMMM yyyy");
					Date convertedDate = dateFormat.parse(obj.getEnd_date());
					Date startDate = dateFormat.parse(obj.getStart_date());
					dataObj.setStart_date( new SimpleDateFormat("MM/dd/yyyy").format(startDate) );
					dataObj.setEnd_date( new SimpleDateFormat("MM/dd/yyyy").format(convertedDate) );
					
					chartTitle = dateFormatString.format(startDate);
					
					ArrayList<String> categories = new ArrayList<String>();				
					List<?> dataExports = dataObj.getDataReports();
					if(dataExports.size() > 0) {
						for( int j = 0; j < dataExports.size(); j++){
							MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(j);
							String categoriesTime = (String) item.getCategories_time();
							categories.add( categoriesTime );
						}
					}
					
					
					XSSFSheet chartSheet = document.createSheet("Monthly Performance");
					XSSFSheet dataSheet = document.createSheet("data");
					XSSFSheet dataSheetTotal = document.createSheet("total");
					
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
					Drawing<?> drawing = chartSheet.createDrawingPatriarch();
					
					// Create an anchor that is attached to the worksheet
					ClientAnchor anchor = helper.createClientAnchor();
					anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
					// set top-left corner for the image
					
					anchor.setCol1(12);
					anchor.setRow1(1);
//						anchor.setCol2(3);
//						anchor.setRow2(4);
					
					// Creates a picture
					Picture pict = drawing.createPicture(anchor, pictureIdx);
					
					// Reset the image to the original sizege
					pict.resize(1.45, 3.50);
					
					
					writeHeaderMonthlyReport(chartSheet, 0, dataObj);
					// create the data
					int r = 0;
					Double totalActual = 0.0; 
					Double totalEstimated = 0.0;
					for (String cat : categories) {
						MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(r);
						
						String categoriesTime = (String) item.getCategories_time();
						dataSheet.createRow(r).createCell(0).setCellValue(categoriesTime);
						dataSheet.getRow(r).createCell(1).setCellValue(item.getActual());
						dataSheet.getRow(r).createCell(2).setCellValue(item.getEstimated());
						dataSheet.getRow(r).createCell(3).setCellValue(item.getPercent());
						
						totalActual = totalActual + item.getActual();
						totalEstimated = totalEstimated + item.getEstimated();
						r++;
					}
					
					dataSheetTotal.createRow(0).createCell(0).setCellValue("");
					dataSheetTotal.getRow(0).createCell(1).setCellValue(totalActual);
					dataSheetTotal.getRow(0).createCell(2).setCellValue(totalEstimated);
					
					// ----------------------------------------------------------------------------------------------------
					XSSFClientAnchor anchor1;
					XDDFChart chart;
					XDDFChartLegend legend;
					XDDFCategoryAxis bottomAxis;
					XDDFValueAxis leftAxis;
					XDDFChartData data;
					XDDFChartData.Series series;
					// create the chart 1
					XSSFDrawing drawing1 = chartSheet.createDrawingPatriarch();
					
					//====== first line chart============================================================
					anchor1 = drawing1.createAnchor(5, 5, 5, 5, 5, 8, 14, 23);
					chart = drawing1.createChart(anchor1);
					chart.setTitleText("");
					chart.setTitleOverlay(false);
					
					// create data sources
					int numOfPoints = categories.size();
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
					
					for (int i = 0; i < numOfPoints; i++) {
						XSSFRow row = dataSheet.getRow(i);
						if (row == null)
							row = dataSheet.createRow(i);
						XSSFCell cell = row.createCell(255);
						cell.setCellValue(0);
					}
					
					// data source for the pad series
					XDDFNumericalDataSource<Double> pad = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
							new CellRangeAddress(0, numOfPoints - 1, 255, 255));
					
					// first bar chart
					bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
					if (bottomAxis.hasNumberFormat()) bottomAxis.setNumberFormat("@");

					leftAxis = chart.createValueAxis(AxisPosition.LEFT);
					leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
					leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
					leftAxis.setTitle("GENERATION (KWH)");
					leftAxis.setMinimum(0);
					if (leftAxis.hasNumberFormat()) leftAxis.setNumberFormat("#,##0.00");
					
					
					data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
					XDDFBarChartData bar = (XDDFBarChartData) data;
					bar.setBarDirection(BarDirection.COL);
					
					CTPlotArea plotArea = chart.getCTChart().getPlotArea();
					plotArea.getValAxArray()[0].addNewMajorGridlines();
					
					series = data.addSeries(categoriesData, valuesData1);
					series.setTitle("Actual Generation (kWh)",
							new CellReference(chartSheet.getSheetName(), 8, 1, true, true));
					
					series = data.addSeries(categoriesData, valuesData2);
					series.setTitle("Estimated Generation (kWh)",
							new CellReference(chartSheet.getSheetName(), 8, 2, true, true));
					chart.plot(data);
					
					// set bar colors
					solidFillSeries(data, 0, PresetColor.STEEL_BLUE);
					solidFillSeries(data, 1, PresetColor.LIGHT_STEEL_BLUE);
					
					// second bar chart
					// bottom axis must be there but must not be visible
					bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
					bottomAxis.setVisible(false);
					if (bottomAxis.hasNumberFormat()) bottomAxis.setNumberFormat("@");
					
					XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
					rightAxis.setCrosses(AxisCrosses.MAX);
					rightAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
					rightAxis.setTitle("PERFORMANCE INDEX (%)");
					if (rightAxis.hasNumberFormat()) rightAxis.setNumberFormat("#,##0.00");
					
					// set correct cross axis
					bottomAxis.crossAxis(rightAxis);
					rightAxis.crossAxis(bottomAxis);
													
					data = chart.createData(ChartTypes.LINE, bottomAxis, rightAxis);
					data.setVaryColors(false);
					bar.setBarDirection(BarDirection.COL);
					XDDFLineChartData.Series seriesLine = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData3);
					seriesLine.setTitle("Estimated Generation Index (%)", new CellReference(chartSheet.getSheetName(), 8, 3, true, true));
					chart.plot(data);		
					// set line colors				
					solidLineSeries(data, 0, PresetColor.GRAY);
									
					// set legend
					legend = chart.getOrAddLegend();
					legend.setPosition(LegendPosition.BOTTOM);
					
					
					//======second line chart============================================================
					anchor = drawing1.createAnchor(5, 5, 5, 5, 5, 25, 14, 41);
					chart = drawing1.createChart(anchor);
					chart.setTitleText(chartTitle);
					chart.setTitleOverlay(false);
					chart.getCTChart().getTitle().getTx().getRich().getPArray(0).getRArray(0).getRPr().setSz(1200);
					
					// create the axes
					bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
					bottomAxis.setVisible(false);
					if (bottomAxis.hasNumberFormat()) bottomAxis.setNumberFormat("@");
					
					leftAxis = chart.createValueAxis(AxisPosition.LEFT);
					leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
					leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
					leftAxis.setTitle("GENERATION (KWH)");
					leftAxis.setMinimum(0);
					if (leftAxis.hasNumberFormat()) leftAxis.setNumberFormat("#,##0.00");
					
					// create chart data
					data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
					
					// create data sources
					int numOfPoints2 = 1;
					Double[] dummyValuesForPad2 = new Double[numOfPoints2];
					for (int i = 0; i < numOfPoints2; i++) {
						dummyValuesForPad2[i] = 0d;
					}
					
					XDDFDataSource<String> categoriesData2 = XDDFDataSourcesFactory.fromStringCellRange(dataSheetTotal,
							new CellRangeAddress(0, numOfPoints2 - 1, 0, 0));
					XDDFNumericalDataSource<Double> valuesData12 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheetTotal,
							new CellRangeAddress(0, numOfPoints2 - 1, 1, 1));
					XDDFNumericalDataSource<Double> valuesData22 = XDDFDataSourcesFactory.fromNumericCellRange(dataSheetTotal,
							new CellRangeAddress(0, numOfPoints2 - 1, 2, 2));
					
					for (int i = 0; i < numOfPoints2; i++) {
						XSSFRow row2 = dataSheetTotal.getRow(i);
						if (row2 == null)
							row2 = dataSheetTotal.createRow(i);
						XSSFCell cell2 = row2.createCell(255);
						cell2.setCellValue(0);
					}
					
					XDDFBarChartData bar2 = (XDDFBarChartData) data;
					bar2.setBarDirection(BarDirection.COL);
					bar2.setOverlap((byte) -24);
					bar2.setGapWidth(400);
					
					CTPlotArea plotArea2 = chart.getCTChart().getPlotArea();
					plotArea2.getValAxArray()[0].addNewMajorGridlines();
					
					series = data.addSeries(categoriesData2, valuesData12);
					series.setTitle("Actual Generation (kWh)",
							new CellReference(chartSheet.getSheetName(), 8, 1, true, true));
					
					series = data.addSeries(categoriesData2, valuesData22);
					series.setTitle("Estimated Generation (kWh)",
							new CellReference(chartSheet.getSheetName(), 8, 2, true, true));
					
					
					chart.plot(data);
					
					// set bar colors
					solidFillSeries(data, 0, PresetColor.STEEL_BLUE);
					solidFillSeries(data, 1, PresetColor.LIGHT_STEEL_BLUE);
					
					// set legend
					legend = chart.getOrAddLegend();
					legend.setPosition(LegendPosition.BOTTOM);
					
					// Write the output to a file
					String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
					String dir = uploadRootPath() + "/"
							+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
					String fileName = dir + "/Monthly-report-" + timeStamp + ".xlsx";
					
					try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
						document.write(fileOut);
						String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
								Constants.mailFromContact);

						String msgTemplate = Constants.getMailTempleteByState(16);
						String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "Monthly ", "", "");
						String mailTo = dataObj.getSubscribers();
						String subject = Constants.getMailSubjectByState(16);

						String tags = "report_monthly";
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
	 * @description sent mail monthly report in pdf
	 * @author Hung.Bui
	 * @since 2022-11-29
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-pdf-monthly-report")
	public Object sentMailPdfMonthlyReport(@RequestBody ViewReportEntity obj) {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
			String dir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
			String fileName = dir + "/Monthly-report-" + timeStamp + ".pdf";
			File file = new File(fileName);
			
			try (
					PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
					Document document = new Document(pdfDocument, PageSize.A3.rotate());
					) {
				
				ReportsService service = new ReportsService();
				ViewReportEntity dataObj = (ViewReportEntity) service.getMonthlyReport(obj);
				
				if (dataObj != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat dateFormatString = new SimpleDateFormat("MMMM yyyy");
					Date convertedDate = dateFormat.parse(obj.getEnd_date());
					Date startDate = dateFormat.parse(obj.getStart_date());
					dataObj.setStart_date( new SimpleDateFormat("MM/dd/yyyy").format(startDate) );
					dataObj.setEnd_date( new SimpleDateFormat("MM/dd/yyyy").format(convertedDate) );
					List<?> dataExports = dataObj.getDataReports();
					
					// total column: 14
					final float[] columnWidths = {4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
					
					Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
					table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
					table.setFontSize(8);
					table.setTextAlignment(TextAlignment.CENTER);
					
					Image logoImage = new Image(ImageDataFactory.create(uploadRootPath() + "/reports/logo-report.jpg"));
					logoImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT).scaleToFit(100, 100);
				
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
					double totalActual = 0;
					double totalEstimated = 0;
					DecimalFormat dfa = new DecimalFormat("###,###");
					DecimalFormat df = new DecimalFormat("###,###.0");
					for (int i = 0; i < dataExports.size(); i++) {
						MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(i);
						
						table.addCell(item.getCategories_time());
						table.addCell(dfa.format(item.getActual()));
						table.addCell(dfa.format(item.getEstimated()));
						table.addCell(df.format(item.getPercent()));
						
						totalActual = totalActual + item.getActual();
						totalEstimated = totalEstimated + item.getEstimated();
					}
					
					// total
					table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setHeight(14).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(1)));
					table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("Total")).setBold().setBorder(Border.NO_BORDER));
					table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(dfa.format(totalActual))).setBold().setBorder(Border.NO_BORDER));
					table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(dfa.format(totalEstimated))).setBold().setBorder(Border.NO_BORDER));
					table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(dfa.format((totalActual / totalEstimated) * 100))).setBold().setBorder(Border.NO_BORDER));
					
					//====== first chart ============================================================
					final float tickMarkLength = 5;
					final float tickMarkStroke = 1;
					final double domainAxisMargin = 0.01;
					final double domainAxisMargin2 = 0.3;
					CategoryPlot plot = new CategoryPlot();
					
					// configure plot
					plot.setRangeGridlineStroke(new BasicStroke(tickMarkStroke));
					plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
					
					// configure horizontal axis
					CategoryAxis domainAxis = new CategoryAxis();
					domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
					domainAxis.setTickMarkInsideLength(tickMarkLength);
					domainAxis.setTickMarkOutsideLength(tickMarkLength);
					domainAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					domainAxis.setLowerMargin(domainAxisMargin);
					domainAxis.setUpperMargin(domainAxisMargin);
					domainAxis.setCategoryMargin(0.25);
					
					plot.setDomainAxis(domainAxis);
					
					// configure bar chart
					final DefaultCategoryDataset barChartDataset = new DefaultCategoryDataset();
					for ( int i = 0; i < dataExports.size(); i++ ) {
						MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(i);
						
						barChartDataset.addValue(item.getActual(), "Actual Generation (kWh)", item.getCategories_time());
						barChartDataset.addValue(item.getEstimated(), "Estimate Generation (kWh)", item.getCategories_time());
					}
					
					BarRenderer barRenderer = new BarRenderer();
					barRenderer.setShadowVisible(false);
					barRenderer.setBarPainter(new StandardBarPainter());
					barRenderer.setSeriesPaint(0, new Color(49, 119, 168));
					barRenderer.setSeriesPaint(1, new Color(163, 188, 215));
					barRenderer.setItemMargin(0);
					
					NumberAxis leftAxis = new NumberAxis("GENERATION (KWH)");
					leftAxis.setTickMarkInsideLength(tickMarkLength);
					leftAxis.setTickMarkOutsideLength(tickMarkLength);
					leftAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					
					plot.setRenderer(0, barRenderer);
					plot.setRangeAxis(0, leftAxis);
					plot.setDataset(0, barChartDataset);
					plot.mapDatasetToRangeAxis(0, 0);
					
					// configure line chart
					final DefaultCategoryDataset lineChartDataset = new DefaultCategoryDataset();
					for ( int i = 0; i < dataExports.size(); i++ ) {
						MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(i);
						lineChartDataset.addValue(item.getPercent(), "Estimate Generation Index (%)", item.getCategories_time());
					}
					
					LineAndShapeRenderer lineAndShapeRenderer = new LineAndShapeRenderer();
					lineAndShapeRenderer.setSeriesPaint(0, Color.gray);
					lineAndShapeRenderer.setSeriesShape(0, ShapeUtils.createUpTriangle(3));
					
					NumberAxis rightAxis = new NumberAxis("PERFORMANCE INDEX (%)");
					rightAxis.setTickMarkInsideLength(tickMarkLength);
					rightAxis.setTickMarkOutsideLength(tickMarkLength);
					rightAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					
					plot.setRenderer(1, lineAndShapeRenderer);
					plot.setRangeAxis(1, rightAxis);
					plot.setDataset(1, lineChartDataset);
					plot.mapDatasetToRangeAxis(1, 1);
					
					// plot and return image
					JFreeChart chart = new JFreeChart(plot);
					chart.setBackgroundPaint(Color.white);
					innerTable.addCell(new Image(ImageDataFactory.create(chart.createBufferedImage(900, 400), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 400));
					// gap between charts
					innerTable.addCell(new com.itextpdf.layout.element.Cell().setHeight(18 * (dataExports.size() + 1 - 30)).setBorder(Border.NO_BORDER));
					
					//====== second chart ============================================================
					CategoryPlot plot2 = new CategoryPlot();
					
					// configure plot
					plot2.setRangeGridlineStroke(new BasicStroke(tickMarkStroke));
					plot2.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
					
					// configure horizontal axis
					CategoryAxis domainAxis2 = new CategoryAxis();
					domainAxis2.setTickMarkInsideLength(tickMarkLength);
					domainAxis2.setTickMarkOutsideLength(tickMarkLength);
					domainAxis2.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					domainAxis2.setLowerMargin(domainAxisMargin2);
					domainAxis2.setUpperMargin(domainAxisMargin2);
					
					plot2.setDomainAxis(domainAxis2);
					
					// configure bar chart
					final DefaultCategoryDataset barChartDataset2 = new DefaultCategoryDataset();
					barChartDataset2.addValue(totalActual, "Actual Generation (kWh)", "");
					barChartDataset2.addValue(totalEstimated, "Estimate Generation (kWh)", "");
					
					BarRenderer barRenderer2 = new BarRenderer();
					barRenderer2.setShadowVisible(false);
					barRenderer2.setBarPainter(new StandardBarPainter());
					barRenderer2.setSeriesPaint(0, new Color(49, 119, 168));
					barRenderer2.setSeriesPaint(1, new Color(163, 188, 215));
					barRenderer2.setItemMargin(0.05);
					plot2.setRenderer(0, barRenderer2);
					
					NumberAxis leftAxis2 = new NumberAxis("GENERATION (KWH)");
					leftAxis2.setTickMarkInsideLength(tickMarkLength);
					leftAxis2.setTickMarkOutsideLength(tickMarkLength);
					leftAxis2.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					
					plot2.setRangeAxis(0, leftAxis2);
					plot2.setDataset(0, barChartDataset2);
					plot2.mapDatasetToRangeAxis(0, 0);
					
					// plot and return image
					JFreeChart chart2 = new JFreeChart(plot2);
					chart2.setBackgroundPaint(Color.white);
					chart2.setTitle(dateFormatString.format(startDate));
					innerTable.addCell(new Image(ImageDataFactory.create(chart2.createBufferedImage(900, 350), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 350));

					// Write the output to a file
					document.add(table);
					// It must be closed before attach to mail
					document.close();

				    String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
				    String msgTemplate = Constants.getMailTempleteByState(16);
				    String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "Monthly ", "", "");
				    String mailTo = dataObj.getSubscribers();
				    String subject = Constants.getMailSubjectByState(16);
				    
				    String tags = "report_monthly";
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
			private static void writeHeaderCustomReport(Sheet sheet, int rowIndex, ViewReportEntity dataObj) {
				try {
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
					sheet.setColumnWidth(11, 18 * 256);
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
					
					Row row1 = sheet.createRow(0);
					Cell cel1A = row1.createCell(0);
					cel1A.setCellStyle(cellStyleLeft);
					cel1A.setCellValue("Site Name");
					
					Cell cel1B = row1.createCell(1);
					cel1B.setCellStyle(cellStyleLeft);
					cel1B.setCellValue("");
					
					Cell cel1C = row1.createCell(2);
					row1.setHeight((short) 600);
					cel1C.setCellStyle(cellStyleLeft);
					cel1C.setCellValue(dataObj.getSite_name());
					
					Cell cel1D = row1.createCell(3);
					cel1D.setCellStyle(cellStyleCenter);
					cel1D.setCellValue("");
					
					Cell cel1E = row1.createCell(4);
					cel1E.setCellStyle(cellStyleCenter);
					cel1E.setCellValue("");
					
					
					Row row2 = sheet.createRow(1);
					Cell cel2A = row2.createCell(0);
					row2.setHeight((short) 600);
					cel2A.setCellStyle(cellStyleLeft);
					cel2A.setCellValue("Report Date");
					
					Cell cel2B = row2.createCell(1);
					cel2B.setCellStyle(cellStyleLeft);
					cel2B.setCellValue("");
					
					Cell cel2C = row2.createCell(2);
					cel2C.setCellStyle(cellStyleCenter);
					cel2C.setCellValue(dataObj.getReport_date());
					
					Cell cel2D = row2.createCell(3);
					cel2D.setCellStyle(cellStyleCenter);
					cel2D.setCellValue("");
					
					Cell cel2E = row2.createCell(4);
					cel2E.setCellStyle(cellStyleCenter);
					cel2E.setCellValue("");
					
					
					Row row3 = sheet.createRow(2);
					row3.setHeight((short) 600);
					Cell cel3A = row3.createCell(0);
					cel3A.setCellStyle(cellStyleLeft);
					cel3A.setCellValue("Covered Period");
					
					Cell cel3B = row3.createCell(1);
					cel3B.setCellStyle(cellStyleLeft);
					cel3B.setCellValue("");
					
					Cell cel3C = row3.createCell(2);
					cel3C.setCellStyle(cellStyleCenter);
					cel3C.setCellValue(dataObj.getDate_from() + " - " + dataObj.getDate_to());
					
					Cell cel3D = row3.createCell(3);
					cel3D.setCellStyle(cellStyleCenter);
					cel3D.setCellValue("");
					
					Cell cel3E = row3.createCell(4);
					cel3E.setCellStyle(cellStyleCenter);
					cel3E.setCellValue("");
					
					sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 10));	
					Cell cel3F = row3.createCell(5);
					cel3F.setCellStyle(cellStyleFontBold);
					cel3F.setCellValue("PRODUCTION REPORT");
					
					
					Row row4 = sheet.createRow(3);
					row4.setHeight((short) 600);
					Cell cel4A = row4.createCell(0);
					cel4A.setCellStyle(cellStyleLeft);
					cel4A.setCellValue("System Size (kW DC)");
					
					Cell cel4B = row4.createCell(1);
					cel4B.setCellStyle(cellStyleLeft);
					cel4B.setCellValue("");
					
					Cell cel4C = row4.createCell(2);
					cel4C.setCellStyle(cellStyleCenter);
					cel4C.setCellValue( df.format(dataObj.getDc_capacity() ) );
					
					Cell cel4D = row4.createCell(3);
					cel4D.setCellStyle(cellStyleCenter);
					cel4D.setCellValue("");
					
					Cell cel4E = row4.createCell(4);
					cel4E.setCellStyle(cellStyleCenter);
					cel4E.setCellValue("");
					
					
					// Create font
					Font styleH = sheet.getWorkbook().createFont();
					styleH.setFontName("Times New Roman");
					styleH.setBold(true);
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
					
					sheet.addMergedRegion(new CellRangeAddress(24, 24, 0, 2));
					sheet.addMergedRegion(new CellRangeAddress(24, 24, 3, 5));
					sheet.addMergedRegion(new CellRangeAddress(24, 24, 6, 8));
					sheet.addMergedRegion(new CellRangeAddress(24, 24, 9, 11));
					
					Row row25 = sheet.createRow(24);
					Cell cel25D = row25.createCell(3);
					cel25D.setCellStyle(cellStyleH);
					cel25D.setCellValue("Timestamp");
					
					Cell cel25E = row25.createCell(4);
					cel25E.setCellStyle(cellStyleH);
					cel25E.setCellValue("");
					
					Cell cel25F = row25.createCell(5);
					cel25F.setCellStyle(cellStyleH);
					cel25F.setCellValue("");
					
					Cell cel25G = row25.createCell(6);
					cel25G.setCellStyle(cellStyleH);
					cel25G.setCellValue("Actual Generation (kWh)");
					
					Cell cel25H = row25.createCell(7);
					cel25H.setCellStyle(cellStyleH);
					cel25H.setCellValue("");
					
					Cell cel25I = row25.createCell(8);
					cel25I.setCellStyle(cellStyleH);
					cel25I.setCellValue("");
					
					
					List dataExports = dataObj.getDataReports();
					if(dataExports.size() > 0) {
						for(int i = 0 ;i < dataExports.size(); i++) {
							Map<String, Object> item = (Map<String, Object>) dataExports.get(i);
							Double itemActual = Double.parseDouble(item.get("actual").toString());
							String itemCategoryTime = item.get("categories_time").toString();

							int t = 25 + i;
							
							sheet.addMergedRegion(new CellRangeAddress(t, t, 0, 2));
							sheet.addMergedRegion(new CellRangeAddress(t, t, 3, 5));
							sheet.addMergedRegion(new CellRangeAddress(t, t, 6, 8));
							sheet.addMergedRegion(new CellRangeAddress(t, t, 9, 11));
							
							Row row26 = sheet.createRow(t);
							Cell cel26D = row26.createCell(3);
							cel26D.setCellStyle(cellStyleR);
							cel26D.setCellValue(itemCategoryTime);
							
							Cell cel26E = row26.createCell(4);
							cel26E.setCellStyle(cellStyleR);
							cel26E.setCellValue("");
							
							Cell cel26F = row26.createCell(5);
							cel26F.setCellStyle(cellStyleR);
							cel26F.setCellValue("");
							
							Cell cel26G = row26.createCell(6);
							cel26G.setCellStyle(cellStyleR);
							String actual = itemActual < 0 ? "0" : (itemActual == 0.001 ? null : dfs.format(itemActual));
							cel26G.setCellValue(actual);
							
							Cell cel26H = row26.createCell(7);
							cel26H.setCellStyle(cellStyleR);
							cel26H.setCellValue("");
							
							Cell cel26I = row26.createCell(8);
							cel26I.setCellStyle(cellStyleR);
							cel26I.setCellValue("");
						}
					}
				} catch (Exception e) {
				}

			}
					
			/**
			 * @description sent mail daily report in excel
			 * @author Hung.Bui
			 * @since 2022-12-20
			 * @param id
			 * @return data (status, message, array, total_row
			 */
			@PostMapping("/sent-mail-excel-custom-report")
			public Object sentMailCustomReport(@RequestBody ViewReportEntity obj) {
				try {
					try (XSSFWorkbook document = new XSSFWorkbook()) {
						ReportsService service = new ReportsService();
						ViewReportEntity dataObj = (ViewReportEntity) service.getCustomReport(obj);
						if (dataObj != null) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat dayFormat = new SimpleDateFormat("MM/dd/yyyy");
							SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");
							SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
							SimpleDateFormat format = null;
							Date dateFrom = dateFormat.parse(obj.getDate_from());
							Date dateTo = dateFormat.parse(obj.getDate_to());
							
							// select format based on intervals
							switch (obj.getData_intervals()) {
								case Constants.DAILY_INTERVAL:
									format = dayFormat;
									break;
									
								case Constants.MONTHLY_INTERVAL:
									format = monthFormat;
									break;
									
								case Constants.ANNUALLY_INTERVAL:
									format = yearFormat;
									break;
	
								default:
									format = monthFormat;
									break;
							}
							
							Calendar calQ = Calendar.getInstance();
							dataObj.setReport_date(dayFormat.format(calQ.getTime()));
							calQ.setTime(dateFrom);
							dataObj.setDate_from(format.format(calQ.getTime()));
							calQ.setTime(dateTo);
							dataObj.setDate_to(format.format(calQ.getTime()));
							
							XSSFSheet chartSheet = document.createSheet("Production Report");
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
							anchor.setCol1(11);
							anchor.setRow1(1);

							// Creates a picture
							Picture pict = drawing.createPicture(anchor, pictureIdx);
							// Reset the image to the original size
							pict.resize(1, 3.5);
							
							writeHeaderCustomReport(chartSheet, 0, dataObj);
							// create the data
							List dataExports = dataObj.getDataReports();
							if(dataExports.size() > 0) {
								for(int i = 0; i< dataExports.size(); i++) {
									Map<String, Object> item = (Map<String, Object>) dataExports.get(i);
									Double itemActual = Double.parseDouble(item.get("actual").toString());
									String itemCategoryTime = item.get("categories_time").toString();
									Double actual = itemActual <= 0 ? 0 : itemActual; 
									
									dataSheet.createRow(i).createCell(0).setCellValue(itemCategoryTime);
									dataSheet.getRow(i).createCell(1).setCellValue(((actual == 0.001) ? 0 : actual));
								}
							}
							
							XSSFClientAnchor anchor1;
							XSSFChart chart;
							// create the chart 
						    XSSFDrawing drawing1 = chartSheet.createDrawingPatriarch();
							
							//====== line chart============================================================
							anchor1 = drawing1.createAnchor(0, 0, 0, 0, 0, 6, 12, 22);
							chart = drawing1.createChart(anchor1);
							chart.setTitleText("");
							chart.setTitleOverlay(false);

							// create data sources
							int numOfPoints = dataExports.size();
							// dummy 0-values for the pad data source
							Double[] dummyValuesForPad = new Double[numOfPoints];
							for (int i = 0; i < numOfPoints; i++) {
								dummyValuesForPad[i] = 0d;
							}
							XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 0, 0));
							XDDFNumericalDataSource<Double> valuesData = XDDFDataSourcesFactory.fromNumericCellRange(dataSheet,
									new CellRangeAddress(0, numOfPoints - 1, 1, 1));
							
							for (int i = 0; i < numOfPoints; i++) {
								XSSFRow row = dataSheet.getRow(i);
								if (row == null)
									row = dataSheet.createRow(i);
								XSSFCell cell = row.createCell(255);
								cell.setCellValue(0);
							}

							
							// create axis
							XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
							if (bottomAxis.hasNumberFormat()) bottomAxis.setNumberFormat("@");
							
							XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
							leftAxis.setTitle("kWh");
							leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
							leftAxis.setCrossBetween(AxisCrossBetween.MIDPOINT_CATEGORY);
							leftAxis.setMinimum(0);
							XDDFLineProperties lineProperties = new XDDFLineProperties();
							byte[] color = {(byte) 240, (byte) 240, (byte) 240};
							lineProperties.setFillProperties(new XDDFSolidFillProperties(XDDFColor.from(color)));
							leftAxis.getOrAddMajorGridProperties().setLineProperties(lineProperties);
							if (leftAxis.hasNumberFormat()) leftAxis.setNumberFormat("#,##0.00");
				
							// create data and series
							XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
							data.setVaryColors(false);
							
							XDDFLineChartData.Series series = (XDDFLineChartData.Series) data.addSeries(categoriesData, valuesData);
							series.setTitle("Actual Generation (kWh)", new CellReference(chartSheet.getSheetName(), 24, 6, true, true));


							chart.plot(data);
							solidLineSeries(data, 0, PresetColor.STEEL_BLUE);
							
				
							// create legend
							XDDFChartLegend legend = chart.getOrAddLegend();
							legend.setPosition(LegendPosition.BOTTOM);
							legend.setOverlay(false);
							
							// Write the output to a file
							String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
							String dir = uploadRootPath() + "/"
									+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
							String fileName = dir + "/Custom-report-" + timeStamp + ".xlsx";
							
							try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
								document.write(fileOut);
								String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName,
										Constants.mailFromContact);

								String msgTemplate = Constants.getMailTempleteByState(16);
								String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "", "", "");
								String mailTo = dataObj.getSubscribers();
								String subject = Constants.getMailSubjectByState(16);

								String tags = "report_custom";
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
	 * @description sent mail daily report in pdf
	 * @author Hung.Bui
	 * @since 2022-12-19
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-pdf-custom-report")
	public Object sentMailPdfCustomReport(@RequestBody ViewReportEntity obj) {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
			String dir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
			String fileName = dir + "/Custom-report-" + timeStamp + ".pdf";
			File file = new File(fileName);
			
			try (
					PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
					Document document = new Document(pdfDocument, PageSize.A3.rotate());
					) {
				
				ReportsService service = new ReportsService();
				ViewReportEntity dataObj = (ViewReportEntity) service.getCustomReport(obj);

				if (dataObj != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat dayFormat = new SimpleDateFormat("MM/dd/yyyy");
					SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");
					SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
					SimpleDateFormat format = null;
					DateTickUnitType dateTickUnitType = null;
					Date dateFrom = dateFormat.parse(obj.getDate_from());
					Date dateTo = dateFormat.parse(obj.getDate_to());
					
					// select format based on intervals
					switch (obj.getData_intervals()) {
						case Constants.DAILY_INTERVAL:
							format = dayFormat;
							dateTickUnitType = DateTickUnitType.DAY;
							break;
							
						case Constants.MONTHLY_INTERVAL:
							format = monthFormat;
							dateTickUnitType = DateTickUnitType.MONTH;
							break;
							
						case Constants.ANNUALLY_INTERVAL:
							format = yearFormat;
							dateTickUnitType = DateTickUnitType.YEAR;
							break;
	
						default:
							format = monthFormat;
							dateTickUnitType = DateTickUnitType.MONTH;
							break;
					}
					
					Calendar calQ = Calendar.getInstance();
					dataObj.setReport_date(dayFormat.format(calQ.getTime()));
					calQ.setTime(dateFrom);
					dataObj.setDate_from(format.format(calQ.getTime()));
					calQ.setTime(dateTo);
					dataObj.setDate_to(format.format(calQ.getTime()));
					List<?> dataExports = dataObj.getDataReports();
					
					// total column: 12
					Table table = new Table(UnitValue.createPercentArray(12)).useAllAvailableWidth();
					table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
					table.setFontSize(8);
					table.setTextAlignment(TextAlignment.CENTER);
					
					Image logoImage = new Image(ImageDataFactory.create(uploadRootPath() + "/reports/logo-report.jpg"));
					logoImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT).scaleToFit(100, 100);
				
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
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObj.getDate_from() + " - " + dataObj.getDate_to()).setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("System Size (kW DC)").setBold().setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(String.valueOf(dataObj.getDc_capacity())).setTextAlignment(TextAlignment.LEFT)));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
					
					// chart
					com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(16, 12);
					table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
					// empty row
					table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
					
					// header of data table
					table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setBorder(Border.NO_BORDER));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Timestamp").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Actual Generation (kWh)").setBold()));
					table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setBorder(Border.NO_BORDER));
					// data table
					DecimalFormat dfs = new DecimalFormat("###,###");
					for (int i = 0; i < dataExports.size(); i++) {
						Map<String, Object> item = (Map<String, Object>) dataExports.get(i);
						Double itemActual = Double.parseDouble(item.get("actual").toString());
						String itemCategoryTime = item.get("categories_time").toString();
						
						String actual = itemActual < 0 ? "0" :  itemActual == 0.001 ? "" : dfs.format(itemActual).toString();

						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(itemCategoryTime)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(actual)));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setBorder(Border.NO_BORDER));
					}
					
					//====== chart ============================================================
					final float tickMarkLength = 5;
					final float tickMarkStroke = 1;
					final float seriesStroke = 2;
					
					TimeSeries powerSeries = new TimeSeries("Actual Generation (kWh)");
					
					TimeSeriesCollection powerDataset = new TimeSeriesCollection(powerSeries);
					
					JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "", powerDataset);
					
					// configure plot
					XYPlot plot = chart.getXYPlot();
					plot.setBackgroundPaint(Color.white);
					plot.setRangeGridlinePaint(Color.gray);
					// remove gap between plot and axis
					plot.setAxisOffset(new RectangleInsets(0,0,0,0));
					
					// configure horizontal axis
					DateAxis domainAxis= (DateAxis) plot.getDomainAxis();
					domainAxis.setDateFormatOverride(format);
					domainAxis.setTickMarkInsideLength(tickMarkLength);
					domainAxis.setTickMarkOutsideLength(tickMarkLength);
					domainAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					domainAxis.setTickUnit(new DateTickUnit(dateTickUnitType, (int) Math.ceil((double) dataExports.size() / 15)));
					
					// dataset
					for ( int i = 0; i < dataExports.size(); i++ ) {
						Map<String, Object> item = (Map<String, Object>) dataExports.get(i);
						Double itemActual = Double.parseDouble(item.get("actual").toString());
						String itemCategoryTime = item.get("categories_time").toString();

						double actual = itemActual <= 0 ? 0 : (itemActual == 0.001 ? 0 : itemActual);
						
						RegularTimePeriod period = null;
						switch (obj.getData_intervals()) {
							case Constants.DAILY_INTERVAL:
								period = new Day(format.parse(itemCategoryTime));
								break;
								
							case Constants.MONTHLY_INTERVAL:
								period = new Month(format.parse(itemCategoryTime));
								break;
								
							case Constants.ANNUALLY_INTERVAL:
								period = new Year(format.parse(itemCategoryTime));
								break;
		
							default:
								period = new Month(format.parse(itemCategoryTime));
								break;
						}
						powerSeries.add(period, actual);
					}
					
					// power line chart
					XYLineAndShapeRenderer powerRenderer = new XYLineAndShapeRenderer(true, false);
					powerRenderer.setSeriesPaint(0, new Color(49, 119, 168));
					powerRenderer.setSeriesStroke(0, new BasicStroke(seriesStroke));
					
					NumberAxis powerAxis = new NumberAxis("kWh");
					powerAxis.setTickMarkInsideLength(tickMarkLength);
					powerAxis.setTickMarkOutsideLength(tickMarkLength);
					powerAxis.setTickMarkStroke(new BasicStroke(tickMarkStroke));
					
					plot.setRenderer(0, powerRenderer);
					plot.setRangeAxis(0, powerAxis);
					plot.setDataset(0, powerDataset);
					plot.mapDatasetToRangeAxis(0, 0);
					
					// plot and return image
					chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1800, 700), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 700));

					// Write the output to a file
					document.add(table);
					// It must be closed before attach to mail
					document.close();
					
				    String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
				    String msgTemplate = Constants.getMailTempleteByState(16);
				    String body = String.format(msgTemplate, dataObj.getSite_name(), dataObj.getId_site(), "Customer", "", "", "");
				    String mailTo = dataObj.getSubscribers();
				    String subject = Constants.getMailSubjectByState(16);
				    
				    String tags = "report_custom";
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
	
	private static XDDFCategoryAxis createCategoryAxis(XDDFChart chart, boolean isVisible) {
		XDDFCategoryAxis axis = chart.createCategoryAxis(AxisPosition.BOTTOM);
		axis.setMajorTickMark(AxisTickMark.OUT);
		axis.setVisible(isVisible);
		if (axis.hasNumberFormat()) axis.setNumberFormat("@");
		
		return axis;
	}
	
	private static XDDFValueAxis createLeftValueAxis(XDDFChart chart) {
		XDDFValueAxis axis = chart.createValueAxis(AxisPosition.LEFT);
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
	
	private static XDDFValueAxis createRightValueAxis(XDDFChart chart, XDDFCategoryAxis bottomAxis) {
		XDDFValueAxis axis = chart.createValueAxis(AxisPosition.RIGHT);
		axis.setCrosses(AxisCrosses.MAX);
		axis.setCrossBetween(AxisCrossBetween.BETWEEN);
		axis.setMajorTickMark(AxisTickMark.NONE);
		
		XDDFLineProperties properties = new XDDFLineProperties(new XDDFNoFillProperties());
		axis.getOrAddShapeProperties().setLineProperties(properties);
		
		// set correct cross axis
		bottomAxis.crossAxis(axis);
		axis.crossAxis(bottomAxis);
		
		if (axis.hasNumberFormat()) axis.setNumberFormat("#,##0.00");
		
		return axis;
	}

	private static void solidFillSeries(XDDFChartData data, int index, PresetColor color) {
		XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
		XDDFChartData.Series series = data.getSeries(index);
		XDDFShapeProperties properties = series.getShapeProperties();
		if (properties == null) properties = new XDDFShapeProperties();
		properties.setFillProperties(fill);
		series.setShapeProperties(properties);
	}
	
	private static void solidFillSeries(XDDFChartData data, int index, PresetColor color, PresetColor borderColor) {
		XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
		XDDFChartData.Series series = data.getSeries(index);
		XDDFShapeProperties properties = series.getShapeProperties();
		if (properties == null) properties = new XDDFShapeProperties();
		properties.setFillProperties(fill);
		
		fill.setColor(XDDFColor.from(borderColor));
		XDDFLineProperties borderProperties = new XDDFLineProperties(fill);
		properties.setLineProperties(borderProperties);
		series.setShapeProperties(properties);
	}

	private static void solidLineSeries(XDDFChartData data, int index, PresetColor color) {
		XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
		XDDFLineProperties line = new XDDFLineProperties(fill);
		XDDFLineChartData.Series series = (XDDFLineChartData.Series) data.getSeries(index);
		XDDFShapeProperties properties = series.getShapeProperties();
		if (properties == null) {
			properties = new XDDFShapeProperties();
		}
		properties.setLineProperties(line);
		series.setShapeProperties(properties);
		series.setSmooth(false);
		series.setMarkerStyle(MarkerStyle.NONE);
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

//	private static void solidLineSeries(XDDFChartData data, int index, PresetColor color) {
//		XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
//		XDDFLineProperties line = new XDDFLineProperties();
//		line.setFillProperties(fill);
//		XDDFChartData.Series series = data.getSeries().get(index);
//		XDDFShapeProperties properties = series.getShapeProperties();
//		if (properties == null) {
//			properties = new XDDFShapeProperties();
//		}
//		properties.setLineProperties(line);
//		series.setShapeProperties(properties);
//	}

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

	// Create dummy data
	private static List<Book> getBooks() {
		List<Book> listBook = new ArrayList<>();
		Book book;
		for (int i = 1; i <= 5; i++) {
			book = new Book(i, "Book " + i, i * 2, i * 1000);
			listBook.add(book);
		}
		return listBook;
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
		return cellStyle;
	}
	
	// Create CellStyle for table row
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
		cellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(noDecimalDataFormat));
		return cellStyle;
	}
	
	// Create CellStyle for no border table row
	private static CellStyle createStyleForNoBorderTableRow(Sheet sheet) {
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
	private static void writeHeaderMonthlyReport(Sheet sheet, int rowIndex, ViewReportEntity dataObj ) {
		try {
			
			
			sheet.autoSizeColumn(12);
			
			
			DecimalFormat df = new DecimalFormat("###,###.0");
			DecimalFormat dfa = new DecimalFormat("###,###");
			// create CellStyle
			Font fontDef = sheet.getWorkbook().createFont();
			fontDef.setFontName("Times New Roman");
			fontDef.setFontHeightInPoints((short) 12); // font size
						
			CellStyle cellStyle = createStyleForHeader(sheet);
			cellStyle.setFont(fontDef);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			
			// create CellStyle title
			CellStyle cellStyleTitle = createStyleForHeader(sheet);
			cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleTitle.setAlignment(HorizontalAlignment.LEFT);
			
			cellStyleTitle.setBorderBottom(BorderStyle.THIN);
			cellStyleTitle.setBorderTop(BorderStyle.THIN);
			cellStyleTitle.setBorderRight(BorderStyle.THIN);
			cellStyleTitle.setBorderLeft(BorderStyle.THIN);
			cellStyleTitle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyleTitle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyleTitle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyleTitle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			
			
			// Create style 
			Font fontRowB = sheet.getWorkbook().createFont();
			fontRowB.setFontName("Times New Roman");
			fontRowB.setBold(true);
			fontRowB.setColor(IndexedColors.BLACK.getIndex()); // text color
			fontRowB.setFontHeightInPoints((short) 12); // font size
			// Create CellStyle
			CellStyle cellStyleItemB = sheet.getWorkbook().createCellStyle();
			cellStyleItemB.setFont(fontRowB);
			cellStyleItemB.setWrapText(true);
			cellStyleItemB.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleItemB.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleItemB.setAlignment(HorizontalAlignment.LEFT);
			cellStyleItemB.setBorderBottom(BorderStyle.THIN);
			cellStyleItemB.setBorderTop(BorderStyle.THIN);
			cellStyleItemB.setBorderRight(BorderStyle.THIN);
			cellStyleItemB.setBorderLeft(BorderStyle.THIN);
			cellStyleItemB.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyleItemB.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyleItemB.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyleItemB.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			

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

			Row row1 = sheet.createRow(0);
			row1.setHeight((short) 600);
			Cell cell = row1.createCell(0);
			cell.setCellStyle(cellStyleItemB);
			cell.setCellValue("Site Name");

			cell = row1.createCell(1);
			cell.setCellStyle(cellStyleItemB);
			cell.setCellValue(dataObj.getSite_name());

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
			
			sheet.addMergedRegion(new CellRangeAddress(0, 5, 11, 13));
			
			cellStyleCustom.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellStyleCustom.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleCustom.setAlignment(HorizontalAlignment.CENTER);
			cell = row1.createCell(2);
			cell.setCellStyle(cellStyleCustom);
			cell.setCellValue("MONTHLY PRODUCTION REPORT");


			// Create font
			Font font11 = sheet.getWorkbook().createFont();
			font11.setFontName("Times New Roman");
			font11.setBold(true);
			font11.setFontHeightInPoints((short) 12); // font size
			font11.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleCustom11 = sheet.getWorkbook().createCellStyle();
			cellStyleCustom11.setFont(font11);
			
			cellStyleCustom11.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleCustom11.setAlignment(HorizontalAlignment.CENTER);
			cellStyleCustom11.setWrapText(true);
			
			cellStyleCustom11.setBorderBottom(BorderStyle.THIN);
			cellStyleCustom11.setBorderTop(BorderStyle.THIN);
			cellStyleCustom11.setBorderRight(BorderStyle.THIN);
			cellStyleCustom11.setBorderLeft(BorderStyle.THIN);
			cellStyleCustom11.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCustom11.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCustom11.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyleCustom11.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			

			Row row2 = sheet.createRow(1);
			row2.setHeight((short) 600);
			Cell cell2 = row2.createCell(0);
			cell2.setCellStyle(cellStyleItemB);
			cell2.setCellValue("Report Date");

			cell2 = row2.createCell(1);
			cell2.setCellStyle(cellStyleTitle);
			cell2.setCellValue(dataObj.getReport_date());

			Row row3 = sheet.createRow(2);
			row3.setHeight((short) 600);
			Cell cell3 = row3.createCell(0);
			cell3.setCellStyle(cellStyleItemB);
			cell3.setCellValue("Covered Period");

			cell3 = row3.createCell(1);
			cell3.setCellStyle(cellStyleTitle);
			cell3.setCellValue(dataObj.getStart_date() + " - " + dataObj.getEnd_date());

			Row row4 = sheet.createRow(3);
			row4.setHeight((short) 600);
			Cell cell4 = row4.createCell(0);
			cell4.setCellStyle(cellStyleItemB);
			cell4.setCellValue("System Size (kW DC)");

			cell4 = row4.createCell(1);
			cell4.setCellStyle(cellStyleTitle);
			cell4.setCellValue(dataObj.getDc_capacity());


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
			cellStyle6.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
			cellStyle6.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle6.setVerticalAlignment(VerticalAlignment.CENTER);

			Row row6 = sheet.createRow(6);
			Cell cell6 = row6.createCell(0);
			cell6.setCellStyle(cellStyle6);
			cell6.setCellValue("Performance Reporting");

			// Monthly Data
			Row row7 = sheet.createRow(8);
			Cell cell7 = row7.createCell(0);
			cell7.setCellStyle(cellStyleCustom11);
			cell7.setCellValue("Date");
			cell7.getRow().setHeight((short) 1000);
			
			Cell cell71 = row7.createCell(1);
			cell71.setCellStyle(cellStyleCustom11);
			cell71.setCellValue("Actual Generation (kWh)");
			
			Cell cell72 = row7.createCell(2);
			cell72.setCellStyle(cellStyleCustom11);
			cell72.setCellValue("Estimated Generation (kWh)");
			
			Cell cell73 = row7.createCell(3);
			cell73.setCellStyle(cellStyleCustom11);
			cell73.setCellValue("Estimated Generation Index (%)");
			
			
			List<?> dataExports = dataObj.getDataReports();
			double totalActual = 0;
			double totalEstimated = 0;
			if(dataExports.size() > 0) {
				// Create font
				Font fontR = sheet.getWorkbook().createFont();
				fontR.setFontName("Times New Roman");
				fontR.setBold(false);
				fontR.setFontHeightInPoints((short) 12); // font size
				fontR.setColor(IndexedColors.BLACK.getIndex()); // text color
				// Create CellStyle
				CellStyle cellStyleRow = sheet.getWorkbook().createCellStyle();
				cellStyleRow.setFont(fontR);
				cellStyleRow.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleRow.setAlignment(HorizontalAlignment.CENTER);
				
				
				cellStyleRow.setBorderBottom(BorderStyle.THIN);
				cellStyleRow.setBorderTop(BorderStyle.THIN);
				cellStyleRow.setBorderRight(BorderStyle.THIN);
				cellStyleRow.setBorderLeft(BorderStyle.THIN);
				cellStyleRow.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleRow.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleRow.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				cellStyleRow.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
				
				
				int r = 9;
				for( int j = 0; j < dataExports.size(); j++){
					MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(j);
					String categoriesTime = (String) item.getCategories_time();
					
					Row row7f = sheet.createRow(r+j);
					Cell cell7f = row7f.createCell(0);
					cell7f.setCellStyle(cellStyleRow);
					cell7f.setCellValue(categoriesTime);
					
					Cell cell71f = row7f.createCell(1);
					cell71f.setCellStyle(cellStyleRow);
					cell71f.setCellValue(dfa.format(item.getActual()) );
					
					Cell cell72f = row7f.createCell(2);
					cell72f.setCellStyle(cellStyleRow);
					cell72f.setCellValue(dfa.format(item.getEstimated()));
					
					Cell cell73f = row7f.createCell(3);
					cell73f.setCellStyle(cellStyleRow);
					
					cell73f.setCellValue(df.format(item.getPercent()));
					
					totalActual = totalActual + item.getActual();
					totalEstimated = totalEstimated + item.getEstimated();
				}
			}
			
			// Create font
			Font fontF = sheet.getWorkbook().createFont();
			fontF.setFontName("Times New Roman");
			fontF.setBold(true);
			fontF.setFontHeightInPoints((short) 12); // font size
			fontF.setColor(IndexedColors.BLACK.getIndex()); // text color
			// Create CellStyle
			CellStyle cellStyleF = sheet.getWorkbook().createCellStyle();
			cellStyleF.setFont(fontF);
			
			cellStyleF.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleF.setAlignment(HorizontalAlignment.CENTER);
			cellStyleF.setWrapText(true);
			cellStyleF.setBorderTop(BorderStyle.DOUBLE);
			cellStyleF.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
			
			
			Row row8 = sheet.createRow(41);
			Cell cell8 = row8.createCell(0);
			cell8.setCellStyle(cellStyleF);
			cell8.setCellValue("Total");
			
			Cell cell81 = row8.createCell(1);
			cell81.setCellStyle(cellStyleF);
			cell81.setCellValue(dfa.format(totalActual));
			
			Cell cell82 = row8.createCell(2);
			cell82.setCellStyle(cellStyleF);
			cell82.setCellValue(dfa.format(totalEstimated));
			
			Cell cell83 = row8.createCell(3);
			cell83.setCellStyle(cellStyleF);
			cell83.setCellValue(df.format((totalActual / totalEstimated) * 100));
			
		} catch (Exception e) {
		}

	}
	
	private static void writeHeaderAssetManagementAndOperationPerformanceReport(Sheet sheet, ViewReportEntity dataObj, int totalColumns, String title) {
		try {
			sheet.setDefaultColumnWidth(16);
			sheet.setDisplayGridlines(false);
			
			Font reportTitleFont = sheet.getWorkbook().createFont();
			reportTitleFont.setFontName("Times New Roman");
			reportTitleFont.setBold(true);
			reportTitleFont.setFontHeightInPoints((short) 22);
			reportTitleFont.setColor(IndexedColors.BLACK.getIndex());
			
			CellStyle reportTitleCellStyle = sheet.getWorkbook().createCellStyle();
			reportTitleCellStyle.setFont(reportTitleFont);
			reportTitleCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			reportTitleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			reportTitleCellStyle.setAlignment(HorizontalAlignment.CENTER);
			
			Font reportInfoFont = sheet.getWorkbook().createFont();
			reportInfoFont.setFontName("Times New Roman");
			reportInfoFont.setFontHeightInPoints((short) 12);
			reportInfoFont.setColor(IndexedColors.BLACK.getIndex());
			
			CellStyle reportInfoCellStyle = sheet.getWorkbook().createCellStyle();
			reportInfoCellStyle.setFont(reportInfoFont);
			reportInfoCellStyle.setWrapText(true);
			reportInfoCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			reportInfoCellStyle.setAlignment(HorizontalAlignment.LEFT);
			reportInfoCellStyle.setBorderBottom(BorderStyle.THIN);
			reportInfoCellStyle.setBorderTop(BorderStyle.THIN);
			reportInfoCellStyle.setBorderRight(BorderStyle.THIN);
			reportInfoCellStyle.setBorderLeft(BorderStyle.THIN);
			reportInfoCellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			reportInfoCellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			reportInfoCellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			reportInfoCellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
			
			Font reportInfoBoldFont = sheet.getWorkbook().createFont();
			reportInfoBoldFont.setFontName("Times New Roman");
			reportInfoBoldFont.setFontHeightInPoints((short) 12);
			reportInfoBoldFont.setColor(IndexedColors.BLACK.getIndex());
			reportInfoBoldFont.setBold(true);
			
			CellStyle reportInfoBoldCellStyle = sheet.getWorkbook().createCellStyle();
			reportInfoBoldCellStyle.cloneStyleFrom(reportInfoCellStyle);
			reportInfoBoldCellStyle.setFont(reportInfoBoldFont);
			
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellStyle(reportTitleCellStyle);
			cell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, totalColumns - 1));

			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellStyle(reportInfoCellStyle);
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
			cell.setCellStyle(reportInfoCellStyle);
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
			cell.setCellStyle(reportInfoCellStyle);
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
			CellStyle tableHeaderCellStyle = createStyleForTableHeader(sheet);
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRow(sheet);
			CellStyle tableRowOneDecimalPlaceCellStyle = createStyleForTableRow(sheet);
			tableRowOneDecimalPlaceCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceDataFormat));
			CellStyle tableRowOneDecimalPlaceWithPercentageCellStyle = createStyleForTableRow(sheet);
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
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
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
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRow(sheet);
			CellStyle tableRowOneDecimalPlaceWithPercentageCellStyle = createStyleForTableRow(sheet);
			tableRowOneDecimalPlaceWithPercentageCellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(oneDecimalPlaceWithPercentageDataFormat));
			CellStyle tableRowNoDecimalNoBorderCellStyle = createStyleForNoBorderTableRow(sheet);
			CellStyle tableRowOneDecimalPlaceWithPercentageNoBorderCellStyle = createStyleForNoBorderTableRow(sheet);
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
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
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
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRow(sheet);
			CellStyle tableRowNoDecimaLeftAlignlCellStyle = createStyleForTableRow(sheet);
			tableRowNoDecimaLeftAlignlCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCurrencyCellStyle = createStyleForTableRow(sheet);
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
					tableCell.setCellStyle(tableRowNoDecimaLeftAlignlCellStyle);
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
			CellStyle tableRowNoDecimalCellStyle = createStyleForTableRow(sheet);
			CellStyle tableRowNoDecimaLeftAlignlCellStyle = createStyleForTableRow(sheet);
			tableRowNoDecimaLeftAlignlCellStyle.setAlignment(HorizontalAlignment.LEFT);
			CellStyle tableRowNoDecimalCurrencyCellStyle = createStyleForTableRow(sheet);
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
					tableCell.setCellStyle(tableRowNoDecimaLeftAlignlCellStyle);
					if(item.getEvent() != null) tableCell.setCellValue(item.getEvent());
					tableCell = tableRow.createCell(1);
					tableCell.setCellStyle(tableRowNoDecimaLeftAlignlCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(r+j, r+j, 0, 1));
					
					tableCell = tableRow.createCell(2);
					tableCell.setCellStyle(tableRowNoDecimaLeftAlignlCellStyle);
					if(item.getDevicename() != null) tableCell.setCellValue(item.getDevicename());
					tableCell = tableRow.createCell(3);
					tableCell.setCellStyle(tableRowNoDecimaLeftAlignlCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(r+j, r+j, 2, 3));
					
					tableCell = tableRow.createCell(4);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
					if(item.getStartTime() != null) tableCell.setCellValue(item.getStartTime());
					
					tableCell = tableRow.createCell(5);
					tableCell.setCellStyle(tableRowNoDecimalCellStyle);
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
	
//	@PostMapping("/view-report")
//	public Object delete(@Valid @RequestBody ReportsEntity obj) {
//		ReportsService service = new ReportsService();
//		try {
//			boolean result = service.deleteReports(obj);
//			if (result) {
//				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
//			}
//			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
//		} catch (Exception e) {
//			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
//		}
//	}
//	

//	/**
//	 * @description update Employee status
//	 * @author long.pham
//	 * @since 2021-01-08
//	 * @param id
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/add-manage-site-by-employee")
//	public Object addEmployeeManageSite(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			if(obj.getId_site() > 0 && obj.getId_employee() > 0 ) {
//				int checkExits = service.checkExitsManageSite(obj);
//				if(checkExits <= 0) {
//					// Insert
//					SiteEntity data = service.insertSiteEmployeeMap(obj);
//					return this.jsonResult(true,  Constants.SAVE_SUCCESS_MSG , data, 1);
//				} else {
//					// Delete
//					service.deleteSiteEmployeeMap(obj);
//					return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
//				}
//			} else {
//				return this.jsonResult(false,  Constants.GET_ERROR_MSG , null, 0);
//			}
//			
//			
//		} catch (Exception e) {
//			// log error
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	

//	
//	
//	/**
//	 * @description update site status
//	 * @author long.pham
//	 * @since 2021-01-11
//	 * @param id
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/update-status")
//	public Object updateStatus(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			service.updateStatus(obj);
//			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
//		} catch (Exception e) {
//			// log error
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	

//	
//	
//	/**
//	 * @description Get all site by id_customer
//	 * @author long.pham
//	 * @since 2020-10-08
//	 * @param id_customer
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/all-by-employee")
//	public Object getAllSiteByEmployee(@RequestBody SiteEntity site) {
//		try {
//			SiteService service = new SiteService();
//			List data = service.getAllSiteByEmployee(site);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	
//	/**
//	 * @description Get all site by id_customer
//	 * @author long.pham
//	 * @since 2020-10-08
//	 * @param id_customer
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/all")
//	public Object getAllCompany(@RequestBody SiteEntity site) {
//		try {
//			SiteService service = new SiteService();
//			List data = service.getAllSite(site);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//
//	/**
//	 * @description Get list site by id_customer
//	 * @author long.pham
//	 * @since 2020-10-21
//	 * @param id_customer
//	 * @return data (status, message, array, total_row
//	 */
//
//	@PostMapping("/get-summary-site-by-customer")
//	public Object getSummarySiteByCustomerId(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			SiteEntity getSiteCustomer = service.getSiteCustomerById(obj.getId_customer());
//			if (getSiteCustomer != null) {
//				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getSiteCustomer, 1);
//			} else {
//				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
//			}
//		} catch (Exception e) {
//			// log error
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	
//	/**
//	 * @description Get detail site 
//	 * @author long.pham
//	 * @since 2020-10-22
//	 * @param id_customer, id_site
//	 * @return data (status, message, object, total_row
//	 */
//
//	@PostMapping("/detail")
//	public Object getDetailSite(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			SiteEntity getDetailSite = service.getDetailSite(obj);
//			if (getDetailSite != null) {
//				List dataActiveAlarm = service.getActiveAlarm(obj);
//				getDetailSite.setActiveAlarm(dataActiveAlarm);
//				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetailSite, 1);
//			} else {
//				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
//			}
//		} catch (Exception e) {
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	
//	/**
//	 * @description Get chart kpi by site
//	 * @author long.pham
//	 * @since 2020-10-08
//	 * @param id_customer
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/get-chart-kpi-day")
//	public Object getChartKPIDay(@RequestBody SiteEntity obj) {
//		try {
//
//			SiteService service = new SiteService();
//			String KPIType = obj.getKpi_type();
//			switch(KPIType) {
//			  case "month":
//				  List dataMonth = service.getChartKPIMonth(obj);
//				  obj.setEnergy(dataMonth);
//				  
//			    break;
//			  case "year":
//				  List dataYear = service.getChartKPIYear(obj);
//				  obj.setEnergy(dataYear);
//				  
//			    break;
//			  default:
//				  List dataIrradiance = service.getChartKPIDayIrradiance(obj);
//				  obj.setIrradiance(dataIrradiance);
//				  
//				  List dataPower = service.getChartKPIDayPower(obj);
//				  obj.setPower(dataPower);
//				  
//				  List dataEnergy = service.getChartKPIDayEnergy(obj);
//				  obj.setEnergy(dataEnergy);
//				  
//			}
//			
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	
//	/**
//     * @description  Update site information
//     * @author long.pham
//     * @since 2020-10-08
//     * @Params id_site, id_customer
//     */
//	@PostMapping("/update")
//	public Object save (HttpServletRequest request,@Valid @RequestBody SiteEntity site) {
//		SiteService service = new SiteService();
//		try {
//			String fileName = "";
//			String saveDir = "";
//			if(!Lib.isBlank(site.getFile_upload())) {
//				saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKey);
//				fileName = randomAlphabetic(16);
//				String saveFileName = Lib.uploadFromBase64(site.getFile_upload(), fileName, saveDir);
//				site.setGallery(Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKey)+"/"+saveFileName);
//			}
//			if(service.updateSiteInformation(site)) {
//				return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, site, 1);
//			}
//			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
//		} catch (Exception e) {
//			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, e, 0);
//		}
//		
//	}
//	
//	
//	/**
//	 * @description Get list data report quick query
//	 * @author long.pham
//	 * @since 2020-11-09
//	 * @param id_site, id_customer, id_device
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/report-quick-query")
//	public Object getReportQuickQuery(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			List data = service.reportQuickQuery(obj);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//
//	
//	/**
//	 * @description Get list data specific yield month
//	 * @author long.pham
//	 * @since 2020-11-10
//	 * @param id_site, id_customer
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/specific-yield-month")
//	public Object getSpecificYieldMonth(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			List data = service.getSpecificYieldMonth(obj);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	/**
//	 * @description Get list data specific yield month
//	 * @author long.pham
//	 * @since 2020-11-10
//	 * @param id_site, id_customer
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/specific-yield-year")
//	public Object getSpecificYieldYear(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			List data = service.getSpecificYieldYear(obj);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	
//	
//	/**
//	 * @description Get daily report
//	 * @author long.pham
//	 * @since 2020-11-11
//	 * @param id_customer, id_site, start_date, end_date
//	 * @return data (status, message, object, total_row
//	 */
//
//	@PostMapping("/daily-report")
//	public Object getDailyReportSumary(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			Object getDailyReport = service.getDailyReportSumary(obj);
//			if (getDailyReport != null) {
//				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDailyReport, 1);
//			} else {
//				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
//			}
//		} catch (Exception e) {
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	/**
//	 * @description Get list data specific yield month
//	 * @author long.pham
//	 * @since 2020-11-10
//	 * @param id_site, id_customer, start_date, end_date
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/daily-report-chart")
//	public Object getDailyReportChart(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			List data = service.getDailyReportChart(obj);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	
//	
//	/**
//	 * @description get list data report visualization device
//	 * @author long.pham
//	 * @since 2020-11-12
//	 * @param id_site, id_customer, id_device, start_date, end_date
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/report-visualization-device")
//	public Object getReportVisualizationDevice(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			List data = service.getReportVisualizationDevice(obj);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	/**
//	 * @description get list data report visualization device
//	 * @author long.pham
//	 * @since 2020-11-12
//	 * @param id_site, id_customer, id_device, start_date, end_date
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/report-visualization-device-by-day")
//	public Object getReportVisualizationDeviceDay(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			List data = service.getReportVisualizationDeviceDay(obj);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	
//	/**
//	 * @description get list annual comparison
//	 * @author long.pham
//	 * @since 2020-11-12
//	 * @param id_site, id_customer, id_device, start_date, end_date
//	 * @return data (status, message, array, total_row
//	 */
//	@PostMapping("/annual-comparison")
//	public Object getAnnualComparison(@RequestBody SiteEntity obj) {
//		try {
//			SiteService service = new SiteService();
//			List data = service.getAnnualComparison(obj);
//			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
//	

}
