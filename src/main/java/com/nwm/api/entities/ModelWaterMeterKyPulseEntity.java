/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelWaterMeterKyPulseEntity extends ModelBaseEntity {
	private double MODBUSID;
	private double BaudRate;
	private double ParityDataStopBits;
	private double DataOrder;
	private double CounterMode;
	private double CounterEdge;
	private double PulsesPerCubicFootOfWater;
	private double DigitalInputBinary;
	private double PulseCounter;
	private double TrueCounter;
	private double TotalWaterUsage;
	public double getMODBUSID() {
		return MODBUSID;
	}
	public void setMODBUSID(double mODBUSID) {
		MODBUSID = mODBUSID;
	}
	public double getBaudRate() {
		return BaudRate;
	}
	public void setBaudRate(double baudRate) {
		BaudRate = baudRate;
	}
	public double getParityDataStopBits() {
		return ParityDataStopBits;
	}
	public void setParityDataStopBits(double parityDataStopBits) {
		ParityDataStopBits = parityDataStopBits;
	}
	public double getDataOrder() {
		return DataOrder;
	}
	public void setDataOrder(double dataOrder) {
		DataOrder = dataOrder;
	}
	public double getCounterMode() {
		return CounterMode;
	}
	public void setCounterMode(double counterMode) {
		CounterMode = counterMode;
	}
	public double getCounterEdge() {
		return CounterEdge;
	}
	public void setCounterEdge(double counterEdge) {
		CounterEdge = counterEdge;
	}
	public double getPulsesPerCubicFootOfWater() {
		return PulsesPerCubicFootOfWater;
	}
	public void setPulsesPerCubicFootOfWater(double pulsesPerCubicFootOfWater) {
		PulsesPerCubicFootOfWater = pulsesPerCubicFootOfWater;
	}
	public double getDigitalInputBinary() {
		return DigitalInputBinary;
	}
	public void setDigitalInputBinary(double digitalInputBinary) {
		DigitalInputBinary = digitalInputBinary;
	}
	public double getPulseCounter() {
		return PulseCounter;
	}
	public void setPulseCounter(double pulseCounter) {
		PulseCounter = pulseCounter;
	}
	public double getTrueCounter() {
		return TrueCounter;
	}
	public void setTrueCounter(double trueCounter) {
		TrueCounter = trueCounter;
	}
	public double getTotalWaterUsage() {
		return TotalWaterUsage;
	}
	public void setTotalWaterUsage(double totalWaterUsage) {
		TotalWaterUsage = totalWaterUsage;
	}
	
	
	
}
