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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.KioskViewTodayEntity;
import com.nwm.api.entities.MiniSiteRequest;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.Constants.ChartingFilter;

public class MiniSiteService extends DB {

	
	/**
	 * @description get mini site detail
	 * @author long.pham
	 * @since 2020-11-02
	 * @param id_site
	 * @return Object
	 */

	public Object getMiniSiteInfo(MiniSiteRequest obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		SiteEntity siteEntity = mapper.convertValue(obj, SiteEntity.class);
		siteEntity.setKiosk_view(1);
		
		CustomerViewService customerViewService = new CustomerViewService();
		return customerViewService.getCustomerViewInfo(siteEntity);
	}
	
	/**
	 * @description get monthly report
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */

	public Object getChartPerformance(MiniSiteRequest obj) {
		try {
			// ----- Create DateTime List ----- Begin
			DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:00");
			DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH:00");
			ChronoUnit timeUnit = ChronoUnit.HOURS;
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).withHour(0).withMinute(0).withSecond(0);
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).withHour(23).withMinute(59).withSecond(59);
			
			switch (ChartingFilter.fromValue(obj.getFilterBy())) {
				case TODAY:
					timeUnit = ChronoUnit.HOURS;
            		categoriesTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:00");
            		obj.setData_send_time(3);
            		break;
				case THIS_MONTH:
				case LAST_MONTH:
					start = start.withDayOfMonth(1);
					end = end.with(TemporalAdjusters.lastDayOfMonth());
					timeUnit = ChronoUnit.DAYS;
					categoriesTimeFormat = DateTimeFormatter.ofPattern("MM/dd");
					obj.setData_send_time(4);
					break;
				case LAST_12_MONTHS:
					start = start.withDayOfMonth(1);
					timeUnit = ChronoUnit.MONTHS;
            		categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM-yyyy");
            		obj.setData_send_time(6);
					break;
				case LIFETIME:
					start = start.withDayOfYear(1);
					timeUnit = ChronoUnit.YEARS;
            		categoriesTimeFormat = DateTimeFormatter.ofPattern("yyyy");
            		obj.setData_send_time(7);
					break;
				default:
					break;
			}
			
			obj.setStart_date(start.format(inputFormat));
			obj.setEnd_date(end.format(inputFormat));
			
			List<KioskViewTodayEntity> categories = new ArrayList<>();
			while (!start.isAfter(end)) {
				KioskViewTodayEntity dateTime = new KioskViewTodayEntity();
				dateTime.setCategories_time(start.format(categoriesTimeFormat));
				if (ChartingFilter.fromValue(obj.getFilterBy()) == ChartingFilter.TODAY) dateTime.setHour_time(start.format(hourFormat));
				categories.add(dateTime);
				start = start.plus(1, timeUnit);
			}
			// ----- Create DateTime List ----- End
			
			if(obj.getDevice_mode() == 2) {
				// get List device meter, inverter, weather station 
				List devices = queryForList("MiniSite.getListDeviceMeterInverterWeather", obj);
				List datas = new ArrayList();
				
				if(devices.size() > 0) {
					for(int i = 0; i < devices.size(); i++) {
						Map<String, Object> item = (Map<String, Object>) devices.get(i);
						item.put("start_date", obj.getStart_date());
						item.put("end_date", obj.getEnd_date());
						item.put("filterBy", obj.getFilterBy());
						List<KioskViewTodayEntity> dataDevice = queryForList("MiniSite.getDataByDevice", item);
						List<KioskViewTodayEntity> fulfilledData = Lib.fulfillData(categories, dataDevice, "categories_time");
						if(dataDevice.size() > 0) {
							item.put("datas", fulfilledData);
						}
						datas.add(item);
					}
					
				}
				
				obj.setEnergy(datas);

			} else {
				List dataListDeviceIrr = queryForList("MiniSite.getListDeviceTypeIrradiance", obj);
				if (dataListDeviceIrr != null && dataListDeviceIrr.size() > 0) obj.setHave_poa(true);
				if (obj.getEnable_virtual_device() == 0) {
					List dataListDeviceMeter = queryForList("MiniSite.getListDeviceTypeMeter", obj);			
					List dataListDevicePower = dataListDeviceMeter.size() > 0 ? dataListDeviceMeter : queryForList("MiniSite.getListDeviceTypeInverter", obj);
					if (dataListDevicePower.size() > 0) {
						if (dataListDeviceIrr != null && dataListDeviceIrr.size() > 0) dataListDevicePower.addAll(dataListDeviceIrr);
						obj.setGroupDevices(dataListDevicePower);
					}
				}
				
				List<KioskViewTodayEntity> dataEnergy = obj.getEnable_virtual_device() == 1 ? queryForList("MiniSite.getDataVirtualDevice", obj) : queryForList("MiniSite.getDataEnergy", obj);
				List<KioskViewTodayEntity> fulfilledData = Lib.fulfillData(categories, dataEnergy, "categories_time");
				if (fulfilledData.size() > 0) obj.setEnergy(fulfilledData);
			}
			
			
			
			return obj;
		} catch (Exception ex) {
			return null;
		}
	}

	public List<SiteEntity> getListSiteAutoChange(SiteEntity obj) {
        try {
            List<Integer> companyUseAutoChange = Arrays.asList(2, 3, 146, 147);
            SiteEntity entity = (SiteEntity) queryForObject("MiniSite.getCompanyBySiteHash", obj);
            if (entity == null) {
                return new ArrayList<>();
            }
            if (companyUseAutoChange.stream().anyMatch(x -> x == entity.getId_company())) {
                List<SiteEntity> dataList = queryForList("MiniSite.getListSiteAutoChange", entity);
                if (dataList == null) {
                    return new ArrayList<>();
                }
                return dataList;
            }
        } catch (Exception e) {
            log.error("MiniSiteService.getListSiteAutoChange", e);
        }
        return new ArrayList<>();
    }
	
}
