package com.nwm.api.entities;

public class WeatherAlertConfigurationEntity {
    private Long id;
    private Long idSite;
    private WeatherAlertType alertType;
    private String weatherParameter;
    private String warningOperator;
    private Double warningThreshold;
    private String criticalOperator;
    private Double criticalThreshold;
    private String unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSite() {
        return idSite;
    }

    public void setIdSite(Long idSite) {
        this.idSite = idSite;
    }

    public WeatherAlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(WeatherAlertType alertType) {
        this.alertType = alertType;
    }

    public String getWeatherParameter() {
        return weatherParameter;
    }

    public void setWeatherParameter(String weatherParameter) {
        this.weatherParameter = weatherParameter;
    }

    public String getWarningOperator() {
        return warningOperator;
    }

    public void setWarningOperator(String warningOperator) {
        this.warningOperator = warningOperator;
    }

    public Double getWarningThreshold() {
        return warningThreshold;
    }

    public void setWarningThreshold(Double warningThreshold) {
        this.warningThreshold = warningThreshold;
    }

    public String getCriticalOperator() {
        return criticalOperator;
    }

    public void setCriticalOperator(String criticalOperator) {
        this.criticalOperator = criticalOperator;
    }

    public Double getCriticalThreshold() {
        return criticalThreshold;
    }

    public void setCriticalThreshold(Double criticalThreshold) {
        this.criticalThreshold = criticalThreshold;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
