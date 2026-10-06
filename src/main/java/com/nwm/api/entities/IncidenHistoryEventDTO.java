/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class IncidenHistoryEventDTO {
	private Integer id;
	private List<IncidentHistoryEntity> incidents;
	
	public IncidenHistoryEventDTO(Integer id, List<IncidentHistoryEntity> incidents) {
		this.id = id;
		this.incidents = incidents;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<IncidentHistoryEntity> getIncidents() {
		return incidents;
	}
	public void setIncidents(List<IncidentHistoryEntity> incidents) {
		this.incidents = incidents;
	}
}
