package com.nwm.api.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SitesAnalyticsReportEntity {
    private int id;
    private String siteName;
    private String customerName;
    private String html;
    private String fileName;
    private String fileType;
    private String email;
    private String title;

    public SitesAnalyticsReportEntity(int id, String siteName, String customerName, String html, String fileName, String fileType, String email, String title) {
        this.id = id;
        this.siteName = siteName;
        this.customerName = customerName;
        this.html = html;
        this.fileName = fileName;
        this.fileType = fileType;
        this.email = email;
        this.title = title;
    }
}
