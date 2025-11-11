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
import com.nwm.api.entities.ModelSmaStp2550us50Entity;
import com.nwm.api.entities.ModelXantrexGT500EEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelSmaStp2550us50Service extends DB {
	
	/**
	 * @description set data ModelSmaStp2550us50
	 * @author duy.phan
	 * @since 2025-04-04
	 * @param data
	 */
	
	public ModelSmaStp2550us50Entity setModelSmaStp2550us50(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSmaStp2550us50Entity dataModelSmaStp2550us50 = new ModelSmaStp2550us50Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001");
				
				dataModelSmaStp2550us50.setTime(words.get(0).replace("'", ""));
				dataModelSmaStp2550us50.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSmaStp2550us50.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSmaStp2550us50.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelSmaStp2550us50.setGrid_Frequency(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSmaStp2550us50.setApparent_Power(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSmaStp2550us50.setReactive_Power(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSmaStp2550us50.setActive_Power(power);
				dataModelSmaStp2550us50.setPower_Factor(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSmaStp2550us50.setGrid_Current(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSmaStp2550us50.setCurrent_Phase_1(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));			
				dataModelSmaStp2550us50.setCurrent_Phase_2(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSmaStp2550us50.setCurrent_Phase_3(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSmaStp2550us50.setActive_Power_Phase_1(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSmaStp2550us50.setActive_Power_Phase_2(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSmaStp2550us50.setActive_Power_Phase_3(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSmaStp2550us50.setReactive_Power_Phase_1(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSmaStp2550us50.setReactive_Power_Phase_2(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelSmaStp2550us50.setReactive_Power_Phase_3(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelSmaStp2550us50.setApparent_Power_Phase_1(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));			
				dataModelSmaStp2550us50.setApparent_Power_Phase_2(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelSmaStp2550us50.setApparent_Power_Phase_3(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelSmaStp2550us50.setVoltage_Phase_1(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(23) : "0.001"));
				dataModelSmaStp2550us50.setVoltage_Phase_2(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelSmaStp2550us50.setVoltage_Phase_3(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelSmaStp2550us50.setVoltage_Phase_1_2(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelSmaStp2550us50.setVoltage_Phase_2_3(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelSmaStp2550us50.setVoltage_Phase_3_1(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelSmaStp2550us50.setResidual_DC_Current(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelSmaStp2550us50.setInsulation_Resistance(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelSmaStp2550us50.setFeed_In_Time(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));				
				dataModelSmaStp2550us50.setOperating_Time(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelSmaStp2550us50.setTotal_Yield(energy);
				dataModelSmaStp2550us50.setInternal_Temperature(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelSmaStp2550us50.setDC_Current_Input_1(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelSmaStp2550us50.setDC_Current_Input_2(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelSmaStp2550us50.setDC_Current_Input_3(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelSmaStp2550us50.setDC_Current_Input_4(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));				
				dataModelSmaStp2550us50.setDC_Voltage_Input_1(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelSmaStp2550us50.setDC_Voltage_Input_2(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelSmaStp2550us50.setDC_Voltage_Input_3(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelSmaStp2550us50.setDC_Voltage_Input_4(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelSmaStp2550us50.setDC_Power_Input_1(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelSmaStp2550us50.setDC_Power_Input_2(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelSmaStp2550us50.setDC_Power_Input_3(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelSmaStp2550us50.setDC_Power_Input_4(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				
				dataModelSmaStp2550us50.setEventMessage(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelSmaStp2550us50.setHealthCondition(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelSmaStp2550us50.setFaultCorrectionMeasure(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelSmaStp2550us50.setBlockStatus(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelSmaStp2550us50.setReasonforDerating(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSmaStp2550us50.setNvmActivePower(power);
				dataModelSmaStp2550us50.setNvmActiveEnergy(energy);
				return dataModelSmaStp2550us50;
				
			} else {
				return new ModelSmaStp2550us50Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSmaStp2550us50Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to ModelSmaStp2550us50
	 * @author duy.phan
	 * @since 2025-04-04
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmaStp2550us50(ModelSmaStp2550us50Entity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setTotal_Yield(energy);
			}
			
			ModelSmaStp2550us50Entity dataObj = (ModelSmaStp2550us50Entity) queryForObject("ModelSmaStp2550us50.getLastRow", obj);
			// filter data 
//			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() < dataObj.getNvmActiveEnergy() || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
//				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				obj.setTotal_Yield(dataObj.getNvmActiveEnergy());
//			}
						
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSmaStp2550us50.insertModelSmaStp2550us50", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
	        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
	        int hours = zdtNowLosAngeles.getHour();
	        
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelSmaStp2550us50(obj);
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

	public ModelSmaStp2550us50Entity checkAlertWriteCode(ModelSmaStp2550us50Entity obj) {
		ModelSmaStp2550us50Entity rowItem = new ModelSmaStp2550us50Entity();
		try {

			List dataList = queryForList("ModelSmaStp2550us50.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalFaultCode1 = 0, totalFaultCode2 = 0, totalFaultCode3 = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double EventMessage = (double) item.get("EventMessage");
					if(Double.compare(obj.getEventMessage(), EventMessage) == 0 && obj.getEventMessage() > 0 && EventMessage > 0 && obj.getEventMessage() != 886 && EventMessage != 886) { 
						totalFaultCode1++;
					}
					
					double BlockStatus = (double) item.get("BlockStatus");
					if(Double.compare(obj.getBlockStatus(), BlockStatus) == 0 && obj.getBlockStatus() > 0 && BlockStatus > 0 && obj.getBlockStatus() != 302 && BlockStatus != 302) { 
						totalFaultCode2++;
					}
					
					double ReasonforDerating = (double) item.get("ReasonforDerating");
					if(Double.compare(obj.getReasonforDerating(), ReasonforDerating) == 0 && obj.getReasonforDerating() > 0 && ReasonforDerating > 0 && obj.getReasonforDerating() != 884 && ReasonforDerating != 884) { 
						totalFaultCode3++;
						
					}
					
				}
				rowItem.setTotalFaultCode1(totalFaultCode1);
				rowItem.setTotalFaultCode2(totalFaultCode2);
				rowItem.setTotalFaultCode3(totalFaultCode3);
			}
			
			if (rowItem == null)
				return new ModelSmaStp2550us50Entity();
		} catch (Exception ex) {
			log.error("ModelSmaStp2550us50.checkAlertWriteCode", ex);
			return new ModelSmaStp2550us50Entity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author duy.phan
	 * @since 2023-11-16
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelSmaStp2550us50(ModelSmaStp2550us50Entity obj) {
		// Check device alert by fault code
		 int faultCode1 = (obj.getEventMessage() > 0 && obj.getEventMessage() != 886 && obj.getEventMessage() != 0.001) ? (int) obj.getEventMessage() : 0;
		 int faultCode2 = (obj.getBlockStatus() > 0 && obj.getBlockStatus() != 302 && obj.getBlockStatus() != 0.001) ? (int) obj.getBlockStatus() : 0;
		 int faultCode3 = (obj.getReasonforDerating() > 0 && obj.getReasonforDerating() != 884 && obj.getReasonforDerating() != 0.001) ? (int) obj.getReasonforDerating() : 0;
		 
		
		 ModelSmaStp2550us50Entity rowItem = (ModelSmaStp2550us50Entity) checkAlertWriteCode(obj);
		
		if(faultCode1 > 0 && rowItem.getTotalFaultCode1() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelSmaStp2550us50(faultCode1, 1);	
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
				if(rowItem.getTotalFaultCode1() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is error code
					alertItemClose.setFaultCodeLevel(1);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelSmaStp2550us50.getListTriggerFaultCode", alertItemClose);
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
		
		
		if(faultCode2 > 0 && rowItem.getTotalFaultCode2() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelSmaStp2550us50(faultCode2, 2);	
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
				if(rowItem.getTotalFaultCode2() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is error code
					alertItemClose.setFaultCodeLevel(2);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelSmaStp2550us50.getListTriggerFaultCode", alertItemClose);
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
		
		
		
		if(faultCode3 > 0 && rowItem.getTotalFaultCode3() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelSmaStp2550us50(faultCode3, 3);	
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
				if(rowItem.getTotalFaultCode3() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is error code
					alertItemClose.setFaultCodeLevel(3);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelSmaStp2550us50.getListTriggerFaultCode", alertItemClose);
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
