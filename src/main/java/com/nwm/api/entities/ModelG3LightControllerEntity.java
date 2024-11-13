/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelG3LightControllerEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	
	private double InputsStatus164;
	private double InputsManualFlag164;
	private double ZonesStatus164;
	private double ZonesManualFlag164;
	private double ZonesFeedbackState164;
	private double ZonesAlarmState164;
	private double ZoneswithNonRespondingBreaker164;
	private double BreakersPanel0L;
	private double BreakersPanel0R;
	private double BreakersPanel1L;
	private double BreakersPanel1R;
	private double BreakersPanel2L;
	private double BreakersPanel2R;
	private double BreakerUnresponsivePanel0L;
	private double BreakerUnresponsivePanel0R;
	private double BreakerUnresponsivePanel1L;
	private double BreakerUnresponsivePanel1R;
	private double BreakerUnresponsivePanel2L;
	private double BreakerUnresponsivePanel2R;
	
	
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
	private int enable_alert;
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
	public double getInputsStatus164() {
		return InputsStatus164;
	}
	public void setInputsStatus164(double inputsStatus164) {
		InputsStatus164 = inputsStatus164;
	}
	public double getInputsManualFlag164() {
		return InputsManualFlag164;
	}
	public void setInputsManualFlag164(double inputsManualFlag164) {
		InputsManualFlag164 = inputsManualFlag164;
	}
	public double getZonesStatus164() {
		return ZonesStatus164;
	}
	public void setZonesStatus164(double zonesStatus164) {
		ZonesStatus164 = zonesStatus164;
	}
	public double getZonesManualFlag164() {
		return ZonesManualFlag164;
	}
	public void setZonesManualFlag164(double zonesManualFlag164) {
		ZonesManualFlag164 = zonesManualFlag164;
	}
	public double getZonesFeedbackState164() {
		return ZonesFeedbackState164;
	}
	public void setZonesFeedbackState164(double zonesFeedbackState164) {
		ZonesFeedbackState164 = zonesFeedbackState164;
	}
	public double getZonesAlarmState164() {
		return ZonesAlarmState164;
	}
	public void setZonesAlarmState164(double zonesAlarmState164) {
		ZonesAlarmState164 = zonesAlarmState164;
	}
	public double getZoneswithNonRespondingBreaker164() {
		return ZoneswithNonRespondingBreaker164;
	}
	public void setZoneswithNonRespondingBreaker164(double zoneswithNonRespondingBreaker164) {
		ZoneswithNonRespondingBreaker164 = zoneswithNonRespondingBreaker164;
	}
	public double getBreakersPanel0L() {
		return BreakersPanel0L;
	}
	public void setBreakersPanel0L(double breakersPanel0L) {
		BreakersPanel0L = breakersPanel0L;
	}
	public double getBreakersPanel0R() {
		return BreakersPanel0R;
	}
	public void setBreakersPanel0R(double breakersPanel0R) {
		BreakersPanel0R = breakersPanel0R;
	}
	public double getBreakersPanel1L() {
		return BreakersPanel1L;
	}
	public void setBreakersPanel1L(double breakersPanel1L) {
		BreakersPanel1L = breakersPanel1L;
	}
	public double getBreakersPanel1R() {
		return BreakersPanel1R;
	}
	public void setBreakersPanel1R(double breakersPanel1R) {
		BreakersPanel1R = breakersPanel1R;
	}
	public double getBreakersPanel2L() {
		return BreakersPanel2L;
	}
	public void setBreakersPanel2L(double breakersPanel2L) {
		BreakersPanel2L = breakersPanel2L;
	}
	public double getBreakersPanel2R() {
		return BreakersPanel2R;
	}
	public void setBreakersPanel2R(double breakersPanel2R) {
		BreakersPanel2R = breakersPanel2R;
	}
	public double getBreakerUnresponsivePanel0L() {
		return BreakerUnresponsivePanel0L;
	}
	public void setBreakerUnresponsivePanel0L(double breakerUnresponsivePanel0L) {
		BreakerUnresponsivePanel0L = breakerUnresponsivePanel0L;
	}
	public double getBreakerUnresponsivePanel0R() {
		return BreakerUnresponsivePanel0R;
	}
	public void setBreakerUnresponsivePanel0R(double breakerUnresponsivePanel0R) {
		BreakerUnresponsivePanel0R = breakerUnresponsivePanel0R;
	}
	public double getBreakerUnresponsivePanel1L() {
		return BreakerUnresponsivePanel1L;
	}
	public void setBreakerUnresponsivePanel1L(double breakerUnresponsivePanel1L) {
		BreakerUnresponsivePanel1L = breakerUnresponsivePanel1L;
	}
	public double getBreakerUnresponsivePanel1R() {
		return BreakerUnresponsivePanel1R;
	}
	public void setBreakerUnresponsivePanel1R(double breakerUnresponsivePanel1R) {
		BreakerUnresponsivePanel1R = breakerUnresponsivePanel1R;
	}
	public double getBreakerUnresponsivePanel2L() {
		return BreakerUnresponsivePanel2L;
	}
	public void setBreakerUnresponsivePanel2L(double breakerUnresponsivePanel2L) {
		BreakerUnresponsivePanel2L = breakerUnresponsivePanel2L;
	}
	public double getBreakerUnresponsivePanel2R() {
		return BreakerUnresponsivePanel2R;
	}
	public void setBreakerUnresponsivePanel2R(double breakerUnresponsivePanel2R) {
		BreakerUnresponsivePanel2R = breakerUnresponsivePanel2R;
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
