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

    public ElectricalMetricsResponse() {}

    public ElectricalMetricsResponse(int siteId, String siteName, Double eui, Double vsPortfolioEui, Double vsRegionEui, Double peakDemand, Double loadFactor, Double periodOverPriorPeriod) {
        this.siteId = siteId;
        this.siteName = siteName;
        this.eui = eui;
        this.vsPortfolioEui = vsPortfolioEui;
        this.vsRegionEui = vsRegionEui;
        this.peakDemand = peakDemand;
        this.loadFactor = loadFactor;
        this.periodOverPriorPeriod = periodOverPriorPeriod;
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
}
