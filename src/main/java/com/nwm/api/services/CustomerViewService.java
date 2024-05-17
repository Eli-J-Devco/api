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
import java.util.stream.Collectors;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.SecretCards;

public class CustomerViewService extends DB {
	
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
				for (ClientMonthlyDateEntity dateTime: dateTimeList) {
					boolean isFound = false;
					
					for(ClientMonthlyDateEntity data: dataList) {
						String timeFull = dateTime.getTime_full();
						String powerTime = data.getTime_full();
						
						if (timeFull.equals(powerTime)) {
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
	 * @description get chart data energy
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id_site, id_customer
	 */

	public List getChartDataPerformance(SiteEntity obj) {
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
			
			List dataListDeviceMeter = queryForList("CustomerView.getListDeviceTypeMeter", obj);
			List dataListDevicePower = dataListDeviceMeter.size() > 0 ? dataListDeviceMeter : queryForList("CustomerView.getListDeviceTypeInverter", obj);
			if (dataListDevicePower.size() > 0) {
				List dataListDeviceIrr = queryForList("CustomerView.getListDeviceTypeIrradiance", obj);
				
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
						
						// Show each meter
						if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
							obj.setGroupMeter(dataListDeviceMeter);
							
							List<ClientMonthlyDateEntity> dataPower = new ArrayList<>();
							switch (obj.getFilterBy()) {
								case "today":
									dataPower = queryForList("CustomerView.getDataPowerToday", obj);
									break;
								case "3_day":
									dataPower = queryForList("CustomerView.getDataPower3Day", obj);
									break;
								case "this_week":
								case "last_week":
								case "this_month":
								case "last_month":
									dataPower = queryForList("CustomerView.getDataEnergyThisWeek", obj);
									break;
							}
							
							for (int i = 0; i < dataListDeviceMeter.size(); i++) {
								Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
								List<ClientMonthlyDateEntity> dataItem = dataPower.stream().filter(item -> item.getId() == Integer.parseInt(device.get("id").toString())).collect(Collectors.toList());
								List<ClientMonthlyDateEntity> dataNew = fulfillData(dateTimeList, dataItem);
								if (dataNew.size() > 0) {
									Map<String, Object> deviceItem = new HashMap<>();
									deviceItem.put("data_energy", dataNew);
									deviceItem.put("type", "energy");
									deviceItem.put("devicename", device.get("devicename"));
									deviceItem.put("deviceType", "meter");
									dataEnergy.add(deviceItem);
								}
							}
						}
						
						if (obj.getEnable_virtual_device() == 1) {
							obj.setDatatablename(obj.getTable_data_virtual());
							List<ClientMonthlyDateEntity> dataList = new ArrayList<>();
							switch (obj.getFilterBy()) {
								case "today":
									dataList = queryForList("CustomerView.getDataVirtualDeviceToday", obj);
									break;
								case "3_day":
									dataList = queryForList("CustomerView.getDataVirtualDevice3Day", obj);
									break;
								case "this_week":
								case "last_week":
								case "this_month":
			                	case "last_month":
									dataList = queryForList("CustomerView.getDataVirtualDeviceThisWeek", obj);
									break;
							}
							List<ClientMonthlyDateEntity> fulfilledData = fulfillData(dateTimeList, dataList);
							if (fulfilledData.size() > 0) {
								List<ClientMonthlyDateEntity> powerList = new ArrayList<>();
								List<ClientMonthlyDateEntity> expectedPowerList = new ArrayList<>();
								List<ClientMonthlyDateEntity> irradianceList = new ArrayList<>();
								
								for (ClientMonthlyDateEntity dataListItem: fulfilledData) {
									ClientMonthlyDateEntity powerListItem = new ClientMonthlyDateEntity();
									ClientMonthlyDateEntity expectedPowerListItem = new ClientMonthlyDateEntity();
									ClientMonthlyDateEntity irradianceListItem = new ClientMonthlyDateEntity();
									
									powerListItem.setTime_format(dataListItem.getTime_format());
									powerListItem.setTime_full(dataListItem.getTime_full());
									powerListItem.setCategories_time(dataListItem.getCategories_time());
									powerListItem.setChart_energy_kwh(isPower ? dataListItem.getNvmActivePower() : dataListItem.getNvmActiveEnergy());
									powerList.add(powerListItem);

									expectedPowerListItem.setTime_format(dataListItem.getTime_format());
									expectedPowerListItem.setTime_full(dataListItem.getTime_full());
									expectedPowerListItem.setCategories_time(dataListItem.getCategories_time());
									if (isPower) expectedPowerListItem.setExpected_power(dataListItem.getExpected_power());
									else expectedPowerListItem.setExpected_energy(dataListItem.getExpected_energy());
									expectedPowerList.add(expectedPowerListItem);

									irradianceListItem.setTime_format(dataListItem.getTime_format());
									irradianceListItem.setTime_full(dataListItem.getTime_full());
									irradianceListItem.setCategories_time(dataListItem.getCategories_time());
									irradianceListItem.setChart_energy_kwh(dataListItem.getNvm_irradiance());
									irradianceList.add(irradianceListItem);
								}
								
								Map<String, Object> powerItem = new HashMap<>();
								powerItem.put("data_energy", powerList);
								powerItem.put("type", "energy");
								powerItem.put("devicename", isPower ? "Power" : "Energy Output");
								powerItem.put("deviceType", dataListDeviceMeter.size() > 0 ? "meter" : "inverter");
								dataEnergy.add(powerItem);
								
								if (dataListDeviceIrr.size() > 0) {
									Map<String, Object> expectedPowerItem = new HashMap<>();
									expectedPowerItem.put("data_energy", expectedPowerList);
									expectedPowerItem.put("type", isPower ? "expected_power" : "expected_energy");
									expectedPowerItem.put("devicename", isPower ? "Expected Power" : "Expected Energy");
									dataEnergy.add(expectedPowerItem);
									
									Map<String, Object> irradianceItem = new HashMap<>();
									irradianceItem.put("data_energy", irradianceList);
									irradianceItem.put("type", "irradiance");
									irradianceItem.put("devicename", "Irradiance");
									dataEnergy.add(irradianceItem);
								}
							}
						} else {
							if (dataListDevicePower.size() > 0) {
								obj.setIs_show_each_meter(0);
								obj.setGroupMeter(dataListDevicePower);
								List<ClientMonthlyDateEntity> dataPower = new ArrayList<>();
								switch (obj.getFilterBy()) {
									case "today":
										dataPower = queryForList("CustomerView.getDataPowerToday", obj);
										break;
									case "3_day":
										dataPower = queryForList("CustomerView.getDataPower3Day", obj);
										break;
									case "this_week":
									case "last_week":
									case "this_month":
				                	case "last_month":
										dataPower = queryForList("CustomerView.getDataEnergyThisWeek", obj);
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
							
							// Get Irradiance
							if (dataListDeviceIrr.size() > 0) {
								for(int i = 0; i < dataListDeviceIrr.size(); i++) {
									Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
									obj.setDatatablename(item.get("datatablename").toString());
									obj.setId_device(Integer.parseInt(item.get("id").toString()));
									
									List<ClientMonthlyDateEntity> dataIrradianceDevice = new ArrayList<>();
									switch (obj.getFilterBy()) {
										case "today":
											dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceToday", obj);
											break;
										case "3_day":
										case "this_week":
										case "last_week":
										case "this_month":
					                	case "last_month":
											dataIrradianceDevice = queryForList("CustomerView.getDataIrradiance3Day", obj);
											break;
									}
									List<ClientMonthlyDateEntity> fulfilledData = fulfillData(dateTimeList, dataIrradianceDevice);
									if(fulfilledData.size() > 0 ) {
										// Get Expected Power
										if (i == 0) {
											Map<String, Object> deviceExpectedPowerItem = new HashMap<>();
											deviceExpectedPowerItem.put("data_energy", fulfilledData);
											deviceExpectedPowerItem.put("type", isPower ? "expected_power" : "expected_energy");
											deviceExpectedPowerItem.put("devicename", isPower ? "Expected Power" : "Expected Energy");
											dataEnergy.add(deviceExpectedPowerItem);
										}
										
										// Get Irradiance
										Map<String, Object> deviceItem = new HashMap<>();
										deviceItem.put("data_energy", fulfilledData);
										deviceItem.put("type", "irradiance");
										deviceItem.put("devicename", dataListDeviceIrr.get(i));
										dataEnergy.add(deviceItem);
									}
								}
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
						
						// Show each meter
						if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
							List<ClientMonthlyDateEntity> dataPower = queryForList("CustomerView.getDataPowerCustom", obj);
							
							for (int i = 0; i < dataListDeviceMeter.size(); i++) {
								Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
								List<ClientMonthlyDateEntity> dataItem = dataPower.stream().filter(item -> item.getId() == Integer.parseInt(device.get("id").toString())).collect(Collectors.toList());
								List<ClientMonthlyDateEntity> dataNew = fulfillData(dateTimeList, dataItem);
								if (dataNew.size() > 0) {
									Map<String, Object> deviceItem = new HashMap<>();
									deviceItem.put("data_energy", dataNew);
									deviceItem.put("type", "energy");
									deviceItem.put("devicename", device.get("devicename"));
									deviceItem.put("deviceType", "meter");
									dataEnergy.add(deviceItem);
								}
							}
						}
						
						obj.setIs_show_each_meter(0);
						obj.setDatatablename(obj.getEnable_virtual_device() == 1 ? obj.getTable_data_virtual() : obj.getDatatablename());
						List<ClientMonthlyDateEntity> dataPowerM = obj.getEnable_virtual_device() == 1 ? queryForList("CustomerView.getDataVirtualDeviceCustom", obj) : queryForList("CustomerView.getDataPowerCustom", obj);
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
	 * @description get customer view site info
	 * @author long.pham
	 * @since 2020-12-02
	 * @param id_site, id_customer
	 * @return Object
	 */

	public Object getCustomerViewInfo(SiteEntity obj) {
		Object dataObj = null;
		List dataListDeviceMeter = new ArrayList<>();
		try {
			dataListDeviceMeter = queryForList("CustomerView.getListDeviceTypeMeter", obj);
			if (dataListDeviceMeter.size() > 0) {
				obj.setGroupMeter(dataListDeviceMeter);
				// Get data by meter
				dataObj = queryForObject("CustomerView.getCustomerViewInfoMeter", obj);
			} else {
				// Get data by inverter
				List dataListDeviceInverter = queryForList("CustomerView.getListDeviceTypeInverter", obj);
				if (dataListDeviceInverter.size() > 0) {
					obj.setGroupMeter(dataListDeviceInverter);
					dataObj = queryForObject("CustomerView.getCustomerViewInfoInverter", obj);
				}
			}
			return dataObj;
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
