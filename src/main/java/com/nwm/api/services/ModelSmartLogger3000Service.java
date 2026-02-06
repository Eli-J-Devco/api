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
import com.nwm.api.entities.ModelSmartLogger3000Entity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelSmartLogger3000Service extends DB {
	
	/**
	 * @description set data ModelElkorWattsonPVMeter
	 * @author duy.phan
	 * @since 2023-12-12
	 * @param data
	 */
	
	public ModelSmartLogger3000Entity setModelSmartLogger3000(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSmartLogger3000Entity dataModel = new ModelSmartLogger3000Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setPowerOn(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setPowerOff(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setPowerOnOff(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setPowerOnOffReverse(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setTransferTrip(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setArrayReset(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setActiveAdjustment(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setReactiveAdjustment(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setActiveAdjustment2(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setReactiveAdjustment2(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setActivePowerAdjustmentBy(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setPowerFactorAdjustment(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setDCCurrent(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setInputPower(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setCO2Reduction(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setActivePower(power);
				dataModel.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setPlantStatusQinghai(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setCO2ReductionLarge(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setDCCurrent2(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setETotal(energy);
				dataModel.setEDaily(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setDurationOfDailyPowerGeneration(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setPlantStatusXinjiang(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setPlantStatusNingxia(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setActiveAlarmSequenceNumber(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setHistoricalAlarmSequenceNumber(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setPhaseACurrent(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setPhaseBCurrent(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setPhaseCCurrent(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));				
				dataModel.setUab(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setUbc(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setUca(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));				
				dataModel.setReserved(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				
				
				dataModel.setInverterEfficiency(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setMaxReactiveAdjustment(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				dataModel.setMinReactiveAdjustment(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setMaxActiveAdjustment(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setLocked(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setDIStatus(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setESN(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setSystemReset(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setFastDeviceAccess(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setDeviceOperation(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setDeviceAccessStatus(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setActivePowerControlMode(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				dataModel.setActivePowerSchedulingTargetValue(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setReactivePowerControlMode(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setReactivePowerSchedulingCurveMode(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setReactivePowerSchedulingTargetValue(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel.setActiveSchedulingPercentage(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel.setCO2EmissionReductionCoefficient(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel.setPVModuleCapacity(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel.setRatedPlantCapacity(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel.setTotalRatedCapacityOfGridConnectedInverters(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel.setConversionCoefficient(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModel.setCommunicationStatus(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				
				dataModel.setAlarmInfo1(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel.setAlarmInfo2(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel.setAlarmInfo3(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSmartLogger3000Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSmartLogger3000Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_sungrow_logger1000
	 * @author duy.phan
	 * @since 2023-12-12
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmartLogger3000(ModelSmartLogger3000Entity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setETotal(energy);
			}
			ModelSmartLogger3000Entity dataObj = (ModelSmartLogger3000Entity) queryForObject("ModelSmartLogger3000.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setETotal(dataObj.getNvmActiveEnergy());
			}
			 
			Object insertId = insert("ModelSmartLogger3000.insertModelSmartLogger3000", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        // Update measuredProduction 
 			if (dataObj != null && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy() >= 0 ) {
 				ModelSmartLogger3000Entity objUpdateMeasured = new ModelSmartLogger3000Entity();
 				objUpdateMeasured.setDatatablename(obj.getDatatablename());
 				objUpdateMeasured.setTime(dataObj.getTime());
 				objUpdateMeasured.setMeasuredProduction(obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy());
 				update("Device.updateMeasuredProduction", objUpdateMeasured);
 			}
	        
	        ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();
	        
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelSmartLogger3000(obj);
	        }
	        
	        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	
	/**
	 * @description get last row
	 * @author long.pham
	 * @since 2022-05-11
	 * @param id_device
	 * @return Object
	 */

	public ModelSmartLogger3000Entity getLastRow(ModelSmartLogger3000Entity obj) {
		ModelSmartLogger3000Entity dataObj = new ModelSmartLogger3000Entity();
		try {
			dataObj = (ModelSmartLogger3000Entity) queryForObject("ModelSmartLogger3000.getLastRow", obj);
			if (dataObj == null)
				return new ModelSmartLogger3000Entity();
		} catch (Exception ex) {
			return new ModelSmartLogger3000Entity();
		}
		return dataObj;
	}
	
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2023-04-03
	 * @param datatablename
	 */

	public ModelSmartLogger3000Entity checkAlertWriteCode(ModelSmartLogger3000Entity obj) {
		ModelSmartLogger3000Entity rowItem = new ModelSmartLogger3000Entity();
		try {
			List dataList = queryForList("ModelSmartLogger3000.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalFault1 = 0, totalFault2 = 0, totalFault3 = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double fault1 = (double) item.get("AlarmInfo1");
					if(Double.compare(obj.getAlarmInfo1(), fault1) == 0 && obj.getAlarmInfo1() > 0 && fault1 > 0) { 
						totalFault1++;
					}
					
					double fault2 = (double) item.get("AlarmInfo2");
					if(Double.compare(obj.getAlarmInfo2(), fault2) == 0 && obj.getAlarmInfo2() > 0 && fault2 > 0) { 
						totalFault2++;
					}
					
					double fault3 = (double) item.get("AlarmInfo3");
					if(Double.compare(obj.getAlarmInfo1(), fault3) == 0 && obj.getAlarmInfo1() > 0 && fault3 > 0) { 
						totalFault3++;
					}
					
				}
				rowItem.setTotalFaultWord1(totalFault1);
				rowItem.setTotalFaultWord2(totalFault2);
				rowItem.setTotalFaultWord3(totalFault3);
				
			}
			
			if (rowItem == null)
				return new ModelSmartLogger3000Entity();
		} catch (Exception ex) {
			return new ModelSmartLogger3000Entity();
		}
		return rowItem;
	}
	

	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since  2023-04-03
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelSmartLogger3000(ModelSmartLogger3000Entity obj) {
		// Check device alert by fault code
		int fault1 = (obj.getAlarmInfo1() > 0 && obj.getAlarmInfo1() != 0.001) ? (int) obj.getAlarmInfo1() : 0;
		int fault2 = (obj.getAlarmInfo2() > 0 && obj.getAlarmInfo2() != 0.001) ? (int) obj.getAlarmInfo2() : 0;
		int fault3 = (obj.getAlarmInfo3() > 0 && obj.getAlarmInfo3() != 0.001) ? (int) obj.getAlarmInfo3() : 0;
		
		
		ModelSmartLogger3000Entity rowItem = (ModelSmartLogger3000Entity) checkAlertWriteCode(obj);
		
		
		// check fault code 1
		if (fault1 > 0  && rowItem.getTotalFaultWord1() >= 20) {
			try {
				String toBinary = Integer.toBinaryString(fault1);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSmartLogger3000(v, 1);
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
					}
					v += 1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// Close fault code 1
			try {
				if(rowItem.getTotalFaultWord1() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(1);
					List dataListFault1 = new ArrayList();
					dataListFault1 = queryForList("ModelSmartLogger3000.getListTriggerFaultCode", alertItemClose);
					if(dataListFault1.size() > 0) {
						for(int i = 0; i < dataListFault1.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFault1.get(i);
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
		
		
		// check fault code 2
		if (fault2 > 0  && rowItem.getTotalFaultWord2() >= 20) {
			try {
				String toBinary = Integer.toBinaryString(fault2);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetErrorCodeModelSmartLogger3000(v, 2);
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
					}
					v += 1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// Close fault code 
			try {
				if(rowItem.getTotalFaultWord2() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(2);
					List dataListFault2 = new ArrayList();
					dataListFault2 = queryForList("ModelSmartLogger3000.getListTriggerFaultCode", alertItemClose);
					if(dataListFault2.size() > 0) {
						for(int i = 0; i < dataListFault2.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) dataListFault2.get(i);
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
		
		// check fault code 3
				if (fault3 > 0  && rowItem.getTotalFaultWord3() >= 20) {
					try {
						String toBinary = Integer.toBinaryString(fault3);
						String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
						int v = 0;
						for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
							int index = b;
							int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
							if (bitLevel == 1) {
								int errorId = LibErrorCode.GetErrorCodeModelSmartLogger3000(v, 3);
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
							}
							v += 1;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					// Close fault code 3
					try {
						if(rowItem.getTotalFaultWord3() == 0) {
							AlertEntity alertItemClose = new AlertEntity();
							alertItemClose.setId_device(obj.getId_device());
							alertItemClose.setFaultCodeLevel(3);
							List dataListFault3 = new ArrayList();
							dataListFault3 = queryForList("ModelSmartLogger3000.getListTriggerFaultCode", alertItemClose);
							if(dataListFault3.size() > 0) {
								for(int i = 0; i < dataListFault3.size(); i++) {
									Map<String, Object> itemFault = (Map<String, Object>) dataListFault3.get(i);
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
