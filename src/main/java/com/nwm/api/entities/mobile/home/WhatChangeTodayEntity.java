package com.nwm.api.entities.mobile.home;

public class WhatChangeTodayEntity {
    private int totalInverterFalures;
    private int totalAlert; 

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
