package com.nwm.api.entities.mobile.device;

public class InverterAvailabilityEntity {
    private int id;
    private int secondsAgo;
    private String siteName;
    private int totalInverter;
    private int inverterOnline;
    private String deviceGroupName;

    public int getId() {
		return this.id;
	}
	
	public void setId(int _id) {
		this.id = _id;
	}

    public String getDeviceGroupName() {
		return this.deviceGroupName;
	}
	
	public void setDeviceGroupName(String deviceGroupName) {
		this.deviceGroupName = deviceGroupName;
	}

    public String getSiteName() {
		return this.siteName;
	}
	
	public void setSiteName(String _siteName) {
		this.siteName = _siteName;
	}

    public int getSecondsAgo() {
        return this.secondsAgo;
    }

    public void setSecondsAgo(int _secondsAgo) {
        this.secondsAgo = _secondsAgo;
    }

    public int getTotalInverter() {
        return this.totalInverter;
    }

    public void setTotalInverter(int totalInverter) {
        this.totalInverter = totalInverter;
    }
    public int getInverterOnline() {
        return this.inverterOnline;
    }

    public void setInverterOnline(int inverterOnline) {
        this.inverterOnline = inverterOnline;
    }
}
