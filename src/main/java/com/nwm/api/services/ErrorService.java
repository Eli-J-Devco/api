/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
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
		SqlSession session = this.beginTransaction();
		try {
			session.insert("Error.insertError", obj);
			int insertId = obj.getId();
			if (insertId == 0) return null;
			
			List recommendTools = obj.getRecommendTools();
			if(Objects.nonNull(recommendTools) && recommendTools.size() > 0 ) {
				session.insert("Error.insertErrorTools", obj);
			}
			
			List dataSteps = obj.getDataSteps();
			if(Objects.nonNull(dataSteps) && dataSteps.size() > 0 ) {
				session.insert("Error.insertErrorSteps", obj);
			}

			session.commit();
			return obj;
		} catch (Exception ex) {
			session.rollback();
			log.error("Error.insertError", ex);
			return null;
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * @description update error 
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean updateError(ErrorEntity obj){
		SqlSession session = this.beginTransaction();
		try {
			session.update("Error.updateError", obj);
			session.delete("Error.deleteErrorTools", obj);
			session.delete("Error.deleteErrorSteps", obj);
			
			List recommendTools = obj.getRecommendTools();
			if(Objects.nonNull(recommendTools) && recommendTools.size() > 0 ) {
				session.insert("Error.insertErrorTools", obj);
			}
			
			List dataSteps = obj.getDataSteps();
			if(Objects.nonNull(dataSteps) && dataSteps.size() > 0 ) {
				session.insert("Error.insertErrorSteps", obj);
			}

			session.commit();
			return true;
		} catch (Exception ex) {
			session.rollback();
			log.error("Error.updateError", ex);
			return false;
		} finally {
			session.close();
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
	
	/**
	 * @description get list device group
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getListDeviceGroup(ErrorEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Error.getListDeviceGroup", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description get list error message
	 * @author long.pham
	 * @since 2021-01-28
	 * @returns array
	 */
	
	public List getListErrorMessage(ErrorEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("Error.getListErrorMessage", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	/**
	 * @description update permission nw client
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 */
	public boolean updatePermissionNwClient(ErrorEntity obj){
		try{
			return update("Error.updatePermissionNwClient", obj)>0;
		}catch (Exception ex) {
			log.error("Error.updatePermissionNwClient", ex);
			return false;
		}
	}
	
	
	/**
	 * @description get error detail
	 * @author Long.Pham
	 * @since 2025-08-23
	 * @param id_error
	 */
	
	
	public ErrorEntity getErrorDetail(ErrorEntity obj) {
		ErrorEntity dataObj = null;
		try {
			 dataObj = (ErrorEntity) queryForObject("Error.getErrorDetail", obj);
			 
			 List recommendTools = queryForList("Error.getDataTecommendToolsByErrorId", obj);
			 dataObj.setRecommendTools(recommendTools);
			 List dataSteps = queryForList("Error.getDataStepsByErrorId", obj);
			 dataObj.setDataSteps(dataSteps);
			 
			if (dataObj == null)
				return new ErrorEntity();
		} catch (Exception ex) {
			return new ErrorEntity();
		}
		return dataObj;
	}

}
