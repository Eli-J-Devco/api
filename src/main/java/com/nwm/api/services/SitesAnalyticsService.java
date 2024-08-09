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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EmployeeFilterFavoritesEntity;
import com.nwm.api.entities.EmployeeFilterRecentlyEntity;
import com.nwm.api.entities.SitesAnalyticsReportEntity;


public class SitesAnalyticsService extends DB {

	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2022-02-22
	 * @param id_site
	 */
	public List getListDeviceBySite(DeviceEntity obj) {
		try {
			List dataListNew = new ArrayList();
			List<Map<String, Object>> dataList = queryForList("SitesAnalytics.getListDeviceBySite", obj);
			
			if(dataList.size() > 0) {
				Map<String, List> map = new HashMap<String, List>();
				map.put("list", dataList);
				List<Map<String, Object>> dataListParameter = queryForList("SitesAnalytics.getListDeviceParameter", map);
				dataList.forEach(item -> {
					item.put("dataParameter", dataListParameter.stream().filter(p -> p.get("id_device").equals(item.get("id"))).collect(Collectors.toList()));
					dataListNew.add(item);
				});
			}
			
			return dataListNew;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description fulfill data in specific range of time
	 * @author Hung.Bui
	 * @since 2024-05-03
	 * @param dateTimeList
	 * @param dataList
	 * @return
	 */
	private List<Map<String, Object>> fulfillData(List<Map<String, Object>> dateTimeList, List<Map<String, Object>> dataList) {
		List<Map<String, Object>> fulfilledDataList = new ArrayList<Map<String, Object>>();
		
		try {
			if(dataList.size() > 0 && dateTimeList.size() > 0) {
				boolean firstCategory = false;
				for (Map<String, Object> dateTime: dateTimeList) {
					boolean isFound = false;
					
					for(Map<String, Object> data: dataList) {
						String fullTime = dateTime.get("time_full").toString();
						String powerTime = data.get("time_full").toString();
						
						if (fullTime.equals(powerTime)) {
							fulfilledDataList.add(data);
							isFound = true;
							firstCategory = true;
							break;
						}
					}
					
					if (!isFound && firstCategory) fulfilledDataList.add(dateTime);
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
	private List<Map<String, Object>> getDateTimeList(DeviceEntity obj, LocalDateTime start, LocalDateTime end) {
		List<Map<String, Object>> dateTimeList = new ArrayList<>();
		
		try {
			int interval = 0;
			DateTimeFormatter fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
			ChronoUnit timeUnit = ChronoUnit.MINUTES;
			boolean isDiffLessThan45Days = ChronoUnit.DAYS.between(start, end) < 45;
		
			switch (obj.getData_send_time()) {
				case 4: // 1 minute
					interval = 1;
					timeUnit = ChronoUnit.MINUTES;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (obj.getFilterBy()) {
	                	case "today":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
	                		break;
	                	case "3_day":
	                	case "this_week":
	                	case "last_week":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
	                		break;
	                	case "custom":
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                }
					break;
					
				case 1: // 5 minutes
					interval = 5;
					timeUnit = ChronoUnit.MINUTES;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (obj.getFilterBy()) {
	                	case "today":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
	                		break;
	                	case "3_day":
	                	case "this_week":
	                	case "last_week":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
	                		break;
	                	case "custom":
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                }
					break;
					
				case 2: // 15 minutes
					interval = 15;
					timeUnit = ChronoUnit.MINUTES;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (obj.getFilterBy()) {
	                	case "today":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
	                		break;
	                	case "3_day":
	                	case "this_week":
	                	case "last_week":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
	                		break;
	                	case "this_month":
	                	case "last_month":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd");
	                		break;
	                	case "12_month":
	                	case "year":
	                	case "lifetime":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	case "custom":
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                }
					break;
					
				case 3: // 1 hour
					interval = 1;
					timeUnit = ChronoUnit.HOURS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (obj.getFilterBy()) {
	                	case "today":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
	                		break;
	                	case "3_day":
	                	case "this_week":
	                	case "last_week":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
	                		break;
	                	case "this_month":
	                	case "last_month":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd");
	                		break;
	                	case "12_month":
	                	case "year":
	                	case "lifetime":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	case "custom":
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                }
					break;
					
				case 5: // 1 day
					interval = 1;
					timeUnit = ChronoUnit.DAYS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					switch (obj.getFilterBy()) {
	                	case "today":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                		break;
	                	case "3_day":
	                	case "this_week":
	                	case "last_week":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL");
	                		break;
	                	case "this_month":
	                	case "last_month":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd");
	                		break;
	                	case "12_month":
	                	case "year":
	                	case "lifetime":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	case "custom":
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
					}
					break;
					
				case 6: // 7 days
					interval = 7;
					timeUnit = ChronoUnit.DAYS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                switch (obj.getFilterBy()) {
	                	case "this_month":
	                	case "last_month":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd");
	                		break;
	                	case "12_month":
	                	case "year":
	                	case "lifetime":
	                	case "custom":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                }
					break;
					
				case 7: // 1 month
					interval = 1;
					timeUnit = ChronoUnit.MONTHS;
					fullTimeFormat = DateTimeFormatter.ofPattern("MM/yyyy");
					categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
					start = start.withDayOfMonth(1);
					break;
					
				case 8: // 1 year
					interval = 1;
					timeUnit = ChronoUnit.YEARS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy");
					categoryTimeFormat = DateTimeFormatter.ofPattern("yyyy");
					start = start.withDayOfYear(1);
					break;
			}
			
			while (!start.isAfter(end)) {
				Map<String, Object> dateTime = new HashMap<String, Object>();
				dateTime.put("time_full", start.format(fullTimeFormat));
				dateTime.put("categories_time", start.format(categoryTimeFormat));
				dateTimeList.add(dateTime);
				start = start.plus(interval, timeUnit);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return dateTimeList;
	}
	
	
	/**
	 * @description get list device parameter
	 * @author long.pham
	 * @since 2022-02-22
	 * @param arr
	 */
	

	public List getChartParameterDevice(DeviceEntity obj) {
		try {
			List dataList = new ArrayList();
			List dataDevice = obj.getDataDevice();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						
			if(dataDevice.size() > 0) {
				List<CompletableFuture<Map<String, Object>>> list = new ArrayList<CompletableFuture<Map<String, Object>>>();
				
				LocalDateTime startDate = LocalDateTime.parse(obj.getStart_date(), dateFormat).withHour(0).withMinute(0).withSecond(0);
				LocalDateTime endDate = LocalDateTime.parse(obj.getEnd_date(), dateFormat).withHour(23).withMinute(59).withSecond(59);
				long diff5Days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
				
				for(int i = 0; i < dataDevice.size(); i++) {
					int k = i;
					
					CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
						Map<String, Object> maps = new HashMap<>();
						
						try {
							Map<String, Object> map = (Map<String, Object>) dataDevice.get(k);
							
							map.put("filterEnabled", obj.isFilterEnabled());
							map.put("filterBy", obj.getFilterBy());
							map.put("start_date", obj.getStart_date());
							map.put("end_date", obj.getEnd_date());
							map.put("diff5Days", diff5Days <= 5 && diff5Days > 0);
							map.put("data_send_time", obj.getData_send_time());
							
							// get list of time to exclude data from
							List hiddenDataList = queryForList("SitesAnalytics.getHiddenDataListByDevice", map);
							map.put("hidden_data_list", hiddenDataList);
							// if device is virtual device, use table_data_virtual
							if ((int) map.get("id_device_type") == 12 && (int) map.get("id_device_group") != 81) map.put("datatablename", map.get("table_data_virtual"));
							// if data is more than 3 months, use view_tablename, else use datatablename
							else map.put("datatablename", map.get(startDate.isBefore(LocalDateTime.now().minusMonths(3)) ? "datatablename" : "view_tablename"));
							
							List<Map<String, Object>> getDataChartParameter = queryForList("SitesAnalytics.getDataChartParameter", map);
							
							maps.put("id", map.get("id"));
							maps.put("device_name", map.get("devicename"));
							maps.put("id_device_group", map.get("id_device_group"));
							maps.put("id_device_type", map.get("id_device_type"));
							maps.put("data", fulfillData(getDateTimeList(obj, startDate, endDate), getDataChartParameter));
						} catch (Exception ex) {
							log.error("getChartParameterDevice", ex);
						}
						
						return maps;
					});
					
					list.add(future);
				}
				
				CompletableFuture<Void> combinedFutures = CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
				List<Map<String, Object>> deviceDataList = combinedFutures.thenApply(__ -> list.stream().map(future -> future.join()).collect(Collectors.toList())).get();
			    deviceDataList.forEach(data -> dataList.add(data));
			}
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
		
	}
	
	
	
	
	/**
	 * @description insert error level
	 * @author long.pham
	 * @since 2021-02-26
	 */
	public EmployeeFilterFavoritesEntity saveEmployeeFilterFavorites(EmployeeFilterFavoritesEntity obj) {
		try {
			// Save
			Object insertId = insert("EmployeeFilterFavorites.saveFilterFavorites", obj);
			if (insertId != null && insertId instanceof Integer) {
				Object total = queryForObject("EmployeeFilterFavorites.getListCount", obj);
				if((int)total > 10) {
					// Delete one row
					delete("EmployeeFilterFavorites.deleteFilterFavorites", obj);
				}
				return obj;
			} else {
				return null;
			}

		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
	
	
	/**
	 * @description insert error level
	 * @author long.pham
	 * @since 2022-05-03
	 */
	public EmployeeFilterRecentlyEntity saveRecentlyUsedFilter(EmployeeFilterRecentlyEntity obj) {
		try {
			// Save
			Object insertId = insert("EmployeeFilterRecently.saveRecentlyUsedFilter", obj);
			if (insertId != null && insertId instanceof Integer) {
				Object total = queryForObject("EmployeeFilterRecently.getListCount", obj);
				
				if((int)total > 10) {
					// Delete one row
					delete("EmployeeFilterRecently.deleteRecentlyUsedFilter", obj);
				}
				
				return obj;
			} else {
				return null;
			}

		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
	
	
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 */
	

	public List getListEmployeeFilter(EmployeeFilterFavoritesEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmployeeFilterCharting.getListEmployeeFilter", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site, id_device
	 */
	

	public List getListRecently(EmployeeFilterRecentlyEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmployeeFilterRecently.getListRecently", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	
	/**
	 * @description get list favorites by id_site
	 * @author long.pham
	 * @since 2022-05-03
	 * @param id_site
	 */
	

	public List getListFavorites(EmployeeFilterFavoritesEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmployeeFilterFavorites.getListFavorites", obj);
			return dataList;
				
		} catch (Exception ex) {
			return new ArrayList();
		}
	}

	public void sendCustomReport(SitesAnalyticsReportEntity obj) {

	}
}
