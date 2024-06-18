/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSolarEdgeInverterV1Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double C_DeviceAddress;
	private double C_SunSpec_DID;
	private double C_SunSpec_Length;
	private double I_AC_Current;
	private double I_AC_CurrentA;
	private double I_AC_CurrentB;
	private double I_AC_CurrentC;
	private double I_AC_Current_SF;
	private double I_AC_VoltageAB;
	private double I_AC_VoltageBC;
	private double I_AC_VoltageCA;
	private double I_AC_VoltageAN;
	private double I_AC_VoltageBN;
	private double I_AC_VoltageCN;
	private double I_AC_Voltage_SF;
	private double I_AC_Power;
	private double I_AC_Power_SF;
	private double I_AC_Frequency;
	private double I_AC_Frequency_SF;
	private double I_AC_VA;
	private double I_AC_VA_SF;
	private double I_AC_VAR;
	private double I_AC_VAR_SF;
	private double I_AC_PF;
	private double I_AC_PF_SF;
	private double I_AC_Energy_WH;
	private double I_AC_Energy_WH_SF;
	private double I_DC_Current;
	private double I_DC_Current_SF;
	private double I_DC_Voltage;
	private double I_DC_Voltage_SF;
	private double I_DC_Power;
	private double I_DC_Power_SF;
	private double I_Temp_Sink;
	private double I_Temp_SF;
	private double I_Status;
	private double I_Status_Vendor;
	private long Serial_Number_Hex_4_Reg;
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
	public long getSerial_Number_Hex_4_Reg() {
		return Serial_Number_Hex_4_Reg;
	}
	public void setSerial_Number_Hex_4_Reg(long serial_Number_Hex_4_Reg) {
		Serial_Number_Hex_4_Reg = serial_Number_Hex_4_Reg;
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
	public double getC_DeviceAddress() {
		return C_DeviceAddress;
	}
	public void setC_DeviceAddress(double c_DeviceAddress) {
		C_DeviceAddress = c_DeviceAddress;
	}
	public double getC_SunSpec_DID() {
		return C_SunSpec_DID;
	}
	public void setC_SunSpec_DID(double c_SunSpec_DID) {
		C_SunSpec_DID = c_SunSpec_DID;
	}
	public double getC_SunSpec_Length() {
		return C_SunSpec_Length;
	}
	public void setC_SunSpec_Length(double c_SunSpec_Length) {
		C_SunSpec_Length = c_SunSpec_Length;
	}
	public double getI_AC_Current() {
		return I_AC_Current;
	}
	public void setI_AC_Current(double i_AC_Current) {
		I_AC_Current = i_AC_Current;
	}
	public double getI_AC_CurrentA() {
		return I_AC_CurrentA;
	}
	public void setI_AC_CurrentA(double i_AC_CurrentA) {
		I_AC_CurrentA = i_AC_CurrentA;
	}
	public double getI_AC_CurrentB() {
		return I_AC_CurrentB;
	}
	public void setI_AC_CurrentB(double i_AC_CurrentB) {
		I_AC_CurrentB = i_AC_CurrentB;
	}
	public double getI_AC_CurrentC() {
		return I_AC_CurrentC;
	}
	public void setI_AC_CurrentC(double i_AC_CurrentC) {
		I_AC_CurrentC = i_AC_CurrentC;
	}
	public double getI_AC_Current_SF() {
		return I_AC_Current_SF;
	}
	public void setI_AC_Current_SF(double i_AC_Current_SF) {
		I_AC_Current_SF = i_AC_Current_SF;
	}
	public double getI_AC_VoltageAB() {
		return I_AC_VoltageAB;
	}
	public void setI_AC_VoltageAB(double i_AC_VoltageAB) {
		I_AC_VoltageAB = i_AC_VoltageAB;
	}
	public double getI_AC_VoltageBC() {
		return I_AC_VoltageBC;
	}
	public void setI_AC_VoltageBC(double i_AC_VoltageBC) {
		I_AC_VoltageBC = i_AC_VoltageBC;
	}
	public double getI_AC_VoltageCA() {
		return I_AC_VoltageCA;
	}
	public void setI_AC_VoltageCA(double i_AC_VoltageCA) {
		I_AC_VoltageCA = i_AC_VoltageCA;
	}
	public double getI_AC_VoltageAN() {
		return I_AC_VoltageAN;
	}
	public void setI_AC_VoltageAN(double i_AC_VoltageAN) {
		I_AC_VoltageAN = i_AC_VoltageAN;
	}
	public double getI_AC_VoltageBN() {
		return I_AC_VoltageBN;
	}
	public void setI_AC_VoltageBN(double i_AC_VoltageBN) {
		I_AC_VoltageBN = i_AC_VoltageBN;
	}
	public double getI_AC_VoltageCN() {
		return I_AC_VoltageCN;
	}
	public void setI_AC_VoltageCN(double i_AC_VoltageCN) {
		I_AC_VoltageCN = i_AC_VoltageCN;
	}
	public double getI_AC_Voltage_SF() {
		return I_AC_Voltage_SF;
	}
	public void setI_AC_Voltage_SF(double i_AC_Voltage_SF) {
		I_AC_Voltage_SF = i_AC_Voltage_SF;
	}
	public double getI_AC_Power() {
		return I_AC_Power;
	}
	public void setI_AC_Power(double i_AC_Power) {
		I_AC_Power = i_AC_Power;
	}
	public double getI_AC_Power_SF() {
		return I_AC_Power_SF;
	}
	public void setI_AC_Power_SF(double i_AC_Power_SF) {
		I_AC_Power_SF = i_AC_Power_SF;
	}
	public double getI_AC_Frequency() {
		return I_AC_Frequency;
	}
	public void setI_AC_Frequency(double i_AC_Frequency) {
		I_AC_Frequency = i_AC_Frequency;
	}
	public double getI_AC_Frequency_SF() {
		return I_AC_Frequency_SF;
	}
	public void setI_AC_Frequency_SF(double i_AC_Frequency_SF) {
		I_AC_Frequency_SF = i_AC_Frequency_SF;
	}
	public double getI_AC_VA() {
		return I_AC_VA;
	}
	public void setI_AC_VA(double i_AC_VA) {
		I_AC_VA = i_AC_VA;
	}
	public double getI_AC_VA_SF() {
		return I_AC_VA_SF;
	}
	public void setI_AC_VA_SF(double i_AC_VA_SF) {
		I_AC_VA_SF = i_AC_VA_SF;
	}
	public double getI_AC_VAR() {
		return I_AC_VAR;
	}
	public void setI_AC_VAR(double i_AC_VAR) {
		I_AC_VAR = i_AC_VAR;
	}
	public double getI_AC_VAR_SF() {
		return I_AC_VAR_SF;
	}
	public void setI_AC_VAR_SF(double i_AC_VAR_SF) {
		I_AC_VAR_SF = i_AC_VAR_SF;
	}
	public double getI_AC_PF() {
		return I_AC_PF;
	}
	public void setI_AC_PF(double i_AC_PF) {
		I_AC_PF = i_AC_PF;
	}
	public double getI_AC_PF_SF() {
		return I_AC_PF_SF;
	}
	public void setI_AC_PF_SF(double i_AC_PF_SF) {
		I_AC_PF_SF = i_AC_PF_SF;
	}
	public double getI_AC_Energy_WH() {
		return I_AC_Energy_WH;
	}
	public void setI_AC_Energy_WH(double i_AC_Energy_WH) {
		I_AC_Energy_WH = i_AC_Energy_WH;
	}
	public double getI_AC_Energy_WH_SF() {
		return I_AC_Energy_WH_SF;
	}
	public void setI_AC_Energy_WH_SF(double i_AC_Energy_WH_SF) {
		I_AC_Energy_WH_SF = i_AC_Energy_WH_SF;
	}
	public double getI_DC_Current() {
		return I_DC_Current;
	}
	public void setI_DC_Current(double i_DC_Current) {
		I_DC_Current = i_DC_Current;
	}
	public double getI_DC_Current_SF() {
		return I_DC_Current_SF;
	}
	public void setI_DC_Current_SF(double i_DC_Current_SF) {
		I_DC_Current_SF = i_DC_Current_SF;
	}
	public double getI_DC_Voltage() {
		return I_DC_Voltage;
	}
	public void setI_DC_Voltage(double i_DC_Voltage) {
		I_DC_Voltage = i_DC_Voltage;
	}
	public double getI_DC_Voltage_SF() {
		return I_DC_Voltage_SF;
	}
	public void setI_DC_Voltage_SF(double i_DC_Voltage_SF) {
		I_DC_Voltage_SF = i_DC_Voltage_SF;
	}
	public double getI_DC_Power() {
		return I_DC_Power;
	}
	public void setI_DC_Power(double i_DC_Power) {
		I_DC_Power = i_DC_Power;
	}
	public double getI_DC_Power_SF() {
		return I_DC_Power_SF;
	}
	public void setI_DC_Power_SF(double i_DC_Power_SF) {
		I_DC_Power_SF = i_DC_Power_SF;
	}
	public double getI_Temp_Sink() {
		return I_Temp_Sink;
	}
	public void setI_Temp_Sink(double i_Temp_Sink) {
		I_Temp_Sink = i_Temp_Sink;
	}
	public double getI_Temp_SF() {
		return I_Temp_SF;
	}
	public void setI_Temp_SF(double i_Temp_SF) {
		I_Temp_SF = i_Temp_SF;
	}
	public double getI_Status() {
		return I_Status;
	}
	public void setI_Status(double i_Status) {
		I_Status = i_Status;
	}
	public double getI_Status_Vendor() {
		return I_Status_Vendor;
	}
	public void setI_Status_Vendor(double i_Status_Vendor) {
		I_Status_Vendor = i_Status_Vendor;
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
