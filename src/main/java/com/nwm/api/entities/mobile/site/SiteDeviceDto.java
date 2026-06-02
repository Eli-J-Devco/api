package com.nwm.api.entities.mobile.site;

public class SiteDeviceDto {
    private int deviceId;
    private int deviceType;
    private String datatablename;
    private boolean isExcludedMeter;
    private int reversePoa;

    public int getDeviceId() {
        return this.deviceId;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceId(int _deviceId) {
        this.deviceId = _deviceId;
    }

    public void setDeviceType(int _deviceType) {
        this.deviceType = _deviceType;
    }

    public String getDatatablename() {
        return this.datatablename;
    }

    public void setDatatablename(String _datatablename) {
        this.datatablename = _datatablename;
    }

    public boolean getIsExcludedMeter() {
        return this.isExcludedMeter;
    }

    public void setExcludedMeter(boolean _isExcludedMeter) {
        this.isExcludedMeter = _isExcludedMeter;
    }

    public int getIsReversePoa() {
        return this.reversePoa;
    }

    public void setReversePoa(int _reversePoa) {
        this.reversePoa = _reversePoa;
    }
}
