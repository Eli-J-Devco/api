/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSth01TempSensorEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double TEMPRATURE;
	private double HUMIDITY;
	private double DEWPOINT;
	private double Retain;
	private double Modbus_ADDRESS;
	private double BAUDRATE;
	private double PROTOCOL;
	private double PARITY;
	private double DATABITS;
	private double STOPBITS;
	private double RESPONSEDELY;
	private double ACTIVE_OUTPUT_INTERVAL;
	
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
	public double getTEMPRATURE() {
		return TEMPRATURE;
	}
	public void setTEMPRATURE(double tEMPRATURE) {
		TEMPRATURE = tEMPRATURE;
	}
	public double getHUMIDITY() {
		return HUMIDITY;
	}
	public void setHUMIDITY(double hUMIDITY) {
		HUMIDITY = hUMIDITY;
	}
	public double getDEWPOINT() {
		return DEWPOINT;
	}
	public void setDEWPOINT(double dEWPOINT) {
		DEWPOINT = dEWPOINT;
	}
	public double getRetain() {
		return Retain;
	}
	public void setRetain(double retain) {
		Retain = retain;
	}
	public double getModbus_ADDRESS() {
		return Modbus_ADDRESS;
	}
	public void setModbus_ADDRESS(double modbus_ADDRESS) {
		Modbus_ADDRESS = modbus_ADDRESS;
	}
	public double getBAUDRATE() {
		return BAUDRATE;
	}
	public void setBAUDRATE(double bAUDRATE) {
		BAUDRATE = bAUDRATE;
	}
	public double getPROTOCOL() {
		return PROTOCOL;
	}
	public void setPROTOCOL(double pROTOCOL) {
		PROTOCOL = pROTOCOL;
	}
	public double getPARITY() {
		return PARITY;
	}
	public void setPARITY(double pARITY) {
		PARITY = pARITY;
	}
	public double getDATABITS() {
		return DATABITS;
	}
	public void setDATABITS(double dATABITS) {
		DATABITS = dATABITS;
	}
	public double getSTOPBITS() {
		return STOPBITS;
	}
	public void setSTOPBITS(double sTOPBITS) {
		STOPBITS = sTOPBITS;
	}
	public double getRESPONSEDELY() {
		return RESPONSEDELY;
	}
	public void setRESPONSEDELY(double rESPONSEDELY) {
		RESPONSEDELY = rESPONSEDELY;
	}
	public double getACTIVE_OUTPUT_INTERVAL() {
		return ACTIVE_OUTPUT_INTERVAL;
	}
	public void setACTIVE_OUTPUT_INTERVAL(double aCTIVE_OUTPUT_INTERVAL) {
		ACTIVE_OUTPUT_INTERVAL = aCTIVE_OUTPUT_INTERVAL;
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
