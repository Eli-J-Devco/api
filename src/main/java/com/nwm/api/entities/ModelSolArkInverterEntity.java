/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSolArkInverterEntity extends ModelBaseEntity {
	private double ActivePowerGenerationToday;
	private double ReactivePowerGenerationToday;
	private double Invertergridsidetotalactivepowergeneration;
	private double Invertergridsidetotalreactivepowergeneration;
	private double PhaseAPower;
	private double PhaseBPower;
	private double PhaseCPower;
	private double TotalActivePower;
	private double TotalApparentPower;
	private double PhaseAVoltage;
	private double PhaseBVoltage;
	private double PhaseCVoltage;
	private double PhaseACurrent;
	private double PhaseBCurrent;
	private double PhaseCCurrent;
	private double PhaseAReactivePower;
	private double PhaseBReactivePower;
	private double PhaseCReactivePower;
	private double Frequency;
	private double PV1Voltage;
	private double PV1Current;
	private double PV2Voltage;
	private double PV2Current;
	private double PV3Voltage;
	private double PV3Current;
	private double PV4Voltage;
	private double PV4Current;
	private double Operationstatus;
	private double Warningmessageword1;
	private double Warningmessageword2;
	private double Faultmessageword1;
	private double Faultmessageword2;
	private double Faultmessageword3;
	private double Faultmessageword4;
	private double PhaseAPowerlow16bits;
	private double PhaseAPowerhigh16bits;
	private double PhaseBPowerlow16bits;
	private double PhaseBPowerhigh16bits;
	private double PhaseCPowerlow16bits;
	private double PhaseCPowerhigh16bits;
	private double TotalActivePowerlow16bits;
	private double TotalActivePowerhigh16bits;
	private double TotalApparentPowerlow16bits;
	private double TotalApparentPowerhigh16bits;
	private double DCinvertertemperature;
	private double Heatsinktemperature;
	private double Batterychargeoftheday;
	private double Batterydischargeoftheday;
	private double Totalchargetothebattery;
	private double Totaldischargeofthebattery;
	private double Battery1temperature;
	private double Battery1voltage;
	private double Battery1Current;
	private double Battery1OutputPower;
	private double Battery1CorrectedCapacityAH;
	private double Battery1SOC;
	private double Battery2Temperature;
	private double Battery2Voltage;
	private double Battery2Current;
	private double Battery2OutputPower;
	private double Battery2CorrectedCapacityAH;
	private double Battery2SOC;
	private double Negativecurrentfrombattery1;
	private double Negativecurrentfrombattery2;
	
	public double getActivePowerGenerationToday() {
		return ActivePowerGenerationToday;
	}
	public void setActivePowerGenerationToday(double activePowerGenerationToday) {
		ActivePowerGenerationToday = activePowerGenerationToday;
	}
	public double getReactivePowerGenerationToday() {
		return ReactivePowerGenerationToday;
	}
	public void setReactivePowerGenerationToday(double reactivePowerGenerationToday) {
		ReactivePowerGenerationToday = reactivePowerGenerationToday;
	}
	public double getInvertergridsidetotalactivepowergeneration() {
		return Invertergridsidetotalactivepowergeneration;
	}
	public void setInvertergridsidetotalactivepowergeneration(double invertergridsidetotalactivepowergeneration) {
		Invertergridsidetotalactivepowergeneration = invertergridsidetotalactivepowergeneration;
	}
	public double getInvertergridsidetotalreactivepowergeneration() {
		return Invertergridsidetotalreactivepowergeneration;
	}
	public void setInvertergridsidetotalreactivepowergeneration(double invertergridsidetotalreactivepowergeneration) {
		Invertergridsidetotalreactivepowergeneration = invertergridsidetotalreactivepowergeneration;
	}
	public double getPhaseAPower() {
		return PhaseAPower;
	}
	public void setPhaseAPower(double phaseAPower) {
		PhaseAPower = phaseAPower;
	}
	public double getPhaseBPower() {
		return PhaseBPower;
	}
	public void setPhaseBPower(double phaseBPower) {
		PhaseBPower = phaseBPower;
	}
	public double getPhaseCPower() {
		return PhaseCPower;
	}
	public void setPhaseCPower(double phaseCPower) {
		PhaseCPower = phaseCPower;
	}
	public double getTotalActivePower() {
		return TotalActivePower;
	}
	public void setTotalActivePower(double totalActivePower) {
		TotalActivePower = totalActivePower;
	}
	public double getTotalApparentPower() {
		return TotalApparentPower;
	}
	public void setTotalApparentPower(double totalApparentPower) {
		TotalApparentPower = totalApparentPower;
	}
	public double getPhaseAVoltage() {
		return PhaseAVoltage;
	}
	public void setPhaseAVoltage(double phaseAVoltage) {
		PhaseAVoltage = phaseAVoltage;
	}
	public double getPhaseBVoltage() {
		return PhaseBVoltage;
	}
	public void setPhaseBVoltage(double phaseBVoltage) {
		PhaseBVoltage = phaseBVoltage;
	}
	public double getPhaseCVoltage() {
		return PhaseCVoltage;
	}
	public void setPhaseCVoltage(double phaseCVoltage) {
		PhaseCVoltage = phaseCVoltage;
	}
	public double getPhaseACurrent() {
		return PhaseACurrent;
	}
	public void setPhaseACurrent(double phaseACurrent) {
		PhaseACurrent = phaseACurrent;
	}
	public double getPhaseBCurrent() {
		return PhaseBCurrent;
	}
	public void setPhaseBCurrent(double phaseBCurrent) {
		PhaseBCurrent = phaseBCurrent;
	}
	public double getPhaseCCurrent() {
		return PhaseCCurrent;
	}
	public void setPhaseCCurrent(double phaseCCurrent) {
		PhaseCCurrent = phaseCCurrent;
	}
	public double getPhaseAReactivePower() {
		return PhaseAReactivePower;
	}
	public void setPhaseAReactivePower(double phaseAReactivePower) {
		PhaseAReactivePower = phaseAReactivePower;
	}
	public double getPhaseBReactivePower() {
		return PhaseBReactivePower;
	}
	public void setPhaseBReactivePower(double phaseBReactivePower) {
		PhaseBReactivePower = phaseBReactivePower;
	}
	public double getPhaseCReactivePower() {
		return PhaseCReactivePower;
	}
	public void setPhaseCReactivePower(double phaseCReactivePower) {
		PhaseCReactivePower = phaseCReactivePower;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getPV1Voltage() {
		return PV1Voltage;
	}
	public void setPV1Voltage(double pV1Voltage) {
		PV1Voltage = pV1Voltage;
	}
	public double getPV1Current() {
		return PV1Current;
	}
	public void setPV1Current(double pV1Current) {
		PV1Current = pV1Current;
	}
	public double getPV2Voltage() {
		return PV2Voltage;
	}
	public void setPV2Voltage(double pV2Voltage) {
		PV2Voltage = pV2Voltage;
	}
	public double getPV2Current() {
		return PV2Current;
	}
	public void setPV2Current(double pV2Current) {
		PV2Current = pV2Current;
	}
	public double getPV3Voltage() {
		return PV3Voltage;
	}
	public void setPV3Voltage(double pV3Voltage) {
		PV3Voltage = pV3Voltage;
	}
	public double getPV3Current() {
		return PV3Current;
	}
	public void setPV3Current(double pV3Current) {
		PV3Current = pV3Current;
	}
	public double getPV4Voltage() {
		return PV4Voltage;
	}
	public void setPV4Voltage(double pV4Voltage) {
		PV4Voltage = pV4Voltage;
	}
	public double getPV4Current() {
		return PV4Current;
	}
	public void setPV4Current(double pV4Current) {
		PV4Current = pV4Current;
	}
	public double getOperationstatus() {
		return Operationstatus;
	}
	public void setOperationstatus(double operationstatus) {
		Operationstatus = operationstatus;
	}
	public double getWarningmessageword1() {
		return Warningmessageword1;
	}
	public void setWarningmessageword1(double warningmessageword1) {
		Warningmessageword1 = warningmessageword1;
	}
	public double getWarningmessageword2() {
		return Warningmessageword2;
	}
	public void setWarningmessageword2(double warningmessageword2) {
		Warningmessageword2 = warningmessageword2;
	}
	public double getFaultmessageword1() {
		return Faultmessageword1;
	}
	public void setFaultmessageword1(double faultmessageword1) {
		Faultmessageword1 = faultmessageword1;
	}
	public double getFaultmessageword2() {
		return Faultmessageword2;
	}
	public void setFaultmessageword2(double faultmessageword2) {
		Faultmessageword2 = faultmessageword2;
	}
	public double getFaultmessageword3() {
		return Faultmessageword3;
	}
	public void setFaultmessageword3(double faultmessageword3) {
		Faultmessageword3 = faultmessageword3;
	}
	public double getFaultmessageword4() {
		return Faultmessageword4;
	}
	public void setFaultmessageword4(double faultmessageword4) {
		Faultmessageword4 = faultmessageword4;
	}
	public double getPhaseAPowerlow16bits() {
		return PhaseAPowerlow16bits;
	}
	public void setPhaseAPowerlow16bits(double phaseAPowerlow16bits) {
		PhaseAPowerlow16bits = phaseAPowerlow16bits;
	}
	public double getPhaseAPowerhigh16bits() {
		return PhaseAPowerhigh16bits;
	}
	public void setPhaseAPowerhigh16bits(double phaseAPowerhigh16bits) {
		PhaseAPowerhigh16bits = phaseAPowerhigh16bits;
	}
	public double getPhaseBPowerlow16bits() {
		return PhaseBPowerlow16bits;
	}
	public void setPhaseBPowerlow16bits(double phaseBPowerlow16bits) {
		PhaseBPowerlow16bits = phaseBPowerlow16bits;
	}
	public double getPhaseBPowerhigh16bits() {
		return PhaseBPowerhigh16bits;
	}
	public void setPhaseBPowerhigh16bits(double phaseBPowerhigh16bits) {
		PhaseBPowerhigh16bits = phaseBPowerhigh16bits;
	}
	public double getPhaseCPowerlow16bits() {
		return PhaseCPowerlow16bits;
	}
	public void setPhaseCPowerlow16bits(double phaseCPowerlow16bits) {
		PhaseCPowerlow16bits = phaseCPowerlow16bits;
	}
	public double getPhaseCPowerhigh16bits() {
		return PhaseCPowerhigh16bits;
	}
	public void setPhaseCPowerhigh16bits(double phaseCPowerhigh16bits) {
		PhaseCPowerhigh16bits = phaseCPowerhigh16bits;
	}
	public double getTotalActivePowerlow16bits() {
		return TotalActivePowerlow16bits;
	}
	public void setTotalActivePowerlow16bits(double totalActivePowerlow16bits) {
		TotalActivePowerlow16bits = totalActivePowerlow16bits;
	}
	public double getTotalActivePowerhigh16bits() {
		return TotalActivePowerhigh16bits;
	}
	public void setTotalActivePowerhigh16bits(double totalActivePowerhigh16bits) {
		TotalActivePowerhigh16bits = totalActivePowerhigh16bits;
	}
	public double getTotalApparentPowerlow16bits() {
		return TotalApparentPowerlow16bits;
	}
	public void setTotalApparentPowerlow16bits(double totalApparentPowerlow16bits) {
		TotalApparentPowerlow16bits = totalApparentPowerlow16bits;
	}
	public double getTotalApparentPowerhigh16bits() {
		return TotalApparentPowerhigh16bits;
	}
	public void setTotalApparentPowerhigh16bits(double totalApparentPowerhigh16bits) {
		TotalApparentPowerhigh16bits = totalApparentPowerhigh16bits;
	}
	public double getDCinvertertemperature() {
		return DCinvertertemperature;
	}
	public void setDCinvertertemperature(double dCinvertertemperature) {
		DCinvertertemperature = dCinvertertemperature;
	}
	public double getHeatsinktemperature() {
		return Heatsinktemperature;
	}
	public void setHeatsinktemperature(double heatsinktemperature) {
		Heatsinktemperature = heatsinktemperature;
	}
	public double getBatterychargeoftheday() {
		return Batterychargeoftheday;
	}
	public void setBatterychargeoftheday(double batterychargeoftheday) {
		Batterychargeoftheday = batterychargeoftheday;
	}
	public double getBatterydischargeoftheday() {
		return Batterydischargeoftheday;
	}
	public void setBatterydischargeoftheday(double batterydischargeoftheday) {
		Batterydischargeoftheday = batterydischargeoftheday;
	}
	public double getTotalchargetothebattery() {
		return Totalchargetothebattery;
	}
	public void setTotalchargetothebattery(double totalchargetothebattery) {
		Totalchargetothebattery = totalchargetothebattery;
	}
	public double getTotaldischargeofthebattery() {
		return Totaldischargeofthebattery;
	}
	public void setTotaldischargeofthebattery(double totaldischargeofthebattery) {
		Totaldischargeofthebattery = totaldischargeofthebattery;
	}
	public double getBattery1temperature() {
		return Battery1temperature;
	}
	public void setBattery1temperature(double battery1temperature) {
		Battery1temperature = battery1temperature;
	}
	public double getBattery1voltage() {
		return Battery1voltage;
	}
	public void setBattery1voltage(double battery1voltage) {
		Battery1voltage = battery1voltage;
	}
	public double getBattery1Current() {
		return Battery1Current;
	}
	public void setBattery1Current(double battery1Current) {
		Battery1Current = battery1Current;
	}
	public double getBattery1OutputPower() {
		return Battery1OutputPower;
	}
	public void setBattery1OutputPower(double battery1OutputPower) {
		Battery1OutputPower = battery1OutputPower;
	}
	public double getBattery1CorrectedCapacityAH() {
		return Battery1CorrectedCapacityAH;
	}
	public void setBattery1CorrectedCapacityAH(double battery1CorrectedCapacityAH) {
		Battery1CorrectedCapacityAH = battery1CorrectedCapacityAH;
	}
	public double getBattery1SOC() {
		return Battery1SOC;
	}
	public void setBattery1SOC(double battery1soc) {
		Battery1SOC = battery1soc;
	}
	public double getBattery2Temperature() {
		return Battery2Temperature;
	}
	public void setBattery2Temperature(double battery2Temperature) {
		Battery2Temperature = battery2Temperature;
	}
	public double getBattery2Voltage() {
		return Battery2Voltage;
	}
	public void setBattery2Voltage(double battery2Voltage) {
		Battery2Voltage = battery2Voltage;
	}
	public double getBattery2Current() {
		return Battery2Current;
	}
	public void setBattery2Current(double battery2Current) {
		Battery2Current = battery2Current;
	}
	public double getBattery2OutputPower() {
		return Battery2OutputPower;
	}
	public void setBattery2OutputPower(double battery2OutputPower) {
		Battery2OutputPower = battery2OutputPower;
	}
	public double getBattery2CorrectedCapacityAH() {
		return Battery2CorrectedCapacityAH;
	}
	public void setBattery2CorrectedCapacityAH(double battery2CorrectedCapacityAH) {
		Battery2CorrectedCapacityAH = battery2CorrectedCapacityAH;
	}
	public double getBattery2SOC() {
		return Battery2SOC;
	}
	public void setBattery2SOC(double battery2soc) {
		Battery2SOC = battery2soc;
	}
	public double getNegativecurrentfrombattery1() {
		return Negativecurrentfrombattery1;
	}
	public void setNegativecurrentfrombattery1(double negativecurrentfrombattery1) {
		Negativecurrentfrombattery1 = negativecurrentfrombattery1;
	}
	public double getNegativecurrentfrombattery2() {
		return Negativecurrentfrombattery2;
	}
	public void setNegativecurrentfrombattery2(double negativecurrentfrombattery2) {
		Negativecurrentfrombattery2 = negativecurrentfrombattery2;
	}
	
	
	
}
