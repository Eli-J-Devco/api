package com.nwm.api.entities.mobile.common;

public class BaseDto {
    private int userId;
    private int isSupperAdmin = 0;
	private int limit;
	private int offset;

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
