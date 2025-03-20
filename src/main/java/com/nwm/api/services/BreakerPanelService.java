/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

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
	
	
	/** @description delete error level
	 * @author long.pham
	 * @since 2021-02-26
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
	 * @since 2021-02-26
	 */
	public BreakerPanelEntity insertBreakerPanel(BreakerPanelEntity obj) 
	{
		try
	    {
	       Object insertId = insert("BreakerPanel.insertBreakerPanel", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("insert", ex);
	        return null;
	    }	
	}
	
	
	/**
	 * @description update breaker
	 * @author long.pham
	 * @since 2025-03-20
	 * @param id
	 */
	public boolean updateBreakerPanel(BreakerPanelEntity obj){
		try{
			return update("BreakerPanel.updateBreakerPanel", obj)>0;
		}catch (Exception ex) {
			log.error("BreakerPanel.updateSite", ex);
			return false;
		}
	}
	
	

}
