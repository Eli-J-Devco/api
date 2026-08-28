/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

public class PortfolioAnalyticalReportTrackerEntity {

    private Integer id_device;
    private String devicename;
    private String status;
    private String issue_started;
    private Double low_production_threshold;
    private Double availability;

    public PortfolioAnalyticalReportTrackerEntity() {
    }

    public PortfolioAnalyticalReportTrackerEntity(Integer id_device, String devicename) {
        this.id_device = id_device;
        this.devicename = devicename;
    }

    public Integer getId_device() {
        return id_device;
    }

    public void setId_device(Integer id_device) {
        this.id_device = id_device;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssue_started() {
        return issue_started;
    }

    public void setIssue_started(String issue_started) {
        this.issue_started = issue_started;
    }

    public Double getLow_production_threshold() {
        return low_production_threshold;
    }

    public void setLow_production_threshold(Double low_production_threshold) {
        this.low_production_threshold = low_production_threshold;
    }
    public Double getAvailability() {
        return availability;
    }

    public void setAvailability(Double availability) {
        this.availability = availability;
    }
}