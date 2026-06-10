/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class WidgetGroupParameterEntity{
	
	private int id;
	private int id_widget_group;
	private int id_group_parameter;
	private int menu_order;
	private String unit;
	private String name;
	private String value;
	private String bg_color;
	private int formula;
	private double totalRecord;
	private List dataDevices;
	private List<String> tablename;
	private String fieldname;
	private int type;
	private String id_device;
	private Double cost;
	private String time_zone;
	
	
	public String getId_device() {
		return id_device;
	}
	public void setId_device(String id_device) {
		this.id_device = id_device;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_widget_group() {
		return id_widget_group;
	}
	public void setId_widget_group(int id_widget_group) {
		this.id_widget_group = id_widget_group;
	}
	public int getId_group_parameter() {
		return id_group_parameter;
	}
	public void setId_group_parameter(int id_group_parameter) {
		this.id_group_parameter = id_group_parameter;
	}
	public int getMenu_order() {
		return menu_order;
	}
	public void setMenu_order(int menu_order) {
		this.menu_order = menu_order;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getBg_color() {
		return bg_color;
	}
	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}
	public int getFormula() {
		return formula;
	}
	public void setFormula(int formula) {
		this.formula = formula;
	}
	public double getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(double totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List getDataDevices() {
		return dataDevices;
	}
	public void setDataDevices(List dataDevices) {
		this.dataDevices = dataDevices;
	}
	public List<String> getTablename() {
		return tablename;
	}
	public void setTablename(List<String> tablename) {
		this.tablename = tablename;
	}
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getTime_zone() {
		return time_zone;
	}
	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}
	
	
}
