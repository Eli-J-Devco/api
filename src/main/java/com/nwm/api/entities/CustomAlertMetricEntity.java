package com.nwm.api.entities;

public class CustomAlertMetricEntity {
    private int id;
    private String name;
    private int id_device_type;
    private int id_device_group;
    private String field_data_name;
    private  int is_delete;

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

    public int getId_device_type() {
        return id_device_type;
    }

    public void setId_device_type(int id_device_type) {
        this.id_device_type = id_device_type;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
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
}
