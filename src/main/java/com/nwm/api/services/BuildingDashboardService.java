/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
import com.nwm.api.entities.BuildingReportEntity;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceGroupEntity;
import com.nwm.api.entities.ElectricInformationEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.entities.TopChangeContributorsEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

public class BuildingDashboardService extends DB {
	
	/**
	 * @description fulfill data in specific range of time
	 * @author Hung.Bui
	 * @since 2024-03-20
	 * @param List<ClientMonthlyDateEntity> dateTimeList
	 * @param List<ClientMonthlyDateEntity> dataList
	 */
	private List<ClientMonthlyDateEntity> fulfillData(List<ClientMonthlyDateEntity> dateTimeList, List<ClientMonthlyDateEntity> dataList) {
		try {
			if (dataList == null || dateTimeList.size() == 0) return dataList;
			List<ClientMonthlyDateEntity> fulfilledDataList = new ArrayList<ClientMonthlyDateEntity>();
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
			
			return fulfilledDataList;
		} catch (Exception e) {
			return dataList;
		}
		
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
	
	
	
	/**
	 * @description get list site building floor
	 * @author Long.Pham
	 * @since 2025-02-20
	 * @param obj
	 */
	
	
	public ElectricInformationEntity getElectricInformation(ElectricInformationEntity obj) {
		ElectricInformationEntity dataObj = null;
		try {
			List deviceMainLoad = queryForList("BuildingDashboard.getDeviceMainLoad", obj);
			obj.setDevices(deviceMainLoad);
			
			dataObj = (ElectricInformationEntity) queryForObject("BuildingDashboard.getElectricInformation", obj);
			if (dataObj == null)
				return new ElectricInformationEntity();
		} catch (Exception ex) {
			return new ElectricInformationEntity();
		}
		return dataObj;
	}
	
	
	
	/**
	 * @description get list site building floor
	 * @author Long.Pham
	 * @since 2025-02-20
	 * @param obj
	 */
	
	
	public ElectricInformationEntity getMonthlyEnergyUsageByComponent(ElectricInformationEntity obj) {
		ElectricInformationEntity dataObj = null;
		try {
			dataObj = (ElectricInformationEntity) queryForObject("BuildingDashboard.getMonthlyEnergyUsageByComponent", obj);
			if (dataObj == null)
				return new ElectricInformationEntity();
		} catch (Exception ex) {
			return new ElectricInformationEntity();
		}
		return dataObj;
	}
	
	
	
	
	/**
	 * @description get list site building floor
	 * @author Long.Pham
	 * @since 2025-02-20
	 * @param obj
	 */
	
	
	public ElectricInformationEntity getDataChangeSwitchTopKPI(ElectricInformationEntity obj) {
		try {
			
			// Get device by id_site
			List devices = queryForList("BuildingDashboard.getListDeviceByMeterType", obj);
			if(devices.size() > 0) {
				obj.setDevices(devices);
				if(obj.getMeter_type() == 5) {
					ElectricInformationEntity dataObj = (ElectricInformationEntity) queryForObject("BuildingDashboard.getData24H", obj);
					obj.setWater_avg_24h(dataObj.getWater_avg_24h());
					obj.setWater_peak_24h(dataObj.getWater_peak_24h());
				} else {
					List dataDevices = queryForList("BuildingDashboard.getDataChangeSwitchTopKPI", obj);
					obj.setDataDevices(dataDevices);
				}
			}
			return obj;
		} catch (Exception ex) {
			return new ElectricInformationEntity();
		}
		
	}
	
	
	
	/**
	 * @description get list site building floor
	 * @author Long.Pham
	 * @since 2025-02-20
	 * @param obj
	 */
	
	
	public List getHourlyPeakPower(SitesDevicesEntity obj) {
		try {
			
			List dataListDeviceMeter = queryForList("EnergyUsage.getListDeviceTypeMeter", obj);
			if(dataListDeviceMeter.size() <= 0) { return new ArrayList(); }
			
			obj.setList_device(dataListDeviceMeter);
			int interval = 0;
			DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
			DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
			ChronoUnit timeUnit = ChronoUnit.HOURS;
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
			switch (obj.getId_time_filter()) {
				case "hourly": // 1 hour
					interval = 1;
					timeUnit = ChronoUnit.HOURS;
					timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
                    if(!"today".equals(obj.getId_filter() )) {
                        categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                    }
					break;
				
				case "day": // 1 hour
					interval = 1;
					timeUnit = ChronoUnit.DAYS;
					timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					break;
					
				case "this_week": // 1 day
				case "last_week":
				case "this_month":
				case "last_month":
					interval = 1;
					timeUnit = ChronoUnit.DAYS;
					timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("dd");
					break;
					
				case "month":
					interval = 1;
					timeUnit = ChronoUnit.MONTHS;
					timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
					start = start.withDayOfMonth(1);
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
					
				default:
					interval = 1;
					timeUnit = ChronoUnit.HOURS;
					timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					categoriesTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
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
			
			// get Energy usage
			List<ClientMonthlyDateEntity> dataEnergyUsage = queryForList("BuildingDashboard.getHourlyPeakPowerV2", obj);
			List<ClientMonthlyDateEntity> fulfilledData = Lib.fulfillData(dateTimeList, dataEnergyUsage, "time_full");
			
			return fulfilledData;
		} catch (Exception ex) {
			return new ArrayList();
		}
		
	}
	
	
	
	
	/**
	 * @description get list site building floor
	 * @author Long.Pham
	 * @since 2025-02-20
	 * @param obj
	 */
	
	
	public List getData30DaysByDevice(DeviceEntity obj) {
		try {
			int interval = 1;
			DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy");
			ChronoUnit timeUnit = ChronoUnit.DAYS;
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
			// get Energy usage 
			List<ClientMonthlyDateEntity> dataEnergyUsage = new ArrayList<>();
			dataEnergyUsage = queryForList("BuildingDashboard.getData30Days", obj);

            switch (Constants.ChartingFilter.fromValue(obj.getFilterBy())) {
                case TODAY:
                    timeUnit = ChronoUnit.HOURS;
                    timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                    categoriesTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
                    break;
                case THIS_WEEK:
                case LAST_WEEK:
                case THIS_MONTH:
                case LAST_MONTH:
                    timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                    categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                    break;
                case LAST_12_MONTHS:
                case THIS_YEAR:
                case LIFETIME:
                    timeUnit = ChronoUnit.MONTHS;
                    timeFullFormat = DateTimeFormatter.ofPattern("MMM. yyyy");
                    categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM, yyyy");
                    if ( dataEnergyUsage != null && !dataEnergyUsage.isEmpty()) {
                        YearMonth ym = YearMonth.parse(dataEnergyUsage.get(0).getTime_full(), timeFullFormat);
                        start = ym.atDay(1).atStartOfDay();
                    }
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
			return fulfilledData;
		} catch (Exception ex) {
			return new ArrayList();
		}
		
	}
	
	
	
	/**
	 * @description get data top change contributors
	 * @author Long.Pham
	 * @since 2025-09-08
	 * @param id_site
	 */
	public TopChangeContributorsEntity getTopChangeContributors(TopChangeContributorsEntity obj) {
		try {
			// Get device by id_site
			List devices = queryForList("BuildingDashboard.getListDeviceBySite", obj);
			if(devices.size() > 0) {
				List<Object> electrics = new ArrayList<>();
				List<Object> gas = new ArrayList<>();
				List<Object> pvProduction = new ArrayList<>();
				List<Object> waters = new ArrayList<>();
				List<Object> weather = new ArrayList<>();
				List<Object> lighting = new ArrayList<>();
				List<Object> hvac = new ArrayList<>();
				
				for (int j = 0; j < devices.size(); j++) {
					Map<String, Object> item = (Map<String, Object>) devices.get(j);
					int meterType = Integer.parseInt(item.get("meter_type").toString());
					int idDeviceType = Integer.parseInt(item.get("id_device_type").toString());
					if(idDeviceType == 4) {
						weather.add(item);
					}
					
					switch (meterType) {
				        case 3:
				        	pvProduction.add(item);
				            break;
				        case 4:
				        	electrics.add(item);
				            break;
				        case 5:
				        	waters.add(item);
				            break;
				        case 7:
				        	gas.add(item);
				            break;
				            
				        case 1:
				        	lighting.add(item);
				            break;
				        case 6:
				        	hvac.add(item);
				            break;
				    }
				}
				
				if(pvProduction.size() > 0) {
					obj.setDevices(pvProduction);
					TopChangeContributorsEntity data = (TopChangeContributorsEntity) queryForObject("BuildingDashboard.getTopChangeContributors", obj);
					if(data != null) {
						obj.setPv_current(data.getCurrent());
						obj.setPv_compare(data.getCurrent_compare());
					}
				}
				
				if(gas.size() > 0) {
					obj.setDevices(gas);
					TopChangeContributorsEntity data = (TopChangeContributorsEntity) queryForObject("BuildingDashboard.getTopChangeContributors", obj);
					if(data != null) {
						obj.setGas_current(data.getCurrent());
						obj.setGas_compare(data.getCurrent_compare());
					}
				}
				
				if(waters.size() > 0) {
					obj.setDevices(waters);
					TopChangeContributorsEntity data = (TopChangeContributorsEntity) queryForObject("BuildingDashboard.getTopChangeContributors", obj);
					if(data != null) {
						obj.setWater_current(data.getCurrent());
						obj.setWater_compare(data.getCurrent_compare());
					}
				}
				if(electrics.size() > 0) {
					obj.setDevices(electrics);
					TopChangeContributorsEntity data = (TopChangeContributorsEntity) queryForObject("BuildingDashboard.getTopChangeContributors", obj);
					if(data != null) {
						obj.setElectric_current(data.getCurrent());
						obj.setElectric_compare(data.getCurrent_compare());
					}
					
				}
				
				if(lighting.size() > 0) {
					obj.setDevices(lighting);
					TopChangeContributorsEntity data = (TopChangeContributorsEntity) queryForObject("BuildingDashboard.getTopChangeContributors", obj);
					if(data != null) {
						obj.setLighting_current(data.getCurrent());
						obj.setLighting_compare(data.getCurrent_compare());
					}
					
				}
				
				if(hvac.size() > 0) {
					obj.setDevices(hvac);
					TopChangeContributorsEntity data = (TopChangeContributorsEntity) queryForObject("BuildingDashboard.getTopChangeContributors", obj);
					if(data != null) {
						obj.setHvac_current(data.getCurrent());
						obj.setHvac_compare(data.getCurrent_compare());
					}
					
				}

			}
			
			return obj;
		} catch (Exception ex) {
			return new TopChangeContributorsEntity();
		}
	}
}
