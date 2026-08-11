/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

public class AnalyticalReportTrackerGlobalConfigCurrentStatusEntity {
	private Integer id;
	private Integer modified_by;
	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getModified_by() {
		return modified_by;
	}

	public void setModified_by(Integer modified_by) {
		this.modified_by = modified_by;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
