/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZoneGraphDateEntity extends DateTimeReportDataEntity {
	
	private double ZonesStatus132;
	private double BreakersPanel0L;
	private double BreakersPanel0R;
	private double BreakersPanel1L;
	private double BreakersPanel1R;
	private double BreakersPanel2L;
	private double BreakersPanel2R;
	private double NonRespondingBreakersPanel0L;
	private double NonRespondingBreakersPanel0R;
	private double NonRespondingBreakersPanel1L;
	private double NonRespondingBreakersPanel1R;
	private double NonRespondingBreakersPanel2L;
	private double NonRespondingBreakersPanel2R;
	private double BreakerPresentPanel0L;
	private double BreakerPresentPanel0R;
	private double BreakerPresentPanel1L;
	private double BreakerPresentPanel1R;
	private double BreakerPresentPanel2L;
	private double BreakerPresentPanel2R;
	private int on_time;
	
	
	

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
	public int getOn_time() {
		return on_time;
	}
	public void setOn_time(int on_time) {
		this.on_time = on_time;
	}
	public double getBreakersPanel0L() {
		return BreakersPanel0L;
	}
	public void setBreakersPanel0L(double breakersPanel0L) {
		BreakersPanel0L = breakersPanel0L;
	}
	public double getZonesStatus132() {
		return ZonesStatus132;
	}
	public void setZonesStatus132(double zonesStatus132) {
		ZonesStatus132 = zonesStatus132;
	}
	
	
}
