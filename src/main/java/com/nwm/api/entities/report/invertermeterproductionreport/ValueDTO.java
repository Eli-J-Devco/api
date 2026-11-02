package com.nwm.api.entities.report.invertermeterproductionreport;

public class ValueDTO {
	private String timestamp;
	private Double productionMeterActivePower;
	private Double inverterActivePower;
	private Double productionMeterNetEnergy;
	private Double inverterNetEnergy;
	private Double poa;
	private Double expectedPower;
	private Double expectedEnergy;
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Double getProductionMeterActivePower() {
		return productionMeterActivePower;
	}
	public void setProductionMeterActivePower(Double productionMeterActivePower) {
		this.productionMeterActivePower = productionMeterActivePower;
	}
	public Double getInverterActivePower() {
		return inverterActivePower;
	}
	public void setInverterActivePower(Double inverterActivePower) {
		this.inverterActivePower = inverterActivePower;
	}
	public Double getProductionMeterNetEnergy() {
		return productionMeterNetEnergy;
	}
	public void setProductionMeterNetEnergy(Double productionMeterNetEnergy) {
		this.productionMeterNetEnergy = productionMeterNetEnergy;
	}
	public Double getInverterNetEnergy() {
		return inverterNetEnergy;
	}
	public void setInverterNetEnergy(Double inverterNetEnergy) {
		this.inverterNetEnergy = inverterNetEnergy;
	}
	public Double getPoa() {
		return poa;
	}
	public void setPoa(Double poa) {
		this.poa = poa;
	}
	public Double getExpectedPower() {
		return expectedPower;
	}
	public void setExpectedPower(Double expectedPower) {
		this.expectedPower = expectedPower;
	}
	public Double getExpectedEnergy() {
		return expectedEnergy;
	}
	public void setExpectedEnergy(Double expectedEnergy) {
		this.expectedEnergy = expectedEnergy;
	}
}
