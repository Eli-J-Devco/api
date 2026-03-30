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
import com.nwm.api.entities.ModelADAM6050TransformerSpecificEntity;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.LibErrorCode;

public class ModelADAM6050TransformerSpecificService extends DB {

	public ModelADAM6050TransformerSpecificEntity setModelADAM6050TransformerSpecific(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelADAM6050TransformerSpecificEntity dataModel = new ModelADAM6050TransformerSpecificEntity();
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModel.setVacuum(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setPressure(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setLiquidLevel(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setLiquidTemperatureHigh(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setLiquidTemperatureWarning(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				
				return dataModel;
				
			} else {
				return new ModelADAM6050TransformerSpecificEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelADAM6050TransformerSpecificEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_kippzonen_rt1_class8009
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelADAM6050TransformerSpecific(ModelADAM6050TransformerSpecificEntity obj) {
		try {
			 Object insertId = insert("ModelADAM6050TransformerSpecific.insertModelADAM6050TransformerSpecific", obj);
		        if(insertId == null ) {
		        	return false;
		        }
		        
		        ZoneId zoneId = ZoneId.of(obj.getTimezone_value());
				ZonedDateTime zdtNow = ZonedDateTime.now(zoneId);
				int hours = zdtNow.getHour();
		        
		        if (hours >= 9 && hours <= 17 && obj.getEnable_alert() >= 1) {
		        	checkTriggerAlertModelADAM6050TransformerSpecific(obj);
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

	public ModelADAM6050TransformerSpecificEntity checkAlertWriteCode(ModelADAM6050TransformerSpecificEntity obj) {
		ModelADAM6050TransformerSpecificEntity rowItem = new ModelADAM6050TransformerSpecificEntity();
		try {
			
			List dataList = queryForList("ModelADAM6050TransformerSpecific.checkAlertWriteCode", obj);
			if(dataList.size() > 0) {
				int totalFaultCode = 0;
				for(int i =0; i < dataList.size(); i ++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					double statusFault = (double) item.get("Vacuum");
					if(Double.compare(obj.getVacuum(), statusFault) == 0 && obj.getVacuum() > 0 && statusFault > 0) { 
						totalFaultCode++;
					}
				}
				rowItem.setTotalFaultCode(totalFaultCode);
			}
			
			if (rowItem == null)
				return new ModelADAM6050TransformerSpecificEntity();
		} catch (Exception ex) {
			log.error("ModelADAM6050TransformerSpecific.checkAlertWriteCode", ex);
			return new ModelADAM6050TransformerSpecificEntity();
		}
		return rowItem;
	}
	
	/**
	 * @description check trigger alert fault code
	 * @author duy.phan
	 * @since 2023-11-16
	 * @param data from datalogger
	 */

	public void checkTriggerAlertModelADAM6050TransformerSpecific(ModelADAM6050TransformerSpecificEntity obj) {
		// Check device alert by fault code
		 int faultCode = (obj.getVacuum() > 0 && obj.getVacuum() != 0.001) ? (int) obj.getVacuum() : 0;
		
		 ModelADAM6050TransformerSpecificEntity rowItem = (ModelADAM6050TransformerSpecificEntity) checkAlertWriteCode(obj);
		
		if(faultCode > 0 && rowItem.getTotalFaultCode() >= 20) {
			try {
				int errorId = LibErrorCode.GetAlertModelADAM6050TransformerSpecific(faultCode);	
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
					dataListWarningCode = queryForList("ModelADAM6050TransformerSpecific.getListTriggerFaultCode", alertItemClose);
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