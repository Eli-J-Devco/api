package com.nwm.api.entities;

public class ModelAtonometricsRC22MEntity extends ModelBaseEntity {
	private double Irradiance;
	private double ShortCircuitCurrent;
	private double PVCellBodyTemperature;
	private double BOMSensor;
	private double AmbientAirTempSensor;
	private double WindSpeed;
	public double getIrradiance() {
		return Irradiance;
	}
	public void setIrradiance(double irradiance) {
		Irradiance = irradiance;
	}
	public double getShortCircuitCurrent() {
		return ShortCircuitCurrent;
	}
	public void setShortCircuitCurrent(double shortCircuitCurrent) {
		ShortCircuitCurrent = shortCircuitCurrent;
	}
	public double getPVCellBodyTemperature() {
		return PVCellBodyTemperature;
	}
	public void setPVCellBodyTemperature(double pVCellBodyTemperature) {
		PVCellBodyTemperature = pVCellBodyTemperature;
	}
	public double getBOMSensor() {
		return BOMSensor;
	}
	public void setBOMSensor(double bOMSensor) {
		BOMSensor = bOMSensor;
	}
	public double getAmbientAirTempSensor() {
		return AmbientAirTempSensor;
	}
	public void setAmbientAirTempSensor(double ambientAirTempSensor) {
		AmbientAirTempSensor = ambientAirTempSensor;
	}
	public double getWindSpeed() {
		return WindSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		WindSpeed = windSpeed;
	}
	
	
	
}
