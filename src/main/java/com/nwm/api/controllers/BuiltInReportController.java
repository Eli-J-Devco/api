/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.MonthlyProductionTrendReportEntity;
import com.nwm.api.entities.ViewReportEntity;
import com.nwm.api.entities.WeeklyDateEntity;
import com.nwm.api.services.BuiltInReportService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@ApiIgnore
@RequestMapping("/built-in-reports")
public class BuiltInReportController extends BaseController {

	/**
	 * @description Sent Mail Annual Production Trend Report
	 * @author long.pham
	 * @since 2023-07-25
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/annual-production-trend-report")
	public Object annualProductionTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			BuiltInReportService service = new BuiltInReportService();
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			List<ViewReportEntity> summarizedList = service.summarizeReport(dataObjList, WeeklyDateEntity.class);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, summarizedList, summarizedList.size());

		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * @description Sent Mail Annual Production Trend Report
	 * @author long.pham
	 * @since 2023-07-25
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-annual-production-trend-report")
	public Object sentMailAnnualTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			BuiltInReportService service = new BuiltInReportService();
			return service.sentMailAnnualTrendReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}	
	
	/**
	 * @description Sent Mail monthly Production Trend Report
	 * @author long.pham
	 * @since 2023-07-25
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/monthly-production-trend-report")
	public Object monthlyProductionTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			BuiltInReportService service = new BuiltInReportService();
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			List<ViewReportEntity> summarizedList = service.summarizeReport(dataObjList, MonthlyProductionTrendReportEntity.class);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, summarizedList, summarizedList.size());

		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	
	
	/**
	 * @description Sent Mail monthly portfolio Production Report
	 * @author long.pham
	 * @since 2023-07-25
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-monthly-production-trend-report")
	public Object sentMailMonthlyTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			BuiltInReportService service = new BuiltInReportService();
			return service.sentMailMonthlyTrendReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
		
	/**
	 * @description get weekly production trend report 
	 * @author Hung.Bui
	 * @since 2023-07-31
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/weekly-production-trend-report")
	public Object weeklyProductionTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			BuiltInReportService service = new BuiltInReportService();
			List<ViewReportEntity> dataObjList = service.getReportDataList(obj);
			List<ViewReportEntity> summarizedList = service.summarizeReport(dataObjList, WeeklyDateEntity.class);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, summarizedList, summarizedList.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * @description Sent Mail Weekly Production Trend Report
	 * @author Hung.Bui
	 * @since 2023-07-31
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/sent-mail-excel-weekly-production-trend-report")
	public Object sentMailWeeklyTrendReport(@RequestBody ViewReportEntity obj) {
		try {
			BuiltInReportService service = new BuiltInReportService();
			return service.sentMailWeeklyTrendReport(obj) ? this.jsonResult(true, Constants.SENT_EMAIL_SUCCESS, obj, 1) : this.jsonResult(false, Constants.SENT_EMAIL_ERROR, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.SENT_EMAIL_ERROR, e, 0);
		}
	}
}
