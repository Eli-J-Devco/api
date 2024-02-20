/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPhoenixContactQuintUPSEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double ActualInputVoltage;
	private double ActualInputCurrent;
	private double ActualOutputVoltage;
	private double ActualOutputCurrent;
	private double BatteryActualVoltage;
	private double BatteryChargeCurrent;
	private double BatteryTemperature;
	private double DeviceTemperature;
	private double StateofCharge;
	private double StateofChargeRemainingTime;
	private double StateofChargeRemainingTimetoPCShutdown;
	private double StateofHealth;
	private double StateofHealthRemainingLifetime;
	private double NumberofDevices;
	private double OperationTime;
	private double BatteryModeTime;
	private double StatusAlarm;
	private double StatusWarning;
	private double Battery1StateofCharge;
	private double Battery1StateofHealth;
	private double Battery1Temperature;
	private double Battery1StateofFuse;
	private double Battery1ActualInternalVoltage;
	private double Battery1ActualBlockVoltage;
	private double Battery1InstalledCapacity;
	private double Battery1NominalResistance;
	private double Battery1MaxTemperature;
	private double Battery1MinTemperature;
	private double Battery1NominalLifetime;
	private double Battery1MaxChargeCurrent;
	private double Battery1ChargeAbsorptionVoltage;
	private double Battery1ChargeEndvoltage;
	private double Battery1ChargeTemperatureCoefficient;
	private double Battery1DischargeEndvoltage;
	private double Battery1MaxDischargeCurrent;
	private double Battery1MaxTemperatureWarning;
	private double Battery1MinTemperatureWarning;
	private double Battery1DischargeEndvoltageLowCurrent;
	
	private int totalAlarm;
	private int totalWarning;
	private int totalFuse1;
	
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
	
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
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getLow_alarm() {
		return low_alarm;
	}
	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}
	public int getHigh_alarm() {
		return high_alarm;
	}
	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
	}
	public double getActualInputVoltage() {
		return ActualInputVoltage;
	}
	public void setActualInputVoltage(double actualInputVoltage) {
		ActualInputVoltage = actualInputVoltage;
	}
	public double getActualInputCurrent() {
		return ActualInputCurrent;
	}
	public void setActualInputCurrent(double actualInputCurrent) {
		ActualInputCurrent = actualInputCurrent;
	}
	public double getActualOutputVoltage() {
		return ActualOutputVoltage;
	}
	public void setActualOutputVoltage(double actualOutputVoltage) {
		ActualOutputVoltage = actualOutputVoltage;
	}
	public double getActualOutputCurrent() {
		return ActualOutputCurrent;
	}
	public void setActualOutputCurrent(double actualOutputCurrent) {
		ActualOutputCurrent = actualOutputCurrent;
	}
	public double getBatteryActualVoltage() {
		return BatteryActualVoltage;
	}
	public void setBatteryActualVoltage(double batteryActualVoltage) {
		BatteryActualVoltage = batteryActualVoltage;
	}
	public double getBatteryChargeCurrent() {
		return BatteryChargeCurrent;
	}
	public void setBatteryChargeCurrent(double batteryChargeCurrent) {
		BatteryChargeCurrent = batteryChargeCurrent;
	}
	public double getBatteryTemperature() {
		return BatteryTemperature;
	}
	public void setBatteryTemperature(double batteryTemperature) {
		BatteryTemperature = batteryTemperature;
	}
	public double getDeviceTemperature() {
		return DeviceTemperature;
	}
	public void setDeviceTemperature(double deviceTemperature) {
		DeviceTemperature = deviceTemperature;
	}
	public double getStateofCharge() {
		return StateofCharge;
	}
	public void setStateofCharge(double stateofCharge) {
		StateofCharge = stateofCharge;
	}
	public double getStateofChargeRemainingTime() {
		return StateofChargeRemainingTime;
	}
	public void setStateofChargeRemainingTime(double stateofChargeRemainingTime) {
		StateofChargeRemainingTime = stateofChargeRemainingTime;
	}
	public double getStateofChargeRemainingTimetoPCShutdown() {
		return StateofChargeRemainingTimetoPCShutdown;
	}
	public void setStateofChargeRemainingTimetoPCShutdown(double stateofChargeRemainingTimetoPCShutdown) {
		StateofChargeRemainingTimetoPCShutdown = stateofChargeRemainingTimetoPCShutdown;
	}
	public double getStateofHealth() {
		return StateofHealth;
	}
	public void setStateofHealth(double stateofHealth) {
		StateofHealth = stateofHealth;
	}
	public double getStateofHealthRemainingLifetime() {
		return StateofHealthRemainingLifetime;
	}
	public void setStateofHealthRemainingLifetime(double stateofHealthRemainingLifetime) {
		StateofHealthRemainingLifetime = stateofHealthRemainingLifetime;
	}
	public double getNumberofDevices() {
		return NumberofDevices;
	}
	public void setNumberofDevices(double numberofDevices) {
		NumberofDevices = numberofDevices;
	}
	public double getOperationTime() {
		return OperationTime;
	}
	public void setOperationTime(double operationTime) {
		OperationTime = operationTime;
	}
	public double getBatteryModeTime() {
		return BatteryModeTime;
	}
	public void setBatteryModeTime(double batteryModeTime) {
		BatteryModeTime = batteryModeTime;
	}
	public double getStatusAlarm() {
		return StatusAlarm;
	}
	public void setStatusAlarm(double statusAlarm) {
		StatusAlarm = statusAlarm;
	}
	public double getStatusWarning() {
		return StatusWarning;
	}
	public void setStatusWarning(double statusWarning) {
		StatusWarning = statusWarning;
	}
	public double getBattery1StateofCharge() {
		return Battery1StateofCharge;
	}
	public void setBattery1StateofCharge(double battery1StateofCharge) {
		Battery1StateofCharge = battery1StateofCharge;
	}
	public double getBattery1StateofHealth() {
		return Battery1StateofHealth;
	}
	public void setBattery1StateofHealth(double battery1StateofHealth) {
		Battery1StateofHealth = battery1StateofHealth;
	}
	public double getBattery1Temperature() {
		return Battery1Temperature;
	}
	public void setBattery1Temperature(double battery1Temperature) {
		Battery1Temperature = battery1Temperature;
	}
	public double getBattery1StateofFuse() {
		return Battery1StateofFuse;
	}
	public void setBattery1StateofFuse(double battery1StateofFuse) {
		Battery1StateofFuse = battery1StateofFuse;
	}
	public double getBattery1ActualInternalVoltage() {
		return Battery1ActualInternalVoltage;
	}
	public void setBattery1ActualInternalVoltage(double battery1ActualInternalVoltage) {
		Battery1ActualInternalVoltage = battery1ActualInternalVoltage;
	}
	public double getBattery1ActualBlockVoltage() {
		return Battery1ActualBlockVoltage;
	}
	public void setBattery1ActualBlockVoltage(double battery1ActualBlockVoltage) {
		Battery1ActualBlockVoltage = battery1ActualBlockVoltage;
	}
	public double getBattery1InstalledCapacity() {
		return Battery1InstalledCapacity;
	}
	public void setBattery1InstalledCapacity(double battery1InstalledCapacity) {
		Battery1InstalledCapacity = battery1InstalledCapacity;
	}
	public double getBattery1NominalResistance() {
		return Battery1NominalResistance;
	}
	public void setBattery1NominalResistance(double battery1NominalResistance) {
		Battery1NominalResistance = battery1NominalResistance;
	}
	public double getBattery1MaxTemperature() {
		return Battery1MaxTemperature;
	}
	public void setBattery1MaxTemperature(double battery1MaxTemperature) {
		Battery1MaxTemperature = battery1MaxTemperature;
	}
	public double getBattery1MinTemperature() {
		return Battery1MinTemperature;
	}
	public void setBattery1MinTemperature(double battery1MinTemperature) {
		Battery1MinTemperature = battery1MinTemperature;
	}
	public double getBattery1NominalLifetime() {
		return Battery1NominalLifetime;
	}
	public void setBattery1NominalLifetime(double battery1NominalLifetime) {
		Battery1NominalLifetime = battery1NominalLifetime;
	}
	public double getBattery1MaxChargeCurrent() {
		return Battery1MaxChargeCurrent;
	}
	public void setBattery1MaxChargeCurrent(double battery1MaxChargeCurrent) {
		Battery1MaxChargeCurrent = battery1MaxChargeCurrent;
	}
	public double getBattery1ChargeAbsorptionVoltage() {
		return Battery1ChargeAbsorptionVoltage;
	}
	public void setBattery1ChargeAbsorptionVoltage(double battery1ChargeAbsorptionVoltage) {
		Battery1ChargeAbsorptionVoltage = battery1ChargeAbsorptionVoltage;
	}
	public double getBattery1ChargeEndvoltage() {
		return Battery1ChargeEndvoltage;
	}
	public void setBattery1ChargeEndvoltage(double battery1ChargeEndvoltage) {
		Battery1ChargeEndvoltage = battery1ChargeEndvoltage;
	}
	public double getBattery1ChargeTemperatureCoefficient() {
		return Battery1ChargeTemperatureCoefficient;
	}
	public void setBattery1ChargeTemperatureCoefficient(double battery1ChargeTemperatureCoefficient) {
		Battery1ChargeTemperatureCoefficient = battery1ChargeTemperatureCoefficient;
	}
	public double getBattery1DischargeEndvoltage() {
		return Battery1DischargeEndvoltage;
	}
	public void setBattery1DischargeEndvoltage(double battery1DischargeEndvoltage) {
		Battery1DischargeEndvoltage = battery1DischargeEndvoltage;
	}
	public double getBattery1MaxDischargeCurrent() {
		return Battery1MaxDischargeCurrent;
	}
	public void setBattery1MaxDischargeCurrent(double battery1MaxDischargeCurrent) {
		Battery1MaxDischargeCurrent = battery1MaxDischargeCurrent;
	}
	public double getBattery1MaxTemperatureWarning() {
		return Battery1MaxTemperatureWarning;
	}
	public void setBattery1MaxTemperatureWarning(double battery1MaxTemperatureWarning) {
		Battery1MaxTemperatureWarning = battery1MaxTemperatureWarning;
	}
	public double getBattery1MinTemperatureWarning() {
		return Battery1MinTemperatureWarning;
	}
	public void setBattery1MinTemperatureWarning(double battery1MinTemperatureWarning) {
		Battery1MinTemperatureWarning = battery1MinTemperatureWarning;
	}
	public double getBattery1DischargeEndvoltageLowCurrent() {
		return Battery1DischargeEndvoltageLowCurrent;
	}
	public void setBattery1DischargeEndvoltageLowCurrent(double battery1DischargeEndvoltageLowCurrent) {
		Battery1DischargeEndvoltageLowCurrent = battery1DischargeEndvoltageLowCurrent;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getJob_tablename() {
		return job_tablename;
	}
	public void setJob_tablename(String job_tablename) {
		this.job_tablename = job_tablename;
	}
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
	}
	public int getTotalAlarm() {
		return totalAlarm;
	}
	public void setTotalAlarm(int totalAlarm) {
		this.totalAlarm = totalAlarm;
	}
	public int getTotalWarning() {
		return totalWarning;
	}
	public void setTotalWarning(int totalWarning) {
		this.totalWarning = totalWarning;
	}
	public int getTotalFuse1() {
		return totalFuse1;
	}
	public void setTotalFuse1(int totalFuse1) {
		this.totalFuse1 = totalFuse1;
	}
	
}
