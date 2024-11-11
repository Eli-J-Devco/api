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
import com.nwm.api.entities.ModelXGI1500Entity;
import com.nwm.api.utils.Lib;

public class ModelXGI1500Service extends DB {

	/**
	 * @description set data ModelXGI1500
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelXGI1500Entity setModelXGI1500(String line, double offset_data_old) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelXGI1500Entity dataModelXGI1500 = new ModelXGI1500Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001");
				if(energy < 0 ) { energy = energy * -1; } 
				if(offset_data_old > 0 && energy > 0 ) { energy = energy + offset_data_old; }
				
				
				
				dataModelXGI1500.setTime(words.get(0).replace("'", ""));
				dataModelXGI1500.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelXGI1500.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelXGI1500.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModelXGI1500.setACCurrentAverage(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelXGI1500.setACCurrentA(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelXGI1500.setACCurrentB(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelXGI1500.setACCurrentC(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelXGI1500.setACVoltageAB(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelXGI1500.setACVoltageBC(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelXGI1500.setACVoltageCA(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModelXGI1500.setACVoltageAN(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelXGI1500.setACVoltageBN(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelXGI1500.setACVoltageCN(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelXGI1500.setActivePower(power);
				dataModelXGI1500.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelXGI1500.setApparentPower(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelXGI1500.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelXGI1500.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelXGI1500.setActiveEnergyGross(energy);
				
				dataModelXGI1500.setActiveEnergyRawNet(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelXGI1500.setDCCurrentTotal(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelXGI1500.setDCPowerTotal(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelXGI1500.setDCVoltageAvg(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelXGI1500.setOperatingState(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelXGI1500.setOperatingStatus(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelXGI1500.setFaultStatus(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelXGI1500.setFault1(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelXGI1500.setFault2(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelXGI1500.setFault3(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelXGI1500.setSerialNumberHex4Reg(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModelXGI1500.setCabinetTemperature(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelXGI1500.setHeatSinkTemperature(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelXGI1500.setOtherTemperature(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelXGI1500.setCurrentscalefactor(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelXGI1500.setVoltageScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelXGI1500.setActivePowerScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelXGI1500.setFrequencyScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelXGI1500.setApparentPowerScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelXGI1500.setReactivePowerScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelXGI1500.setPowerFactorScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				dataModelXGI1500.setEnergyScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelXGI1500.setDCCurrentscalefactor(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelXGI1500.setDCVoltagescalefactor(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				
				dataModelXGI1500.setDCPowerScaleFactor(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelXGI1500.setID(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelXGI1500.setLength(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelXGI1500.setManufacturer(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelXGI1500.setModel(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelXGI1500.setControlDeviceOnOff(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelXGI1500.setActivePowerSetpointPercent(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModelXGI1500.setActivePowerControlEnable(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModelXGI1500.setPowerFactorSetpoint(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModelXGI1500.setPowerFactorControlEnable(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModelXGI1500.setReactivePowerSetpointPercent(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModelXGI1500.setReactivePowerControlEnable(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelXGI1500.setNvmActivePower(power);
				dataModelXGI1500.setNvmActiveEnergy(energy);
				
				
				return dataModelXGI1500;
				
			} else {
				return new ModelXGI1500Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelXGI1500Entity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelXGI1500(ModelXGI1500Entity obj) {
		try {
			 ModelXGI1500Entity dataObj = (ModelXGI1500Entity) queryForObject("ModelXGI1500.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
			 }
			 
			 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
				 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				 obj.setActiveEnergyGross(dataObj.getNvmActiveEnergy());
			 }
			 
			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelXGI1500.insertModelXGI1500", obj);
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
