/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelSth01TempSensorEntity extends ModelBaseEntity {
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
}
