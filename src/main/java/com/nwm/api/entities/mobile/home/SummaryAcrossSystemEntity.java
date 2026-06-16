package com.nwm.api.entities.mobile.home;

public class SummaryAcrossSystemEntity {
    private double generation;
    private int totalAlerts;
    private double inverterAvailability;
    private double fleetLoss;

    public int getTotalAlerts() {
        return this.totalAlerts;
    }

    public void setTotalAlerts(int totalAlerts) {
        this.totalAlerts = totalAlerts;
    }

    public double getGeneration() {
        return this.generation;
    }

    public void setGeneration(double generation) {
        this.generation = generation;
    }
    
    public double getInverterAvailability() {
        return this.inverterAvailability;
    }

    public void setInverterAvailability(double inverterAvailability) {
        this.inverterAvailability = inverterAvailability;
    }

    public double getFleetLoss() {
        return this.fleetLoss;
    }

    public void setFleetLoss(double fleetLoss) {
        this.fleetLoss = fleetLoss;
    }
}
