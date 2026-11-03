package com.nwm.api.entities;

/** Read model for parameter mappings attached to a configured string. */
public class StringParameterMapEntity {
	private Integer id;
	private Integer id_string;
	private Integer id_parameter;
	private Integer parameter_type;
	private String parameter_name;
	private String parameter_slug;
	private String unit;

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public Integer getId_string() { return id_string; }
	public void setId_string(Integer id_string) { this.id_string = id_string; }
	public Integer getId_parameter() { return id_parameter; }
	public void setId_parameter(Integer id_parameter) { this.id_parameter = id_parameter; }
	public Integer getParameter_type() { return parameter_type; }
	public void setParameter_type(Integer parameter_type) { this.parameter_type = parameter_type; }
	public String getParameter_name() { return parameter_name; }
	public void setParameter_name(String parameter_name) { this.parameter_name = parameter_name; }
	public String getParameter_slug() { return parameter_slug; }
	public void setParameter_slug(String parameter_slug) { this.parameter_slug = parameter_slug; }
	public String getUnit() { return unit; }
	public void setUnit(String unit) { this.unit = unit; }
}
