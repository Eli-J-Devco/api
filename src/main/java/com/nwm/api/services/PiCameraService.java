/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/

package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelPiCameraEntity;


public class PiCameraService extends DB {
	
	
	
	
	
	/**
	 * @description get data pi camera
	 * @author long.pham
	 * @since 2025-01-13
	 */
	
	public DeviceEntity getDeviceByModbusAndSerNumber(DeviceEntity obj) {
		try {
			DeviceEntity dataObj = (DeviceEntity) queryForObject("PiCamera.getDeviceByModbusAndSerNumber", obj);
			if (dataObj == null)
				return new DeviceEntity();
			return dataObj;
		} catch (Exception ex) {
			return new DeviceEntity();
		}

	}
	
	
	/**
	 * @description insert data from datalogger to model_pi_camera_water_meter
	 * @author long.pham
	 * @since 2025-01-13
	 * @param data from datalogger
	 */
	
	public boolean insertModelPiCamraWaterMeter(ModelPiCameraEntity obj) {
		try {
			ModelPiCameraEntity dataObj = (ModelPiCameraEntity) queryForObject("PiCamera.getLastRow", obj);
			double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && obj.getReading_value()> 0 && obj.getReading_value() != 0.001 ) {
				 measuredProduction = obj.getReading_value() - dataObj.getReading_value();
			 }
			 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
				 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 obj.setReading_value(dataObj.getNvmActiveEnergy());
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 obj.setNvmActiveEnergy(obj.getReading_value());
			 Object insertId = insert("PiCamera.insertModelPiCamraWaterMeter", obj);
		        if(insertId == null ) {
		        	return false;
		        }
		        return true;
		} catch (Exception ex) {
			log.error("insert", ex);

			return false;
		}

	}

}
