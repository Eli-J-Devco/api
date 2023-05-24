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
	
	private String ipaddr;
	private String dns1;
	private String dns2;
	private String gateway;
	private String netmask;
	private String network;
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
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public String getDns1() {
		return dns1;
	}
	public void setDns1(String dns1) {
		this.dns1 = dns1;
	}
	public String getDns2() {
		return dns2;
	}
	public void setDns2(String dns2) {
		this.dns2 = dns2;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getNetmask() {
		return netmask;
	}
	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	
	
	
	
	
}
