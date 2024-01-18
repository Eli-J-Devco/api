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
import com.nwm.api.entities.ModelSungrowLogger1000Entity;
import com.nwm.api.utils.Lib;

public class ModelSungrowLogger1000Service extends DB {
	
	/**
	 * @description set data ModelElkorWattsonPVMeter
	 * @author duy.phan
	 * @since 2023-12-12
	 * @param data
	 */
	
	public ModelSungrowLogger1000Entity setModelSungrowLogger1000(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSungrowLogger1000Entity dataModelSG1000 = new ModelSungrowLogger1000Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001");
				
				dataModelSG1000.setTime(words.get(0).replace("'", ""));
				dataModelSG1000.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSG1000.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSG1000.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelSG1000.setTotalNumberOfConnectedDevices(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSG1000.setTotalFaultDeviceNumber(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSG1000.setTotalActivePower(power);
				dataModelSG1000.setDailyYield(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSG1000.setTotalReactivePower(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSG1000.setTotalYield(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSG1000.setDigitalInputState(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelSG1000.setPT1001(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSG1000.setPT1002(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSG1000.setADC1Voltage(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSG1000.setADC1Current(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSG1000.setADC2Voltage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSG1000.setADC2Current(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSG1000.setADC3Voltage(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelSG1000.setADC3Current(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelSG1000.setADC4Voltage(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelSG1000.setADC4Current(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelSG1000.setLongitude(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelSG1000.setLatitude(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelSG1000.setMaxTotalRatedActivePower(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelSG1000.setMinTotalRatedActivePower(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelSG1000.setMaxTotalRatedReactivePower(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelSG1000.setMinTotalRatedReactivePower(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelSG1000.setActualTotalInverterActivePower(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelSG1000.setActualTotalInverterReactivePower(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelSG1000.setOnOffStateOfDataLogger(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelSG1000.setLockingStateOfDataLogger(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelSG1000.setMinAdjustableActivePower(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelSG1000.setMaxAdjustableActivePower(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelSG1000.setMinAdjustableReactivePower(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelSG1000.setMaxAdjustableReactivePower(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));				
				dataModelSG1000.setRatedActivePower(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelSG1000.setRatedReactivePower(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelSG1000.setNumberOfOnGridDevices(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));				
				dataModelSG1000.setNumberOfOffGridDevices(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSG1000.setNvmActivePower(power);
				dataModelSG1000.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				
				return dataModelSG1000;
				
			} else {
				return new ModelSungrowLogger1000Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSungrowLogger1000Entity();
		}
	}
	
	/**
	 * @description insert data from datalogger to model_sungrow_logger1000
	 * @author duy.phan
	 * @since 2023-12-12
	 * @param data from datalogger
	 */
	
	public boolean insertModelSungrowLogger1000(ModelSungrowLogger1000Entity obj) {
		try {
			ModelSungrowLogger1000Entity dataObj = (ModelSungrowLogger1000Entity) queryForObject("ModelSungrowLogger1000.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelSungrowLogger1000.insertModelSungrowLogger1000", obj);
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
