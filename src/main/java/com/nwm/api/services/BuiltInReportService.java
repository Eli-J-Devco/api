/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DateTimeReportDataEntity;
import com.nwm.api.entities.MonthlyProductionTrendReportEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeeklyDateEntity;
import com.nwm.api.utils.Constants.ReportIntervals;
import com.nwm.api.utils.Constants.ReportRange;
import com.nwm.api.utils.DocumentHelper;
import com.nwm.api.utils.Lib;

public class BuiltInReportService extends DB {
	
	ReportsService reportsService = new ReportsService();
	
	/**
	 * @description create date time list
	 * @author Hung.Bui
	 * @since 2024-05-03
	 * @param obj device object
	 * @param start start date time
	 * @param end end date time
	 * @return
	 */
	private <K extends DateTimeReportDataEntity> List<K> getDateTimeList(ViewReportEntity obj, Class<K> clazz) {
		List<K> dateTimeList = new ArrayList<K>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), formatter).withHour(0).withMinute(0).withSecond(0);
		LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), formatter).withHour(23).withMinute(59).withSecond(59);
		
		try {
			int interval = 1;
			DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
			ChronoUnit timeUnit = ChronoUnit.DAYS;
		
			switch (ReportRange.fromValue(obj.getCadence_range())) {
				case LAST_MONTH:
				case MONTHLY:
					switch (obj.getData_intervals()) {
						case 2:
							interval = 15;
							categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:00");
							timeUnit = ChronoUnit.MINUTES;
							break;
						case 6:
							categoryTimeFormat = DateTimeFormatter.ofPattern("MMM-yy");
							timeUnit = ChronoUnit.MONTHS;
							break;
					}
					break;
				case ANNUALLY:
					categoryTimeFormat = DateTimeFormatter.ofPattern("MMM-yy");
					timeUnit = ChronoUnit.MONTHS;
					break;
				case CUSTOM:
					interval = 15;
					categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:00");
					timeUnit = ChronoUnit.MINUTES;
					break;
				case LAST_WEEK:
				case WEEKLY:
					categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
					timeUnit = ChronoUnit.DAYS;
					break;
				default:
					break;
			}
			
			while (!start.isAfter(end)) {
				K dateTime = clazz.getDeclaredConstructor().newInstance();
				dateTime.setCategories_time(start.format(categoryTimeFormat));
				dateTimeList.add(dateTime);
				start = start.plus(interval, timeUnit);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return dateTimeList;
	}
	
	/**
	 * @description Get data list in multi threads
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param id, ids, start_date, end_date
	 * @return data list
	 */
	public List<ViewReportEntity> getReportDataList(ViewReportEntity reportObj) {
		try {
			List<CompletableFuture<ViewReportEntity>> list = new ArrayList<CompletableFuture<ViewReportEntity>>();
			
			if (reportObj.getIds() != null && reportObj.getIds().size() > 0) {
				List<ViewReportEntity> siteReportList = queryForList("BuiltInReport.getSiteReportMappingList", reportObj);
				
				for (int i = 0; i < siteReportList.size(); i++) {
					ViewReportEntity siteObj = siteReportList.get(i);
					
					CompletableFuture<ViewReportEntity> future = CompletableFuture.supplyAsync(() -> {
						try {
							switch (ReportRange.fromValue(siteObj.getCadence_range())) {
							case LAST_MONTH:
							case MONTHLY:
							case CUSTOM:
								return this.getMonthlyTrendBuitInReport(siteObj);
							case LAST_WEEK:
							case WEEKLY:
								return this.getWeeklyBuiltInReport(siteObj);
							case ANNUALLY:
								return this.getAnnuallyBuitInReport(siteObj);
							default:
								return null;
							}
						} catch (Exception ex) {
							log.error(ex);
							return null;
						}
					});
					
					list.add(future);
				}
			}
			
			return list.stream().map(future -> future.join()).filter(item -> item != null).collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public <K extends DateTimeReportDataEntity> List<ViewReportEntity> summarizeReport(List<ViewReportEntity> dataList, Class<K> clazz) {
		if (dataList == null || dataList.size() == 0) return new ArrayList<ViewReportEntity>();
		
		try {
			ViewReportEntity findReport = dataList.stream().filter(item -> item.getDataReports() != null && item.getDataReports().size() > 0).findFirst().orElse(new ViewReportEntity());
			List<K> summaryData = ((List<K>) findReport.getDataReports())
					.stream()
					.map(item -> {
						try {
							K target = clazz.getDeclaredConstructor().newInstance();
							target.setCategories_time(item.getCategories_time());
							return target;
						} catch (Exception e) {
							return null;
						}
					})
					.collect(Collectors.toList());
			
			if (findReport.getCadence_range() == ReportRange.ANNUALLY.getValue() || findReport.getCadence_range() == ReportRange.WEEKLY.getValue()) {
				for (int i = 0; i < summaryData.size(); i++) {
					WeeklyDateEntity sum = (WeeklyDateEntity) summaryData.get(i);
					List<Double> actualGeneration = new ArrayList<Double>();
					List<Double> expectedGeneration = new ArrayList<Double>();
					List<Double> modeledGeneration = new ArrayList<Double>();
					List<Double> poa = new ArrayList<Double>();
					
					for (int j = 0; j < dataList.size(); j++) {
						List<WeeklyDateEntity> dataReports = dataList.get(j).getDataReports();
						if (dataReports == null || dataReports.size() == 0) continue;
						WeeklyDateEntity item = dataReports.get(i);
						
						if (item != null && sum.getCategories_time().equals(item.getCategories_time())) {
							actualGeneration.add(item.getActualGeneration());
							expectedGeneration.add(item.getExpectedGeneration());
							modeledGeneration.add(item.getModeledGeneration());
							poa.add(item.getPoa());
						}
					}
					
					sum.setActualGeneration(actualGeneration.stream().allMatch(Objects::isNull) ? null : actualGeneration.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum());
					sum.setExpectedGeneration(expectedGeneration.stream().allMatch(Objects::isNull) ? null : expectedGeneration.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum());
					sum.setModeledGeneration(modeledGeneration.stream().allMatch(Objects::isNull) ? null : modeledGeneration.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum());
					sum.setPoa(poa.stream().allMatch(Objects::isNull) ? null : BigDecimal.valueOf(poa.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().getAsDouble()).doubleValue());
					sum.setExpectedGenerationIndex(Optional.ofNullable(sum.getActualGeneration()).orElse(0.0) > 0 && Optional.ofNullable(sum.getExpectedGeneration()).orElse(0.0) > 0 ? BigDecimal.valueOf(sum.getActualGeneration() / sum.getExpectedGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue() : null);
					sum.setModeledGenerationIndex(Optional.ofNullable(sum.getActualGeneration()).orElse(0.0) > 0 && Optional.ofNullable(sum.getModeledGeneration()).orElse(0.0) > 0 ? BigDecimal.valueOf(sum.getActualGeneration() / sum.getModeledGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue() : null);
				}
			} else if (findReport.getCadence_range() == ReportRange.MONTHLY.getValue() && findReport.getData_intervals() == ReportIntervals.MONTHLY.getValue()) {
				for (int i = 0; i < summaryData.size(); i++) {
					MonthlyProductionTrendReportEntity sum = (MonthlyProductionTrendReportEntity) summaryData.get(i);
					
					for (int j = 0; j < dataList.size(); j++) {
						List<MonthlyProductionTrendReportEntity> dataReports = dataList.get(j).getDataReports();
						if (dataReports == null || dataReports.size() == 0) continue;
						MonthlyProductionTrendReportEntity item = dataReports.get(i);
						
						if (item != null && sum.getCategories_time().equals(item.getCategories_time())) {
							if (item.getMonthlyProduction() != null) sum.setMonthlyProduction((sum.getMonthlyProduction() != null ? sum.getMonthlyProduction() : 0) + item.getMonthlyProduction());
						}
					}
				}
			} else {
				return dataList;
			}
			
			Gson gson = new Gson();
			ViewReportEntity summaryReport = gson.fromJson(gson.toJson(findReport), ViewReportEntity.class);
			summaryReport.setDataReports(summaryData);
			summaryReport.setSite_name("Summary");
			summaryReport.setId_site(0);
			
			dataList.add(0, summaryReport);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return dataList;
	}
	
	/**
	 * @description get annually production trend report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public ViewReportEntity getAnnuallyBuitInReport(ViewReportEntity obj) {
		try {
			List<WeeklyDateEntity> data = queryForList("BuiltInReport.getDataAnnualTrendReport", obj);
			List<WeeklyDateEntity> fulfillData = Lib.fulfillData(getDateTimeList(obj, WeeklyDateEntity.class), data, "categories_time");
			if (fulfillData.size() > 0) {
				WeeklyDateEntity totalRow = new WeeklyDateEntity();
				totalRow.setCategories_time("Total");
				totalRow.setActualGeneration(fulfillData.stream().filter(item -> item.getActualGeneration() != null).mapToDouble(item -> item.getActualGeneration()).sum());
				totalRow.setExpectedGeneration(fulfillData.stream().filter(item -> item.getExpectedGeneration() != null).mapToDouble(item -> item.getExpectedGeneration()).sum());
				totalRow.setModeledGeneration(fulfillData.stream().filter(item -> item.getModeledGeneration() != null).mapToDouble(item -> item.getModeledGeneration()).sum());
				if (totalRow.getActualGeneration() > 0 && totalRow.getExpectedGeneration() > 0) totalRow.setExpectedGenerationIndex(BigDecimal.valueOf(totalRow.getActualGeneration() / totalRow.getExpectedGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue());
				if (totalRow.getActualGeneration() > 0 && totalRow.getModeledGeneration() > 0) totalRow.setModeledGenerationIndex(BigDecimal.valueOf(totalRow.getActualGeneration() / totalRow.getModeledGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue());
				
				fulfillData.add(totalRow);
			}
			
			obj.setDataReports(fulfillData);
			
			return obj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public ViewReportEntity getMonthlyTrendBuitInReport(ViewReportEntity obj) {
		try {
			List powerDeviceList = obj.isHave_meter() ? queryForList("BuiltInReport.getListDeviceTypeMeter", obj) : queryForList("BuiltInReport.getListDeviceTypeInverter", obj);
			if (powerDeviceList.size() > 0) {
				obj.setGroupDevices(powerDeviceList);
				List<MonthlyProductionTrendReportEntity> data = queryForList("BuiltInReport.getMonthlyTrendBuitInReport", obj);
				obj.setDataReports(Lib.fulfillData(getDateTimeList(obj, MonthlyProductionTrendReportEntity.class), data, "categories_time"));
			}
			
			return obj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description get weekly report 
	 * @author Hung.Bui
	 * @since 2023-07-31
	 * @param id_site, date_from, date_to
	 */
	
	public ViewReportEntity getWeeklyBuiltInReport(ViewReportEntity obj) {
		try {
			List<WeeklyDateEntity> data = queryForList("BuiltInReport.getDataWeeklyTrendReport", obj);
			List<WeeklyDateEntity> fulfillData = Lib.fulfillData(getDateTimeList(obj, WeeklyDateEntity.class), data, "categories_time");
			if (fulfillData.size() > 0) {
				WeeklyDateEntity totalRow = new WeeklyDateEntity();
				totalRow.setCategories_time("Total");
				totalRow.setActualGeneration(fulfillData.stream().filter(item -> item.getActualGeneration() != null).mapToDouble(item -> item.getActualGeneration()).sum());
				totalRow.setExpectedGeneration(fulfillData.stream().filter(item -> item.getExpectedGeneration() != null).mapToDouble(item -> item.getExpectedGeneration()).sum());
				totalRow.setModeledGeneration(fulfillData.stream().filter(item -> item.getModeledGeneration() != null).mapToDouble(item -> item.getModeledGeneration()).sum());
				if (totalRow.getActualGeneration() > 0 && totalRow.getExpectedGeneration() > 0) totalRow.setExpectedGenerationIndex(BigDecimal.valueOf(totalRow.getActualGeneration() / totalRow.getExpectedGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue());
				if (totalRow.getActualGeneration() > 0 && totalRow.getModeledGeneration() > 0) totalRow.setModeledGenerationIndex(BigDecimal.valueOf(totalRow.getActualGeneration() / totalRow.getModeledGeneration() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue());
				
				fulfillData.add(totalRow);
			}
			
			obj.setDataReports(fulfillData);
			
			return obj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description send weekly production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailWeeklyTrendReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return false;
			String title = "Weekly Production Trend Report (Daily Interval)";
			List<ViewReportEntity> summarizedList = summarizeReport(dataObjList, WeeklyDateEntity.class);
			String filePath = createWeeklyTrendReportSheetFile(summarizedList, obj.getReport_name());
			if (filePath == null) return false;
			
			reportsService.sentReportByMail(filePath, dataObjList.get(0).getSubscribers(), title, 18, "Customer", title);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download weekly production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadWeeklyTrendReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return null;
			List<ViewReportEntity> summarizedList = summarizeReport(dataObjList, WeeklyDateEntity.class);
			return createWeeklyTrendReportSheetFile(summarizedList, obj.getReport_name());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description send monthly production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailMonthlyTrendReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return false;
			String title = "Monthly Production Trend Report ";
			if (dataObjList.get(0).getData_intervals() == ReportIntervals._15_MINUTES.getValue()) title = title.concat("(15-minute Interval)");
			else if (dataObjList.get(0).getData_intervals() == ReportIntervals.MONTHLY.getValue()) title = title.concat("(Monthly Interval)");
			String filePath = createMonthlyTrendReportSheetFile(obj, dataObjList, obj.getReport_name());
			if (filePath == null) return false;
			
			reportsService.sentReportByMail(filePath, dataObjList.get(0).getSubscribers(), title, 18, "Customer", title);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download monthly production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadMonthlyTrendReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return null;
			return createMonthlyTrendReportSheetFile(obj, dataObjList, obj.getReport_name());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description send annual production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailAnnualTrendReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return false;
			String title = "Annual Production Trend Report (Monthly Interval)";
			List<ViewReportEntity> summarizedList = summarizeReport(dataObjList, WeeklyDateEntity.class);
			String filePath = createWeeklyTrendReportSheetFile(summarizedList, obj.getReport_name());
			if (filePath == null) return false;
			
			reportsService.sentReportByMail(filePath, dataObjList.get(0).getSubscribers(), title, 18, "Customer", title);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download annual production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadAnnualTrendReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return null;
			List<ViewReportEntity> summarizedList = summarizeReport(dataObjList, WeeklyDateEntity.class);
			return createWeeklyTrendReportSheetFile(summarizedList, obj.getReport_name());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description create weekly production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createWeeklyTrendReportSheetFile(List<ViewReportEntity> summarizedList, String title) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			// insert logo image
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			ClientAnchor logoAnchor = new XSSFClientAnchor(10 * Units.EMU_PER_PIXEL, -10 * Units.EMU_PER_PIXEL, 0, 10 * Units.EMU_PER_PIXEL, 7, 1, 8, 4);
			
			for (int s = 0; s < summarizedList.size(); s++) {
				ViewReportEntity dataObj = summarizedList.get(s);
				
				if (dataObj != null) {
					List<WeeklyDateEntity> dataExports = dataObj.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() - 1 : 0; // exclude total row
					
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
						Supplier<Stream<WeeklyDateEntity>> filteredDataExports = () -> dataExports.stream().filter(item -> !item.getCategories_time().equals("Total"));
						DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getActualGeneration() == null), data, categoriesData, valuesData1, "Actual Generation (kWh)", XDDFColor.from(new byte[] {(byte) 70, (byte) 130, (byte) 180}), null);
						DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getExpectedGeneration() == null), data, categoriesData, valuesData2, "Expected Generation (kWh)", XDDFColor.from(new byte[] {(byte) 166, (byte) 166, (byte) 166}), null);
						DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getModeledGeneration() == null), data, categoriesData, valuesData3, "Modeled Generation (kWh)", XDDFColor.from(new byte[] {(byte) 176, (byte) 196, (byte) 222}), null);
						
						chart.plot(data);
						
						// right value axis
						XDDFValueAxis rightAxis = DocumentHelper.createRightValueAxis(chart, bottomAxis, "PERFORMANCE INDEX (%)");
						
						data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, rightAxis);
						Series lineSeries = DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getExpectedGenerationIndex() == null), data, categoriesData, valuesData4, "Expected Generation Index (%)", XDDFColor.from(new byte[] {(byte) 112, (byte) 173, (byte) 71}), null);
						DocumentHelper.solidFillLineMarker(chart, lineSeries, 0, MarkerStyle.CIRCLE, XDDFColor.from(new byte[] {(byte) 112, (byte) 173, (byte) 71}));
						lineSeries = DocumentHelper.addSeries(filteredDataExports.get().allMatch(item -> item.getModeledGenerationIndex() == null), data, categoriesData, valuesData5, "Modeled Generation Index (%)", XDDFColor.from(new byte[] {(byte) 255, (byte) 192, (byte) 0}), null);
						DocumentHelper.solidFillLineMarker(chart, lineSeries, 1, MarkerStyle.CIRCLE, XDDFColor.from(new byte[] {(byte) 255, (byte) 192, (byte) 0}));
						
						chart.plot(data);
					}
				}
			}
			
			return reportsService.writeToSheetFile(document, title);
		} catch (Exception e) {
			return null;
		}
	}
	
	private static void writeHeaderWeeklyReport(Sheet sheet, ViewReportEntity dataObj) {
		try {
			setPrintAreaToFitOnePage(sheet);
			
			// set column width
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
			String title = dataObj.getCadence_range() == ReportRange.ANNUALLY.getValue() ? "ANNUAL PRODUCTION TREND REPORT (MONTHLY INTERVAL)" : "WEEKLY PRODUCTION TREND REPORT (DAILY INTERVAL)";
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
			
			if(dataExports != null && dataExports.size() > 0) {
				for (int i = 0; i < dataExports.size(); i++) {
					WeeklyDateEntity item = dataExports.get(i);
					boolean isTotalRow = item.getCategories_time().equals("Total");
					
					Row row5 = sheet.createRow(6 + i);
					Cell cell5 = row5.createCell(0);
					cell5.setCellStyle(isTotalRow ? tableRowBoldCellStyle : tableRowCellStyle);
					cell5.setCellValue(item.getCategories_time());
					
					Cell cell51 = row5.createCell(1);
					cell51.setCellStyle(isTotalRow ? tableRowNoDecimalBoldCellStyle : tableRowNoDecimalCellStyle);
					if (item.getActualGeneration() != null) cell51.setCellValue(item.getActualGeneration());
					
					Cell cell52 = row5.createCell(2);
					cell52.setCellStyle(isTotalRow ? tableRowNoDecimalBoldCellStyle : tableRowNoDecimalCellStyle);
					if (item.getExpectedGeneration() != null) cell52.setCellValue(item.getExpectedGeneration());
					
					Cell cell53 = row5.createCell(3);
					cell53.setCellStyle(isTotalRow ? tableRowNoDecimalBoldCellStyle : tableRowNoDecimalCellStyle);
					if (item.getModeledGeneration() != null) cell53.setCellValue(item.getModeledGeneration());
					
					Cell cell54 = row5.createCell(4);
					cell54.setCellStyle(isTotalRow ? tableRowOneDecimalPlaceBoldCellStyle : tableRowTwoDecimalPlaceCellStyle);
					if (item.getPoa() != null) cell54.setCellValue(item.getPoa());
					
					Cell cell55 = row5.createCell(5);
					cell55.setCellStyle(isTotalRow ? tableRowOneDecimalPlaceBoldCellStyle : tableRowOneDecimalPlaceCellStyle);
					if (item.getExpectedGenerationIndex() != null) cell55.setCellValue(item.getExpectedGenerationIndex());
					
					Cell cell56 = row5.createCell(6);
					cell56.setCellStyle(isTotalRow ? tableRowOneDecimalPlaceBoldCellStyle : tableRowOneDecimalPlaceCellStyle);
					if (item.getModeledGenerationIndex() != null) cell56.setCellValue(item.getModeledGenerationIndex());
					Cell cell57 = row5.createCell(7);
					cell57.setCellStyle(isTotalRow ? tableRowOneDecimalPlaceBoldCellStyle : tableRowOneDecimalPlaceCellStyle);
					sheet.addMergedRegion(new CellRangeAddress(6 + i, 6 + i, 6, 7));
				}
			}
			
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
	
	private static void setPrintAreaToFitOnePage(Sheet sheet) {
		sheet.setFitToPage(true);
		sheet.getPrintSetup().setFitWidth((short) 1);
		sheet.getPrintSetup().setFitHeight((short) 0);
	}
	
	/**
	 * @description create monthly production trend report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createMonthlyTrendReportSheetFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList, String title) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			List<ViewReportEntity> summarizedList = summarizeReport(dataObjList, MonthlyProductionTrendReportEntity.class);
			
			// insert logo image
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			
			if(dataObjList.get(0).getData_intervals() == ReportIntervals._15_MINUTES.getValue()) {
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
					
			return reportsService.writeToSheetFile(document, title);
		} catch (Exception e) {
			return null;
		}
	}
	
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
	
	private static void writeHeaderMonthTrendReport(Sheet sheet, ViewReportEntity dataObj) {
		try {
			setPrintAreaToFitOnePage(sheet);
			
			// set column width
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
					sheet.addMergedRegionUnsafe(new CellRangeAddress(i + 6, i + 6, 0, 2));
					
					Cell cell51 = row5.createCell(3);
					cell51.setCellStyle(tableRowNoDecimalCellStyle);
					if (item.getMonthlyProduction() != null) cell51.setCellValue(item.getMonthlyProduction());
					cell51 = row5.createCell(4);
					cell51.setCellStyle(tableRowNoDecimalCellStyle);
					sheet.addMergedRegionUnsafe(new CellRangeAddress(i + 6, i + 6, 3, 4));
				}
			}
		} catch (Exception e) {
		}
	}
}
