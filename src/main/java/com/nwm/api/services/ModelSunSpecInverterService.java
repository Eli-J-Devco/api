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
import com.nwm.api.entities.ModelSunSpecInverterEntity;
import com.nwm.api.utils.Lib;

public class ModelSunSpecInverterService extends DB {
	/**
	 * @description set data model_aes_tx_inverter
	 * @author long.pham
	 * @since 2023-08-02
	 * @param data
	 */
	
	public ModelSunSpecInverterEntity setModelSunSpecInverter(String line, double offset_data_old) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSunSpecInverterEntity dataModelSunSpecInverter = new ModelSunSpecInverterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				
				
				dataModelSunSpecInverter.setTime(words.get(0).replace("'", ""));
				dataModelSunSpecInverter.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSunSpecInverter.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSunSpecInverter.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelSunSpecInverter.setACCurrent(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSunSpecInverter.setPhaseACurrent(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSunSpecInverter.setPhaseBCurrent(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSunSpecInverter.setPhaseCCurrent(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSunSpecInverter.setPhaseVoltageAB(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSunSpecInverter.setPhaseVoltageBC(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSunSpecInverter.setPhaseVoltageCA(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelSunSpecInverter.setPhaseVoltageAN(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModelSunSpecInverter.setPhaseVoltageBN(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSunSpecInverter.setPhaseVoltageCN(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSunSpecInverter.setACPower(power);
				dataModelSunSpecInverter.setLineFrequency(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelSunSpecInverter.setACApparentPower(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSunSpecInverter.setACReactivePower(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelSunSpecInverter.setACPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelSunSpecInverter.setACEnergy(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelSunSpecInverter.setDCAmps(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelSunSpecInverter.setDCVoltage(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				
				dataModelSunSpecInverter.setDCPower(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelSunSpecInverter.setCabinetTemperature(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelSunSpecInverter.setHeatSinkTemperature(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelSunSpecInverter.setTransformerTemperature(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelSunSpecInverter.setOtherTemperature(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelSunSpecInverter.setOperatingState(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelSunSpecInverter.setVendorSpecificOperatingState(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelSunSpecInverter.setEventField(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelSunSpecInverter.setReservedforFuture(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelSunSpecInverter.setVendorEvent1(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				
				dataModelSunSpecInverter.setVendorEvent2(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelSunSpecInverter.setVendorEvent3(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelSunSpecInverter.setVendorEvent4(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSunSpecInverter.setNvmActivePower(power);
				dataModelSunSpecInverter.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				return dataModelSunSpecInverter;
				
			} else {
				return new ModelSunSpecInverterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSunSpecInverterEntity();
		}
	}


	/**
	 * @description insert data from datalogger to model_sun_pec_inverter
	 * @author long.pham
	 * @since 2024-09-08
	 * @param data from datalogger
	 */
	
	public boolean insertModelSunSpecInverter(ModelSunSpecInverterEntity obj) {
		try {
			ModelSunSpecInverterEntity dataObj = (ModelSunSpecInverterEntity) queryForObject("ModelSunSpecInverter.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
					 obj.setACEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
		 	Object insertId = insert("ModelSunSpecInverter.insertModelSunSpecInverter", obj);
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
