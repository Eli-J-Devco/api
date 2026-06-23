package com.nwm.api.entities.mobile.home;

import com.nwm.api.entities.mobile.common.BaseDto;

public class GetCriticalSiteSummaryDto extends BaseDto {
    private int siteId;
    private String startDate;
    private String endDate;

    public int getSiteId() {
        return this.siteId;
    }

    public void setSiteId(int _siteId) {
        this.siteId = _siteId;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setStartDate(String _startDate) {
        this.startDate = _startDate;
    }

    public void setEndDate(String _endDate) {
        this.endDate = _endDate;
    }
}