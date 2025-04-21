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

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.EnergyUsageService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;
import java.util.List;

@RestController
@ApiIgnore
@RequestMapping("/energy-usage")
public class EnergyUsageController extends BaseController {

	/**
	 * @description Get chart data energy usage
	 * @author long.pham
	 * @since 2025-02-07
	 * @param {}
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-chart-data")
	public Object getChartDataEnergyUsage(@RequestBody SiteEntity obj) {
		try {
			EnergyUsageService service = new EnergyUsageService();
			List dataEnergy = service.getChartDataEnergyUsage(obj);
			obj.setEnergy(dataEnergy);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get chart data energy usage by device id
	 * @author long.pham
	 * @since 2025-02-07
	 * @param {}
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-chart-data-by-device")
	public Object getChartDataEnergyByDevice(@RequestBody DeviceEntity obj) {
		try {
			EnergyUsageService service = new EnergyUsageService();
			List dataEnergy = service.getChartDataEnergyByDevice(obj);
			obj.setDataDevice(dataEnergy);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
