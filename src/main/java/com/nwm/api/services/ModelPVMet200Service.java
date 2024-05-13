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
				Double irradiance = Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001");
				if(irradiance < 0) { irradiance = 0.0; };
				
				dataPVMet200.setTime(words.get(0).replace("'", ""));
				dataPVMet200.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataPVMet200.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataPVMet200.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataPVMet200.setC_SunSpec_ID(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataPVMet200.setE_BaseMet_Air_Temperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataPVMet200.setE_Irradiance_Plane_Of_Array_1(irradiance);
				dataPVMet200.setE_BOM_Temp_1(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataPVMet200.setE_BOM_Temp_2(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataPVMet200.setE_BaseMet_Wind_Speed(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));		
				dataPVMet200.setE_BaseMet_Wind_Direction(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataPVMet200.setE_Irradiance_Global_Horizontal_1(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				// set custom field nvm_irradiance
				dataPVMet200.setNvm_irradiance(irradiance);
				dataPVMet200.setNvm_temperature(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataPVMet200.setNvm_panel_temperature(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				
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