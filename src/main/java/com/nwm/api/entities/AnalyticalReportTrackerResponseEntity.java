/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.ArrayList;
import java.util.List;

public class AnalyticalReportTrackerResponseEntity {

    private int id_site;
    private String site_name;
    private Integer cadence;
    private String timezone_value;
    private Integer data_send_time;
	private String hash_id;
    private String domain;
    private String start_date;
    private String end_date;
    private String start_date_base_on_cadence;

    // Production Report - Page 3
    private List<ClientMonthlyDateEntity> productionReportList = new ArrayList<>();
    private Double totalActualGeneration;
    private Double totalExpectedGeneration;
    private Double totalActualExpected;
    private Double poaIrradiance;

    // Generation Summary - Page 1
    private List<ClientMonthlyDateEntity> generationSummaryList = new ArrayList<>();
    private Double totalActual;
    private Double totalExpected;
    private Double actualExpected;
    private Double siteAvailability;
    private Double finalScore;
    private String finalScoreGrade;
    private String finalScoreLabel;

    // Inverter - Page 4
    private List<PerformanceDataChartItemEntity> inverterDataList = new ArrayList<>();

    // Portfolio Tracker - Page 2
    private List<PortfolioAnalyticalReportTrackerEntity> portfolioTrackerList = new ArrayList<>();
    private Integer noProductionCount = 0;
    private Integer noCommCount = 0;
    private Integer lowProductionCount = 0;
    private Integer normalCount = 0;


    public AnalyticalReportTrackerResponseEntity() {
    }

    public AnalyticalReportTrackerResponseEntity(AnalyticalReportTrackerDTO other) {
        this.id_site = other.getId_site();
        this.site_name = other.getSite_name();
        this.cadence = other.getCadence();
    }


    public int getId_site() {
        return id_site;
    }

    public void setId_site(int id_site) {
        this.id_site = id_site;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public Integer getCadence() {
        return cadence;
    }

    public void setCadence(Integer cadence) {
        this.cadence = cadence;
    }

    public String getTimezone_value() {
        return timezone_value;
    }

    public void setTimezone_value(String timezone_value) {
        this.timezone_value = timezone_value;
    }

    public List<ClientMonthlyDateEntity> getProductionReportList() {
        return productionReportList;
    }

    public void setProductionReportList(
            List<ClientMonthlyDateEntity> productionReportList) {
        this.productionReportList = productionReportList;
    }

    public Double getTotalActualGeneration() {
        return totalActualGeneration;
    }

    public void setTotalActualGeneration(Double totalActualGeneration) {
        this.totalActualGeneration = totalActualGeneration;
    }

    public Double getTotalExpectedGeneration() {
        return totalExpectedGeneration;
    }

    public void setTotalExpectedGeneration(Double totalExpectedGeneration) {
        this.totalExpectedGeneration = totalExpectedGeneration;
    }

    public Double getTotalActualExpected() {
        return totalActualExpected;
    }

    public void setTotalActualExpected(Double totalActualExpected) {
        this.totalActualExpected = totalActualExpected;
    }

    public Double getPoaIrradiance() {
        return poaIrradiance;
    }

    public void setPoaIrradiance(Double poaIrradiance) {
        this.poaIrradiance = poaIrradiance;
    }

    public List<ClientMonthlyDateEntity> getGenerationSummaryList() {
        return generationSummaryList;
    }

    public void setGenerationSummaryList(
            List<ClientMonthlyDateEntity> generationSummaryList) {
        this.generationSummaryList = generationSummaryList;
    }

    public Double getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(Double totalActual) {
        this.totalActual = totalActual;
    }

    public Double getTotalExpected() {
        return totalExpected;
    }

    public void setTotalExpected(Double totalExpected) {
        this.totalExpected = totalExpected;
    }

    public Double getActualExpected() {
        return actualExpected;
    }

    public void setActualExpected(Double actualExpected) {
        this.actualExpected = actualExpected;
    }

    public Double getSiteAvailability() {
        return siteAvailability;
    }

    public void setSiteAvailability(Double siteAvailability) {
        this.siteAvailability = siteAvailability;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public String getFinalScoreGrade() { return finalScoreGrade; }
    public void setFinalScoreGrade(String finalScoreGrade) { this.finalScoreGrade = finalScoreGrade; }
    public String getFinalScoreLabel() { return finalScoreLabel; }
    public void setFinalScoreLabel(String finalScoreLabel) { this.finalScoreLabel = finalScoreLabel; }

    public List<PerformanceDataChartItemEntity> getInverterDataList() {
        return inverterDataList;
    }

    public void setInverterDataList(
            List<PerformanceDataChartItemEntity> inverterDataList) {
        this.inverterDataList = inverterDataList;
    }

    public List<PortfolioAnalyticalReportTrackerEntity> getPortfolioTrackerList() {
        return portfolioTrackerList;
    }

    public void setPortfolioTrackerList(
            List<PortfolioAnalyticalReportTrackerEntity> portfolioTrackerList) {
        this.portfolioTrackerList = portfolioTrackerList;
    }

    public Integer getNoProductionCount() {
        return noProductionCount;
    }

    public void setNoProductionCount(Integer noProductionCount) {
        this.noProductionCount = noProductionCount;
    }

    public Integer getNoCommCount() {
        return noCommCount;
    }

    public void setNoCommCount(Integer noCommCount) {
        this.noCommCount = noCommCount;
    }

    public Integer getLowProductionCount() {
        return lowProductionCount;
    }

    public void setLowProductionCount(Integer lowProductionCount) {
        this.lowProductionCount = lowProductionCount;
    }

    public Integer getNormalCount() {
        return normalCount;
    }

    public void setNormalCount(Integer normalCount) {
        this.normalCount = normalCount;
    }

	public Integer getData_send_time() {
		return data_send_time;
	}

	public void setData_send_time(Integer data_send_time) {
		this.data_send_time = data_send_time;
	}

	public String getHash_id() {
		return hash_id;
	}

	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getStart_date_base_on_cadence() {
		return start_date_base_on_cadence;
	}

	public void setStart_date_base_on_cadence(String start_date_base_on_cadence) {
		this.start_date_base_on_cadence = start_date_base_on_cadence;
	}
	
}
