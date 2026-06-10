/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


public class BatchJobTableEntity{
	
	private String time;
	private String time_format;
	private int id_device;
	private int error;
	private String datatablename;
	private String view_tablename;
	private String job_tablename;
    private String start_date;
	private String end_date;
	private int status;
	private int id;
	private String current_time;
	private int start_date_time;
	private int end_date_time;
	private int id_error;
	private String devicename;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private int id_device_type;	
	private int id_device_group;
	private int count_item;
	private int count_is_comm;
	private Integer is_no_comm;
    private int duration;
	private String serial_number;
	
	private String updated_by;
	private Integer is_no_production;
	
	
	
	
	public Integer getIs_no_production() {
		return is_no_production;
	}
	public void setIs_no_production(Integer is_no_production) {
		this.is_no_production = is_no_production;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public int getCount_is_comm() {
		return count_is_comm;
	}
	public void setCount_is_comm(int count_is_comm) {
		this.count_is_comm = count_is_comm;
	}
	public int getCount_item() {
		return count_item;
	}
	public void setCount_item(int count_item) {
		this.count_item = count_item;
	}
	public String getJob_tablename() {
		return job_tablename;
	}
	public void setJob_tablename(String job_tablename) {
		this.job_tablename = job_tablename;
	}
	public String getView_tablename() {
		return view_tablename;
	}
	public void setView_tablename(String view_tablename) {
		this.view_tablename = view_tablename;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTime_format() {
		return time_format;
	}
	public void setTime_format(String time_format) {
		this.time_format = time_format;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurrent_time() {
		return current_time;
	}
	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}
	public int getStart_date_time() {
		return start_date_time;
	}
	public void setStart_date_time(int start_date_time) {
		this.start_date_time = start_date_time;
	}
	public int getEnd_date_time() {
		return end_date_time;
	}
	public void setEnd_date_time(int end_date_time) {
		this.end_date_time = end_date_time;
	}
	public int getId_error() {
		return id_error;
	}
	public void setId_error(int id_error) {
		this.id_error = id_error;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public double getNvmActivePower() {
		return nvmActivePower;
	}
	public void setNvmActivePower(double nvmActivePower) {
		this.nvmActivePower = nvmActivePower;
	}
	public double getNvmActiveEnergy() {
		return nvmActiveEnergy;
	}
	public void setNvmActiveEnergy(double nvmActiveEnergy) {
		this.nvmActiveEnergy = nvmActiveEnergy;
	}
	public int getId_device_type() {
		return id_device_type;
	}
	public void setId_device_type(int id_device_type) {
		this.id_device_type = id_device_type;
	}
	public int getId_device_group() {
		return id_device_group;
	}
	public void setId_device_group(int id_device_group) {
		this.id_device_group = id_device_group;
	}

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Integer getIs_no_comm() {
        return is_no_comm;
    }

    public void setIs_no_comm(Integer is_no_comm) {
        this.is_no_comm = is_no_comm;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }
}
