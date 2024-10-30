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

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceGroupEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.BuildingDashboardService;
import com.nwm.api.services.CustomerViewService;
import com.nwm.api.services.DeviceGroupService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/building-dashboard")
public class BuildingDashboardController extends BaseController {

	
	
	/**
	 * @description Get list load meter device
	 * @author long.pham
	 * @since 2024-10-29
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-load-meter")
	public Object getListLoadMeterDevices(@RequestBody SiteEntity obj) {
		try {
			BuildingDashboardService service = new BuildingDashboardService();
			List data = service.getListLoadMeterDevices(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list data field
	 * @author long.pham
	 * @since 2024-10-29
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-data-field")
	public Object getListDeviceDataField(@RequestBody DeviceEntity obj) {
		try {
			BuildingDashboardService service = new BuildingDashboardService();
			List data = service.getListDeviceDataField(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get dashboard chart data
	 * @author long.pham
	 * @since 2024-10-30
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-dashboard-chart-data")
	public Object getDashboardChartData(@RequestBody SiteEntity obj) {
		try {
			BuildingDashboardService service = new BuildingDashboardService();
			List dataEnergy = service.getDashboardChartData(obj);
			obj.setEnergy(dataEnergy);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
}
