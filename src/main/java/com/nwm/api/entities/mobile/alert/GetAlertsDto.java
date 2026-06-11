package com.nwm.api.entities.mobile.alert;

import java.util.List;

public class GetAlertsDto {
    private int userId;
    private int isSupperAdmin;
    private int priorityType;
    private int limit = 20;
    private int offset = 0;
    private String startDate;
    private String endDate;
    private List<Integer> alertTypes;
    private boolean state;
    private boolean acknowledged;
    private boolean status;

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
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int _userId) {
        this.userId = _userId;
    }

    public int getIsSupperAdmin() {
        return this.isSupperAdmin;
    }

    public void setIsSupperAdmin(int _isSupperAdmin) {
        this.isSupperAdmin = _isSupperAdmin;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int _limit) {
        this.limit = _limit;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int _offset) {
        this.offset = _offset;
    }

    public int getPriorityType(){
        return this.priorityType;
    }

    public void setPriorityType(int _type){
        this.priorityType = _type;
    }

    public String getStartDate(){
        return this.startDate;
    }
    public String getEndDate(){
        return this.endDate;
    }
    public void getEndDate(String _date){
        this.endDate = _date;
    }

    public void getStartDate(String _date){
        this.startDate = _date;
    }
}
