/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelAbbTrioClass6210Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double AuroraType;
	private double GridType;
	private double TransformerType;
	private double StatesByte0;
	private double StatesByte1;
	private double StatesByte2;
	private double StatesByte3;
	private double StatesByte4;
	private double TotalEnergy;
	private double GridVoltage;
	private double GridCurrent;
	private double GridPower;
	private double Frequency;
	private double Input1Power;
	private double Input1Voltage;
	private double Input1Current;
	private double Input2Power;
	private double Input2Voltage;
	private double Input2Current;
	private double InverterTemperature;
	private double BooseterTemperature;
	private double IslolationResistance;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	
	private int totalStatesByte0;
	private int totalStatesByte1;
	private int totalStatesByte2;
	private int totalStatesByte3;
	private int totalStatesByte4;
	
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
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
	
	
	
	public int getTotalStatesByte0() {
		return totalStatesByte0;
	}
	public void setTotalStatesByte0(int totalStatesByte0) {
		this.totalStatesByte0 = totalStatesByte0;
	}
	public int getTotalStatesByte1() {
		return totalStatesByte1;
	}
	public void setTotalStatesByte1(int totalStatesByte1) {
		this.totalStatesByte1 = totalStatesByte1;
	}
	public int getTotalStatesByte2() {
		return totalStatesByte2;
	}
	public void setTotalStatesByte2(int totalStatesByte2) {
		this.totalStatesByte2 = totalStatesByte2;
	}
	public int getTotalStatesByte3() {
		return totalStatesByte3;
	}
	public void setTotalStatesByte3(int totalStatesByte3) {
		this.totalStatesByte3 = totalStatesByte3;
	}
	public int getTotalStatesByte4() {
		return totalStatesByte4;
	}
	public void setTotalStatesByte4(int totalStatesByte4) {
		this.totalStatesByte4 = totalStatesByte4;
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
	public double getAuroraType() {
		return AuroraType;
	}
	public void setAuroraType(double auroraType) {
		AuroraType = auroraType;
	}
	public double getGridType() {
		return GridType;
	}
	public void setGridType(double gridType) {
		GridType = gridType;
	}
	public double getTransformerType() {
		return TransformerType;
	}
	public void setTransformerType(double transformerType) {
		TransformerType = transformerType;
	}
	public double getStatesByte0() {
		return StatesByte0;
	}
	public void setStatesByte0(double statesByte0) {
		StatesByte0 = statesByte0;
	}
	public double getStatesByte1() {
		return StatesByte1;
	}
	public void setStatesByte1(double statesByte1) {
		StatesByte1 = statesByte1;
	}
	public double getStatesByte2() {
		return StatesByte2;
	}
	public void setStatesByte2(double statesByte2) {
		StatesByte2 = statesByte2;
	}
	public double getStatesByte3() {
		return StatesByte3;
	}
	public void setStatesByte3(double statesByte3) {
		StatesByte3 = statesByte3;
	}
	public double getStatesByte4() {
		return StatesByte4;
	}
	public void setStatesByte4(double statesByte4) {
		StatesByte4 = statesByte4;
	}
	public double getTotalEnergy() {
		return TotalEnergy;
	}
	public void setTotalEnergy(double totalEnergy) {
		TotalEnergy = totalEnergy;
	}
	public double getGridVoltage() {
		return GridVoltage;
	}
	public void setGridVoltage(double gridVoltage) {
		GridVoltage = gridVoltage;
	}
	public double getGridCurrent() {
		return GridCurrent;
	}
	public void setGridCurrent(double gridCurrent) {
		GridCurrent = gridCurrent;
	}
	public double getGridPower() {
		return GridPower;
	}
	public void setGridPower(double gridPower) {
		GridPower = gridPower;
	}
	public double getFrequency() {
		return Frequency;
	}
	public void setFrequency(double frequency) {
		Frequency = frequency;
	}
	public double getInput1Power() {
		return Input1Power;
	}
	public void setInput1Power(double input1Power) {
		Input1Power = input1Power;
	}
	public double getInput1Voltage() {
		return Input1Voltage;
	}
	public void setInput1Voltage(double input1Voltage) {
		Input1Voltage = input1Voltage;
	}
	public double getInput1Current() {
		return Input1Current;
	}
	public void setInput1Current(double input1Current) {
		Input1Current = input1Current;
	}
	public double getInput2Power() {
		return Input2Power;
	}
	public void setInput2Power(double input2Power) {
		Input2Power = input2Power;
	}
	public double getInput2Voltage() {
		return Input2Voltage;
	}
	public void setInput2Voltage(double input2Voltage) {
		Input2Voltage = input2Voltage;
	}
	public double getInput2Current() {
		return Input2Current;
	}
	public void setInput2Current(double input2Current) {
		Input2Current = input2Current;
	}
	public double getInverterTemperature() {
		return InverterTemperature;
	}
	public void setInverterTemperature(double inverterTemperature) {
		InverterTemperature = inverterTemperature;
	}
	public double getBooseterTemperature() {
		return BooseterTemperature;
	}
	public void setBooseterTemperature(double booseterTemperature) {
		BooseterTemperature = booseterTemperature;
	}
	public double getIslolationResistance() {
		return IslolationResistance;
	}
	public void setIslolationResistance(double islolationResistance) {
		IslolationResistance = islolationResistance;
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
	
	
	
	
}
