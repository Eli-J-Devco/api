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
import com.nwm.api.entities.ModelAeRefusolEntity;
import com.nwm.api.utils.Lib;

public class ModelAeRefusolService extends DB {
	
	/**
	 * @description set data ModelSolectriaSGI226IVT
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelAeRefusolEntity setModelAeRefusol(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelAeRefusolEntity dataModelAeR = new ModelAeRefusolEntity();
				
				double power = !Lib.isBlank(words.get(4)) ? Double.parseDouble(words.get(4)) : 0.001;
				if(power < 0) { power = 0.0; }
				
				dataModelAeR.setTime(words.get(0).replace("'", ""));
				dataModelAeR.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelAeR.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelAeR.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelAeR.setACPower(power);
				dataModelAeR.setACVoltageAverageRMS(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelAeR.setACVoltage1RMS(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelAeR.setACVoltage2RMS(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelAeR.setACVoltage3RMS(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelAeR.setACCurrentSum(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelAeR.setACCurrent1(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelAeR.setACCurrent2(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelAeR.setACCurrent3(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelAeR.setACFrequency1(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelAeR.setACFrequency2(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelAeR.setACFrequency3(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelAeR.setDCPower(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelAeR.setDCVoltage(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelAeR.setDCCurrent(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelAeR.setHeatSink(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelAeR.setInterior(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelAeR.setIrradiation(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelAeR.setPanel(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelAeR.setDailyYield(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelAeR.setTotalYield(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelAeR.setOperatingHours(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelAeR.setStatus(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001");
				dataModelAeR.setErrorMessageCode(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001");
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelAeR.setNvmActivePower(power);
				dataModelAeR.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				
				return dataModelAeR;
				
			} else {
				return new ModelAeRefusolEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelAeRefusolEntity();
		}
	}

	/**
	 * @description insert data from datalogger to model_ae_refusol
	 * @author long.pham
	 * @since 2021-12-20
	 * @param data from datalogger
	 */
	
	public boolean insertModelAeRefusol(ModelAeRefusolEntity obj) {
		try {
			ModelAeRefusolEntity dataObj = (ModelAeRefusolEntity) queryForObject("ModelAeRefusol.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelAeRefusol.insertModelAeRefusol", obj);
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
