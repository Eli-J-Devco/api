/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class AccumulatedEnergyByMonthEntity {
	private Integer deviceTypeId;
	private Double currentBOM;
	private Double currentEOM;
	private Double lastBOM;
	private Double lastEOM;
	
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public Double getCurrentBOM() {
		return currentBOM;
	}
	public void setCurrentBOM(Double currentBOM) {
		this.currentBOM = currentBOM;
	}
	public Double getCurrentEOM() {
		return currentEOM;
	}
	public void setCurrentEOM(Double currentEOM) {
		this.currentEOM = currentEOM;
	}
	public Double getLastBOM() {
		return lastBOM;
	}
	public void setLastBOM(Double lastBOM) {
		this.lastBOM = lastBOM;
	}
	public Double getLastEOM() {
		return lastEOM;
	}
	public void setLastEOM(Double lastEOM) {
		this.lastEOM = lastEOM;
	}
	
}
