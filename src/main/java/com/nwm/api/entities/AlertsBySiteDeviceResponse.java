/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.HashMap;
import java.util.Map;

public class AlertsBySiteDeviceResponse {
	private Integer error_level_id;
	private String error_level_name;
	private String color;
	private String alert_icon;
	private String description;
	private String start;
	private String date_from;
	private String date_to;
	
	public static Map<String, Object> convertToMap(AlertsBySiteDeviceResponse obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) return map;
		
		map.put("error_level_id", obj.getError_level_id());
		map.put("error_level_name", obj.getError_level_name());
		map.put("color", obj.getColor());
		map.put("alert_icon", obj.getAlert_icon());
		map.put("description", obj.getDescription());
		map.put("time_full", obj.getStart());
		map.put("date_from", obj.getDate_from());
		map.put("date_to", obj.getDate_to());
		
		return map;
	}
	
	public static AlertsBySiteDeviceResponse convertFromMap(Map<String, Object> map) {
		AlertsBySiteDeviceResponse entity = new AlertsBySiteDeviceResponse();
		if (map == null) return entity;
		
		entity.setError_level_id((Integer) map.get("error_level_id"));
		entity.setError_level_name((String) map.get("error_level_name"));
		entity.setColor((String) map.get("color"));
		entity.setAlert_icon((String) map.get("alert_icon"));
		entity.setDescription((String) map.get("description"));
		entity.setStart((String) map.get("time_full"));
		entity.setDate_from((String) map.get("date_from"));
		entity.setDate_to((String) map.get("date_to"));
		
		return entity;
	}
	
	public Integer getError_level_id() {
		return error_level_id;
	}
	public void setError_level_id(Integer error_level_id) {
		this.error_level_id = error_level_id;
	}
	public String getError_level_name() {
		return error_level_name;
	}
	public void setError_level_name(String error_level_name) {
		this.error_level_name = error_level_name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getDate_from() {
		return date_from;
	}
	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}
	public String getDate_to() {
		return date_to;
	}
	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}
	
}
