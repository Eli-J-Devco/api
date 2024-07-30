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
				 
				 if(obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) {
					 obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
					 obj.setRealEnergyConsumption(dataObj.getNvmActiveEnergy());
				 }
			 }

			 obj.setMeasuredProduction(measuredProduction);
			 
			 Object insertId = insert("ModelLevitonS40000rPowerMeter.insertModelLevitonS40000rPowerMeter", obj);
		     if(insertId == null ) {
		    	 return false;
		     }
		     
		  // Insert data to virtual meter
				List dataList = queryForList("ModelLevitonVirtualMeter.getListVirtualMeterParameter", obj);
				DeviceEntity objDevice = (DeviceEntity) queryForObject("ModelLevitonVirtualMeter.getDeviceVirtualMeter", obj);
					
					
				double power = 0, energy = 0, 
						warehouseLights = 0,
						WarehouseLightsDemand = 0,
						OfficeLights = 0,
						OfficeLightsDemand = 0,
						ServerRoom = 0,
						ServerRoomDemand = 0,
						ServerRoomReceptacles = 0,
						ServerRoomReceptaclesDemand = 0,
						ReceptaclesPannel1A = 0,
						ReceptaclesPannel1ADemand = 0,
						ReceptaclesPannel2A = 0,
						ReceptaclesPannel2ADemand = 0,
						ReceptaclesPannel2B = 0,
						ReceptaclesPannel2BDemand = 0,
						Compressor = 0,
						CompressorDemand = 0,
						TonHVACUnit = 0,
						TonHVACUnitDemand = 0,
						HVACLoads = 0,
						HVACLoadsDemand = 0;
				
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
						case 2854:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { warehouseLights = warehouseLights + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { warehouseLights = warehouseLights + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { warehouseLights = warehouseLights + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { warehouseLights = warehouseLights + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { warehouseLights = warehouseLights + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { warehouseLights = warehouseLights + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { warehouseLights = warehouseLights + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { warehouseLights = warehouseLights + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { warehouseLights = warehouseLights + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { warehouseLights = warehouseLights + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { warehouseLights = warehouseLights + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { warehouseLights = warehouseLights + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { warehouseLights = warehouseLights + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { warehouseLights = warehouseLights + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { warehouseLights = warehouseLights + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { warehouseLights = warehouseLights + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { warehouseLights = warehouseLights + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { warehouseLights = warehouseLights + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { warehouseLights = warehouseLights + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { warehouseLights = warehouseLights + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { warehouseLights = warehouseLights + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { warehouseLights = warehouseLights + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { warehouseLights = warehouseLights + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { warehouseLights = warehouseLights + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { warehouseLights = warehouseLights + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { warehouseLights = warehouseLights + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { warehouseLights = warehouseLights + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { warehouseLights = warehouseLights + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { warehouseLights = warehouseLights + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { warehouseLights = warehouseLights + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { warehouseLights = warehouseLights + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { warehouseLights = warehouseLights + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { warehouseLights = warehouseLights + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { warehouseLights = warehouseLights + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { warehouseLights = warehouseLights + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { warehouseLights = warehouseLights + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { warehouseLights =warehouseLights + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2855:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { WarehouseLightsDemand = WarehouseLightsDemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { WarehouseLightsDemand =WarehouseLightsDemand + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2856:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { OfficeLights = OfficeLights + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { OfficeLights = OfficeLights + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { OfficeLights = OfficeLights + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { OfficeLights = OfficeLights + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { OfficeLights = OfficeLights + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { OfficeLights = OfficeLights + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { OfficeLights = OfficeLights + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { OfficeLights = OfficeLights + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { OfficeLights = OfficeLights + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { OfficeLights = OfficeLights + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { OfficeLights = OfficeLights + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { OfficeLights = OfficeLights + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { OfficeLights = OfficeLights + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { OfficeLights = OfficeLights + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { OfficeLights = OfficeLights + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { OfficeLights = OfficeLights + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { OfficeLights = OfficeLights + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { OfficeLights = OfficeLights + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { OfficeLights = OfficeLights + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { OfficeLights = OfficeLights + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { OfficeLights = OfficeLights + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { OfficeLights = OfficeLights + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { OfficeLights = OfficeLights + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { OfficeLights = OfficeLights + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { OfficeLights = OfficeLights + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { OfficeLights = OfficeLights + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { OfficeLights = OfficeLights + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { OfficeLights = OfficeLights + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { OfficeLights = OfficeLights + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { OfficeLights = OfficeLights + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { OfficeLights = OfficeLights + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { OfficeLights = OfficeLights + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { OfficeLights = OfficeLights + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { OfficeLights = OfficeLights + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { OfficeLights = OfficeLights + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { OfficeLights = OfficeLights + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { OfficeLights =OfficeLights + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2857:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { OfficeLightsDemand = OfficeLightsDemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { OfficeLightsDemand = OfficeLightsDemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { OfficeLightsDemand = OfficeLightsDemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { OfficeLightsDemand = OfficeLightsDemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { OfficeLightsDemand = OfficeLightsDemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { OfficeLightsDemand = OfficeLightsDemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { OfficeLightsDemand = OfficeLightsDemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { OfficeLightsDemand = OfficeLightsDemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { OfficeLightsDemand = OfficeLightsDemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { OfficeLightsDemand = OfficeLightsDemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { OfficeLightsDemand = OfficeLightsDemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { OfficeLightsDemand = OfficeLightsDemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { OfficeLightsDemand = OfficeLightsDemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { OfficeLightsDemand = OfficeLightsDemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { OfficeLightsDemand = OfficeLightsDemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { OfficeLightsDemand = OfficeLightsDemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { OfficeLightsDemand = OfficeLightsDemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { OfficeLightsDemand = OfficeLightsDemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { OfficeLightsDemand = OfficeLightsDemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { OfficeLightsDemand = OfficeLightsDemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { OfficeLightsDemand = OfficeLightsDemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { OfficeLightsDemand = OfficeLightsDemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { OfficeLightsDemand = OfficeLightsDemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { OfficeLightsDemand = OfficeLightsDemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { OfficeLightsDemand = OfficeLightsDemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { OfficeLightsDemand = OfficeLightsDemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { OfficeLightsDemand = OfficeLightsDemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { OfficeLightsDemand = OfficeLightsDemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { OfficeLightsDemand = OfficeLightsDemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { OfficeLightsDemand = OfficeLightsDemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { OfficeLightsDemand = OfficeLightsDemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { OfficeLightsDemand = OfficeLightsDemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { OfficeLightsDemand = OfficeLightsDemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { OfficeLightsDemand = OfficeLightsDemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { OfficeLightsDemand = OfficeLightsDemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { OfficeLightsDemand = OfficeLightsDemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { OfficeLightsDemand =OfficeLightsDemand + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2858:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ServerRoom = ServerRoom + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ServerRoom = ServerRoom + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ServerRoom = ServerRoom + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ServerRoom = ServerRoom + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ServerRoom = ServerRoom + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ServerRoom = ServerRoom + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ServerRoom = ServerRoom + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ServerRoom = ServerRoom + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ServerRoom = ServerRoom + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ServerRoom = ServerRoom + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ServerRoom = ServerRoom + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ServerRoom = ServerRoom + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ServerRoom = ServerRoom + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ServerRoom = ServerRoom + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ServerRoom = ServerRoom + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ServerRoom = ServerRoom + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ServerRoom = ServerRoom + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ServerRoom = ServerRoom + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ServerRoom = ServerRoom + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ServerRoom = ServerRoom + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ServerRoom = ServerRoom + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ServerRoom = ServerRoom + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ServerRoom = ServerRoom + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ServerRoom = ServerRoom + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ServerRoom = ServerRoom + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ServerRoom = ServerRoom + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ServerRoom = ServerRoom + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ServerRoom = ServerRoom + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ServerRoom = ServerRoom + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ServerRoom = ServerRoom + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ServerRoom = ServerRoom + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ServerRoom = ServerRoom + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ServerRoom = ServerRoom + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ServerRoom = ServerRoom + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ServerRoom = ServerRoom + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ServerRoom = ServerRoom + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ServerRoom =ServerRoom + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2859:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ServerRoomDemand = ServerRoomDemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ServerRoomDemand = ServerRoomDemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ServerRoomDemand = ServerRoomDemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ServerRoomDemand = ServerRoomDemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ServerRoomDemand = ServerRoomDemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ServerRoomDemand = ServerRoomDemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ServerRoomDemand = ServerRoomDemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ServerRoomDemand = ServerRoomDemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ServerRoomDemand = ServerRoomDemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ServerRoomDemand = ServerRoomDemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ServerRoomDemand = ServerRoomDemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ServerRoomDemand = ServerRoomDemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ServerRoomDemand = ServerRoomDemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ServerRoomDemand = ServerRoomDemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ServerRoomDemand = ServerRoomDemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ServerRoomDemand = ServerRoomDemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ServerRoomDemand = ServerRoomDemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ServerRoomDemand = ServerRoomDemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ServerRoomDemand = ServerRoomDemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ServerRoomDemand = ServerRoomDemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ServerRoomDemand = ServerRoomDemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ServerRoomDemand = ServerRoomDemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ServerRoomDemand = ServerRoomDemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ServerRoomDemand = ServerRoomDemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ServerRoomDemand = ServerRoomDemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ServerRoomDemand = ServerRoomDemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ServerRoomDemand = ServerRoomDemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ServerRoomDemand = ServerRoomDemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ServerRoomDemand = ServerRoomDemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ServerRoomDemand = ServerRoomDemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ServerRoomDemand = ServerRoomDemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ServerRoomDemand = ServerRoomDemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ServerRoomDemand = ServerRoomDemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ServerRoomDemand = ServerRoomDemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ServerRoomDemand = ServerRoomDemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ServerRoomDemand = ServerRoomDemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ServerRoomDemand =ServerRoomDemand + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2860:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ServerRoomReceptacles = ServerRoomReceptacles + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ServerRoomReceptacles =ServerRoomReceptacles + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2861:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ServerRoomReceptaclesDemand = ServerRoomReceptaclesDemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ServerRoomReceptaclesDemand =ServerRoomReceptaclesDemand + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2862:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ReceptaclesPannel1A = ReceptaclesPannel1A + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ReceptaclesPannel1A =ReceptaclesPannel1A + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2863:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ReceptaclesPannel1ADemand = ReceptaclesPannel1ADemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ReceptaclesPannel1ADemand =ReceptaclesPannel1ADemand + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2864:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ReceptaclesPannel2A = ReceptaclesPannel2A + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ReceptaclesPannel2A =ReceptaclesPannel2A + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2865:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ReceptaclesPannel2ADemand = ReceptaclesPannel2ADemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ReceptaclesPannel2ADemand =ReceptaclesPannel2ADemand + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2866:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ReceptaclesPannel2B = ReceptaclesPannel2B + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ReceptaclesPannel2B =ReceptaclesPannel2B + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2867:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { ReceptaclesPannel2BDemand = ReceptaclesPannel2BDemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { ReceptaclesPannel2BDemand =ReceptaclesPannel2BDemand + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2868:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { Compressor = Compressor + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { Compressor = Compressor + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { Compressor = Compressor + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { Compressor = Compressor + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { Compressor = Compressor + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { Compressor = Compressor + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { Compressor = Compressor + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { Compressor = Compressor + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { Compressor = Compressor + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { Compressor = Compressor + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { Compressor = Compressor + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { Compressor = Compressor + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { Compressor = Compressor + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { Compressor = Compressor + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { Compressor = Compressor + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { Compressor = Compressor + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { Compressor = Compressor + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { Compressor = Compressor + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { Compressor = Compressor + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { Compressor = Compressor + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { Compressor = Compressor + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { Compressor = Compressor + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { Compressor = Compressor + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { Compressor = Compressor + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { Compressor = Compressor + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { Compressor = Compressor + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { Compressor = Compressor + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { Compressor = Compressor + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { Compressor = Compressor + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { Compressor = Compressor + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { Compressor = Compressor + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { Compressor = Compressor + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { Compressor = Compressor + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { Compressor = Compressor + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { Compressor = Compressor + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { Compressor = Compressor + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { Compressor =Compressor + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2869:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { CompressorDemand = CompressorDemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { CompressorDemand = CompressorDemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { CompressorDemand = CompressorDemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { CompressorDemand = CompressorDemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { CompressorDemand = CompressorDemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { CompressorDemand = CompressorDemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { CompressorDemand = CompressorDemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { CompressorDemand = CompressorDemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { CompressorDemand = CompressorDemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { CompressorDemand = CompressorDemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { CompressorDemand = CompressorDemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { CompressorDemand = CompressorDemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { CompressorDemand = CompressorDemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { CompressorDemand = CompressorDemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { CompressorDemand = CompressorDemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { CompressorDemand = CompressorDemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { CompressorDemand = CompressorDemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { CompressorDemand = CompressorDemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { CompressorDemand = CompressorDemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { CompressorDemand = CompressorDemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { CompressorDemand = CompressorDemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { CompressorDemand = CompressorDemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { CompressorDemand = CompressorDemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { CompressorDemand = CompressorDemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { CompressorDemand = CompressorDemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { CompressorDemand = CompressorDemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { CompressorDemand = CompressorDemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { CompressorDemand = CompressorDemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { CompressorDemand = CompressorDemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { CompressorDemand = CompressorDemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { CompressorDemand = CompressorDemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { CompressorDemand = CompressorDemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { CompressorDemand = CompressorDemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { CompressorDemand = CompressorDemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { CompressorDemand = CompressorDemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { CompressorDemand = CompressorDemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { CompressorDemand =CompressorDemand + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2870:

if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { TonHVACUnit = TonHVACUnit + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { TonHVACUnit = TonHVACUnit + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { TonHVACUnit = TonHVACUnit + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { TonHVACUnit = TonHVACUnit + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { TonHVACUnit = TonHVACUnit + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { TonHVACUnit = TonHVACUnit + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { TonHVACUnit = TonHVACUnit + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { TonHVACUnit = TonHVACUnit + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { TonHVACUnit = TonHVACUnit + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { TonHVACUnit = TonHVACUnit + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { TonHVACUnit = TonHVACUnit + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { TonHVACUnit = TonHVACUnit + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { TonHVACUnit = TonHVACUnit + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { TonHVACUnit = TonHVACUnit + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { TonHVACUnit = TonHVACUnit + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { TonHVACUnit = TonHVACUnit + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { TonHVACUnit = TonHVACUnit + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { TonHVACUnit = TonHVACUnit + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { TonHVACUnit = TonHVACUnit + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { TonHVACUnit = TonHVACUnit + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { TonHVACUnit = TonHVACUnit + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { TonHVACUnit = TonHVACUnit + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { TonHVACUnit = TonHVACUnit + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { TonHVACUnit = TonHVACUnit + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { TonHVACUnit = TonHVACUnit + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { TonHVACUnit = TonHVACUnit + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { TonHVACUnit = TonHVACUnit + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { TonHVACUnit = TonHVACUnit + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { TonHVACUnit = TonHVACUnit + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { TonHVACUnit = TonHVACUnit + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { TonHVACUnit = TonHVACUnit + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { TonHVACUnit = TonHVACUnit + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { TonHVACUnit = TonHVACUnit + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { TonHVACUnit = TonHVACUnit + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { TonHVACUnit = TonHVACUnit + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { TonHVACUnit = TonHVACUnit + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { TonHVACUnit =TonHVACUnit + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2871:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { TonHVACUnitDemand = TonHVACUnitDemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { TonHVACUnitDemand =TonHVACUnitDemand + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2872:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { HVACLoads = HVACLoads + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { HVACLoads = HVACLoads + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { HVACLoads = HVACLoads + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { HVACLoads = HVACLoads + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { HVACLoads = HVACLoads + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { HVACLoads = HVACLoads + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { HVACLoads = HVACLoads + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { HVACLoads = HVACLoads + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { HVACLoads = HVACLoads + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { HVACLoads = HVACLoads + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { HVACLoads = HVACLoads + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { HVACLoads = HVACLoads + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { HVACLoads = HVACLoads + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { HVACLoads = HVACLoads + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { HVACLoads = HVACLoads + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { HVACLoads = HVACLoads + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { HVACLoads = HVACLoads + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { HVACLoads = HVACLoads + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { HVACLoads = HVACLoads + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { HVACLoads = HVACLoads + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { HVACLoads = HVACLoads + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { HVACLoads = HVACLoads + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { HVACLoads = HVACLoads + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { HVACLoads = HVACLoads + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { HVACLoads = HVACLoads + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { HVACLoads = HVACLoads + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { HVACLoads = HVACLoads + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { HVACLoads = HVACLoads + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { HVACLoads = HVACLoads + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { HVACLoads = HVACLoads + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { HVACLoads = HVACLoads + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { HVACLoads = HVACLoads + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { HVACLoads = HVACLoads + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { HVACLoads = HVACLoads + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { HVACLoads = HVACLoads + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { HVACLoads = HVACLoads + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { HVACLoads =HVACLoads + obj.getTotalReactivePowerMaxDemand(); }
							break;
						case 2873:
							if(itemField.get("slug").toString().equals("RealEnergyConsumption")) { HVACLoadsDemand = HVACLoadsDemand + obj.getRealEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousRealPower")) { HVACLoadsDemand = HVACLoadsDemand + obj.getTotalInstantaneousRealPower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousReactivePower")) { HVACLoadsDemand = HVACLoadsDemand + obj.getTotalInstantaneousReactivePower(); }
							if(itemField.get("slug").toString().equals("TotalInstantaneousApparentPower")) { HVACLoadsDemand = HVACLoadsDemand + obj.getTotalInstantaneousApparentPower(); }
							if(itemField.get("slug").toString().equals("TotalPowerFactor")) { HVACLoadsDemand = HVACLoadsDemand + obj.getTotalPowerFactor(); }
							if(itemField.get("slug").toString().equals("VoltageLL3pAve")) { HVACLoadsDemand = HVACLoadsDemand + obj.getVoltageLL3pAve(); }
							if(itemField.get("slug").toString().equals("VoltageLN3pAve")) { HVACLoadsDemand = HVACLoadsDemand + obj.getVoltageLN3pAve(); }
							if(itemField.get("slug").toString().equals("Current3pAve")) { HVACLoadsDemand = HVACLoadsDemand + obj.getCurrent3pAve(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseA")) { HVACLoadsDemand = HVACLoadsDemand + obj.getRealPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseB")) { HVACLoadsDemand = HVACLoadsDemand + obj.getRealPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("RealPowerPhaseC")) { HVACLoadsDemand = HVACLoadsDemand + obj.getRealPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseA")) { HVACLoadsDemand = HVACLoadsDemand + obj.getPowerFactorPhaseA(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseB")) { HVACLoadsDemand = HVACLoadsDemand + obj.getPowerFactorPhaseB(); }
							if(itemField.get("slug").toString().equals("PowerFactorPhaseC")) { HVACLoadsDemand = HVACLoadsDemand + obj.getPowerFactorPhaseC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAB")) { HVACLoadsDemand = HVACLoadsDemand + obj.getVoltagePhaseAB(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBC")) { HVACLoadsDemand = HVACLoadsDemand + obj.getVoltagePhaseBC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAC")) { HVACLoadsDemand = HVACLoadsDemand + obj.getVoltagePhaseAC(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseAN")) { HVACLoadsDemand = HVACLoadsDemand + obj.getVoltagePhaseAN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseBN")) { HVACLoadsDemand = HVACLoadsDemand + obj.getVoltagePhaseBN(); }
							if(itemField.get("slug").toString().equals("VoltagePhaseCN")) { HVACLoadsDemand = HVACLoadsDemand + obj.getVoltagePhaseCN(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseA")) { HVACLoadsDemand = HVACLoadsDemand + obj.getCurrentInstantaneousPhaseA(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseB")) { HVACLoadsDemand = HVACLoadsDemand + obj.getCurrentInstantaneousPhaseB(); }
							if(itemField.get("slug").toString().equals("CurrentInstantaneousPhaseC")) { HVACLoadsDemand = HVACLoadsDemand + obj.getCurrentInstantaneousPhaseC(); }
							if(itemField.get("slug").toString().equals("Frequency")) { HVACLoadsDemand = HVACLoadsDemand + obj.getFrequency(); }
							if(itemField.get("slug").toString().equals("ApparentEnergyConsumption")) { HVACLoadsDemand = HVACLoadsDemand + obj.getApparentEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ReactiveEnergyConsumption")) { HVACLoadsDemand = HVACLoadsDemand + obj.getReactiveEnergyConsumption(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseA")) { HVACLoadsDemand = HVACLoadsDemand + obj.getApparentPowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseB")) { HVACLoadsDemand = HVACLoadsDemand + obj.getApparentPowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ApparentPowerPhaseC")) { HVACLoadsDemand = HVACLoadsDemand + obj.getApparentPowerPhaseC(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseA")) { HVACLoadsDemand = HVACLoadsDemand + obj.getReactivePowerPhaseA(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseB")) { HVACLoadsDemand = HVACLoadsDemand + obj.getReactivePowerPhaseB(); }
							if(itemField.get("slug").toString().equals("ReactivePowerPhaseC")) { HVACLoadsDemand = HVACLoadsDemand + obj.getReactivePowerPhaseC(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerPresentDemand")) { HVACLoadsDemand = HVACLoadsDemand + obj.getTotalRealPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerPresentDemand")) { HVACLoadsDemand = HVACLoadsDemand + obj.getTotalReactivePowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalApparentPowerPresentDemand")) { HVACLoadsDemand = HVACLoadsDemand + obj.getTotalApparentPowerPresentDemand(); }
							if(itemField.get("slug").toString().equals("TotalRealPowerMaxDemand")) { HVACLoadsDemand = HVACLoadsDemand + obj.getTotalRealPowerMaxDemand(); }
							if(itemField.get("slug").toString().equals("TotalReactivePowerMaxDemand")) { HVACLoadsDemand =HVACLoadsDemand + obj.getTotalReactivePowerMaxDemand(); }
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
					virtualMeterEntity.setField2(warehouseLights);
					virtualMeterEntity.setField3(WarehouseLightsDemand);
					virtualMeterEntity.setField4(OfficeLights);
					virtualMeterEntity.setField5(OfficeLightsDemand);
					virtualMeterEntity.setField6(ServerRoom);
					virtualMeterEntity.setField7(ServerRoomDemand);
					virtualMeterEntity.setField8(ServerRoomReceptacles);
					virtualMeterEntity.setField9(ServerRoomReceptaclesDemand);
					virtualMeterEntity.setField10(ReceptaclesPannel1A);
					virtualMeterEntity.setField11(ReceptaclesPannel1ADemand);
					virtualMeterEntity.setField12(ReceptaclesPannel2A);
					virtualMeterEntity.setField13(ReceptaclesPannel2ADemand);
					virtualMeterEntity.setField14(ReceptaclesPannel2B);
					virtualMeterEntity.setField15(ReceptaclesPannel2BDemand);
					virtualMeterEntity.setField16(Compressor);
					virtualMeterEntity.setField17(CompressorDemand);
					virtualMeterEntity.setField18(TonHVACUnit);
					virtualMeterEntity.setField19(TonHVACUnitDemand);
					virtualMeterEntity.setField20(HVACLoads);
					virtualMeterEntity.setField21(HVACLoadsDemand);
					
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
