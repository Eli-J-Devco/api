/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.DashboardEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.services.DashboardService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = "https://staging.nextwavemonitoring.com")
@RestController
@ApiIgnore
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/list")
    public Object getList(@RequestBody AlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(200);
			}
			
			DashboardService service = new DashboardService();
			List data = service.getList(obj);
			TablePreferenceEntity preference = service.getPreference(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1, preference);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
    }
	
	
	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/list-actual-vs-expected")
    public Object getListActualvsExpected(@RequestBody DashboardEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(1000);
			}
			
			DashboardService service = new DashboardService();
			List data = service.getListActualvsExpected(obj);
			TablePreferenceEntity preference = service.getPreference(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1, preference);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
    }
	
	
	/**
	 * @description Get detail alert
	 * @author long.pham
	 * @since 2020-11-24
	 * @param id_site, id_customer, id_alert, current_time
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/alert-summary")
	public Object getAlertSummary(@RequestBody AlertEntity obj) {
		try {
			DashboardService service = new DashboardService();
			Object detailObj = service.getAlertSummary(obj);
			if (detailObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detailObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
}
