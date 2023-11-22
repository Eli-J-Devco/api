/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelPVPowered3550260500kwInverterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private String InverterIDASCIICHAR0001;
	private String InverterIDASCIICHAR0203;
	private String InverterIDASCIICHAR0405;
	private String InverterIDASCIICHAR0607;
	private String InverterIDASCIICHAR0809;
	private String InverterIDASCIICHAR1011;
	private String InverterIDASCIICHAR1213;
	private String InverterIDASCIICHAR1415;
	private String FirmwareVersionASCIICHAR0001;
	private String FirmwareVersionASCIICHAR0203;
	private String FirmwareVersionASCIICHAR0405;
	private String FirmwareVersionASCIICHAR0607;
	private String MapVersion;
	private String InverterConfiguration;
	private String InverterSerialASCIICHAR0001;
	private String InverterSerialASCIICHAR0203;
	private String InverterSerialASCIICHAR0405;
	private String InverterSerialASCIICHAR0607;
	private String InverterSerialASCIICHAR0809;
	private String InverterSerialASCIICHAR1011;
	private String InverterSerialASCIICHAR1213;
	private String InverterSerialASCIICHAR1415;
	private String InverterSerialASCIICHAR1617;
	private String InverterSerialASCIICHAR1819;
	private double VoltageAN;
	private double VoltageBN;
	private double VoltageCN;
	private double CurrentA;
	private double CurrentB;
	private double CurrentC;
	private double DCInputVoltage;
	private double DCInputCurrent;
	private double LineFrequency;
	private double OutputGeneration;
	private double TotalEnergyGeneration;
	private double PVInputVoltage;
	private double InputGenerationCalculated;
	private double InverterOperatingStatus;
	private double MainFault;
	private double DriveFault;
	private double VoltageFault;
	private double GridFault;
	private double TemperatureFault;
	private double SystemFault;
	private double SystemWarnings;
	private double PVMStatusCodes;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	
	
	private int totalInverterOperatingStatus;
	private int totalMainFault;
	private int totalDriveFault;
	private int totalVoltageFault;
	private int totalGridFault;
	private int totalTemperatureFault;
	private int totalSystemFault;
	private int totalSystemWarnings;
	private int totalPVMStatusCodes;
	
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	
	
	
	
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
	
	
	
	
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
	
	
	
	
	
	public int getTotalInverterOperatingStatus() {
		return totalInverterOperatingStatus;
	}
	public void setTotalInverterOperatingStatus(int totalInverterOperatingStatus) {
		this.totalInverterOperatingStatus = totalInverterOperatingStatus;
	}
	public int getTotalMainFault() {
		return totalMainFault;
	}
	public void setTotalMainFault(int totalMainFault) {
		this.totalMainFault = totalMainFault;
	}
	public int getTotalDriveFault() {
		return totalDriveFault;
	}
	public void setTotalDriveFault(int totalDriveFault) {
		this.totalDriveFault = totalDriveFault;
	}
	public int getTotalVoltageFault() {
		return totalVoltageFault;
	}
	public void setTotalVoltageFault(int totalVoltageFault) {
		this.totalVoltageFault = totalVoltageFault;
	}
	public int getTotalGridFault() {
		return totalGridFault;
	}
	public void setTotalGridFault(int totalGridFault) {
		this.totalGridFault = totalGridFault;
	}
	public int getTotalTemperatureFault() {
		return totalTemperatureFault;
	}
	public void setTotalTemperatureFault(int totalTemperatureFault) {
		this.totalTemperatureFault = totalTemperatureFault;
	}
	public int getTotalSystemFault() {
		return totalSystemFault;
	}
	public void setTotalSystemFault(int totalSystemFault) {
		this.totalSystemFault = totalSystemFault;
	}
	public int getTotalSystemWarnings() {
		return totalSystemWarnings;
	}
	public void setTotalSystemWarnings(int totalSystemWarnings) {
		this.totalSystemWarnings = totalSystemWarnings;
	}
	public int getTotalPVMStatusCodes() {
		return totalPVMStatusCodes;
	}
	public void setTotalPVMStatusCodes(int totalPVMStatusCodes) {
		this.totalPVMStatusCodes = totalPVMStatusCodes;
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
	public String getInverterIDASCIICHAR0001() {
		return InverterIDASCIICHAR0001;
	}
	public void setInverterIDASCIICHAR0001(String inverterIDASCIICHAR0001) {
		InverterIDASCIICHAR0001 = inverterIDASCIICHAR0001;
	}
	public String getInverterIDASCIICHAR0203() {
		return InverterIDASCIICHAR0203;
	}
	public void setInverterIDASCIICHAR0203(String inverterIDASCIICHAR0203) {
		InverterIDASCIICHAR0203 = inverterIDASCIICHAR0203;
	}
	public String getInverterIDASCIICHAR0405() {
		return InverterIDASCIICHAR0405;
	}
	public void setInverterIDASCIICHAR0405(String inverterIDASCIICHAR0405) {
		InverterIDASCIICHAR0405 = inverterIDASCIICHAR0405;
	}
	public String getInverterIDASCIICHAR0607() {
		return InverterIDASCIICHAR0607;
	}
	public void setInverterIDASCIICHAR0607(String inverterIDASCIICHAR0607) {
		InverterIDASCIICHAR0607 = inverterIDASCIICHAR0607;
	}
	public String getInverterIDASCIICHAR0809() {
		return InverterIDASCIICHAR0809;
	}
	public void setInverterIDASCIICHAR0809(String inverterIDASCIICHAR0809) {
		InverterIDASCIICHAR0809 = inverterIDASCIICHAR0809;
	}
	public String getInverterIDASCIICHAR1011() {
		return InverterIDASCIICHAR1011;
	}
	public void setInverterIDASCIICHAR1011(String inverterIDASCIICHAR1011) {
		InverterIDASCIICHAR1011 = inverterIDASCIICHAR1011;
	}
	public String getInverterIDASCIICHAR1213() {
		return InverterIDASCIICHAR1213;
	}
	public void setInverterIDASCIICHAR1213(String inverterIDASCIICHAR1213) {
		InverterIDASCIICHAR1213 = inverterIDASCIICHAR1213;
	}
	public String getInverterIDASCIICHAR1415() {
		return InverterIDASCIICHAR1415;
	}
	public void setInverterIDASCIICHAR1415(String inverterIDASCIICHAR1415) {
		InverterIDASCIICHAR1415 = inverterIDASCIICHAR1415;
	}
	public String getFirmwareVersionASCIICHAR0001() {
		return FirmwareVersionASCIICHAR0001;
	}
	public void setFirmwareVersionASCIICHAR0001(String firmwareVersionASCIICHAR0001) {
		FirmwareVersionASCIICHAR0001 = firmwareVersionASCIICHAR0001;
	}
	public String getFirmwareVersionASCIICHAR0203() {
		return FirmwareVersionASCIICHAR0203;
	}
	public void setFirmwareVersionASCIICHAR0203(String firmwareVersionASCIICHAR0203) {
		FirmwareVersionASCIICHAR0203 = firmwareVersionASCIICHAR0203;
	}
	public String getFirmwareVersionASCIICHAR0405() {
		return FirmwareVersionASCIICHAR0405;
	}
	public void setFirmwareVersionASCIICHAR0405(String firmwareVersionASCIICHAR0405) {
		FirmwareVersionASCIICHAR0405 = firmwareVersionASCIICHAR0405;
	}
	public String getFirmwareVersionASCIICHAR0607() {
		return FirmwareVersionASCIICHAR0607;
	}
	public void setFirmwareVersionASCIICHAR0607(String firmwareVersionASCIICHAR0607) {
		FirmwareVersionASCIICHAR0607 = firmwareVersionASCIICHAR0607;
	}
	public String getMapVersion() {
		return MapVersion;
	}
	public void setMapVersion(String mapVersion) {
		MapVersion = mapVersion;
	}
	public String getInverterConfiguration() {
		return InverterConfiguration;
	}
	public void setInverterConfiguration(String inverterConfiguration) {
		InverterConfiguration = inverterConfiguration;
	}
	public String getInverterSerialASCIICHAR0001() {
		return InverterSerialASCIICHAR0001;
	}
	public void setInverterSerialASCIICHAR0001(String inverterSerialASCIICHAR0001) {
		InverterSerialASCIICHAR0001 = inverterSerialASCIICHAR0001;
	}
	public String getInverterSerialASCIICHAR0203() {
		return InverterSerialASCIICHAR0203;
	}
	public void setInverterSerialASCIICHAR0203(String inverterSerialASCIICHAR0203) {
		InverterSerialASCIICHAR0203 = inverterSerialASCIICHAR0203;
	}
	public String getInverterSerialASCIICHAR0405() {
		return InverterSerialASCIICHAR0405;
	}
	public void setInverterSerialASCIICHAR0405(String inverterSerialASCIICHAR0405) {
		InverterSerialASCIICHAR0405 = inverterSerialASCIICHAR0405;
	}
	public String getInverterSerialASCIICHAR0607() {
		return InverterSerialASCIICHAR0607;
	}
	public void setInverterSerialASCIICHAR0607(String inverterSerialASCIICHAR0607) {
		InverterSerialASCIICHAR0607 = inverterSerialASCIICHAR0607;
	}
	public String getInverterSerialASCIICHAR0809() {
		return InverterSerialASCIICHAR0809;
	}
	public void setInverterSerialASCIICHAR0809(String inverterSerialASCIICHAR0809) {
		InverterSerialASCIICHAR0809 = inverterSerialASCIICHAR0809;
	}
	public String getInverterSerialASCIICHAR1011() {
		return InverterSerialASCIICHAR1011;
	}
	public void setInverterSerialASCIICHAR1011(String inverterSerialASCIICHAR1011) {
		InverterSerialASCIICHAR1011 = inverterSerialASCIICHAR1011;
	}
	public String getInverterSerialASCIICHAR1213() {
		return InverterSerialASCIICHAR1213;
	}
	public void setInverterSerialASCIICHAR1213(String inverterSerialASCIICHAR1213) {
		InverterSerialASCIICHAR1213 = inverterSerialASCIICHAR1213;
	}
	public String getInverterSerialASCIICHAR1415() {
		return InverterSerialASCIICHAR1415;
	}
	public void setInverterSerialASCIICHAR1415(String inverterSerialASCIICHAR1415) {
		InverterSerialASCIICHAR1415 = inverterSerialASCIICHAR1415;
	}
	public String getInverterSerialASCIICHAR1617() {
		return InverterSerialASCIICHAR1617;
	}
	public void setInverterSerialASCIICHAR1617(String inverterSerialASCIICHAR1617) {
		InverterSerialASCIICHAR1617 = inverterSerialASCIICHAR1617;
	}
	public String getInverterSerialASCIICHAR1819() {
		return InverterSerialASCIICHAR1819;
	}
	public void setInverterSerialASCIICHAR1819(String inverterSerialASCIICHAR1819) {
		InverterSerialASCIICHAR1819 = inverterSerialASCIICHAR1819;
	}
	public double getVoltageAN() {
		return VoltageAN;
	}
	public void setVoltageAN(double voltageAN) {
		VoltageAN = voltageAN;
	}
	public double getVoltageBN() {
		return VoltageBN;
	}
	public void setVoltageBN(double voltageBN) {
		VoltageBN = voltageBN;
	}
	public double getVoltageCN() {
		return VoltageCN;
	}
	public void setVoltageCN(double voltageCN) {
		VoltageCN = voltageCN;
	}
	public double getCurrentA() {
		return CurrentA;
	}
	public void setCurrentA(double currentA) {
		CurrentA = currentA;
	}
	public double getCurrentB() {
		return CurrentB;
	}
	public void setCurrentB(double currentB) {
		CurrentB = currentB;
	}
	public double getCurrentC() {
		return CurrentC;
	}
	public void setCurrentC(double currentC) {
		CurrentC = currentC;
	}
	public double getDCInputVoltage() {
		return DCInputVoltage;
	}
	public void setDCInputVoltage(double dCInputVoltage) {
		DCInputVoltage = dCInputVoltage;
	}
	public double getDCInputCurrent() {
		return DCInputCurrent;
	}
	public void setDCInputCurrent(double dCInputCurrent) {
		DCInputCurrent = dCInputCurrent;
	}
	public double getLineFrequency() {
		return LineFrequency;
	}
	public void setLineFrequency(double lineFrequency) {
		LineFrequency = lineFrequency;
	}
	public double getOutputGeneration() {
		return OutputGeneration;
	}
	public void setOutputGeneration(double outputGeneration) {
		OutputGeneration = outputGeneration;
	}
	public double getTotalEnergyGeneration() {
		return TotalEnergyGeneration;
	}
	public void setTotalEnergyGeneration(double totalEnergyGeneration) {
		TotalEnergyGeneration = totalEnergyGeneration;
	}
	public double getPVInputVoltage() {
		return PVInputVoltage;
	}
	public void setPVInputVoltage(double pVInputVoltage) {
		PVInputVoltage = pVInputVoltage;
	}
	public double getInputGenerationCalculated() {
		return InputGenerationCalculated;
	}
	public void setInputGenerationCalculated(double inputGenerationCalculated) {
		InputGenerationCalculated = inputGenerationCalculated;
	}
	public double getInverterOperatingStatus() {
		return InverterOperatingStatus;
	}
	public void setInverterOperatingStatus(double inverterOperatingStatus) {
		InverterOperatingStatus = inverterOperatingStatus;
	}
	public double getMainFault() {
		return MainFault;
	}
	public void setMainFault(double mainFault) {
		MainFault = mainFault;
	}
	public double getDriveFault() {
		return DriveFault;
	}
	public void setDriveFault(double driveFault) {
		DriveFault = driveFault;
	}
	public double getVoltageFault() {
		return VoltageFault;
	}
	public void setVoltageFault(double voltageFault) {
		VoltageFault = voltageFault;
	}
	public double getGridFault() {
		return GridFault;
	}
	public void setGridFault(double gridFault) {
		GridFault = gridFault;
	}
	public double getTemperatureFault() {
		return TemperatureFault;
	}
	public void setTemperatureFault(double temperatureFault) {
		TemperatureFault = temperatureFault;
	}
	public double getSystemFault() {
		return SystemFault;
	}
	public void setSystemFault(double systemFault) {
		SystemFault = systemFault;
	}
	public double getSystemWarnings() {
		return SystemWarnings;
	}
	public void setSystemWarnings(double systemWarnings) {
		SystemWarnings = systemWarnings;
	}
	public double getPVMStatusCodes() {
		return PVMStatusCodes;
	}
	public void setPVMStatusCodes(double pVMStatusCodes) {
		PVMStatusCodes = pVMStatusCodes;
	}
	public double getNvmActivePower() {
		return nvmActivePower;
	}
	public void setNvmActivePower(double nvmActivePower) {
		this.nvmActivePower = nvmActivePower;
	}
	public double getNvmActiveEnergy() {
		return nvmActiveEnergy;
	}
	public void setNvmActiveEnergy(double nvmActiveEnergy) {
		this.nvmActiveEnergy = nvmActiveEnergy;
	}
	
	
	
}
