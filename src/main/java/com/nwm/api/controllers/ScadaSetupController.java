/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.ScadaSetupService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/scada-setup")
public class ScadaSetupController extends BaseController {
	
	/**
	 * @description Get detail site 
	 * @author long.pham
	 * @since 2024-04-01
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/site-detail")
	public Object getSiteDetail(@RequestBody SitesDevicesEntity obj) {
		try {			
			ScadaSetupService service = new ScadaSetupService();
			SitesDevicesEntity getDetail = service.getSiteDetail(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
