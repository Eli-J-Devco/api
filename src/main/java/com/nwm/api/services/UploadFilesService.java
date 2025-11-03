/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelBaseEntity;
import com.nwm.api.entities.ModelSolarEdgeInverterEntity;
import com.nwm.api.entities.ModelSolarEdgeInverterV1Entity;
import com.nwm.api.utils.Constants.ModbusError;

import net.objecthunter.exp4j.ExpressionBuilder;

public class UploadFilesService extends DB {

	/**
	 * @description scaling device parameters
	 * @author Hung.Bui
	 */
	public <T extends ModelBaseEntity> void scalingDeviceParameters(List<DeviceEntity> scaledDeviceParameters, T entity) {
		try {
			if (scaledDeviceParameters.size() > 0) {
				for (int j = 0; j < scaledDeviceParameters.size(); j++) {
					DeviceEntity scaledDeviceParameter = scaledDeviceParameters.get(j);
					if(scaledDeviceParameter.is_user_defined()) continue;
					String slug = scaledDeviceParameter.getParameter_slug();
					String scaleExpressions = scaledDeviceParameter.getParameter_scale();
					String variableName = scaledDeviceParameter.getVariable_name();
					PropertyDescriptor pd = new PropertyDescriptor(slug, entity.getClass());
					Double initialValue = (Double) pd.getReadMethod().invoke(entity);
					if (initialValue == 0.001) continue;
					Double scaledValue = new ExpressionBuilder(scaleExpressions).variable(variableName).build().setVariable(variableName, initialValue).evaluate();
					pd.getWriteMethod().invoke(entity, scaledValue);
					if (scaledDeviceParameter.is_active_power()) entity.setNvmActivePower(scaledValue);
					if (scaledDeviceParameter.is_energy()) {
						int scaleFactor = 1;
						if (entity.getClass().toString().equals(ModelSolarEdgeInverterEntity.class.toString()) || entity.getClass().toString().equals(ModelSolarEdgeInverterV1Entity.class.toString())) scaleFactor = 1000;
						entity.setNvmActiveEnergy(scaledValue/scaleFactor);
					}
					if (scaledDeviceParameter.is_irradiance()) entity.setNvm_irradiance(scaledValue);
					if (scaledDeviceParameter.is_temperature()) entity.setNvm_temperature(scaledValue);
					if (scaledDeviceParameter.is_panel_temperature()) entity.setNvm_panel_temperature(scaledValue);
				}
			}
		} catch (Exception ex) {
			log.error("UploadFiles.scalingDeviceParameters", ex);
		}
	}
	
	public void deletingFile(Path root, String fileName) {
		try {
			File logFile = new File(root.resolve(fileName).toString());
			if(logFile.delete()) {}
			
			File logGzFile = new File(root.resolve(fileName.concat(".gz")).toString());
			
			if(logGzFile.delete()) {}
		} catch(Exception ex) {
			log.error("UploadFiles.deletingFile", ex);
		}
	}
	
	/**
	 * @description device last updated
	 * @author Hung.Bui
	 * @since 2025-10-31
	 */
	public void deviceLastUpdated(DeviceEntity item, ModelBaseEntity entity) {
		try {
			DeviceService service = new DeviceService();
			item.setLast_updated(ModbusError.fromValue(entity.getError()) == ModbusError.DEVICE_FAILED_TO_RESPOND ? null : entity.getTime());
			service.updateLastUpdated(item);
		} catch (Exception e) {
		}
	}
	
	/**
	 * @description check wrong energy alert
	 * @author Hung.Bui
	 * @since 2024-12-03
	 */
	public void checkWrongEnergy(DeviceEntity item, ModelBaseEntity entity) {
		try {
			if (!(item.getId_device_type() == 1 || ((item.getId_device_type() == 3 || item.getId_device_type() == 7 || item.getId_device_type() == 9) && !item.isIs_excluded_meter()))) return;
			AlertEntity alertItem = new AlertEntity();
			alertItem.setId_device(item.getId());
			alertItem.setId_device_group(item.getId_device_group());
			alertItem.setError_code("1003");
			
			Integer errorId = (Integer) queryForObject("Device.getErrorId", alertItem);
			if (errorId == null) return;
			alertItem.setId_error(errorId);
			
			if (entity.getMeasuredProduction() < -10 || entity.getMeasuredProduction() > 500) {
				boolean isAlertExist = (int) queryForObject("BatchJob.checkAlertlExist", alertItem) > 0;
				if (isAlertExist) return;
				alertItem.setStart_date(entity.getTime());
				insert("BatchJob.insertAlert", alertItem);
			} else {
				// Close alert
				AlertEntity alertObj = (AlertEntity) queryForObject("BatchJob.getAlertDetail", alertItem);
				if (alertObj == null || alertObj.getId() == 0) return; 
				alertItem.setEnd_date(entity.getTime());
				alertItem.setId(alertObj.getId());
				update("BatchJob.updateCloseAlert", alertItem);
			}
		} catch (Exception ex) {
			log.error("UploadFiles.checkWrongEnergy", ex);
		}
	}
	
}
