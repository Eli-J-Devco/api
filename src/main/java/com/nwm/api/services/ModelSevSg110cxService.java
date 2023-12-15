/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ModelSevSg110cxEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelSevSg110cxService extends DB {
	/**
	 * @description set data ModelSevSg110cx
	 * @author long.pham
	 * @since 2023-11-01
	 * @param data
	 */
	
	public ModelSevSg110cxEntity setModelSevSg110cx(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSevSg110cxEntity dataModelSev = new ModelSevSg110cxEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001");
				
				
				dataModelSev.setTime(words.get(0).replace("'", ""));
				dataModelSev.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSev.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSev.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelSev.setTotalYield(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSev.setDailyYield(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSev.setArrayInsulationResistance(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSev.setInteriorTemperature(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSev.setTotalDCPower(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSev.setTotalApparentPower(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSev.setTotalActivePower(power);
				dataModelSev.setTotalReactivePower(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSev.setTotalPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSev.setGridFrequency(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSev.setPhaseAVoltage(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSev.setPhaseBVoltage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSev.setPhaseCVoltage(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSev.setPhaseACurrent(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelSev.setPhaseBCurrent(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelSev.setPhaseCCurrent(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelSev.setFaultCode(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				
				dataModelSev.setNominalReactivePower(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelSev.setNominalActivePower(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelSev.setBusVoltage(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelSev.setNegativeVoltageToGround(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelSev.setWorkState1(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelSev.setWorkState2(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSev.setNvmActivePower(power);
				dataModelSev.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				
				
				return dataModelSev;
				
			} else {
				return new ModelSevSg110cxEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSevSg110cxEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_sev_sg110cx
	 * @author long.pham
	 * @since 2023-11-01
	 * @param data from datalogger
	 */

	public boolean insertModelSevSg110cx(ModelSevSg110cxEntity obj) {
		try {
			ModelSevSg110cxEntity dataObj = (ModelSevSg110cxEntity) queryForObject("ModelSevSg110cx.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelSevSg110cx.insertModelSevSg110cx", obj);
			if (insertId == null) {
				return false;
			}
			
			ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();

			if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
				checkTriggerAlertModelSevSg110cx(obj);
			}
			
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	
	/**
	 * @description get last row "data table name" by device
	 * @author Hung.Bui
	 * @since 2023-11-29
	 * @param datatablename
	 */

	public ModelSevSg110cxEntity checkAlertWriteCode(ModelSevSg110cxEntity obj) {
		ModelSevSg110cxEntity rowItem = new ModelSevSg110cxEntity();
		try {
			rowItem = (ModelSevSg110cxEntity) queryForObject("ModelSevSg110cx.checkAlertWriteCode", obj);
			if (rowItem == null)
				return new ModelSevSg110cxEntity();
		} catch (Exception ex) {
			return new ModelSevSg110cxEntity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author Hung.Bui
	 * @since 2023-11-29
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelSevSg110cx(ModelSevSg110cxEntity obj) {
		// Check device alert by fault code
		
		int faultCode = (obj.getFaultCode() > 0 && obj.getFaultCode() != 0.001) ? (int) obj.getFaultCode() : 0;
		int workState1 = (obj.getWorkState1() > 0 && obj.getWorkState1() != 0.001) ? (int) obj.getWorkState1() : 0;
		int workState2 = (obj.getWorkState2() > 0 && obj.getWorkState2() != 0.001) ? (int) obj.getWorkState2() : 0;
		
		ModelSevSg110cxEntity rowItem = (ModelSevSg110cxEntity) checkAlertWriteCode(obj);
		
		// check fault code
		if (faultCode > 0 && rowItem.getFaultCode() >= 20) {
			try {
				int errorId = LibErrorCode.GetFaultCodeModelSevSg110cx(faultCode);
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist", alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			// Close fault code
			try {
				if(rowItem.getFaultCode() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					List dataListStatusCode = queryForList("ModelSevSg110cx.getListTriggerFaultCode", alertItemClose);
					
					if (dataListStatusCode.size() > 0) {
						for (int i = 0; i < dataListStatusCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListStatusCode.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id);
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
