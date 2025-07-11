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
import com.nwm.api.entities.ModelSmaShp7510Entity;
import com.nwm.api.utils.Lib;

public class ModelSmaShp7510Service extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelSmaShp7510Entity setModelSmaShp7510(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSmaShp7510Entity dataModel = new ModelSmaShp7510Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001");
				
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setACcurrentA(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setCurrentlineconductorL1(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setCurrentlineconductorL2(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setCurrentlineconductorL3(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setScalefactorcurrent(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setVoltagelineconductorL1toL2(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setVoltagelineconductorL2toL3(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModel.setVoltagelineconductorL3toL1(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setVoltagelineconductorL1toN(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setVoltagelineconductorL2toN(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setVoltagelineconductorL3toN(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setScalefactorvoltage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setActivepower(power);
				dataModel.setScalefactoractivepower(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setPowerfrequency(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setScalefactorpowerfrequency(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModel.setApparentpower(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setScalefactorapparentpower(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setReactivepower(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setScalefactorreactivepower(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setDisplacementpowerfactorcos(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setScalefactordisplacementpowerfactor(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setTotalyield(energy);
				dataModel.setCalefactortotalyield(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setDCcurrent(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setDCvoltage(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setScalefactorDCvoltage(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModel.setDCpower(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setScalefactorDCpower(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setInternaltemperature(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setHeatsinktemperature(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setTransformer(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setOthertemperature(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setScalefactortemperature(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setOperatingstatus(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setManufacturerspecificstatuscode(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setEventnumberEvt1(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModel.setEventnumberEvt2(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				
				dataModel.setManufacturerspecificeventcodeEvtVnd1(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setManufacturerspecificeventcodeEvtVnd2(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setManufacturerspecificeventcodeEvtVnd3(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setManufacturerspecificeventcodeEvtVnd4(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				
				
				
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelSmaShp7510Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSmaShp7510Entity();
		}
	}

	
	

	/**
	 * @description insert data from datalogger to model_ivt_solaron_ext
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelSmaShp7510(ModelSmaShp7510Entity obj) {
		try {
			ModelSmaShp7510Entity dataObj = (ModelSmaShp7510Entity) queryForObject("ModelSmaShp7510.getLastRow", obj);
			
			// filter data 
			if(dataObj != null && ( obj.getError() > 0 || obj.getNvmActiveEnergy() < dataObj.getNvmActiveEnergy() || obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setTotalyield(dataObj.getNvmActiveEnergy());
				obj.setActivepower(0);
			}
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
		 	Object insertId = insert("ModelSmaShp7510.insertModelSmaShp7510", obj);
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
