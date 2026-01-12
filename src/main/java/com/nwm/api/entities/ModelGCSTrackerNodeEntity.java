/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelGCSTrackerNodeEntity extends ModelBaseEntity {
	private double TrackernodeID;
	private double Timestamp;
	private double Targettrackingangle;
	private double Paneltableangle;
	private double Batteryvoltage;
	private double Chargingstatus;
	private double Averagemotorcurrent;
	private double Peakmotorcurrent;
	private double Alarm;
	private double Statusflag;
	private double PeakTemperature;
	
	public double getTrackernodeID() {
		return TrackernodeID;
	}
	public void setTrackernodeID(double trackernodeID) {
		TrackernodeID = trackernodeID;
	}
	public double getTimestamp() {
		return Timestamp;
	}
	public void setTimestamp(double timestamp) {
		Timestamp = timestamp;
	}
	public double getTargettrackingangle() {
		return Targettrackingangle;
	}
	public void setTargettrackingangle(double targettrackingangle) {
		Targettrackingangle = targettrackingangle;
	}
	public double getPaneltableangle() {
		return Paneltableangle;
	}
	public void setPaneltableangle(double paneltableangle) {
		Paneltableangle = paneltableangle;
	}
	public double getBatteryvoltage() {
		return Batteryvoltage;
	}
	public void setBatteryvoltage(double batteryvoltage) {
		Batteryvoltage = batteryvoltage;
	}
	public double getChargingstatus() {
		return Chargingstatus;
	}
	public void setChargingstatus(double chargingstatus) {
		Chargingstatus = chargingstatus;
	}
	public double getAveragemotorcurrent() {
		return Averagemotorcurrent;
	}
	public void setAveragemotorcurrent(double averagemotorcurrent) {
		Averagemotorcurrent = averagemotorcurrent;
	}
	public double getPeakmotorcurrent() {
		return Peakmotorcurrent;
	}
	public void setPeakmotorcurrent(double peakmotorcurrent) {
		Peakmotorcurrent = peakmotorcurrent;
	}
	public double getAlarm() {
		return Alarm;
	}
	public void setAlarm(double alarm) {
		Alarm = alarm;
	}
	public double getStatusflag() {
		return Statusflag;
	}
	public void setStatusflag(double statusflag) {
		Statusflag = statusflag;
	}
	public double getPeakTemperature() {
		return PeakTemperature;
	}
	public void setPeakTemperature(double peakTemperature) {
		PeakTemperature = peakTemperature;
	}
	
}
