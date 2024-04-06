/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ScadaDeviceChartDataEntity;
import com.nwm.api.entities.ScadaDeviceEntity;

public class ScadaDeviceService extends DB {

	/**
	 * @description get Scada devices list by site
	 * @author Hung.Bui
	 * @since 2024-03-26
	 * @param id_site
	 * @return List<ScadaDeviceEntity>
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
	 * @param List<ScadaDeviceChartDataEntity> dateTimeList
	 * @param List<ScadaDeviceChartDataEntity> dataList
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
	 * @description get chart data
	 * @author Hung.Bui
	 * @since 2024-04-05
	 * @param id_site
	 * @param modbusdevicenumber
	 * @return List<ScadaDeviceChartDataEntity>
	 */
	public List<ScadaDeviceChartDataEntity> getChartData(ScadaDeviceEntity obj) {
		try {
			List<ScadaDeviceChartDataEntity> dataList = new ArrayList<ScadaDeviceChartDataEntity>();
			
			Map<String, String> device = (HashMap<String, String>) queryForObject("ScadaDevice.getDeviceDetail", obj);
			if (device == null) return new ArrayList<ScadaDeviceChartDataEntity>();
			
			LocalDateTime end = LocalDateTime.now().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of(device.get("time_zone_value"))).toLocalDateTime().withHour(23).withMinute(59).withSecond(59);
			device.put("end_datetime", end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			LocalDateTime start = end.minusDays(2).withHour(0).withMinute(0).withSecond(0);
			device.put("start_datetime", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			List<ScadaDeviceChartDataEntity> dateTimeList = new ArrayList<>();
			while (!start.isAfter(end)) {
				ScadaDeviceChartDataEntity dateTime = new ScadaDeviceChartDataEntity();
				dateTime.setFull_time(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
				dateTime.setCategory_time(start.format(DateTimeFormatter.ofPattern("HH:mm dd. LLL")));
				dateTimeList.add(dateTime);
				start = start.plusMinutes(5);
			}
			
			dataList = queryForList("ScadaDevice.getChartData", device);
			if (dataList == null) return new ArrayList<ScadaDeviceChartDataEntity>();
			return fulfillData(dateTimeList, dataList);
		} catch (Exception ex) {
			return new ArrayList<ScadaDeviceChartDataEntity>();
		}
	}

}
