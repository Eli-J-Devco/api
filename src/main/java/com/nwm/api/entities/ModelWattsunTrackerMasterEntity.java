/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelWattsunTrackerMasterEntity extends ModelBaseEntity {
	private double Tracker_Address;
	private double Hour;
	private double Minute;
	private double Day;
	private double Digital_Inputs;
	private double Digital_Outputs;
	private double Watchdog_Counter;
	private double AngleCalc;
	private double Latitude;
	private double Longitude;
	private double Tracker_1_Mode;
	private double Tracker_1_First_Clear_Accumulator;
	private double Tracker_1_Second_Clear_Accumulator;
	private double Tracker_1_Angle_Setpoint;
	private double Tracker_1_Angle;
	private double Tracker_1_Night_Stow_Position;
	private double Tracker_1_Wind_Stow_Position;
	private double Tracker_2_Mode;
	private double Tracker_2_First_Clear_Accumulator;
	private double Tracker_2_Second_Clear_Accumulator;
	private double Tracker_2_Angle_Setpoint;
	private double Tracker_2_Angle;
	private double Tracker_2_Night_Stow_Position;
	private double Tracker_2_Wind_Stow_Position;
	private double Tracker_3_Mode;
	private double Tracker_3_First_Clear_Accumulator;
	private double Tracker_3_Second_Clear_Accumulator;
	private double Tracker_3_Angle_Setpoint;
	private double Tracker_3_Angle;
	private double Tracker_3_Night_Stow_Position;
	private double Tracker_3_Wind_Stow_Position;
	private double Tracker_4_Mode;
	private double Tracker_4_First_Clear_Accumulator;
	private double Tracker_4_Second_Clear_Accumulator;
	private double Tracker_4_Angle_Setpoint;
	private double Tracker_4_Angle;
	private double Tracker_4_Night_Stow_Position;
	private double Tracker_4_Wind_Stow_Position	;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
	
	public double getTracker_Address() {
		return Tracker_Address;
	}
	public void setTracker_Address(double tracker_Address) {
		Tracker_Address = tracker_Address;
	}
	public double getHour() {
		return Hour;
	}
	public void setHour(double hour) {
		Hour = hour;
	}
	public double getMinute() {
		return Minute;
	}
	public void setMinute(double minute) {
		Minute = minute;
	}
	public double getDay() {
		return Day;
	}
	public void setDay(double day) {
		Day = day;
	}
	public double getDigital_Inputs() {
		return Digital_Inputs;
	}
	public void setDigital_Inputs(double digital_Inputs) {
		Digital_Inputs = digital_Inputs;
	}
	public double getDigital_Outputs() {
		return Digital_Outputs;
	}
	public void setDigital_Outputs(double digital_Outputs) {
		Digital_Outputs = digital_Outputs;
	}
	public double getWatchdog_Counter() {
		return Watchdog_Counter;
	}
	public void setWatchdog_Counter(double watchdog_Counter) {
		Watchdog_Counter = watchdog_Counter;
	}
	public double getAngleCalc() {
		return AngleCalc;
	}
	public void setAngleCalc(double angleCalc) {
		AngleCalc = angleCalc;
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
	public double getTracker_1_Mode() {
		return Tracker_1_Mode;
	}
	public void setTracker_1_Mode(double tracker_1_Mode) {
		Tracker_1_Mode = tracker_1_Mode;
	}
	public double getTracker_1_First_Clear_Accumulator() {
		return Tracker_1_First_Clear_Accumulator;
	}
	public void setTracker_1_First_Clear_Accumulator(double tracker_1_First_Clear_Accumulator) {
		Tracker_1_First_Clear_Accumulator = tracker_1_First_Clear_Accumulator;
	}
	public double getTracker_1_Second_Clear_Accumulator() {
		return Tracker_1_Second_Clear_Accumulator;
	}
	public void setTracker_1_Second_Clear_Accumulator(double tracker_1_Second_Clear_Accumulator) {
		Tracker_1_Second_Clear_Accumulator = tracker_1_Second_Clear_Accumulator;
	}
	public double getTracker_1_Angle_Setpoint() {
		return Tracker_1_Angle_Setpoint;
	}
	public void setTracker_1_Angle_Setpoint(double tracker_1_Angle_Setpoint) {
		Tracker_1_Angle_Setpoint = tracker_1_Angle_Setpoint;
	}
	public double getTracker_1_Angle() {
		return Tracker_1_Angle;
	}
	public void setTracker_1_Angle(double tracker_1_Angle) {
		Tracker_1_Angle = tracker_1_Angle;
	}
	public double getTracker_1_Night_Stow_Position() {
		return Tracker_1_Night_Stow_Position;
	}
	public void setTracker_1_Night_Stow_Position(double tracker_1_Night_Stow_Position) {
		Tracker_1_Night_Stow_Position = tracker_1_Night_Stow_Position;
	}
	public double getTracker_1_Wind_Stow_Position() {
		return Tracker_1_Wind_Stow_Position;
	}
	public void setTracker_1_Wind_Stow_Position(double tracker_1_Wind_Stow_Position) {
		Tracker_1_Wind_Stow_Position = tracker_1_Wind_Stow_Position;
	}
	public double getTracker_2_Mode() {
		return Tracker_2_Mode;
	}
	public void setTracker_2_Mode(double tracker_2_Mode) {
		Tracker_2_Mode = tracker_2_Mode;
	}
	public double getTracker_2_First_Clear_Accumulator() {
		return Tracker_2_First_Clear_Accumulator;
	}
	public void setTracker_2_First_Clear_Accumulator(double tracker_2_First_Clear_Accumulator) {
		Tracker_2_First_Clear_Accumulator = tracker_2_First_Clear_Accumulator;
	}
	public double getTracker_2_Second_Clear_Accumulator() {
		return Tracker_2_Second_Clear_Accumulator;
	}
	public void setTracker_2_Second_Clear_Accumulator(double tracker_2_Second_Clear_Accumulator) {
		Tracker_2_Second_Clear_Accumulator = tracker_2_Second_Clear_Accumulator;
	}
	public double getTracker_2_Angle_Setpoint() {
		return Tracker_2_Angle_Setpoint;
	}
	public void setTracker_2_Angle_Setpoint(double tracker_2_Angle_Setpoint) {
		Tracker_2_Angle_Setpoint = tracker_2_Angle_Setpoint;
	}
	public double getTracker_2_Angle() {
		return Tracker_2_Angle;
	}
	public void setTracker_2_Angle(double tracker_2_Angle) {
		Tracker_2_Angle = tracker_2_Angle;
	}
	public double getTracker_2_Night_Stow_Position() {
		return Tracker_2_Night_Stow_Position;
	}
	public void setTracker_2_Night_Stow_Position(double tracker_2_Night_Stow_Position) {
		Tracker_2_Night_Stow_Position = tracker_2_Night_Stow_Position;
	}
	public double getTracker_2_Wind_Stow_Position() {
		return Tracker_2_Wind_Stow_Position;
	}
	public void setTracker_2_Wind_Stow_Position(double tracker_2_Wind_Stow_Position) {
		Tracker_2_Wind_Stow_Position = tracker_2_Wind_Stow_Position;
	}
	public double getTracker_3_Mode() {
		return Tracker_3_Mode;
	}
	public void setTracker_3_Mode(double tracker_3_Mode) {
		Tracker_3_Mode = tracker_3_Mode;
	}
	public double getTracker_3_First_Clear_Accumulator() {
		return Tracker_3_First_Clear_Accumulator;
	}
	public void setTracker_3_First_Clear_Accumulator(double tracker_3_First_Clear_Accumulator) {
		Tracker_3_First_Clear_Accumulator = tracker_3_First_Clear_Accumulator;
	}
	public double getTracker_3_Second_Clear_Accumulator() {
		return Tracker_3_Second_Clear_Accumulator;
	}
	public void setTracker_3_Second_Clear_Accumulator(double tracker_3_Second_Clear_Accumulator) {
		Tracker_3_Second_Clear_Accumulator = tracker_3_Second_Clear_Accumulator;
	}
	public double getTracker_3_Angle_Setpoint() {
		return Tracker_3_Angle_Setpoint;
	}
	public void setTracker_3_Angle_Setpoint(double tracker_3_Angle_Setpoint) {
		Tracker_3_Angle_Setpoint = tracker_3_Angle_Setpoint;
	}
	public double getTracker_3_Angle() {
		return Tracker_3_Angle;
	}
	public void setTracker_3_Angle(double tracker_3_Angle) {
		Tracker_3_Angle = tracker_3_Angle;
	}
	public double getTracker_3_Night_Stow_Position() {
		return Tracker_3_Night_Stow_Position;
	}
	public void setTracker_3_Night_Stow_Position(double tracker_3_Night_Stow_Position) {
		Tracker_3_Night_Stow_Position = tracker_3_Night_Stow_Position;
	}
	public double getTracker_3_Wind_Stow_Position() {
		return Tracker_3_Wind_Stow_Position;
	}
	public void setTracker_3_Wind_Stow_Position(double tracker_3_Wind_Stow_Position) {
		Tracker_3_Wind_Stow_Position = tracker_3_Wind_Stow_Position;
	}
	public double getTracker_4_Mode() {
		return Tracker_4_Mode;
	}
	public void setTracker_4_Mode(double tracker_4_Mode) {
		Tracker_4_Mode = tracker_4_Mode;
	}
	public double getTracker_4_First_Clear_Accumulator() {
		return Tracker_4_First_Clear_Accumulator;
	}
	public void setTracker_4_First_Clear_Accumulator(double tracker_4_First_Clear_Accumulator) {
		Tracker_4_First_Clear_Accumulator = tracker_4_First_Clear_Accumulator;
	}
	public double getTracker_4_Second_Clear_Accumulator() {
		return Tracker_4_Second_Clear_Accumulator;
	}
	public void setTracker_4_Second_Clear_Accumulator(double tracker_4_Second_Clear_Accumulator) {
		Tracker_4_Second_Clear_Accumulator = tracker_4_Second_Clear_Accumulator;
	}
	public double getTracker_4_Angle_Setpoint() {
		return Tracker_4_Angle_Setpoint;
	}
	public void setTracker_4_Angle_Setpoint(double tracker_4_Angle_Setpoint) {
		Tracker_4_Angle_Setpoint = tracker_4_Angle_Setpoint;
	}
	public double getTracker_4_Angle() {
		return Tracker_4_Angle;
	}
	public void setTracker_4_Angle(double tracker_4_Angle) {
		Tracker_4_Angle = tracker_4_Angle;
	}
	public double getTracker_4_Night_Stow_Position() {
		return Tracker_4_Night_Stow_Position;
	}
	public void setTracker_4_Night_Stow_Position(double tracker_4_Night_Stow_Position) {
		Tracker_4_Night_Stow_Position = tracker_4_Night_Stow_Position;
	}
	public double getTracker_4_Wind_Stow_Position() {
		return Tracker_4_Wind_Stow_Position;
	}
	public void setTracker_4_Wind_Stow_Position(double tracker_4_Wind_Stow_Position) {
		Tracker_4_Wind_Stow_Position = tracker_4_Wind_Stow_Position;
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
}
