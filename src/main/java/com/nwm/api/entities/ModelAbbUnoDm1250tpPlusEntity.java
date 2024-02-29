/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelAbbUnoDm1250tpPlusEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double Powerfactorcosphi;
	private double Energytotal;
	private double Gridfrequency;
	private double CurrentAC;
	private double CurrentDCtotal;
	private double PowerAC;
	private double VoltageAC;
	private double VoltageACphase1;
	private double VoltageACphase2;
	private double VoltageACphase3;
	private double PhasevoltageL1L2;
	private double PhasevoltageL2L3;
	private double PhasevoltageL3L1;
	private double VoltageDC;
	private double CabinetTemperature;
	private double HeatSinkTemperature;
	private double OtherTemperature;
	private double Apparentpower;
	private double Reactivepower;
	private double DCCurrentChanel1;
	private double DCVoltageChanel1;
	private double DCPowerChanel1;
	private double OperatingStateChanel1;
	private double DCCurrentChanel2;
	private double DCVoltageChanel2;
	private double DCPowerChanel2;
	private double OperatingStateChanel2;
	private double Inverterstate;
	private double Alarmstate;
	private double GlobalState;
	private double WMax;
	
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
	public double getPowerfactorcosphi() {
		return Powerfactorcosphi;
	}
	public void setPowerfactorcosphi(double powerfactorcosphi) {
		Powerfactorcosphi = powerfactorcosphi;
	}
	public double getEnergytotal() {
		return Energytotal;
	}
	public void setEnergytotal(double energytotal) {
		Energytotal = energytotal;
	}
	public double getGridfrequency() {
		return Gridfrequency;
	}
	public void setGridfrequency(double gridfrequency) {
		Gridfrequency = gridfrequency;
	}
	public double getCurrentAC() {
		return CurrentAC;
	}
	public void setCurrentAC(double currentAC) {
		CurrentAC = currentAC;
	}
	public double getCurrentDCtotal() {
		return CurrentDCtotal;
	}
	public void setCurrentDCtotal(double currentDCtotal) {
		CurrentDCtotal = currentDCtotal;
	}
	public double getPowerAC() {
		return PowerAC;
	}
	public void setPowerAC(double powerAC) {
		PowerAC = powerAC;
	}
	public double getVoltageAC() {
		return VoltageAC;
	}
	public void setVoltageAC(double voltageAC) {
		VoltageAC = voltageAC;
	}
	public double getVoltageACphase1() {
		return VoltageACphase1;
	}
	public void setVoltageACphase1(double voltageACphase1) {
		VoltageACphase1 = voltageACphase1;
	}
	public double getVoltageACphase2() {
		return VoltageACphase2;
	}
	public void setVoltageACphase2(double voltageACphase2) {
		VoltageACphase2 = voltageACphase2;
	}
	public double getVoltageACphase3() {
		return VoltageACphase3;
	}
	public void setVoltageACphase3(double voltageACphase3) {
		VoltageACphase3 = voltageACphase3;
	}
	public double getPhasevoltageL1L2() {
		return PhasevoltageL1L2;
	}
	public void setPhasevoltageL1L2(double phasevoltageL1L2) {
		PhasevoltageL1L2 = phasevoltageL1L2;
	}
	public double getPhasevoltageL2L3() {
		return PhasevoltageL2L3;
	}
	public void setPhasevoltageL2L3(double phasevoltageL2L3) {
		PhasevoltageL2L3 = phasevoltageL2L3;
	}
	public double getPhasevoltageL3L1() {
		return PhasevoltageL3L1;
	}
	public void setPhasevoltageL3L1(double phasevoltageL3L1) {
		PhasevoltageL3L1 = phasevoltageL3L1;
	}
	public double getVoltageDC() {
		return VoltageDC;
	}
	public void setVoltageDC(double voltageDC) {
		VoltageDC = voltageDC;
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
	public double getOtherTemperature() {
		return OtherTemperature;
	}
	public void setOtherTemperature(double otherTemperature) {
		OtherTemperature = otherTemperature;
	}
	public double getApparentpower() {
		return Apparentpower;
	}
	public void setApparentpower(double apparentpower) {
		Apparentpower = apparentpower;
	}
	public double getReactivepower() {
		return Reactivepower;
	}
	public void setReactivepower(double reactivepower) {
		Reactivepower = reactivepower;
	}
	public double getDCCurrentChanel1() {
		return DCCurrentChanel1;
	}
	public void setDCCurrentChanel1(double dCCurrentChanel1) {
		DCCurrentChanel1 = dCCurrentChanel1;
	}
	public double getDCVoltageChanel1() {
		return DCVoltageChanel1;
	}
	public void setDCVoltageChanel1(double dCVoltageChanel1) {
		DCVoltageChanel1 = dCVoltageChanel1;
	}
	public double getDCPowerChanel1() {
		return DCPowerChanel1;
	}
	public void setDCPowerChanel1(double dCPowerChanel1) {
		DCPowerChanel1 = dCPowerChanel1;
	}
	public double getOperatingStateChanel1() {
		return OperatingStateChanel1;
	}
	public void setOperatingStateChanel1(double operatingStateChanel1) {
		OperatingStateChanel1 = operatingStateChanel1;
	}
	public double getDCCurrentChanel2() {
		return DCCurrentChanel2;
	}
	public void setDCCurrentChanel2(double dCCurrentChanel2) {
		DCCurrentChanel2 = dCCurrentChanel2;
	}
	public double getDCVoltageChanel2() {
		return DCVoltageChanel2;
	}
	public void setDCVoltageChanel2(double dCVoltageChanel2) {
		DCVoltageChanel2 = dCVoltageChanel2;
	}
	public double getDCPowerChanel2() {
		return DCPowerChanel2;
	}
	public void setDCPowerChanel2(double dCPowerChanel2) {
		DCPowerChanel2 = dCPowerChanel2;
	}
	public double getOperatingStateChanel2() {
		return OperatingStateChanel2;
	}
	public void setOperatingStateChanel2(double operatingStateChanel2) {
		OperatingStateChanel2 = operatingStateChanel2;
	}
	public double getInverterstate() {
		return Inverterstate;
	}
	public void setInverterstate(double inverterstate) {
		Inverterstate = inverterstate;
	}
	public double getAlarmstate() {
		return Alarmstate;
	}
	public void setAlarmstate(double alarmstate) {
		Alarmstate = alarmstate;
	}
	public double getGlobalState() {
		return GlobalState;
	}
	public void setGlobalState(double globalState) {
		GlobalState = globalState;
	}
	public double getWMax() {
		return WMax;
	}
	public void setWMax(double wMax) {
		WMax = wMax;
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
