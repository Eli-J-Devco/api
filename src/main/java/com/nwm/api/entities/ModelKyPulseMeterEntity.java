/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelKyPulseMeterEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
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
	
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private double MeasuredProduction;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
	private int data_send_time;
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
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
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
	public int getData_send_time() {
		return data_send_time;
	}
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	
	
}
