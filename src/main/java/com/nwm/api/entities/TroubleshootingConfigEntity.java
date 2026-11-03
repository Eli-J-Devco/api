package com.nwm.api.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TroubleshootingConfigEntity {
	private int id_config;
	private int id_site;
	private int version;
	private int id_device;

	public int getId_config() { return id_config; }
	public void setId_config(int value) { this.id_config = value; }
	public int getId_site() { return id_site; }
	public void setId_site(int value) { this.id_site = value; }
	public int getVersion() { return version; }
	public void setVersion(int value) { this.version = value; }
	public int getId_device() { return id_device; }
	public void setId_device(int value) { this.id_device = value; }

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MainMeter extends TroubleshootingConfigEntity {
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

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PqMeter extends TroubleshootingConfigEntity {
		private int id;
		private String direction_parameter;
		private String confidence_parameter;
		private String timestamp_parameter;
		private String duration_parameter;
		private String phase_a_parameter;
		private String phase_b_parameter;
		private String phase_c_parameter;

		public int getId() { return id; }
		public void setId(int value) { this.id = value; }
		public String getDirection_parameter() { return direction_parameter; }
		public void setDirection_parameter(String value) { this.direction_parameter = value; }
		public String getConfidence_parameter() { return confidence_parameter; }
		public void setConfidence_parameter(String value) { this.confidence_parameter = value; }
		public String getTimestamp_parameter() { return timestamp_parameter; }
		public void setTimestamp_parameter(String value) { this.timestamp_parameter = value; }
		public String getDuration_parameter() { return duration_parameter; }
		public void setDuration_parameter(String value) { this.duration_parameter = value; }
		public String getPhase_a_parameter() { return phase_a_parameter; }
		public void setPhase_a_parameter(String value) { this.phase_a_parameter = value; }
		public String getPhase_b_parameter() { return phase_b_parameter; }
		public void setPhase_b_parameter(String value) { this.phase_b_parameter = value; }
		public String getPhase_c_parameter() { return phase_c_parameter; }
		public void setPhase_c_parameter(String value) { this.phase_c_parameter = value; }
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class SubMeterConfig extends TroubleshootingConfigEntity {
		private List<Panel> panels;
		public List<Panel> getPanels() { return panels; }
		public void setPanels(List<Panel> value) { panels = value; }
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Panel {
		private int id;
		private int id_config;
		private String name;
		private int sort_order;
		private List<Submeter> submeters = new ArrayList<>();
		private List<MeterGroup> groups = new ArrayList<>();

		public int getId() { return id; }
		public void setId(int value) { id = value; }
		public int getId_config() { return id_config; }
		public void setId_config(int value) { id_config = value; }
		public String getName() { return name; }
		public void setName(String value) { name = value; }
		public int getSort_order() { return sort_order; }
		public void setSort_order(int value) { sort_order = value; }
		public List<Submeter> getSubmeters() { return submeters; }
		public void setSubmeters(List<Submeter> value) { submeters = value; }
		public List<MeterGroup> getGroups() { return groups; }
		public void setGroups(List<MeterGroup> value) { groups = value; }
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MeterGroup {
		private int id;
		private int id_panel;
		private String name;
		private int sort_order;

		public int getId() { return id; }
		public void setId(int value) { id = value; }
		public int getId_panel() { return id_panel; }
		public void setId_panel(int value) { id_panel = value; }
		public String getName() { return name; }
		public void setName(String value) { name = value; }
		public int getSort_order() { return sort_order; }
		public void setSort_order(int value) { sort_order = value; }
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Submeter {
		private int id;
		private int id_panel;
		private Integer id_group;
		private String name;
		private Integer id_device;
		private String current_parameter;
		private String voltage_parameter;
		private String power_factor_parameter;
		private String phase_type;

		public int getId() { return id; }
		public void setId(int value) { id = value; }
		public int getId_panel() { return id_panel; }
		public void setId_panel(int value) { id_panel = value; }
		public Integer getId_group() { return id_group; }
		public void setId_group(Integer value) { id_group = value; }
		public String getName() { return name; }
		public void setName(String value) { name = value; }
		public Integer getId_device() { return id_device; }
		public void setId_device(Integer value) { id_device = value; }
		public String getCurrent_parameter() { return current_parameter; }
		public void setCurrent_parameter(String value) { current_parameter = value; }
		public String getVoltage_parameter() { return voltage_parameter; }
		public void setVoltage_parameter(String value) { voltage_parameter = value; }
		public String getPower_factor_parameter() { return power_factor_parameter; }
		public void setPower_factor_parameter(String value) { power_factor_parameter = value; }
		public String getPhase_type() { return phase_type; }
		public void setPhase_type(String value) { phase_type = value; }
	}
}
