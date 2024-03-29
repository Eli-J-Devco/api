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
	
	private double TotalEnergy;
	private double Wmax;
	private double ACActivePower;
	private double ACReactivePower;
	private double ACApparentPower;
	private double ACPowerFactor;
	private double ACGridFrequency;
	private double ACVoltagePhaseAB;
	private double ACVoltagePhaseBC;
	private double ACVoltagePhaseCA;
	private double ACVoltagePhaseAN;
	private double ACVoltagePhaseBN;
	private double ACVoltagePhaseCN;
	private double ACCurrent;
	private double ACCurrentPhaseA;
	private double ACCurrentPhaseB;
	private double ACCurrentPhaseC;
	private double DCVoltage;
	private double DCCurrent;
	private double DCPowerChanel1;
	private double DCVoltageChanel1;
	private double DCCurrentChanel1;
	private double DCOperatingStateChanel1;
	private double DCPowerChanel2;
	private double DCVoltageChanel2;
	private double DCCurrentChanel2;
	private double DCOperatingStateChanel2;
	private double MPPT1Voltage;
	private double MPPT1Current;
	private double MPPT2Voltage;
	private double MPPT2Current;
	private double STRING1;
	private double STRING2;
	private double Panel1;
	private double Panel2;
	private double Panel3;
	private double Panel4;
	private double OperatingState;
	private double Alarmstate;
	private double GlobalState;
	private double CabinetTemperature;
	private double HeatSinkTemperature;
	private double OtherTemperature;
	private double WMaxPercent;
	private double WMaxPercentEnable;
	private double PFSet;
	private double PFSetEnable;
	private double VarMaxPercent;
	private double VarMaxPercentEnable;
	private double ControlINV;
	
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
	public double getTotalEnergy() {
		return TotalEnergy;
	}
	public void setTotalEnergy(double totalEnergy) {
		TotalEnergy = totalEnergy;
	}
	public double getWmax() {
		return Wmax;
	}
	public void setWmax(double wmax) {
		Wmax = wmax;
	}
	public double getACActivePower() {
		return ACActivePower;
	}
	public void setACActivePower(double aCActivePower) {
		ACActivePower = aCActivePower;
	}
	public double getACReactivePower() {
		return ACReactivePower;
	}
	public void setACReactivePower(double aCReactivePower) {
		ACReactivePower = aCReactivePower;
	}
	public double getACApparentPower() {
		return ACApparentPower;
	}
	public void setACApparentPower(double aCApparentPower) {
		ACApparentPower = aCApparentPower;
	}
	public double getACPowerFactor() {
		return ACPowerFactor;
	}
	public void setACPowerFactor(double aCPowerFactor) {
		ACPowerFactor = aCPowerFactor;
	}
	public double getACGridFrequency() {
		return ACGridFrequency;
	}
	public void setACGridFrequency(double aCGridFrequency) {
		ACGridFrequency = aCGridFrequency;
	}
	public double getACVoltagePhaseAB() {
		return ACVoltagePhaseAB;
	}
	public void setACVoltagePhaseAB(double aCVoltagePhaseAB) {
		ACVoltagePhaseAB = aCVoltagePhaseAB;
	}
	public double getACVoltagePhaseBC() {
		return ACVoltagePhaseBC;
	}
	public void setACVoltagePhaseBC(double aCVoltagePhaseBC) {
		ACVoltagePhaseBC = aCVoltagePhaseBC;
	}
	public double getACVoltagePhaseCA() {
		return ACVoltagePhaseCA;
	}
	public void setACVoltagePhaseCA(double aCVoltagePhaseCA) {
		ACVoltagePhaseCA = aCVoltagePhaseCA;
	}
	public double getACVoltagePhaseAN() {
		return ACVoltagePhaseAN;
	}
	public void setACVoltagePhaseAN(double aCVoltagePhaseAN) {
		ACVoltagePhaseAN = aCVoltagePhaseAN;
	}
	public double getACVoltagePhaseBN() {
		return ACVoltagePhaseBN;
	}
	public void setACVoltagePhaseBN(double aCVoltagePhaseBN) {
		ACVoltagePhaseBN = aCVoltagePhaseBN;
	}
	public double getACVoltagePhaseCN() {
		return ACVoltagePhaseCN;
	}
	public void setACVoltagePhaseCN(double aCVoltagePhaseCN) {
		ACVoltagePhaseCN = aCVoltagePhaseCN;
	}
	public double getACCurrent() {
		return ACCurrent;
	}
	public void setACCurrent(double aCCurrent) {
		ACCurrent = aCCurrent;
	}
	public double getACCurrentPhaseA() {
		return ACCurrentPhaseA;
	}
	public void setACCurrentPhaseA(double aCCurrentPhaseA) {
		ACCurrentPhaseA = aCCurrentPhaseA;
	}
	public double getACCurrentPhaseB() {
		return ACCurrentPhaseB;
	}
	public void setACCurrentPhaseB(double aCCurrentPhaseB) {
		ACCurrentPhaseB = aCCurrentPhaseB;
	}
	public double getACCurrentPhaseC() {
		return ACCurrentPhaseC;
	}
	public void setACCurrentPhaseC(double aCCurrentPhaseC) {
		ACCurrentPhaseC = aCCurrentPhaseC;
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
	public double getDCPowerChanel1() {
		return DCPowerChanel1;
	}
	public void setDCPowerChanel1(double dCPowerChanel1) {
		DCPowerChanel1 = dCPowerChanel1;
	}
	public double getDCVoltageChanel1() {
		return DCVoltageChanel1;
	}
	public void setDCVoltageChanel1(double dCVoltageChanel1) {
		DCVoltageChanel1 = dCVoltageChanel1;
	}
	public double getDCCurrentChanel1() {
		return DCCurrentChanel1;
	}
	public void setDCCurrentChanel1(double dCCurrentChanel1) {
		DCCurrentChanel1 = dCCurrentChanel1;
	}
	public double getDCOperatingStateChanel1() {
		return DCOperatingStateChanel1;
	}
	public void setDCOperatingStateChanel1(double dCOperatingStateChanel1) {
		DCOperatingStateChanel1 = dCOperatingStateChanel1;
	}
	public double getDCPowerChanel2() {
		return DCPowerChanel2;
	}
	public void setDCPowerChanel2(double dCPowerChanel2) {
		DCPowerChanel2 = dCPowerChanel2;
	}
	public double getDCVoltageChanel2() {
		return DCVoltageChanel2;
	}
	public void setDCVoltageChanel2(double dCVoltageChanel2) {
		DCVoltageChanel2 = dCVoltageChanel2;
	}
	public double getDCCurrentChanel2() {
		return DCCurrentChanel2;
	}
	public void setDCCurrentChanel2(double dCCurrentChanel2) {
		DCCurrentChanel2 = dCCurrentChanel2;
	}
	public double getDCOperatingStateChanel2() {
		return DCOperatingStateChanel2;
	}
	public void setDCOperatingStateChanel2(double dCOperatingStateChanel2) {
		DCOperatingStateChanel2 = dCOperatingStateChanel2;
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
	public double getSTRING1() {
		return STRING1;
	}
	public void setSTRING1(double sTRING1) {
		STRING1 = sTRING1;
	}
	public double getSTRING2() {
		return STRING2;
	}
	public void setSTRING2(double sTRING2) {
		STRING2 = sTRING2;
	}
	public double getPanel1() {
		return Panel1;
	}
	public void setPanel1(double panel1) {
		Panel1 = panel1;
	}
	public double getPanel2() {
		return Panel2;
	}
	public void setPanel2(double panel2) {
		Panel2 = panel2;
	}
	public double getPanel3() {
		return Panel3;
	}
	public void setPanel3(double panel3) {
		Panel3 = panel3;
	}
	public double getPanel4() {
		return Panel4;
	}
	public void setPanel4(double panel4) {
		Panel4 = panel4;
	}
	public double getOperatingState() {
		return OperatingState;
	}
	public void setOperatingState(double operatingState) {
		OperatingState = operatingState;
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
	public double getWMaxPercent() {
		return WMaxPercent;
	}
	public void setWMaxPercent(double wMaxPercent) {
		WMaxPercent = wMaxPercent;
	}
	public double getWMaxPercentEnable() {
		return WMaxPercentEnable;
	}
	public void setWMaxPercentEnable(double wMaxPercentEnable) {
		WMaxPercentEnable = wMaxPercentEnable;
	}
	public double getPFSet() {
		return PFSet;
	}
	public void setPFSet(double pFSet) {
		PFSet = pFSet;
	}
	public double getPFSetEnable() {
		return PFSetEnable;
	}
	public void setPFSetEnable(double pFSetEnable) {
		PFSetEnable = pFSetEnable;
	}
	public double getVarMaxPercent() {
		return VarMaxPercent;
	}
	public void setVarMaxPercent(double varMaxPercent) {
		VarMaxPercent = varMaxPercent;
	}
	public double getVarMaxPercentEnable() {
		return VarMaxPercentEnable;
	}
	public void setVarMaxPercentEnable(double varMaxPercentEnable) {
		VarMaxPercentEnable = varMaxPercentEnable;
	}
	public double getControlINV() {
		return ControlINV;
	}
	public void setControlINV(double controlINV) {
		ControlINV = controlINV;
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
