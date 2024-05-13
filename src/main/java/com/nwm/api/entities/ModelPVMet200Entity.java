/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPVMet200Entity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double C_SunSpec_ID;
	private double E_BaseMet_Air_Temperature;
	private double E_Irradiance_Plane_Of_Array_1;
	private double E_BOM_Temp_1;
	private double E_BOM_Temp_2;
	private double E_BaseMet_Wind_Speed;
	private double E_BaseMet_Wind_Direction;
	private double E_Irradiance_Global_Horizontal_1;
	private double nvm_irradiance;
	private double nvm_temperature;
	private double nvm_panel_temperature;
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
	public double getC_SunSpec_ID() {
		return C_SunSpec_ID;
	}
	public void setC_SunSpec_ID(double c_SunSpec_ID) {
		C_SunSpec_ID = c_SunSpec_ID;
	}
	public double getE_BaseMet_Air_Temperature() {
		return E_BaseMet_Air_Temperature;
	}
	public void setE_BaseMet_Air_Temperature(double e_BaseMet_Air_Temperature) {
		E_BaseMet_Air_Temperature = e_BaseMet_Air_Temperature;
	}
	public double getE_Irradiance_Plane_Of_Array_1() {
		return E_Irradiance_Plane_Of_Array_1;
	}
	public void setE_Irradiance_Plane_Of_Array_1(double e_Irradiance_Plane_Of_Array_1) {
		E_Irradiance_Plane_Of_Array_1 = e_Irradiance_Plane_Of_Array_1;
	}
	public double getE_BOM_Temp_1() {
		return E_BOM_Temp_1;
	}
	public void setE_BOM_Temp_1(double e_BOM_Temp_1) {
		E_BOM_Temp_1 = e_BOM_Temp_1;
	}
	public double getE_BOM_Temp_2() {
		return E_BOM_Temp_2;
	}
	public void setE_BOM_Temp_2(double e_BOM_Temp_2) {
		E_BOM_Temp_2 = e_BOM_Temp_2;
	}
	public double getE_BaseMet_Wind_Speed() {
		return E_BaseMet_Wind_Speed;
	}
	public void setE_BaseMet_Wind_Speed(double e_BaseMet_Wind_Speed) {
		E_BaseMet_Wind_Speed = e_BaseMet_Wind_Speed;
	}
	public double getE_BaseMet_Wind_Direction() {
		return E_BaseMet_Wind_Direction;
	}
	public void setE_BaseMet_Wind_Direction(double e_BaseMet_Wind_Direction) {
		E_BaseMet_Wind_Direction = e_BaseMet_Wind_Direction;
	}
	public double getE_Irradiance_Global_Horizontal_1() {
		return E_Irradiance_Global_Horizontal_1;
	}
	public void setE_Irradiance_Global_Horizontal_1(double e_Irradiance_Global_Horizontal_1) {
		E_Irradiance_Global_Horizontal_1 = e_Irradiance_Global_Horizontal_1;
	}
	public double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	public double getNvm_temperature() {
		return nvm_temperature;
	}
	public void setNvm_temperature(double nvm_temperature) {
		this.nvm_temperature = nvm_temperature;
	}
	public double getNvm_panel_temperature() {
		return nvm_panel_temperature;
	}
	public void setNvm_panel_temperature(double nvm_panel_temperature) {
		this.nvm_panel_temperature = nvm_panel_temperature;
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
