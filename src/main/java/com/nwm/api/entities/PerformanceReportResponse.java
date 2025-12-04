/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class PerformanceReportResponse extends DateTimeReportDataEntity {
	private Double actual;
	private Double predicted;
	private Double predictedIndex;
	private Double expected;
	private Double expectedIndex;
	private Double insolation;
	private Double predictedInsolation;
	private Double predictedInsolationIndex;
	private Double inverterAvailability;
	
	private Double actualCumulative;
	private Double predictedCumulative;
	private Double predictedCumulativeIndex;
	private Double expectedCumulative;
	private Double expectedCumulativeIndex;
	private Double insolationCumulative;
	
	public Double getActual() {
		return actual;
	}
	public void setActual(Double actual) {
		this.actual = actual;
	}
	public Double getPredicted() {
		return predicted;
	}
	public void setPredicted(Double predicted) {
		this.predicted = predicted;
	}
	public Double getPredictedIndex() {
		return predictedIndex;
	}
	public void setPredictedIndex(Double predictedIndex) {
		this.predictedIndex = predictedIndex;
	}
	public Double getExpected() {
		return expected;
	}
	public void setExpected(Double expected) {
		this.expected = expected;
	}
	public Double getExpectedIndex() {
		return expectedIndex;
	}
	public void setExpectedIndex(Double expectedIndex) {
		this.expectedIndex = expectedIndex;
	}
	public Double getInsolation() {
		return insolation;
	}
	public void setInsolation(Double insolation) {
		this.insolation = insolation;
	}
	public Double getPredictedInsolation() {
		return predictedInsolation;
	}
	public void setPredictedInsolation(Double predictedInsolation) {
		this.predictedInsolation = predictedInsolation;
	}
	public Double getPredictedInsolationIndex() {
		return predictedInsolationIndex;
	}
	public void setPredictedInsolationIndex(Double predictedInsolationIndex) {
		this.predictedInsolationIndex = predictedInsolationIndex;
	}
	public Double getInverterAvailability() {
		return inverterAvailability;
	}
	public void setInverterAvailability(Double inverterAvailability) {
		this.inverterAvailability = inverterAvailability;
	}
	
	public Double getActualCumulative() {
		return actualCumulative;
	}
	public void setActualCumulative(Double actualCumulative) {
		this.actualCumulative = actualCumulative;
	}
	public Double getPredictedCumulative() {
		return predictedCumulative;
	}
	public void setPredictedCumulative(Double predictedCumulative) {
		this.predictedCumulative = predictedCumulative;
	}
	public Double getPredictedCumulativeIndex() {
		return predictedCumulativeIndex;
	}
	public void setPredictedCumulativeIndex(Double predictedCumulativeIndex) {
		this.predictedCumulativeIndex = predictedCumulativeIndex;
	}
	public Double getExpectedCumulative() {
		return expectedCumulative;
	}
	public void setExpectedCumulative(Double expectedCumulative) {
		this.expectedCumulative = expectedCumulative;
	}
	public Double getExpectedCumulativeIndex() {
		return expectedCumulativeIndex;
	}
	public void setExpectedCumulativeIndex(Double expectedCumulativeIndex) {
		this.expectedCumulativeIndex = expectedCumulativeIndex;
	}
	public Double getInsolationCumulative() {
		return insolationCumulative;
	}
	public void setInsolationCumulative(Double insolationCumulative) {
		this.insolationCumulative = insolationCumulative;
	}
}
