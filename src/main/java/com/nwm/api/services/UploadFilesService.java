/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelBaseEntity;
import com.nwm.api.entities.ModelSolarEdgeInverterEntity;
import com.nwm.api.entities.ModelSolarEdgeInverterV1Entity;
import com.nwm.api.events.LowProductionAlertEvent;
import com.nwm.api.events.SolarTrackerNoMotionAlertEvent;
import com.nwm.api.events.WrongEneryAlertEvent;
import com.nwm.api.utils.Constants.DeviceType;
import com.nwm.api.utils.Constants.ModbusError;

import net.objecthunter.exp4j.ExpressionBuilder;

@Service
public class UploadFilesService extends DB {
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

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
//						if (entity.getClass().toString().equals(ModelSolarEdgeInverterEntity.class.toString()) || entity.getClass().toString().equals(ModelSolarEdgeInverterV1Entity.class.toString())) scaleFactor = 1000;
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
	 * @description custom alert checking
	 * @author Hung.Bui
	 * @since 2026-01-08
	 */
	public void customAlertChecking(DeviceEntity item, ModelBaseEntity entity, List<DeviceEntity> dataDevice) {
		try {
			ZoneId zoneId = ZoneId.of(item.getTimezone_value());
	        ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
	        int hours = zdtNow.getHour();
			
			switch (DeviceType.fromValue(item.getId_device_type())) {
				case PV_SYSTEM_INVERTER:
					if (hours >= item.getStart_date_time() && hours <= item.getEnd_date_time()) applicationEventPublisher.publishEvent(new LowProductionAlertEvent(this, item, entity, dataDevice));
					applicationEventPublisher.publishEvent(new WrongEneryAlertEvent(this, item, entity));
					break;
					
				case PRODUCTION_METER:
				case LOAD_METER:
				case CONSUMTION_METER:
					if (item.isIs_excluded_meter()) break;
					if (hours >= item.getStart_date_time() && hours <= item.getEnd_date_time()) applicationEventPublisher.publishEvent(new LowProductionAlertEvent(this, item, entity, dataDevice));
					applicationEventPublisher.publishEvent(new WrongEneryAlertEvent(this, item, entity));
					break;
			
				case SOLAR_TRACKER:
					applicationEventPublisher.publishEvent(new SolarTrackerNoMotionAlertEvent(this, item, entity));
					break;
	
				default:
					break;
			}
		} catch (Exception ex) {
			log.error("UploadFiles.customAlertChecking", ex);
		}
	}
}
