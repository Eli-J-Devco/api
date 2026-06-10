/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.ArrayList;
import java.util.List;

public class SanityCheckReportEntity {
	private String siteName;
	private List<Double> recDifference1 = new ArrayList<Double>();
	private List<Double> recDifference2 = new ArrayList<Double>();
	private Double productionDifference1;
	private Double productionDifference2;
	private Double irradianceDifference1;
	private Double irradianceDifference2;
	private Integer alert;
	private List<Double> accumulatedEnergyBOMByMeter = new ArrayList<Double>();
	private List<Double> accumulatedEnergyEOMByMeter = new ArrayList<Double>();
	private List<Double> accumulatedEnergyDifferenceByMeter = new ArrayList<Double>();
	private List<Double> accumulatedEnergyBOMByInverter = new ArrayList<Double>();
	private List<Double> accumulatedEnergyEOMByInverter = new ArrayList<Double>();
	private List<Double> accumulatedEnergyDifferenceByInverter = new ArrayList<Double>();
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public List<Double> getRecDifference1() {
		return recDifference1;
	}
	public void addRecDifference1(Double recDifference1) {
		this.recDifference1.add(recDifference1);
	}
	public List<Double> getRecDifference2() {
		return recDifference2;
	}
	public void addRecDifference2(Double recDifference2) {
		this.recDifference2.add(recDifference2);
	}
	public Double getProductionDifference1() {
		return productionDifference1;
	}
	public void setProductionDifference1(Double productionDifference1) {
		this.productionDifference1 = productionDifference1;
	}
	public Double getProductionDifference2() {
		return productionDifference2;
	}
	public void setProductionDifference2(Double productionDifference2) {
		this.productionDifference2 = productionDifference2;
	}
	public Double getIrradianceDifference1() {
		return irradianceDifference1;
	}
	public void setIrradianceDifference1(Double irradianceDifference1) {
		this.irradianceDifference1 = irradianceDifference1;
	}
	public Double getIrradianceDifference2() {
		return irradianceDifference2;
	}
	public void setIrradianceDifference2(Double irradianceDifference2) {
		this.irradianceDifference2 = irradianceDifference2;
	}
	public Integer getAlert() {
		return alert;
	}
	public void setAlert(Integer alert) {
		this.alert = alert;
	}
	public List<Double> getAccumulatedEnergyBOMByMeter() {
		return accumulatedEnergyBOMByMeter;
	}
	public void addAccumulatedEnergyBOMByMeter(Double accumulatedEnergyBOMByMeter) {
		this.accumulatedEnergyBOMByMeter.add(accumulatedEnergyBOMByMeter);
	}
	public List<Double> getAccumulatedEnergyEOMByMeter() {
		return accumulatedEnergyEOMByMeter;
	}
	public void addAccumulatedEnergyEOMByMeter(Double accumulatedEnergyEOMByMeter) {
		this.accumulatedEnergyEOMByMeter.add(accumulatedEnergyEOMByMeter);
	}
	public List<Double> getAccumulatedEnergyDifferenceByMeter() {
		return accumulatedEnergyDifferenceByMeter;
	}
	public void addAccumulatedEnergyDifferenceByMeter(Double accumulatedEnergyDifferenceByMeter) {
		this.accumulatedEnergyDifferenceByMeter.add(accumulatedEnergyDifferenceByMeter);
	}
	public List<Double> getAccumulatedEnergyBOMByInverter() {
		return accumulatedEnergyBOMByInverter;
	}
	public void addAccumulatedEnergyBOMByInverter(Double accumulatedEnergyBOMByInverter) {
		this.accumulatedEnergyBOMByInverter.add(accumulatedEnergyBOMByInverter);
	}
	public List<Double> getAccumulatedEnergyEOMByInverter() {
		return accumulatedEnergyEOMByInverter;
	}
	public void addAccumulatedEnergyEOMByInverter(Double accumulatedEnergyEOMByInverter) {
		this.accumulatedEnergyEOMByInverter.add(accumulatedEnergyEOMByInverter);
	}
	public List<Double> getAccumulatedEnergyDifferenceByInverter() {
		return accumulatedEnergyDifferenceByInverter;
	}
	public void addAccumulatedEnergyDifferenceByInverter(Double accumulatedEnergyDifferenceByInverter) {
		this.accumulatedEnergyDifferenceByInverter.add(accumulatedEnergyDifferenceByInverter);
	}
	
}
