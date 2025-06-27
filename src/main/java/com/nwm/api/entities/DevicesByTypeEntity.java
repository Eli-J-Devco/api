/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class DevicesByTypeEntity {
	private List<DeviceEntity> meter;
	private List<DeviceEntity> inverter;
	private List<DeviceEntity> irradiance;
	
	public DevicesByTypeEntity() {}
	
	public DevicesByTypeEntity(List<DeviceEntity> meter, List<DeviceEntity> inverter, List<DeviceEntity> irradiance) {
		super();
		this.meter = meter;
		this.inverter = inverter;
		this.irradiance = irradiance;
	}
	
	public List<DeviceEntity> getMeter() {
		return meter;
	}
	public void setMeter(List<DeviceEntity> meter) {
		this.meter = meter;
	}
	public List<DeviceEntity> getInverter() {
		return inverter;
	}
	public void setInverter(List<DeviceEntity> inverter) {
		this.inverter = inverter;
	}
	public List<DeviceEntity> getIrradiance() {
		return irradiance;
	}
	public void setIrradiance(List<DeviceEntity> irradiance) {
		this.irradiance = irradiance;
	}
}
