/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ModelLevitonS40000rPowerMeterEntity;
import com.nwm.api.entities.ModelLevitonVirtualMeterEntity;
import com.nwm.api.utils.Lib;

public class ModelLevitonS40000rPowerMeterService extends DB {
	/**
	 * @description set data 
	 * @author long.pham
	 * @since 2024-03-21
	 * @param data
	 */
	
	public ModelLevitonS40000rPowerMeterEntity setModelLevitonS40000rPowerMeter(String line, double offset_data_old) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelLevitonS40000rPowerMeterEntity dataModel = new ModelLevitonS40000rPowerMeterEntity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001");
				if(energy > 0) { energy = energy + offset_data_old; }
				
				
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModel.setRealEnergyConsumption(energy);
				dataModel.setTotalInstantaneousRealPower(power);
				dataModel.setTotalInstantaneousReactivePower(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setTotalInstantaneousApparentPower(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setTotalPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setVoltageLL3pAve(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setVoltageLN3pAve(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModel.setCurrent3pAve(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setRealPowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				
				dataModel.setRealPowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setRealPowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setPowerFactorPhaseA(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setPowerFactorPhaseB(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setPowerFactorPhaseC(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setVoltagePhaseAB(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setVoltagePhaseBC(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModel.setVoltagePhaseAC(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setVoltagePhaseAN(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setVoltagePhaseBN(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				
				dataModel.setVoltagePhaseCN(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setCurrentInstantaneousPhaseA(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setCurrentInstantaneousPhaseB(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setCurrentInstantaneousPhaseC(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setFrequency(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setApparentEnergyConsumption(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setReactiveEnergyConsumption(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setApparentPowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModel.setApparentPowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setApparentPowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				
				dataModel.setReactivePowerPhaseA(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setReactivePowerPhaseB(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setReactivePowerPhaseC(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				
				
				dataModel.setTotalRealPowerPresentDemand(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setTotalReactivePowerPresentDemand(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModel.setTotalApparentPowerPresentDemand(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setTotalRealPowerMaxDemand(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setTotalReactivePowerMaxDemand(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				return dataModel;
				
			} else {
				return new ModelLevitonS40000rPowerMeterEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelLevitonS40000rPowerMeterEntity();
		}
	}


	/**
	 * @description insert data from datalogger to ModelLevitonS40000rPowerMeter
	 * @author long.pham
	 * @since 2023-01-16
	 * @param data from datalogger
	 */
	
	public boolean insertModelLevitonS40000rPowerMeter(ModelLevitonS40000rPowerMeterEntity obj) {
		try {
			ModelLevitonS40000rPowerMeterEntity dataObj = (ModelLevitonS40000rPowerMeterEntity) queryForObject("ModelLevitonS40000rPowerMeter.getLastRow", obj);
			 double measuredProduction = 0;
			 if(dataObj != null && dataObj.getId_device() > 0 && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() != 0.001 ) {
				 measuredProduction = obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy();
				 if(measuredProduction < 0 ) { measuredProduction = 0;}
				 
//				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
//					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
//				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelLevitonS40000rPowerMeter.insertModelLevitonS40000rPowerMeter", obj);
		     if(insertId == null ) {
		    	 return false;
		     }
		     
		  // Insert data to virtual meter
				List dataList = queryForList("ModelLevitonVirtualMeter.getListVirtualMeterParameter", obj);
				DeviceEntity objDevice = (DeviceEntity) queryForObject("ModelLevitonVirtualMeter.getDeviceVirtualMeter", obj);
					
					
				double power = 0, energy = 0;
				if (dataList != null && objDevice.getId() > 0) {
					for (int i = 0; i < (dataList.size()); i++) {
						Map<String, Object> itemField = (Map<String, Object>) dataList.get(i);
						switch(Integer.parseInt(itemField.get("id_device_parameter").toString())) {
						case 2852:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { power = power + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { power = power + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { power = power + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { power = power + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { power = power + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { power = power + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { power = power + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { power = power + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { power = power + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { power = power + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { power = power + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { power = power + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { power = power + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { power = power + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { power = power + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { power = power + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { power = power + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { power = power + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { power = power + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { power = power + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { power = power + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { power = power + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { power = power + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { power = power + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { power = power + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { power = power + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { power = power + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { power = power + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { power = power + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { power = power + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { power = power + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { power = power + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { power = power + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { power = power + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { power = power + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { power = power + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { power = power + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2853:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { energy = energy + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { energy = energy + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { energy = energy + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { energy = energy + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { energy = energy + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { energy = energy + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { energy = energy + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { energy = energy + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { energy = energy + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { energy = energy + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { energy = energy + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { energy = energy + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { energy = energy + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { energy = energy + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { energy = energy + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { energy = energy + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { energy = energy + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { energy = energy + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { energy = energy + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { energy = energy + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { energy = energy + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { energy = energy + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { energy = energy + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { energy = energy + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { energy = energy + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { energy = energy + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { energy = energy + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { energy = energy + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { energy = energy + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { energy = energy + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { energy = energy + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { energy = energy + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { energy = energy + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { energy = energy + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { energy = energy + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { energy = energy + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { energy = energy + obj.getTotalReactivePowerMaxDemand(); }
							break;
						}
					}
					ModelLevitonVirtualMeterEntity virtualMeterEntity = new ModelLevitonVirtualMeterEntity();
					virtualMeterEntity.setTime(obj.getTime());
					virtualMeterEntity.setId_device(objDevice.getId());
					virtualMeterEntity.setId_device_map(obj.getId_device());
					virtualMeterEntity.setDatatablename(objDevice.getDatatablename());
					virtualMeterEntity.setField0(power);
					virtualMeterEntity.setField1(energy);
					ModelLevitonAbviusA891123ChannelService abvService = new ModelLevitonAbviusA891123ChannelService();
					Object insertVirtualMeterId = abvService.insertModelLevitonVirtualMeter(virtualMeterEntity);
					
				}
		     return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}

}
