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

import com.nwm.api.entities.ScadaOverviewEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.CustomerViewService;
import com.nwm.api.services.ScadaOverviewService;
import com.nwm.api.services.SitesDevicesService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("scada/overview")
public class ScadaOverviewController extends BaseController {
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-by-id-site")
	public Object getListDeviceByIdSite(@RequestBody ScadaOverviewEntity obj) {
		try {
			ScadaOverviewService service = new ScadaOverviewService();
			List data = service.getListDeviceByIdSite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
	}
	
	/**
	 * @description Get site information
	 * @author long.pham
	 * @since 2020-12-02
	 * @param id_site, id_customer
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/get-customer-view-info")
	public Object getCustomerViewInfo(@RequestBody ScadaOverviewEntity obj) {
		try {
			ScadaOverviewService service = new ScadaOverviewService();
			Object dataObj = service.getCustomerViewInfo(obj);
			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get customer view chart data
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-chart-data")
	public Object getChartDataPerformance(@RequestBody SiteEntity obj) {
		try {
			ScadaOverviewService service = new ScadaOverviewService();
			List dataEnergy = service.getChartDataPerformance(obj);
			obj.setEnergy(dataEnergy);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

}
