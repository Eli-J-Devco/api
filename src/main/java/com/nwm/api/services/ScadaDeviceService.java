/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ScadaDeviceAlarmEntity;
import com.nwm.api.entities.ScadaDeviceChartDataEntity;
import com.nwm.api.entities.ScadaDeviceEntity;

public class ScadaDeviceService extends DB {

	/**
	 * @description get devices list by site
	 * @author Hung.Bui
	 * @since 2024-03-26
	 * @param obj { id_site }
	 * @return
	 */
	public List<ScadaDeviceEntity> getListDeviceBySite(ScadaDeviceEntity obj) {
		List<ScadaDeviceEntity> dataList = new ArrayList<ScadaDeviceEntity>();
		try {
			dataList = queryForList("ScadaDevice.getListDeviceBySite", obj);
			if (dataList == null) return new ArrayList<ScadaDeviceEntity>();
		} catch (Exception ex) {
			return new ArrayList<ScadaDeviceEntity>();
		}
		return dataList;
	}
	
	/**
	 * @description fulfill data in specific range of time
	 * @author Hung.Bui
	 * @since 2024-04-05
	 * @param dateTimeList
	 * @param dataList
	 * @return
	 */
	private List<ScadaDeviceChartDataEntity> fulfillData(List<ScadaDeviceChartDataEntity> dateTimeList, List<ScadaDeviceChartDataEntity> dataList) {
		List<ScadaDeviceChartDataEntity> fulfilledDataList = new ArrayList<ScadaDeviceChartDataEntity>();
		
		try {
			if(dataList.size() > 0 && dateTimeList.size() > 0) {
				for (ScadaDeviceChartDataEntity dateTime: dateTimeList) {
					boolean isFound = false;
					
					for(ScadaDeviceChartDataEntity data: dataList) {
						String fullTime = dateTime.getFull_time();
						String powerTime = data.getFull_time();
						
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
	 * @since 2024-04-22
	 * @param obj device object
	 * @param start start date time
	 * @param end end date time
	 * @return
	 */
	private List<ScadaDeviceChartDataEntity> getDateTimeList(ScadaDeviceEntity obj, LocalDateTime start, LocalDateTime end) {
		List<ScadaDeviceChartDataEntity> dateTimeList = new ArrayList<>();
		
		try {
			int interval = 0;
			DateTimeFormatter fullTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
			ChronoUnit timeUnit = ChronoUnit.MINUTES;
		
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
	                	case "this_month":
	                	case "last_month":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM/dd");
	                		break;
	                	case "12_month":
	                	case "year":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
	                		break;
	                	case "custom":
	                		categoryTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
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
				ScadaDeviceChartDataEntity dateTime = new ScadaDeviceChartDataEntity();
				dateTime.setFull_time(start.format(fullTimeFormat));
				dateTime.setCategory_time(start.format(categoryTimeFormat));
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
	 * @since 2024-04-05
	 * @param obj { id, datatablename, timezone_value, id_filter, start_date, end_date, data_send_time }
	 * @return
	 */
	public List<ScadaDeviceChartDataEntity> getChartData(ScadaDeviceEntity obj) {
		try {
			List<ScadaDeviceChartDataEntity> dataList = new ArrayList<ScadaDeviceChartDataEntity>();
			
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")).withHour(0).withMinute(0).withSecond(0);
			obj.setStart_date(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")).withHour(23).withMinute(59).withSecond(59);
			obj.setEnd_date(end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			dataList = queryForList("ScadaDevice.getChartData", obj);
			if (dataList == null) return new ArrayList<ScadaDeviceChartDataEntity>();
			return fulfillData(getDateTimeList(obj, start, end), dataList);
		} catch (Exception ex) {
			return new ArrayList<ScadaDeviceChartDataEntity>();
		}
	}
	
	/**
	 * @description get alarms list by device
	 * @author Hung.Bui
	 * @since 2024-04-12
	 * @param obj { hash_id_site, modbusdevicenumber }
	 * @return
	 */
	public List<ScadaDeviceAlarmEntity> getActiveAlarmsListByDevice(ScadaDeviceEntity obj) {
		try {
			List<ScadaDeviceAlarmEntity> dataList = queryForList("ScadaDevice.getActiveAlarmsListByDevice", obj);
			if (dataList == null) return new ArrayList<ScadaDeviceAlarmEntity>();
			return dataList;
		} catch (Exception ex) {
			return new ArrayList<ScadaDeviceAlarmEntity>();
		}
	}
	
	/**
	 * @description get device detail
	 * @author Hung.Bui
	 * @since 2024-04-12
	 * @param obj { hash_id_site, modbusdevicenumber }
	 * @return
	 */
	public ScadaDeviceEntity getDeviceDetail(ScadaDeviceEntity obj) {
		try {
			return (ScadaDeviceEntity) queryForObject("ScadaDevice.getDeviceDetail", obj);
		} catch (Exception ex) {
			return null;
		}
	}

}
