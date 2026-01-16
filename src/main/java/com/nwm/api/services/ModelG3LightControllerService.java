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
import com.nwm.api.entities.ModelG3LightControllerEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelG3LightControllerService extends DB {

	/**
	 * @description set data ModelG3LightController
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelG3LightControllerEntity setModelG3LightController(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelG3LightControllerEntity dataModelG3LightController = new ModelG3LightControllerEntity();
				
				dataModelG3LightController.setTime(words.get(0).replace("'", ""));
				dataModelG3LightController.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelG3LightController.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelG3LightController.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelG3LightController.setInputsStatus132(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelG3LightController.setInputsManualFlag132(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelG3LightController.setZonesStatus132(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelG3LightController.setZonesManualFlag132(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelG3LightController.setZonesFeedbackState132(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelG3LightController.setZonesAlarmState132(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelG3LightController.setBreakersPanel0L(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModelG3LightController.setBreakersPanel0R(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelG3LightController.setBreakersPanel1L(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelG3LightController.setBreakersPanel1R(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelG3LightController.setBreakersPanel2L(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelG3LightController.setBreakersPanel2R(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				
				
				dataModelG3LightController.setBreakerPresentPanel0L(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelG3LightController.setBreakerPresentPanel0R(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelG3LightController.setBreakerPresentPanel1L(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				
				dataModelG3LightController.setBreakerPresentPanel1R(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelG3LightController.setBreakerPresentPanel2L(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelG3LightController.setBreakerPresentPanel2R(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				
				
				dataModelG3LightController.setNonRespondingBreakersPanel0L(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel0R(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel1L(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel1R(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel2L(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelG3LightController.setNonRespondingBreakersPanel2R(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelG3LightController.setScheduleStatus116(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelG3LightController.setSchedule1PeriodStatus124(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				
				
				return dataModelG3LightController;
				
			} else {
				return new ModelG3LightControllerEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelG3LightControllerEntity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelG3LightController(ModelG3LightControllerEntity obj) {
		try {			 
			Object insertId = insert("ModelG3LightController.insertModelG3LightController", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        ModelG3LightControllerEntity dataObj = (ModelG3LightControllerEntity) queryForObject("ModelG3LightController.getLastRow", obj);
	        
	        ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
			ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
			int hours = zdtNow.getHour();
	        
	        if (hours >= 9 && hours <= 17 && dataObj.getEnable_alert() >= 1) {
	        	checkTriggerAlertModelG3LightController(obj);
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

	public ModelG3LightControllerEntity checkAlertWriteCode(ModelG3LightControllerEntity obj) {
		ModelG3LightControllerEntity rowItem = new ModelG3LightControllerEntity();
		try {
//			rowItem = (ModelXantrexGT100250500Entity) queryForObject("ModelXantrexGT100250500.checkAlertWriteCode", obj);
			List dataList = queryForList("ModelG3LightController.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalNoComm = 0;
				int totalZonesAlarmState132 = 0;
				int totalBreakersPanel0L = 0;
				int totalBreakersPanel0R = 0;
				int totalBreakersPanel1L = 0;
				int totalBreakersPanel1R = 0;
				int totalBreakersPanel2L = 0;
				int totalBreakersPanel2R = 0;
				
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double NoCom = (double) item.get("error");
					double ZonesAlarmState132 = (double) item.get("ZonesAlarmState132");
					double BreakersPanel0L = (double) item.get("BreakersPanel0L");
					double NonRespondingBreakersPanel0L = (double) item.get("NonRespondingBreakersPanel0L");
					double BreakersPanel0R = (double) item.get("BreakersPanel0R");
					double NonRespondingBreakersPanel0R = (double) item.get("NonRespondingBreakersPanel0R");
					double BreakersPanel1L = (double) item.get("BreakersPanel1L");
					double NonRespondingBreakersPanel1L = (double) item.get("NonRespondingBreakersPanel1L");
					double BreakersPanel1R = (double) item.get("BreakersPanel1R");
					double NonRespondingBreakersPanel1R = (double) item.get("NonRespondingBreakersPanel1R");
					double BreakersPanel2L = (double) item.get("BreakersPanel2L");
					double NonRespondingBreakersPanel2L = (double) item.get("NonRespondingBreakersPanel2L");
					double BreakersPanel2R = (double) item.get("BreakersPanel2R");
					double NonRespondingBreakersPanel2R = (double) item.get("NonRespondingBreakersPanel2R");
					
					if(NoCom > 0) { 
						totalNoComm++;
					}
					
					if(ZonesAlarmState132 != 0 && ZonesAlarmState132 <= 1) { 
						totalZonesAlarmState132++;
					}
					
					if(BreakersPanel0L == 1 && NonRespondingBreakersPanel0L == 1) { 
						totalBreakersPanel0L++;
					}
					
					if(BreakersPanel0R == 1 && NonRespondingBreakersPanel0R == 1) { 
						totalBreakersPanel0R++;
					}
					
					if(BreakersPanel1L == 1 && NonRespondingBreakersPanel1L == 1) { 
						totalBreakersPanel1L++;
					}
					
					if(BreakersPanel1R == 1 && NonRespondingBreakersPanel1R == 1) { 
						totalBreakersPanel1R++;
					}
					
					if(BreakersPanel2L == 1 && NonRespondingBreakersPanel2L == 1) { 
						totalBreakersPanel2L++;
					}
					
					if(BreakersPanel2R == 1 && NonRespondingBreakersPanel2R == 1) { 
						totalBreakersPanel2R++;
					}
					
				}
				rowItem.setTotalNoComm(totalNoComm);
				rowItem.setTotalZonesAlarmState132(totalZonesAlarmState132);			
				rowItem.setTotalBreakersPanel0L(totalBreakersPanel0L);
				rowItem.setTotalBreakersPanel0R(totalBreakersPanel0R);
				rowItem.setTotalBreakersPanel1L(totalBreakersPanel1L);
				rowItem.setTotalBreakersPanel1R(totalBreakersPanel1R);
				rowItem.setTotalBreakersPanel2L(totalBreakersPanel2L);
				rowItem.setTotalBreakersPanel2R(totalBreakersPanel2R);
			}
			
			if (rowItem == null)
				return new ModelG3LightControllerEntity();
		} catch (Exception ex) {
			log.error("ModelG3LightController.checkAlertWriteCode", ex);
			return new ModelG3LightControllerEntity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author duy.phan
	 * @since 2023-05-23
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelG3LightController(ModelG3LightControllerEntity obj) {
		// Check device alert by fault code
		
		ModelG3LightControllerEntity rowItem = (ModelG3LightControllerEntity) checkAlertWriteCode(obj);
		
		if(obj.getError() > 0 && rowItem.getTotalNoComm() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelG3LightController(1);	
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
				if(rowItem.getTotalNoComm() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 1 is error code
					alertItemClose.setFaultCodeLevel(1);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelG3LightController.getListTriggerFaultCode", alertItemClose);
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
		
		if(obj.getZonesAlarmState132() != 0 && obj.getZonesAlarmState132() <= 1 && rowItem.getTotalZonesAlarmState132() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelG3LightController(2);	
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
				if(rowItem.getTotalZonesAlarmState132() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is error code
					alertItemClose.setFaultCodeLevel(2);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelG3LightController.getListTriggerFaultCode", alertItemClose);
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
		
		if(obj.getBreakersPanel0L() == 1 && obj.getNonRespondingBreakersPanel0L() == 1 && rowItem.getTotalBreakersPanel0L() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelG3LightController(3);	
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
				if(rowItem.getTotalBreakersPanel0L() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is error code
					alertItemClose.setFaultCodeLevel(3);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelG3LightController.getListTriggerFaultCode", alertItemClose);
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
		
		if(obj.getBreakersPanel0R() == 1 && obj.getNonRespondingBreakersPanel0R() == 1 && rowItem.getTotalBreakersPanel0R() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelG3LightController(4);	
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
				if(rowItem.getTotalBreakersPanel0R() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is error code
					alertItemClose.setFaultCodeLevel(4);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelG3LightController.getListTriggerFaultCode", alertItemClose);
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
		
		if(obj.getBreakersPanel1L() == 1 && obj.getNonRespondingBreakersPanel1L() == 1 && rowItem.getTotalBreakersPanel1L() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelG3LightController(5);	
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
				if(rowItem.getTotalBreakersPanel1L() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is error code
					alertItemClose.setFaultCodeLevel(5);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelG3LightController.getListTriggerFaultCode", alertItemClose);
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
		
		
		if(obj.getBreakersPanel1R() == 1 && obj.getNonRespondingBreakersPanel1R() == 1 && rowItem.getTotalBreakersPanel1R() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelG3LightController(6);	
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
				if(rowItem.getTotalBreakersPanel1R() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is error code
					alertItemClose.setFaultCodeLevel(6);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelG3LightController.getListTriggerFaultCode", alertItemClose);
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
		
		if(obj.getBreakersPanel2L() == 1 && obj.getNonRespondingBreakersPanel2L() == 1 && rowItem.getTotalBreakersPanel2L() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelG3LightController(7);	
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
				if(rowItem.getTotalBreakersPanel2L() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is error code
					alertItemClose.setFaultCodeLevel(7);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelG3LightController.getListTriggerFaultCode", alertItemClose);
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
		
		if(obj.getBreakersPanel2R() == 1 && obj.getNonRespondingBreakersPanel2R() == 1 && rowItem.getTotalBreakersPanel2R() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelG3LightController(8);	
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
				if(rowItem.getTotalBreakersPanel2R() == 0) {
					AlertEntity alertItemClose = new AlertEntity();
					alertItemClose.setId_device(obj.getId_device());
					// type 2 is error code
					alertItemClose.setFaultCodeLevel(8);
					List dataListWarningCode = new ArrayList();
					dataListWarningCode = queryForList("ModelG3LightController.getListTriggerFaultCode", alertItemClose);
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
