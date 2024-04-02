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
import com.nwm.api.entities.ModelXantrexGT500EEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelXantrexGT500EService extends DB {
	/**
	 * @description set data ModelXantrexGT500E
	 * @author long.pham
	 * @since 2023-10-20
	 * @param data
	 */
	
	public ModelXantrexGT500EEntity setModelXantrexGT500E(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelXantrexGT500EEntity dataModelXantrex = new ModelXantrexGT500EEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001");
				
				
				dataModelXantrex.setTime(words.get(0).replace("'", ""));
				dataModelXantrex.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelXantrex.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelXantrex.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelXantrex.setAC_CURRENT_A(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelXantrex.setAC_CURRENT_B(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelXantrex.setAC_CURRENT_C(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelXantrex.setAC_POWER(power);
				dataModelXantrex.setAC_VOLTAGE_AB(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelXantrex.setAC_VOLTAGE_BC(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelXantrex.setAC_VOLTAGE_CA(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(11) : "0.001"));
				dataModelXantrex.setDC_CURRENT(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelXantrex.setDC_POWER(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelXantrex.setDC_VOLTAGE(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelXantrex.setENERGY_DELIVERED(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelXantrex.setFREQUENCY(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelXantrex.setSTATUS_FAULT(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelXantrex.setSTATUS_GOAL(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelXantrex.setSTATUS_INVERTER(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelXantrex.setSTATUS_OPERATING(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelXantrex.setSTATUS_PV(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelXantrex.setT_LEFT_MATRIX(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelXantrex.setT_RIGHT_MATRIX(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));

				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelXantrex.setNvmActivePower(power);
				dataModelXantrex.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				
				
				return dataModelXantrex;
				
			} else {
				return new ModelXantrexGT500EEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelXantrexGT500EEntity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_xantrex_gt500e
	 * @author long.pham
	 * @since 2020-12-11
	 * @param data from datalogger
	 */

	public boolean insertModelXantrexGT500EService(ModelXantrexGT500EEntity obj) {
		try {
			ModelXantrexGT500EEntity dataObj = (ModelXantrexGT500EEntity) queryForObject("ModelXantrexGT500E.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}

//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			Object insertId = insert("ModelXantrexGT500E.insertModelXantrexGT500E", obj);
			if (insertId == null) {
				return false;
			}
			
			ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
	        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
	        int hours = zdtNowLosAngeles.getHour();
	        
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelXantrexGT500E(obj);
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
	 * @since 2023-11-16
	 * @param datatablename
	 */

	public ModelXantrexGT500EEntity checkAlertWriteCode(ModelXantrexGT500EEntity obj) {
		ModelXantrexGT500EEntity rowItem = new ModelXantrexGT500EEntity();
		try {
//			rowItem = (ModelXantrexGT500EEntity) queryForObject("ModelXantrexGT500E.checkAlertWriteCode", obj);
			List dataList = queryForList("ModelXantrexGT500E.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalFaultCode = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double statusFault = (double) item.get("active_faults1");
					if(Double.compare(obj.getSTATUS_FAULT(), statusFault) == 0 && obj.getSTATUS_FAULT() > 0 && statusFault > 0) { 
						totalFaultCode++;
					}
				}
				rowItem.setTotalFaultCode(totalFaultCode);
			}
			
			if (rowItem == null)
				return new ModelXantrexGT500EEntity();
		} catch (Exception ex) {
			log.error("ModelXantrexGT500E.checkAlertWriteCode", ex);
			return new ModelXantrexGT500EEntity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author duy.phan
	 * @since 2023-11-16
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelXantrexGT500E(ModelXantrexGT500EEntity obj) {
		// Check device alert by fault code
		 int faultCode = (obj.getSTATUS_FAULT() > 0 && obj.getSTATUS_FAULT() != 0.001) ? (int) obj.getSTATUS_FAULT() : 0;
		
		 ModelXantrexGT500EEntity rowItem = (ModelXantrexGT500EEntity) checkAlertWriteCode(obj);
		
		if(faultCode > 0 && rowItem.getTotalFaultCode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelXantrexGT500E(faultCode);	
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
					dataListWarningCode = queryForList("ModelXantrexGT500E.getListTriggerFaultCode", alertItemClose);
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
