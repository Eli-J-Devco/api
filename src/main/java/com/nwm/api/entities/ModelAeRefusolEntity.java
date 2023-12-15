/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelAeRefusolEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double ACPower;
	private double ACVoltageAverageRMS;
	private double ACVoltage1RMS;
	private double ACVoltage2RMS;
	private double ACVoltage3RMS;
	private double ACCurrentSum;
	private double ACCurrent1;
	private double ACCurrent2;
	private double ACCurrent3;
	private double ACFrequency1;
	private double ACFrequency2;
	private double ACFrequency3;
	private double DCPower;
	private double DCVoltage;
	private double DCCurrent;
	private double HeatSink;
	private double Interior;
	private double Irradiation;
	private double Panel;
	private double DailyYield;
	private double TotalYield;
	private double OperatingHours;
	private String Status;
	private String ErrorMessageCode;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
private int enable_alert;
	
	
	
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
	}
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
	public double getACPower() {
		return ACPower;
	}
	public void setACPower(double aCPower) {
		ACPower = aCPower;
	}
	public double getACVoltageAverageRMS() {
		return ACVoltageAverageRMS;
	}
	public void setACVoltageAverageRMS(double aCVoltageAverageRMS) {
		ACVoltageAverageRMS = aCVoltageAverageRMS;
	}
	public double getACVoltage1RMS() {
		return ACVoltage1RMS;
	}
	public void setACVoltage1RMS(double aCVoltage1RMS) {
		ACVoltage1RMS = aCVoltage1RMS;
	}
	public double getACVoltage2RMS() {
		return ACVoltage2RMS;
	}
	public void setACVoltage2RMS(double aCVoltage2RMS) {
		ACVoltage2RMS = aCVoltage2RMS;
	}
	public double getACVoltage3RMS() {
		return ACVoltage3RMS;
	}
	public void setACVoltage3RMS(double aCVoltage3RMS) {
		ACVoltage3RMS = aCVoltage3RMS;
	}
	public double getACCurrentSum() {
		return ACCurrentSum;
	}
	public void setACCurrentSum(double aCCurrentSum) {
		ACCurrentSum = aCCurrentSum;
	}
	public double getACCurrent1() {
		return ACCurrent1;
	}
	public void setACCurrent1(double aCCurrent1) {
		ACCurrent1 = aCCurrent1;
	}
	public double getACCurrent2() {
		return ACCurrent2;
	}
	public void setACCurrent2(double aCCurrent2) {
		ACCurrent2 = aCCurrent2;
	}
	public double getACCurrent3() {
		return ACCurrent3;
	}
	public void setACCurrent3(double aCCurrent3) {
		ACCurrent3 = aCCurrent3;
	}
	public double getACFrequency1() {
		return ACFrequency1;
	}
	public void setACFrequency1(double aCFrequency1) {
		ACFrequency1 = aCFrequency1;
	}
	public double getACFrequency2() {
		return ACFrequency2;
	}
	public void setACFrequency2(double aCFrequency2) {
		ACFrequency2 = aCFrequency2;
	}
	public double getACFrequency3() {
		return ACFrequency3;
	}
	public void setACFrequency3(double aCFrequency3) {
		ACFrequency3 = aCFrequency3;
	}
	public double getDCPower() {
		return DCPower;
	}
	public void setDCPower(double dCPower) {
		DCPower = dCPower;
	}
	public double getDCVoltage() {
		return DCVoltage;
	}
	public void setDCVoltage(double dCVoltage) {
		DCVoltage = dCVoltage;
	}
	public double getDCCurrent() {
		return DCCurrent;
	}
	public void setDCCurrent(double dCCurrent) {
		DCCurrent = dCCurrent;
	}
	public double getHeatSink() {
		return HeatSink;
	}
	public void setHeatSink(double heatSink) {
		HeatSink = heatSink;
	}
	public double getInterior() {
		return Interior;
	}
	public void setInterior(double interior) {
		Interior = interior;
	}
	public double getIrradiation() {
		return Irradiation;
	}
	public void setIrradiation(double irradiation) {
		Irradiation = irradiation;
	}
	public double getPanel() {
		return Panel;
	}
	public void setPanel(double panel) {
		Panel = panel;
	}
	public double getDailyYield() {
		return DailyYield;
	}
	public void setDailyYield(double dailyYield) {
		DailyYield = dailyYield;
	}
	public double getTotalYield() {
		return TotalYield;
	}
	public void setTotalYield(double totalYield) {
		TotalYield = totalYield;
	}
	public double getOperatingHours() {
		return OperatingHours;
	}
	public void setOperatingHours(double operatingHours) {
		OperatingHours = operatingHours;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getErrorMessageCode() {
		return ErrorMessageCode;
	}
	public void setErrorMessageCode(String errorMessageCode) {
		ErrorMessageCode = errorMessageCode;
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
	

}
