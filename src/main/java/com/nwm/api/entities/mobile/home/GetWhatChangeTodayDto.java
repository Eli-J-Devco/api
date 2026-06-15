package com.nwm.api.entities.mobile.home;

import com.nwm.api.entities.mobile.common.BaseDto;

public class GetWhatChangeTodayDto extends BaseDto {
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
