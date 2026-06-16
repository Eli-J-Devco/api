package com.nwm.api.entities.mobile.device;

public class InverterFailureEntity {
    private int id;
    private String deviceName;
    private String message;
    private String errorLevel;
    private String siteName;
    private String opened;
    private int secondsAgo;

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String _deviceName) {
        this.deviceName = _deviceName;
    }

    public int getSecondsAgo() {
        return this.secondsAgo;
    }

    public void setSecondsAgo(int _secondsAgo) {
        this.secondsAgo = _secondsAgo;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String _message) {
        this.message = _message;
    }

    public String getErrorLevel() {
        return this.errorLevel;
    }

    public void setErrorLevel(String _errorLevel) {
        this.errorLevel = _errorLevel;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String _siteName) {
        this.siteName = _siteName;
    }

    public String getOpened() {
        return this.opened;
    }

    public void setOpened(String _opened) {
        this.opened = _opened;
    }
}
