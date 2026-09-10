/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

/** Event row used internally by Status Management operations. */
public class StatusManagementEventEntity {
	private Integer id;
	private Integer idCategory;
	private String status;
	private String notes;
	private String adminNotes;
	private String updatedBy;
	private String statusNumber;
	private String closingNotes;
	private List<Integer> selectedEvents;

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public Integer getIdCategory() { return idCategory; }
	public void setIdCategory(Integer idCategory) { this.idCategory = idCategory; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }
	public String getAdminNotes() { return adminNotes; }
	public void setAdminNotes(String adminNotes) { this.adminNotes = adminNotes; }
	public String getUpdatedBy() { return updatedBy; }
	public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
	public String getStatusNumber() { return statusNumber; }
	public void setStatusNumber(String statusNumber) { this.statusNumber = statusNumber; }
	public String getClosingNotes() { return closingNotes; }
	public void setClosingNotes(String closingNotes) { this.closingNotes = closingNotes; }
	public List<Integer> getSelectedEvents() { return selectedEvents; }
	public void setSelectedEvents(List<Integer> selectedEvents) { this.selectedEvents = selectedEvents; }
}
