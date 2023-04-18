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

import com.nwm.api.entities.DateFormatEntity;
import com.nwm.api.entities.TimeZoneEntity;
import com.nwm.api.services.TimeZoneService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/time-zone")
public class TimeZoneController extends BaseController {

	/**
	 * @description Get list time zone
	 * @author long.pham
	 * @since 2020-10-30
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody TimeZoneEntity obj) {
		try {
			TimeZoneService service = new TimeZoneService();
			List data = service.getList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list date format
	 * @author long.pham
	 * @since 2023-04-17
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/dropdownlist-date-format")
	public Object getListDateFormat(@RequestBody DateFormatEntity obj) {
		try {
			TimeZoneService service = new TimeZoneService();
			List data = service.getListDateFormat(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
