package com.nwm.api.entities.mobile.alert;

public class GetAlertsDto {
    private int userId;
    private int isSupperAdmin;
    private int priorityType;
    private int limit = 20;
    private int offset = 0;
    private String startDate;
    private String endDate;
    private int type;
    private boolean state;
    private boolean acknowledged;
    private boolean status;

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
