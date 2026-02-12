/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SitesTrackersEntity;

public class SitesTrackersService extends DB {
	
	/**
	 * @description get list trackers
	 * @author duy.phan
	 * @since 2026-02-10
	 * @param id_site
	 */
	public List getListTracker(SitesTrackersEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("SitesTrackers.getListTrackers", obj);
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
		
	}
	
	/**
	 * @description get site detail
	 * @author long.pham
	 * @since 2026-02-10
	 * @param id_site
	 * @return Object
	 */

	public SitesTrackersEntity getDetail(SitesTrackersEntity obj) {
		SitesTrackersEntity dataObj = new SitesTrackersEntity();
		try {
			dataObj = (SitesTrackersEntity) queryForObject("SitesTrackers.getDetail", obj);
			
			if (dataObj == null)
				return new SitesTrackersEntity();
		} catch (Exception ex) {
			return new SitesTrackersEntity();
		}
		return dataObj;
	}
	
	/**
	 * @description update warning error threshold of a site
	 * @author duy.phan
	 * @since 2026-02-10
	 * @param id_site
	 */
	public boolean updateWarningErrorThreshold(SitesTrackersEntity obj){
		try{
			return update("SitesTrackers.updateWarningErrorThreshold", obj)>0;
		}catch (Exception ex) {
			log.error("SitesTrackers.updateWarningErrorThreshold", ex);
			return false;
		}
	}
}
