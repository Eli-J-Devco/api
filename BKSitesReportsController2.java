/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
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
import org.apache.poi.ss.util.CellRangeAddress;
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
import org.apache.poi.xddf.usermodel.chart.BarGrouping;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.Book;
import com.nwm.api.entities.ReportsEntity;
import com.nwm.api.entities.SitesReportsEntity;
//import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
//import com.nwm.api.services.CustomerViewService;
import com.nwm.api.services.ReportsService;
import com.nwm.api.services.SitesReportsService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;
//import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/sites-reports")
public class BKSitesReportsController2 extends BaseController {

	public static final int COLUMN_INDEX_ID = 0;
	public static final int COLUMN_INDEX_TITLE = 1;
	public static final int COLUMN_INDEX_PRICE = 2;
	public static final int COLUMN_INDEX_QUANTITY = 3;
	public static final int COLUMN_INDEX_TOTAL = 4;
	private static CellStyle cellStyleFormatNumber = null;


	/**
	 * @description save customer
	 * @author long.pham
	 * @since 2021-01-05
	 * @param screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody ReportsEntity obj) {
		try {
			ReportsService service = new ReportsService();
			if (obj.getScreen_mode() == 1) {
				ReportsEntity data = service.insertReports(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					boolean insert = service.updateReports(obj);
					if (insert == true) {
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
	public Object getList(@RequestBody SitesReportsEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			SitesReportsService service = new SitesReportsService();
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
				series.setTitle("Baseline Generation Index (%)", new CellReference(chartSheet.getSheetName(), 8, 0, true, true));
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
//	        System.out.println("Got Repsonse");
//	        System.out.println(response);

//			if (result) {
//				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
//			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
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
		System.out.println("Done!!!");
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
			fontRow.setFontHeightInPoints((short) 14); // font size
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
			fontBold.setFontHeightInPoints((short) 14); // font size
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
			font11.setFontHeightInPoints((short) 14); // font size
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
			font6.setFontHeightInPoints((short) 14); // font size
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
			System.out.println(e);
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
		font.setFontHeightInPoints((short) 14); // font size
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
