/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SitesMetricsSummaryEntity {
	private int id;
	private String hashId;
	private String name;
	private String alertsJSON;
	private List<Map<String, Object>> alerts = new ArrayList<>();
	private double capacity;
	private double activePower;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHashId() {
		return hashId;
	}
	public void setHashId(String hashId) {
		this.hashId = hashId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlertsJSON() {
		return alertsJSON;
	}
	public void setAlertsJSON(String alertsJSON) {
		this.alertsJSON = alertsJSON;
	}
	public List<Map<String, Object>> getAlerts() {
		return alerts;
	}
	public void setAlerts(List<Map<String, Object>> alerts) {
		this.alerts = alerts;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public double getActivePower() {
		return activePower;
	}
	public void setActivePower(double activePower) {
		this.activePower = activePower;
	}
	
}
