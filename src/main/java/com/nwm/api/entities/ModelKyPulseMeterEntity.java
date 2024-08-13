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
	private double CounterModeInput1;
	private double CounterEdgeInput1;
	private double ImpulseperRealValueInput1;
	private double DelayLowtoHighInput1;
	private double DelayHightoLowInput1;
	private double DigitalInput1binary;
	private double CounterInput132bit;
	private double CounterInput1RealValue;
	
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
	public double getCounterModeInput1() {
		return CounterModeInput1;
	}
	public void setCounterModeInput1(double counterModeInput1) {
		CounterModeInput1 = counterModeInput1;
	}
	public double getCounterEdgeInput1() {
		return CounterEdgeInput1;
	}
	public void setCounterEdgeInput1(double counterEdgeInput1) {
		CounterEdgeInput1 = counterEdgeInput1;
	}
	public double getImpulseperRealValueInput1() {
		return ImpulseperRealValueInput1;
	}
	public void setImpulseperRealValueInput1(double impulseperRealValueInput1) {
		ImpulseperRealValueInput1 = impulseperRealValueInput1;
	}
	public double getDelayLowtoHighInput1() {
		return DelayLowtoHighInput1;
	}
	public void setDelayLowtoHighInput1(double delayLowtoHighInput1) {
		DelayLowtoHighInput1 = delayLowtoHighInput1;
	}
	public double getDelayHightoLowInput1() {
		return DelayHightoLowInput1;
	}
	public void setDelayHightoLowInput1(double delayHightoLowInput1) {
		DelayHightoLowInput1 = delayHightoLowInput1;
	}
	public double getDigitalInput1binary() {
		return DigitalInput1binary;
	}
	public void setDigitalInput1binary(double digitalInput1binary) {
		DigitalInput1binary = digitalInput1binary;
	}
	public double getCounterInput132bit() {
		return CounterInput132bit;
	}
	public void setCounterInput132bit(double counterInput132bit) {
		CounterInput132bit = counterInput132bit;
	}
	public double getCounterInput1RealValue() {
		return CounterInput1RealValue;
	}
	public void setCounterInput1RealValue(double counterInput1RealValue) {
		CounterInput1RealValue = counterInput1RealValue;
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
