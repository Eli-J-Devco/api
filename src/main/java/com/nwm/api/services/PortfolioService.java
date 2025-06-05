/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.EnergyEntity;
import com.nwm.api.entities.PortfolioAvailabilityVsPerformanceEntity;
import com.nwm.api.entities.PerformanceDataChartItemEntity;
import com.nwm.api.entities.PortfolioEntity;
import com.nwm.api.entities.SiteEnergyEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesMetricsSummaryEntity;
import com.nwm.api.entities.WeatherEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

public class PortfolioService extends DB {

	/**
	 * @description get list portfolio by array(id_site)
	 * @author long.pham
	 * @since 2021-01-21
	 * @param arr id_site
	 */

	public List getList(PortfolioEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Portfolio.getList", obj);
			if (dataList == null)
				return new ArrayList();
			
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> site = (Map<String, Object>) dataList.get(i);
				String devicesList = (String) site.get("devices_list");
				String alerts = (String) site.get("alerts");
				String tags = (String) site.get("tags");
				JSONParser parse = new JSONParser();
				
				if (!Lib.isBlank(alerts)) {
					JSONArray jsonAlerts = (JSONArray) parse.parse(alerts);
					site.put("alerts", jsonAlerts);
				}
				
				if (!Lib.isBlank(tags)) {
					JSONArray jsonTags = (JSONArray) parse.parse(tags);
					site.put("tags", jsonTags);
				}
							
				if (!Lib.isBlank(devicesList)) {
					List<Map<String, Object>> jsonArray = (JSONArray) parse.parse(devicesList);
					
					List<Object> green = new ArrayList<>();
					List<Object> yellow = new ArrayList<>();
					List<Object> red = new ArrayList<>();
					
					boolean hasInverter = jsonArray.stream().filter(item -> Integer.parseInt(item.get("id_device_type").toString()) == 1).findFirst().isPresent();
					
					for (int j = 0; j < jsonArray.size(); j++) {
						Map<String, Object> device = (Map<String, Object>) jsonArray.get(j);
						Double comparison_ratio = device.get("comparison_ratio") == null ? null : Double.parseDouble(device.get("comparison_ratio").toString()) ;
						int id_device_type = Integer.parseInt(device.get("id_device_type").toString());
						if (id_device_type != (hasInverter ? 1 : 3)) continue;
						
						if (comparison_ratio == null || comparison_ratio <= 10) {
							red.add(device);
						} else if (comparison_ratio <= 70) {
							yellow.add(device);
						} else {
							green.add(device);
						}
					}
					
					site.put("green", green);
					site.put("yellow", yellow);
					site.put("red", red);
					site.remove("devices_list");
				}
			}
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}

	public int getTotalRecord(PortfolioEntity obj) {
		try {
			return (int)queryForObject("Portfolio.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}

	public static WeatherEntity fetchFromJSONNext(double lat, double lon)
			throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {

		try {
			String inline = "";
			WeatherEntity item = new WeatherEntity();
			String APIURL = Constants.weatherAPIURL + "?lat=" + lat + "&lon=" + lon + "&appid="
					+ Constants.weatherAPIKEY + "&units=imperial&lang=en";
			URL url = new URL(APIURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				JSONParser parse = new JSONParser();
				JSONObject jobj = (JSONObject) parse.parse(inline);
				JSONArray jsonarr_1 = (JSONArray) jobj.get("weather");
				for (int k = 0; k < jsonarr_1.size(); k++) {
					JSONObject jsonobj_1 = (JSONObject) jsonarr_1.get(k);
					String weatherIcon = (String) jsonobj_1.get("icon");
					String weatherDescription = (String) jsonobj_1.get("description");
					item.setWeather_icon(weatherIcon);
					item.setWeather_description(weatherDescription);
				}
			}
			return item;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * @description get summary 
	 * @author long.pham
	 * @since 2021-03-18
	 * @param id_site
	 * @return Object
	 */

	public PortfolioEntity getAlertSummary(PortfolioEntity obj) {
		PortfolioEntity dataObj = new PortfolioEntity();
		try {
			dataObj = (PortfolioEntity) queryForObject("Portfolio.getAlertSummary", obj);
			if (dataObj == null)
				return new PortfolioEntity();
		} catch (Exception ex) {
			return new PortfolioEntity();
		}
		return dataObj;

	}
	
	/**
	 * @description update a note of a site
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id
	 */
	public boolean updateNote(PortfolioEntity obj) {
		try {
			return update("Portfolio.updateNote", obj) > 0;
		} catch (Exception ex) {
			log.error("Portfolio.updateNote", ex);
			return false;
		}
	}
	
	/**
	 * @description get list site for page employee manage site
	 * @author long.pham
	 * @since 2021-01-12
	 */

	public List getListDeviceBySite(PortfolioEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Portfolio.getListDeviceBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description update a note of a site
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id
	 */
	public boolean updateDefaultDevice(PortfolioEntity obj) {
			SqlSession session = this.beginTransaction();
			try {
				update("Portfolio.updateAllDevicesBySite", obj);
				update("Portfolio.updateDefaultDevice", obj);
				session.commit();
				return true;
			} catch (Exception ex) {
				session.rollback();
				log.error("Portfolio.updateNote", ex);
				return false;
			} finally {
				session.close();
			}
			
		
	}

	/**
	 * @description get availability vs performance
	 * @author giang.le
	 * @since 2025-05-07
	 */

	public List getAvailabilityVsPerformance(PortfolioEntity obj) {
		List<SiteEntity> sites = getSites(obj);
		List dataList = new ArrayList();
		if (sites.size() == 0) return dataList;
		List site_ids = new ArrayList();
		for (SiteEntity site : sites) {
			site_ids.add(site.getId_site());
		}
		obj.setId_sites(site_ids);

		try {			
			dataList = queryForList("Portfolio.getAvailability", obj);
			if (dataList == null)
				return new ArrayList();
			List<SiteEnergyEntity> energyData = getSitesMetricsActualVsExpected(obj);
			for (HashMap<String, Object> item: (List<HashMap<String, Object>>) dataList) {		
				for (SiteEnergyEntity energyItem: energyData) {
					if ((int) item.get("site_id") == energyItem.getId()) {
						item.put("actual_energy", energyItem.getActualEnergy());
						item.put("actual_power", energyItem.getActualPower());
						item.put("expected_energy", energyItem.getExpectedEnergy());
						item.put("expected_power", energyItem.getExpectedPower());
						item.put("performance", Objects.nonNull(energyItem.getActualEnergy()) && Objects.nonNull(energyItem.getExpectedEnergy()) && energyItem.getExpectedEnergy() > 0 ? energyItem.getActualEnergy() / energyItem.getExpectedEnergy() * 100 : null);
						item.put("variance", energyItem.getVariance());
					}
				}
			}
//			try {
//				SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date start_date = date_format.parse(obj.getStart_date());
//				Date end_date = date_format.parse(obj.getEnd_date());
//				Calendar startCalendar = Calendar.getInstance();
//				Calendar endCalendar = Calendar.getInstance();
//				startCalendar.setTime(start_date);
//				endCalendar.setTime(end_date);
//				switch(obj.getId_filter()) {
//					case "today":
//						startCalendar.add(Calendar.DATE, -1);
//						endCalendar.add(Calendar.DATE, -1);
//						endCalendar.set(Calendar.HOUR_OF_DAY, 23);
//						endCalendar.set(Calendar.MINUTE, 59);
//						endCalendar.set(Calendar.SECOND, 59);
//						endCalendar.set(Calendar.MILLISECOND, 999);
//						break;
//					case "this_month":
//						startCalendar.add(Calendar.MONTH, -1);
//						endCalendar.add(Calendar.MONTH, -1);
//						endCalendar.set(Calendar.DATE, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endCalendar.set(Calendar.HOUR_OF_DAY, 23);
//						endCalendar.set(Calendar.MINUTE, 59);
//						endCalendar.set(Calendar.SECOND, 59);
//						endCalendar.set(Calendar.MILLISECOND, 999);
//						break;
//					case "last_month":
//						startCalendar.add(Calendar.MONTH, -1);
//						endCalendar.add(Calendar.MONTH, -1);
//						endCalendar.set(Calendar.DATE, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endCalendar.set(Calendar.HOUR_OF_DAY, 23);
//						endCalendar.set(Calendar.MINUTE, 59);
//						endCalendar.set(Calendar.SECOND, 59);
//						endCalendar.set(Calendar.MILLISECOND, 999);
//						break;
//					case "12_month":
//						startCalendar.add(Calendar.MONTH, -13);
//						endCalendar.add(Calendar.MONTH, -13);
//						endCalendar.set(Calendar.DATE, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//						endCalendar.set(Calendar.HOUR_OF_DAY, 23);
//						endCalendar.set(Calendar.MINUTE, 59);
//						endCalendar.set(Calendar.SECOND, 59);
//						endCalendar.set(Calendar.MILLISECOND, 999);
//						break;
//					default:
//				}
//				obj.setStart_date(date_format.format(startCalendar.getTime()));
//				obj.setEnd_date(date_format.format(endCalendar.getTime()));
//				List previousDataList = queryForList("Portfolio.getAvailability", obj);
//				for (HashMap<String, Object> item: (List<HashMap<String, Object>>) dataList) {
//					for (HashMap<String, Object> previousItem: (List<HashMap<String, Object>>) previousDataList) {
//						if (item.get("site_id").equals(previousItem.get("site_id"))) {
//							item.put("previous_unavailable_inverters", previousItem.get("unavailable_inverters"));
//							item.put("previous_availability", previousItem.get("availability"));
//							item.put("previous_total_inverters", previousItem.get("total_inverters"));
//						}
//					}
//				}
//				List<SiteEnergyEntity> previousEnergyData = getSitesMetricsActualVsExpected(obj);
//				for (HashMap<String, Object> item: (List<HashMap<String, Object>>) dataList) {
//					for (SiteEnergyEntity previousItem: previousEnergyData) {
//						if ((int) item.get("site_id") == previousItem.getId()) {
//							item.put("previous_actual_energy", previousItem.getActualEnergy());
//							item.put("previous_actual_power", previousItem.getActualPower());
//							item.put("previous_expected_energy", previousItem.getExpectedEnergy());
//							item.put("previous_expected_power", previousItem.getExpectedPower());
//							try {
//								item.put("previous_performance", (previousItem.getActualEnergy() / previousItem.getExpectedEnergy()) * 100);
//							} catch (Exception e) {
//								item.put("previous_performance", null);
//							}
//							item.put("previous_variance", previousItem.getVariance());
//						}
//					}
//				}
//			} catch (Exception e) {
//				System.out.println(e);
//			}
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	 
	/**
	 * @description Get sites metrics summary by employee
	 * @author Hung.Bui
	 * @since 2025-05-07
	 * @param obj
	 */
	public SitesMetricsSummaryEntity getSitesMetricsSummary(PortfolioEntity obj) {
		try {
			SitesMetricsSummaryEntity data = (SitesMetricsSummaryEntity) queryForObject("Portfolio.getSitesMetricsSummary", obj);
			if (data == null) return new SitesMetricsSummaryEntity();
			
			return data;
		} catch (Exception ex) {
			return new SitesMetricsSummaryEntity();
		}
	}
	
	/**
	 * @description Get site detail list
	 * @author Hung.Bui
	 * @since 2025-05-08
	 * @param obj
	 */
	public List<SiteEntity> getSites(PortfolioEntity obj) {
		try {
			List<SiteEntity> dataList = queryForList("Portfolio.getSites", obj);
			if (dataList == null) return new ArrayList<>();
			return dataList;
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * @description Get sites metrics loss past 24h
	 * @author Hung.Bui
	 * @since 2025-05-08
	 * @param obj
	 */
	public EnergyEntity getSitesMetricsLossPast24h(PortfolioEntity obj) {
		try {
			List<SiteEntity> sites = getSites(obj);
			if (sites.size() == 0) return new EnergyEntity();
			
			List<CompletableFuture<EnergyEntity>> futureList = new ArrayList<CompletableFuture<EnergyEntity>>();
			for (int i = 0; i < sites.size(); i++) {
				SiteEntity site = sites.get(i);
				
				CompletableFuture<EnergyEntity> future = CompletableFuture.supplyAsync(() -> {
					try {
						EnergyEntity data = new EnergyEntity();
						
						if (site.getEnable_virtual_device() == 1) {
							data = (EnergyEntity) queryForObject("Portfolio.getSitesMetricsLossPast24hByVirtualDevice", site);
						} else {
							List<DeviceEntity> devices = queryForList("CustomerView.getDevicesBySite", site);
							List<DeviceEntity> meters = devices.stream().filter(item -> (item.getId_device_type() == 3 || item.getId_device_type() == 7 || item.getId_device_type() == 9) && !item.isIs_excluded_meter()).collect(Collectors.toList());
							List<DeviceEntity> inverters = devices.stream().filter(item -> (item.getId_device_type() == 1)).collect(Collectors.toList());
							List<DeviceEntity> irradiances = devices.stream().filter(item -> (item.getId_device_type() == 4) && item.getReverse_poa() == 0).collect(Collectors.toList());
							List<DeviceEntity> powerDevices = meters.size() > 0 ? meters : inverters;
							
							if (powerDevices.size() > 0) {
								Double actual = (Double) queryForObject("Portfolio.getSitesMetricsLossPast24hActualByDevice", powerDevices);
								data.setActual(actual);
							}
							if (irradiances.size() > 0) {
								Double expected = (Double) queryForObject("Portfolio.getSitesMetricsLossPast24hExpectedByDevice", irradiances.get(0));
								data.setExpected(expected);
							}
						}
						
						return Objects.nonNull(data) ? data : new EnergyEntity();
					} catch (Exception e) {
						return new EnergyEntity();
					}
				});
				futureList.add(future);
			}
			
			List<EnergyEntity> dataList = futureList.stream().map(future -> future.join()).collect(Collectors.toList());
			Double actual = dataList.stream().anyMatch(item -> Objects.nonNull(item.getActual())) ?
					BigDecimal.valueOf(dataList.stream().filter(item -> Objects.nonNull(item.getActual())).mapToDouble(EnergyEntity::getActual).sum()).setScale(0, RoundingMode.HALF_UP).doubleValue()
					: null;
			Double expected = dataList.stream().anyMatch(item -> Objects.nonNull(item.getExpected())) ?
					BigDecimal.valueOf(dataList.stream().filter(item -> Objects.nonNull(item.getExpected())).mapToDouble(EnergyEntity::getExpected).sum()).setScale(0, RoundingMode.HALF_UP).doubleValue()
					: null;
			Double loss = Objects.nonNull(actual) && Objects.nonNull(expected) && expected > 0 ?
					BigDecimal.valueOf((expected - actual) / expected * 100).setScale(1, RoundingMode.HALF_UP).doubleValue()
					: null;
			return new EnergyEntity(actual, expected, loss);
		} catch (Exception ex) {
			return new EnergyEntity();
		}
	}
	
	/**
	 * @description Get sites metrics actual vs expected
	 * @author Hung.Bui
	 * @since 2025-05-12
	 * @param obj
	 */
	public List<SiteEnergyEntity> getSitesMetricsActualVsExpected(PortfolioEntity obj) {
		try {
			List<SiteEntity> sites = getSites(obj);
			if (sites.size() == 0) return new ArrayList<>();
			
			CustomerViewService customerViewService = new CustomerViewService();
			List<CompletableFuture<SiteEnergyEntity>> futureList = new ArrayList<CompletableFuture<SiteEnergyEntity>>();
			for (int i = 0; i < sites.size(); i++) {
				SiteEntity site = sites.get(i);
				site.setStart_date(obj.getStart_date());
				site.setEnd_date(obj.getEnd_date());
				site.setFilterBy(obj.getId_filter());

				CompletableFuture<SiteEnergyEntity> future = CompletableFuture.supplyAsync(() -> {
					List<PerformanceDataChartItemEntity> data = customerViewService.getChartDataPerformance(site);
					
					SiteEnergyEntity item = new SiteEnergyEntity();
					item.setName(site.getName());
					item.setId(site.getId_site());
					
					for (PerformanceDataChartItemEntity entity : data) {
						ClientMonthlyDateEntity siteEnergyData = entity.getData_energy().get(0);
						
						if (entity.getType().equals("chart_energy_kwh")) {
							item.setActualPower(siteEnergyData.getNvmActivePower());
							item.setActualEnergy(siteEnergyData.getNvmActiveEnergy());
						} else if (entity.getType().equals("expected_power") || entity.getType().equals("expected_energy")) {
							item.setExpectedPower(siteEnergyData.getExpected_power());
							item.setExpectedEnergy(siteEnergyData.getExpected_energy());
						}
					}
					
					if (Objects.nonNull(item.getActualEnergy()) && Objects.nonNull(item.getExpectedEnergy()) && item.getExpectedEnergy() > 0) {
						item.setVariance(BigDecimal.valueOf((item.getExpectedEnergy() - item.getActualEnergy()) / item.getExpectedEnergy() * 100).setScale(2, RoundingMode.HALF_UP).doubleValue());
					}
					
					return item;
				});
				futureList.add(future);
			}
			
			List<SiteEnergyEntity> dataList = futureList.stream().map(future -> future.join()).collect(Collectors.toList());
			return dataList;
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}
	
}
