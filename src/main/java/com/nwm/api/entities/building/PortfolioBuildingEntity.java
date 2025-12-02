/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities.building;

import java.util.List;
import java.util.Map;

public class PortfolioBuildingEntity {
	private String id;
	private int id_employee;
	private int is_supper_admin;
	private String start_date;
	private String end_date;
	private String id_filter;
	private String timezone_value;
	private String domain;
	private String domain_role;
	private String hash_id_site_group;
	private String hash_id_site_sub_group;
	private String sort_column;
	private String order_by;
	private List list_site;
	private List id_sites;
	private int limit;
	private String filterBy;
	
	
	
	
	public String getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
	}
	public int getIs_supper_admin() {
		return is_supper_admin;
	}
	public void setIs_supper_admin(int is_supper_admin) {
		this.is_supper_admin = is_supper_admin;
	}
	public List getId_sites() {
		return id_sites;
	}
	public void setId_sites(List id_sites) {
		this.id_sites = id_sites;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getSort_column() {
		return sort_column;
	}
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}
	public String getOrder_by() {
		return order_by;
	}
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	public String getHash_id_site_group() {
		return hash_id_site_group;
	}
	public void setHash_id_site_group(String hash_id_site_group) {
		this.hash_id_site_group = hash_id_site_group;
	}
	public String getHash_id_site_sub_group() {
		return hash_id_site_sub_group;
	}
	public void setHash_id_site_sub_group(String hash_id_site_sub_group) {
		this.hash_id_site_sub_group = hash_id_site_sub_group;
	}
	public String getDomain_role() {
		return domain_role;
	}
	public void setDomain_role(String domain_role) {
		this.domain_role = domain_role;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public List getList_site() {
		return list_site;
	}
	public void setList_site(List list_site) {
		this.list_site = list_site;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getId_filter() {
		return id_filter;
	}
	public void setId_filter(String id_filter) {
		this.id_filter = id_filter;
	}
	public String getTimezone_value() {
		return timezone_value;
	}
	public void setTimezone_value(String timezone_value) {
		this.timezone_value = timezone_value;
	}
}
