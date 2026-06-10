/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelG3LightControllerEntity extends ModelBaseEntity {
	private double InputsStatus132;
	private double InputsManualFlag132;
	private double ZonesStatus132;
	private double ZonesManualFlag132;
	private double ZonesFeedbackState132;
	private double ZonesAlarmState132;
	private double BreakersPanel0L;
	private double BreakersPanel0R;
	private double BreakersPanel1L;
	private double BreakersPanel1R;
	private double BreakersPanel2L;
	private double BreakersPanel2R;
	private double BreakerPresentPanel0L;
	private double BreakerPresentPanel0R;
	private double BreakerPresentPanel1L;
	private double BreakerPresentPanel1R;
	private double BreakerPresentPanel2L;
	private double BreakerPresentPanel2R;
	private double NonRespondingBreakersPanel0L;
	private double NonRespondingBreakersPanel0R;
	private double NonRespondingBreakersPanel1L;
	private double NonRespondingBreakersPanel1R;
	private double NonRespondingBreakersPanel2L;
	private double NonRespondingBreakersPanel2R;
	private double ScheduleStatus116;
	private double Schedule1PeriodStatus124;
	
	private int totalNoComm;
	private int totalZonesAlarmState132;
	private int totalBreakersPanel0L;
	private int totalBreakersPanel0R;
	private int totalBreakersPanel1L;
	private int totalBreakersPanel1R;
	private int totalBreakersPanel2L;
	private int totalBreakersPanel2R;
	
	
	public int getTotalNoComm() {
		return totalNoComm;
	}
	public void setTotalNoComm(int totalNoComm) {
		this.totalNoComm = totalNoComm;
	}
	public int getTotalZonesAlarmState132() {
		return totalZonesAlarmState132;
	}
	public void setTotalZonesAlarmState132(int totalZonesAlarmState132) {
		this.totalZonesAlarmState132 = totalZonesAlarmState132;
	}
	public int getTotalBreakersPanel0L() {
		return totalBreakersPanel0L;
	}
	public void setTotalBreakersPanel0L(int totalBreakersPanel0L) {
		this.totalBreakersPanel0L = totalBreakersPanel0L;
	}
	public int getTotalBreakersPanel0R() {
		return totalBreakersPanel0R;
	}
	public void setTotalBreakersPanel0R(int totalBreakersPanel0R) {
		this.totalBreakersPanel0R = totalBreakersPanel0R;
	}
	public int getTotalBreakersPanel1L() {
		return totalBreakersPanel1L;
	}
	public void setTotalBreakersPanel1L(int totalBreakersPanel1L) {
		this.totalBreakersPanel1L = totalBreakersPanel1L;
	}
	public int getTotalBreakersPanel1R() {
		return totalBreakersPanel1R;
	}
	public void setTotalBreakersPanel1R(int totalBreakersPanel1R) {
		this.totalBreakersPanel1R = totalBreakersPanel1R;
	}
	public int getTotalBreakersPanel2L() {
		return totalBreakersPanel2L;
	}
	public void setTotalBreakersPanel2L(int totalBreakersPanel2L) {
		this.totalBreakersPanel2L = totalBreakersPanel2L;
	}
	public int getTotalBreakersPanel2R() {
		return totalBreakersPanel2R;
	}
	public void setTotalBreakersPanel2R(int totalBreakersPanel2R) {
		this.totalBreakersPanel2R = totalBreakersPanel2R;
	}
	public double getInputsStatus132() {
		return InputsStatus132;
	}
	public void setInputsStatus132(double inputsStatus132) {
		InputsStatus132 = inputsStatus132;
	}
	public double getInputsManualFlag132() {
		return InputsManualFlag132;
	}
	public void setInputsManualFlag132(double inputsManualFlag132) {
		InputsManualFlag132 = inputsManualFlag132;
	}
	public double getZonesStatus132() {
		return ZonesStatus132;
	}
	public void setZonesStatus132(double zonesStatus132) {
		ZonesStatus132 = zonesStatus132;
	}
	public double getZonesManualFlag132() {
		return ZonesManualFlag132;
	}
	public void setZonesManualFlag132(double zonesManualFlag132) {
		ZonesManualFlag132 = zonesManualFlag132;
	}
	public double getZonesFeedbackState132() {
		return ZonesFeedbackState132;
	}
	public void setZonesFeedbackState132(double zonesFeedbackState132) {
		ZonesFeedbackState132 = zonesFeedbackState132;
	}
	public double getZonesAlarmState132() {
		return ZonesAlarmState132;
	}
	public void setZonesAlarmState132(double zonesAlarmState132) {
		ZonesAlarmState132 = zonesAlarmState132;
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
	public double getBreakerPresentPanel0L() {
		return BreakerPresentPanel0L;
	}
	public void setBreakerPresentPanel0L(double breakerPresentPanel0L) {
		BreakerPresentPanel0L = breakerPresentPanel0L;
	}
	public double getBreakerPresentPanel0R() {
		return BreakerPresentPanel0R;
	}
	public void setBreakerPresentPanel0R(double breakerPresentPanel0R) {
		BreakerPresentPanel0R = breakerPresentPanel0R;
	}
	public double getBreakerPresentPanel1L() {
		return BreakerPresentPanel1L;
	}
	public void setBreakerPresentPanel1L(double breakerPresentPanel1L) {
		BreakerPresentPanel1L = breakerPresentPanel1L;
	}
	public double getBreakerPresentPanel1R() {
		return BreakerPresentPanel1R;
	}
	public void setBreakerPresentPanel1R(double breakerPresentPanel1R) {
		BreakerPresentPanel1R = breakerPresentPanel1R;
	}
	public double getBreakerPresentPanel2L() {
		return BreakerPresentPanel2L;
	}
	public void setBreakerPresentPanel2L(double breakerPresentPanel2L) {
		BreakerPresentPanel2L = breakerPresentPanel2L;
	}
	public double getBreakerPresentPanel2R() {
		return BreakerPresentPanel2R;
	}
	public void setBreakerPresentPanel2R(double breakerPresentPanel2R) {
		BreakerPresentPanel2R = breakerPresentPanel2R;
	}
	public double getNonRespondingBreakersPanel0L() {
		return NonRespondingBreakersPanel0L;
	}
	public void setNonRespondingBreakersPanel0L(double nonRespondingBreakersPanel0L) {
		NonRespondingBreakersPanel0L = nonRespondingBreakersPanel0L;
	}
	public double getNonRespondingBreakersPanel0R() {
		return NonRespondingBreakersPanel0R;
	}
	public void setNonRespondingBreakersPanel0R(double nonRespondingBreakersPanel0R) {
		NonRespondingBreakersPanel0R = nonRespondingBreakersPanel0R;
	}
	public double getNonRespondingBreakersPanel1L() {
		return NonRespondingBreakersPanel1L;
	}
	public void setNonRespondingBreakersPanel1L(double nonRespondingBreakersPanel1L) {
		NonRespondingBreakersPanel1L = nonRespondingBreakersPanel1L;
	}
	public double getNonRespondingBreakersPanel1R() {
		return NonRespondingBreakersPanel1R;
	}
	public void setNonRespondingBreakersPanel1R(double nonRespondingBreakersPanel1R) {
		NonRespondingBreakersPanel1R = nonRespondingBreakersPanel1R;
	}
	public double getNonRespondingBreakersPanel2L() {
		return NonRespondingBreakersPanel2L;
	}
	public void setNonRespondingBreakersPanel2L(double nonRespondingBreakersPanel2L) {
		NonRespondingBreakersPanel2L = nonRespondingBreakersPanel2L;
	}
	public double getNonRespondingBreakersPanel2R() {
		return NonRespondingBreakersPanel2R;
	}
	public void setNonRespondingBreakersPanel2R(double nonRespondingBreakersPanel2R) {
		NonRespondingBreakersPanel2R = nonRespondingBreakersPanel2R;
	}
	public double getScheduleStatus116() {
		return ScheduleStatus116;
	}
	public void setScheduleStatus116(double scheduleStatus116) {
		ScheduleStatus116 = scheduleStatus116;
	}
	public double getSchedule1PeriodStatus124() {
		return Schedule1PeriodStatus124;
	}
	public void setSchedule1PeriodStatus124(double schedule1PeriodStatus124) {
		Schedule1PeriodStatus124 = schedule1PeriodStatus124;
	}
	
	
}
