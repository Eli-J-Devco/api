/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPVHMasterEntity extends ModelBaseEntity {
	
	private double Year;
	private double Month;
	private double Day;
	private double Hour;
	private double Minute;
	private double Second;
	private double SunAzimuth;
	private double SunZenith;
	private double SunAngle;
	private double WindDirection;
	private double WindSpeed;
	private double WindSpeedAlarm;
	private double WindInactivityAlarm;
	private double UPSAlarm;
	private double UPSBattery;
	private double UPSCharging;
	private double AnemometerActiveNumber;
	private double AnemometerTotalNumber;
	private double AnemometerWarning;
	private double SnowState;
	private double HailState;
	private double InternalHailSensorValue;
	private double HailApplicableSensorValue;
	private double PredictiveHailAlarm;
	private double PredictiveHeartbeat;
	private double PredictiveDataValidTimeout;
	private double HailingDataSource;
	private double HailStowDirection;
	private double DiffuseEnabled;
	private double HailEnabled;
	private double SnowUnlockable;
	private double HailForceReturnCode;
	private double SnowForceReturnCode;
	private double WindForceReturnCode;
	private double Globalsuntrackingsetpoint;
	public double getYear() {
		return Year;
	}
	public void setYear(double year) {
		Year = year;
	}
	public double getMonth() {
		return Month;
	}
	public void setMonth(double month) {
		Month = month;
	}
	public double getDay() {
		return Day;
	}
	public void setDay(double day) {
		Day = day;
	}
	public double getHour() {
		return Hour;
	}
	public void setHour(double hour) {
		Hour = hour;
	}
	public double getMinute() {
		return Minute;
	}
	public void setMinute(double minute) {
		Minute = minute;
	}
	public double getSecond() {
		return Second;
	}
	public void setSecond(double second) {
		Second = second;
	}
	public double getSunAzimuth() {
		return SunAzimuth;
	}
	public void setSunAzimuth(double sunAzimuth) {
		SunAzimuth = sunAzimuth;
	}
	public double getSunZenith() {
		return SunZenith;
	}
	public void setSunZenith(double sunZenith) {
		SunZenith = sunZenith;
	}
	public double getSunAngle() {
		return SunAngle;
	}
	public void setSunAngle(double sunAngle) {
		SunAngle = sunAngle;
	}
	public double getWindDirection() {
		return WindDirection;
	}
	public void setWindDirection(double windDirection) {
		WindDirection = windDirection;
	}
	public double getWindSpeed() {
		return WindSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		WindSpeed = windSpeed;
	}
	public double getWindSpeedAlarm() {
		return WindSpeedAlarm;
	}
	public void setWindSpeedAlarm(double windSpeedAlarm) {
		WindSpeedAlarm = windSpeedAlarm;
	}
	public double getWindInactivityAlarm() {
		return WindInactivityAlarm;
	}
	public void setWindInactivityAlarm(double windInactivityAlarm) {
		WindInactivityAlarm = windInactivityAlarm;
	}
	public double getUPSAlarm() {
		return UPSAlarm;
	}
	public void setUPSAlarm(double uPSAlarm) {
		UPSAlarm = uPSAlarm;
	}
	public double getUPSBattery() {
		return UPSBattery;
	}
	public void setUPSBattery(double uPSBattery) {
		UPSBattery = uPSBattery;
	}
	public double getUPSCharging() {
		return UPSCharging;
	}
	public void setUPSCharging(double uPSCharging) {
		UPSCharging = uPSCharging;
	}
	public double getAnemometerActiveNumber() {
		return AnemometerActiveNumber;
	}
	public void setAnemometerActiveNumber(double anemometerActiveNumber) {
		AnemometerActiveNumber = anemometerActiveNumber;
	}
	public double getAnemometerTotalNumber() {
		return AnemometerTotalNumber;
	}
	public void setAnemometerTotalNumber(double anemometerTotalNumber) {
		AnemometerTotalNumber = anemometerTotalNumber;
	}
	public double getAnemometerWarning() {
		return AnemometerWarning;
	}
	public void setAnemometerWarning(double anemometerWarning) {
		AnemometerWarning = anemometerWarning;
	}
	public double getSnowState() {
		return SnowState;
	}
	public void setSnowState(double snowState) {
		SnowState = snowState;
	}
	public double getHailState() {
		return HailState;
	}
	public void setHailState(double hailState) {
		HailState = hailState;
	}
	public double getInternalHailSensorValue() {
		return InternalHailSensorValue;
	}
	public void setInternalHailSensorValue(double internalHailSensorValue) {
		InternalHailSensorValue = internalHailSensorValue;
	}
	public double getHailApplicableSensorValue() {
		return HailApplicableSensorValue;
	}
	public void setHailApplicableSensorValue(double hailApplicableSensorValue) {
		HailApplicableSensorValue = hailApplicableSensorValue;
	}
	public double getPredictiveHailAlarm() {
		return PredictiveHailAlarm;
	}
	public void setPredictiveHailAlarm(double predictiveHailAlarm) {
		PredictiveHailAlarm = predictiveHailAlarm;
	}
	public double getPredictiveHeartbeat() {
		return PredictiveHeartbeat;
	}
	public void setPredictiveHeartbeat(double predictiveHeartbeat) {
		PredictiveHeartbeat = predictiveHeartbeat;
	}
	public double getPredictiveDataValidTimeout() {
		return PredictiveDataValidTimeout;
	}
	public void setPredictiveDataValidTimeout(double predictiveDataValidTimeout) {
		PredictiveDataValidTimeout = predictiveDataValidTimeout;
	}
	public double getHailingDataSource() {
		return HailingDataSource;
	}
	public void setHailingDataSource(double hailingDataSource) {
		HailingDataSource = hailingDataSource;
	}
	public double getHailStowDirection() {
		return HailStowDirection;
	}
	public void setHailStowDirection(double hailStowDirection) {
		HailStowDirection = hailStowDirection;
	}
	public double getDiffuseEnabled() {
		return DiffuseEnabled;
	}
	public void setDiffuseEnabled(double diffuseEnabled) {
		DiffuseEnabled = diffuseEnabled;
	}
	public double getHailEnabled() {
		return HailEnabled;
	}
	public void setHailEnabled(double hailEnabled) {
		HailEnabled = hailEnabled;
	}
	public double getSnowUnlockable() {
		return SnowUnlockable;
	}
	public void setSnowUnlockable(double snowUnlockable) {
		SnowUnlockable = snowUnlockable;
	}
	public double getHailForceReturnCode() {
		return HailForceReturnCode;
	}
	public void setHailForceReturnCode(double hailForceReturnCode) {
		HailForceReturnCode = hailForceReturnCode;
	}
	public double getSnowForceReturnCode() {
		return SnowForceReturnCode;
	}
	public void setSnowForceReturnCode(double snowForceReturnCode) {
		SnowForceReturnCode = snowForceReturnCode;
	}
	public double getWindForceReturnCode() {
		return WindForceReturnCode;
	}
	public void setWindForceReturnCode(double windForceReturnCode) {
		WindForceReturnCode = windForceReturnCode;
	}
	public double getGlobalsuntrackingsetpoint() {
		return Globalsuntrackingsetpoint;
	}
	public void setGlobalsuntrackingsetpoint(double globalsuntrackingsetpoint) {
		Globalsuntrackingsetpoint = globalsuntrackingsetpoint;
	}
	
	
	
	
}
