/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSatconPowergate225InverterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double Fault1;
	private double Fault2;
	private double Fault3;
	private double Fault4;
	private double GridStatus;
	private double Status6;
	private double Status7;
	private double PCSState;
	private double DCInputPower;
	private double DC_Link_Volts;
	private double DCInputVoltage;
	private double DCInputCurrent;
	private double OutputKVAR;
	private double OutputKW;
	private double OutputKVA;
	private double Line_Volts_A_TEST;
	private double Line_Volts_B_TEST;
	private double Line_Volts_C_TEST;
	private double Line_Amps_A_TEST;
	private double Line_Amps_B_TEST;
	private double Line_Amps_C_TEST;
	private double NeutralCurrent;
	private double StopCode;
	private double KWHlow;
	private double KWH;
	private double PowerFactor;
	private double LineFreq;
	private double OutputPowerLimit;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	
	private int totalFault1;
	private int totalFault2;
	private int totalFault3;
	private int totalFault4;
	private int totalGridStatus;
	private int totalStatus6;
	private int totalStatus7;
	
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	
	
	
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
	
	
	
	public int getTotalFault1() {
		return totalFault1;
	}
	public void setTotalFault1(int totalFault1) {
		this.totalFault1 = totalFault1;
	}
	public int getTotalFault2() {
		return totalFault2;
	}
	public void setTotalFault2(int totalFault2) {
		this.totalFault2 = totalFault2;
	}
	public int getTotalFault3() {
		return totalFault3;
	}
	public void setTotalFault3(int totalFault3) {
		this.totalFault3 = totalFault3;
	}
	public int getTotalFault4() {
		return totalFault4;
	}
	public void setTotalFault4(int totalFault4) {
		this.totalFault4 = totalFault4;
	}
	public int getTotalGridStatus() {
		return totalGridStatus;
	}
	public void setTotalGridStatus(int totalGridStatus) {
		this.totalGridStatus = totalGridStatus;
	}
	public int getTotalStatus6() {
		return totalStatus6;
	}
	public void setTotalStatus6(int totalStatus6) {
		this.totalStatus6 = totalStatus6;
	}
	public int getTotalStatus7() {
		return totalStatus7;
	}
	public void setTotalStatus7(int totalStatus7) {
		this.totalStatus7 = totalStatus7;
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
	public double getFault1() {
		return Fault1;
	}
	public void setFault1(double fault1) {
		Fault1 = fault1;
	}
	public double getFault2() {
		return Fault2;
	}
	public void setFault2(double fault2) {
		Fault2 = fault2;
	}
	public double getFault3() {
		return Fault3;
	}
	public void setFault3(double fault3) {
		Fault3 = fault3;
	}
	public double getFault4() {
		return Fault4;
	}
	public void setFault4(double fault4) {
		Fault4 = fault4;
	}
	public double getGridStatus() {
		return GridStatus;
	}
	public void setGridStatus(double gridStatus) {
		GridStatus = gridStatus;
	}
	public double getStatus6() {
		return Status6;
	}
	public void setStatus6(double status6) {
		Status6 = status6;
	}
	public double getStatus7() {
		return Status7;
	}
	public void setStatus7(double status7) {
		Status7 = status7;
	}
	public double getPCSState() {
		return PCSState;
	}
	public void setPCSState(double pCSState) {
		PCSState = pCSState;
	}
	public double getDCInputPower() {
		return DCInputPower;
	}
	public void setDCInputPower(double dCInputPower) {
		DCInputPower = dCInputPower;
	}
	public double getDC_Link_Volts() {
		return DC_Link_Volts;
	}
	public void setDC_Link_Volts(double dC_Link_Volts) {
		DC_Link_Volts = dC_Link_Volts;
	}
	public double getDCInputVoltage() {
		return DCInputVoltage;
	}
	public void setDCInputVoltage(double dCInputVoltage) {
		DCInputVoltage = dCInputVoltage;
	}
	public double getDCInputCurrent() {
		return DCInputCurrent;
	}
	public void setDCInputCurrent(double dCInputCurrent) {
		DCInputCurrent = dCInputCurrent;
	}
	public double getOutputKVAR() {
		return OutputKVAR;
	}
	public void setOutputKVAR(double outputKVAR) {
		OutputKVAR = outputKVAR;
	}
	public double getOutputKW() {
		return OutputKW;
	}
	public void setOutputKW(double outputKW) {
		OutputKW = outputKW;
	}
	public double getOutputKVA() {
		return OutputKVA;
	}
	public void setOutputKVA(double outputKVA) {
		OutputKVA = outputKVA;
	}
	public double getLine_Volts_A_TEST() {
		return Line_Volts_A_TEST;
	}
	public void setLine_Volts_A_TEST(double line_Volts_A_TEST) {
		Line_Volts_A_TEST = line_Volts_A_TEST;
	}
	public double getLine_Volts_B_TEST() {
		return Line_Volts_B_TEST;
	}
	public void setLine_Volts_B_TEST(double line_Volts_B_TEST) {
		Line_Volts_B_TEST = line_Volts_B_TEST;
	}
	public double getLine_Volts_C_TEST() {
		return Line_Volts_C_TEST;
	}
	public void setLine_Volts_C_TEST(double line_Volts_C_TEST) {
		Line_Volts_C_TEST = line_Volts_C_TEST;
	}
	public double getLine_Amps_A_TEST() {
		return Line_Amps_A_TEST;
	}
	public void setLine_Amps_A_TEST(double line_Amps_A_TEST) {
		Line_Amps_A_TEST = line_Amps_A_TEST;
	}
	public double getLine_Amps_B_TEST() {
		return Line_Amps_B_TEST;
	}
	public void setLine_Amps_B_TEST(double line_Amps_B_TEST) {
		Line_Amps_B_TEST = line_Amps_B_TEST;
	}
	public double getLine_Amps_C_TEST() {
		return Line_Amps_C_TEST;
	}
	public void setLine_Amps_C_TEST(double line_Amps_C_TEST) {
		Line_Amps_C_TEST = line_Amps_C_TEST;
	}
	public double getNeutralCurrent() {
		return NeutralCurrent;
	}
	public void setNeutralCurrent(double neutralCurrent) {
		NeutralCurrent = neutralCurrent;
	}
	public double getStopCode() {
		return StopCode;
	}
	public void setStopCode(double stopCode) {
		StopCode = stopCode;
	}
	public double getKWHlow() {
		return KWHlow;
	}
	public void setKWHlow(double kWHlow) {
		KWHlow = kWHlow;
	}
	public double getKWH() {
		return KWH;
	}
	public void setKWH(double kWH) {
		KWH = kWH;
	}
	public double getPowerFactor() {
		return PowerFactor;
	}
	public void setPowerFactor(double powerFactor) {
		PowerFactor = powerFactor;
	}
	public double getLineFreq() {
		return LineFreq;
	}
	public void setLineFreq(double lineFreq) {
		LineFreq = lineFreq;
	}
	public double getOutputPowerLimit() {
		return OutputPowerLimit;
	}
	public void setOutputPowerLimit(double outputPowerLimit) {
		OutputPowerLimit = outputPowerLimit;
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
