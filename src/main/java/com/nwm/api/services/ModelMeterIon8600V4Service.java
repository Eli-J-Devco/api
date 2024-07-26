/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelMeterIon8600V4Entity;
import com.nwm.api.utils.Lib;

public class ModelMeterIon8600V4Service extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelMeterIon8600V4Entity setModelMeterIon8600V4(String line, double offset_data_old) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelMeterIon8600V4Entity dataModelIon = new ModelMeterIon8600V4Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001");
				if(energy > 0) { energy = energy + offset_data_old; }
				
				
				dataModelIon.setTime(words.get(0).replace("'", ""));
				dataModelIon.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelIon.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelIon.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelIon.setVlnA(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelIon.setVlnB(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelIon.setVlnC(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelIon.setVlnAve(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelIon.setVllAb(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelIon.setVllAc(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelIon.setVllCa(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelIon.setVllAve(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModelIon.setIA(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				
				dataModelIon.setIB(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelIon.setIC(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelIon.setIAve(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelIon.setVUnbal(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelIon.setIUnbal(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelIon.setFreq(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelIon.setI4(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelIon.setKWA(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelIon.setKWB(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelIon.setKWC(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				
				dataModelIon.setKWTot(power);
				dataModelIon.setKVARA(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelIon.setKVARB(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelIon.setKVARC(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelIon.setKVARTot(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelIon.setKVAA(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelIon.setKVAB(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelIon.setKVAC(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelIon.setKVATot(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelIon.setPFSignA(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				
				dataModelIon.setPFSignB(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelIon.setPFSignC(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelIon.setPFSignTot(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				
				
				dataModelIon.setVIIAveMx(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelIon.setIAveMx(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelIon.setKWTotMx(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelIon.setKVARTotMx(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelIon.setKVATotMx(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelIon.setFreqMx(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				
				dataModelIon.setVIIAveMn(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelIon.setIAveMn(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelIon.setFreqMn(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelIon.setKWSdDelRec(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelIon.setKVASdDelRec(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelIon.setKVARSdDelRec(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelIon.setKWSdMxDR(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelIon.setKVASdMxDR(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelIon.setKVARSdMxDR(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModelIon.setPhaseRev(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModelIon.setKWhDel(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModelIon.setKWhRec(energy);
				dataModelIon.setKWhDelRec(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModelIon.setKWhDel_Rec(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModelIon.setKVARhDel(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModelIon.setKVARhRec(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModelIon.setKVARhDelRec(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModelIon.setKVARhDel_Rec(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModelIon.setKVAhDelRec(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModelIon.setV1THDMx(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModelIon.setV2THDMx(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModelIon.setV3THDMx(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModelIon.setI1THDMx(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelIon.setNvmActivePower(power);
				dataModelIon.setNvmActiveEnergy(energy);
				
				return dataModelIon;
				
			} else {
				return new ModelMeterIon8600V4Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelMeterIon8600V4Entity();
		}
	}


	public static Double findMin(List<Double> list) 
    { 
        // check list is empty or not 
        if (list == null || list.size() == 0) { 
            return Double.MAX_VALUE; 
        } 
        // create a new list to avoid modification 
        // in the original list 
        List<Double> sortedlist = new ArrayList<>(list); 
        // sort list in natural order 
        Collections.sort(sortedlist); 
        return sortedlist.get(0); 
    } 
	
	/**
	 * @description insert data from datalogger to model_meter_ion_8600_v3
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelMeterIon8600V4(ModelMeterIon8600V4Entity obj) {
		try {
			ModelMeterIon8600V4Entity dataObj = (ModelMeterIon8600V4Entity) queryForObject("ModelMeterIon8600V4.getLastRow", obj);
			 double measuredProduction = 0, measuredProduction0 = 0,measuredProduction1 = 0, measuredProduction2 = 0;
			 
			 List<Double> listMeasuredProduction = new ArrayList<>();
			 
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getKWhRec() > 0 && obj.getKWhRec() > 0 && obj.getKWhRec() != 0.001 ) {
				 measuredProduction0 = obj.getKWhRec() - dataObj.getKWhRec();
				 if(measuredProduction0 < 0 ) { measuredProduction0 = 0;}
				 listMeasuredProduction.add(measuredProduction0);
			 }
			 
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getKWhDel_Rec() > 0 && obj.getKWhDel_Rec() > 0 && obj.getKWhDel_Rec() != 0.001 ) {
				 measuredProduction1 = obj.getKWhDel_Rec() - dataObj.getKWhDel_Rec();
				 if(measuredProduction1 < 0 ) { measuredProduction1 = 0;}
				 listMeasuredProduction.add(measuredProduction1);
			 }

			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getKWhDelRec() > 0 && obj.getKWhDelRec() > 0 && obj.getKWhDelRec() != 0.001 ) {
				 measuredProduction2 = obj.getKWhDelRec() - dataObj.getKWhDelRec();
				 if(measuredProduction2 < 0 ) { measuredProduction2 = 0;}
				 listMeasuredProduction.add(measuredProduction2);
			 }
			 if(listMeasuredProduction.size() > 0) {
				 measuredProduction = findMin(listMeasuredProduction);
			 }
			 
			 if(measuredProduction > 3000) {
				 switch(dataObj.getData_send_time()) {
				 	// 1: 5 minutes, 2: 15 minutes, 3: 1 minute
					 case 1:
						 measuredProduction = obj.getNvmActivePower() >= 0 ? obj.getNvmActivePower() / (60/5) : 0;
						 break;
					 case 2:
						 measuredProduction = obj.getNvmActivePower() >= 0 ? obj.getNvmActivePower() / (60/15) : 0;
						 break;
					 case 3:
						 measuredProduction = obj.getNvmActivePower() >= 0 ? obj.getNvmActivePower() / (60/60) : 0;
						 break;
				 }
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelMeterIon8600V4.insertModelMeterIon8600V4", obj);
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
