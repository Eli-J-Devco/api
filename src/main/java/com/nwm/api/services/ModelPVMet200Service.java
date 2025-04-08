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
import com.nwm.api.entities.ModelPVMet200Entity;
import com.nwm.api.utils.Lib;

public class ModelPVMet200Service extends DB {

	public ModelPVMet200Entity setModelPVMet200(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPVMet200Entity dataPVMet200 = new ModelPVMet200Entity();
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataPVMet200.setTime(words.get(0).replace("'", ""));
				dataPVMet200.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataPVMet200.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataPVMet200.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataPVMet200.setAmbient_Air_Temperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataPVMet200.setRelative_Humidity(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataPVMet200.setBarometric_Pressure(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataPVMet200.setWind_Speed(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataPVMet200.setWind_Direction(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataPVMet200.setRainfall(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));		
				dataPVMet200.setSnowfall(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataPVMet200.setPrecipitation_Type(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));		
				dataPVMet200.setElectric_Field(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataPVMet200.setSurface_Wetness(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataPVMet200.setSoil_Moisture(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataPVMet200.setGlobal_Horizontal_Irradiance(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataPVMet200.setPOA_Irradiance(irradiance);
				dataPVMet200.setDiffuse_Irradiance(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataPVMet200.setDirect_Irradiance(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));		
				dataPVMet200.setOther_Irradiance(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataPVMet200.setBOM_Temp_1(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataPVMet200.setBOM_Temp_2(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataPVMet200.setBOM_Temp_3(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				
				
				
				// set custom field nvm_irradiance
				dataPVMet200.setNvm_irradiance(irradiance);
				dataPVMet200.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataPVMet200.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				
				return dataPVMet200;
				
			} else {
				return new ModelPVMet200Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPVMet200Entity();
		}
	}

	/**
	 * @description insert data from datalogger to model_kippzonen_rt1_class8009
	 * @author long.pham
	 * @since 2021-04-02
	 * @param data from datalogger
	 */
	
	public boolean insertModelPVMet200(ModelPVMet200Entity obj) {
		try {
			 Object insertId = insert("ModelPVMet200.insertModelPVMet200", obj);
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