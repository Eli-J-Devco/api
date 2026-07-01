/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEnergyBySiteDTO;
import com.nwm.api.entities.DeviceEnergyBySiteRequest;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.EmployeeChartFilterEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.AlertsBySiteDeviceRequest;
import com.nwm.api.entities.AlertsBySiteDeviceResponse;
import com.nwm.api.utils.Constants.ChartingFilter;
import com.nwm.api.utils.Constants.ChartingGranularity;
import com.nwm.api.utils.Constants.DeviceType;
import com.nwm.api.utils.Constants.UploadingDataIntervals;
import com.nwm.api.utils.Lib;


@Service
public class SitesAnalyticsService extends DB {
	@Autowired
	SiteService siteService;

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
	

	public List<Map<String, Object>> getChartParameterDevice(DeviceEntity obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<DeviceEntity> dataDevice = mapper.convertValue(obj.getDataDevice(), new TypeReference<List<DeviceEntity>>(){});
						
			if (!CollectionUtils.isEmpty(dataDevice)) {
				DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
				LocalDateTime startDate = LocalDateTime.parse(obj.getStart_date(), inputDateFormat).withHour(0).withMinute(0).withSecond(0);
				LocalDateTime endDate = LocalDateTime.parse(obj.getEnd_date(), inputDateFormat).withHour(23).withMinute(59).withSecond(59);
				ChartingGranularity chartingGranularity = ChartingGranularity.fromValue(obj.getData_send_time());
				ChartingFilter chartingFilter = ChartingFilter.fromValue(obj.getFilterBy());
				
				return dataDevice.stream()
					.map(device -> CompletableFuture.supplyAsync(() -> {
						device.setFilterEnabled(obj.isFilterEnabled());
						List<Map<String, Object>> chartData = getDeviceData(device, startDate, endDate, chartingGranularity, chartingFilter);
						
						Map<String, Object> maps = new HashMap<>();
						maps.put("id", device.getId());
						maps.put("device_name", device.getName());
						maps.put("id_device_group", device.getId_device_group());
						maps.put("id_device_type", device.getId_device_type());
						maps.put("order", device.getOrder());
						maps.put("data", convertDateTimeFormat(obj, chartData, startDate, endDate));
						
						return maps;
					}))
					.map(future -> future.join())
					.collect(Collectors.toList());
			}
			
			return new ArrayList<>();
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}
	
	public List<Map<String, Object>> getDeviceData(DeviceEntity device, LocalDateTime startDate, LocalDateTime endDate, ChartingGranularity granularity, ChartingFilter filter) {
		try {
			Optional<SiteEntity> siteOptional = siteService.getSiteById(device.getId_site());
			if (!siteOptional.isPresent()) return new ArrayList<>();
			SiteEntity site = siteOptional.get();
			
			DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String timeString = "time";
			String timeFullString = "time_full";
			String categoriesTimeString = "categories_time";
			Map<Temporal, List<Map<String, Object>>> dataGroupByGranularityMap = new TreeMap<>();
			UploadingDataIntervals siteUploadingInterval = UploadingDataIntervals.fromValue(site.getData_send_time());
			ChartingGranularity granularityBySiteUploadingInterval = siteUploadingInterval == UploadingDataIntervals._5_MINUTES ?
				ChartingGranularity._5_MINUTES
				: 
				siteUploadingInterval == UploadingDataIntervals._15_MINUTES ? 
					ChartingGranularity._15_MINUTES
					:
					ChartingGranularity._1_MINUTE;
			long diff5Days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
			boolean isDiffLessThan5Days = diff5Days <= 5 && diff5Days > 0;
			
			device.setStart_date(startDate.format(dateTimeFormat));
			device.setEnd_date(endDate.format(dateTimeFormat));
			device.setFilterBy(filter.getValue());
			device.setData_send_time(granularity.getValue());
			// get list of time to exclude data from
			List hiddenDataList = queryForList("SitesAnalytics.getHiddenDataListByDevice", device);
			device.setHidden_data_list(hiddenDataList);
			// if device is virtual device, use table_data_virtual
			if (DeviceType.fromValue(device.getId_device_type()) == DeviceType.SYSTEM) device.setDatatablename(device.getTable_data_virtual());
			
			List<Map<String, Object>> rawData = queryForList("SitesAnalytics.getDataChartParameter", device);
			
			rawData.stream().forEach(item -> {
				Temporal timeFull = stringToDateTimeByGranularity(item.get(timeFullString).toString(), granularity);
				dataGroupByGranularityMap.putIfAbsent(timeFull, new ArrayList<Map<String, Object>>());
				dataGroupByGranularityMap.get(timeFull).add(item);
			});
			
			List<DeviceParameterEntity> parameters = device.getParameters();
			
			List<Map<String, Object>> chartData = dataGroupByGranularityMap.values().stream()
				.map(dataListItem -> {
					Map<String, Object> map = new HashMap<>();
					map.put(timeFullString, dataListItem.get(0).get(timeFullString).toString());
					map.put(categoriesTimeString, dataListItem.get(0).get(categoriesTimeString).toString());
					
					parameters.stream().forEach(parameter -> {
						String parameterSlug = parameter.getSlug();
						
						// filter value of parameter that is out of range
						if (device.isFilterEnabled()) {
							dataListItem.stream().forEach(item -> {
								Optional<Number> value = Optional.ofNullable((Number) item.get(parameterSlug));
								Optional<Double> min = Optional.ofNullable(parameter.getMin_value());
								Optional<Double> max = Optional.ofNullable(parameter.getMax_value());
								
								if (!value.isPresent()) return;
								if (
									(min.isPresent() && value.get().doubleValue() < min.get().doubleValue()) ||
									(max.isPresent() && value.get().doubleValue() > max.get().doubleValue())
								) item.put(parameterSlug, null);
							});
						}
						
						if ((parameter.isIs_energy() && parameter.isIs_user_defined()) || (parameterSlug.equals("MeasuredProduction") && !isDiffLessThan5Days && DeviceType.fromValue(device.getId_device_type()) != DeviceType.SYSTEM)) {
							// interval energy parameter
							Optional<Map<String, Object>> dataByMinTime = dataListItem.stream().min(Comparator.comparing(item -> LocalDateTime.parse(item.get(timeString).toString(), dateTimeFormat)));
							map.put(parameterSlug, dataByMinTime.get());
						} else if (parameter.isIs_energy() && !parameter.isIs_user_defined() && DeviceType.fromValue(device.getId_device_type()) != DeviceType.SYSTEM) {
							// accumulated energy parameter
							Map<String, Object> dataByMinTime = dataListItem.stream().min(Comparator.comparing(item -> LocalDateTime.parse(item.get(timeString).toString(), dateTimeFormat))).get();
							boolean isCurrentTimestampValid = isCurrentTimestampValid(dataByMinTime.get(timeString).toString(), startDate, granularity, siteUploadingInterval);
							if (!isCurrentTimestampValid) return;
							Optional.ofNullable((Double) dataByMinTime.get(parameterSlug)).ifPresent(value -> map.put(parameterSlug, BigDecimal.valueOf(value).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue()));
						} else if (parameterSlug.equals("MeasuredProduction")) {
							Supplier<DoubleStream> dataStream = () -> dataListItem.stream()
								.map(item -> (Number) item.get(isDiffLessThan5Days ? "nvmActivePower" : "nvmActiveEnergy"))
								.filter(Objects::nonNull)
								.mapToDouble(Number::doubleValue);
							
							if (!dataStream.get().findAny().isPresent()) return;
							
							map.put(parameterSlug, BigDecimal.valueOf(isDiffLessThan5Days ? dataStream.get().average().getAsDouble() : dataStream.get().sum()).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue());
						} else if (parameterSlug.equals("expected_power")) {
							if (DeviceType.fromValue(device.getId_device_type()) == DeviceType.SYSTEM) {
								dataListItem.stream()
									.map(item -> (Number) item.get("expected_power_ac"))
									.filter(Objects::nonNull)
									.mapToDouble(Number::doubleValue)
									.average()
									.ifPresent(value -> map.put(parameterSlug, BigDecimal.valueOf(value).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue()));
							} else {
								String timeFullBySiteUploadingIntervalString = "time_full_by_site_uploading_interval";
								String irradianceSlug = "nvm_irradiance";
								String temperatureSlug = "nvm_temperature";
								String panelTemperatureSlug = "nvm_panel_temperature";
								
								dataListItem.stream()
									.collect(Collectors.groupingBy(item -> item.get(timeFullBySiteUploadingIntervalString).toString()))
									.values()
									.stream()
									.map(dataList -> {
										Function<String, OptionalDouble> function = slug -> dataList.stream()
											.map(item -> (Number) item.get(slug))
											.filter(Objects::nonNull)
											.mapToDouble(Number::doubleValue)
											.average();
										
										OptionalDouble irradiance = function.apply(irradianceSlug);
										OptionalDouble temperature = function.apply(temperatureSlug);
										OptionalDouble panelTemperature = function.apply(panelTemperatureSlug);
										
										if (!irradiance.isPresent() || !temperature.isPresent() || !panelTemperature.isPresent()) return null;
										
										double irradianceValue = irradiance.getAsDouble();
										double temperatureValue = temperature.getAsDouble();
										double panelTemperatureValue = panelTemperature.getAsDouble();
										double power = 0;
										double acCapacity = site.getAc_capacity();
										double dcCapacity = site.getDc_capacity();
										double globalSolarIrradianceAtStc = Optional.ofNullable(site.getGlobal_solar_irradiance_at_stc()).orElse(1000.0);
										double pvModuleTemperatureCoeff = Optional.ofNullable(site.getPv_module_temperature_coeff()).orElse(site.getPv_model() == 3 ? -0.37 : -0.43);
										double stcTemperature = Optional.ofNullable(site.getStc_temperature()).orElse(25.0);
										double systemLoss = Optional.ofNullable(site.getSystem_loss()).orElse(9.0);
										double inverterEfficiency = Optional.ofNullable(site.getInverter_efficiency()).orElse(site.getPv_model() == 1 ? 96.0 : site.getPv_model() == 2 ? 98.5 : 100.0);
										double annualPvModuleDegradation = Optional.ofNullable(site.getAnnual_pv_module_degradation()).orElse(0.5);
										double soiling = Optional.ofNullable(site.getSoiling()).orElse(5.0);
										double cableLosses = Optional.ofNullable(site.getCable_losses()).orElse(1.0);
										double transformerLosses = Optional.ofNullable(site.getTransformer_losses()).orElse(1.5);
										double otherLosses = Optional.ofNullable(site.getOther_losses()).orElse(1.5);
										double minIrradianceLimit = Optional.ofNullable(site.getMin_irradiance_limit()).orElse(0.0);
										double clip = Optional.ofNullable(site.getClip()).orElse(99.0);
										Optional<Double> tAvg = Optional.ofNullable(site.getT_avg());
										String commissioning = Optional.ofNullable(site.getCommissioning()).orElse(site.getBuilt_since());
										
										switch (site.getPv_model()) {
											case 1:
												double value = (
													dcCapacity *
													(irradianceValue / globalSolarIrradianceAtStc) *
													(1 - (pvModuleTemperatureCoeff / 100) * (stcTemperature - temperatureValue)) *
													(1 - systemLoss / 100) *
													(inverterEfficiency / 100)
												);
												
												return value < acCapacity ? value : acCapacity;
											
											case 2:
												return (
													dcCapacity *
													(irradianceValue / globalSolarIrradianceAtStc) *
													(1 - (1 + (pvModuleTemperatureCoeff / 100)) * (((panelTemperatureValue > 0 ? panelTemperatureValue : temperatureValue) - stcTemperature) / 100)) *
													(Math.pow(1 - (annualPvModuleDegradation / 100), LocalDateTime.parse(dataList.stream().findFirst().get().get(timeString).toString(), dateTimeFormat).getYear() - LocalDateTime.parse(commissioning, dateTimeFormat).getYear())) *
													(1 - (soiling / 100)) *
													(1 - (cableLosses / 100)) *
													(1 - (transformerLosses / 100)) *
													(1 - (otherLosses / 100)) *
													(inverterEfficiency / 100)
												);
											
											case 3:
												return (irradianceValue >= minIrradianceLimit) && power <= (acCapacity * clip / 100) && tAvg.isPresent() ? 
													(
														dcCapacity *
														(irradianceValue / globalSolarIrradianceAtStc) *
														(1 - (pvModuleTemperatureCoeff / 100) * (tAvg.get() - (temperatureValue + irradianceValue / globalSolarIrradianceAtStc * 3))) *
														(inverterEfficiency / 100)
													)
													:
													null;
		
											default:
												return null;
										}
									})
									.filter(Objects::nonNull)
									.mapToDouble(Number::doubleValue)
									.average()
									.ifPresent(value -> map.put(parameterSlug, BigDecimal.valueOf(value).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue()));
							}
						} else if (parameterSlug.equals("SolarInsolation")) {
							String irradianceSlug = "nvm_irradiance";
							
							switch (granularity) {
								case _1_MINUTE:
								case _5_MINUTES:
								case _15_MINUTES:
								case _1_HOUR:
									dataListItem.stream()
										.map(item -> (Number) item.get(irradianceSlug))
										.filter(Objects::nonNull)
										.mapToDouble(Number::doubleValue)
										.average()
										.ifPresent(value -> map.put(parameterSlug, BigDecimal.valueOf(value / (granularity == ChartingGranularity._1_MINUTE ? 60 : granularity == ChartingGranularity._5_MINUTES ? 12 : granularity == ChartingGranularity._15_MINUTES ? 4 : 1)).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue()));
									break;
									
								case _1_DAY:
								case _7_DAYS:
								case _1_MONTH:
								case _1_YEAR:
									String timeGroupByHourString = "time_group_by_hour";
									
									Supplier<DoubleStream> dataStream = () -> dataListItem.stream()
										.map(item -> {
											item.put(timeGroupByHourString, LocalDateTime.parse(item.get(timeString).toString(), dateTimeFormat).withMinute(0).withSecond(0));
											return item;
										})
										.collect(Collectors.groupingBy(item -> item.get(timeGroupByHourString).toString()))
										.values()
										.stream()
										.map(dataList -> {
											OptionalDouble average = dataList.stream()
											.map(item -> (Number) item.get(irradianceSlug))
											.filter(Objects::nonNull)
											.mapToDouble(Number::doubleValue)
											.average();
											
											return average.isPresent() ? average.getAsDouble() : null;
										})
										.filter(Objects::nonNull)
										.mapToDouble(Number::doubleValue);
									
									if (!dataStream.get().findAny().isPresent()) return;
									map.put(parameterSlug, BigDecimal.valueOf(dataStream.get().sum() / (granularity == ChartingGranularity._1_YEAR ? 1000000 : 1000)).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue());
									break;
	
								default:
									break;
							}
						} else if (parameterSlug.equals("AverageDailySolarInsolation")) {
							switch (granularity) {
								case _1_DAY:
								case _7_DAYS:
								case _1_MONTH:
								case _1_YEAR:
									String irradianceSlug = "nvm_irradiance";
									String timeGroupByHourString = "time_group_by_hour";
									String timeGroupByDayString = "time_group_by_day";
									
									dataListItem.stream()
										.map(item -> {
											item.put(timeGroupByHourString, LocalDateTime.parse(item.get(timeString).toString(), dateTimeFormat).withMinute(0).withSecond(0));
											return item;
										})
										.collect(Collectors.groupingBy(item -> item.get(timeGroupByHourString).toString()))
										.values()
										.stream()
										.map(dataList -> {
											OptionalDouble average = dataList.stream()
												.map(item -> (Number) item.get(irradianceSlug))
												.filter(Objects::nonNull)
												.mapToDouble(Number::doubleValue)
												.average();
											
											Map<String, Object> dataByHour = new HashMap<>();
											dataByHour.put(timeGroupByDayString, LocalDateTime.parse(dataList.stream().findFirst().get().get(timeString).toString(), dateTimeFormat).withHour(0).withMinute(0).withSecond(0));
											dataByHour.put(irradianceSlug, average.isPresent() ? average.getAsDouble() : null);
											return dataByHour;
										})
										.collect(Collectors.groupingBy(item -> item.get(timeGroupByDayString).toString()))
										.values()
										.stream()
										.map(dataList -> {
											Supplier<DoubleStream> dataStream = () -> dataList.stream()
												.map(item -> (Number) item.get(irradianceSlug))
												.filter(Objects::nonNull)
												.mapToDouble(Number::doubleValue);
											
											if (!dataStream.get().findAny().isPresent()) return null;	
											return dataStream.get().sum();
										})
										.filter(Objects::nonNull)
										.mapToDouble(Number::doubleValue)
										.average()
										.ifPresent(value -> map.put(parameterSlug, BigDecimal.valueOf(value / 1000).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue()));
									break;
		
								default:
									break;
							}
						} else {
							// other parameter
							Supplier<DoubleStream> dataStream = () -> dataListItem.stream()
								.map(item -> (Number) item.get(parameterSlug))
								.filter(Objects::nonNull)
								.mapToDouble(Number::doubleValue);
							
							if (!dataStream.get().findAny().isPresent()) return;
							double value = 0;
							
							switch (parameter.getValue_chart_tool()) {
								case "min":
									value = dataStream.get().min().getAsDouble();
									break;
									
								case "max":
									value = dataStream.get().max().getAsDouble();
									break;
									
								case "avg":
								default:
									value = dataStream.get().average().getAsDouble();
									break;
									
								case "sum":
									value = dataStream.get().sum();
									break;
							}
							
							map.put(parameterSlug, BigDecimal.valueOf(value).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue());
						}
					});
					
					return map;
				})
				.collect(Collectors.toList());
			
			// interval energy calculation
			parameters.stream()
				.filter(parameter -> (parameter.isIs_energy() && parameter.isIs_user_defined()) || (parameter.getSlug().equals("MeasuredProduction") && !isDiffLessThan5Days && DeviceType.fromValue(device.getId_device_type()) != DeviceType.SYSTEM))
				.forEach(parameter -> {
					String parameterSlug = parameter.getSlug();
					String accumulatedEnergySlug = Objects.nonNull(parameter.getSlug_accumulated_energy()) ? parameter.getSlug_accumulated_energy() : "nvmActiveEnergy";
					
					for (int i = 0; i < chartData.size(); i++) {
						Map<String, Object> currData = (Map<String, Object>) chartData.get(i).get(parameterSlug);
						Map<String, Object> nextData = (Map<String, Object>) (i == chartData.size() - 1 ? new HashMap<>() : chartData.get(i + 1).get(parameterSlug));
						String currTimeString = Optional.ofNullable(currData.get(timeString)).map(Object::toString).orElse(null);
						String nextTimeString = Optional.ofNullable(nextData.get(timeString)).map(Object::toString).orElse(null);
						
						chartData.get(i).put(parameterSlug, null);
						
						boolean isAdjacentTimestampValid = isAdjacentTimestampValid(currTimeString, nextTimeString, startDate, granularity, siteUploadingInterval);
						
						if (isAdjacentTimestampValid && Objects.nonNull(currData.get(accumulatedEnergySlug)) && Objects.nonNull(nextData.get(accumulatedEnergySlug))) {
							double value = Double.parseDouble(nextData.get(accumulatedEnergySlug).toString()) - Double.parseDouble(currData.get(accumulatedEnergySlug).toString());

							if (isIntervalEnergyInRange(value, LocalDateTime.parse(currTimeString, dateTimeFormat), site.getDc_capacity(), site.getMinimum_energy_value(), site.getInterval_energy_threshold(), granularity, startDate, endDate)) {
								chartData.get(i).put(parameterSlug, BigDecimal.valueOf(value).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue());
								continue;
							}
						}
						
						// if the time interval hasn’t ended, use most recent data
						if (i == chartData.size() - 1) {
							Map<String, Object> lastData = rawData.get(rawData.size() - 1);
							LocalDateTime lastTime = LocalDateTime.parse(lastData.get(timeString).toString(), dateTimeFormat);
							boolean isLastTimeNotEnded = isLastTimeNotEndedByGranularity(granularity, ZoneId.of(site.getTime_zone_value()), lastTime, startDate);
							boolean isCurrentTimestampValid = isCurrentTimestampValid(currTimeString, startDate, granularity, siteUploadingInterval);
							
							if (isLastTimeNotEnded && isCurrentTimestampValid && Objects.nonNull(currData.get(accumulatedEnergySlug)) && Objects.nonNull(lastData.get(accumulatedEnergySlug))) {
								double value = Double.parseDouble(lastData.get(accumulatedEnergySlug).toString()) - Double.parseDouble(currData.get(accumulatedEnergySlug).toString());
								
								if (isIntervalEnergyInRange(value, LocalDateTime.parse(currTimeString, dateTimeFormat), site.getDc_capacity(), site.getMinimum_energy_value(), site.getInterval_energy_threshold(), granularity, startDate, endDate)) {
									chartData.get(i).put(parameterSlug, BigDecimal.valueOf(value).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue());
									continue;
								}
							}
						}
						
						if (
							(siteUploadingInterval == UploadingDataIntervals._1_MINUTE   && granularity == ChartingGranularity._1_MINUTE) ||
							(siteUploadingInterval == UploadingDataIntervals._5_MINUTES  && granularity == ChartingGranularity._5_MINUTES) ||	
							(siteUploadingInterval == UploadingDataIntervals._15_MINUTES && granularity == ChartingGranularity._15_MINUTES) 	
						) continue;
						
						Double sumValue = dataSummingBySiteUploadingIntervalGranularity(site, dataGroupByGranularityMap, currData, accumulatedEnergySlug, granularity, granularityBySiteUploadingInterval, siteUploadingInterval, startDate, endDate);
						if (Objects.nonNull(sumValue)) chartData.get(i).put(parameterSlug, BigDecimal.valueOf(sumValue).setScale(parameter.getRounding_decimals(), RoundingMode.HALF_UP).doubleValue());
					}
				});
			
			return fulfillData(getDateTimeList(device, startDate, endDate), chartData);
		} catch (Exception ex) {
			log.error("getDeviceData", ex);
			return new ArrayList<>();
		}
	}
	
	private Temporal stringToDateTimeByGranularity(String dateTimeString, ChartingGranularity granularity) {
		switch (granularity) {
			default:
			case _1_MINUTE:
			case _5_MINUTES:
			case _15_MINUTES:
			case _1_HOUR:	return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			case _1_DAY:
			case _7_DAYS:	return LocalDate.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			case _1_MONTH:	return YearMonth.parse(dateTimeString, DateTimeFormatter.ofPattern("MM/yyyy"));
			case _1_YEAR:	return Year.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy"));
		}
	}
	
	private LocalDateTime dateTimeFormattingBySiteUploadingInterval(String dateTimeString, UploadingDataIntervals siteUploadingInterval) {
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, dateTimeFormat).withSecond(0);
		
		switch (siteUploadingInterval) {
			default:
			case _1_MINUTE:		return dateTime;
			case _5_MINUTES:	return dateTime.withMinute(dateTime.getMinute() - dateTime.getMinute() % UploadingDataIntervals._5_MINUTES.getInterval());
			case _15_MINUTES:	return dateTime.withMinute(dateTime.getMinute() - dateTime.getMinute() % UploadingDataIntervals._15_MINUTES.getInterval());
		}
	}
	
	private boolean isAdjacentTimestampValid(String currDateTimeString, String nextDateTimeString, LocalDateTime startDate, ChartingGranularity granularity, UploadingDataIntervals siteUploadingInterval) {
		if (Objects.isNull(currDateTimeString) || Objects.isNull(nextDateTimeString)) return false;
		
		LocalDateTime currTime = dateTimeFormattingBySiteUploadingInterval(currDateTimeString, siteUploadingInterval);
		LocalDateTime nextTime = dateTimeFormattingBySiteUploadingInterval(nextDateTimeString, siteUploadingInterval);
		
		switch (granularity) {
			case _1_MINUTE: 
				return currTime.plusMinutes(1).equals(nextTime);
				
			case _5_MINUTES: 
				return (
						currTime.plusMinutes(5).equals(nextTime) &&
						Arrays.asList(0,5,10,15,20,25,30,35,40,45,50,55).contains(currTime.getMinute()) &&
						Arrays.asList(0,5,10,15,20,25,30,35,40,45,50,55).contains(nextTime.getMinute())
				);
				
			case _15_MINUTES:
				return (
						currTime.plusMinutes(15).equals(nextTime) &&
						Arrays.asList(0,15,30,45).contains(currTime.getMinute()) &&
						Arrays.asList(0,15,30,45).contains(nextTime.getMinute())
				);
				
			case _1_HOUR:
				return (
						currTime.plusHours(1).equals(nextTime) &&
						currTime.getMinute() == 0 &&
						nextTime.getMinute() == 0
				);
				
			case _1_DAY:
				return (
						currTime.plusDays(1).equals(nextTime) &&
						currTime.getHour() == 0 && currTime.getMinute() == 0 &&
						nextTime.getHour() == 0 && nextTime.getMinute() == 0
				);
				
			case _7_DAYS:
				return (
						currTime.plusDays(7).equals(nextTime) &&
						currTime.getHour() == 0 && currTime.getMinute() == 0 &&
						currTime.getDayOfYear() == currTime.minusDays((currTime.getDayOfYear() - startDate.getDayOfYear()) % 7).getDayOfYear() &&
						nextTime.getHour() == 0 && nextTime.getMinute() == 0 &&
						nextTime.getDayOfYear() == nextTime.minusDays((nextTime.getDayOfYear() - startDate.getDayOfYear()) % 7).getDayOfYear()
				);
				
			case _1_MONTH:
				return (
						currTime.plusMonths(1).equals(nextTime) &&
						currTime.getDayOfMonth() == 1 && currTime.getHour() == 0 && currTime.getMinute() == 0 &&
						nextTime.getDayOfMonth() == 1 && nextTime.getHour() == 0 && nextTime.getMinute() == 0 
				);
				
			case _1_YEAR:
				return (
						currTime.plusYears(1).equals(nextTime) &&
						currTime.getDayOfYear() == 1 && currTime.getHour() == 0 && currTime.getMinute() == 0 &&
						nextTime.getDayOfYear() == 1 && nextTime.getHour() == 0 && nextTime.getMinute() == 0 
				);

			default:
				return false;
		}
		
	}
	
	private boolean isCurrentTimestampValid(String currDateTimeString, LocalDateTime startDate, ChartingGranularity granularity, UploadingDataIntervals siteUploadingInterval) {
		if (Objects.isNull(currDateTimeString)) return false;
		
		LocalDateTime currTime = dateTimeFormattingBySiteUploadingInterval(currDateTimeString, siteUploadingInterval);
		
		switch (granularity) {
			case _1_MINUTE:	return true;
			case _5_MINUTES:return Arrays.asList(0,5,10,15,20,25,30,35,40,45,50,55).contains(currTime.getMinute());
			case _15_MINUTES:return Arrays.asList(0,15,30,45).contains(currTime.getMinute());
			case _1_HOUR:	return currTime.getMinute() == 0;
			case _1_DAY:	return currTime.getHour() == 0 && currTime.getMinute() == 0;
			case _7_DAYS:	return currTime.getHour() == 0 && currTime.getMinute() == 0 && currTime.getDayOfYear() == currTime.minusDays((currTime.getDayOfYear() - startDate.getDayOfYear()) % 7).getDayOfYear();
			case _1_MONTH:	return currTime.getDayOfMonth() == 1 && currTime.getHour() == 0 && currTime.getMinute() == 0;
			case _1_YEAR:	return currTime.getDayOfYear() == 1 && currTime.getHour() == 0 && currTime.getMinute() == 0;
			default:		return false;
		}
	}
	
	private boolean isLastTimeNotEndedByGranularity(ChartingGranularity granularity, ZoneId timeZone, LocalDateTime lastTime, LocalDateTime startDate) {
		LocalDateTime now = ZonedDateTime.now(timeZone).toLocalDateTime();
		
		switch (granularity) {
			case _1_DAY:	return lastTime.getYear() == now.getYear() && lastTime.getMonthValue() == now.getMonthValue() && lastTime.getDayOfMonth() == now.getDayOfMonth();
			case _7_DAYS:	lastTime = lastTime.minusDays((lastTime.getDayOfYear() - startDate.getDayOfYear()) % 7);
							now = now.minusDays((now.getDayOfYear() - startDate.getDayOfYear()) % 7);
							return lastTime.getYear() == now.getYear() && lastTime.getMonthValue() == now.getMonthValue() && lastTime.getDayOfMonth() == now.getDayOfMonth();
			case _1_MONTH:	return lastTime.getYear() == now.getYear() && lastTime.getMonthValue() == now.getMonthValue();
			case _1_YEAR:	return lastTime.getYear() == now.getYear();
			default:		return false;
		}
	}
	
	private boolean isIntervalEnergyInRange(double value, LocalDateTime currTime, double dcCapacity, double minimumEnergyValue, double intervalEnergyThreshold, ChartingGranularity granularity, LocalDateTime startDate, LocalDateTime endDate) {
		double factorByGranularity = 1.0;
		
		switch (granularity) {
			case _1_MINUTE:
				factorByGranularity = 1.0 / 60.0;
				break;
				
			case _5_MINUTES:
				factorByGranularity = 1.0 / 12.0;
				break;
				
			case _15_MINUTES:
				factorByGranularity = 1.0 / 4.0;
				break;
				
			case _1_HOUR:
				factorByGranularity = 1.0;
				break;
				
			case _1_DAY:
				factorByGranularity = 24.0;
				break;
				
			case _7_DAYS:
				factorByGranularity = 24.0 * (
					!currTime.isAfter(endDate.minusDays(1 + (ChronoUnit.DAYS.between(startDate, endDate) % 7))) ?
						7
						:
						(1 + (ChronoUnit.DAYS.between(startDate, endDate) % 7))
				);
				break;
				
			case _1_MONTH:
				factorByGranularity = 24.0 * (
					endDate.getYear() == startDate.getYear() && endDate.getMonth() == startDate.getMonth() ?
						(1 + ChronoUnit.DAYS.between(startDate, endDate))
						:
						!currTime.isBefore(startDate) && !currTime.isAfter(startDate.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59)) ?
							(1 + ChronoUnit.DAYS.between(startDate, startDate.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59)))
							:
							!currTime.isBefore(endDate.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0)) && !currTime.isAfter(endDate) ?
								(1 + ChronoUnit.DAYS.between(endDate.with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0), endDate))
								:
								currTime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()
				);

				break;
				
			case _1_YEAR:
				factorByGranularity = 24.0 * (
						endDate.getYear() == startDate.getYear() ?
							(1 + ChronoUnit.DAYS.between(startDate, endDate))
							:
							!currTime.isBefore(startDate) && !currTime.isAfter(startDate.with(TemporalAdjusters.lastDayOfYear()).withHour(23).withMinute(59).withSecond(59)) ?
								(1 + ChronoUnit.DAYS.between(startDate, startDate.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59)))
								:
								!currTime.isBefore(endDate.with(TemporalAdjusters.firstDayOfYear()).withHour(0).withMinute(0).withSecond(0)) && !currTime.isAfter(endDate) ?
									(1 + ChronoUnit.DAYS.between(endDate.with(TemporalAdjusters.firstDayOfYear()).withHour(0).withMinute(0).withSecond(0), endDate))
									:
									currTime.with(TemporalAdjusters.lastDayOfYear()).getDayOfYear()
					);
				break;
	
			default:
				break;
		}
		
		return (
			(value >= minimumEnergyValue && value <= dcCapacity * (1 + (intervalEnergyThreshold / 100)) * factorByGranularity) ||
			dcCapacity == 0
		);
	}
	
	private Double dataSummingBySiteUploadingIntervalGranularity(SiteEntity site, Map<Temporal, List<Map<String, Object>>> dataGroupByGranularityMap, Map<String, Object> currData, String accumulatedEnergySlug, ChartingGranularity chartingGranularity, ChartingGranularity granularityBySiteUploadingInterval, UploadingDataIntervals siteUploadingInterval, LocalDateTime startDate, LocalDateTime endDate) {
		String timeString = "time";
		String timeFullString = "time_full";
		String timeFullBySiteUploadingIntervalString = "time_full_by_site_uploading_interval";
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Map<Temporal, List<Map<String, Object>>> dataGroupBySiteUploadingIntervalMap = new TreeMap<>();
		Temporal currTimeFull = stringToDateTimeByGranularity(currData.get(timeFullString).toString(), chartingGranularity);
		List<Map<String, Object>> dataListByCurrentTime = dataGroupByGranularityMap.get(currTimeFull);
		
		dataListByCurrentTime.stream().forEach(item -> {
			Temporal timeFullBySiteUploadingInterval = stringToDateTimeByGranularity(item.get(timeFullBySiteUploadingIntervalString).toString(), granularityBySiteUploadingInterval);
			dataGroupBySiteUploadingIntervalMap.putIfAbsent(timeFullBySiteUploadingInterval, new ArrayList<Map<String, Object>>());
			dataGroupBySiteUploadingIntervalMap.get(timeFullBySiteUploadingInterval).add(item);
		});
		
		List<Map<String, Object>> dataListByMinTime = dataGroupBySiteUploadingIntervalMap.values().stream()
			.map(dataListItem -> dataListItem.stream().min(Comparator.comparing(item -> LocalDateTime.parse(item.get(timeString).toString(), dateTimeFormat))).get())
			.collect(Collectors.toList());
		
		Double sumValue = null;
		
		for (int j = 0; j < dataListByMinTime.size() - 1; j++) {
			Map<String, Object> currDataByMinTime = dataListByMinTime.get(j);
			Map<String, Object> nextDataByMinTime = dataListByMinTime.get(j + 1);
			String currTimeString = Optional.ofNullable(currDataByMinTime.get(timeString)).map(Object::toString).orElse(null);
			String nextTimeString = Optional.ofNullable(nextDataByMinTime.get(timeString)).map(Object::toString).orElse(null);
			boolean isAdjacentTimestampBySiteUploadingIntervalValid = isAdjacentTimestampValid(currTimeString, nextTimeString, startDate, granularityBySiteUploadingInterval, siteUploadingInterval);
			
			if (isAdjacentTimestampBySiteUploadingIntervalValid && Objects.nonNull(currDataByMinTime.get(accumulatedEnergySlug)) && Objects.nonNull(nextDataByMinTime.get(accumulatedEnergySlug))) {
				double value = Double.parseDouble(nextDataByMinTime.get(accumulatedEnergySlug).toString()) - Double.parseDouble(currDataByMinTime.get(accumulatedEnergySlug).toString());
				if (!isIntervalEnergyInRange(value, LocalDateTime.parse(currTimeString, dateTimeFormat), site.getDc_capacity(), site.getMinimum_energy_value(), site.getInterval_energy_threshold(), granularityBySiteUploadingInterval, startDate, endDate)) continue;
				
				sumValue = (Objects.nonNull(sumValue) ? sumValue : 0) + value;
			}
		}
		
		return sumValue;
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
	 * @param obj { id, id_employee, hash_id_site, params, created_date, name, is_favorite }
	 * @return
	 */
	public List<EmployeeChartFilterEntity> saveFilter(EmployeeChartFilterEntity obj) {
		try {
			Integer insertId = (Integer) (obj.getId() > 0 ? update("SitesAnalytics.updateFilter", obj) : insert("SitesAnalytics.saveFilter", obj));
			if (insertId == null || insertId <= 0) return null;
			if (!obj.isIs_favorite()) delete("SitesAnalytics.deleteOldRecentFilter", obj);
			
			return getListFilter(obj);
		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
	
	/**
	 * @description delete filter
	 * @author Hung.Bui
	 * @since 2026-04-01
	 * @param obj { id, id_employee, hash_id_site, params, created_date, name, is_favorite }
	 * @return
	 */
	public List<EmployeeChartFilterEntity> deleteFilter(EmployeeChartFilterEntity obj) {
		try {
			int insertId = delete("SitesAnalytics.deleteFilter", obj);
			if (insertId <= 0) return null;
			
			return getListFilter(obj);
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
				device.setData_send_time(ChartingGranularity._1_HOUR.getValue());
				
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
