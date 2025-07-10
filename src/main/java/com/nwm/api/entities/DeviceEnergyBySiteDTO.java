/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.Map;

public class DeviceEnergyBySiteDTO extends DateTimeReportDataEntity {
	private Double actual;
	private Double expected;
	private Double aeRatio;
	
	public static DeviceEnergyBySiteDTO convertDateTimeToEntity(Map<String, Object> map) {
		DeviceEnergyBySiteDTO entity = new DeviceEnergyBySiteDTO();
		if (map == null) return entity;
		
		entity.setTime_full((String) map.get("time_full"));
		entity.setCategories_time((String) map.get("categories_time"));
		
		return entity;
	}
	
	public Double getActual() {
		return actual;
	}
	public void setActual(Double actual) {
		this.actual = actual;
	}
	public Double getExpected() {
		return expected;
	}
	public void setExpected(Double expected) {
		this.expected = expected;
	}
	public Double getAeRatio() {
		return aeRatio;
	}
	public void setAeRatio(Double aeRatio) {
		this.aeRatio = aeRatio;
	}
}
