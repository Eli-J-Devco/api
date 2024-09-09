/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSunSpecInverterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
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
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getLow_alarm() {
		return low_alarm;
	}
	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}
	public int getHigh_alarm() {
		return high_alarm;
	}
	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
	}
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
	public double getNvmActivePower() {
		return nvmActivePower;
	}
	public void setNvmActivePower(double nvmActivePower) {
		this.nvmActivePower = nvmActivePower;
	}
	public double getNvmActiveEnergy() {
		return nvmActiveEnergy;
	}
	public void setNvmActiveEnergy(double nvmActiveEnergy) {
		this.nvmActiveEnergy = nvmActiveEnergy;
	}
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getJob_tablename() {
		return job_tablename;
	}
	public void setJob_tablename(String job_tablename) {
		this.job_tablename = job_tablename;
	}
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
	}
}
