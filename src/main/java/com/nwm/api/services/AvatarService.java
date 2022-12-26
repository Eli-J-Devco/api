/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AvatarEntity;

public class AvatarService extends DB {

	/**
	 * @description get list icon
	 * @author long.pham
	 * @since 2021-05-17
	 * @returns array
	 */
	
	public List getListDropdown(AvatarEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Avatar.getListDropdown", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list icon
	 * @author 2021-05-17
	 * @since 2021-03-31
	 * @param {}
	 */

	public List getList(AvatarEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Avatar.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecord(AvatarEntity obj) {
		try {
			return (int)queryForObject("Avatar.getTotalRecord", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/** @description delete icon
	 * @author long.pham
	 * @since 2021-05-17
	 * @param id
	 */
	public boolean delete(AvatarEntity obj) {
		try {
			return delete("Avatar.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("Avatar.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert icon
	 * @author long.pham
	 * @since 2021-05-17
	 */
	public AvatarEntity insertAvatar(AvatarEntity obj) 
	{
		try
	    {
	       Object insertId = insert("Avatar.insertAvatar", obj);
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
	 * @since 2021-05-17
	 * @param id
	 */
	public boolean updateAvatar(AvatarEntity obj){
		try{
			return update("Avatar.updateAvatar", obj)>0;
		}catch (Exception ex) {
			log.error("Avatar.updateAvatar", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update icon status
	 * @author long.pham
	 * @since 2021-05-17
	 * @param id
	 */
	public boolean updateStatus(AvatarEntity obj){
		try{
			return update("Avatar.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("Avatar.updateStatus", ex);
			return false;
		}
	}

}
