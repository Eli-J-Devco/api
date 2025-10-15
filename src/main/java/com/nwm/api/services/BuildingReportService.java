/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.svg.converter.SvgConverter;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.properties.TextAlignment;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.BuildingReportDateEntity;
import com.nwm.api.entities.BuildingReportEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import org.jfree.chart.text.TextBlock;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.imageio.ImageIO;

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
						obj.setPower_factor(dataDaytimeAndNightTime.getPower_factor());
						obj.setMax_annual_daily(dataDaytimeAndNightTime.getMax_annual_demand());
						obj.setMax_monthly_demand(dataDaytimeAndNightTime.getMax_monthly_demand());
					}
					
					
					BuildingReportEntity dataPF = (BuildingReportEntity) queryForObject("BuildingReport.getDataLowestPF", obj);
					if(dataPF != null) {
						obj.setPower_factor_pf(dataPF.getPower_factor_pf());
						obj.setPower_factor_pf_time(dataPF.getPower_factor_pf_time());
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
			
			String filePath = createReportPdfFile(obj);
			obj.setDownload_file_path(filePath);

            System.out.println(filePath);
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

    final int BORDER_RADIUS = 10;
    final DeviceRgb UP_COLOR = new DeviceRgb(249, 101, 102);
    final DeviceRgb DOWN_COLOR = new DeviceRgb(1, 155, 78);

    final String TEMPERATURE = "Temperature";
    final String HUMIDITY = "Humidity";
    final String ELECTRIC = "Electric";
    final String GAS = "Gas";
    final String WATER = "Water";
    final String PV_PRODUCTION = "PV Production";

    final String ELECTRIC_UNIT = "kWh";
    final String GAS_UNIT = "therms";
    final String WATER_UNIT = "gal";
    final String PV_PRODUCTION_UNIT = "kWh";

    final String REPORT_USAGE_HISTORY = "Usage History";
    final String REPORT_DAILY_TOTALS = "Daily Totals";

    final Color COLOR_ELECTRIC_CHART = new Color(234, 127, 31);
    final Color COLOR_GAS_CHART = new Color(255, 213, 79);
    final Color COLOR_WATER_CHART = new Color(0, 143, 211);
    final Color COLOR_PV_PRODUCTION_CHART = new Color(1, 155, 78);
    final Color COLOR_EXPECTED_CHART = new Color(211, 211, 211);

    final DeviceRgb COLOR_ELECTRIC_CARD_BACKGROUND = new DeviceRgb(255, 235, 235);
    final DeviceRgb COLOR_GAS_CARD_BACKGROUND = new DeviceRgb(255, 250, 235);
    final DeviceRgb COLOR_WATER_CARD_BACKGROUND = new DeviceRgb(235, 250, 255);
    final DeviceRgb COLOR_PV_PRODUCTION_CARD_BACKGROUND = new DeviceRgb(235, 255, 245);

    final String UP_ARROW_URL = "https://files.nextwavemonitoring.com/uploads/reports/up.png";
    final String DOWN_ARROW_URL = "https://files.nextwavemonitoring.com/uploads/reports/down.png";

    final String ICON_ELECTRIC_URL = "https://files.nextwavemonitoring.com/uploads/reports/icon_electric.svg";
    final String ICON_GAS_URL = "https://files.nextwavemonitoring.com/uploads/reports/icon_gas.svg";
    final String ICON_WATER_URL = "https://files.nextwavemonitoring.com/uploads/reports/icon_water.svg";
    final String ICON_PV_PRODUCTION_URL = "https://files.nextwavemonitoring.com/uploads/reports/icon_pv.svg";

    final String CHART_ILLUSTRATION_ELECTRIC_URL = "https://files.nextwavemonitoring.com/uploads/reports/electric.svg";
    final String CHART_ILLUSTRATION_GAS_URL = "https://files.nextwavemonitoring.com/uploads/reports/gas.svg";
    final String CHART_ILLUSTRATION_WATER_URL = "https://files.nextwavemonitoring.com/uploads/reports/water.svg";
    final String CHART_ILLUSTRATION_PV_PRODUCTION_URL = "https://files.nextwavemonitoring.com/uploads/reports/pv.svg";

    final String REPORT_WATER_TITLE_IMG_URL = "https://files.nextwavemonitoring.com/uploads/reports/img_water.png";
    final String REPORT_GAS_TITLE_IMG_URL = "https://files.nextwavemonitoring.com/uploads/reports/img_gas.png";
    final String REPORT_ELECTRIC_TITLE_IMG_URL = "https://files.nextwavemonitoring.com/uploads/reports/img_electric.png";
    final String REPORT_PV_PRODUCTION_TITLE_IMG_URL = "https://files.nextwavemonitoring.com/uploads/reports/img_pv.png";

    final String REPORT_WATER_ITEM_METER_IMG_URL = "https://files.nextwavemonitoring.com/uploads/reports/water-meter.png";
    final String REPORT_GAS_ITEM_METER_IMG_URL = "https://files.nextwavemonitoring.com/uploads/reports/gas-meter.png";
    final String REPORT_ELECTRIC_ITEM_METER_IMG_URL = "https://files.nextwavemonitoring.com/uploads/reports/electric-meter.png";
    final String REPORT_PV_PRODUCTION_ITEM_METER_IMG_URL = "https://files.nextwavemonitoring.com/uploads/reports/pv-meter.png";

    /**
     * @description create building pdf report file
     * @author Minh.Le
     * @since 2025-09-28
     * @param obj
     * @return String: path
     */
	public String createReportPdfFile(BuildingReportEntity obj) {
		try {
            String userHome = System.getProperty("user.home");
            File downloadsDir = Paths.get(userHome, "Downloads").toFile();

            File file = new File(downloadsDir, "report.pdf");

            //Fetch data
            BuildingReportEntity buildingReportEntity = getDataBuildingReport(obj);
            BuildingReportEntity buildingReportEntity_1 = getDataCategoryStatisticsReport(obj);
            BuildingReportEntity buildingReportEntity_2 = getDataWeatherStationReport(obj);
            BuildingReportEntity buildingReportEntity_3 = getDataBuildingReportByType(obj);



			
			try (
				PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
				Document document = new Document(pdfDocument, PageSize.A4.rotate());
			) {
                //Mock data
                com.itextpdf.kernel.colors.Color[] cardBackgroundColors = new com.itextpdf.kernel.colors.Color[] {
                        new DeviceRgb(255, 235, 235),
                        new DeviceRgb(255, 250, 235),
                        new DeviceRgb(235, 250, 255),
                        new DeviceRgb(235, 255, 245)
                };

                com.itextpdf.kernel.colors.Color[] textColors = new com.itextpdf.kernel.colors.Color[] {
                        new DeviceRgb(234, 127, 31),
                        new DeviceRgb(255, 213, 79),
                        new DeviceRgb(0, 143, 211),
                        new DeviceRgb(1, 155, 78)
                };

                String[] energyTypes = {"Electric", "Gas", "Water", "PV Production"};
                String[] units = {"kWh", "Therms", "Gal", "kWh"};
                String[] logoLinks = new String[]{
                        "https://files.nextwavemonitoring.com/uploads/reports/icon_electric.svg",
                        "https://files.nextwavemonitoring.com/uploads/reports/icon_gas.svg",
                        "https://files.nextwavemonitoring.com/uploads/reports/icon_water.svg",
                        "https://files.nextwavemonitoring.com/uploads/reports/icon_pv.svg"
                };

                String[] cardBottomImages = new String[]{
                        "https://files.nextwavemonitoring.com/uploads/reports/electric.svg",
                        "https://files.nextwavemonitoring.com/uploads/reports/gas.svg",
                        "https://files.nextwavemonitoring.com/uploads/reports/water.svg",
                        "https://files.nextwavemonitoring.com/uploads/reports/pv.svg"
                };

                //PDF Title
                document.add(new Paragraph("Costco - Murrieta (#1390) - Comprehensive Utilities Report").setBold());
                document.add(new Paragraph("August 30, 2025 - September 29, 2025 • 31 days")
                        .setFontColor(DeviceGray.GRAY));
                document.add(new Paragraph("\n"));

                //Overview
                Table overviewTable = createOverView(cardBackgroundColors, energyTypes,
                        units, logoLinks, cardBottomImages, pdfDocument);
                document.add(overviewTable);

                //Performance Insights
                document.add(new Paragraph("\nPerformance Insights\n").setBold());

                Table perfInsTable = createPerfInsCards(logoLinks, pdfDocument);

                document.add(perfInsTable);

                document.add(new Paragraph("\n"));

                document.add(createPerfInsCharts());

                document.add(new Paragraph("\n"));

                //Usage Summary
                Div usageGenerationSummary = createUsageGenerationSummary(logoLinks, energyTypes, units, textColors, pdfDocument);
                document.add(usageGenerationSummary);

                //Usage Report
                Div energyWaterReport = createEnergyReportByType(WATER, pdfDocument);
                Div energyGasReport = createEnergyReportByType(GAS, pdfDocument);
                Div energyElectricReport = createEnergyReportByType(ELECTRIC, pdfDocument);
                Div energyPVProductionReport = createEnergyReportByType(PV_PRODUCTION, pdfDocument);

                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                document.add(energyWaterReport);
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                document.add(energyGasReport);
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                document.add(energyElectricReport);
                document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                document.add(energyPVProductionReport);

                System.out.println("Export completed !");
                return file.getPath();
			}
		} catch (Exception e) {
            e.printStackTrace();
			return null;
		}
	}


    /**
     * @description create overview
     * @author Minh.Le
     * @since 2025-09-28
     * @param colors
     * @param energyTypes
     * @param units
     * @param logoLinks
     * @param cardBottomImages
     * @param pdfDocument
     * @return
     */
    public Table createOverView(com.itextpdf.kernel.colors.Color[] colors, String[] energyTypes, String[] units,
                                String[] logoLinks, String[] cardBottomImages, PdfDocument pdfDocument) {
        //Mock data
        double[] values = {120.5, 85.2, 200.0, 50.8};
        boolean[] upArrow = {true, false, true, false};
        double[] percents = {5.2, 3.4, 90, 21.5};

        DeviceRgb[] cardBorderColors = {
                new DeviceRgb(234, 127, 31),
                new DeviceRgb(255, 213, 79),
                new DeviceRgb(0, 143, 211),
                new DeviceRgb(1, 155, 78)
        };

        float[] colWidthsOverView = {1, 0.04f, 1, 0.04f, 1, 0.04f, 1};
        Table overviewTable = createCommonTableContainer(colWidthsOverView);

        //Create cards
        for (int i = 0; i < 4; i++) {
            Cell cardCell = createCommonCell();

            Div card = new Div();
            card.setBackgroundColor(colors[i]);
            card.setBorderTop(new SolidBorder(cardBorderColors[i], 1f));
            card.setBorderRadius(new BorderRadius(BORDER_RADIUS));
            card.setMinHeight(210);

            Table cardBody = createCommonTableContainer(new float[] {1});
            cardBody.setMargins(5,5,5,5);

            //Handle first row
            Table topRow = createCommonTableContainer(new float[] {80,20});

            //Left top
            Table leftTop = createCommonTableContainer(new float[] {25,75});

            //Logo
            Image logo = imageFromSvgHandler(logoLinks[i], pdfDocument)
                    .scaleToFit(23,23)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);

            Cell logoCell = createCommonCell()
                    .add(logo)
                    .setBorderRadius(new BorderRadius(6))
                    .setBackgroundColor(new DeviceRgb(255, 255, 255))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            leftTop.addCell(logoCell);

            Cell nameCell = new Cell(2, 1)
                    .add(new Paragraph(energyTypes[i]).setBold())
                    .setBorder(Border.NO_BORDER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            leftTop.addCell(nameCell);

            //Right top
            Table rightTop = createCommonTableContainer(new float[] {1});

            Table badgeContentTable = createCommonTableContainer(new float[] {30,70});

            DeviceRgb badgeColor = upArrow[i]
                    ? UP_COLOR
                    : DOWN_COLOR;

            Image arrowIcon = upArrow[i]
                ? imageFromPngHandler(UP_ARROW_URL)
                : imageFromPngHandler(DOWN_ARROW_URL);
            arrowIcon.scaleToFit(12, 12);

            Cell iconCell = createCommonCell()
                    .add(arrowIcon)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.RIGHT);

            Cell textCell = createCommonCell()
                    .add(new Paragraph(percents[i] + "%"))
                    .setFontColor(badgeColor)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.LEFT);

            badgeContentTable.addCell(iconCell);
            badgeContentTable.addCell(textCell);

            Div badgeDiv = new Div()
                    .setBackgroundColor(null)
                    .setBorder(new SolidBorder(badgeColor, 1))
                    .setBorderRadius(new BorderRadius(BORDER_RADIUS))
                    .setTextAlignment(TextAlignment.CENTER);

            badgeDiv.add(badgeContentTable);

            Cell percentCell = createCommonCell()
                    .setPadding(0)
                    .add(badgeDiv);

            rightTop.addCell(percentCell);
            rightTop.addCell(createCommonCell()
                    .add(new Paragraph("vs. Last Month").setFontColor(DeviceGray.GRAY))
                    .setTextAlignment(TextAlignment.CENTER));

            topRow.addCell(createCommonCell().add(leftTop));
            topRow.addCell(createCommonCell().add(rightTop));

            cardBody.addCell(createCommonCell().add(topRow));

            //Index
            cardBody.addCell(createCommonCell().add(
                            new Paragraph(String.valueOf(values[i]))
                                    .setFontSize(24).setBold()
                                    .setTextAlignment(TextAlignment.CENTER)));

            //Unit of measure
            cardBody.addCell(createCommonCell().add(
                            new Paragraph(units[i])
                                    .setFontSize(12)
                                    .setFontColor(DeviceGray.GRAY)
                                    .setTextAlignment(TextAlignment.CENTER)));

            //Image bottom
            cardBody.addCell(createCommonCell().add(
                            imageFromSvgHandler(cardBottomImages[i], pdfDocument)
                                    .setAutoScale(true))
                    .setTextAlignment(TextAlignment.CENTER));

            card.add(cardBody);

            cardCell.add(card);
            overviewTable.addCell(cardCell);

            if (i < 3) {
                Cell spacer = createCommonCell();
                overviewTable.addCell(spacer);
            }
        }
        return overviewTable;
    }

    /**
     * @description create performance insights cards
     * @author Minh.Le
     * @since 2025-09-28
     * @param logoLinks
     * @param pdfDocument
     * @return
     */
    public Table createPerfInsCards(String[] logoLinks, PdfDocument pdfDocument) {

        Table perfInsTable = createCommonTableContainer(new float[]{1, 0.04f, 1, 0.04f, 1});

        Div electricalLoadCard = createCommonDivContainer()
                .setBackgroundColor(new DeviceRgb(250, 250, 250));

        Table electricalLoadCardTitle = createCommonTableContainer(new float[]{10, 90});

        electricalLoadCardTitle.addCell(createCommonCell().add(imageFromSvgHandler(logoLinks[0], pdfDocument)
                        .scaleToFit(23,23))
                .setVerticalAlignment(VerticalAlignment.TOP));

        electricalLoadCardTitle.addCell(createCommonCell()
                .add(new Paragraph("Electrical Loads (PV + Grid Consumption)").setBold())
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        electricalLoadCard.add(electricalLoadCardTitle);

        electricalLoadCard.add(new Paragraph("Highest Grid Demand:").setFontColor(DeviceGray.GRAY));
        electricalLoadCard.add(new Paragraph("925.7 kW at 13:35 PM on Aug 31, 2025"));

        electricalLoadCard.add(new Paragraph("Monthly Total Usage:").setFontColor(DeviceGray.GRAY));
        electricalLoadCard.add(new Paragraph("528,725.6 kWh (96,487.9 kWh + 432,237.8 kWh)"));

        electricalLoadCard.add(new Paragraph("Daily Average:").setFontColor(DeviceGray.GRAY));
        electricalLoadCard.add(new Paragraph("17,055.7 kWh/day"));

        electricalLoadCard.add(new Paragraph("Monthly PV Offset:").setFontColor(DeviceGray.GRAY));
        electricalLoadCard.add(new Paragraph("22.3% (96,487.9 kWh PV ÷ 432,237.8 kWh load)"));

        electricalLoadCard.add(new Paragraph("Year-over-Year Comparison:").setFontColor(DeviceGray.GRAY));
        electricalLoadCard.add(new Paragraph("Electric usage +100% vs August 2024"));

        perfInsTable.addCell(createCommonCell().add(electricalLoadCard));

        perfInsTable.addCell(createCommonCell());

        Div gasCard = createCommonDivContainer()
                .setBackgroundColor(new DeviceRgb(250, 250, 250));

        Table gasCardTitle = createCommonTableContainer(new float[]{10, 90});

        gasCardTitle.addCell(createCommonCell().add(imageFromSvgHandler(logoLinks[1], pdfDocument)
                        .scaleToFit(23,23))
                .setVerticalAlignment(VerticalAlignment.TOP));

        gasCardTitle.addCell(createCommonCell().add(new Paragraph("Gas").setBold())
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        gasCard.add(gasCardTitle);

        gasCard.add(new Paragraph("Peak Flow Rate:").setFontColor(DeviceGray.GRAY));
        gasCard.add(new Paragraph("171,985 therms at 13:35 PM on Sep 04, 2025"));

        gasCard.add(new Paragraph("Daily Average:").setFontColor(DeviceGray.GRAY));
        gasCard.add(new Paragraph("6,062.4 therms/day"));

        gasCard.add(new Paragraph("Year-over-Year Comparison:").setFontColor(DeviceGray.GRAY));

        Table gasComparison = createCommonTableContainer(new float[]{5, 95});
        gasComparison.addCell(createCommonCell().add(imageFromPngHandler(UP_ARROW_URL)
                        .scaleToFit(12,12))
                .setVerticalAlignment(VerticalAlignment.MIDDLE));
        gasComparison.addCell(createCommonCell().add(new Paragraph("100% vs August 2024")));

        gasCard.add(gasComparison);

        perfInsTable.addCell(createCommonCell().add(gasCard));

        perfInsTable.addCell(createCommonCell());

        Div waterCard = createCommonDivContainer()
                .setBackgroundColor(new DeviceRgb(250, 250, 250));

        Table waterCardTitle = createCommonTableContainer(new float[]{10, 90});

        waterCardTitle.addCell(createCommonCell().add(imageFromSvgHandler(logoLinks[2], pdfDocument)
                        .scaleToFit(23,23))
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        waterCardTitle.addCell(createCommonCell().add(new Paragraph("Water").setBold())
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        waterCard.add(waterCardTitle);

        waterCard.add(new Paragraph("Peak Flow Rate:").setFontColor(DeviceGray.GRAY));
        waterCard.add(new Paragraph("216 gal at 05:16 AM on Sep 11, 2025"));

        waterCard.add(new Paragraph("Daily Average:").setFontColor(DeviceGray.GRAY));
        waterCard.add(new Paragraph("9,720.3 gal/day"));

        waterCard.add(new Paragraph("Year-over-Year Comparison:").setFontColor(DeviceGray.GRAY));

        Table waterComparison = createCommonTableContainer(new float[]{5, 95});
        waterComparison.addCell(createCommonCell().add(imageFromPngHandler(UP_ARROW_URL)
                        .scaleToFit(12,12))
                .setVerticalAlignment(VerticalAlignment.MIDDLE));
        waterComparison.addCell(createCommonCell().add(new Paragraph("100% vs August 2024")));

        waterCard.add(waterComparison);

        perfInsTable.addCell(createCommonCell().add(waterCard));

        return perfInsTable;
    }

    /**
     * @description create performance insights chart layout
     * @author Minh.Le
     * @since 2025-09-28
     * @return
     * @throws IOException
     */
    public Table createPerfInsCharts() throws IOException {
        //Performance Insights chart table
        Table perfInsChartTable = createCommonTableContainer(new float[]{1, 0.04f, 1});

        Div perfInsChartCardTemperature = createCommonDivContainer()
                .setBackgroundColor(new DeviceRgb(250, 250, 250));

        perfInsChartCardTemperature.add(new Paragraph("Temperature Effect (°F)").setBold())
                .add(new Paragraph("This Month vs. Last Month").setFontColor(DeviceGray.GRAY));

        Div temperatureChart = drawPerfInsChart(TEMPERATURE);
        perfInsChartCardTemperature.add(temperatureChart);

        perfInsChartTable.addCell(createCommonCell().add(perfInsChartCardTemperature));

        perfInsChartTable.addCell(createCommonCell());

        Div perfInsChartCardHumidity = createCommonDivContainer()
                .setBackgroundColor(new DeviceRgb(250, 250, 250));

        perfInsChartCardHumidity.add(new Paragraph("Humidity Effect").setBold())
                .add(new Paragraph("This Month vs. Last Month").setFontColor(DeviceGray.GRAY));

        Div humidityChart = drawPerfInsChart(HUMIDITY);

        perfInsChartCardHumidity.add(humidityChart);

        perfInsChartTable.addCell(createCommonCell().add(perfInsChartCardHumidity));

        return perfInsChartTable;
    }

    /**
     * @description draw performance insights chart
     * @author Minh.Le
     * @since 2025-09-28
     * @param name
     * @return
     */
    public Div drawPerfInsChart(String name) {
        Div chartContainer = new Div();
        // Series line
        TimeSeries series = new TimeSeries("");
        if(TEMPERATURE.equals(name)) {
            series.setKey("Temperature");
        }
        else if(HUMIDITY.equals(name)) {
            series.setKey("Humidity");
        }
        TimeSeries highlightMaxSeries = new TimeSeries("Max");
        TimeSeries highlightMinSeries = new TimeSeries("Min");

        int maxValue = Integer.MIN_VALUE;
        int minValue = Integer.MAX_VALUE;
        Random rand = new Random();
        Day maxX = new Day(1, 1, 1990);
        Day minX = new Day(1, 1, 1990);

        // Mock data
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        for (int day = 1; day <= 30; day++) {
            int value = rand.nextInt(50);
            Day x = new Day(day, 9, 2025);
            series.add(x, value);

            if (value > maxValue) {
                maxValue = value;
                maxX = x;
            }
            if (value < minValue) {
                minValue = value;
                minX = x;
            }
        }

        highlightMaxSeries.add(maxX, maxValue);
        highlightMinSeries.add(minX, minValue + 1);

        TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        TimeSeriesCollection datasetMax = new TimeSeriesCollection(highlightMaxSeries);
        TimeSeriesCollection datasetMin = new TimeSeriesCollection(highlightMinSeries);

        // Axis
        DateAxis xAxis = new DateAxis();
        xAxis.setDateFormatOverride(new SimpleDateFormat("MMM dd, yyyy"));
        xAxis.setAutoRange(true);
        xAxis.setVerticalTickLabels(true);

        NumberAxis yAxis = new NumberAxis();
        if(TEMPERATURE.equals(name)) {
            yAxis.setLabel("Temperature");
        }
        if(HUMIDITY.equals(name)) {
            yAxis.setLabel("Humidity");
        }

        yAxis.setAxisLineVisible(false);
        yAxis.setTickMarksVisible(false);
        yAxis.setTickUnit(new NumberTickUnit(10));

        //Symbol ° for Y axis
        if(TEMPERATURE.equals(name)) {
            yAxis.setNumberFormatOverride(new DecimalFormat("0°"));
        }

        // Renderer spline cho line
        XYLineAndShapeRenderer splineRenderer = new XYLineAndShapeRenderer();
        splineRenderer.setSeriesPaint(0, new Color(255, 99, 71));
        splineRenderer.setSeriesShapesVisible(0, false);

        // Renderer max
        XYLineAndShapeRenderer maxRenderer = new XYLineAndShapeRenderer();
        maxRenderer.setSeriesLinesVisible(0, false);
        maxRenderer.setSeriesShapesVisible(0, true);
        maxRenderer.setSeriesShape(0, new Ellipse2D.Double(-5, -5, 10, 10));
        maxRenderer.setSeriesPaint(0, new Color(255, 99, 71));

        // Renderer min
        XYLineAndShapeRenderer minRenderer = new XYLineAndShapeRenderer();
        minRenderer.setSeriesLinesVisible(0, false);
        minRenderer.setSeriesShapesVisible(0, true);
        minRenderer.setSeriesShape(0, new Ellipse2D.Double(-5, -5, 10, 10));
        minRenderer.setSeriesPaint(0, new Color(34, 139, 34));

        XYPlot plot = new XYPlot();
        plot.setDataset(0, dataset);
        plot.setRenderer(0, splineRenderer);

        plot.setDataset(1, datasetMax);
        plot.setRenderer(1, maxRenderer);

        plot.setDataset(2, datasetMin);
        plot.setRenderer(2, minRenderer);

        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        plot.setDomainAxis(xAxis);
        plot.setRangeAxis(yAxis);

        // Style
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlineStroke(new BasicStroke(1.0f));
        plot.setDomainGridlinesVisible(false);

        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, false);

        chart.setBackgroundPaint(null);

        // Export chart to Image
        BufferedImage bufferedChart = chart.createBufferedImage(500, 300);

        Image pdfChartImage = writeDataFromBufferToImage(bufferedChart);
        chartContainer.add(pdfChartImage.setAutoScale(true));

        String unit = "";
        if(TEMPERATURE.equals(name)) unit = "°F";
        else unit = "%";

        Table peakLegend = createCommonTableContainer(new float[]{5, 95});

        Cell bulletPeak = createCommonCell().setVerticalAlignment(VerticalAlignment.TOP)
                .setPaddingTop(6);
        bulletPeak.add(createBulletImage(8, new DeviceRgb(255, 99, 71))
                .setHorizontalAlignment(HorizontalAlignment.CENTER));
        peakLegend.addCell(bulletPeak);

        Paragraph peakLegendText = new Paragraph("Peak: 46.6" + unit + " on Sep 23, 2025 → Possible impact: Increased energy usage")
                .setFontColor(DeviceGray.GRAY);
        peakLegend.addCell(createCommonCell().add(peakLegendText));

        Table lowLegend = createCommonTableContainer(new float[]{5, 95});

        Cell bulletLow = createCommonCell().setVerticalAlignment(VerticalAlignment.MIDDLE);
        bulletLow.add(createBulletImage(8, new DeviceRgb(34, 139, 34))
                .setHorizontalAlignment(HorizontalAlignment.CENTER));
        lowLegend.addCell(bulletLow);

        Paragraph lowLegendText = new Paragraph("Lowest Utility Usage: 0 units on Sep 10, 2025")
                .setFontColor(DeviceGray.GRAY);
        lowLegend.addCell(createCommonCell().add(lowLegendText));

        chartContainer.add(peakLegend);
        chartContainer.add(lowLegend);

        return chartContainer;
    }

    /**
     * @description create usage generation summary
     * @author Minh.Le
     * @since 2025-09-28
     * @param logoLinks
     * @param energyTypes
     * @param units
     * @param textColors
     * @param pdfDocument
     * @return
     */
    public Div createUsageGenerationSummary(String[] logoLinks, String[] energyTypes, String[] units,
                                            com.itextpdf.kernel.colors.Color[] textColors, PdfDocument pdfDocument) {
        Div energyCard = createCommonDivContainer();

        Paragraph title = new Paragraph("Usage & Generation Summary")
                .setBold()
                .setMarginBottom(10);
        energyCard.add(title);

        Table summaryTable = createCommonTableContainer(new float[]{1, 0.04f, 1, 0.04f, 1, 0.04f, 1});

        for (int i = 0; i < 4; i++) {
            Table energyTitle = createCommonTableContainer(new float[]{10, 90});

            energyTitle.addCell(createCommonCell().add(imageFromSvgHandler(logoLinks[i], pdfDocument)
                    .scaleToFit(23,23))
                    .setVerticalAlignment(VerticalAlignment.MIDDLE));

            energyTitle.addCell(createCommonCell()
                    .add(new Paragraph(energyTypes[i]).setBold())
                    .setVerticalAlignment(VerticalAlignment.MIDDLE));

            SolidLine lineStyle = new SolidLine(1f);
            lineStyle.setColor(DeviceGray.GRAY);

            LineSeparator energyLine = new LineSeparator(lineStyle)
                    .setMarginTop(10)
                    .setMarginBottom(10);

            Cell energyDetail = createCommonCell();

            energyDetail.add(new Paragraph("Peak:").setFontColor(DeviceGray.GRAY));
            energyDetail.add(new Paragraph("Sep 19, 2025 / 15,184.3 " + units[i]).setMarginBottom(10));
            energyDetail.add(new Paragraph("Low:").setFontColor(DeviceGray.GRAY));
            energyDetail.add(new Paragraph("Oct 05, 2025 / 9,867 " + units[i]).setMarginBottom(10));
            energyDetail.add(new Paragraph("Range:").setFontColor(DeviceGray.GRAY));
            energyDetail.add(new Paragraph("413,152 " + units[i]).setFontColor(textColors[i]));

            summaryTable.addCell(createCommonCell()
                            .add(energyTitle)
                            .add(energyLine)
                            .add(energyDetail));

            if(i < 3) summaryTable.addCell(createCommonCell());
        }

        energyCard.add(summaryTable);

        return energyCard;
    }

    /**
     * @description create usage report
     * @author Minh.Le
     * @since 2025-09-28
     * @param energyType
     * @param pdfDocument
     * @return
     */
    public Div createEnergyReportByType(String energyType, PdfDocument pdfDocument) {
        Div energyReport = new Div();

        Table energyReportHeader = createCommonTableContainer(new float[]{1, 1});

        Paragraph title = new Paragraph().setBold();

        if (!energyType.equals("PV Production")) {
            title.add(energyType + " Service Usage Report");
        } else {
            title.add(energyType + " Service Report");
        }

        Paragraph subTitle = new Paragraph("September 06, 2025 - October 05, 2025 • 30 days")
                .setFontColor(DeviceGray.GRAY);

        //Report header
        energyReportHeader.addCell(createCommonCell()
                .add(title)
                .add(subTitle));

        Image titleImage = imageFromPngHandler(REPORT_WATER_TITLE_IMG_URL);
        switch (energyType) {
            case WATER:
                break;
            case GAS:
                titleImage = imageFromPngHandler(REPORT_GAS_TITLE_IMG_URL);
                break;
            case ELECTRIC:
                titleImage = imageFromPngHandler(REPORT_ELECTRIC_TITLE_IMG_URL);
                break;
            case PV_PRODUCTION:
                titleImage = imageFromPngHandler(REPORT_PV_PRODUCTION_TITLE_IMG_URL);
                break;
        }

        energyReportHeader.addCell(createCommonCell()
                .add(titleImage.scaleToFit(50,50)
                        .setHorizontalAlignment(HorizontalAlignment.RIGHT)));

        energyReport.add(energyReportHeader);

        Table reportBody = createCommonTableContainer(new float[]{6, 0.1f, 4});

        Cell leftSide = createCommonCell();

        //Detail of Current Charges
        Div detail = createDetailOfCurrentCharges(energyType, pdfDocument);
        leftSide.add(detail);

        leftSide.add(new Paragraph("\n"));

        //History
        Div history = createUsageHistory(energyType).setKeepTogether(true);
        leftSide.add(history);

        //Billing
        Div billing = createBillingSummary(energyType);
        Cell rightSide = createCommonCell();
        rightSide.add(billing);

        rightSide.add(new Paragraph("\n"));

        //Daily Totals This Period
        Div dailyTotals = createDailyTotals(energyType);
        rightSide.add(dailyTotals);

        rightSide.add(new Paragraph("\n"));

        Div itemMeter = createItemMeter(energyType);
        rightSide.add(itemMeter);

        reportBody.addCell(leftSide);

        reportBody.addCell(createCommonCell());

        reportBody.addCell(rightSide);

        energyReport.add(reportBody);

        return energyReport;
    }

    /**
     * @description create datail of current charges
     * @author Minh.Le
     * @since 2025-09-28
     * @param energyType
     * @param pdfDocument
     * @return
     */
    public Div createDetailOfCurrentCharges(String energyType, PdfDocument pdfDocument) {
        Div detail = createCommonDivContainer();

        detail.add(new Paragraph("Detail of Current Charges")
                .setBold()
                .setMarginBottom(10));

        //Service information
        detail.add(new Paragraph("Service Information")
                .setBold());

        Table detailServiceInfo = createCommonTableContainer(new float[] {1,1});

        Cell detailServiceInfoName = createCommonCell();

        detailServiceInfoName.add(new Paragraph("Service Address:"));
        detailServiceInfoName.add(new Paragraph("Meter Number:"));
        detailServiceInfoName.add(new Paragraph("Meter Model:"));

        detailServiceInfo.addCell(detailServiceInfoName);

        Cell detailServiceInfoData = createCommonCell().setTextAlignment(TextAlignment.RIGHT);

        detailServiceInfoData.add(new Paragraph("Murrieta, 92563"));
        detailServiceInfoData.add(new Paragraph("\n"));
        detailServiceInfoData.add(new Paragraph("Sensus Omni T2 Water Meter"));

        detailServiceInfo.addCell(detailServiceInfoData);

        detail.add(detailServiceInfo);

        //Read Details
        detail.add(new Paragraph("Read Details")
                .setBold());

        Table detailReadDetail = createCommonTableContainer(new float[] {1,1});

        Cell detailReadDetailName = createCommonCell();

        detailReadDetailName.add(new Paragraph("Previous read:"));
        detailReadDetailName.add(new Paragraph("Current read:"));
        detailReadDetailName.add(new Paragraph("Difference:"));

        detailReadDetail.addCell(detailReadDetailName);

        Cell detailReadDetailData = createCommonCell()
                .setTextAlignment(TextAlignment.RIGHT);

        detailReadDetailData.add(new Paragraph("11,147,827"));
        detailReadDetailData.add(new Paragraph("11,426,646"));
        if(WATER.equals(energyType))
            detailReadDetailData.add(new Paragraph("11,426,646" + " gal"));
        else if(GAS.equals(energyType))
            detailReadDetailData.add(new Paragraph("11,426,646" + " therms"));
        else
            detailReadDetailData.add(new Paragraph("11,426,646" + " kWh"));

        detailReadDetail.addCell(detailReadDetailData);

        detail.add(detailReadDetail);

        //Usage this Period
        detail.add(new Paragraph("Usage this Period")
                .setBold());

        Table detailUsageThisPeriod = createCommonTableContainer(new float[] {1,1});

        Cell detailUsageThisPeriodName = createCommonCell();

        detailUsageThisPeriodName.add(new Paragraph("Total Usage").setBold());
        detailUsageThisPeriodName.add(new Paragraph("Peak Flow Rate:"));
        detailUsageThisPeriodName.add(new Paragraph("Max Daily Usage:"));
        detailUsageThisPeriodName.add(new Paragraph("Max Annual Daily Usage:"));

        detailUsageThisPeriod.addCell(detailUsageThisPeriodName);

        Cell detailUsageThisPeriodData = createCommonCell().setTextAlignment(TextAlignment.RIGHT);;

        if(WATER.equals(energyType)) {
            detailUsageThisPeriodData.add(new Paragraph("278,819" + " gal").setBold());
            detailUsageThisPeriodData.add(new Paragraph("216" + " gal on Sep 11, 2025 at 05:16 AM"));
            detailUsageThisPeriodData.add(new Paragraph("12920" + " gal on Sep 16, 2025"));
            detailUsageThisPeriodData.add(new Paragraph("12,920" + " gal on Sep 16, 2025"));
        }
        else if(GAS.equals(energyType)) {
            detailUsageThisPeriodData.add(new Paragraph("278,819" + " therms").setBold());
            detailUsageThisPeriodData.add(new Paragraph("216" + " therms on Sep 11, 2025 at 05:16 AM"));
            detailUsageThisPeriodData.add(new Paragraph("12920" + " therms on Sep 16, 2025"));
            detailUsageThisPeriodData.add(new Paragraph("12,920" + " therms on Sep 16, 2025"));
        }
        else {
            detailUsageThisPeriodData.add(new Paragraph("278,819" + " kWh").setBold());
            detailUsageThisPeriodData.add(new Paragraph("216" + " kWh on Sep 11, 2025 at 05:16 AM"));
            detailUsageThisPeriodData.add(new Paragraph("12920" + " kWh on Sep 16, 2025"));
            detailUsageThisPeriodData.add(new Paragraph("12,920" + " kWh on Sep 16, 2025"));
        }

        detailUsageThisPeriod.addCell(detailUsageThisPeriodData);

        detail.add(detailUsageThisPeriod);

        SolidLine lineStyle = new SolidLine(1f);
        lineStyle.setColor(DeviceGray.GRAY);

        LineSeparator breakLine = new LineSeparator(lineStyle)
                .setMarginTop(1)
                .setMarginBottom(1);

        detail.add(breakLine);

        if(ELECTRIC.equals(energyType)) {
            //Powerfactor
            Table powerFactorTable = createCommonTableContainer(new float[] {1,1});
            Cell pfName = createCommonCell();

            pfName.add(new Paragraph("Powerfactor(%)").setFontColor(DeviceGray.GRAY));
            pfName.add(new Paragraph("Lowest PF this period:").setFontColor(DeviceGray.GRAY));

            powerFactorTable.addCell(pfName);

            Cell pfData = createCommonCell().setTextAlignment(TextAlignment.RIGHT);;

            pfData.add(new Paragraph("-97.6%").setFontColor(DeviceGray.GRAY));
            pfData.add(new Paragraph("-99.8% on at 08:50 AM on Sep 26, 2025").setFontColor(DeviceGray.GRAY));

            powerFactorTable.addCell(pfData);

            detail.add(powerFactorTable);

            //Usage breakdown
            Div usageBreakdown = new Div().setBorder(Border.NO_BORDER).setKeepTogether(true);

            usageBreakdown.add(new Paragraph("Usage Breakdown").setBold());

            Table usageBreakdownBody = createCommonTableContainer(new float[] {1,1});
            Cell usageBreakdownName = createCommonCell();

            usageBreakdownName.add(new Paragraph("Daytime (6 AM – 6 PM):"));
            usageBreakdownName.add(new Paragraph("Nighttime (6 PM - 6 AM):"));
            usageBreakdownName.add(new Paragraph("Grid Import:"));
            usageBreakdownName.add(new Paragraph("PV Production:"));
            usageBreakdownName.add(new Paragraph("PV Offset:"));

            usageBreakdownBody.addCell(usageBreakdownName);

            Cell usageBreakdownData = createCommonCell().setTextAlignment(TextAlignment.RIGHT);;

            usageBreakdownData.add(new Paragraph("239,165.3" + " " + ELECTRIC_UNIT));
            usageBreakdownData.add(new Paragraph("172,441" + " " + ELECTRIC_UNIT));
            usageBreakdownData.add(new Paragraph("411,606.3" + " " + ELECTRIC_UNIT));
            usageBreakdownData.add(new Paragraph("92,671.8" + " " + ELECTRIC_UNIT));
            usageBreakdownData.add(new Paragraph("18.4% = 92,671.8 ÷ ( 411,606.3 + 92,671.8)"));

            usageBreakdownBody.addCell(usageBreakdownData);

            usageBreakdown.add(usageBreakdownBody);

            detail.add(usageBreakdown);
        }

        //Comparisons
        Div comparisons = new Div().setBorder(Border.NO_BORDER);

        comparisons.add(new Paragraph("Comparisons").setBold());

        Table comparisonBody = createCommonTableContainer(new float[] {1,1}).setFontColor(DeviceGray.GRAY);

        String unit = "";
        switch(energyType) {
            case WATER:
                unit = WATER_UNIT;
                break;
            case GAS:
                unit = GAS_UNIT;
                break;
            case ELECTRIC:
            case PV_PRODUCTION:
                unit = ELECTRIC_UNIT;
                break;
        }

        comparisonBody.addCell(createCommonCell().add(new Paragraph("Daily Average This Period:")));

        comparisonBody.addCell(createCommonCell().add(new Paragraph("9,294 " + unit + "/day")
                .setTextAlignment(TextAlignment.RIGHT)));

        comparisonBody.addCell(createCommonCell().add(new Paragraph().add(imageFromSvgHandler("https://www.svgrepo.com/show/352329/pencil-alt.svg", pdfDocument)
                        .scaleToFit(12,12))
                .add(" Daily Average Last Period:")));
        comparisonBody.addCell(createCommonCell().add(new Paragraph("10,093.7 " + unit + "/day")
                .setTextAlignment(TextAlignment.RIGHT)));

        comparisonBody.addCell(createCommonCell().add(new Paragraph("Change vs Last Month:")));

        Table changeDataLastMonth = createCommonTableContainer(new float[]{90,10});

        changeDataLastMonth.addCell(createCommonCell().add(imageFromPngHandler(UP_ARROW_URL)
                        .scaleToFit(12,12)
                        .setHorizontalAlignment(HorizontalAlignment.RIGHT))
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        changeDataLastMonth.addCell(createCommonCell().add(new Paragraph("97.62%")
                        .setFontColor(UP_COLOR)));

        comparisonBody.addCell(createCommonCell().add(changeDataLastMonth));

        comparisonBody.addCell(createCommonCell().add(new Paragraph("Change vs Last Year:")));

        Table changeDataLastYear = createCommonTableContainer(new float[]{90,10});

        changeDataLastYear.addCell(createCommonCell().add(imageFromPngHandler(UP_ARROW_URL)
                        .scaleToFit(12,12)
                        .setHorizontalAlignment(HorizontalAlignment.RIGHT))
                .setVerticalAlignment(VerticalAlignment.MIDDLE));

        changeDataLastYear.addCell(createCommonCell().add(new Paragraph("100%")
                        .setFontColor(UP_COLOR)));

        comparisonBody.addCell(createCommonCell().add(changeDataLastYear));

        comparisons.add(comparisonBody);

        detail.add(comparisons);

        return detail;
    }

    /**
     * @description create usage history
     * @author Minh.Le
     * @since 2025-09-28
     * @param energyType
     * @return
     */
    public Div createUsageHistory(String energyType) {
        Div history = createCommonDivContainer();

        history.add(new Paragraph(energyType + " Usage History")
                .setBold()
                .setMarginBottom(10));

        Image chartImage = drawUsageReportChart(REPORT_USAGE_HISTORY, energyType);

        history.add(chartImage);

        if(WATER.equals(energyType))
            history.add(new Paragraph("Units: gallons • 1 CCF = 748 gallons").setFontColor(DeviceGray.GRAY));

        Table statisticalTable = createCommonTableContainer(new float[]{0.34f, 0.22f, 0.22f, 0.22f})
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);

        statisticalTable.addCell(createCommonCell().add(new Paragraph(""))
                .setBorderBottom(new SolidBorder(DeviceGray.GRAY, 1)));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("Jul 2025"))
                .setBorderBottom(new SolidBorder(DeviceGray.GRAY, 1)));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("Aug 2025"))
                .setBorderBottom(new SolidBorder(DeviceGray.GRAY, 1)));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("Sep 2025"))
                .setBorderBottom(new SolidBorder(DeviceGray.GRAY, 1))
                .setBackgroundColor(new DeviceRgb(243, 244, 246)));

        String unit = "";
        if(WATER.equals(energyType)) unit = WATER_UNIT;
        else if(GAS.equals(energyType)) unit = GAS_UNIT;
        else if(ELECTRIC.equals(energyType)) unit = ELECTRIC_UNIT;
        else if(PV_PRODUCTION.equals(energyType)) unit = PV_PRODUCTION_UNIT;

        //Total Used
        statisticalTable.addCell(createCommonCell().add(new Paragraph("Total " + unit + " Used")));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("279,548")));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("327,629")));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("269,543"))
                .setBackgroundColor(new DeviceRgb(243, 244, 246)));

        //Daily Avergage
        statisticalTable.addCell(createCommonCell().add(new Paragraph("Daily Average " + "(" + unit + ")")));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("9,017.7 ")));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("10,568.7")));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("8,984.8"))
                .setBackgroundColor(new DeviceRgb(243, 244, 246)));

        //Days in Cycle
        statisticalTable.addCell(createCommonCell().add(new Paragraph("Days in Cycle")));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("31")));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("31")));
        statisticalTable.addCell(createCommonCell().add(new Paragraph("30"))
                .setBackgroundColor(new DeviceRgb(243, 244, 246)));

        history.add(statisticalTable);

        return history;
    }

    /**
     * @description create billing summary
     * @author Minh.Le
     * @since 2025-09-28
     * @param energyType
     * @return
     */
    public Div createBillingSummary(String energyType) {
        Div billingSummary = createCommonDivContainer();

        billingSummary.add(new Paragraph("Billing Summary")
                .setBold()
                .setMarginBottom(10));

        Table billingSummaryBody = createCommonTableContainer(new float[] {1,1});

        Cell billingSummaryName = createCommonCell();
        billingSummaryName.add(new Paragraph("Usage this period:"));
        billingSummaryName.add(new Paragraph("Average per day:"));
        billingSummaryName.add(new Paragraph("Days in period:"));

        billingSummaryBody.addCell(billingSummaryName);

        Cell billingSummaryData = createCommonCell().setTextAlignment(TextAlignment.RIGHT);
        String line1 = "278,819";
        String line2 = "9,294";

        if(WATER.equals(energyType)) {
            billingSummaryData.add(new Paragraph(line1 + " " + WATER_UNIT));
            billingSummaryData.add(new Paragraph(line2 + " " + WATER_UNIT + "/day"));
            billingSummaryData.add(new Paragraph("30"));
        } else if(GAS.equals(energyType)) {
            billingSummaryData.add(new Paragraph(line1 + " " + GAS_UNIT));
            billingSummaryData.add(new Paragraph(line2 + " " + GAS_UNIT + "/day"));
            billingSummaryData.add(new Paragraph("30"));
        } else {
            billingSummaryData.add(new Paragraph(line1 + " " + ELECTRIC_UNIT));
            billingSummaryData.add(new Paragraph(line2 + " " + ELECTRIC_UNIT + "/day"));
            billingSummaryData.add(new Paragraph("30"));
        }

        billingSummaryBody.addCell(billingSummaryData);

        billingSummary.add(billingSummaryBody);

        return billingSummary;
    }

    /**
     * @description create daily totals
     * @author Minh.Le
     * @since 2025-09-28
     * @param energyType
     * @return
     */
    public Div createDailyTotals(String energyType) {
        return createCommonDivContainer().add(drawUsageReportChart(REPORT_DAILY_TOTALS, energyType));
    }

    /**
     * @description create completed image meter with text
     * @author Minh.Le
     * @since 2025-09-28
     * @since 2025-09-28
     * @param energyType
     * @return
     */
    public Div createItemMeter(String energyType)  {
        Div itemMeter = createCommonDivContainer().setBorder(Border.NO_BORDER);

        //Meter image
        Image meterImage = drawItemMeterWithText(energyType);

        meterImage.scaleToFit(260,260).setHorizontalAlignment(HorizontalAlignment.CENTER);

        itemMeter.add(meterImage);

        return itemMeter;
    }

    /**
     * @description draw usage report chart
     * @author Minh.Le
     * @since 2025-09-28
     * @param sectionName
     * @param energyType
     * @return Image
     */
    public Image drawUsageReportChart(String sectionName, String energyType) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        double[] expectedValues;
        double[] actualValues = new double[0];
        if (REPORT_USAGE_HISTORY.equals(sectionName)) {
            String[] months = {
                    "Jan 2025", "Feb 2025", "Mar 2025", "Apr 2025",
                    "May 2025", "Jun 2025", "Jul 2025", "Aug 2025",
                    "Sep 2025", "Oct 2025", "Nov 2025", "Dec 2025"
            };

            expectedValues = new double[]{
                    230000, 220000, 240000, 210000,
                    250000, 260000, 240000, 230000,
                    220000, 210000, 250000, 260000
            };

            actualValues = new double[]{
                    270000, 230000, 350000, 280000,
                    270000, 310000, 330000, 250000,
                    240000, 230000, 280000, 290000
            };

            for (int i = 0; i < months.length; i++) {
                double actual = actualValues[i];
                double expected = expectedValues[i];

                dataset.addValue(actual, "Actual", months[i]);

                double diff = Math.max(expected - actual, 0);
                dataset.addValue(diff, "Expected", months[i]);
            }
        }
        else if (REPORT_DAILY_TOTALS.equals(sectionName)) {
            LocalDate startDate = LocalDate.of(2025, 9, 1);
            expectedValues = new double[30];
            actualValues = new double[30];
            Random random = new Random();

            for (int i = 0; i < 30; i++) {
                expectedValues[i] = 15000 + random.nextInt(10000);
                actualValues[i] = 20000 + random.nextInt(12000);

                LocalDate date = startDate.plusDays(i);
                String dayLabel = date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));

                double actual = actualValues[i];
                double expected = expectedValues[i];
                double diff = Math.max(expected - actual, 0);

                dataset.addValue(actual, "Actual", dayLabel);
                dataset.addValue(diff, "Expected", dayLabel);
            }

        }

        JFreeChart chart = ChartFactory.createBarChart(
                "", "", "",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false, false
        );

        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.TOP);

        LegendItemCollection legendItems = new LegendItemCollection();

        Color actualLegendColor = COLOR_EXPECTED_CHART;
        switch(energyType) {
            case WATER:
                actualLegendColor = COLOR_WATER_CHART;
                break;
            case GAS:
                actualLegendColor = COLOR_GAS_CHART;
                break;
            case ELECTRIC:
                actualLegendColor = COLOR_ELECTRIC_CHART;
                break;
            case PV_PRODUCTION:
                actualLegendColor = COLOR_PV_PRODUCTION_CHART;
                break;
        }

        legendItems.add(new LegendItem(
                "Actual",
                "Actual",
                null, null,
                new Ellipse2D.Double(-5, -5, 10, 10),
                actualLegendColor
        ));

        legendItems.add(new LegendItem(
                "Expected",
                "Expected",
                null, null,
                new Ellipse2D.Double(-5, -5, 10, 10),
                COLOR_EXPECTED_CHART
        ));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlineStroke(new BasicStroke(1f));
        plot.setDomainGridlinesVisible(false);
        plot.setFixedLegendItems(legendItems);

        // Axis X
        if(REPORT_USAGE_HISTORY.equals(sectionName)) {
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            domainAxis.setTickMarksVisible(false);
        }
        else if(REPORT_DAILY_TOTALS.equals(sectionName)) {
            final int labelInterval = 4;

            CategoryAxis customAxis = new CategoryAxis() {
                @Override
                @SuppressWarnings("unchecked")
                public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {
                    List<CategoryTick> ticks = super.refreshTicks(g2, state, dataArea, edge);
                    List<CategoryTick> newTicks = new ArrayList<>(ticks.size());

                    for (int i = 0; i < ticks.size(); i++) {
                        CategoryTick tick = ticks.get(i);

                        if (i % labelInterval == 0) {
                            newTicks.add(tick);
                        } else {
                            TextBlock emptyBlock = new TextBlock();
                            CategoryTick blankTick = new CategoryTick(
                                    tick.getCategory(),
                                    emptyBlock,
                                    tick.getLabelAnchor(),
                                    tick.getRotationAnchor(),
                                    tick.getAngle()
                            );
                            newTicks.add(blankTick);
                        }
                    }
                    return newTicks;
                }
            };

            customAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            customAxis.setTickMarksVisible(false);

            plot.setDomainAxis(customAxis);
        }

        // Axis Y
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setTickMarksVisible(false);
        rangeAxis.setAxisLineVisible(false);

        if(REPORT_USAGE_HISTORY.equals(sectionName)) {
            double max = Arrays.stream(actualValues).max().getAsDouble();
            double upper = Math.ceil(max / 100000.0) * 100000;
            rangeAxis.setRange(0, upper);
            rangeAxis.setTickUnit(new NumberTickUnit(100000));
        }

        //Format axis Y unit
        rangeAxis.setNumberFormatOverride(new NumberFormat() {
            @Override
            public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
                if (number == 0) {
                    return toAppendTo.append("0");
                }
                return toAppendTo.append((int)(number / 1000)).append("k");
            }

            @Override
            public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
                if (number == 0) {
                    return toAppendTo.append("0");
                }
                return toAppendTo.append((int)(number / 1000)).append("k");
            }

            @Override
            public Number parse(String source, ParsePosition parsePosition) {
                return null;
            }
        });

        //Format column colors
        StackedBarRenderer renderer = new StackedBarRenderer() {
            @Override
            public Paint getItemPaint(int row, int column) {
                if (row == 0) {
                    Color bottom = new Color(250, 250, 250);
                    Color top = new Color(250, 250, 250);
                    switch (energyType) {
                        case WATER:
                            top = COLOR_WATER_CHART;
                            break;
                        case GAS:
                            top = COLOR_GAS_CHART;
                            break;
                        case ELECTRIC:
                            top = COLOR_ELECTRIC_CHART;
                            break;
                        case PV_PRODUCTION:
                            top = COLOR_PV_PRODUCTION_CHART;
                            break;
                    }

                    return new GradientPaint(0f, 0f, top, 0f, 1f, bottom);
                } else {
                    return new Color(211, 211, 211);
                }
            }
        };

        renderer.setBarPainter(new StandardBarPainter());
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        renderer.setMaximumBarWidth(0.08);
        renderer.setGradientPaintTransformer(
                new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL)
        );
        switch (energyType) {
            case WATER:
                renderer.setSeriesPaint(0, COLOR_WATER_CHART);
                break;
            case GAS:
                renderer.setSeriesPaint(0, COLOR_GAS_CHART);
                break;
            case ELECTRIC:
                renderer.setSeriesPaint(0, COLOR_ELECTRIC_CHART);
                break;
            case PV_PRODUCTION:
                renderer.setSeriesPaint(0, COLOR_PV_PRODUCTION_CHART);
                break;
        }

        renderer.setSeriesPaint(1, COLOR_EXPECTED_CHART);
        plot.setRenderer(renderer);


        //Export image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if(REPORT_USAGE_HISTORY.equals(sectionName))
                ChartUtils.writeChartAsPNG(baos, chart, 400, 250);
            else
                ChartUtils.writeChartAsPNG(baos, chart, 300, 250);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Image(ImageDataFactory.create(baos.toByteArray()));
    }

    /**
     * @description create div
     * @author Minh.Le
     * @since 2025-09-28
     * @return Div
     */
    public Div createCommonDivContainer() {
        return new Div()
                .setBorder(new SolidBorder(ColorConstants.GRAY, 1))
                .setBorderRadius(new BorderRadius(BORDER_RADIUS))
                .setPadding(10);
    }

    /**
     * @description create table
     * @author Minh.Le
     * @since 2025-09-28
     * @return Table
     */
    public Table createCommonTableContainer(float[] ratio) {
        return new Table(UnitValue.createPercentArray(ratio))
                .useAllAvailableWidth()
                .setBorder(Border.NO_BORDER);
    }

    /**
     * @description create cell
     * @author Minh.Le
     * @since 2025-09-28
     * @return Cell
     */
    public Cell createCommonCell() {
        return new Cell().setBorder(Border.NO_BORDER);
    }

    /**
     * @descriptrion draw text into image
     * @author Minh.Le
     * @since 2025-09-28
     * @param energyType
     * @return
     */
    public Image drawItemMeterWithText(String energyType) {
        String meterUrl = "";
        switch (energyType) {
            case WATER:
                meterUrl = REPORT_WATER_ITEM_METER_IMG_URL;
                break;
            case GAS:
                meterUrl = REPORT_GAS_ITEM_METER_IMG_URL;
                break;
            case ELECTRIC:
                meterUrl = REPORT_ELECTRIC_ITEM_METER_IMG_URL;
                break;
            case PV_PRODUCTION:
                meterUrl = REPORT_PV_PRODUCTION_ITEM_METER_IMG_URL;
                break;
        }

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new URL(meterUrl));
        } catch (IOException e) {
            System.out.println("Meter URL not true");
            throw new RuntimeException(e);
        }

        Graphics2D g2d = bufferedImage.createGraphics();

        switch(energyType) {
            case WATER:
                drawTextWithBackground(g2d, "267,066",194, 150, new Font("Arial", Font.PLAIN, 35), Color.BLACK, Color.WHITE);
                break;

            case GAS:
                String text = "4,102";
                Font font = new Font("Arial", Font.PLAIN, 35);
                g2d.setFont(font);
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(text);

                int textCenterX = 196;

                int textX = textCenterX - textWidth / 2;
                int textY = 148;

                g2d.setColor(Color.WHITE);
                g2d.drawString(text, textX, textY);
                break;
            case ELECTRIC:
                drawTextWithBackground(g2d, "408,883.5", 173, 140, new Font("Arial", Font.PLAIN, 35), Color.BLACK, Color.WHITE);
                break;

            case PV_PRODUCTION:
                drawTextWithBackground(g2d, "92,311.5", 170, 145, new Font("Arial", Font.PLAIN, 35), Color.BLACK, Color.WHITE);
                break;
        }

        g2d.dispose();

        return writeDataFromBufferToImage(bufferedImage);
    }

    /**
     * @descriptrion draw text with background
     * @author Minh.Le
     * @since 2025-09-28
     * @param g2d
     * @param text
     * @param centerX
     * @param y
     * @param font
     * @param textColor
     * @param bgColor
     */
    private void drawTextWithBackground(Graphics2D g2d, String text, int centerX, int y, Font font, Color textColor, Color bgColor) {
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int paddingX = 20, paddingY = 20;
        int textWidth = fm.stringWidth(text);

        int ascent = fm.getAscent();
        int descent = fm.getDescent();

        int x = centerX - textWidth / 2;

        int rectX = centerX - paddingX / 2 - textWidth / 2;
        int rectY = y - ascent - paddingY / 2;
        int rectWidth = textWidth + paddingX;
        int rectHeight = ascent + descent + paddingY;

        g2d.setColor(bgColor);
        g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 10, 10);

        g2d.setColor(textColor);
        g2d.drawString(text, x, y);
    }

    /**
     * @description create bullet image for legend performance insights chart
     * @author Minh.Le
     * @since 2025-09-28
     * @param diameter
     * @param color
     * @return Image
     */
    public Image createBulletImage(int diameter, DeviceRgb color) {
        BufferedImage bufferedImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(color.getColorValue()[0], color.getColorValue()[1], color.getColorValue()[2]));
        g2d.fillOval(0, 0, diameter, diameter);

        g2d.dispose();

        return writeDataFromBufferToImage(bufferedImage);
    }

    /**
     * @description create image from svg link
     * @author Minh.Le
     * @since 2025-09-28
     * @param imgLink, pdfDocument
     * @return Image
     */
    public Image imageFromSvgHandler(String imgLink, PdfDocument pdfDocument) {
        try(InputStream svgStream = new URL(imgLink).openStream()) {
            PdfFormXObject svgObject = SvgConverter.convertToXObject(svgStream, pdfDocument);
            return new Image(svgObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @description create image from png link
     * @author Minh.Le
     * @since 2025-09-28
     * @param imgLink
     * @return Image
     */
    public Image imageFromPngHandler(String imgLink) {
        try {
            return new Image(ImageDataFactory.create(imgLink));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @description create image from buffer
     * @author Minh.Le
     * @since 2025-09-28
     * @param bufferedImage
     * @return Image
     */
    public Image writeDataFromBufferToImage(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            System.out.println("Write data to image failed");
            throw new RuntimeException(e);
        }
        return new Image(ImageDataFactory.create(baos.toByteArray()));
    }
}
