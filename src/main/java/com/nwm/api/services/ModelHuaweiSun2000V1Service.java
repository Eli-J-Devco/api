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
import com.nwm.api.entities.ModelHuaweiSun2000V1Entity;
import com.nwm.api.utils.Lib;

public class ModelHuaweiSun2000V1Service extends DB {

	/**
	 * @description set data ModelShark100
	 * @author long.pham
	 * @since 2022-12-20
	 * @param data
	 */
	
	public ModelHuaweiSun2000V1Entity setModelHuaweiSun2000V1(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHuaweiSun2000V1Entity dataModel = new ModelHuaweiSun2000V1Entity();
				
				Double power = Double.parseDouble(!Lib.isBlank(words.get(37)) ? words.get(37) : "0.001");
				Double energy = Double.parseDouble(!Lib.isBlank(words.get(48)) ? words.get(48) : "0.001");
							
				dataModel.setTime(words.get(0).replace("'", ""));
				dataModel.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModel.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModel.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				dataModel.setModelID(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModel.setNumberOfPVStrings(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModel.setNumberOfMPPTrackers(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModel.setRatedPower(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModel.setMaximumActivePower(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModel.setMaximumApparentPower(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModel.setMaximumReactivePowerFedToTheGrid(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				
				dataModel.setMaximumReactivePowerAbsorbedFromTheGrid(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));
				dataModel.setState1(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModel.setState2(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModel.setState3(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModel.setAlarm1(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));
				dataModel.setAlarm2(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModel.setAlarm3(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModel.setPV1Voltage(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));
				dataModel.setPV1Current(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				
				dataModel.setPV2Voltage(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModel.setPV2Current(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModel.setPV3Voltage(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModel.setPV3Current(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
				dataModel.setPV4Voltage(Double.parseDouble(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001"));
				dataModel.setPV4Current(Double.parseDouble(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001"));
				dataModel.setInputPower(Double.parseDouble(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001"));
				dataModel.setLineVoltageBetweenPhasesAAndB(Double.parseDouble(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001"));
				dataModel.setLineVoltageBetweenPhasesBAndC(Double.parseDouble(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001"));
				dataModel.setLineVoltageBetweenPhasesCAndA(Double.parseDouble(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001"));
				dataModel.setPhaseAVoltage(Double.parseDouble(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001"));
				
				dataModel.setPhaseBVoltage(Double.parseDouble(!Lib.isBlank(words.get(31)) ? words.get(31) : "0.001"));
				dataModel.setPhaseCVoltage(Double.parseDouble(!Lib.isBlank(words.get(32)) ? words.get(32) : "0.001"));
				dataModel.setPhaseACurrent(Double.parseDouble(!Lib.isBlank(words.get(33)) ? words.get(33) : "0.001"));
				dataModel.setPhaseBCurrent(Double.parseDouble(!Lib.isBlank(words.get(34)) ? words.get(34) : "0.001"));
				dataModel.setPhaseCCurrent(Double.parseDouble(!Lib.isBlank(words.get(35)) ? words.get(35) : "0.001"));
				dataModel.setPeakActivePowerOfCurrentDay(Double.parseDouble(!Lib.isBlank(words.get(36)) ? words.get(36) : "0.001"));
				dataModel.setActivePower(power);
				dataModel.setReactivePower(Double.parseDouble(!Lib.isBlank(words.get(38)) ? words.get(38) : "0.001"));
				dataModel.setPowerFactor(Double.parseDouble(!Lib.isBlank(words.get(39)) ? words.get(39) : "0.001"));
				dataModel.setGridFrequency(Double.parseDouble(!Lib.isBlank(words.get(40)) ? words.get(40) : "0.001"));
				
				dataModel.setEfficiency(Double.parseDouble(!Lib.isBlank(words.get(41)) ? words.get(41) : "0.001"));
				dataModel.setInternalTemperature(Double.parseDouble(!Lib.isBlank(words.get(42)) ? words.get(42) : "0.001"));
				dataModel.setInsulationResistance(Double.parseDouble(!Lib.isBlank(words.get(43)) ? words.get(43) : "0.001"));
				dataModel.setDeviceStatus(Double.parseDouble(!Lib.isBlank(words.get(44)) ? words.get(44) : "0.001"));
				dataModel.setFaultCode(Double.parseDouble(!Lib.isBlank(words.get(45)) ? words.get(45) : "0.001"));
				
				dataModel.setStartupTime(Double.parseDouble(!Lib.isBlank(words.get(46)) ? words.get(46) : "0.001"));
				dataModel.setShutdownTime(Double.parseDouble(!Lib.isBlank(words.get(47)) ? words.get(47) : "0.001"));
				dataModel.setAccumulatedEnergyYield(energy);
				dataModel.setDailyEnergyYield(Double.parseDouble(!Lib.isBlank(words.get(49)) ? words.get(49) : "0.001"));
				
				dataModel.setPV5Voltage(words.size() > 50 ? Double.parseDouble(!Lib.isBlank(words.get(50)) ? words.get(50) : "0.001") : 0.001);
				dataModel.setPV5Current(words.size() > 51 ? Double.parseDouble(!Lib.isBlank(words.get(51)) ? words.get(51) : "0.001") : 0.001);
				dataModel.setPV6Voltage(words.size() > 52 ? Double.parseDouble(!Lib.isBlank(words.get(52)) ? words.get(52) : "0.001") : 0.001);
				dataModel.setPV6Current(words.size() > 53 ? Double.parseDouble(!Lib.isBlank(words.get(53)) ? words.get(53) : "0.001") : 0.001);
				dataModel.setPV7Voltage(words.size() > 54 ? Double.parseDouble(!Lib.isBlank(words.get(54)) ? words.get(54) : "0.001") : 0.001);
				dataModel.setPV7Current(words.size() > 55 ? Double.parseDouble(!Lib.isBlank(words.get(55)) ? words.get(55) : "0.001") : 0.001);
				dataModel.setPV8Voltage(words.size() > 56 ? Double.parseDouble(!Lib.isBlank(words.get(56)) ? words.get(56) : "0.001") : 0.001);				
				dataModel.setPV8Current(words.size() > 57 ? Double.parseDouble(!Lib.isBlank(words.get(57)) ? words.get(57) : "0.001") : 0.001);
				dataModel.setPV9Voltage(words.size() > 58 ? Double.parseDouble(!Lib.isBlank(words.get(58)) ? words.get(58) : "0.001") : 0.001);
				dataModel.setPV9Current(words.size() > 59 ? Double.parseDouble(!Lib.isBlank(words.get(59)) ? words.get(59) : "0.001") : 0.001);
				dataModel.setPV10Voltage(words.size() > 60 ? Double.parseDouble(!Lib.isBlank(words.get(60)) ? words.get(60) : "0.001") : 0.001);
				dataModel.setPV10Current(words.size() > 61 ? Double.parseDouble(!Lib.isBlank(words.get(61)) ? words.get(61) : "0.001") : 0.001);
				dataModel.setPV11Voltage(words.size() > 62 ? Double.parseDouble(!Lib.isBlank(words.get(62)) ? words.get(62) : "0.001") : 0.001);
				dataModel.setPV11Current(words.size() > 63 ? Double.parseDouble(!Lib.isBlank(words.get(63)) ? words.get(63) : "0.001") : 0.001);
				dataModel.setPV12Voltage(words.size() > 64 ? Double.parseDouble(!Lib.isBlank(words.get(64)) ? words.get(64) : "0.001") : 0.001);
				dataModel.setPV12Current(words.size() > 65 ? Double.parseDouble(!Lib.isBlank(words.get(65)) ? words.get(65) : "0.001") : 0.001);
				dataModel.setPV13Voltage(words.size() > 66 ? Double.parseDouble(!Lib.isBlank(words.get(66)) ? words.get(66) : "0.001") : 0.001);
				dataModel.setPV13Current(words.size() > 67 ? Double.parseDouble(!Lib.isBlank(words.get(67)) ? words.get(67) : "0.001") : 0.001);
				dataModel.setPV14Voltage(words.size() > 68 ? Double.parseDouble(!Lib.isBlank(words.get(68)) ? words.get(68) : "0.001") : 0.001);
				dataModel.setPV14Current(words.size() > 69 ? Double.parseDouble(!Lib.isBlank(words.get(69)) ? words.get(69) : "0.001") : 0.001);
				dataModel.setPV15Voltage(words.size() > 70 ? Double.parseDouble(!Lib.isBlank(words.get(70)) ? words.get(70) : "0.001") : 0.001);
				dataModel.setPV15Current(words.size() > 71 ? Double.parseDouble(!Lib.isBlank(words.get(71)) ? words.get(71) : "0.001") : 0.001);
				dataModel.setPV16Voltage(words.size() > 72 ? Double.parseDouble(!Lib.isBlank(words.get(72)) ? words.get(72) : "0.001") : 0.001);
				dataModel.setPV16Current(words.size() > 73 ? Double.parseDouble(!Lib.isBlank(words.get(73)) ? words.get(73) : "0.001") : 0.001);
				dataModel.setPV17Voltage(words.size() > 74 ? Double.parseDouble(!Lib.isBlank(words.get(74)) ? words.get(74) : "0.001") : 0.001);
				dataModel.setPV17Current(words.size() > 75 ? Double.parseDouble(!Lib.isBlank(words.get(75)) ? words.get(75) : "0.001") : 0.001);
				dataModel.setPV18Voltage(words.size() > 76 ? Double.parseDouble(!Lib.isBlank(words.get(76)) ? words.get(76) : "0.001") : 0.001);
				dataModel.setPV18Current(words.size() > 77 ? Double.parseDouble(!Lib.isBlank(words.get(77)) ? words.get(77) : "0.001") : 0.001);
				dataModel.setPV19Voltage(words.size() > 78 ? Double.parseDouble(!Lib.isBlank(words.get(78)) ? words.get(78) : "0.001") : 0.001);
				dataModel.setPV19Current(words.size() > 79 ? Double.parseDouble(!Lib.isBlank(words.get(79)) ? words.get(79) : "0.001") : 0.001);
				dataModel.setPV20Voltage(words.size() > 80 ? Double.parseDouble(!Lib.isBlank(words.get(80)) ? words.get(80) : "0.001") : 0.001);
				dataModel.setPV20Current(words.size() > 81 ? Double.parseDouble(!Lib.isBlank(words.get(81)) ? words.get(81) : "0.001") : 0.001);
				dataModel.setPV21Voltage(words.size() > 82 ? Double.parseDouble(!Lib.isBlank(words.get(82)) ? words.get(82) : "0.001") : 0.001);
				dataModel.setPV21Current(words.size() > 83 ? Double.parseDouble(!Lib.isBlank(words.get(83)) ? words.get(83) : "0.001") : 0.001);
				dataModel.setPV22Voltage(words.size() > 84 ? Double.parseDouble(!Lib.isBlank(words.get(84)) ? words.get(84) : "0.001") : 0.001);
				dataModel.setPV22Current(words.size() > 85 ? Double.parseDouble(!Lib.isBlank(words.get(85)) ? words.get(85) : "0.001") : 0.001);
				dataModel.setPV23Voltage(words.size() > 86 ? Double.parseDouble(!Lib.isBlank(words.get(86)) ? words.get(86) : "0.001") : 0.001);	
				dataModel.setPV23Current(words.size() > 87 ? Double.parseDouble(!Lib.isBlank(words.get(87)) ? words.get(87) : "0.001") : 0.001);
				dataModel.setPV24Voltage(words.size() > 88 ? Double.parseDouble(!Lib.isBlank(words.get(88)) ? words.get(88) : "0.001") : 0.001);
				dataModel.setPV24Current(words.size() > 89 ? Double.parseDouble(!Lib.isBlank(words.get(89)) ? words.get(89) : "0.001") : 0.001);
				dataModel.setPV25Voltage(words.size() > 90 ? Double.parseDouble(!Lib.isBlank(words.get(90)) ? words.get(90) : "0.001") : 0.001);			
				dataModel.setPV25Current(words.size() > 91 ? Double.parseDouble(!Lib.isBlank(words.get(91)) ? words.get(91) : "0.001") : 0.001);
				dataModel.setPV26Voltage(words.size() > 92 ? Double.parseDouble(!Lib.isBlank(words.get(92)) ? words.get(92) : "0.001") : 0.001);
				dataModel.setPV26Current(words.size() > 93 ? Double.parseDouble(!Lib.isBlank(words.get(93)) ? words.get(93) : "0.001") : 0.001);
				dataModel.setPV27Voltage(words.size() > 94 ? Double.parseDouble(!Lib.isBlank(words.get(94)) ? words.get(94) : "0.001") : 0.001);
				dataModel.setPV27Current(words.size() > 95 ? Double.parseDouble(!Lib.isBlank(words.get(95)) ? words.get(95) : "0.001") : 0.001);			
				dataModel.setPV28Voltage(words.size() > 96 ? Double.parseDouble(!Lib.isBlank(words.get(96)) ? words.get(96) : "0.001") : 0.001);
				dataModel.setPV28Current(words.size() > 97 ? Double.parseDouble(!Lib.isBlank(words.get(97)) ? words.get(97) : "0.001") : 0.001);			
				dataModel.setMPPT1Voltage(words.size() > 98 ? Double.parseDouble(!Lib.isBlank(words.get(98)) ? words.get(98) : "0.001") : 0.001);
				dataModel.setMPPT1Current(words.size() > 99 ? Double.parseDouble(!Lib.isBlank(words.get(99)) ? words.get(99) : "0.001") : 0.001);
				dataModel.setMPPT1Power(words.size() > 100 ? Double.parseDouble(!Lib.isBlank(words.get(100)) ? words.get(100) : "0.001") : 0.001);
				dataModel.setMPPT2Voltage(words.size() > 101 ? Double.parseDouble(!Lib.isBlank(words.get(101)) ? words.get(101) : "0.001") : 0.001);
				dataModel.setMPPT2Current(words.size() > 102 ? Double.parseDouble(!Lib.isBlank(words.get(102)) ? words.get(102) : "0.001") : 0.001);
				dataModel.setMPPT2Power(words.size() > 103 ? Double.parseDouble(!Lib.isBlank(words.get(103)) ? words.get(103) : "0.001") : 0.001);
				dataModel.setMPPT3Voltage(words.size() > 104 ? Double.parseDouble(!Lib.isBlank(words.get(104)) ? words.get(104) : "0.001") : 0.001);
				dataModel.setMPPT3Current(words.size() > 105 ? Double.parseDouble(!Lib.isBlank(words.get(105)) ? words.get(105) : "0.001") : 0.001);
				dataModel.setMPPT3Power(words.size() > 106 ? Double.parseDouble(!Lib.isBlank(words.get(106)) ? words.get(106) : "0.001") : 0.001);
				dataModel.setMPPT4Voltage(words.size() > 107 ? Double.parseDouble(!Lib.isBlank(words.get(107)) ? words.get(107) : "0.001") : 0.001);
				dataModel.setMPPT4Current(words.size() > 108 ? Double.parseDouble(!Lib.isBlank(words.get(108)) ? words.get(108) : "0.001") : 0.001);
				dataModel.setMPPT4Power(words.size() > 109 ? Double.parseDouble(!Lib.isBlank(words.get(109)) ? words.get(109) : "0.001") : 0.001);
				dataModel.setMPPT5Voltage(words.size() > 110 ? Double.parseDouble(!Lib.isBlank(words.get(110)) ? words.get(110) : "0.001") : 0.001);
				dataModel.setMPPT5Current(words.size() > 111 ? Double.parseDouble(!Lib.isBlank(words.get(111)) ? words.get(111) : "0.001") : 0.001);
				dataModel.setMPPT5Power(words.size() > 112 ? Double.parseDouble(!Lib.isBlank(words.get(112)) ? words.get(112) : "0.001") : 0.001);
				dataModel.setMPPT6Voltage(words.size() > 113 ? Double.parseDouble(!Lib.isBlank(words.get(113)) ? words.get(113) : "0.001") : 0.001);
				dataModel.setMPPT6Current(words.size() > 114 ? Double.parseDouble(!Lib.isBlank(words.get(114)) ? words.get(114) : "0.001") : 0.001);
				dataModel.setMPPT6Power(words.size() > 115 ? Double.parseDouble(!Lib.isBlank(words.get(115)) ? words.get(115) : "0.001") : 0.001);

				
				
				// set custom field nvmActivePower and nvmActiveEnergy
				dataModel.setNvmActivePower(power);
				dataModel.setNvmActiveEnergy(energy);
				
				
				return dataModel;
				
			} else {
				return new ModelHuaweiSun2000V1Entity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHuaweiSun2000V1Entity();
		}
	}
	/**
	 * @description insert data from datalogger to model shark 100
	 * @author long.pham
	 * @since 2020-10-07
	 * @param data from datalogger
	 */
	
	public boolean insertModelHuaweiSun2000V1(ModelHuaweiSun2000V1Entity obj) {
		try {
			if(obj.getOffset_data_old() !=0) {
				Double energy = obj.getNvmActiveEnergy();
				energy = energy + obj.getOffset_data_old();
				obj.setNvmActiveEnergy(energy);
				obj.setAccumulatedEnergyYield(energy);
			}
			
			ModelHuaweiSun2000V1Entity dataObj = (ModelHuaweiSun2000V1Entity) queryForObject("ModelHuaweiSun2000V1.getLastRow", obj);
			// filter data 
			if(dataObj != null && ( obj.getNvmActiveEnergy() == 0.001 || obj.getNvmActiveEnergy() < 0) ) {
				obj.setNvmActiveEnergy(dataObj.getNvmActiveEnergy());
				obj.setAccumulatedEnergyYield(dataObj.getAccumulatedEnergyYield());
			}
			
			 
			 Object insertId = insert("ModelHuaweiSun2000V1.insertModelHuaweiSun2000V1", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        
	        // Update measuredProduction 
			if (dataObj != null && dataObj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() > 0 && obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy() >= 0 ) {
				ModelHuaweiSun2000V1Entity objUpdateMeasured = new ModelHuaweiSun2000V1Entity();
				objUpdateMeasured.setDatatablename(obj.getDatatablename());
				objUpdateMeasured.setTime(dataObj.getTime());
				objUpdateMeasured.setMeasuredProduction(obj.getNvmActiveEnergy() - dataObj.getNvmActiveEnergy());
				update("Device.updateMeasuredProduction", objUpdateMeasured);
			}
 			
	        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
