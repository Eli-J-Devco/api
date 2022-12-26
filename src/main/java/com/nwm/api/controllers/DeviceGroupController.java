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

import com.nwm.api.entities.DeviceGroupEntity;
import com.nwm.api.services.DeviceGroupService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/device-group")
public class DeviceGroupController extends BaseController {

	/**
	 * @description Get list group device
	 * @author long.pham
	 * @since 2021-01-11
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-dropdown")
	public Object getList(@RequestBody DeviceGroupEntity obj) {
		try {
			DeviceGroupService service = new DeviceGroupService();
			List data = service.getList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list group device
	 * @author long.pham
	 * @since 2021-01-11
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-dropdown-page-error")
	public Object getListDropdownForPageError(@RequestBody DeviceGroupEntity obj) {
		try {
			DeviceGroupService service = new DeviceGroupService();
			List data = service.getListDropdownForPageError(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
}
