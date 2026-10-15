/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class IncidenHistoryCategoryDTO {
	private Integer id;
	private String name;
	private List<IncidenHistoryEventDTO> events;
	
	public IncidenHistoryCategoryDTO(Integer id, String name, List<IncidenHistoryEventDTO> events) {
		this.id = id;
		this.name = name;
		this.events = events;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<IncidenHistoryEventDTO> getEvents() {
		return events;
	}
	public void setEvents(List<IncidenHistoryEventDTO> events) {
		this.events = events;
	}
}
