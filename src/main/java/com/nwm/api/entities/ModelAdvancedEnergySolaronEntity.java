/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.entities;

public class ModelAdvancedEnergySolaronEntity {
	private String time;
	private int id_device;
	private int error;
	private int low_alarm;
	private int high_alarm;
	private double today_kwh;
	private double ytd_kwh_total;
	private double life_kwh_total;
	private double ytd_kwh;
	private double life_kwh;
	private double last_15min_kwh;
	private double timestamp_15minutes;
	private double last_restart;
	private double uptime;
	private double ac_power;
	private double ac_frequency;
	private double pv_voltage;
	private double pv_current;
	private double common_mode;
	private double ambient_temperature;
	private double coolant_temperature;
	private double reactor_temperature;
	private double cabinet_temperature;
	private double bus_voltage;
	private double ground_current;
	private double reactive_power;
	private double active_faults1;
	private double active_faults2;
	private double active_faults3;
	private double status;
	private double warnings1;
	private double warnings2_reserved;
	private double warnings3_reserved;
	private double limits;
	private double year;
	private double month;
	private double day;
	private double hour;
	private double minutes;
	private double seconds;
	private double current_time;
	private double nvmActivePower;
	private double nvmActiveEnergy;
	private int totalFault1;
	private int totalFault2;
	private int totalFault3;
	private int totalLimits;
	private int totalWarning;
	private int totalStatus;
	
	private double MeasuredProduction;
	
	
	
	
	public double getMeasuredProduction() {
		return MeasuredProduction;
	}
	public void setMeasuredProduction(double measuredProduction) {
		MeasuredProduction = measuredProduction;
	}
	
	
	
	
	public int getTotalFault1() {
		return totalFault1;
	}
	public void setTotalFault1(int totalFault1) {
		this.totalFault1 = totalFault1;
	}
	public int getTotalFault2() {
		return totalFault2;
	}
	public void setTotalFault2(int totalFault2) {
		this.totalFault2 = totalFault2;
	}
	public int getTotalFault3() {
		return totalFault3;
	}
	public void setTotalFault3(int totalFault3) {
		this.totalFault3 = totalFault3;
	}
	public int getTotalLimits() {
		return totalLimits;
	}
	public void setTotalLimits(int totalLimits) {
		this.totalLimits = totalLimits;
	}
	public int getTotalWarning() {
		return totalWarning;
	}
	public void setTotalWarning(int totalWarning) {
		this.totalWarning = totalWarning;
	}
	public int getTotalStatus() {
		return totalStatus;
	}
	public void setTotalStatus(int totalStatus) {
		this.totalStatus = totalStatus;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId_device() {
		return id_device;
	}
	public void setId_device(int id_device) {
		this.id_device = id_device;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public int getLow_alarm() {
		return low_alarm;
	}
	public void setLow_alarm(int low_alarm) {
		this.low_alarm = low_alarm;
	}
	public int getHigh_alarm() {
		return high_alarm;
	}
	public void setHigh_alarm(int high_alarm) {
		this.high_alarm = high_alarm;
	}
	public double getToday_kwh() {
		return today_kwh;
	}
	public void setToday_kwh(double today_kwh) {
		this.today_kwh = today_kwh;
	}
	public double getYtd_kwh_total() {
		return ytd_kwh_total;
	}
	public void setYtd_kwh_total(double ytd_kwh_total) {
		this.ytd_kwh_total = ytd_kwh_total;
	}
	public double getLife_kwh_total() {
		return life_kwh_total;
	}
	public void setLife_kwh_total(double life_kwh_total) {
		this.life_kwh_total = life_kwh_total;
	}
	public double getYtd_kwh() {
		return ytd_kwh;
	}
	public void setYtd_kwh(double ytd_kwh) {
		this.ytd_kwh = ytd_kwh;
	}
	public double getLife_kwh() {
		return life_kwh;
	}
	public void setLife_kwh(double life_kwh) {
		this.life_kwh = life_kwh;
	}
	public double getLast_15min_kwh() {
		return last_15min_kwh;
	}
	public void setLast_15min_kwh(double last_15min_kwh) {
		this.last_15min_kwh = last_15min_kwh;
	}
	public double getTimestamp_15minutes() {
		return timestamp_15minutes;
	}
	public void setTimestamp_15minutes(double timestamp_15minutes) {
		this.timestamp_15minutes = timestamp_15minutes;
	}
	public double getLast_restart() {
		return last_restart;
	}
	public void setLast_restart(double last_restart) {
		this.last_restart = last_restart;
	}
	public double getUptime() {
		return uptime;
	}
	public void setUptime(double uptime) {
		this.uptime = uptime;
	}
	public double getAc_power() {
		return ac_power;
	}
	public void setAc_power(double ac_power) {
		this.ac_power = ac_power;
	}
	public double getAc_frequency() {
		return ac_frequency;
	}
	public void setAc_frequency(double ac_frequency) {
		this.ac_frequency = ac_frequency;
	}
	public double getPv_voltage() {
		return pv_voltage;
	}
	public void setPv_voltage(double pv_voltage) {
		this.pv_voltage = pv_voltage;
	}
	public double getPv_current() {
		return pv_current;
	}
	public void setPv_current(double pv_current) {
		this.pv_current = pv_current;
	}
	public double getCommon_mode() {
		return common_mode;
	}
	public void setCommon_mode(double common_mode) {
		this.common_mode = common_mode;
	}
	public double getAmbient_temperature() {
		return ambient_temperature;
	}
	public void setAmbient_temperature(double ambient_temperature) {
		this.ambient_temperature = ambient_temperature;
	}
	public double getCoolant_temperature() {
		return coolant_temperature;
	}
	public void setCoolant_temperature(double coolant_temperature) {
		this.coolant_temperature = coolant_temperature;
	}
	public double getReactor_temperature() {
		return reactor_temperature;
	}
	public void setReactor_temperature(double reactor_temperature) {
		this.reactor_temperature = reactor_temperature;
	}
	public double getCabinet_temperature() {
		return cabinet_temperature;
	}
	public void setCabinet_temperature(double cabinet_temperature) {
		this.cabinet_temperature = cabinet_temperature;
	}
	public double getBus_voltage() {
		return bus_voltage;
	}
	public void setBus_voltage(double bus_voltage) {
		this.bus_voltage = bus_voltage;
	}
	public double getGround_current() {
		return ground_current;
	}
	public void setGround_current(double ground_current) {
		this.ground_current = ground_current;
	}
	public double getReactive_power() {
		return reactive_power;
	}
	public void setReactive_power(double reactive_power) {
		this.reactive_power = reactive_power;
	}
	public double getActive_faults1() {
		return active_faults1;
	}
	public void setActive_faults1(double active_faults1) {
		this.active_faults1 = active_faults1;
	}
	public double getActive_faults2() {
		return active_faults2;
	}
	public void setActive_faults2(double active_faults2) {
		this.active_faults2 = active_faults2;
	}
	public double getActive_faults3() {
		return active_faults3;
	}
	public void setActive_faults3(double active_faults3) {
		this.active_faults3 = active_faults3;
	}
	public double getStatus() {
		return status;
	}
	public void setStatus(double status) {
		this.status = status;
	}
	public double getWarnings1() {
		return warnings1;
	}
	public void setWarnings1(double warnings1) {
		this.warnings1 = warnings1;
	}
	public double getWarnings2_reserved() {
		return warnings2_reserved;
	}
	public void setWarnings2_reserved(double warnings2_reserved) {
		this.warnings2_reserved = warnings2_reserved;
	}
	public double getWarnings3_reserved() {
		return warnings3_reserved;
	}
	public void setWarnings3_reserved(double warnings3_reserved) {
		this.warnings3_reserved = warnings3_reserved;
	}
	public double getLimits() {
		return limits;
	}
	public void setLimits(double limits) {
		this.limits = limits;
	}
	public double getYear() {
		return year;
	}
	public void setYear(double year) {
		this.year = year;
	}
	public double getMonth() {
		return month;
	}
	public void setMonth(double month) {
		this.month = month;
	}
	public double getDay() {
		return day;
	}
	public void setDay(double day) {
		this.day = day;
	}
	public double getHour() {
		return hour;
	}
	public void setHour(double hour) {
		this.hour = hour;
	}
	public double getMinutes() {
		return minutes;
	}
	public void setMinutes(double minutes) {
		this.minutes = minutes;
	}
	public double getSeconds() {
		return seconds;
	}
	public void setSeconds(double seconds) {
		this.seconds = seconds;
	}
	public double getCurrent_time() {
		return current_time;
	}
	public void setCurrent_time(double current_time) {
		this.current_time = current_time;
	}
	public double getNvmActivePower() {
		return nvmActivePower;
	}
	public void setNvmActivePower(double nvmActivePower) {
		this.nvmActivePower = nvmActivePower;
	}
	public double getNvmActiveEnergy() {
		return nvmActiveEnergy;
	}
	public void setNvmActiveEnergy(double nvmActiveEnergy) {
		this.nvmActiveEnergy = nvmActiveEnergy;
	}
	
	
}
