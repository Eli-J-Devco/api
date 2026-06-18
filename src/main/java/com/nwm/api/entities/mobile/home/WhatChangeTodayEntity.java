package com.nwm.api.entities.mobile.home;

public class WhatChangeTodayEntity {
    private int totalInverterFalures;
    private int totalAlert;
    private int performanceDrops;
    private int ticketsCreated;

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
    
    public int getPerformanceDrops() {
        return this.performanceDrops;
    }

    public void setPerformanceDrops(int performanceDrops) {
        this.performanceDrops = performanceDrops;
    }

    public int getTicketsCreated() {
        return this.ticketsCreated;
    }

    public void setTicketsCreated(int ticketsCreated) {
        this.ticketsCreated = ticketsCreated;
    }

    public int getInverterFailures() {
        return this.totalInverterFalures;
    }

    public void setInverterFailures(int totalInverterFalures) {
        this.totalInverterFalures = totalInverterFalures;
    }
}
