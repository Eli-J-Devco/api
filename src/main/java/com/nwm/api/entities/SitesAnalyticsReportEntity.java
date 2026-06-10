package com.nwm.api.entities;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
