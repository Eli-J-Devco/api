/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AuditingLogsEntity;

public class AuditingLogsService extends DB {
	/**
	 * @description get list auditing logs by employee
	 * @author Hung.Bui
	 * @since 2023-04-25
	 */

	public List getList(AuditingLogsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AuditingLogs.getListByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list auditing logs by site
	 * @author duy.phan
	 * @since 2023-05-10
	 */

	public List getListBySite(AuditingLogsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AuditingLogs.getListBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get latest records by employee
	 * @author Hung.Bui
	 * @since 2023-04-25
	 */
	
	public List getLatestRecordsByEmployee(AuditingLogsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AuditingLogs.getLatestRecordsByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get total record auditing logs
	 * @author Hung.Bui
	 * @since 2023-04-25
	 */
	public int getTotalRecord(AuditingLogsEntity obj) {
		try {
			return (int) queryForObject("AuditingLogs.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description get total record auditing logs by Site
	 * @author duy.phan
	 * @since 2023-05-10
	 */
	public int getTotalRecordBySite(AuditingLogsEntity obj) {
		try {
			return (int) queryForObject("AuditingLogs.getListCountBySite", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description insert auditing log
	 * @author Hung.Bui
	 * @since 2023-04-25
	 * @param id
	 */
	public AuditingLogsEntity insertAccountStatus(AuditingLogsEntity obj) {
		try {
			Object insertId = insert("AuditingLogs.insertAuditingLog", obj);
			if (insertId != null && insertId instanceof Integer) {
				return obj;
			} else {
				return null;
			}
		} catch (Exception ex) {
			log.error("AuditingLogs.insertAuditingLog", ex);
			return null;
		}
	}
	
	/** @description delete old records by employee
	 * @author Hung.Bui
	 * @since 2023-04-25
	 * @param id
	 */
	public boolean deleteOldRecordsByEmployee(AuditingLogsEntity obj) {
		try {
			return delete("AuditingLogs.deleteOldRecordsByEmployee", obj) > 0;
		} catch (Exception ex) {
			log.error("AuditingLogs.deleteOldRecordsByEmployee", ex);
			return false;
		}
	}
	
	/**
	 * @description get list auditing logs by employee
	 * @author duy.phan
	 * @since 2023-07-31
	 */

	public List getListAll(AuditingLogsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AuditingLogs.getListAll", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get total record auditing logs
	 * @author duy.phan
	 * @since 2023-07-31
	 */
	public int getTotalAllRecord(AuditingLogsEntity obj) {
		try {
			return (int) queryForObject("AuditingLogs.getListAllCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}

}
