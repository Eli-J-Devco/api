/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelHuaweiSun2000US10Entity extends ModelBaseEntity {
	private double Frequency;
	private double VoltagePhaseAtoB;
	private double VoltagePhaseBtoC;
	private double VoltagePhaseCtoA;
	private double VoltagePhaseA;
	private double VoltagePhaseB;
	private double VoltagePhaseC;
	private double CurrentPhaseA;
	private double CurrentPhaseB;
	private double CurrentPhaseC;
	private double ActivePower;
	private double TotalEnergyDelivered;
	private double PVVoltage1;
	private double PVVoltage2;
	private double PVVoltage3;
	private double PVVoltage4;
	private double PVVoltage5;
	private double PVVoltage6;
	private double PVCurrent1;
	private double PVCurrent2;
	private double PVCurrent3;
	private double PVCurrent4;
	private double PVCurrent5;
	private double PVCurrent6;
	private double CabinetTemperature;
	private double PowerFactor;
	private double InverterEfficiency;
	private double InverterStatus;
	private double FaultCode;
	private double FaultCode1;
	private double FaultCode2;
	private double FaultCode3;
	private double FaultCode4;
	private double FaultCode5;
	private double FaultCode6;
	private double FaultCode7;
	private double FaultCode8;
	private double FaultCode9;
	private double FaultCode10;
	private double FaultCode11;
	private double RatedInverterPower;
	private double ActivePowerPeakToday;
	private double ReactivePower;
	private double TotalInputPower;
	private double EnergyDeliveredToday;
	private double InsulationResistance;
	private double MPPT1Voltage;
	private double MPPT1Current;
	private double MPPT1Power;
	private double MPPT2Voltage;
	private double MPPT2Current;
	private double MPPT2Power;
	private double MPPT3Voltage;
	private double MPPT3Current;
	private double MPPT3Power;
	
	
	public double getRatedInverterPower() {
		return RatedInverterPower;
	}
	public void setRatedInverterPower(double ratedInverterPower) {
		RatedInverterPower = ratedInverterPower;
	}
	public double getActivePowerPeakToday() {
		return ActivePowerPeakToday;
	}
	public void setActivePowerPeakToday(double activePowerPeakToday) {
		ActivePowerPeakToday = activePowerPeakToday;
	}
	public double getReactivePower() {
		return ReactivePower;
	}
	public void setReactivePower(double reactivePower) {
		ReactivePower = reactivePower;
	}
	public double getTotalInputPower() {
		return TotalInputPower;
	}
	public void setTotalInputPower(double totalInputPower) {
		TotalInputPower = totalInputPower;
	}
	public double getEnergyDeliveredToday() {
		return EnergyDeliveredToday;
	}
	public void setEnergyDeliveredToday(double energyDeliveredToday) {
		EnergyDeliveredToday = energyDeliveredToday;
	}
	public double getInsulationResistance() {
		return InsulationResistance;
	}
	public void setInsulationResistance(double insulationResistance) {
		InsulationResistance = insulationResistance;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getVoltagePhaseAtoB() {
		return VoltagePhaseAtoB;
	}
	public void setVoltagePhaseAtoB(double voltagePhaseAtoB) {
		VoltagePhaseAtoB = voltagePhaseAtoB;
	}
	public double getVoltagePhaseBtoC() {
		return VoltagePhaseBtoC;
	}
	public void setVoltagePhaseBtoC(double voltagePhaseBtoC) {
		VoltagePhaseBtoC = voltagePhaseBtoC;
	}
	public double getVoltagePhaseCtoA() {
		return VoltagePhaseCtoA;
	}
	public void setVoltagePhaseCtoA(double voltagePhaseCtoA) {
		VoltagePhaseCtoA = voltagePhaseCtoA;
	}
	public double getVoltagePhaseA() {
		return VoltagePhaseA;
	}
	public void setVoltagePhaseA(double voltagePhaseA) {
		VoltagePhaseA = voltagePhaseA;
	}
	public double getVoltagePhaseB() {
		return VoltagePhaseB;
	}
	public void setVoltagePhaseB(double voltagePhaseB) {
		VoltagePhaseB = voltagePhaseB;
	}
	public double getVoltagePhaseC() {
		return VoltagePhaseC;
	}
	public void setVoltagePhaseC(double voltagePhaseC) {
		VoltagePhaseC = voltagePhaseC;
	}
	public double getCurrentPhaseA() {
		return CurrentPhaseA;
	}
	public void setCurrentPhaseA(double currentPhaseA) {
		CurrentPhaseA = currentPhaseA;
	}
	public double getCurrentPhaseB() {
		return CurrentPhaseB;
	}
	public void setCurrentPhaseB(double currentPhaseB) {
		CurrentPhaseB = currentPhaseB;
	}
	public double getCurrentPhaseC() {
		return CurrentPhaseC;
	}
	public void setCurrentPhaseC(double currentPhaseC) {
		CurrentPhaseC = currentPhaseC;
	}
	public double getActivePower() {
		return ActivePower;
	}
	public void setActivePower(double activePower) {
		ActivePower = activePower;
	}
	public double getTotalEnergyDelivered() {
		return TotalEnergyDelivered;
	}
	public void setTotalEnergyDelivered(double totalEnergyDelivered) {
		TotalEnergyDelivered = totalEnergyDelivered;
	}
	public double getPVVoltage1() {
		return PVVoltage1;
	}
	public void setPVVoltage1(double pVVoltage1) {
		PVVoltage1 = pVVoltage1;
	}
	public double getPVVoltage2() {
		return PVVoltage2;
	}
	public void setPVVoltage2(double pVVoltage2) {
		PVVoltage2 = pVVoltage2;
	}
	public double getPVVoltage3() {
		return PVVoltage3;
	}
	public void setPVVoltage3(double pVVoltage3) {
		PVVoltage3 = pVVoltage3;
	}
	public double getPVVoltage4() {
		return PVVoltage4;
	}
	public void setPVVoltage4(double pVVoltage4) {
		PVVoltage4 = pVVoltage4;
	}
	public double getPVVoltage5() {
		return PVVoltage5;
	}
	public void setPVVoltage5(double pVVoltage5) {
		PVVoltage5 = pVVoltage5;
	}
	public double getPVVoltage6() {
		return PVVoltage6;
	}
	public void setPVVoltage6(double pVVoltage6) {
		PVVoltage6 = pVVoltage6;
	}
	public double getPVCurrent1() {
		return PVCurrent1;
	}
	public void setPVCurrent1(double pVCurrent1) {
		PVCurrent1 = pVCurrent1;
	}
	public double getPVCurrent2() {
		return PVCurrent2;
	}
	public void setPVCurrent2(double pVCurrent2) {
		PVCurrent2 = pVCurrent2;
	}
	public double getPVCurrent3() {
		return PVCurrent3;
	}
	public void setPVCurrent3(double pVCurrent3) {
		PVCurrent3 = pVCurrent3;
	}
	public double getPVCurrent4() {
		return PVCurrent4;
	}
	public void setPVCurrent4(double pVCurrent4) {
		PVCurrent4 = pVCurrent4;
	}
	public double getPVCurrent5() {
		return PVCurrent5;
	}
	public void setPVCurrent5(double pVCurrent5) {
		PVCurrent5 = pVCurrent5;
	}
	public double getPVCurrent6() {
		return PVCurrent6;
	}
	public void setPVCurrent6(double pVCurrent6) {
		PVCurrent6 = pVCurrent6;
	}
	public double getCabinetTemperature() {
		return CabinetTemperature;
	}
	public void setCabinetTemperature(double cabinetTemperature) {
		CabinetTemperature = cabinetTemperature;
	}
	public double getPowerFactor() {
		return PowerFactor;
	}
	public void setPowerFactor(double powerFactor) {
		PowerFactor = powerFactor;
	}
	public double getInverterEfficiency() {
		return InverterEfficiency;
	}
	public void setInverterEfficiency(double inverterEfficiency) {
		InverterEfficiency = inverterEfficiency;
	}
	public double getInverterStatus() {
		return InverterStatus;
	}
	public void setInverterStatus(double inverterStatus) {
		InverterStatus = inverterStatus;
	}
	public double getFaultCode() {
		return FaultCode;
	}
	public void setFaultCode(double faultCode) {
		FaultCode = faultCode;
	}
	public double getFaultCode1() {
		return FaultCode1;
	}
	public void setFaultCode1(double faultCode1) {
		FaultCode1 = faultCode1;
	}
	public double getFaultCode2() {
		return FaultCode2;
	}
	public void setFaultCode2(double faultCode2) {
		FaultCode2 = faultCode2;
	}
	public double getFaultCode3() {
		return FaultCode3;
	}
	public void setFaultCode3(double faultCode3) {
		FaultCode3 = faultCode3;
	}
	public double getFaultCode4() {
		return FaultCode4;
	}
	public void setFaultCode4(double faultCode4) {
		FaultCode4 = faultCode4;
	}
	public double getFaultCode5() {
		return FaultCode5;
	}
	public void setFaultCode5(double faultCode5) {
		FaultCode5 = faultCode5;
	}
	public double getFaultCode6() {
		return FaultCode6;
	}
	public void setFaultCode6(double faultCode6) {
		FaultCode6 = faultCode6;
	}
	public double getFaultCode7() {
		return FaultCode7;
	}
	public void setFaultCode7(double faultCode7) {
		FaultCode7 = faultCode7;
	}
	public double getFaultCode8() {
		return FaultCode8;
	}
	public void setFaultCode8(double faultCode8) {
		FaultCode8 = faultCode8;
	}
	public double getFaultCode9() {
		return FaultCode9;
	}
	public void setFaultCode9(double faultCode9) {
		FaultCode9 = faultCode9;
	}
	public double getFaultCode10() {
		return FaultCode10;
	}
	public void setFaultCode10(double faultCode10) {
		FaultCode10 = faultCode10;
	}
	public double getFaultCode11() {
		return FaultCode11;
	}
	public void setFaultCode11(double faultCode11) {
		FaultCode11 = faultCode11;
	}
	public double getMPPT1Voltage() {
		return MPPT1Voltage;
	}
	public void setMPPT1Voltage(double mPPT1Voltage) {
		MPPT1Voltage = mPPT1Voltage;
	}
	public double getMPPT1Current() {
		return MPPT1Current;
	}
	public void setMPPT1Current(double mPPT1Current) {
		MPPT1Current = mPPT1Current;
	}
	public double getMPPT1Power() {
		return MPPT1Power;
	}
	public void setMPPT1Power(double mPPT1Power) {
		MPPT1Power = mPPT1Power;
	}
	public double getMPPT2Voltage() {
		return MPPT2Voltage;
	}
	public void setMPPT2Voltage(double mPPT2Voltage) {
		MPPT2Voltage = mPPT2Voltage;
	}
	public double getMPPT2Current() {
		return MPPT2Current;
	}
	public void setMPPT2Current(double mPPT2Current) {
		MPPT2Current = mPPT2Current;
	}
	public double getMPPT2Power() {
		return MPPT2Power;
	}
	public void setMPPT2Power(double mPPT2Power) {
		MPPT2Power = mPPT2Power;
	}
	public double getMPPT3Voltage() {
		return MPPT3Voltage;
	}
	public void setMPPT3Voltage(double mPPT3Voltage) {
		MPPT3Voltage = mPPT3Voltage;
	}
	public double getMPPT3Current() {
		return MPPT3Current;
	}
	public void setMPPT3Current(double mPPT3Current) {
		MPPT3Current = mPPT3Current;
	}
	public double getMPPT3Power() {
		return MPPT3Power;
	}
	public void setMPPT3Power(double mPPT3Power) {
		MPPT3Power = mPPT3Power;
	}
}
