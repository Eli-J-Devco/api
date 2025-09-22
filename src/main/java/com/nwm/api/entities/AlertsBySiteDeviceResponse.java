/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.HashMap;
import java.util.Map;

public class AlertsBySiteDeviceResponse {
	private String error_level_name;
	private String devicename;
	private String alert_icon;
	private String description;
	private String color;
	private String start;
	
	public static Map<String, Object> convertToMap(AlertsBySiteDeviceResponse obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) return map;
		
		map.put("error_level_name", obj.getError_level_name());
		map.put("devicename", obj.getDevicename());
		map.put("alert_icon", obj.getAlert_icon());
		map.put("description", obj.getDescription());
		map.put("color", obj.getColor());
		map.put("time_full", obj.getStart());
		
		return map;
	}
	
	public static AlertsBySiteDeviceResponse convertFromMap(Map<String, Object> map) {
		AlertsBySiteDeviceResponse entity = new AlertsBySiteDeviceResponse();
		if (map == null) return entity;
		
		entity.setError_level_name((String) map.get("error_level_name"));
		entity.setDevicename((String) map.get("devicename"));
		entity.setAlert_icon((String) map.get("alert_icon"));
		entity.setDescription((String) map.get("description"));
		entity.setColor((String) map.get("color"));
		entity.setStart((String) map.get("time_full"));
		
		return entity;
	}
	
	public String getError_level_name() {
		return error_level_name;
	}
	public void setError_level_name(String error_level_name) {
		this.error_level_name = error_level_name;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getAlert_icon() {
		return alert_icon;
	}
	public void setAlert_icon(String alert_icon) {
		this.alert_icon = alert_icon;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
}
