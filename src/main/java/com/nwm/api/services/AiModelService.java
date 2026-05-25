/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteAiModelEntity;
import com.nwm.api.entities.SiteEntity;


public class AiModelService extends DB {

	
	public static String[] push(String[] array, String element) {
        int originalLength = array.length;
        String[] newArray = new String[originalLength + 1];
        System.arraycopy(array, 0, newArray, 0, originalLength);
        newArray[originalLength] = element;
        return newArray;
    }
	
	
	
	/**
	 * @description get all device by site
	 * @author long.pham
	 * @since 2025-06-21
	 * @param id_site
	 */

	public List getListDevices(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AiModel.buildDataBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	public List getParameterByDeviceGroup(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AiModel.getParameterByDeviceGroup", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	public List getDataByDevice(DeviceEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AiModel.getDataByDevice", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	/**
	 * @description insert site ai model
	 * @author long.pham
	 * @since 2025-06-19
	 */
	public SiteAiModelEntity insertSiteAiModel(SiteAiModelEntity obj) 
	{
		try
	    {
	       Object insertId = insert("AiModel.insertSiteAiModel", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("insertSiteAiModel", ex);
	        return null;
	    }	
	}
	
	

	
	public DeviceEntity updateDataDeviceAiGeneration(DeviceEntity obj) 
	{
		try
	    {
	       Object insertId = insert("AiModel.updateDataDeviceAiGeneration", obj);
	       if(insertId != null && insertId instanceof Integer) {
	    	   return obj;
	       }else {
	    	   return null;
	       }
	    }
	    catch(Exception ex)
	    {
	        log.error("updateDataDeviceAiGeneration", ex);
	        return null;
	    }	
	}
	
	
	/**
	 * @description get list model AI
	 * @author long.pham
	 * @since 2025-06-20
	 */
	
	public List getListModelAiBySite(SiteAiModelEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("AiModel.getListModelAiBySite", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	/**
	 * @description get AI model detail
	 * @author Long.Pham
	 * @since 2025-06-21
	 * @param id_site, id_model
	 */
	
	
	public SiteAiModelEntity getAiModelDetail(SiteAiModelEntity obj) {
		SiteAiModelEntity dataObj = null;
		try {
			 dataObj = (SiteAiModelEntity) queryForObject("AiModel.getAiModelDetail", obj);
			if (dataObj == null)
				return new SiteAiModelEntity();
		} catch (Exception ex) {
			return new SiteAiModelEntity();
		}
		return dataObj;
	}
	
}
