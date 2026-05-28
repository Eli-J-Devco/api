package com.nwm.api.entities.mobile.site;

public class SiteGenerationMobileEntity {
    private double powerToday;
    private double acCapacity;
    private double energyThisMonth;

    public double getPowerToday() {
        return this.powerToday;
    }

    public void setPowerToday(double _powerToday) {
        this.powerToday = _powerToday;
    }

    public double getAcCapacity() {
        return this.acCapacity;
    }

    public void setAcCapacity(double _acCapacity) {
        this.acCapacity = _acCapacity;
    }

    public double getEnergyThisMonth() {
        return this.energyThisMonth;
    }

    public void setEnergyThisMonth(double _energyThisMonth) {
        this.energyThisMonth = _energyThisMonth;
    }
}
