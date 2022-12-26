/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.IconsEntity;

public class IconsService extends DB {

	/**
	 * @description get list icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @returns array
	 */
	
	public List getListDropdown(IconsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Icons.getListDropdown", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param {}
	 */

	public List getList(IconsEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Icons.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecord(IconsEntity obj) {
		try {
			return (int)queryForObject("Icons.getTotalRecord", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/** @description delete icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 */
	public boolean delete(IconsEntity obj) {
		try {
			return delete("Icons.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("Icons.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert icon
	 * @author long.pham
	 * @since 2021-03-31
	 */
	public IconsEntity insertIcon(IconsEntity obj) 
	{
		try
	    {
	       Object insertId = insert("Icons.insertIcon", obj);
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
	 * @description update icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 */
	public boolean updateIcon(IconsEntity obj){
		try{
			return update("Icons.updateIcon", obj)>0;
		}catch (Exception ex) {
			log.error("Icons.updateIcon", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update icon status
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 */
	public boolean updateStatus(IconsEntity obj){
		try{
			return update("Icons.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("Icons.updateStatus", ex);
			return false;
		}
	}

}
