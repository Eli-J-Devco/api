/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AuditLog;
import com.nwm.api.entities.AuditingLogsEntity;
import com.nwm.api.entities.LogBase;
import com.nwm.api.entities.LogDifference;
import com.nwm.api.entities.LogOperationEnum;

public class AuditingLogsService extends DB {
	
	public <T extends LogBase> List<AuditLog> getLogDifferences(List<T> logs, List<String> excludedFields) {
		try {
			List<AuditLog> auditLogList = new ArrayList<>();
			
			for (int i = 0; i < logs.size(); i++) {
				T log = logs.get(i);
				
				switch (LogOperationEnum.fromValue(log.getOperation())) {
					case INSERT:
						auditLogList.add(new AuditLog(log.getModified_date(), log.getModified_by(), LogOperationEnum.INSERT, new ArrayList<>()));
						continue;
						
					case DELETE:
						auditLogList.add(new AuditLog(log.getModified_date(), log.getModified_by(), LogOperationEnum.DELETE, new ArrayList<>()));
						continue;
						
					case UPDATE:
						if (i + 1 >= logs.size()) continue;
						T prevLog = logs.get(i + 1);
						if (prevLog.getOperation().equals(LogOperationEnum.DELETE.getValue())) continue;
						List<LogDifference> logDifferences = new ArrayList<>();
						
						for (Field field: log.getClass().getDeclaredFields()) {
							if (Objects.nonNull(excludedFields) && excludedFields.contains(field.getName())) continue;
							field.setAccessible(true);
							Object newValue = field.get(log);
							Object oldValue = field.get(prevLog);
							if (Objects.equals(oldValue, newValue)) continue;
							logDifferences.add(new LogDifference(field.getName(), oldValue, newValue));
						}
						
						if (logDifferences.size() > 0) auditLogList.add(new AuditLog(log.getModified_date(), log.getModified_by(), LogOperationEnum.UPDATE, logDifferences));
						continue;
	
					default:
						break;
				}
			}
			
			return auditLogList;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
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
