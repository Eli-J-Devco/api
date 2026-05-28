package com.nwm.api.entities.mobile.site;

public class GetSiteGenerationDto {
    private int siteId;
    private String currentDate; 
    private int isSupperAdmin;
    private String tableDataReport;

    public int getSiteId() {
        return this.siteId;
    }

    public void setSiteId(int _siteId) {
        this.siteId = _siteId;
    }

    public String getCurrentDate() {
        return this.currentDate;
    }

    public void setCurrentDate(String _currentDate) {
        this.currentDate = _currentDate;
    }

    public int getIsSupperAdmin() {
        return this.isSupperAdmin;
    }

    public void setIsSupperAdmin(int _isSupperAdmin) {
        this.isSupperAdmin = _isSupperAdmin;
    }

    public String getTableDataReport() {
        return this.tableDataReport;
    }

    public void setTableDataReport(String _tableDataReport) {
        this.tableDataReport = _tableDataReport;
    }
}