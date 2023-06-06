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
import com.nwm.api.entities.ModelAE1000NXClass9644Entity;
import com.nwm.api.utils.Lib;

public class ModelAE1000NXClass9644Service extends DB {
	
	/**
	 * @description set data ModelAE1000NXClass9644
	 * @author duy.phan
	 * @since 2023-06-01
	 * @param data
	 */
	
	public ModelAE1000NXClass9644Entity setModelAE1000NXClass9644(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelAE1000NXClass9644Entity dataModelAE1000NX = new ModelAE1000NXClass9644Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001");
				if(power < 0) { power = 0.0; };
				
				dataModelAE1000NX.setTime(words.get(0).replace("'", ""));
				dataModelAE1000NX.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelAE1000NX.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelAE1000NX.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelAE1000NX.setLastRestart(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelAE1000NX.setUptime(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelAE1000NX.setYear(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelAE1000NX.setMonth(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelAE1000NX.setDay(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelAE1000NX.setHour(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelAE1000NX.setMinutes(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelAE1000NX.setSeconds(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModelAE1000NX.setCurrentTime(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelAE1000NX.setACPower(power);
				dataModelAE1000NX.setACFrequency(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelAE1000NX.setPVVoltage(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModelAE1000NX.setPVCurrent(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelAE1000NX.setCommonMode(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelAE1000NX.setAmbientTemperature(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModelAE1000NX.setCoolantTemperature(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelAE1000NX.setReactorTemperature(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelAE1000NX.setCabinetTemperature(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelAE1000NX.setBusVoltage(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelAE1000NX.setGroundCurrent(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModelAE1000NX.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModelAE1000NX.setACCurrent(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModelAE1000NX.setTodaykWh(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModelAE1000NX.setYTDkWhTotal(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModelAE1000NX.setLifekWhTotal(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModelAE1000NX.setYTDkWh(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModelAE1000NX.setLifekWh(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				dataModelAE1000NX.setLast15MinkWh(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModelAE1000NX.setTimeStamp15Minutes(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModelAE1000NX.setState(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModelAE1000NX.setLimits(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModelAE1000NX.setMasterFault(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModelAE1000NX.setMasterWarning(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModelAE1000NX.setArrayFault(Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001"));
				dataModelAE1000NX.setArrayWarning(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModelAE1000NX.setGridFault(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModelAE1000NX.setGridWarning(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				dataModelAE1000NX.setSystemFault(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModelAE1000NX.setSystemWarning(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModelAE1000NX.setDriveFault(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModelAE1000NX.setDriveWarning(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModelAE1000NX.setTemperatureFault(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				dataModelAE1000NX.setTemperatureWarning(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModelAE1000NX.setCoolingSystemFault(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModelAE1000NX.setCoolingSystemWarning(Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001"));
				dataModelAE1000NX.setElectricInterlockFault(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				dataModelAE1000NX.setElectricInterlockWarning(Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001"));
				dataModelAE1000NX.setPowerSupplyFault(Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001"));
				dataModelAE1000NX.setPowerSupplyWarning(Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001"));
				dataModelAE1000NX.setRequestSetACPowerLimit(Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001"));
				dataModelAE1000NX.setLowerRangeOfACPowerLimit(Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001"));
				dataModelAE1000NX.setUpperRangeOfACPowerLimit(Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001"));
				dataModelAE1000NX.setRequestSetInstantaneousReactivePowerSetPoint(Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001"));
				dataModelAE1000NX.setLowerRangeOfInstantaneousReactivePowerSetPoint(Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001"));
				dataModelAE1000NX.setUpperRangeOfInstantaneousReactivePowerSetPoint(Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001"));
				dataModelAE1000NX.setEnableDisableTheUnit(Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001"));
				dataModelAE1000NX.setSetReadReactivePowerMode(Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001"));
				dataModelAE1000NX.setSetReadPACLimit(Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001"));
				dataModelAE1000NX.setSetReadInstantaneousReactivePowerSetPoint(Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001"));
				dataModelAE1000NX.setSetReadPowerFactorSetPoint(Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001"));
				dataModelAE1000NX.setACPowerRampRate(Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001"));
				dataModelAE1000NX.setReactivePowerRampRate(Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001"));
				dataModelAE1000NX.setPowerFactorRampRate(Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001"));
				dataModelAE1000NX.setReactivePowerSignConvention(Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001"));
				dataModelAE1000NX.setDCS_N(Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001"));
				dataModelAE1000NX.setDCS_StCtcr(Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001"));
				dataModelAE1000NX.setDCS_StCpt(Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001"));
				dataModelAE1000NX.setDCS_EvtFlt(Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001"));
				dataModelAE1000NX.setDCS_EvtWrn(Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001"));
				dataModelAE1000NX.setDCS_StVnd(Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001"));
				dataModelAE1000NX.setDCS_DCA(Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001"));
				dataModelAE1000NX.setDCS_DCADif(Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001"));
				dataModelAE1000NX.setDCS_DCV(Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001"));
				dataModelAE1000NX.setDCS_Tmp(Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001"));
				dataModelAE1000NX.setDCS_InId01(Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt01(Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn01(Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA01(Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA01(Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001"));
				dataModelAE1000NX.setDCS_InId02(Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt02(Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn02(Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA02(Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA02(Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001"));
				dataModelAE1000NX.setDCS_InId03(Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt03(Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn03(Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA03(Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA03(Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001"));
				dataModelAE1000NX.setDCS_InId04(Double.parseDouble(!Lib.isBlank(words.get(93)) ? words.get(93) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt04(Double.parseDouble(!Lib.isBlank(words.get(94)) ? words.get(94) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn04(Double.parseDouble(!Lib.isBlank(words.get(95)) ? words.get(95) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA04(Double.parseDouble(!Lib.isBlank(words.get(96)) ? words.get(96) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA04(Double.parseDouble(!Lib.isBlank(words.get(97)) ? words.get(97) : "0.001"));
				dataModelAE1000NX.setDCS_InId05(Double.parseDouble(!Lib.isBlank(words.get(98)) ? words.get(98) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt05(Double.parseDouble(!Lib.isBlank(words.get(99)) ? words.get(99) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn05(Double.parseDouble(!Lib.isBlank(words.get(100)) ? words.get(100) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA05(Double.parseDouble(!Lib.isBlank(words.get(101)) ? words.get(101) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA05(Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0.001"));
				dataModelAE1000NX.setDCS_InId06(Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt06(Double.parseDouble(!Lib.isBlank(words.get(104)) ? words.get(104) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn06(Double.parseDouble(!Lib.isBlank(words.get(105)) ? words.get(105) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA06(Double.parseDouble(!Lib.isBlank(words.get(106)) ? words.get(106) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA06(Double.parseDouble(!Lib.isBlank(words.get(107)) ? words.get(107) : "0.001"));
				dataModelAE1000NX.setDCS_InId07(Double.parseDouble(!Lib.isBlank(words.get(108)) ? words.get(108) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt07(Double.parseDouble(!Lib.isBlank(words.get(109)) ? words.get(109) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn07(Double.parseDouble(!Lib.isBlank(words.get(110)) ? words.get(110) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA07(Double.parseDouble(!Lib.isBlank(words.get(111)) ? words.get(111) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA07(Double.parseDouble(!Lib.isBlank(words.get(112)) ? words.get(112) : "0.001"));
				dataModelAE1000NX.setDCS_InId08(Double.parseDouble(!Lib.isBlank(words.get(113)) ? words.get(113) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt08(Double.parseDouble(!Lib.isBlank(words.get(114)) ? words.get(114) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn08(Double.parseDouble(!Lib.isBlank(words.get(115)) ? words.get(115) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA08(Double.parseDouble(!Lib.isBlank(words.get(116)) ? words.get(116) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA08(Double.parseDouble(!Lib.isBlank(words.get(117)) ? words.get(117) : "0.001"));
				dataModelAE1000NX.setDCS_InId09(Double.parseDouble(!Lib.isBlank(words.get(118)) ? words.get(118) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt09(Double.parseDouble(!Lib.isBlank(words.get(119)) ? words.get(119) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn09(Double.parseDouble(!Lib.isBlank(words.get(120)) ? words.get(120) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA09(Double.parseDouble(!Lib.isBlank(words.get(121)) ? words.get(121) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA09(Double.parseDouble(!Lib.isBlank(words.get(122)) ? words.get(122) : "0.001"));
				dataModelAE1000NX.setDCS_InId10(Double.parseDouble(!Lib.isBlank(words.get(123)) ? words.get(123) : "0.001"));
				dataModelAE1000NX.setDCS_InFlt10(Double.parseDouble(!Lib.isBlank(words.get(124)) ? words.get(124) : "0.001"));
				dataModelAE1000NX.setDCS_InWrn10(Double.parseDouble(!Lib.isBlank(words.get(125)) ? words.get(125) : "0.001"));
				dataModelAE1000NX.setDCS_InPDCA10(Double.parseDouble(!Lib.isBlank(words.get(126)) ? words.get(126) : "0.001"));
				dataModelAE1000NX.setDCS_InNDCA10(Double.parseDouble(!Lib.isBlank(words.get(127)) ? words.get(127) : "0.001"));
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModelAE1000NX.setNvmActivePower(power);
				dataModelAE1000NX.setNvmActiveEnergy(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				return dataModelAE1000NX;
				
			} else {
				return new ModelAE1000NXClass9644Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelAE1000NXClass9644Entity();
		}
	}
	

	/**
	 * @description insert data from datalogger to model_ae1000nx_class9644
	 * @author duy.phan
	 * @since 2023-06-01
	 * @param data from datalogger
	 */
	
	public boolean insertModelAE1000NXClass9644(ModelAE1000NXClass9644Entity obj) {
		try {
			 Object insertId = insert("ModelAE1000NXClass9644.insertModelAE1000NXClass9644", obj);
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
