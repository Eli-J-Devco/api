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
import com.nwm.api.services.MapService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/map")
public class MapController extends BaseController {

	
	
	/**
	 * @description Get all site by id_customer
	 * @author long.pham
	 * @since 2021-03-03
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/all-by-employee")
	public Object getAllSiteByEmployee(@RequestBody SiteEntity site) {
		try {
			MapService service = new MapService();
			List data = service.getAllSiteByEmployee(site);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
