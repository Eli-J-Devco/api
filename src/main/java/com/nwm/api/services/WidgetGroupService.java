/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.WidgetGroupEntity;

public class WidgetGroupService extends DB {

	/**
	 * @description get error level
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getList(WidgetGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("WidgetGroup.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description get error level
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getListRoot(WidgetGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("WidgetGroup.getListRoot", obj);
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
	
	
	public List getListManage(WidgetGroupEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("WidgetGroup.getListManage", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecordManage(WidgetGroupEntity obj) {
		try {
			return (int)queryForObject("WidgetGroup.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean delete(WidgetGroupEntity obj) {
		try {
			return delete("WidgetGroup.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("WidgetGroup.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert error level
	 * @author long.pham
	 * @since 2021-02-26
	 */
	public WidgetGroupEntity insertWidgetGroup(WidgetGroupEntity obj) 
	{
		try
	    {
	       Object insertId = insert("WidgetGroup.insertWidgetGroup", obj);
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
	 * @description update error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean updateWidgetGroup(WidgetGroupEntity obj){
		try{
			return update("WidgetGroup.updateWidgetGroup", obj)>0;
		}catch (Exception ex) {
			log.error("Site.updateSite", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update error level status
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean updateStatus(WidgetGroupEntity obj){
		try{
			return update("WidgetGroup.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("WidgetGroup.updateStatus", ex);
			return false;
		}
	}
	

}
