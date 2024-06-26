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

import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SystemEntity;
import com.nwm.api.services.SystemService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/system")
public class SystemController extends BaseController {

	/**
	 * @description Get system setting
	 * @author Hung.Bui
	 * @since 2023-08-24
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/system-setting")
	public Object getSystemSetting(@RequestBody SystemEntity obj) {
		try {
			SystemService service = new SystemService();
			List data = service.getSystemSetting(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description update system setting
	 * @author Hung.Bui
	 * @since 2023-08-24
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update")
	public Object update(@RequestBody SystemEntity obj) {
		try {
			SystemService service = new SystemService();
			service.update(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get system setting
	 * @author Hung.Bui
	 * @since 2023-08-24
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/restore-log-alert")
	
	public Object getDataSite(@RequestBody SiteEntity obj) {
		try {
			SystemService service = new SystemService();
			List data = service.getListDeviceINVMeter(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
}
