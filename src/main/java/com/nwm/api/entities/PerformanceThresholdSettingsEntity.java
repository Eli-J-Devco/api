package com.nwm.api.entities;

public class PerformanceThresholdSettingsEntity {
	private Integer id;
	private Integer id_site;
	private Integer normal_threshold;
	private Integer warning_threshold;
	private Integer error_threshold;
	private Integer arc_failure_threshold;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_site() {
		return id_site;
	}

	public void setId_site(Integer id_site) {
		this.id_site = id_site;
	}

	public Integer getNormal_threshold() {
		return normal_threshold;
	}

	public void setNormal_threshold(Integer normal_threshold) {
		this.normal_threshold = normal_threshold;
	}

	public Integer getWarning_threshold() {
		return warning_threshold;
	}

	public void setWarning_threshold(Integer warning_threshold) {
		this.warning_threshold = warning_threshold;
	}

	public Integer getError_threshold() {
		return error_threshold;
	}

	public void setError_threshold(Integer error_threshold) {
		this.error_threshold = error_threshold;
	}

	public Integer getArc_failure_threshold() {
		return arc_failure_threshold;
	}

	public void setArc_failure_threshold(Integer arc_failure_threshold) {
		this.arc_failure_threshold = arc_failure_threshold;
	}
}
