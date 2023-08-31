/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class EmployeeManageEntity{
	
	private int id;
	private String password;
	private String first_name;
	private String last_name;
	private String address;
	private String phone;
	private String email;
	private String birthday;
	private String avatar;
	private int status;
	private int is_delete;
	private String created_date;
	private String updated_date;
	private int limit;
	private int offset;
	private int totalRecord;
	private String file_upload;
	private int screen_mode;
	private String order_by;
	private String sort_column;
	private String keyword;
	private String group_roles;
	private String skype;
	private String fullname;
	private List roles;
	private String role_ids;
	private int manage_site_total;
	private String re_email;
	private List dataSite;
	private String ids_site;
	private String color;
	private int id_role;
	private String text;
	private String label;
	private int value;
	private String table_column;
	private String last_login;
	private String offset_timezone;
	private String page_login;
	private int alert_per_page;
	private String alert_filter;
	private int failed_attempt;
	private int account_locked;
	private String lock_time;
	private String filter;
	private int is_send_email_unblock;
	private String hash_id_user;
	
	
	public String getHash_id_user() {
		return hash_id_user;
	}
	public void setHash_id_user(String hash_id_user) {
		this.hash_id_user = hash_id_user;
	}
	public int getIs_send_email_unblock() {
		return is_send_email_unblock;
	}
	public void setIs_send_email_unblock(int is_send_email_unblock) {
		this.is_send_email_unblock = is_send_email_unblock;
	}
	public int getFailed_attempt() {
		return failed_attempt;
	}
	public void setFailed_attempt(int failed_attempt) {
		this.failed_attempt = failed_attempt;
	}
	public int getAccount_locked() {
		return account_locked;
	}
	public void setAccount_locked(int account_locked) {
		this.account_locked = account_locked;
	}
	public String getLock_time() {
		return lock_time;
	}
	public void setLock_time(String lock_time) {
		this.lock_time = lock_time;
	}
	public String getAlert_filter() {
		return alert_filter;
	}
	public void setAlert_filter(String alert_filter) {
		this.alert_filter = alert_filter;
	}
	public int getAlert_per_page() {
		return alert_per_page;
	}
	public void setAlert_per_page(int alert_per_page) {
		this.alert_per_page = alert_per_page;
	}
	public String getTable_column() {
		return table_column;
	}
	public void setTable_column(String table_column) {
		this.table_column = table_column;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
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
	public String getFile_upload() {
		return file_upload;
	}
	public void setFile_upload(String file_upload) {
		this.file_upload = file_upload;
	}
	public int getScreen_mode() {
		return screen_mode;
	}
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	public String getOrder_by() {
		return order_by;
	}
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	public String getSort_column() {
		return sort_column;
	}
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getGroup_roles() {
		return group_roles;
	}
	public void setGroup_roles(String group_roles) {
		this.group_roles = group_roles;
	}
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public List getRoles() {
		return roles;
	}
	public void setRoles(List roles) {
		this.roles = roles;
	}
	public String getRole_ids() {
		return role_ids;
	}
	public void setRole_ids(String role_ids) {
		this.role_ids = role_ids;
	}
	public int getManage_site_total() {
		return manage_site_total;
	}
	public void setManage_site_total(int manage_site_total) {
		this.manage_site_total = manage_site_total;
	}
	public String getRe_email() {
		return re_email;
	}
	public void setRe_email(String re_email) {
		this.re_email = re_email;
	}
	public List getDataSite() {
		return dataSite;
	}
	public void setDataSite(List dataSite) {
		this.dataSite = dataSite;
	}
	public String getIds_site() {
		return ids_site;
	}
	public void setIds_site(String ids_site) {
		this.ids_site = ids_site;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getId_role() {
		return id_role;
	}
	public void setId_role(int id_role) {
		this.id_role = id_role;
	}
	public String getLast_login() {
		return last_login;
	}
	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}
	public String getOffset_timezone() {
		return offset_timezone;
	}
	public void setOffset_timezone(String offset_timezone) {
		this.offset_timezone = offset_timezone;
	}
	public String getPage_login() {
		return page_login;
	}
	public void setPage_login(String page_login) {
		this.page_login = page_login;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	
	
	
}
