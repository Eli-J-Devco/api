/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSEL651REntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double PhaseACurrent;
	private double PhaseAAngle;
	private double PhaseBCurrent;
	private double PhaseBAngle;
	private double PhaseCCurrent;
	private double PhaseCAngle;
	private double ResidualGroundCurrent;
	private double ResidualGroundCurrentAngle;
	private double NeutralCurrent;
	private double NeutralCurrentAngle;
	private double PhaseAVoltageYTerminals;
	private double PhaseAVoltageAngleYTerminals;
	private double PhaseBVoltageYTerminals;
	private double PhaseBVoltageAngleYTerminals;
	private double PhaseCVoltageYTerminals;
	private double PhaseCVoltageAngleYTerminals;
	private double PhaseAVoltageZTerminals;
	private double PhaseAVoltageAngleZTerminals;
	private double PhaseBVoltageZTerminals;
	private double PhaseBVoltageAngleZTerminals;
	private double PhaseCVoltageZTerminals;
	private double PhaseCVoltageAngleZTerminals;
	private double ThreePhaseRealPower;
	private double ThreePhaseReactivePower;
	private double ThreePhasePowerFactor;
	private double ThreePhasePowerFactorLeading;
	private double Frequency;
	private double DCBatteryVoltage;
	private double ThreePhaseRealEnergyIn;
	private double ThreePhaseRealEnergyOut;
	private double ThreePhaseReactiveEnergyIn;
	private double ThreePhaseReactiveEnergyOut;
	private double ActiveSettingsGroup;
	private double FrontPanelLEDs1;
	private double FrontPanelLEDs2;
	private double FrontPanelLEDs3;
	private double Row64;
	private double Row18;
	private double OperatingStatus;
	private double BreakerStatus;
	private double TargetReset;
	private double Open;
	private double Close;
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
	public double getPhaseACurrent() {
		return PhaseACurrent;
	}
	public void setPhaseACurrent(double phaseACurrent) {
		PhaseACurrent = phaseACurrent;
	}
	public double getPhaseAAngle() {
		return PhaseAAngle;
	}
	public void setPhaseAAngle(double phaseAAngle) {
		PhaseAAngle = phaseAAngle;
	}
	public double getPhaseBCurrent() {
		return PhaseBCurrent;
	}
	public void setPhaseBCurrent(double phaseBCurrent) {
		PhaseBCurrent = phaseBCurrent;
	}
	public double getPhaseBAngle() {
		return PhaseBAngle;
	}
	public void setPhaseBAngle(double phaseBAngle) {
		PhaseBAngle = phaseBAngle;
	}
	public double getPhaseCCurrent() {
		return PhaseCCurrent;
	}
	public void setPhaseCCurrent(double phaseCCurrent) {
		PhaseCCurrent = phaseCCurrent;
	}
	public double getPhaseCAngle() {
		return PhaseCAngle;
	}
	public void setPhaseCAngle(double phaseCAngle) {
		PhaseCAngle = phaseCAngle;
	}
	public double getResidualGroundCurrent() {
		return ResidualGroundCurrent;
	}
	public void setResidualGroundCurrent(double residualGroundCurrent) {
		ResidualGroundCurrent = residualGroundCurrent;
	}
	public double getResidualGroundCurrentAngle() {
		return ResidualGroundCurrentAngle;
	}
	public void setResidualGroundCurrentAngle(double residualGroundCurrentAngle) {
		ResidualGroundCurrentAngle = residualGroundCurrentAngle;
	}
	public double getNeutralCurrent() {
		return NeutralCurrent;
	}
	public void setNeutralCurrent(double neutralCurrent) {
		NeutralCurrent = neutralCurrent;
	}
	public double getNeutralCurrentAngle() {
		return NeutralCurrentAngle;
	}
	public void setNeutralCurrentAngle(double neutralCurrentAngle) {
		NeutralCurrentAngle = neutralCurrentAngle;
	}
	public double getPhaseAVoltageYTerminals() {
		return PhaseAVoltageYTerminals;
	}
	public void setPhaseAVoltageYTerminals(double phaseAVoltageYTerminals) {
		PhaseAVoltageYTerminals = phaseAVoltageYTerminals;
	}
	public double getPhaseAVoltageAngleYTerminals() {
		return PhaseAVoltageAngleYTerminals;
	}
	public void setPhaseAVoltageAngleYTerminals(double phaseAVoltageAngleYTerminals) {
		PhaseAVoltageAngleYTerminals = phaseAVoltageAngleYTerminals;
	}
	public double getPhaseBVoltageYTerminals() {
		return PhaseBVoltageYTerminals;
	}
	public void setPhaseBVoltageYTerminals(double phaseBVoltageYTerminals) {
		PhaseBVoltageYTerminals = phaseBVoltageYTerminals;
	}
	public double getPhaseBVoltageAngleYTerminals() {
		return PhaseBVoltageAngleYTerminals;
	}
	public void setPhaseBVoltageAngleYTerminals(double phaseBVoltageAngleYTerminals) {
		PhaseBVoltageAngleYTerminals = phaseBVoltageAngleYTerminals;
	}
	public double getPhaseCVoltageYTerminals() {
		return PhaseCVoltageYTerminals;
	}
	public void setPhaseCVoltageYTerminals(double phaseCVoltageYTerminals) {
		PhaseCVoltageYTerminals = phaseCVoltageYTerminals;
	}
	public double getPhaseCVoltageAngleYTerminals() {
		return PhaseCVoltageAngleYTerminals;
	}
	public void setPhaseCVoltageAngleYTerminals(double phaseCVoltageAngleYTerminals) {
		PhaseCVoltageAngleYTerminals = phaseCVoltageAngleYTerminals;
	}
	public double getPhaseAVoltageZTerminals() {
		return PhaseAVoltageZTerminals;
	}
	public void setPhaseAVoltageZTerminals(double phaseAVoltageZTerminals) {
		PhaseAVoltageZTerminals = phaseAVoltageZTerminals;
	}
	public double getPhaseAVoltageAngleZTerminals() {
		return PhaseAVoltageAngleZTerminals;
	}
	public void setPhaseAVoltageAngleZTerminals(double phaseAVoltageAngleZTerminals) {
		PhaseAVoltageAngleZTerminals = phaseAVoltageAngleZTerminals;
	}
	public double getPhaseBVoltageZTerminals() {
		return PhaseBVoltageZTerminals;
	}
	public void setPhaseBVoltageZTerminals(double phaseBVoltageZTerminals) {
		PhaseBVoltageZTerminals = phaseBVoltageZTerminals;
	}
	public double getPhaseBVoltageAngleZTerminals() {
		return PhaseBVoltageAngleZTerminals;
	}
	public void setPhaseBVoltageAngleZTerminals(double phaseBVoltageAngleZTerminals) {
		PhaseBVoltageAngleZTerminals = phaseBVoltageAngleZTerminals;
	}
	public double getPhaseCVoltageZTerminals() {
		return PhaseCVoltageZTerminals;
	}
	public void setPhaseCVoltageZTerminals(double phaseCVoltageZTerminals) {
		PhaseCVoltageZTerminals = phaseCVoltageZTerminals;
	}
	public double getPhaseCVoltageAngleZTerminals() {
		return PhaseCVoltageAngleZTerminals;
	}
	public void setPhaseCVoltageAngleZTerminals(double phaseCVoltageAngleZTerminals) {
		PhaseCVoltageAngleZTerminals = phaseCVoltageAngleZTerminals;
	}
	public double getThreePhaseRealPower() {
		return ThreePhaseRealPower;
	}
	public void setThreePhaseRealPower(double threePhaseRealPower) {
		ThreePhaseRealPower = threePhaseRealPower;
	}
	public double getThreePhaseReactivePower() {
		return ThreePhaseReactivePower;
	}
	public void setThreePhaseReactivePower(double threePhaseReactivePower) {
		ThreePhaseReactivePower = threePhaseReactivePower;
	}
	public double getThreePhasePowerFactor() {
		return ThreePhasePowerFactor;
	}
	public void setThreePhasePowerFactor(double threePhasePowerFactor) {
		ThreePhasePowerFactor = threePhasePowerFactor;
	}
	public double getThreePhasePowerFactorLeading() {
		return ThreePhasePowerFactorLeading;
	}
	public void setThreePhasePowerFactorLeading(double threePhasePowerFactorLeading) {
		ThreePhasePowerFactorLeading = threePhasePowerFactorLeading;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getDCBatteryVoltage() {
		return DCBatteryVoltage;
	}
	public void setDCBatteryVoltage(double dCBatteryVoltage) {
		DCBatteryVoltage = dCBatteryVoltage;
	}
	public double getThreePhaseRealEnergyIn() {
		return ThreePhaseRealEnergyIn;
	}
	public void setThreePhaseRealEnergyIn(double threePhaseRealEnergyIn) {
		ThreePhaseRealEnergyIn = threePhaseRealEnergyIn;
	}
	public double getThreePhaseRealEnergyOut() {
		return ThreePhaseRealEnergyOut;
	}
	public void setThreePhaseRealEnergyOut(double threePhaseRealEnergyOut) {
		ThreePhaseRealEnergyOut = threePhaseRealEnergyOut;
	}
	public double getThreePhaseReactiveEnergyIn() {
		return ThreePhaseReactiveEnergyIn;
	}
	public void setThreePhaseReactiveEnergyIn(double threePhaseReactiveEnergyIn) {
		ThreePhaseReactiveEnergyIn = threePhaseReactiveEnergyIn;
	}
	public double getThreePhaseReactiveEnergyOut() {
		return ThreePhaseReactiveEnergyOut;
	}
	public void setThreePhaseReactiveEnergyOut(double threePhaseReactiveEnergyOut) {
		ThreePhaseReactiveEnergyOut = threePhaseReactiveEnergyOut;
	}
	public double getActiveSettingsGroup() {
		return ActiveSettingsGroup;
	}
	public void setActiveSettingsGroup(double activeSettingsGroup) {
		ActiveSettingsGroup = activeSettingsGroup;
	}
	public double getFrontPanelLEDs1() {
		return FrontPanelLEDs1;
	}
	public void setFrontPanelLEDs1(double frontPanelLEDs1) {
		FrontPanelLEDs1 = frontPanelLEDs1;
	}
	public double getFrontPanelLEDs2() {
		return FrontPanelLEDs2;
	}
	public void setFrontPanelLEDs2(double frontPanelLEDs2) {
		FrontPanelLEDs2 = frontPanelLEDs2;
	}
	public double getFrontPanelLEDs3() {
		return FrontPanelLEDs3;
	}
	public void setFrontPanelLEDs3(double frontPanelLEDs3) {
		FrontPanelLEDs3 = frontPanelLEDs3;
	}
	public double getRow64() {
		return Row64;
	}
	public void setRow64(double row64) {
		Row64 = row64;
	}
	public double getRow18() {
		return Row18;
	}
	public void setRow18(double row18) {
		Row18 = row18;
	}
	public double getOperatingStatus() {
		return OperatingStatus;
	}
	public void setOperatingStatus(double operatingStatus) {
		OperatingStatus = operatingStatus;
	}
	public double getBreakerStatus() {
		return BreakerStatus;
	}
	public void setBreakerStatus(double breakerStatus) {
		BreakerStatus = breakerStatus;
	}
	public double getTargetReset() {
		return TargetReset;
	}
	public void setTargetReset(double targetReset) {
		TargetReset = targetReset;
	}
	public double getOpen() {
		return Open;
	}
	public void setOpen(double open) {
		Open = open;
	}
	public double getClose() {
		return Close;
	}
	public void setClose(double close) {
		Close = close;
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
