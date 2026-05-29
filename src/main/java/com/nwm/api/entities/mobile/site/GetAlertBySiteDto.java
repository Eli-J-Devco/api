package com.nwm.api.entities.mobile.site;

public class GetAlertBySiteDto {
    private int siteId;
    private String siteName;
    private String timeZone;

    public int getSiteId() {
        return this.siteId;
    }

    public void setSiteId(int _siteId) {
        this.siteId = _siteId;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String _siteName) {
        this.siteName = _siteName;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String _timeZone) {
        this.timeZone = _timeZone;
    }
}
