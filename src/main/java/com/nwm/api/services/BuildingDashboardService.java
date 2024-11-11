/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.sql.SQLException;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceGroupEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Constants;

public class BuildingDashboardService extends DB {
	
	/**
	 * @description fulfill data in specific range of time
	 * @author Hung.Bui
	 * @since 2024-03-20
	 * @param List<ClientMonthlyDateEntity> dateTimeList
	 * @param List<ClientMonthlyDateEntity> dataList
	 */
	private List<ClientMonthlyDateEntity> fulfillData(List<ClientMonthlyDateEntity> dateTimeList, List<ClientMonthlyDateEntity> dataList) {
		List<ClientMonthlyDateEntity> fulfilledDataList = new ArrayList<ClientMonthlyDateEntity>();
		
		try {
			if(dataList.size() > 0 && dateTimeList.size() > 0) {
				int count = 0;
				for (int i = 0; i < dateTimeList.size(); i++) {
					ClientMonthlyDateEntity dateTimeItem = dateTimeList.get(i);
					if (i - count > dataList.size() - 1) {
						fulfilledDataList.add(dateTimeItem);
						continue;
					}
					ClientMonthlyDateEntity dataItem = dataList.get(i - count);
					if (dateTimeItem.getTime_full().equals(dataItem.getTime_full())) {
						fulfilledDataList.add(dataItem);
					} else {
						fulfilledDataList.add(dateTimeItem);
						count++;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return fulfilledDataList;
	}

	/**
	 * @description get list load meter device
	 * @author long.pham
	 * @since 2024-10-29
	 * @returns array
	 */
	
	public List getListLoadMeterDevices(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BuildingDashboard.getListLoadMeterDevices", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	
	/**
	 * @description get list data field
	 * @author long.pham
	 * @since 2024-10-29
	 * @returns array
	 */
	
	@SuppressWarnings("unchecked")
	public List getListDeviceDataField(DeviceEntity obj) throws JsonProcessingException {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BuildingDashboard.getListDeviceDataField", obj);
			if (dataList == null)
				return new ArrayList();
			
			// Get last data
			Map<String, Object> dataLastValueField = (Map<String, Object>) queryForObject("BuildingDashboard.getLastDataField", obj);
			dataList.forEach((item) -> {
				try {
					List dataListGroupField = new ArrayList();
					List dataListField = queryForList("BuildingDashboard.getListField", item);
					if(dataListField.size() > 0) {
						for(int i = 0; i< dataListField.size(); i ++) {
							Map<String, Object> itemField = (Map<String, Object>) dataListField.get(i);
							String keyField = (String) itemField.get("slug");
							if(keyField != "") {
								Object valueField = dataLastValueField.get(keyField);
								itemField.put("value", valueField);
							} else {
								itemField.put("value", 0);
							}
							dataListGroupField.add(itemField);
						}
					}
					((Map<String, Object>) item).put("fields", dataListGroupField);
					
				} catch (SQLException e) {
					((Map<String, Object>) item).put("fields", new ArrayList<Map<String, Object>>());
				}
			});
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	
	/**
	 * @description get dashboard chart data energy
	 * @author long.pham
	 * @since 2024-10-30
	 * @param {}
	 */

	public List getDashboardChartData(SiteEntity obj) {
		try {
			List dataEnergy = new ArrayList<>();
			
			// if data is in 3 latest months then data is fetch from view, else it's from table
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.MONTH, -3);
			SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = dateFor.parse(obj.getStart_date());
			Date d2 = dateFor.parse(dateFor.format(c.getTime()));
			if(d1.compareTo(d2) < 0) obj.setRead_data_all("all_data");
			
			List dataListDeviceMeter = queryForList("BuildingDashboard.getListDeviceTypeMeter", obj);
			List dataListDeviceMeterConsumption =  queryForList("BuildingDashboard.getListDeviceTypeMeterConsumption", obj);
			
			if(dataListDeviceMeter.size() > 0 || dataListDeviceMeterConsumption.size() > 0) {
				
				// ----- Create DateTime List ----- Begin
				int interval = 0;
				DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
				DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
				ChronoUnit timeUnit = ChronoUnit.MINUTES;
				LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
				switch (obj.getData_send_time()) {
					case 8: // 1 minute
						interval = 1;
						timeUnit = ChronoUnit.MINUTES;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
		                switch (obj.getFilterBy()) {
		                	case "today":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
		                		break;
		                	case "3_day":
		                	case "this_week":
		                	case "last_week":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
		                		break;
		                }
						break;
						
					case 1: // 5 minutes
						interval = 5;
						timeUnit = ChronoUnit.MINUTES;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
		                switch (obj.getFilterBy()) {
		                	case "today":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
		                		break;
		                	case "3_day":
		                	case "this_week":
		                	case "last_week":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
		                		break;
		                }
						break;
						
					case 2: // 15 minutes
						interval = 15;
						timeUnit = ChronoUnit.MINUTES;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
		                switch (obj.getFilterBy()) {
		                	case "today":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
		                		break;
		                	case "3_day":
		                	case "this_week":
		                	case "last_week":
		                	case "this_month":
		                	case "last_month":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
		                		break;
		                }
						break;
						
					case 3: // 1 hour
						interval = 1;
						timeUnit = ChronoUnit.HOURS;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
		                switch (obj.getFilterBy()) {
		                	case "today":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm");
		                		break;
		                	case "3_day":
		                	case "this_week":
		                	case "last_week":
		                	case "this_month":
		                	case "last_month":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("dd. LLL HH:mm");
		                		break;
		                }
						break;
						
					case 4: // 1 day
						interval = 1;
						timeUnit = ChronoUnit.DAYS;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
						switch (obj.getFilterBy()) {
		                	case "today":
		                	case "3_day":
		                	case "this_week":
		                	case "last_week":
		                	case "this_month":
		                	case "last_month":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("dd. LLL");
		                		break;
		                	case "12_month":
		                	case "year":
		                	case "custom":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
						}
						break;
						
					case 5: // 7 days
						interval = 7;
						timeUnit = ChronoUnit.DAYS;
						timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		                switch (obj.getFilterBy()) {
		                	case "this_month":
		                	case "last_month":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("dd. LLL");
		                		break;
		                	case "12_month":
		                	case "year":
		                	case "custom":
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
		                		break;
		                }
						break;
						
					case 6: // 1 month
						interval = 1;
						timeUnit = ChronoUnit.MONTHS;
						timeFullFormat = DateTimeFormatter.ofPattern("MM/yyyy");
						categoriesTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
						start = start.withDayOfMonth(1);
						break;
						
					case 7: // 1 year
						interval = 1;
						timeUnit = ChronoUnit.YEARS;
						timeFullFormat = DateTimeFormatter.ofPattern("yyyy");
						categoriesTimeFormat = DateTimeFormatter.ofPattern("yyyy");
						start = start.withDayOfYear(1);
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
				// ----- Create DateTime List ----- End
				
				switch (obj.getFilterBy()) {
				case "today":
				case "3_day":
				case "this_week":
				case "last_week":
				case "this_month":
				case "last_month": {
					// get list of time to exclude data from
					List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList);
					boolean isPower = obj.getFilterBy().equals("today") || obj.getFilterBy().equals("3_day");
					
					if (dataListDeviceMeter.size() > 0) {
						obj.setGroupMeter(dataListDeviceMeter);
						List<ClientMonthlyDateEntity> dataPower = new ArrayList<>();
						switch (obj.getFilterBy()) {
							case "today":
								dataPower = queryForList("BuildingDashboard.getDataPowerToday", obj);
								break;
							case "3_day":
								dataPower = queryForList("BuildingDashboard.getDataPower3Day", obj);
								break;
							case "this_week":
							case "last_week":
							case "this_month":
		                	case "last_month":
								dataPower = queryForList("BuildingDashboard.getDataEnergyThisWeek", obj);
								break;
						}
						List<ClientMonthlyDateEntity> fulfilledData = fulfillData(dateTimeList, dataPower);
						if (fulfilledData.size() > 0) {
							Map<String, Object> deviceItem = new HashMap<>();
							deviceItem.put("data_energy", fulfilledData);
							deviceItem.put("type", "energy");
							deviceItem.put("devicename", isPower ? "Power" : "Energy Output");
							deviceItem.put("deviceType", dataListDeviceMeter.size() > 0 ? "meter" : "inverter");
							dataEnergy.add(deviceItem);
						}
					}
					
					break;
				}
			
				case "custom":
				case "year":
				case "12_month":
				case "lifetime": {
					// get list of time to exclude data from
					List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList);
					
					obj.setGroupMeter(dataListDeviceMeter);
					obj.setDatatablename(obj.getEnable_virtual_device() == 1 ? obj.getTable_data_virtual() : obj.getDatatablename());
					List<ClientMonthlyDateEntity> dataPowerM = queryForList("BuildingDashboard.getDataPowerCustom", obj);
					List<ClientMonthlyDateEntity> fulfilledData = fulfillData(dateTimeList, dataPowerM);
					if (fulfilledData.size() > 0) {
						Map<String, Object> deviceItem = new HashMap<>();
						deviceItem.put("data_energy", fulfilledData);
						deviceItem.put("type", "energy");
						deviceItem.put("devicename", "Energy Output");
						deviceItem.put("deviceType", dataListDeviceMeter.size() > 0 ? "meter" : "inverter");
						dataEnergy.add(deviceItem);
					}
									
					break;
				}
			}
		}
						
						
			return dataEnergy;
		} catch (Exception ex) {
			return new ArrayList();
		}

	}
	
	
}
