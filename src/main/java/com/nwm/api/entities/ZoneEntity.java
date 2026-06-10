/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ZoneEntity{
	
	private int id;
	private String name;
	private String text;
	private int active;
	private int limit;
	private int offset;
	private int totalRecord;
	private String order_by;
	private String sort_column;
	private String keyword;
	private int screen_mode;
	private List listDataMaps;
	private int status = 1;
	private int is_delete = 0;
	private String field_0L;
	private String field_0R;
	private String field_0L_verify;
	private String field_0R_verify;
	private int menu_order = 0;
	private int id_site;
	private String devicename;
	private int id_device;
	private int id_device_group;
	private String zone_field;
	private int bit_position;
	private int bit_map;
	private List dataZoneVirtual;
	private String datatablename;
	
	
	
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public List getDataZoneVirtual() {
		return dataZoneVirtual;
	}
	public void setDataZoneVirtual(List dataZoneVirtual) {
		this.dataZoneVirtual = dataZoneVirtual;
	}
	public int getBit_map() {
		return bit_map;
	}
	public void setBit_map(int bit_map) {
		this.bit_map = bit_map;
	}
	public String getZone_field() {
		return zone_field;
	}
	public void setZone_field(String zone_field) {
		this.zone_field = zone_field;
	}
	public int getBit_position() {
		return bit_position;
	}
	public void setBit_position(int bit_position) {
		this.bit_position = bit_position;
	}
	public int getId_device_group() {
		return id_device_group;
	}
	public void setId_device_group(int id_device_group) {
		this.id_device_group = id_device_group;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public String getField_0L() {
		return field_0L;
	}
	public void setField_0L(String field_0l) {
		field_0L = field_0l;
	}
	public String getField_0R() {
		return field_0R;
	}
	public void setField_0R(String field_0r) {
		field_0R = field_0r;
	}
	public String getField_0L_verify() {
		return field_0L_verify;
	}
	public void setField_0L_verify(String field_0l_verify) {
		field_0L_verify = field_0l_verify;
	}
	public String getField_0R_verify() {
		return field_0R_verify;
	}
	public void setField_0R_verify(String field_0r_verify) {
		field_0R_verify = field_0r_verify;
	}
	public int getMenu_order() {
		return menu_order;
	}
	public void setMenu_order(int menu_order) {
		this.menu_order = menu_order;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
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
	public int getScreen_mode() {
		return screen_mode;
	}
	public void setScreen_mode(int screen_mode) {
		this.screen_mode = screen_mode;
	}
	public List getListDataMaps() {
		return listDataMaps;
	}
	public void setListDataMaps(List listDataMaps) {
		this.listDataMaps = listDataMaps;
	}
	
	
	
}
