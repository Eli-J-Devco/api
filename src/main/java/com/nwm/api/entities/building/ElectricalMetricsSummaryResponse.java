package com.nwm.api.entities.building;

public class ElectricalMetricsSummaryResponse {
    private Double avgLoadFactor;
    private Double avgEui;
    private Integer totalPQEvents;
    private Double PQDirectionSplit;

    public ElectricalMetricsSummaryResponse() {
    }

    public ElectricalMetricsSummaryResponse(Double avgLoadFactor, Double avgEui, Integer totalPQEvents, Double PQDirectionSplit) {
        this.avgLoadFactor = avgLoadFactor;
        this.avgEui = avgEui;
        this.totalPQEvents = totalPQEvents;
        this.PQDirectionSplit = PQDirectionSplit;
    }

    public Double getAvgLoadFactor() {
        return avgLoadFactor;
    }

    public void setAvgLoadFactor(Double avgLoadFactor) {
        this.avgLoadFactor = avgLoadFactor;
    }

    public Double getAvgEui() {
        return avgEui;
    }

    public void setAvgEui(Double avgEui) {
        this.avgEui = avgEui;
    }

    public Integer getTotalPQEvents() {
        return totalPQEvents;
    }

    public void setTotalPQEvents(Integer totalPQEvents) {
        this.totalPQEvents = totalPQEvents;
    }

    public Double getPQDirectionSplit() {
        return PQDirectionSplit;
    }

    public void setPQDirectionSplit(Double PQDirectionSplit) {
        this.PQDirectionSplit = PQDirectionSplit;
    }
}
