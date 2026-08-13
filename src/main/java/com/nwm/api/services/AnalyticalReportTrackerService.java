/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AnalyticalReportTrackerDTO;
import com.nwm.api.entities.AnalyticalReportTrackerEntity;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigActionFlagEntity;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigDTO;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigCurrentStatusEntity;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigPathForwardUpdateEntity;
import com.nwm.api.entities.AnalyticalReportTrackerGlobalConfigRuleEntity;
import com.nwm.api.entities.AnalyticalReportTrackerLogs;
import com.nwm.api.entities.AuditLog;
import com.nwm.api.utils.Lib;

@Service
public class AnalyticalReportTrackerService extends DB {
	private static final int STATUS_DRAFT = 1;
	private static final int STATUS_PAUSED = 4;
	private static final int MAX_PAUSE_REASON_LENGTH = 100;
	private static final int MAX_NOTES_LENGTH = 500;
	
	@Autowired
	AuditingLogsService logsService;

	/**
	 * @description save analytical report tracker status
	 * @author Duc-Pham
	 * @since 2026-08-04
	 */
	public AnalyticalReportTrackerDTO saveStatus(AnalyticalReportTrackerDTO obj) {
		if (obj == null || obj.getId_site() <= 0) {
			return null;
		}
		
		AnalyticalReportTrackerEntity entity = new AnalyticalReportTrackerEntity(obj);

		Integer status = entity.getStatus();
		if (status == null || status.intValue() < STATUS_DRAFT || status.intValue() > STATUS_PAUSED) {
			return null;
		}

		String pauseReason = entity.getPause_reason() == null ? "" : entity.getPause_reason().trim();
		String notes = entity.getNotes() == null ? "" : entity.getNotes().trim();
		if (status.intValue() != STATUS_PAUSED) {
			pauseReason = "";
			notes = "";
		} else if (pauseReason.length() == 0) {
			return null;
		}
		if (pauseReason.length() > MAX_PAUSE_REASON_LENGTH || notes.length() > MAX_NOTES_LENGTH) {
			return null;
		}

		entity.setStatus(status);
		entity.setPause_reason(pauseReason);
		entity.setNotes(notes);

		try {
			boolean hasReportId = entity.getId() != null && entity.getId().intValue() > 0;

			if (hasReportId) update("AnalyticalReportTracker.updateStatus", entity);
			else insert("AnalyticalReportTracker.insertStatus", entity);

			return new AnalyticalReportTrackerDTO(entity);
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.saveStatus", ex);
			return null;
		}
	}

	/**
	 * @description get tracker summary list
	 * @author Minh Le
	 * @since 2026-08-06
	 */
	public List<AnalyticalReportTrackerDTO> getTrackerSummaryList(Map<String, Object> params) {
		try {
			List<AnalyticalReportTrackerEntity> data = Optional.ofNullable(queryForList("AnalyticalReportTracker.getTrackerSummaryList", params)).orElse(new ArrayList<AnalyticalReportTrackerEntity>());
			Object count = queryForObject("AnalyticalReportTracker.countTrackerSummaryList", params);
			return data.stream().map(AnalyticalReportTrackerDTO::new).collect(Collectors.toList());
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.getTrackerSummaryList", ex);
			return null;
		}
	}

	/**
	 * @description count total tracker summary
	 * @author Minh Le
	 * @since 2026-08-06
	 */
	public Object countTotalTrackerSummary(Map<String, Object> params) {
		try {
			Object count = queryForObject("AnalyticalReportTracker.countTrackerSummaryList", params);
			return count;
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.getTrackerSummaryList", ex);
			return null;
		}
	}
	
	/**
	 * @description Get logs
	 * @author Hung.Bui
	 * @since 2026-08-07
	 * @param id
	 */
	public List<AuditLog> getLogs(int id) {
		try {
			List<AnalyticalReportTrackerLogs> logs = Optional.ofNullable(queryForList("AnalyticalReportTracker.getLogs", id)).orElse(new ArrayList<>());
			return logsService.getLogDifferences(logs, null);
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}

	/**
	 * @description Get global config detail
	 * @author Duc-Pham
	 * @since 2026-08-11
	 */
	public AnalyticalReportTrackerGlobalConfigDTO getGlobalConfigDetail() {
		try {
			AnalyticalReportTrackerGlobalConfigDTO data = new AnalyticalReportTrackerGlobalConfigDTO();
			List<AnalyticalReportTrackerGlobalConfigActionFlagEntity> actionFlags = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigActionFlagList")).orElse(new ArrayList<>());
			List<AnalyticalReportTrackerGlobalConfigCurrentStatusEntity> currentStatuses = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigCurrentStatusList")).orElse(new ArrayList<>());
			List<AnalyticalReportTrackerGlobalConfigPathForwardUpdateEntity> pathForwardUpdates = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigPathForwardUpdateList")).orElse(new ArrayList<>());
			List<AnalyticalReportTrackerGlobalConfigRuleEntity> performanceRules = Optional.ofNullable(queryForList("AnalyticalReportTracker.getGlobalConfigRuleList")).orElse(new ArrayList<>());

			data.setActionFlags(actionFlags);
			data.setCurrentStatuses(currentStatuses);
			data.setPathForwardUpdates(pathForwardUpdates);
			data.setPerformanceRules(performanceRules);
			if (!actionFlags.isEmpty()) data.setModified_by(actionFlags.get(0).getModified_by());
			else if (!currentStatuses.isEmpty()) data.setModified_by(currentStatuses.get(0).getModified_by());
			else if (!pathForwardUpdates.isEmpty()) data.setModified_by(pathForwardUpdates.get(0).getModified_by());
			else if (!performanceRules.isEmpty()) data.setModified_by(performanceRules.get(0).getModified_by());
			return data;
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.getGlobalConfigDetail", ex);
			return null;
		}
	}

	/**
	 * @description Save global config
	 * @author Duc-Pham
	 * @since 2026-08-11
	 */
	public AnalyticalReportTrackerGlobalConfigDTO saveGlobalConfig(AnalyticalReportTrackerGlobalConfigDTO obj) {
		if (obj == null || obj.getModified_by() == null || obj.getModified_by().intValue() <= 0) {
			return null;
		}

		SqlSession session = this.beginTransaction();
		try {
			if (obj.getActionFlags() != null) {
				if (obj.getDeletedActionFlagIds() != null && obj.getDeletedActionFlagIds().size() > 0) session.delete("AnalyticalReportTracker.deleteGlobalConfigActionFlags", obj.getDeletedActionFlagIds());
				if (obj.getActionFlags().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigActionFlags", obj);
			}
			if (obj.getCurrentStatuses() != null) {
				if (obj.getDeletedCurrentStatusIds() != null && obj.getDeletedCurrentStatusIds().size() > 0) session.delete("AnalyticalReportTracker.deleteGlobalConfigCurrentStatuses", obj.getDeletedCurrentStatusIds());
				if (obj.getCurrentStatuses().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigCurrentStatuses", obj);
			}
			if (obj.getPathForwardUpdates() != null) {
				if (obj.getDeletedPathForwardUpdateIds() != null && obj.getDeletedPathForwardUpdateIds().size() > 0) session.delete("AnalyticalReportTracker.deleteGlobalConfigPathForwardUpdates", obj.getDeletedPathForwardUpdateIds());
				if (obj.getPathForwardUpdates().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigPathForwardUpdates", obj);
			}
			if (obj.getPerformanceRules() != null) {
				if (obj.getDeletedPerformanceRuleIds() != null && obj.getDeletedPerformanceRuleIds().size() > 0) session.delete("AnalyticalReportTracker.deleteGlobalConfigRules", obj.getDeletedPerformanceRuleIds());
				if (obj.getPerformanceRules().size() > 0) session.insert("AnalyticalReportTracker.insertGlobalConfigRules", obj);
			}

			session.commit();
			return getGlobalConfigDetail();
		} catch (Exception ex) {
			session.rollback();
			log.error("AnalyticalReportTracker.saveGlobalConfig", ex);
			return null;
		} finally {
			session.close();
		}
	}
}
