package com.nwm.api.services;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelPVPInverterEntity;
import com.nwm.api.entities.ModelSunnyCentralClass9775InverterEntity;
import com.nwm.api.utils.Lib;

public class ModelSunnyCentralClass9775InverterService extends DB{
	
	
	
	/**
	 * @description set data ModelAdvancedEnergySolaron
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelSunnyCentralClass9775InverterEntity setModelSunnyCentralClass9775Inverter(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelSunnyCentralClass9775InverterEntity dataModelSunnyCentralClass9775Inverter = new ModelSunnyCentralClass9775InverterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelSunnyCentralClass9775Inverter.setTime(words.get(0).replace("'", ""));
				dataModelSunnyCentralClass9775Inverter.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelSunnyCentralClass9775Inverter.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelSunnyCentralClass9775Inverter.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelSunnyCentralClass9775Inverter.setFault(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setOperatingState(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setStatus(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setEventNumber(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setLifekWhTotal(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setPVCurrent(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setPVVoltage(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setPVPower(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				
				dataModelSunnyCentralClass9775Inverter.setACPower(power);
				dataModelSunnyCentralClass9775Inverter.setACVoltageAB(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setACVoltageBC(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(13) : "0.001"));;
				dataModelSunnyCentralClass9775Inverter.setACVoltageCA(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(13) : "0.001"));;
				
				dataModelSunnyCentralClass9775Inverter.setACCurrent(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setACCurrentL1(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setACCurrentL2(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setACCurrentL3(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModelSunnyCentralClass9775Inverter.setACFrequency(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setApparentPower(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setReactivePowerMode(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setPowerFactorFeedback(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setOperatingMode(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setACPowerLimit(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setACVoltage(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setHeatSinkTemp(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setInteriorTemperature(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelSunnyCentralClass9775Inverter.setExternalTemperature(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelSunnyCentralClass9775Inverter.setNvmActivePower(power);
				dataModelSunnyCentralClass9775Inverter.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				return dataModelSunnyCentralClass9775Inverter;
				
			} else {
				return new ModelSunnyCentralClass9775InverterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelSunnyCentralClass9775InverterEntity();
		}
	}
	
	
	
	/**
	 * @description insert data from datalogger to model_sunny_central_class9775_inverter
	 * @author duy.phan
	 * @since 2023-01-11
	 * @param data from datalogger
	 */
	
	public boolean insertModelSunnyCentralClass9775Inverter(ModelSunnyCentralClass9775InverterEntity obj) {
		try {
			 Object insertId = insert("ModelSunnyCentralClass9775Inverter.insertModelSunnyCentralClass9775Inverter", obj);
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
