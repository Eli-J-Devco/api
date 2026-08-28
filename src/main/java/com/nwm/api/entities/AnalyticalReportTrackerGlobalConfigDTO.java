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
	private List<AnalyticalReportTrackerGlobalConfigPerformanceStatusMappingEntity> performanceStatusMappings = new ArrayList<>();
	private List<AnalyticalReportTrackerGlobalConfigDefinitionsGlossaryEntity> definitionsGlossary = new ArrayList<>();
	private List<AnalyticalReportTrackerGlobalConfigFinalScoreFormulaEntity> finalScoreFormula = new ArrayList<>();
	private List<Integer> deletedActionFlagIds = new ArrayList<>();
	private List<Integer> deletedCurrentStatusIds = new ArrayList<>();
	private List<Integer> deletedPathForwardUpdateIds = new ArrayList<>();
	private List<Integer> deletedPerformanceRuleIds = new ArrayList<>();
	private List<Integer> deletedPerformanceStatusMappingIds = new ArrayList<>();
	private List<Integer> deletedDefinitionsGlossaryIds = new ArrayList<>();

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

	public List<AnalyticalReportTrackerGlobalConfigPerformanceStatusMappingEntity> getPerformanceStatusMappings() {
		return performanceStatusMappings;
	}

	public void setPerformanceStatusMappings(List<AnalyticalReportTrackerGlobalConfigPerformanceStatusMappingEntity> performanceStatusMappings) {
		this.performanceStatusMappings = performanceStatusMappings;
	}

	public List<AnalyticalReportTrackerGlobalConfigDefinitionsGlossaryEntity> getDefinitionsGlossary() {
		return definitionsGlossary;
	}

	public void setDefinitionsGlossary(List<AnalyticalReportTrackerGlobalConfigDefinitionsGlossaryEntity> definitionsGlossary) {
		this.definitionsGlossary = definitionsGlossary;
	}

	public List<AnalyticalReportTrackerGlobalConfigFinalScoreFormulaEntity> getFinalScoreFormula() {
		return finalScoreFormula;
	}

	public void setFinalScoreFormula(List<AnalyticalReportTrackerGlobalConfigFinalScoreFormulaEntity> finalScoreFormula) {
		this.finalScoreFormula = finalScoreFormula;
	}

	public List<Integer> getDeletedActionFlagIds() { return deletedActionFlagIds; }
	public void setDeletedActionFlagIds(List<Integer> ids) { this.deletedActionFlagIds = ids; }
	public List<Integer> getDeletedCurrentStatusIds() { return deletedCurrentStatusIds; }
	public void setDeletedCurrentStatusIds(List<Integer> ids) { this.deletedCurrentStatusIds = ids; }
	public List<Integer> getDeletedPathForwardUpdateIds() { return deletedPathForwardUpdateIds; }
	public void setDeletedPathForwardUpdateIds(List<Integer> ids) { this.deletedPathForwardUpdateIds = ids; }
	public List<Integer> getDeletedPerformanceRuleIds() { return deletedPerformanceRuleIds; }
	public void setDeletedPerformanceRuleIds(List<Integer> ids) { this.deletedPerformanceRuleIds = ids; }

	public List<Integer> getDeletedPerformanceStatusMappingIds() {
		return deletedPerformanceStatusMappingIds;
	}

	public void setDeletedPerformanceStatusMappingIds(List<Integer> deletedPerformanceStatusMappingIds) {
		this.deletedPerformanceStatusMappingIds = deletedPerformanceStatusMappingIds;
	}

	public List<Integer> getDeletedDefinitionsGlossaryIds() {
		return deletedDefinitionsGlossaryIds;
	}

	public void setDeletedDefinitionsGlossaryIds(List<Integer> deletedDefinitionsGlossaryIds) {
		this.deletedDefinitionsGlossaryIds = deletedDefinitionsGlossaryIds;
	}
}
