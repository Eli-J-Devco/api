/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ScadaChartingEntity;
import com.nwm.api.entities.ScadaEmployeeChartFilterEntity;

public class ScadaChartingService extends DB {
	
	/**
	 * @description get site detail
	 * @author Hung.Bui
	 * @since 2024-05-03
	 * @param obj { hash_id_site }
	 * @return
	 */
	public ScadaChartingEntity getSiteDetail(ScadaChartingEntity obj) {
		try {
			return (ScadaChartingEntity) queryForObject("ScadaCharting.getSiteDetail", obj);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @description Get recently filter list
	 * @author Hung.Bui
	 * @since 2024-06-07
	 * @param obj { id_employee, hash_id_site }
	 * @return
	 */
	public List<ScadaEmployeeChartFilterEntity> getListFilter(ScadaEmployeeChartFilterEntity obj) {
		try {
			List<ScadaEmployeeChartFilterEntity> dataList = queryForList("ScadaCharting.getListFilter", obj);
			if (dataList == null) return new ArrayList<ScadaEmployeeChartFilterEntity>();
			return dataList;
		} catch (Exception ex) {
			return new ArrayList<ScadaEmployeeChartFilterEntity>();
		}
	}
	
	/**
	 * @description save filter
	 * @author Hung.Bui
	 * @since 2024-06-07
	 * @param obj { id_employee, hash_id_site, params, created_date, name, is_favorite }
	 * @return
	 */
	public ScadaEmployeeChartFilterEntity saveFilter(ScadaEmployeeChartFilterEntity obj) {
		try {
			Integer insertId = (Integer) insert("ScadaCharting.saveFilter", obj);
			if (insertId != null && insertId <= 0) return null;
			Integer total = (Integer) queryForObject("ScadaCharting.getFiltersCount", obj);
			if(total > 10) delete("ScadaCharting.deleteFilter", obj);
			
			return obj;
		} catch (Exception ex) {
			log.error("insert", ex);
			return null;
		}
	}
}
