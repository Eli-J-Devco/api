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
					switch (obj.getData_send_time()) {
					case 1:
						Map<String, Object> deviceItem1 = new HashMap<>();
						obj.setGroupMeter(dataListDeviceMeter);
						List dataPower1 = queryForList("CustomerView.getDataPowerMeterFiveMinutes", obj);
						if (dataPower1.size() > 0) {
							deviceItem1.put("data_energy", dataPower1);
							deviceItem1.put("type", "energy");
							deviceItem1.put("devicename", "Power");
							deviceItem1.put("deviceType", "meter");
							dataEnergy.add(deviceItem1);
						}
						
						// Get Irradiance
						if (dataListDeviceIrr.size() > 0) {
							for(int i = 0; i < dataListDeviceIrr.size(); i++) {
								Map<String, Object> deviceIrrItem1 = new HashMap<>();
								
								List dataListAIrrDevice = new ArrayList<>();
								
								Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
								dataListAIrrDevice.add(item);
								
								obj.setGroupMeter(dataListAIrrDevice);
								
								List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFiveMinutes", obj);
								if(dataIrradianceDevice.size() > 0 ) {
									deviceIrrItem1.put("data_energy", dataIrradianceDevice);
									deviceIrrItem1.put("type", "irradiance");
									deviceIrrItem1.put("devicename", dataListDeviceIrr.get(i));
									dataEnergy.add(deviceIrrItem1);
								}
							}
						}
						
						break;
					case 2:
						Map<String, Object> deviceItem2 = new HashMap<>();
						obj.setGroupMeter(dataListDeviceMeter);
						List dataPower2 = queryForList("CustomerView.getDataEnergyFifteenMinutes", obj);
						if (dataPower2.size() > 0) {
							deviceItem2.put("data_energy", dataPower2);
							deviceItem2.put("type", "energy");
							deviceItem2.put("devicename", "Power");
							deviceItem2.put("deviceType", "meter");
							dataEnergy.add(deviceItem2);
						}
						
						
						// Get Irradiance
						if (dataListDeviceIrr.size() > 0) {
							for(int i = 0; i < dataListDeviceIrr.size(); i++) {
								Map<String, Object> deviceIrrItem2 = new HashMap<>();
								
								List dataListAIrrDevice = new ArrayList<>();
								
								Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
								dataListAIrrDevice.add(item);
								
								obj.setGroupMeter(dataListAIrrDevice);
								
								List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFifteenMinutes", obj);
								if(dataIrradianceDevice.size() > 0 ) {
									deviceIrrItem2.put("data_energy", dataIrradianceDevice);
									deviceIrrItem2.put("type", "irradiance");
									deviceIrrItem2.put("devicename", dataListDeviceIrr.get(i));
									dataEnergy.add(deviceIrrItem2);
								}
							}
						}

						break;
						
					case 3:
						Map<String, Object> deviceItem3 = new HashMap<>();
						obj.setGroupMeter(dataListDeviceMeter);
						List dataPower3 = queryForList("CustomerView.getDataEnergyHour", obj);
						if (dataPower3.size() > 0) {
							deviceItem3.put("data_energy", dataPower3);
							deviceItem3.put("type", "energy");
							deviceItem3.put("devicename", "Power");
							deviceItem3.put("deviceType", "meter");
							dataEnergy.add(deviceItem3);
						}
						
						// Get Irradiance
						if (dataListDeviceIrr.size() > 0) {
							for(int i = 0; i < dataListDeviceIrr.size(); i++) {
								Map<String, Object> deviceIrrItem3 = new HashMap<>();
								
								List dataListAIrrDevice = new ArrayList<>();
								
								Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
								dataListAIrrDevice.add(item);
								
								obj.setGroupMeter(dataListAIrrDevice);
								
								List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceHour", obj);
								if(dataIrradianceDevice.size() > 0 ) {
									deviceIrrItem3.put("data_energy", dataIrradianceDevice);
									deviceIrrItem3.put("type", "irradiance");
									deviceIrrItem3.put("devicename", dataListDeviceIrr.get(i));
									dataEnergy.add(deviceIrrItem3);
								}
							}
						}
						break;
						
					case 4:
						Map<String, Object> deviceItem4 = new HashMap<>();
						obj.setGroupMeter(dataListDeviceMeter);
						List dataPower4 = queryForList("CustomerView.getDataEnergyHourDay", obj);
						if (dataPower4.size() > 0) {
							deviceItem4.put("data_energy", dataPower4);
							deviceItem4.put("type", "energy");
							deviceItem4.put("devicename", "Power");
							deviceItem4.put("deviceType", "meter");
							dataEnergy.add(deviceItem4);
						}
						
						// Get Irradiance
						if (dataListDeviceIrr.size() > 0) {
							for(int i = 0; i < dataListDeviceIrr.size(); i++) {
								Map<String, Object> deviceIrrItem4 = new HashMap<>();
								
								List dataListAIrrDevice = new ArrayList<>();
								
								Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
								dataListAIrrDevice.add(item);
								
								obj.setGroupMeter(dataListAIrrDevice);
								
								List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceHourDay", obj);
								if(dataIrradianceDevice.size() > 0 ) {
									deviceIrrItem4.put("data_energy", dataIrradianceDevice);
									deviceIrrItem4.put("type", "irradiance");
									deviceIrrItem4.put("devicename", dataListDeviceIrr.get(i));
									dataEnergy.add(deviceIrrItem4);
								}
							}
						}
						break;
					}
					break;
				case "custom":
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
					
					switch (obj.getData_send_time()) {
						case 4: {
							long forCountYTD = ChronoUnit.DAYS.between(calCustom.getTime().toInstant(), calEndCustom.getTime().toInstant());
							
							List<ClientMonthlyDateEntity> categories = new ArrayList<ClientMonthlyDateEntity> ();
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
								categories.add(headerDate);
							}
							
							List<ClientMonthlyDateEntity> dataNew = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerM = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataPowerMeterDayCustomAtMost5Days", obj) : queryForList("CustomerView.getDataPowerMeterDayCustom", obj);
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
										
							break;
						}
						
						case 5: {
							LocalDate dateToSelect = LocalDate.of(calCustom.get(Calendar.YEAR), calCustom.get(Calendar.MONTH) + 1, calCustom.get(Calendar.DAY_OF_MONTH));
							LocalDate lastVisible = LocalDate.of(calEndCustom.get(Calendar.YEAR), calEndCustom.get(Calendar.MONTH) + 1, calEndCustom.get(Calendar.DAY_OF_MONTH));
							long forCountYTD7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
							long forCountYTD = ChronoUnit.DAYS.between(calCustom.getTime().toInstant(), calEndCustom.getTime().toInstant());

							List<ClientMonthlyDateEntity> categoriesYTD7Day = new ArrayList<ClientMonthlyDateEntity> ();
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
								categoriesYTD7Day.add(headerDateLT);
							}
							
							List<ClientMonthlyDateEntity> dataNewYTD7Day = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerYTD7Day = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataPowerMeter7DayCustomAtMost5Days", obj) : queryForList("CustomerView.getDataPowerMeter7DayCustom", obj);
							
							if(dataPowerYTD7Day.size() > 0 && categoriesYTD7Day.size() > 0) {
								for (ClientMonthlyDateEntity item : categoriesYTD7Day) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObjYTD = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPowerYTD7Day.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPowerYTD7Day.get(v);
										String categoriesTimeYTD = item.getTime_format();
										String powerTimeYTD = itemT.get("time_format").toString();
										if (categoriesTimeYTD.equals(powerTimeYTD)) {
								        	flag = true;
								        	mapItemObjYTD.setCategories_time(itemT.get("categories_time").toString());
								        	mapItemObjYTD.setTime_format(itemT.get("time_format").toString());
								        	mapItemObjYTD.setTime_full(itemT.get("time_full").toString());
								        	mapItemObjYTD.setDownload_time(itemT.get("download_time").toString());
								        	mapItemObjYTD.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNewYTD7Day.add(mapItem);
									} else {
										dataNewYTD7Day.add(mapItemObjYTD);
									}
								}
							}
							
							Map<String, Object> deviceItemMYTD7Day = new HashMap<>();
							if (dataPowerYTD7Day.size() > 0) {
								deviceItemMYTD7Day.put("data_energy", dataNewYTD7Day);
								deviceItemMYTD7Day.put("type", "energy");
								deviceItemMYTD7Day.put("devicename", "Energy output");
								deviceItemMYTD7Day.put("deviceType", "meter");
								dataEnergy.add(deviceItemMYTD7Day);
							}							
							break;
						}

						case 6: {
							YearMonth startMonth = YearMonth.of( calCustom.get(Calendar.YEAR) , calCustom.get(Calendar.MONTH) + 1 );
							YearMonth endMonth = YearMonth.of(calEndCustom.get(Calendar.YEAR) , calEndCustom.get(Calendar.MONTH) + 1);
							long forCountYTDMonth = ChronoUnit.MONTHS.between(startMonth, endMonth);
							long forCountYTD = ChronoUnit.DAYS.between(calCustom.getTime().toInstant(), calEndCustom.getTime().toInstant());
					        
							List<ClientMonthlyDateEntity> categoriesYTDMonth = new ArrayList<ClientMonthlyDateEntity> ();
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
								categoriesYTDMonth.add(headerDateLT);
							}
							
							List<ClientMonthlyDateEntity> dataNewYTDMonth = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerMLT = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataPowerMeterMonthCustomAtMost5Days", obj) : queryForList("CustomerView.getDataPowerMeterMonthCustom", obj);
							if(dataPowerMLT.size() > 0 && categoriesYTDMonth.size() > 0) {
								for (ClientMonthlyDateEntity item : categoriesYTDMonth) {
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
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNewYTDMonth.add(mapItem);
									} else {
										dataNewYTDMonth.add(mapItemObjLT);
									}
								}
							}
							
							
							Map<String, Object> deviceItemMLT = new HashMap<>();
							if (dataPowerMLT.size() > 0) {
								deviceItemMLT.put("data_energy", dataNewYTDMonth);
								deviceItemMLT.put("type", "energy");
								deviceItemMLT.put("devicename", "Energy output");
								deviceItemMLT.put("deviceType", "meter");
								dataEnergy.add(deviceItemMLT);
							}
							
							break;
						}

						case 7: {
							YearMonth startMonthCustom = YearMonth.of( calCustom.get(Calendar.YEAR) , calCustom.get(Calendar.MONTH) + 1 );
							YearMonth endMonthCustom = YearMonth.of(calEndCustom.get(Calendar.YEAR) , calEndCustom.get(Calendar.MONTH) + 1);
							long forCountLTYear = ChronoUnit.YEARS.between(startMonthCustom, endMonthCustom);
							long forCountYTD = ChronoUnit.DAYS.between(calCustom.getTime().toInstant(), calEndCustom.getTime().toInstant());

							if(calCustom.get(Calendar.MONTH) > calEndCustom.get(Calendar.MONTH)) {
									forCountLTYear += 1;
								}
							
							List<ClientMonthlyDateEntity> categoriesLTYear = new ArrayList<ClientMonthlyDateEntity> ();
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
								categoriesLTYear.add(headerDateLT);
							}
							
							List<ClientMonthlyDateEntity> dataNewLTYear = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerMLTYear = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataPowerMeterYearCustomAtMost5Days", obj) :  queryForList("CustomerView.getDataPowerMeterYearCustom", obj);
							
							if(dataPowerMLTYear.size() > 0 && categoriesLTYear.size() > 0) {
								for (ClientMonthlyDateEntity item : categoriesLTYear) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObjLT = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPowerMLTYear.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPowerMLTYear.get(v);
										String categoriesTimeLT = item.getTime_format();
										String powerTimeLT = itemT.get("time_format").toString();
										if (categoriesTimeLT.equals(powerTimeLT)) {
								        	flag = true;
								        	mapItemObjLT.setCategories_time(itemT.get("categories_time").toString());
								        	mapItemObjLT.setTime_format(itemT.get("time_format").toString());
								        	mapItemObjLT.setTime_full(itemT.get("time_full").toString());
								        	mapItemObjLT.setDownload_time(itemT.get("download_time").toString());
								        	mapItemObjLT.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNewLTYear.add(mapItem);
									} else {
										dataNewLTYear.add(mapItemObjLT);
									}
								}
							}
							
							Map<String, Object> deviceItemMLTYear = new HashMap<>();
							if (dataPowerMLTYear.size() > 0) {
								deviceItemMLTYear.put("data_energy", dataNewLTYear);
								deviceItemMLTYear.put("type", "energy");
								deviceItemMLTYear.put("devicename", "Energy output");
								deviceItemMLTYear.put("deviceType", "meter");
								dataEnergy.add(deviceItemMLTYear);
							}
							break;
						}
					}
					break;
				case "3_day":
					switch (obj.getData_send_time()) {
						case 1:
							Map<String, Object> deviceItem5 = new HashMap<>();
							obj.setGroupMeter(dataListDeviceMeter);
							List dataPower5 = queryForList("CustomerView.getDataPowerMeterFiveMinutes3Day", obj);
							if (dataPower5.size() > 0) {
								deviceItem5.put("data_energy", dataPower5);
								deviceItem5.put("type", "energy");
								deviceItem5.put("devicename", "Power");
								deviceItem5.put("deviceType", "meter");
								dataEnergy.add(deviceItem5);
							}
							
							// Get Irradiance
							
							if (dataListDeviceIrr.size() > 0) {
								for(int i = 0; i < dataListDeviceIrr.size(); i++) {
									Map<String, Object> deviceIrrItem5 = new HashMap<>();

									List dataListAIrrDevice = new ArrayList<>();
									
									Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
									dataListAIrrDevice.add(item);
									
									obj.setGroupMeter(dataListAIrrDevice);
									
									List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFiveMinutes3Day", obj);
									if(dataIrradianceDevice.size() > 0 ) {
										deviceIrrItem5.put("data_energy", dataIrradianceDevice);
										deviceIrrItem5.put("type", "irradiance");
										deviceIrrItem5.put("devicename", dataListDeviceIrr.get(i));
										dataEnergy.add(deviceIrrItem5);
									}
								}
							}
							
							break;
						case 2:
							Map<String, Object> deviceItem6 = new HashMap<>();
							obj.setGroupMeter(dataListDeviceMeter);
							List dataPower6 = queryForList("CustomerView.getDataEnergyFifteenMinutes3Day", obj);
							if (dataPower6.size() > 0) {
								deviceItem6.put("data_energy", dataPower6);
								deviceItem6.put("type", "energy");
								deviceItem6.put("devicename", "Power");
								deviceItem6.put("deviceType", "meter");
								dataEnergy.add(deviceItem6);
							}
							
							
							// Get Irradiance
							
							if (dataListDeviceIrr.size() > 0) {
								for(int i = 0; i < dataListDeviceIrr.size(); i++) {
									Map<String, Object> deviceIrrItem6 = new HashMap<>();
									
									List dataListAIrrDevice = new ArrayList<>();
										
									Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
									dataListAIrrDevice.add(item);
										
									obj.setGroupMeter(dataListAIrrDevice);
									
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFifteenMinutes3Day", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem6.put("data_energy", dataIrradianceDevice);
											deviceIrrItem6.put("type", "irradiance");
											deviceIrrItem6.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem6);
										}
								}
							}
	
							break;
						
						case 3:
							Map<String, Object> deviceItem7 = new HashMap<>();
							obj.setGroupMeter(dataListDeviceMeter);
							List dataPower7 = queryForList("CustomerView.getDataEnergyHour3Day", obj);
							if (dataPower7.size() > 0) {
								deviceItem7.put("data_energy", dataPower7);
								deviceItem7.put("type", "energy");
								deviceItem7.put("devicename", "Power");
								deviceItem7.put("deviceType", "meter");
								dataEnergy.add(deviceItem7);
							}
							
							// Get Irradiance

							if (dataListDeviceIrr.size() > 0) {
								for(int i = 0; i < dataListDeviceIrr.size(); i++) {
									Map<String, Object> deviceIrrItem7 = new HashMap<>();
									
									List dataListAIrrDevice = new ArrayList<>();
									
									Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
									dataListAIrrDevice.add(item);
									
									obj.setGroupMeter(dataListAIrrDevice);
									
									List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceHour3Day", obj);
									if(dataIrradianceDevice.size() > 0 ) {
										deviceIrrItem7.put("data_energy", dataIrradianceDevice);
										deviceIrrItem7.put("type", "irradiance");
										deviceIrrItem7.put("devicename", dataListDeviceIrr.get(i));
										dataEnergy.add(deviceIrrItem7);
									}
								}
							}
							break;
							
							// 4 day
						case 4: 
							Map<String, Object> deviceItem8 = new HashMap<>();
							obj.setGroupMeter(dataListDeviceMeter);
							List dataPower8 = queryForList("CustomerView.getDataEnergyDay3Day", obj);
							if (dataPower8.size() > 0) {
								deviceItem8.put("data_energy", dataPower8);
								deviceItem8.put("type", "energy");
								deviceItem8.put("devicename", "Power");
								deviceItem8.put("deviceType", "meter");
								dataEnergy.add(deviceItem8);
							}
							
							// Get Irradiance
							if (dataListDeviceIrr.size() > 0) {
								for(int i = 0; i < dataListDeviceIrr.size(); i++) {
									Map<String, Object> deviceIrrItem8 = new HashMap<>();
									
									List dataListAIrrDevice = new ArrayList<>();
									
									Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
									dataListAIrrDevice.add(item);
									
									obj.setGroupMeter(dataListAIrrDevice);
									
									List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceDay3Day", obj);
									if(dataIrradianceDevice.size() > 0 ) {
										deviceIrrItem8.put("data_energy", dataIrradianceDevice);
										deviceIrrItem8.put("type", "irradiance");
										deviceIrrItem8.put("devicename", dataListDeviceIrr.get(i));
										dataEnergy.add(deviceIrrItem8);
									}
								}
							}
							break;
						}
					break;
				case "this_week":
				case "last_week":
						Map<String, Object> deviceItem5 = new HashMap<>();
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
							for(int i = 0; i < dataListDeviceIrr.size(); i++) {
								Map<String, Object> deviceIrrItem5 = new HashMap<>();
								
								List dataListAIrrDevice = new ArrayList<>();
								
								Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
								dataListAIrrDevice.add(item);
								
								obj.setGroupMeter(dataListAIrrDevice);
								
								List dataIrradianceDevice = new ArrayList<>();
								switch (obj.getData_send_time()) {
									case 1:
										dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFiveMinutes3Day", obj);
										break;
									case 2:
										dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFifteenMinutes3Day", obj);
										break;
									case 3:
										dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceHour3Day", obj);
										break;
									case 4: 
										dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceDay3Day", obj);
										break;
								}

								if(dataIrradianceDevice.size() > 0 ) {
									deviceIrrItem5.put("data_energy", dataIrradianceDevice);
									deviceIrrItem5.put("type", "irradiance");
									deviceIrrItem5.put("devicename", dataListDeviceIrr.get(i));
									dataEnergy.add(deviceIrrItem5);
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
						headerDate.setNvm_irradiance(0.001);
						categories.add(headerDate);
					}
					
					List<ClientMonthlyDateEntity> dataNew = new ArrayList<ClientMonthlyDateEntity> ();
					List dataPowerM = queryForList("CustomerView.getDataPowerMeterThisMonth", obj);
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
					
					switch (obj.getData_send_time()) {
						case 4:
							List<ClientMonthlyDateEntity> categoriesYTD = new ArrayList<ClientMonthlyDateEntity> ();
							int dayYTD = 1;
							long forCountYTD = ChronoUnit.DAYS.between(calYTD.getTime().toInstant(), calEndYTD.getTime().toInstant());
							
							for(int t = 0; t <= forCountYTD; t++) {
								calYTD.setTime(startDateYTD);
								
								ClientMonthlyDateEntity headerDateYTD = new ClientMonthlyDateEntity();
								calYTD.add(Calendar.DATE, t * dayYTD);
								headerDateYTD.setDownload_time(usFormatYTD.format(calYTD.getTime()));
								headerDateYTD.setTime_full(usFormatYTD.format(calYTD.getTime()));
								headerDateYTD.setTime_format(usFormatYTD.format(calYTD.getTime()));
								headerDateYTD.setCategories_time(catFormatYTD.format(calYTD.getTime()));
								headerDateYTD.setChart_energy_kwh(0.001);
								headerDateYTD.setNvm_irradiance(0.001);
								categoriesYTD.add(headerDateYTD);
							}
							
							List<ClientMonthlyDateEntity> dataNewYTD = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerMYTD = queryForList("CustomerView.getDataPowerMeterDayYear", obj);
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
							break;							
						case 5:
							LocalDate dateToSelect = LocalDate.of(calYTD.get(Calendar.YEAR), calYTD.get(Calendar.MONTH) + 1, calYTD.get(Calendar.DAY_OF_MONTH));
							LocalDate lastVisible = LocalDate.of(calEndYTD.get(Calendar.YEAR), calEndYTD.get(Calendar.MONTH) + 1, calEndYTD.get(Calendar.DAY_OF_MONTH));
							long forCountYTD7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
						    
							List<ClientMonthlyDateEntity> categoriesYTD7Day = new ArrayList<ClientMonthlyDateEntity> ();
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
								headerDateLT.setNvm_irradiance(0.001);
								categoriesYTD7Day.add(headerDateLT);
							}
							
							List<ClientMonthlyDateEntity> dataNewYTD7Day = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerYTD7Day = queryForList("CustomerView.getDataPowerMeter7DayYear", obj);
							
							if(dataPowerYTD7Day.size() > 0 && categoriesYTD7Day.size() > 0) {
								for (ClientMonthlyDateEntity item : categoriesYTD7Day) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObjYTD = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPowerYTD7Day.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPowerYTD7Day.get(v);
										String categoriesTimeYTD = item.getTime_format();
										String powerTimeYTD = itemT.get("time_format").toString();
										if (categoriesTimeYTD.equals(powerTimeYTD)) {
								        	flag = true;
								        	mapItemObjYTD.setCategories_time(itemT.get("categories_time").toString());
								        	mapItemObjYTD.setTime_format(itemT.get("time_format").toString());
								        	mapItemObjYTD.setTime_full(itemT.get("time_full").toString());
								        	mapItemObjYTD.setDownload_time(itemT.get("download_time").toString());
								        	mapItemObjYTD.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNewYTD7Day.add(mapItem);
									} else {
										dataNewYTD7Day.add(mapItemObjYTD);
									}
								}
							}
							
							Map<String, Object> deviceItemMYTD7Day = new HashMap<>();
							if (dataPowerYTD7Day.size() > 0) {
								deviceItemMYTD7Day.put("data_energy", dataNewYTD7Day);
								deviceItemMYTD7Day.put("type", "energy");
								deviceItemMYTD7Day.put("devicename", "Energy output");
								deviceItemMYTD7Day.put("deviceType", "meter");
								dataEnergy.add(deviceItemMYTD7Day);
							}							
							break;						
						case 6:
							YearMonth startMonth = YearMonth.of( calYTD.get(Calendar.YEAR) , calYTD.get(Calendar.MONTH) + 1 );
					        YearMonth endMonth = YearMonth.of(calEndYTD.get(Calendar.YEAR) , calEndYTD.get(Calendar.MONTH) + 1);
					        long forCountYTDMonth = ChronoUnit.MONTHS.between(startMonth, endMonth);
					        
							List<ClientMonthlyDateEntity> categoriesYTDMonth = new ArrayList<ClientMonthlyDateEntity> ();
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
								headerDateLT.setNvm_irradiance(0.001);
								categoriesYTDMonth.add(headerDateLT);
							}
							
							List<ClientMonthlyDateEntity> dataNewYTDMonth = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerMLT = queryForList("CustomerView.getDataPowerMeterMonthYear", obj);
							if(dataPowerMLT.size() > 0 && categoriesYTDMonth.size() > 0) {
								for (ClientMonthlyDateEntity item : categoriesYTDMonth) {
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
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNewYTDMonth.add(mapItem);
									} else {
										dataNewYTDMonth.add(mapItemObjLT);
									}
								}
							}
							
							
							Map<String, Object> deviceItemMLT = new HashMap<>();
							if (dataPowerMLT.size() > 0) {
								deviceItemMLT.put("data_energy", dataNewYTDMonth);
								deviceItemMLT.put("type", "energy");
								deviceItemMLT.put("devicename", "Energy output");
								deviceItemMLT.put("deviceType", "meter");
								dataEnergy.add(deviceItemMLT);
							}
							
							break;
					}
					
					break;

				case "12_month":				
					// Create list date 
					SimpleDateFormat dateFormat12 = new SimpleDateFormat("yyyy-MM-dd"); 
					SimpleDateFormat usFormat12 = new SimpleDateFormat("MM/yyyy");
					SimpleDateFormat catFormat12 = new SimpleDateFormat("MM/yyyy");
					SimpleDateFormat dayFormat12 = new SimpleDateFormat("dd");
					
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
					
					switch (obj.getData_send_time()) {
						case 4:
							List<ClientMonthlyDateEntity> categories12MonthDay = new ArrayList<ClientMonthlyDateEntity> ();
							int day12Month = 1;
							long forCountYTD = ChronoUnit.DAYS.between(cal12.getTime().toInstant(), calEnd12.getTime().toInstant());
							
							for(int t = 0; t <= forCountYTD; t++) {
								cal12.setTime(startDate12);
								
								ClientMonthlyDateEntity headerDate12MonthDay = new ClientMonthlyDateEntity();
								cal12.add(Calendar.DATE, t * day12Month);
								headerDate12MonthDay.setDownload_time(usFormat12MonthDay.format(cal12.getTime()));
								headerDate12MonthDay.setTime_full(usFormat12MonthDay.format(cal12.getTime()));
								headerDate12MonthDay.setTime_format(usFormat12MonthDay.format(cal12.getTime()));
								headerDate12MonthDay.setCategories_time(catFormat12MonthDay.format(cal12.getTime()));
								headerDate12MonthDay.setChart_energy_kwh(0.001);
								headerDate12MonthDay.setNvm_irradiance(0.001);
								categories12MonthDay.add(headerDate12MonthDay);
							}
							
							List<ClientMonthlyDateEntity> dataNew12MonthDay = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerM12MonthDay = queryForList("CustomerView.getDataPowerMeterDayYear", obj);
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
						break;				
						case 5:
							LocalDate dateToSelect = LocalDate.of(cal12.get(Calendar.YEAR), cal12.get(Calendar.MONTH) + 1, cal12.get(Calendar.DAY_OF_MONTH));
							LocalDate lastVisible = LocalDate.of(calEnd12.get(Calendar.YEAR), calEnd12.get(Calendar.MONTH) + 1, calEnd12.get(Calendar.DAY_OF_MONTH));
							long forCount12Month7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
							
							List<ClientMonthlyDateEntity> categories12Month7Day = new ArrayList<ClientMonthlyDateEntity> ();
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
								headerDateLT.setNvm_irradiance(0.001);
								categories12Month7Day.add(headerDateLT);
							}
							
							List<ClientMonthlyDateEntity> dataNew12Month7Day = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPower12Month7Day = queryForList("CustomerView.getDataPowerMeter7Day12Month", obj);
							
							if(dataPower12Month7Day.size() > 0 && categories12Month7Day.size() > 0) {
								for (ClientMonthlyDateEntity item : categories12Month7Day) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObjYTD = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPower12Month7Day.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPower12Month7Day.get(v);
										String categoriesTimeYTD = item.getTime_format();
										String powerTimeYTD = itemT.get("time_format").toString();
										if (categoriesTimeYTD.equals(powerTimeYTD)) {
								        	flag = true;
								        	mapItemObjYTD.setCategories_time(itemT.get("categories_time").toString());
								        	mapItemObjYTD.setTime_format(itemT.get("time_format").toString());
								        	mapItemObjYTD.setTime_full(itemT.get("time_full").toString());
								        	mapItemObjYTD.setDownload_time(itemT.get("download_time").toString());
								        	mapItemObjYTD.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
										mapItem.setNvm_irradiance(item.getNvm_irradiance());
										dataNew12Month7Day.add(mapItem);
									} else {
										dataNew12Month7Day.add(mapItemObjYTD);
									}
								}
							}
							
							Map<String, Object> deviceItem12Month7Day = new HashMap<>();
							if (dataPower12Month7Day.size() > 0) {
								deviceItem12Month7Day.put("data_energy", dataNew12Month7Day);
								deviceItem12Month7Day.put("type", "energy");
								deviceItem12Month7Day.put("devicename", "Energy output");
								deviceItem12Month7Day.put("deviceType", "meter");
								dataEnergy.add(deviceItem12Month7Day);
							}
							
						break;
						case 6:
							cal12.set(Calendar.DAY_OF_MONTH, cal12.getActualMaximum(Calendar.DAY_OF_MONTH));
							YearMonth startMonth12 = YearMonth.of( cal12.get(Calendar.YEAR) , cal12.get(Calendar.MONTH) + 1 );
							YearMonth endMonth12 = YearMonth.of(calEnd12.get(Calendar.YEAR) , calEnd12.get(Calendar.MONTH) + 1);
							long forCount12Month = ChronoUnit.MONTHS.between(startMonth12, endMonth12);
							
							List<ClientMonthlyDateEntity> categories12 = new ArrayList<ClientMonthlyDateEntity> ();
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
								headerDate12.setNvm_irradiance(0.001);
								categories12.add(headerDate12);
							}
							
							List<ClientMonthlyDateEntity> dataNew12 = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerM12 = queryForList("CustomerView.getDataPowerMeterMonth12Month", obj);
							if(dataPowerM12.size() > 0 && categories12.size() > 0) {
								for (ClientMonthlyDateEntity item : categories12) {
									boolean flag = false;
									ClientMonthlyDateEntity mapItemObj12 = new ClientMonthlyDateEntity();
									for( int v = 0; v < dataPowerM12.size(); v++){
										Map<String, Object> itemT = (Map<String, Object>) dataPowerM12.get(v);
										String categoriesTime12 = item.getTime_format();
										String powerTime12 = itemT.get("time_format").toString();
										if (categoriesTime12.equals(powerTime12)) {
								        	flag = true;
								        	mapItemObj12.setCategories_time(itemT.get("categories_time").toString());
								        	mapItemObj12.setTime_format(itemT.get("time_format").toString());
								        	mapItemObj12.setTime_full(itemT.get("time_full").toString());
								        	mapItemObj12.setDownload_time(itemT.get("download_time").toString());
								        	mapItemObj12.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
								        	mapItemObj12.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
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
										dataNew12.add(mapItem);
									} else {
										dataNew12.add(mapItemObj12);
									}
								}
							}
							
							
							Map<String, Object> deviceItemM12 = new HashMap<>();
							if (dataPowerM12.size() > 0) {
								deviceItemM12.put("data_energy", dataNew12);
								deviceItemM12.put("type", "energy");
								deviceItemM12.put("devicename", "Energy output");
								deviceItemM12.put("deviceType", "meter");
								dataEnergy.add(deviceItemM12);
							}
						break;
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
					switch (obj.getData_send_time()) {
						case 6:						
					        long forCountLT = ChronoUnit.MONTHS.between(startMonth, endMonth);
							
							List<ClientMonthlyDateEntity> categoriesLT = new ArrayList<ClientMonthlyDateEntity> ();
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
								headerDateLT.setNvm_irradiance(0.001);
								categoriesLT.add(headerDateLT);
							}
							
							List<ClientMonthlyDateEntity> dataNewLT = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerMLT = queryForList("CustomerView.getDataPowerMeterMonth12Month", obj);
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
							
						break;
						case 7:
								long forCountLTYear = ChronoUnit.YEARS.between(startMonth, endMonth);
								
								if(calLT.get(Calendar.MONTH) > calEndLT.get(Calendar.MONTH)) {
									forCountLTYear += 1;
								}
								
								List<ClientMonthlyDateEntity> categoriesLTYear = new ArrayList<ClientMonthlyDateEntity> ();
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
									headerDateLT.setNvm_irradiance(0.001);
									categoriesLTYear.add(headerDateLT);
								}
								
								List<ClientMonthlyDateEntity> dataNewLTYear = new ArrayList<ClientMonthlyDateEntity> ();
								List dataPowerMLTYear = queryForList("CustomerView.getDataPowerMeterYearLifetime", obj);
								
								if(dataPowerMLTYear.size() > 0 && categoriesLTYear.size() > 0) {
									for (ClientMonthlyDateEntity item : categoriesLTYear) {
										boolean flag = false;
										ClientMonthlyDateEntity mapItemObjLT = new ClientMonthlyDateEntity();
										for( int v = 0; v < dataPowerMLTYear.size(); v++){
											Map<String, Object> itemT = (Map<String, Object>) dataPowerMLTYear.get(v);
											String categoriesTimeLT = item.getTime_format();
											String powerTimeLT = itemT.get("time_format").toString();
											if (categoriesTimeLT.equals(powerTimeLT)) {
									        	flag = true;
									        	mapItemObjLT.setCategories_time(itemT.get("categories_time").toString());
									        	mapItemObjLT.setTime_format(itemT.get("time_format").toString());
									        	mapItemObjLT.setTime_full(itemT.get("time_full").toString());
									        	mapItemObjLT.setDownload_time(itemT.get("download_time").toString());
									        	mapItemObjLT.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
											mapItem.setNvm_irradiance(item.getNvm_irradiance());
											dataNewLTYear.add(mapItem);
										} else {
											dataNewLTYear.add(mapItemObjLT);
										}
									}
								}
								
								Map<String, Object> deviceItemMLTYear = new HashMap<>();
								if (dataPowerMLTYear.size() > 0) {
									deviceItemMLTYear.put("data_energy", dataNewLTYear);
									deviceItemMLTYear.put("type", "energy");
									deviceItemMLTYear.put("devicename", "Energy output");
									deviceItemMLTYear.put("deviceType", "meter");
									dataEnergy.add(deviceItemMLTYear);
								}
						break;
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
							switch (obj.getData_send_time()) {
							case 1:
								Map<String, Object> deviceItem11 = new HashMap<>();
								obj.setGroupMeter(dataListInverter);
								List dataPower11 = queryForList("CustomerView.getDataPowerMeterFiveMinutesInverter", obj);
								if (dataPower11.size() > 0) {
									deviceItem11.put("data_energy", dataPower11);
									deviceItem11.put("type", "energy");
									deviceItem11.put("devicename", "Power");
									deviceItem11.put("deviceType", "inverter");
									dataEnergy.add(deviceItem11);
								}
								
								// Get Irradiance
								
								if (dataListDeviceIrr.size() > 0) {
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										Map<String, Object> deviceIrrItem11 = new HashMap<>();
										
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFiveMinutes", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem11.put("data_energy", dataIrradianceDevice);
											deviceIrrItem11.put("type", "irradiance");
											deviceIrrItem11.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem11);
										}
									}
								}
								
								break;
							case 2:
								Map<String, Object> deviceItem22 = new HashMap<>();
								obj.setGroupMeter(dataListInverter);
								List dataPower22 = queryForList("CustomerView.getDataEnergyFifteenMinutesInverter", obj);
								if (dataPower22.size() > 0) {
									deviceItem22.put("data_energy", dataPower22);
									deviceItem22.put("type", "energy");
									deviceItem22.put("devicename", "Power");
									deviceItem22.put("deviceType", "inverter");
									dataEnergy.add(deviceItem22);
								}
								
								// Get Irradiance
								
								if (dataListDeviceIrr.size() > 0) {
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										Map<String, Object> deviceIrrItem22 = new HashMap<>();
										
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFifteenMinutes", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem22.put("data_energy", dataIrradianceDevice);
											deviceIrrItem22.put("type", "irradiance");
											deviceIrrItem22.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem22);
										}
									}
								}
								break;
							case 3:
								Map<String, Object> deviceItem33 = new HashMap<>();
								obj.setGroupMeter(dataListInverter);
								List dataPower33 = queryForList("CustomerView.getDataEnergyHourInverter", obj);
								if (dataPower33.size() > 0) {
									deviceItem33.put("data_energy", dataPower33);
									deviceItem33.put("type", "energy");
									deviceItem33.put("devicename", "Power");
									deviceItem33.put("deviceType", "inverter");
									dataEnergy.add(deviceItem33);
								}
								
								// Get Irradiance
								
								if (dataListDeviceIrr.size() > 0) {
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										Map<String, Object> deviceIrrItem33 = new HashMap<>();
										
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceHour", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem33.put("data_energy", dataIrradianceDevice);
											deviceIrrItem33.put("type", "irradiance");
											deviceIrrItem33.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem33);
										}
									}
								}
								break;
								
							case 4:
								Map<String, Object> deviceItem44 = new HashMap<>();
								obj.setGroupMeter(dataListInverter);
								List dataPower44 = queryForList("CustomerView.getDataEnergyInverterHourDay", obj);
								if (dataPower44.size() > 0) {
									deviceItem44.put("data_energy", dataPower44);
									deviceItem44.put("type", "energy");
									deviceItem44.put("devicename", "Power");
									deviceItem44.put("deviceType", "meter");
									dataEnergy.add(deviceItem44);
								}
								
								// Get Irradiance
								if (dataListDeviceIrr.size() > 0) {
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										Map<String, Object> deviceIrrItem44 = new HashMap<>();
										
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceHourDay", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem44.put("data_energy", dataIrradianceDevice);
											deviceIrrItem44.put("type", "irradiance");
											deviceIrrItem44.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem44);
										}
									}
								}
								break;
							}
							break;
							
						case "custom":
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
						
						switch (obj.getData_send_time()) {
							case 4:
							long forCountYTD = ChronoUnit.DAYS.between(calCustom.getTime().toInstant(), calEndCustom.getTime().toInstant());
							
							List<ClientMonthlyDateEntity> categories = new ArrayList<ClientMonthlyDateEntity> ();
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
								categories.add(headerDate);
							}
							
							List<ClientMonthlyDateEntity> dataNew = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerM = forCountYTD + 1 <= 5 ? queryForList("CustomerView.getDataPowerMeterDayCustomAtMost5Days", obj) : queryForList("CustomerView.getDataPowerMeterDayCustom", obj);
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
										
							break;
						
							case 5:
								LocalDate dateToSelect = LocalDate.of(calCustom.get(Calendar.YEAR), calCustom.get(Calendar.MONTH) + 1, calCustom.get(Calendar.DAY_OF_MONTH));
								LocalDate lastVisible = LocalDate.of(calEndCustom.get(Calendar.YEAR), calEndCustom.get(Calendar.MONTH) + 1, calEndCustom.get(Calendar.DAY_OF_MONTH));
								long forCountYTD7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
									
								List<ClientMonthlyDateEntity> categoriesYTD7Day = new ArrayList<ClientMonthlyDateEntity> ();
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
									categoriesYTD7Day.add(headerDateLT);
								}
								
								List<ClientMonthlyDateEntity> dataNewYTD7Day = new ArrayList<ClientMonthlyDateEntity> ();
								List dataPowerYTD7Day = queryForList("CustomerView.getDataPowerMeter7DayCustom", obj);
								
								if(dataPowerYTD7Day.size() > 0 && categoriesYTD7Day.size() > 0) {
									for (ClientMonthlyDateEntity item : categoriesYTD7Day) {
										boolean flag = false;
										ClientMonthlyDateEntity mapItemObjYTD = new ClientMonthlyDateEntity();
										for( int v = 0; v < dataPowerYTD7Day.size(); v++){
											Map<String, Object> itemT = (Map<String, Object>) dataPowerYTD7Day.get(v);
											String categoriesTimeYTD = item.getTime_format();
											String powerTimeYTD = itemT.get("time_format").toString();
											if (categoriesTimeYTD.equals(powerTimeYTD)) {
														flag = true;
														mapItemObjYTD.setCategories_time(itemT.get("categories_time").toString());
														mapItemObjYTD.setTime_format(itemT.get("time_format").toString());
														mapItemObjYTD.setTime_full(itemT.get("time_full").toString());
														mapItemObjYTD.setDownload_time(itemT.get("download_time").toString());
														mapItemObjYTD.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
											mapItem.setNvm_irradiance(item.getNvm_irradiance());
											dataNewYTD7Day.add(mapItem);
										} else {
											dataNewYTD7Day.add(mapItemObjYTD);
										}
									}
								}
								
								Map<String, Object> deviceItemMYTD7Day = new HashMap<>();
								if (dataPowerYTD7Day.size() > 0) {
									deviceItemMYTD7Day.put("data_energy", dataNewYTD7Day);
									deviceItemMYTD7Day.put("type", "energy");
									deviceItemMYTD7Day.put("devicename", "Energy output");
									deviceItemMYTD7Day.put("deviceType", "meter");
									dataEnergy.add(deviceItemMYTD7Day);
								}							
								break;

							case 6:
								YearMonth startMonth = YearMonth.of( calCustom.get(Calendar.YEAR) , calCustom.get(Calendar.MONTH) + 1 );
								YearMonth endMonth = YearMonth.of(calEndCustom.get(Calendar.YEAR) , calEndCustom.get(Calendar.MONTH) + 1);
								long forCountYTDMonth = ChronoUnit.MONTHS.between(startMonth, endMonth);
										
								List<ClientMonthlyDateEntity> categoriesYTDMonth = new ArrayList<ClientMonthlyDateEntity> ();
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
									categoriesYTDMonth.add(headerDateLT);
								}
								
								List<ClientMonthlyDateEntity> dataNewYTDMonth = new ArrayList<ClientMonthlyDateEntity> ();
								List dataPowerMLT = queryForList("CustomerView.getDataPowerMeterMonthCustom", obj);
								if(dataPowerMLT.size() > 0 && categoriesYTDMonth.size() > 0) {
									for (ClientMonthlyDateEntity item : categoriesYTDMonth) {
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
											mapItem.setNvm_irradiance(item.getNvm_irradiance());
											dataNewYTDMonth.add(mapItem);
										} else {
											dataNewYTDMonth.add(mapItemObjLT);
										}
									}
								}
								
								
								Map<String, Object> deviceItemMLT = new HashMap<>();
								if (dataPowerMLT.size() > 0) {
									deviceItemMLT.put("data_energy", dataNewYTDMonth);
									deviceItemMLT.put("type", "energy");
									deviceItemMLT.put("devicename", "Energy output");
									deviceItemMLT.put("deviceType", "meter");
									dataEnergy.add(deviceItemMLT);
								}
								
								break;

							case 7:
								YearMonth startMonthCustom = YearMonth.of( calCustom.get(Calendar.YEAR) , calCustom.get(Calendar.MONTH) + 1 );
								YearMonth endMonthCustom = YearMonth.of(calEndCustom.get(Calendar.YEAR) , calEndCustom.get(Calendar.MONTH) + 1);
								long forCountLTYear = ChronoUnit.YEARS.between(startMonthCustom, endMonthCustom);

								if(calCustom.get(Calendar.MONTH) > calEndCustom.get(Calendar.MONTH)) {
										forCountLTYear += 1;
									}
								
								List<ClientMonthlyDateEntity> categoriesLTYear = new ArrayList<ClientMonthlyDateEntity> ();
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
									categoriesLTYear.add(headerDateLT);
								}
								
								List<ClientMonthlyDateEntity> dataNewLTYear = new ArrayList<ClientMonthlyDateEntity> ();
								List dataPowerMLTYear = queryForList("CustomerView.getDataPowerMeterYearCustom", obj);
								
								if(dataPowerMLTYear.size() > 0 && categoriesLTYear.size() > 0) {
									for (ClientMonthlyDateEntity item : categoriesLTYear) {
										boolean flag = false;
										ClientMonthlyDateEntity mapItemObjLT = new ClientMonthlyDateEntity();
										for( int v = 0; v < dataPowerMLTYear.size(); v++){
											Map<String, Object> itemT = (Map<String, Object>) dataPowerMLTYear.get(v);
											String categoriesTimeLT = item.getTime_format();
											String powerTimeLT = itemT.get("time_format").toString();
											if (categoriesTimeLT.equals(powerTimeLT)) {
														flag = true;
														mapItemObjLT.setCategories_time(itemT.get("categories_time").toString());
														mapItemObjLT.setTime_format(itemT.get("time_format").toString());
														mapItemObjLT.setTime_full(itemT.get("time_full").toString());
														mapItemObjLT.setDownload_time(itemT.get("download_time").toString());
														mapItemObjLT.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
											mapItem.setNvm_irradiance(item.getNvm_irradiance());
											dataNewLTYear.add(mapItem);
										} else {
											dataNewLTYear.add(mapItemObjLT);
										}
									}
								}
								
								Map<String, Object> deviceItemMLTYear = new HashMap<>();
								if (dataPowerMLTYear.size() > 0) {
									deviceItemMLTYear.put("data_energy", dataNewLTYear);
									deviceItemMLTYear.put("type", "energy");
									deviceItemMLTYear.put("devicename", "Energy output");
									deviceItemMLTYear.put("deviceType", "meter");
									dataEnergy.add(deviceItemMLTYear);
								}
								break;
						}
						break;
						case "3_day":
							switch (obj.getData_send_time()) {
							case 1:
								Map<String, Object> deviceItem55 = new HashMap<>();
								obj.setGroupMeter(dataListInverter);
								List dataPower55 = queryForList("CustomerView.getDataPowerMeterFiveMinutesInverter3Day", obj);
								if (dataPower55.size() > 0) {
									deviceItem55.put("data_energy", dataPower55);
									deviceItem55.put("type", "energy");
									deviceItem55.put("devicename", "Power");
									deviceItem55.put("deviceType", "inverter");
									dataEnergy.add(deviceItem55);
								}
								
								// Get Irradiance
								
								if (dataListDeviceIrr.size() > 0) {
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										Map<String, Object> deviceIrrItem55 = new HashMap<>();
										
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFiveMinutes3Day", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem55.put("data_energy", dataIrradianceDevice);
											deviceIrrItem55.put("type", "irradiance");
											deviceIrrItem55.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem55);
										}
									}
								}
								
								break;
							case 2:
								Map<String, Object> deviceItem66 = new HashMap<>();
								obj.setGroupMeter(dataListInverter);
								List dataPower66 = queryForList("CustomerView.getDataEnergyFifteenMinutesInverter3Day", obj);
								if (dataPower66.size() > 0) {
									deviceItem66.put("data_energy", dataPower66);
									deviceItem66.put("type", "energy");
									deviceItem66.put("devicename", "Power");
									deviceItem66.put("deviceType", "inverter");
									dataEnergy.add(deviceItem66);
								}
								
								// Get Irradiance
								
								if (dataListDeviceIrr.size() > 0) {
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										List dataListSensor = new ArrayList<>();
										Map<String, Object> deviceIrrItem66 = new HashMap<>();
										
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFifteenMinutes3Day", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem66.put("data_energy", dataIrradianceDevice);
											deviceIrrItem66.put("type", "irradiance");
											deviceIrrItem66.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem66);
										}
									}
								}
								break;
							case 3:
								Map<String, Object> deviceItem77 = new HashMap<>();
								obj.setGroupMeter(dataListInverter);
								List dataPower77 = queryForList("CustomerView.getDataEnergyHourInverter3Day", obj);
								if (dataPower77.size() > 0) {
									deviceItem77.put("data_energy", dataPower77);
									deviceItem77.put("type", "energy");
									deviceItem77.put("devicename", "Power");
									deviceItem77.put("deviceType", "inverter");
									dataEnergy.add(deviceItem77);
								}
								
								// Get Irradiance
								
								if (dataListDeviceIrr.size() > 0) {
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										Map<String, Object> deviceIrrItem77 = new HashMap<>();
										
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceHour3Day", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem77.put("data_energy", dataIrradianceDevice);
											deviceIrrItem77.put("type", "irradiance");
											deviceIrrItem77.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem77);
										}
									}
								}
								break;
								
								// 4 day
							case 4: 
								Map<String, Object> deviceItem88 = new HashMap<>();
								obj.setGroupMeter(dataListInverter);
								List dataPower88 = queryForList("CustomerView.getDataEnergyInverterHourDay", obj);								if (dataPower88.size() > 0) {
									deviceItem88.put("data_energy", dataPower88);
									deviceItem88.put("type", "energy");
									deviceItem88.put("devicename", "Power");
									deviceItem88.put("deviceType", "meter");
									dataEnergy.add(deviceItem88);
								}
								
								// Get Irradiance
								if (dataListDeviceIrr.size() > 0) {
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										Map<String, Object> deviceIrrItem88 = new HashMap<>();
										
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceDay3Day", obj);
										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem88.put("data_energy", dataIrradianceDevice);
											deviceIrrItem88.put("type", "irradiance");
											deviceIrrItem88.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem88);
										}
									}
								}
								
							}
							break;
						case "this_week":
						case "last_week":
								Map<String, Object> deviceItem5 = new HashMap<>();
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
									for(int i = 0; i < dataListDeviceIrr.size(); i++) {
										Map<String, Object> deviceIrrItem5 = new HashMap<>();
										
										List dataListAIrrDevice = new ArrayList<>();
										
										Map<String, Object> item = (Map<String, Object>) dataListDeviceIrr.get(i);
										dataListAIrrDevice.add(item);
										
										obj.setGroupMeter(dataListAIrrDevice);
										
										List dataIrradianceDevice = new ArrayList<>();
										switch (obj.getData_send_time()) {
											case 1:
												dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFiveMinutes3Day", obj);
												break;
											case 2:
												dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceFifteenMinutes3Day", obj);
												break;
											case 3:
												dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceHour3Day", obj);
												break;
											case 4: 
												dataIrradianceDevice = queryForList("CustomerView.getDataIrradianceDay3Day", obj);
												break;
										}

										if(dataIrradianceDevice.size() > 0 ) {
											deviceIrrItem5.put("data_energy", dataIrradianceDevice);
											deviceIrrItem5.put("type", "irradiance");
											deviceIrrItem5.put("devicename", dataListDeviceIrr.get(i));
											dataEnergy.add(deviceIrrItem5);
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
								headerDate.setNvm_irradiance(0.001);
								categories.add(headerDate);
							}
							
							List<ClientMonthlyDateEntity> dataNew = new ArrayList<ClientMonthlyDateEntity> ();
							List dataPowerM = queryForList("CustomerView.getDataPowerMeterThisMonth", obj);
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
								        	mapItemObj.setChart_energy_kwh(Double.parseDouble(itemT.get("inverterEnergy").toString()) );
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
							
							switch (obj.getData_send_time()) {
								case 4:
									List<ClientMonthlyDateEntity> categoriesYTD = new ArrayList<ClientMonthlyDateEntity> ();
									int dayYTD = 1;
									long forCountYTD = ChronoUnit.DAYS.between(calYTD.getTime().toInstant(), calEndYTD.getTime().toInstant());
									
									for(int t = 0; t <= forCountYTD; t++) {
										calYTD.setTime(startDateYTD);
										
										ClientMonthlyDateEntity headerDateYTD = new ClientMonthlyDateEntity();
										calYTD.add(Calendar.DATE, t * dayYTD);
										headerDateYTD.setDownload_time(usFormatYTD.format(calYTD.getTime()));
										headerDateYTD.setTime_full(usFormatYTD.format(calYTD.getTime()));
										headerDateYTD.setTime_format(usFormatYTD.format(calYTD.getTime()));
										headerDateYTD.setCategories_time(catFormatYTD.format(calYTD.getTime()));
										headerDateYTD.setChart_energy_kwh(0.001);
										headerDateYTD.setNvm_irradiance(0.001);
										categoriesYTD.add(headerDateYTD);
									}
									
									List<ClientMonthlyDateEntity> dataNewYTD = new ArrayList<ClientMonthlyDateEntity> ();
									List dataPowerMYTD = queryForList("CustomerView.getDataPowerMeterDayYear", obj);
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
									break;							
								case 5:
									LocalDate dateToSelect = LocalDate.of(calYTD.get(Calendar.YEAR), calYTD.get(Calendar.MONTH) + 1, calYTD.get(Calendar.DAY_OF_MONTH));
									LocalDate lastVisible = LocalDate.of(calEndYTD.get(Calendar.YEAR), calEndYTD.get(Calendar.MONTH) + 1, calEndYTD.get(Calendar.DAY_OF_MONTH));
									long forCountYTD7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
										
									List<ClientMonthlyDateEntity> categoriesYTD7Day = new ArrayList<ClientMonthlyDateEntity> ();
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
										headerDateLT.setNvm_irradiance(0.001);
										categoriesYTD7Day.add(headerDateLT);
									}
									
									List<ClientMonthlyDateEntity> dataNewYTD7Day = new ArrayList<ClientMonthlyDateEntity> ();
									List dataPowerYTD7Day = queryForList("CustomerView.getDataPowerMeter7DayYear", obj);
									
									if(dataPowerYTD7Day.size() > 0 && categoriesYTD7Day.size() > 0) {
										for (ClientMonthlyDateEntity item : categoriesYTD7Day) {
											boolean flag = false;
											ClientMonthlyDateEntity mapItemObjYTD = new ClientMonthlyDateEntity();
											for( int v = 0; v < dataPowerYTD7Day.size(); v++){
												Map<String, Object> itemT = (Map<String, Object>) dataPowerYTD7Day.get(v);
												String categoriesTimeYTD = item.getTime_format();
												String powerTimeYTD = itemT.get("time_format").toString();
												if (categoriesTimeYTD.equals(powerTimeYTD)) {
															flag = true;
															mapItemObjYTD.setCategories_time(itemT.get("categories_time").toString());
															mapItemObjYTD.setTime_format(itemT.get("time_format").toString());
															mapItemObjYTD.setTime_full(itemT.get("time_full").toString());
															mapItemObjYTD.setDownload_time(itemT.get("download_time").toString());
															mapItemObjYTD.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
												mapItem.setNvm_irradiance(item.getNvm_irradiance());
												dataNewYTD7Day.add(mapItem);
											} else {
												dataNewYTD7Day.add(mapItemObjYTD);
											}
										}
									}
									
									Map<String, Object> deviceItemMYTD7Day = new HashMap<>();
									if (dataPowerYTD7Day.size() > 0) {
										deviceItemMYTD7Day.put("data_energy", dataNewYTD7Day);
										deviceItemMYTD7Day.put("type", "energy");
										deviceItemMYTD7Day.put("devicename", "Energy output");
										deviceItemMYTD7Day.put("deviceType", "meter");
										dataEnergy.add(deviceItemMYTD7Day);
									}							
									break;						
								case 6:
									YearMonth startMonth = YearMonth.of( calYTD.get(Calendar.YEAR) , calYTD.get(Calendar.MONTH) + 1 );
											YearMonth endMonth = YearMonth.of(calEndYTD.get(Calendar.YEAR) , calEndYTD.get(Calendar.MONTH) + 1);
											long forCountYTDMonth = ChronoUnit.MONTHS.between(startMonth, endMonth);
											
									List<ClientMonthlyDateEntity> categoriesYTDMonth = new ArrayList<ClientMonthlyDateEntity> ();
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
										headerDateLT.setNvm_irradiance(0.001);
										categoriesYTDMonth.add(headerDateLT);
									}
									
									List<ClientMonthlyDateEntity> dataNewYTDMonth = new ArrayList<ClientMonthlyDateEntity> ();
									List dataPowerMLT = queryForList("CustomerView.getDataPowerMeterMonthYear", obj);
									if(dataPowerMLT.size() > 0 && categoriesYTDMonth.size() > 0) {
										for (ClientMonthlyDateEntity item : categoriesYTDMonth) {
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
												mapItem.setNvm_irradiance(item.getNvm_irradiance());
												dataNewYTDMonth.add(mapItem);
											} else {
												dataNewYTDMonth.add(mapItemObjLT);
											}
										}
									}
									
									
									Map<String, Object> deviceItemMLT = new HashMap<>();
									if (dataPowerMLT.size() > 0) {
										deviceItemMLT.put("data_energy", dataNewYTDMonth);
										deviceItemMLT.put("type", "energy");
										deviceItemMLT.put("devicename", "Energy output");
										deviceItemMLT.put("deviceType", "meter");
										dataEnergy.add(deviceItemMLT);
									}
									
									break;
							}
							
							break;
						case "12_month":				
							// Create list date 
							SimpleDateFormat dateFormat12 = new SimpleDateFormat("yyyy-MM-dd"); 
							SimpleDateFormat usFormat12 = new SimpleDateFormat("MM/yyyy");
							SimpleDateFormat catFormat12 = new SimpleDateFormat("MM/yyyy");
							SimpleDateFormat dayFormat12 = new SimpleDateFormat("dd");
							
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
							
							switch (obj.getData_send_time()) {
								case 4:
									List<ClientMonthlyDateEntity> categories12MonthDay = new ArrayList<ClientMonthlyDateEntity> ();
									int day12Month = 1;
									long forCountYTD = ChronoUnit.DAYS.between(cal12.getTime().toInstant(), calEnd12.getTime().toInstant());
									
									for(int t = 0; t <= forCountYTD; t++) {
										cal12.setTime(startDate12);
										
										ClientMonthlyDateEntity headerDate12MonthDay = new ClientMonthlyDateEntity();
										cal12.add(Calendar.DATE, t * day12Month);
										headerDate12MonthDay.setDownload_time(usFormat12MonthDay.format(cal12.getTime()));
										headerDate12MonthDay.setTime_full(usFormat12MonthDay.format(cal12.getTime()));
										headerDate12MonthDay.setTime_format(usFormat12MonthDay.format(cal12.getTime()));
										headerDate12MonthDay.setCategories_time(catFormat12MonthDay.format(cal12.getTime()));
										headerDate12MonthDay.setChart_energy_kwh(0.001);
										headerDate12MonthDay.setNvm_irradiance(0.001);
										categories12MonthDay.add(headerDate12MonthDay);
									}
									
									List<ClientMonthlyDateEntity> dataNew12MonthDay = new ArrayList<ClientMonthlyDateEntity> ();
									List dataPowerM12MonthDay = queryForList("CustomerView.getDataPowerMeterDayYear", obj);
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
									break;		
								case 5:
									LocalDate dateToSelect = LocalDate.of(cal12.get(Calendar.YEAR), cal12.get(Calendar.MONTH) + 1, cal12.get(Calendar.DAY_OF_MONTH));
									LocalDate lastVisible = LocalDate.of(calEnd12.get(Calendar.YEAR), calEnd12.get(Calendar.MONTH) + 1, calEnd12.get(Calendar.DAY_OF_MONTH));
									long forCount12Month7Day = ChronoUnit.WEEKS.between(dateToSelect, lastVisible);
									
									List<ClientMonthlyDateEntity> categories12Month7Day = new ArrayList<ClientMonthlyDateEntity> ();
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
										headerDateLT.setNvm_irradiance(0.001);
										categories12Month7Day.add(headerDateLT);
									}
									
									List<ClientMonthlyDateEntity> dataNew12Month7Day = new ArrayList<ClientMonthlyDateEntity> ();
									List dataPower12Month7Day = queryForList("CustomerView.getDataPowerMeter7Day12Month", obj);
									
									if(dataPower12Month7Day.size() > 0 && categories12Month7Day.size() > 0) {
										for (ClientMonthlyDateEntity item : categories12Month7Day) {
											boolean flag = false;
											ClientMonthlyDateEntity mapItemObjYTD = new ClientMonthlyDateEntity();
											for( int v = 0; v < dataPower12Month7Day.size(); v++){
												Map<String, Object> itemT = (Map<String, Object>) dataPower12Month7Day.get(v);
												String categoriesTimeYTD = item.getTime_format();
												String powerTimeYTD = itemT.get("time_format").toString();
												if (categoriesTimeYTD.equals(powerTimeYTD)) {
															flag = true;
															mapItemObjYTD.setCategories_time(itemT.get("categories_time").toString());
															mapItemObjYTD.setTime_format(itemT.get("time_format").toString());
															mapItemObjYTD.setTime_full(itemT.get("time_full").toString());
															mapItemObjYTD.setDownload_time(itemT.get("download_time").toString());
															mapItemObjYTD.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
												mapItem.setNvm_irradiance(item.getNvm_irradiance());
												dataNew12Month7Day.add(mapItem);
											} else {
												dataNew12Month7Day.add(mapItemObjYTD);
											}
										}
									}
									
									Map<String, Object> deviceItem12Month7Day = new HashMap<>();
									if (dataPower12Month7Day.size() > 0) {
										deviceItem12Month7Day.put("data_energy", dataNew12Month7Day);
										deviceItem12Month7Day.put("type", "energy");
										deviceItem12Month7Day.put("devicename", "Energy output");
										deviceItem12Month7Day.put("deviceType", "meter");
										dataEnergy.add(deviceItem12Month7Day);
									}
									
								break;
								case 6:
									cal12.set(Calendar.DAY_OF_MONTH, cal12.getActualMaximum(Calendar.DAY_OF_MONTH));
									List<ClientMonthlyDateEntity> categories12 = new ArrayList<ClientMonthlyDateEntity> ();
									int day12 = 1;
									
									for(int t = 0; t <= 12; t++) {
										cal12.setTime(startDate12);
										ClientMonthlyDateEntity headerDate12 = new ClientMonthlyDateEntity();
										cal12.add(Calendar.MONTH, t * day12);
										headerDate12.setDownload_time(usFormat12.format(cal12.getTime()));
										headerDate12.setTime_full(usFormat12.format(cal12.getTime()));
										headerDate12.setTime_format(usFormat12.format(cal12.getTime()));
										headerDate12.setCategories_time(catFormat12.format(cal12.getTime()));
										headerDate12.setChart_energy_kwh(0.001);
										headerDate12.setNvm_irradiance(0.001);
										categories12.add(headerDate12);
									}
									
									List<ClientMonthlyDateEntity> dataNew12 = new ArrayList<ClientMonthlyDateEntity> ();
									List dataPowerM12 = queryForList("CustomerView.getDataPowerMeterMonth12Month", obj);
									if(dataPowerM12.size() > 0 && categories12.size() > 0) {
										for (ClientMonthlyDateEntity item : categories12) {
											boolean flag = false;
											ClientMonthlyDateEntity mapItemObj12 = new ClientMonthlyDateEntity();
											for( int v = 0; v < dataPowerM12.size(); v++){
												Map<String, Object> itemT = (Map<String, Object>) dataPowerM12.get(v);
												String categoriesTime12 = item.getTime_format();
												String powerTime12 = itemT.get("time_format").toString();
												if (categoriesTime12.equals(powerTime12)) {
															flag = true;
															mapItemObj12.setCategories_time(itemT.get("categories_time").toString());
															mapItemObj12.setTime_format(itemT.get("time_format").toString());
															mapItemObj12.setTime_full(itemT.get("time_full").toString());
															mapItemObj12.setDownload_time(itemT.get("download_time").toString());
															mapItemObj12.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
															mapItemObj12.setNvm_irradiance(Double.parseDouble(itemT.get("nvm_irradiance").toString()) );
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
												dataNew12.add(mapItem);
											} else {
												dataNew12.add(mapItemObj12);
											}
										}
									}
									
									
									Map<String, Object> deviceItemM12 = new HashMap<>();
									if (dataPowerM12.size() > 0) {
										deviceItemM12.put("data_energy", dataNew12);
										deviceItemM12.put("type", "energy");
										deviceItemM12.put("devicename", "Energy output");
										deviceItemM12.put("deviceType", "meter");
										dataEnergy.add(deviceItemM12);
									}
								break;
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
							switch (obj.getData_send_time()) {
								case 6:						
											long forCountLT = ChronoUnit.MONTHS.between(startMonth, endMonth);
									
									List<ClientMonthlyDateEntity> categoriesLT = new ArrayList<ClientMonthlyDateEntity> ();
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
										headerDateLT.setNvm_irradiance(0.001);
										categoriesLT.add(headerDateLT);
									}
									
									List<ClientMonthlyDateEntity> dataNewLT = new ArrayList<ClientMonthlyDateEntity> ();
									List dataPowerMLT = queryForList("CustomerView.getDataPowerMeterMonth12Month", obj);
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
									
								break;
								case 7:
										long forCountLTYear = ChronoUnit.YEARS.between(startMonth, endMonth);
										
										if(calLT.get(Calendar.MONTH) > calEndLT.get(Calendar.MONTH)) {
											forCountLTYear += 1;
										}
										
										List<ClientMonthlyDateEntity> categoriesLTYear = new ArrayList<ClientMonthlyDateEntity> ();
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
											headerDateLT.setNvm_irradiance(0.001);
											categoriesLTYear.add(headerDateLT);
										}
										
										List<ClientMonthlyDateEntity> dataNewLTYear = new ArrayList<ClientMonthlyDateEntity> ();
										List dataPowerMLTYear = queryForList("CustomerView.getDataPowerMeterYearLifetime", obj);
										
										if(dataPowerMLTYear.size() > 0 && categoriesLTYear.size() > 0) {
											for (ClientMonthlyDateEntity item : categoriesLTYear) {
												boolean flag = false;
												ClientMonthlyDateEntity mapItemObjLT = new ClientMonthlyDateEntity();
												for( int v = 0; v < dataPowerMLTYear.size(); v++){
													Map<String, Object> itemT = (Map<String, Object>) dataPowerMLTYear.get(v);
													String categoriesTimeLT = item.getTime_format();
													String powerTimeLT = itemT.get("time_format").toString();
													if (categoriesTimeLT.equals(powerTimeLT)) {
																flag = true;
																mapItemObjLT.setCategories_time(itemT.get("categories_time").toString());
																mapItemObjLT.setTime_format(itemT.get("time_format").toString());
																mapItemObjLT.setTime_full(itemT.get("time_full").toString());
																mapItemObjLT.setDownload_time(itemT.get("download_time").toString());
																mapItemObjLT.setChart_energy_kwh(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
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
													mapItem.setNvm_irradiance(item.getNvm_irradiance());
													dataNewLTYear.add(mapItem);
												} else {
													dataNewLTYear.add(mapItemObjLT);
												}
											}
										}
										
										Map<String, Object> deviceItemMLTYear = new HashMap<>();
										if (dataPowerMLTYear.size() > 0) {
											deviceItemMLTYear.put("data_energy", dataNewLTYear);
											deviceItemMLTYear.put("type", "energy");
											deviceItemMLTYear.put("devicename", "Energy output");
											deviceItemMLTYear.put("deviceType", "meter");
											dataEnergy.add(deviceItemMLTYear);
										}
								break;
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
