package com.nwm.api.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TroubleshootingMainMeterConfigEntity {
	private int id_config;
	private int id_site;
	private int version;
	private int id_device;
	private String current_parameter;
	private String current_phase_a_parameter;
	private String current_phase_b_parameter;
	private String current_phase_c_parameter;
	private BigDecimal current_issue_threshold = new BigDecimal("10.00");
	private String voltage_parameter;
	private String voltage_phase_a_parameter;
	private String voltage_phase_b_parameter;
	private String voltage_phase_c_parameter;
	private BigDecimal voltage_issue_threshold = new BigDecimal("5.00");
	private String power_factor_parameter;
	private String power_factor_phase_a_parameter;
	private String power_factor_phase_b_parameter;
	private String power_factor_phase_c_parameter;
	private BigDecimal power_factor_issue_threshold = new BigDecimal("0.95");

	public int getId_config() { return id_config; }
	public void setId_config(int id_config) { this.id_config = id_config; }
	public int getId_site() { return id_site; }
	public void setId_site(int id_site) { this.id_site = id_site; }
	public int getVersion() { return version; }
	public void setVersion(int version) { this.version = version; }
	public int getId_device() { return id_device; }
	public void setId_device(int id_device) { this.id_device = id_device; }
	public String getCurrent_parameter() { return current_parameter; }
	public void setCurrent_parameter(String current_parameter) { this.current_parameter = current_parameter; }
	public String getCurrent_phase_a_parameter() { return current_phase_a_parameter; }
	public void setCurrent_phase_a_parameter(String value) { this.current_phase_a_parameter = value; }
	public String getCurrent_phase_b_parameter() { return current_phase_b_parameter; }
	public void setCurrent_phase_b_parameter(String value) { this.current_phase_b_parameter = value; }
	public String getCurrent_phase_c_parameter() { return current_phase_c_parameter; }
	public void setCurrent_phase_c_parameter(String value) { this.current_phase_c_parameter = value; }
	public BigDecimal getCurrent_issue_threshold() { return current_issue_threshold; }
	public void setCurrent_issue_threshold(BigDecimal value) { this.current_issue_threshold = value; }
	public String getVoltage_parameter() { return voltage_parameter; }
	public void setVoltage_parameter(String voltage_parameter) { this.voltage_parameter = voltage_parameter; }
	public String getVoltage_phase_a_parameter() { return voltage_phase_a_parameter; }
	public void setVoltage_phase_a_parameter(String value) { this.voltage_phase_a_parameter = value; }
	public String getVoltage_phase_b_parameter() { return voltage_phase_b_parameter; }
	public void setVoltage_phase_b_parameter(String value) { this.voltage_phase_b_parameter = value; }
	public String getVoltage_phase_c_parameter() { return voltage_phase_c_parameter; }
	public void setVoltage_phase_c_parameter(String value) { this.voltage_phase_c_parameter = value; }
	public BigDecimal getVoltage_issue_threshold() { return voltage_issue_threshold; }
	public void setVoltage_issue_threshold(BigDecimal value) { this.voltage_issue_threshold = value; }
	public String getPower_factor_parameter() { return power_factor_parameter; }
	public void setPower_factor_parameter(String power_factor_parameter) { this.power_factor_parameter = power_factor_parameter; }
	public String getPower_factor_phase_a_parameter() { return power_factor_phase_a_parameter; }
	public void setPower_factor_phase_a_parameter(String value) { this.power_factor_phase_a_parameter = value; }
	public String getPower_factor_phase_b_parameter() { return power_factor_phase_b_parameter; }
	public void setPower_factor_phase_b_parameter(String value) { this.power_factor_phase_b_parameter = value; }
	public String getPower_factor_phase_c_parameter() { return power_factor_phase_c_parameter; }
	public void setPower_factor_phase_c_parameter(String value) { this.power_factor_phase_c_parameter = value; }
	public BigDecimal getPower_factor_issue_threshold() { return power_factor_issue_threshold; }
	public void setPower_factor_issue_threshold(BigDecimal value) { this.power_factor_issue_threshold = value; }
}
