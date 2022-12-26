/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CompanyEntity;

public class CompanyService extends DB {

	/**
	 * @description get list dropdown company
	 * @author long.pham
	 * @since 2022-03-10
	 * @returns array
	 */
	
	public List getDropdownList(CompanyEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Company.getDropdownList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list company 
	 * @author long.pham
	 * @since 2022-03-10
	 * @param {}
	 */
	
	
	public List getList(CompanyEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Company.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecord(CompanyEntity obj) {
		try {
			return (int)queryForObject("Company.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	
	/** @description delete company
	 * @author long.pham
	 * @since 2022-03-10
	 * @param id
	 */
	public boolean delete(CompanyEntity obj) {
		try {
			return delete("Company.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("Company.delete", ex);
			return false;
		}
	}
	
	
	
	/**
	 * @description insert company
	 * @author long.pham
	 * @since 2022-03-10
	 */
	public CompanyEntity insertCompany(CompanyEntity obj) 
	{
		try
	    {
	       Object insertId = insert("Company.insertCompany", obj);
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
	 * @description update company
	 * @author long.pham
	 * @since 2022-03-10
	 * @param id
	 */
	public boolean updateCompany(CompanyEntity obj){
		try{
			return update("Company.updateCompany", obj)>0;
		}catch (Exception ex) {
			log.error("Company.updateCompany", ex);
			return false;
		}
	}
	
	
	/**
	 * @description update company status
	 * @author long.pham
	 * @since 2022-03-10
	 * @param id
	 */
	public boolean updateStatus(CompanyEntity obj){
		try{
			return update("Company.updateStatus", obj)>0;
		}catch (Exception ex) {
			log.error("Company.updateStatus", ex);
			return false;
		}
	}
	

}
