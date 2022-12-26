/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ErrorLevelEntity;

public class ErrorLevelService extends DB {

	/**
	 * @description get error level
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getList(ErrorLevelEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("ErrorLevel.getList", obj);
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
	
	
	public List getListManage(ErrorLevelEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("ErrorLevel.getListManage", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecordManage(ErrorLevelEntity obj) {
		try {
			return (int)queryForObject("ErrorLevel.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean delete(ErrorLevelEntity obj) {
		try {
			return delete("ErrorLevel.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("ErrorLevel.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert error level
	 * @author long.pham
	 * @since 2021-02-26
	 */
	public ErrorLevelEntity insertErrorLevel(ErrorLevelEntity obj) 
	{
		try
	    {
	       Object insertId = insert("ErrorLevel.insertErrorLevel", obj);
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
	public boolean updateErrorLevel(ErrorLevelEntity obj){
		try{
			return update("ErrorLevel.updateErrorLevel", obj)>0;
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
	public boolean updateStatus(ErrorLevelEntity obj){
		try{
			return update("ErrorLevel.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("ErrorLevel.updateStatus", ex);
			return false;
		}
	}
	

}
