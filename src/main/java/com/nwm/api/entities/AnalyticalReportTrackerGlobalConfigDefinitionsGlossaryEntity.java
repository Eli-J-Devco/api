/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

public class AnalyticalReportTrackerGlobalConfigDefinitionsGlossaryEntity {
	private Integer id;
	private Integer modified_by;
	private String term;
	private String description;

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

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
