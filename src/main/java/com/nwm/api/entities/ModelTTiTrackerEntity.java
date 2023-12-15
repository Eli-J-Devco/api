/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelTTiTrackerEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double Mode;
	private double SubMode;
	private double MotorStatus;
	private double ReadAngle;
	private double SetAngle;
	private double OptimalAngle;
	private double WindSpeed;
	private double TTiTime;
	private double MotorFault;
	private double RemoteInterfaceFault;
	private double InclinometerFault;
	private double ModbusAddress;
	private double FirmwareVersion;
	private double Units;
	private double InclinometerOffset;
	private double MotorStopDelay;
	private double CoastAngle;
	private double MaxRotationWest;
	private double MaxRotationEast;
	private double SoftAngleLimitsEnabled;
	private double MotorMonitorSampleTime;
	private double MotorMonitorMinAngle;
	private double EnableMotorMonitor;
	private double DeadBand;
	private double NightTimeStowAltitude;
	private double NightTimeStowAngle;
	private double PoleSpacing;
	private double ModuleWidth;
	private double MotorPolarity;
	private double InclinometerPolarity;
	private double Latitude;
	private double Longitude;
	private double LoggingInterval;
	private double HelicalVarationAngle;
	private double DriveArmSlope;
	private double WindConstant;
	private double WindStowSpeed;
	private double WindStowTime;
	private int totalMode;
	private int totalSubMode;
	private int totalMotorFault;
	private int totalRemoteInterfaceFault;
	private int totalInclinometerFault;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
private int enable_alert;
	
	
	
	public int getEnable_alert() {
		return enable_alert;
	}
	public void setEnable_alert(int enable_alert) {
		this.enable_alert = enable_alert;
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
	
	
	
	public int getTotalMode() {
		return totalMode;
	}
	public void setTotalMode(int totalMode) {
		this.totalMode = totalMode;
	}
	public int getTotalSubMode() {
		return totalSubMode;
	}
	public void setTotalSubMode(int totalSubMode) {
		this.totalSubMode = totalSubMode;
	}
	public int getTotalMotorFault() {
		return totalMotorFault;
	}
	public void setTotalMotorFault(int totalMotorFault) {
		this.totalMotorFault = totalMotorFault;
	}
	public int getTotalRemoteInterfaceFault() {
		return totalRemoteInterfaceFault;
	}
	public void setTotalRemoteInterfaceFault(int totalRemoteInterfaceFault) {
		this.totalRemoteInterfaceFault = totalRemoteInterfaceFault;
	}
	public int getTotalInclinometerFault() {
		return totalInclinometerFault;
	}
	public void setTotalInclinometerFault(int totalInclinometerFault) {
		this.totalInclinometerFault = totalInclinometerFault;
	}
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
	public double getMode() {
		return Mode;
	}
	public void setMode(double mode) {
		Mode = mode;
	}
	public double getSubMode() {
		return SubMode;
	}
	public void setSubMode(double subMode) {
		SubMode = subMode;
	}
	public double getMotorStatus() {
		return MotorStatus;
	}
	public void setMotorStatus(double motorStatus) {
		MotorStatus = motorStatus;
	}
	public double getReadAngle() {
		return ReadAngle;
	}
	public void setReadAngle(double readAngle) {
		ReadAngle = readAngle;
	}
	public double getSetAngle() {
		return SetAngle;
	}
	public void setSetAngle(double setAngle) {
		SetAngle = setAngle;
	}
	public double getOptimalAngle() {
		return OptimalAngle;
	}
	public void setOptimalAngle(double optimalAngle) {
		OptimalAngle = optimalAngle;
	}
	public double getWindSpeed() {
		return WindSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		WindSpeed = windSpeed;
	}
	public double getTTiTime() {
		return TTiTime;
	}
	public void setTTiTime(double tTiTime) {
		TTiTime = tTiTime;
	}
	public double getMotorFault() {
		return MotorFault;
	}
	public void setMotorFault(double motorFault) {
		MotorFault = motorFault;
	}
	public double getRemoteInterfaceFault() {
		return RemoteInterfaceFault;
	}
	public void setRemoteInterfaceFault(double remoteInterfaceFault) {
		RemoteInterfaceFault = remoteInterfaceFault;
	}
	public double getInclinometerFault() {
		return InclinometerFault;
	}
	public void setInclinometerFault(double inclinometerFault) {
		InclinometerFault = inclinometerFault;
	}
	public double getModbusAddress() {
		return ModbusAddress;
	}
	public void setModbusAddress(double modbusAddress) {
		ModbusAddress = modbusAddress;
	}
	public double getFirmwareVersion() {
		return FirmwareVersion;
	}
	public void setFirmwareVersion(double firmwareVersion) {
		FirmwareVersion = firmwareVersion;
	}
	public double getUnits() {
		return Units;
	}
	public void setUnits(double units) {
		Units = units;
	}
	public double getInclinometerOffset() {
		return InclinometerOffset;
	}
	public void setInclinometerOffset(double inclinometerOffset) {
		InclinometerOffset = inclinometerOffset;
	}
	public double getMotorStopDelay() {
		return MotorStopDelay;
	}
	public void setMotorStopDelay(double motorStopDelay) {
		MotorStopDelay = motorStopDelay;
	}
	public double getCoastAngle() {
		return CoastAngle;
	}
	public void setCoastAngle(double coastAngle) {
		CoastAngle = coastAngle;
	}
	public double getMaxRotationWest() {
		return MaxRotationWest;
	}
	public void setMaxRotationWest(double maxRotationWest) {
		MaxRotationWest = maxRotationWest;
	}
	public double getMaxRotationEast() {
		return MaxRotationEast;
	}
	public void setMaxRotationEast(double maxRotationEast) {
		MaxRotationEast = maxRotationEast;
	}
	public double getSoftAngleLimitsEnabled() {
		return SoftAngleLimitsEnabled;
	}
	public void setSoftAngleLimitsEnabled(double softAngleLimitsEnabled) {
		SoftAngleLimitsEnabled = softAngleLimitsEnabled;
	}
	public double getMotorMonitorSampleTime() {
		return MotorMonitorSampleTime;
	}
	public void setMotorMonitorSampleTime(double motorMonitorSampleTime) {
		MotorMonitorSampleTime = motorMonitorSampleTime;
	}
	public double getMotorMonitorMinAngle() {
		return MotorMonitorMinAngle;
	}
	public void setMotorMonitorMinAngle(double motorMonitorMinAngle) {
		MotorMonitorMinAngle = motorMonitorMinAngle;
	}
	public double getEnableMotorMonitor() {
		return EnableMotorMonitor;
	}
	public void setEnableMotorMonitor(double enableMotorMonitor) {
		EnableMotorMonitor = enableMotorMonitor;
	}
	public double getDeadBand() {
		return DeadBand;
	}
	public void setDeadBand(double deadBand) {
		DeadBand = deadBand;
	}
	public double getNightTimeStowAltitude() {
		return NightTimeStowAltitude;
	}
	public void setNightTimeStowAltitude(double nightTimeStowAltitude) {
		NightTimeStowAltitude = nightTimeStowAltitude;
	}
	public double getNightTimeStowAngle() {
		return NightTimeStowAngle;
	}
	public void setNightTimeStowAngle(double nightTimeStowAngle) {
		NightTimeStowAngle = nightTimeStowAngle;
	}
	public double getPoleSpacing() {
		return PoleSpacing;
	}
	public void setPoleSpacing(double poleSpacing) {
		PoleSpacing = poleSpacing;
	}
	public double getModuleWidth() {
		return ModuleWidth;
	}
	public void setModuleWidth(double moduleWidth) {
		ModuleWidth = moduleWidth;
	}
	public double getMotorPolarity() {
		return MotorPolarity;
	}
	public void setMotorPolarity(double motorPolarity) {
		MotorPolarity = motorPolarity;
	}
	public double getInclinometerPolarity() {
		return InclinometerPolarity;
	}
	public void setInclinometerPolarity(double inclinometerPolarity) {
		InclinometerPolarity = inclinometerPolarity;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public double getLoggingInterval() {
		return LoggingInterval;
	}
	public void setLoggingInterval(double loggingInterval) {
		LoggingInterval = loggingInterval;
	}
	public double getHelicalVarationAngle() {
		return HelicalVarationAngle;
	}
	public void setHelicalVarationAngle(double helicalVarationAngle) {
		HelicalVarationAngle = helicalVarationAngle;
	}
	public double getDriveArmSlope() {
		return DriveArmSlope;
	}
	public void setDriveArmSlope(double driveArmSlope) {
		DriveArmSlope = driveArmSlope;
	}
	public double getWindConstant() {
		return WindConstant;
	}
	public void setWindConstant(double windConstant) {
		WindConstant = windConstant;
	}
	public double getWindStowSpeed() {
		return WindStowSpeed;
	}
	public void setWindStowSpeed(double windStowSpeed) {
		WindStowSpeed = windStowSpeed;
	}
	public double getWindStowTime() {
		return WindStowTime;
	}
	public void setWindStowTime(double windStowTime) {
		WindStowTime = windStowTime;
	}
	
	
	
	
	
}
