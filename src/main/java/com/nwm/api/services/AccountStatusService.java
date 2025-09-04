/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AccountStatusEntity;

public class AccountStatusService extends DB {
	/**
	 * @description get list account status by employee
	 * @author Hung.Bui
	 * @since 2023-03-24
	 */

	public List getList(AccountStatusEntity obj) {
		try {
			List dataList = queryForList("AccountStatus.getListByEmployee", obj);
			return dataList == null ? new ArrayList<>() : dataList;
		} catch (Exception ex) {
			return new ArrayList<>();
		}
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
		SqlSession session = this.beginTransaction();
		try {
			AccountStatusEntity latest = session.selectOne("AccountStatus.getLatestRecordsByEmployee", obj);
			int row = latest != null && obj.getPage_login().equals(latest.getPage_login()) ? session.update("AccountStatus.update", latest) : session.insert("AccountStatus.insert", obj);
			session.commit();
			return row > 0 ? obj : null;
		} catch (Exception ex) {
			session.rollback();
			log.error("AccountStatus.insert", ex);
			return null;
		} finally {
			session.close();
		}
	}

}
