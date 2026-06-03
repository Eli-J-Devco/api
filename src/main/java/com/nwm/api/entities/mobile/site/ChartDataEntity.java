package com.nwm.api.entities.mobile.site;

public class ChartDataEntity {
    private int id;
    private String time;
    private Double power;

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

    public Double getPower() {
        return this.power;
    }

    public void setPower(Double _power) {
        this.power = _power;
    }
}
