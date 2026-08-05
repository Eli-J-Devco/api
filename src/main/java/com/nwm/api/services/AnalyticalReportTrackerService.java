/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.services;

import org.apache.ibatis.session.SqlSession;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AnalyticalReportTrackerEntity;

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
	public AnalyticalReportTrackerEntity saveStatus(AnalyticalReportTrackerEntity obj) {
		if (obj == null || obj.getId_site() <= 0) {
			return null;
		}

		Integer status = normalizeStatus(obj.getStatus());
		if (status == null) {
			return null;
		}

		String pauseReason = normalizeText(obj.getPause_reason());
		String notes = normalizeText(obj.getNotes());
		if (status.intValue() != STATUS_PAUSED) {
			pauseReason = "";
			notes = "";
		} else if (pauseReason.length() == 0) {
			return null;
		}
		if (pauseReason.length() > MAX_PAUSE_REASON_LENGTH || notes.length() > MAX_NOTES_LENGTH) {
			return null;
		}

		obj.setStatus(status);
		obj.setPause_reason(pauseReason);
		obj.setNotes(notes);

		SqlSession session = this.beginTransaction();
		if (session == null) {
			return null;
		}

		try {
			Number existingRows = (Number) session.selectOne("AnalyticalReportTracker.countBySite", obj);
			if (existingRows != null && existingRows.intValue() > 0) {
				session.update("AnalyticalReportTracker.updateStatus", obj);
			} else {
				session.insert("AnalyticalReportTracker.insertStatus", obj);
			}
			session.commit();

			try {
				AnalyticalReportTrackerEntity data =
						(AnalyticalReportTrackerEntity) session.selectOne("AnalyticalReportTracker.getDetailBySite", obj);
				return data == null ? obj : data;
			} catch (Exception ex) {
				log.error("AnalyticalReportTracker.saveStatus.getDetailBySite", ex);
				return obj;
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
	public AnalyticalReportTrackerEntity getDetailBySite(AnalyticalReportTrackerEntity obj) {
		if (obj == null || obj.getId_site() <= 0) {
			return null;
		}

		SqlSession session = this.beginTransaction();
		if (session == null) {
			return null;
		}

		try {
			AnalyticalReportTrackerEntity dataObj =
					(AnalyticalReportTrackerEntity) session.selectOne("AnalyticalReportTracker.getDetailBySite", obj);
			session.commit();
			if (dataObj == null) {
				return createDraft(obj.getId_site());
			}
			return dataObj;
		} catch (Exception ex) {
			session.rollback();
			log.error("AnalyticalReportTracker.getDetailBySite", ex);
			return null;
		} finally {
			session.close();
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
