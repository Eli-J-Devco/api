package com.nwm.api.entities.mobile.device;

public class GetYieldDto {
    private int deviceId;
    private String dataTable;
    private String timeZone;

    public GetYieldDto(){}
    
    public GetYieldDto(int deviceId, String dataTable, String timeZone) {
        this.deviceId = deviceId;
        this.dataTable = dataTable;
        this.timeZone = timeZone;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
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
