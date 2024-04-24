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

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ScadaAlertEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.services.AlertService;
import com.nwm.api.services.ScadaAlertService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("scada/alert")
public class ScadaAlertController extends BaseController {
	
	/**
	 * @description Get list device type
	 * @author long.pham
	 * @since 2020-11-06
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-type")
	public Object getDeviceTypeList(@RequestBody ScadaAlertEntity obj) {
		try {
			ScadaAlertService service = new ScadaAlertService();
			List data = service.getDeviceTypeList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list time zone
	 * @author long.pham
	 * @since 2020-10-30
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-error-type")
	public Object getErrorTypeList(@RequestBody ScadaAlertEntity obj) {
		try {
			ScadaAlertService service = new ScadaAlertService();
			List data = service.getErrorTypeList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list error level
	 * @author long.pham
	 * @since 2021-01-28
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-error-level")
	public Object getErrorLevelList(@RequestBody ScadaAlertEntity obj) {
		try {
			ScadaAlertService service = new ScadaAlertService();
			List data = service.getErrorLevelList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-by-id-site")
	public Object getListDeviceByIdSite(@RequestBody ScadaAlertEntity obj) {
		try {
			ScadaAlertService service = new ScadaAlertService();
			List data = service.getListDeviceByIdSite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
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

	@PostMapping("/get-list-alert-by-id-site")
    public Object getListAlertBySiteId(@RequestBody ScadaAlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			
			ScadaAlertService service = new ScadaAlertService();
			List data = service.getListAlertBySiteId(obj);
			int totalRecord = service.getListBySiteIdTotalCount(obj);
			TablePreferenceEntity preference = service.getPreference(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord, preference);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }
	
	/**
	 * @description Get list alert by site
	 * @author long.pham
	 * @since 2020-11-16
	 * @param id_customer, id_site, start_date, end_date
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/get-data-chart")
    public Object getDataChart(@RequestBody ScadaAlertEntity obj){
		try {
			if(obj.getLimit() == 0) {
				obj.setLimit(10000);
			}
			
			ScadaAlertService service = new ScadaAlertService();
			List data = service.getDataChart(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
    }

}
