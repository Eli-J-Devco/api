/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSunSpecInverterEntity extends ModelBaseEntity {
	private double ACCurrent;
	private double PhaseACurrent;
	private double PhaseBCurrent;
	private double PhaseCCurrent;
	private double PhaseVoltageAB;
	private double PhaseVoltageBC;
	private double PhaseVoltageCA;
	private double PhaseVoltageAN;
	private double PhaseVoltageBN;
	private double PhaseVoltageCN;
	private double ACPower;
	private double LineFrequency;
	private double ACApparentPower;
	private double ACReactivePower;
	private double ACPowerFactor;
	private double ACEnergy;
	private double DCAmps;
	private double DCVoltage;
	private double DCPower;
	private double CabinetTemperature;
	private double HeatSinkTemperature;
	private double TransformerTemperature;
	private double OtherTemperature;
	private double OperatingState;
	private double VendorSpecificOperatingState;
	private double EventField;
	private double ReservedforFuture;
	private double VendorEvent1;
	private double VendorEvent2;
	private double VendorEvent3;
	private double VendorEvent4;

	public double getACCurrent() {
		return ACCurrent;
	}
	public void setACCurrent(double aCCurrent) {
		ACCurrent = aCCurrent;
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
	public double getPhaseVoltageAB() {
		return PhaseVoltageAB;
	}
	public void setPhaseVoltageAB(double phaseVoltageAB) {
		PhaseVoltageAB = phaseVoltageAB;
	}
	public double getPhaseVoltageBC() {
		return PhaseVoltageBC;
	}
	public void setPhaseVoltageBC(double phaseVoltageBC) {
		PhaseVoltageBC = phaseVoltageBC;
	}
	public double getPhaseVoltageCA() {
		return PhaseVoltageCA;
	}
	public void setPhaseVoltageCA(double phaseVoltageCA) {
		PhaseVoltageCA = phaseVoltageCA;
	}
	public double getPhaseVoltageAN() {
		return PhaseVoltageAN;
	}
	public void setPhaseVoltageAN(double phaseVoltageAN) {
		PhaseVoltageAN = phaseVoltageAN;
	}
	public double getPhaseVoltageBN() {
		return PhaseVoltageBN;
	}
	public void setPhaseVoltageBN(double phaseVoltageBN) {
		PhaseVoltageBN = phaseVoltageBN;
	}
	public double getPhaseVoltageCN() {
		return PhaseVoltageCN;
	}
	public void setPhaseVoltageCN(double phaseVoltageCN) {
		PhaseVoltageCN = phaseVoltageCN;
	}
	public double getACPower() {
		return ACPower;
	}
	public void setACPower(double aCPower) {
		ACPower = aCPower;
	}
	public double getLineFrequency() {
		return LineFrequency;
	}
	public void setLineFrequency(double lineFrequency) {
		LineFrequency = lineFrequency;
	}
	public double getACApparentPower() {
		return ACApparentPower;
	}
	public void setACApparentPower(double aCApparentPower) {
		ACApparentPower = aCApparentPower;
	}
	public double getACReactivePower() {
		return ACReactivePower;
	}
	public void setACReactivePower(double aCReactivePower) {
		ACReactivePower = aCReactivePower;
	}
	public double getACPowerFactor() {
		return ACPowerFactor;
	}
	public void setACPowerFactor(double aCPowerFactor) {
		ACPowerFactor = aCPowerFactor;
	}
	public double getACEnergy() {
		return ACEnergy;
	}
	public void setACEnergy(double aCEnergy) {
		ACEnergy = aCEnergy;
	}
	public double getDCAmps() {
		return DCAmps;
	}
	public void setDCAmps(double dCAmps) {
		DCAmps = dCAmps;
	}
	public double getDCVoltage() {
		return DCVoltage;
	}
	public void setDCVoltage(double dCVoltage) {
		DCVoltage = dCVoltage;
	}
	public double getDCPower() {
		return DCPower;
	}
	public void setDCPower(double dCPower) {
		DCPower = dCPower;
	}
	public double getCabinetTemperature() {
		return CabinetTemperature;
	}
	public void setCabinetTemperature(double cabinetTemperature) {
		CabinetTemperature = cabinetTemperature;
	}
	public double getHeatSinkTemperature() {
		return HeatSinkTemperature;
	}
	public void setHeatSinkTemperature(double heatSinkTemperature) {
		HeatSinkTemperature = heatSinkTemperature;
	}
	public double getTransformerTemperature() {
		return TransformerTemperature;
	}
	public void setTransformerTemperature(double transformerTemperature) {
		TransformerTemperature = transformerTemperature;
	}
	public double getOtherTemperature() {
		return OtherTemperature;
	}
	public void setOtherTemperature(double otherTemperature) {
		OtherTemperature = otherTemperature;
	}
	public double getOperatingState() {
		return OperatingState;
	}
	public void setOperatingState(double operatingState) {
		OperatingState = operatingState;
	}
	public double getVendorSpecificOperatingState() {
		return VendorSpecificOperatingState;
	}
	public void setVendorSpecificOperatingState(double vendorSpecificOperatingState) {
		VendorSpecificOperatingState = vendorSpecificOperatingState;
	}
	public double getEventField() {
		return EventField;
	}
	public void setEventField(double eventField) {
		EventField = eventField;
	}
	public double getReservedforFuture() {
		return ReservedforFuture;
	}
	public void setReservedforFuture(double reservedforFuture) {
		ReservedforFuture = reservedforFuture;
	}
	public double getVendorEvent1() {
		return VendorEvent1;
	}
	public void setVendorEvent1(double vendorEvent1) {
		VendorEvent1 = vendorEvent1;
	}
	public double getVendorEvent2() {
		return VendorEvent2;
	}
	public void setVendorEvent2(double vendorEvent2) {
		VendorEvent2 = vendorEvent2;
	}
	public double getVendorEvent3() {
		return VendorEvent3;
	}
	public void setVendorEvent3(double vendorEvent3) {
		VendorEvent3 = vendorEvent3;
	}
	public double getVendorEvent4() {
		return VendorEvent4;
	}
	public void setVendorEvent4(double vendorEvent4) {
		VendorEvent4 = vendorEvent4;
	}
}
