/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.KioskViewTodayEntity;
import com.nwm.api.entities.SiteEntity;

public class MiniSiteService extends DB {

	
	/**
	 * @description get mini site detail
	 * @author long.pham
	 * @since 2020-11-02
	 * @param id_site
	 * @return Object
	 */

	public Object getMiniSiteInfo(SiteEntity obj) {
		Object dataObj = null;
		try {
			dataObj = queryForObject("MiniSite.getMiniSiteInfo", obj);
			if (dataObj == null)
				return new SiteEntity();
		} catch (Exception ex) {
			return new SiteEntity();
		}
		return dataObj;
	}
	
	/**
	 * @description fulfill data in specific range of time
	 * @author Hung.Bui
	 * @since 2024-03-20
	 * @param List<KioskViewTodayEntity> dateTimeList
	 * @param List<KioskViewTodayEntity> dataList
	 */
	private List<KioskViewTodayEntity> fulfillData(List<KioskViewTodayEntity> dateTimeList, List<KioskViewTodayEntity> dataList) {
		List<KioskViewTodayEntity> fulfilledDataList = new ArrayList<KioskViewTodayEntity>();
		
		try {
			if(dataList.size() > 0 && dateTimeList.size() > 0) {
				boolean firstCategory = false;
				for (KioskViewTodayEntity dateTime: dateTimeList) {
					boolean isFound = false;
					
					for(KioskViewTodayEntity data: dataList) {
						String timeFull = dateTime.getCategories_time();
						String powerTime = data.getCategories_time();
						
						if (timeFull.equals(powerTime)) {
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
	 * @description get monthly report
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */

	public Object getChartPerformance(SiteEntity obj) {
		try {
			// ----- Create DateTime List ----- Begin
			DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:00");
			DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH:00");
			ChronoUnit timeUnit = ChronoUnit.HOURS;
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).withHour(0).withMinute(0).withSecond(0);
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).withHour(23).withMinute(59).withSecond(59);
			
			switch (obj.getFilterBy()) {
				case "today":
					timeUnit = ChronoUnit.HOURS;
            		categoriesTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:00");
            		break;
				case "this_month":
				case "last_month":
					start = start.withDayOfMonth(1);
					end = end.with(TemporalAdjusters.lastDayOfMonth());
					timeUnit = ChronoUnit.DAYS;
					categoriesTimeFormat = DateTimeFormatter.ofPattern("MM/dd");
					break;
				case "12_month":
					start = start.withDayOfMonth(1);
					timeUnit = ChronoUnit.MONTHS;
            		categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM-yyyy");
					break;
				case "lifetime":
					end = end.with(TemporalAdjusters.lastDayOfYear());
					timeUnit = ChronoUnit.YEARS;
            		categoriesTimeFormat = DateTimeFormatter.ofPattern("yyyy");
					break;
			}
			
			obj.setStart_date(start.format(inputFormat));
			obj.setEnd_date(end.format(inputFormat));
			
			List<KioskViewTodayEntity> categories = new ArrayList<>();
			while (!start.isAfter(end)) {
				KioskViewTodayEntity dateTime = new KioskViewTodayEntity();
				dateTime.setCategories_time(start.format(categoriesTimeFormat));
				if (obj.getFilterBy().equals("today")) dateTime.setHour_time(start.format(hourFormat));
				categories.add(dateTime);
				start = start.plus(1, timeUnit);
			}
			// ----- Create DateTime List ----- End
			
			List dataListDeviceIrr = queryForList("MiniSite.getListDeviceTypeIrradiance", obj);
			if (dataListDeviceIrr != null && dataListDeviceIrr.size() > 0) obj.setHave_poa(true);
			if (obj.getEnable_virtual_device() == 0 && obj.getFilterBy().equals("today")) {
				List dataListDeviceMeter = queryForList("MiniSite.getListDeviceTypeMeter", obj);
				List dataListDevicePower = dataListDeviceMeter.size() > 0 ? dataListDeviceMeter : queryForList("MiniSite.getListDeviceTypeInverter", obj);
				if (dataListDevicePower.size() > 0) {
					if (dataListDeviceIrr != null && dataListDeviceIrr.size() > 0) dataListDevicePower.addAll(dataListDeviceIrr);
					obj.setGroupDevices(dataListDevicePower);
				}
			}
			
			List<KioskViewTodayEntity> dataEnergy = obj.getEnable_virtual_device() == 1 ? queryForList("MiniSite.getDataVirtualDevice", obj) : queryForList("MiniSite.getDataEnergy", obj);
			List<KioskViewTodayEntity> fulfilledData = fulfillData(categories, dataEnergy);
			if (fulfilledData.size() > 0) obj.setEnergy(fulfilledData);
			
			return obj;
		} catch (Exception ex) {
			return null;
		}
	}

	
	
}
