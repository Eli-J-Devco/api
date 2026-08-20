/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*********************************************************/
package com.nwm.api.entities;

import java.math.BigDecimal;

public class AnalyticalReportTrackerGlobalConfigFinalScoreFormulaEntity {
	private Integer id;
	private Integer modified_by;
	private String name;
	private BigDecimal weight;

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public Integer getModified_by() { return modified_by; }
	public void setModified_by(Integer modified_by) { this.modified_by = modified_by; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public BigDecimal getWeight() { return weight; }
	public void setWeight(BigDecimal weight) { this.weight = weight; }
}
