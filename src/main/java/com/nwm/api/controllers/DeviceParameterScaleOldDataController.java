/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.DeviceParameterScaleOldDataEntity;
import com.nwm.api.services.DeviceParameterScaleOldDataService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/device-parameter-scale-old-data")
public class DeviceParameterScaleOldDataController extends BaseController {
	
	/**
	 * @description save device parameter scale old data and update old data
	 * @author Hung.Bui
	 * @since 2023-06-26
	 * @param  screen_mode = 0:add, 1:edit
	 */
	@PostMapping("/update-old-data")
	public Object updateDeviceParameterOldData(@Valid @RequestBody DeviceParameterScaleOldDataEntity obj) {
		try {
			DeviceParameterScaleOldDataService service = new DeviceParameterScaleOldDataService();
			
			boolean insert = service.updateDeviceParameterOldData(obj);
			if (insert == true) {
				return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
			} else {
				return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}

}
