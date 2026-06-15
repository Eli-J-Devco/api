package com.nwm.api.entities.mobile.device;

import com.nwm.api.entities.mobile.common.BaseDto;

public class GetInverterFailuresDto extends BaseDto {
    private String startDate;
    private String endDate;

    public String getStartDate(){
        return this.startDate;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }
    public String getEndDate(){
        return this.endDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }
}
