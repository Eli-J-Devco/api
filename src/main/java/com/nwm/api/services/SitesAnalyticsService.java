/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEnergyBySiteDTO;
import com.nwm.api.entities.DeviceEnergyBySiteRequest;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.EmployeeChartFilterEntity;
import com.nwm.api.entities.AlertsBySiteDeviceRequest;
import com.nwm.api.entities.AlertsBySiteDeviceResponse;
import com.nwm.api.utils.Constants.ChartingFilter;
import com.nwm.api.utils.Constants.ChartingGranularity;
import com.nwm.api.utils.Lib;


public class SitesAnalyticsService extends DB {

	/**
	 * @description get list device by id_site
	 * @author long.pham
	 * @since 2022-02-22
	 * @param id_site
	 */
	public List getListDeviceBySite(DeviceEntity obj) {
		try {
			List<Map<String, Object>> dataList = queryForList("SitesAnalytics.getListDeviceBySite", obj);
			ObjectMapper mapper = new ObjectMapper();
			dataList.forEach(item -> {
					try {
						List<Map<String, Object>> parameters = mapper.readValue(item.get("parameters").toString(), new TypeReference<List<Map<String, Object>>>(){});
						item.put("parameters", parameters.stream().sorted((param1, param2) -> param1.get("name").toString().compareTo(param2.get("name").toString())).collect(Collectors.toList()));
					} catch (JsonProcessingException e) {
						item.put("parameters", new ArrayList<Map<String, Object>>());
					}
			});
			return dataList;
		} catch (Exception ex) {
			return new ArrayList<Map<String, Object>>();
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
		try {
			if (dataList == null || dateTimeList.size() == 0) return dataList;
			List<Map<String, Object>> fulfilledDataList = new ArrayList<Map<String, Object>>();
			int count = 0;
			for (int i = 0; i < dateTimeList.size(); i++) {
				Map<String, Object> dateTimeItem = dateTimeList.get(i);
				if (i - count > dataList.size() - 1) {
					fulfilledDataList.add(dateTimeItem);
					continue;
				}
				Map<String, Object> dataItem = dataList.get(i - count);
				if (dateTimeItem.get("time_full").toString().equals(dataItem.get("time_full").toString())) {
					fulfilledDataList.add(dataItem);
				} else {
					fulfilledDataList.add(dateTimeItem);
					count++;
				}
			}
			
			return fulfilledDataList;
		} catch (Exception e) {
			return dataList;
		}
		
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
	public List<Map<String, Object>> getDateTimeList(DeviceEntity obj, LocalDateTime start, LocalDateTime end) {
		List<Map<String, Object>> dateTimeList = new ArrayList<>();
		
		try {
			int interval = 0;
			DateTimeFormatter fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
			ChronoUnit timeUnit = ChronoUnit.MINUTES;
			boolean isDiffLessThan45Days = ChronoUnit.DAYS.between(start, end) < 45;
		
			switch (ChartingGranularity.fromValue(obj.getData_send_time())) {
				case _1_MINUTE:
					interval = 1;
					timeUnit = ChronoUnit.MINUTES;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (ChartingFilter.fromValue(obj.getFilterBy())) {
	                	case TODAY:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
	                		break;
	                	case _3_DAYS:
	                	case THIS_WEEK:
	                	case LAST_WEEK:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
	                		break;
	                	case THIS_MONTH:
	                	case LAST_MONTH:
	                	case CUSTOM:
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	default:
	                		break;
	                }
					break;
					
				case _5_MINUTES:
					interval = 5;
					timeUnit = ChronoUnit.MINUTES;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (ChartingFilter.fromValue(obj.getFilterBy())) {
	                	case TODAY:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
	                		break;
	                	case _3_DAYS:
	                	case THIS_WEEK:
	                	case LAST_WEEK:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
	                		break;
	                	case THIS_MONTH:
	                	case LAST_MONTH:
	                	case CUSTOM:
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	default:
	                		break;
	                }
					break;
					
				case _15_MINUTES:
					interval = 15;
					timeUnit = ChronoUnit.MINUTES;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (ChartingFilter.fromValue(obj.getFilterBy())) {
	                	case TODAY:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
	                		break;
	                	case _3_DAYS:
	                	case THIS_WEEK:
	                	case LAST_WEEK:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
	                		break;
	                	case THIS_MONTH:
	                	case LAST_MONTH:
	                	case LAST_12_MONTHS:
	                	case YEAR_TO_DATE:
	                	case LIFETIME:
	                	case CUSTOM:
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	default:
	                		break;
	                }
					break;
					
				case _1_HOUR:
					interval = 1;
					timeUnit = ChronoUnit.HOURS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	                switch (ChartingFilter.fromValue(obj.getFilterBy())) {
	                	case TODAY:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
	                		break;
	                	case _3_DAYS:
	                	case THIS_WEEK:
	                	case LAST_WEEK:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
	                		break;
	                	case THIS_MONTH:
	                	case LAST_MONTH:
	                	case LAST_12_MONTHS:
	                	case YEAR_TO_DATE:
	                	case LIFETIME:
	                	case CUSTOM:
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	default:
	                		break;
	                }
					break;
					
				case _1_DAY:
					interval = 1;
					timeUnit = ChronoUnit.DAYS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					switch (ChartingFilter.fromValue(obj.getFilterBy())) {
	                	case TODAY:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                		break;
	                	case _3_DAYS:
	                	case THIS_WEEK:
	                	case LAST_WEEK:
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL");
	                		break;
	                	case THIS_MONTH:
	                	case LAST_MONTH:
	                	case LAST_12_MONTHS:
	                	case YEAR_TO_DATE:
	                	case LIFETIME:
	                	case CUSTOM:
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	default:
	                		break;
					}
					break;
					
				case _7_DAYS:
					interval = 7;
					timeUnit = ChronoUnit.DAYS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	                switch (ChartingFilter.fromValue(obj.getFilterBy())) {
		                case THIS_MONTH:
	                	case LAST_MONTH:
	                	case LAST_12_MONTHS:
	                	case YEAR_TO_DATE:
	                	case LIFETIME:
	                	case CUSTOM:
	                		categoryTimeFormat = isDiffLessThan45Days ? DateTimeFormatter.ofPattern("MM/dd") : DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	default:
	                		break;
	                }
					break;
					
				case _1_MONTH:
					interval = 1;
					timeUnit = ChronoUnit.MONTHS;
					fullTimeFormat = DateTimeFormatter.ofPattern("MM/yyyy");
					categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
					start = start.withDayOfMonth(1);
					break;
					
				case _1_YEAR:
					interval = 1;
					timeUnit = ChronoUnit.YEARS;
					fullTimeFormat = DateTimeFormatter.ofPattern("yyyy");
					categoryTimeFormat = DateTimeFormatter.ofPattern("yyyy");
					start = start.withDayOfYear(1);
					break;
					
				default:
					return dateTimeList;
					
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
	 * @description convert date time format
	 * @author Hung.Bui
	 * @since 2024-11-11
	 */
	public List<Map<String, Object>> convertDateTimeFormat(DeviceEntity obj, List<Map<String, Object>> dataList, LocalDateTime start, LocalDateTime end) {
		try {
			if (obj.getDate_format() == null || obj.getTime_format() == 0 || obj.getLocale() == null) return dataList;
			Locale locale = new Locale(obj.getLocale());
			DateTimeFormatter fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
			DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(obj.getDate_format() + (obj.getTime_format() == 2 ? " hh:mm a" : " HH:mm"), locale);
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(obj.getDate_format(), locale);
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(obj.getTime_format() == 2 ? "ha" : "HH:mm", locale);
			
			for (Map<String, Object> data: dataList) {
				switch (ChartingFilter.fromValue(obj.getFilterBy())) {
					case TODAY:
						switch (ChartingGranularity.fromValue(obj.getData_send_time())) {
							case _1_MINUTE:
							case _5_MINUTES:
							case _15_MINUTES:
							case _1_HOUR:
								categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
		                		if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDateTime.parse(data.get("time_full").toString(), fullTimeFormat).format(dateTimeFormat));
		                		if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", LocalTime.parse(data.get("categories_time").toString(), categoryTimeFormat).format(timeFormat));
		                		break;
							case _1_DAY:
							case _7_DAYS:
								fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								categoryTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDate.parse(data.get("time_full").toString(), fullTimeFormat).format(dateFormat));
								if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", LocalDate.parse(data.get("categories_time").toString(), categoryTimeFormat).format(DateTimeFormatter.ofPattern("dd. LLL", locale)));
								break;
							default:
								break;
						}
						break;
					
					case _3_DAYS:
                	case THIS_WEEK:
                	case LAST_WEEK:
                		switch (ChartingGranularity.fromValue(obj.getData_send_time())) {
	                		case _1_MINUTE:
							case _5_MINUTES:
							case _15_MINUTES:
							case _1_HOUR:
								categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDateTime.parse(data.get("time_full").toString(), fullTimeFormat).format(dateTimeFormat));
								if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", MonthDay.parse(data.get("categories_time").toString(), categoryTimeFormat).format(DateTimeFormatter.ofPattern("dd. LLL", locale)) + " " + LocalTime.parse(data.get("categories_time").toString(), categoryTimeFormat).format(timeFormat));
		                		break;
							case _1_DAY:
							case _7_DAYS:
								fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL");
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDate.parse(data.get("time_full").toString(), fullTimeFormat).format(dateFormat));
								if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", MonthDay.parse(data.get("categories_time").toString(), categoryTimeFormat).format(categoryTimeFormat.withLocale(locale)));
		                		break;
							default:
								break;
						}
						break;
					
                	case THIS_MONTH:
                	case LAST_MONTH:
                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd");
                		switch (ChartingGranularity.fromValue(obj.getData_send_time())) {
	                		case _1_MINUTE:
							case _5_MINUTES:
							case _15_MINUTES:
							case _1_HOUR:
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDateTime.parse(data.get("time_full").toString(), fullTimeFormat).format(dateTimeFormat));
		                		break;
							case _1_DAY:
							case _7_DAYS:
								fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDate.parse(data.get("time_full").toString(), fullTimeFormat).format(dateFormat));
		                		break;
							default:
								break;
						}
                		if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", MonthDay.parse(data.get("categories_time").toString(), categoryTimeFormat).format(DateTimeFormatter.ofPattern(obj.getLocale().equals("vi") ? "dd/MM" : "MM/dd", locale)));
                		break;
                		
                	case LAST_12_MONTHS:
                	case YEAR_TO_DATE:
                	case LIFETIME:
                		categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
                		switch (ChartingGranularity.fromValue(obj.getData_send_time())) {
	                		case _15_MINUTES:
							case _1_HOUR:
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDateTime.parse(data.get("time_full").toString(), fullTimeFormat).format(dateTimeFormat));
								if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", YearMonth.parse(data.get("categories_time").toString(), categoryTimeFormat).format(categoryTimeFormat.withLocale(locale)));
		                		break;
							case _1_DAY:
							case _7_DAYS:
								fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDate.parse(data.get("time_full").toString(), fullTimeFormat).format(dateFormat));
								if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", YearMonth.parse(data.get("categories_time").toString(), categoryTimeFormat).format(categoryTimeFormat.withLocale(locale)));
		                		break;
							case _1_MONTH:
								if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", YearMonth.parse(data.get("categories_time").toString(), categoryTimeFormat).format(categoryTimeFormat.withLocale(locale)));
								break;
							default:
								break;
						}
                		break;
                	
                	case CUSTOM:
                		boolean isDiffLessThan45Days = ChronoUnit.DAYS.between(start, end) < 45;
	            		switch (ChartingGranularity.fromValue(obj.getData_send_time())) {
		            		case _1_MINUTE:
							case _5_MINUTES:
							case _15_MINUTES:
							case _1_HOUR:
								categoryTimeFormat = DateTimeFormatter.ofPattern(isDiffLessThan45Days ? "MM/dd" : "LLL. yyyy");
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDateTime.parse(data.get("time_full").toString(), fullTimeFormat).format(dateTimeFormat));
								if (Objects.nonNull(data.get("categories_time")))  {
									if (isDiffLessThan45Days) data.put("categories_time", MonthDay.parse(data.get("categories_time").toString(), categoryTimeFormat).format(DateTimeFormatter.ofPattern(obj.getLocale().equals("vi") ? "dd/MM" : "MM/dd", locale)));
									else data.put("categories_time", YearMonth.parse(data.get("categories_time").toString(), categoryTimeFormat).format(categoryTimeFormat.withLocale(locale)));
								}
		                		break;
							case _1_DAY:
								fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								categoryTimeFormat = DateTimeFormatter.ofPattern(isDiffLessThan45Days ? "MM/dd" : "LLL. yyyy");
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDate.parse(data.get("time_full").toString(), fullTimeFormat).format(dateFormat));
								if (Objects.nonNull(data.get("categories_time")))  {
									if (isDiffLessThan45Days) data.put("categories_time", MonthDay.parse(data.get("categories_time").toString(), categoryTimeFormat).format(DateTimeFormatter.ofPattern(obj.getLocale().equals("vi") ? "dd/MM" : "MM/dd", locale)));
									else data.put("categories_time", YearMonth.parse(data.get("categories_time").toString(), categoryTimeFormat).format(categoryTimeFormat.withLocale(locale)));
								}
		                		break;
							case _7_DAYS:
								fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
								categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
								if (Objects.nonNull(data.get("time_full"))) data.put("time_full", LocalDate.parse(data.get("time_full").toString(), fullTimeFormat).format(dateFormat));
								if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", YearMonth.parse(data.get("categories_time").toString(), categoryTimeFormat).format(categoryTimeFormat.withLocale(locale)));
								break;
							case _1_MONTH:
								categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
								dateFormat = categoryTimeFormat.withLocale(locale);
								if (Objects.nonNull(data.get("categories_time"))) data.put("categories_time", YearMonth.parse(data.get("categories_time").toString(), categoryTimeFormat).format(dateFormat));
								break;
							default:
								break;
						}
						break;
				}
			}
		} catch (Exception e) {
		}
		
		return dataList;
	}
	
	
	/**
	 * @description get list device parameter
	 * @author long.pham
	 * @since 2022-02-22
	 * @param arr
	 */
	

	public List getChartParameterDevice(DeviceEntity obj) {
		try {
			List dataDevice = obj.getDataDevice();
			DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
			DateTimeFormatter isoDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						
			if(dataDevice.size() > 0) {
				List<CompletableFuture<Map<String, Object>>> list = new ArrayList<CompletableFuture<Map<String, Object>>>();
				
				LocalDateTime startDate = LocalDateTime.parse(obj.getStart_date(), inputDateFormat).withHour(0).withMinute(0).withSecond(0);
				LocalDateTime endDate = LocalDateTime.parse(obj.getEnd_date(), inputDateFormat).withHour(23).withMinute(59).withSecond(59);
				long diff5Days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
				
				for(int i = 0; i < dataDevice.size(); i++) {
					int k = i;
					
					CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
						Map<String, Object> maps = new HashMap<>();
						
						try {
							Map<String, Object> map = (Map<String, Object>) dataDevice.get(k);
							
							map.put("filterEnabled", obj.isFilterEnabled());
							map.put("filterBy", obj.getFilterBy());
							map.put("start_date", startDate.format(isoDateFormat));
							map.put("end_date", endDate.format(isoDateFormat));
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
							maps.put("device_name", map.get("name"));
							maps.put("id_device_group", map.get("id_device_group"));
							maps.put("id_device_type", map.get("id_device_type"));
							maps.put("order", map.get("order"));
							maps.put("data", convertDateTimeFormat(obj, fulfillData(getDateTimeList(obj, startDate, endDate), getDataChartParameter), startDate, endDate));
						} catch (Exception ex) {
							log.error("getChartParameterDevice", ex);
						}
						
						return maps;
					});
					
					list.add(future);
				}
				
				return list.stream().map(future -> future.join()).collect(Collectors.toList());
			}
		} catch (Exception ex) {
		}
		return new ArrayList();
	}
	
	/**
	 * @description Get recently filter list
	 * @author Hung.Bui
	 * @since 2024-06-07
	 * @param obj { id_employee, hash_id_site }
	 * @return
	 */
	public List<EmployeeChartFilterEntity> getListFilter(EmployeeChartFilterEntity obj) {
		try {
			List<EmployeeChartFilterEntity> dataList = queryForList("SitesAnalytics.getListFilter", obj);
			if (dataList == null) return new ArrayList<EmployeeChartFilterEntity>();
			return dataList;
		} catch (Exception ex) {
			return new ArrayList<EmployeeChartFilterEntity>();
		}
	}
	
	/**
	 * @description save filter
	 * @author Hung.Bui
	 * @since 2024-06-07
	 * @param obj { id_employee, hash_id_site, params, created_date, name, is_favorite }
	 * @return
	 */
	public EmployeeChartFilterEntity saveFilter(EmployeeChartFilterEntity obj) {
		try {
			Integer insertId = (Integer) insert("SitesAnalytics.saveFilter", obj);
			if (insertId != null && insertId <= 0) return null;
			Integer total = (Integer) queryForObject("SitesAnalytics.getFiltersCount", obj);
			if(total > 10) delete("SitesAnalytics.deleteFilter", obj);
			
			return obj;
		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
	

	/**
	 * @description get device energy by site
	 * @author Hung.Bui
	 * @since 2025-07-10
	 * @param obj { date, siteId, deviceTypeId, granularityId }
	 * @return list of devices
	 */
	public List<DeviceEntity> getDeviceEnergyBySite(DeviceEnergyBySiteRequest obj) {
		try {
			CustomerViewService customerViewService = new CustomerViewService();
			DevicesByTypeEntity devicesByType = customerViewService.getDevicesBySite(obj);
			List<DeviceEntity> devices = obj.getDeviceTypeId().equals("inverter") ? devicesByType.getInverter() : devicesByType.getMeter();
			if (devices.size() == 0) return new ArrayList<>();
			
			DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter outputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime startDate = LocalDate.parse(obj.getDate(), inputDateFormat).atStartOfDay();
			LocalDateTime endDate = LocalDate.parse(obj.getDate(), inputDateFormat).atTime(23, 59, 59);
			
			List<CompletableFuture<DeviceEntity>> list = new ArrayList<CompletableFuture<DeviceEntity>>();
			
			DeviceEntity dateTimeParams = new DeviceEntity();
			switch (obj.getGranularityId()) {
				case "hourly":
				default:
					dateTimeParams.setData_send_time(ChartingGranularity._1_HOUR.getValue());
					dateTimeParams.setFilterBy(ChartingFilter.TODAY.getValue());
					break;
			}
			
			List<DeviceEnergyBySiteDTO> dateTimeList = getDateTimeList(dateTimeParams, startDate, endDate)
					.stream()
					.map(DeviceEnergyBySiteDTO::convertDateTimeToEntity)
					.collect(Collectors.toList());
			
			for(int i = 0; i < devices.size(); i++) {
				DeviceEntity device = devices.get(i);
				device.setStart_date(startDate.format(outputDateFormat));
				device.setEnd_date(endDate.format(outputDateFormat));
				device.setFilterBy(obj.getGranularityId());
				
				CompletableFuture<DeviceEntity> future = CompletableFuture.supplyAsync(() -> {
					try {
						List<DeviceEnergyBySiteDTO> data = queryForList("SitesAnalytics.getDeviceEnergyBySite", device);
						device.setDataDevice(Lib.fulfillData(dateTimeList, data, "time_full"));
					} catch (Exception ex) {
						log.error("getDeviceEnergyBySite", ex);
					}
					
					return device;
				});
				
				list.add(future);
			}
			
			return list.stream().map(future -> future.join()).collect(Collectors.toList());
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * @description get events by site's devices
	 * @author Hung.Bui
	 * @since 2025-09-19
	 * @param obj { id, date_from, date_to, device_list, data_send_time, filterBy, date_format, time_format, locale }
	 * @return list of events
	 */
	public List<List<AlertsBySiteDeviceResponse>> getEvents(AlertsBySiteDeviceRequest obj) {
		try {
			if (obj.getDevice_list().size() == 0) return new ArrayList<>();
			AlertService alertService = new AlertService();
			List<AlertsBySiteDeviceResponse> events = alertService.getSiteDeviceAlerts(obj);
			
			Map<Integer, List<AlertsBySiteDeviceResponse>> errorLevel = new HashMap<>();
			
			for (AlertsBySiteDeviceResponse event: events) {
				int key = event.getError_level_id();
				if (errorLevel.containsKey(key)) errorLevel.get(key).add(event);
				else errorLevel.put(key, new ArrayList<>(Arrays.asList(event)));
			}
			
			DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime startDate = LocalDateTime.parse(obj.getDate_from(), dateTimeFormat).withHour(0).withMinute(0).withSecond(0);
			LocalDateTime endDate = LocalDateTime.parse(obj.getDate_to(), dateTimeFormat).withHour(23).withMinute(59).withSecond(59);
			DeviceEntity settings = new DeviceEntity();
			settings.setData_send_time(obj.getData_send_time());
			settings.setFilterBy(obj.getFilterBy());
			settings.setTime_format(obj.getTime_format());
			settings.setDate_format(obj.getDate_format());
			settings.setLocale(obj.getLocale());
			
			List<List<AlertsBySiteDeviceResponse>> eventsByErrorLevel = new ArrayList<>();
			
			for (List<AlertsBySiteDeviceResponse> value: errorLevel.values()) {
				List<Map<String, Object>> convertedEvents = value.stream().map(item -> AlertsBySiteDeviceResponse.convertToMap(item)).collect(Collectors.toList());
				List<AlertsBySiteDeviceResponse> convertedDateTimeFormatEvents = convertDateTimeFormat(settings, fulfillData(getDateTimeList(settings, startDate, endDate), convertedEvents), startDate, endDate).stream().map(item -> AlertsBySiteDeviceResponse.convertFromMap(item)).collect(Collectors.toList());
				eventsByErrorLevel.add(convertedDateTimeFormatEvents);
			}
			
			return eventsByErrorLevel;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
}
