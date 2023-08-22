/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	 * @description get monthly report
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */

	public Object getChartPerformance(SiteEntity obj) {
		try {

			List dataListDeviceMeter = queryForList("MiniSite.getListDeviceTypeMeter", obj);
			if (dataListDeviceMeter.size() > 0) {
				obj.setGroupDevices(dataListDeviceMeter);
				List dataEnergy = queryForList("MiniSite.getDataEnergyMeter", obj);
				if (dataEnergy.size() > 0) {
					obj.setEnergy(dataEnergy);
				}
			} else {
				List dataListInverter = queryForList("MiniSite.getListDeviceTypeInverter", obj);
				if (dataListInverter.size() > 0) {
					obj.setGroupDevices(dataListInverter);
					List dataEnergy = queryForList("MiniSite.getDataEnergyInverter", obj);
					if (dataEnergy.size() > 0) {
						obj.setEnergy(dataEnergy);
					}
				}
			}

			// Create list date
			List<KioskViewTodayEntity> categories = new ArrayList<KioskViewTodayEntity>();

			switch (obj.getFilterBy()) {
				case "today":
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");
					SimpleDateFormat catFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm aaa");
					SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH:00");
					Date startDate = dateFormat.parse(obj.getStart_date() + " AM");
					Calendar cal = Calendar.getInstance();
					cal.setTime(startDate);
					
					for (int t = 0; t < 24; t++) {
						cal.setTime(startDate);
						KioskViewTodayEntity headerDate = new KioskViewTodayEntity();
						cal.add(Calendar.HOUR, t);
						headerDate.setTime_format(catFormat.format(cal.getTime()));
						String hours = dateFormatHour.format(cal.getTime());
						headerDate.setCategories_time(catFormat.format(cal.getTime()));
						headerDate.setEnergy(0.001);
						headerDate.setPower(0.001);
						headerDate.setIrradiance(0.001);
						headerDate.setHour_time(hours);
						categories.add(headerDate);
					}
					break;
				case "this_month":
				case "last_month":
					SimpleDateFormat dateM = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");
					Date startDateM = dateM.parse(obj.getStart_date() + " AM");
					Calendar calM = Calendar.getInstance();
					calM.setTime(startDateM);
					calM.add(Calendar.MONTH, 1);  
					calM.set(Calendar.DAY_OF_MONTH, 1);  
					calM.add(Calendar.DATE, -1);
					Date lastDayOfMonth = calM.getTime();  

			        SimpleDateFormat lastofmonthFormat = new SimpleDateFormat("dd");
			        System.out.println("Last Day of Month: " + lastofmonthFormat.format(lastDayOfMonth)); 
			        int lofmonth = Integer.parseInt(lastofmonthFormat.format(lastDayOfMonth));
			        
			        SimpleDateFormat catMFormat = new SimpleDateFormat("MM/dd");
			        Calendar calA = Calendar.getInstance();
					calA.setTime(startDateM);
			        for (int t = 0; t < lofmonth; t++) {
						calA.setTime(startDateM);
						KioskViewTodayEntity headerDate = new KioskViewTodayEntity();
						calA.add(Calendar.DATE, t);
						headerDate.setTime_format(catMFormat.format(calA.getTime()));
						headerDate.setCategories_time(catMFormat.format(calA.getTime()));
						headerDate.setEnergy(0.001);
						headerDate.setPower(0.001);
						headerDate.setIrradiance(0.001);
						headerDate.setHour_time("0");
						categories.add(headerDate);
					}
			        
					break;
					
				case "12_month":
					SimpleDateFormat date12M = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");
					Date startDate12M = date12M.parse(obj.getStart_date() + " AM");
					Calendar cal12M = Calendar.getInstance();
					cal12M.setTime(startDate12M);
					cal12M.add(Calendar.MONTH, 1);  
					cal12M.set(Calendar.DAY_OF_MONTH, 1);  
					cal12M.add(Calendar.DATE, -1);
			        SimpleDateFormat cat12MFormat = new SimpleDateFormat("MMM-yyyy");
			        Calendar cal12A = Calendar.getInstance();
					cal12A.setTime(startDate12M);
			        for (int t = 0; t < 12; t++) {
						cal12A.setTime(startDate12M);
						KioskViewTodayEntity headerDate = new KioskViewTodayEntity();
						cal12A.add(Calendar.MONTH, t);
						headerDate.setTime_format(cat12MFormat.format(cal12A.getTime()));
						headerDate.setCategories_time(cat12MFormat.format(cal12A.getTime()));
						headerDate.setEnergy(0.001);
						headerDate.setPower(0.001);
						headerDate.setIrradiance(0.001);
						headerDate.setHour_time(cat12MFormat.format(cal12A.getTime()));
						categories.add(headerDate);
					}
			        
					break;
					
				case "lifetime":
					SimpleDateFormat dateYM = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aaa");
					Date startDateYM = dateYM.parse(obj.getStart_date() + " AM");
					Date endDateYM = dateYM.parse(obj.getEnd_date() + " AM");
					Calendar calYM = Calendar.getInstance();
					calYM.setTime(startDateYM);
					int startYear = calYM.get(Calendar.YEAR);
					
					Calendar calYMEnd = Calendar.getInstance();
					calYMEnd.setTime(endDateYM);
					int endYear = calYMEnd.get(Calendar.YEAR);
					int countYear = endYear - startYear + 1;
		
			        SimpleDateFormat catYMFormat = new SimpleDateFormat("yyyy");
			        Calendar calYA = Calendar.getInstance();
					calYA.setTime(startDateYM);
			        for (int t = 0; t < countYear; t++) {
						calYA.setTime(startDateYM);
						KioskViewTodayEntity headerDate = new KioskViewTodayEntity();
						calYA.add(Calendar.YEAR, t);
						headerDate.setTime_format(catYMFormat.format(calYA.getTime()));
						headerDate.setCategories_time(catYMFormat.format(calYA.getTime()));
						headerDate.setEnergy(0.001);
						headerDate.setPower(0.001);
						headerDate.setIrradiance(0.001);
						headerDate.setHour_time(catYMFormat.format(calYA.getTime()));
						categories.add(headerDate);
					}
			        
					break;
			}

			List dataEnergy = obj.getEnergy();
			List<KioskViewTodayEntity> dataNewPower = new ArrayList<KioskViewTodayEntity>();
			if (categories.size() > 0) {
				for (KioskViewTodayEntity item : categories) {
					boolean flag = false;
					KioskViewTodayEntity mapItemObj = new KioskViewTodayEntity();
					if (dataEnergy != null && dataEnergy.size() > 0) {
						for (int v = 0; v < dataEnergy.size(); v++) {
							Map<String, Object> itemT = (Map<String, Object>) dataEnergy.get(v);
							String categoriesTime = item.getTime_format();
							String powerTime = itemT.get("categories_time").toString();
							if (categoriesTime.equals(powerTime)) {
								flag = true;
								mapItemObj.setCategories_time(itemT.get("categories_time").toString());
								Double energy = Double.parseDouble(itemT.get("nvmActiveEnergy").toString());
								energy = (energy == -0.0) ? 0 : energy;

								mapItemObj.setEnergy(energy);
								mapItemObj.setIrradiance(item.getIrradiance());
								mapItemObj.setTime_format(itemT.get("categories_time").toString());
								mapItemObj.setHour_time(itemT.get("hour_time").toString());
								break;
							}
						}
					}

					if (flag == false) {
						KioskViewTodayEntity mapItem = new KioskViewTodayEntity();
						mapItem.setCategories_time(item.getCategories_time());
						mapItem.setTime_format(item.getTime_format());
						mapItem.setHour_time(item.getHour_time());
						mapItem.setIrradiance(item.getIrradiance());
						mapItem.setEnergy(item.getEnergy());
						mapItem.setPower(item.getPower());

						dataNewPower.add(mapItem);
					} else {
						dataNewPower.add(mapItemObj);
					}
				}
			}

			obj.setEnergy(dataNewPower);
			
			// get irradiance 
			List dataListDeviceIrr = queryForList("MiniSite.getListDeviceTypeIrradiance", obj);
			if (dataListDeviceIrr.size() > 0) {
				obj.setGroupDevices(dataListDeviceIrr);
				List dataIrradiance = queryForList("MiniSite.getDataIrradiance", obj);
				if(dataNewPower.size() > 0) {
					List<KioskViewTodayEntity> dataNewIrr = new ArrayList<KioskViewTodayEntity> ();
					for (KioskViewTodayEntity item : dataNewPower) {
						for( int v = 0; v < dataIrradiance.size(); v++){
							Map<String, Object> itemT = (Map<String, Object>) dataIrradiance.get(v);
							String categoriesTime = item.getTime_format();
							String powerTime = itemT.get("categories_time").toString();
					        if (categoriesTime.equals(powerTime)) {
					        	item.setIrradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()));
					        	break;
					        }
					    }
						dataNewIrr.add(item);
					}
					obj.setIrradiance(dataNewIrr);
				}
			}
					
			return obj;
		} catch (Exception ex) {
			return null;
		}
	}

	
	
}
