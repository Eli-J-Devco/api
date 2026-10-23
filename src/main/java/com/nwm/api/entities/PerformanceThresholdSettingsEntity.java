package com.nwm.api.entities;

public class PerformanceThresholdSettingsEntity {
	private Integer id;
	private Integer id_site;
	private Integer normal_threshold;
	private Integer underperforming_threshold;
	private Integer critical_threshold;
	private Integer zero_offline_threshold;

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

	public Integer getUnderperforming_threshold() {
		return underperforming_threshold;
	}

	public void setUnderperforming_threshold(Integer underperforming_threshold) {
		this.underperforming_threshold = underperforming_threshold;
	}

	public Integer getCritical_threshold() {
		return critical_threshold;
	}

	public void setCritical_threshold(Integer critical_threshold) {
		this.critical_threshold = critical_threshold;
	}

	public Integer getZero_offline_threshold() {
		return zero_offline_threshold;
	}

	public void setZero_offline_threshold(Integer zero_offline_threshold) {
		this.zero_offline_threshold = zero_offline_threshold;
	}
}
