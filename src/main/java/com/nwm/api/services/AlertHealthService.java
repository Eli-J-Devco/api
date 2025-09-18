/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.AlertFilterEntity;
import com.nwm.api.entities.AlertHistoryEntity;
import com.nwm.api.entities.AlertNoteEntity;
import com.nwm.api.entities.ChartAlertDateEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Lib;

public class AlertHealthService extends DB {
	
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getList(AlertEntity obj) {
		try {
			List rs = queryForList("AlertHealth.getList", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getListAlertByDevice(AlertEntity obj) {
		try {
			List rs = queryForList("AlertHealth.getListAlertByDevice", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * @description count total alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 */

	public int getListTotalCount(AlertEntity obj) {
		try {
			AlertEntity totalRecord = (AlertEntity) queryForObject("AlertHealth.getTotal", obj);
			return totalRecord.getTotalRecord();
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description get detail alert
	 * @author long.pham
	 * @since 2021-03-18
	 * @param id_site
	 * @return Object
	 */

	public AlertEntity getAlertSummary(AlertEntity obj) {
		AlertEntity dataObj = new AlertEntity();
		try {
			dataObj = (AlertEntity) queryForObject("AlertHealth.getAlertSummary", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;

	}
	
	/**
	 * @description get list site group by id_employee, id_company
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 */
	

	public List getListSiteSubGroups(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AlertHealth.getListSiteSubGroups", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}

}
