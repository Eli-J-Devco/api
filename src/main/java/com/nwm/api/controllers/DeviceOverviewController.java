/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.DeviceOverviewService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/device-overview")
public class DeviceOverviewController extends BaseController {
	
	
	
	
	/**
	 * @description Get list device by company
	 * @author long.pham
	 * @since 2025-07-30
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/get-list-devices")
	public Object getListDeviceByIdSite(@RequestBody SitesDevicesEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			DeviceOverviewService service = new DeviceOverviewService();
			List data = service.getListDeviceByCompany(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
