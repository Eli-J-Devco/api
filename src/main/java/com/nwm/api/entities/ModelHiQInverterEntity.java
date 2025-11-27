/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelHiQInverterEntity extends ModelBaseEntity {
	private double TimeLastContacted;
	private double InverterMode;
	private double InverterStatus;
	private double ActivePower;
	private double TotalEnergy;
	private double String1Voltage;
	private double String1Current;
	private double String1Power;
	private double String1Status;
	private double String2Voltage;
	private double String2Current;
	private double String2Power;
	private double String2Status;
	private double SerialNumberASCII_1_4;
	private double SerialNumberASCII_5_8;
	private double NumberOfStrings;
	private double PLCSignal;
	private double PLCNoise;
	private double FirmwareVersionASCII;
	
	public double getTimeLastContacted() {
		return TimeLastContacted;
	}
	public void setTimeLastContacted(double timeLastContacted) {
		TimeLastContacted = timeLastContacted;
	}
	public double getInverterMode() {
		return InverterMode;
	}
	public void setInverterMode(double inverterMode) {
		InverterMode = inverterMode;
	}
	public double getInverterStatus() {
		return InverterStatus;
	}
	public void setInverterStatus(double inverterStatus) {
		InverterStatus = inverterStatus;
	}
	public double getActivePower() {
		return ActivePower;
	}
	public void setActivePower(double activePower) {
		ActivePower = activePower;
	}
	public double getTotalEnergy() {
		return TotalEnergy;
	}
	public void setTotalEnergy(double totalEnergy) {
		TotalEnergy = totalEnergy;
	}
	public double getString1Voltage() {
		return String1Voltage;
	}
	public void setString1Voltage(double string1Voltage) {
		String1Voltage = string1Voltage;
	}
	public double getString1Current() {
		return String1Current;
	}
	public void setString1Current(double string1Current) {
		String1Current = string1Current;
	}
	public double getString1Power() {
		return String1Power;
	}
	public void setString1Power(double string1Power) {
		String1Power = string1Power;
	}
	public double getString1Status() {
		return String1Status;
	}
	public void setString1Status(double string1Status) {
		String1Status = string1Status;
	}
	public double getString2Voltage() {
		return String2Voltage;
	}
	public void setString2Voltage(double string2Voltage) {
		String2Voltage = string2Voltage;
	}
	public double getString2Current() {
		return String2Current;
	}
	public void setString2Current(double string2Current) {
		String2Current = string2Current;
	}
	public double getString2Power() {
		return String2Power;
	}
	public void setString2Power(double string2Power) {
		String2Power = string2Power;
	}
	public double getString2Status() {
		return String2Status;
	}
	public void setString2Status(double string2Status) {
		String2Status = string2Status;
	}
	public double getSerialNumberASCII_1_4() {
		return SerialNumberASCII_1_4;
	}
	public void setSerialNumberASCII_1_4(double serialNumberASCII_1_4) {
		SerialNumberASCII_1_4 = serialNumberASCII_1_4;
	}
	public double getSerialNumberASCII_5_8() {
		return SerialNumberASCII_5_8;
	}
	public void setSerialNumberASCII_5_8(double serialNumberASCII_5_8) {
		SerialNumberASCII_5_8 = serialNumberASCII_5_8;
	}
	public double getNumberOfStrings() {
		return NumberOfStrings;
	}
	public void setNumberOfStrings(double numberOfStrings) {
		NumberOfStrings = numberOfStrings;
	}
	public double getPLCSignal() {
		return PLCSignal;
	}
	public void setPLCSignal(double pLCSignal) {
		PLCSignal = pLCSignal;
	}
	public double getPLCNoise() {
		return PLCNoise;
	}
	public void setPLCNoise(double pLCNoise) {
		PLCNoise = pLCNoise;
	}
	public double getFirmwareVersionASCII() {
		return FirmwareVersionASCII;
	}
	public void setFirmwareVersionASCII(double firmwareVersionASCII) {
		FirmwareVersionASCII = firmwareVersionASCII;
	}
}
