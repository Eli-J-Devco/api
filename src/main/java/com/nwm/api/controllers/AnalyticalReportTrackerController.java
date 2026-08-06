/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.controllers;

import com.nwm.api.entities.AnalyticalReportTrackerEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AnalyticalReportTrackerDTO;
import com.nwm.api.services.AnalyticalReportTrackerService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

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
	public Object save(@RequestBody AnalyticalReportTrackerDTO obj,
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

			AnalyticalReportTrackerDTO data = service.saveStatus(obj);
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
	public Object getDetailById(@RequestBody AnalyticalReportTrackerDTO obj,
			@RequestHeader(name = "Authorization") String authz) {
		try {
			if (!isValidSite(obj) || !Lib.isSiteManagedByUser(authz, obj.getId_site())) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}

			AnalyticalReportTrackerService service = new AnalyticalReportTrackerService();
			AnalyticalReportTrackerDTO data = service.getDetailById(obj);
			if (data != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
			}
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description get track summary list
	 * @author Minh Le
	 * @since 2026-08-06
	 */
	@PostMapping("/tracker-summary-list")
	public Object getTrackerSummaryList(@RequestBody Map<String, Object> obj,
								  @RequestHeader(name = "Authorization") String authz) {
		try {
			if(Objects.isNull(authz)) return new Object();

			AnalyticalReportTrackerService service = new AnalyticalReportTrackerService();

			int page = Optional.ofNullable((Integer) obj.get("page")).orElse(1);
			int pageSize = Optional.ofNullable((Integer) obj.get("pageSize")).orElse(10);

			int offset = Math.max(0, (page - 1) * pageSize);

			Map<String, Object> filterMap = (Map<String, Object>) obj.get("filters");

			String country = (String) filterMap.get("country");
			String company = (String) filterMap.get("company");
			String site = (String) filterMap.get("site");
			String cadence = (String) filterMap.get("cadence");
			String status = (String) filterMap.get("status");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("searchKeyword", obj.get("keyword"));
			params.put("filterCountry", country);
			params.put("filterCompany", company);
			params.put("filterSite", site);
			params.put("filterCadence", cadence);
			params.put("filterStatus", status);
			params.put("pageSize", pageSize);
			params.put("offset", offset);

			List<AnalyticalReportTrackerEntity> data = service.getTrackerSummaryList(params);
			Object totalTrackerSummary = service.countTotalTrackerSummary(params);

			Map<String, Object> result = new HashMap<>();
			result.put("data", data);
			result.put("total", totalTrackerSummary);

			if (data != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, result, data.size());
			}
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	private boolean isValidSite(AnalyticalReportTrackerDTO obj) {
		return obj != null && obj.getId_site() > 0;
	}

	private boolean isValidReport(AnalyticalReportTrackerService service, AnalyticalReportTrackerDTO obj, String authz) {
		if (obj == null || obj.getId() == null || obj.getId().intValue() <= 0) {
			return true;
		}

		AnalyticalReportTrackerDTO query = new AnalyticalReportTrackerDTO();
		query.setId(obj.getId());
		AnalyticalReportTrackerDTO currentReport = service.getDetailById(query);
		return currentReport != null && Lib.isSiteManagedByUser(authz, currentReport.getId_site());
	}
}
