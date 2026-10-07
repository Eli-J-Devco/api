package com.nwm.api.entities;

import java.time.LocalDate;

public class WeatherAlertEntity {
    private Long id;
    private Long idAlertConfiguration;
    private Long idSite;
    private String weatherEvent;
    private Double reading;
    private String unit;
    private String threshold;
    private LocalDate forecastDate;
    private Boolean enabled;

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

    public Long getIdSite() {
        return idSite;
    }

    public void setIdSite(Long idSite) {
        this.idSite = idSite;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
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
}
