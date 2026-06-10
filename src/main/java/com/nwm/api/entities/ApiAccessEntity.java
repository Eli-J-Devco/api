package com.nwm.api.entities;

import java.util.List;

public class ApiAccessEntity {
    private int id;
    private int id_user;
    private String security_key;
    private String security_key_name;
    private int status;
    private Integer rate_limit;
    private Integer rate_limit_per_min;
    private String billing_date;
    private String next_billing_date;
    private String last_used;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getSecurity_key() {
        return security_key;
    }

    public void setSecurity_key(String security_key) {
        this.security_key = security_key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSecurity_key_name() {
        return security_key_name;
    }

    public void setSecurity_key_name(String security_key_name) {
        this.security_key_name = security_key_name;
    }

    public Integer getRate_limit() {
        return rate_limit;
    }

    public void setRate_limit(Integer rate_limit) {
        this.rate_limit = rate_limit;
    }

    public Integer getRate_limit_per_min() {
        return rate_limit_per_min;
    }

    public void setRate_limit_per_min(Integer rate_limit_per_min) {
        this.rate_limit_per_min = rate_limit_per_min;
    }

    public String getBilling_date() {
        return billing_date;
    }

    public void setBilling_date(String billing_date) {
        this.billing_date = billing_date;
    }

    public String getNext_billing_date() {
        return next_billing_date;
    }

    public void setNext_billing_date(String next_billing_date) {
        this.next_billing_date = next_billing_date;
    }

    public String getLast_used() {
        return last_used;
    }

    public void setLast_used(String last_used) {
        this.last_used = last_used;
    }
}
