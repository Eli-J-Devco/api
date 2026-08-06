/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AnalyticalReportTrackerEntity;
import com.nwm.api.services.AnalyticalReportTrackerService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/analytical-report-tracker")
public class AnalyticalReportTrackerController extends BaseController {
	/**
	 * @description save analytical report tracker status
	 * @author Duc-Pham
	 * @since 2026-08-04
	 */
	@PostMapping("/save")
	public Object save(@RequestBody AnalyticalReportTrackerEntity obj,
			@RequestHeader(name = "Authorization") String authz) {
		try {
			if (!isValidSite(obj) || !Lib.isSiteManagedByUser(authz, obj.getId_site())) {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}

			int userId = Lib.getUserId(authz);
			if (userId <= 0) {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
			obj.setModified_by(userId);

			AnalyticalReportTrackerService service = new AnalyticalReportTrackerService();
			if (!isValidReport(service, obj, authz)) {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}

			AnalyticalReportTrackerEntity data = service.saveStatus(obj);
			if (data != null) {
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			}
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description get analytical report tracker status by site
	 * @author Duc-Pham
	 * @since 2026-08-04
	 */
	@PostMapping("/detail-by-site")
	public Object getDetailBySite(@RequestBody AnalyticalReportTrackerEntity obj,
			@RequestHeader(name = "Authorization") String authz) {
		try {
			if (!isValidSite(obj) || !Lib.isSiteManagedByUser(authz, obj.getId_site())) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			AnalyticalReportTrackerService service = new AnalyticalReportTrackerService();
			AnalyticalReportTrackerEntity data = service.getDetailBySite(obj);
			if (data != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
			}
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	private boolean isValidSite(AnalyticalReportTrackerEntity obj) {
		return obj != null && obj.getId_site() > 0;
	}

	private boolean isValidReport(AnalyticalReportTrackerService service, AnalyticalReportTrackerEntity obj, String authz) {
		if (obj == null || obj.getId() == null || obj.getId().intValue() <= 0) {
			return true;
		}

		AnalyticalReportTrackerEntity query = new AnalyticalReportTrackerEntity();
		query.setId(obj.getId());
		AnalyticalReportTrackerEntity currentReport = service.getDetailById(query);
		return currentReport != null && Lib.isSiteManagedByUser(authz, currentReport.getId_site());
	}
}
