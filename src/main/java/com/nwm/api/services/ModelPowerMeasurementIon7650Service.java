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
import com.nwm.api.entities.ModelPowerMeasurementIon7650Entity;
import com.nwm.api.utils.Lib;

public class ModelPowerMeasurementIon7650Service extends DB {

	/**
	 * @description set data Model Power Measurement Ion 7650
	 * @author long.pham
	 * @since 2023-09-11
	 * @param data
	 */
	
	public ModelPowerMeasurementIon7650Entity setModelPowerMeasurementIon7650(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelPowerMeasurementIon7650Entity dataModelPP7650 = new ModelPowerMeasurementIon7650Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelPP7650.setTime(words.get(0).replace("'", ""));
				dataModelPP7650.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelPP7650.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelPP7650.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelPP7650.setIa(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelPP7650.setIb(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelPP7650.setIc(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelPP7650.setI4(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelPP7650.setI5(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelPP7650.setIAvg(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelPP7650.setIAvgMn(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModelPP7650.setIAvgMx(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelPP7650.setIAvgMean(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelPP7650.setFreq(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				
				
				dataModelPP7650.setFreqMn(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelPP7650.setFreqMx(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelPP7650.setFreqMean(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelPP7650.setVUnbal(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelPP7650.setIUnbal(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelPP7650.setPhaseRev(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModelPP7650.setVInA(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelPP7650.setVInB(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelPP7650.setVInC(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelPP7650.setVInAvg(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelPP7650.setVInAvgMx(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelPP7650.setUnused1(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelPP7650.setVIIAb(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelPP7650.setVIIBc(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelPP7650.setVIICa(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelPP7650.setVIIAvg(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelPP7650.setVIIAvgMx(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModelPP7650.setVIIAvgMean(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelPP7650.setUnused2(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelPP7650.setUnused3(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelPP7650.setUnused4(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelPP7650.setUnused5(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelPP7650.setkWA(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelPP7650.setkWB(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelPP7650.setkWC(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelPP7650.setkWTot(power);
				dataModelPP7650.setkWTotMax(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				dataModelPP7650.setkVARA(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelPP7650.setkVARB(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelPP7650.setkVARC(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelPP7650.setkVARTot(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelPP7650.setkVARTotMax(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelPP7650.setkVAA(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelPP7650.setkVAB(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelPP7650.setkVAC(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelPP7650.setkVATot(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelPP7650.setkVATotMax(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				
				dataModelPP7650.setUnused6(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModelPP7650.setkWhDel(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModelPP7650.setkWhRec(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModelPP7650.setkVARhDel(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModelPP7650.setkVARhRec(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModelPP7650.setkVARhDelRec(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModelPP7650.setUnused7(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModelPP7650.setUnused8(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModelPP7650.setUnused9(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModelPP7650.setUnused10(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				
				dataModelPP7650.setUnused11(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModelPP7650.setUnused12(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModelPP7650.setUnused13(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModelPP7650.setUnused14(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModelPP7650.setUnused15(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModelPP7650.setUnused16(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModelPP7650.setUnused17(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModelPP7650.setPFSignA(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModelPP7650.setPFSignB(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModelPP7650.setPFSignC(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				
				dataModelPP7650.setPFSignTot(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModelPP7650.setV1THDMx(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModelPP7650.setV2THDMx(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModelPP7650.setV3THDMx(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModelPP7650.setI1THDMx(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModelPP7650.setI2THDMx(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModelPP7650.setI3THDMx(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModelPP7650.setI1KFactor(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModelPP7650.setI2KFactor(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModelPP7650.setI3KFactor(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				
				dataModelPP7650.setI1CrestFactor(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModelPP7650.setI2CrestFactor(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModelPP7650.setI3CrestFactor(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
				
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelPP7650.setNvmActivePower(power);
				dataModelPP7650.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				
				
				return dataModelPP7650;
				
			} else {
				return new ModelPowerMeasurementIon7650Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelPowerMeasurementIon7650Entity();
		}
	}
	/**
	 * @description insert data from data logger to model Power measurement Ion 7650
	 * @author long.pham
	 * @since 2023-09-11
	 * @param data from data logger
	 */
	
	public boolean insertModelPowerMeasurementIon7650(ModelPowerMeasurementIon7650Entity obj) {
		try {
			ModelPowerMeasurementIon7650Entity dataObj = (ModelPowerMeasurementIon7650Entity) queryForObject("ModelPowerMeasurementIon7650.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 }
			 }
			 if(measuredProduction < 0 ) { measuredProduction = 0;}
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelPowerMeasurementIon7650.insertModelPowerMeasurementIon7650", obj);
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
