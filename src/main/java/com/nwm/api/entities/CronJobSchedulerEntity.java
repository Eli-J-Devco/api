/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.Date;

public class CronJobSchedulerEntity {

	private static final long serialVersionUID = 1L;
	private String job_code;
	private String job_name;
	private Date last_start_time;
	private Date last_end_time;
	private String last_status;
	private String deseription;
	private Date updated_at;

	public String getJob_code() {
		return job_code;
	}

	public void setJob_code(String job_code) {
		this.job_code = job_code;
	}

	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public Date getLast_start_time() {
		return last_start_time;
	}

	public void setLast_start_time(Date last_start_time) {
		this.last_start_time = last_start_time;
	}

	public Date getLast_end_time() {
		return last_end_time;
	}

	public void setLast_end_time(Date last_end_time) {
		this.last_end_time = last_end_time;
	}

	public String getLast_status() {
		return last_status;
	}

	public void setLast_status(String last_status) {
		this.last_status = last_status;
	}

	public String getDeseription() {
		return deseription;
	}

	public void setDeseription(String deseription) {
		this.deseription = deseription;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}