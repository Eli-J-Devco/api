/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class SitesMetricsSummaryEntity {
	private int sites;
	private int noProdAlerts;
	private int noCommAlerts;
	private int otherAlerts;
	private int totalInAlertsSites;
	private int criticalIssuesSites;
	private int noProdSites;
	private int noCommSites;
	private int otherAlertsSites;
	private double capacity;
	private double activePower;
	
	public int getSites() {
		return sites;
	}
	public void setSites(int sites) {
		this.sites = sites;
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
	public int getTotalInAlertsSites() {
		return totalInAlertsSites;
	}
	public void setTotalInAlertsSites(int totalInAlertsSites) {
		this.totalInAlertsSites = totalInAlertsSites;
	}
	public int getCriticalIssuesSites() {
		return criticalIssuesSites;
	}
	public void setCriticalIssuesSites(int criticalIssuesSites) {
		this.criticalIssuesSites = criticalIssuesSites;
	}
	public int getNoProdSites() {
		return noProdSites;
	}
	public void setNoProdSites(int noProdSites) {
		this.noProdSites = noProdSites;
	}
	public int getNoCommSites() {
		return noCommSites;
	}
	public void setNoCommSites(int noCommSites) {
		this.noCommSites = noCommSites;
	}
	public int getOtherAlertsSites() {
		return otherAlertsSites;
	}
	public void setOtherAlertsSites(int otherAlertsSites) {
		this.otherAlertsSites = otherAlertsSites;
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
