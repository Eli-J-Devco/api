/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;
import com.nwm.api.entities.ModelMetterSchneiderPM2200Entity;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.utils.Lib;

@Service
public class ModelMetterSchneiderPM2200Service extends DB {

	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data
	 */
	
	public ModelMetterSchneiderPM2200Entity setModelMetterSchneiderPM2200(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelMetterSchneiderPM2200Entity dataModel = new ModelMetterSchneiderPM2200Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setPositiveactiveelectricity(energy);
				dataModel.setNegativeactiveelectricity(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setTotalactiveelectricity(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setActiveelectricity(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setPositivereactiveelectricity(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setNegativereactiveelectricity(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setTotalreactiveelectricity(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setReactiveelectricity(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setPositiveapparentelectricity(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setNegativeapparentelectricity(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setTotalapparentelectricity(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setApparentelectricity(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setRate1value(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setRate2value(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setPhaseAcurrent(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setPhaseBcurrent(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setPhaseCcurrent(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setPhaseNcurrent(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setPhaseGcurrent(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setAveragecurrent(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setPhaseAcurrentunbalance(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setPhaseBcurrentunbalance(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setPhaseCcurrentunbalance(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setWorstcurrentunbalance(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setABlinevoltage(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setBClinevoltage(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setCAlinevoltage(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setAverageLLlinevoltage(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setPhaseAvoltage(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setPhaseBvoltage(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setPhaseCvoltage(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setAveragePhasevoltage(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setABlinevoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setBClinevoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setCAlinevoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setWorstLLlinevoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setPhaseAvoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModel.setPhaseBvoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setPhaseCvoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setWorstPhasevoltageunbalance(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setPhaseAactivepower(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setPhaseBactivepower(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModel.setPhaseCactivepower(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setActivepower(power);
				dataModel.setPhaseAreactivepower(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModel.setPhaseBreactivepower(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModel.setPhaseCreactivepower(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModel.setReactivepower(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModel.setPhaseAapparentpower(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModel.setPhaseBapparentpower(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModel.setPhaseCapparentpower(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModel.setApparentpower(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModel.setPhaseApowerfactor(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModel.setPhaseBpowerfactor(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModel.setPhaseCpowerfactor(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModel.setPowerfactor(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModel.setPhaseAdisplacementpowerfactor(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModel.setPhaseBdisplacementpowerfactor(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModel.setPhaseCdisplacementpowerfactor(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModel.setDisplacementpowerfactor(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModel.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModel.setPowerfactorIEC(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModel.setPowerfactorLeadLag(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModel.setPowerfactorIECint(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModel.setPowerfactorLeadLagint(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModel.setAccumulatedenergyresetdatetime(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModel.setPositiveactiveelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				dataModel.setNegativeactiveelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModel.setTotalactiveelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModel.setActiveelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModel.setPositivereactiveelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModel.setNegativereactiveelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModel.setTotalreactiveelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModel.setReactiveelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModel.setPositiveapparentelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModel.setNegativeapparentelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModel.setTotalapparentelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				dataModel.setApparentelectricity_INT64(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelMetterSchneiderPM2200Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelMetterSchneiderPM2200Entity();
		}
	}


	/**
	 * @description insert data from datalogger to model_meter_ion_8600
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelMetterSchneiderPM2200(ModelMetterSchneiderPM2200Entity obj) {
		try {
			Object insertId = insert("ModelMetterSchneiderPM2200.insertModelMetterSchneiderPM2200", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        return true;
		} catch (Exception ex) {
			log.error("insertModelMetterSchneiderPM2200", ex);
			return false;
		}

	}
}

