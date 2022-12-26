/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AccountEntity;
import com.nwm.api.entities.ErrorEntity;

public class ErrorService extends DB {

	/**
	 * @description get error 
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getList(ErrorEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Error.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	public int getTotalRecord(ErrorEntity obj) {
		try {
			return (int)queryForObject("Error.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete error
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean delete(ErrorEntity obj) {
		try {
			return delete("Error.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("Error.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert error
	 * @author long.pham
	 * @since 2021-02-26
	 */
	public ErrorEntity insertError(ErrorEntity obj) 
	{
		try
	    {
	       Object insertId = insert("Error.insertError", obj);
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
	 * @description update error 
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean updateError(ErrorEntity obj){
		try{
			return update("Error.updateError", obj)>0;
		}catch (Exception ex) {
			log.error("Error.updateError", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update error level status
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean updateStatus(ErrorEntity obj){
		try{
			return update("Error.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("Error.updateStatus", ex);
			return false;
		}
	}
	
	
	

}
