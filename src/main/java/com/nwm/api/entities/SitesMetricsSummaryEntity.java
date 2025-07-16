/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class SitesMetricsSummaryEntity {
	private int id;
	private int fatalAlerts;
	private int warningAlerts;
	private int noProdAlerts;
	private int noCommAlerts;
	private int otherAlerts;
	private double capacity;
	private double activePower;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFatalAlerts() {
		return fatalAlerts;
	}
	public void setFatalAlerts(int fatalAlerts) {
		this.fatalAlerts = fatalAlerts;
	}
	public int getWarningAlerts() {
		return warningAlerts;
	}
	public void setWarningAlerts(int warningAlerts) {
		this.warningAlerts = warningAlerts;
	}
	public int getNoProdAlerts() {
		return noProdAlerts;
	}
	public void setNoProdAlerts(int noProdAlerts) {
		this.noProdAlerts = noProdAlerts;
	}
	public int getNoCommAlerts() {
		return noCommAlerts;
	}
	public void setNoCommAlerts(int noCommAlerts) {
		this.noCommAlerts = noCommAlerts;
	}
	public int getOtherAlerts() {
		return otherAlerts;
	}
	public void setOtherAlerts(int otherAlerts) {
		this.otherAlerts = otherAlerts;
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
