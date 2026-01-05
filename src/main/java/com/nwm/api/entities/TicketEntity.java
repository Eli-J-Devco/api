/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TicketEntity {
    private Integer ticket_id;
    private Integer alert_id;
    private Integer site_id;
    private Integer device_category_id;
    private Integer category_id;
    private Integer severity_id;
    private Integer assignee_id;
    private Integer created_by;
    private Integer status_id;
    private LocalDateTime opened;
    private LocalDate due_date;
    private String description;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Integer getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(Integer ticket_id) {
        this.ticket_id = ticket_id;
    }

    public Integer getAlert_id() {
        return alert_id;
    }

    public void setAlert_id(Integer alert_id) {
        this.alert_id = alert_id;
    }

    public Integer getSite_id() {
        return site_id;
    }

    public void setSite_id(Integer site_id) {
        this.site_id = site_id;
    }

    public Integer getDevice_category_id() {
        return device_category_id;
    }

    public void setDevice_category_id(Integer device_category_id) {
        this.device_category_id = device_category_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getSeverity_id() {
        return severity_id;
    }

    public void setSeverity_id(Integer severity_id) {
        this.severity_id = severity_id;
    }

    public Integer getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(Integer assignee_id) {
        this.assignee_id = assignee_id;
    }

    public Integer getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Integer created_by) {
        this.created_by = created_by;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public LocalDateTime getOpened() {
        return opened;
    }

    public void setOpened(LocalDateTime opened) {
        this.opened = opened;
    }

    public LocalDate getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "ticket_id=" + ticket_id +
                ", alert_id=" + alert_id +
                ", site_id=" + site_id +
                ", device_category_id=" + device_category_id +
                ", category_id=" + category_id +
                ", severity_id=" + severity_id +
                ", assignee_id=" + assignee_id +
                ", created_by=" + created_by +
                ", status_id=" + status_id +
                ", opened=" + opened +
                ", due_date=" + due_date +
                ", description='" + description + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
