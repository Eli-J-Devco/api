/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

/**
 * Response wrapper for Incident History list with pagination
 */
public class IncidentHistoryResponseEntity {
    private List<IncidentHistoryEntity> incidents;
    private Integer totalCount;

    public IncidentHistoryResponseEntity() {
    }

    public IncidentHistoryResponseEntity(List<IncidentHistoryEntity> incidents, Integer totalCount) {
        this.incidents = incidents;
        this.totalCount = totalCount;
    }

    public List<IncidentHistoryEntity> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<IncidentHistoryEntity> incidents) {
        this.incidents = incidents;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
