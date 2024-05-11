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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ScadaChartingDeviceEntity;
import com.nwm.api.entities.ScadaChartingEntity;

public class ScadaChartingService extends DB {
	
	/**
	 * @description get site detail
	 * @author Hung.Bui
	 * @since 2024-05-03
	 * @param obj { hash_id_site }
	 * @return
	 */
	public ScadaChartingEntity getSiteDetail(ScadaChartingEntity obj) {
		try {
			return (ScadaChartingEntity) queryForObject("ScadaCharting.getSiteDetail", obj);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @description get devices list by site
	 * @author Hung.Bui
	 * @since 2024-05-03
	 * @param obj { hash_id_site }
	 * @return
	 */
	public List<ScadaChartingDeviceEntity> getListDeviceBySite(ScadaChartingEntity obj) {
		try {
			List<ScadaChartingDeviceEntity> dataList = queryForList("ScadaCharting.getListDeviceBySite", obj);
			ObjectMapper mapper = new ObjectMapper();
			dataList.forEach(item -> {
					try {
						item.setParameters(mapper.readValue(item.getParametersJSON(), new TypeReference<List<Map<String, Object>>>(){}));
					} catch (JsonProcessingException e) {
						item.setParameters(new ArrayList<Map<String, Object>>());
					}
				item.setParametersJSON(null);
			});
			return dataList;
		} catch (Exception ex) {
			return new ArrayList<ScadaChartingDeviceEntity>();
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
				for (Map<String, Object> dateTime: dateTimeList) {
					boolean isFound = false;
					
					for(Map<String, Object> data: dataList) {
						String fullTime = dateTime.get("time_full").toString();
						String powerTime = data.get("time_full").toString();
						
						if (fullTime.equals(powerTime)) {
							fulfilledDataList.add(data);
							isFound = true;
							break;
						}
					}
					
					if (!isFound) fulfilledDataList.add(dateTime);
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
	private List<Map<String, Object>> getDateTimeList(ScadaChartingEntity obj, LocalDateTime start, LocalDateTime end) {
		List<Map<String, Object>> dateTimeList = new ArrayList<>();
		
		try {
			int interval = 0;
			DateTimeFormatter fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
			ChronoUnit timeUnit = ChronoUnit.MINUTES;
			boolean isDiffLessThan45Days = ChronoUnit.DAYS.between(start, end) < 45;
		
			switch (obj.getData_send_time()) {
				case 8: // 1 minute
					interval = 1;
					timeUnit = ChronoUnit.MINUTES;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (obj.getId_filter()) {
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
	                switch (obj.getId_filter()) {
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
	                switch (obj.getId_filter()) {
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
	                	case "custom":
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                }
					break;
					
				case 3: // 1 hour
					interval = 1;
					timeUnit = ChronoUnit.HOURS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (obj.getId_filter()) {
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
	                	case "custom":
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                }
					break;
					
				case 4: // 1 day
					interval = 1;
					timeUnit = ChronoUnit.DAYS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					switch (obj.getId_filter()) {
	                	case "today":
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
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	case "custom":
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
					}
					break;
					
				case 5: // 7 days
					interval = 7;
					timeUnit = ChronoUnit.DAYS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                switch (obj.getId_filter()) {
	                	case "this_month":
	                	case "last_month":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd");
	                		break;
	                	case "12_month":
	                	case "year":
	                	case "custom":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                }
					break;
					
				case 6: // 1 month
					interval = 1;
					timeUnit = ChronoUnit.MONTHS;
					fullTimeFormat = DateTimeFormatter.ofPattern("MM/yyyy");
					categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
					start = start.withDayOfMonth(1);
					break;
					
				case 7: // 1 year
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
	 * @description get chart data
	 * @author Hung.Bui
	 * @since 2024-05-03
	 * @param obj { id_filter, start_date, end_date, data_send_time, devicesList }
	 * @return
	 */
	public List getChartData(ScadaChartingEntity obj) {
		try {
			List dataList = new ArrayList();
			List devicesList = obj.getDevicesList();
			DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
			DateTimeFormatter isoDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						
			if(devicesList.size() > 0) {
				List<CompletableFuture<Map<String, Object>>> list = new ArrayList<CompletableFuture<Map<String, Object>>>();
				
				LocalDateTime startDate = LocalDateTime.parse(obj.getStart_date(), inputDateFormat).withHour(0).withMinute(0).withSecond(0);
				LocalDateTime endDate = LocalDateTime.parse(obj.getEnd_date(), inputDateFormat).withHour(23).withMinute(59).withSecond(59);
				long diff5Days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
				
				for(int i = 0; i < devicesList.size(); i++) {
					int k = i;
					
					CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
						Map<String, Object> maps = new HashMap<>();
						
						try {
							Map<String, Object> map = (Map<String, Object>) devicesList.get(k);
							
							map.put("filterBy", obj.getId_filter());
							map.put("start_date", startDate.format(isoDateFormat));
							map.put("end_date", endDate.format(isoDateFormat));
							map.put("diff5Days", diff5Days <= 5 && diff5Days > 0);
							map.put("data_send_time", obj.getData_send_time());
							
							// get list of time to exclude data from
							List hiddenDataList = queryForList("ScadaCharting.getHiddenDataListByDevice", map);
							map.put("hidden_data_list", hiddenDataList);
							// if device is virtual device, use table_data_virtual
							if ((int) map.get("id_device_type") == 12) map.put("datatablename", map.get("table_data_virtual"));
							// if data is more than 3 months, use view_tablename, else use datatablename
							else map.put("datatablename", map.get(startDate.isBefore(LocalDateTime.now().minusMonths(3)) ? "datatablename" : "view_tablename"));
							
							List chartData = queryForList("ScadaCharting.getChartData", map);
							
							// get list of filter parameters
							List filterParamsList = queryForList("ScadaCharting.getFilterParamsByDevice", map);
							
							maps.put("id", map.get("id"));
							maps.put("device_name", map.get("name"));
							maps.put("id_device_group", map.get("id_device_group"));
							maps.put("id_device_type", map.get("id_device_type"));
							maps.put("filter_params", filterParamsList);
							maps.put("data", fulfillData(getDateTimeList(obj, startDate, endDate), chartData));
						} catch (Exception ex) {
							log.error("getChartData", ex);
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
}
