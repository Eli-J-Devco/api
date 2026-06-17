package com.nwm.api.entities.mobile.alert;

import java.util.List;

import com.nwm.api.entities.mobile.common.BaseDto;

public class GetAlertsDto extends BaseDto {
    private int priorityType;
    private String startDate;
    private String endDate;
    private List<Integer> alertTypes;
    private boolean state;
    private boolean acknowledged;
    private boolean status = true;

    public GetAlertsDto() {

    }

    public GetAlertsDto(BaseDto baseDto) {
        super(baseDto);
    }

    public GetAlertsDto(int userId, int isSupperAdmin, String startDate, String endDate, boolean satus) {
        this.setUserId(userId);
        this.setIsSupperAdmin(isSupperAdmin);
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = satus;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean _status) {
        this.status = _status;
    }

    public boolean getAcknowledged() {
        return this.acknowledged;
    }

    public void setAcknowledged(boolean _acknowledged) {
        this.acknowledged = _acknowledged;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean _state) {
        this.state = _state;
    }

    public List<Integer> getAlertTypes() {
        return this.alertTypes;
    }

    public void setAlertTypes(List<Integer> _types) {
        this.alertTypes = _types;
    }

    public int getPriorityType() {
        return this.priorityType;
    }

    public void setPriorityType(int _type) {
        this.priorityType = _type;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void getEndDate(String _date) {
        this.endDate = _date;
    }

    public void getStartDate(String _date) {
        this.startDate = _date;
    }
}
