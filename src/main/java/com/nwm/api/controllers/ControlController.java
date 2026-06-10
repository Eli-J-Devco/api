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

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.ControlService;

import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/control")
public class ControlController extends BaseController {
	
	
    
	/**
	 * @description write data to plc s7-1200
	 * @author long.pham
	 * @since 2023-03-22
	 * @param object
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/writePLCS71200")
	public Object writePLCS71200(@RequestBody SiteEntity obj) {
		try {
			ControlService service = new ControlService();
			service.writePLCS71200(obj);
			return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description read node meter from plc s7-2100
	 * @author long.pham
	 * @since 2023-03-22
	 * @param {}
	 * @return data (status, message, obj, total_row
	 */

	@PostMapping("/readNodeMeterFromPLCS71200")
	public Object getAlertDetail(@RequestBody SiteEntity obj) {
		try {
			ControlService service = new ControlService();
			Object detailObj = service.readNodeMeterFromPLCS71200(obj);
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
	
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/getListInverter")
	public Object getListInverter(@RequestBody DeviceEntity obj) {
		try {
			ControlService service = new ControlService();
			List data = service.getListInverter(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
