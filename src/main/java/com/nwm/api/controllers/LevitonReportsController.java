/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.services.LevitonReportsService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/leviton")
public class LevitonReportsController extends BaseController {
	/**
	 * @description Get daily report
	 * @author long.pham
	 * @since 2021-12-28
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-report")
	public Object getDataReport(@RequestBody ViewReportEntity obj) {
		try {
			LevitonReportsService service = new LevitonReportsService();
			ViewReportEntity dataObj = service.getDataReport(obj);
			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	@PostMapping(path = { "/sent-email-excel-leviton-report", "/sent-email-pdf-leviton-report" })
	public Object sentMail(@RequestBody ViewReportEntity obj) {
		try {
			LevitonReportsService service = new LevitonReportsService();
			return service.sentMail(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
}
