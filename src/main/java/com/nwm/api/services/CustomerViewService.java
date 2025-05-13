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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.PerformanceDataChartItemEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SecretCards;

public class CustomerViewService extends DB {
	
	SitesAnalyticsService sitesAnalyticsService = new SitesAnalyticsService();
	
	private List<ClientMonthlyDateEntity> convertDateTimeFormat(SiteEntity obj, List<ClientMonthlyDateEntity> dataList, LocalDateTime start, LocalDateTime end) {
		try {
			DeviceEntity chartParams = new DeviceEntity();
			chartParams.setData_send_time(obj.getData_send_time());
			chartParams.setFilterBy(obj.getFilterBy());
			chartParams.setDate_format(obj.getDate_format());
			chartParams.setTime_format(obj.getTime_format());
			chartParams.setLocale(obj.getLocale());
			
			List<Map<String, Object>> data = dataList
					.stream()
					.map(item -> ClientMonthlyDateEntity.convertDateTimeToMap(item))
					.collect(Collectors.toList());
			
			List<ClientMonthlyDateEntity> convertedDateTimeList = sitesAnalyticsService.convertDateTimeFormat(chartParams, data, start, end)
					.stream()
					.map(item -> ClientMonthlyDateEntity.convertDateTimeToEntity(item))
					.collect(Collectors.toList());
			
			for (int i = 0; i < dataList.size(); i++) {
				ClientMonthlyDateEntity item = dataList.get(i);
				ClientMonthlyDateEntity convertedItem = convertedDateTimeList.get(i);
				item.setTime_full(convertedItem.getTime_full());
				item.setCategories_time(convertedItem.getCategories_time());
			}
		} catch (Exception e) {
		}
		
		return dataList;
	}
	
	private List<ClientMonthlyDateEntity> getDateTimeList(SiteEntity obj, LocalDateTime start, LocalDateTime end) {
		try {
			DeviceEntity chartParams = new DeviceEntity();
			chartParams.setData_send_time(obj.getData_send_time());
			chartParams.setFilterBy(obj.getFilterBy());
			
			return sitesAnalyticsService
					.getDateTimeList(chartParams, start, end)
					.stream()
					.map(item -> ClientMonthlyDateEntity.convertDateTimeToEntity(item))
					.collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<ClientMonthlyDateEntity>();
		}
	}
	
	/**
	 * @description get chart data energy
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id_site, id_customer
	 */

	public List<PerformanceDataChartItemEntity> getChartDataPerformance(SiteEntity obj) {
		try {
			List<PerformanceDataChartItemEntity> dataEnergy = new ArrayList<>();
			
			List<DeviceEntity> devices = queryForList("CustomerView.getDevicesBySite", obj);
			List<DeviceEntity> meterDevices = devices.stream().filter(item -> (item.getId_device_type() == 3 || item.getId_device_type() == 7 || item.getId_device_type() == 9) && !item.isIs_excluded_meter()).collect(Collectors.toList());
			List<DeviceEntity> inverterDevices = devices.stream().filter(item -> (item.getId_device_type() == 1)).collect(Collectors.toList());
			List<DeviceEntity> irradianceDevices = devices.stream().filter(item -> (item.getId_device_type() == 4) && item.getReverse_poa() == 0).collect(Collectors.toList());
			List<DeviceEntity> powerDevices = meterDevices.size() > 0 ? meterDevices : inverterDevices;
			if (powerDevices.size() == 0) return new ArrayList<>();
			
			// get date time list
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
			// get list of time to exclude data from
			List<Map<String, Object>> hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
			obj.setHidden_data_list(hiddenDataList);
			
			boolean isPower = ChronoUnit.DAYS.between(start, end) < 5;
			boolean isGranularityLessThan1Day = Arrays.asList(new Integer[] {8,1,2,3}).stream().anyMatch(item -> item == obj.getData_send_time());
			
			// Show each meter
			if (meterDevices.size() > 1 && obj.getIs_show_each_meter() == 1) {
				obj.setGroupMeter(meterDevices);
				List<ClientMonthlyDateEntity> dataList = isGranularityLessThan1Day ? queryForList("CustomerView.getDataEnergy", obj) : queryForList("CustomerView.getDataSiteDataReport", obj);
				
				for (int i = 0; i < meterDevices.size(); i++) {
					DeviceEntity device = meterDevices.get(i);
					List<ClientMonthlyDateEntity> dataItem = dataList.stream().filter(item -> item.getId() == device.getId()).collect(Collectors.toList());
					List<ClientMonthlyDateEntity> fulfilledData = convertDateTimeFormat(obj, Lib.fulfillData(getDateTimeList(obj, start, end), dataItem, "time_full"), start, end);
					
					if (fulfilledData.size() > 0) {
						PerformanceDataChartItemEntity deviceItem = new PerformanceDataChartItemEntity(fulfilledData, "chart_energy_kwh", isPower ? "kW" : "kWh", device.getDevicename(), true);
						dataEnergy.add(deviceItem);
					}
				}
			}
			
			obj.setIs_show_each_meter(0);
			if (obj.getEnable_virtual_device() == 1) {
				obj.setDatatablename(obj.getTable_data_virtual());
				List<ClientMonthlyDateEntity> dataList = queryForList("CustomerView.getDataVirtualDevice", obj);
				List<ClientMonthlyDateEntity> fulfilledData = convertDateTimeFormat(obj, Lib.fulfillData(getDateTimeList(obj, start, end), dataList, "time_full"), start, end);
				if (fulfilledData.size() > 0) separateDataByType(dataEnergy, obj, fulfilledData, irradianceDevices, isPower);
			} else {
				if (isGranularityLessThan1Day) {
					if (powerDevices.size() > 0) {
						obj.setGroupMeter(powerDevices);
						List<ClientMonthlyDateEntity> dataList = queryForList("CustomerView.getDataEnergy", obj);
						List<ClientMonthlyDateEntity> fulfilledData = convertDateTimeFormat(obj, Lib.fulfillData(getDateTimeList(obj, start, end), dataList, "time_full"), start, end);
						
						if (fulfilledData.size() > 0) {
							PerformanceDataChartItemEntity energyData = new PerformanceDataChartItemEntity(fulfilledData, "chart_energy_kwh", isPower ? "kW" : "kWh", isPower ? "Power" : "Energy Output");
							dataEnergy.add(energyData);
						}
					}
					
					if (irradianceDevices.size() > 0) {
						for(int i = 0; i < irradianceDevices.size(); i++) {
							DeviceEntity item = irradianceDevices.get(i);
							obj.setDatatablename(item.getDatatablename());
							obj.setId_device(item.getId());
							
							List<ClientMonthlyDateEntity> dataList = queryForList("CustomerView.getDataIrradiance", obj);
							List<ClientMonthlyDateEntity> fulfilledData = convertDateTimeFormat(obj, Lib.fulfillData(getDateTimeList(obj, start, end), dataList, "time_full"), start, end);
							
							if(fulfilledData.size() > 0 ) {
								if (i == 0) {
									PerformanceDataChartItemEntity expectedData = new PerformanceDataChartItemEntity(fulfilledData, isPower ? "expected_power" : "expected_energy", isPower ? "kW" : "kWh", (isPower ? "Expected Power" : "Expected Energy") + (obj.getPv_model() == 3 ? " NREL 8760" : ""));
									dataEnergy.add(expectedData);
								}
								
								PerformanceDataChartItemEntity irradianceData = new PerformanceDataChartItemEntity(fulfilledData, "nvm_irradiance", "W/m²", irradianceDevices.size() > 1 ? irradianceDevices.get(i).getDevicename() : "Irradiance");
								dataEnergy.add(irradianceData);
							}
						}
					}
				} else {
					obj.setTotalMeter(meterDevices.size());
					List<ClientMonthlyDateEntity> dataList = queryForList("CustomerView.getDataSiteDataReport", obj);
					List<ClientMonthlyDateEntity> fulfilledData = convertDateTimeFormat(obj, Lib.fulfillData(getDateTimeList(obj, start, end), dataList, "time_full"), start, end);
					if (fulfilledData.size() > 0) separateDataByType(dataEnergy, obj, fulfilledData, irradianceDevices, isPower);
				}
			}

			return dataEnergy;
		} catch (Exception ex) {
			return new ArrayList<>();
		}

	}
	
	private void separateDataByType(List<PerformanceDataChartItemEntity> dataEnergy, SiteEntity obj, List<ClientMonthlyDateEntity> data , List<DeviceEntity> irradianceDevices, boolean isPower) {
		List<ClientMonthlyDateEntity> energy = new ArrayList<>();
		List<ClientMonthlyDateEntity> expected = new ArrayList<>();
		List<ClientMonthlyDateEntity> irradiance = new ArrayList<>();
		
		for (ClientMonthlyDateEntity item : data) {
			ClientMonthlyDateEntity energyItem = new ClientMonthlyDateEntity();
			energyItem.setTime_full(item.getTime_full());
			energyItem.setCategories_time(item.getCategories_time());
			energyItem.setChart_energy_kwh(item.getChart_energy_kwh());
			energyItem.setNvmActivePower(item.getNvmActivePower());
			energyItem.setNvmActiveEnergy(item.getNvmActiveEnergy());
			energy.add(energyItem);
			
			ClientMonthlyDateEntity expectedItem = new ClientMonthlyDateEntity();
			expectedItem.setTime_full(item.getTime_full());
			expectedItem.setCategories_time(item.getCategories_time());
			expectedItem.setExpected_power(item.getExpected_power());
			expectedItem.setExpected_energy(item.getExpected_energy());
			expected.add(expectedItem);
			
			ClientMonthlyDateEntity irradianceItem = new ClientMonthlyDateEntity();
			irradianceItem.setTime_full(item.getTime_full());
			irradianceItem.setCategories_time(item.getCategories_time());
			irradianceItem.setNvm_irradiance(item.getNvm_irradiance());
			irradiance.add(irradianceItem);
		}
		
		PerformanceDataChartItemEntity energyData = new PerformanceDataChartItemEntity(energy, "chart_energy_kwh", isPower ? "kW" : "kWh", isPower ? "Power" : "Energy Output");
		dataEnergy.add(energyData);
		
		if (irradianceDevices.size() > 0) {
			PerformanceDataChartItemEntity expectedData = new PerformanceDataChartItemEntity(expected, isPower ? "expected_power" : "expected_energy", isPower ? "kW" : "kWh", (isPower ? "Expected Power" : "Expected Energy") + (obj.getPv_model() == 3 ? " NREL 8760" : ""));
			dataEnergy.add(expectedData);
			
			PerformanceDataChartItemEntity irradianceData = new PerformanceDataChartItemEntity(irradiance, "nvm_irradiance", "W/m²", "Irradiance");
			dataEnergy.add(irradianceData);
		}
	}
	
	/**
	 * @description get customer view site info
	 * @author long.pham
	 * @since 2020-12-02
	 * @param id_site, id_customer
	 * @return Object
	 */

	public Object getCustomerViewInfo(SiteEntity obj) {
		try {
			List<DeviceEntity> devices = queryForList("CustomerView.getDevicesBySite", obj);
			List<DeviceEntity> meterDevices = devices.stream().filter(item -> (item.getId_device_type() == 3 || item.getId_device_type() == 7 || item.getId_device_type() == 9) && !item.isIs_excluded_meter()).collect(Collectors.toList());
			obj.setMeter_type(meterDevices.size() > 0 ? 1 : 0);
			
			return queryForObject("CustomerView.getCustomerViewInfo", obj);
		} catch (Exception ex) {
			return null;
		}
	}
	
	

	/**
	 * @description get list site by id customer
	 * @author long.pham
	 * @since 2020-12-08
	 * @param id_customer
	 */

	public List getList(SiteEntity obj) {

		List dataList = new ArrayList();
		SecretCards secretCard = new SecretCards();
		try {
			List getList = queryForList("CustomerView.getList", obj);
			return getList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}

	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2021-09-02
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getListAlertBySite(AlertEntity obj) {
		try {
			List rs = queryForList("CustomerView.getListAlertCustomerView", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @description count total alert by site
	 * @author long.pham
	 * @since 2021-03-09
	 * @param id_customer, id_site, start_date, end_date
	 */

	public int getAlertCustomerViewTotalCount(AlertEntity obj) {
		try {
			return (int)queryForObject("CustomerView.countAlertCustomerView", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	


	/**
	 * @description count total notification alert by customer
	 * @author long.pham
	 * @since 2021-03-09
	 * @param id_customer, end_date
	 */

	public int countNotificationAlert(AlertEntity obj) {
		try {
			return (int) queryForObject("CustomerView.countCustomerViewNotificationAlert", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description get detail alert
	 * @author long.pham
	 * @since 2021-03-18
	 * @param id_site
	 * @return Object
	 */

	public AlertEntity getAlertSummary(AlertEntity obj) {
		AlertEntity dataObj = new AlertEntity();
		try {
			dataObj = (AlertEntity) queryForObject("CustomerView.getAlertSummary", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;

	}

}
