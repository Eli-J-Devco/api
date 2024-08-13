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
import com.nwm.api.entities.ScadaOverviewEntity;
import com.nwm.api.entities.SiteEntity;



public class ScadaOverviewService extends DB {
	
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
	 * @description get list device by id site
	 * @author long.pham
	 * @since 2022-03-04
	 * @param id_site
	 * @return Array of Devices
	 */
	
	public List getListDeviceByIdSite(ScadaOverviewEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("ScadaOverview.getListDeviceByIdSite", obj);
			
			return dataList;
				
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

	public Object getCustomerViewInfo(ScadaOverviewEntity obj) {
		Object dataObj = null;
		List dataListDeviceMeter = new ArrayList<>();
		try {
			dataListDeviceMeter = queryForList("ScadaOverview.getListDeviceTypeMeter", obj);
			if (dataListDeviceMeter.size() > 0) {
				obj.setGroupMeter(dataListDeviceMeter);
				// Get data by meter
				dataObj = queryForObject("ScadaOverview.getCustomerViewInfoMeter", obj);
			} else {
				// Get data by inverter
				List dataListDeviceInverter = queryForList("ScadaOverview.getListDeviceTypeInverter", obj);
				if (dataListDeviceInverter.size() > 0) {
					obj.setGroupMeter(dataListDeviceInverter);
					dataObj = queryForObject("ScadaOverview.getCustomerViewInfoInverter", obj);
				}
			}
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
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
			
			List dataListDeviceMeter = queryForList("ScadaOverview.getListDeviceTypeMeter", obj);
			List dataListDevicePower = dataListDeviceMeter.size() > 0 ? dataListDeviceMeter : queryForList("ScadaOverview.getListDeviceTypeInverter", obj);
			if (dataListDevicePower.size() > 0) {
				List dataListDeviceIrr = queryForList("ScadaOverview.getListDeviceTypeIrradiance", obj);
				
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
		                		categoriesTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
		                		break;
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
						if (obj.getEnable_virtual_device() == 1) {
							// Show each meter
							if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
								for (int i = 0; i < dataListDeviceMeter.size(); i++) {
									// get list of time to exclude data from
									Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
									List hiddenDataListDevice = queryForList("ScadaOverview.getHiddenDataListByDevice", device);
									device.put("hidden_data_list", hiddenDataListDevice);
									
									device.put("start_date", obj.getStart_date());
									device.put("end_date", obj.getEnd_date());
									device.put("data_send_time", obj.getData_send_time());
									List<ClientMonthlyDateEntity> dataPower = new ArrayList<>();
									switch (obj.getFilterBy()) {
										case "today":
											dataPower = queryForList("ScadaOverview.getDataPowerTodayEachMeter", device);
											break;
										case "3_day":
											dataPower = queryForList("ScadaOverview.getDataPower3DayEachMeter", device);
											break;
										case "this_week":
										case "last_week":
										case "this_month":
					                	case "last_month":
											dataPower = queryForList("ScadaOverview.getDataEnergyThisWeekEachMeter", device);
											break;
									}
									List<ClientMonthlyDateEntity> dataNew = fulfillData(dateTimeList, dataPower);
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
							
							obj.setDatatablename(obj.getTable_data_virtual());
							
							// get list of time to exclude data from
							List hiddenDataList = queryForList("ScadaOverview.getHiddenDataListBySite", obj);
							obj.setHidden_data_list(hiddenDataList);
							
							List<ClientMonthlyDateEntity> dataList = new ArrayList<>();
							switch (obj.getFilterBy()) {
								case "today":
									dataList = queryForList("ScadaOverview.getDataVirtualDeviceToday", obj);
									break;
								case "3_day":
									dataList = queryForList("ScadaOverview.getDataVirtualDevice3Day", obj);
									break;
								case "this_week":
								case "last_week":
								case "this_month":
			                	case "last_month":
									dataList = queryForList("ScadaOverview.getDataVirtualDeviceThisWeek", obj);
									break;
							}
							List<ClientMonthlyDateEntity> fulfilledData = fulfillData(dateTimeList, dataList);
							if (fulfilledData.size() > 0) {
								boolean isPower = obj.getFilterBy().equals("today") || obj.getFilterBy().equals("3_day");
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
							boolean isPower = obj.getFilterBy().equals("today") || obj.getFilterBy().equals("3_day");
							
							if (dataListDevicePower.size() > 0) {
								for (int i = 0; i < dataListDevicePower.size(); i++) {
									// get list of time to exclude data from
									Map<String, Object> device = (Map<String, Object>) dataListDevicePower.get(i);
									List hiddenDataList = queryForList("ScadaOverview.getHiddenDataListByDevice", device);
									device.put("hidden_data_list", hiddenDataList);
									
									// Show each meter
									if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
										device.put("start_date", obj.getStart_date());
										device.put("end_date", obj.getEnd_date());
										device.put("data_send_time", obj.getData_send_time());
										List<ClientMonthlyDateEntity> dataPower = new ArrayList<>();
										switch (obj.getFilterBy()) {
											case "today":
												dataPower = queryForList("ScadaOverview.getDataPowerTodayEachMeter", device);
												break;
											case "3_day":
												dataPower = queryForList("ScadaOverview.getDataPower3DayEachMeter", device);
												break;
											case "this_week":
											case "last_week":
											case "this_month":
						                	case "last_month":
												dataPower = queryForList("ScadaOverview.getDataEnergyThisWeekEachMeter", device);
												break;
										}
										List<ClientMonthlyDateEntity> dataNew = fulfillData(dateTimeList, dataPower);
										if (dataNew.size() > 0) {
											Map<String, Object> deviceItem = new HashMap<>();
											deviceItem.put("data_energy", dataNew);
											deviceItem.put("type", "energy");
											deviceItem.put("devicename", device.get("devicename"));
											deviceItem.put("deviceType", dataListDeviceMeter.size() > 0 ? "meter" : "inverter");
											dataEnergy.add(deviceItem);
										}
									}
								}
								
								obj.setGroupMeter(dataListDevicePower);
								List<ClientMonthlyDateEntity> dataPower = new ArrayList<>();
								switch (obj.getFilterBy()) {
									case "today":
										dataPower = queryForList("ScadaOverview.getDataPowerToday", obj);
										break;
									case "3_day":
										dataPower = queryForList("ScadaOverview.getDataPower3Day", obj);
										break;
									case "this_week":
									case "last_week":
									case "this_month":
				                	case "last_month":
										dataPower = queryForList("ScadaOverview.getDataEnergyThisWeek", obj);
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
									List dataListAIrrDevice = new ArrayList<>();
									
									Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
									// get list of time to exclude data from
									List hiddenDataList = queryForList("ScadaOverview.getHiddenDataListByDevice", item);
									item.put("hidden_data_list", hiddenDataList);
									dataListAIrrDevice.add(item);
									
									obj.setGroupMeter(dataListAIrrDevice);
									
									List<ClientMonthlyDateEntity> dataIrradianceDevice = new ArrayList<>();
									switch (obj.getFilterBy()) {
										case "today":
											dataIrradianceDevice = queryForList("ScadaOverview.getDataIrradianceToday", obj);
											break;
										case "3_day":
										case "this_week":
										case "last_week":
										case "this_month":
					                	case "last_month":
											dataIrradianceDevice = queryForList("ScadaOverview.getDataIrradiance3Day", obj);
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
				
				case "custom": {
					SimpleDateFormat dateFormatCustom = new SimpleDateFormat("yyyy-MM-dd"); 
					
					Date startDateCustom = dateFormatCustom.parse(obj.getStart_date());
					Calendar calCustom = Calendar.getInstance();
					calCustom.setTime(startDateCustom);
					
					Date endDateCustom = dateFormatCustom.parse(obj.getEnd_date());
					Calendar calEndCustom = Calendar.getInstance();
					calEndCustom.setTime(endDateCustom);

					long forCountYTD = ChronoUnit.DAYS.between(calCustom.getTime().toInstant(), calEndCustom.getTime().toInstant());
					
					// get list of time to exclude data from
					List hiddenDataList = queryForList("ScadaOverview.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList);
					obj.setGroupMeter(dataListDevicePower);
					
					List<ClientMonthlyDateEntity> dataPowerM = new ArrayList<>();
					if (obj.getEnable_virtual_device() == 1) {
						 obj.setDatatablename(obj.getTable_data_virtual());
						 dataPowerM = forCountYTD + 1 <= 5 ? queryForList("ScadaOverview.getDataVirtualDeviceCustomAtMost5Days", obj) : queryForList("ScadaOverview.getDataVirtualDeviceCustom", obj);
					} else {
						 dataPowerM = forCountYTD + 1 <= 5 ? queryForList("ScadaOverview.getDataPowerCustomAtMost5Days", obj) : queryForList("ScadaOverview.getDataPowerCustom", obj);
					}
					List<ClientMonthlyDateEntity> fulfilledData = fulfillData(dateTimeList, dataPowerM);
					if (fulfilledData.size() > 0) {
						Map<String, Object> deviceItem = new HashMap<>();
						deviceItem.put("data_energy", fulfilledData);
						deviceItem.put("type", "energy");
						deviceItem.put("devicename", "Energy Output");
						deviceItem.put("deviceType", dataListDeviceMeter.size() > 0 ? "meter" : "inverter");
						dataEnergy.add(deviceItem);
					}
					
					// Show each meter 
					if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
						for (int i = 0; i < dataListDeviceMeter.size(); i++) {
							// get list of time to exclude data from
							Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
							List hiddenDataListDevice = queryForList("ScadaOverview.getHiddenDataListByDevice", device);
							device.put("hidden_data_list", hiddenDataListDevice);
							
							// Show each meter
							device.put("start_date", obj.getStart_date());
							device.put("end_date", obj.getEnd_date());
							device.put("data_send_time", obj.getData_send_time());
							device.put("table_data_report", obj.getTable_data_report());
							List<ClientMonthlyDateEntity> dataPower = forCountYTD + 1 <= 5 ? queryForList("ScadaOverview.getDataPowerCustomAtMost5DaysEachMeter", device) : queryForList("ScadaOverview.getDataPowerThisMonthEachMeter", device);
							List<ClientMonthlyDateEntity> dataNew = fulfillData(dateTimeList, dataPower);
							
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
				
					break;
				}
				
				case "year": {
					// get list of time to exclude data from
					List hiddenDataList1 = queryForList("ScadaOverview.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList1);
					obj.setGroupMeter(dataListDevicePower);
					
					List<ClientMonthlyDateEntity> dataPowerMYTD = new ArrayList<>();
					if (obj.getEnable_virtual_device() == 1) {
						obj.setDatatablename(obj.getTable_data_virtual());
						dataPowerMYTD = queryForList("ScadaOverview.getDataVirtualDeviceYear", obj);
					} else {
						dataPowerMYTD = queryForList("ScadaOverview.getDataPowerYear", obj);
					}
					List<ClientMonthlyDateEntity> fulfilledData = fulfillData(dateTimeList, dataPowerMYTD);
					
					if (fulfilledData.size() > 0) {
						Map<String, Object> deviceItem = new HashMap<>();
						deviceItem.put("data_energy", fulfilledData);
						deviceItem.put("type", "energy");
						deviceItem.put("devicename", "Energy Output");
						deviceItem.put("deviceType", dataListDeviceMeter.size() > 0 ? "meter" : "inverter");
						dataEnergy.add(deviceItem);
					}
					
					// Show each meter 
					if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
						for (int i = 0; i < dataListDeviceMeter.size(); i++) {
							// get list of time to exclude data from
							Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
							List hiddenDataListDevice = queryForList("ScadaOverview.getHiddenDataListByDevice", device);
							device.put("hidden_data_list", hiddenDataListDevice);
							
							device.put("start_date", obj.getStart_date());
							device.put("end_date", obj.getEnd_date());
							device.put("data_send_time", obj.getData_send_time());
							device.put("table_data_report", obj.getTable_data_report());
							List<ClientMonthlyDateEntity> dataPower = queryForList("ScadaOverview.getDataPowerThisMonthEachMeter", device);
							List<ClientMonthlyDateEntity> dataNew = fulfillData(dateTimeList, dataPower);
							
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
					
					break;
				}

				case "12_month": {
					// get list of time to exclude data from
					List hiddenDataList2 = queryForList("ScadaOverview.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList2);
					obj.setGroupMeter(dataListDevicePower);
							
					List<ClientMonthlyDateEntity> dataPowerM12MonthDay = new ArrayList<>();
					if (obj.getEnable_virtual_device() == 1) {
						obj.setDatatablename(obj.getTable_data_virtual());
						dataPowerM12MonthDay = queryForList("ScadaOverview.getDataVirtualDeviceYear", obj);
					} else {
						dataPowerM12MonthDay = queryForList("ScadaOverview.getDataPowerYear", obj);
					}
					List<ClientMonthlyDateEntity> fulfilledData = fulfillData(dateTimeList, dataPowerM12MonthDay);
					
					if (fulfilledData.size() > 0) {
						Map<String, Object> deviceItem = new HashMap<>();
						deviceItem.put("data_energy", fulfilledData);
						deviceItem.put("type", "energy");
						deviceItem.put("devicename", "Energy Output");
						deviceItem.put("deviceType", dataListDeviceMeter.size() > 0 ? "meter" : "inverter");
						dataEnergy.add(deviceItem);
					}
					
					// Show each meter 
					if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
						for (int i = 0; i < dataListDeviceMeter.size(); i++) {
							// get list of time to exclude data from
							Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
							List hiddenDataListDevice = queryForList("ScadaOverview.getHiddenDataListByDevice", device);
							device.put("hidden_data_list", hiddenDataListDevice);
							
							device.put("start_date", obj.getStart_date());
							device.put("end_date", obj.getEnd_date());
							device.put("data_send_time", obj.getData_send_time());
							device.put("table_data_report", obj.getTable_data_report());
							List<ClientMonthlyDateEntity> dataPower = queryForList("ScadaOverview.getDataPowerThisMonthEachMeter", device);
							List<ClientMonthlyDateEntity> dataNew = fulfillData(dateTimeList, dataPower);
							
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
					
					break;
				}
				
				case "lifetime": {
					// get list of time to exclude data from
					List hiddenDataList3 = queryForList("ScadaOverview.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList3);
					obj.setGroupMeter(dataListDevicePower);
							
					List<ClientMonthlyDateEntity> dataPowerMLT = new ArrayList<>();
					if (obj.getEnable_virtual_device() == 1) {
						obj.setDatatablename(obj.getTable_data_virtual());
						dataPowerMLT = queryForList("ScadaOverview.getDataVirtualDeviceYear", obj);
					} else {
						dataPowerMLT = queryForList("ScadaOverview.getDataPowerYear", obj);
					}
					
					List<ClientMonthlyDateEntity> fulfilledData = fulfillData(dateTimeList, dataPowerMLT);
					if (fulfilledData.size() > 0) {
						Map<String, Object> deviceItem = new HashMap<>();
						deviceItem.put("data_energy", fulfilledData);
						deviceItem.put("type", "energy");
						deviceItem.put("devicename", "Energy Output");
						deviceItem.put("deviceType", dataListDeviceMeter.size() > 0 ? "meter" : "inverter");
						dataEnergy.add(deviceItem);
					}
					
					// Show each meter 
					if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
						for (int i = 0; i < dataListDeviceMeter.size(); i++) {
							// get list of time to exclude data from
							Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
							List hiddenDataListDevice = queryForList("ScadaOverview.getHiddenDataListByDevice", device);
							device.put("hidden_data_list", hiddenDataListDevice);
							
							device.put("start_date", obj.getStart_date());
							device.put("end_date", obj.getEnd_date());
							device.put("data_send_time", obj.getData_send_time());
							device.put("table_data_report", obj.getTable_data_report());
							List<ClientMonthlyDateEntity> dataPower = queryForList("ScadaOverview.getDataPowerThisMonthEachMeter", device);
							List<ClientMonthlyDateEntity> dataNew = fulfillData(dateTimeList, dataPower);
							
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
