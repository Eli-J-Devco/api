package com.nwm.api.entities.mobile.device;

public class DeviceMobileEntity {
    private int id;
    private String name;
    private String deviceType;
    private String serialNumber;
    private String orderID;
    private int modbusNumber;
    private String dataloggerSN;
    private String lastUpdated;
    private String value;
    private double currentPVPower;
    private double reactiveACPower;
    private double yieldToday;
    private double yieldYesterday;
    private double yieldLastSevenDays;
    private double yieldYTD;
    private String dataTable;
    private String timeZone;

    // --- GETTERS & SETTERS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getModbusNumber() {
        return modbusNumber;
    }

    public void setModbusNumber(int modbusNumber) {
        this.modbusNumber = modbusNumber;
    }

    public String getDataloggerSN() {
        return dataloggerSN;
    }

    public void setDataloggerSN(String dataloggerSN) {
        this.dataloggerSN = dataloggerSN;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getCurrentPVPower() {
        return currentPVPower;
    }

    public void setCurrentPVPower(double currentPVPower) {
        this.currentPVPower = currentPVPower;
    }

    public double getReactiveACPower() {
        return reactiveACPower;
    }

    public void setReactiveACPower(double reactiveACPower) {
        this.reactiveACPower = reactiveACPower;
    }

    public double getYieldToday() {
        return yieldToday;
    }

    public void setYieldToday(double yieldToday) {
        this.yieldToday = yieldToday;
    }

    public double getYieldYesterday() {
        return yieldYesterday;
    }

    public void setYieldYesterday(double yieldYesterday) {
        this.yieldYesterday = yieldYesterday;
    }

    public double getYieldLastSevenDays() {
        return yieldLastSevenDays;
    }

    public void setYieldLastSevenDays(double yieldLastSevenDays) {
        this.yieldLastSevenDays = yieldLastSevenDays;
    }

    public double getYieldYTD() {
        return yieldYTD;
    }

    public void setYieldYTD(double yieldYTD) {
        this.yieldYTD = yieldYTD;
    }

    public String getDataTable() {
        return dataTable;
    }

    public void setDataTable(String dataTable) {
        this.dataTable = dataTable;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
