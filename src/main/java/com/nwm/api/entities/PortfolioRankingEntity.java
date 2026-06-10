/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.List;

public class PortfolioRankingEntity extends SortEntity {
	private int id;
	private int id_company;
	private String filterBy;
	private String start_date;
	private String end_date;
	private String domain;
	private int limit;
	private int offset;
	private int totalRecord;
	private String sort_by;
	private List dataPortRanking;
	private int total_inv;
	private int active_inv;
	private int total_meter;
	private int active_meter;
	private String last_updated;
	private int is_supper_admin;
	private boolean isUserNW;
	
	
	
	public boolean isUserNW() {
		return isUserNW;
	}
	public void setIsUserNW(boolean isUserNW) {
		this.isUserNW = isUserNW;
	}
	public int getIs_supper_admin() {
		return is_supper_admin;
	}
	public void setIs_supper_admin(int is_supper_admin) {
		this.is_supper_admin = is_supper_admin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_company() {
		return id_company;
	}
	public void setId_company(int id_company) {
		this.id_company = id_company;
	}
	public String getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
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
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public String getSort_by() {
		return sort_by;
	}
	public void setSort_by(String sort_by) {
		this.sort_by = sort_by;
	}
	public List getDataPortRanking() {
		return dataPortRanking;
	}
	public void setDataPortRanking(List dataPortRanking) {
		this.dataPortRanking = dataPortRanking;
	}
	public int getTotal_inv() {
		return total_inv;
	}
	public void setTotal_inv(int total_inv) {
		this.total_inv = total_inv;
	}
	public int getActive_inv() {
		return active_inv;
	}
	public void setActive_inv(int active_inv) {
		this.active_inv = active_inv;
	}
	public int getTotal_meter() {
		return total_meter;
	}
	public void setTotal_meter(int total_meter) {
		this.total_meter = total_meter;
	}
	public int getActive_meter() {
		return active_meter;
	}
	public void setActive_meter(int active_meter) {
		this.active_meter = active_meter;
	}
	public String getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}
	
	
	
	
}
