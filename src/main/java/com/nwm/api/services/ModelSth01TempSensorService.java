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
import com.nwm.api.entities.ModelSth01TempSensorEntity;
import com.nwm.api.utils.Lib;

public class ModelSth01TempSensorService extends DB {
	/**
	 * @description set data ModelSth01TempSensor
	 * @author duy.phan
	 * @since 2024-06-26
	 * @param data
	 */
	
	public ModelSth01TempSensorEntity setModelSth01TempSensor(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSth01TempSensorEntity dataModelSth01TempSensor = new ModelSth01TempSensorEntity();
				
//				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001");
//				if(irradiance < 0) { irradiance = 0.0; };
				
				dataModelSth01TempSensor.setTime(words.get(0).replace("'", ""));
				dataModelSth01TempSensor.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSth01TempSensor.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSth01TempSensor.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelSth01TempSensor.setTEMPRATURE(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSth01TempSensor.setHUMIDITY(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSth01TempSensor.setDEWPOINT(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSth01TempSensor.setRetain(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSth01TempSensor.setModbus_ADDRESS(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSth01TempSensor.setBAUDRATE(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSth01TempSensor.setPROTOCOL(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelSth01TempSensor.setPARITY(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelSth01TempSensor.setDATABITS(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelSth01TempSensor.setSTOPBITS(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSth01TempSensor.setRESPONSEDELY(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelSth01TempSensor.setACTIVE_OUTPUT_INTERVAL(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));				
				// set custom field nvm_irradiance
				
				dataModelSth01TempSensor.setNvm_irradiance(0);
				dataModelSth01TempSensor.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSth01TempSensor.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				
				return dataModelSth01TempSensor;
				
			} else {
				return new ModelSth01TempSensorEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSth01TempSensorEntity();
		}
	}

	/**
	 * @description insert data from datalogger to  ModelSth01TempSensor
	 * @author duy.phan
	 * @since 2024-06-26
	 * @param data from datalogger
	 */
	
	public boolean insertModelSth01TempSensor(ModelSth01TempSensorEntity obj) {
		try {
			 Object insertId = insert("ModelSth01TempSensor.insertModelSth01TempSensor", obj);
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
