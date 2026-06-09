package com.nwm.api.entities.mobile.alert;

public class AlertSummaryEntity {
    private int id;
    private String title;
    private Integer value;

    public int getId() {
        return this.id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer _vlaue) {
        this.value = _vlaue;
    }

    public void setTitle(String _title) {
        this.title = _title;
    }
}
