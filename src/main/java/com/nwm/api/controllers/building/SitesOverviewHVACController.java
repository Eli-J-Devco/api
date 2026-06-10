/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers.building;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.building.ChartConsumptionEntity;
import com.nwm.api.entities.building.HVACMappingPointEntity;
import com.nwm.api.entities.building.SitesOverviewHVACFieldChartEntity;
import com.nwm.api.entities.building.SitesOverviewHVACLayoutMapEntity;
import com.nwm.api.services.building.SitesOverviewHVACService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("building/sites-overview/hvac")
public class SitesOverviewHVACController extends BaseController {
	
	/**
	 * @description Save mapping points
	 * @author Hung.Bui
	 * @since 2025-03-22
	 * @param obj { id_hvac_layout, points }
	 * @return data (status, message, obj)
	 */

	@PostMapping("/save-mapping-points")
	public Object saveMappingPoints(@Valid @RequestBody SitesOverviewHVACLayoutMapEntity obj) {
		try {
			SitesOverviewHVACService service = new SitesOverviewHVACService();
			boolean isSucceed = service.saveMappingPoints(obj);
			return isSucceed ? this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj) : this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
		}
	}
	
	/**
	 * @description Get mapping points
	 * @author Hung.Bui
	 * @since 2025-03-22
	 * @param obj { id_hvac_layout }
	 * @return data (status, message, obj)
	 */
	@PostMapping("/mapping-points")
	public Object getMappingPoints(@RequestBody SitesOverviewHVACLayoutMapEntity obj) {
		try {
			SitesOverviewHVACService service = new SitesOverviewHVACService();
			List<HVACMappingPointEntity> data = service.getMappingPoints(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * @description Save config points
	 * @author Hung.Bui
	 * @since 2025-03-31
	 * @param obj { config_points }
	 * @return data (status, message, obj)
	 */

	@PostMapping("/save-config-points")
	public Object saveConfigPoints(@Valid @RequestBody SitesOverviewHVACLayoutMapEntity obj) {
		try {
			SitesOverviewHVACService service = new SitesOverviewHVACService();
			boolean isSucceed = service.saveConfigPoints(obj);
			return isSucceed ? this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj) : this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
		}
	}

	
	/**
	 * @description Get config points
	 * @author Hung.Bui
	 * @since 2025-03-31
	 * @param obj { id_gateway }
	 * @return data (status, message, obj)
	 */
	@PostMapping("/config-points")
	public Object getConfigPoints(@RequestBody SitesOverviewHVACLayoutMapEntity obj) {
		try {
			SitesOverviewHVACService service = new SitesOverviewHVACService();
			List<String> data = service.getConfigPoints(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * Get field chart
	 * @author Hung.Bui
	 * @since 2025-04-11
	 * @param obj { id_gateway, field_list }
	 * @return data (status, message, obj)
	 */
	@PostMapping("/field-chart")
	public Object getFieldChart(@RequestBody SitesOverviewHVACFieldChartEntity obj) {
		try {
			SitesOverviewHVACService service = new SitesOverviewHVACService();
			List<ChartConsumptionEntity> data = service.getFieldChart(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
}
