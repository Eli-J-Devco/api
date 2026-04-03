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
import com.nwm.api.entities.ModelPVHTboxEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelPVHTboxService extends DB {

	public ModelPVHTboxEntity setModelPVHTbox(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPVHTboxEntity dataModel = new ModelPVHTboxEntity();
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModel.setControlTriggersWord(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setManualElevationTarget(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setOperationMode(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setAngleSetpoint(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setAnglePosition(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setBatteryLevel(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setAlarms(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setMaxMotorCurrent(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setWarnings(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setPositionCode(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setNotifications(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				return dataModel;
				
			} else {
				return new ModelPVHTboxEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPVHTboxEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_kippzonen_rt1_class8009
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelPVHTbox(ModelPVHTboxEntity obj) {
		try {
			 Object insertId = insert("ModelPVHTbox.insertModelPVHTbox", obj);
		        if(insertId == null ) {
		        	return false;
		        }
		        
		        
		        ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
				ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
				int hours = zdtNow.getHour();
		        
		        if (hours >= 9 && hours <= 17 && obj.getEnable_alert() >= 1) {
		        	checkTriggerAlertModelPVHTbox(obj);
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
	 * @since 2021-05-18
	 * @param datatablename
	 */

	public ModelPVHTboxEntity checkAlertWriteCode(ModelPVHTboxEntity obj) {
		ModelPVHTboxEntity rowItem = new ModelPVHTboxEntity();
		try {
			
			List dataList = queryForList("ModelPVHTbox.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalFault1 = 0, totalFault2 = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double Alarms = (double) item.get("Alarms");
					if(Double.compare(obj.getAlarms(), Alarms) == 0 && obj.getAlarms() > 0 && Alarms > 0) { 
						totalFault1++;
					}
					
					double Warnings = (double) item.get("Warnings");
					if(Double.compare(obj.getWarnings(), Warnings) == 0 && obj.getWarnings() > 0 && Warnings > 0) { 
						totalFault2++;
					}
				}
				rowItem.setTotalAlarm(totalFault1);
				rowItem.setTotalWarning(totalFault2);
				
				
			}
			
			if (rowItem == null)
				return new ModelPVHTboxEntity();
		} catch (Exception ex) {
			return new ModelPVHTboxEntity();
		}
		return rowItem;
	}
	
	
	
	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since 2022-09-26
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelPVHTbox(ModelPVHTboxEntity obj) {
		// Check device alert by fault code
		long fault1 = (obj.getAlarms() > 0 && obj.getAlarms() != 0.001) ? (long) obj.getAlarms() : 0;
		long fault2 = (obj.getWarnings() > 0 && obj.getWarnings() != 0.001) ? (long) obj.getWarnings() : 0;
		
		
		ModelPVHTboxEntity rowItem = (ModelPVHTboxEntity) checkAlertWriteCode(obj);
		
		if(fault1 > 0 && rowItem.getTotalWarning() >= 20) {
			try {
				String toBinary = Long.toBinaryString(fault1);
				String toBinary32Bit = String.format("%32s", toBinary).replaceAll(" ", "0");
				int v = 0;
				for (int b = toBinary32Bit.length() - 1; b >= 0; b--) {
					int index = b;
					int bitLevel = Integer.parseInt(toBinary32Bit.substring(index, Math.min(index + 1, toBinary32Bit.length())));
					if (bitLevel == 1) {
						int errorId = LibErrorCode.GeAlarmPVHTbox(v);
						
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
				if(rowItem.getTotalWarning() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 6 is warning code
					alertItemClose.setFaultCodeLevel(1);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelPVHTbox.getListTriggerFaultCode", alertItemClose);
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