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
import com.nwm.api.entities.ModelMeterIon6200Entity;
import com.nwm.api.utils.Lib;

public class ModelMeterIon6200Service extends DB {
	/**
	 * @description set data 
	 * @author Hung.Bui
	 * @since 2024-03-18
	 * @param data
	 */
	
	public ModelMeterIon6200Entity setModelMeterIon6200(String line, double offset_data_old) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelMeterIon6200Entity data = new ModelMeterIon6200Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001");
				if(energy > 0) { energy = energy + offset_data_old; }
				
				data.setTime(words.get(0).replace("'", ""));
				data.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				data.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				data.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				data.setVlna(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				data.setVlnb(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				data.setVlnc(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				data.setVlnave(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				data.setVllab(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				data.setVllbc(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				data.setVllca(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				data.setVllave(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				data.setIa(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				data.setIb(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				data.setIc(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				data.setIave(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				data.setIdemand(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				data.setIpeakdemand(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				data.setI4(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				data.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				data.setPFsigntotal(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				data.setPFsigna(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				data.setPFsignb(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				data.setPFsignc(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				data.setkWtotal(power);
				data.setkVARtotal(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				data.setkVAtotal(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				data.setkWa(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				data.setkWb(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				data.setkWc(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				data.setkVARa(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				data.setkVARb(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				data.setkVARc(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				data.setkVAa(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				data.setkVAb(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				data.setkVAc(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				data.setkWdemand(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				data.setkWpeakdemand(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				data.setkVARdemand(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				data.setkVAdemand(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				data.setkVARpeakdemand(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				data.setkVApeakdemand(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				data.setkWhdel(energy);
				data.setkWhrec(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				data.setkVARhdel(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				data.setkVARhrec(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				data.setkVAhdelrec(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				data.setV1THD(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				data.setV2THD(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				data.setV3THD(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				data.setI1THD(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				data.setI2THD(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				data.setI3THD(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				data.setIademand(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				data.setIbdemand(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				data.setIcdemand(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				data.setIapeakdemand(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				data.setIbpeakdemand(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				data.setIcpeakdemand(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				data.setkWhadel(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				data.setkWhbdel(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				data.setkWhcdel(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				data.setkWharec(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				data.setkWhbrec(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				data.setkWhcrec(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				data.setNvmActivePower(power);
				data.setNvmActiveEnergy(energy);
				
				return data;
				
			} else {
				return new ModelMeterIon6200Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelMeterIon6200Entity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_6200
	 * @author Hung.Bui
	 * @since 2024-03-18
	 * @param data from datalogger
	 */
	
	public boolean insertModelMeterIon6200(ModelMeterIon6200Entity obj) {
		try {
			ModelMeterIon6200Entity dataObj = (ModelMeterIon6200Entity) queryForObject("ModelMeterIon6200.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
					 obj.setkWhdel(dataObj.getNvmActiveEnergy());
				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelMeterIon6200.insertModelMeterIon6200", obj);
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
