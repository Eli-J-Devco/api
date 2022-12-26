/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class SiteDataReportEntity {
	private String time;
	private int id_device;
	private int InverterUptime;
	private int DayTime;
	private double InverterUptimeDaytime;
	private double ActualGeneration;
	private double EstimatedGeneration;
	private double EstimatedGenerationIndex;
	private double InverterAvailability;
	private double PowerTodayTotal;
	private double PowerTodayAVG;
	private double POATotal;
	private double POAAVG;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getInverterUptime() {
		return InverterUptime;
	}
	public void setInverterUptime(int inverterUptime) {
		InverterUptime = inverterUptime;
	}
	public int getDayTime() {
		return DayTime;
	}
	public void setDayTime(int dayTime) {
		DayTime = dayTime;
	}
	public double getInverterUptimeDaytime() {
		return InverterUptimeDaytime;
	}
	public void setInverterUptimeDaytime(double inverterUptimeDaytime) {
		InverterUptimeDaytime = inverterUptimeDaytime;
	}
	public double getActualGeneration() {
		return ActualGeneration;
	}
	public void setActualGeneration(double actualGeneration) {
		ActualGeneration = actualGeneration;
	}
	public double getEstimatedGeneration() {
		return EstimatedGeneration;
	}
	public void setEstimatedGeneration(double estimatedGeneration) {
		EstimatedGeneration = estimatedGeneration;
	}
	public double getEstimatedGenerationIndex() {
		return EstimatedGenerationIndex;
	}
	public void setEstimatedGenerationIndex(double estimatedGenerationIndex) {
		EstimatedGenerationIndex = estimatedGenerationIndex;
	}
	public double getInverterAvailability() {
		return InverterAvailability;
	}
	public void setInverterAvailability(double inverterAvailability) {
		InverterAvailability = inverterAvailability;
	}
	public double getPowerTodayTotal() {
		return PowerTodayTotal;
	}
	public void setPowerTodayTotal(double powerTodayTotal) {
		PowerTodayTotal = powerTodayTotal;
	}
	public double getPowerTodayAVG() {
		return PowerTodayAVG;
	}
	public void setPowerTodayAVG(double powerTodayAVG) {
		PowerTodayAVG = powerTodayAVG;
	}
	public double getPOATotal() {
		return POATotal;
	}
	public void setPOATotal(double pOATotal) {
		POATotal = pOATotal;
	}
	public double getPOAAVG() {
		return POAAVG;
	}
	public void setPOAAVG(double pOAAVG) {
		POAAVG = pOAAVG;
	}
	
	
	
	
}
