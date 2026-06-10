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
import com.nwm.api.entities.CategorizeDataEntity;

public class CategorizeDataService extends DB {

	/**
	 * @description Get list categorize data
	 * @author Hung.Bui
	 * @since 2023-11-14
	 * @returns array
	 */
	
	public List getList(CategorizeDataEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("CategorizeData.getList", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	public int getTotalRecord(CategorizeDataEntity obj) {
		try {
			return (int)queryForObject("CategorizeData.getListCount", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/** @description delete categorize data
	 * @author Hung.Bui
	 * @since 2023-11-14
	 * @param id
	 */
	public boolean delete(CategorizeDataEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			if (obj.getId() == 1) return false;
			session.update("CategorizeData.delete", obj);
			session.update("CategorizeData.resetDeviceParameterToDefaultCategory", obj);
			session.delete("CategorizeData.deleteDeviceTemplateMappingByCategorizeData", obj);
			session.commit();
			return true;
		} catch (Exception ex) {
			log.error("CategorizeData.delete", ex);
			session.rollback();
			return false;
		} finally {
			session.close();
		}
	}
	
	/**
	 * @description insert categorize data
	 * @author Hung.Bui
	 * @since 2023-11-14
	 */
	public CategorizeDataEntity insert(CategorizeDataEntity obj) 
	{
		try
	    {
	       Object insertId = insert("CategorizeData.insert", obj);
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
	 * @description update categorize data
	 * @author Hung.Bui
	 * @since 2023-11-14
	 * @param id
	 */
	public boolean update(CategorizeDataEntity obj){
		try{
			return update("CategorizeData.update", obj)>0;
		}catch (Exception ex) {
			log.error("CategorizeData.update", ex);
			return false;
		}
	}

}
