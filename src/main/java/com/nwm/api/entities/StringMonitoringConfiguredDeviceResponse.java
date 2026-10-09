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

    private Double underperforming_threshold;

    private Double critical_threshold;

    private Double zero_offline_threshold;

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

    public Double getUnderperforming_threshold() {
        return underperforming_threshold;
    }

    public void setUnderperforming_threshold(Double underperforming_threshold) {
        this.underperforming_threshold = underperforming_threshold;
    }

    public Double getCritical_threshold() {
        return critical_threshold;
    }

    public void setCritical_threshold(Double critical_threshold) {
        this.critical_threshold = critical_threshold;
    }

    public Double getZero_offline_threshold() {
        return zero_offline_threshold;
    }

    public void setZero_offline_threshold(Double zero_offline_threshold) {
        this.zero_offline_threshold = zero_offline_threshold;
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
