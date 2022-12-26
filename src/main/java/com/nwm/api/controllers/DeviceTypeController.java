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

import com.nwm.api.entities.DeviceTypeEntity;
import com.nwm.api.services.DeviceTypeService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/device-type")
public class DeviceTypeController extends BaseController {

	/**
	 * @description Get list device type
	 * @author long.pham
	 * @since 2020-11-06
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-dropdown")
	public Object getList(@RequestBody DeviceTypeEntity obj) {
		try {
			DeviceTypeService service = new DeviceTypeService();
			List data = service.getList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
