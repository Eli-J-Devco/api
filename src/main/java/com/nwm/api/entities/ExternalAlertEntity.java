/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

/**
 * Entity for external API alert response
 * Contains basic alert information for third-party integrations
 */
public class ExternalAlertEntity {
	private String alert_name;
	private String source;
	private String message;
	private String code;
	private String status;
	private int acknowledgment;

	public String getAlert_name() {
		return alert_name;
	}
	public void setAlert_name(String alert_name) {
		this.alert_name = alert_name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAcknowledgment() {
		return acknowledgment;
	}
	public void setAcknowledgment(int acknowledgment) {
		this.acknowledgment = acknowledgment;
	}
}
