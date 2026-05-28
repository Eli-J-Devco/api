package com.nwm.api.entities.mobile;

public class SiteSummaryEntity {
    private int totalSite = 0;
    private int sitesWithIssue = 0;

    public int getTotalSite() {
        return this.totalSite;
    }

    public void setTotalSite(int _totalSite) {
        this.totalSite = _totalSite;
    }

    public int getSitesWithIssue() {
        return this.sitesWithIssue;
    }

    public void setSitesWithIssue(int _sitesWithIssue) {
        this.sitesWithIssue = _sitesWithIssue;
    }
}
