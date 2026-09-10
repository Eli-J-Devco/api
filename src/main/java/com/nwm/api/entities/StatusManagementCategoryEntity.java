/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

/** Request/response model for the complete Status Management tab. */
public class StatusManagementCategoryEntity {
	private Integer id;
	private String name;
	private Boolean archived;
	private String status;
	private String event;
	private Integer eventId;
	private String statusNumber;
	private String updated;
	private String note;
	private String admin;

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public Boolean getArchived() { return archived; }
	public void setArchived(Boolean archived) { this.archived = archived; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getEvent() { return event; }
	public void setEvent(String event) { this.event = event; }
	public Integer getEventId() { return eventId; }
	public void setEventId(Integer eventId) { this.eventId = eventId; }
	public String getStatusNumber() { return statusNumber; }
	public void setStatusNumber(String statusNumber) { this.statusNumber = statusNumber; }
	public String getUpdated() { return updated; }
	public void setUpdated(String updated) { this.updated = updated; }
	public String getNote() { return note; }
	public void setNote(String note) { this.note = note; }
	public String getAdmin() { return admin; }
	public void setAdmin(String admin) { this.admin = admin; }
}
