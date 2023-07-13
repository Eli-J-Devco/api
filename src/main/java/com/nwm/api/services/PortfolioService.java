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

public class PortfolioService extends DB {

	/**
	 * @description get list portfolio by array(id_site)
	 * @author long.pham
	 * @since 2021-01-21
	 * @param arr id_site
	 */

	public List getList(PortfolioEntity obj) {
		List dataList, newData = new ArrayList();
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
			
			dataList = queryForList("Portfolio.getList", obj);
			if (dataList == null)
				return new ArrayList();

			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> siteItem = (Map<String, Object>) dataList.get(i);
				// get alert by site
				List dataListAlert = queryForList("Portfolio.getListAlertBySite", siteItem);
				siteItem.put("alerts", dataListAlert);
				
				// get inverter status  by site
				List inverterList = queryForList("Portfolio.getInverterDeviceStatus", siteItem);
				siteItem.put("inverters", inverterList);
				if (inverterList.size() == 0) {
					List meterList = queryForList("Portfolio.getMeterDeviceStatus", siteItem);
					siteItem.put("meters", meterList);
				}
				
				if (Integer.parseInt((String) siteItem.get("enable_virtual_device")) == 1) {
					double expected_power = (double) queryForObject("Portfolio.getExpectedPowerByVirtualDevice", siteItem);
					siteItem.put("expected_power", expected_power);
				}
				
				// Get weather API
				newData.add(siteItem);
			}
		} catch (Exception ex) {
			return new ArrayList();
		}
		return newData;
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
