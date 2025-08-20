/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.time.ZonedDateTime;
import java.util.List;

public class AuditLog {
	private ZonedDateTime dateTime;
	private String modifiedBy;
	private LogOperationEnum operation;
	private List<LogDifference> changes;
	
	public AuditLog() {}
	
	public AuditLog(ZonedDateTime dateTime, String modifiedBy, LogOperationEnum operation, List<LogDifference> changes) {
		this.dateTime = dateTime;
		this.modifiedBy = modifiedBy;
		this.operation = operation;
		this.changes = changes;
	}
	
	public ZonedDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(ZonedDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public LogOperationEnum getOperation() {
		return operation;
	}
	public void setOperation(LogOperationEnum operation) {
		this.operation = operation;
	}
	public List<LogDifference> getChanges() {
		return changes;
	}
	public void setChanges(List<LogDifference> changes) {
		this.changes = changes;
	}
}
