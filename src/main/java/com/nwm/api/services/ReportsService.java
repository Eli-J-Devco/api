/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AssetManagementAndOperationPerformanceReportEntity;
import com.nwm.api.entities.CustomReportDataEntity;
import com.nwm.api.entities.DailyDateEntity;
import com.nwm.api.entities.DateTimeReportDataEntity;
import com.nwm.api.entities.MonthlyDateEntity;
import com.nwm.api.entities.AssetManagementAndOperationPerformanceDataEntity;
import com.nwm.api.entities.QuarterlyDateEntity;
import com.nwm.api.entities.ReportsEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.utils.Constants;

public class ReportsService extends DB {
	
	/**
	 * @description fulfill data in specific range of time
	 * @author Hung.Bui
	 * @since 2024-05-03
	 * @param dateTimeList
	 * @param dataList
	 * @return
	 */
	private <K extends DateTimeReportDataEntity> List<K> fulfillData(List<K> dateTimeList, List<K> dataList) {
		List<K> fulfilledDataList = new ArrayList<K>();
		
		try {
			if(dataList != null && dateTimeList.size() > 0) {
				int count = 0;
				for (int i = 0; i < dateTimeList.size(); i++) {
					K dateTimeItem = dateTimeList.get(i);
					if (i - count > dataList.size() - 1) {
						fulfilledDataList.add(dateTimeItem);
						continue;
					}
					K dataItem = dataList.get(i - count);
					if (dateTimeItem.getCategories_time().equals(dataItem.getCategories_time())) {
						fulfilledDataList.add(dataItem);
					} else {
						fulfilledDataList.add(dateTimeItem);
						count++;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return fulfilledDataList;
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
		
			switch (obj.getCadence_range()) {
				case 1: // daily
					categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy HH:mm");
					switch (obj.getData_intervals()) {
						case 1:
							interval = 5;
							timeUnit = ChronoUnit.MINUTES;
							break;
						case 2:
							interval = 15;
							timeUnit = ChronoUnit.MINUTES;
							break;
						case 3:
							interval = 1;
							timeUnit = ChronoUnit.HOURS;
							break;
					}
					break;
				case 2: // monthly
					categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
					timeUnit = ChronoUnit.DAYS;
					break;
				case 3: // quarterly
					switch (obj.getData_intervals()) {
						case 4:
							categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
							timeUnit = ChronoUnit.DAYS;
							break;
						case 6:
							categoryTimeFormat = DateTimeFormatter.ofPattern("MMM-yyyy");
							timeUnit = ChronoUnit.MONTHS;
							break;
					}
					break;
				case 4: // annually
					categoryTimeFormat = DateTimeFormatter.ofPattern("MMM");
					timeUnit = ChronoUnit.MONTHS;
					break;
				case 5: // custom
	                switch (obj.getData_intervals()) {
	                	case 4:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
	                		timeUnit = ChronoUnit.DAYS;
	                		break;
	                	case 6:
	                		end = end.with(TemporalAdjusters.lastDayOfMonth());
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/yyy");
	                		timeUnit = ChronoUnit.MONTHS;
	                		break;
	                	case 7:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("yyyy");
	                		timeUnit = ChronoUnit.YEARS;
	                		break;
	                }
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
					
					CompletableFuture<ViewReportEntity> future = CompletableFuture.supplyAsync(() -> {
						try {
							ViewReportEntity data = null;
							if (reportObj.getCadence_range() == 1) data = (ViewReportEntity) this.getDailyReport(siteObj);
							else if (reportObj.getCadence_range() == 2) data = (ViewReportEntity) this.getMonthlyReport(siteObj);
							else if (reportObj.getCadence_range() == 3) data = (ViewReportEntity) this.getQuarterlyReport(siteObj);
							else if (reportObj.getCadence_range() == 4) data = (ViewReportEntity) this.getAnnuallyReport(siteObj);
							else if (reportObj.getCadence_range() == 5) data = (ViewReportEntity) this.getCustomReport(siteObj);
							
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
			List<ViewReportEntity> dataList =  combinedFutures.thenApply(__ -> list.stream().map(future -> future.join()).filter(item -> item != null).collect(Collectors.toList())).get();
			return reportObj.getCadence_range() == 5 ? this.dataSummarize(this.dataSort(dataList, reportObj)) : dataList;
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
	
	public ViewReportEntity getDailyReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null || dataObj.getId_site() == 0) return null;
			
			List<DailyDateEntity> dataPower = new ArrayList<DailyDateEntity>();
			List powerDeviceList = dataObj.isHave_meter() ? queryForList("Reports.getListDeviceTypeMeter", obj) : queryForList("Reports.getListDeviceTypeInverter", obj);
			if(powerDeviceList.size() > 0 ) {
				obj.setGroupDevices(powerDeviceList);
				dataPower = queryForList("Reports.getDataEnergyMeterDailyReport", obj);
			}
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setData_intervals(dataObj.getData_intervals());
			List<DailyDateEntity> dateTimeList = getDateTimeList(obj, DailyDateEntity.class);
			List<DailyDateEntity> fulfillData = fulfillData(dateTimeList, dataPower);
			
			// get irradiance 
			List dataListDeviceIrr = queryForList("Reports.getListDeviceTypeIrradiance", obj);
			if (dataListDeviceIrr.size() > 0) {
				obj.setGroupDevices(dataListDeviceIrr);
				List<DailyDateEntity> dataIrradiance = queryForList("Reports.getDataIrradiance", obj);
				List<DailyDateEntity> fulfillIrradiance = fulfillData(dateTimeList, dataIrradiance);
				if(fulfillIrradiance.size() > 0) {
					for (int i = 0; i < fulfillData.size(); i++) {
						DailyDateEntity item = (DailyDateEntity) fulfillData.get(i);
						item.setIrradiance(fulfillIrradiance.get(i).getIrradiance());
					}
				}
			}
			
			dataObj.setDataReports(fulfillData);
			dataObj.setHave_poa(dataListDeviceIrr.size() > 0);
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
	
	public Object getAnnuallyReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null || dataObj.getId_site() == 0) return null;
			
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setTable_data_report(dataObj.getTable_data_report());
			obj.setHave_meter(dataObj.isHave_meter());
			obj.setHave_inverter(dataObj.isHave_inverter());
			List<QuarterlyDateEntity> dataEnergy = queryForList("Reports.getDataEnergyAnnuallyReport", obj);
			dataObj.setDataReports(fulfillData(getDateTimeList(obj, QuarterlyDateEntity.class), dataEnergy));
			
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
	
	public Object getQuarterlyReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null || dataObj.getId_site() == 0) return null;
			
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setData_intervals(dataObj.getData_intervals());
			obj.setTable_data_report(dataObj.getTable_data_report());
			obj.setHave_meter(dataObj.isHave_meter());
			List<QuarterlyDateEntity> dataEnergy = dataObj.getData_intervals() == Constants.MONTHLY_INTERVAL ? queryForList("Reports.getDataEnergyQuarterlyReportByMonth", obj) : queryForList("Reports.getDataEnergyQuarterlyReportByDay", obj);
			dataObj.setDataReports(fulfillData(getDateTimeList(obj, QuarterlyDateEntity.class), dataEnergy));
			
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
			ViewReportEntity reportObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (reportObj == null) return null;
			
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
		try {
			List dataList = (List<Map<String, Object>>) queryForList("Reports.getListSiteByEmployee", obj);
			if (dataList == null) return new ArrayList();
			ObjectMapper mapper = new ObjectMapper();
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> item = (Map<String, Object>) dataList.get(i);
				
				try {
					item.put("options", mapper.readValue(item.get("sitesJSON").toString(), new TypeReference<List<Map<String, Object>>>(){}));
				} catch (JsonProcessingException e) {
					item.put("options", new ArrayList<Map<String, Object>>());
				}
				
				item.put("sitesJSON", null);
			}
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
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
			if (dataSite != null && dataSite.size() > 0) session.insert("Reports.insertReportSiteMap", obj);
			
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
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-06-12
	 * @param id_site
	 */
	
	public Object getMonthlyReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null || dataObj.getId_site() == 0) return null;
			
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setTable_data_report(dataObj.getTable_data_report());
			obj.setHave_meter(dataObj.isHave_meter());
			List<MonthlyDateEntity> dataEnergy = queryForList("Reports.getDataEnergyMonthlyReport", obj);
			dataObj.setDataReports(fulfillData(getDateTimeList(obj, MonthlyDateEntity.class), dataEnergy));
			
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
	
	public Object getCustomReport(ViewReportEntity obj) {
		try {
			ViewReportEntity dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null || dataObj.getId_site() == 0) return null;
			
			obj.setCadence_range(dataObj.getCadence_range());
			obj.setTable_data_report(dataObj.getTable_data_report());
			obj.setHave_meter(dataObj.isHave_meter());
			List<CustomReportDataEntity> dataPower = queryForList("Reports.getDataEnergyCustomReport", obj);
			List<CustomReportDataEntity> fulfillData = fulfillData(getDateTimeList(obj, CustomReportDataEntity.class), dataPower);
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
	
}
