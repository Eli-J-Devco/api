/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

/**
 * Incident History Entity - represents a historical record of platform incidents
 */
public class IncidentHistoryEntity {
    private Integer id;
    private Integer eventId;
    private String eventNumber;
    private Integer categoryId;
    private String categoryName;
    private String status;
    private String statusNumber;
    private String description;
    private String adminNotes;
    private String updatedBy;
    private String actionType;
    private String dateFormatted;
    private String eventCreatedDate;
    private String eventUpdatedDate;
    private String eventClosedDate;
    private Integer durationMinutes;
    private String durationFormatted;
    private String affectedUsers;
    private String historyCreatedDate;
    
    // Filter parameters
    private String searchQuery;
    private Integer limit;
    private Integer offset;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(String eventNumber) {
        this.eventNumber = eventNumber;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusNumber() {
        return statusNumber;
    }

    public void setStatusNumber(String statusNumber) {
        this.statusNumber = statusNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
    }

    public String getEventCreatedDate() {
        return eventCreatedDate;
    }

    public void setEventCreatedDate(String eventCreatedDate) {
        this.eventCreatedDate = eventCreatedDate;
    }

    public String getEventUpdatedDate() {
        return eventUpdatedDate;
    }

    public void setEventUpdatedDate(String eventUpdatedDate) {
        this.eventUpdatedDate = eventUpdatedDate;
    }

    public String getEventClosedDate() {
        return eventClosedDate;
    }

    public void setEventClosedDate(String eventClosedDate) {
        this.eventClosedDate = eventClosedDate;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getDurationFormatted() {
        return durationFormatted;
    }

    public void setDurationFormatted(String durationFormatted) {
        this.durationFormatted = durationFormatted;
    }

    public String getAffectedUsers() {
        return affectedUsers;
    }

    public void setAffectedUsers(String affectedUsers) {
        this.affectedUsers = affectedUsers;
    }

    public String getHistoryCreatedDate() {
        return historyCreatedDate;
    }

    public void setHistoryCreatedDate(String historyCreatedDate) {
        this.historyCreatedDate = historyCreatedDate;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
