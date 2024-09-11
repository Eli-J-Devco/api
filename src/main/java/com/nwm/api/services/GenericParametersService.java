/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;


import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.GenericParameterEntity;

public class GenericParametersService extends DB {
	
	/**
	 * @description Get generic parameters list
	 * @author Hung.Bui
	 * @since 2024-09-06
	 * @param  empty
	 * @return array
	 */
	
	public List getList(GenericParameterEntity obj) {
		try {
			List dataList = queryForList("GenericParameters.getList", obj);
			return dataList == null ? new ArrayList() : dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description Get paginated generic parameters list
	 * @author Hung.Bui
	 * @since 2024-09-11
	 * @param { limit, offset }
	 * @return array
	 */
	
	public List getPaginatedList(GenericParameterEntity obj) {
		try {
			List dataList = queryForList("GenericParameters.getPaginatedList", obj);
			return dataList == null ? new ArrayList() : dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	public int getTotalRecord(GenericParameterEntity obj) {
		try {
			return (int)queryForObject("GenericParameters.getTotalRecord", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description insert
	 * @author Hung.Bui
	 * @since 2024-09-11
	 */
	public GenericParameterEntity insert(GenericParameterEntity obj) {
		try {
	       Integer insertId = (Integer) insert("GenericParameters.insert", obj);
    	   return (insertId != null && insertId > 0) ? obj : null;
	    } catch(Exception ex) {
	        log.error("insert", ex);
	        return null;
	    }	
	}
	
	/**
	 * @description update
	 * @author Hung.Bui
	 * @since 2024-09-11
	 * @param id
	 */
	public boolean update(GenericParameterEntity obj) {
		try{
			return update("GenericParameters.update", obj) > 0;
		} catch (Exception ex) {
			log.error("GenericParameters.update", ex);
			return false;
		}
	}
	
	/** @description delete
	 * @author Hung.Bui
	 * @since 2024-09-11
	 * @param id
	 */
	public boolean delete(GenericParameterEntity obj) {
		try {
			return delete("GenericParameters.delete", obj) > 0;
		} catch (Exception ex) {
			log.error("GenericParameters.delete", ex);
			return false;
		}
	}
}
