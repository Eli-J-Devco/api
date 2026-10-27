/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

import com.nwm.api.utils.Constants;

public class ElectricalMetricsRequest {
	private Constants.ChartingFilter filterBy;
	private String hashGroupId;
	private String hashSubGroupId;
	private String sortColumn;
	private String orderBy;
	private String domain;
	private String domain_role;

	public Constants.ChartingFilter getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(String filterBy) {
		this.filterBy = Constants.ChartingFilter.fromValue(filterBy);
	}

	public String getHashGroupId() {
		return hashGroupId;
	}

	public void setHashGroupId(String hashGroupId) {
		this.hashGroupId = hashGroupId;
	}

	public String getHashSubGroupId() {
		return hashSubGroupId;
	}

	public void setHashSubGroupId(String hashSubGroupId) {
		this.hashSubGroupId = hashSubGroupId;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain_role() {
		return domain_role;
	}

	public void setDomain_role(String domain_role) {
		this.domain_role = domain_role;
	}
}
