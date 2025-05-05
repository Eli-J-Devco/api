/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelGasMeterEntity extends ModelBaseEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double ProcessedValue;
	private double Digit1;
	private double Digit2;
	private double Digit3;
	private double Digit4;
	private double Digit5;
	private double Digit6;
	private double Digit7;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	
	
	public double getDigit7() {
		return Digit7;
	}
	public void setDigit7(double digit7) {
		Digit7 = digit7;
	}
	public double getDigit5() {
		return Digit5;
	}
	public void setDigit5(double digit5) {
		Digit5 = digit5;
	}
	public double getDigit6() {
		return Digit6;
	}
	public void setDigit6(double digit6) {
		Digit6 = digit6;
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
	public double getProcessedValue() {
		return ProcessedValue;
	}
	public void setProcessedValue(double processedValue) {
		ProcessedValue = processedValue;
	}
	public double getDigit1() {
		return Digit1;
	}
	public void setDigit1(double digit1) {
		Digit1 = digit1;
	}
	public double getDigit2() {
		return Digit2;
	}
	public void setDigit2(double digit2) {
		Digit2 = digit2;
	}
	public double getDigit3() {
		return Digit3;
	}
	public void setDigit3(double digit3) {
		Digit3 = digit3;
	}
	public double getDigit4() {
		return Digit4;
	}
	public void setDigit4(double digit4) {
		Digit4 = digit4;
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
