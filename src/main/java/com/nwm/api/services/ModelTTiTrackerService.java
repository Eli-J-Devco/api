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
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.ModelSolectriaSGI226IVTEntity;
import com.nwm.api.entities.ModelTTiTrackerEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelTTiTrackerService extends DB {

	/**
	 * @description set data ModelTTiTracker
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelTTiTrackerEntity setModelTTiTracker(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelTTiTrackerEntity dataModelTTiTracker = new ModelTTiTrackerEntity();
				
				dataModelTTiTracker.setTime(words.get(0).replace("'", ""));
				dataModelTTiTracker.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelTTiTracker.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelTTiTracker.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelTTiTracker.setMode(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelTTiTracker.setSubMode(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelTTiTracker.setMotorStatus(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				
				
				
				
				double readAngle = Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.0");
				readAngle = Math.round((readAngle * 180) / 3.14); 
				
				double setAngle = Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.0");
				setAngle = Math.round((setAngle * 180) / 3.14);
				
				dataModelTTiTracker.setReadAngle(Double.parseDouble(!Lib.isBlank(words.get(7)) ? String.valueOf(setAngle) : "0.001"));
				dataModelTTiTracker.setSetAngle(Double.parseDouble(!Lib.isBlank(words.get(8)) ? String.valueOf(readAngle) : "0.001"));
				
				
				dataModelTTiTracker.setOptimalAngle(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelTTiTracker.setWindSpeed(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelTTiTracker.setTTiTime(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModelTTiTracker.setMotorFault(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelTTiTracker.setRemoteInterfaceFault(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelTTiTracker.setInclinometerFault(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelTTiTracker.setModbusAddress(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelTTiTracker.setFirmwareVersion(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelTTiTracker.setUnits(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelTTiTracker.setInclinometerOffset(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelTTiTracker.setMotorStopDelay(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelTTiTracker.setCoastAngle(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelTTiTracker.setMaxRotationWest(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				
				dataModelTTiTracker.setMaxRotationEast(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelTTiTracker.setSoftAngleLimitsEnabled(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelTTiTracker.setMotorMonitorSampleTime(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelTTiTracker.setMotorMonitorMinAngle(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelTTiTracker.setEnableMotorMonitor(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelTTiTracker.setDeadBand(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelTTiTracker.setNightTimeStowAltitude(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelTTiTracker.setNightTimeStowAngle(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelTTiTracker.setPoleSpacing(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelTTiTracker.setModuleWidth(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				
				dataModelTTiTracker.setMotorPolarity(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelTTiTracker.setInclinometerPolarity(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelTTiTracker.setLatitude(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelTTiTracker.setLongitude(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelTTiTracker.setLoggingInterval(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelTTiTracker.setHelicalVarationAngle(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelTTiTracker.setDriveArmSlope(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelTTiTracker.setWindConstant(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelTTiTracker.setWindStowSpeed(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelTTiTracker.setWindStowTime(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				
				return dataModelTTiTracker;
				
			} else {
				return new ModelTTiTrackerEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelTTiTrackerEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_tti_tracker
	 * @author long.pham
	 * @since 2020-12-11
	 * @param data from datalogger
	 */

	public boolean insertModelTTiTracker(ModelTTiTrackerEntity obj) {
		try {
			Object insertId = insert("ModelTTiTracker.insertModelTTiTracker", obj);
			if (insertId == null) {
				return false;
			}
			ZoneId zoneIdLosAngeles = ZoneId.of("America/Los_Angeles"); // "America/Los_Angeles"
	        ZonedDateTime zdtNowLosAngeles = ZonedDateTime.now(zoneIdLosAngeles);
	        int hours = zdtNowLosAngeles.getHour();
	        
	        if (hours >= 9 && hours <= 17 && obj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelTTiTracker(obj);
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

	public ModelTTiTrackerEntity checkAlertWriteCode(ModelTTiTrackerEntity obj) {
		ModelTTiTrackerEntity rowItem = new ModelTTiTrackerEntity();
		try {
			rowItem = (ModelTTiTrackerEntity) queryForObject("ModelTTiTracker.checkAlertWriteCode", obj);
			if (rowItem == null)
				return new ModelTTiTrackerEntity();
		} catch (Exception ex) {
			log.error("ModelTTiTracker.checkAlertWriteCode", ex);
			return new ModelTTiTrackerEntity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author long.pham
	 * @since 2022-09-26
	 * @param data from datalogger 
	 */

	public void checkTriggerAlertModelTTiTracker(ModelTTiTrackerEntity obj) {
		
		
		// Check device alert by fault code
		int Mode = (obj.getMode() > 0 && obj.getMode() != 0.001) ? (int) obj.getMode() : 0;
		int SubMode = (obj.getSubMode() > 0 && obj.getSubMode() != 0.001) ? (int) obj.getSubMode() : 0;
		int MotorFault = (obj.getMotorFault() > 0 && obj.getMotorFault() != 0.001) ? (int) obj.getMotorFault() : 0;
		int RemoteInterfaceFault = (obj.getRemoteInterfaceFault() > 0 && obj.getRemoteInterfaceFault() != 0.001) ? (int) obj.getRemoteInterfaceFault() : 0;
		int InclinometerFault = (obj.getInclinometerFault() > 0 && obj.getInclinometerFault() != 0.001) ? (int) obj.getInclinometerFault() : 0;
		
		
		ModelTTiTrackerEntity rowItem = (ModelTTiTrackerEntity) checkAlertWriteCode(obj);
		
		if(Mode == 0 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(0);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 1 is warning code
				alertItemClose.setFaultCodeLevel(1);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		// -------------------------------------------
		if(Mode == 1 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(1);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 2 is warning code
				alertItemClose.setFaultCodeLevel(2);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// -------------------------------------------
		if(Mode == 2 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(2);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 3 is warning code
				alertItemClose.setFaultCodeLevel(3);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// -------------------------------------------
		if(Mode == 3 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(3);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 4 is warning code
				alertItemClose.setFaultCodeLevel(4);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// -------------------------------------------
		if(Mode == 4 && SubMode == 0 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(40);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 5 is warning code
				alertItemClose.setFaultCodeLevel(5);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// -------------------------------------------
		if(Mode == 4 && SubMode == 1 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(41);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 6 is warning code
				alertItemClose.setFaultCodeLevel(6);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// -------------------------------------------
		if(Mode == 4 && SubMode == 2 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(42);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 7 is warning code
				alertItemClose.setFaultCodeLevel(7);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// -------------------------------------------
		if(Mode == 4 && SubMode == 3 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(43);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 8 is warning code
				alertItemClose.setFaultCodeLevel(8);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// -------------------------------------------
		if(Mode == 4 && SubMode == 4 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(44);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 9 is warning code
				alertItemClose.setFaultCodeLevel(9);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// -------------------------------------------
		if(Mode == 4 && SubMode == 5 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(45);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 10 is warning code
				alertItemClose.setFaultCodeLevel(10);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// -------------------------------------------
		if(Mode == 4 && SubMode == 6 && rowItem.getTotalMode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(46);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 11 is warning code
				alertItemClose.setFaultCodeLevel(11);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		// -------------------------------------------
		if(MotorFault == 1 && rowItem.getTotalMotorFault() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(5);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 12 is warning code
				alertItemClose.setFaultCodeLevel(12);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// -------------------------------------------
		if(RemoteInterfaceFault == 1 && rowItem.getTotalRemoteInterfaceFault() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(6);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 13 is warning code
				alertItemClose.setFaultCodeLevel(13);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// -------------------------------------------
		if(InclinometerFault == 1 && rowItem.getTotalInclinometerFault() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertTTiTracker(7);
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
			// Close warning code 
			try {
				AlertEntity alertItemClose = new AlertEntity();
				alertItemClose.setId_device(obj.getId_device());
				// type 14 is warning code
				alertItemClose.setFaultCodeLevel(14);
				List dataListWarningCode = new ArrayList();
				dataListWarningCode = queryForList("ModelTTiTracker.getListTriggerFaultCode", alertItemClose);
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
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
