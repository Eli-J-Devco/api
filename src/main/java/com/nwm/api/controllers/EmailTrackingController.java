/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.EmailTrackingEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.EmailTrackingService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/email-tracking")
public class EmailTrackingController extends BaseController {
	
	/**
	 * @description Get info setup
	 * @author long.pham
	 * @since 2024-08-27
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/get-info-setup")
	public Object getDetailSite(@RequestBody SitesDevicesEntity obj) {
		try {			
			EmailTrackingService service = new EmailTrackingService();
			SitesDevicesEntity getDetail = service.getDetail(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description save email tracking setup 
	 * @author long.pham
	 * @since 2021-01-05
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object saveEmailTrackingSetup(@RequestBody SitesDevicesEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			obj.setUpdated_by(Lib.getUserId(authz));
			EmailTrackingService service = new EmailTrackingService();
			boolean insert = service.updateSiteEmailTrackingSetup(obj);
			return insert ? this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1) : this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		}
	}
	
	
	
	/**
	 * @description Get data charting for email tracking
	 * @author long.pham
	 * @since 2024-08-27
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/get-data-charting-for-email-tracking")
	public Object getDataChartingForEmailTracking(@RequestBody EmailTrackingEntity obj) {
		try {			
			EmailTrackingService service = new EmailTrackingService();
			EmailTrackingEntity getDetail = service.getDataChartingForEmailTracking(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get data summary for email tracking
	 * @author long.pham
	 * @since 2024-08-27
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/get-data-summary-for-email-tracking")
	public Object getDataSummaryForEmailTracking(@RequestBody EmailTrackingEntity obj) {
		try {			
			EmailTrackingService service = new EmailTrackingService();
			EmailTrackingEntity getDetail = service.getDataSummaryForEmailTracking(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
			
		}
	}
	
	
	/**
	 * @description Get list alert for email tracking
	 * @author long.pham
	 * @since 2024-09-04
	 * @return data (status, message, array, total_row)
	 */
	
	@PostMapping("/get-list-alert-for-email-tracking")
    public Object getListAlertsForEmailTracking(@RequestBody AlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(1000);
			}
			
			EmailTrackingService service = new EmailTrackingService();
			List data = service.getListAlertsEmailTracking(obj);
			int totalRecord = service.getListAlertsEmailTrackingCount(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	
	
	/**
	 * @description delete queue
	 * @author long.pham
	 * @since 2024-09-10
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-alert-queue")
	public Object deleteALertQueue(@Valid @RequestBody AlertEntity obj) {
		EmailTrackingService service = new EmailTrackingService();
		try {
			boolean result = service.deleteALertQueue(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description run process queue
	 * @author long.pham
	 * @since 2024-09-10
	 * @param id
	 * @return {}
	 */
	@PostMapping("/process-alert-queue")
	public Object processALertQueue(@Valid @RequestBody AlertEntity obj) {  
		try {
			EmailTrackingService service = new EmailTrackingService();
			boolean insert = service.processALertQueue(obj);
			return insert ? this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1) : this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
		}
	}
}
