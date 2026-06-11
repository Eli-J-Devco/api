package com.nwm.api.entities.mobile.alert;

public class AlertMobileEntity {
    private int id;
    private String message;
    private String errorLevel;
    private String deviceName;
    private String siteName;
    private String deviceType;
    private String priority;
    private String opened;
    private double capacity;
    private int secondsAgo;
    private String description;
    private boolean alertAcknowledged;
    private boolean disableNotification;
    private String lastComm;

    public String getLastComm() {
        return this.lastComm;
    }

    public void setlastComm(String _lastComm) {
        this.lastComm = _lastComm;
    }

    public boolean getAlertAcknowledged() {
        return this.alertAcknowledged;
    }

    public void setAlertAcknowledged(boolean _alertAcknowledged) {
        this.alertAcknowledged = _alertAcknowledged;
    }

    public boolean getDisableNotification() {
        return this.disableNotification;
    }

    public void setDisableNotification(boolean _disableNotification) {
        this.disableNotification = _disableNotification;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

    public int getSecondsAgo() {
        return this.secondsAgo;
    }

    public void setSecondsAgo(int _secondsAgo) {
        this.secondsAgo = _secondsAgo;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public void setCapacity(double _capacity) {
        this.capacity = _capacity;
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

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String _deviceName) {
        this.deviceName = _deviceName;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String _siteName) {
        this.siteName = _siteName;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String _deviceType) {
        this.deviceType = _deviceType;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String _priority) {
        this.priority = _priority;
    }

    public String getOpened() {
        return this.opened;
    }

    public void setOpened(String _opened) {
        this.opened = _opened;
    }
}
