package com.nwm.api.entities.mobile.site;

public class ChartDataEntity {
    private String time;
    private Double value;

    public String getTime() {
        return this.time;
    }

    public void setTime(String _time) {
        this.time = _time;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double _value) {
        this.value = _value;
    }
}
