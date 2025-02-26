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
import com.nwm.api.entities.ElectricInformationEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.BuildingDashboardService;
import com.nwm.api.services.CustomerViewService;
import com.nwm.api.services.DeviceGroupService;
import com.nwm.api.services.SiteService;
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
	
	
	/**
	 * @description Get electric information
	 * @author long.pham
	 * @since 2025-02-20
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-lectric-information")
	public Object getElectricInformation(@RequestBody ElectricInformationEntity obj) {
		try {
			BuildingDashboardService service = new BuildingDashboardService();
			ElectricInformationEntity electricInfo = service.getElectricInformation(obj);
			if (electricInfo != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, electricInfo, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get electric information
	 * @author long.pham
	 * @since 2025-02-20
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-monthly-energy-usage-by-component")
	public Object getMonthlyEnergyUsageByComponent(@RequestBody ElectricInformationEntity obj) {
		try {
			BuildingDashboardService service = new BuildingDashboardService();
			ElectricInformationEntity data = service.getMonthlyEnergyUsageByComponent(obj);
			if (data != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get site detail
	 * @author Duy.Phan
	 * @since 2024-08-12
	 * @param id_site
	 * @return data (status, message, array
	 */
//	@PostMapping("/site-detail")
//	public Object getSiteDetail(@RequestBody SiteEntity obj) {
//		try {
//
//			SiteService service = new SiteService();
//
//			SiteEntity siteDetail = service.getSiteDetail(obj);
//			
//			if (siteDetail != null) {
//				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, siteDetail, 1);
//			} else {
//				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
//			}
//		} catch (Exception e) {
//			log.error(e);
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
	
	
	
	
}
