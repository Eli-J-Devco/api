/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;
import java.util.List;

public class BuildingReportEntity extends SortEntity {
	private int id;
	private int id_site;
	private int id_company;
	private int id_employee;
	private String start_date;
	private String end_date;
	private List devices;
	private int meter_type;
	private String table_data_report;
	
	private double pv_current_month;
	private double pv_compare_current_month;
	
	private double gas_current_month;
	private double gas_compare_current_month;
	
	private double water_current_month;
	private double water_compare_current_month;
	
	private double electric_current_month;
	private double electric_compare_current_month;
	
	private double current_month;
	private double compare_current_month;
	private List dataWeatherCurrentMonth;
	private List dataWeatherComapreMonth;
	
	private List dataPVStatistics;
	private List dataElectricStatistics;
	private List dataGasStatistics;
	private List dataWaterStatistics;
	private List dataDaily;
	private List dataDailyExpected;
	
	private List dataHistory;
	private List dataHistoryExpected;
	private double peak_demand;
	private String peak_demand_date;
	private double lastMonth;
	private double avg3Month;
	
	
	
	
	
	
	public String getTable_data_report() {
		return table_data_report;
	}
	public void setTable_data_report(String table_data_report) {
		this.table_data_report = table_data_report;
	}
	public double getAvg3Month() {
		return avg3Month;
	}
	public void setAvg3Month(double avg3Month) {
		this.avg3Month = avg3Month;
	}
	public double getLastMonth() {
		return lastMonth;
	}
	public void setLastMonth(double lastMonth) {
		this.lastMonth = lastMonth;
	}
	public double getPeak_demand() {
		return peak_demand;
	}
	public void setPeak_demand(double peak_demand) {
		this.peak_demand = peak_demand;
	}
	public String getPeak_demand_date() {
		return peak_demand_date;
	}
	public void setPeak_demand_date(String peak_demand_date) {
		this.peak_demand_date = peak_demand_date;
	}
	public List getDataHistory() {
		return dataHistory;
	}
	public void setDataHistory(List dataHistory) {
		this.dataHistory = dataHistory;
	}
	public List getDataHistoryExpected() {
		return dataHistoryExpected;
	}
	public void setDataHistoryExpected(List dataHistoryExpected) {
		this.dataHistoryExpected = dataHistoryExpected;
	}
	public List getDataDailyExpected() {
		return dataDailyExpected;
	}
	public void setDataDailyExpected(List dataDailyExpected) {
		this.dataDailyExpected = dataDailyExpected;
	}
	public List getDataDaily() {
		return dataDaily;
	}
	public void setDataDaily(List dataDaily) {
		this.dataDaily = dataDaily;
	}
	public List getDataPVStatistics() {
		return dataPVStatistics;
	}
	public void setDataPVStatistics(List dataPVStatistics) {
		this.dataPVStatistics = dataPVStatistics;
	}
	public List getDataElectricStatistics() {
		return dataElectricStatistics;
	}
	public void setDataElectricStatistics(List dataElectricStatistics) {
		this.dataElectricStatistics = dataElectricStatistics;
	}
	public List getDataGasStatistics() {
		return dataGasStatistics;
	}
	public void setDataGasStatistics(List dataGasStatistics) {
		this.dataGasStatistics = dataGasStatistics;
	}
	public List getDataWaterStatistics() {
		return dataWaterStatistics;
	}
	public void setDataWaterStatistics(List dataWaterStatistics) {
		this.dataWaterStatistics = dataWaterStatistics;
	}
	public List getDataWeatherCurrentMonth() {
		return dataWeatherCurrentMonth;
	}
	public void setDataWeatherCurrentMonth(List dataWeatherCurrentMonth) {
		this.dataWeatherCurrentMonth = dataWeatherCurrentMonth;
	}
	public List getDataWeatherComapreMonth() {
		return dataWeatherComapreMonth;
	}
	public void setDataWeatherComapreMonth(List dataWeatherComapreMonth) {
		this.dataWeatherComapreMonth = dataWeatherComapreMonth;
	}
	public double getCurrent_month() {
		return current_month;
	}
	public void setCurrent_month(double current_month) {
		this.current_month = current_month;
	}
	public double getCompare_current_month() {
		return compare_current_month;
	}
	public void setCompare_current_month(double compare_current_month) {
		this.compare_current_month = compare_current_month;
	}
	public double getPv_current_month() {
		return pv_current_month;
	}
	public void setPv_current_month(double pv_current_month) {
		this.pv_current_month = pv_current_month;
	}
	public double getPv_compare_current_month() {
		return pv_compare_current_month;
	}
	public void setPv_compare_current_month(double pv_compare_current_month) {
		this.pv_compare_current_month = pv_compare_current_month;
	}
	public double getGas_current_month() {
		return gas_current_month;
	}
	public void setGas_current_month(double gas_current_month) {
		this.gas_current_month = gas_current_month;
	}
	public double getGas_compare_current_month() {
		return gas_compare_current_month;
	}
	public void setGas_compare_current_month(double gas_compare_current_month) {
		this.gas_compare_current_month = gas_compare_current_month;
	}
	public double getWater_current_month() {
		return water_current_month;
	}
	public void setWater_current_month(double water_current_month) {
		this.water_current_month = water_current_month;
	}
	public double getWater_compare_current_month() {
		return water_compare_current_month;
	}
	public void setWater_compare_current_month(double water_compare_current_month) {
		this.water_compare_current_month = water_compare_current_month;
	}
	public double getElectric_current_month() {
		return electric_current_month;
	}
	public void setElectric_current_month(double electric_current_month) {
		this.electric_current_month = electric_current_month;
	}
	public double getElectric_compare_current_month() {
		return electric_compare_current_month;
	}
	public void setElectric_compare_current_month(double electric_compare_current_month) {
		this.electric_compare_current_month = electric_compare_current_month;
	}
	public int getMeter_type() {
		return meter_type;
	}
	public void setMeter_type(int meter_type) {
		this.meter_type = meter_type;
	}
	public List getDevices() {
		return devices;
	}
	public void setDevices(List devices) {
		this.devices = devices;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_site() {
		return id_site;
	}
	public void setId_site(int id_site) {
		this.id_site = id_site;
	}
	public int getId_company() {
		return id_company;
	}
	public void setId_company(int id_company) {
		this.id_company = id_company;
	}
	public int getId_employee() {
		return id_employee;
	}
	public void setId_employee(int id_employee) {
		this.id_employee = id_employee;
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
	
	
	
	
	
	
	
	
}
