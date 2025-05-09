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
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.PortfolioAvailabilityVsPerformanceEntity;
import com.nwm.api.entities.PortfolioEntity;
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

	public List getAvailabilityVsPerformance() {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Portfolio.getAvailabilityVsPerformance", null);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	 /**
	 * @description get availability vs performance
	 * @author giang.le
	 * @since 2025-05-07
	 */
	 
	 public HashMap getEnergyBySite(int site_id, String start_date, String end_date) {
		HashMap data = new HashMap();
		try {
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("site_id", site_id);
			params.put("start_date", start_date);
			params.put("end_date", end_date);
			data = (HashMap) queryForObject("Portfolio.getEnergyBySite", params);
			if (data == null)
				return new HashMap();
		} catch (Exception ex) {
			return new HashMap();
		}
		return data;
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
	
}
