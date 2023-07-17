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
import com.nwm.api.entities.ModelXantrexGT100250500Entity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelXantrexGT100250500Service extends DB {
	/**
	 * @description set data ModelXantrexGT100250500
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelXantrexGT100250500Entity setModelXantrexGT100250500(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelXantrexGT100250500Entity dataModelXantrex = new ModelXantrexGT100250500Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelXantrex.setTime(words.get(0).replace("'", ""));
				dataModelXantrex.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelXantrex.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelXantrex.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelXantrex.setVAB(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelXantrex.setVBC(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelXantrex.setVCA(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelXantrex.setCurrentA(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelXantrex.setCurrentB(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelXantrex.setCurrentC(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelXantrex.setReadPower(power);
				dataModelXantrex.setPVVoltage(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelXantrex.setPVCurrent(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelXantrex.setPVPower(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelXantrex.setGridFrequency(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelXantrex.setSystemState(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelXantrex.setGoalState(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelXantrex.setFaultCode(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelXantrex.setAccumulatedEnergy(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelXantrex.setRMatrixTemp(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelXantrex.setLMatrixTemp(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelXantrex.setIntakeAirTemperature(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelXantrex.setNvmActivePower(power);
				dataModelXantrex.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				
				
				return dataModelXantrex;
				
			} else {
				return new ModelXantrexGT100250500Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelXantrexGT100250500Entity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_xantrex_gt100_250_500
	 * @author long.pham
	 * @since 2020-12-11
	 * @param data from datalogger
	 */

	public boolean insertModelXantrexGT100250500(ModelXantrexGT100250500Entity obj) {
		try {
			ModelXantrexGT100250500Entity dataObj = (ModelXantrexGT100250500Entity) queryForObject("ModelXantrexGT100250500.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelXantrexGT100250500.insertModelXantrexGT100250500", obj);
			if (insertId == null) {
				return false;
			}
			
			ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
	        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
	        int hours = zdtNowLosAngeles.getHour();
	        
	        if(hours >=8 && hours <= 18) {
	        	checkTriggerAlertModelXantrexGT100250500(obj);
	        }
			
			return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	
	
	/**
	 * @description get last row "data table name" by device
	 * @author duy.phan
	 * @since 2023-05-23
	 * @param datatablename
	 */

	public ModelXantrexGT100250500Entity checkAlertWriteCode(ModelXantrexGT100250500Entity obj) {
		ModelXantrexGT100250500Entity rowItem = new ModelXantrexGT100250500Entity();
		try {
			rowItem = (ModelXantrexGT100250500Entity) queryForObject("ModelXantrexGT100250500.checkAlertWriteCode", obj);
			if (rowItem == null)
				return new ModelXantrexGT100250500Entity();
		} catch (Exception ex) {
			log.error("ModelXantrexGT100250500.checkAlertWriteCode", ex);
			return new ModelXantrexGT100250500Entity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author duy.phan
	 * @since 2023-05-23
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelXantrexGT100250500(ModelXantrexGT100250500Entity obj) {
		// Check device alert by fault code
		 int faultCode = (obj.getFaultCode() > 0 && obj.getFaultCode() != 0.001) ? (int) obj.getFaultCode() : 0;
		
		ModelXantrexGT100250500Entity rowItem = (ModelXantrexGT100250500Entity) checkAlertWriteCode(obj);
		
		if(faultCode > 0 && rowItem.getTotalFaultCode() >= 4) {
			try {
				int errorId = LibErrorCode.GetAlertModelXantrexGT100250500(faultCode);
				System.out.println("status errorId: " + errorId);		
				if (errorId > 0) {
					AlertEntity alertDeviceItem = new AlertEntity();
					alertDeviceItem.setId_device(obj.getId_device());
					alertDeviceItem.setStart_date(obj.getTime());
					alertDeviceItem.setId_error(errorId);
					boolean checkAlertDeviceExist = (int) queryForObject("BatchJob.checkAlertlExist",
							alertDeviceItem) > 0;
					boolean errorExits = (int) queryForObject("BatchJob.checkErrorExist", alertDeviceItem) > 0;
					if (!checkAlertDeviceExist && errorExits) {
						insert("BatchJob.insertAlert", alertDeviceItem);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Close faultCode
			try {
				if(rowItem.getTotalFaultCode() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is error code
					alertItemClose.setFaultCodeLevel(1);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelXantrexGT100250500.getListTriggerFaultCode", alertItemClose);
					if(dataListWarningCode.size() > 0) {
						for(int i = 0; i < dataListWarningCode.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListWarningCode.get(i);
							int id =  Integer.parseInt(itemFault.get("id").toString());
							int idError =  Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id );
							alertItemClose.setId_error(idError);
							update("Alert.UpdateErrorRow", alertItemClose);
						}
					}
				}
				
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
