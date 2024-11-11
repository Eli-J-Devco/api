/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DateTimeReportDataEntity;
import com.nwm.api.entities.MonthlyProductionTrendReportEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeeklyDateEntity;

public class BuiltInReportService extends DB {
	
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
			DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
			ChronoUnit timeUnit = ChronoUnit.DAYS;
		
			switch (obj.getCadence_range()) {
				case 2: // monthly
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
				case 4: // annual
					categoryTimeFormat = DateTimeFormatter.ofPattern("MMM-yy");
					timeUnit = ChronoUnit.MONTHS;
					break;
				case 6: // weekly
					categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyy");
					timeUnit = ChronoUnit.DAYS;
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
							ViewReportEntity data = null;
							if (siteObj.getCadence_range() == 2) data = (ViewReportEntity) this.getMonthlyTrendBuitInReport(siteObj);
							else if (siteObj.getCadence_range() == 4) data = (ViewReportEntity) this.getAnnuallyBuitInReport(siteObj);
							else if (siteObj.getCadence_range() == 6) data = (ViewReportEntity) this.getWeeklyBuiltInReport(siteObj);
							
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
			
			if (findReport.getCadence_range() == 4 || findReport.getCadence_range() == 6) {
				for (int i = 0; i < summaryData.size(); i++) {
					WeeklyDateEntity sum = (WeeklyDateEntity) summaryData.get(i);
					
					for (int j = 0; j < dataList.size(); j++) {
						List<WeeklyDateEntity> dataReports = dataList.get(j).getDataReports();
						if (dataReports == null || dataReports.size() == 0) continue;
						WeeklyDateEntity item = dataReports.get(i);
						
						if (item != null && sum.getCategories_time().equals(item.getCategories_time())) {
							if (item.getActualGeneration() != null) sum.setActualGeneration((sum.getActualGeneration() != null ? sum.getActualGeneration() : 0) + item.getActualGeneration());
							if (item.getExpectedGeneration() != null) sum.setExpectedGeneration((sum.getExpectedGeneration() != null ? sum.getExpectedGeneration() : 0) + item.getExpectedGeneration());
							if (item.getModeledGeneration() != null) sum.setModeledGeneration((sum.getModeledGeneration() != null ? sum.getModeledGeneration() : 0) + item.getModeledGeneration());
							if (item.getPoa() != null) sum.setPoa((sum.getPoa() != null ? sum.getPoa() : 0) + item.getPoa());
							if (item.getExpectedGenerationIndex() != null) sum.setExpectedGenerationIndex((sum.getExpectedGenerationIndex() != null ? sum.getExpectedGenerationIndex() : 0) + item.getExpectedGenerationIndex());
							if (item.getModeledGenerationIndex() != null) sum.setModeledGenerationIndex((sum.getModeledGenerationIndex() != null ? sum.getModeledGenerationIndex() : 0) + item.getModeledGenerationIndex());
						}
					}
					
					if (sum.getPoa() != null) sum.setPoa(sum.getPoa() / dataList.size());
					if (sum.getExpectedGenerationIndex() != null) sum.setExpectedGenerationIndex(sum.getExpectedGenerationIndex() / dataList.size());
					if (sum.getModeledGenerationIndex() != null) sum.setModeledGenerationIndex(sum.getModeledGenerationIndex() / dataList.size());
				}
			} else if (findReport.getCadence_range() == 2 && findReport.getData_intervals() == 6) {
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
	
	public Object getAnnuallyBuitInReport(ViewReportEntity obj) {
		try {
			List<WeeklyDateEntity> data = queryForList("BuiltInReport.getDataAnnualTrendReport", obj);
			obj.setDataReports(fulfillData(getDateTimeList(obj, WeeklyDateEntity.class), data));
			
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
	
	public Object getMonthlyTrendBuitInReport(ViewReportEntity obj) {
		try {
			List powerDeviceList = obj.isHave_meter() ? queryForList("BuiltInReport.getListDeviceTypeMeter", obj) : queryForList("BuiltInReport.getListDeviceTypeInverter", obj);
			if (powerDeviceList.size() > 0) {
				obj.setGroupDevices(powerDeviceList);
				List<MonthlyProductionTrendReportEntity> data = queryForList("BuiltInReport.getMonthlyTrendBuitInReport", obj);
				obj.setDataReports(fulfillData(getDateTimeList(obj, MonthlyProductionTrendReportEntity.class), data));
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
			obj.setDataReports(fulfillData(getDateTimeList(obj, WeeklyDateEntity.class), data));
			
			return obj;
		} catch (Exception ex) {
			return null;
		}
	}
	
}
