/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelKyPulseMeterEntity extends ModelBaseEntity {
	private double MODBUSID;
	private double BaudRate;
	private double ParityDataStopBits;
	private double DataOrder;
	private double CounterMode;
	private double CounterEdge;
	private double Pulsesper18kWhDelivered;
	private double DigitalInputBinary;
	private double PulseCounter;
	private double TrueCounter;
	private double CumulativeEnergyDelivered;
	
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
	public double getPulsesper18kWhDelivered() {
		return Pulsesper18kWhDelivered;
	}
	public void setPulsesper18kWhDelivered(double pulsesper18kWhDelivered) {
		Pulsesper18kWhDelivered = pulsesper18kWhDelivered;
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
	public double getCumulativeEnergyDelivered() {
		return CumulativeEnergyDelivered;
	}
	public void setCumulativeEnergyDelivered(double cumulativeEnergyDelivered) {
		CumulativeEnergyDelivered = cumulativeEnergyDelivered;
	}
	
	
}
