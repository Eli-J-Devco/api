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
import com.nwm.api.entities.ModelRT1Class30000Entity;
import com.nwm.api.utils.Lib;

public class ModelRT1Class30000Service extends DB {
	/**
	 * @description set data ModelRT1Class30000
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelRT1Class30000Entity setModelRT1Class30000(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelRT1Class30000Entity dataModelRTC30000 = new ModelRT1Class30000Entity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataModelRTC30000.setTime(words.get(0).replace("'", ""));
				dataModelRTC30000.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelRTC30000.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelRTC30000.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelRTC30000.setDevice_type(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelRTC30000.setData_model_version(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelRTC30000.setOperational_mode(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelRTC30000.setStatus_flags(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelRTC30000.setSensor1_data(irradiance);
				dataModelRTC30000.setPanel_temperature(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelRTC30000.setExternal_power_sensor(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelRTC30000.setCalibration_date(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelRTC30000.setError_code(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelRTC30000.setProtocol_error(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelRTC30000.setBatch_number(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelRTC30000.setSerial_number(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelRTC30000.setSoftware_version(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelRTC30000.setHardware_version(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelRTC30000.setNode_id(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				

				// set custom field nvm_irradiance
				dataModelRTC30000.setNvm_irradiance(irradiance);
				dataModelRTC30000.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				
				
				return dataModelRTC30000;
				
			} else {
				return new ModelRT1Class30000Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelRT1Class30000Entity();
		}
	}

	/**
	 * @description insert data from datalogger to model_rt1_class30000
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelRT1Class30000(ModelRT1Class30000Entity obj) {
		try {
			 Object insertId = insert("ModelRT1Class30000.insertModelRT1Class30000", obj);
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
