/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.List;

public class ChartToolEntity {
	private int id;
	private int id_customer;
	private int limit;
	private int offset;
	private int totalRecord;
	private String order_by;
	private String sort_by;
	
	private String gallery;
	private int id_site_type;
	private String current_time;
	
	private String filterBy;
	private String start_date;
	private String end_date;
	private int id_site;
	private int id_device;
	private String localization_format;
	private String format_sql_short;
	private String format_sql_long;
	private String format_sql_string_short;
	private String format_sql_string_long;
	private String format_sql_string_mdy;
	private String offset_from;
	private String typeView;
	private String keyword;
	private String sort_column;
	private int screen_mode;
	private List parameter;
	private String hash_id;
	private String view_minute;
	private int data_send_time;
	private int setup_send_time;
	private int start_date_time;
	private int end_date_time;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the id_customer
	 */
	public int getId_customer() {
		return id_customer;
	}
	/**
	 * @param id_customer the id_customer to set
	 */
	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
	}
	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	/**
	 * @return the totalRecord
	 */
	public int getTotalRecord() {
		return totalRecord;
	}
	/**
	 * @param totalRecord the totalRecord to set
	 */
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	/**
	 * @return the order_by
	 */
	public String getOrder_by() {
		return order_by;
	}
	/**
	 * @param order_by the order_by to set
	 */
	public void setOrder_by(String order_by) {
		this.order_by = order_by;
	}
	/**
	 * @return the sort_by
	 */
	public String getSort_by() {
		return sort_by;
	}
	/**
	 * @param sort_by the sort_by to set
	 */
	public void setSort_by(String sort_by) {
		this.sort_by = sort_by;
	}
	/**
	 * @return the gallery
	 */
	public String getGallery() {
		return gallery;
	}
	/**
	 * @param gallery the gallery to set
	 */
	public void setGallery(String gallery) {
		this.gallery = gallery;
	}
	/**
	 * @return the id_site_type
	 */
	public int getId_site_type() {
		return id_site_type;
	}
	/**
	 * @param id_site_type the id_site_type to set
	 */
	public void setId_site_type(int id_site_type) {
		this.id_site_type = id_site_type;
	}
	/**
	 * @return the current_time
	 */
	public String getCurrent_time() {
		return current_time;
	}
	/**
	 * @param current_time the current_time to set
	 */
	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}
	/**
	 * @return the filterBy
	 */
	public String getFilterBy() {
		return filterBy;
	}
	/**
	 * @param filterBy the filterBy to set
	 */
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}
	/**
	 * @return the start_date
	 */
	public String getStart_date() {
		return start_date;
	}
	/**
	 * @param start_date the start_date to set
	 */
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	/**
	 * @return the end_date
	 */
	public String getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return the id_site
	 */
	public int getId_site() {
		return id_site;
	}
	/**
	 * @param id_site the id_site to set
	 */
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	/**
	 * @return the id_device
	 */
	public int getId_device() {
		return id_device;
	}
	/**
	 * @param id_device the id_device to set
	 */
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	/**
	 * @return the localization_format
	 */
	public String getLocalization_format() {
		return localization_format;
	}
	/**
	 * @param localization_format the localization_format to set
	 */
	public void setLocalization_format(String localization_format) {
		this.localization_format = localization_format;
	}
	/**
	 * @return the format_sql_short
	 */
	public String getFormat_sql_short() {
		return format_sql_short;
	}
	/**
	 * @param format_sql_short the format_sql_short to set
	 */
	public void setFormat_sql_short(String format_sql_short) {
		this.format_sql_short = format_sql_short;
	}
	/**
	 * @return the format_sql_long
	 */
	public String getFormat_sql_long() {
		return format_sql_long;
	}
	/**
	 * @param format_sql_long the format_sql_long to set
	 */
	public void setFormat_sql_long(String format_sql_long) {
		this.format_sql_long = format_sql_long;
	}
	/**
	 * @return the format_sql_string_short
	 */
	public String getFormat_sql_string_short() {
		return format_sql_string_short;
	}
	/**
	 * @param format_sql_string_short the format_sql_string_short to set
	 */
	public void setFormat_sql_string_short(String format_sql_string_short) {
		this.format_sql_string_short = format_sql_string_short;
	}
	/**
	 * @return the format_sql_string_long
	 */
	public String getFormat_sql_string_long() {
		return format_sql_string_long;
	}
	/**
	 * @param format_sql_string_long the format_sql_string_long to set
	 */
	public void setFormat_sql_string_long(String format_sql_string_long) {
		this.format_sql_string_long = format_sql_string_long;
	}
	/**
	 * @return the format_sql_string_mdy
	 */
	public String getFormat_sql_string_mdy() {
		return format_sql_string_mdy;
	}
	/**
	 * @param format_sql_string_mdy the format_sql_string_mdy to set
	 */
	public void setFormat_sql_string_mdy(String format_sql_string_mdy) {
		this.format_sql_string_mdy = format_sql_string_mdy;
	}
	/**
	 * @return the offset_from
	 */
	public String getOffset_from() {
		return offset_from;
	}
	/**
	 * @param offset_from the offset_from to set
	 */
	public void setOffset_from(String offset_from) {
		this.offset_from = offset_from;
	}
	/**
	 * @return the typeView
	 */
	public String getTypeView() {
		return typeView;
	}
	/**
	 * @param typeView the typeView to set
	 */
	public void setTypeView(String typeView) {
		this.typeView = typeView;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @return the sort_column
	 */
	public String getSort_column() {
		return sort_column;
	}
	/**
	 * @param sort_column the sort_column to set
	 */
	public void setSort_column(String sort_column) {
		this.sort_column = sort_column;
	}
	/**
	 * @return the screen_mode
	 */
	public int getScreen_mode() {
		return screen_mode;
	}
	/**
	 * @param screen_mode the screen_mode to set
	 */
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	/**
	 * @return the parameter
	 */
	public List getParameter() {
		return parameter;
	}
	/**
	 * @param parameter the parameter to set
	 */
	public void setParameter(List parameter) {
		this.parameter = parameter;
	}
	/**
	 * @return the hash_id
	 */
	public String getHash_id() {
		return hash_id;
	}
	/**
	 * @param hash_id the hash_id to set
	 */
	public void setHash_id(String hash_id) {
		this.hash_id = hash_id;
	}
	/**
	 * @return the view_minute
	 */
	public String getView_minute() {
		return view_minute;
	}
	/**
	 * @param view_minute the view_minute to set
	 */
	public void setView_minute(String view_minute) {
		this.view_minute = view_minute;
	}
	/**
	 * @return the data_send_time
	 */
	public int getData_send_time() {
		return data_send_time;
	}
	/**
	 * @param data_send_time the data_send_time to set
	 */
	public void setData_send_time(int data_send_time) {
		this.data_send_time = data_send_time;
	}
	/**
	 * @return the setup_send_time
	 */
	public int getSetup_send_time() {
		return setup_send_time;
	}
	/**
	 * @param setup_send_time the setup_send_time to set
	 */
	public void setSetup_send_time(int setup_send_time) {
		this.setup_send_time = setup_send_time;
	}
	/**
	 * @return the start_date_time
	 */
	public int getStart_date_time() {
		return start_date_time;
	}
	/**
	 * @param start_date_time the start_date_time to set
	 */
	public void setStart_date_time(int start_date_time) {
		this.start_date_time = start_date_time;
	}
	/**
	 * @return the end_date_time
	 */
	public int getEnd_date_time() {
		return end_date_time;
	}
	/**
	 * @param end_date_time the end_date_time to set
	 */
	public void setEnd_date_time(int end_date_time) {
		this.end_date_time = end_date_time;
	}
	
	
	
}
