/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers.building;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.building.SitesOverviewGasEntity;
import com.nwm.api.entities.building.SitesOverviewGasEventEntity;
import com.nwm.api.services.building.SitesOverviewGasService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("building/sites-overview/pv-production")
public class SitesOverviewPVProductionController extends BaseController {
	
	/**
	 * @description Get summary
	 * @author Hung.Bui
	 * @since 2025-02-25
	 * @return data (status, message, obj)
	 */
	@PostMapping("/events")
	public Object getEvents(@RequestBody SitesOverviewGasEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			if (!Lib.isSiteManagedByUser(authz, obj.getId())) return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
			SitesOverviewGasService service = new SitesOverviewGasService();
			obj.setId_device_type(new int[] {1,3,7,9});
			List<SitesOverviewGasEventEntity> data = service.getEvents(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
}
