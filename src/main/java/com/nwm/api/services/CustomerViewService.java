/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.SecretCards;

public class CustomerViewService extends DB {

	/**
	 * @description get chart data energy
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id_site, id_customer
	 */

	public List getChartDataPerformance(SiteEntity obj) {
		List dataEnergy = new ArrayList<>();
		List dataListDeviceMeter = new ArrayList<>();
		List dataListDeviceIrr = new ArrayList<>();
		try {
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.MONTH, -3);
			SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = dateFor.parse(obj.getStart_date());
			Date d2 = dateFor.parse(dateFor.format(c.getTime()));
			if(d1.compareTo(d2) < 0) {
				obj.setRead_data_all("all_data");
			}
			
			dataListDeviceMeter = queryForList("CustomerView.getListDeviceTypeMeter", obj);
			if (dataListDeviceMeter.size() > 0) {
				// Get by meter
				List dataListMeter = new ArrayList<>();
				for (int i = 0; i < dataListDeviceMeter.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) dataListDeviceMeter.get(i);
					item.put("start_date", obj.getStart_date());
					item.put("end_date", obj.getEnd_date());
					dataListMeter.add(item);
				}
				
				dataListDeviceIrr = queryForList("CustomerView.getListDeviceTypeIrradiance", obj);
				switch (obj.getFilterBy()) {
				
				case "today":
						if (obj.getEnable_virtual_device() == 1) {
							// Show each meter
							if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
								for (int i = 0; i < dataListDeviceMeter.size(); i++) {
									Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
									List hiddenDataListDevice = queryForList("CustomerView.getHiddenDataListByDevice", device);
									device.put("hidden_data_list", hiddenDataListDevice);
									Map<String, Object> deviceItemEach5 = new HashMap<>();
									device.put("data_send_time", obj.getData_send_time());
									List dataPower = queryForList("CustomerView.getDataPowerTodayEachMeter", device);
									if (dataPower.size() > 0) {
										deviceItemEach5.put("data_energy", dataPower);
										deviceItemEach5.put("type", "energy");
										deviceItemEach5.put("devicename", device.get("devicename"));
										deviceItemEach5.put("deviceType", "meter");
										dataEnergy.add(deviceItemEach5);
									}
								}
							}
							
							obj.setDatatablename(obj.getTable_data_virtual());
							
							// get list of time to exclude data from
							List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
							obj.setHidden_data_list(hiddenDataList);
							
							List dataList = queryForList("CustomerView.getDataVirtualDeviceToday", obj);
							if (dataList.size() > 0) {
								Map<String, Object> powerItem = new HashMap<>();
								Map<String, Object> expectedPowerItem = new HashMap<>();
								Map<String, Object> irradianceItem = new HashMap<>();
								List powerList = new ArrayList<>();
								List expectedPowerList = new ArrayList<>();
								List irradianceList = new ArrayList<>();
								
								for (int i = 0; i < dataList.size(); i++) {
									Map<String, Object> dataListItem = (Map<String, Object>) dataList.get(i);
									Map<String, Object> powerListItem = new HashMap<>();
									Map<String, Object> expectedPowerListItem = new HashMap<>();
									Map<String, Object> irradianceListItem = new HashMap<>();
									
									powerListItem.put("time", dataListItem.get("time"));
									powerListItem.put("download_time", dataListItem.get("download_time"));
									powerListItem.put("time_format", dataListItem.get("time_format"));
									powerListItem.put("time_full", dataListItem.get("time_full"));
									powerListItem.put("categories_time", dataListItem.get("categories_time"));
									powerListItem.put("chart_energy_kwh", dataListItem.get("nvmActivePower"));
									powerList.add(powerListItem);

									expectedPowerListItem.put("time", dataListItem.get("time"));
									expectedPowerListItem.put("download_time", dataListItem.get("download_time"));
									expectedPowerListItem.put("time_format", dataListItem.get("time_format"));
									expectedPowerListItem.put("time_full", dataListItem.get("time_full"));
									expectedPowerListItem.put("categories_time", dataListItem.get("categories_time"));
									expectedPowerListItem.put("expected_power", dataListItem.get("expected_power"));
									expectedPowerList.add(expectedPowerListItem);

									irradianceListItem.put("time", dataListItem.get("time"));
									irradianceListItem.put("download_time", dataListItem.get("download_time"));
									irradianceListItem.put("time_format", dataListItem.get("time_format"));
									irradianceListItem.put("time_full", dataListItem.get("time_full"));
									irradianceListItem.put("categories_time", dataListItem.get("categories_time"));
									irradianceListItem.put("chart_energy_kwh", dataListItem.get("nvm_irradiance"));
									irradianceList.add(irradianceListItem);
								}
								
								powerItem.put("data_energy", powerList);
								powerItem.put("type", "energy");
								powerItem.put("devicename", "Power");
								powerItem.put("deviceType", "meter");
								dataEnergy.add(powerItem);
								
								if (dataListDeviceIrr.size() > 0) {
									expectedPowerItem.put("data_energy", expectedPowerList);
									expectedPowerItem.put("type", "expected_power");
									expectedPowerItem.put("devicename", "Expected Power");
									dataEnergy.add(expectedPowerItem);
									
									irradianceItem.put("data_energy", irradianceList);
									irradianceItem.put("type", "irradiance");
									irradianceItem.put("devicename", "Irradiance");
									dataEnergy.add(irradianceItem);
								}
							}
						} else {
							Map<String, Object> deviceItem1 = new HashMap<>();
							
							// get list of time to exclude data from
							for (int i = 0; i < dataListDeviceMeter.size(); i++) {
								Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
								List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", device);
								device.put("hidden_data_list", hiddenDataList);
								// Show each meter
								if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
									Map<String, Object> deviceItemEach5 = new HashMap<>();
									device.put("data_send_time", obj.getData_send_time());
									List dataPower = queryForList("CustomerView.getDataPowerTodayEachMeter", device);
									if (dataPower.size() > 0) {
										deviceItemEach5.put("data_energy", dataPower);
										deviceItemEach5.put("type", "energy");
										deviceItemEach5.put("devicename", device.get("devicename"));
										deviceItemEach5.put("deviceType", "meter");
										dataEnergy.add(deviceItemEach5);
									}
								}
							}
							
							obj.setGroupMeter(dataListDeviceMeter);
							List dataPower1 = queryForList("CustomerView.getDataPowerToday", obj);
							if (dataPower1.size() > 0) {
								deviceItem1.put("data_energy", dataPower1);
								deviceItem1.put("type", "energy");
								deviceItem1.put("devicename", "Power");
								deviceItem1.put("deviceType", "meter");
								dataEnergy.add(deviceItem1);
							}
							
							
							// Get Irradiance
							if (dataListDeviceIrr.size() > 0) {
								int countExpectedPower = 1;
								for(int i = 0; i < dataListDeviceIrr.size(); i++) {
									Map<String, Object> deviceIrrItem1 = new HashMap<>();
									Map<String, Object> deviceExpectedPowerItem1 = new HashMap<>();
									
									List dataListAIrrDevice = new ArrayList<>();
									
									Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
									// get list of time to exclude data from
									List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", item);
									item.put("hidden_data_list", hiddenDataList);
									dataListAIrrDevice.add(item);
									
									obj.setGroupMeter(dataListAIrrDevice);
									
									List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceToday", obj);
									if(dataIrradianceDevice.size() > 0 ) {
										// Get Expected Power
										if (countExpectedPower == 1) {
											deviceExpectedPowerItem1.put("data_energy", dataIrradianceDevice);
											deviceExpectedPowerItem1.put("type", "expected_power");
											deviceExpectedPowerItem1.put("devicename", "Expected Power");
											dataEnergy.add(deviceExpectedPowerItem1);
										}
										
										// Get Irradiance
										deviceIrrItem1.put("data_energy", dataIrradianceDevice);
										deviceIrrItem1.put("type", "irradiance");
										deviceIrrItem1.put("devicename", dataListDeviceIrr.get(i));
										dataEnergy.add(deviceIrrItem1);
										countExpectedPower++;
									}
								}
							}
						}
						
						break;
				case "custom": {
					// Create list date 
					SimpleDateFormat dateFormatCustom = new SimpleDateFormat("yyyy-MM-dd"); 
					SimpleDateFormat usFormatCustom = new SimpleDateFormat("MM/dd/yyyy");
					SimpleDateFormat usFormatCustomMonth = new SimpleDateFormat("MM/yyyy");

					SimpleDateFormat catFormatCustomDay = new SimpleDateFormat("MM/dd");
					
					SimpleDateFormat catFormatCustom = new SimpleDateFormat("MMM. yyyy");
					SimpleDateFormat catFormatCustomMonth = new SimpleDateFormat("MMM. yyyy");

					SimpleDateFormat usFormatCustomYear = new SimpleDateFormat("yyyy");
					SimpleDateFormat catFormatCustomYear = new SimpleDateFormat("yyyy");
					
					Date startDateCustom = dateFormatCustom.parse(obj.getStart_date() + " AM");
					Calendar calCustom = Calendar.getInstance();
					calCustom.setTime(startDateCustom);
					
					
					Date endDateCustom = dateFormatCustom.parse(obj.getEnd_date() + " PM");
					Calendar calEndCustom = Calendar.getInstance();
					calEndCustom.setTime(endDateCustom);

					List<ClientMonthlyDateEntity> categories = new ArrayList<ClientMonthlyDateEntity> ();
					long forCountYTD = ChronoUnit.DAYS.between(calCustom.getTime().toInstant(), calEndCustom.getTime().toInstant());
					
					switch (obj.getData_send_time()) {
						case 4:
							int day = 1;
												
							for(int t = 0; t <= forCountYTD; t++) {
								calCustom.setTime(startDateCustom);
								ClientMonthlyDateEntity headerDate = new ClientMonthlyDateEntity();
								calCustom.add(Calendar.DATE, t * day);
								headerDate.setDownload_time(usFormatCustom.format(calCustom.getTime()));
								headerDate.setTime_full(usFormatCustom.format(calCustom.getTime()));
								headerDate.setTime_format(usFormatCustom.format(calCustom.getTime()));
								headerDate.setCategories_time(forCountYTD <= 44 ? catFormatCustomDay.format(calCustom.getTime()) : catFormatCustom.format(calCustom.getTime()));
								headerDate.setChart_energy_kwh(0.001);
								headerDate.setNvm_irradiance(0.001);
								headerDate.setExpected_power(0.001);
								categories.add(headerDate);
							}
							break;
						case 5:
							LocalDate dateToSelect = LocalDate.of(calCustom.get(Calendar.YEAR), calCustom.get(Calendar.MONTH) + 1, calCustom.get(Calendar.DAY_OF_MONTH));
							LocalDate lastVisible = LocalDate.of(calEndCustom.get(Calendar.YEAR), calEndCustom.get(Calendar.MONTH) + 1, calEndCustom.get(Calendar.DAY_OF_MONTH));
							long forCountYTD7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
							int YTD7Day = 1;
							
							for(int t = 0; t <= forCountYTD7Day; t++) {
								calCustom.setTime(startDateCustom);
								ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
								calCustom.add(Calendar.WEEK_OF_YEAR, t * YTD7Day);
								headerDateLT.setDownload_time(usFormatCustom.format(calCustom.getTime()));
								headerDateLT.setTime_format(String.valueOf(t));
								headerDateLT.setTime_full(usFormatCustom.format(calCustom.getTime()));
								headerDateLT.setCategories_time(catFormatCustom.format(calCustom.getTime()));
								headerDateLT.setChart_energy_kwh(0.001);
								headerDateLT.setNvm_irradiance(0.001);
								headerDateLT.setExpected_power(0.001);
								categories.add(headerDateLT);
							}
							break;
						case 6: 
							YearMonth startMonth = YearMonth.of( calCustom.get(Calendar.YEAR) , calCustom.get(Calendar.MONTH) + 1 );
							YearMonth endMonth = YearMonth.of(calEndCustom.get(Calendar.YEAR) , calEndCustom.get(Calendar.MONTH) + 1);
							long forCountYTDMonth = ChronoUnit.MONTHS.between(startMonth, endMonth);
							int monthYTD = 1;
							
							for(int t = 0; t <= forCountYTDMonth; t++) {
								calCustom.setTime(startDateCustom);
								ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
								calCustom.add(Calendar.MONTH, t * monthYTD);
								headerDateLT.setDownload_time(usFormatCustomMonth.format(calCustom.getTime()));
								headerDateLT.setTime_full(usFormatCustomMonth.format(calCustom.getTime()));
								headerDateLT.setTime_format(usFormatCustomMonth.format(calCustom.getTime()));
								headerDateLT.setCategories_time(catFormatCustomMonth.format(calCustom.getTime()));
								headerDateLT.setChart_energy_kwh(0.001);
								headerDateLT.setNvm_irradiance(0.001);
								headerDateLT.setExpected_power(0.001);
								categories.add(headerDateLT);
							}
							break;
						case 7:
							YearMonth startMonthCustom = YearMonth.of( calCustom.get(Calendar.YEAR) , calCustom.get(Calendar.MONTH) + 1 );
							YearMonth endMonthCustom = YearMonth.of(calEndCustom.get(Calendar.YEAR) , calEndCustom.get(Calendar.MONTH) + 1);
							long forCountLTYear = ChronoUnit.YEARS.between(startMonthCustom, endMonthCustom);
							if(calCustom.get(Calendar.MONTH) > calEndCustom.get(Calendar.MONTH)) {
									forCountLTYear += 1;
							}
							int yearLT = 1;
							
							for(int t = 0; t <= forCountLTYear; t++) {
								calCustom.setTime(startDateCustom);
								ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
								calCustom.add(Calendar.YEAR, t * yearLT);
								headerDateLT.setDownload_time(usFormatCustomYear.format(calCustom.getTime()));
								headerDateLT.setTime_full(usFormatCustomYear.format(calCustom.getTime()));
								headerDateLT.setTime_format(usFormatCustomYear.format(calCustom.getTime()));
								headerDateLT.setCategories_time(catFormatCustomYear.format(calCustom.getTime()));
								headerDateLT.setChart_energy_kwh(0.001);
								headerDateLT.setNvm_irradiance(0.001);
								headerDateLT.setExpected_power(0.001);
								categories.add(headerDateLT);
							}
							break;
					}
					
					// get list of time to exclude data from
					List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList);
					obj.setGroupMeter(dataListDeviceMeter);
					
					List dataPowerM = null;
					if (obj.getEnable_virtual_device() == 1) {
						 obj.setDatatablename(obj.getTable_data_virtual());
						 dataPowerM = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataVirtualDeviceCustomAtMost5Days", obj) : queryForList("CustomerView.getDataVirtualDeviceCustom", obj);
					} else {
						 dataPowerM = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataPowerCustomAtMost5Days", obj) : queryForList("CustomerView.getDataPowerCustom", obj);
					}
					
					List<ClientMonthlyDateEntity> dataNew = new ArrayList<ClientMonthlyDateEntity> ();
					if(dataPowerM.size() > 0 && categories.size() > 0) {
						for (ClientMonthlyDateEntity item : categories) {
							boolean flag = false;
							ClientMonthlyDateEntity mapItemObj = new ClientMonthlyDateEntity();
							for( int v = 0; v < dataPowerM.size(); v++){
								Map<String, Object> itemT = (Map<String, Object>) dataPowerM.get(v);
								String categoriesTime = item.getTime_format();
								String powerTime = itemT.get("time_format").toString();
								if (categoriesTime.equals(powerTime)) {
						        	flag = true;
						        	mapItemObj.setCategories_time(itemT.get("categories_time").toString());
						        	mapItemObj.setTime_format(itemT.get("time_format").toString());
						        	mapItemObj.setTime_full(itemT.get("time_full").toString());
						        	mapItemObj.setDownload_time(itemT.get("download_time").toString());
						        	mapItemObj.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
						        	mapItemObj.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
						        	mapItemObj.setExpected_power(Double.parseDouble(itemT.get("expected_power").toString()) );
						        	break;
						        }
							}
							
							if(flag == false) {
								ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
								mapItem.setCategories_time(item.getCategories_time());
								mapItem.setTime_format(item.getTime_format());
								mapItem.setTime_full(item.getTime_full());
								mapItem.setDownload_time(item.getDownload_time());
								mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
								mapItem.setNvm_irradiance(item.getNvm_irradiance());
								mapItem.setExpected_power(item.getExpected_power());
								dataNew.add(mapItem);
							} else {
								dataNew.add(mapItemObj);
							}
						}
					}
					
					Map<String, Object> deviceItemM = new HashMap<>();
					if (dataPowerM.size() > 0) {
						deviceItemM.put("data_energy", dataNew);
						deviceItemM.put("type", "energy");
						deviceItemM.put("devicename", "Energy output");
						deviceItemM.put("deviceType", "meter");
						dataEnergy.add(deviceItemM);
					}
					
					// Show each meter 
					if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
						for (int i = 0; i < dataListDeviceMeter.size(); i++) {
							Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
							List hiddenDataListDevice = queryForList("CustomerView.getHiddenDataListByDevice", device);
							device.put("hidden_data_list", hiddenDataListDevice);
							// Show each meter
							Map<String, Object> deviceItemEach5 = new HashMap<>();
							device.put("data_send_time", obj.getData_send_time());
							device.put("table_data_report", obj.getTable_data_report());
							List dataPower = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataPowerCustomAtMost5DaysEachMeter", device) : queryForList("CustomerView.getDataPowerThisMonthEachMeter", device);
							
							if (dataPower.size() > 0) {
								deviceItemEach5.put("data_energy", dataPower);
								deviceItemEach5.put("type", "energy");
								deviceItemEach5.put("devicename", device.get("devicename"));
								deviceItemEach5.put("deviceType", "meter");
								dataEnergy.add(deviceItemEach5);
							}
						}
					}
				
					break;
				}
				case "3_day":
							if (obj.getEnable_virtual_device() == 1) {
								// Show each meter
								if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
									for (int i = 0; i < dataListDeviceMeter.size(); i++) {
										Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
										List hiddenDataListDevice = queryForList("CustomerView.getHiddenDataListByDevice", device);
										device.put("hidden_data_list", hiddenDataListDevice);
										Map<String, Object> deviceItemEach5 = new HashMap<>();
										device.put("data_send_time", obj.getData_send_time());
										List dataPower = queryForList("CustomerView.getDataPower3DayEachMeter", device);
										if (dataPower.size() > 0) {
											deviceItemEach5.put("data_energy", dataPower);
											deviceItemEach5.put("type", "energy");
											deviceItemEach5.put("devicename", device.get("devicename"));
											deviceItemEach5.put("deviceType", "meter");
											dataEnergy.add(deviceItemEach5);
										}
									}
								}
								
								obj.setDatatablename(obj.getTable_data_virtual());
								// get list of time to exclude data from
								List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
								obj.setHidden_data_list(hiddenDataList);
								
								List dataList = queryForList("CustomerView.getDataVirtualDevice3Day", obj);
								if (dataList.size() > 0) {
									Map<String, Object> powerItem = new HashMap<>();
									Map<String, Object> expectedPowerItem = new HashMap<>();
									Map<String, Object> irradianceItem = new HashMap<>();
									List powerList = new ArrayList<>();
									List expectedPowerList = new ArrayList<>();
									List irradianceList = new ArrayList<>();
									
									for (int i = 0; i < dataList.size(); i++) {
										Map<String, Object> dataListItem = (Map<String, Object>) dataList.get(i);
										Map<String, Object> powerListItem = new HashMap<>();
										Map<String, Object> expectedPowerListItem = new HashMap<>();
										Map<String, Object> irradianceListItem = new HashMap<>();
										
										powerListItem.put("time", dataListItem.get("time"));
										powerListItem.put("download_time", dataListItem.get("download_time"));
										powerListItem.put("time_format", dataListItem.get("time_format"));
										powerListItem.put("time_full", dataListItem.get("time_full"));
										powerListItem.put("categories_time", dataListItem.get("categories_time"));
										powerListItem.put("chart_energy_kwh", dataListItem.get("nvmActivePower"));
										powerList.add(powerListItem);

										expectedPowerListItem.put("time", dataListItem.get("time"));
										expectedPowerListItem.put("download_time", dataListItem.get("download_time"));
										expectedPowerListItem.put("time_format", dataListItem.get("time_format"));
										expectedPowerListItem.put("time_full", dataListItem.get("time_full"));
										expectedPowerListItem.put("categories_time", dataListItem.get("categories_time"));
										expectedPowerListItem.put("expected_power", dataListItem.get("expected_power"));
										expectedPowerList.add(expectedPowerListItem);

										irradianceListItem.put("time", dataListItem.get("time"));
										irradianceListItem.put("download_time", dataListItem.get("download_time"));
										irradianceListItem.put("time_format", dataListItem.get("time_format"));
										irradianceListItem.put("time_full", dataListItem.get("time_full"));
										irradianceListItem.put("categories_time", dataListItem.get("categories_time"));
										irradianceListItem.put("chart_energy_kwh", dataListItem.get("nvm_irradiance"));
										irradianceList.add(irradianceListItem);
									}
									
									powerItem.put("data_energy", powerList);
									powerItem.put("type", "energy");
									powerItem.put("devicename", "Power");
									powerItem.put("deviceType", "meter");
									dataEnergy.add(powerItem);
									
									if (dataListDeviceIrr.size() > 0) {
										expectedPowerItem.put("data_energy", expectedPowerList);
										expectedPowerItem.put("type", "expected_power");
										expectedPowerItem.put("devicename", "Expected Power");
										dataEnergy.add(expectedPowerItem);
										
										irradianceItem.put("data_energy", irradianceList);
										irradianceItem.put("type", "irradiance");
										irradianceItem.put("devicename", "Irradiance");
										dataEnergy.add(irradianceItem);
									}
								}
							} else {
								Map<String, Object> deviceItem5 = new HashMap<>();
								
								// get list of time to exclude data from
								for (int i = 0; i < dataListDeviceMeter.size(); i++) {
									Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
									List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", device);
									device.put("hidden_data_list", hiddenDataList);
									// Show each meter
									if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
										Map<String, Object> deviceItemEach5 = new HashMap<>();
										device.put("data_send_time", obj.getData_send_time());
										List dataPower = queryForList("CustomerView.getDataPower3DayEachMeter", device);
										if (dataPower.size() > 0) {
											deviceItemEach5.put("data_energy", dataPower);
											deviceItemEach5.put("type", "energy");
											deviceItemEach5.put("devicename", device.get("devicename"));
											deviceItemEach5.put("deviceType", "meter");
											dataEnergy.add(deviceItemEach5);
										}
									}
								}
								
								obj.setGroupMeter(dataListDeviceMeter);
								List dataPower5 = queryForList("CustomerView.getDataPower3Day", obj);
								if (dataPower5.size() > 0) {
									deviceItem5.put("data_energy", dataPower5);
									deviceItem5.put("type", "energy");
									deviceItem5.put("devicename", "Power");
									deviceItem5.put("deviceType", "meter");
									dataEnergy.add(deviceItem5);
								}
								
								// Get Irradiance
								
								if (dataListDeviceIrr.size() > 0) {
									int countExpectedPower = 1;
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										Map<String, Object> deviceIrrItem5 = new HashMap<>();
										Map<String, Object> deviceExpectedPowerItem5 = new HashMap<>();
	
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										// get list of time to exclude data from
										List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", item);
										item.put("hidden_data_list", hiddenDataList);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradiance3Day", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											// Get Expected Power
											if(countExpectedPower == 1) {
												deviceExpectedPowerItem5.put("data_energy", dataIrradianceDevice);
												deviceExpectedPowerItem5.put("type", "expected_power");
												deviceExpectedPowerItem5.put("devicename", "Expected Power");
												dataEnergy.add(deviceExpectedPowerItem5);
											}
											
											// Get Irradiance
											deviceIrrItem5.put("data_energy", dataIrradianceDevice);
											deviceIrrItem5.put("type", "irradiance");
											deviceIrrItem5.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem5);
											countExpectedPower++;
										}
									}
								}
							}
							
							break;
				case "this_week":
				case "last_week":
						if (obj.getEnable_virtual_device() == 1) {
							// Show each meter
							if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
								for (int i = 0; i < dataListDeviceMeter.size(); i++) {
									Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
									List hiddenDataListDevice = queryForList("CustomerView.getHiddenDataListByDevice", device);
									device.put("hidden_data_list", hiddenDataListDevice);
									Map<String, Object> deviceItemEach5 = new HashMap<>();
									device.put("data_send_time", obj.getData_send_time());
									List dataPower = queryForList("CustomerView.getDataEnergyThisWeekEachMeter", device);
									if (dataPower.size() > 0) {
										deviceItemEach5.put("data_energy", dataPower);
										deviceItemEach5.put("type", "energy");
										deviceItemEach5.put("devicename", device.get("devicename"));
										deviceItemEach5.put("deviceType", "meter");
										dataEnergy.add(deviceItemEach5);
									}
								}
							}
							
							obj.setDatatablename(obj.getTable_data_virtual());
							
							// get list of time to exclude data from
							List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
							obj.setHidden_data_list(hiddenDataList);
							
							List dataList = queryForList("CustomerView.getDataVirtualDeviceThisWeek", obj);
							if (dataList.size() > 0) {
								Map<String, Object> energyItem = new HashMap<>();
								Map<String, Object> irradianceItem = new HashMap<>();
								Map<String, Object> expectedEnergyItem = new HashMap<>();
								List energyList = new ArrayList<>();
								List irradianceList = new ArrayList<>();
								List expectedEnergyList = new ArrayList<>();
								
								for (int i = 0; i < dataList.size(); i++) {
									Map<String, Object> dataListItem = (Map<String, Object>) dataList.get(i);
									Map<String, Object> energyListItem = new HashMap<>();
									Map<String, Object> expectedEnergyListItem = new HashMap<>();
									Map<String, Object> irradianceListItem = new HashMap<>();
									
									energyListItem.put("time", dataListItem.get("time"));
									energyListItem.put("download_time", dataListItem.get("download_time"));
									energyListItem.put("time_format", dataListItem.get("time_format"));
									energyListItem.put("time_full", dataListItem.get("time_full"));
									energyListItem.put("categories_time", dataListItem.get("categories_time"));
									energyListItem.put("chart_energy_kwh", dataListItem.get("nvmActiveEnergy"));
									energyList.add(energyListItem);

									expectedEnergyListItem.put("time", dataListItem.get("time"));
									expectedEnergyListItem.put("download_time", dataListItem.get("download_time"));
									expectedEnergyListItem.put("time_format", dataListItem.get("time_format"));
									expectedEnergyListItem.put("time_full", dataListItem.get("time_full"));
									expectedEnergyListItem.put("categories_time", dataListItem.get("categories_time"));
									expectedEnergyListItem.put("expected_energy", dataListItem.get("expected_energy"));
									expectedEnergyList.add(expectedEnergyListItem);

									irradianceListItem.put("time", dataListItem.get("time"));
									irradianceListItem.put("download_time", dataListItem.get("download_time"));
									irradianceListItem.put("time_format", dataListItem.get("time_format"));
									irradianceListItem.put("time_full", dataListItem.get("time_full"));
									irradianceListItem.put("categories_time", dataListItem.get("categories_time"));
									irradianceListItem.put("chart_energy_kwh", dataListItem.get("nvm_irradiance"));
									irradianceList.add(irradianceListItem);
								}
								
								energyItem.put("data_energy", energyList);
								energyItem.put("type", "energy");
								energyItem.put("devicename", "Energy output");
								energyItem.put("deviceType", "meter");
								dataEnergy.add(energyItem);
								
								if (dataListDeviceIrr.size() > 0) {
									expectedEnergyItem.put("data_energy", expectedEnergyList);
									expectedEnergyItem.put("type", "expected_energy");
									expectedEnergyItem.put("devicename", "Expected Energy");
									dataEnergy.add(expectedEnergyItem);
									
									irradianceItem.put("data_energy", irradianceList);
									irradianceItem.put("type", "irradiance");
									irradianceItem.put("devicename", "Irradiance");
									dataEnergy.add(irradianceItem);
								}
							}
						} else {
							Map<String, Object> deviceItem5 = new HashMap<>();
							
							// get list of time to exclude data from
							for (int i = 0; i < dataListDeviceMeter.size(); i++) {
								Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
								List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", device);
								device.put("hidden_data_list", hiddenDataList);
								// Show each meter
								if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
									Map<String, Object> deviceItemEach5 = new HashMap<>();
									device.put("data_send_time", obj.getData_send_time());
									List dataPower = queryForList("CustomerView.getDataEnergyThisWeekEachMeter", device);
									if (dataPower.size() > 0) {
										deviceItemEach5.put("data_energy", dataPower);
										deviceItemEach5.put("type", "energy");
										deviceItemEach5.put("devicename", device.get("devicename"));
										deviceItemEach5.put("deviceType", "meter");
										dataEnergy.add(deviceItemEach5);
									}
								}
							}
							
							obj.setGroupMeter(dataListDeviceMeter);
							List dataPower5 = queryForList("CustomerView.getDataEnergyThisWeek", obj);
							if (dataPower5.size() > 0) {
								deviceItem5.put("data_energy", dataPower5);
								deviceItem5.put("type", "energy");
								deviceItem5.put("devicename", "Energy output");
								deviceItem5.put("deviceType", "meter");
								dataEnergy.add(deviceItem5);
							}
							
							// Get Irradiance
							
							if (dataListDeviceIrr.size() > 0) {
								int countExpectedPower = 1;
								for(int i = 0; i < dataListDeviceIrr.size(); i++) {
									Map<String, Object> deviceIrrItem5 = new HashMap<>();
									Map<String, Object> deviceExpectedEnergyItem5 = new HashMap<>();
									
									List dataListAIrrDevice = new ArrayList<>();
									
									Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
									// get list of time to exclude data from
									List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", item);
									item.put("hidden_data_list", hiddenDataList);
									dataListAIrrDevice.add(item);
									
									obj.setGroupMeter(dataListAIrrDevice);
									
									List dataIrradianceDevice = queryForList("CustomerView.getDataIrradiance3Day", obj);
	
									if(dataIrradianceDevice.size() > 0 ) {
										// Get Expected Energy
										if (countExpectedPower == 1) {
											deviceExpectedEnergyItem5.put("data_energy", dataIrradianceDevice);
											deviceExpectedEnergyItem5.put("type", "expected_energy");
											deviceExpectedEnergyItem5.put("devicename", "Expected Energy");
											dataEnergy.add(deviceExpectedEnergyItem5);
										}		
										
										// Get Irradiance
										deviceIrrItem5.put("data_energy", dataIrradianceDevice);
										deviceIrrItem5.put("type", "irradiance");
										deviceIrrItem5.put("devicename", dataListDeviceIrr.get(i));
										dataEnergy.add(deviceIrrItem5);
										countExpectedPower++;
									}
								}
							}
						}
					break;
				case "last_month":
				case "this_month":
					
					
					
					// Create list date 
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
					SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy");
					SimpleDateFormat catFormat = new SimpleDateFormat("MM/dd");
					SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
					Date startDate = dateFormat.parse(obj.getStart_date() + " AM");
					Calendar cal = Calendar.getInstance();
					cal.setTime(startDate);
					
					cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
					
					List<ClientMonthlyDateEntity> categories = new ArrayList<ClientMonthlyDateEntity> ();
					int day = 1;
					int forCount = Integer.parseInt(dayFormat.format(cal.getTime()).toString());
					
					for(int t = 0; t < forCount; t++) {
						cal.setTime(startDate);
						ClientMonthlyDateEntity headerDate = new ClientMonthlyDateEntity();
						cal.add(Calendar.DATE, t * day);
						headerDate.setDownload_time(usFormat.format(cal.getTime()));
						headerDate.setTime_full(usFormat.format(cal.getTime()));
						headerDate.setTime_format(usFormat.format(cal.getTime()));
						headerDate.setCategories_time(catFormat.format(cal.getTime()));
						headerDate.setChart_energy_kwh(0.001);
						headerDate.setExpected_power(0.001);
						headerDate.setNvm_irradiance(0.001);
						categories.add(headerDate);
					}

					// get list of time to exclude data from
					List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList);
					obj.setGroupMeter(dataListDeviceMeter);
					
					List dataPowerM = null;
					if (obj.getEnable_virtual_device() == 1) {
						obj.setDatatablename(obj.getTable_data_virtual());
						 dataPowerM = queryForList("CustomerView.getDataVirtualDeviceThisMonth", obj);
					} else {
						 dataPowerM = queryForList("CustomerView.getDataPowerThisMonth", obj);
					}
					
					
					List<ClientMonthlyDateEntity> dataNew = new ArrayList<ClientMonthlyDateEntity> ();
					if(dataPowerM.size() > 0 && categories.size() > 0) {
						for (ClientMonthlyDateEntity item : categories) {
							boolean flag = false;
							ClientMonthlyDateEntity mapItemObj = new ClientMonthlyDateEntity();
							for( int v = 0; v < dataPowerM.size(); v++){
								Map<String, Object> itemT = (Map<String, Object>) dataPowerM.get(v);
								String categoriesTime = item.getTime_format();
								String powerTime = itemT.get("time_format").toString();
								if (categoriesTime.equals(powerTime)) {
						        	flag = true;
						        	mapItemObj.setCategories_time(itemT.get("categories_time").toString());
						        	mapItemObj.setTime_format(itemT.get("time_format").toString());
						        	mapItemObj.setTime_full(itemT.get("time_full").toString());
						        	mapItemObj.setDownload_time(itemT.get("download_time").toString());
						        	mapItemObj.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
						        	mapItemObj.setExpected_power(Double.parseDouble(itemT.get("expected_energy").toString()) );
						        	mapItemObj.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
						        	break;
						        }
							}
							
							if(flag == false) {
								ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
								mapItem.setCategories_time(item.getCategories_time());
								mapItem.setTime_format(item.getTime_format());
								mapItem.setTime_full(item.getTime_full());
								mapItem.setDownload_time(item.getDownload_time());
								mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
								mapItem.setExpected_power(item.getExpected_power());
								mapItem.setNvm_irradiance(item.getNvm_irradiance());
								dataNew.add(mapItem);
							} else {
								dataNew.add(mapItemObj);
							}
						}
					}
					
					
					Map<String, Object> deviceItemM = new HashMap<>();
					if (dataPowerM.size() > 0) {
						deviceItemM.put("data_energy", dataNew);
						deviceItemM.put("type", "energy");
						deviceItemM.put("devicename", "Energy output");
						deviceItemM.put("deviceType", "meter");
						dataEnergy.add(deviceItemM);
					}
					
					// Show each meter 
					if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
						for (int i = 0; i < dataListDeviceMeter.size(); i++) {
							Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
							List hiddenDataListDevice = queryForList("CustomerView.getHiddenDataListByDevice", device);
							device.put("hidden_data_list", hiddenDataListDevice);
							// Show each meter
							Map<String, Object> deviceItemEach5 = new HashMap<>();
							device.put("data_send_time", obj.getData_send_time());
							device.put("table_data_report", obj.getTable_data_report());
							List dataPower = queryForList("CustomerView.getDataPowerThisMonthEachMeter", device);
							if (dataPower.size() > 0) {
								deviceItemEach5.put("data_energy", dataPower);
								deviceItemEach5.put("type", "energy");
								deviceItemEach5.put("devicename", device.get("devicename"));
								deviceItemEach5.put("deviceType", "meter");
								dataEnergy.add(deviceItemEach5);
							}
						}
					}
						
					break;
					
				case "year":
					// Create list date 
					SimpleDateFormat dateFormatYTD = new SimpleDateFormat("yyyy-MM-dd"); 
					SimpleDateFormat usFormatYTD = new SimpleDateFormat("MM/dd/yyyy");
					SimpleDateFormat usFormatYTDMonth = new SimpleDateFormat("MM/yyyy");
					
					SimpleDateFormat catFormatYTD = new SimpleDateFormat("MMM. yyyy");
					SimpleDateFormat catFormatYTDMonth = new SimpleDateFormat("MMM. yyyy");
					
					Date startDateYTD = dateFormatYTD.parse(obj.getStart_date() + " AM");
					Calendar calYTD = Calendar.getInstance();
					calYTD.setTime(startDateYTD);
					
					
					Date endDateYTD = dateFormatYTD.parse(obj.getEnd_date() + " PM");
					Calendar calEndYTD = Calendar.getInstance();
					calEndYTD.setTime(endDateYTD);

					List<ClientMonthlyDateEntity> categoriesYTD = new ArrayList<ClientMonthlyDateEntity> ();
					
					switch (obj.getData_send_time()) {
						case 4:
							long forCountYTD = ChronoUnit.DAYS.between(calYTD.getTime().toInstant(), calEndYTD.getTime().toInstant());
							int dayYTD = 1;
							
							for(int t = 0; t <= forCountYTD; t++) {
								calYTD.setTime(startDateYTD);
								
								ClientMonthlyDateEntity headerDateYTD = new ClientMonthlyDateEntity();
								calYTD.add(Calendar.DATE, t * dayYTD);
								headerDateYTD.setDownload_time(usFormatYTD.format(calYTD.getTime()));
								headerDateYTD.setTime_full(usFormatYTD.format(calYTD.getTime()));
								headerDateYTD.setTime_format(usFormatYTD.format(calYTD.getTime()));
								headerDateYTD.setCategories_time(catFormatYTD.format(calYTD.getTime()));
								headerDateYTD.setChart_energy_kwh(0.001);
								headerDateYTD.setExpected_power(0.001);
								headerDateYTD.setNvm_irradiance(0.001);
								categoriesYTD.add(headerDateYTD);
							}
							break;
						case 5:
							LocalDate dateToSelect = LocalDate.of(calYTD.get(Calendar.YEAR), calYTD.get(Calendar.MONTH) + 1, calYTD.get(Calendar.DAY_OF_MONTH));
							LocalDate lastVisible = LocalDate.of(calEndYTD.get(Calendar.YEAR), calEndYTD.get(Calendar.MONTH) + 1, calEndYTD.get(Calendar.DAY_OF_MONTH));
							long forCountYTD7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
							int YTD7Day = 1;
							
							for(int t = 0; t <= forCountYTD7Day; t++) {
								calYTD.setTime(startDateYTD);
								ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
								calYTD.add(Calendar.WEEK_OF_YEAR, t * YTD7Day);
								headerDateLT.setDownload_time(usFormatYTD.format(calYTD.getTime()));
								headerDateLT.setTime_format(String.valueOf(t));
								headerDateLT.setTime_full(usFormatYTD.format(calYTD.getTime()));
								headerDateLT.setCategories_time(catFormatYTD.format(calYTD.getTime()));
								headerDateLT.setChart_energy_kwh(0.001);
								headerDateLT.setExpected_power(0.001);
								headerDateLT.setNvm_irradiance(0.001);
								categoriesYTD.add(headerDateLT);
							}
							break;
						case 6:
							YearMonth startMonth = YearMonth.of( calYTD.get(Calendar.YEAR) , calYTD.get(Calendar.MONTH) + 1 );
					        YearMonth endMonth = YearMonth.of(calEndYTD.get(Calendar.YEAR) , calEndYTD.get(Calendar.MONTH) + 1);
					        long forCountYTDMonth = ChronoUnit.MONTHS.between(startMonth, endMonth);
							int monthYTD = 1;
							
							for(int t = 0; t <= forCountYTDMonth; t++) {
								calYTD.setTime(startDateYTD);
								ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
								calYTD.add(Calendar.MONTH, t * monthYTD);
								headerDateLT.setDownload_time(usFormatYTDMonth.format(calYTD.getTime()));
								headerDateLT.setTime_full(usFormatYTDMonth.format(calYTD.getTime()));
								headerDateLT.setTime_format(usFormatYTDMonth.format(calYTD.getTime()));
								headerDateLT.setCategories_time(catFormatYTDMonth.format(calYTD.getTime()));
								headerDateLT.setChart_energy_kwh(0.001);
								headerDateLT.setExpected_power(0.001);
								headerDateLT.setNvm_irradiance(0.001);
								categoriesYTD.add(headerDateLT);
							}
							break;
					}
					
					// get list of time to exclude data from
					List hiddenDataList1 = queryForList("CustomerView.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList1);
					obj.setGroupMeter(dataListDeviceMeter);
					
					List dataPowerMYTD = null;
					if (obj.getEnable_virtual_device() == 1) {
						obj.setDatatablename(obj.getTable_data_virtual());
						dataPowerMYTD = queryForList("CustomerView.getDataVirtualDeviceYear", obj);
					} else {
						dataPowerMYTD = queryForList("CustomerView.getDataPowerYear", obj);
					}
					
					List<ClientMonthlyDateEntity> dataNewYTD = new ArrayList<ClientMonthlyDateEntity> ();
					if(dataPowerMYTD.size() > 0 && categoriesYTD.size() > 0) {
						for (ClientMonthlyDateEntity item : categoriesYTD) {
							boolean flag = false;
							ClientMonthlyDateEntity mapItemObjYTD = new ClientMonthlyDateEntity();
							for( int v = 0; v < dataPowerMYTD.size(); v++){
								Map<String, Object> itemT = (Map<String, Object>) dataPowerMYTD.get(v);
								String categoriesTimeYTD = item.getTime_format();
								String powerTimeYTD = itemT.get("time_format").toString();
								if (categoriesTimeYTD.equals(powerTimeYTD)) {
									flag = true;
									mapItemObjYTD.setCategories_time(itemT.get("categories_time").toString());
									mapItemObjYTD.setTime_format(itemT.get("time_format").toString());
									mapItemObjYTD.setTime_full(itemT.get("time_full").toString());
									mapItemObjYTD.setDownload_time(itemT.get("download_time").toString());
									mapItemObjYTD.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
									mapItemObjYTD.setExpected_power(Double.parseDouble(itemT.get("expected_energy").toString()) );
									mapItemObjYTD.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
									break;
								}
							}
							
							if(flag == false) {
								ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
								mapItem.setCategories_time(item.getCategories_time());
								mapItem.setTime_format(item.getTime_format());
								mapItem.setTime_full(item.getTime_full());
								mapItem.setDownload_time(item.getDownload_time());
								mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
								mapItem.setExpected_power(item.getExpected_power());
								mapItem.setNvm_irradiance(item.getNvm_irradiance());
								dataNewYTD.add(mapItem);
							} else {
								dataNewYTD.add(mapItemObjYTD);
							}
						}
					}
					
					Map<String, Object> deviceItemMYTD = new HashMap<>();
					if (dataPowerMYTD.size() > 0) {
						deviceItemMYTD.put("data_energy", dataNewYTD);
						deviceItemMYTD.put("type", "energy");
						deviceItemMYTD.put("devicename", "Energy output");
						deviceItemMYTD.put("deviceType", "meter");
						dataEnergy.add(deviceItemMYTD);
					}
					
					// Show each meter 
					if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
						for (int i = 0; i < dataListDeviceMeter.size(); i++) {
							Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
							List hiddenDataListDevice = queryForList("CustomerView.getHiddenDataListByDevice", device);
							device.put("hidden_data_list", hiddenDataListDevice);
							// Show each meter
							Map<String, Object> deviceItemEach5 = new HashMap<>();
							device.put("data_send_time", obj.getData_send_time());
							device.put("table_data_report", obj.getTable_data_report());
							List dataPower = queryForList("CustomerView.getDataPowerThisMonthEachMeter", device);
							
							if (dataPower.size() > 0) {
								deviceItemEach5.put("data_energy", dataPower);
								deviceItemEach5.put("type", "energy");
								deviceItemEach5.put("devicename", device.get("devicename"));
								deviceItemEach5.put("deviceType", "meter");
								dataEnergy.add(deviceItemEach5);
							}
						}
					}
					
					break;

				case "12_month":
					
					// Create list date 
					SimpleDateFormat dateFormat12 = new SimpleDateFormat("yyyy-MM-dd"); 
					SimpleDateFormat usFormat12 = new SimpleDateFormat("MM/yyyy");
					SimpleDateFormat catFormat12 = new SimpleDateFormat("MM/yyyy");
					
					SimpleDateFormat usFormat12Month7Day = new SimpleDateFormat("MM/dd/yyyy");
					SimpleDateFormat catFormat12Month7Day = new SimpleDateFormat("MMM. yyyy");
					
					SimpleDateFormat usFormat12MonthDay = new SimpleDateFormat("MM/dd/yyyy");
					SimpleDateFormat catFormat12MonthDay = new SimpleDateFormat("MMM. yyyy");
					
					Date startDate12 = dateFormat12.parse(obj.getStart_date() + " AM");
					Calendar cal12 = Calendar.getInstance();
					cal12.setTime(startDate12);					
					
					Date endDate12 = dateFormat12.parse(obj.getEnd_date() + " PM");
					Calendar calEnd12 = Calendar.getInstance();
					calEnd12.setTime(endDate12);

					List<ClientMonthlyDateEntity> categories12MonthDay = new ArrayList<ClientMonthlyDateEntity> ();
					
					switch (obj.getData_send_time()) {
						case 4:
							long forCountYTD = ChronoUnit.DAYS.between(cal12.getTime().toInstant(), calEnd12.getTime().toInstant());
							int day12Month = 1;
							
							for(int t = 0; t <= forCountYTD; t++) {
								cal12.setTime(startDate12);
								
								ClientMonthlyDateEntity headerDate12MonthDay = new ClientMonthlyDateEntity();
								cal12.add(Calendar.DATE, t * day12Month);
								headerDate12MonthDay.setDownload_time(usFormat12MonthDay.format(cal12.getTime()));
								headerDate12MonthDay.setTime_full(usFormat12MonthDay.format(cal12.getTime()));
								headerDate12MonthDay.setTime_format(usFormat12MonthDay.format(cal12.getTime()));
								headerDate12MonthDay.setCategories_time(catFormat12MonthDay.format(cal12.getTime()));
								headerDate12MonthDay.setChart_energy_kwh(0.001);
								headerDate12MonthDay.setExpected_power(0.001);
								headerDate12MonthDay.setNvm_irradiance(0.001);
								categories12MonthDay.add(headerDate12MonthDay);
							}
							break;
						case 5:
							LocalDate dateToSelect = LocalDate.of(cal12.get(Calendar.YEAR), cal12.get(Calendar.MONTH) + 1, cal12.get(Calendar.DAY_OF_MONTH));
							LocalDate lastVisible = LocalDate.of(calEnd12.get(Calendar.YEAR), calEnd12.get(Calendar.MONTH) + 1, calEnd12.get(Calendar.DAY_OF_MONTH));
							long forCount12Month7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
							int week12 = 1;
							
							for(int t = 0; t <= forCount12Month7Day; t++) {
								cal12.setTime(startDate12);
								ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
								cal12.add(Calendar.WEEK_OF_YEAR, t * week12);
								headerDateLT.setDownload_time(usFormat12Month7Day.format(cal12.getTime()));
								headerDateLT.setTime_format(String.valueOf(t));
								headerDateLT.setTime_full(usFormat12Month7Day.format(cal12.getTime()));
								headerDateLT.setCategories_time(catFormat12Month7Day.format(cal12.getTime()));
								headerDateLT.setChart_energy_kwh(0.001);
								headerDateLT.setExpected_power(0.001);
								headerDateLT.setNvm_irradiance(0.001);
								categories12MonthDay.add(headerDateLT);
							}
							break;
						case 6:
							cal12.set(Calendar.DAY_OF_MONTH, cal12.getActualMaximum(Calendar.DAY_OF_MONTH));
							YearMonth startMonth12 = YearMonth.of( cal12.get(Calendar.YEAR) , cal12.get(Calendar.MONTH) + 1 );
							YearMonth endMonth12 = YearMonth.of(calEnd12.get(Calendar.YEAR) , calEnd12.get(Calendar.MONTH) + 1);
							long forCount12Month = ChronoUnit.MONTHS.between(startMonth12, endMonth12);
							int day12 = 1;
							
							for(int t = 0; t <= forCount12Month; t++) {
								cal12.setTime(startDate12);
								ClientMonthlyDateEntity headerDate12 = new ClientMonthlyDateEntity();
								cal12.add(Calendar.MONTH, t * day12);
								headerDate12.setDownload_time(usFormat12.format(cal12.getTime()));
								headerDate12.setTime_full(usFormat12.format(cal12.getTime()));
								headerDate12.setTime_format(usFormat12.format(cal12.getTime()));
								headerDate12.setCategories_time(catFormat12.format(cal12.getTime()));
								headerDate12.setChart_energy_kwh(0.001);
								headerDate12.setExpected_power(0.001);
								headerDate12.setNvm_irradiance(0.001);
								categories12MonthDay.add(headerDate12);
							}
							break;
					}
					
					// get list of time to exclude data from
					List hiddenDataList2 = queryForList("CustomerView.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList2);
					obj.setGroupMeter(dataListDeviceMeter);
							
					List dataPowerM12MonthDay = null;
					if (obj.getEnable_virtual_device() == 1) {
						obj.setDatatablename(obj.getTable_data_virtual());
						dataPowerM12MonthDay = queryForList("CustomerView.getDataVirtualDeviceYear", obj);
					} else {
						dataPowerM12MonthDay = queryForList("CustomerView.getDataPowerYear", obj);
					}
					
					List<ClientMonthlyDateEntity> dataNew12MonthDay = new ArrayList<ClientMonthlyDateEntity> ();
					if(dataPowerM12MonthDay.size() > 0 && categories12MonthDay.size() > 0) {
						for (ClientMonthlyDateEntity item : categories12MonthDay) {
							boolean flag = false;
							ClientMonthlyDateEntity mapItemObj12MonthDay = new ClientMonthlyDateEntity();
							for( int v = 0; v < dataPowerM12MonthDay.size(); v++){
								Map<String, Object> itemT = (Map<String, Object>) dataPowerM12MonthDay.get(v);
								String categoriesTime12MonthDay = item.getTime_format();
								String powerTime12MonthDay = itemT.get("time_format").toString();
								if (categoriesTime12MonthDay.equals(powerTime12MonthDay)) {
											flag = true;
											mapItemObj12MonthDay.setCategories_time(itemT.get("categories_time").toString());
											mapItemObj12MonthDay.setTime_format(itemT.get("time_format").toString());
											mapItemObj12MonthDay.setTime_full(itemT.get("time_full").toString());
											mapItemObj12MonthDay.setDownload_time(itemT.get("download_time").toString());
											mapItemObj12MonthDay.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
								        	mapItemObj12MonthDay.setExpected_power(Double.parseDouble(itemT.get("expected_energy").toString()) );
											mapItemObj12MonthDay.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
											break;
										}
							}
							
							if(flag == false) {
								ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
								mapItem.setCategories_time(item.getCategories_time());
								mapItem.setTime_format(item.getTime_format());
								mapItem.setTime_full(item.getTime_full());
								mapItem.setDownload_time(item.getDownload_time());
								mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
								mapItem.setExpected_power(item.getExpected_power());
								mapItem.setNvm_irradiance(item.getNvm_irradiance());
								dataNew12MonthDay.add(mapItem);
							} else {
								dataNew12MonthDay.add(mapItemObj12MonthDay);
							}
						}
					}
					
					Map<String, Object> deviceItemM12MonthDay = new HashMap<>();
					if (dataPowerM12MonthDay.size() > 0) {
						deviceItemM12MonthDay.put("data_energy", dataNew12MonthDay);
						deviceItemM12MonthDay.put("type", "energy");
						deviceItemM12MonthDay.put("devicename", "Energy output");
						deviceItemM12MonthDay.put("deviceType", "meter");
						dataEnergy.add(deviceItemM12MonthDay);
					}
					
					// Show each meter 
					if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
						for (int i = 0; i < dataListDeviceMeter.size(); i++) {
							Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
							List hiddenDataListDevice = queryForList("CustomerView.getHiddenDataListByDevice", device);
							device.put("hidden_data_list", hiddenDataListDevice);
							// Show each meter
							Map<String, Object> deviceItemEach5 = new HashMap<>();
							device.put("data_send_time", obj.getData_send_time());
							device.put("table_data_report", obj.getTable_data_report());
							List dataPower = queryForList("CustomerView.getDataPowerThisMonthEachMeter", device);
							
							if (dataPower.size() > 0) {
								deviceItemEach5.put("data_energy", dataPower);
								deviceItemEach5.put("type", "energy");
								deviceItemEach5.put("devicename", device.get("devicename"));
								deviceItemEach5.put("deviceType", "meter");
								dataEnergy.add(deviceItemEach5);
							}
						}
					}
					
					break;
				case "lifetime":
					// Create list date 
					SimpleDateFormat dateFormatLT = new SimpleDateFormat("yyyy-MM-dd"); 
					SimpleDateFormat usFormatLTMonth = new SimpleDateFormat("MM/yyyy");
					SimpleDateFormat usFormatLTYear = new SimpleDateFormat("yyyy");
					SimpleDateFormat catFormatLTMonth = new SimpleDateFormat("MM/yyyy");
					SimpleDateFormat catFormatLTYear = new SimpleDateFormat("yyyy");
					Date startDateLT = dateFormatLT.parse(obj.getStart_date() + " AM");
					Calendar calLT = Calendar.getInstance();
					calLT.setTime(startDateLT);
					
					Date endDateLT = dateFormatLT.parse(obj.getEnd_date() + " PM");
					Calendar calEndLT = Calendar.getInstance();
					calEndLT.setTime(endDateLT);

					YearMonth startMonth = YearMonth.of( calLT.get(Calendar.YEAR) , calLT.get(Calendar.MONTH) + 1 );
			        YearMonth endMonth = YearMonth.of(calEndLT.get(Calendar.YEAR) , calEndLT.get(Calendar.MONTH) + 1);
			        
			        List<ClientMonthlyDateEntity> categoriesLT = new ArrayList<ClientMonthlyDateEntity> ();
			        
					switch (obj.getData_send_time()) {
						case 6:						
					        long forCountLT = ChronoUnit.MONTHS.between(startMonth, endMonth);
							int dayLT = 1;
							
							for(int t = 0; t <= forCountLT; t++) {
								calLT.setTime(startDateLT);
								ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
								calLT.add(Calendar.MONTH, t * dayLT);
								headerDateLT.setDownload_time(usFormatLTMonth.format(calLT.getTime()));
								headerDateLT.setTime_full(usFormatLTMonth.format(calLT.getTime()));
								headerDateLT.setTime_format(usFormatLTMonth.format(calLT.getTime()));
								headerDateLT.setCategories_time(catFormatLTMonth.format(calLT.getTime()));
								headerDateLT.setChart_energy_kwh(0.001);
								headerDateLT.setExpected_power(0.001);
								headerDateLT.setNvm_irradiance(0.001);
								categoriesLT.add(headerDateLT);
							}
							break;
						case 7:
								long forCountLTYear = ChronoUnit.YEARS.between(startMonth, endMonth);
								if(calLT.get(Calendar.MONTH) > calEndLT.get(Calendar.MONTH)) {
									forCountLTYear += 1;
								}
								
								int yearLT = 1;
								
								for(int t = 0; t <= forCountLTYear; t++) {
									calLT.setTime(startDateLT);
									ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
									calLT.add(Calendar.YEAR, t * yearLT);
									headerDateLT.setDownload_time(usFormatLTYear.format(calLT.getTime()));
									headerDateLT.setTime_full(usFormatLTYear.format(calLT.getTime()));
									headerDateLT.setTime_format(usFormatLTYear.format(calLT.getTime()));
									headerDateLT.setCategories_time(catFormatLTYear.format(calLT.getTime()));
									headerDateLT.setChart_energy_kwh(0.001);
									headerDateLT.setExpected_power(0.001);
									headerDateLT.setNvm_irradiance(0.001);
									categoriesLT.add(headerDateLT);
								}
								break;
					}
					
					// get list of time to exclude data from
					List hiddenDataList3 = queryForList("CustomerView.getHiddenDataListBySite", obj);
					obj.setHidden_data_list(hiddenDataList3);
					obj.setGroupMeter(dataListDeviceMeter);
							
					List dataPowerMLT = null;
					if (obj.getEnable_virtual_device() == 1) {
						obj.setDatatablename(obj.getTable_data_virtual());
						dataPowerMLT = queryForList("CustomerView.getDataVirtualDeviceYear", obj);
					} else {
						dataPowerMLT = queryForList("CustomerView.getDataPowerYear", obj);
					}
					
					List<ClientMonthlyDateEntity> dataNewLT = new ArrayList<ClientMonthlyDateEntity> ();
					if(dataPowerMLT.size() > 0 && categoriesLT.size() > 0) {
						for (ClientMonthlyDateEntity item : categoriesLT) {
							boolean flag = false;
							ClientMonthlyDateEntity mapItemObjLT = new ClientMonthlyDateEntity();
							for( int v = 0; v < dataPowerMLT.size(); v++){
								Map<String, Object> itemT = (Map<String, Object>) dataPowerMLT.get(v);
								String categoriesTimeLT = item.getTime_format();
								String powerTimeLT = itemT.get("time_format").toString();
								if (categoriesTimeLT.equals(powerTimeLT)) {
						        	flag = true;
						        	mapItemObjLT.setCategories_time(itemT.get("categories_time").toString());
						        	mapItemObjLT.setTime_format(itemT.get("time_format").toString());
						        	mapItemObjLT.setTime_full(itemT.get("time_full").toString());
						        	mapItemObjLT.setDownload_time(itemT.get("download_time").toString());
						        	mapItemObjLT.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
						        	mapItemObjLT.setExpected_power(Double.parseDouble(itemT.get("expected_energy").toString()) );
						        	mapItemObjLT.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
						        	break;
						        }
							}
							
							if(flag == false) {
								ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
								mapItem.setCategories_time(item.getCategories_time());
								mapItem.setTime_format(item.getTime_format());
								mapItem.setTime_full(item.getTime_full());
								mapItem.setDownload_time(item.getDownload_time());
								mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
								mapItem.setExpected_power(item.getExpected_power());
								mapItem.setNvm_irradiance(item.getNvm_irradiance());
								dataNewLT.add(mapItem);
							} else {
								dataNewLT.add(mapItemObjLT);
							}
						}
					}
					
					Map<String, Object> deviceItemMLT = new HashMap<>();
					if (dataPowerMLT.size() > 0) {
						deviceItemMLT.put("data_energy", dataNewLT);
						deviceItemMLT.put("type", "energy");
						deviceItemMLT.put("devicename", "Energy output");
						deviceItemMLT.put("deviceType", "meter");
						dataEnergy.add(deviceItemMLT);
					}
					
					// Show each meter 
					if (dataListDeviceMeter.size() > 1 && obj.getIs_show_each_meter() == 1) {
						for (int i = 0; i < dataListDeviceMeter.size(); i++) {
							Map<String, Object> device = (Map<String, Object>) dataListDeviceMeter.get(i);
							List hiddenDataListDevice = queryForList("CustomerView.getHiddenDataListByDevice", device);
							device.put("hidden_data_list", hiddenDataListDevice);
							// Show each meter
							Map<String, Object> deviceItemEach5 = new HashMap<>();
							device.put("data_send_time", obj.getData_send_time());
							device.put("table_data_report", obj.getTable_data_report());
							List dataPower = queryForList("CustomerView.getDataPowerThisMonthEachMeter", device);
							
							if (dataPower.size() > 0) {
								deviceItemEach5.put("data_energy", dataPower);
								deviceItemEach5.put("type", "energy");
								deviceItemEach5.put("devicename", device.get("devicename"));
								deviceItemEach5.put("deviceType", "meter");
								dataEnergy.add(deviceItemEach5);
							}
						}
					}
					
					break;
				}
			} else {
				// Get by inverter
				List dataListInverter = queryForList("CustomerView.getListDeviceTypeInverter", obj);
				dataListDeviceIrr = queryForList("CustomerView.getListDeviceTypeIrradiance", obj);
				
				if(dataListInverter.size() > 0) {
					List dataInverter = new ArrayList<>();
					for (int i = 0; i < dataListInverter.size(); i++) {
						Map<String, Object> item = (Map<String, Object>) dataListInverter.get(i);
						item.put("start_date", obj.getStart_date());
						item.put("end_date", obj.getEnd_date());
						dataInverter.add(item);
					}
					
					switch (obj.getFilterBy()) {
						case "today":
								if (obj.getEnable_virtual_device() == 1) {
									obj.setDatatablename(obj.getTable_data_virtual());
									
									// get list of time to exclude data from
									List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
									obj.setHidden_data_list(hiddenDataList);
									
									List dataList = queryForList("CustomerView.getDataVirtualDeviceToday", obj);
									if (dataList.size() > 0) {
										Map<String, Object> powerItem = new HashMap<>();
										Map<String, Object> expectedPowerItem = new HashMap<>();
										Map<String, Object> irradianceItem = new HashMap<>();
										List powerList = new ArrayList<>();
										List expectedPowerList = new ArrayList<>();
										List irradianceList = new ArrayList<>();
										
										for (int i = 0; i < dataList.size(); i++) {
											Map<String, Object> dataListItem = (Map<String, Object>) dataList.get(i);
											Map<String, Object> powerListItem = new HashMap<>();
											Map<String, Object> expectedPowerListItem = new HashMap<>();
											Map<String, Object> irradianceListItem = new HashMap<>();
											
											powerListItem.put("time", dataListItem.get("time"));
											powerListItem.put("download_time", dataListItem.get("download_time"));
											powerListItem.put("time_format", dataListItem.get("time_format"));
											powerListItem.put("time_full", dataListItem.get("time_full"));
											powerListItem.put("categories_time", dataListItem.get("categories_time"));
											powerListItem.put("chart_energy_kwh", dataListItem.get("nvmActivePower"));
											powerList.add(powerListItem);

											expectedPowerListItem.put("time", dataListItem.get("time"));
											expectedPowerListItem.put("download_time", dataListItem.get("download_time"));
											expectedPowerListItem.put("time_format", dataListItem.get("time_format"));
											expectedPowerListItem.put("time_full", dataListItem.get("time_full"));
											expectedPowerListItem.put("categories_time", dataListItem.get("categories_time"));
											expectedPowerListItem.put("expected_power", dataListItem.get("expected_power"));
											expectedPowerList.add(expectedPowerListItem);

											irradianceListItem.put("time", dataListItem.get("time"));
											irradianceListItem.put("download_time", dataListItem.get("download_time"));
											irradianceListItem.put("time_format", dataListItem.get("time_format"));
											irradianceListItem.put("time_full", dataListItem.get("time_full"));
											irradianceListItem.put("categories_time", dataListItem.get("categories_time"));
											irradianceListItem.put("chart_energy_kwh", dataListItem.get("nvm_irradiance"));
											irradianceList.add(irradianceListItem);
										}
										
										powerItem.put("data_energy", powerList);
										powerItem.put("type", "energy");
										powerItem.put("devicename", "Power");
										powerItem.put("deviceType", "inverter");
										dataEnergy.add(powerItem);
										
										if (dataListDeviceIrr.size() > 0) {
											expectedPowerItem.put("data_energy", expectedPowerList);
											expectedPowerItem.put("type", "expected_power");
											expectedPowerItem.put("devicename", "Expected Power");
											dataEnergy.add(expectedPowerItem);
											
											irradianceItem.put("data_energy", irradianceList);
											irradianceItem.put("type", "irradiance");
											irradianceItem.put("devicename", "Irradiance");
											dataEnergy.add(irradianceItem);
										}
									}
								} else {
									Map<String, Object> deviceItem11 = new HashMap<>();
									
									// get list of time to exclude data from
									for (int i = 0; i < dataListInverter.size(); i++) {
										Map<String, Object> device = (Map<String, Object>) dataListInverter.get(i);
										List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", device);
										device.put("hidden_data_list", hiddenDataList);
									}
									
									obj.setGroupMeter(dataListInverter);
									List dataPower11 = queryForList("CustomerView.getDataPowerToday", obj);
									if (dataPower11.size() > 0) {
										deviceItem11.put("data_energy", dataPower11);
										deviceItem11.put("type", "energy");
										deviceItem11.put("devicename", "Power");
										deviceItem11.put("deviceType", "inverter");
										dataEnergy.add(deviceItem11);
									}
									
									// Get Irradiance
									
									if (dataListDeviceIrr.size() > 0) {
										int countExpectedPower = 1;
										for(int i = 0; i < dataListDeviceIrr.size(); i++) {
											Map<String, Object> deviceIrrItem11 = new HashMap<>();
											Map<String, Object> deviceExpectedPowerItem11 = new HashMap<>();
											
											List dataListAIrrDevice = new ArrayList<>();
											
											Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
											// get list of time to exclude data from
											List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", item);
											item.put("hidden_data_list", hiddenDataList);
											dataListAIrrDevice.add(item);
											
											obj.setGroupMeter(dataListAIrrDevice);
											
											List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceToday", obj);
											if(dataIrradianceDevice.size() > 0 ) {
												// Get Expected Power
												if (countExpectedPower == 1) {
													deviceExpectedPowerItem11.put("data_energy", dataIrradianceDevice);
													deviceExpectedPowerItem11.put("type", "expected_power");
													deviceExpectedPowerItem11.put("devicename", "Expected Power");
													dataEnergy.add(deviceExpectedPowerItem11);
												}
												
												// Get Irradiance
												deviceIrrItem11.put("data_energy", dataIrradianceDevice);
												deviceIrrItem11.put("type", "irradiance");
												deviceIrrItem11.put("devicename", dataListDeviceIrr.get(i));
												dataEnergy.add(deviceIrrItem11);
												countExpectedPower++;
											}
										}
									}
								}
								
								break;
						case "custom":{
							// Create list date 
							SimpleDateFormat dateFormatCustom = new SimpleDateFormat("yyyy-MM-dd"); 
							SimpleDateFormat usFormatCustom = new SimpleDateFormat("MM/dd/yyyy");
							SimpleDateFormat usFormatCustomMonth = new SimpleDateFormat("MM/yyyy");

							SimpleDateFormat catFormatCustomDay = new SimpleDateFormat("MM/dd");
							
							SimpleDateFormat catFormatCustom = new SimpleDateFormat("MMM. yyyy");
							SimpleDateFormat catFormatCustomMonth = new SimpleDateFormat("MMM. yyyy");

							SimpleDateFormat usFormatCustomYear = new SimpleDateFormat("yyyy");
							SimpleDateFormat catFormatCustomYear = new SimpleDateFormat("yyyy");
							
							Date startDateCustom = dateFormatCustom.parse(obj.getStart_date() + " AM");
							Calendar calCustom = Calendar.getInstance();
							calCustom.setTime(startDateCustom);
							
							
							Date endDateCustom = dateFormatCustom.parse(obj.getEnd_date() + " PM");
							Calendar calEndCustom = Calendar.getInstance();
							calEndCustom.setTime(endDateCustom);

							List<ClientMonthlyDateEntity> categories = new ArrayList<ClientMonthlyDateEntity> ();
							long forCountYTD = ChronoUnit.DAYS.between(calCustom.getTime().toInstant(), calEndCustom.getTime().toInstant());
							
							switch (obj.getData_send_time()) {
								case 4:
									int day = 1;
														
									for(int t = 0; t <= forCountYTD; t++) {
										calCustom.setTime(startDateCustom);
										ClientMonthlyDateEntity headerDate = new ClientMonthlyDateEntity();
										calCustom.add(Calendar.DATE, t * day);
										headerDate.setDownload_time(usFormatCustom.format(calCustom.getTime()));
										headerDate.setTime_full(usFormatCustom.format(calCustom.getTime()));
										headerDate.setTime_format(usFormatCustom.format(calCustom.getTime()));
										headerDate.setCategories_time(forCountYTD <= 44 ? catFormatCustomDay.format(calCustom.getTime()) : catFormatCustom.format(calCustom.getTime()));
										headerDate.setChart_energy_kwh(0.001);
										headerDate.setNvm_irradiance(0.001);
										headerDate.setExpected_power(0.001);
										categories.add(headerDate);
									}
									break;
								case 5:
									LocalDate dateToSelect = LocalDate.of(calCustom.get(Calendar.YEAR), calCustom.get(Calendar.MONTH) + 1, calCustom.get(Calendar.DAY_OF_MONTH));
									LocalDate lastVisible = LocalDate.of(calEndCustom.get(Calendar.YEAR), calEndCustom.get(Calendar.MONTH) + 1, calEndCustom.get(Calendar.DAY_OF_MONTH));
									long forCountYTD7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
									int YTD7Day = 1;
									
									for(int t = 0; t <= forCountYTD7Day; t++) {
										calCustom.setTime(startDateCustom);
										ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
										calCustom.add(Calendar.WEEK_OF_YEAR, t * YTD7Day);
										headerDateLT.setDownload_time(usFormatCustom.format(calCustom.getTime()));
										headerDateLT.setTime_format(String.valueOf(t));
										headerDateLT.setTime_full(usFormatCustom.format(calCustom.getTime()));
										headerDateLT.setCategories_time(catFormatCustom.format(calCustom.getTime()));
										headerDateLT.setChart_energy_kwh(0.001);
										headerDateLT.setNvm_irradiance(0.001);
										headerDateLT.setExpected_power(0.001);
										categories.add(headerDateLT);
									}
									break;
								case 6: 
									YearMonth startMonth = YearMonth.of( calCustom.get(Calendar.YEAR) , calCustom.get(Calendar.MONTH) + 1 );
									YearMonth endMonth = YearMonth.of(calEndCustom.get(Calendar.YEAR) , calEndCustom.get(Calendar.MONTH) + 1);
									long forCountYTDMonth = ChronoUnit.MONTHS.between(startMonth, endMonth);
									int monthYTD = 1;
									
									for(int t = 0; t <= forCountYTDMonth; t++) {
										calCustom.setTime(startDateCustom);
										ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
										calCustom.add(Calendar.MONTH, t * monthYTD);
										headerDateLT.setDownload_time(usFormatCustomMonth.format(calCustom.getTime()));
										headerDateLT.setTime_full(usFormatCustomMonth.format(calCustom.getTime()));
										headerDateLT.setTime_format(usFormatCustomMonth.format(calCustom.getTime()));
										headerDateLT.setCategories_time(catFormatCustomMonth.format(calCustom.getTime()));
										headerDateLT.setChart_energy_kwh(0.001);
										headerDateLT.setNvm_irradiance(0.001);
										headerDateLT.setExpected_power(0.001);
										categories.add(headerDateLT);
									}
									break;
								case 7:
									YearMonth startMonthCustom = YearMonth.of( calCustom.get(Calendar.YEAR) , calCustom.get(Calendar.MONTH) + 1 );
									YearMonth endMonthCustom = YearMonth.of(calEndCustom.get(Calendar.YEAR) , calEndCustom.get(Calendar.MONTH) + 1);
									long forCountLTYear = ChronoUnit.YEARS.between(startMonthCustom, endMonthCustom);
									if(calCustom.get(Calendar.MONTH) > calEndCustom.get(Calendar.MONTH)) {
											forCountLTYear += 1;
									}
									int yearLT = 1;
									
									for(int t = 0; t <= forCountLTYear; t++) {
										calCustom.setTime(startDateCustom);
										ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
										calCustom.add(Calendar.YEAR, t * yearLT);
										headerDateLT.setDownload_time(usFormatCustomYear.format(calCustom.getTime()));
										headerDateLT.setTime_full(usFormatCustomYear.format(calCustom.getTime()));
										headerDateLT.setTime_format(usFormatCustomYear.format(calCustom.getTime()));
										headerDateLT.setCategories_time(catFormatCustomYear.format(calCustom.getTime()));
										headerDateLT.setChart_energy_kwh(0.001);
										headerDateLT.setNvm_irradiance(0.001);
										headerDateLT.setExpected_power(0.001);
										categories.add(headerDateLT);
									}
									break;
							}
							
							// get list of time to exclude data from
							List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
							obj.setHidden_data_list(hiddenDataList);
							obj.setGroupMeter(dataListDeviceMeter);
							
							List dataPowerM = null;
							if (obj.getEnable_virtual_device() == 1) {
								 obj.setDatatablename(obj.getTable_data_virtual());
								 dataPowerM = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataVirtualDeviceCustomAtMost5Days", obj) : queryForList("CustomerView.getDataVirtualDeviceCustom", obj);
							} else {
								 dataPowerM = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataPowerCustomAtMost5Days", obj) : queryForList("CustomerView.getDataPowerCustom", obj);
							}
							
							List<ClientMonthlyDateEntity> dataNew = new ArrayList<ClientMonthlyDateEntity> ();
							if(dataPowerM.size() > 0 && categories.size() > 0) {
								for (ClientMonthlyDateEntity item : categories) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObj = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPowerM.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPowerM.get(v);
										String categoriesTime = item.getTime_format();
										String powerTime = itemT.get("time_format").toString();
										if (categoriesTime.equals(powerTime)) {
								        	flag = true;
								        	mapItemObj.setCategories_time(itemT.get("categories_time").toString());
								        	mapItemObj.setTime_format(itemT.get("time_format").toString());
								        	mapItemObj.setTime_full(itemT.get("time_full").toString());
								        	mapItemObj.setDownload_time(itemT.get("download_time").toString());
								        	mapItemObj.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
								        	mapItemObj.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
								        	mapItemObj.setExpected_power(Double.parseDouble(itemT.get("expected_power").toString()) );
								        	break;
								        }
									}
									
									if(flag == false) {
										ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
										mapItem.setCategories_time(item.getCategories_time());
										mapItem.setTime_format(item.getTime_format());
										mapItem.setTime_full(item.getTime_full());
										mapItem.setDownload_time(item.getDownload_time());
										mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										mapItem.setExpected_power(item.getExpected_power());
										dataNew.add(mapItem);
									} else {
										dataNew.add(mapItemObj);
									}
								}
							}
							
							Map<String, Object> deviceItemM = new HashMap<>();
							if (dataPowerM.size() > 0) {
								deviceItemM.put("data_energy", dataNew);
								deviceItemM.put("type", "energy");
								deviceItemM.put("devicename", "Energy output");
								deviceItemM.put("deviceType", "inverter");
								dataEnergy.add(deviceItemM);
							}
						
							break;
						}
						case "3_day":
								if (obj.getEnable_virtual_device() == 1) {
									obj.setDatatablename(obj.getTable_data_virtual());
									
									// get list of time to exclude data from
									List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
									obj.setHidden_data_list(hiddenDataList);
									
									List dataList = queryForList("CustomerView.getDataVirtualDevice3Day", obj);
									if (dataList.size() > 0) {
										Map<String, Object> powerItem = new HashMap<>();
										Map<String, Object> expectedPowerItem = new HashMap<>();
										Map<String, Object> irradianceItem = new HashMap<>();
										List powerList = new ArrayList<>();
										List expectedPowerList = new ArrayList<>();
										List irradianceList = new ArrayList<>();
										
										for (int i = 0; i < dataList.size(); i++) {
											Map<String, Object> dataListItem = (Map<String, Object>) dataList.get(i);
											Map<String, Object> powerListItem = new HashMap<>();
											Map<String, Object> expectedPowerListItem = new HashMap<>();
											Map<String, Object> irradianceListItem = new HashMap<>();
											
											powerListItem.put("time", dataListItem.get("time"));
											powerListItem.put("download_time", dataListItem.get("download_time"));
											powerListItem.put("time_format", dataListItem.get("time_format"));
											powerListItem.put("time_full", dataListItem.get("time_full"));
											powerListItem.put("categories_time", dataListItem.get("categories_time"));
											powerListItem.put("chart_energy_kwh", dataListItem.get("nvmActivePower"));
											powerList.add(powerListItem);

											expectedPowerListItem.put("time", dataListItem.get("time"));
											expectedPowerListItem.put("download_time", dataListItem.get("download_time"));
											expectedPowerListItem.put("time_format", dataListItem.get("time_format"));
											expectedPowerListItem.put("time_full", dataListItem.get("time_full"));
											expectedPowerListItem.put("categories_time", dataListItem.get("categories_time"));
											expectedPowerListItem.put("expected_power", dataListItem.get("expected_power"));
											expectedPowerList.add(expectedPowerListItem);

											irradianceListItem.put("time", dataListItem.get("time"));
											irradianceListItem.put("download_time", dataListItem.get("download_time"));
											irradianceListItem.put("time_format", dataListItem.get("time_format"));
											irradianceListItem.put("time_full", dataListItem.get("time_full"));
											irradianceListItem.put("categories_time", dataListItem.get("categories_time"));
											irradianceListItem.put("chart_energy_kwh", dataListItem.get("nvm_irradiance"));
											irradianceList.add(irradianceListItem);
										}
										
										powerItem.put("data_energy", powerList);
										powerItem.put("type", "energy");
										powerItem.put("devicename", "Power");
										powerItem.put("deviceType", "inverter");
										dataEnergy.add(powerItem);
										
										if (dataListDeviceIrr.size() > 0) {
											expectedPowerItem.put("data_energy", expectedPowerList);
											expectedPowerItem.put("type", "expected_power");
											expectedPowerItem.put("devicename", "Expected Power");
											dataEnergy.add(expectedPowerItem);
											
											irradianceItem.put("data_energy", irradianceList);
											irradianceItem.put("type", "irradiance");
											irradianceItem.put("devicename", "Irradiance");
											dataEnergy.add(irradianceItem);
										}
									}
								} else {
									Map<String, Object> deviceItem55 = new HashMap<>();
									
									// get list of time to exclude data from
									for (int i = 0; i < dataListInverter.size(); i++) {
										Map<String, Object> device = (Map<String, Object>) dataListInverter.get(i);
										List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", device);
										device.put("hidden_data_list", hiddenDataList);
									}
									
									obj.setGroupMeter(dataListInverter);
									List dataPower55 = queryForList("CustomerView.getDataPower3Day", obj);
									if (dataPower55.size() > 0) {
										deviceItem55.put("data_energy", dataPower55);
										deviceItem55.put("type", "energy");
										deviceItem55.put("devicename", "Power");
										deviceItem55.put("deviceType", "inverter");
										dataEnergy.add(deviceItem55);
									}
									
									// Get Irradiance
									
									if (dataListDeviceIrr.size() > 0) {
										int countExpectedPower = 1;
										for(int i = 0; i < dataListDeviceIrr.size(); i++) {
											Map<String, Object> deviceIrrItem55 = new HashMap<>();
											Map<String, Object> deviceExpectedPowerItem55 = new HashMap<>();
											
											List dataListAIrrDevice = new ArrayList<>();
											
											Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
											// get list of time to exclude data from
											List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", item);
											item.put("hidden_data_list", hiddenDataList);
											dataListAIrrDevice.add(item);
											
											obj.setGroupMeter(dataListAIrrDevice);
											
											List dataIrradianceDevice = queryForList("CustomerView.getDataIrradiance3Day", obj);
											if(dataIrradianceDevice.size() > 0 ) {
												// Get Expected Power
												if (countExpectedPower == 1) {
													deviceExpectedPowerItem55.put("data_energy", dataIrradianceDevice);
													deviceExpectedPowerItem55.put("type", "expected_power");
													deviceExpectedPowerItem55.put("devicename", "Expected Power");
													dataEnergy.add(deviceExpectedPowerItem55);
												}
												
												// Get Irradiance
												deviceIrrItem55.put("data_energy", dataIrradianceDevice);
												deviceIrrItem55.put("type", "irradiance");
												deviceIrrItem55.put("devicename", dataListDeviceIrr.get(i));
												dataEnergy.add(deviceIrrItem55);
												countExpectedPower++;
											}
										}
									}
								}
								
								break;
						case "this_week":
						case "last_week":
							if (obj.getEnable_virtual_device() == 1) {
								obj.setDatatablename(obj.getTable_data_virtual());
								
								// get list of time to exclude data from
								List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
								obj.setHidden_data_list(hiddenDataList);
								
								List dataList = queryForList("CustomerView.getDataVirtualDeviceThisWeek", obj);
								if (dataList.size() > 0) {
									Map<String, Object> energyItem = new HashMap<>();
									Map<String, Object> irradianceItem = new HashMap<>();
									Map<String, Object> expectedEnergyItem = new HashMap<>();
									List energyList = new ArrayList<>();
									List irradianceList = new ArrayList<>();
									List expectedEnergyList = new ArrayList<>();
									
									for (int i = 0; i < dataList.size(); i++) {
										Map<String, Object> dataListItem = (Map<String, Object>) dataList.get(i);
										Map<String, Object> energyListItem = new HashMap<>();
										Map<String, Object> expectedEnergyListItem = new HashMap<>();
										Map<String, Object> irradianceListItem = new HashMap<>();
										
										energyListItem.put("time", dataListItem.get("time"));
										energyListItem.put("download_time", dataListItem.get("download_time"));
										energyListItem.put("time_format", dataListItem.get("time_format"));
										energyListItem.put("time_full", dataListItem.get("time_full"));
										energyListItem.put("categories_time", dataListItem.get("categories_time"));
										energyListItem.put("chart_energy_kwh", dataListItem.get("nvmActiveEnergy"));
										energyList.add(energyListItem);

										expectedEnergyListItem.put("time", dataListItem.get("time"));
										expectedEnergyListItem.put("download_time", dataListItem.get("download_time"));
										expectedEnergyListItem.put("time_format", dataListItem.get("time_format"));
										expectedEnergyListItem.put("time_full", dataListItem.get("time_full"));
										expectedEnergyListItem.put("categories_time", dataListItem.get("categories_time"));
										expectedEnergyListItem.put("expected_energy", dataListItem.get("expected_energy"));
										expectedEnergyList.add(expectedEnergyListItem);

										irradianceListItem.put("time", dataListItem.get("time"));
										irradianceListItem.put("download_time", dataListItem.get("download_time"));
										irradianceListItem.put("time_format", dataListItem.get("time_format"));
										irradianceListItem.put("time_full", dataListItem.get("time_full"));
										irradianceListItem.put("categories_time", dataListItem.get("categories_time"));
										irradianceListItem.put("chart_energy_kwh", dataListItem.get("nvm_irradiance"));
										irradianceList.add(irradianceListItem);
									}
									
									energyItem.put("data_energy", energyList);
									energyItem.put("type", "energy");
									energyItem.put("devicename", "Energy output");
									energyItem.put("deviceType", "inverter");
									dataEnergy.add(energyItem);
									
									if (dataListDeviceIrr.size() > 0) {
										expectedEnergyItem.put("data_energy", expectedEnergyList);
										expectedEnergyItem.put("type", "expected_energy");
										expectedEnergyItem.put("devicename", "Expected Energy");
										dataEnergy.add(expectedEnergyItem);
										
										irradianceItem.put("data_energy", irradianceList);
										irradianceItem.put("type", "irradiance");
										irradianceItem.put("devicename", "Irradiance");
										dataEnergy.add(irradianceItem);
									}
								}
							} else {
									Map<String, Object> deviceItem5 = new HashMap<>();

									// get list of time to exclude data from
									for (int i = 0; i < dataListInverter.size(); i++) {
										Map<String, Object> device = (Map<String, Object>) dataListInverter.get(i);
										List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", device);
										device.put("hidden_data_list", hiddenDataList);
									}
									
									obj.setGroupMeter(dataListInverter);
									List dataPower5 = queryForList("CustomerView.getDataEnergyThisWeek", obj);
									if (dataPower5.size() > 0) {
										deviceItem5.put("data_energy", dataPower5);
										deviceItem5.put("type", "energy");
										deviceItem5.put("devicename", "Energy output");
										deviceItem5.put("deviceType", "inverter");
										dataEnergy.add(deviceItem5);
									}
									
									// Get Irradiance
									
									if (dataListDeviceIrr.size() > 0) {
										int countExpectedPower = 1;
										for(int i = 0; i < dataListDeviceIrr.size(); i++) {
											Map<String, Object> deviceIrrItem5 = new HashMap<>();
											Map<String, Object> deviceExpectedEnergyItem5 = new HashMap<>();
											
											List dataListAIrrDevice = new ArrayList<>();
											
											Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
											// get list of time to exclude data from
											List hiddenDataList = queryForList("CustomerView.getHiddenDataListByDevice", item);
											item.put("hidden_data_list", hiddenDataList);
											dataListAIrrDevice.add(item);
											
											obj.setGroupMeter(dataListAIrrDevice);
											
											List dataIrradianceDevice = queryForList("CustomerView.getDataIrradiance3Day", obj);

											if(dataIrradianceDevice.size() > 0 ) {
												// Get Expected Energy
												if (countExpectedPower == 1) {
													deviceExpectedEnergyItem5.put("data_energy", dataIrradianceDevice);
													deviceExpectedEnergyItem5.put("type", "expected_energy");
													deviceExpectedEnergyItem5.put("devicename", "Expected Energy");
													dataEnergy.add(deviceExpectedEnergyItem5);
												}
												
												// Get Irradiance
												deviceIrrItem5.put("data_energy", dataIrradianceDevice);
												deviceIrrItem5.put("type", "irradiance");
												deviceIrrItem5.put("devicename", dataListDeviceIrr.get(i));
												dataEnergy.add(deviceIrrItem5);
												countExpectedPower++;
											}
										}
									}
								}							
							break;
						case "last_month":
						case "this_month":
							
							// Create list date 
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
							SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy");
							SimpleDateFormat catFormat = new SimpleDateFormat("MM/dd");
							SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
							Date startDate = dateFormat.parse(obj.getStart_date() + " AM");
							Calendar cal = Calendar.getInstance();
							cal.setTime(startDate);
							
							cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
							
							List<ClientMonthlyDateEntity> categories = new ArrayList<ClientMonthlyDateEntity> ();
							int day = 1;
							int forCount = Integer.parseInt(dayFormat.format(cal.getTime()).toString());
							
							for(int t = 0; t < forCount; t++) {
								cal.setTime(startDate);
								ClientMonthlyDateEntity headerDate = new ClientMonthlyDateEntity();
								cal.add(Calendar.DATE, t * day);
								headerDate.setDownload_time(usFormat.format(cal.getTime()));
								headerDate.setTime_full(usFormat.format(cal.getTime()));
								headerDate.setTime_format(usFormat.format(cal.getTime()));
								headerDate.setCategories_time(catFormat.format(cal.getTime()));
								headerDate.setChart_energy_kwh(0.001);
								headerDate.setExpected_power(0.001);
								headerDate.setNvm_irradiance(0.001);
								categories.add(headerDate);
							}
							
							// get list of time to exclude data from
							List hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
							obj.setHidden_data_list(hiddenDataList);
							obj.setGroupMeter(dataListDeviceMeter);

							List dataPowerM = null;
							if (obj.getEnable_virtual_device() == 1) {
								 obj.setDatatablename(obj.getTable_data_virtual());
								 dataPowerM = queryForList("CustomerView.getDataVirtualDeviceThisMonth", obj);
							} else {
								 dataPowerM = queryForList("CustomerView.getDataPowerThisMonth", obj);
							}
							
							List<ClientMonthlyDateEntity> dataNew = new ArrayList<ClientMonthlyDateEntity> ();
							if(dataPowerM.size() > 0 && categories.size() > 0) {
								for (ClientMonthlyDateEntity item : categories) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObj = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPowerM.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPowerM.get(v);
										String categoriesTime = item.getTime_format();
										String powerTime = itemT.get("time_format").toString();
										if (categoriesTime.equals(powerTime)) {
								        	flag = true;
								        	mapItemObj.setCategories_time(itemT.get("categories_time").toString());
								        	mapItemObj.setTime_format(itemT.get("time_format").toString());
								        	mapItemObj.setTime_full(itemT.get("time_full").toString());
								        	mapItemObj.setDownload_time(itemT.get("download_time").toString());
								        	mapItemObj.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
								        	mapItemObj.setExpected_power(Double.parseDouble(itemT.get("expected_energy").toString()) );
								        	mapItemObj.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
								        	break;
								        }
									}
									
									if(flag == false) {
										ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
										mapItem.setCategories_time(item.getCategories_time());
										mapItem.setTime_format(item.getTime_format());
										mapItem.setTime_full(item.getTime_full());
										mapItem.setDownload_time(item.getDownload_time());
										mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
										mapItem.setExpected_power(item.getExpected_power());
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNew.add(mapItem);
									} else {
										dataNew.add(mapItemObj);
									}
								}
							}
							
							
							Map<String, Object> deviceItemM = new HashMap<>();
							if (dataPowerM.size() > 0) {
								deviceItemM.put("data_energy", dataNew);
								deviceItemM.put("type", "energy");
								deviceItemM.put("devicename", "Energy output");
								deviceItemM.put("deviceType", "inverter");
								dataEnergy.add(deviceItemM);
							}
							
							break;
							
						case "year":
							// Create list date 
							SimpleDateFormat dateFormatYTD = new SimpleDateFormat("yyyy-MM-dd"); 
							SimpleDateFormat usFormatYTD = new SimpleDateFormat("MM/dd/yyyy");
							SimpleDateFormat usFormatYTDMonth = new SimpleDateFormat("MM/yyyy");
							
							SimpleDateFormat catFormatYTD = new SimpleDateFormat("MMM. yyyy");
							SimpleDateFormat catFormatYTDMonth = new SimpleDateFormat("MMM. yyyy");
							
							Date startDateYTD = dateFormatYTD.parse(obj.getStart_date() + " AM");
							Calendar calYTD = Calendar.getInstance();
							calYTD.setTime(startDateYTD);
							
							
							Date endDateYTD = dateFormatYTD.parse(obj.getEnd_date() + " PM");
							Calendar calEndYTD = Calendar.getInstance();
							calEndYTD.setTime(endDateYTD);
							
							List<ClientMonthlyDateEntity> categoriesYTD = new ArrayList<ClientMonthlyDateEntity> ();

							switch (obj.getData_send_time()) {
								case 4:
									long forCountYTD = ChronoUnit.DAYS.between(calYTD.getTime().toInstant(), calEndYTD.getTime().toInstant());
									int dayYTD = 1;
									
									for(int t = 0; t <= forCountYTD; t++) {
										calYTD.setTime(startDateYTD);
										
										ClientMonthlyDateEntity headerDateYTD = new ClientMonthlyDateEntity();
										calYTD.add(Calendar.DATE, t * dayYTD);
										headerDateYTD.setDownload_time(usFormatYTD.format(calYTD.getTime()));
										headerDateYTD.setTime_full(usFormatYTD.format(calYTD.getTime()));
										headerDateYTD.setTime_format(usFormatYTD.format(calYTD.getTime()));
										headerDateYTD.setCategories_time(catFormatYTD.format(calYTD.getTime()));
										headerDateYTD.setChart_energy_kwh(0.001);
										headerDateYTD.setExpected_power(0.001);
										headerDateYTD.setNvm_irradiance(0.001);
										categoriesYTD.add(headerDateYTD);
									}
									break;
								case 5:
									LocalDate dateToSelect = LocalDate.of(calYTD.get(Calendar.YEAR), calYTD.get(Calendar.MONTH) + 1, calYTD.get(Calendar.DAY_OF_MONTH));
									LocalDate lastVisible = LocalDate.of(calEndYTD.get(Calendar.YEAR), calEndYTD.get(Calendar.MONTH) + 1, calEndYTD.get(Calendar.DAY_OF_MONTH));
									long forCountYTD7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
									int YTD7Day = 1;
									
									for(int t = 0; t <= forCountYTD7Day; t++) {
										calYTD.setTime(startDateYTD);
										ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
										calYTD.add(Calendar.WEEK_OF_YEAR, t * YTD7Day);
										headerDateLT.setDownload_time(usFormatYTD.format(calYTD.getTime()));
										headerDateLT.setTime_format(String.valueOf(t));
										headerDateLT.setTime_full(usFormatYTD.format(calYTD.getTime()));
										headerDateLT.setCategories_time(catFormatYTD.format(calYTD.getTime()));
										headerDateLT.setChart_energy_kwh(0.001);
										headerDateLT.setExpected_power(0.001);
										headerDateLT.setNvm_irradiance(0.001);
										categoriesYTD.add(headerDateLT);
									}
									break;
								case 6:
									YearMonth startMonth = YearMonth.of( calYTD.get(Calendar.YEAR) , calYTD.get(Calendar.MONTH) + 1 );
							        YearMonth endMonth = YearMonth.of(calEndYTD.get(Calendar.YEAR) , calEndYTD.get(Calendar.MONTH) + 1);
							        long forCountYTDMonth = ChronoUnit.MONTHS.between(startMonth, endMonth);
									int monthYTD = 1;
									
									for(int t = 0; t <= forCountYTDMonth; t++) {
										calYTD.setTime(startDateYTD);
										ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
										calYTD.add(Calendar.MONTH, t * monthYTD);
										headerDateLT.setDownload_time(usFormatYTDMonth.format(calYTD.getTime()));
										headerDateLT.setTime_full(usFormatYTDMonth.format(calYTD.getTime()));
										headerDateLT.setTime_format(usFormatYTDMonth.format(calYTD.getTime()));
										headerDateLT.setCategories_time(catFormatYTDMonth.format(calYTD.getTime()));
										headerDateLT.setChart_energy_kwh(0.001);
										headerDateLT.setExpected_power(0.001);
										headerDateLT.setNvm_irradiance(0.001);
										categoriesYTD.add(headerDateLT);
									}
									break;
							}
									
							// get list of time to exclude data from
							List hiddenDataList1 = queryForList("CustomerView.getHiddenDataListBySite", obj);
							obj.setHidden_data_list(hiddenDataList1);
							obj.setGroupMeter(dataListDeviceMeter);
							
							List dataPowerMYTD = null;
							if (obj.getEnable_virtual_device() == 1) {
								obj.setDatatablename(obj.getTable_data_virtual());
								dataPowerMYTD = queryForList("CustomerView.getDataVirtualDeviceYear", obj);
							} else {
								dataPowerMYTD = queryForList("CustomerView.getDataPowerYear", obj);
							}
							
							List<ClientMonthlyDateEntity> dataNewYTD = new ArrayList<ClientMonthlyDateEntity> ();
							if(dataPowerMYTD.size() > 0 && categoriesYTD.size() > 0) {
								for (ClientMonthlyDateEntity item : categoriesYTD) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObjYTD = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPowerMYTD.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPowerMYTD.get(v);
										String categoriesTimeYTD = item.getTime_format();
										String powerTimeYTD = itemT.get("time_format").toString();
										if (categoriesTimeYTD.equals(powerTimeYTD)) {
													flag = true;
													mapItemObjYTD.setCategories_time(itemT.get("categories_time").toString());
													mapItemObjYTD.setTime_format(itemT.get("time_format").toString());
													mapItemObjYTD.setTime_full(itemT.get("time_full").toString());
													mapItemObjYTD.setDownload_time(itemT.get("download_time").toString());
													mapItemObjYTD.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
										        	mapItemObjYTD.setExpected_power(Double.parseDouble(itemT.get("expected_energy").toString()) );
													mapItemObjYTD.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
													break;
												}
									}
									
									if(flag == false) {
										ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
										mapItem.setCategories_time(item.getCategories_time());
										mapItem.setTime_format(item.getTime_format());
										mapItem.setTime_full(item.getTime_full());
										mapItem.setDownload_time(item.getDownload_time());
										mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
										mapItem.setExpected_power(item.getExpected_power());
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNewYTD.add(mapItem);
									} else {
										dataNewYTD.add(mapItemObjYTD);
									}
								}
							}
							
							
							Map<String, Object> deviceItemMYTD = new HashMap<>();
							if (dataPowerMYTD.size() > 0) {
								deviceItemMYTD.put("data_energy", dataNewYTD);
								deviceItemMYTD.put("type", "energy");
								deviceItemMYTD.put("devicename", "Energy output");
								deviceItemMYTD.put("deviceType", "inverter");
								dataEnergy.add(deviceItemMYTD);
							}							
							
							break;
						case "12_month":				
							// Create list date 
							SimpleDateFormat dateFormat12 = new SimpleDateFormat("yyyy-MM-dd"); 
							SimpleDateFormat usFormat12 = new SimpleDateFormat("MM/yyyy");
							SimpleDateFormat catFormat12 = new SimpleDateFormat("MM/yyyy");
							
							SimpleDateFormat usFormat12Month7Day = new SimpleDateFormat("MM/dd/yyyy");
							SimpleDateFormat catFormat12Month7Day = new SimpleDateFormat("MMM. yyyy");
							
							SimpleDateFormat usFormat12MonthDay = new SimpleDateFormat("MM/dd/yyyy");
							SimpleDateFormat catFormat12MonthDay = new SimpleDateFormat("MMM. yyyy");
							
							Date startDate12 = dateFormat12.parse(obj.getStart_date() + " AM");
							Calendar cal12 = Calendar.getInstance();
							cal12.setTime(startDate12);					
							
							Date endDate12 = dateFormat12.parse(obj.getEnd_date() + " PM");
							Calendar calEnd12 = Calendar.getInstance();
							calEnd12.setTime(endDate12);

							List<ClientMonthlyDateEntity> categories12MonthDay = new ArrayList<ClientMonthlyDateEntity> ();
							
							switch (obj.getData_send_time()) {
								case 4:
									long forCountYTD = ChronoUnit.DAYS.between(cal12.getTime().toInstant(), calEnd12.getTime().toInstant());
									int day12Month = 1;
									
									for(int t = 0; t <= forCountYTD; t++) {
										cal12.setTime(startDate12);
										
										ClientMonthlyDateEntity headerDate12MonthDay = new ClientMonthlyDateEntity();
										cal12.add(Calendar.DATE, t * day12Month);
										headerDate12MonthDay.setDownload_time(usFormat12MonthDay.format(cal12.getTime()));
										headerDate12MonthDay.setTime_full(usFormat12MonthDay.format(cal12.getTime()));
										headerDate12MonthDay.setTime_format(usFormat12MonthDay.format(cal12.getTime()));
										headerDate12MonthDay.setCategories_time(catFormat12MonthDay.format(cal12.getTime()));
										headerDate12MonthDay.setChart_energy_kwh(0.001);
										headerDate12MonthDay.setExpected_power(0.001);
										headerDate12MonthDay.setNvm_irradiance(0.001);
										categories12MonthDay.add(headerDate12MonthDay);
									}
									break;
								case 5:
									LocalDate dateToSelect = LocalDate.of(cal12.get(Calendar.YEAR), cal12.get(Calendar.MONTH) + 1, cal12.get(Calendar.DAY_OF_MONTH));
									LocalDate lastVisible = LocalDate.of(calEnd12.get(Calendar.YEAR), calEnd12.get(Calendar.MONTH) + 1, calEnd12.get(Calendar.DAY_OF_MONTH));
									long forCount12Month7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
									int week12 = 1;
									
									for(int t = 0; t <= forCount12Month7Day; t++) {
										cal12.setTime(startDate12);
										ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
										cal12.add(Calendar.WEEK_OF_YEAR, t * week12);
										headerDateLT.setDownload_time(usFormat12Month7Day.format(cal12.getTime()));
										headerDateLT.setTime_format(String.valueOf(t));
										headerDateLT.setTime_full(usFormat12Month7Day.format(cal12.getTime()));
										headerDateLT.setCategories_time(catFormat12Month7Day.format(cal12.getTime()));
										headerDateLT.setChart_energy_kwh(0.001);
										headerDateLT.setExpected_power(0.001);
										headerDateLT.setNvm_irradiance(0.001);
										categories12MonthDay.add(headerDateLT);
									}
									break;
								case 6:
									cal12.set(Calendar.DAY_OF_MONTH, cal12.getActualMaximum(Calendar.DAY_OF_MONTH));
									YearMonth startMonth12 = YearMonth.of( cal12.get(Calendar.YEAR) , cal12.get(Calendar.MONTH) + 1 );
									YearMonth endMonth12 = YearMonth.of(calEnd12.get(Calendar.YEAR) , calEnd12.get(Calendar.MONTH) + 1);
									long forCount12Month = ChronoUnit.MONTHS.between(startMonth12, endMonth12);
									int day12 = 1;
									
									for(int t = 0; t <= forCount12Month; t++) {
										cal12.setTime(startDate12);
										ClientMonthlyDateEntity headerDate12 = new ClientMonthlyDateEntity();
										cal12.add(Calendar.MONTH, t * day12);
										headerDate12.setDownload_time(usFormat12.format(cal12.getTime()));
										headerDate12.setTime_full(usFormat12.format(cal12.getTime()));
										headerDate12.setTime_format(usFormat12.format(cal12.getTime()));
										headerDate12.setCategories_time(catFormat12.format(cal12.getTime()));
										headerDate12.setChart_energy_kwh(0.001);
										headerDate12.setExpected_power(0.001);
										headerDate12.setNvm_irradiance(0.001);
										categories12MonthDay.add(headerDate12);
									}
									break;
							}
							
							// get list of time to exclude data from
							List hiddenDataList2 = queryForList("CustomerView.getHiddenDataListBySite", obj);
							obj.setHidden_data_list(hiddenDataList2);
							obj.setGroupMeter(dataListDeviceMeter);
									
							List dataPowerM12MonthDay = null;
							if (obj.getEnable_virtual_device() == 1) {
								obj.setDatatablename(obj.getTable_data_virtual());
								dataPowerM12MonthDay = queryForList("CustomerView.getDataVirtualDeviceYear", obj);
							} else {
								dataPowerM12MonthDay = queryForList("CustomerView.getDataPowerYear", obj);
							}
							
							List<ClientMonthlyDateEntity> dataNew12MonthDay = new ArrayList<ClientMonthlyDateEntity> ();
							if(dataPowerM12MonthDay.size() > 0 && categories12MonthDay.size() > 0) {
								for (ClientMonthlyDateEntity item : categories12MonthDay) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObj12MonthDay = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPowerM12MonthDay.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPowerM12MonthDay.get(v);
										String categoriesTime12MonthDay = item.getTime_format();
										String powerTime12MonthDay = itemT.get("time_format").toString();
										if (categoriesTime12MonthDay.equals(powerTime12MonthDay)) {
													flag = true;
													mapItemObj12MonthDay.setCategories_time(itemT.get("categories_time").toString());
													mapItemObj12MonthDay.setTime_format(itemT.get("time_format").toString());
													mapItemObj12MonthDay.setTime_full(itemT.get("time_full").toString());
													mapItemObj12MonthDay.setDownload_time(itemT.get("download_time").toString());
													mapItemObj12MonthDay.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
										        	mapItemObj12MonthDay.setExpected_power(Double.parseDouble(itemT.get("expected_energy").toString()) );
													mapItemObj12MonthDay.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
													break;
												}
									}
									
									if(flag == false) {
										ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
										mapItem.setCategories_time(item.getCategories_time());
										mapItem.setTime_format(item.getTime_format());
										mapItem.setTime_full(item.getTime_full());
										mapItem.setDownload_time(item.getDownload_time());
										mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
										mapItem.setExpected_power(item.getExpected_power());
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNew12MonthDay.add(mapItem);
									} else {
										dataNew12MonthDay.add(mapItemObj12MonthDay);
									}
								}
							}
							
							Map<String, Object> deviceItemM12MonthDay = new HashMap<>();
							if (dataPowerM12MonthDay.size() > 0) {
								deviceItemM12MonthDay.put("data_energy", dataNew12MonthDay);
								deviceItemM12MonthDay.put("type", "energy");
								deviceItemM12MonthDay.put("devicename", "Energy output");
								deviceItemM12MonthDay.put("deviceType", "inverter");
								dataEnergy.add(deviceItemM12MonthDay);
							}	
							break;
						case "lifetime":
							// Create list date 
							SimpleDateFormat dateFormatLT = new SimpleDateFormat("yyyy-MM-dd"); 
							SimpleDateFormat usFormatLTMonth = new SimpleDateFormat("MM/yyyy");
							SimpleDateFormat usFormatLTYear = new SimpleDateFormat("yyyy");
							SimpleDateFormat catFormatLTMonth = new SimpleDateFormat("MM/yyyy");
							SimpleDateFormat catFormatLTYear = new SimpleDateFormat("yyyy");
							Date startDateLT = dateFormatLT.parse(obj.getStart_date() + " AM");
							Calendar calLT = Calendar.getInstance();
							calLT.setTime(startDateLT);
							
							Date endDateLT = dateFormatLT.parse(obj.getEnd_date() + " PM");
							Calendar calEndLT = Calendar.getInstance();
							calEndLT.setTime(endDateLT);

							YearMonth startMonth = YearMonth.of( calLT.get(Calendar.YEAR) , calLT.get(Calendar.MONTH) + 1 );
					        YearMonth endMonth = YearMonth.of(calEndLT.get(Calendar.YEAR) , calEndLT.get(Calendar.MONTH) + 1);
					        
					        List<ClientMonthlyDateEntity> categoriesLT = new ArrayList<ClientMonthlyDateEntity> ();
					        
							switch (obj.getData_send_time()) {
								case 6:						
							        long forCountLT = ChronoUnit.MONTHS.between(startMonth, endMonth);
									int dayLT = 1;
									
									for(int t = 0; t <= forCountLT; t++) {
										calLT.setTime(startDateLT);
										ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
										calLT.add(Calendar.MONTH, t * dayLT);
										headerDateLT.setDownload_time(usFormatLTMonth.format(calLT.getTime()));
										headerDateLT.setTime_full(usFormatLTMonth.format(calLT.getTime()));
										headerDateLT.setTime_format(usFormatLTMonth.format(calLT.getTime()));
										headerDateLT.setCategories_time(catFormatLTMonth.format(calLT.getTime()));
										headerDateLT.setChart_energy_kwh(0.001);
										headerDateLT.setExpected_power(0.001);
										headerDateLT.setNvm_irradiance(0.001);
										categoriesLT.add(headerDateLT);
									}
									break;
								case 7:
										long forCountLTYear = ChronoUnit.YEARS.between(startMonth, endMonth);
										if(calLT.get(Calendar.MONTH) > calEndLT.get(Calendar.MONTH)) {
											forCountLTYear += 1;
										}
										
										int yearLT = 1;
										
										for(int t = 0; t <= forCountLTYear; t++) {
											calLT.setTime(startDateLT);
											ClientMonthlyDateEntity headerDateLT = new ClientMonthlyDateEntity();
											calLT.add(Calendar.YEAR, t * yearLT);
											headerDateLT.setDownload_time(usFormatLTYear.format(calLT.getTime()));
											headerDateLT.setTime_full(usFormatLTYear.format(calLT.getTime()));
											headerDateLT.setTime_format(usFormatLTYear.format(calLT.getTime()));
											headerDateLT.setCategories_time(catFormatLTYear.format(calLT.getTime()));
											headerDateLT.setChart_energy_kwh(0.001);
											headerDateLT.setExpected_power(0.001);
											headerDateLT.setNvm_irradiance(0.001);
											categoriesLT.add(headerDateLT);
										}
										break;
							}
							
							// get list of time to exclude data from
							List hiddenDataList3 = queryForList("CustomerView.getHiddenDataListBySite", obj);
							obj.setHidden_data_list(hiddenDataList3);
							obj.setGroupMeter(dataListDeviceMeter);
									
							List dataPowerMLT = null;
							if (obj.getEnable_virtual_device() == 1) {
								obj.setDatatablename(obj.getTable_data_virtual());
								dataPowerMLT = queryForList("CustomerView.getDataVirtualDeviceYear", obj);
							} else {
								dataPowerMLT = queryForList("CustomerView.getDataPowerYear", obj);
							}
							
							List<ClientMonthlyDateEntity> dataNewLT = new ArrayList<ClientMonthlyDateEntity> ();
							if(dataPowerMLT.size() > 0 && categoriesLT.size() > 0) {
								for (ClientMonthlyDateEntity item : categoriesLT) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObjLT = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPowerMLT.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPowerMLT.get(v);
										String categoriesTimeLT = item.getTime_format();
										String powerTimeLT = itemT.get("time_format").toString();
										if (categoriesTimeLT.equals(powerTimeLT)) {
								        	flag = true;
								        	mapItemObjLT.setCategories_time(itemT.get("categories_time").toString());
								        	mapItemObjLT.setTime_format(itemT.get("time_format").toString());
								        	mapItemObjLT.setTime_full(itemT.get("time_full").toString());
								        	mapItemObjLT.setDownload_time(itemT.get("download_time").toString());
								        	mapItemObjLT.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
								        	mapItemObjLT.setExpected_power(Double.parseDouble(itemT.get("expected_energy").toString()) );
								        	mapItemObjLT.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
								        	break;
								        }
									}
									
									if(flag == false) {
										ClientMonthlyDateEntity mapItem = new ClientMonthlyDateEntity();
										mapItem.setCategories_time(item.getCategories_time());
										mapItem.setTime_format(item.getTime_format());
										mapItem.setTime_full(item.getTime_full());
										mapItem.setDownload_time(item.getDownload_time());
										mapItem.setChart_energy_kwh(item.getChart_energy_kwh());
										mapItem.setExpected_power(item.getExpected_power());
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNewLT.add(mapItem);
									} else {
										dataNewLT.add(mapItemObjLT);
									}
								}
							}
							
							Map<String, Object> deviceItemMLT = new HashMap<>();
							if (dataPowerMLT.size() > 0) {
								deviceItemMLT.put("data_energy", dataNewLT);
								deviceItemMLT.put("type", "energy");
								deviceItemMLT.put("devicename", "Energy output");
								deviceItemMLT.put("deviceType", "inverter");
								dataEnergy.add(deviceItemMLT);
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
