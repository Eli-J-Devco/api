/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

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
	
}
