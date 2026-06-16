package com.nwm.api.entities.mobile.home;

import java.util.List;

import com.nwm.api.entities.mobile.common.BaseDto;

public class GetSummaryDto extends BaseDto {
    private List idSites;
    
    public List getIdSites() {
        return this.idSites;
    }

    public void setIdSites(List idSites) {
        this.idSites = idSites;
    }
}
