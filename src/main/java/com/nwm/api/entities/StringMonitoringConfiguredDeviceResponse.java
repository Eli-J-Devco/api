/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class StringMonitoringConfiguredDeviceResponse {

    private int id;

    private String name;

    private Double value;

    private String datatablename;

    private int id_site;

    private Double normal_threshold;

    private Double warning_threshold;

    private Double error_threshold;

    private Double arc_failure_threshold;

    private SiteDTO site;

    private List<StringMonitoringMpptDTO> mppts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDatatablename() {
        return datatablename;
    }

    public void setDatatablename(String datatablename) {
        this.datatablename = datatablename;
    }

    public int getId_site() {
        return id_site;
    }

    public void setId_site(int id_site) {
        this.id_site = id_site;
    }

    public Double getNormal_threshold() {
        return normal_threshold;
    }

    public void setNormal_threshold(Double normal_threshold) {
        this.normal_threshold = normal_threshold;
    }

    public Double getWarning_threshold() {
        return warning_threshold;
    }

    public void setWarning_threshold(Double warning_threshold) {
        this.warning_threshold = warning_threshold;
    }

    public Double getError_threshold() {
        return error_threshold;
    }

    public void setError_threshold(Double error_threshold) {
        this.error_threshold = error_threshold;
    }

    public Double getArc_failure_threshold() {
        return arc_failure_threshold;
    }

    public void setArc_failure_threshold(Double arc_failure_threshold) {
        this.arc_failure_threshold = arc_failure_threshold;
    }

    public SiteDTO getSite() {
        return site;
    }

    public void setSite(SiteDTO site) {
        this.site = site;
    }

    public List<StringMonitoringMpptDTO> getMppts() {
        return mppts;
    }

    public void setMppts(List<StringMonitoringMpptDTO> mppts) {
        this.mppts = mppts;
    }
}
