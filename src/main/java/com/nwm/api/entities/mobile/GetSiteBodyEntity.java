package com.nwm.api.entities.mobile;

public class GetSiteBodyEntity {
	private int userId;
	private int isSupperAdmin = 0;
	private int limit = 20;
	private int offset = 0;
	private String siteStatus = "all";

	public String getSiteStatus() {
		return this.siteStatus;
	}

	public void setSiteStatus(String _siteStatus) {
		this.siteStatus = _siteStatus;
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
}
