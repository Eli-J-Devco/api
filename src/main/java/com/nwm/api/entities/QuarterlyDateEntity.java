/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class QuarterlyDateEntity{
	
	private String month;
	private String year;
	private String name;
	private String time_full;
	private String time_format;
	private String categories_time;
	private int month_number;
	private Double actual;
	private Double estimated;
	private Double difference;
	private Double differencePercentage;
	private Double inverterAvailability;
	private Double POAInsolation;
	private Double TCellAVG;
	private Double temperatureCorrected;
	private Double actualCumulative;
	private Double estimatedCumulative;
	private Double cumulativeDifference;
	private Double cumulativeDifferencePercentage;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime_full() {
		return time_full;
	}
	public void setTime_full(String time_full) {
		this.time_full = time_full;
	}
	public String getTime_format() {
		return time_format;
	}
	public void setTime_format(String time_format) {
		this.time_format = time_format;
	}
	public String getCategories_time() {
		return categories_time;
	}
	public void setCategories_time(String categories_time) {
		this.categories_time = categories_time;
	}
	public int getMonth_number() {
		return month_number;
	}
	public void setMonth_number(int month_number) {
		this.month_number = month_number;
	}
	public Double getActual() {
		return actual;
	}
	public void setActual(Double actual) {
		this.actual = actual;
	}
	public Double getEstimated() {
		return estimated;
	}
	public void setEstimated(Double estimated) {
		this.estimated = estimated;
	}
	public Double getDifference() {
		return difference;
	}
	public void setDifference(Double difference) {
		this.difference = difference;
	}
	public Double getDifferencePercentage() {
		return differencePercentage;
	}
	public void setDifferencePercentage(Double differencePercentage) {
		this.differencePercentage = differencePercentage;
	}
	public Double getInverterAvailability() {
		return inverterAvailability;
	}
	public void setInverterAvailability(Double inverterAvailability) {
		this.inverterAvailability = inverterAvailability;
	}
	public Double getPOAInsolation() {
		return POAInsolation;
	}
	public void setPOAInsolation(Double pOAInsolation) {
		POAInsolation = pOAInsolation;
	}
	public Double getTCellAVG() {
		return TCellAVG;
	}
	public void setTCellAVG(Double tCellAVG) {
		TCellAVG = tCellAVG;
	}
	public Double getTemperatureCorrected() {
		return temperatureCorrected;
	}
	public void setTemperatureCorrected(Double temperatureCorrected) {
		this.temperatureCorrected = temperatureCorrected;
	}
	public Double getActualCumulative() {
		return actualCumulative;
	}
	public void setActualCumulative(Double actualCumulative) {
		this.actualCumulative = actualCumulative;
	}
	public Double getEstimatedCumulative() {
		return estimatedCumulative;
	}
	public void setEstimatedCumulative(Double estimatedCumulative) {
		this.estimatedCumulative = estimatedCumulative;
	}
	public Double getCumulativeDifference() {
		return cumulativeDifference;
	}
	public void setCumulativeDifference(Double cumulativeDifference) {
		this.cumulativeDifference = cumulativeDifference;
	}
	public Double getCumulativeDifferencePercentage() {
		return cumulativeDifferencePercentage;
	}
	public void setCumulativeDifferencePercentage(Double cumulativeDifferencePercentage) {
		this.cumulativeDifferencePercentage = cumulativeDifferencePercentage;
	}
	
	
	
}
