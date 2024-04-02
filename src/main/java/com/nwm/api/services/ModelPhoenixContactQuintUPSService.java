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
import com.nwm.api.entities.ModelPhoenixContactQuintUPSEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelPhoenixContactQuintUPSService extends DB {
	
	/**
	 * @description set data ModelPhoenixContactQuintUPS
	 * @author Hung.Bui
	 * @since 2024-02-19
	 * @param data
	 */
	
	public ModelPhoenixContactQuintUPSEntity setModelPhoenixContactQuintUPS(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPhoenixContactQuintUPSEntity data = new ModelPhoenixContactQuintUPSEntity();
				
				data.setTime(words.get(0).replace("'", ""));
				data.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				data.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				data.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				data.setActualInputVoltage(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				data.setActualInputCurrent(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				data.setActualOutputVoltage(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				data.setActualOutputCurrent(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				data.setBatteryActualVoltage(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				data.setBatteryChargeCurrent(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				data.setBatteryTemperature(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				data.setDeviceTemperature(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				data.setStateofCharge(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				data.setStateofChargeRemainingTime(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				data.setStateofChargeRemainingTimetoPCShutdown(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				data.setStateofHealth(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				data.setStateofHealthRemainingLifetime(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				data.setNumberofDevices(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				data.setOperationTime(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				data.setBatteryModeTime(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				data.setStatusAlarm(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				data.setStatusWarning(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				data.setBattery1StateofCharge(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				data.setBattery1StateofHealth(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				data.setBattery1Temperature(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				data.setBattery1StateofFuse(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				data.setBattery1ActualInternalVoltage(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				data.setBattery1ActualBlockVoltage(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				data.setBattery1InstalledCapacity(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				data.setBattery1NominalResistance(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				data.setBattery1MaxTemperature(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				data.setBattery1MinTemperature(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				data.setBattery1NominalLifetime(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				data.setBattery1MaxChargeCurrent(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				data.setBattery1ChargeAbsorptionVoltage(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				data.setBattery1ChargeEndvoltage(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				data.setBattery1ChargeTemperatureCoefficient(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				data.setBattery1DischargeEndvoltage(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				data.setBattery1MaxDischargeCurrent(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				data.setBattery1MaxTemperatureWarning(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				data.setBattery1MinTemperatureWarning(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				data.setBattery1DischargeEndvoltageLowCurrent(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				
				return data;
				
			} else {
				return new ModelPhoenixContactQuintUPSEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPhoenixContactQuintUPSEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_phoenix_contact_quint_ups
	 * @author Hung.Bui
	 * @since 2024-02-19
	 * @param data from datalogger
	 */
	
	public boolean insertModelPhoenixContactQuintUPS(ModelPhoenixContactQuintUPSEntity obj) {
		try {
			ModelPhoenixContactQuintUPSEntity dataObj = (ModelPhoenixContactQuintUPSEntity) queryForObject("ModelPhoenixContactQuintUPS.getLastRow", obj);
			
			Object insertId = insert("ModelPhoenixContactQuintUPS.insertModelPhoenixContactQuintUPS", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
	        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
	        int hours = zdtNowLosAngeles.getHour();
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelPhoenixContactQuintUPS(obj);
	        	
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
	 * @since 2024-02-19
	 * @param datatablename
	 */

	public ModelPhoenixContactQuintUPSEntity checkAlertWriteCode(ModelPhoenixContactQuintUPSEntity obj) {
		ModelPhoenixContactQuintUPSEntity rowItem = new ModelPhoenixContactQuintUPSEntity();
		try {
//			rowItem = (ModelPhoenixContactQuintUPSEntity) queryForObject("ModelPhoenixContactQuintUPS.checkAlertWriteCode", obj);
			List dataList = queryForList("ModelPhoenixContactQuintUPS.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalStatusAlarm = 0, totalStatusWarning = 0, totalBattery1StateofFuse = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double StatusAlarm = (double) item.get("StatusAlarm");
					if(Double.compare(obj.getStatusAlarm(), StatusAlarm) == 0 && obj.getStatusAlarm() > 0 && StatusAlarm > 0) { 
						totalStatusAlarm++;
					}
					
					double StatusWarning = (double) item.get("StatusWarning");
					if(Double.compare(obj.getStatusWarning(), StatusWarning) == 0 && obj.getStatusWarning() > 0 && StatusWarning > 0) { 
						totalStatusWarning++;
					}
					
					double Battery1StateofFuse = (double) item.get("Battery1StateofFuse");
					if(Double.compare(obj.getBattery1StateofFuse(), Battery1StateofFuse) == 0 && obj.getBattery1StateofFuse() > 0 && Battery1StateofFuse > 0) { 
						totalBattery1StateofFuse++;
					}
				}
				rowItem.setTotalAlarm(totalStatusAlarm);
				rowItem.setTotalWarning(totalStatusWarning);
				rowItem.setTotalFuse1(totalBattery1StateofFuse);
				
			}
			if (rowItem == null)
				return new ModelPhoenixContactQuintUPSEntity();
		} catch (Exception ex) {
			return new ModelPhoenixContactQuintUPSEntity();
		}
		return rowItem;
	}
	
	
	/**
	 * @description check trigger alert fault code
	 * @author Hung.Bui
	 * @since 2024-02-19
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelPhoenixContactQuintUPS(ModelPhoenixContactQuintUPSEntity obj) {
		// Check device alert by fault code
		long alarmCode = (obj.getStatusAlarm() > 0 && obj.getStatusAlarm() != 0.001) ? (long) obj.getStatusAlarm() : 0;
		long warningCode = (obj.getStatusWarning() > 0 && obj.getStatusWarning() != 0.001) ? (long) obj.getStatusWarning() : 0;
		int fuse1Code = (obj.getBattery1StateofFuse() > 0 && obj.getBattery1StateofFuse() != 0.001) ? (int) obj.getBattery1StateofFuse() : 0;
		
		ModelPhoenixContactQuintUPSEntity rowItem = (ModelPhoenixContactQuintUPSEntity) checkAlertWriteCode(obj);
		
		if (alarmCode > 0 && rowItem.getTotalAlarm() >= Constants.TOTAL_CONSECUTIVE_ALARMS) {
			try {
				String toBinary = Long.toBinaryString(alarmCode);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetAlarmCodeModelPhoenixContactQuintUPS(v);
						
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
			// Close warning code 
			try {
				if (rowItem.getTotalAlarm() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is alarm code
					alertItemClose.setFaultCodeLevel(1);
					List activeAlerts = queryForList("ModelPhoenixContactQuintUPS.getListTriggerFaultCode", alertItemClose);
					
					if (activeAlerts.size() > 0) {
						for (int i = 0; i < activeAlerts.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) activeAlerts.get(i);
							int id =  Integer.parseInt(itemFault.get("id").toString());
							int idError =  Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id );
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

		
		if (warningCode > 0  && rowItem.getTotalWarning() >= Constants.TOTAL_CONSECUTIVE_ALARMS) {
			try {
				String toBinary = Long.toBinaryString(warningCode);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GetWarningCodeModelPhoenixContactQuintUPS(v);
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
			// Close status code 
			try {
				if (rowItem.getTotalWarning() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is warning code
					alertItemClose.setFaultCodeLevel(2);
					List activeAlerts = queryForList("ModelPhoenixContactQuintUPS.getListTriggerFaultCode", alertItemClose);
					
					if (activeAlerts.size() > 0) {
						for (int i = 0; i < activeAlerts.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) activeAlerts.get(i);
							int id =  Integer.parseInt(itemFault.get("id").toString());
							int idError =  Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id );
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
		
		
		if (fuse1Code > 0  && rowItem.getTotalFuse1() >= Constants.TOTAL_CONSECUTIVE_ALARMS) {
			try {
				int errorId = LibErrorCode.GetFuse1CodeModelPhoenixContactQuintUPS(fuse1Code);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Close fuse 1 code 
			try {
				if (rowItem.getTotalFuse1() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// Type 3 is fuse 1 status code
					alertItemClose.setFaultCodeLevel(3);
					List activeAlerts = queryForList("ModelPhoenixContactQuintUPS.getListTriggerFaultCode", alertItemClose);
					
					if (activeAlerts.size() > 0) {
						for (int i = 0; i < activeAlerts.size(); i++) {
							Map<String, Object> itemFault = (Map<String, Object>) activeAlerts.get(i);
							int id = Integer.parseInt(itemFault.get("id").toString());
							int idError = Integer.parseInt(itemFault.get("id_error").toString());
							alertItemClose.setEnd_date(itemFault.get("end_date").toString());
							alertItemClose.setId(id );
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
