/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelHiQGatewayEntity;
import com.nwm.api.utils.Lib;

public class ModelHiQGatewayService extends DB {
	/**
	 * @description set data 
	 * @author Duy.Phan
	 * @since 2025-11-26
	 * @param data
	 */
	
	public ModelHiQGatewayEntity setModelHiQGateway(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHiQGatewayEntity dataModel = new ModelHiQGatewayEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setModbusMapVersion(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setInverterCount(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setSiteStatus(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setTotalActivePower(power);
				dataModel.setTotalEnergy(energy);
				dataModel.setGatewayStatus(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setGatewayUTCTime(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setGatewayActiveFaults(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));				
				dataModel.setGatewayTemperature(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));				
				dataModel.setSerialNumberASCII_1_4(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setSerialNumberASCII_5_8(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setFirmwareVersionASCII(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));	
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelHiQGatewayEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHiQGatewayEntity();
		}
	}
	
	/**
	 * @description insert data from datalogger to ModelHiQGateway
	 * @author Duy.Phan
	 * @since 2025-11-26
	 * @param data from datalogger
	 */
	
	public boolean insertModelHiQGateway(ModelHiQGatewayEntity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setTotalEnergy(energy);
			}
			
			ModelHiQGatewayEntity dataObj = (ModelHiQGatewayEntity) queryForObject("ModelHiQGateway.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setTotalEnergy(dataObj.getNvmActiveEnergy());	
			}
						
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }

			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelHiQGateway.insertModelHiQGateway", obj);
		        if(insertId == null ) {
		        	return false;
		        }
		        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
