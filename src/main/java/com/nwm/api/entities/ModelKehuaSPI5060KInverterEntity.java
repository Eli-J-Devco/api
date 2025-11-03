package com.nwm.api.entities;

public class ModelKehuaSPI5060KInverterEntity extends ModelBaseEntity {
	private double DeviceStatus;
	private double DailyEnergy;
	private double TotalEnergy;
	private double Gridfrequency;
	private double UphaseUVgridvoltage;
	private double VphaseVWgridvoltage;
	private double WphaseWUgridvoltage;
	private double Uphasegridcurrent;
	private double Vphasegridcurrent;
	private double Wphasegridcurrent;
	private double Gridconnectedtotalactivepower;
	private double Gridconnectedtotalreactivepower;
	private double Heatsinktemperature;
	private double Innertemperature;
	private double Gridconnectedtotalapparentpower;
	private double IGBTtemperature;
	private double Outputpowerfactor;
	private double PVinputtotalpower;
	private double ACleakagecurrent;
	private double Dailypowerconsumption;
	private double Totalpowerconsumption;
	private double Ongridactivepower;
	private double Ongridapparentpower;
	private double Ongridreactivepower;
	private double OngridPowerfactor;
	private double TotalInsulationImpedance;
	private double MPPT1Voltage;
	private double MPPT2Voltage;
	private double MPPT3Voltage;
	private double MPPT4Voltage;
	private double MPPT1Current;
	private double MPPT2Current;
	private double MPPT3Current;
	private double MPPT4Current;
	private double BusVoltage;
	private double FaultWord;
	private int totalFaultWord;
	
	
	
	public int getTotalFaultWord() {
		return totalFaultWord;
	}
	public void setTotalFaultWord(int totalFaultWord) {
		this.totalFaultWord = totalFaultWord;
	}
	public double getTotalInsulationImpedance() {
		return TotalInsulationImpedance;
	}
	public void setTotalInsulationImpedance(double totalInsulationImpedance) {
		TotalInsulationImpedance = totalInsulationImpedance;
	}
	public double getMPPT1Voltage() {
		return MPPT1Voltage;
	}
	public void setMPPT1Voltage(double mPPT1Voltage) {
		MPPT1Voltage = mPPT1Voltage;
	}
	public double getMPPT2Voltage() {
		return MPPT2Voltage;
	}
	public void setMPPT2Voltage(double mPPT2Voltage) {
		MPPT2Voltage = mPPT2Voltage;
	}
	public double getMPPT3Voltage() {
		return MPPT3Voltage;
	}
	public void setMPPT3Voltage(double mPPT3Voltage) {
		MPPT3Voltage = mPPT3Voltage;
	}
	public double getMPPT4Voltage() {
		return MPPT4Voltage;
	}
	public void setMPPT4Voltage(double mPPT4Voltage) {
		MPPT4Voltage = mPPT4Voltage;
	}
	public double getMPPT1Current() {
		return MPPT1Current;
	}
	public void setMPPT1Current(double mPPT1Current) {
		MPPT1Current = mPPT1Current;
	}
	public double getMPPT2Current() {
		return MPPT2Current;
	}
	public void setMPPT2Current(double mPPT2Current) {
		MPPT2Current = mPPT2Current;
	}
	public double getMPPT3Current() {
		return MPPT3Current;
	}
	public void setMPPT3Current(double mPPT3Current) {
		MPPT3Current = mPPT3Current;
	}
	public double getMPPT4Current() {
		return MPPT4Current;
	}
	public void setMPPT4Current(double mPPT4Current) {
		MPPT4Current = mPPT4Current;
	}
	public double getBusVoltage() {
		return BusVoltage;
	}
	public void setBusVoltage(double busVoltage) {
		BusVoltage = busVoltage;
	}
	public double getFaultWord() {
		return FaultWord;
	}
	public void setFaultWord(double faultWord) {
		FaultWord = faultWord;
	}
	public double getDeviceStatus() {
		return DeviceStatus;
	}
	public void setDeviceStatus(double deviceStatus) {
		DeviceStatus = deviceStatus;
	}
	public double getDailyEnergy() {
		return DailyEnergy;
	}
	public void setDailyEnergy(double dailyEnergy) {
		DailyEnergy = dailyEnergy;
	}
	public double getTotalEnergy() {
		return TotalEnergy;
	}
	public void setTotalEnergy(double totalEnergy) {
		TotalEnergy = totalEnergy;
	}
	public double getGridfrequency() {
		return Gridfrequency;
	}
	public void setGridfrequency(double gridfrequency) {
		Gridfrequency = gridfrequency;
	}
	public double getUphaseUVgridvoltage() {
		return UphaseUVgridvoltage;
	}
	public void setUphaseUVgridvoltage(double uphaseUVgridvoltage) {
		UphaseUVgridvoltage = uphaseUVgridvoltage;
	}
	public double getVphaseVWgridvoltage() {
		return VphaseVWgridvoltage;
	}
	public void setVphaseVWgridvoltage(double vphaseVWgridvoltage) {
		VphaseVWgridvoltage = vphaseVWgridvoltage;
	}
	public double getWphaseWUgridvoltage() {
		return WphaseWUgridvoltage;
	}
	public void setWphaseWUgridvoltage(double wphaseWUgridvoltage) {
		WphaseWUgridvoltage = wphaseWUgridvoltage;
	}
	public double getUphasegridcurrent() {
		return Uphasegridcurrent;
	}
	public void setUphasegridcurrent(double uphasegridcurrent) {
		Uphasegridcurrent = uphasegridcurrent;
	}
	public double getVphasegridcurrent() {
		return Vphasegridcurrent;
	}
	public void setVphasegridcurrent(double vphasegridcurrent) {
		Vphasegridcurrent = vphasegridcurrent;
	}
	public double getWphasegridcurrent() {
		return Wphasegridcurrent;
	}
	public void setWphasegridcurrent(double wphasegridcurrent) {
		Wphasegridcurrent = wphasegridcurrent;
	}
	public double getGridconnectedtotalactivepower() {
		return Gridconnectedtotalactivepower;
	}
	public void setGridconnectedtotalactivepower(double gridconnectedtotalactivepower) {
		Gridconnectedtotalactivepower = gridconnectedtotalactivepower;
	}
	public double getGridconnectedtotalreactivepower() {
		return Gridconnectedtotalreactivepower;
	}
	public void setGridconnectedtotalreactivepower(double gridconnectedtotalreactivepower) {
		Gridconnectedtotalreactivepower = gridconnectedtotalreactivepower;
	}
	public double getHeatsinktemperature() {
		return Heatsinktemperature;
	}
	public void setHeatsinktemperature(double heatsinktemperature) {
		Heatsinktemperature = heatsinktemperature;
	}
	public double getInnertemperature() {
		return Innertemperature;
	}
	public void setInnertemperature(double innertemperature) {
		Innertemperature = innertemperature;
	}
	public double getGridconnectedtotalapparentpower() {
		return Gridconnectedtotalapparentpower;
	}
	public void setGridconnectedtotalapparentpower(double gridconnectedtotalapparentpower) {
		Gridconnectedtotalapparentpower = gridconnectedtotalapparentpower;
	}
	public double getIGBTtemperature() {
		return IGBTtemperature;
	}
	public void setIGBTtemperature(double iGBTtemperature) {
		IGBTtemperature = iGBTtemperature;
	}
	public double getOutputpowerfactor() {
		return Outputpowerfactor;
	}
	public void setOutputpowerfactor(double outputpowerfactor) {
		Outputpowerfactor = outputpowerfactor;
	}
	public double getPVinputtotalpower() {
		return PVinputtotalpower;
	}
	public void setPVinputtotalpower(double pVinputtotalpower) {
		PVinputtotalpower = pVinputtotalpower;
	}
	public double getACleakagecurrent() {
		return ACleakagecurrent;
	}
	public void setACleakagecurrent(double aCleakagecurrent) {
		ACleakagecurrent = aCleakagecurrent;
	}
	public double getDailypowerconsumption() {
		return Dailypowerconsumption;
	}
	public void setDailypowerconsumption(double dailypowerconsumption) {
		Dailypowerconsumption = dailypowerconsumption;
	}
	public double getTotalpowerconsumption() {
		return Totalpowerconsumption;
	}
	public void setTotalpowerconsumption(double totalpowerconsumption) {
		Totalpowerconsumption = totalpowerconsumption;
	}
	public double getOngridactivepower() {
		return Ongridactivepower;
	}
	public void setOngridactivepower(double ongridactivepower) {
		Ongridactivepower = ongridactivepower;
	}
	public double getOngridapparentpower() {
		return Ongridapparentpower;
	}
	public void setOngridapparentpower(double ongridapparentpower) {
		Ongridapparentpower = ongridapparentpower;
	}
	public double getOngridreactivepower() {
		return Ongridreactivepower;
	}
	public void setOngridreactivepower(double ongridreactivepower) {
		Ongridreactivepower = ongridreactivepower;
	}
	public double getOngridPowerfactor() {
		return OngridPowerfactor;
	}
	public void setOngridPowerfactor(double ongridPowerfactor) {
		OngridPowerfactor = ongridPowerfactor;
	}
	
	
	
}
