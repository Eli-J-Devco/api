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
import com.nwm.api.entities.ModelKippZonenRT1Class8009Entity;
import com.nwm.api.utils.Lib;

public class ModelKippZonenRT1Class8009Service extends DB {
	/**
	 * @description set data ModelKippZonenRT1Class8009
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelKippZonenRT1Class8009Entity setModelKippZonenRT1Class8009(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelKippZonenRT1Class8009Entity dataKippZonen = new ModelKippZonenRT1Class8009Entity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataKippZonen.setTime(words.get(0).replace("'", ""));
				dataKippZonen.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataKippZonen.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataKippZonen.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataKippZonen.setDevice_type(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataKippZonen.setData_model_version(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataKippZonen.setOperational_mode(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataKippZonen.setStatus_flags(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataKippZonen.setSensor1_data(irradiance);
				dataKippZonen.setPanel_temperature(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataKippZonen.setExternal_power_sensor(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataKippZonen.setCalibration_date(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataKippZonen.setError_code(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataKippZonen.setProtocol_error(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataKippZonen.setBatch_number(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataKippZonen.setSerial_number(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataKippZonen.setSoftware_version(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataKippZonen.setHardware_version(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataKippZonen.setNode_id(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				
				// set custom field nvm_irradiance
				dataKippZonen.setNvm_irradiance(irradiance);
				dataKippZonen.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataKippZonen.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				
				return dataKippZonen;
				
			} else {
				return new ModelKippZonenRT1Class8009Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelKippZonenRT1Class8009Entity();
		}
	}

	/**
	 * @description insert data from datalogger to model_kippzonen_rt1_class8009
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelKippZonenRT1Class8009(ModelKippZonenRT1Class8009Entity obj) {
		try {
			 Object insertId = insert("ModelKippZonenRT1Class8009.insertModelKippZonenRT1Class8009", obj);
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
