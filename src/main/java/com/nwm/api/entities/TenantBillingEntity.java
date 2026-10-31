/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.List;
import java.util.Map;

public class TenantBillingEntity {

	private int id_site;
	private int id_device;
	private String start_date;
	private String end_date;
	private String keyword;
	private String status;
	private String sort_column;
	private int limit;
	private int offset;

	// filled by the service, not by the client
	private String timezone_offset;
	private List<Map<String, Object>> devices;

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

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSort_column() {
		return sort_column;
	}

	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getTimezone_offset() {
		return timezone_offset;
	}

	public void setTimezone_offset(String timezone_offset) {
		this.timezone_offset = timezone_offset;
	}

	public List<Map<String, Object>> getDevices() {
		return devices;
	}

	public void setDevices(List<Map<String, Object>> devices) {
		this.devices = devices;
	}
}
