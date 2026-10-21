package com.nwm.api.entities;

import java.time.LocalDate;

public class WeatherAlertEntity {
    private Long id;
    private Long idAlertConfiguration;
    private Integer idSite;
    private String siteName;
    private String weatherEvent;
    private Double reading;
    private Integer severity;
    private Double threshold;
    private String operator;
    private String unit;
    private LocalDate forecastDate;
    private Boolean enabled;

    private WeatherAlertType alertType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAlertConfiguration() {
        return idAlertConfiguration;
    }

    public void setIdAlertConfiguration(Long idAlertConfiguration) {
        this.idAlertConfiguration = idAlertConfiguration;
    }

    public Integer getIdSite() {
        return idSite;
    }

    public void setIdSite(Integer idSite) {
        this.idSite = idSite;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getWeatherEvent() {
        return weatherEvent;
    }

    public void setWeatherEvent(String weatherEvent) {
        this.weatherEvent = weatherEvent;
    }

    public Double getReading() {
        return reading;
    }

    public void setReading(Double reading) {
        this.reading = reading;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(LocalDate forecastDate) {
        this.forecastDate = forecastDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public WeatherAlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(WeatherAlertType alertType) {
        this.alertType = alertType;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
