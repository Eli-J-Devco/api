/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class PerformanceDataChartItemEntity {
	private List<ClientMonthlyDateEntity> data_energy;
	private String type;
	private String unit;
	private String devicename;
	private boolean isShowEachMeter = false;
	
	public PerformanceDataChartItemEntity() {}
	
	public PerformanceDataChartItemEntity(List<ClientMonthlyDateEntity> data_energy, String type, String unit, String devicename, boolean isShowEachMeter) {
		this.data_energy = data_energy;
		this.type = type;
		this.unit = unit;
		this.devicename = devicename;
		this.isShowEachMeter = isShowEachMeter;
	}
	
	public PerformanceDataChartItemEntity(List<ClientMonthlyDateEntity> data_energy, String type, String unit, String devicename) {
		this.data_energy = data_energy;
		this.type = type;
		this.unit = unit;
		this.devicename = devicename;
	}
	
	public List<ClientMonthlyDateEntity> getData_energy() {
		return data_energy;
	}
	public void setData_energy(List<ClientMonthlyDateEntity> data_energy) {
		this.data_energy = data_energy;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public boolean isIsShowEachMeter() {
		return isShowEachMeter;
	}
	public void setIsShowEachMeter(boolean isShowEachMeter) {
		this.isShowEachMeter = isShowEachMeter;
	}
	
}
