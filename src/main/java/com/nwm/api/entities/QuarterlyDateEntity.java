/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class QuarterlyDateEntity extends DateTimeReportDataEntity {
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
