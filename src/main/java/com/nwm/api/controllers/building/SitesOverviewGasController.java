/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers.building;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.building.SitesOverviewGasConsumptionEntity;
import com.nwm.api.entities.building.SitesOverviewGasEntity;
import com.nwm.api.services.building.SitesOverviewGasService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("building/sites-overview/gas")
public class SitesOverviewGasController extends BaseController {
	
	/**
	 * @description Get consumption
	 * @author Hung.Bui
	 * @since 2025-02-17
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/consumption")
	public Object getConsumption(@RequestBody SitesOverviewGasEntity obj) {
		try {
			SitesOverviewGasService service = new SitesOverviewGasService();
			SitesOverviewGasConsumptionEntity data = service.getConsumption(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null);
		}
	}
	
}
