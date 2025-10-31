/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.EnergyEntity;
import com.nwm.api.entities.PerformanceDataChartItemEntity;
import com.nwm.api.entities.PortfolioEntity;
import com.nwm.api.entities.SiteEnergyEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesMetricsSummaryEntity;
import com.nwm.api.entities.WeatherEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.Constants.ChartingFilter;
import com.nwm.api.utils.Constants.ChartingGranularity;

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
			if (dataList == null) return new ArrayList();
			
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> site = (Map<String, Object>) dataList.get(i);
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
							
				setInverterStatus(site);
			}
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	private void setInverterStatus(Map<String, Object> site) {
		try {
			String devicesList = (String) site.get("devices_list");
			if (Lib.isBlank(devicesList)) return;
			
			JSONParser parse = new JSONParser();
			List<Map<String, Object>> jsonArray = (JSONArray) parse.parse(devicesList);
			
			List<Object> green = new ArrayList<>();
			List<Object> yellow = new ArrayList<>();
			List<Object> red = new ArrayList<>();
			List<Object> inverters = new ArrayList<>();
			
			boolean hasInverter = jsonArray.stream().filter(item -> Integer.parseInt(item.get("id_device_type").toString()) == 1).findFirst().isPresent();
			
			for (int j = 0; j < jsonArray.size(); j++) {
				Map<String, Object> device = (Map<String, Object>) jsonArray.get(j);
				Double comparison_ratio = device.get("comparison_ratio") == null ? null : Double.parseDouble(device.get("comparison_ratio").toString()) ;
				int id_device_type = Integer.parseInt(device.get("id_device_type").toString());
				if(id_device_type == 1) { inverters.add(device); }
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
			site.put("inverters", inverters);
			site.remove("devices_list");
		} catch (Exception e) {
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
		try {			
			List<Map<String, Object>> dataList = queryForList("Portfolio.getAvailability", obj);
			if (dataList == null) return new ArrayList();
			
			List<SiteEnergyEntity> energyData = getSitesMetricsActualVsExpected(obj);
			for (Map<String, Object> item: dataList) {
				setInverterStatus(item);
				
				for (SiteEnergyEntity energyItem: energyData) {
					if ((int) item.get("site_id") == energyItem.getId()) {
						item.put("actual_energy", energyItem.getActualEnergy());
						item.put("actual_power", energyItem.getActualPower());
						item.put("expected_energy", energyItem.getExpectedEnergy());
						item.put("expected_power", energyItem.getExpectedPower());
						item.put("performance", Objects.nonNull(energyItem.getActualEnergy()) && Objects.nonNull(energyItem.getExpectedEnergy()) && energyItem.getExpectedEnergy() > 0 ? Math.round(energyItem.getActualEnergy() / energyItem.getExpectedEnergy() * 1000.0) / 1000.0 : null);
						item.put("variance", energyItem.getVariance());
						item.put("hash_id", energyItem.getHash_id());
						
						break;
					}
				}
			}
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	 
	/**
	 * @description Get sites metrics summary by employee
	 * @author Hung.Bui
	 * @since 2025-05-07
	 * @param obj
	 */
	public List<SitesMetricsSummaryEntity> getSitesMetricsSummary(PortfolioEntity obj) {
		try {
			List<SitesMetricsSummaryEntity> data = queryForList("Portfolio.getSitesMetricsSummary", obj);
			if (data == null) return new ArrayList<>();
			
			JSONParser parse = new JSONParser();
			for (SitesMetricsSummaryEntity site : data) {
				try {
					String alerts = site.getAlertsJSON();
					if (Objects.isNull(alerts)) continue;
					List<Map<String, Object>> jsonArray = (JSONArray) parse.parse(alerts);
					site.setAlertsJSON(null);
					site.setAlerts(jsonArray);
				} catch (Exception e) {
				}
			}
			
			return data;
		} catch (Exception ex) {
			return new ArrayList<>();
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
	public List<EnergyEntity> getSitesMetricsLossPast24h(PortfolioEntity obj) {
		try {
			List<SiteEntity> sites = getSites(obj);
			if (sites.size() == 0) return new ArrayList<>();
			
			List<CompletableFuture<EnergyEntity>> futureList = new ArrayList<CompletableFuture<EnergyEntity>>();
			for (int i = 0; i < sites.size(); i++) {
				SiteEntity site = sites.get(i);
				site.setDomain(obj.getDomain());
				
				CompletableFuture<EnergyEntity> future = CompletableFuture.supplyAsync(() -> {
					EnergyEntity data = new EnergyEntity(site.getId_site(), site.getHash_id(), site.getName());
					
					try {
						if (site.getEnable_virtual_device() == 1) {
							EnergyEntity energy = (EnergyEntity) queryForObject("Portfolio.getSitesMetricsLossPast24hByVirtualDevice", site);
							if (energy == null) return data;
							
							data.setActual(energy.getActual());
							data.setExpected(energy.getExpected());
						} else {
							CustomerViewService customerViewService = new CustomerViewService();
							DevicesByTypeEntity devices = customerViewService.getDevicesBySite(site);
							List<DeviceEntity> meters = devices.getMeter();
							List<DeviceEntity> inverters = devices.getInverter();
							List<DeviceEntity> irradiances = devices.getIrradiance();
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
						
						if (Objects.nonNull(data.getActual()) && Objects.nonNull(data.getExpected()) && data.getExpected() > 0) data.setLoss((data.getExpected() - data.getActual()) / data.getExpected());
						
					} catch (Exception e) {
					}
					
					return data;
				});
				futureList.add(future);
			}
			
			return futureList.stream().map(future -> future.join()).collect(Collectors.toList());
		} catch (Exception ex) {
			return new ArrayList<>();
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
				
				int data_send_time = 0;
				switch (ChartingFilter.fromValue(obj.getId_filter())) {
					case TODAY:
						data_send_time = ChartingGranularity._1_DAY.getValue();
						break;
					case THIS_MONTH:
						data_send_time = ChartingGranularity._1_MONTH.getValue();
						break;
					default:
						break;
				}
				site.setData_send_time(data_send_time);

				CompletableFuture<SiteEnergyEntity> future = CompletableFuture.supplyAsync(() -> {
					List<PerformanceDataChartItemEntity> data = customerViewService.getChartDataPerformance(site);
					
					SiteEnergyEntity item = new SiteEnergyEntity();
					item.setName(site.getName());
					item.setId(site.getId_site());
					item.setHash_id(site.getHash_id());
					item.setLast_updated(site.getLast_updated());
					item.setOverPerformingActualExpected(site.getOverPerformingActualExpected());
					item.setOnTargetBetweenActualExpected(site.getOnTargetBetweenActualExpected());
					item.setOnTargetAndActualExpected(site.getOnTargetAndActualExpected());
					item.setUnderPerformingActualExpected(site.getUnderPerformingActualExpected());
					


					
					for (PerformanceDataChartItemEntity entity : data) {
						ClientMonthlyDateEntity siteEnergyData = entity.getData_energy().get(0);
						
						if (entity.getType().equals("chart_energy_kwh")) {
							item.setActualPower(siteEnergyData.getNvmActivePower());
							item.setActualEnergy(siteEnergyData.getNvmActiveEnergy());
						} else if (entity.getType().equals("expected_power") || entity.getType().equals("expected_energy")) {
							item.setExpectedPower(siteEnergyData.getExpected_power());
							item.setExpectedEnergy(siteEnergyData.getExpected_energy());
						} else if (entity.getType().equals("nvm_irradiance")) {
							item.setIrradiance(siteEnergyData.getNvm_irradiance());						
							}
						
					}
					
					if (Objects.nonNull(item.getActualEnergy()) && Objects.nonNull(item.getExpectedEnergy()) && item.getExpectedEnergy() > 0) {
						item.setVariance((item.getActualEnergy() - item.getExpectedEnergy()) / item.getExpectedEnergy());
						item.setAe(Math.round(item.getActualEnergy() / item.getExpectedEnergy() * 1000.0) / 1000.0);
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
	
	/**
	 * @description Get sites metrics chart generation
	 * @author Hung.Bui
	 * @since 2025-07-21
	 * @param obj
	 */
	public List<ClientMonthlyDateEntity> getSitesMetricsChartGeneration(PortfolioEntity obj) {
		try {
			List<SiteEntity> sites = getSites(obj);
			if (sites.size() == 0) return new ArrayList<>();
			
			CustomerViewService customerViewService = new CustomerViewService();
			List<CompletableFuture<List<ClientMonthlyDateEntity>>> futureList = new ArrayList<CompletableFuture<List<ClientMonthlyDateEntity>>>();
			
			for (int i = 0; i < sites.size(); i++) {
				SiteEntity site = sites.get(i);
				site.setDomain(obj.getDomain());
				site.setStart_date(obj.getStart_date());
				site.setEnd_date(obj.getEnd_date());
				site.setData_send_time(ChartingGranularity._15_MINUTES.getValue());
				site.setFilterBy(ChartingFilter._3_DAYS.getValue());
				CompletableFuture<List<ClientMonthlyDateEntity>> future = CompletableFuture.supplyAsync(() -> customerViewService.getSitePowerChart(site));
				futureList.add(future);
			}
			
			List<List<ClientMonthlyDateEntity>> siteDataList = futureList.stream()
					.map(future -> future.join())
					.filter(item -> item.size() > 0)
					.collect(Collectors.toList());
			List<ClientMonthlyDateEntity> longestSiteData = Collections.max(siteDataList, (l1, l2) -> l1.size() - l2.size());
			List<ClientMonthlyDateEntity> data = new ArrayList<ClientMonthlyDateEntity>(longestSiteData);
			
			for (int i = 0; i < longestSiteData.size(); i++) {
				data.get(i).setNvmActivePower(null);
				
				for (List<ClientMonthlyDateEntity> siteData : siteDataList) {
					if (i > siteData.size() - 1) continue;
					if (Objects.nonNull(siteData.get(i).getNvmActivePower())) data.get(i).setNvmActivePower(Optional.ofNullable(data.get(i).getNvmActivePower()).orElse(0.0) + siteData.get(i).getNvmActivePower());
				}
			}
			
			return data;
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}
}
