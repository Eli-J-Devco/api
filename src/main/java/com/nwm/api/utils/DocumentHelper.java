package com.nwm.api.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.TimeSeriesCollection;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTDispBlanksAs;
import org.openxmlformats.schemas.drawingml.x2006.chart.STDispBlanksAs;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;

public class DocumentHelper {
	
	public static final String noDecimalDataFormat = "###,##0";
	public static final String oneDecimalPlaceDataFormat = "###,##0.0";
	public static final String twoDecimalPlaceDataFormat = "###,##0.00";
	public static final String fourDecimalPlaceDataFormat = "###,##0.0000";
	public static final String oneDecimalPlaceWithPercentageDataFormat = "###,##0.0%";
	public static final String noDecimalCurrencyDataFormat = "$###,##0";
	public static final Color BLUE_COLOR = new Color(49, 119, 168);
	public static final Color LIGHT_BLUE_COLOR = new Color(109, 189, 246);
	public static final Color ORANGE_COLOR = new Color(255, 129, 39);
	
	private static String uploadRootPath() {
		return Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey);
	}
	
	public static int readLogoImageFile(XSSFWorkbook workbook) throws IOException {
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
	
	public static Image readLogoImageFile() throws Exception {
		Image logoImage = new Image(ImageDataFactory.create(uploadRootPath() + "/reports/logo-report.jpg"));
		logoImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.RIGHT).scaleToFit(100, 100);
		
		return logoImage;
	}
	
	public static void insertLogo(XSSFSheet sheet, ClientAnchor anchor, int pictureIndex) {
		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
		drawing.createPicture(anchor, pictureIndex);
	}
	
	public static XDDFChart insertChart(XSSFSheet sheet, ClientAnchor anchor, String title) {
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
	
	public static XDDFCategoryAxis createCategoryAxis(XDDFChart chart) {
		XDDFCategoryAxis axis = chart.createCategoryAxis(AxisPosition.BOTTOM);
		axis.setMajorTickMark(AxisTickMark.OUT);
		if (axis.hasNumberFormat()) axis.setNumberFormat("@");
		
		return axis;
	}
	
	public static XDDFValueAxis createLeftValueAxis(XDDFChart chart, String title) {
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
	
	public static XDDFValueAxis createRightValueAxis(XDDFChart chart, XDDFCategoryAxis bottomAxis, String title) {
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
	
	public static XDDFChartData createChartData(XDDFChart chart, ChartTypes type, XDDFCategoryAxis categoryAxis, XDDFValueAxis valueAxis) {
		XDDFChartData chartData = chart.createData(type, categoryAxis, valueAxis);
		chartData.setVaryColors(false);
		if (type == ChartTypes.BAR) ((XDDFBarChartData) chartData).setBarDirection(BarDirection.COL);
		
		return chartData;
	}
	
	public static Series addSeries(boolean isDataEmpty, XDDFChartData chartData, XDDFDataSource<String> categories, XDDFNumericalDataSource<Double> value, String name, XDDFColor color, XDDFColor borderColor) {
		if (categories == null || value == null) return null;
		
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
		
		return series;
	}
	
	// set default colors by system
	public static Series addSeries(boolean isDataEmpty, XDDFChartData chartData, XDDFDataSource<String> categories, XDDFNumericalDataSource<Double> value, String name) {
		if (categories == null || value == null) return null;
		
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
		
		return series;
	}

	private static void solidFillSeries(Series series, XDDFColor color, XDDFColor borderColor) {
		XDDFFillProperties fill = color != null ? new XDDFSolidFillProperties(color) : new XDDFNoFillProperties();
		XDDFShapeProperties properties = new XDDFShapeProperties();
		
		if (series.getClass() == XDDFLineChartData.Series.class) {
			XDDFLineProperties lineProperties = new XDDFLineProperties(fill);
			properties.setLineProperties(lineProperties);
			
			((XDDFLineChartData.Series) series).setSmooth(false);
			((XDDFLineChartData.Series) series).setMarkerStyle(MarkerStyle.NONE);
		} else if (series.getClass() == XDDFBarChartData.Series.class) {
			properties.setFillProperties(fill);
			
			if (borderColor != null) {
				XDDFFillProperties borderFill = new XDDFSolidFillProperties(borderColor);
				XDDFLineProperties borderProperties = new XDDFLineProperties(borderFill);
				properties.setLineProperties(borderProperties);
			}
		}
		
		series.setShapeProperties(properties);
	}
	
	public static void solidFillLineMarker(XDDFChart chart, Series series, int seriesIndex, MarkerStyle markerStyle, XDDFColor color) {
		((XDDFLineChartData.Series) series).setMarkerStyle(markerStyle != null ? markerStyle : MarkerStyle.NONE);
		if (color == null) return;
		XDDFShapeProperties propertiesMarker = new XDDFShapeProperties();
		propertiesMarker.setFillProperties(new XDDFSolidFillProperties(color));
		propertiesMarker.setLineProperties(new XDDFLineProperties(new XDDFNoFillProperties()));
        chart.getCTChart().getPlotArea().getLineChartArray(0).getSerArray(seriesIndex).getMarker().addNewSpPr().set(propertiesMarker.getXmlObject());
	}

	public static JFreeChart createJFreeChart(String title) {
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
	
	public static TimeSeriesCollection createJFreeChartBarDataset(int datasetIndex, XYPlot plot) {
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
	
	public static TimeSeriesCollection createJFreeChartLineDataset(int datasetIndex, XYPlot plot, Shape markerShape) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.setXPosition(TimePeriodAnchor.MIDDLE);
		plot.setDataset(datasetIndex, dataset);
		
		XYLineAndShapeRenderer lineRenderer = new XYLineAndShapeRenderer(true, false);
		lineRenderer.setSeriesStroke(0, new BasicStroke(2));
		if (markerShape != null) {
			lineRenderer.setDefaultShapesVisible(true);
			lineRenderer.setSeriesShape(0, markerShape);
			lineRenderer.setDefaultLegendShape(markerShape);
		}
		plot.setRenderer(datasetIndex, lineRenderer);
		
		return dataset;
	}
	
	public static DateAxis createJFreeChartDomainAxis(XYPlot plot, DateTickUnit tickUnit, Date minDate, Date maxDate) {
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
	
	public static NumberAxis createJFreeChartNumberAxis(String title, AxisLocation location, int axisIndex, int datasetIndex, XYPlot plot) {
		NumberAxis numberAxis = new NumberAxis(title);
		numberAxis.setTickMarksVisible(false);
		numberAxis.setAxisLineVisible(false);
		plot.setRangeAxis(axisIndex, numberAxis);
		plot.setRangeAxisLocation(axisIndex, location);
		plot.mapDatasetToRangeAxis(datasetIndex, axisIndex);
		
		return numberAxis;
	}

	// Create CellStyle for report title
	public static CellStyle createStyleForReportTitle(Sheet sheet, short fontSize, boolean isBold) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(isBold);
		font.setFontHeightInPoints((short) fontSize);
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
	public static CellStyle createStyleForReportInfo(Sheet sheet, boolean isBold) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(isBold);
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
	
	// Create CellStyle for table title
	public static CellStyle createStyleForTableTitle(Sheet sheet) {
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
	public static CellStyle createStyleForTableHeader(Sheet sheet) {
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
	public static CellStyle createStyleForTableRow(Sheet sheet, boolean isBold) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(isBold);
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
	public static CellStyle createStyleForTableRowNumber(Sheet sheet, boolean isBold, String dataFormat) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(isBold);
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
		if (dataFormat != null) cellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(dataFormat));
		
		return cellStyle;
	}
	
	// Create CellStyle for no border table row (number)
	public static CellStyle createStyleForNoBorderTableRowNumber(Sheet sheet, boolean isBold, String dataFormat) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(isBold);
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(noDecimalDataFormat));
		if (dataFormat != null) cellStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat(dataFormat));
		
		return cellStyle;
	}
}
