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
	private List devices_pv;
	private int meter_type;
	private String table_data_report;
	private String type_group;
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
	private List dateTimeList;
	private List dataHistory;
	private List dataHistoryExpected;
	private double peak_demand;
	private String peak_demand_date;
	private double lastMonth;
	private double power_factor;
	private double power_factor_pf;
	private String power_factor_pf_time;
	private double gas_peak_flow_rate;
	private String gas_peak_flow_rate_date;
	private double water_peak_flow_rate;
	private String water_peak_flow_rate_date;
	private double pv_peak_flow_rate;
	private String pv_peak_flow_rate_date;
	private double electric_peak_flow_rate;
	private String electric_peak_flow_rate_date;
	
	private double peak_flow_rate;
	private String peak_flow_rate_date;
	
	private double year_over_year;
	private String year_over_date;
	
	private double pv_year_over_year;
	private String pv_year_over_date;
	private double water_year_over_year;
	private String water_year_over_date;
	private double gas_year_over_year;
	private String gas_year_over_date;
	private double electric_year_over_year;
	private String electric_year_over_date;
	
	private double last_year;
	private double avg_last_period;
	private double daytime;
	private double nighttime;
	private String download_file_path;
	private double max_annual_demand;
	private double max_monthly_demand;

    private String site_name;
    private String address_short;
    private String pv_system_size;
    private Double water_avg_last_period;
    private Double gas_avg_last_period;
    private Double electric_avg_last_period;
    private Double pv_avg_last_period;

	public List getDevices_pv() {
		return devices_pv;
	}
	public void setDevices_pv(List devices_pv) {
		this.devices_pv = devices_pv;
	}
	public double getMax_annual_demand() {
		return max_annual_demand;
	}
	public void setMax_annual_demand(double max_annual_demand) {
		this.max_annual_demand = max_annual_demand;
	}
	public double getMax_monthly_demand() {
		return max_monthly_demand;
	}
	public void setMax_monthly_demand(double max_monthly_demand) {
		this.max_monthly_demand = max_monthly_demand;
	}
	public double getPower_factor() {
		return power_factor;
	}
	public void setPower_factor(double power_factor) {
		this.power_factor = power_factor;
	}
	public double getPower_factor_pf() {
		return power_factor_pf;
	}
	public void setPower_factor_pf(double power_factor_pf) {
		this.power_factor_pf = power_factor_pf;
	}
	public String getPower_factor_pf_time() {
		return power_factor_pf_time;
	}
	public void setPower_factor_pf_time(String power_factor_pf_time) {
		this.power_factor_pf_time = power_factor_pf_time;
	}
	public String getDownload_file_path() {
		return download_file_path;
	}
	public void setDownload_file_path(String download_file_path) {
		this.download_file_path = download_file_path;
	}
	public String getType_group() {
		return type_group;
	}
	public void setType_group(String type_group) {
		this.type_group = type_group;
	}
	public double getDaytime() {
		return daytime;
	}
	public void setDaytime(double daytime) {
		this.daytime = daytime;
	}
	public double getNighttime() {
		return nighttime;
	}
	public void setNighttime(double nighttime) {
		this.nighttime = nighttime;
	}
	public double getAvg_last_period() {
		return avg_last_period;
	}
	public void setAvg_last_period(double avg_last_period) {
		this.avg_last_period = avg_last_period;
	}
	public double getLast_year() {
		return last_year;
	}
	public void setLast_year(double last_year) {
		this.last_year = last_year;
	}
	private double max_annual_daily;
	public double getMax_annual_daily() {
		return max_annual_daily;
	}
	public void setMax_annual_daily(double max_annual_daily) {
		this.max_annual_daily = max_annual_daily;
	}
	public String getMax_annual_daily_date() {
		return max_annual_daily_date;
	}
	public void setMax_annual_daily_date(String max_annual_daily_date) {
		this.max_annual_daily_date = max_annual_daily_date;
	}
	private String max_annual_daily_date;
	
	
	
	
	
	
	public double getPv_year_over_year() {
		return pv_year_over_year;
	}
	public void setPv_year_over_year(double pv_year_over_year) {
		this.pv_year_over_year = pv_year_over_year;
	}
	public String getPv_year_over_date() {
		return pv_year_over_date;
	}
	public void setPv_year_over_date(String pv_year_over_date) {
		this.pv_year_over_date = pv_year_over_date;
	}
	public double getWater_year_over_year() {
		return water_year_over_year;
	}
	public void setWater_year_over_year(double water_year_over_year) {
		this.water_year_over_year = water_year_over_year;
	}
	public String getWater_year_over_date() {
		return water_year_over_date;
	}
	public void setWater_year_over_date(String water_year_over_date) {
		this.water_year_over_date = water_year_over_date;
	}
	public double getGas_year_over_year() {
		return gas_year_over_year;
	}
	public void setGas_year_over_year(double gas_year_over_year) {
		this.gas_year_over_year = gas_year_over_year;
	}
	public String getGas_year_over_date() {
		return gas_year_over_date;
	}
	public void setGas_year_over_date(String gas_year_over_date) {
		this.gas_year_over_date = gas_year_over_date;
	}
	public double getElectric_year_over_year() {
		return electric_year_over_year;
	}
	public void setElectric_year_over_year(double electric_year_over_year) {
		this.electric_year_over_year = electric_year_over_year;
	}
	public String getElectric_year_over_date() {
		return electric_year_over_date;
	}
	public void setElectric_year_over_date(String electric_year_over_date) {
		this.electric_year_over_date = electric_year_over_date;
	}
	public double getYear_over_year() {
		return year_over_year;
	}
	public void setYear_over_year(double year_over_year) {
		this.year_over_year = year_over_year;
	}
	public String getYear_over_date() {
		return year_over_date;
	}
	public void setYear_over_date(String year_over_date) {
		this.year_over_date = year_over_date;
	}
	public double getPeak_flow_rate() {
		return peak_flow_rate;
	}
	public void setPeak_flow_rate(double peak_flow_rate) {
		this.peak_flow_rate = peak_flow_rate;
	}
	public String getPeak_flow_rate_date() {
		return peak_flow_rate_date;
	}
	public void setPeak_flow_rate_date(String peak_flow_rate_date) {
		this.peak_flow_rate_date = peak_flow_rate_date;
	}
	public double getGas_peak_flow_rate() {
		return gas_peak_flow_rate;
	}
	public void setGas_peak_flow_rate(double gas_peak_flow_rate) {
		this.gas_peak_flow_rate = gas_peak_flow_rate;
	}
	public String getGas_peak_flow_rate_date() {
		return gas_peak_flow_rate_date;
	}
	public void setGas_peak_flow_rate_date(String gas_peak_flow_rate_date) {
		this.gas_peak_flow_rate_date = gas_peak_flow_rate_date;
	}
	public double getWater_peak_flow_rate() {
		return water_peak_flow_rate;
	}
	public void setWater_peak_flow_rate(double water_peak_flow_rate) {
		this.water_peak_flow_rate = water_peak_flow_rate;
	}
	public String getWater_peak_flow_rate_date() {
		return water_peak_flow_rate_date;
	}
	public void setWater_peak_flow_rate_date(String water_peak_flow_rate_date) {
		this.water_peak_flow_rate_date = water_peak_flow_rate_date;
	}
	public double getPv_peak_flow_rate() {
		return pv_peak_flow_rate;
	}
	public void setPv_peak_flow_rate(double pv_peak_flow_rate) {
		this.pv_peak_flow_rate = pv_peak_flow_rate;
	}
	public String getPv_peak_flow_rate_date() {
		return pv_peak_flow_rate_date;
	}
	public void setPv_peak_flow_rate_date(String pv_peak_flow_rate_date) {
		this.pv_peak_flow_rate_date = pv_peak_flow_rate_date;
	}
	public double getElectric_peak_flow_rate() {
		return electric_peak_flow_rate;
	}
	public void setElectric_peak_flow_rate(double electric_peak_flow_rate) {
		this.electric_peak_flow_rate = electric_peak_flow_rate;
	}
	public String getElectric_peak_flow_rate_date() {
		return electric_peak_flow_rate_date;
	}
	public void setElectric_peak_flow_rate_date(String electric_peak_flow_rate_date) {
		this.electric_peak_flow_rate_date = electric_peak_flow_rate_date;
	}
	public List getDateTimeList() {
		return dateTimeList;
	}
	public void setDateTimeList(List dateTimeList) {
		this.dateTimeList = dateTimeList;
	}
	public String getTable_data_report() {
		return table_data_report;
	}
	public void setTable_data_report(String table_data_report) {
		this.table_data_report = table_data_report;
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


    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getAddress_short() {
        return address_short;
    }

    public void setAddress_short(String address_short) {
        this.address_short = address_short;
    }

    public String getPv_system_size() {
        return pv_system_size;
    }

    public void setPv_system_size(String pv_system_size) {
        this.pv_system_size = pv_system_size;
    }

    public Double getWater_avg_last_period() {
        return water_avg_last_period;
    }

    public void setWater_avg_last_period(Double water_avg_last_period) {
        this.water_avg_last_period = water_avg_last_period;
    }

    public Double getGas_avg_last_period() {
        return gas_avg_last_period;
    }

    public void setGas_avg_last_period(Double gas_avg_last_period) {
        this.gas_avg_last_period = gas_avg_last_period;
    }

    public Double getElectric_avg_last_period() {
        return electric_avg_last_period;
    }

    public void setElectric_avg_last_period(Double electric_avg_last_period) {
        this.electric_avg_last_period = electric_avg_last_period;
    }

    public Double getPv_avg_last_period() {
        return pv_avg_last_period;
    }

    public void setPv_avg_last_period(Double pv_avg_last_period) {
        this.pv_avg_last_period = pv_avg_last_period;
    }

    @Override
    public String toString() {
        return "BuildingReportEntity{" +
                "id=" + id +
                ",\nid_site=" + id_site +
                ",\nid_company=" + id_company +
                ",\nid_employee=" + id_employee +
                ",\nstart_date='" + start_date + '\'' +
                ",\nend_date='" + end_date + '\'' +
                ",\ndevices=" + devices +
                ",\ndevices_pv=" + devices_pv +
                ",\nmeter_type=" + meter_type +
                ",\ntable_data_report='" + table_data_report + '\'' +
                ",\ntype_group='" + type_group + '\'' +
                ",\npv_current_month=" + pv_current_month +
                ",\npv_compare_current_month=" + pv_compare_current_month +
                ",\ngas_current_month=" + gas_current_month +
                ",\ngas_compare_current_month=" + gas_compare_current_month +
                ",\nwater_current_month=" + water_current_month +
                ",\nwater_compare_current_month=" + water_compare_current_month +
                ",\nelectric_current_month=" + electric_current_month +
                ",\nelectric_compare_current_month=" + electric_compare_current_month +
                ",\ncurrent_month=" + current_month +
                ",\ncompare_current_month=" + compare_current_month +
                ",\ndataWeatherCurrentMonth=" + dataWeatherCurrentMonth +
                ",\ndataWeatherComapreMonth=" + dataWeatherComapreMonth +
                ",\ndataPVStatistics=" + dataPVStatistics +
                ",\ndataElectricStatistics=" + dataElectricStatistics +
                ",\ndataGasStatistics=" + dataGasStatistics +
                ",\ndataWaterStatistics=" + dataWaterStatistics +
                ",\ndataDaily=" + dataDaily +
                ",\ndataDailyExpected=" + dataDailyExpected +
                ",\ndateTimeList=" + dateTimeList +
                ",\ndataHistory=" + dataHistory +
                ",\ndataHistoryExpected=" + dataHistoryExpected +
                ",\npeak_demand=" + peak_demand +
                ",\npeak_demand_date='" + peak_demand_date + '\'' +
                ",\nlastMonth=" + lastMonth +
                ",\npower_factor=" + power_factor +
                ",\npower_factor_pf=" + power_factor_pf +
                ",\npower_factor_pf_time='" + power_factor_pf_time + '\'' +
                ",\ngas_peak_flow_rate=" + gas_peak_flow_rate +
                ",\ngas_peak_flow_rate_date='" + gas_peak_flow_rate_date + '\'' +
                ",\nwater_peak_flow_rate=" + water_peak_flow_rate +
                ",\nwater_peak_flow_rate_date='" + water_peak_flow_rate_date + '\'' +
                ",\npv_peak_flow_rate=" + pv_peak_flow_rate +
                ",\npv_peak_flow_rate_date='" + pv_peak_flow_rate_date + '\'' +
                ",\nelectric_peak_flow_rate=" + electric_peak_flow_rate +
                ",\nelectric_peak_flow_rate_date='" + electric_peak_flow_rate_date + '\'' +
                ",\npeak_flow_rate=" + peak_flow_rate +
                ",\npeak_flow_rate_date='" + peak_flow_rate_date + '\'' +
                ",\nyear_over_year=" + year_over_year +
                ",\nyear_over_date='" + year_over_date + '\'' +
                ",\npv_year_over_year=" + pv_year_over_year +
                ",\npv_year_over_date='" + pv_year_over_date + '\'' +
                ",\nwater_year_over_year=" + water_year_over_year +
                ",\nwater_year_over_date='" + water_year_over_date + '\'' +
                ",\ngas_year_over_year=" + gas_year_over_year +
                ",\ngas_year_over_date='" + gas_year_over_date + '\'' +
                ",\nelectric_year_over_year=" + electric_year_over_year +
                ",\nelectric_year_over_date='" + electric_year_over_date + '\'' +
                ",\nlast_year=" + last_year +
                ",\navg_last_period=" + avg_last_period +
                ",\ndaytime=" + daytime +
                ",\nnighttime=" + nighttime +
                ",\ndownload_file_path='" + download_file_path + '\'' +
                ",\nmax_annual_demand=" + max_annual_demand +
                ",\nmax_monthly_demand=" + max_monthly_demand +
                ",\nsite_name='" + site_name + '\'' +
                ",\naddress_short='" + address_short + '\'' +
                ",\npv_system_size='" + pv_system_size + '\'' +
                ",\nmax_annual_daily=" + max_annual_daily +
                ",\nmax_annual_daily_date='" + max_annual_daily_date + '\'' +
                '}';
    }
}
