/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSolarOpenWeatherEntity {
	private String time;
	private int id_device;
	private double ghi;
	private double dni;
	private double dhi;
	private double ghi_cs;
	private double dni_cs;
	private double dhi_cs;
	private double nvm_irradiance;
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
	public double getGhi() {
		return ghi;
	}
	public void setGhi(double ghi) {
		this.ghi = ghi;
	}
	public double getDni() {
		return dni;
	}
	public void setDni(double dni) {
		this.dni = dni;
	}
	public double getDhi() {
		return dhi;
	}
	public void setDhi(double dhi) {
		this.dhi = dhi;
	}
	public double getGhi_cs() {
		return ghi_cs;
	}
	public void setGhi_cs(double ghi_cs) {
		this.ghi_cs = ghi_cs;
	}
	public double getDni_cs() {
		return dni_cs;
	}
	public void setDni_cs(double dni_cs) {
		this.dni_cs = dni_cs;
	}
	public double getDhi_cs() {
		return dhi_cs;
	}
	public void setDhi_cs(double dhi_cs) {
		this.dhi_cs = dhi_cs;
	}
	public double getNvm_irradiance() {
		return nvm_irradiance;
	}
	public void setNvm_irradiance(double nvm_irradiance) {
		this.nvm_irradiance = nvm_irradiance;
	}
	
	
}
