/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ModelHuaweiSun200028ktlEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelHuaweiSun200028ktlService extends DB {
	/**
	 * @description set data ModelHuaweiSun200028ktl
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelHuaweiSun200028ktlEntity setModelHuaweiSun200028ktl(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHuaweiSun200028ktlEntity dataModel = new ModelHuaweiSun200028ktlEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setActivePower(power);
				dataModel.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setTotalDCInputCurrent(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setTotalInputPower(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setInsulationResistance(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setInverterStatus(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setCabinetTemperature(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModel.setMajorFaultCode(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setMinorFaultCode(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setWarningCode(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(Double.parseDouble("0.001"));
				
				return dataModel;
				
			} else {
				return new ModelHuaweiSun200028ktlEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHuaweiSun200028ktlEntity();
		}
	}

	
	

	/**
	 * @description insert data from datalogger to model_ivt_solaron_ext
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelHuaweiSun200028ktl(ModelHuaweiSun200028ktlEntity obj) {
		try {
			ModelHuaweiSun200028ktlEntity dataObj = (ModelHuaweiSun200028ktlEntity) queryForObject("ModelHuaweiSun200028ktl.getLastRow", obj);
			 
		 	Object insertId = insert("ModelHuaweiSun200028ktl.insertModelHuaweiSun200028ktl", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        // Update measuredProduction 
 			if (dataObj != null && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy() >= 0 ) {
 				ModelHuaweiSun200028ktlEntity objUpdateMeasured = new ModelHuaweiSun200028ktlEntity();
 				objUpdateMeasured.setDatatablename(obj.getDatatablename());
 				objUpdateMeasured.setTime(dataObj.getTime());
 				objUpdateMeasured.setMeasuredProduction(obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy());
 				update("Device.updateMeasuredProduction", objUpdateMeasured);
 			}
	        
	        ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();
			
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelHuaweiSun200028ktl(obj);
	        	
	        }
	        
	        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
	
	
	/**
	 * @description get last row "data table name" by device
	 * @author long.pham
	 * @since 2023-04-04
	 * @param datatablename
	 */

	public ModelHuaweiSun200028ktlEntity checkAlertWriteCode(ModelHuaweiSun200028ktlEntity obj) {
		ModelHuaweiSun200028ktlEntity rowItem = new ModelHuaweiSun200028ktlEntity();
		try {
			List dataList = queryForList("ModelHuaweiSun200028ktl.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalFault1 = 0, totalFault2 = 0, totalFault3 = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double MajorFaultCode = (double) item.get("MajorFaultCode");
					if(Double.compare(obj.getMajorFaultCode(), MajorFaultCode) == 0 && obj.getMajorFaultCode() > 0 && MajorFaultCode > 0) { 
						totalFault1++;
					}
					
					double MinorFaultCode = (double) item.get("MinorFaultCode");
					if(Double.compare(obj.getMinorFaultCode(), MinorFaultCode) == 0 && obj.getMinorFaultCode() > 0 && MinorFaultCode > 0) { 
						totalFault2++;
					}
					
					double WarningCode = (double) item.get("WarningCode");
					if(Double.compare(obj.getWarningCode(), WarningCode) == 0 && obj.getWarningCode() > 0 && WarningCode > 0) { 
						totalFault3++;
					}
					
					
				}
				rowItem.setTotalFault1(totalFault1);
				rowItem.setTotalFault2(totalFault2);
				rowItem.setTotalFault3(totalFault3);
				
				
			}
			if (rowItem == null)
				return new ModelHuaweiSun200028ktlEntity();
		} catch (Exception ex) {
			return new ModelHuaweiSun200028ktlEntity();
		}
		return rowItem;
	}
	
	
	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since 2022-09-26
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelHuaweiSun200028ktl(ModelHuaweiSun200028ktlEntity obj) {
		// Check device alert by fault code
		long fault1 = (obj.getMajorFaultCode() > 0 && obj.getMajorFaultCode() != 0.001) ? (long) obj.getMajorFaultCode() : 0;
		long fault2 = (obj.getMinorFaultCode() > 0 && obj.getMinorFaultCode() != 0.001) ? (long) obj.getMinorFaultCode() : 0;
		long fault3 = (obj.getWarningCode() > 0 && obj.getWarningCode() != 0.001) ? (long) obj.getWarningCode() : 0;

		ModelHuaweiSun200028ktlEntity rowItem = (ModelHuaweiSun200028ktlEntity) checkAlertWriteCode(obj);
		if (fault1 > 0  && rowItem.getTotalFault1() >= 20) {
			try {
				String toBinary = Long.toBinaryString(fault1);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				String causeId = toBinary32Bit.substring(17, 32);
				String alarmId = toBinary32Bit.substring(0,16);
				
				if(causeId != "" && alarmId != "") {
					int decimalCauseId = Integer.parseInt(causeId, 2);
					int decimalAlarmId = Integer.parseInt(alarmId, 2);
					if(decimalCauseId > 0 && decimalAlarmId > 0) {
						int errorId = LibErrorCode.GetErrorCodeModelHuaweiSun200028kt(decimalAlarmId, decimalCauseId, 1);
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
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			// Close fault code 1
			try {
				if(rowItem.getTotalFault1() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(1);
					List dataListFault1 = queryForList("ModelHuaweiSun200028ktl.getListTriggerFaultCode", alertItemClose);
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
				e.printStackTrace();
			}
		}
		
		
		if (fault2 > 0  && rowItem.getTotalFault2() >= 20) {
			try {
				String toBinary = Long.toBinaryString(fault2);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				String causeId = toBinary32Bit.substring(17, 32);
				String alarmId = toBinary32Bit.substring(0,16);
				
				if(causeId != "" && alarmId != "") {
					int decimalCauseId = Integer.parseInt(causeId, 2);
					int decimalAlarmId = Integer.parseInt(alarmId, 2);
					if(decimalCauseId > 0 && decimalAlarmId > 0) {
						int errorId = LibErrorCode.GetErrorCodeModelHuaweiSun200028kt(decimalAlarmId, decimalCauseId, 2);
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
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			// Close fault code 1
			try {
				if(rowItem.getTotalFault2() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(2);
					List dataListFault2 = queryForList("ModelHuaweiSun200028ktl.getListTriggerFaultCode", alertItemClose);
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
				e.printStackTrace();
			}
		}
		
		
		if (fault3 > 0  && rowItem.getTotalFault3() >= 20) {
			try {
				String toBinary = Long.toBinaryString(fault3);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				String causeId = toBinary32Bit.substring(17, 32);
				String alarmId = toBinary32Bit.substring(0,16);
				
				if(causeId != "" && alarmId != "") {
					int decimalCauseId = Integer.parseInt(causeId, 2);
					int decimalAlarmId = Integer.parseInt(alarmId, 2);
					if(decimalCauseId > 0 && decimalAlarmId > 0) {
						int errorId = LibErrorCode.GetErrorCodeModelHuaweiSun200028kt(decimalAlarmId, decimalCauseId, 3);
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
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			// Close fault code 1
			try {
				if(rowItem.getTotalFault3() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					alertItemClose.setFaultCodeLevel(3);
					List dataListFault3 = queryForList("ModelHuaweiSun200028ktl.getListTriggerFaultCode", alertItemClose);
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
				e.printStackTrace();
			}
		}
		

		
	}
	
	

}
