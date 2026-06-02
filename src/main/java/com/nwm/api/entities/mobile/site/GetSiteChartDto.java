package com.nwm.api.entities.mobile.site;

public class GetSiteChartDto {
    private int siteId;
    private String startDate;
    private String endDate;
    private String tableDataReport;

    public int getSiteId() {
        return this.siteId;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setSiteId(int _siteId) {
        this.siteId = _siteId;
    }

    public void setStartDate(String _startDate) {
        this.startDate = _startDate;
    }

    public void setEndDate(String _endDate) {
        this.endDate = _endDate;
    }

    public String getTableDataReport() {
        return this.tableDataReport;
    }

    public void setTableDataReport(String _tableDataReport) {
        this.tableDataReport = _tableDataReport;
    }   
}
