/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AssetManagementAndOperationPerformanceReportEntity;
import com.nwm.api.entities.DailyDateEntity;
import com.nwm.api.entities.EnergyExpectationsEntity;
import com.nwm.api.entities.MonthlyDateEntity;
import com.nwm.api.entities.AssetManagementAndOperationPerformanceDataEntity;
import com.nwm.api.entities.QuarterlyDateEntity;
import com.nwm.api.entities.ReportsEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.utils.Constants;

public class ReportsService extends DB {
	
	private static String readProperty(ResourceBundle resourceBundle, String key, String defaultValue) {
		String value = defaultValue;
		try {
			value = resourceBundle.getString(key);
		} catch (Exception e) {
			// TODO: handle exception
			dbLog.error(e);
		}
		return value;
	}
	
	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public Object getDailyReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		DecimalFormat df = new DecimalFormat("###.#");
		try {
			dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null) {
				return null;
			}
			
			List dataListDeviceMeter = queryForList("Reports.getListDeviceTypeMeter", obj);
			if(dataListDeviceMeter.size() > 0 ) {
				obj.setGroupDevices(dataListDeviceMeter);
				List dataPower = queryForList("Reports.getDataEnergyMeterDailyReport", obj);
				if (dataPower.size() > 0) {
					dataObj.setDataReports(dataPower);
				}
			} else {
				List dataListInverter = queryForList("Reports.getListDeviceTypeInverter", obj);
				if(dataListInverter.size() > 0) {
					obj.setGroupDevices(dataListInverter);
					List dataPower = queryForList("Reports.getDataEnergyInverterDailyReport", obj);
					if (dataPower.size() > 0) {
						dataObj.setDataReports(dataPower);
					}
				} 
			}
			
			// Create list date 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			SimpleDateFormat catFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			
			SimpleDateFormat dateFormatHour = new SimpleDateFormat("HH:00");
			Date startDate = dateFormat.parse(obj.getStart_date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			List<DailyDateEntity> categories = new ArrayList<DailyDateEntity> ();
			int minute = 5;
			int forCount = 288 * 3;
			if(obj.getData_intervals() == 1) {
				minute = 5;
				forCount = 288 * 3;
			} else if(obj.getData_intervals() == 2) {
				minute = 15;
				forCount = 96*3;
			} else if(obj.getData_intervals() == 3) {
				minute = 60;
				forCount = 24*3;
			}
			for(int t = 0; t < forCount; t++) {
				cal.setTime(startDate);
				DailyDateEntity headerDate = new DailyDateEntity();
				cal.add(Calendar.MINUTE, t * minute);
				
				headerDate.setTime_format(dateFormat.format(cal.getTime()));
				String hours = dateFormatHour.format(cal.getTime());
				headerDate.setCategories_time(catFormat.format(cal.getTime()));
				headerDate.setEnergy(0.001);
				headerDate.setPower(0.001);
				headerDate.setIrradiance(0.001);
				headerDate.setHour_time(hours);
				categories.add(headerDate);
			}
			
			List dataPower = dataObj.getDataReports();
			List<DailyDateEntity> dataNewPower = new ArrayList<DailyDateEntity> ();
			if(categories.size() > 0) {
				for (DailyDateEntity item : categories) {
					boolean flag = false;
					DailyDateEntity mapItemObj = new DailyDateEntity();
					if(dataPower != null && dataPower.size() > 0) {
						for( int v = 0; v < dataPower.size(); v++){
							Map<String, Object> itemT = (Map<String, Object>) dataPower.get(v);
							String categoriesTime = item.getTime_format();
							String powerTime = itemT.get("time_format").toString();
					        if (categoriesTime.equals(powerTime)) {
					        	flag = true;
					        	mapItemObj.setCategories_time(itemT.get("categories_time").toString());
					        	Double power = Double.parseDouble(itemT.get("power").toString());
					        	power = (power == -0.0) ? 0 : power;
					        	
					        	mapItemObj.setPower( power );
					        	mapItemObj.setIrradiance(item.getIrradiance());
					        	mapItemObj.setTime_format(itemT.get("categories_time").toString());
					        	mapItemObj.setHour_time(itemT.get("hour_time").toString());
					        	
					        	
					        	if(itemT.get("power") == null || Double.parseDouble(itemT.get("power").toString()) == 0.001) {
						        	mapItemObj.setEnergy( 0.001 );
					        	} else {
					        		Double energy = (double)Math.round(Double.parseDouble(itemT.get("power").toString()) * 15/60);
						        	mapItemObj.setEnergy( energy > 0 ? energy : 0 );
					        	}
					        	
					        	
					        	break;
					        }
					    }
					}
					
					
					
					if(flag == false) {
						DailyDateEntity mapItem = new DailyDateEntity();
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
			
			dataObj.setDataReports(dataNewPower);
			
			
			// get irradiance 
			List dataListDeviceIrr = queryForList("Reports.getListDeviceTypeIrradiance", obj);
			if (dataListDeviceIrr.size() > 0) {
				obj.setGroupDevices(dataListDeviceIrr);
				List dataIrradiance = queryForList("Reports.getDataIrradiance", obj);
				if(dataNewPower.size() > 0) {
					List<DailyDateEntity> dataNewIrr = new ArrayList<DailyDateEntity> ();
					for (DailyDateEntity item : dataNewPower) {
						for( int v = 0; v < dataIrradiance.size(); v++){
							Map<String, Object> itemT = (Map<String, Object>) dataIrradiance.get(v);
							String categoriesTime = item.getTime_format();
							String powerTime = itemT.get("categories_time").toString();
					        if (categoriesTime.equals(powerTime)) {
					        	item.setIrradiance(Double.parseDouble(itemT.get("irradiance").toString()));
					        	break;
					        }
					    }
						dataNewIrr.add(item);
					}
					dataObj.setDataReports(dataNewIrr);
				}
			}
			
			dataObj.setHave_poa(dataListDeviceIrr.size() > 0);
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public DailyDateEntity findUsingIterator( String name, List<DailyDateEntity> items) {
//	    Iterator<DailyDateEntity> iterator = items.iterator();
	    
	    for (DailyDateEntity item : items) {
	        if (item.getCategories_time().equals(name)) {
	            return item;
	        }
	    }
	    return null;
	    
//	    while (iterator.hasNext()) {
//	    	DailyDateEntity item = iterator.next();
//	        if (item.getCategories_time().equals(name)) {
//	            return item;
//	        }
//	    }
//	    return null;
	}
	
	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public Object getAnnuallyReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null) {
				return null;
			}
			
			obj.setTable_data_report(dataObj.getTable_data_report());
			List dataListInverter = queryForList("Reports.getListDeviceTypeInverter", obj);
			List dataEnergy = queryForList("Reports.getDataEnergyAnnuallyReport", obj);
			
			if (dataEnergy.size() > 0) {
				dataObj.setDataReports(dataEnergy);
			}
				
			if(dataListInverter.size() > 0) {
				List dataAvailability = queryForList("Reports.getInverterAvailability", obj);
				dataObj.setDataAvailability(dataAvailability);
				dataObj.setDeviceType("inverter");
			} else {
				List dataAvailability = queryForList("Reports.getMeterAvailability", obj);
				dataObj.setDataAvailability(dataAvailability);
				dataObj.setDeviceType("meter");
			}
			
			List dataEnergyExpectations = queryForList("Reports.getReportEnergyExpectations", obj);
			dataObj.setDataExpectations(dataEnergyExpectations);
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	

	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */
	
	public Object getQuarterlyReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null) {
				return null;
			}
			
			obj.setTable_data_report(dataObj.getTable_data_report());
			List dataEnergy = queryForList("Reports.getDataEnergyQurterlyReport", obj);
			if (dataEnergy.size() > 0) {
				dataObj.setDataReports(dataEnergy);
			}
			
			EnergyExpectationsEntity expec = (EnergyExpectationsEntity) queryForObject("Reports.getExpectationsRow", obj);
			
			List<QuarterlyDateEntity> categories = new ArrayList<QuarterlyDateEntity> ();
			
			SimpleDateFormat dateFormat;
			SimpleDateFormat catFormat;
			SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
			
			boolean quarterlyReportByDay = dataObj.getData_intervals() == Constants.DAILY_INTERVAL;
			
			if (quarterlyReportByDay) {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				catFormat = new SimpleDateFormat("MM/dd/yyyy");

				List dataInverterAvailability = queryForList("Reports.getDataInverterAvailabilityByDay", obj);
				if (dataInverterAvailability.size() > 0) { 
					dataObj.setDataAvailability(dataInverterAvailability);
				}
				
				List dataWeatherStation = queryForList("Reports.getDataWeatherStationByDay", obj);
				if (dataWeatherStation != null && dataWeatherStation.size() > 0) {
					dataObj.setDataWeatherStation(dataWeatherStation);
				}
			} else {
				dateFormat = new SimpleDateFormat("yyyy-MM");
				catFormat = new SimpleDateFormat("MMM-yyyy");
			}
			
			Date startDate = dateFormat.parse(obj.getStart_date());
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			
			for (int i = 0; i < 3; i++) {
				int month = Integer.parseInt(monthFormat.format(cal.getTime()).toString());
				int expecValue = 0;
				if(expec != null) {
					switch ( month ) {
					case  1: expecValue = expec.getJan(); break;
					case  2: expecValue = expec.getFeb(); break;
					case  3: expecValue = expec.getMar(); break;
					case  4: expecValue = expec.getApr(); break;
					case  5: expecValue = expec.getMay(); break;
					case  6: expecValue = expec.getJun(); break;
					case  7: expecValue = expec.getJul(); break;
					case  8: expecValue = expec.getAug(); break;
					case  9: expecValue = expec.getSep(); break;
					case  10: expecValue = expec.getOct(); break;
					case  11: expecValue = expec.getNov(); break;
					case  12: expecValue = expec.getDec(); break;
					default:
						expecValue = 0;
					}
				}
				
				if (quarterlyReportByDay) {
					int numOfDaysInMonth = cal.getActualMaximum(Calendar.DATE);
					
					for (int j = 0; j < numOfDaysInMonth; j++) {
						QuarterlyDateEntity category = new QuarterlyDateEntity();
						category.setTime_format(dateFormat.format(cal.getTime()));
						category.setCategories_time(catFormat.format(cal.getTime()));
						category.setEstimated((double) expecValue/numOfDaysInMonth);
						categories.add(category);
						cal.add(Calendar.DATE, 1);
					}
				} else {
					QuarterlyDateEntity category = new QuarterlyDateEntity();
					category.setTime_format(dateFormat.format(cal.getTime()));
					category.setCategories_time(catFormat.format(cal.getTime()));
					category.setEstimated((double) expecValue);
					categories.add(category);
					cal.add(Calendar.MONTH, 1);
				}
			}
			
			List data = dataObj.getDataReports();
			List<QuarterlyDateEntity> dataNew = new ArrayList<QuarterlyDateEntity> ();
			
			if (categories.size() > 0) {
				for (QuarterlyDateEntity item : categories) {
					boolean flag = false;
					QuarterlyDateEntity mapItemObj = new QuarterlyDateEntity();
					
					if(data != null && data.size() > 0) {
						for( int v = 0; v < data.size(); v++) {
							Map<String, Object> itemT = (Map<String, Object>) data.get(v);
							String categoriesTime = item.getTime_format();
							String powerTime = itemT.get(quarterlyReportByDay ? "time_format_by_day" : "time_format").toString();
							
							if (categoriesTime.equals(powerTime)) {
								flag = true;
								mapItemObj.setCategories_time(itemT.get(quarterlyReportByDay ? "categories_time_by_day" : "categories_time").toString());
								mapItemObj.setTime_format(itemT.get(quarterlyReportByDay ? "time_format_by_day" : "time_format").toString());
								mapItemObj.setActual(Double.parseDouble(itemT.get("chart_energy_kwh").toString()));
								mapItemObj.setEstimated(item.getEstimated());
								break;
							}
						}
					}
					
					if(flag == false) {
						QuarterlyDateEntity mapItem = new QuarterlyDateEntity();
						mapItem.setCategories_time(item.getCategories_time());
						mapItem.setTime_format(item.getTime_format());
						mapItem.setActual(null);
						mapItem.setEstimated(null);
						dataNew.add(mapItem);
					} else {
						dataNew.add(mapItemObj);
					}
				}
			}
			
			dataObj.setDataReports(dataNew);
			
			if (quarterlyReportByDay) {
				List dataInverterAvailability = dataObj.getDataAvailability();
				List<QuarterlyDateEntity> dataInverterNew = new ArrayList<QuarterlyDateEntity> ();
				
				if (categories.size() > 0) {
					for (QuarterlyDateEntity item : categories) {
						boolean flag = false;
						QuarterlyDateEntity mapItemObj = new QuarterlyDateEntity();
						
						if (dataInverterAvailability != null && dataInverterAvailability.size() > 0) {
							for( int v = 0; v < dataInverterAvailability.size(); v++) {
								Map<String, Object> itemT = (Map<String, Object>) dataInverterAvailability.get(v);
								String categoriesTime = item.getTime_format();
								String powerTime = itemT.get("time_format").toString();
								
								if (categoriesTime.equals(powerTime)) {
									flag = true;
									mapItemObj.setCategories_time(itemT.get("categories_time").toString());
									mapItemObj.setTime_format(itemT.get("time_format").toString());
									mapItemObj.setInverterAvailability(Double.parseDouble(itemT.get("InverterAvailability").toString()));
									break;
								}
							}
						}
						
						if(flag == false) {
							QuarterlyDateEntity mapItem = new QuarterlyDateEntity();
							mapItem.setCategories_time(item.getCategories_time());
							mapItem.setTime_format(item.getTime_format());
							mapItem.setInverterAvailability(null);
							dataInverterNew.add(mapItem);
						} else {
							dataInverterNew.add(mapItemObj);
						}
					}
					
					dataObj.setDataAvailability(dataInverterNew);
				}
				
				
				List dataWeatherStation = dataObj.getDataWeatherStation();
				List<QuarterlyDateEntity> dataWeatherStationNew = new ArrayList<QuarterlyDateEntity> ();
				
				if (categories.size() > 0) {
					for (QuarterlyDateEntity item : categories) {
						boolean flag = false;
						QuarterlyDateEntity mapItemObj = new QuarterlyDateEntity();
						
						if (dataWeatherStation != null && dataWeatherStation.size() > 0) {
							for( int v = 0; v < dataWeatherStation.size(); v++) {
								Map<String, Object> itemT = (Map<String, Object>) dataWeatherStation.get(v);
								String categoriesTime = item.getTime_format();
								String powerTime = itemT.get("time_format").toString();
								
								if (categoriesTime.equals(powerTime)) {
									flag = true;
									mapItemObj.setCategories_time(itemT.get("categories_time").toString());
									mapItemObj.setTime_format(itemT.get("time_format").toString());
									mapItemObj.setPOAAVG(itemT.get("POAAVG") != null ? Double.parseDouble(itemT.get("POAAVG").toString()) : null);
									mapItemObj.setTCellAVG(itemT.get("TCellAVG") != null ? Double.parseDouble(itemT.get("TCellAVG").toString()) : null);
									break;
								}
							}
						}
						
						if(flag == false) {
							QuarterlyDateEntity mapItem = new QuarterlyDateEntity();
							mapItem.setCategories_time(item.getCategories_time());
							mapItem.setTime_format(item.getTime_format());
							mapItem.setPOAAVG(null);
							mapItem.setTCellAVG(null);
							dataWeatherStationNew.add(mapItem);
						} else {
							dataWeatherStationNew.add(mapItemObj);
						}
					}
					
					dataObj.setDataWeatherStation(dataWeatherStationNew);
				}
				
			}
			
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description Get asset management and performance report
	 * @author Hung.Bui
	 * @since 2024-06-10
	 * @param id_site, date_from, data_to
	 */
	
	public AssetManagementAndOperationPerformanceReportEntity getAssetManagementAndOperationPerformanceReport(ViewReportEntity obj) {
		try {
			ViewReportEntity reportObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (reportObj == null) return null;
			
			DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			DateTimeFormatter mmm_yyFormat = DateTimeFormatter.ofPattern("MMM-yy");
			DateTimeFormatter mm_dd_yyyyFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDateTime startDate = LocalDateTime.parse(obj.getStart_date(), inputDateFormat).withHour(0).withMinute(0).withSecond(0);
			LocalDateTime endDate = LocalDateTime.parse(obj.getEnd_date(), inputDateFormat).withHour(23).withMinute(59).withSecond(59);
			AssetManagementAndOperationPerformanceReportEntity dataObj = new AssetManagementAndOperationPerformanceReportEntity();
			dataObj.setReportDetail(reportObj);
			
			/* operation performance report */
			reportObj.setStart_date(startDate.format(inputDateFormat));
			reportObj.setEnd_date(endDate.format(inputDateFormat));
			List<AssetManagementAndOperationPerformanceDataEntity> operationPerformanceData = queryForList("Reports.getOperationPerformanceReport", reportObj);
			
			if (operationPerformanceData != null && operationPerformanceData.size() > 0) {
				// fulfill data
				LocalDateTime start = startDate;
				LocalDateTime end = endDate;
				List<AssetManagementAndOperationPerformanceDataEntity> dateTimeList = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				List<AssetManagementAndOperationPerformanceDataEntity> fulfilledDataList = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				
				while (!start.isAfter(end)) {
					AssetManagementAndOperationPerformanceDataEntity dateTime = new AssetManagementAndOperationPerformanceDataEntity();
					dateTime.setTime_full(start.format(mmm_yyFormat));
					dateTimeList.add(dateTime);
					start = start.plus(1, ChronoUnit.MONTHS);
				}
				
				for (AssetManagementAndOperationPerformanceDataEntity dateTime: dateTimeList) {
					boolean isFound = false;
					
					for(AssetManagementAndOperationPerformanceDataEntity data: operationPerformanceData) {
						String fullTime = dateTime.getTime_full();
						String powerTime = data.getTime_full();
						
						if (fullTime.equals(powerTime)) {
							fulfilledDataList.add(data);
							isFound = true;
							break;
						}
					}
					
					if (!isFound) fulfilledDataList.add(dateTime);
				}
				
				dataObj.setOperationPerformanceData(fulfilledDataList);
			}
			
			AssetManagementAndOperationPerformanceDataEntity currentMonthOperationPerformanceData = operationPerformanceData.stream().filter(item -> YearMonth.parse(item.getTime_full(), mmm_yyFormat).equals(YearMonth.from(endDate))).findFirst().orElse(null);
			List<AssetManagementAndOperationPerformanceDataEntity> currentYearOperationPerformanceData = operationPerformanceData.stream().filter(item -> YearMonth.parse(item.getTime_full(), mmm_yyFormat).getYear() == YearMonth.from(endDate).getYear()).collect(Collectors.toList());
			
			/* monthly performance report */
			LocalDateTime startDateOfCurrentMonth = endDate.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
			reportObj.setStart_date(startDateOfCurrentMonth.format(inputDateFormat));
			List<AssetManagementAndOperationPerformanceDataEntity> monthlyPerformanceData = queryForList("Reports.getMonthlyPerformanceReport", reportObj);
			
			if (monthlyPerformanceData != null && monthlyPerformanceData.size() > 0) {
				// fulfill data
				LocalDateTime start = startDateOfCurrentMonth;
				LocalDateTime end = endDate;
				List<AssetManagementAndOperationPerformanceDataEntity> dateTimeList = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				List<AssetManagementAndOperationPerformanceDataEntity> fulfilledDataList = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				
				while (!start.isAfter(end)) {
					AssetManagementAndOperationPerformanceDataEntity dateTime = new AssetManagementAndOperationPerformanceDataEntity();
					dateTime.setTime_full(start.format(mm_dd_yyyyFormat));
					dateTimeList.add(dateTime);
					start = start.plus(1, ChronoUnit.DAYS);
				}
				
				for (AssetManagementAndOperationPerformanceDataEntity dateTime: dateTimeList) {
					boolean isFound = false;
					
					for(AssetManagementAndOperationPerformanceDataEntity data: monthlyPerformanceData) {
						String fullTime = dateTime.getTime_full();
						String powerTime = data.getTime_full();
						
						if (fullTime.equals(powerTime)) {
							fulfilledDataList.add(data);
							isFound = true;
							break;
						}
					}
					
					if (!isFound) fulfilledDataList.add(dateTime);
				}
				
				AssetManagementAndOperationPerformanceDataEntity initial = new AssetManagementAndOperationPerformanceDataEntity();
				AssetManagementAndOperationPerformanceDataEntity totalMonthlyPerformanceData = monthlyPerformanceData.stream().reduce(initial, (acc, item) -> {
					if (item.getActualEnergy() != null) acc.setActualEnergy((acc.getActualEnergy() != null ? acc.getActualEnergy() : 0) + item.getActualEnergy());
					if (item.getExpectedEnergy() != null) acc.setExpectedEnergy((acc.getExpectedEnergy() != null ? acc.getExpectedEnergy() : 0) + item.getExpectedEnergy());
					if (item.getModeledEnergy() != null) acc.setModeledEnergy((acc.getModeledEnergy() != null ? acc.getModeledEnergy() : 0) + item.getModeledEnergy());
					
					return acc;
				});
				
				Double totalActualEnergy = totalMonthlyPerformanceData.getActualEnergy();
				Double totalExpectedEnergy = totalMonthlyPerformanceData.getExpectedEnergy();
				Double totalModeledEnergy = totalMonthlyPerformanceData.getModeledEnergy();
				Double totalExpectedGenerationIndex = totalActualEnergy != null && totalExpectedEnergy != null ? totalActualEnergy / totalExpectedEnergy : null;
				Double totalModeledGenerationIndex = totalActualEnergy != null && totalModeledEnergy != null ? totalActualEnergy / totalModeledEnergy : null;
				Double totalWeatherIndex =  currentMonthOperationPerformanceData.getWeatherIndex();
				Double totalInverterAvaiability = currentMonthOperationPerformanceData.getInverterAvailability();
				totalMonthlyPerformanceData.setExpectedGenerationIndex(totalExpectedGenerationIndex);
				totalMonthlyPerformanceData.setModeledGenerationIndex(totalModeledGenerationIndex);
				totalMonthlyPerformanceData.setWeatherIndex(totalWeatherIndex);
				totalMonthlyPerformanceData.setInverterAvailability(totalInverterAvaiability);
				totalMonthlyPerformanceData.setTime_full(endDate.format(mmm_yyFormat));
				
				Map<String, Object> monthlyPerformanceDataObj = new HashMap<String, Object>();
				monthlyPerformanceDataObj.put("data", fulfilledDataList);
				monthlyPerformanceDataObj.put("total", totalMonthlyPerformanceData);
				
				dataObj.setMonthlyPerformanceData(monthlyPerformanceDataObj);
			}
			
			/* monthly asset management report */
			if (currentMonthOperationPerformanceData != null && currentYearOperationPerformanceData.size() > 0) {
				AssetManagementAndOperationPerformanceDataEntity initial = new AssetManagementAndOperationPerformanceDataEntity();
				AssetManagementAndOperationPerformanceDataEntity currentYearTotalAssetManagementData = currentYearOperationPerformanceData.stream().reduce(initial, (acc, item) -> {
					if (item.getActualEnergy() != null) acc.setActualEnergy((acc.getActualEnergy() != null ? acc.getActualEnergy() : 0) + item.getActualEnergy());
					if (item.getModeledEnergy() != null) acc.setModeledEnergy((acc.getModeledEnergy() != null ? acc.getModeledEnergy() : 0) + item.getModeledEnergy());
					
					return acc;
				});
				
				Double monthActualEnergy =  currentMonthOperationPerformanceData.getActualEnergy();
				Double monthModeledEnergy =  currentMonthOperationPerformanceData.getModeledEnergy();
				Double monthEnergyDifference = monthActualEnergy != null && monthModeledEnergy != null ? monthActualEnergy - monthModeledEnergy : null;
				Double monthActualEnergyRevenue = 0.224 * monthActualEnergy;
				Double monthEstimatedEnergyRevenue = 0.224 * monthModeledEnergy;
				Double monthEnergyRevenueDifference = monthActualEnergyRevenue != null && monthEstimatedEnergyRevenue != null ? monthActualEnergyRevenue - monthEstimatedEnergyRevenue : null;
				currentMonthOperationPerformanceData.setEnergyDifference(monthEnergyDifference);
				currentMonthOperationPerformanceData.setActualEnergyRevenue(monthActualEnergyRevenue);
				currentMonthOperationPerformanceData.setEstimatedEnergyRevenue(monthEstimatedEnergyRevenue);
				currentMonthOperationPerformanceData.setEnergyRevenueDifference(monthEnergyRevenueDifference);
				
				Double yearActualEnergy = currentYearTotalAssetManagementData.getActualEnergy();
				Double yearModeledEnergy = currentYearTotalAssetManagementData.getModeledEnergy();
				Double yearEnergyDifference = yearActualEnergy != null && yearModeledEnergy != null ? yearActualEnergy - yearModeledEnergy : null;
				Double yearActualEnergyRevenue = 0.224 * yearActualEnergy;
				Double yearEstimatedEnergyRevenue = 0.224 * yearModeledEnergy;
				Double yearEnergyRevenueDifference = yearActualEnergyRevenue != null && yearEstimatedEnergyRevenue != null ? yearActualEnergyRevenue - yearEstimatedEnergyRevenue : null;
				currentYearTotalAssetManagementData.setEnergyDifference(yearEnergyDifference);
				currentYearTotalAssetManagementData.setActualEnergyRevenue(yearActualEnergyRevenue);
				currentYearTotalAssetManagementData.setEstimatedEnergyRevenue(yearEstimatedEnergyRevenue);
				currentYearTotalAssetManagementData.setEnergyRevenueDifference(yearEnergyRevenueDifference);
				currentYearTotalAssetManagementData.setTime_full(String.valueOf(endDate.getYear()));
				
				List<AssetManagementAndOperationPerformanceDataEntity> monthlyAssetManagementData = new ArrayList<AssetManagementAndOperationPerformanceDataEntity>();
				monthlyAssetManagementData.add(currentMonthOperationPerformanceData);
				monthlyAssetManagementData.add(currentYearTotalAssetManagementData);
				
				dataObj.setMonthlyAssetManagementData(monthlyAssetManagementData);
			}
			
			/* estimated loss by event report */
			List<AssetManagementAndOperationPerformanceDataEntity> estimatedLossByEventData = queryForList("Reports.getEstimatedLossByEventReport", reportObj);
			if (estimatedLossByEventData != null && estimatedLossByEventData.size() > 0) {
				dataObj.setEstimatedLossByEventData(estimatedLossByEventData);
			}
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description update site rec_id
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean updateRECID(SiteEntity obj) {
		try {
			return update("Reports.updateRECID", obj) > 0;
		} catch (Exception ex) {
			log.error("Reports.updateRECID", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update site gu_id
	 * @author long.pham
	 * @since 2023-03-27
	 * @param id
	 */
	public boolean updateGUID(SiteEntity obj) {
		try {
			return update("Reports.updateGUID", obj) > 0;
		} catch (Exception ex) {
			log.error("Reports.updateGUID", ex);
			return false;
		}
	}
	
	/**
	 * @description get list site for page employee manage site
	 * @author long.pham
	 * @since 2021-01-07
	 */

	public List getListSiteByEmployee(ReportsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Reports.getListSiteByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	/**
	 * @description Get list site sub-group by employee
	 * @author Hung.Bui
	 * @since 2023-07-24
	 */
	
	public List getListSiteSubGroupByEmployee(ReportsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Reports.getListSiteSubGroupByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description insert report
	 * @author long.pham
	 * @since 2021-12-27
	 */
	public ReportsEntity insertReports(ReportsEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			session.insert("Reports.insertReports", obj);
			int insertLastId = obj.getId();
			
			List dataSite = obj.getDataSite();
			if (insertLastId > 0 && dataSite != null && dataSite.size() > 0) {
				session.insert("Reports.insertReportSiteMap", obj);
			}
			
			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Reports.insertReports", ex);
			return null;
		} finally {
			session.close();
		}

	}

	/**
	 * @description update role
	 * @author long.pham
	 * @since 2021-01-08
	 * @param id
	 */
	public boolean updateReports(ReportsEntity obj) {

		SqlSession session = this.beginTransaction();
		try {
			session.update("Reports.updateReports", obj);
			
			List dataSite = obj.getDataSite();
			
			session.delete("Reports.deleteReportSiteMap", obj);
			if (dataSite != null && dataSite.size() > 0) session.insert("Reports.insertReportSiteMap", obj);
			
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("Reports.updateReports", ex);
			return false;
		} finally {
			session.close();
		}
	}

	/**
	 * @description get list site by id customer
	 * @author long.pham
	 * @since 2021-12-27
	 * @param object
	 */

	public List getList(ReportsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Reports.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	public int getTotalRecord(ReportsEntity obj) {
		try {
			return (int) queryForObject("Reports.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * @description delete site
	 * @author long.pham
	 * @since 2021-01-11
	 * @param id
	 */
	public boolean deleteReports(ReportsEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			session.delete("Reports.deleteReportSiteMap", obj);
			int rowDelete = session.delete("Reports.deleteReports", obj);
			session.commit();
			return rowDelete > 0;
		} catch (Exception ex) {
			session.rollback();
			log.error("Reports.deleteReports", ex);
			return false;
		} finally {
			session.close();
		}
	}
	
	
	
	/**
	 * @description get monthly  report 
	 * @author long.pham
	 * @since 2022-06-12
	 * @param id_site
	 */
	
	public Object getMonthlyReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);
			if (dataObj == null) {
				return null;
			}
			
			obj.setTable_data_report(dataObj.getTable_data_report());
			List dataEnergy = queryForList("Reports.getDataEnergyMonthlyReport", obj);
			if (dataEnergy.size() > 0) {
				dataObj.setDataReports(dataEnergy);
			}
			
			EnergyExpectationsEntity expec = (EnergyExpectationsEntity) queryForObject("Reports.getExpectationsRow", obj);
			
			// Create list date 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			SimpleDateFormat catFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
			SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
			
			Date startDate = dateFormat.parse(obj.getStart_date() + " AM");
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			List<MonthlyDateEntity> categories = new ArrayList<MonthlyDateEntity> ();
			int day = 1;
			int forCount = Integer.parseInt(dayFormat.format(cal.getTime()).toString());
			
			int month = Integer.parseInt(monthFormat.format(cal.getTime()).toString());
			
			int expecValue = 0;
			if(expec != null) {
				switch ( month ) {
				case  1: expecValue = expec.getJan(); break;
				case  2: expecValue = expec.getFeb(); break;
				case  3: expecValue = expec.getMar(); break;
				case  4: expecValue = expec.getApr(); break;
				case  5: expecValue = expec.getMay(); break;
				case  6: expecValue = expec.getJun(); break;
				case  7: expecValue = expec.getJul(); break;
				case  8: expecValue = expec.getAug(); break;
				case  9: expecValue = expec.getSep(); break;
				case  10: expecValue = expec.getOct(); break;
				case  11: expecValue = expec.getNov(); break;
				case  12: expecValue = expec.getDec(); break;
				default:
					expecValue = 0;
				}
				
			}
			
			for(int t = 0; t < forCount; t++) {
				cal.setTime(startDate);
				MonthlyDateEntity headerDate = new MonthlyDateEntity();
				cal.add(Calendar.DATE, t * day);
				headerDate.setTime_format(dateFormat.format(cal.getTime()));
				headerDate.setCategories_time(catFormat.format(cal.getTime()));
				headerDate.setActual(0.0);
				headerDate.setEstimated((double) expecValue / forCount);
				headerDate.setPercent( expecValue > 0 ? (0.0 / expecValue) : 0);
				categories.add(headerDate);
			}
			
			
			
			List data = dataObj.getDataReports();
			List<MonthlyDateEntity> dataNew = new ArrayList<MonthlyDateEntity> ();
			if(categories.size() > 0) {
				for (MonthlyDateEntity item : categories) {
					boolean flag = false;
					MonthlyDateEntity mapItemObj = new MonthlyDateEntity();
					if(data != null && data.size() > 0) {
						for( int v = 0; v < data.size(); v++){
							Map<String, Object> itemT = (Map<String, Object>) data.get(v);
							String categoriesTime = item.getTime_format();
							String powerTime = itemT.get("time_format").toString();
							
							if (categoriesTime.equals(powerTime)) {
								flag = true;
								mapItemObj.setCategories_time(itemT.get("categories_time").toString());
								
								mapItemObj.setTime_format(itemT.get("time_format").toString());
								mapItemObj.setActual(Double.parseDouble(itemT.get("chart_energy_kwh").toString()) );
								mapItemObj.setEstimated( (double) expecValue/forCount );
								double energy = Double.parseDouble(itemT.get("chart_energy_kwh")!= null ? itemT.get("chart_energy_kwh").toString() : "0.0");
								Double percent = (expecValue / forCount > 0) ?  ((energy /  (expecValue / forCount)) * 100) : 0;
								mapItemObj.setPercent(percent);
								break;
							}
						}
					}
					
					if(flag == false) {
						MonthlyDateEntity mapItem = new MonthlyDateEntity();
						mapItem.setCategories_time(item.getCategories_time());
						mapItem.setActual(item.getActual());
						mapItem.setEstimated(item.getEstimated());
						mapItem.setPercent(item.getPercent());
						mapItem.setTime_format(item.getTime_format());
						dataNew.add(mapItem);
					} else {
						dataNew.add(mapItemObj);
					}
				}
			}
			
			dataObj.setDataReports(dataNew);
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	/**
	 * @description get custom  report 
	 * @author Hung.Bui
	 * @since 2022-12-15
	 * @param id_site, date_from, date_to
	 */
	
	public Object getCustomReport(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("Reports.getDetailReport", obj);

			if (dataObj == null) {
				return null;
			}
			
			obj.setTable_data_report(dataObj.getTable_data_report());
			List dataPower = queryForList("Reports.getDataEnergyCustomReport", obj);
			if (dataPower.size() > 0 ) {
				dataObj.setDataReports(dataPower);
			}
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	

	public Object getSiteDetail(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("Reports.getSiteDetail", obj);
			if (dataObj == null) {
				return null;
			}
			
			switch (dataObj.getId_site_type()) {
				case 1:
				case 6:
				case 2:
				case 4:
					obj.setTable_name("model_shark100");
					break;
				
				case 3:
					obj.setTable_name("");
					break;
				case 5:
				case 9:
					obj.setTable_name("model_veris_industries_e51c2_power_meter");
					break;
				case 7:
					obj.setTable_name("model_elkor_wattson_pv_meter");
					break;
				case 8:
					obj.setTable_name("");
					break;
			}
			
			List dataEnergy = queryForList("Reports.getReportYearDataEnergy", obj);
			dataObj.setDataReports(dataEnergy);
			
			List dataEnergyExpectations = queryForList("Reports.getReportEnergyExpectations", obj);
			dataObj.setDataExpectations(dataEnergyExpectations);
			
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}
	
	
	/**
	 * @description RENEWABLE ENERGY CREDITS
	 * @author long.pham
	 * @since 2022-06-06
	 * @param {}
	 */
	
	public Object renewableReportMonth(ViewReportEntity obj) {
		ViewReportEntity dataObj = new ViewReportEntity();
		try {
			dataObj = (ViewReportEntity) queryForObject("SitesReports.getDetailReport", obj);
			if (dataObj == null) {
				return null;
			}
			List dataListDeviceMeter = queryForList("SitesReports.getListDeviceTypeMeter", obj);
			if(dataListDeviceMeter.size() > 0 ) {
				obj.setGroupDevices(dataListDeviceMeter);
				for (int i = 0; i < dataListDeviceMeter.size(); i++) {
					Map<String, Object> deviceItem = (Map<String, Object>) dataListDeviceMeter.get(i);
					List dataEnergy = queryForList("SitesReports.getDataEnergyMeterMonth", obj);
					if (dataEnergy.size() > 0) {
						dataObj.setDataReports(dataEnergy);
					}
				}
			} else {
				List dataListInverter = queryForList("SitesReports.getListDeviceTypeInverter", obj);
				if(dataListInverter.size() > 0) {
					for (int i = 0; i < dataListInverter.size(); i++) {
						Map<String, Object> deviceItem = (Map<String, Object>) dataListInverter.get(i);
						obj.setGroupDevices(dataListInverter);
						List dataPower = queryForList("SitesReports.getDataEnergyInverterMonth", obj);
						if (dataPower.size() > 0) {
							dataObj.setDataReports(dataPower);
						}
					}
				} 
			}
			return dataObj;
		} catch (Exception ex) {
			return null;
		}
	}

	
	/**
	 * @description get list rec report 
	 * @author long.pham
	 * @since 2022-06-22
	 * @param arr id_site
	 */

	public List getListREC(ReportsEntity obj) {
		try {
			List dataList = queryForList("Reports.getDataEnergyRECReport", obj);
			if (dataList.size() <=0)
				return new ArrayList();
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
}
