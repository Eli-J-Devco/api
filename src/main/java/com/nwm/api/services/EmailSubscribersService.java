/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.EmailSubscribersEntity;

public class EmailSubscribersService extends DB {

	/**
	 * @description get list email subscribers
	 * @author long.pham
	 * @since 2021-05-17
	 * @returns array
	 */
	
	public List getListDropdown(EmailSubscribersEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmailSubscribers.getListDropdown", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list email subscribers
	 * @author 2021-05-17
	 * @since 2021-03-31
	 * @param {}
	 */

	public List getList(EmailSubscribersEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("EmailSubscribers.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecord(EmailSubscribersEntity obj) {
		try {
			return (int)queryForObject("EmailSubscribers.getTotalRecord", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/** @description delete email subscribers
	 * @author long.pham
	 * @since 2021-05-17
	 * @param id
	 */
	public boolean delete(EmailSubscribersEntity obj) {
		try {
			return delete("EmailSubscribers.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("EmailSubscribers.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert email subscribers
	 * @author long.pham
	 * @since 2021-05-17
	 */
	public EmailSubscribersEntity insertEmailSubscribers(EmailSubscribersEntity obj) 
	{
		try
	    {
	       Object insertId = insert("EmailSubscribers.insertEmailSubscribers", obj);
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
	 * @description update email subscribers
	 * @author long.pham
	 * @since 2021-05-17
	 * @param id
	 */
	public boolean updateEmailSubscribers(EmailSubscribersEntity obj){
		try{
			return update("EmailSubscribers.updateEmailSubscribers", obj)>0;
		}catch (Exception ex) {
			log.error("EmailSubscribers.updateEmailSubscribers", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update email subscribers status
	 * @author long.pham
	 * @since 2021-05-17
	 * @param id
	 */
	public boolean updateStatus(EmailSubscribersEntity obj){
		try{
			return update("EmailSubscribers.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("EmailSubscribers.updateStatus", ex);
			return false;
		}
	}

}
