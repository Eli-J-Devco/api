/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AccountStatusEntity;

public class AccountStatusService extends DB {
	/**
	 * @description get list account status by employee
	 * @author Hung.Bui
	 * @since 2023-03-24
	 */

	public List getList(AccountStatusEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AccountStatus.getListByEmployee", obj);
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
	 * @since 2023-03-24
	 */
	
	public List getLatestRecordsByEmployee(AccountStatusEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AccountStatus.getLatestRecordsByEmployee", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get total record account status
	 * @author Hung.Bui
	 * @since 2023-03-24
	 */
	public int getTotalRecord(AccountStatusEntity obj) {
		try {
			return (int) queryForObject("AccountStatus.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description insert account status
	 * @author Hung.Bui
	 * @since 2023-03-24
	 * @param id
	 */
	public AccountStatusEntity insertAccountStatus(AccountStatusEntity obj) {
		try {
			Object insertId = insert("AccountStatus.insertAccountStatus", obj);
			if (insertId != null && insertId instanceof Integer) {
				return obj;
			} else {
				return null;
			}
		} catch (Exception ex) {
			log.error("AccountStatus.insertAccountStatus", ex);
			return null;
		}
	}
	
	/** @description delete old records by employee
	 * @author Hung.Bui
	 * @since 2023-03-24
	 * @param id
	 */
	public boolean deleteOldRecordsByEmployee(AccountStatusEntity obj) {
		try {
			return delete("AccountStatus.deleteOldRecordsByEmployee", obj) > 0;
		} catch (Exception ex) {
			log.error("AccountStatus.deleteOldRecordsByEmployee", ex);
			return false;
		}
	}

}
