/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.nwm.api.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelBaseEntity;
import com.nwm.api.entities.ModelEC350GasMeterEntity;
import com.nwm.api.entities.ModelGasMeterEntity;
import com.nwm.api.entities.ModelMeterIon8600Entity;
import com.nwm.api.entities.ModelMeterIon8600V1Entity;
import com.nwm.api.entities.ModelMeterIon8600V2Entity;
import com.nwm.api.entities.ModelMeterIon8600V3Entity;
import com.nwm.api.entities.ModelMeterIon8600V4Entity;
import com.nwm.api.entities.ModelSolarEdgeInverterEntity;
import com.nwm.api.entities.ModelSolarEdgeInverterV1Entity;
import com.nwm.api.entities.ModelWaterMeterKyPulseEntity;
import com.nwm.api.events.LowProductionAlertEvent;
import com.nwm.api.events.NoCommunicationAlertEvent;
import com.nwm.api.events.SolarTrackerNoMotionAlertEvent;
import com.nwm.api.events.WrongEneryAlertEvent;
import com.nwm.api.utils.Constants.DeviceType;
import com.nwm.api.utils.Constants.ModbusError;
import com.nwm.api.utils.Constants.UploadingDataIntervals;

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
	 * @description handle energy field
	 * @author Hung.Bui
	 * @since 2026-01-22
	 */
	public <T extends ModelBaseEntity> void handleEnergyField(DeviceEntity item, T currentEntity, String energyFieldSlug) {
		try {
			// get the write method of energy field
			Method writeMethod = null;
			if (Objects.nonNull(energyFieldSlug)) {
				PropertyDescriptor pd = new PropertyDescriptor(energyFieldSlug, currentEntity.getClass());
				writeMethod = pd.getWriteMethod();
			}
			
			boolean isCurrentInvalid = currentEntity.getError() > 0 || currentEntity.getNvmActiveEnergy() == 0.001 || currentEntity.getNvmActiveEnergy() < 0;
			
			// update current value if old device was replaced by the new one with the offset value
			if (currentEntity.getOffset_data_old() != 0 && !isCurrentInvalid) {
				Double offsetEnergy = currentEntity.getNvmActiveEnergy() + currentEntity.getOffset_data_old();
				currentEntity.setNvmActiveEnergy(offsetEnergy);
				if (Objects.nonNull(writeMethod)) writeMethod.invoke(currentEntity, offsetEnergy);
			}
			
			Map<String, Object> lastEntity = (Map<String, Object>) queryForObject("Device.getLastRow", currentEntity);
			if (Objects.isNull(lastEntity) || Objects.isNull(lastEntity.get("nvmActiveEnergy"))) return;
			Double lastEnergy = Double.parseDouble(lastEntity.get("nvmActiveEnergy").toString());
			String lastTime = lastEntity.get("time").toString();
			if (lastEnergy < 0) return;
			
			// update current value to be the last value if the current value is 0 / invalid
			if (currentEntity.getNvmActiveEnergy() == 0 /* || isCurrentInvalid */) {
				currentEntity.setNvmActiveEnergy(lastEnergy);
				if (Objects.nonNull(writeMethod)) writeMethod.invoke(currentEntity, lastEnergy);
			}
			
			// calculate the measured production
			double measuredProduction = isCurrentInvalid ? 0 : currentEntity.getNvmActiveEnergy() - lastEnergy;
			
			if (currentEntity.getClass().toString().equals(ModelMeterIon8600V3Entity.class.toString())) {
				measuredProduction = Double.MAX_VALUE;
				ModelMeterIon8600V3Entity castCurrentEntity = (ModelMeterIon8600V3Entity) currentEntity;
				
				if (Objects.nonNull(lastEntity.get("kWhDel")) && Double.parseDouble(lastEntity.get("kWhDel").toString()) > 0 && castCurrentEntity.getKWhDel() > 0 && castCurrentEntity.getKWhDel() != 0.001) {
					double newMeasuredProduction = castCurrentEntity.getKWhDel() - Double.parseDouble(lastEntity.get("kWhDel").toString());
					measuredProduction = newMeasuredProduction < measuredProduction ? newMeasuredProduction : measuredProduction;
				}
				
				if (Objects.nonNull(lastEntity.get("kWhDel_Rec")) && Double.parseDouble(lastEntity.get("kWhDel_Rec").toString()) > 0 && castCurrentEntity.getKWhDel_Rec() > 0 && castCurrentEntity.getKWhDel_Rec() != 0.001) {
					double newMeasuredProduction = castCurrentEntity.getKWhDel_Rec() - Double.parseDouble(lastEntity.get("kWhDel_Rec").toString());
					measuredProduction = newMeasuredProduction < measuredProduction ? newMeasuredProduction : measuredProduction;
				}

				if (Objects.nonNull(lastEntity.get("kWhDelRec")) && Double.parseDouble(lastEntity.get("kWhDelRec").toString()) > 0 && castCurrentEntity.getKWhDelRec() > 0 && castCurrentEntity.getKWhDelRec() != 0.001) {
					double newMeasuredProduction = castCurrentEntity.getKWhDelRec() - Double.parseDouble(lastEntity.get("kWhDelRec").toString());
					measuredProduction = newMeasuredProduction < measuredProduction ? newMeasuredProduction : measuredProduction;
				}
				
				if (measuredProduction == Double.MAX_VALUE) measuredProduction = 0;
			} else if (currentEntity.getClass().toString().equals(ModelMeterIon8600V4Entity.class.toString())) {
				measuredProduction = Double.MAX_VALUE;
				ModelMeterIon8600V4Entity castCurrentEntity = (ModelMeterIon8600V4Entity) currentEntity;
				
				if (Objects.nonNull(lastEntity.get("kWhRec")) && Double.parseDouble(lastEntity.get("kWhRec").toString()) > 0 && castCurrentEntity.getKWhDel() > 0 && castCurrentEntity.getKWhDel() != 0.001) {
					double newMeasuredProduction = castCurrentEntity.getKWhDel() - Double.parseDouble(lastEntity.get("kWhRec").toString());
					measuredProduction = newMeasuredProduction < measuredProduction ? newMeasuredProduction : measuredProduction;
				}
				
				if (Objects.nonNull(lastEntity.get("kWhDel_Rec")) && Double.parseDouble(lastEntity.get("kWhDel_Rec").toString()) > 0 && castCurrentEntity.getKWhDel_Rec() > 0 && castCurrentEntity.getKWhDel_Rec() != 0.001) {
					double newMeasuredProduction = castCurrentEntity.getKWhDel_Rec() - Double.parseDouble(lastEntity.get("kWhDel_Rec").toString());
					measuredProduction = newMeasuredProduction < measuredProduction ? newMeasuredProduction : measuredProduction;
				}

				if (Objects.nonNull(lastEntity.get("kWhDelRec")) && Double.parseDouble(lastEntity.get("kWhDelRec").toString()) > 0 && castCurrentEntity.getKWhDelRec() > 0 && castCurrentEntity.getKWhDelRec() != 0.001) {
					double newMeasuredProduction = castCurrentEntity.getKWhDelRec() - Double.parseDouble(lastEntity.get("kWhDelRec").toString());
					measuredProduction = newMeasuredProduction < measuredProduction ? newMeasuredProduction : measuredProduction;
				}
				
				if (measuredProduction == Double.MAX_VALUE) measuredProduction = 0;
			}
			
			if (measuredProduction > 3000 && (
				currentEntity.getClass().toString().equals(ModelMeterIon8600Entity.class.toString()) ||
				currentEntity.getClass().toString().equals(ModelMeterIon8600V1Entity.class.toString()) ||
				currentEntity.getClass().toString().equals(ModelMeterIon8600V2Entity.class.toString()) ||
				currentEntity.getClass().toString().equals(ModelMeterIon8600V3Entity.class.toString()) ||
				currentEntity.getClass().toString().equals(ModelMeterIon8600V4Entity.class.toString())
			)) {
				measuredProduction = currentEntity.getNvmActivePower() >= 0 ? currentEntity.getNvmActivePower() / 60 * UploadingDataIntervals.fromValue(item.getData_send_time()).getInterval() : 0;
			}
			
//			if (measuredProduction < 0) return;
			
			if (
				currentEntity.getClass().toString().equals(ModelEC350GasMeterEntity.class.toString()) ||
				currentEntity.getClass().toString().equals(ModelWaterMeterKyPulseEntity.class.toString()) ||
				currentEntity.getClass().toString().equals(ModelGasMeterEntity.class.toString())
			) {
				currentEntity.setNvmActivePower(measuredProduction);
			}
			
			Map<String, Object> measuredProductionEntity = new HashMap<>();
			measuredProductionEntity.put("datatablename", currentEntity.getDatatablename());
			measuredProductionEntity.put("time", lastTime);
			measuredProductionEntity.put("MeasuredProduction", measuredProduction);
			update("Device.updateMeasuredProduction", measuredProductionEntity);
		} catch (Exception ex) {
			log.error("UploadFiles.handleEnergyField", ex);
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
//					if (hours >= item.getStart_date_time() && hours <= item.getEnd_date_time()) applicationEventPublisher.publishEvent(new LowProductionAlertEvent(this, item, entity, dataDevice));
					applicationEventPublisher.publishEvent(new WrongEneryAlertEvent(this, item, entity));
					break;
					
				case PRODUCTION_METER:
				case LOAD_METER:
				case CONSUMTION_METER:
					if (item.isIs_excluded_meter()) break;
//					if (hours >= item.getStart_date_time() && hours <= item.getEnd_date_time()) applicationEventPublisher.publishEvent(new LowProductionAlertEvent(this, item, entity, dataDevice));
					applicationEventPublisher.publishEvent(new WrongEneryAlertEvent(this, item, entity));
					break;
			
				case SOLAR_TRACKER:
                case UPS:
                case WEATHER_STATION:
                case BREAKER:
//                    if (hours >= item.getStart_date_time() && hours <= item.getEnd_date_time()) {
//                        applicationEventPublisher.publishEvent(new NoCommunicationAlertEvent(this, item, entity));
//                    }
                    if (DeviceType.fromValue(item.getId_device_type()) == Constants.DeviceType.SOLAR_TRACKER) {
                        applicationEventPublisher.publishEvent(new SolarTrackerNoMotionAlertEvent(this, item, entity));
                    }
                    break;
                default:
					break;
			}
		} catch (Exception ex) {
			log.error("UploadFiles.customAlertChecking", ex);
		}
	}
}
