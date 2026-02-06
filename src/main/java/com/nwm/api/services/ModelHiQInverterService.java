/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ModelHiQInverterEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode.ModelHiQInverterMode;
import com.nwm.api.utils.LibErrorCode.ModelHiQInverterStatus;
import com.nwm.api.utils.LibErrorCode.ModelHiQInverterString1Status;
import com.nwm.api.utils.LibErrorCode.ModelHiQInverterString2Status;

public class ModelHiQInverterService extends DB {
	/**
	 * @description set data 
	 * @author Duy.Phan
	 * @since 2025-11-26
	 * @param data
	 */
	
	public ModelHiQInverterEntity setModelHiQInverter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHiQInverterEntity dataModel = new ModelHiQInverterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setTimeLastContacted(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setInverterMode(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setInverterStatus(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setActivePower(power);
				dataModel.setTotalEnergy(energy);
				dataModel.setString1Voltage(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setString1Current(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setString1Power(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));				
				dataModel.setString1Status(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));			
				dataModel.setString2Voltage(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setString2Current(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setString2Power(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setString2Status(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setSerialNumberASCII_1_4(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setSerialNumberASCII_5_8(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setNumberOfStrings(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setPLCSignal(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setPLCNoise(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setFirmwareVersionASCII(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelHiQInverterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHiQInverterEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to ModelHiQInverter
	 * @author Duy.Phan
	 * @since 2025-11-26
	 * @param data from datalogger
	 */
	
	public boolean insertModelHiQInverter(ModelHiQInverterEntity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setTotalEnergy(energy);
			}
			
			ModelHiQInverterEntity dataObj = (ModelHiQInverterEntity) queryForObject("ModelHiQInverter.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setTotalEnergy(dataObj.getNvmActiveEnergy());	
			}
						
			 
			Object insertId = insert("ModelHiQInverter.insertModelHiQInverter", obj);
			if (insertId == null) return false;
			
			// Update measuredProduction 
 			if (dataObj != null && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy() >= 0 ) {
 				ModelHiQInverterEntity objUpdateMeasured = new ModelHiQInverterEntity();
 				objUpdateMeasured.setDatatablename(obj.getDatatablename());
 				objUpdateMeasured.setTime(dataObj.getTime());
 				objUpdateMeasured.setMeasuredProduction(obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy());
 				update("Device.updateMeasuredProduction", objUpdateMeasured);
 			}
			
			if (obj.getEnable_alert() == 1) alertChecking(obj);
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	
	/**
	 * @description check trigger alert
	 * @author Hung.Bui
	 * @since 2026-01-20
	 * @param data from datalogger
	 */
	public void alertChecking(ModelHiQInverterEntity obj) {
		try {
			Map<String, Long> continuousCount = (Map<String, Long>) queryForObject("ModelHiQInverter.continuousCount", obj);
			
			statusChecking(obj, continuousCount.get("status").intValue());
			modeChecking(obj, continuousCount.get("mode").intValue());
			string1StatusChecking(obj, continuousCount.get("string1Status").intValue());
			string2StatusChecking(obj, continuousCount.get("string2Status").intValue());
		} catch (Exception ex) {
			log.error("ModelHiQInverter.alertChecking", ex);
		}
	}
	
	private void statusChecking(ModelHiQInverterEntity obj, int count) {
		try {
			if (count < 20) return;
			ModelHiQInverterStatus status = ModelHiQInverterStatus.fromValue((int) obj.getInverterStatus());
			if (status == ModelHiQInverterStatus.RESERVED) return;
			
			AlertEntity alert = new AlertEntity();
			alert.setId_device(obj.getId_device());
				
			if (status != ModelHiQInverterStatus.HEALTHY) {
				alert.setId_error(status.getErrorId());
				alert.setStart_date(obj.getTime());
				
				boolean isErrorExits = (int) queryForObject("BatchJob.checkErrorExist", alert) > 0;
				if (!isErrorExits) return;
				boolean isAlertExist = (int) queryForObject("BatchJob.checkAlertlExist", alert) > 0;
				if (isAlertExist) return;
				
				insert("BatchJob.insertAlert", alert);
			} else {
				List<Integer> errorIds = new ArrayList<>();
				for (ModelHiQInverterStatus item : ModelHiQInverterStatus.values()) {
					Integer errorId = item.getErrorId();
					if (Objects.isNull(errorId)) continue;
					errorIds.add(errorId);
				}
				alert.setId_errors(errorIds);
				alert.setEnd_date(obj.getTime());
				
				update("Alert.closeAlertByDeviceAndErrors", alert);
			}
		} catch (Exception ex) {
			log.error("ModelHiQInverter.statusChecking", ex);
		}
	}
	
	private void modeChecking(ModelHiQInverterEntity obj, int count) {
		try {
			if (count < 20) return;
			ModelHiQInverterMode status = ModelHiQInverterMode.fromValue((int) obj.getInverterMode());
			if (status == ModelHiQInverterMode.RESERVED) return;
			
			AlertEntity alert = new AlertEntity();
			alert.setId_device(obj.getId_device());
				
			if (status == ModelHiQInverterMode.LOW_LIGHT || status == ModelHiQInverterMode.LOCKED_OFF) {
				alert.setId_error(status.getErrorId());
				alert.setStart_date(obj.getTime());
				
				boolean isErrorExits = (int) queryForObject("BatchJob.checkErrorExist", alert) > 0;
				if (!isErrorExits) return;
				boolean isAlertExist = (int) queryForObject("BatchJob.checkAlertlExist", alert) > 0;
				if (isAlertExist) return;
				
				insert("BatchJob.insertAlert", alert);
			} else {
				List<Integer> errorIds = new ArrayList<>();
				for (ModelHiQInverterMode item : ModelHiQInverterMode.values()) {
					Integer errorId = item.getErrorId();
					if (Objects.isNull(errorId)) continue;
					errorIds.add(errorId);
				}
				alert.setId_errors(errorIds);
				alert.setEnd_date(obj.getTime());
				
				update("Alert.closeAlertByDeviceAndErrors", alert);
			}
		} catch (Exception ex) {
			log.error("ModelHiQInverter.modeChecking", ex);
		}
	}
	
	private void string1StatusChecking(ModelHiQInverterEntity obj, int count) {
		try {
			if (count < 20) return;
			ModelHiQInverterString1Status status = ModelHiQInverterString1Status.fromValue((int) obj.getString1Status());
			
			AlertEntity alert = new AlertEntity();
			alert.setId_device(obj.getId_device());
				
			if (status == ModelHiQInverterString1Status.NOT_AVAILABLE || status == ModelHiQInverterString1Status.NOT_DETECTED || status == ModelHiQInverterString1Status.LOW_LIGHT || status == ModelHiQInverterString1Status.REVERSE_VOLTAGE || status == ModelHiQInverterString1Status.RCD_FAULT || status == ModelHiQInverterString1Status.ARC_FAULT) {
				alert.setId_error(status.getErrorId());
				alert.setStart_date(obj.getTime());
				
				boolean isErrorExits = (int) queryForObject("BatchJob.checkErrorExist", alert) > 0;
				if (!isErrorExits) return;
				boolean isAlertExist = (int) queryForObject("BatchJob.checkAlertlExist", alert) > 0;
				if (isAlertExist) return;
				
				insert("BatchJob.insertAlert", alert);
			} else {
				List<Integer> errorIds = new ArrayList<>();
				for (ModelHiQInverterString1Status item : ModelHiQInverterString1Status.values()) {
					Integer errorId = item.getErrorId();
					if (Objects.isNull(errorId)) continue;
					errorIds.add(errorId);
				}
				alert.setId_errors(errorIds);
				alert.setEnd_date(obj.getTime());
				
				update("Alert.closeAlertByDeviceAndErrors", alert);
			}
		} catch (Exception ex) {
			log.error("ModelHiQInverter.modeChecking", ex);
		}
	}
	
	private void string2StatusChecking(ModelHiQInverterEntity obj, int count) {
		try {
			if (count < 20) return;
			ModelHiQInverterString2Status status = ModelHiQInverterString2Status.fromValue((int) obj.getString2Status());
			
			AlertEntity alert = new AlertEntity();
			alert.setId_device(obj.getId_device());
				
			if (status == ModelHiQInverterString2Status.NOT_AVAILABLE || status == ModelHiQInverterString2Status.NOT_DETECTED || status == ModelHiQInverterString2Status.LOW_LIGHT || status == ModelHiQInverterString2Status.REVERSE_VOLTAGE || status == ModelHiQInverterString2Status.RCD_FAULT || status == ModelHiQInverterString2Status.ARC_FAULT) {
				alert.setId_error(status.getErrorId());
				alert.setStart_date(obj.getTime());
				
				boolean isErrorExits = (int) queryForObject("BatchJob.checkErrorExist", alert) > 0;
				if (!isErrorExits) return;
				boolean isAlertExist = (int) queryForObject("BatchJob.checkAlertlExist", alert) > 0;
				if (isAlertExist) return;
				
				insert("BatchJob.insertAlert", alert);
			} else {
				List<Integer> errorIds = new ArrayList<>();
				for (ModelHiQInverterString2Status item : ModelHiQInverterString2Status.values()) {
					Integer errorId = item.getErrorId();
					if (Objects.isNull(errorId)) continue;
					errorIds.add(errorId);
				}
				alert.setId_errors(errorIds);
				alert.setEnd_date(obj.getTime());
				
				update("Alert.closeAlertByDeviceAndErrors", alert);
			}
		} catch (Exception ex) {
			log.error("ModelHiQInverter.modeChecking", ex);
		}
	}
}
