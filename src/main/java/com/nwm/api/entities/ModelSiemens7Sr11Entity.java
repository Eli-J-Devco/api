/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSiemens7Sr11Entity extends ModelBaseEntity {
	private double EventCount;
	private double EventRecordPart1;
	private double EventRecordPart2;
	private double VoltagePhaseAtoB;
	private double VoltagePhaseBtoC;
	private double VoltagePhaseCtoA;
	private double VoltagePhaseA;
	private double VoltagePhaseB;
	private double VoltagePhaseC;
	private double Vzps;
	private double Vpps;
	private double Vnps;
	private double VzpsDegrees;
	private double VppsDegrees;
	private double VnpsDegrees;
	private double Frequency;
	private double PhaseACurrent;
	private double PhaseBCurrent;
	private double PhaseCCurrent;
	private double NeutralCurrent;
	private double GroundCurrent;
	private double ActivePowerPhaseA;
	private double ActivePowerPhaseB;
	private double ActivePowerPhaseC;
	private double ActivePower3PhaseTotal;
	private double ReactivePowerPhaseA;
	private double ReactivePowerPhaseB;
	private double ReactivePowerPhaseC;
	private double ReactivePower3PhaseTotal;
	private double ApparentPowerPhaseA;
	private double ApparentPowerPhaseB;
	private double ApparentPowerPhaseC;
	private double ApparentPower3PhaseTotal;
	private double PowerFactorPhaseA;
	private double PowerFactorPhaseB;
	private double PowerFactorPhaseC;
	private double PowerFactor3PhaseTotal;
	private double ActiveEnergyExported;
	private double ActiveEnergyImported;
	private double ReactiveEnergyExported;
	private double ReactiveEnergyImported;
	private double ThermalStatusPhaseA;
	private double ThermalStatusPhaseB;
	private double ThermalStatusPhaseC;
	private double FaultRecords;
	private double EventRecords;
	private double LED1_16Bitmap;
	private double LED17_32Bitmap;
	private double InputStatus1_16Bitmap;
	private double InputStatus17_32Bitmap;
	private double CircuitBreaker1Status;
	private double AutoRecloseActive;
	private double RemoteMode;
	private double LocalRemoteMode;
	private double OutofServiceMode;
	private double CicruitBreaker1Open;
	private double CircuitBreaker1Close;
	
	public double getEventCount() {
		return EventCount;
	}
	public void setEventCount(double eventCount) {
		EventCount = eventCount;
	}
	public double getEventRecordPart1() {
		return EventRecordPart1;
	}
	public void setEventRecordPart1(double eventRecordPart1) {
		EventRecordPart1 = eventRecordPart1;
	}
	public double getEventRecordPart2() {
		return EventRecordPart2;
	}
	public void setEventRecordPart2(double eventRecordPart2) {
		EventRecordPart2 = eventRecordPart2;
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
	public double getVzps() {
		return Vzps;
	}
	public void setVzps(double vzps) {
		Vzps = vzps;
	}
	public double getVpps() {
		return Vpps;
	}
	public void setVpps(double vpps) {
		Vpps = vpps;
	}
	public double getVnps() {
		return Vnps;
	}
	public void setVnps(double vnps) {
		Vnps = vnps;
	}
	public double getVzpsDegrees() {
		return VzpsDegrees;
	}
	public void setVzpsDegrees(double vzpsDegrees) {
		VzpsDegrees = vzpsDegrees;
	}
	public double getVppsDegrees() {
		return VppsDegrees;
	}
	public void setVppsDegrees(double vppsDegrees) {
		VppsDegrees = vppsDegrees;
	}
	public double getVnpsDegrees() {
		return VnpsDegrees;
	}
	public void setVnpsDegrees(double vnpsDegrees) {
		VnpsDegrees = vnpsDegrees;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
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
	public double getNeutralCurrent() {
		return NeutralCurrent;
	}
	public void setNeutralCurrent(double neutralCurrent) {
		NeutralCurrent = neutralCurrent;
	}
	public double getGroundCurrent() {
		return GroundCurrent;
	}
	public void setGroundCurrent(double groundCurrent) {
		GroundCurrent = groundCurrent;
	}
	public double getActivePowerPhaseA() {
		return ActivePowerPhaseA;
	}
	public void setActivePowerPhaseA(double activePowerPhaseA) {
		ActivePowerPhaseA = activePowerPhaseA;
	}
	public double getActivePowerPhaseB() {
		return ActivePowerPhaseB;
	}
	public void setActivePowerPhaseB(double activePowerPhaseB) {
		ActivePowerPhaseB = activePowerPhaseB;
	}
	public double getActivePowerPhaseC() {
		return ActivePowerPhaseC;
	}
	public void setActivePowerPhaseC(double activePowerPhaseC) {
		ActivePowerPhaseC = activePowerPhaseC;
	}
	public double getActivePower3PhaseTotal() {
		return ActivePower3PhaseTotal;
	}
	public void setActivePower3PhaseTotal(double activePower3PhaseTotal) {
		ActivePower3PhaseTotal = activePower3PhaseTotal;
	}
	public double getReactivePowerPhaseA() {
		return ReactivePowerPhaseA;
	}
	public void setReactivePowerPhaseA(double reactivePowerPhaseA) {
		ReactivePowerPhaseA = reactivePowerPhaseA;
	}
	public double getReactivePowerPhaseB() {
		return ReactivePowerPhaseB;
	}
	public void setReactivePowerPhaseB(double reactivePowerPhaseB) {
		ReactivePowerPhaseB = reactivePowerPhaseB;
	}
	public double getReactivePowerPhaseC() {
		return ReactivePowerPhaseC;
	}
	public void setReactivePowerPhaseC(double reactivePowerPhaseC) {
		ReactivePowerPhaseC = reactivePowerPhaseC;
	}
	public double getReactivePower3PhaseTotal() {
		return ReactivePower3PhaseTotal;
	}
	public void setReactivePower3PhaseTotal(double reactivePower3PhaseTotal) {
		ReactivePower3PhaseTotal = reactivePower3PhaseTotal;
	}
	public double getApparentPowerPhaseA() {
		return ApparentPowerPhaseA;
	}
	public void setApparentPowerPhaseA(double apparentPowerPhaseA) {
		ApparentPowerPhaseA = apparentPowerPhaseA;
	}
	public double getApparentPowerPhaseB() {
		return ApparentPowerPhaseB;
	}
	public void setApparentPowerPhaseB(double apparentPowerPhaseB) {
		ApparentPowerPhaseB = apparentPowerPhaseB;
	}
	public double getApparentPowerPhaseC() {
		return ApparentPowerPhaseC;
	}
	public void setApparentPowerPhaseC(double apparentPowerPhaseC) {
		ApparentPowerPhaseC = apparentPowerPhaseC;
	}
	public double getApparentPower3PhaseTotal() {
		return ApparentPower3PhaseTotal;
	}
	public void setApparentPower3PhaseTotal(double apparentPower3PhaseTotal) {
		ApparentPower3PhaseTotal = apparentPower3PhaseTotal;
	}
	public double getPowerFactorPhaseA() {
		return PowerFactorPhaseA;
	}
	public void setPowerFactorPhaseA(double powerFactorPhaseA) {
		PowerFactorPhaseA = powerFactorPhaseA;
	}
	public double getPowerFactorPhaseB() {
		return PowerFactorPhaseB;
	}
	public void setPowerFactorPhaseB(double powerFactorPhaseB) {
		PowerFactorPhaseB = powerFactorPhaseB;
	}
	public double getPowerFactorPhaseC() {
		return PowerFactorPhaseC;
	}
	public void setPowerFactorPhaseC(double powerFactorPhaseC) {
		PowerFactorPhaseC = powerFactorPhaseC;
	}
	public double getPowerFactor3PhaseTotal() {
		return PowerFactor3PhaseTotal;
	}
	public void setPowerFactor3PhaseTotal(double powerFactor3PhaseTotal) {
		PowerFactor3PhaseTotal = powerFactor3PhaseTotal;
	}
	public double getActiveEnergyExported() {
		return ActiveEnergyExported;
	}
	public void setActiveEnergyExported(double activeEnergyExported) {
		ActiveEnergyExported = activeEnergyExported;
	}
	public double getActiveEnergyImported() {
		return ActiveEnergyImported;
	}
	public void setActiveEnergyImported(double activeEnergyImported) {
		ActiveEnergyImported = activeEnergyImported;
	}
	public double getReactiveEnergyExported() {
		return ReactiveEnergyExported;
	}
	public void setReactiveEnergyExported(double reactiveEnergyExported) {
		ReactiveEnergyExported = reactiveEnergyExported;
	}
	public double getReactiveEnergyImported() {
		return ReactiveEnergyImported;
	}
	public void setReactiveEnergyImported(double reactiveEnergyImported) {
		ReactiveEnergyImported = reactiveEnergyImported;
	}
	public double getThermalStatusPhaseA() {
		return ThermalStatusPhaseA;
	}
	public void setThermalStatusPhaseA(double thermalStatusPhaseA) {
		ThermalStatusPhaseA = thermalStatusPhaseA;
	}
	public double getThermalStatusPhaseB() {
		return ThermalStatusPhaseB;
	}
	public void setThermalStatusPhaseB(double thermalStatusPhaseB) {
		ThermalStatusPhaseB = thermalStatusPhaseB;
	}
	public double getThermalStatusPhaseC() {
		return ThermalStatusPhaseC;
	}
	public void setThermalStatusPhaseC(double thermalStatusPhaseC) {
		ThermalStatusPhaseC = thermalStatusPhaseC;
	}
	public double getFaultRecords() {
		return FaultRecords;
	}
	public void setFaultRecords(double faultRecords) {
		FaultRecords = faultRecords;
	}
	public double getEventRecords() {
		return EventRecords;
	}
	public void setEventRecords(double eventRecords) {
		EventRecords = eventRecords;
	}
	public double getLED1_16Bitmap() {
		return LED1_16Bitmap;
	}
	public void setLED1_16Bitmap(double lED1_16Bitmap) {
		LED1_16Bitmap = lED1_16Bitmap;
	}
	public double getLED17_32Bitmap() {
		return LED17_32Bitmap;
	}
	public void setLED17_32Bitmap(double lED17_32Bitmap) {
		LED17_32Bitmap = lED17_32Bitmap;
	}
	public double getInputStatus1_16Bitmap() {
		return InputStatus1_16Bitmap;
	}
	public void setInputStatus1_16Bitmap(double inputStatus1_16Bitmap) {
		InputStatus1_16Bitmap = inputStatus1_16Bitmap;
	}
	public double getInputStatus17_32Bitmap() {
		return InputStatus17_32Bitmap;
	}
	public void setInputStatus17_32Bitmap(double inputStatus17_32Bitmap) {
		InputStatus17_32Bitmap = inputStatus17_32Bitmap;
	}
	public double getCircuitBreaker1Status() {
		return CircuitBreaker1Status;
	}
	public void setCircuitBreaker1Status(double circuitBreaker1Status) {
		CircuitBreaker1Status = circuitBreaker1Status;
	}
	public double getAutoRecloseActive() {
		return AutoRecloseActive;
	}
	public void setAutoRecloseActive(double autoRecloseActive) {
		AutoRecloseActive = autoRecloseActive;
	}
	public double getRemoteMode() {
		return RemoteMode;
	}
	public void setRemoteMode(double remoteMode) {
		RemoteMode = remoteMode;
	}
	public double getLocalRemoteMode() {
		return LocalRemoteMode;
	}
	public void setLocalRemoteMode(double localRemoteMode) {
		LocalRemoteMode = localRemoteMode;
	}
	public double getOutofServiceMode() {
		return OutofServiceMode;
	}
	public void setOutofServiceMode(double outofServiceMode) {
		OutofServiceMode = outofServiceMode;
	}
	public double getCicruitBreaker1Open() {
		return CicruitBreaker1Open;
	}
	public void setCicruitBreaker1Open(double cicruitBreaker1Open) {
		CicruitBreaker1Open = cicruitBreaker1Open;
	}
	public double getCircuitBreaker1Close() {
		return CircuitBreaker1Close;
	}
	public void setCircuitBreaker1Close(double circuitBreaker1Close) {
		CircuitBreaker1Close = circuitBreaker1Close;
	}
}
