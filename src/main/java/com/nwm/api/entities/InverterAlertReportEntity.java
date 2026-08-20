/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class InverterAlertReportEntity {
	private Integer id_device;
    private String error_code;
    private String start_date;

    public InverterAlertReportEntity() {
    }

	public Integer getId_device() {
		return id_device;
	}

	public void setId_device(Integer id_device) {
		this.id_device = id_device;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
}
