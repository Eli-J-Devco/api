/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.entities;

import java.util.ArrayList;
import java.util.List;

public class AnalyticalReportTrackerGlobalConfigDTO {
	private Integer modified_by;
	private List<AnalyticalReportTrackerGlobalConfigActionFlagEntity> actionFlags = new ArrayList<>();
	private List<AnalyticalReportTrackerGlobalConfigCurrentStatusEntity> currentStatuses = new ArrayList<>();
	private List<AnalyticalReportTrackerGlobalConfigPathForwardUpdateEntity> pathForwardUpdates = new ArrayList<>();
	private List<AnalyticalReportTrackerGlobalConfigRuleEntity> performanceRules = new ArrayList<>();

	public Integer getModified_by() {
		return modified_by;
	}

	public void setModified_by(Integer modified_by) {
		this.modified_by = modified_by;
	}

	public List<AnalyticalReportTrackerGlobalConfigActionFlagEntity> getActionFlags() {
		return actionFlags;
	}

	public void setActionFlags(List<AnalyticalReportTrackerGlobalConfigActionFlagEntity> actionFlags) {
		this.actionFlags = actionFlags;
	}

	public List<AnalyticalReportTrackerGlobalConfigCurrentStatusEntity> getCurrentStatuses() {
		return currentStatuses;
	}

	public void setCurrentStatuses(List<AnalyticalReportTrackerGlobalConfigCurrentStatusEntity> currentStatuses) {
		this.currentStatuses = currentStatuses;
	}

	public List<AnalyticalReportTrackerGlobalConfigPathForwardUpdateEntity> getPathForwardUpdates() {
		return pathForwardUpdates;
	}

	public void setPathForwardUpdates(List<AnalyticalReportTrackerGlobalConfigPathForwardUpdateEntity> pathForwardUpdates) {
		this.pathForwardUpdates = pathForwardUpdates;
	}

	public List<AnalyticalReportTrackerGlobalConfigRuleEntity> getPerformanceRules() {
		return performanceRules;
	}

	public void setPerformanceRules(List<AnalyticalReportTrackerGlobalConfigRuleEntity> performanceRules) {
		this.performanceRules = performanceRules;
	}

}
