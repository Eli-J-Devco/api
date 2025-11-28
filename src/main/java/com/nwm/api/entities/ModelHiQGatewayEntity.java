/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelHiQGatewayEntity extends ModelBaseEntity {
	private double ModbusMapVersion;
	private double InverterCount;
	private double SiteStatus;
	private double TotalActivePower;
	private double TotalEnergy;
	private double GatewayStatus;
	private double GatewayUTCTime;
	private double GatewayActiveFaults;
	private double GatewayTemperature;
	private double SerialNumberASCII_1_4;
	private double SerialNumberASCII_5_8;
	private double FirmwareVersionASCII;
	
	public double getModbusMapVersion() {
		return ModbusMapVersion;
	}
	public void setModbusMapVersion(double modbusMapVersion) {
		ModbusMapVersion = modbusMapVersion;
	}
	public double getInverterCount() {
		return InverterCount;
	}
	public void setInverterCount(double inverterCount) {
		InverterCount = inverterCount;
	}
	public double getSiteStatus() {
		return SiteStatus;
	}
	public void setSiteStatus(double siteStatus) {
		SiteStatus = siteStatus;
	}
	public double getTotalActivePower() {
		return TotalActivePower;
	}
	public void setTotalActivePower(double totalActivePower) {
		TotalActivePower = totalActivePower;
	}
	public double getTotalEnergy() {
		return TotalEnergy;
	}
	public void setTotalEnergy(double totalEnergy) {
		TotalEnergy = totalEnergy;
	}
	public double getGatewayStatus() {
		return GatewayStatus;
	}
	public void setGatewayStatus(double gatewayStatus) {
		GatewayStatus = gatewayStatus;
	}
	public double getGatewayUTCTime() {
		return GatewayUTCTime;
	}
	public void setGatewayUTCTime(double gatewayUTCTime) {
		GatewayUTCTime = gatewayUTCTime;
	}
	public double getGatewayActiveFaults() {
		return GatewayActiveFaults;
	}
	public void setGatewayActiveFaults(double gatewayActiveFaults) {
		GatewayActiveFaults = gatewayActiveFaults;
	}
	public double getGatewayTemperature() {
		return GatewayTemperature;
	}
	public void setGatewayTemperature(double gatewayTemperature) {
		GatewayTemperature = gatewayTemperature;
	}
	public double getSerialNumberASCII_1_4() {
		return SerialNumberASCII_1_4;
	}
	public void setSerialNumberASCII_1_4(double serialNumberASCII_1_4) {
		SerialNumberASCII_1_4 = serialNumberASCII_1_4;
	}
	public double getSerialNumberASCII_5_8() {
		return SerialNumberASCII_5_8;
	}
	public void setSerialNumberASCII_5_8(double serialNumberASCII_5_8) {
		SerialNumberASCII_5_8 = serialNumberASCII_5_8;
	}
	public double getFirmwareVersionASCII() {
		return FirmwareVersionASCII;
	}
	public void setFirmwareVersionASCII(double firmwareVersionASCII) {
		FirmwareVersionASCII = firmwareVersionASCII;
	}
}
