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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AnalyticalReportTrackerDTO;
import com.nwm.api.entities.AnalyticalReportTrackerEntity;
import com.nwm.api.entities.AnalyticalReportTrackerLogs;
import com.nwm.api.entities.AuditLog;

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

		Integer status = normalizeStatus(entity.getStatus());
		if (status == null) {
			return null;
		}

		String pauseReason = normalizeText(entity.getPause_reason());
		String notes = normalizeText(entity.getNotes());
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

	private Integer normalizeStatus(Integer status) {
		if (status == null) {
			return null;
		}

		int statusValue = status.intValue();
		return statusValue >= STATUS_DRAFT && statusValue <= STATUS_PAUSED ? status : null;
	}

	private String normalizeText(String value) {
		return value == null ? "" : value.trim();
	}
}
