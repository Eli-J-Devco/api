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
import com.nwm.api.entities.ModelDent48PSHDMeterEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelDent48PSHDMeterService extends DB {
	
	/**
	 * @description set data ModelElkorWattsonPVMeter
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelDent48PSHDMeterEntity setModelDent48PSHDMeter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelDent48PSHDMeterEntity dataModel = new ModelDent48PSHDMeterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setCurrentSum(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0"));
				dataModel.setCurrentCH1A(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setCurrentCH2B(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setCurrentCH3C(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setVoltageLNAvg(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setVoltageL1NAN(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setVoltageL2NBN(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setVoltageL3NCN(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setVoltageLLAvg(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setVoltageL1L2(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setVoltageL2L3(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setVoltageL3L1(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setLineFrequency(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setPowerSum(power);
				dataModel.setPowerCH1A(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setPowerCH2B(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setPowerCH3C(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setVASum(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setVACH1A(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setVACH2B(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setVACH3C(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setVARSum(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setVARCH1A(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setVARCH2B(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setVARCH3C(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setApparentPFAvg(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setApparentPFCH1A(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setApparentPFCH2B(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setApparentPFCH3C(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setExportedEnergySum(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setExportedEnergyCH1A(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				
				dataModel.setExportedEnergyCH2B(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setExportedEnergyCH3C(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setImportedEnergySum(energy);
				dataModel.setImportedEnergyCH1A(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setImportedEnergyCH2B(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setImportedEnergyCH3C(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				dataModel.setExportedVAhSum(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setExportedVAhCH1A(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setExportedVAhCH2B(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setExportedVAhCH3C(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setImportedVAhSum(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setImportedVAhCH1A(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setImportedVAhCH2B(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setImportedVAhCH3C(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setImportedVARhQ1Sum(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setImportedVARhQ1A(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				dataModel.setImportedVARhQ1B(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setImportedVARhQ1C(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setImportedVARhQ2Sum(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setImportedVARhQ2A(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel.setImportedVARhQ2B(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel.setImportedVARhQ2C(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel.setExportedVARhQ3Sum(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel.setExportedVARhQ3A(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel.setExportedVARhQ3B(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel.setExportedVARhQ3C(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				
				dataModel.setExportedVARhQ4Sum(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel.setExportedVARhQ4A(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel.setExportedVARhQ4B(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModel.setExportedVARhQ4C(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModel.setWattDemandElement(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				
//				dataModel.setDisplacementPFSum(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
//				dataModel.setDisplacementPFCH1A(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
//				dataModel.setDisplacementPFCH2B(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
//				dataModel.setDisplacementPFCH3C(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
//				dataModel.setTHDSum(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
//				dataModel.setTHDCH1A(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
//				dataModel.setTHDCH2B(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
//				dataModel.setTHDCH3C(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
//				dataModel.setVADemandElement(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
//				dataModel.setEnergyNetSum(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
//				dataModel.setEnergyNetCH1(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
//				dataModel.setEnergyNetCH2(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
//				dataModel.setEnergyNetCH3(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
//				dataModel.setApparentEnergyNetSum(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
//				dataModel.setApparentEnergyNetCH1(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
//				dataModel.setApparentEnergyNetCH2(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
//				dataModel.setApparentEnergyNetCH3(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
//				dataModel.setVARhNetSum(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
//				dataModel.setVARhNetCH1(Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001"));
//				dataModel.setVARhNetCH2(Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001"));
//				dataModel.setVARhNetCH3(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
//				dataModel.setThetaCH1Angle(Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001"));
//				dataModel.setThetaCH2Angle(Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001"));
//				dataModel.setThetaCH3Angle(Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001"));
//				dataModel.setRoCoilPGAGainCh1(Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001"));
//				dataModel.setRoCoilPGAGainCh2(Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001"));
//				dataModel.setRoCoilPGAGainCh3(Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelDent48PSHDMeterEntity();
			}
			
			
		} catch (Exception ex) {
			return new ModelDent48PSHDMeterEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_elkor_wattson_pv_meter
	 * @author long.pham
	 * @since 2021-08-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelDent48PSHDMeter(ModelDent48PSHDMeterEntity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setImportedEnergySum(energy);
			}
			
			ModelDent48PSHDMeterEntity dataObj = (ModelDent48PSHDMeterEntity) queryForObject("ModelDent48PSHDMeter.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0  || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setImportedEnergySum(dataObj.getNvmActiveEnergy());
			}
			 
			Object insertId = insert("ModelDent48PSHDMeter.insertModelDent48PSHDMeter", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        // Update measuredProduction 
 			if (dataObj != null && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy() >= 0 ) {
 				ModelDent48PSHDMeterEntity objUpdateMeasured = new ModelDent48PSHDMeterEntity();
 				objUpdateMeasured.setDatatablename(obj.getDatatablename());
 				objUpdateMeasured.setTime(dataObj.getTime());
 				objUpdateMeasured.setMeasuredProduction(obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy());
 				update("Device.updateMeasuredProduction", objUpdateMeasured);
 			}
	        
	        ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();
	        
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelDent48PSHDMeter(obj);
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

	public ModelDent48PSHDMeterEntity checkAlertWriteCode(ModelDent48PSHDMeterEntity obj) {
		ModelDent48PSHDMeterEntity rowItem = new ModelDent48PSHDMeterEntity();
		try {
//			rowItem = (ModelXantrexGT100250500Entity) queryForObject("ModelXantrexGT100250500.checkAlertWriteCode", obj);
			List dataList = queryForList("ModelDent48PSHDMeter.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalApparentPFAvg = 0;
				int totalLineFrequency = 0; 
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double ApparentPFAvg = (double) item.get("ApparentPFAvg");
					double LineFrequency = (double) item.get("LineFrequency");
					
					if(ApparentPFAvg > -0.7 && ApparentPFAvg < 0.7) { 
						totalApparentPFAvg++;
					}
					
					if(LineFrequency < 59.8 || LineFrequency > 60.2) { 
						totalLineFrequency++;
					}
					
				}
				rowItem.setTotalApparentPFAvg(totalApparentPFAvg);
				rowItem.setTotalLineFrequency(totalLineFrequency);
			}
			
			if (rowItem == null)
				return new ModelDent48PSHDMeterEntity();
		} catch (Exception ex) {
			log.error("ModelDent48PSHDMeter.checkAlertWriteCode", ex);
			return new ModelDent48PSHDMeterEntity();
		}
		return rowItem;
	}
	
	
	/**
	 * @description check trigger alert fault code
	 * @author duy.phan
	 * @since 2023-05-23
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelDent48PSHDMeter(ModelDent48PSHDMeterEntity obj) {
		// Check device alert by fault code
		
		 ModelDent48PSHDMeterEntity rowItem = (ModelDent48PSHDMeterEntity) checkAlertWriteCode(obj);
		
		if((obj.getApparentPFAvg() > -0.7 && obj.getApparentPFAvg() < 0.7) && rowItem.getTotalApparentPFAvg() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelDent48PSHDMeter(1);	
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
				if(rowItem.getTotalApparentPFAvg() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is error code
					alertItemClose.setFaultCodeLevel(1);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelDent48PSHDMeter.getListTriggerFaultCode", alertItemClose);
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
		
		if((obj.getLineFrequency() < 59.8 || obj.getLineFrequency() > 60.2) && rowItem.getTotalLineFrequency() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelDent48PSHDMeter(2);	
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
				if(rowItem.getTotalLineFrequency() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is error code
					alertItemClose.setFaultCodeLevel(2);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelDent48PSHDMeter.getListTriggerFaultCode", alertItemClose);
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
