/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AnalyticalReportTrackerDTO;
import com.nwm.api.entities.AnalyticalReportTrackerEntity;

import java.util.List;
import java.util.Map;

@Service
public class AnalyticalReportTrackerService extends DB {
	private static final int STATUS_DRAFT = 1;
	private static final int STATUS_SUBMITTED = 2;
	private static final int STATUS_SENT = 3;
	private static final int STATUS_PAUSED = 4;
	private static final int MAX_PAUSE_REASON_LENGTH = 100;
	private static final int MAX_NOTES_LENGTH = 500;

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

		SqlSession session = this.beginTransaction();
		if (session == null) {
			return null;
		}

		try {
			boolean hasReportId = entity.getId() != null && entity.getId().intValue() > 0;
			if (hasReportId) {
				int updatedRows = session.update("AnalyticalReportTracker.updateStatus", entity);
				if (updatedRows <= 0) {
					session.rollback();
					return null;
				}
			} else {
				session.insert("AnalyticalReportTracker.insertStatus", entity);
			}
			session.commit();

			try {
				AnalyticalReportTrackerEntity data = Optional.ofNullable((AnalyticalReportTrackerEntity) session.selectOne("AnalyticalReportTracker.getDetailById", entity)).orElse(new AnalyticalReportTrackerEntity());
				return new AnalyticalReportTrackerDTO(data);
			} catch (Exception ex) {
				log.error("AnalyticalReportTracker.saveStatus.getDetail", ex);
				return new AnalyticalReportTrackerDTO();
			}
		} catch (Exception ex) {
			session.rollback();
			log.error("AnalyticalReportTracker.saveStatus", ex);
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * @description get analytical report tracker status by site
	 * @author Duc-Pham
	 * @since 2026-08-04
	 */
	public AnalyticalReportTrackerDTO getDetailById(AnalyticalReportTrackerDTO obj) {
		if (obj == null || obj.getId_site() <= 0) {
			return null;
		}

		try {
			AnalyticalReportTrackerEntity dataObj = Optional.ofNullable((AnalyticalReportTrackerEntity) queryForObject("AnalyticalReportTracker.getDetailById", obj)).orElse(createDraft(obj.getId_site()));
			return new AnalyticalReportTrackerDTO(dataObj);
		} catch (Exception ex) {
			log.error("AnalyticalReportTracker.getDetailBySite", ex);
			return null;
		}
	}

	/**
	 * @description get tracker summary list
	 * @author Minh Le
	 * @since 2026-08-06
	 */
	public List<AnalyticalReportTrackerEntity> getTrackerSummaryList(Map<String, Object> params) {
		try {
			List<AnalyticalReportTrackerEntity> data = queryForList("AnalyticalReportTracker.getTrackerSummaryList", params);
			Object count = queryForObject("AnalyticalReportTracker.countTrackerSummaryList", params);
			return data;
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

	private AnalyticalReportTrackerEntity createDraft(int idSite) {
		AnalyticalReportTrackerEntity draft = new AnalyticalReportTrackerEntity();
		draft.setId_site(idSite);
		draft.setStatus(STATUS_DRAFT);
		draft.setPause_reason("");
		draft.setNotes("");
		return draft;
	}
}
