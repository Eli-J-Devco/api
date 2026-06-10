/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import org.apache.ibatis.session.SqlSession;
import org.springframework.batch.core.DefaultJobKeyGenerator;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceParameterScaleOldDataEntity;

public class DeviceParameterScaleOldDataService extends DB {
	
	/**
	 * @description update device parameter
	 * @author Hung.Bui
	 * @since 2023-06-26
	 * @param id
	 */
	public boolean updateDeviceParameterOldData(DeviceParameterScaleOldDataEntity obj){
		SqlSession session = this.beginTransaction();
		try{
			Object insertId = session.insert("DeviceParameterScaleOldData.insertDeviceParameterScaleOldData", obj);
			
			if (insertId != null && insertId instanceof Integer) {
				Object total = queryForObject("DeviceParameterScaleOldData.getListCount", obj);
				if((int)total > 20) {
					// Delete one row
					session.delete("DeviceParameterScaleOldData.deleteDeviceParameterScaleOldDataByIdEmployee", obj);
				}
			}
			
			if (obj.isIs_active_power()) {
				obj.setScale(obj.getScale().replace("value", obj.getSlug()));
				session.update("DeviceParameterScaleOldData.updateScaleOldData", obj);
				obj.setScale(obj.getScale().replace(obj.getSlug(), "nvmActivePower"));
				obj.setSlug("nvmActivePower");
				session.update("DeviceParameterScaleOldData.updateScaleOldData", obj);				
			} else if (obj.isIs_energy()) {
				if (obj.getSlug().equals("Energy")) {
					obj.setScale(obj.getScale().replace("value", "nvmActiveEnergy"));
					obj.setSlug("nvmActiveEnergy");
					session.update("DeviceParameterScaleOldData.updateScaleOldData", obj);
				} else {
					obj.setScale(obj.getScale().replace("value", obj.getSlug()));
					session.update("DeviceParameterScaleOldData.updateScaleOldData", obj);
					obj.setScale(obj.getScale().replace(obj.getSlug(), "nvmActiveEnergy"));
					obj.setSlug("nvmActiveEnergy");
					session.update("DeviceParameterScaleOldData.updateScaleOldData", obj);
				}		
			} else if (obj.isIs_irradiance()) {
				obj.setScale(obj.getScale().replace("value", obj.getSlug()));
				session.update("DeviceParameterScaleOldData.updateScaleOldData", obj);
				obj.setScale(obj.getScale().replace(obj.getSlug(), "nvm_irradiance"));
				obj.setSlug("nvm_irradiance");
				session.update("DeviceParameterScaleOldData.updateScaleOldData", obj);
			} else {
				obj.setScale(obj.getScale().replace("value", obj.getSlug()));
				session.update("DeviceParameterScaleOldData.updateScaleOldData", obj);
			}
			
			session.commit();
			return true;
		}catch (Exception ex) {
			session.rollback();
			log.error("DeviceParameter.updateDeviceParameter", ex);
			return false;
		}finally {
			session.close();
		}
	}
}
