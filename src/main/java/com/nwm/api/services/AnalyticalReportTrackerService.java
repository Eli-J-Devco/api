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
	private static final String STATUS_DRAFT = "Draft";
	private static final String STATUS_PAUSED = "Paused";
	private static final String STATUS_SUBMITTED = "Submitted";
	private static final String STATUS_SENT = "Sent";
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

		String status = normalizeStatus(obj.getStatus());
		if (status == null) {
			return null;
		}

		String pauseReason = normalizeText(obj.getPause_reason());
		String notes = normalizeText(obj.getNotes());
		if (!STATUS_PAUSED.equals(status)) {
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

	private String normalizeStatus(String status) {
		String normalizedStatus = normalizeText(status);
		if (normalizedStatus.length() == 0) {
			return null;
		}
		if (STATUS_DRAFT.equalsIgnoreCase(normalizedStatus)) {
			return STATUS_DRAFT;
		}
		if (STATUS_PAUSED.equalsIgnoreCase(normalizedStatus)) {
			return STATUS_PAUSED;
		}
		if (STATUS_SUBMITTED.equalsIgnoreCase(normalizedStatus)) {
			return STATUS_SUBMITTED;
		}
		if (STATUS_SENT.equalsIgnoreCase(normalizedStatus)) {
			return STATUS_SENT;
		}

		return null;
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
