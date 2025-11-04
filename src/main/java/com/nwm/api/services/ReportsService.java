/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.ibatis.session.SqlSession;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChart;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData.Series;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.nwm.api.DBManagers.DB;
import com.nwm.api.batchjob.BatchJob;
import com.nwm.api.entities.AssetManagementAndOperationPerformanceReportEntity;
import com.nwm.api.entities.AuditLog;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.CustomReportDataEntity;
import com.nwm.api.entities.DailyDateEntity;
import com.nwm.api.entities.DateTimeReportDataEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.MonthlyDateEntity;
import com.nwm.api.entities.PerformanceDataChartItemEntity;
import com.nwm.api.entities.AccumulatedEnergyByMonthEntity;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.AssetManagementAndOperationPerformanceDataEntity;
import com.nwm.api.entities.QuarterlyDateEntity;
import com.nwm.api.entities.ReportDuplicateRequest;
import com.nwm.api.entities.ReportLogs;
import com.nwm.api.entities.ReportsEntity;
import com.nwm.api.entities.SanityCheckReportEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Constants.ChartingFilter;
import com.nwm.api.utils.Constants.ChartingGranularity;
import com.nwm.api.utils.Constants.ReportFileType;
import com.nwm.api.utils.Constants.ReportIntervals;
import com.nwm.api.utils.Constants.ReportRange;
import com.nwm.api.utils.DocumentHelper;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;

public class ReportsService extends DB {
	
	private static final Color BLUE_COLOR = new Color(49, 119, 168);
	private static final Color LIGHT_BLUE_COLOR = new Color(109, 189, 246);
	private static final Color ORANGE_COLOR = new Color(255, 129, 39);
	
	private String getReportFolderPath() {
		String uploadRootPath = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey);
		return uploadRootPath + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
	}
	
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
			DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
			ChronoUnit timeUnit = ChronoUnit.MINUTES;
		
			switch (ReportRange.fromValue(obj.getCadence_range())) {
				case DAILY:
					categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm");
					switch (ReportIntervals.fromValue(obj.getData_intervals())) {
						case _5_MINUTE:
							interval = 5;
							timeUnit = ChronoUnit.MINUTES;
							break;
						case _15_MINUTES:
							interval = 15;
							timeUnit = ChronoUnit.MINUTES;
							break;
						case _1_HOUR:
							interval = 1;
							timeUnit = ChronoUnit.HOURS;
							break;
						default:
							break;
					}
					break;
				case LAST_MONTH:
				case MONTHLY:
					categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
					timeUnit = ChronoUnit.DAYS;
					break;
				case LAST_QUARTER:
					switch (ReportIntervals.fromValue(obj.getData_intervals())) {
						case DAILY:
							categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
							timeUnit = ChronoUnit.DAYS;
							break;
						case MONTHLY:
							categoryTimeFormat = DateTimeFormatter.ofPattern("MMM-yyyy");
							timeUnit = ChronoUnit.MONTHS;
							break;
						default:
							break;
					}
					break;
				case ANNUALLY:
					categoryTimeFormat = DateTimeFormatter.ofPattern("MMM");
					timeUnit = ChronoUnit.MONTHS;
					break;
				case CUSTOM:
	                switch (ReportIntervals.fromValue(obj.getData_intervals())) {
		                case _15_MINUTES:
		                	categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm");
							interval = 15;
							timeUnit = ChronoUnit.MINUTES;
							break;
		                case _30_MINUTES:
		                	categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm");
							interval = 30;
							timeUnit = ChronoUnit.MINUTES;
							break;
	                	case DAILY:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
	                		timeUnit = ChronoUnit.DAYS;
	                		break;
	                	case MONTHLY:
	                		end = end.with(TemporalAdjusters.lastDayOfMonth());
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/yyy");
	                		timeUnit = ChronoUnit.MONTHS;
	                		break;
	                	case ANNUAL:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("yyyy");
	                		timeUnit = ChronoUnit.YEARS;
	                		break;
	                	default:
	    					break;
	                }
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
	 * @description Summarize data by interval for whole sites
	 * @author Hung.Bui
	 * @since 2024-11-12
	 * @param dataList
	 * @return data list
	 */
	private List<ViewReportEntity> dataSummarize(List<ViewReportEntity> dataList) {
		try {
			ViewReportEntity sumObj = new ViewReportEntity();
			sumObj.setSite_name("Total");
			
			List<CustomReportDataEntity> sumReport = dataList
					.stream()
					.map(item -> (List<CustomReportDataEntity>) item.getDataReports())
					.reduce(new ArrayList<CustomReportDataEntity>(), (acc, item) -> {
						for (int i = 0; i < item.size(); i++) {
							if (acc.size() < item.size()) acc.add(new CustomReportDataEntity());
							if (acc.get(i).getCategories_time() == null) acc.get(i).setCategories_time(item.get(i).getCategories_time());
							if (item.get(i).getActual() != null) acc.get(i).setActual(Optional.ofNullable(acc.get(i).getActual()).orElse(0.0) + item.get(i).getActual());
						}
						
						return acc;
					});
			
			sumObj.setDataReports(sumReport);
			dataList.add(sumObj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return dataList;
	}
	
	/**
	 * @description Sort data
	 * @author Hung.Bui
	 * @since 2024-11-13
	 * @param dataList
	 * @return data list
	 */
	private List<ViewReportEntity> dataSort(List<ViewReportEntity> dataList, ViewReportEntity reportObj) {
		try {
			List<ViewReportEntity> sortedDataList = new ArrayList<ViewReportEntity>();
			if 		(reportObj.getSort_by() == 1) sortedDataList = dataList.stream().sorted((a, b) -> a.getSite_name().compareTo(b.getSite_name())).collect(Collectors.toList());
			else if (reportObj.getSort_by() == 2) sortedDataList = dataList.stream().sorted((a, b) -> b.getSite_name().compareTo(a.getSite_name())).collect(Collectors.toList());
			else if (reportObj.getSort_by() == 3) sortedDataList = dataList.stream().sorted((a, b) -> Double.compare(((List<CustomReportDataEntity>) b.getDataReports()).get(b.getDataReports().size() - 1).getActual(), ((List<CustomReportDataEntity>) a.getDataReports()).get(a.getDataReports().size() - 1).getActual())).collect(Collectors.toList());
			else if (reportObj.getSort_by() == 4) sortedDataList = dataList.stream().sorted((a, b) -> Double.compare(((List<CustomReportDataEntity>) a.getDataReports()).get(a.getDataReports().size() - 1).getActual(), ((List<CustomReportDataEntity>) b.getDataReports()).get(b.getDataReports().size() - 1).getActual())).collect(Collectors.toList());
			else 								  sortedDataList = dataList;
			
			return sortedDataList;
		} catch (Exception ex) {
			return dataList;
		}
	}
	
	/**
	 * @description Get data list in multi threads
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param ids, id, data_intervals, start_date, end_date
	 * @return data list
	 */
	public List<ViewReportEntity> getReportDataList(ViewReportEntity reportObj) {
		try {
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
					siteObj.setDomain(reportObj.getDomain());
					siteObj.setDomain_role(reportObj.getDomain_role());
					
					CompletableFuture<ViewReportEntity> future = CompletableFuture.supplyAsync(() -> {
						try {
							switch (ReportRange.fromValue(reportObj.getCadence_range())) {
								case DAILY:
									return this.getDailyReport(siteObj);
								case LAST_MONTH:
								case MONTHLY:
									return this.getMonthlyReport(siteObj);
								case LAST_QUARTER:
									return this.getQuarterlyReport(siteObj);
								case ANNUALLY:
									return this.getAnnuallyReport(siteObj);
								case CUSTOM:
									return this.getCustomReport(siteObj);
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
			
			List<ViewReportEntity> dataList = list.stream().map(future -> future.join()).filter(item -> item != null).collect(Collectors.toList());
			
			return reportObj.getCadence_range() == ReportRange.CUSTOM.getValue() ? this.dataSummarize(this.dataSort(dataList, reportObj)) : dataList;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	private ViewReportEntity getReportDetail(ViewReportEntity obj) {
		try {
			ViewReportEntity report = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (report == null || report.getId_site() == 0) return null;
			return report;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public ViewReportEntity getDailyReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = getReportDetail(obj);
			if (dataObj == null) return null;
			
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setData_intervals(dataObj.getData_intervals());
			
			CustomerViewService customerService = new CustomerViewService();
			DevicesByTypeEntity devices = customerService.getDevicesBySite(obj);
			List<DeviceEntity> meterDevices = devices.getMeter();
			List<DeviceEntity> inverterDevices = devices.getInverter();
			List<DeviceEntity> irradianceDevices = devices.getIrradiance();
			List<DeviceEntity> powerDevices = meterDevices.size() > 0 ? meterDevices : inverterDevices;
			
			if(powerDevices.size() > 0) {
				obj.setGroupDevices(powerDevices);
				List<DailyDateEntity> dataPower = getEnergyByMeter(obj);
				dataObj.setDataReports(dataPower);
			}
			
			// get irradiance 
			if (irradianceDevices.size() > 0) {
				obj.setGroupDevices(irradianceDevices);
				List<DailyDateEntity> dataIrradiance = getIrradianceByWS(obj);
				List<DailyDateEntity> dataPower = dataObj.getDataReports();
				
				if (dataIrradiance.size() > 0 && dataPower.size() > 0) {
					for (int i = 0; i < dataPower.size(); i++) {
						DailyDateEntity item = (DailyDateEntity) dataPower.get(i);
						item.setIrradiance(dataIrradiance.get(i).getIrradiance());
					}
				} else if (dataIrradiance.size() > 0) {
					dataObj.setDataReports(dataIrradiance);
				}
				
				dataObj.setHave_poa(true);
			}
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	private List<DailyDateEntity> getEnergyByMeter(ViewReportEntity obj) {
		try {
			List<DailyDateEntity> data = obj.getGroupDevices().isEmpty() ? new ArrayList<>() : queryForList("Reports.getDataEnergyMeter", obj);
			return Lib.fulfillData(getDateTimeList(obj, DailyDateEntity.class), data, "categories_time");
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	private List<DailyDateEntity> getIrradianceByWS(ViewReportEntity obj) {
		try {
			List<DailyDateEntity> data = obj.getGroupDevices().isEmpty() ? new ArrayList<>() : queryForList("Reports.getDataIrradiance", obj);
			return Lib.fulfillData(getDateTimeList(obj, DailyDateEntity.class), data, "categories_time");
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public ViewReportEntity getAnnuallyReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = getReportDetail(obj);
			if (dataObj == null) return null;
			
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setTable_data_report(dataObj.getTable_data_report());
			obj.setHave_meter(dataObj.isHave_meter());
			obj.setHave_inverter(dataObj.isHave_inverter());
			List<QuarterlyDateEntity> dataEnergy = queryForList("Reports.getDataEnergyAnnuallyReport", obj);
			dataObj.setDataReports(Lib.fulfillData(getDateTimeList(obj, QuarterlyDateEntity.class), dataEnergy, "categories_time"));
			
			return dataObj;
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
	
	public ViewReportEntity getQuarterlyReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = getReportDetail(obj);
			if (dataObj == null) return null;
			
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setData_intervals(dataObj.getData_intervals());
			obj.setTable_data_report(dataObj.getTable_data_report());
			obj.setHave_meter(dataObj.isHave_meter());
			List<QuarterlyDateEntity> dataEnergy = dataObj.getData_intervals() == ReportIntervals.MONTHLY.getValue() ? queryForList("Reports.getDataEnergyQuarterlyReportByMonth", obj) : queryForList("Reports.getDataEnergyQuarterlyReportByDay", obj);
			dataObj.setDataReports(Lib.fulfillData(getDateTimeList(obj, QuarterlyDateEntity.class), dataEnergy, "categories_time"));
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description Get asset management and performance report
	 * @author Hung.Bui
	 * @since 2024-06-10
	 * @param id_site, date_from, data_to
	 */
	
	public AssetManagementAndOperationPerformanceReportEntity getAssetManagementAndOperationPerformanceReport(ViewReportEntity obj) {
		try {
			String id_sites = obj.getId_sites() != null ? obj.getId_sites() : (obj.getIds_site() != null ? obj.getIds_site() : null);
			String[] idSiteArr = id_sites != null ? id_sites.split(",") : new String[0];
			if (idSiteArr.length == 0) return null;
			obj.setId_site(Integer.parseInt(idSiteArr[0]));
			
			ViewReportEntity reportObj = getReportDetail(obj);
			if (reportObj == null) return null;
			reportObj.setReport_name(obj.getReport_name());
			
			DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			DateTimeFormatter mmm_yyFormat = DateTimeFormatter.ofPattern("MMM-yy");
			DateTimeFormatter mm_dd_yyyyFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDateTime startDate = LocalDateTime.parse(obj.getStart_date(), inputDateFormat).withHour(0).withMinute(0).withSecond(0);
			LocalDateTime endDate = LocalDateTime.parse(obj.getEnd_date(), inputDateFormat).withHour(23).withMinute(59).withSecond(59);
			AssetManagementAndOperationPerformanceReportEntity dataObj = new AssetManagementAndOperationPerformanceReportEntity();
			dataObj.setReportDetail(reportObj);
			
			/* operation performance report */
			reportObj.setStart_date(startDate.format(inputDateFormat));
			reportObj.setEnd_date(endDate.format(inputDateFormat));
			List<AssetManagementAndOperationPerformanceDataEntity> operationPerformanceData = queryForList("Reports.getOperationPerformanceReport", reportObj);
			
			if (operationPerformanceData != null && operationPerformanceData.size() > 0) {
				// fulfill data
				LocalDateTime start = startDate;
				LocalDateTime end = endDate;
				List<AssetManagementAndOperationPerformanceDataEntity> dateTimeList = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				List<AssetManagementAndOperationPerformanceDataEntity> fulfilledDataList = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				
				while (!start.isAfter(end)) {
					AssetManagementAndOperationPerformanceDataEntity dateTime = new AssetManagementAndOperationPerformanceDataEntity();
					dateTime.setTime_full(start.format(mmm_yyFormat));
					dateTimeList.add(dateTime);
					start = start.plus(1, ChronoUnit.MONTHS);
				}
				
				for (AssetManagementAndOperationPerformanceDataEntity dateTime: dateTimeList) {
					boolean isFound = false;
					
					for(AssetManagementAndOperationPerformanceDataEntity data: operationPerformanceData) {
						String fullTime = dateTime.getTime_full();
						String powerTime = data.getTime_full();
						
						if (fullTime.equals(powerTime)) {
							fulfilledDataList.add(data);
							isFound = true;
							break;
						}
					}
					
					if (!isFound) fulfilledDataList.add(dateTime);
				}
				
				dataObj.setOperationPerformanceData(fulfilledDataList);
			}
			
			AssetManagementAndOperationPerformanceDataEntity currentMonthOperationPerformanceData = operationPerformanceData.stream().filter(item -> YearMonth.parse(item.getTime_full(), mmm_yyFormat).equals(YearMonth.from(endDate))).findFirst().orElse(null);
			List<AssetManagementAndOperationPerformanceDataEntity> currentYearOperationPerformanceData = operationPerformanceData.stream().filter(item -> YearMonth.parse(item.getTime_full(), mmm_yyFormat).getYear() == YearMonth.from(endDate).getYear()).collect(Collectors.toList());
			
			/* monthly performance report */
			LocalDateTime startDateOfCurrentMonth = endDate.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
			reportObj.setStart_date(startDateOfCurrentMonth.format(inputDateFormat));
			List<AssetManagementAndOperationPerformanceDataEntity> monthlyPerformanceData = queryForList("Reports.getMonthlyPerformanceReport", reportObj);
			
			if (monthlyPerformanceData != null && monthlyPerformanceData.size() > 0) {
				// fulfill data
				LocalDateTime start = startDateOfCurrentMonth;
				LocalDateTime end = endDate;
				List<AssetManagementAndOperationPerformanceDataEntity> dateTimeList = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				List<AssetManagementAndOperationPerformanceDataEntity> fulfilledDataList = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				
				while (!start.isAfter(end)) {
					AssetManagementAndOperationPerformanceDataEntity dateTime = new AssetManagementAndOperationPerformanceDataEntity();
					dateTime.setTime_full(start.format(mm_dd_yyyyFormat));
					dateTimeList.add(dateTime);
					start = start.plus(1, ChronoUnit.DAYS);
				}
				
				for (AssetManagementAndOperationPerformanceDataEntity dateTime: dateTimeList) {
					boolean isFound = false;
					
					for(AssetManagementAndOperationPerformanceDataEntity data: monthlyPerformanceData) {
						String fullTime = dateTime.getTime_full();
						String powerTime = data.getTime_full();
						
						if (fullTime.equals(powerTime)) {
							fulfilledDataList.add(data);
							isFound = true;
							break;
						}
					}
					
					if (!isFound) fulfilledDataList.add(dateTime);
				}
				
				AssetManagementAndOperationPerformanceDataEntity initial = new AssetManagementAndOperationPerformanceDataEntity();
				AssetManagementAndOperationPerformanceDataEntity totalMonthlyPerformanceData = monthlyPerformanceData.stream().reduce(initial, (acc, item) -> {
					if (item.getActualEnergy() != null) acc.setActualEnergy((acc.getActualEnergy() != null ? acc.getActualEnergy() : 0) + item.getActualEnergy());
					if (item.getExpectedEnergy() != null) acc.setExpectedEnergy((acc.getExpectedEnergy() != null ? acc.getExpectedEnergy() : 0) + item.getExpectedEnergy());
					if (item.getModeledEnergy() != null) acc.setModeledEnergy((acc.getModeledEnergy() != null ? acc.getModeledEnergy() : 0) + item.getModeledEnergy());
					
					return acc;
				});
				
				Double totalActualEnergy = totalMonthlyPerformanceData.getActualEnergy();
				Double totalExpectedEnergy = totalMonthlyPerformanceData.getExpectedEnergy();
				Double totalModeledEnergy = totalMonthlyPerformanceData.getModeledEnergy();
				Double totalExpectedGenerationIndex = totalActualEnergy != null && totalExpectedEnergy != null ? totalActualEnergy / totalExpectedEnergy : null;
				Double totalModeledGenerationIndex = totalActualEnergy != null && totalModeledEnergy != null ? totalActualEnergy / totalModeledEnergy : null;
				Double totalWeatherIndex =  currentMonthOperationPerformanceData.getWeatherIndex();
				Double totalInverterAvaiability = currentMonthOperationPerformanceData.getInverterAvailability();
				totalMonthlyPerformanceData.setExpectedGenerationIndex(totalExpectedGenerationIndex);
				totalMonthlyPerformanceData.setModeledGenerationIndex(totalModeledGenerationIndex);
				totalMonthlyPerformanceData.setWeatherIndex(totalWeatherIndex);
				totalMonthlyPerformanceData.setInverterAvailability(totalInverterAvaiability);
				totalMonthlyPerformanceData.setTime_full(endDate.format(mmm_yyFormat));
				
				Map<String, Object> monthlyPerformanceDataObj = new HashMap<String, Object>();
				monthlyPerformanceDataObj.put("data", fulfilledDataList);
				monthlyPerformanceDataObj.put("total", totalMonthlyPerformanceData);
				
				dataObj.setMonthlyPerformanceData(monthlyPerformanceDataObj);
			}
			
			/* monthly asset management report */
			if (currentMonthOperationPerformanceData != null && currentYearOperationPerformanceData.size() > 0) {
				AssetManagementAndOperationPerformanceDataEntity initial = new AssetManagementAndOperationPerformanceDataEntity();
				AssetManagementAndOperationPerformanceDataEntity currentYearTotalAssetManagementData = currentYearOperationPerformanceData.stream().reduce(initial, (acc, item) -> {
					if (item.getActualEnergy() != null) acc.setActualEnergy((acc.getActualEnergy() != null ? acc.getActualEnergy() : 0) + item.getActualEnergy());
					if (item.getModeledEnergy() != null) acc.setModeledEnergy((acc.getModeledEnergy() != null ? acc.getModeledEnergy() : 0) + item.getModeledEnergy());
					
					return acc;
				});
				
				Double monthActualEnergy =  currentMonthOperationPerformanceData.getActualEnergy();
				Double monthModeledEnergy =  currentMonthOperationPerformanceData.getModeledEnergy();
				Double monthEnergyDifference = monthActualEnergy != null && monthModeledEnergy != null ? monthActualEnergy - monthModeledEnergy : null;
				Double monthActualEnergyRevenue = 0.224 * monthActualEnergy;
				Double monthEstimatedEnergyRevenue = 0.224 * monthModeledEnergy;
				Double monthEnergyRevenueDifference = monthActualEnergyRevenue != null && monthEstimatedEnergyRevenue != null ? monthActualEnergyRevenue - monthEstimatedEnergyRevenue : null;
				currentMonthOperationPerformanceData.setEnergyDifference(monthEnergyDifference);
				currentMonthOperationPerformanceData.setActualEnergyRevenue(monthActualEnergyRevenue);
				currentMonthOperationPerformanceData.setEstimatedEnergyRevenue(monthEstimatedEnergyRevenue);
				currentMonthOperationPerformanceData.setEnergyRevenueDifference(monthEnergyRevenueDifference);
				
				Double yearActualEnergy = currentYearTotalAssetManagementData.getActualEnergy();
				Double yearModeledEnergy = currentYearTotalAssetManagementData.getModeledEnergy();
				Double yearEnergyDifference = yearActualEnergy != null && yearModeledEnergy != null ? yearActualEnergy - yearModeledEnergy : null;
				Double yearActualEnergyRevenue = 0.224 * yearActualEnergy;
				Double yearEstimatedEnergyRevenue = 0.224 * yearModeledEnergy;
				Double yearEnergyRevenueDifference = yearActualEnergyRevenue != null && yearEstimatedEnergyRevenue != null ? yearActualEnergyRevenue - yearEstimatedEnergyRevenue : null;
				currentYearTotalAssetManagementData.setEnergyDifference(yearEnergyDifference);
				currentYearTotalAssetManagementData.setActualEnergyRevenue(yearActualEnergyRevenue);
				currentYearTotalAssetManagementData.setEstimatedEnergyRevenue(yearEstimatedEnergyRevenue);
				currentYearTotalAssetManagementData.setEnergyRevenueDifference(yearEnergyRevenueDifference);
				currentYearTotalAssetManagementData.setTime_full(String.valueOf(endDate.getYear()));
				
				List<AssetManagementAndOperationPerformanceDataEntity> monthlyAssetManagementData = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				monthlyAssetManagementData.add(currentMonthOperationPerformanceData);
				monthlyAssetManagementData.add(currentYearTotalAssetManagementData);
				
				dataObj.setMonthlyAssetManagementData(monthlyAssetManagementData);
			}
			
			/* estimated loss by event report */
			List<AssetManagementAndOperationPerformanceDataEntity> estimatedLossByEventData = queryForList("Reports.getEstimatedLossByEventReport", reportObj);
			if (estimatedLossByEventData != null && estimatedLossByEventData.size() > 0) {
				dataObj.setEstimatedLossByEventData(estimatedLossByEventData);
			}
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Get sanity check report
	 * @author Hung.Bui
	 * @since 2025-06-25
	 * @return ViewReportEntity
	 */
	public ViewReportEntity getSanityCheckReport(ViewReportEntity obj) {
		try {
			AlertEntity alertObj = new AlertEntity();
			alertObj.setId_sites(obj.getIds());
			alertObj.setDomain(obj.getDomain());
			alertObj.setStart_date(obj.getStart_date());
			alertObj.setEnd_date(obj.getEnd_date());
			AlertService alertService = new AlertService();
			List<AlertEntity> alertCountList = alertService.getSiteAlertCountListInDuration(alertObj);
			
			// convert list of alert count to map
			Map<Integer, Integer> alertCountMap = new HashMap<Integer, Integer>();
			for (AlertEntity alert : alertCountList) alertCountMap.put(alert.getId(), alert.getTotalError());
			
			List<CompletableFuture<SanityCheckReportEntity>> list = new ArrayList<CompletableFuture<SanityCheckReportEntity>>();
			
			if (obj.getIds() != null && obj.getIds().size() > 0) {
				for (int i = 0; i < obj.getIds().size(); i++) {
					int k = i;
					
					CompletableFuture<SanityCheckReportEntity> future = CompletableFuture.supplyAsync(() -> {
						try {
							ViewReportEntity siteObj = new ViewReportEntity();
							siteObj.setId_site((int) obj.getIds().get(k));
							siteObj.setId(obj.getId());
							siteObj.setStart_date(obj.getStart_date());
							siteObj.setDomain(obj.getDomain());
							siteObj.setDomain_role(obj.getDomain_role());
							
							ViewReportEntity dataObj = getReportDetail(siteObj);
							if (dataObj == null) return null;
							
							obj.setReport_date(dataObj.getReport_date());
							obj.setSubscribers(dataObj.getSubscribers());
							
							SanityCheckReportEntity sanityCheckReport = new SanityCheckReportEntity();
							sanityCheckReport.setSiteName(dataObj.getSite_name());
							
							// alert
							sanityCheckReport.setAlert(alertCountMap.get(siteObj.getId_site()));
							
							// meter and inverter
							CustomerViewService customerViewService = new CustomerViewService();
							DevicesByTypeEntity devicesByType = customerViewService.getDevicesBySite(siteObj);
							
							List<DeviceEntity> powerDevices = new ArrayList<>(devicesByType.getMeter());
							powerDevices.addAll(devicesByType.getInverter());
							if (powerDevices.size() == 0) return sanityCheckReport;
							
							siteObj.setGroupDevices(powerDevices);
							List<AccumulatedEnergyByMonthEntity> energyList = queryForList("Reports.getEnergySanityCheckReport", siteObj);
							Double totalCurrEnergy = null;
							Double totalLastEnergy = null;
							
							for (AccumulatedEnergyByMonthEntity item : energyList) {
								Double currEnergy = Objects.nonNull(item.getCurrentEOM()) && Objects.nonNull(item.getCurrentBOM()) ? item.getCurrentEOM() - item.getCurrentBOM() : null;
								Double lastEnergy = Objects.nonNull(item.getLastEOM()) && Objects.nonNull(item.getLastBOM()) ? item.getLastEOM() - item.getLastBOM() : null;
								
								if (item.getDeviceTypeId() == 1) {
									sanityCheckReport.addAccumulatedEnergyBOMByInverter(item.getCurrentBOM());
									sanityCheckReport.addAccumulatedEnergyEOMByInverter(item.getCurrentEOM());
									sanityCheckReport.addAccumulatedEnergyDifferenceByInverter(currEnergy);
								} else if (item.getDeviceTypeId() == 3) {
									sanityCheckReport.addAccumulatedEnergyBOMByMeter(item.getCurrentBOM());
									sanityCheckReport.addAccumulatedEnergyEOMByMeter(item.getCurrentEOM());
									sanityCheckReport.addAccumulatedEnergyDifferenceByMeter(currEnergy);
									
									if (dataObj.is_rec_report() && Objects.nonNull(currEnergy) && Objects.nonNull(lastEnergy) && currEnergy > 0) sanityCheckReport.addRecDifference1(BigDecimal.valueOf((currEnergy - lastEnergy) / currEnergy).setScale(3, RoundingMode.HALF_UP).doubleValue());
									if (dataObj.is_rec_report() && Objects.nonNull(currEnergy) && Objects.nonNull(lastEnergy) && lastEnergy > 0) sanityCheckReport.addRecDifference2(BigDecimal.valueOf((currEnergy - lastEnergy) / lastEnergy).setScale(3, RoundingMode.HALF_UP).doubleValue());
								}
								
								if ((devicesByType.getMeter().size() > 0 && item.getDeviceTypeId() == 3) || (devicesByType.getMeter().size() == 0 && item.getDeviceTypeId() == 1)) {
									if (Objects.nonNull(currEnergy)) totalCurrEnergy = Optional.ofNullable(totalCurrEnergy).orElse(0.0) + currEnergy;
									if (Objects.nonNull(lastEnergy)) totalLastEnergy = Optional.ofNullable(totalLastEnergy).orElse(0.0) + lastEnergy;
								}
							}
							
							if(Objects.nonNull(totalCurrEnergy) && Objects.nonNull(totalLastEnergy) && totalCurrEnergy > 0) sanityCheckReport.setProductionDifference1(BigDecimal.valueOf((totalCurrEnergy - totalLastEnergy) / totalCurrEnergy).setScale(3, RoundingMode.HALF_UP).doubleValue());
							if(Objects.nonNull(totalCurrEnergy) && Objects.nonNull(totalLastEnergy) && totalLastEnergy > 0) sanityCheckReport.setProductionDifference2(BigDecimal.valueOf((totalCurrEnergy - totalLastEnergy) / totalLastEnergy).setScale(3, RoundingMode.HALF_UP).doubleValue());
							
							if (sanityCheckReport.getRecDifference1().stream().allMatch(Objects::isNull)) sanityCheckReport.getRecDifference1().clear();
							if (sanityCheckReport.getRecDifference2().stream().allMatch(Objects::isNull)) sanityCheckReport.getRecDifference2().clear();
							if (sanityCheckReport.getAccumulatedEnergyBOMByInverter().stream().allMatch(Objects::isNull)) sanityCheckReport.getAccumulatedEnergyBOMByInverter().clear();
							if (sanityCheckReport.getAccumulatedEnergyEOMByInverter().stream().allMatch(Objects::isNull)) sanityCheckReport.getAccumulatedEnergyEOMByInverter().clear();
							if (sanityCheckReport.getAccumulatedEnergyDifferenceByInverter().stream().allMatch(Objects::isNull)) sanityCheckReport.getAccumulatedEnergyDifferenceByInverter().clear();
							if (sanityCheckReport.getAccumulatedEnergyBOMByMeter().stream().allMatch(Objects::isNull)) sanityCheckReport.getAccumulatedEnergyBOMByMeter().clear();
							if (sanityCheckReport.getAccumulatedEnergyEOMByMeter().stream().allMatch(Objects::isNull)) sanityCheckReport.getAccumulatedEnergyEOMByMeter().clear();
							if (sanityCheckReport.getAccumulatedEnergyDifferenceByMeter().stream().allMatch(Objects::isNull)) sanityCheckReport.getAccumulatedEnergyDifferenceByMeter().clear();
							
							// irradiance
							if (devicesByType.getIrradiance().size() > 0) {
								siteObj.setGroupDevices(devicesByType.getIrradiance());
								List<Map<String, Double>> irradianceList = queryForList("Reports.getIrradianceSanityCheckReport", siteObj);
								
								if (irradianceList.size() > 0) {
									Map<String, Double> irradianceDevice = irradianceList.get(0);
									if (Objects.isNull(irradianceDevice)) return sanityCheckReport;
									
									Double current = irradianceDevice.get("current");
									Double last = irradianceDevice.get("last");
									if(Objects.nonNull(current) && Objects.nonNull(last) && current > 0) sanityCheckReport.setIrradianceDifference1(BigDecimal.valueOf((current - last) / current).setScale(3, RoundingMode.HALF_UP).doubleValue());
									if(Objects.nonNull(current) && Objects.nonNull(last) && last > 0) sanityCheckReport.setIrradianceDifference2(BigDecimal.valueOf((current - last) / last).setScale(3, RoundingMode.HALF_UP).doubleValue());
								}
							}
							
							return sanityCheckReport;
						} catch (Exception ex) {
							log.error(ex);
							return null;
						}
					});
					
					list.add(future);
				}
			}
			
			List<SanityCheckReportEntity> dataList = list.stream().map(future -> future.join()).filter(item -> item != null).collect(Collectors.toList());
			dataList.sort((a,b) -> a.getSiteName().compareTo(b.getSiteName()));
			obj.setDataReports(dataList);
			
			return obj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description update site rec_id
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean updateRECID(SiteEntity obj) {
		try {
			return update("Reports.updateRECID", obj) > 0;
		} catch (Exception ex) {
			log.error("Reports.updateRECID", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update site gu_id
	 * @author long.pham
	 * @since 2023-03-27
	 * @param id
	 */
	public boolean updateGUID(SiteEntity obj) {
		try {
			return update("Reports.updateGUID", obj) > 0;
		} catch (Exception ex) {
			log.error("Reports.updateGUID", ex);
			return false;
		}
	}
	
	/**
	 * @description get list site for page employee manage site
	 * @author long.pham
	 * @since 2021-01-07
	 */

	public List getListSiteByEmployee(ReportsEntity obj) {
		SiteService service = new SiteService();
		SiteEntity site = new SiteEntity();
		site.setId_sites(obj.getId_sites());
		site.setIs_supper_admin(obj.getIs_supper_admin());
		site.setDomain(obj.getDomain());
		site.setDomain_role(obj.getDomain_role());
		site.setId_employee(obj.getId_employee());
		
		return service.getAllSite(site);
	}
	/**
	 * @description Get list site sub-group by employee
	 * @author Hung.Bui
	 * @since 2023-07-24
	 */
	
	public List getListSiteSubGroupByEmployee(ReportsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Reports.getListSiteSubGroupByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description insert report
	 * @author long.pham
	 * @since 2021-12-27
	 */
	public ReportsEntity insertReports(ReportsEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			session.insert("Reports.insertReports", obj);
			int insertLastId = obj.getId();
			
			List dataSite = obj.getDataSite();
			if (insertLastId > 0 && dataSite != null && dataSite.size() > 0) {
				session.insert("Reports.insertReportSiteMap", obj);
			}
			
			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Reports.insertReports", ex);
			return null;
		} finally {
			session.close();
		}

	}

	/**
	 * @description update role
	 * @author long.pham
	 * @since 2021-01-08
	 * @param id
	 */
	public boolean updateReports(ReportsEntity obj) {

		SqlSession session = this.beginTransaction();
		try {
			session.update("Reports.updateReports", obj);
			
			List dataSite = obj.getDataSite();
			
			session.delete("Reports.deleteReportSiteMap", obj);
			if (obj.getType_option() == 2 && dataSite != null && dataSite.size() > 0) session.insert("Reports.insertReportSiteMap", obj);
			
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("Reports.updateReports", ex);
			return false;
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description duplicate report
	 * @author Hung.Bui
	 * @since 2025-08-07
	 */
	public ReportDuplicateRequest duplicate(ReportDuplicateRequest obj) {
		SqlSession session = this.beginTransaction();
		
		try {
			session.insert("Reports.duplicate", obj);
			int insertLastId = obj.getNewId();
			if (insertLastId == 0) return null;
			
			session.insert("Reports.duplicateReportSiteMap", obj);
			session.commit();
			
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Reports.duplicate", ex);
			
			return null;
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description download report
	 * @author Hung.Bui
	 * @since 2025-08-07
	 */
	public Resource download(ViewReportEntity obj) {
		try {
			BatchJob batchJob = new BatchJob();
			
			// download one report
			Resource resource = batchJob.reportDownload(obj);
			if (Objects.isNull(resource)) return null;
			byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
			if (resource.exists()) resource.getFile().delete();
			return new ByteArrayResource(bytes);
			
			// download all reports
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ZipOutputStream zos = new ZipOutputStream(baos);
//			List<CompletableFuture<Resource>> list = new ArrayList<CompletableFuture<Resource>>();
//			
//			for (int i = 0; i < obj.size(); i++) {
//				ViewReportEntity reportEntity = obj.get(i);
//				CompletableFuture<Resource> future = CompletableFuture.supplyAsync(() -> batchJob.reportDownload(reportEntity));
//				list.add(future);
//			}
//			
//			list.stream().forEach(future -> {
//				try {
//					Resource resource = future.join();
//					if (Objects.isNull(resource)) return;
//					zos.putNextEntry(new ZipEntry(resource.getFilename()));
//					IOUtils.copy(resource.getInputStream(), zos);
//					zos.closeEntry();
//					if (resource.exists()) resource.getFile().delete();
//				} catch (IOException e) {
//				}
//			});
//			
//			zos.close();
//			baos.close();
//			
//			return new ByteArrayResource(baos.toByteArray());
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @description get list site by id customer
	 * @author long.pham
	 * @since 2021-12-27
	 * @param object
	 */

	public List getList(ReportsEntity obj) {
		try {
			List<Map<String, Object>> dataList = new ArrayList();
			dataList = queryForList("Reports.getList", obj);
			if (dataList == null) return new ArrayList();
			
			ObjectMapper mapper = new ObjectMapper();
			dataList.forEach(item -> {
				try {
					if (item.get("sitesListJSON") != null) item.put("sitesList", mapper.readValue(item.get("sitesListJSON").toString(), new TypeReference<List<Map<String, Object>>>(){}));
				} catch (JsonProcessingException e) {
					item.put("sitesList", new ArrayList<Map<String, Object>>());
				}
				item.remove("sitesListJSON");
			});
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}

	public int getTotalRecord(ReportsEntity obj) {
		try {
			return (int) queryForObject("Reports.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * @description delete site
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean deleteReports(ReportsEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			session.delete("Reports.deleteReportSiteMap", obj);
			int rowDelete = session.delete("Reports.deleteReports", obj);
			session.update("Reports.auditLogging", obj);
			session.commit();
			return rowDelete > 0;
		} catch (Exception ex) {
			session.rollback();
			log.error("Reports.deleteReports", ex);
			return false;
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description Get report logs
	 * @author Hung.Bui
	 * @since 2025-08-19
	 * @param id
	 */
	public List<AuditLog> getLogs(ReportsEntity obj) {
		try {
			List<ReportLogs> logs = queryForList("Reports.getLogs", obj);
			if (Objects.isNull(logs)) return new ArrayList<>();
			AuditingLogsService logsService = new AuditingLogsService();
			return logsService.getLogDifferences(logs, null);
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-06-12
	 * @param id_site
	 */
	
	public ViewReportEntity getMonthlyReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = getReportDetail(obj);
			if (dataObj == null) return null;
			
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setTable_data_report(dataObj.getTable_data_report());
			obj.setHave_meter(dataObj.isHave_meter());
			
			if (dataObj.isHave_poa()) {
				CustomerViewService customerViewService = new CustomerViewService();
				SiteEntity siteObj = new SiteEntity();
				siteObj.setId_site(dataObj.getId_site());
				siteObj.setStart_date(obj.getStart_date());
				siteObj.setEnd_date(obj.getEnd_date());
				siteObj.setFilterBy(ChartingFilter.THIS_MONTH.getValue());
				siteObj.setData_send_time(ChartingGranularity._1_DAY.getValue());
				siteObj.setTable_data_virtual(dataObj.getTable_data_virtual());
				siteObj.setTable_data_report(dataObj.getTable_data_report());
				siteObj.setIs_show_each_meter(0);
				siteObj.setTotalMeter(dataObj.isHave_meter() ? 1 : 0);
				siteObj.setHidden_data_list(new ArrayList<>());
				siteObj.setEnable_virtual_device(dataObj.isEnable_virtual_device() ? 1 : 0);
				
				List<PerformanceDataChartItemEntity> data = customerViewService.getChartDataPerformance(siteObj);
				List<ClientMonthlyDateEntity> actualData = data.stream().filter(item -> item.getType().equals("chart_energy_kwh")).findFirst().orElse(new PerformanceDataChartItemEntity()).getData_energy();
				List<ClientMonthlyDateEntity> estimatedData = data.stream().filter(item -> item.getType().equals("expected_power") || item.getType().equals("expected_energy")).findFirst().orElse(new PerformanceDataChartItemEntity()).getData_energy();
				List<MonthlyDateEntity> reportData = new ArrayList<>();
				
				if (Objects.nonNull(actualData)) {
					for (int i = 0; i < actualData.size(); i++) {
						MonthlyDateEntity item = new MonthlyDateEntity();
						ClientMonthlyDateEntity actualItem = actualData.get(i);
						ClientMonthlyDateEntity estimatedItem = Objects.nonNull(estimatedData) && estimatedData.size() > 0 ? estimatedData.get(i) : new ClientMonthlyDateEntity();
						
						item.setCategories_time(LocalDate.parse(actualItem.getTime_full()).format(DateTimeFormatter.ofPattern("MM/dd/yyy")));
						item.setActual(actualItem.getChart_energy_kwh());
						item.setEstimated(estimatedItem.getExpected_energy());
						if (Objects.nonNull(item.getActual()) && Objects.nonNull(item.getEstimated()) && item.getEstimated() > 0) item.setPercent(BigDecimal.valueOf(item.getActual() / item.getEstimated() * 100).setScale(1, RoundingMode.HALF_UP).doubleValue());
						
						reportData.add(item);
					}
				}
				
				dataObj.setDataReports(reportData);
			} else {
				List<MonthlyDateEntity> dataEnergy = queryForList("Reports.getDataEnergyMonthlyReport", obj);
				dataObj.setDataReports(Lib.fulfillData(getDateTimeList(obj, MonthlyDateEntity.class), dataEnergy, "categories_time"));
			}
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	/**
	 * @description get custom  report 
	 * @author Hung.Bui
	 * @since 2022-12-15
	 * @param id_site, date_from, date_to
	 */
	
	public ViewReportEntity getCustomReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = getReportDetail(obj);
			if (dataObj == null) return null;
			
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setTable_data_report(dataObj.getTable_data_report());
			obj.setHave_meter(dataObj.isHave_meter());
			List<CustomReportDataEntity> fulfillData = new ArrayList<>(); 
			
			if (obj.getData_intervals() == ReportIntervals._15_MINUTES.getValue() || obj.getData_intervals() == ReportIntervals._30_MINUTES.getValue()) {
				CustomerViewService customerService = new CustomerViewService();
				DevicesByTypeEntity devices = customerService.getDevicesBySite(obj);
				List<DeviceEntity> meterDevices = devices.getMeter();
				List<DeviceEntity> inverterDevices = devices.getInverter();
				List<DeviceEntity> powerDevices = meterDevices.size() > 0 ? meterDevices : inverterDevices;
				
				obj.setGroupDevices(powerDevices);
				List<DailyDateEntity> dataPower = getEnergyByMeter(obj);
				
				for (DailyDateEntity item : dataPower) {
					CustomReportDataEntity dataItem = new CustomReportDataEntity();
					dataItem.setCategories_time(item.getCategories_time());
					dataItem.setActual(item.getEnergy());
					fulfillData.add(dataItem);
				}
			} else {
				List<CustomReportDataEntity> dataPower = queryForList("Reports.getDataEnergyCustomReport", obj);
				fulfillData = Lib.fulfillData(getDateTimeList(obj, CustomReportDataEntity.class), dataPower, "categories_time");
			}
			
			if (fulfillData.size() > 0) {
				CustomReportDataEntity totalRow = new CustomReportDataEntity();
				totalRow.setCategories_time("Total");
				totalRow.setActual(fulfillData.stream().filter(item -> item.getActual() != null).mapToDouble(item -> item.getActual()).sum());
				
				fulfillData.add(totalRow);
			}
			
			dataObj.setDataReports(fulfillData);
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	

	public Object getSiteDetail(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("Reports.getSiteDetail", obj);
			if (dataObj == null) {
				return null;
			}
			
			switch (dataObj.getId_site_type()) {
				case 1:
				case 6:
				case 2:
				case 4:
					obj.setTable_name("model_shark100");
					break;
				
				case 3:
					obj.setTable_name("");
					break;
				case 5:
				case 9:
					obj.setTable_name("model_veris_industries_e51c2_power_meter");
					break;
				case 7:
					obj.setTable_name("model_elkor_wattson_pv_meter");
					break;
				case 8:
					obj.setTable_name("");
					break;
			}
			
			List dataEnergy = queryForList("Reports.getReportYearDataEnergy", obj);
			dataObj.setDataReports(dataEnergy);
			
			List dataEnergyExpectations = queryForList("Reports.getReportEnergyExpectations", obj);
			dataObj.setDataExpectations(dataEnergyExpectations);
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	/**
	 * @description RENEWABLE ENERGY CREDITS
	 * @author long.pham
	 * @since 2022-06-06
	 * @param {}
	 */
	
	public Object renewableReportMonth(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("SitesReports.getDetailReport", obj);
			if (dataObj == null) {
				return null;
			}
			List dataListDeviceMeter = queryForList("SitesReports.getListDeviceTypeMeter", obj);
			if(dataListDeviceMeter.size() > 0 ) {
				obj.setGroupDevices(dataListDeviceMeter);
				for (int i = 0; i < dataListDeviceMeter.size(); i++) {
					Map<String, Object> deviceItem = (Map<String, Object>) dataListDeviceMeter.get(i);
					List dataEnergy = queryForList("SitesReports.getDataEnergyMeterMonth", obj);
					if (dataEnergy.size() > 0) {
						dataObj.setDataReports(dataEnergy);
					}
				}
			} else {
				List dataListInverter = queryForList("SitesReports.getListDeviceTypeInverter", obj);
				if(dataListInverter.size() > 0) {
					for (int i = 0; i < dataListInverter.size(); i++) {
						Map<String, Object> deviceItem = (Map<String, Object>) dataListInverter.get(i);
						obj.setGroupDevices(dataListInverter);
						List dataPower = queryForList("SitesReports.getDataEnergyInverterMonth", obj);
						if (dataPower.size() > 0) {
							dataObj.setDataReports(dataPower);
						}
					}
				} 
			}
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}

	
	/**
	 * @description get list rec report 
	 * @author long.pham
	 * @since 2022-06-22
	 * @param arr id_site
	 */

	public List getListREC(ReportsEntity obj) {
		try {
			List dataList = queryForList("Reports.getDataEnergyRECReport", obj);
			if (dataList.size() <=0)
				return new ArrayList();
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description send mail daily report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailDailyReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return false;
			String filePath = obj.getFile_type() == ReportFileType.PDF.getValue() ? createDailyReportPdfFile(obj, dataObjList) : createDailyReportSheetFile(obj, dataObjList);
			if (filePath == null) return false;
			
			sentReportByMail(filePath, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), 16, "Customer", obj.getCadence_range_name());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download daily report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadDailyReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return null;
			return obj.getFile_type() == ReportFileType.PDF.getValue() ? createDailyReportPdfFile(obj, dataObjList) : createDailyReportSheetFile(obj, dataObjList);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description send mail monthly report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailMonthlyReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return false;
			String filePath = obj.getFile_type() == ReportFileType.PDF.getValue() ? createMonthlyReportPdfFile(obj, dataObjList) : createMonthlyReportSheetFile(obj, dataObjList);
			if (filePath == null) return false;
			
			sentReportByMail(filePath, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), 16, "Customer", obj.getCadence_range_name());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download monthly report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadMonthlyReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return null;
			return obj.getFile_type() == ReportFileType.PDF.getValue() ? createMonthlyReportPdfFile(obj, dataObjList) : createMonthlyReportSheetFile(obj, dataObjList);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description send mail quarterly report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailQuarterlyReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return false;
			String filePath = obj.getFile_type() == ReportFileType.PDF.getValue() ? createQuarterlyReportPdfFile(obj, dataObjList) : createQuarterlyReportSheetFile(obj, dataObjList);
			if (filePath == null) return false;
			
			sentReportByMail(filePath, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), 16, "Customer", obj.getCadence_range_name());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download quarterly report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadQuarterlyReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return null;
			return obj.getFile_type() == ReportFileType.PDF.getValue() ? createQuarterlyReportPdfFile(obj, dataObjList) : createQuarterlyReportSheetFile(obj, dataObjList);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description send mail annually report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailAnnuallyReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return false;
			String filePath = obj.getFile_type() == ReportFileType.PDF.getValue() ? createAnnuallyReportPdfFile(obj, dataObjList) : createAnnuallyReportSheetFile(obj, dataObjList);
			if (filePath == null) return false;
			
			sentReportByMail(filePath, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), 16, "Customer", obj.getCadence_range_name());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download annually report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadAnnuallyReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return null;
			return obj.getFile_type() == ReportFileType.PDF.getValue() ? createAnnuallyReportPdfFile(obj, dataObjList) : createAnnuallyReportSheetFile(obj, dataObjList);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description send mail custom report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailCustomReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return false;
			String filePath = obj.getFile_type() == ReportFileType.PDF.getValue() ? createCustomReportPdfFile(obj, dataObjList) : createCustomReportSheetFile(obj, dataObjList);
			if (filePath == null) return false;
			
			sentReportByMail(filePath, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), 16, "Customer", obj.getCadence_range_name());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download custom report
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadCustomReport(ViewReportEntity obj) {
		try {
			List<ViewReportEntity> dataObjList = getReportDataList(obj);
			if (dataObjList == null || dataObjList.size() == 0) return null;
			return obj.getFile_type() == ReportFileType.PDF.getValue() ? createCustomReportPdfFile(obj, dataObjList) : createCustomReportSheetFile(obj, dataObjList);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * send mail asset management and performance report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailAssetManagementAndOperationPerformanceReport(ViewReportEntity obj) {
		try {
			AssetManagementAndOperationPerformanceReportEntity dataObj = getAssetManagementAndOperationPerformanceReport(obj);
			if (dataObj == null) return false;
			String filePath = createAssetManagementAndOperationPerformanceReportSheetFile(dataObj);
			if (filePath == null) return false;
			
			sentReportByMail(filePath, dataObj.getReportDetail().getSubscribers(), "asset-management-and-operation-performance", 21, dataObj.getReportDetail().getSite_name(), dataObj.getReportDetail().getId_site(), "Customer");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download asset management and performance report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadAssetManagementAndOperationPerformanceReport(ViewReportEntity obj) {
		try {
			AssetManagementAndOperationPerformanceReportEntity dataObj = getAssetManagementAndOperationPerformanceReport(obj);
			if (dataObj == null) return null;
			return createAssetManagementAndOperationPerformanceReportSheetFile(dataObj);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * send mail sanity check report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public boolean sentMailSanityCheckReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = getSanityCheckReport(obj);
			if (dataObj == null) return false;
			dataObj.setStart_date(obj.getStart_date());
			dataObj.setEnd_date(obj.getEnd_date());
			String filePath = createSanityCheckReportSheetFile(dataObj);
			if (filePath == null) return false;
			
			sentReportByMail(filePath, dataObj.getSubscribers(), "sanity-check", 25);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @description download anity check report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 */
	public String downloadSanityCheckReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = getSanityCheckReport(obj);
			if (dataObj == null) return null;
			dataObj.setStart_date(obj.getStart_date());
			dataObj.setEnd_date(obj.getEnd_date());
			return createSanityCheckReportSheetFile(dataObj);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description write to sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param document
	 * @param reportName
	 * @return file path
	 */
	public String writeToSheetFile(XSSFWorkbook document, String reportName) {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		String fileName = getReportFolderPath() + "/" + reportName.replaceAll("/", "_") + "-" + timeStamp + ".xlsx";
		
		try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
			document.write(fileOut);
			return fileName;
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * @description write to pdf file
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param reportName
	 */
	public File writeToPdfFile(String reportName) throws Exception {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		String fileName = getReportFolderPath() + "/" + reportName.replaceAll("/", "_") + "-" + timeStamp + ".pdf";
		return new File(fileName);
	}
	
	/**
	 * @description sent excel report by mail in template
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param document, subscribers, cadenceRange
	 */
	public void sentReportByMail(String filePath, String subscribers, String cadenceRangeName, int templateState, Object... templateSubstitutions) throws Exception {
		String mailFromContact = Lib.getReourcePropValue(Constants.mailConfigFileName, Constants.mailFromContact);
		String msgTemplate = Constants.getMailTempleteByState(templateState);
		String body = String.format(msgTemplate, templateSubstitutions);
		String mailTo = subscribers;
		String subject = Constants.getMailSubjectByState(templateState);

		String tags = "report_" + cadenceRangeName.toLowerCase();
		String fromName = "NEXT WAVE ENERGY MONITORING INC";
		boolean flagSent = SendMail.SendGmailTLSAttachment(mailFromContact, fromName, mailTo, subject, body, tags, filePath);
		if (!flagSent) {
			throw new Exception(Translator.toLocale(Constants.SENT_EMAIL_ERROR));
		}
		File file = new File(filePath);
		if (file.exists()) file.delete();
	}
	
	/**
	 * @description create daily report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createDailyReportSheetFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			
			for (int i = 0; i < dataObjList.size(); i++) {
			try {
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
						switch (ReportIntervals.fromValue(dataObj.getData_intervals())) {
							case _5_MINUTE:
								dataIntervals = 288;
								break;
							case _15_MINUTES:
								dataIntervals = 96;
								break;
							case _1_HOUR:
								dataIntervals = 24;
								break;
							default:
								break;
						}
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
						DocumentHelper.addSeries(dataExports.stream().allMatch(item -> item.getEnergy() == null), data, categoriesData, valuesData2, "Actual Energy (kWh)", XDDFColor.from(PresetColor.LIGHT_SKY_BLUE), null);
						
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
			} catch (Exception e) {}
			}
			
			return writeToSheetFile(document, obj.getReport_name());
		} catch (Exception e) {
			return null;
		}
	}
	
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
			cell.setCellValue("Actual Energy (kWh)");
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
	 * @description create daily report pdf file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createDailyReportPdfFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try {
			File file = writeToPdfFile(obj.getReport_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
				try {
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
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Actual Energy (kWh)").setBold()));
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
						TimeSeries powerSeries = new TimeSeries("Actual Power (kW)");
						TimeSeries energySeries = new TimeSeries("Actual Energy (kWh)");
						TimeSeries irradianceSeries = new TimeSeries("Irradiance (W/m2)");
						
						for (int i = 0; i < dataExports.size(); i++) {
							DailyDateEntity item = dataExports.get(i);
							RegularTimePeriod period = new Minute(categoryFormat.parse(item.getCategories_time()));
							
							powerSeries.addOrUpdate(period, item.getPower());
							energySeries.addOrUpdate(period, item.getEnergy());
							irradianceSeries.addOrUpdate(period, item.getIrradiance());
						}
						
						TimeSeriesCollection powerDataset = DocumentHelper.createJFreeChartLineDataset(0, plot, null);
						powerDataset.addSeries(powerSeries);
						plot.getRendererForDataset(powerDataset).setSeriesPaint(0, BLUE_COLOR);
						
						TimeSeriesCollection energyDataset = DocumentHelper.createJFreeChartLineDataset(1, plot, null);
						energyDataset.addSeries(energySeries);
						plot.getRendererForDataset(energyDataset).setSeriesPaint(0, LIGHT_BLUE_COLOR);
						
						TimeSeriesCollection irradianceDataset = DocumentHelper.createJFreeChartLineDataset(2, plot, null);
						irradianceDataset.addSeries(irradianceSeries);
						plot.getRendererForDataset(irradianceDataset).setSeriesPaint(0, ORANGE_COLOR);
						plot.getRendererForDataset(irradianceDataset).setSeriesVisible(0, dataObj.isHave_poa());
						
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
				} catch (Exception e) {}
				}
				
				// It must be closed before attach to mail
				document.close();
				
				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description create monthly report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createMonthlyReportSheetFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			
			for (int i = 0; i < dataObjList.size(); i++) {
			try {
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
			} catch (Exception e) {}
			}
				
			return writeToSheetFile(document, obj.getReport_name());
		} catch (Exception e) {
			return null;
		}
	}
	
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
	
	/**
	 * @description create monthly report pdf file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createMonthlyReportPdfFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try {
			File file = writeToPdfFile(obj.getReport_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
				try {
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
						TimeSeries actualSeries = new TimeSeries("Actual Generation (kWh)");
						TimeSeries estimateSeries = new TimeSeries("Estimate Generation (kWh)");
						TimeSeries estimateIndexSeries = new TimeSeries("Estimate Generation Index (%)");
						
						for (int i = 0; i < dataExports.size(); i++) {
							MonthlyDateEntity item = (MonthlyDateEntity) dataExports.get(i);
							RegularTimePeriod period = new Day(dateFormat.parse(item.getCategories_time()));
							
							actualSeries.addOrUpdate(period, item.getActual());
							estimateSeries.addOrUpdate(period, item.getEstimated());
							estimateIndexSeries.addOrUpdate(period, item.getPercent());
						}
						
						TimeSeriesCollection barDataset = DocumentHelper.createJFreeChartBarDataset(0, plot);
						barDataset.addSeries(actualSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(0, BLUE_COLOR);
						barDataset.addSeries(estimateSeries);
						plot.getRendererForDataset(barDataset).setSeriesPaint(1, LIGHT_BLUE_COLOR);
						
						TimeSeriesCollection lineDataset = DocumentHelper.createJFreeChartLineDataset(1, plot, null);
						lineDataset.addSeries(estimateIndexSeries);
						plot.getRendererForDataset(lineDataset).setSeriesPaint(0, Color.gray);
						
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
				} catch (Exception e) {}
				}
					
				// It must be closed before attach to mail
				document.close();

				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description create quarterly report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createQuarterlyReportSheetFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
				
			for (int i = 0; i < dataObjList.size(); i++) {
			try {
				ViewReportEntity dataObj = dataObjList.get(i);
					
				if (dataObj != null) {
					boolean quarterlyReportByMonth = dataObj.getData_intervals() == ReportIntervals.MONTHLY.getValue();

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
			} catch (Exception e) {}
			}
			
			return writeToSheetFile(document, obj.getReport_name());
		} catch (Exception e) {
			return null;
		}
	}
	
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
			
			boolean quarterlyReportByMonth = dataObj.getData_intervals() == ReportIntervals.MONTHLY.getValue();

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
	 * @description create quarterly report pdf file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createQuarterlyReportPdfFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try {
			File file = writeToPdfFile(obj.getReport_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
				try {
					ViewReportEntity dataObj = dataObjList.get(l);
					
					if (dataObj != null) {
						boolean quarterlyReportByMonth = dataObj.getData_intervals() == ReportIntervals.MONTHLY.getValue();
	
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
							TimeSeries estimateSeries = new TimeSeries("Estimate Generation (kWh)");
							TimeSeries actualSeries = new TimeSeries("Actual Generation (kWh)");
							
							for (int i = 0; i < dataExports.size(); i++) {
								QuarterlyDateEntity item = dataExports.get(i);
								RegularTimePeriod period = new Month(monthYearFormat.parse(item.getCategories_time()));
								
								estimateSeries.addOrUpdate(period, item.getEstimated());
								actualSeries.addOrUpdate(period, item.getActual());
							}
							
							TimeSeriesCollection barDataset = DocumentHelper.createJFreeChartBarDataset(0, plot);
							barDataset.addSeries(estimateSeries);
							plot.getRendererForDataset(barDataset).setSeriesPaint(0, BLUE_COLOR);
							barDataset.addSeries(actualSeries);
							plot.getRendererForDataset(barDataset).setSeriesPaint(1, LIGHT_BLUE_COLOR);
							
							// category axis
							DocumentHelper.createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.MONTH, 1, monthYearFormat), startDate, endDate);
							// left axis
							DocumentHelper.createJFreeChartNumberAxis(null, AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
							
							chartCell1.add(new Image(ImageDataFactory.create(chart.createBufferedImage(900, 300), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(600, 300));
							
							//====== second chart ============================================================
							JFreeChart chart2 = DocumentHelper.createJFreeChart(null);
							XYPlot plot2 = chart2.getXYPlot();
							
							// data source
							TimeSeries estimatedCumulativeSeries = new TimeSeries("Estimate Generation (kWh)");
							TimeSeries actualCumulativeSeries = new TimeSeries("Actual Generation (kWh)");
							
							for (int i = 0; i < dataExports.size(); i++) {
								QuarterlyDateEntity item = dataExports.get(i);
								RegularTimePeriod period = new Month(monthYearFormat.parse(item.getCategories_time()));
								
								estimatedCumulativeSeries.addOrUpdate(period, item.getEstimatedCumulative());
								actualCumulativeSeries.addOrUpdate(period, item.getActualCumulative());
							}
							
							TimeSeriesCollection barDataset2 = DocumentHelper.createJFreeChartBarDataset(0, plot2);
							barDataset2.addSeries(estimatedCumulativeSeries);
							plot2.getRendererForDataset(barDataset2).setSeriesPaint(0, BLUE_COLOR);
							barDataset2.addSeries(actualCumulativeSeries);
							plot2.getRendererForDataset(barDataset2).setSeriesPaint(1, LIGHT_BLUE_COLOR);
							
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
				} catch (Exception e) {}
				}
				
				// It must be closed before attach to mail
				document.close();

				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description create annually report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createAnnuallyReportSheetFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
				
			for (int i = 0; i < dataObjList.size(); i++) {
			try {
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
			} catch (Exception e) {}
			}
			
			return writeToSheetFile(document, obj.getReport_name());
		} catch (Exception e) {
			return null;
		}
	}
	
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
	 * @description create annually report pdf file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createAnnuallyReportPdfFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try {
			File file = writeToPdfFile(obj.getReport_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				for (int l = 0; l < dataObjList.size(); l++) {
				try {
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
						
						TimeSeriesCollection lineDataset = DocumentHelper.createJFreeChartLineDataset(1, plot, null);
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
				} catch (Exception e) {}
				}
				
				// It must be closed before attach to mail
				document.close();
				
				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description create custom report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createCustomReportSheetFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
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
			
			// adjust tick mark position based on data intervals
			int dataIntervals = 1;
			if (obj.getData_intervals() == ReportIntervals._15_MINUTES.getValue()) dataIntervals = 96;
			else if (obj.getData_intervals() == ReportIntervals._30_MINUTES.getValue()) dataIntervals = 48;
            chart.getCTChart().getPlotArea().getCatAxArray(0).addNewTickLblSkip().setVal(dataIntervals);
            chart.getCTChart().getPlotArea().getCatAxArray(0).addNewTickMarkSkip().setVal(dataIntervals);
			
			// left value axis
			XDDFValueAxis leftAxis = DocumentHelper.createLeftValueAxis(chart, "kWh");
			
			// report information and table
			writeHeaderCustomReport(sheet, obj, dataObjList);
			
			for (int i = 0; i < dataObjList.size(); i++) {
			try {
				ViewReportEntity dataObj = dataObjList.get(i);
				if (dataObj.getSite_name().equals("Total")) continue; // exclude total by interval
				
				if (dataObj != null) {
					List<CustomReportDataEntity> dataExports = dataObj.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() - 1 : 0; // exclude total row
					
					if (numOfPoints > 0) {
						// data sources
						XDDFDataSource<String> categoriesData = XDDFDataSourcesFactory.fromStringCellRange(sheet, obj.isTransposed() ? new CellRangeAddress(25, 25 + numOfPoints - 1, 0, 0) : new CellRangeAddress(24, 24, 3, 3 + numOfPoints - 1));
						XDDFNumericalDataSource<Double> valuesData = XDDFDataSourcesFactory.fromNumericCellRange(sheet, obj.isTransposed() ? new CellRangeAddress(25, 25 + numOfPoints - 1, 3 + 3*i, 3 + 3*i) : new CellRangeAddress(25 + i, 25 + i, 3, 3 + numOfPoints - 1));
						
						XDDFChartData data = DocumentHelper.createChartData(chart, ChartTypes.LINE, bottomAxis, leftAxis);
						Series lineSeries = DocumentHelper.addSeries(dataExports.stream().filter(item -> !item.getCategories_time().equals("Total")).allMatch(item -> item.getActual() == null), data, categoriesData, valuesData, dataObj.getSite_name());
						if (numOfPoints == 1) DocumentHelper.solidFillLineMarker(chart, lineSeries, 0, MarkerStyle.CIRCLE, null);
						chart.plot(data);
					}
				}
			} catch (Exception e) {}
			}
			
			return writeToSheetFile(document, obj.getReport_name());
		} catch (Exception e) {
			return null;
		}
	}
	
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
//			cell.setCellStyle(reportInfoBoldCellStyle);
//			cell.setCellValue("Site Name");
			cell = row.createCell(1);
//			cell.setCellStyle(reportInfoBoldCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
			
			cell = row.createCell(2);
			row.setHeight((short) 600);
//			cell.setCellStyle(reportInfoBoldCellStyle);
//			cell.setCellValue(dataObj.getSite_name());
			cell = row.createCell(3);
//			cell.setCellStyle(reportInfoBoldCellStyle);
			cell = row.createCell(4);
//			cell.setCellStyle(reportInfoBoldCellStyle);
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
			if (report.getData_intervals() == ReportIntervals.ANNUAL.getValue()) format = new SimpleDateFormat("yyyy");
			else if (report.getData_intervals() == ReportIntervals.MONTHLY.getValue()) format = new SimpleDateFormat("MM/yyyy");
			else format = new SimpleDateFormat("MM/dd/yyyy");
			
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
//			cell.setCellStyle(reportInfoBoldCellStyle);
//			cell.setCellValue("System Size (kW DC)");
			cell = row.createCell(1);
//			cell.setCellStyle(reportInfoBoldCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
			
			cell = row.createCell(2);
//			cell.setCellStyle(reportInfoCellStyle);
//			cell.setCellValue(dataObj.getDc_capacity());
			cell = row.createCell(3);
//			cell.setCellStyle(reportInfoCellStyle);
			cell = row.createCell(4);
//			cell.setCellStyle(reportInfoCellStyle);
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
				try {
					ViewReportEntity dataObj = dataList.get(i);
					if (dataObj.getSite_name().equals("Total") && !report.isShowTotal()) continue;
					
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
								sheet.addMergedRegionUnsafe(new CellRangeAddress(t, t, 0, 2));
							}
							
							Cell cel26G = row26.createCell(3 + 3*i);
							cel26G.setCellStyle(tableRowNoDecimalCellStyle);
							if(item.getActual() != null) cel26G.setCellValue(item.getActual());
							Cell cel26H = row26.createCell(4 + 3*i);
							cel26H.setCellStyle(tableRowNoDecimalCellStyle);
							Cell cel26I = row26.createCell(5 + 3*i);
							cel26I.setCellStyle(tableRowNoDecimalCellStyle);
							sheet.addMergedRegionUnsafe(new CellRangeAddress(t, t, 3 + 3*i, 5 + 3*i));
						}
					}
				} catch (Exception e) {}
				}
			} else {
				for (int i = 0; i < dataList.size(); i++) {
				try {
					ViewReportEntity dataObj = dataList.get(i);
					if (dataObj.getSite_name().equals("Total") && !report.isShowTotal()) continue;
					
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
				} catch (Exception e) {}
				}
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * @description create custom report pdf file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createCustomReportPdfFile(ViewReportEntity obj, List<ViewReportEntity> dataObjList) {
		try {
			File file = writeToPdfFile(obj.getReport_name());
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				// total column: 12
				Table table = new Table(12, true).useAllAvailableWidth();
				table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
				table.setFontSize(8);
				table.setTextAlignment(TextAlignment.CENTER);
				document.add(table);
				
				Image logoImage = DocumentHelper.readLogoImageFile();
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat format  = new SimpleDateFormat("MM/yyyy");
				SimpleDateFormat coveredPeriodFormat = new SimpleDateFormat("MM/dd/yyyy");
				DateTickUnitType dateTickUnitType = DateTickUnitType.MONTH;
				int dateTickInterval = (int) Math.ceil((double) dataObjList.get(0).getDataReports().size() / 15);
				Date startDate = dateFormat.parse(obj.getStart_date());
				Date endDate = dateFormat.parse(obj.getEnd_date());
				
				// select format based on intervals
				switch (ReportIntervals.fromValue(obj.getData_intervals())) {
					case DAILY:
						format = new SimpleDateFormat("MM/dd/yyyy");
						dateTickUnitType = DateTickUnitType.DAY;
						break;
						
					case MONTHLY:
						format = new SimpleDateFormat("MM/yyyy");
						coveredPeriodFormat = new SimpleDateFormat("MM/yyyy");
						dateTickUnitType = DateTickUnitType.MONTH;
						startDate = Date.from(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().with(TemporalAdjusters.firstDayOfMonth()).atZone(ZoneId.systemDefault()).toInstant());
						endDate = Date.from(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().with(TemporalAdjusters.lastDayOfMonth()).atZone(ZoneId.systemDefault()).toInstant());
						break;
						
					case ANNUAL:
						format = new SimpleDateFormat("yyyy");
						coveredPeriodFormat = new SimpleDateFormat("yyyy");
						dateTickUnitType = DateTickUnitType.YEAR;
						break;
					
					default:
						format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
						dateTickUnitType = DateTickUnitType.DAY;
						dateTickInterval = (int) Math.ceil((double) (TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS) + 1) / 15);
						break;
				}
				
				// header and logo
				table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(6, 7).add(new Paragraph("PRODUCTION REPORT")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
				table.addCell(new com.itextpdf.layout.element.Cell(6, 2).add(logoImage).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 1).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 2).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Report Date").setBold().setTextAlignment(TextAlignment.LEFT)));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(dataObjList.get(0).getReport_date()).setTextAlignment(TextAlignment.LEFT)));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Covered Period").setBold().setTextAlignment(TextAlignment.LEFT)));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(coveredPeriodFormat.format(startDate) + " - " + coveredPeriodFormat.format(endDate)).setTextAlignment(TextAlignment.LEFT)));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 1).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 2).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setHeight(14).setBorder(Border.NO_BORDER));
				table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
				
				//====== chart ============================================================
				JFreeChart chart = DocumentHelper.createJFreeChart("Actual Generation (kWh)");
				XYPlot plot = chart.getXYPlot();
				
				// category axis
				DocumentHelper.createJFreeChartDomainAxis(plot, new DateTickUnit(dateTickUnitType, dateTickInterval, format), startDate, endDate);
				// left axis
				DocumentHelper.createJFreeChartNumberAxis("kWh", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
				
				for (int l = 0; l < dataObjList.size(); l++) {
				try {
					ViewReportEntity dataObj = dataObjList.get(l);
					if (dataObj.getSite_name().equals("Total")) continue;
					
					if (dataObj != null) {
						List<CustomReportDataEntity> dataExports = dataObj.getDataReports() != null ? dataObj.getDataReports() : new ArrayList<>();
						
						// data source
						TimeSeries series = new TimeSeries(dataObj.getSite_name());
						
						for (int i = 0; i < dataExports.size(); i++) {
							CustomReportDataEntity item = dataExports.get(i);
							String itemCategoryTime = item.getCategories_time();
							Double itemActual = item.getActual();
							
							if (itemCategoryTime.equals("Total")) continue;
							RegularTimePeriod period = new Month(format.parse(itemCategoryTime));
							switch (ReportIntervals.fromValue(obj.getData_intervals())) {
								case DAILY:
									period = new Day(format.parse(itemCategoryTime));
									break;
								case MONTHLY:
									period = new Month(format.parse(itemCategoryTime));
									break;
								case ANNUAL:
									period = new Year(format.parse(itemCategoryTime));
									break;
								default:
									period = new Minute(format.parse(itemCategoryTime));
									break;
							}
							
							series.addOrUpdate(period, itemActual);
						}
						
						int numOfPoints = dataExports != null ? dataExports.size() - 1 : 0; // exclude total row
						TimeSeriesCollection lineDataset = DocumentHelper.createJFreeChartLineDataset(l, plot, numOfPoints == 1 ? new Ellipse2D.Double(-3, -3, 6, 6) : null);
						lineDataset.addSeries(series);
					}
				} catch (Exception e) {}
				}
				
				com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(16, 12);
				table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
				chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1800, 700), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 700));

				//====== table ============================================================
				DecimalFormat dfs = new DecimalFormat(DocumentHelper.noDecimalDataFormat);
				
				for (int l = 0; l < dataObjList.size(); l++) {
				try {
					ViewReportEntity dataObj = dataObjList.get(l);
					if (dataObj.getSite_name().equals("Total") && !obj.isShowTotal()) continue;

					if (dataObj != null) {
						List<CustomReportDataEntity> dataExports = dataObj.getDataReports() != null ? dataObj.getDataReports() : new ArrayList<>();
						
						// empty row
						table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
						
						// header of data table
						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setBorder(Border.NO_BORDER));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph("Timestamp").setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataObj.getSite_name()).setBold()));
						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setBorder(Border.NO_BORDER));
						
						for (int i = 0; i < dataExports.size(); i++) {
							CustomReportDataEntity item = dataExports.get(i);
							String itemCategoryTime = item.getCategories_time();
							Double itemActual = item.getActual();
							
							if (itemCategoryTime.equals("Total") && !obj.isShowTotal()) continue;
							table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setBorder(Border.NO_BORDER));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 2).add(new Paragraph(itemCategoryTime)));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(itemActual != null ? dfs.format(itemActual).toString() : "")));
							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).setBorder(Border.NO_BORDER));
							
							if (i % 100 == 0) table.flush();
						}
					}
				} catch (Exception e) {}
				}
				
				table.complete();
				// It must be closed before attach to mail
				document.close();
					
				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @description create asset management and performance report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createAssetManagementAndOperationPerformanceReportSheetFile(AssetManagementAndOperationPerformanceReportEntity dataObj) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			
			/* === operation performance === */
			{
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
			{
				List<AssetManagementAndOperationPerformanceDataEntity> data = dataObj.getMonthlyPerformanceData() != null ? (List<AssetManagementAndOperationPerformanceDataEntity>) dataObj.getMonthlyPerformanceData().get("data") : new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				AssetManagementAndOperationPerformanceDataEntity total = dataObj.getMonthlyPerformanceData() != null ? (AssetManagementAndOperationPerformanceDataEntity) dataObj.getMonthlyPerformanceData().get("total") : new AssetManagementAndOperationPerformanceDataEntity();
				
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
			
			/* === monthly asset performance === */
			{
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
			{
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
			
			return writeToSheetFile(document, dataObj.getReportDetail().getReport_name());
		} catch (Exception e) {
			return null;
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
	
	/**
	 * @description create sanity check report sheet file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
	public String createSanityCheckReportSheetFile(ViewReportEntity dataObj) {
		try (XSSFWorkbook document = new XSSFWorkbook()) {
			XSSFSheet sheet = document.createSheet("Sanity Check Report");
			
			// insert logo image
			int pictureIdx = DocumentHelper.readLogoImageFile(document);
			ClientAnchor logoAnchor = new XSSFClientAnchor(0, 10 * Units.EMU_PER_PIXEL, 0, -10 * Units.EMU_PER_PIXEL, 11, 0, 12, 4);
			DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
			
			// report information and table
			writeHeaderSanityCheckReport(sheet, dataObj);
			
			return writeToSheetFile(document, dataObj.getReport_name());
		} catch (Exception e) {
			return null;
		}
	}
	
	private void writeHeaderSanityCheckReport(Sheet sheet, ViewReportEntity dataObj) {
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
			XSSFCellStyle tableHeaderCellStyle = (XSSFCellStyle) DocumentHelper.createStyleForTableHeader(sheet);
			tableHeaderCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			tableHeaderCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{ (byte) 230, (byte) 230, (byte) 230 }, new DefaultIndexedColorMap()));
			XSSFCellStyle tableSubHeaderCellStyle = (XSSFCellStyle) DocumentHelper.createStyleForTableHeader(sheet);
			tableSubHeaderCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			tableSubHeaderCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{ (byte) 245, (byte) 245, (byte) 245 }, new DefaultIndexedColorMap()));
			CellStyle tableRowCellStyle = DocumentHelper.createStyleForTableRow(sheet, false);
			CellStyle tableRowNoDecimalCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, null);
			CellStyle tableRowOneDecimalPlaceWithPercentageCellStyle = DocumentHelper.createStyleForTableRowNumber(sheet, false, DocumentHelper.oneDecimalPlaceWithPercentageDataFormat);

			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell = row.createCell(1);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
			
			cell = row.createCell(2);
			row.setHeight((short) 600);
			cell = row.createCell(3);
			cell = row.createCell(4);
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
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			
			cell = row.createCell(2);
			cell.setCellStyle(reportInfoCellStyle);
			cell.setCellValue(format.format(dateFormat.parse(dataObj.getStart_date())) + " - " + format.format(dateFormat.parse(dataObj.getEnd_date())));
			cell = row.createCell(3);
			cell.setCellStyle(reportInfoCellStyle);
			cell = row.createCell(4);
			cell.setCellStyle(reportInfoCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 4));
			
			row = sheet.createRow(3);
			row.setHeight((short) 600);
			cell = row.createCell(0);
			cell = row.createCell(1);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
			
			cell = row.createCell(2);
			cell = row.createCell(3);
			cell = row.createCell(4);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 4));
			
			for (int i = 0; i <= 3; i++) {
				row = Objects.nonNull(sheet.getRow(i)) ? sheet.getRow(i) : sheet.createRow(i);
				for (int j = 5; j <= 10; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(reportTitleCellStyle);
					if(i == 0 && j == 5) cell.setCellValue("SANITY CHECK REPORT");
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(0, 3, 5, 10));	
			
			row = sheet.createRow(4);
			cell = row.createCell(0);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Site");
			cell = row.createCell(1);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(4, 5, 0, 1));
			
			cell = row.createCell(2);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("% Difference");
			cell = row.createCell(3);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(4);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(5);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(6);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(7);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 7));
			
			cell = row.createCell(8);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Alert");
			sheet.addMergedRegion(new CellRangeAddress(4, 5, 8, 8));
			
			cell = row.createCell(9);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Manual Calculation - Meter");
			cell = row.createCell(10);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(11);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(12);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(13);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(14);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 9, 14));
			
			cell = row.createCell(15);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Manual Calculation - Inverter");
			cell = row.createCell(16);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(17);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(18);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(19);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(20);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 15, 20));
			
			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellStyle(tableHeaderCellStyle);
			cell = row.createCell(1);
			cell.setCellStyle(tableHeaderCellStyle);
			
			cell = row.createCell(2);
			cell.setCellStyle(tableSubHeaderCellStyle);
			cell.setCellValue("REC");
			cell = row.createCell(3);
			cell.setCellStyle(tableSubHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 2, 3));
			
			cell = row.createCell(4);
			cell.setCellStyle(tableSubHeaderCellStyle);
			cell.setCellValue("Production");
			cell = row.createCell(5);
			cell.setCellStyle(tableSubHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 4, 5));
			
			cell = row.createCell(6);
			cell.setCellStyle(tableSubHeaderCellStyle);
			cell.setCellValue("Irradiance");
			cell = row.createCell(7);
			cell.setCellStyle(tableSubHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 7));
			cell = row.createCell(8);
			cell.setCellStyle(tableHeaderCellStyle);
			
			cell = row.createCell(9);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("3 Phase Accumulated Energy BOM");
			cell = row.createCell(10);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 9, 10));
			
			cell = row.createCell(11);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("3 Phase Accumulated Energy EOM");
			cell = row.createCell(12);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 11, 12));
			
			cell = row.createCell(13);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Calculated Difference");
			cell = row.createCell(14);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 13, 14));
			
			cell = row.createCell(15);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("3 Phase Accumulated Energy BOM");
			cell = row.createCell(16);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 15, 16));
			
			cell = row.createCell(17);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("3 Phase Accumulated Energy EOM");
			cell = row.createCell(18);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 17, 18));
			
			cell = row.createCell(19);
			cell.setCellStyle(tableHeaderCellStyle);
			cell.setCellValue("Calculated Difference");
			cell = row.createCell(20);
			cell.setCellStyle(tableHeaderCellStyle);
			sheet.addMergedRegion(new CellRangeAddress(5, 5, 19, 20));
				
			List<SanityCheckReportEntity> dataExports = dataObj.getDataReports();
			if(Objects.nonNull(dataExports) && dataExports.size() > 0) {
				int accumulatedRowCount = 0;
				
				for(int i = 0; i < dataExports.size(); i++) {
				try {
					SanityCheckReportEntity item = dataExports.get(i);
					int firstRow = 6 + accumulatedRowCount + i;
					int countFromFirstRow = Collections.max(Arrays.asList(item.getRecDifference1().size(), item.getRecDifference2().size(), item.getAccumulatedEnergyBOMByMeter().size(), item.getAccumulatedEnergyEOMByMeter().size(), item.getAccumulatedEnergyBOMByInverter().size(), item.getAccumulatedEnergyEOMByInverter().size(), 1)) - 1;
					
					for (int j = firstRow; j <= firstRow + countFromFirstRow; j++) {
						// Site
						Row row6 = Objects.nonNull(sheet.getRow(j)) ? sheet.getRow(j) : sheet.createRow(j);
						cell = row6.createCell(0);
						cell.setCellStyle(tableRowCellStyle);
						if (j == firstRow) cell.setCellValue(item.getSiteName());
						cell = row6.createCell(1);
						cell.setCellStyle(tableRowCellStyle);
						
						// Production Difference 1
						cell = row6.createCell(4);
						cell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
						if (j == firstRow && Objects.nonNull(item.getProductionDifference1())) cell.setCellValue(item.getProductionDifference1());
						
						// Production Difference 2
						cell = row6.createCell(5);
						cell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
						if (j == firstRow && Objects.nonNull(item.getProductionDifference2())) cell.setCellValue(item.getProductionDifference2());
						
						// Irradiance Difference 1
						cell = row6.createCell(6);
						cell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
						if (j == firstRow && Objects.nonNull(item.getIrradianceDifference1())) cell.setCellValue(item.getIrradianceDifference1());
						
						// Irradiance Difference 2
						cell = row6.createCell(7);
						cell.setCellStyle(tableRowOneDecimalPlaceWithPercentageCellStyle);
						if (j == firstRow && Objects.nonNull(item.getIrradianceDifference2())) cell.setCellValue(item.getIrradianceDifference2());
						
						// Alert
						cell = row6.createCell(8);
						cell.setCellStyle(tableRowNoDecimalCellStyle);
						if (j == firstRow && Objects.nonNull(item.getAlert())) cell.setCellValue(item.getAlert());
					}
					sheet.addMergedRegion(new CellRangeAddress(firstRow, firstRow + countFromFirstRow, 0, 1));
					if(countFromFirstRow > 0) sheet.addMergedRegion(new CellRangeAddress(firstRow, firstRow + countFromFirstRow, 4, 4));
					if(countFromFirstRow > 0) sheet.addMergedRegion(new CellRangeAddress(firstRow, firstRow + countFromFirstRow, 5, 5));
					if(countFromFirstRow > 0) sheet.addMergedRegion(new CellRangeAddress(firstRow, firstRow + countFromFirstRow, 6, 6));
					if(countFromFirstRow > 0) sheet.addMergedRegion(new CellRangeAddress(firstRow, firstRow + countFromFirstRow, 7, 7));
					if(countFromFirstRow > 0) sheet.addMergedRegion(new CellRangeAddress(firstRow, firstRow + countFromFirstRow, 8, 8));
					
					int rowsPerMeter = (int) (item.getAccumulatedEnergyBOMByMeter().size() == 0 ? Math.max(item.getAccumulatedEnergyBOMByInverter().size(), 1) : Math.max(Math.floor(item.getAccumulatedEnergyBOMByInverter().size() / item.getAccumulatedEnergyBOMByMeter().size()), 1));
					int rowsPerInverter = (int) (item.getAccumulatedEnergyBOMByInverter().size() == 0 ? Math.max(item.getAccumulatedEnergyBOMByMeter().size(), 1) : Math.max(Math.floor(item.getAccumulatedEnergyBOMByMeter().size() / item.getAccumulatedEnergyBOMByInverter().size()), 1));
					
					// REC Difference 1
					sanityCheckReportListOfValueRender(sheet, item.getRecDifference1(), firstRow, 2, 2, rowsPerMeter, countFromFirstRow + 1, tableRowOneDecimalPlaceWithPercentageCellStyle);
					// REC Difference 2
					sanityCheckReportListOfValueRender(sheet, item.getRecDifference2(), firstRow, 3, 3, rowsPerMeter, countFromFirstRow + 1, tableRowOneDecimalPlaceWithPercentageCellStyle);
					// accumulated energy BOM by meter
					sanityCheckReportListOfValueRender(sheet, item.getAccumulatedEnergyBOMByMeter(), firstRow, 9, 10, rowsPerMeter, countFromFirstRow + 1, tableRowNoDecimalCellStyle);
					// accumulated energy EOM by meter
					sanityCheckReportListOfValueRender(sheet, item.getAccumulatedEnergyEOMByMeter(), firstRow, 11, 12, rowsPerMeter, countFromFirstRow + 1, tableRowNoDecimalCellStyle);
					// accumulated energy difference by meter
					sanityCheckReportListOfValueRender(sheet, item.getAccumulatedEnergyDifferenceByMeter(), firstRow, 13, 14, rowsPerMeter, countFromFirstRow + 1, tableRowNoDecimalCellStyle);
					// accumulated energy BOM by inverter
					sanityCheckReportListOfValueRender(sheet, item.getAccumulatedEnergyBOMByInverter(), firstRow, 15, 16, rowsPerInverter, countFromFirstRow + 1, tableRowNoDecimalCellStyle);
					// accumulated energy EOM by inverter
					sanityCheckReportListOfValueRender(sheet, item.getAccumulatedEnergyEOMByInverter(), firstRow, 17, 18, rowsPerInverter, countFromFirstRow + 1, tableRowNoDecimalCellStyle);
					// accumulated energy difference by inverter
					sanityCheckReportListOfValueRender(sheet, item.getAccumulatedEnergyDifferenceByInverter(), firstRow, 19, 20, rowsPerInverter, countFromFirstRow + 1, tableRowNoDecimalCellStyle);
					
					accumulatedRowCount += countFromFirstRow;
				} catch (Exception e) {}
				}
			}
		} catch (Exception e) {
		}
	}
	
	private void sanityCheckReportListOfValueRender(Sheet sheet, List<Double> data, int firstRow, int firstColumn, int lastColumn, int rowsPerValue, int totalRows, CellStyle cellStyle) {
		// format all cells
		for (int rowIndex = 0; rowIndex < totalRows; rowIndex++) {
			Row row = Objects.nonNull(sheet.getRow(firstRow + rowIndex)) ? sheet.getRow(firstRow + rowIndex) : sheet.createRow(firstRow + rowIndex);
			
			for (int colIndex = firstColumn; colIndex <= lastColumn; colIndex++) {
				Cell cell = row.createCell(colIndex);
				cell.setCellStyle(cellStyle);
			}
		}
		
		if (data.size() == 0) {
			if ((lastColumn - firstColumn > 0) || (totalRows > 1)) sheet.addMergedRegion(new CellRangeAddress(firstRow, firstRow + totalRows - 1, firstColumn, lastColumn));
			return;
		}
		
		for (int dataIndex = 0; dataIndex < data.size(); dataIndex++) {
			Row row = sheet.getRow(firstRow + dataIndex * rowsPerValue);
			Cell cell = row.getCell(firstColumn);
			if(Objects.nonNull(data.get(dataIndex))) cell.setCellValue(data.get(dataIndex));
			
			if ((lastColumn - firstColumn > 0) || (rowsPerValue > 1)) sheet.addMergedRegion(new CellRangeAddress(firstRow + dataIndex * rowsPerValue, firstRow + dataIndex * rowsPerValue + rowsPerValue - 1, firstColumn, lastColumn));
		}
		
		// merge the rest of rows which are empty by row
		if ((data.size() * rowsPerValue < totalRows) && (lastColumn - firstColumn > 0)) {
			for (int i = 0; i < totalRows - data.size() * rowsPerValue; i++) {
				sheet.addMergedRegion(new CellRangeAddress(firstRow + data.size() * rowsPerValue + i, firstRow + data.size() * rowsPerValue + i, firstColumn, lastColumn));
			}
		}
	}
	
	/**
	   * @Get Meter level production irradiance temp report
	   * @author Duy.Phan
	   * @since 2025-10-06
	   * @return {}
	   */
	  
	  public ViewReportEntity getMeterLevelProductionIrradianceTempReport(ViewReportEntity obj) {
	    try {
	      List dataListDeviceMeter = queryForList("Reports.getListDeviceTypeMeterWeatherStation", obj);
	      
	      List<String> headerPower = new ArrayList<>();
		  List<String> headerEnergy = new ArrayList<>();
		  List<String> headerIrradiance = new ArrayList<>();
		  List<String> headerTemp = new ArrayList<>();
	      
	      if(dataListDeviceMeter.size() > 0) {
				List<CompletableFuture<List<Map<String, Object>>>> list = new ArrayList<CompletableFuture<List<Map<String, Object>>>>();
						
				for(int i = 0; i < dataListDeviceMeter.size(); i++) {
					int k = i;
					
					// Header for table
					Map<String, Object> itemHeader = (Map<String, Object>) dataListDeviceMeter.get(i);				
					if ((int) itemHeader.get("id_device_type") == 3) {
						headerPower.add((String) itemHeader.get("power_irradiance"));
						headerEnergy.add((String) itemHeader.get("energy_temp"));
					} else {
						headerIrradiance.add((String) itemHeader.get("power_irradiance"));
						headerTemp.add((String) itemHeader.get("energy_temp"));
					}
					
					CompletableFuture<List<Map<String, Object>>> future = CompletableFuture.supplyAsync(() -> {
						Map<String, Object> maps = new HashMap<>();
						List<Map<String, Object>> dataEnergy = new ArrayList<>();
						try {
							Map<String, Object> map = (Map<String, Object>) dataListDeviceMeter.get(k);
							
							map.put("data_intervals", obj.getData_intervals());
							map.put("start_date", obj.getStart_date());
							map.put("end_date", obj.getEnd_date());							
							
							dataEnergy = (int) map.get("id_device_type") == 3 ? queryForList("Reports.getDataEnergyEachMeter", map) : queryForList("Reports.getDataEnergyEachWeatherStation", map);
							
							
							
							if (dataEnergy.size() == 0) {
								Map<String, Object> item = new HashMap<>();
								item.put((String) map.get("power_irradiance"), null);
								item.put((String) map.get("energy_temp"), null);
								dataEnergy.add(item);
							}
							
						} catch (Exception ex) {
							
							log.error("Reports.getDataEnergyEachMeter", ex);
						}
						
						
						return dataEnergy;
					});
					
					list.add(future);
				}
				List<List<Map<String, Object>>> dataList = list.stream().map(future -> future.join()).collect(Collectors.toList());
				
				 if (dataList.size() > 0) {
					 List<Map<String, Object>> dateTimeList = getDateTimeListMapObject(obj);
					 
					 for (List<Map<String, Object>> data : dataList) {	
						 if (!data.isEmpty()) {
							 int count = 0;
							 for (int i = 0; i < dateTimeList.size(); i++) {
								Map<String, Object> dateTimeItem = dateTimeList.get(i);
								
								if (i - count < data.size()) {
									Map<String, Object> dataItem = data.get(i - count);
									
									if(dataItem.get("Timestamp") == null) {
										for (Map.Entry<String, Object> entry : dataItem.entrySet()) {
											String key = entry.getKey();
											if (!key.contains("Timestamp")) {
												dateTimeList.get(i).put(entry.getKey(), null);
											}
										} 
										count++;
									} else {
										if (dataItem.get("Timestamp") != null && dateTimeItem.get("Timestamp").toString().equals(dataItem.get("Timestamp").toString())) {
											for (Map.Entry<String, Object> entry : dataItem.entrySet()) {
												dateTimeList.get(i).put(entry.getKey(), entry.getValue());
											} 
										} else {
											for (Map.Entry<String, Object> entry : dataItem.entrySet()) {
												String key = entry.getKey();
												if (!key.contains("Timestamp")) {
													dateTimeList.get(i).put(entry.getKey(), entry.getValue());
												}
											} 
											count++;
										}
									}		
									
								} else {
									Map<String, Object> dataItem = data.get(0);
									for (Map.Entry<String, Object> entry : dataItem.entrySet()) {
										String key = entry.getKey();
										if (!key.contains("Timestamp")) {
											dateTimeList.get(i).put(entry.getKey(), null);
										}
									} 
								}
							 }
						 }
					 }
					 
					 obj.setDataReports(dateTimeList);
					 
					 List<String> sortedHeaders = new ArrayList<>();
			         sortedHeaders.add("Timestamp");
			         sortedHeaders.addAll(headerPower);
			         sortedHeaders.addAll(headerEnergy);
			         sortedHeaders.addAll(headerIrradiance);
			         sortedHeaders.addAll(headerTemp);
					 
			         obj.setSortedHeaders(sortedHeaders);		         
				 }			
			}

	      return obj;
	    } catch (Exception ex) {
	      return null;
	    }
	}
	  
	  private List<Map<String, Object>> getDateTimeListMapObject(ViewReportEntity obj) {
		  	List<Map<String, Object>> dateTimeList = new ArrayList<>();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), formatter).withHour(0).withMinute(0).withSecond(0);
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), formatter).withHour(23).withMinute(59).withSecond(59);
			
			try {
				int interval = 1;
				DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
				ChronoUnit timeUnit = ChronoUnit.MINUTES;
			
				switch (ReportRange.fromValue(obj.getCadence_range())) {
					case DAILY:
						categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm");
						switch (ReportIntervals.fromValue(obj.getData_intervals())) {
							case _5_MINUTE:
								interval = 5;
								timeUnit = ChronoUnit.MINUTES;
								break;
							case _15_MINUTES:
								interval = 15;
								timeUnit = ChronoUnit.MINUTES;
								break;
							case _1_HOUR:
								interval = 1;
								timeUnit = ChronoUnit.HOURS;
								break;
							default:
								break;
						}
						break;
					case WEEKLY:
					case LAST_WEEK:
						categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm");
						switch (ReportIntervals.fromValue(obj.getData_intervals())) {
							case _5_MINUTE:
								interval = 5;
								timeUnit = ChronoUnit.MINUTES;
								break;
							case _15_MINUTES:
								interval = 15;
								timeUnit = ChronoUnit.MINUTES;
								break;
							case _1_HOUR:
								interval = 1;
								timeUnit = ChronoUnit.HOURS;
								break;
							case DAILY:
								categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
		                		timeUnit = ChronoUnit.DAYS;
								break;
							default:
								break;
						}
						break;
					case LAST_MONTH:
					case MONTHLY:
						categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
						timeUnit = ChronoUnit.DAYS;
						break;
					case LAST_QUARTER:
						switch (ReportIntervals.fromValue(obj.getData_intervals())) {
							case DAILY:
								categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
								timeUnit = ChronoUnit.DAYS;
								break;
							case MONTHLY:
								categoryTimeFormat = DateTimeFormatter.ofPattern("MMM-yyyy");
								timeUnit = ChronoUnit.MONTHS;
								break;
							default:
								break;
						}
						break;
					case ANNUALLY:
						categoryTimeFormat = DateTimeFormatter.ofPattern("MMM");
						timeUnit = ChronoUnit.MONTHS;
						break;
					case CUSTOM:
		                switch (ReportIntervals.fromValue(obj.getData_intervals())) {
			                case _15_MINUTES:
			                	categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm");
								interval = 15;
								timeUnit = ChronoUnit.MINUTES;
								break;
			                case _30_MINUTES:
			                	categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm");
								interval = 30;
								timeUnit = ChronoUnit.MINUTES;
								break;
			                case _1_HOUR:
			                	categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm");
								interval = 1;
								timeUnit = ChronoUnit.HOURS;
								break;
		                	case DAILY:
		                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
		                		timeUnit = ChronoUnit.DAYS;
		                		break;
		                	case MONTHLY:
		                		end = end.with(TemporalAdjusters.lastDayOfMonth());
		                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/yyy");
		                		timeUnit = ChronoUnit.MONTHS;
		                		break;
		                	case ANNUAL:
		                		categoryTimeFormat = DateTimeFormatter.ofPattern("yyyy");
		                		timeUnit = ChronoUnit.YEARS;
		                		break;
		                	default:
		    					break;
		                }
						break;
					default:
						break;
				}
				
				while (!start.isAfter(end)) {
					Map<String, Object> dateTime = new HashMap<String, Object>();
					dateTime.put("Timestamp", start.format(categoryTimeFormat));
					dateTimeList.add(dateTime);
					start = start.plus(interval, timeUnit);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return dateTimeList;
		}
	  
	  /**
		 * send mail Meter level production irradiance temp file
		 * @author Duy.Phan
		 * @since 2025-10-08
		 * @param obj
		 */
		public boolean sentMailMeterLevelProductionIrradianceTempReport(ViewReportEntity obj) {
			try {
				ViewReportEntity dataObj = getMeterLevelProductionIrradianceTempReport(obj);
				if (dataObj == null) return false;
				dataObj.setStart_date(obj.getStart_date());
				dataObj.setEnd_date(obj.getEnd_date());
				
				obj.setId_site((int) obj.getIds().get(0));
				ViewReportEntity reportDetail = getReportDetail(obj);
				if (reportDetail != null) dataObj.setSubscribers(reportDetail.getSubscribers());
				
				String filePath = createMeterLevelProductionIrradianceTempReportSheetFile(obj, dataObj);
				if (filePath == null) return false;
				
				sentReportByMail(filePath, dataObj.getSubscribers(), "Meter-Level Production vs Irradiance/Temp", 26);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		
		/**
		 * @description Meter level production irradiance temp file
		 * @author Duy.Phan
		 * @since 2025-10-08
		 * @param obj
		 * @return file path
		 */
		public String createMeterLevelProductionIrradianceTempReportSheetFile(ViewReportEntity obj, ViewReportEntity dataObj) {
			try (XSSFWorkbook document = new XSSFWorkbook()) {
				int pictureIdx = DocumentHelper.readLogoImageFile(document);
				
				
				
				if (dataObj != null) {
					List<Map<String, Object>> dataExports = dataObj.getDataReports();
					int numOfPoints = dataExports != null ? dataExports.size() : 0;
					
					XSSFSheet sheet = document.createSheet(WorkbookUtil.createSafeSheetName((1) + "_" + dataObj.getReport_name()));
					sheet.setZoom(85);
					
					// insert logo image
					ClientAnchor logoAnchor = new XSSFClientAnchor(0, 0, 20 * Units.EMU_PER_PIXEL, 20 * Units.EMU_PER_PIXEL, 10, 1, 11, 4);
					DocumentHelper.insertLogo(sheet, logoAnchor, pictureIdx);
					
					// report information and table
					writeHeaderMeterLevelProductionIrradianceTempReport(sheet, dataObj);
					
				}
				return writeToSheetFile(document, obj.getReport_name());
			} catch (Exception e) {
				return null;
			}
		}
		
		private static void writeHeaderMeterLevelProductionIrradianceTempReport(Sheet sheet, ViewReportEntity dataObj) {
			try {
				sheet.setDefaultColumnWidth(16);
				sheet.setColumnWidth(0, 25 * 256);
				sheet.setColumnWidth(1, 20 * 256);
				sheet.setColumnWidth(2, 20 * 256);
				sheet.setColumnWidth(3, 20 * 256);
				sheet.setColumnWidth(4, 20 * 256);
				sheet.setColumnWidth(5, 20 * 256);
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
				
				Row row = sheet.createRow(0);
				Cell cell = row.createCell(0);
				
				cell = row.createCell(2);
				row.setHeight((short) 600);
				cell = row.createCell(3);
				cell = row.createCell(4);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 3));
				
				row = sheet.createRow(1);
				cell = row.createCell(0);
				row.setHeight((short) 600);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("Report");
				
				cell = row.createCell(1);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(dataObj.getReport_name());
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				cell = row.createCell(3);
				cell.setCellStyle(reportInfoCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 3));
				
				row = sheet.createRow(2);
				row.setHeight((short) 600);
				cell = row.createCell(0);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("Start Date:");

				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				
				cell = row.createCell(1);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(format.format(dateFormat.parse(dataObj.getStart_date())));
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				cell = row.createCell(3);
				cell.setCellStyle(reportInfoCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
				
				
				row = sheet.createRow(3);
				row.setHeight((short) 600);
				cell = row.createCell(0);
				cell.setCellStyle(reportInfoBoldCellStyle);
				cell.setCellValue("End Date:");

				
				cell = row.createCell(1);
				cell.setCellStyle(reportInfoCellStyle);
				cell.setCellValue(format.format(dateFormat.parse(dataObj.getEnd_date())));
				cell = row.createCell(2);
				cell.setCellStyle(reportInfoCellStyle);
				cell = row.createCell(3);
				cell.setCellStyle(reportInfoCellStyle);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 3));
				
				
				
				for (int i = 0; i <= 3; i++) {
					row = Objects.nonNull(sheet.getRow(i)) ? sheet.getRow(i) : sheet.createRow(i);
					for (int j = 4; j <= 9; j++) {
						cell = row.createCell(j);
						cell.setCellStyle(reportTitleCellStyle);
						if(i == 0 && j == 4) cell.setCellValue("Meter-Level Production vs Irradiance/Temp");
					}
				}
				sheet.addMergedRegion(new CellRangeAddress(0, 3, 4, 9));
				 
				
				List<Map<String, Object>> dataExports = dataObj.getDataReports();
				List<String> sortedHeaderList = dataObj.getSortedHeaders();
				
				int numberCol = 10;
				int number = (int) ((int) Math.ceil(numberCol/sortedHeaderList.size()) > 0 ? Math.ceil(numberCol/(double) sortedHeaderList.size()) : 1);
				
				if(Objects.nonNull(sortedHeaderList) && sortedHeaderList.size() > 0) {
					row = sheet.createRow(7);
					for(int i = 0; i < sortedHeaderList.size(); i++) {					
						int startCol = i * number;					
						for(int j = 0; j < number; j++) {
							cell = row.createCell(startCol + j);
							cell.setCellStyle(tableHeaderCellStyle);
							cell.setCellValue(sortedHeaderList.get(i));
						}					
						if (number > 1) sheet.addMergedRegion(new CellRangeAddress(7, 7, startCol, startCol + number - 1));
		                
					}
				}
				
				
				if(dataExports != null && dataExports.size() > 0) {
					int r = 8;
					for( int i = 0; i < dataExports.size(); i++){
						Map<String, Object> item = dataExports.get(i);
						
						Row tableRow = sheet.createRow(r+i);
						for (int j = 0; j < sortedHeaderList.size(); j++) {
							int startCol = j * number;
							
							for(int n = 0; n < number; n++) {
								Cell tableCell = tableRow.createCell(startCol + n);
								tableCell.setCellStyle(tableHeaderCellStyle);
								if(item.get(sortedHeaderList.get(j)) != null) {
									if (item.get(sortedHeaderList.get(j)) instanceof Double) {
										tableCell.setCellValue((Double) item.get(sortedHeaderList.get(j)));
									} else if (item.get(sortedHeaderList.get(j)) instanceof String) {
										tableCell.setCellValue((String) item.get(sortedHeaderList.get(j)));							 
									} 
								}
							}
							if (number > 1) sheet.addMergedRegion(new CellRangeAddress(r+i, r+i, startCol, startCol + number - 1));					
						}
					}
				}
			} catch (Exception e) {
			}
		}
		
		
		/**
		 * @description download Meter level production irradiance temp report sheet file
		 * @author Duy.Phan
		 * @since 2025-10-08
		 * @param obj
		 */
		public String downloadMeterLevelProductionIrradianceTempReport(ViewReportEntity obj) {
			try {
				ViewReportEntity dataObj = getMeterLevelProductionIrradianceTempReport(obj);
				if (dataObj == null) return null;
				dataObj.setStart_date(obj.getStart_date());
				dataObj.setEnd_date(obj.getEnd_date());
				return createMeterLevelProductionIrradianceTempReportSheetFile(obj, dataObj);
			} catch (Exception e) {
				return null;
			}
		}
	
}
