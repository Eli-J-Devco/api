/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.awt.Color;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AuditLog;
import com.nwm.api.entities.BuildingReportDateEntity;
import com.nwm.api.entities.BuildingReportEntity;
import com.nwm.api.entities.BuildingReportWeatherEntity;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DailyDateEntity;
import com.nwm.api.entities.DateTimeReportDataEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EmployeeSiteMapEntity;
import com.nwm.api.entities.SiteAreaBuildingFloorRoomEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SiteGasWaterElectricityRateScheduleEntity;
import com.nwm.api.entities.SiteLogs;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.ZoneGraphDateEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.DocumentHelper;
import com.nwm.api.utils.Lib;

public class BuildingReportService extends DB {
	private static final Color BLUE_COLOR = new Color(49, 119, 168);
	private static final Color LIGHT_BLUE_COLOR = new Color(109, 189, 246);
	private static final Color ORANGE_COLOR = new Color(255, 129, 39);
	private String getReportFolderPath() {
		String uploadRootPath = Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadRootPathConfigKey);
		return uploadRootPath + "/" + Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathReportFiles);
	}
	
	/**
	 * @description get data building report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 */
	public BuildingReportEntity getDataBuildingReport(BuildingReportEntity obj) {
		try {
			// Get device by id_site
			List devices = queryForList("BuildingReport.getListDeviceBySite", obj);
			if(devices.size() > 0) {
				List<Object> electrics = new ArrayList<>();
				List<Object> gas = new ArrayList<>();
				List<Object> pvProduction = new ArrayList<>();
				List<Object> waters = new ArrayList<>();
				List<Object> weather = new ArrayList<>();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
					int meterType = Integer.parseInt(item.get("meter_type").toString());
					int idDeviceType = Integer.parseInt(item.get("id_device_type").toString());
					if(idDeviceType == 4) {
						weather.add(item);
					}
					
					switch (meterType) {
				        case 3:
				        	pvProduction.add(item);
				            break;
				        case 4:
				        	electrics.add(item);
				            break;
				        case 5:
				        	waters.add(item);
				            break;
				        case 7:
				        	gas.add(item);
				            break;
				    }
				}
				
				if(pvProduction.size() > 0) {
					obj.setDevices(pvProduction);
					BuildingReportEntity dataPV = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataPV != null) {
						obj.setPv_current_month(dataPV.getCurrent_month());
						obj.setPv_compare_current_month(dataPV.getCompare_current_month());
						obj.setPv_year_over_year(dataPV.getYear_over_year());
						obj.setPv_year_over_date(dataPV.getYear_over_date());
					}
					
					BuildingReportEntity pvPeakFlowRate = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakFlowRate", obj);
					if(pvPeakFlowRate != null) {
						obj.setPv_peak_flow_rate(pvPeakFlowRate.getPeak_flow_rate());
						obj.setPv_peak_flow_rate_date(pvPeakFlowRate.getPeak_flow_rate_date());
					}
					
				}
				
				if(gas.size() > 0) {
					obj.setDevices(gas);
					BuildingReportEntity dataGas = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataGas != null) {
						obj.setGas_current_month(dataGas.getCurrent_month());
						obj.setGas_compare_current_month(dataGas.getCompare_current_month());
						obj.setGas_year_over_year(dataGas.getYear_over_year());
						obj.setGas_year_over_date(dataGas.getYear_over_date());
					}
					
					BuildingReportEntity gasPeakFlowRate = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakFlowRate", obj);
					if(gasPeakFlowRate != null) {
						obj.setGas_peak_flow_rate(gasPeakFlowRate.getPeak_flow_rate());
						obj.setGas_peak_flow_rate_date(gasPeakFlowRate.getPeak_flow_rate_date());
					}
					
					
				}
				
				if(waters.size() > 0) {
					obj.setDevices(waters);
					BuildingReportEntity dataWater = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataWater != null) {
						obj.setWater_current_month(dataWater.getCurrent_month());
						obj.setWater_compare_current_month(dataWater.getCompare_current_month());
						obj.setWater_year_over_year(dataWater.getYear_over_year());
						obj.setWater_year_over_date(dataWater.getYear_over_date());
					}
					
					BuildingReportEntity waterPeakFlowRate = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakFlowRate", obj);
					if(waterPeakFlowRate != null) {
						obj.setWater_peak_flow_rate(waterPeakFlowRate.getPeak_flow_rate());
						obj.setWater_peak_flow_rate_date(waterPeakFlowRate.getPeak_flow_rate_date());
					}
					
				}
				if(electrics.size() > 0) {
					obj.setDevices(electrics);
					BuildingReportEntity dataElectric = (BuildingReportEntity) queryForObject("BuildingReport.getDataDeviceGroup", obj);
					if(dataElectric != null) {
						obj.setElectric_current_month(dataElectric.getCurrent_month());
						obj.setElectric_compare_current_month(dataElectric.getCompare_current_month());
						obj.setElectric_year_over_year(dataElectric.getYear_over_year());
						obj.setElectric_year_over_date(dataElectric.getYear_over_date());
					}
					
					BuildingReportEntity electricPeakFlowRate = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakFlowRate", obj);
					if(electricPeakFlowRate != null) {
						obj.setElectric_peak_flow_rate(electricPeakFlowRate.getPeak_flow_rate());
						obj.setElectric_peak_flow_rate_date(electricPeakFlowRate.getPeak_flow_rate_date());
					}
					
				}

			}
			
			return obj;
		} catch (Exception ex) {
			return new BuildingReportEntity();
		}
	}
	

	
	/**
	 * @description get data building report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 */
	public BuildingReportEntity getDataCategoryStatisticsReport(BuildingReportEntity obj) {
		try {
			// Get device by id_site
			List devices = queryForList("BuildingReport.getListDeviceBySite", obj);
			if(devices.size() > 0) {
				List<Object> electrics = new ArrayList<>();
				List<Object> gas = new ArrayList<>();
				List<Object> pvProduction = new ArrayList<>();
				List<Object> waters = new ArrayList<>();
				List<Object> weather = new ArrayList<>();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
					int meterType = Integer.parseInt(item.get("meter_type").toString());
					int idDeviceType = Integer.parseInt(item.get("id_device_type").toString());
					if(idDeviceType == 4) {
						weather.add(item);
					}
					
					switch (meterType) {
				        case 3:
				        	pvProduction.add(item);
				            break;
				        case 4:
				        	electrics.add(item);
				            break;
				        case 5:
				        	waters.add(item);
				            break;
				        case 7:
				        	gas.add(item);
				            break;
				    }
				}
				
				if(pvProduction.size() > 0) {
					obj.setDevices(pvProduction);
					List dataPVStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataPVStatistics(dataPVStatistics);
				}
				
				if(gas.size() > 0) {
					obj.setDevices(gas);
					List dataGasStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataGasStatistics(dataGasStatistics);
					
				}
				
				if(waters.size() > 0) {
					obj.setDevices(waters);
					List dataWaterStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataWaterStatistics(dataWaterStatistics);
					
				}
				if(electrics.size() > 0) {
					obj.setDevices(electrics);
					List dataElectricStatistics = queryForList("BuildingReport.getDataReportCategoryStatistics", obj);
					obj.setDataElectricStatistics(dataElectricStatistics);
				}

			}
			
			return obj;
		} catch (Exception ex) {
			return new BuildingReportEntity();
		}
	}
	
	/**
	 * @description get data building report
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 */
	public BuildingReportEntity getDataWeatherStationReport(BuildingReportEntity obj) {
		try {
			// Get device by id_site
			List devices = queryForList("BuildingReport.getListDeviceBySite", obj);
			if(devices.size() > 0) {
				List<Object> weather = new ArrayList<>();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
					int meterType = Integer.parseInt(item.get("meter_type").toString());
					int idDeviceType = Integer.parseInt(item.get("id_device_type").toString());
					if(idDeviceType == 21) {
						weather.add(item);
					}
				}
				
				int interval = 1;
				DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				
				ChronoUnit timeUnit = ChronoUnit.DAYS;
				LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				LocalDateTime startLastMonth = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				startLastMonth = startLastMonth.plus(-1, ChronoUnit.MONTHS);
				LocalDateTime endLastMonth = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				endLastMonth = endLastMonth.plus(-1, ChronoUnit.MONTHS);
				
				List<BuildingReportDateEntity> dateTimeList = new ArrayList<>();
				while (!start.isAfter(end)) {
					BuildingReportDateEntity dateTime = new BuildingReportDateEntity();
					dateTime.setTime_full(start.format(timeFullFormat));
					dateTime.setCategories_time(start.format(categoriesTimeFormat));
					dateTime.setTime_format(start.format(timeFormat));
					dateTimeList.add(dateTime);
					start = start.plus(interval, timeUnit);
				}
				
				List<BuildingReportDateEntity> dateTimeListLastMonth = new ArrayList<>();
				while (!startLastMonth.isAfter(endLastMonth)) {
					BuildingReportDateEntity dateTimeLastMonth = new BuildingReportDateEntity();
					dateTimeLastMonth.setTime_full(startLastMonth.format(timeFullFormat));
					dateTimeLastMonth.setCategories_time(startLastMonth.format(categoriesTimeFormat));
					dateTimeLastMonth.setTime_format(startLastMonth.format(timeFormat));
					dateTimeListLastMonth.add(dateTimeLastMonth);
					startLastMonth = startLastMonth.plus(interval, timeUnit);
				}
				
				
				
				if(weather.size() > 0) {
					obj.setDevices(weather);
					List dataWeatherCurrentMonth = queryForList("BuildingReport.getDataWeatherStation", obj);
					List<BuildingReportDateEntity> fillData = Lib.fulfillData(dateTimeList, dataWeatherCurrentMonth, "time_full");
					
					obj.setDataWeatherCurrentMonth(fillData);
					
					List dataWeatherLastMonth = queryForList("BuildingReport.getDataWeatherStationLastMonth", obj);
					List<BuildingReportDateEntity> fillDataLastMonth = Lib.fulfillData(dateTimeListLastMonth, dataWeatherLastMonth, "time_full");
					obj.setDataWeatherComapreMonth(fillDataLastMonth);
				}
			}
			
			return obj;
		} catch (Exception ex) {
			return new BuildingReportEntity();
		}
	}
	
	
	/**
	 * @description get data building report by type
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 */
	
	
	public BuildingReportEntity getDataBuildingReportByType(BuildingReportEntity obj) {
		try {
			// Get device by id_site
			List devices = queryForList("BuildingReport.getListDeviceBySite", obj);
			if(devices.size() > 0) {
				List<Object> electrics = new ArrayList<>();
				List<Object> gas = new ArrayList<>();
				List<Object> pvProduction = new ArrayList<>();
				List<Object> waters = new ArrayList<>();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
					int meterType = Integer.parseInt(item.get("meter_type").toString());
					switch (meterType) {
				        case 3:
				        	pvProduction.add(item);
				            break;
				        case 4:
				        	electrics.add(item);
				            break;
				        case 5:
				        	waters.add(item);
				            break;
				        case 7:
				        	gas.add(item);
				            break;
				    }
				}
				
				switch (obj.getMeter_type()) {
			        case 3:
			        	obj.setDevices(pvProduction);
			            break;
			        case 4:
			        	obj.setDevices(electrics);
			            break;
			        case 5:
			        	obj.setDevices(waters);
			            break;
			        case 7:
			        	obj.setDevices(gas);
			            break;
			    }
				
				
				int interval = 1;
				DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
				DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
				
				ChronoUnit timeUnit = ChronoUnit.DAYS;
				LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				List<BuildingReportDateEntity> dateTimeList = new ArrayList<>();
				while (!start.isAfter(end)) {
					BuildingReportDateEntity dateTime = new BuildingReportDateEntity();
					dateTime.setTime_full(start.format(timeFullFormat));
					dateTime.setCategories_time(start.format(categoriesTimeFormat));
					dateTime.setTime_format(start.format(timeFormat));
					dateTimeList.add(dateTime);
					start = start.plus(interval, timeUnit);
				}
				
				
				// Build data time expected
				LocalDateTime startExpected = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				startExpected = startExpected.plus(-1, ChronoUnit.YEARS);
				LocalDateTime endExpected = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				endExpected = endExpected.plus(-1, ChronoUnit.YEARS);
				List<BuildingReportDateEntity> dateTimeListExpected = new ArrayList<>();
				while (!startExpected.isAfter(endExpected)) {
					BuildingReportDateEntity dateTimeExpected = new BuildingReportDateEntity();
					dateTimeExpected.setTime_full(startExpected.format(timeFullFormat));
					dateTimeExpected.setCategories_time(startExpected.format(categoriesTimeFormat));
					dateTimeExpected.setTime_format(startExpected.format(timeFormat));
					dateTimeListExpected.add(dateTimeExpected);
					startExpected = startExpected.plus(interval, timeUnit);
				}
				
				if(obj.getDevices().size() > 0) {
					List<BuildingReportDateEntity> dataDaily = new ArrayList<>();
					dataDaily = queryForList("BuildingReport.getDataReportDailyByType", obj);
					List<BuildingReportDateEntity> fillDataDaily = Lib.fulfillData(dateTimeList, dataDaily, "time_full");
					obj.setDataDaily(fillDataDaily);
					
					List<BuildingReportDateEntity> dataDailyExpected = queryForList("BuildingReport.getDataReportDailyExpectedByType", obj);
					List<BuildingReportDateEntity> fillDataExpected = Lib.fulfillData(dateTimeListExpected, dataDailyExpected, "time_full");
					obj.setDataDailyExpected(fillDataExpected);
				}
				
				
				// get data History
				int intervalHistory = 1;
				DateTimeFormatter timeFullFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter categoriesTimeFormatHistory = DateTimeFormatter.ofPattern("MMM yyyy");
				DateTimeFormatter timeFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter timeDateFormatHistory = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				
				ChronoUnit timeUnitHistory = ChronoUnit.MONTHS;
				LocalDateTime startHistory = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				startHistory = startHistory.plus(-11, ChronoUnit.MONTHS);
				LocalDateTime endHistory = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				List<BuildingReportDateEntity> dateTimeListHistory = new ArrayList<>();
				while (!startHistory.isAfter(endHistory)) {
					BuildingReportDateEntity dateTimeHistory = new BuildingReportDateEntity();
					dateTimeHistory.setTime_full(startHistory.format(timeFullFormatHistory));
					dateTimeHistory.setCategories_time(startHistory.format(categoriesTimeFormatHistory));
					dateTimeHistory.setTime_format(startHistory.format(timeFormatHistory));
					dateTimeHistory.setStart_date(startHistory.format(timeDateFormatHistory));
					
					startHistory = startHistory.plus(intervalHistory, timeUnitHistory);
					dateTimeHistory.setEnd_date(startHistory.format(timeDateFormatHistory));
					dateTimeListHistory.add(dateTimeHistory);
				}
				
				// Get data history expected 
				// get data History
				int intervalEx = 1;
				DateTimeFormatter timeFullFormatEx = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter categoriesTimeFormatEx = DateTimeFormatter.ofPattern("MMM yyyy");
				DateTimeFormatter timeFormatEx = DateTimeFormatter.ofPattern("yyyy-MM");
				DateTimeFormatter timeDateFormatEx = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				
				ChronoUnit timeUnitEx = ChronoUnit.MONTHS;
				LocalDateTime startEx = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				startEx = startEx.plus(-2, ChronoUnit.YEARS);
				startEx = startEx.plus(1, ChronoUnit.MONTHS);
				LocalDateTime endEx = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				endEx = endEx.plus(-1, ChronoUnit.YEARS);
				
				List<BuildingReportDateEntity> dateTimeListEx = new ArrayList<>();
				while (!startEx.isAfter(endEx)) {
					BuildingReportDateEntity dateTimeEx = new BuildingReportDateEntity();
					dateTimeEx.setTime_full(startEx.format(timeFullFormatEx));
					dateTimeEx.setCategories_time(startEx.format(categoriesTimeFormatEx));
					dateTimeEx.setTime_format(startEx.format(timeFormatEx));
					dateTimeEx.setStart_date(startEx.format(timeDateFormatEx));
					
					startEx = startEx.plus(intervalEx, timeUnitEx);
					dateTimeEx.setEnd_date(startEx.format(timeDateFormatEx));
					dateTimeListEx.add(dateTimeEx);
				}
				
				
				
				if(obj.getDevices().size() > 0) {
					obj.setDateTimeList(dateTimeListHistory);
					List<BuildingReportDateEntity>	dataHistory = queryForList("BuildingReport.getDataReportHistory", obj);
					List<BuildingReportDateEntity> fillDataHistory = Lib.fulfillData(dateTimeListHistory, dataHistory, "time_full");
					obj.setDataHistory(fillDataHistory);
					
					// Get data history expected
					obj.setDateTimeList(dateTimeListEx);
					List<BuildingReportDateEntity>	dataEx = queryForList("BuildingReport.getDataReportHistory", obj);
					List<BuildingReportDateEntity> fillDataEx = Lib.fulfillData(dateTimeListEx, dataEx, "time_full");
					obj.setDataHistoryExpected(fillDataEx);
					
				}
				
				BuildingReportEntity dataPeakDemand = (BuildingReportEntity) queryForObject("BuildingReport.getDataPeakDemand", obj);
				if(dataPeakDemand != null) {
					obj.setPeak_demand(dataPeakDemand.getPeak_demand());
					obj.setPeak_demand_date(dataPeakDemand.getPeak_demand_date());
				}
				
				BuildingReportEntity dataLastMonth = (BuildingReportEntity) queryForObject("BuildingReport.getDataLastMonth", obj);
				if(dataLastMonth != null) {
					obj.setLastMonth(dataLastMonth.getLastMonth());
				}
				
				BuildingReportEntity dataMaxAnnualDaily = (BuildingReportEntity) queryForObject("BuildingReport.getDataMaxAnnualDaily", obj);
				if(dataMaxAnnualDaily != null) {
					obj.setMax_annual_daily(dataMaxAnnualDaily.getMax_annual_daily());
					obj.setMax_annual_daily_date(dataMaxAnnualDaily.getMax_annual_daily_date());
					obj.setLast_year(dataMaxAnnualDaily.getLast_year());
					obj.setAvg_last_eriod(dataMaxAnnualDaily.getAvg_last_eriod());
				}
				
				if("electric".equals(obj.getType_group())){
					BuildingReportEntity dataDaytimeAndNightTime = (BuildingReportEntity) queryForObject("BuildingReport.getDataDaytimeAndNightTime", obj);
					if(dataDaytimeAndNightTime != null) {
						obj.setDaytime(dataDaytimeAndNightTime.getDaytime());
						obj.setNighttime(dataDaytimeAndNightTime.getNighttime());
					}
				}
				
				
				
				
				
				
				
			}
			
			return obj;
		} catch (Exception ex) {
			return new BuildingReportEntity();
		}
	}
	
	
	/**
	 * @description download PDF file report
	 * @author Long.Pham
	 * @since 2025-08-08
	 * @param obj
	 */
	public BuildingReportEntity downloadReportPDFFile(BuildingReportEntity obj) {
		try {
			
//			String filePath = createReportPdfFile(obj);
//			obj.setDownload_file_path(filePath);
//			List<ViewReportEntity> dataObjList = getReportDataList(obj);
//			if (dataObjList == null || dataObjList.size() == 0) return false;
//			String filePath = obj.getFile_type() == 1 ? createDailyReportPdfFile(obj, dataObjList) : createDailyReportSheetFile(obj, dataObjList);
//			if (filePath == null) return false;
			
//			sentReportByMail(filePath, dataObjList.get(0).getSubscribers(), obj.getCadence_range_name(), 16, "Customer", obj.getCadence_range_name());
//			return obj;
			return new BuildingReportEntity();
		} catch (Exception e) {
			return new BuildingReportEntity();
		}
	}
	
	/**
	 * @description write to pdf file
	 * @author Hung.Bui
	 * @since 2024-07-01
	 * @param cadenceRange
	 */
	public File writeToPdfFile(String cadenceRangeName) throws Exception {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		String fileName = getReportFolderPath() + "/" + cadenceRangeName + "-report-" + timeStamp + ".pdf";
		return new File(fileName);
	}
	
	/**
	 * @description create daily report pdf file
	 * @author Hung.Bui
	 * @since 2025-08-08
	 * @param obj
	 * @return file path
	 */
//	public String createReportPdfFile(BuildingReportEntity obj, List<BuildingReportEntity> dataObjList) {
	public String createReportPdfFile(BuildingReportEntity obj) {
		try {
//			File file = writeToPdfFile(obj.getCadence_range_name());
			File file = writeToPdfFile("comprehensive-utilities");
			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A3.rotate());
			) {
				Image logoImage = DocumentHelper.readLogoImageFile();
				
//				for (int l = 0; l < dataObjList.size(); l++) {
//					ViewReportEntity dataObj = dataObjList.get(l);
//					
//					if (dataObj != null) {
//						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//						SimpleDateFormat categoryFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//						Date startDate = dateFormat.parse(obj.getStart_date());
//						Date endDate = dateFormat.parse(obj.getEnd_date());
//						dataObj.setStart_date(format.format(startDate));
//						dataObj.setEnd_date(format.format(endDate));
//						List<DailyDateEntity> dataExports = dataObj.getDataReports() != null ? dataObj.getDataReports() : new ArrayList<>();
//						
//						// total column: 12
//						Table table = new Table(12).useAllAvailableWidth();
//						table.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN));
//						table.setFontSize(8);
//						table.setTextAlignment(TextAlignment.CENTER);
//						
//						//====== table ============================================================
//						// header and logo
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setHeight(14).setBorder(Border.NO_BORDER));
//						table.addCell(new com.itextpdf.layout.element.Cell(6, 5).add(new Paragraph("DAILY PRODUCTION REPORT")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER).setFontSize(20).setBold());
//						table.addCell(new com.itextpdf.layout.element.Cell(6, 3).add(logoImage).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setBorder(Border.NO_BORDER));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Site Name").setBold().setTextAlignment(TextAlignment.LEFT)));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataObj.getSite_name()).setBold().setTextAlignment(TextAlignment.LEFT)));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Report Date").setBold().setTextAlignment(TextAlignment.LEFT)));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataObj.getReport_date()).setTextAlignment(TextAlignment.LEFT)));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("Covered Period").setBold().setTextAlignment(TextAlignment.LEFT)));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(dataObj.getStart_date() + " - " + dataObj.getEnd_date()).setTextAlignment(TextAlignment.LEFT)));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 1).add(new Paragraph("System Size (kW DC)").setBold().setTextAlignment(TextAlignment.LEFT)));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(String.valueOf(dataObj.getDc_capacity())).setTextAlignment(TextAlignment.LEFT)));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 4).setHeight(14).setBorder(Border.NO_BORDER));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
//						
//						// chart
//						com.itextpdf.layout.element.Cell chartCell = new com.itextpdf.layout.element.Cell(16, 12);
//						table.addCell(chartCell.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
//						// empty row
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 12).setHeight(14).setBorder(Border.NO_BORDER));
//						
//						// header of data table
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Time").setBold()));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Actual Power (kW)").setBold()));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Actual Energy (kWh)").setBold()));
//						table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph("Irradiance (W/m2)").setBold()));
//						
//						// data table
//						DecimalFormat dfs = new DecimalFormat(DocumentHelper.noDecimalDataFormat);
//						for (int i = 0; i < dataExports.size(); i++) {
//							DailyDateEntity item = (DailyDateEntity) dataExports.get(i);
//							
//							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getCategories_time())));
//							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getPower() != null ? dfs.format(item.getPower()).replaceAll( "^-(?=0(\\.0*)?$)", "") : "")));
//							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getEnergy() != null ? dfs.format(item.getEnergy()) : "")));
//							table.addCell(new com.itextpdf.layout.element.Cell(1, 3).add(new Paragraph(item.getIrradiance() != null ? dfs.format(item.getIrradiance()) : "")));						
//						}
//						
//						//====== chart ============================================================
//						JFreeChart chart = DocumentHelper.createJFreeChart(null);
//						XYPlot plot = chart.getXYPlot();
//						
//						// data source
//						TimeSeriesCollection powerDataset = DocumentHelper.createJFreeChartLineDataset(0, plot, null);
//						TimeSeries powerSeries = new TimeSeries("Actual Power (kW)");
//						powerDataset.addSeries(powerSeries);
//						plot.getRendererForDataset(powerDataset).setSeriesPaint(0, BLUE_COLOR);
//						
//						TimeSeriesCollection energyDataset = DocumentHelper.createJFreeChartLineDataset(1, plot, null);
//						TimeSeries energySeries = new TimeSeries("Actual Energy (kWh)");
//						energyDataset.addSeries(energySeries);
//						plot.getRendererForDataset(energyDataset).setSeriesPaint(0, LIGHT_BLUE_COLOR);
//						
//						TimeSeriesCollection irradianceDataset = DocumentHelper.createJFreeChartLineDataset(2, plot, null);
//						TimeSeries irradianceSeries = new TimeSeries("Irradiance (W/m2)");
//						irradianceDataset.addSeries(irradianceSeries);
//						plot.getRendererForDataset(irradianceDataset).setSeriesPaint(0, ORANGE_COLOR);
//						plot.getRendererForDataset(irradianceDataset).setSeriesVisible(0, dataObj.isHave_poa());
//						
//						for (int i = 0; i < dataExports.size(); i++) {
//							DailyDateEntity item = dataExports.get(i);
//							RegularTimePeriod period = new Minute(categoryFormat.parse(item.getCategories_time()));
//							
//							powerSeries.add(period, item.getPower());
//							energySeries.add(period, item.getEnergy());
//							irradianceSeries.add(period, item.getIrradiance());
//						}
//						
//						// category axis
//						DocumentHelper.createJFreeChartDomainAxis(plot, new DateTickUnit(DateTickUnitType.HOUR, 24, categoryFormat), startDate, endDate).setTickMarkPosition(DateTickMarkPosition.START);
//						// left axis
//						DocumentHelper.createJFreeChartNumberAxis("kW", AxisLocation.BOTTOM_OR_LEFT, 0, 0, plot);
//						// right axis
//						DocumentHelper.createJFreeChartNumberAxis("kWh", AxisLocation.BOTTOM_OR_RIGHT, 1, 1, plot);
//						// 2nd right axis
//						if (dataObj.isHave_poa()) DocumentHelper.createJFreeChartNumberAxis("W/m2", AxisLocation.BOTTOM_OR_RIGHT, 2, 2, plot);
//						
//						chartCell.add(new Image(ImageDataFactory.create(chart.createBufferedImage(1800, 700), null)).setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER).scaleToFit(1100, 700));
//						document.add(table);
//						if (l < dataObjList.size() - 1) document.add(new AreaBreak());
//					}
//				}
				
				// It must be closed before attach to mail
				document.close();
				
				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	
}
