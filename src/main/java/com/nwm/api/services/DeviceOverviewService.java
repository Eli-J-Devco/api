/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.PortfolioRankingEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesDevicesEntity;

public class DeviceOverviewService extends DB {

	
	/**
	 * @description get list device by id_company
	 * @author long.pham
	 * @since 2025-07-30
	 * @param id_company
	 */
	

	public List getListDeviceByCompany(SitesDevicesEntity obj) {
		try {
			List dataList = queryForList("DeviceOverview.getListDeviceByCompany", obj);
			if (dataList == null) return new ArrayList();
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	
	/**
	 * @description get list device by id_company
	 * @author long.pham
	 * @since 2025-07-30
	 * @param id_company
	 */
	
	public int getTotalRecordByCompany(SitesDevicesEntity obj) {
		try {
			return (int)queryForObject("DeviceOverview.getTotalRecordByCompany", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description get all site by id_employee, id_company
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 */
	

	public List getSiteByEmployee(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceOverview.getSiteByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get list site group by id_employee, id_company
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 */
	

	public List getListSiteGroups(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceOverview.getListSiteGroups", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get alert by device id
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 */
	

	public List getListAlertByDevice(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("DeviceOverview.getListAlertByDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get device detail
	 * @author Duy.Phan
	 * @since 2024-08-12
	 * @param id_site
	 */
	
	
	public Object getDeviceDetail(DeviceEntity obj) {
		Object dataObj = null;
		Map<String, Object> maps = new HashMap<>();
		try {
			maps =  (Map<String, Object>) queryForObject("DeviceOverview.getDeviceDetail", obj);
			if (maps == null)
				return new DeviceEntity();
			List dataList = queryForList("DeviceOverview.getListParamByDeviceGroup", obj);
			
			maps.put("dataParameters", dataList);
		} catch (Exception ex) {
			return new DeviceEntity();
		}
		return maps;
	}
	
	
	/**
	 * @description get getPortFolioAlertRanking
	 * @author Long.Pham
	 * @since 2024-08-12
	 * @param id_site
	 */
	
	
	public Object getPortFolioAlertRanking(PortfolioRankingEntity obj) {
		Map<String, Object> maps = new HashMap<>();
		try {
			maps =  (Map<String, Object>) queryForObject("DeviceOverview.getPortfolioRanking", obj);
			if (maps == null)
				return new PortfolioRankingEntity();
			List dataList = queryForList("DeviceOverview.getPortFolioAlertRanking", obj);
			
			maps.put("dataPortRanking", dataList);
		} catch (Exception ex) {
			return new PortfolioRankingEntity();
		}
		return maps;
	}
	
	
	
}
