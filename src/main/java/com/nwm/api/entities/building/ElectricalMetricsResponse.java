package com.nwm.api.entities.building;

public class ElectricalMetricsResponse {
    private int siteId;
    private String siteName;
    private Double eui;
    private Double vsPortfolioEui;
    private Double vsRegionEui;
    private Double peakDemand;
    private Double loadFactor;
    private Double periodOverPriorPeriod;
    private Integer totalEvents;
    private Integer sags;
    private Integer swells;
    private Double vsPortfolioEvents;
    private Double vsRegionEvents;
    private Double currentUnbalance;
    private Double voltageUnbalance;
    private Double powerFactor;

    public ElectricalMetricsResponse() {}

    public ElectricalMetricsResponse(int siteId, String siteName, Double eui, Double vsPortfolioEui, Double vsRegionEui, Double peakDemand, Double loadFactor, Double periodOverPriorPeriod, Integer totalEvents, Integer sags, Integer swells, Double vsPortfolioEvents, Double vsRegionEvents, Double currentUnbalance, Double voltageUnbalance, Double powerFactor) {
        this.siteId = siteId;
        this.siteName = siteName;
        this.eui = eui;
        this.vsPortfolioEui = vsPortfolioEui;
        this.vsRegionEui = vsRegionEui;
        this.peakDemand = peakDemand;
        this.loadFactor = loadFactor;
        this.periodOverPriorPeriod = periodOverPriorPeriod;
        this.totalEvents = totalEvents;
        this.sags = sags;
        this.swells = swells;
        this.vsPortfolioEvents = vsPortfolioEvents;
        this.vsRegionEvents = vsRegionEvents;
        this.currentUnbalance = currentUnbalance;
        this.voltageUnbalance = voltageUnbalance;
        this.powerFactor = powerFactor;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Double getEui() {
        return eui;
    }

    public void setEui(Double eui) {
        this.eui = eui;
    }

    public Double getVsPortfolioEui() {
        return vsPortfolioEui;
    }

    public void setVsPortfolioEui(Double vsPortfolioEui) {
        this.vsPortfolioEui = vsPortfolioEui;
    }

    public Double getVsRegionEui() {
        return vsRegionEui;
    }

    public void setVsRegionEui(Double vsRegionEui) {
        this.vsRegionEui = vsRegionEui;
    }

    public Double getPeakDemand() {
        return peakDemand;
    }

    public void setPeakDemand(Double peakDemand) {
        this.peakDemand = peakDemand;
    }

    public Double getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(Double loadFactor) {
        this.loadFactor = loadFactor;
    }

    public Double getPeriodOverPriorPeriod() {
        return periodOverPriorPeriod;
    }

    public void setPeriodOverPriorPeriod(Double periodOverPriorPeriod) {
        this.periodOverPriorPeriod = periodOverPriorPeriod;
    }

    public Integer getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Integer totalEvents) {
        this.totalEvents = totalEvents;
    }

    public Integer getSags() {
        return sags;
    }

    public void setSags(Integer sags) {
        this.sags = sags;
    }

    public Integer getSwells() {
        return swells;
    }

    public void setSwells(Integer swells) {
        this.swells = swells;
    }

    public Double getVsPortfolioEvents() {
        return vsPortfolioEvents;
    }

    public void setVsPortfolioEvents(Double vsPortfolioEvents) {
        this.vsPortfolioEvents = vsPortfolioEvents;
    }

    public Double getVsRegionEvents() {
        return vsRegionEvents;
    }

    public void setVsRegionEvents(Double vsRegionEvents) {
        this.vsRegionEvents = vsRegionEvents;
    }

    public Double getCurrentUnbalance() {
        return currentUnbalance;
    }

    public void setCurrentUnbalance(Double currentUnbalance) {
        this.currentUnbalance = currentUnbalance;
    }

    public Double getVoltageUnbalance() {
        return voltageUnbalance;
    }

    public void setVoltageUnbalance(Double voltageUnbalance) {
        this.voltageUnbalance = voltageUnbalance;
    }

    public Double getPowerFactor() {
        return powerFactor;
    }

    public void setPowerFactor(Double powerFactor) {
        this.powerFactor = powerFactor;
    }
}
