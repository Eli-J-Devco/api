package com.nwm.api.entities.mobile.site;

public class SiteDeviceEntity {
    private int deviceId;
    private String deviceName;
    private boolean isCommunicating = true;
    private int totalAlerts;
    private String deviceType;
    private double ratingAcPower;

    public int getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(int _deviceId) {
        this.deviceId = _deviceId;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String _deviceName) {
        this.deviceName = _deviceName;
    }

    public boolean getIsCommunicating() {
        return this.isCommunicating;
    }

    public void setIsCommunicating(boolean _isCommunicating) {
        this.isCommunicating = _isCommunicating;
    }

    public int getTotalAlerts() {
        return this.totalAlerts;
    }

    public void setTotalAlerts(int _totalAlerts) {
        this.totalAlerts = _totalAlerts;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String _deviceType) {
        this.deviceType = _deviceType;
    }

    public double getRatingAcPower() {
        return this.ratingAcPower;
    }

    public void setRatingAcPower(double _ratingAcPower) {
        this.ratingAcPower = _ratingAcPower;
    }
}
