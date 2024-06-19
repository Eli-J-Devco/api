/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.utils.Constants;

public class SiteConfigService extends DB {
	/**
	 * @description update site config
	 * @author long.pham
	 * @since 2022-07-27
	 * @param id
	 */
	public boolean updateSiteConfig(SitesDevicesEntity obj) {

		SqlSession session = this.beginTransaction();
		try {
			if(obj.getId() > 0 && obj.getId_employee() > 0) {
				if (!obj.getAlert_mail_bcc().isEmpty()) {
					int checkExits = (int) queryForObject("SiteConfig.checkExitsEmployeeOnSiteEmailMap", obj);
					if(checkExits <= 0) {
						// Insert
						if (!obj.getCf_email_subscribers().isEmpty()) {
							session.update("SiteConfig.insertEmployeeOnSiteEmailMap", obj);
						}
					} else {
						// Update
						session.update("SiteConfig.updateEmployeeOnSiteEmailMap", obj);
					}
				} else {
					session.delete("SiteConfig.deleteEmployeeOnSiteEmailMap", obj);
				}
				
			}
			
			session.update("SiteConfig.updateSiteConfig", obj);
			session.update("SiteConfig.updateDeviceResetDisableAlert", obj);
			List dataDevice = obj.getDeviceDisableAlerts();
			if (dataDevice.size() > 0) {
				for (int i = 0; i < dataDevice.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) dataDevice.get(i);
					session.update("SiteConfig.enableDeviceDisableAlert", item);
				}
			}

			List errorLevel = obj.getErrorLevel();

			// Delete error_level_site_map
			session.update("SiteConfig.deleteErrorLevelSiteMap", obj);
			if (errorLevel.size() > 0) {
				session.update("SiteConfig.insertErrorLevelSiteMap", obj);
			}

			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("SiteConfig.updateSiteConfig", ex);
			return false;
		} finally {
			session.close();
		}
	}
	
	
	
	
	/**
	 * @description update site config
	 * @author long.pham
	 * @since 2022-07-27
	 * @param id
	 */
	public boolean updateSiteSetting(SitesDevicesEntity obj){
		
		SqlSession session = this.beginTransaction();
		try {
			session.update("SiteConfig.updateSiteSetting", obj);
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("SiteConfig.updateSiteSetting", ex);
			return false;
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description update pv model setting
	 * @author Hung.Bui
	 * @since 2023-06-26
	 * @param id
	 */
	public boolean updatePVModelSetting(SitesDevicesEntity obj){
		
		SqlSession session = this.beginTransaction();
		try {
			session.update("SiteConfig.updatePVModelSetting", obj);
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("SiteConfig.updatePVModelSetting", ex);
			return false;
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description get list site config
	 * @author long.pham
	 * @since 2022-07-28
	 */

	public List getListSiteConfig(SitesDevicesEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("SiteConfig.getListSiteConfig", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
		
	/**
	 * @description Get list weather station
	 * @author Hung.Bui
	 * @since 2023-07-07
	 */
	
	public List getWeatherStationListSiteConfig(SitesDevicesEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("SiteConfig.getWeatherStationListSiteConfig", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

	/**
	 * @description get total site config
	 * @author long.pham
	 * @since 2022-07-28
	 */
	public int getSiteConfigTotal(SitesDevicesEntity obj) {
		try {
			return (int) queryForObject("SiteConfig.getSiteConfigTotal", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
}
