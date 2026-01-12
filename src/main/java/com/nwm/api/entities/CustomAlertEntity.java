package com.nwm.api.entities;

import java.util.List;

public class CustomAlertEntity {
    private int id;
    private int id_site;
    private int id_device_group;
    private int id_device;
    private int id_metric;
    private int condition;
    private int threshold;
    private int compare_to;
    private String time_from;
    private String time_to;
    private int level;
    private int notify_email;
    private int notify_web;
    private String alert_email;
    private int status = 1;
    private int is_delete = 0;
    private List<Integer> ids_site;
    private List<Integer> ids_device;
    private String field_data_name;
    private String datatablename;
    private String time_zone_offset;
    private String custom_alert_tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_site() {
        return id_site;
    }

    public void setId_site(int id_site) {
        this.id_site = id_site;
    }

    public int getId_device() {
        return id_device;
    }

    public void setId_device(int id_device) {
        this.id_device = id_device;
    }

    public int getId_metric() {
        return id_metric;
    }

    public void setId_metric(int id_metric) {
        this.id_metric = id_metric;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNotify_email() {
        return notify_email;
    }

    public void setNotify_email(int notify_email) {
        this.notify_email = notify_email;
    }

    public int getNotify_web() {
        return notify_web;
    }

    public void setNotify_web(int notify_web) {
        this.notify_web = notify_web;
    }

    public String getAlert_email() {
        return alert_email;
    }

    public void setAlert_email(String alert_email) {
        this.alert_email = alert_email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public List<Integer> getIds_site() {
        return ids_site;
    }

    public void setIds_site(List<Integer> ids_site) {
        this.ids_site = ids_site;
    }

    public List<Integer> getIds_device() {
        return ids_device;
    }

    public void setIds_device(List<Integer> ids_device) {
        this.ids_device = ids_device;
    }

    public int getCompare_to() {
        return compare_to;
    }

    public void setCompare_to(int compare_to) {
        this.compare_to = compare_to;
    }

    public int getId_device_group() {
        return id_device_group;
    }

    public void setId_device_group(int id_device_group) {
        this.id_device_group = id_device_group;
    }

    public String getField_data_name() {
        return field_data_name;
    }

    public void setField_data_name(String field_data_name) {
        this.field_data_name = field_data_name;
    }

    public String getDatatablename() {
        return datatablename;
    }

    public void setDatatablename(String datatablename) {
        this.datatablename = datatablename;
    }

    public String getTime_zone_offset() {
        return time_zone_offset;
    }

    public void setTime_zone_offset(String time_zone_offset) {
        this.time_zone_offset = time_zone_offset;
    }

    public String getCustom_alert_tag() {
        return custom_alert_tag;
    }

    public void setCustom_alert_tag(String custom_alert_tag) {
        this.custom_alert_tag = custom_alert_tag;
    }
}
