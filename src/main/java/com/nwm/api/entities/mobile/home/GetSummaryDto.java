package com.nwm.api.entities.mobile.home;

import java.util.List;

public class GetSummaryDto {
    private int userId;
    private List idSites;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int _userId) {
        this.userId = _userId;
    }
    public List getIdSites() {
        return this.idSites;
    }

    public void setIdSites(List idSites) {
        this.idSites = idSites;
    }
}
