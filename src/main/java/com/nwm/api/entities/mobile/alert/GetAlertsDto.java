package com.nwm.api.entities.mobile.alert;

public class GetAlertsDto {
    private String userId;
    private Integer isSupperAdmin;
    private int limit = 20;
    private int offset = 0;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String _userId) {
        this.userId = _userId;
    }

    public Integer getIsSupperAdmin() {
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
}
