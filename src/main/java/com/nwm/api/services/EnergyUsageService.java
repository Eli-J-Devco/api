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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Lib;

public class EnergyUsageService extends DB {
	
	/**
	 * @description get chart data energy
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id_site, id_customer
	 */

	public List getChartDataEnergyUsage(SiteEntity obj) {
		try {
			List dataEnergy = new ArrayList<>();
			List dataListDeviceMeter = queryForList("EnergyUsage.getListDeviceTypeMeter", obj);
			if(dataListDeviceMeter.size() > 0 ) {
				obj.setGroupMeter(dataListDeviceMeter);
				int interval = 0;
				DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
				DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
				ChronoUnit timeUnit = ChronoUnit.MINUTES;
				LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				// get Energy usage 
				List<ClientMonthlyDateEntity> dataEnergyUsage = new ArrayList<>();
				dataEnergyUsage = queryForList("EnergyUsage.getDataEnergyUsage", obj);
				switch (obj.getId_filter()) {
					case "hourly":
						interval = 1;
						timeUnit = ChronoUnit.HOURS;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
						categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
						if(!"today".equals(obj.getFilterBy() )) {
							timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
							categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
						}
						break;
					case "day":
						interval = 1;
						timeUnit = ChronoUnit.DAYS;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
						categoriesTimeFormat = DateTimeFormatter.ofPattern("dd. LLL");
						break;
					case "month":
						interval = 1;
						timeUnit = ChronoUnit.MONTHS;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-yyyy");
						categoriesTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
						break;
					default:
						interval = 1;
						timeUnit = ChronoUnit.DAYS;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
						categoriesTimeFormat = DateTimeFormatter.ofPattern("dd. LLL");
						break;
				}
				
				
				List<ClientMonthlyDateEntity> dateTimeList = new ArrayList<>();
				while (!start.isAfter(end)) {
					ClientMonthlyDateEntity dateTime = new ClientMonthlyDateEntity();
					dateTime.setTime_full(start.format(timeFullFormat));
					dateTime.setCategories_time(start.format(categoriesTimeFormat));
					dateTimeList.add(dateTime);
					start = start.plus(interval, timeUnit);
				}
				
				
				List<ClientMonthlyDateEntity> fulfilledData = Lib.fulfillData(dateTimeList, dataEnergyUsage, "time_full");
				if (fulfilledData.size() > 0) {
					Map<String, Object> deviceItem = new HashMap<>();
					deviceItem.put("data_energy", fulfilledData);
					deviceItem.put("type", "consumption");
					deviceItem.put("devicename", "Consumption");
					dataEnergy.add(deviceItem);
				}
			}
			
			return dataEnergy;
		} catch (Exception ex) {
			return new ArrayList();
		}

	}
	
	
	
	/**
	 * @description get chart data energy by device id
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id_device
	 */

	public List getChartDataEnergyByDevice(DeviceEntity obj) {
		try {
			List dataEnergy = new ArrayList<>();
			int interval = 0;
			DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
			DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
			ChronoUnit timeUnit = ChronoUnit.MINUTES;
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
			// get Energy usage 
			List<ClientMonthlyDateEntity> dataEnergyUsage = new ArrayList<>();
			dataEnergyUsage = queryForList("EnergyUsage.getDataEnergyUsageByDevice", obj);
			switch (obj.getFilterBy()) {
				case "today": // 1 hour
					interval = 1;
					timeUnit = ChronoUnit.HOURS;
					timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
					break;
					
				case "this_week": // 1 day
				case "last_week":
				case "this_month":
				case "last_month":
					interval = 1;
					timeUnit = ChronoUnit.DAYS;
					timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("dd. LLL");
					break;
					
				case "12_month":
					interval = 1;
					timeUnit = ChronoUnit.MONTHS;
					timeFullFormat = DateTimeFormatter.ofPattern("MM-yyyy");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
					start = start.withDayOfMonth(1);
					break;
					
				case "lifetime": // 1 month
					interval = 1;
					timeUnit = ChronoUnit.MONTHS;
					timeFullFormat = DateTimeFormatter.ofPattern("MM-yyyy");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM. yyyy");
					start = start.withDayOfMonth(1);
					break;
					
					
			}
			
			List<ClientMonthlyDateEntity> dateTimeList = new ArrayList<>();
			while (!start.isAfter(end)) {
				ClientMonthlyDateEntity dateTime = new ClientMonthlyDateEntity();
				dateTime.setTime_full(start.format(timeFullFormat));
				dateTime.setCategories_time(start.format(categoriesTimeFormat));
				dateTimeList.add(dateTime);
				start = start.plus(interval, timeUnit);
			}
			
			
			List<ClientMonthlyDateEntity> fulfilledData = Lib.fulfillData(dateTimeList, dataEnergyUsage, "time_full");
			if (fulfilledData.size() > 0) {
				Map<String, Object> deviceItem = new HashMap<>();
				deviceItem.put("data_energy", fulfilledData);
				deviceItem.put("type", "consumption");
				deviceItem.put("devicename", "Consumption");
				dataEnergy.add(deviceItem);
			}
			
			return dataEnergy;
		} catch (Exception ex) {
			return new ArrayList();
		}

	}

}
