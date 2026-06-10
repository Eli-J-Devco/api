/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

import java.util.List;

public class SiteEnergyFlowEntity extends SortEntity {
	private int id;
	private int id_site;
	private int id_company;
	private int id_country;
	private int id_time_zone;
	private String name;
	private String street;
	
	private String start_date;
	private String end_date;
	private int limit;
	private int offset;
	private int totalRecord;
	
	private double pv_current;
	private double pv_peak;
	private double pv_peak_time;
	private double pv_average;
	
	
	private double water_current;
	private double water_peak;
	private double water_peak_time;
	private double water_average;
	
	private double gas_current;
	private double gas_peak;
	private double gas_peak_time;
	private double gas_average;
	
	private double electric_current;
	private double electric_peak;
	private double electric_peak_time;
	private double electric_average;
	
	private double hvac_current;
	private double hvac_peak;
	private double hvac_peak_time;
	private double hvac_average;
	private List devices; 
	
	private List dataPv; 
	private List dataElectric; 
	private List dataWater; 
	private List dataGas;
	private List dataHvac;
	private List dataLighting;
	private String filterBy;
	private String filterType;
	private String datatablename;
	
	
	
	public String getDatatablename() {
		return datatablename;
	}
	public void setDatatablename(String datatablename) {
		this.datatablename = datatablename;
	}
	public List getDataLighting() {
		return dataLighting;
	}
	public void setDataLighting(List dataLighting) {
		this.dataLighting = dataLighting;
	}
	public String getFilterBy() {
		return filterBy;
	}
	public void setFilterBy(String filterBy) {
		this.filterBy = filterBy;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public List getDataPv() {
		return dataPv;
	}
	public void setDataPv(List dataPv) {
		this.dataPv = dataPv;
	}
	public List getDataElectric() {
		return dataElectric;
	}
	public void setDataElectric(List dataElectric) {
		this.dataElectric = dataElectric;
	}
	public List getDataWater() {
		return dataWater;
	}
	public void setDataWater(List dataWater) {
		this.dataWater = dataWater;
	}
	public List getDataGas() {
		return dataGas;
	}
	public void setDataGas(List dataGas) {
		this.dataGas = dataGas;
	}
	public List getDataHvac() {
		return dataHvac;
	}
	public void setDataHvac(List dataHvac) {
		this.dataHvac = dataHvac;
	}
	public List getDevices() {
		return devices;
	}
	public void setDevices(List devices) {
		this.devices = devices;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
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
	public int getId_country() {
		return id_country;
	}
	public void setId_country(int id_country) {
		this.id_country = id_country;
	}
	public int getId_time_zone() {
		return id_time_zone;
	}
	public void setId_time_zone(int id_time_zone) {
		this.id_time_zone = id_time_zone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
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
	public double getPv_current() {
		return pv_current;
	}
	public void setPv_current(double pv_current) {
		this.pv_current = pv_current;
	}
	public double getPv_peak() {
		return pv_peak;
	}
	public void setPv_peak(double pv_peak) {
		this.pv_peak = pv_peak;
	}
	public double getPv_peak_time() {
		return pv_peak_time;
	}
	public void setPv_peak_time(double pv_peak_time) {
		this.pv_peak_time = pv_peak_time;
	}
	public double getPv_average() {
		return pv_average;
	}
	public void setPv_average(double pv_average) {
		this.pv_average = pv_average;
	}
	public double getWater_current() {
		return water_current;
	}
	public void setWater_current(double water_current) {
		this.water_current = water_current;
	}
	public double getWater_peak() {
		return water_peak;
	}
	public void setWater_peak(double water_peak) {
		this.water_peak = water_peak;
	}
	public double getWater_peak_time() {
		return water_peak_time;
	}
	public void setWater_peak_time(double water_peak_time) {
		this.water_peak_time = water_peak_time;
	}
	public double getWater_average() {
		return water_average;
	}
	public void setWater_average(double water_average) {
		this.water_average = water_average;
	}
	public double getGas_current() {
		return gas_current;
	}
	public void setGas_current(double gas_current) {
		this.gas_current = gas_current;
	}
	public double getGas_peak() {
		return gas_peak;
	}
	public void setGas_peak(double gas_peak) {
		this.gas_peak = gas_peak;
	}
	public double getGas_peak_time() {
		return gas_peak_time;
	}
	public void setGas_peak_time(double gas_peak_time) {
		this.gas_peak_time = gas_peak_time;
	}
	public double getGas_average() {
		return gas_average;
	}
	public void setGas_average(double gas_average) {
		this.gas_average = gas_average;
	}
	public double getElectric_current() {
		return electric_current;
	}
	public void setElectric_current(double electric_current) {
		this.electric_current = electric_current;
	}
	public double getElectric_peak() {
		return electric_peak;
	}
	public void setElectric_peak(double electric_peak) {
		this.electric_peak = electric_peak;
	}
	public double getElectric_peak_time() {
		return electric_peak_time;
	}
	public void setElectric_peak_time(double electric_peak_time) {
		this.electric_peak_time = electric_peak_time;
	}
	public double getElectric_average() {
		return electric_average;
	}
	public void setElectric_average(double electric_average) {
		this.electric_average = electric_average;
	}
	public double getHvac_current() {
		return hvac_current;
	}
	public void setHvac_current(double hvac_current) {
		this.hvac_current = hvac_current;
	}
	public double getHvac_peak() {
		return hvac_peak;
	}
	public void setHvac_peak(double hvac_peak) {
		this.hvac_peak = hvac_peak;
	}
	public double getHvac_peak_time() {
		return hvac_peak_time;
	}
	public void setHvac_peak_time(double hvac_peak_time) {
		this.hvac_peak_time = hvac_peak_time;
	}
	public double getHvac_average() {
		return hvac_average;
	}
	public void setHvac_average(double hvac_average) {
		this.hvac_average = hvac_average;
	}
	
	
	
	
	
}
