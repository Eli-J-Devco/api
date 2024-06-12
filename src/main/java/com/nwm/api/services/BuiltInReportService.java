/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.MonthlyProductionTrendReportEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeeklyDateEntity;

public class BuiltInReportService extends DB {
	
	/**
	 * @description get list site in report 
	 * @author long.pham
	 * @since 2023-08-25
	 */
	
	public List getListSiteInReport(ViewReportEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BuiltInReport.getListSiteInReport", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get table data virtual
	 * @author Hung.Bui
	 * @since 2023-11-21
	 */
	
	public String getTableDataVirtual(ViewReportEntity obj) {
		String data = new String();
		try {
			data = queryForObject("BuiltInReport.getTableDataVirtual", obj).toString();
			return data;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description get annually production trend report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public Object getAnnuallyBuitInReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			// Create list date
			SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			Date startDate = startDateFormat.parse(obj.getStart_date());
			Calendar cal = Calendar.getInstance();
			
			List<WeeklyDateEntity> categories = new ArrayList<WeeklyDateEntity> ();
			SimpleDateFormat dateFormat; 
			SimpleDateFormat catFormat;
			int forCount;
			int calField;
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime startLocalDateTime = LocalDateTime.parse(obj.getStart_date(), dateTimeFormatter);
			LocalDateTime endLocalDateTime = LocalDateTime.parse(obj.getEnd_date(), dateTimeFormatter);
			
			switch (obj.getData_intervals()) {
//				case 3:
//					forCount = (int) (24 * (ChronoUnit.DAYS.between(startLocalDateTime, endLocalDateTime) + 1));
//					calField = Calendar.HOUR_OF_DAY;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy HH:00");
//					break;
//					
//				case 4:
//					forCount = (int) (ChronoUnit.DAYS.between(startLocalDateTime, endLocalDateTime) + 1);
//					calField = Calendar.DAY_OF_YEAR;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy");
//					break;
//					
//				case 5:
//					forCount = (int) (ChronoUnit.WEEKS.between(startLocalDateTime, endLocalDateTime) + 1);
//					calField = Calendar.WEEK_OF_YEAR;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy");
//					break;
					
				case 6:
				default:
					forCount = (int) (ChronoUnit.MONTHS.between(startLocalDateTime, endLocalDateTime) + 1);
					calField = Calendar.MONTH;
					dateFormat = new SimpleDateFormat("MMM-yy");
					catFormat = new SimpleDateFormat("MMM-yy");
					break;
			}
			
			for(int t = 0; t < forCount; t++) {
				cal.setTime(startDate);
				WeeklyDateEntity headerDate = new WeeklyDateEntity();
				cal.add(calField, t);
				headerDate.setTime_format(dateFormat.format(cal.getTime()));
				headerDate.setCategories_time(catFormat.format(cal.getTime()));
				headerDate.setActualGeneration(0.0);
				headerDate.setExpectedGeneration(0.0);
				headerDate.setModeledGeneration(0.0);
				headerDate.setPoa(0.0);
				headerDate.setExpectedGenerationIndex(0.0);
				headerDate.setModeledGenerationIndex(0.0);
				categories.add(headerDate);
			}
			
			List data = queryForList("BuiltInReport.getDataAnnualTrendReport", obj);
			List<WeeklyDateEntity> dataNew = new ArrayList<WeeklyDateEntity>();
			
			if (categories.size() > 0) {
				ViewReportEntity site = (ViewReportEntity) queryForObject("BuiltInReport.getSiteDetail", obj);
				
				for (WeeklyDateEntity item : categories) {
					boolean flag = false;
					WeeklyDateEntity mapItem = new WeeklyDateEntity();
					
					if (data != null && data.size() > 0) {
						for(int v = 0; v < data.size(); v++) {
							Map<String, Object> itemT = (Map<String, Object>) data.get(v);
							String categoriesTime = item.getTime_format();
							String powerTime = itemT.get("time_format").toString();
							
							if (categoriesTime.equals(powerTime)) {
								flag = true;
								mapItem.setTime_format(itemT.get("time_format").toString());
								mapItem.setCategories_time(itemT.get("categories_time").toString());
								mapItem.setSiteName(itemT.get("name").toString());
								mapItem.setActualGeneration(Double.parseDouble(itemT.get("ActualGeneration").toString()));
								mapItem.setExpectedGeneration(Double.parseDouble(itemT.get("ExpectedGeneration").toString()));
								mapItem.setModeledGeneration(Double.parseDouble(itemT.get("ModeledGeneration").toString()));
								mapItem.setPoa(Double.parseDouble(itemT.get("POA").toString()));
								mapItem.setExpectedGenerationIndex(Double.parseDouble(itemT.get("ExpectedGenerationIndex").toString()));
								mapItem.setModeledGenerationIndex(Double.parseDouble(itemT.get("ModeledGenerationIndex").toString()));
								break;
							}
						}
					}
					
					if(flag == false) {
						mapItem.setTime_format(item.getTime_format());
						mapItem.setCategories_time(item.getCategories_time());
						mapItem.setSiteName(site.getSite_name());
						mapItem.setActualGeneration(item.getActualGeneration());
						mapItem.setExpectedGeneration(item.getExpectedGeneration());
						mapItem.setModeledGeneration(item.getModeledGeneration());
						mapItem.setPoa(item.getPoa());
						mapItem.setExpectedGenerationIndex(item.getExpectedGenerationIndex());
						mapItem.setModeledGenerationIndex(item.getModeledGenerationIndex());
					}
					
					dataNew.add(mapItem);
				}
			}
			
			dataObj.setDataReports(dataNew);
			
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
	
	public Object getMonthlyTrendBuitInReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			// Create list date
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat catFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			Date startDate = dateFormat.parse(obj.getStart_date());
			Calendar cal = Calendar.getInstance();
			
			List<MonthlyProductionTrendReportEntity> categories = new ArrayList<MonthlyProductionTrendReportEntity>();
			int forCount;
			int calAmount;
			cal.setTime(startDate);
			int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			switch (obj.getData_intervals()) {
//				case 1:
//					forCount = daysInMonth * 24 * 12;
//					calAmount = 5;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:00");
//					break;
					
				case 2:
					forCount = daysInMonth * 24 * 4;
					calAmount = 15;
					dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					catFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:00");
					break;
					
//				case 3:
//					forCount = daysInMonth * 24;
//					calAmount = 60;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy HH:00");
//					break;
//					
//				case 4:
//					forCount = daysInMonth;
//					calAmount = 1440;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy");
//					break;
//					
//				case 5:
//					forCount = (int) Math.ceil(daysInMonth / 7);
//					calAmount = 10080;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy");
//					break;
				
				case 6:
				default:
					forCount = 1;
					calAmount = daysInMonth * 1440;
					dateFormat = new SimpleDateFormat("MMM-yy");
					catFormat = new SimpleDateFormat("MMM-yy");
					break;
			}
			
			for(int t = 0; t < forCount; t++) {
				cal.setTime(startDate);
				MonthlyProductionTrendReportEntity headerDate = new MonthlyProductionTrendReportEntity();
				cal.add(Calendar.MINUTE, t * calAmount);
				headerDate.setTime_format(dateFormat.format(cal.getTime()));
				headerDate.setTime_full(catFormat.format(cal.getTime()));
				headerDate.setMonthlyProduction(0.0);
				categories.add(headerDate);
			}
			
			List dataListDeviceMeter = queryForList("BuiltInReport.getListDeviceTypeMeter", obj);
			
			if(dataListDeviceMeter.size() > 0) {
				obj.setGroupDevices(dataListDeviceMeter);
			} else {
				List dataListInverter = queryForList("BuiltInReport.getListDeviceTypeInverter", obj);
				
				if(dataListInverter.size() > 0) {
					obj.setGroupDevices(dataListInverter);
				}
			}
			
			if (obj.getGroupDevices().size() > 0) {
				List<MonthlyProductionTrendReportEntity> data = queryForList("BuiltInReport.getMonthlyTrendBuitInReport", obj);
				List<MonthlyProductionTrendReportEntity> dataNew = new ArrayList<MonthlyProductionTrendReportEntity>();
				
				if (categories.size() > 0) {
					ViewReportEntity site = (ViewReportEntity) queryForObject("BuiltInReport.getSiteDetail", obj);
					
					for (MonthlyProductionTrendReportEntity item : categories) {
						boolean flag = false;
						MonthlyProductionTrendReportEntity mapItem = new MonthlyProductionTrendReportEntity();
						
						if (data != null && data.size() > 0) {
							for(int v = 0; v < data.size(); v++) {
								MonthlyProductionTrendReportEntity itemT = (MonthlyProductionTrendReportEntity) data.get(v);
								String categoriesTime = item.getTime_format();
								String powerTime = itemT.getTime_format();
								
								if (categoriesTime.equals(powerTime)) {
									flag = true;
									mapItem.setTime_format(itemT.getTime_format());
									mapItem.setTime_full(itemT.getTime_full());
									mapItem.setMonthlyProduction(itemT.getMonthlyProduction());
									dataObj.setSite_name(itemT.getSite_name());
									break;
								}
							}
						}
						
						if(flag == false) {
							mapItem.setTime_format(item.getTime_format());
							mapItem.setTime_full(item.getTime_full());
							mapItem.setMonthlyProduction(item.getMonthlyProduction());
							dataObj.setSite_name(site.getSite_name());
						}
						
						dataNew.add(mapItem);
					}
				}
				
				dataObj.setDataReports(dataNew);
			}
			
			return dataObj;
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
	
	public Object getWeeklyBuiltInReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			// Create list date
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat catFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			Date startDate = dateFormat.parse(obj.getStart_date());
			Calendar cal = Calendar.getInstance();
			
			List<WeeklyDateEntity> categories = new ArrayList<WeeklyDateEntity> ();
			int forCount = 7;
			int calAmount = 1440;
			
			switch (obj.getData_intervals()) {
//				case 1:
//					forCount = 7 * 24 * 12;
//					calAmount = 5;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//					break;
//					
//				case 2:
//					forCount = 7 * 24 * 4;
//					calAmount = 15;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//					break;
//					
//				case 3:
//					forCount = 7 * 24;
//					calAmount = 60;
//					dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00");
//					catFormat = new SimpleDateFormat("MM/dd/yyyy HH:00");
//					break;
					
				case 4:
				default:
					forCount = 7;
					calAmount = 1440;
					dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					catFormat = new SimpleDateFormat("MM/dd/yyyy");
					break;
			}
			
			for(int t = 0; t < forCount; t++) {
				cal.setTime(startDate);
				WeeklyDateEntity headerDate = new WeeklyDateEntity();
				cal.add(Calendar.MINUTE, t * calAmount);
				headerDate.setTime_format(dateFormat.format(cal.getTime()));
				headerDate.setCategories_time(catFormat.format(cal.getTime()));
				headerDate.setActualGeneration(0.0);
				headerDate.setExpectedGeneration(0.0);
				headerDate.setModeledGeneration(0.0);
				headerDate.setPoa(0.0);
				headerDate.setExpectedGenerationIndex(0.0);
				headerDate.setModeledGenerationIndex(0.0);
				categories.add(headerDate);
			}
			
			List data = queryForList("BuiltInReport.getDataWeeklyTrendReport", obj);
			List<WeeklyDateEntity> dataNew = new ArrayList<WeeklyDateEntity>();
			
			if (categories.size() > 0) {
				ViewReportEntity site = (ViewReportEntity) queryForObject("BuiltInReport.getSiteDetail", obj);
				
				for (WeeklyDateEntity item : categories) {
					boolean flag = false;
					WeeklyDateEntity mapItem = new WeeklyDateEntity();
					
					if (data != null && data.size() > 0) {
						for(int v = 0; v < data.size(); v++) {
							Map<String, Object> itemT = (Map<String, Object>) data.get(v);
							String categoriesTime = item.getTime_format();
							String powerTime = itemT.get("time_format").toString();
							
							if (categoriesTime.equals(powerTime)) {
								flag = true;
								mapItem.setTime_format(itemT.get("time_format").toString());
								mapItem.setCategories_time(itemT.get("categories_time").toString());
								mapItem.setSiteName(itemT.get("name").toString());
								mapItem.setActualGeneration(Double.parseDouble(itemT.get("ActualGeneration").toString()));
								mapItem.setExpectedGeneration(Double.parseDouble(itemT.get("ExpectedGeneration").toString()));
								mapItem.setModeledGeneration(Double.parseDouble(itemT.get("ModeledGeneration").toString()));
								mapItem.setPoa(Double.parseDouble(itemT.get("POA").toString()));
								mapItem.setExpectedGenerationIndex(Double.parseDouble(itemT.get("ExpectedGenerationIndex").toString()));
								mapItem.setModeledGenerationIndex(Double.parseDouble(itemT.get("ModeledGenerationIndex").toString()));
								break;
							}
						}
					}
					
					if(flag == false) {
						mapItem.setTime_format(item.getTime_format());
						mapItem.setCategories_time(item.getCategories_time());
						mapItem.setSiteName(site.getSite_name());
						mapItem.setActualGeneration(item.getActualGeneration());
						mapItem.setExpectedGeneration(item.getExpectedGeneration());
						mapItem.setModeledGeneration(item.getModeledGeneration());
						mapItem.setPoa(item.getPoa());
						mapItem.setExpectedGenerationIndex(item.getExpectedGenerationIndex());
						mapItem.setModeledGenerationIndex(item.getModeledGenerationIndex());
					}
					
					dataNew.add(mapItem);
				}
			}
			
			dataObj.setDataReports(dataNew);
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
}
