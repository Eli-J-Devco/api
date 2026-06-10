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
import com.nwm.api.entities.BreakerPanelEntity;

public class BreakerPanelService extends DB {

	/**
	 * @description get error level
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getList(BreakerPanelEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BreakerPanel.getListManage", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list site by id customer
	 * @author long.pham
	 * @since 2021-02-25
	 * @param {}
	 */
	
	
	public List getListManage(BreakerPanelEntity obj) {
		List dataList = new ArrayList();
		try {
			List data = queryForList("BreakerPanel.getListManage", obj);
			if (data == null)
				return new ArrayList();
			for(int i =0; i < data.size(); i ++) {
				BreakerPanelEntity item = (BreakerPanelEntity) data.get(i);
				List dataListBreakMap = queryForList("BreakerPanel.getListBreakerPanelMap", item);
				item.setListDataMaps(dataListBreakMap);
				dataList.add(item);
			}
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecordManage(BreakerPanelEntity obj) {
		try {
			return (int)queryForObject("BreakerPanel.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete breaker
	 * @author long.pham
	 * @since 2025-03-24
	 * @param id
	 */
	public boolean delete(BreakerPanelEntity obj) {
		try {
			return delete("BreakerPanel.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("BreakerPanel.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert breaker
	 * @author long.pham
	 * @since 2025-02-24
	 */
	public BreakerPanelEntity insertBreakerPanel(BreakerPanelEntity obj) 
	{
		SqlSession session = this.beginTransaction();
		try {
			session.insert("BreakerPanel.insertBreakerPanel", obj);
			if (obj.getId() > 0) {
				session.insert("BreakerPanel.insertBreakerPanelMap", obj);
			} else {
				return null;
			}

			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Site.insertSite", ex);
			return null;
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * @description update breaker
	 * @author long.pham
	 * @since 2025-03-24
	 * @param id
	 */
	public boolean updateBreakerPanel(BreakerPanelEntity obj){
		SqlSession session = this.beginTransaction();
		try {
			
			session.update("BreakerPanel.updateBreakerPanel", obj);
			session.delete("BreakerPanel.deleteBreakerPanelMap", obj);
			session.insert("BreakerPanel.insertBreakerPanelMap", obj);
			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("BreakerPanel.updateBreakerPanel", ex);
			return false;
		} finally {
			session.close();
		}
		
	}
	
	

}
