/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

// import java.time.ZoneId;
// import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.constraints.Null;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelHuaweiSun200028ktlEntity;
import com.nwm.api.utils.Lib;

/**
 * @description Service class for ModelHuaweiSun200028ktl - Version 2
 * @author Duc.pham
 * @since 2025-11-24
 */
public class ModelHuaweiSun200028ktlService_v2 extends DB {

    /**
     * @description set data ModelHuaweiSun200028ktl
     * @author Duc.pham
     * @since 2022-12-20
     * @param line - CSV line data
     * @param deviceId - device ID (optional, use 0 if not needed)
     * @param datatablename - table name for data (optional, use null if not needed)
     * @return ModelHuaweiSun200028ktlEntity
     */

	public ModelHuaweiSun200028ktlEntity setModelHuaweiSun200028ktl(String line, DeviceEntity item) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
		if (words.size() > 0) {
				ModelHuaweiSun200028ktlEntity dataModel = new ModelHuaweiSun200028ktlEntity();
				dataModel.setId_device(item.getId());
				dataModel.setDatatablename(item.getDatatablename());
				dataModel.setView_tablename(item.getDatatablename());
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(Lib.isBlank(words.get(1)) ? "0" : words.get(1)));
				dataModel.setLow_alarm(Integer.parseInt(Lib.isBlank(words.get(2)) ? "0" : words.get(2)));
				dataModel.setHigh_alarm(Integer.parseInt(Lib.isBlank(words.get(3)) ? "0" : words.get(3)));
								Double power = Double.parseDouble(Lib.isBlank(words.get(4)) ? "0" : words.get(4));
								dataModel.setActivePower(power);
								dataModel.setReactivePower(Double.parseDouble(Lib.isBlank(words.get(5)) ? "0" : words.get(5)));
								dataModel.setTotalDCInputCurrent(Double.parseDouble(Lib.isBlank(words.get(6)) ? "0" : words.get(6)));
								dataModel.setTotalInputPower(Double.parseDouble(Lib.isBlank(words.get(7)) ? "0" : words.get(7)));
								dataModel.setInsulationResistance(Double.parseDouble(Lib.isBlank(words.get(8)) ? "0" : words.get(8)));
								dataModel.setPowerFactor(Double.parseDouble(Lib.isBlank(words.get(9)) ? "0" : words.get(9)));
								dataModel.setInverterStatus(Double.parseDouble(Lib.isBlank(words.get(10)) ? "0" : words.get(10)));
								dataModel.setCabinetTemperature(Double.parseDouble(Lib.isBlank(words.get(11)) ? "0" : words.get(11)));

								dataModel.setMajorFaultCode(Double.parseDouble(Lib.isBlank(words.get(12)) ? "0" : words.get(12)));
								dataModel.setMinorFaultCode(Double.parseDouble(Lib.isBlank(words.get(13)) ? "0" : words.get(13)));
								dataModel.setWarningCode(Double.parseDouble(Lib.isBlank(words.get(14)) ? "0" : words.get(14)));

								// set custom field nvmActivePower and nvmActiveEnergy
								dataModel.setNvmActivePower(power);
								dataModel.setNvmActiveEnergy(0);
								dataModel.setMeasuredProduction(0);
								return dataModel;


			} else {
				return new ModelHuaweiSun200028ktlEntity();
			}

		} catch (Exception ex) {
			log.error("setModelHuaweiSun200028ktl", ex);
			return new ModelHuaweiSun200028ktlEntity();
		}
	}
	/**
	 * @description insert data from datalogger to model_huawei_sun_2000_28ktl (batch insert)
	 * @author Duc.pham
	 * @since 2020-10-07
	 * @param entityList list of entities to insert
	 * @return true if insert successful, false otherwise
	 */
	public boolean insertModelHuaweiSun200028ktl_v2(List<ModelHuaweiSun200028ktlEntity> entityList) {
		try {
			boolean allSuccess = true;
			for (ModelHuaweiSun200028ktlEntity entity : entityList) {
				// ModelHuaweiSun200028ktlEntity dataObj = (ModelHuaweiSun200028ktlEntity) queryForObject("ModelHuaweiSun200028ktl.getLastRow", entity);
				// double measuredProduction = 0;
				// if (dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && entity.getNvmActiveEnergy() > 0 && entity.getNvmActiveEnergy() != 0.001) {
				//     measuredProduction = entity.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				// }
				// entity.setMeasuredProduction(measuredProduction);
				Object insertId = insert("ModelHuaweiSun200028ktl.insertModelHuaweiSun200028ktl", entity);
				if (insertId == null) {
					allSuccess = false;
				}
				// ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles");
				// ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
				// int hours = zdtNowLosAngeles.getHour();
				// if (hours >= 9 && hours <= 17 && dataObj != null && dataObj.getEnable_alert() >= 1) {
				//     checkTriggerAlertModelHuaweiSun200028ktl(entity);
				// }
			}
			return allSuccess;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}
	}
}