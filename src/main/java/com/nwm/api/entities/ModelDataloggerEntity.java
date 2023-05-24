/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelDataloggerEntity {
	private String time;
	private int id_device;
	private String serialnumber;
	private String loopname;
	private String modbusip;
	private String modbusport;
	private String modbusdevice;
	private String modbusdevicename;
	private String modbusdevicetype;
	private String modbusdevicetypenumber;
	private String modbusdeviceclass;
	private double MemTotal;
	private double MemFree;
	
	
	
	public double getMemTotal() {
		return MemTotal;
	}
	public void setMemTotal(double memTotal) {
		MemTotal = memTotal;
	}
	public double getMemFree() {
		return MemFree;
	}
	public void setMemFree(double memFree) {
		MemFree = memFree;
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
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public String getLoopname() {
		return loopname;
	}
	public void setLoopname(String loopname) {
		this.loopname = loopname;
	}
	public String getModbusip() {
		return modbusip;
	}
	public void setModbusip(String modbusip) {
		this.modbusip = modbusip;
	}
	public String getModbusport() {
		return modbusport;
	}
	public void setModbusport(String modbusport) {
		this.modbusport = modbusport;
	}
	public String getModbusdevice() {
		return modbusdevice;
	}
	public void setModbusdevice(String modbusdevice) {
		this.modbusdevice = modbusdevice;
	}
	public String getModbusdevicename() {
		return modbusdevicename;
	}
	public void setModbusdevicename(String modbusdevicename) {
		this.modbusdevicename = modbusdevicename;
	}
	public String getModbusdevicetype() {
		return modbusdevicetype;
	}
	public void setModbusdevicetype(String modbusdevicetype) {
		this.modbusdevicetype = modbusdevicetype;
	}
	public String getModbusdevicetypenumber() {
		return modbusdevicetypenumber;
	}
	public void setModbusdevicetypenumber(String modbusdevicetypenumber) {
		this.modbusdevicetypenumber = modbusdevicetypenumber;
	}
	public String getModbusdeviceclass() {
		return modbusdeviceclass;
	}
	public void setModbusdeviceclass(String modbusdeviceclass) {
		this.modbusdeviceclass = modbusdeviceclass;
	}
	
	
}
