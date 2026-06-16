package com.nwm.api.entities.mobile.home;

public class WhatChangeTodayEntity {
    private int totalInverterFalures;
    private int totalAlert;

    public WhatChangeTodayEntity(){}

    public WhatChangeTodayEntity(int totalInverterFalures, int totalAlert) {
        this.totalAlert = totalAlert;
        this.totalInverterFalures = totalInverterFalures;
    }

    public int getTotalAlert() {
        return this.totalAlert;
    }

    public void setTotalAlert(int totalAlert) {
        this.totalAlert = totalAlert;
    }

    public int getInverterFailures() {
        return this.totalInverterFalures;
    }

    public void setInverterFailures(int totalInverterFalures) {
        this.totalInverterFalures = totalInverterFalures;
    }
}
