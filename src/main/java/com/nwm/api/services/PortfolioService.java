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
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.PortfolioEntity;
import com.nwm.api.entities.TablePreferenceEntity;
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
				String inverters_meters = (String) site.get("inverters_meters");
				String alerts = (String) site.get("alerts");
				JSONParser parse = new JSONParser();
				
				if (!Lib.isBlank(alerts)) {
					JSONArray jsonAlerts = (JSONArray) parse.parse(alerts);
					site.put("alerts", jsonAlerts);
				}
				
				
				if (!Lib.isBlank(inverters_meters)) {
					JSONArray jsonArray = (JSONArray) parse.parse(inverters_meters);
					
					List<Object> green_inverter = new ArrayList<>();
					List<Object> yellow_inverter = new ArrayList<>();
					List<Object> red_inverter = new ArrayList<>();
					
					List<Object> green_meter = new ArrayList<>();
					List<Object> yellow_meter = new ArrayList<>();
					List<Object> red_meter = new ArrayList<>();
					
					for (int j = 0; j < jsonArray.size(); j++) {
						Map<String, Object> device = (Map<String, Object>) jsonArray.get(j);
						
						
						Double diff = Double.parseDouble(device.get("diff").toString()) ;
						Double active_power = Double.parseDouble(device.get("active_power").toString()) ;
						
						int id_device_type = Integer.parseInt(device.get("id_device_type").toString()) ;
						if (id_device_type == 1) {
							if (diff > 49 || active_power <= 0.09 || active_power == 0.001) {
								red_inverter.add(device);
							} else if (diff <= 49 && diff >= 10) {
								yellow_inverter.add(device);
							} else if (diff < 10) {
								green_inverter.add(device);
							}
						} else {
							if (diff > 49 || active_power <= 0.09 || active_power == 0.001) {
								red_meter.add(device);
							} else if (diff <= 49 && diff >= 10) {
								yellow_meter.add(device);
							} else if (diff < 10) {
								green_meter.add(device);
							}
						}
					}
					
					if (green_inverter.size() == 0 && yellow_inverter.size() == 0 && red_inverter.size() == 0) {
						site.put("green", green_meter);
						site.put("yellow", yellow_meter);
						site.put("red", red_meter);
					} else {
						site.put("green", green_inverter);
						site.put("yellow", yellow_inverter);
						site.put("red", red_inverter);
					}
				}
				site.remove("inverters_meters");
				
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
	 * @description get user preference for table sorting column
	 * @author Hung.Bui
	 * @since 2023-02-27
	 * @param id_customer, id_site
	 */
	public TablePreferenceEntity getPreference(PortfolioEntity obj) {
		try {
			// get user preference for table sorting column
			TablePreferenceEntity tablePreference = new TablePreferenceEntity();
			tablePreference.setId_employee(obj.getId_employee());
			tablePreference.setTable("Portfolio");
			tablePreference = (TablePreferenceEntity) queryForObject("TablePreference.getPreference", tablePreference);
						
			if ((obj.getOrder_by() != null) && (obj.getSort_column() != null)) {
				if (tablePreference != null) {
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					update("TablePreference.updatePreference", tablePreference);
				} else {
					tablePreference = new TablePreferenceEntity();
					tablePreference.setId_employee(obj.getId_employee());
					tablePreference.setTable("Portfolio");
					tablePreference.setOrder_by(obj.getOrder_by());
					tablePreference.setSort_column(obj.getSort_column());
					insert("TablePreference.insertPreference", tablePreference);
				}
			} else {
				if (tablePreference != null) {
					obj.setOrder_by(tablePreference.getOrder_by());
					obj.setSort_column(tablePreference.getSort_column());
				}
			}
			
			if (tablePreference == null) {
				return new TablePreferenceEntity();
			}
			return tablePreference;
		} catch (Exception ex) {
			return null;
		}
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
	
}
