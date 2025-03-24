/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services.building;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.building.HVACMappingPointEntity;
import com.nwm.api.entities.building.SitesOverviewHVACLayoutMapEntity;

public class SitesOverviewHVACService extends DB {
	
	/**
	 * @description Save mapping points
	 * @author Hung.Bui
	 * @since 2025-03-22
	 */
	public boolean saveMappingPoints(SitesOverviewHVACLayoutMapEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			session.delete("SitesOverviewHVAC.deleteMappingPoints", obj);
			session.insert("SitesOverviewHVAC.insertMappingPoints", obj);
			session.commit();
			return true;
	    } catch(Exception ex) {
	    	session.rollback();
	        log.error("SitesOverviewHVAC.saveMappingPoints", ex);
	        return false;
	    } finally {
			session.close();
		}
	}
	
	/**
	 * @description Get gas meters
	 * @author Hung.Bui
	 * @since 2025-03-22
	 * @param SitesOverviewHVACLayoutMapEntity
	 * @return List<HVACMappingPointEntity>
	 */
	public List<HVACMappingPointEntity> getMappingPoints(SitesOverviewHVACLayoutMapEntity obj) {
		try {
			List<HVACMappingPointEntity> dataList = queryForList("SitesOverviewHVAC.getMappingPoints", obj);
			if (dataList != null && dataList.size() > 0) return dataList;
		} catch (Exception ex) {
			log.error("SitesOverviewHVAC.getMappingPoints", ex);
		}
		return new ArrayList<HVACMappingPointEntity>();
	}
	
}
