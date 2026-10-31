/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
*
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.TenantBillingEntity;
import com.nwm.api.services.TenantBillingService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Tenant Billing (WEBDEV-5686). Request and response: WEBDEV-5686-api.md
 * TODO: no check yet that the user may see the site / that the site has tenant meters.
 */
@RestController
@ApiIgnore
@RequestMapping("/tenant-billing")
public class TenantBillingController extends BaseController {

	@Autowired
	private TenantBillingService service;

	/**
	 * @description API 1: list of tenant meters
	 * @param id_site, start_date, end_date, keyword, status, sort_column, limit, offset
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/get-list-device")
	public Object getListDevice(@RequestBody TenantBillingEntity obj) {
		try {
			Map<String, Object> result = service.getListDevice(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, result.get("data"), (Integer) result.get("total_row"));
		} catch (IllegalArgumentException | IllegalStateException e) {
			return this.jsonResult(false, e.getMessage(), null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description API 2: Ready, Incomplete, Period usage
	 * @param id_site, start_date, end_date
	 */
	@PostMapping("/get-summary")
	public Object getSummary(@RequestBody TenantBillingEntity obj) {
		try {
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, service.getSummary(obj), 1);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return this.jsonResult(false, e.getMessage(), null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description API 3: CSV, one row per meter
	 * @param id_site, start_date, end_date
	 */
	@PostMapping("/export-all")
	public Object exportAll(@RequestBody TenantBillingEntity obj) {
		try {
			Map<String, Object> file = service.exportAll(obj);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.get("file_name") + "\"")
					.contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
					.body((byte[]) file.get("content"));
		} catch (IllegalArgumentException | IllegalStateException e) {
			return this.jsonResult(false, e.getMessage(), null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description API 4: daily kWh of one meter
	 * @param id_site, id_device, start_date, end_date
	 */
	@PostMapping("/get-chart")
	public Object getChart(@RequestBody TenantBillingEntity obj) {
		try {
			List<Map<String, Object>> data = service.getChart(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (IllegalArgumentException | IllegalStateException e) {
			return this.jsonResult(false, e.getMessage(), null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description API 5: CSV with the daily kWh of one meter
	 * @param id_site, id_device, start_date, end_date
	 */
	@PostMapping("/export-device")
	public Object exportDevice(@RequestBody TenantBillingEntity obj) {
		try {
			Map<String, Object> file = service.exportDevice(obj);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.get("file_name") + "\"")
					.contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
					.body((byte[]) file.get("content"));
		} catch (IllegalArgumentException | IllegalStateException e) {
			return this.jsonResult(false, e.getMessage(), null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description API 6: side panel header, banner, This period and Peak day
	 * @param id_site, id_device, start_date, end_date
	 */
	@PostMapping("/get-summary-device")
	public Object getSummaryDevice(@RequestBody TenantBillingEntity obj) {
		try {
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, service.getSummaryDevice(obj), 1);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return this.jsonResult(false, e.getMessage(), null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description API 7: average daily kWh
	 * @param id_site, id_device, start_date, end_date
	 */
	@PostMapping("/get-avg-daily")
	public Object getAvgDaily(@RequestBody TenantBillingEntity obj) {
		try {
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, service.getAvgDaily(obj), 1);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return this.jsonResult(false, e.getMessage(), null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

}
