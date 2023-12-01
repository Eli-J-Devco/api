/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class SiteDataReportEntity {
	private String time;
	private int id_device;
	private Double InverterUptime;
	private Double DayTime;
	private Double InverterUptimeDaytime;
	private Double ActualGeneration;
	private Double EstimatedGeneration;
	private Double EstimatedGenerationIndex;
	private Double InverterAvailability;
	private Double PowerTodayTotal;
	private Double PowerTodayAVG;
	private Double POATotal;
	private Double POAAVG;
	private Double TCellAVG;
	private String table_data_report;
	
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
	public Double getInverterUptime() {
		return InverterUptime;
	}
	public void setInverterUptime(Double inverterUptime) {
		InverterUptime = inverterUptime;
	}
	public Double getDayTime() {
		return DayTime;
	}
	public void setDayTime(Double dayTime) {
		DayTime = dayTime;
	}
	public Double getInverterUptimeDaytime() {
		return InverterUptimeDaytime;
	}
	public void setInverterUptimeDaytime(Double inverterUptimeDaytime) {
		InverterUptimeDaytime = inverterUptimeDaytime;
	}
	public Double getActualGeneration() {
		return ActualGeneration;
	}
	public void setActualGeneration(Double actualGeneration) {
		ActualGeneration = actualGeneration;
	}
	public Double getEstimatedGeneration() {
		return EstimatedGeneration;
	}
	public void setEstimatedGeneration(Double estimatedGeneration) {
		EstimatedGeneration = estimatedGeneration;
	}
	public Double getEstimatedGenerationIndex() {
		return EstimatedGenerationIndex;
	}
	public void setEstimatedGenerationIndex(Double estimatedGenerationIndex) {
		EstimatedGenerationIndex = estimatedGenerationIndex;
	}
	public Double getInverterAvailability() {
		return InverterAvailability;
	}
	public void setInverterAvailability(Double inverterAvailability) {
		InverterAvailability = inverterAvailability;
	}
	public Double getPowerTodayTotal() {
		return PowerTodayTotal;
	}
	public void setPowerTodayTotal(Double powerTodayTotal) {
		PowerTodayTotal = powerTodayTotal;
	}
	public Double getPowerTodayAVG() {
		return PowerTodayAVG;
	}
	public void setPowerTodayAVG(Double powerTodayAVG) {
		PowerTodayAVG = powerTodayAVG;
	}
	public Double getPOATotal() {
		return POATotal;
	}
	public void setPOATotal(Double pOATotal) {
		POATotal = pOATotal;
	}
	public Double getPOAAVG() {
		return POAAVG;
	}
	public void setPOAAVG(Double pOAAVG) {
		POAAVG = pOAAVG;
	}
	public Double getTCellAVG() {
		return TCellAVG;
	}
	public void setTCellAVG(Double tCellAVG) {
		TCellAVG = tCellAVG;
	}
	public String getTable_data_report() {
		return table_data_report;
	}
	public void setTable_data_report(String table_data_report) {
		this.table_data_report = table_data_report;
	}
	
}
