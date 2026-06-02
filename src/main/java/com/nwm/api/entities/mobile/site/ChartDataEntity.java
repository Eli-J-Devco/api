package com.nwm.api.entities.mobile.site;

public class ChartDataEntity {
    private int id;
    private String time;
    private Double chartEnergy;

    public int getId() {
        return this.id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String _time) {
        this.time = _time;
    }

    public Double getChart_energy() {
        return this.chartEnergy;
    }

    public void setChart_energy(Double _chart_energy) {
        this.chartEnergy = _chart_energy;
    }
}
