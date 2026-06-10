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

import com.nwm.api.entities.CompanyEntity;
import com.nwm.api.entities.SitesTrackersEntity;
import com.nwm.api.services.CompanyService;
import com.nwm.api.services.SitesTrackersService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/sites-tracker")
public class SitesTrackersController extends BaseController {
	
	/**
	 * @description Get list trackers
	 * @author duy.phan
	 * @since 2026-02-10
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-trackers")
	public Object getListTracker(@RequestBody SitesTrackersEntity obj) {
		try {
			SitesTrackersService service = new SitesTrackersService();
			List data = service.getListTracker(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get detail site 
	 * @author duy.phan
	 * @since 2026-02-10
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */
	@PostMapping("/detail-site")
	public Object getDetailSite(@RequestBody SitesTrackersEntity obj) {
		try {			
			SitesTrackersService service = new SitesTrackersService();
			SitesTrackersEntity getDetail = service.getDetail(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update warning error threshold of a site
	 * @author duy.phan
	 * @since 2026-02-10
	 * @param id_site
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-warning-error-threshold")
	public Object updateWarningErrorThreshold(@RequestBody SitesTrackersEntity obj) {
		try {
			SitesTrackersService service = new SitesTrackersService();
			service.updateWarningErrorThreshold(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
