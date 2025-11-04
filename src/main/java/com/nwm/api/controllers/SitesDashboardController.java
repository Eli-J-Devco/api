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

import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.DevicePanelEntity;
import com.nwm.api.entities.DeviceZoneEntity;
import com.nwm.api.entities.SiteDashboardGenerationEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.services.SitesDashboardService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/sites-dashboard")
public class SitesDashboardController extends BaseController {
	
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-alert-by-id-device")
	public Object getListAlertByIdDevice(@RequestBody AlertEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListAlertByIdDevice(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list device by site
	 * @author long.pham
	 * @since 2022-02-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-by-id-site")
	public Object getListDeviceByIdSite(@RequestBody SitesDevicesEntity obj) {
		try {
			(new EmployeeService()).getTableSort(obj);
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListDeviceByIdSite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get data 7 days by device
	 * @author long.pham
	 * @since 2025-10-08
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-data7days")
	public Object getData7Days(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getData7Days(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list pannel by id_device
	 * @author long.pham
	 * @since 2025-02-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-panel")
	public Object getListPanel(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListPanel(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list zones by id_site
	 * @author long.pham
	 * @since 2025-02-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-zones")
	public Object getListZones(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListZones(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list zones by id_site
	 * @author long.pham
	 * @since 2025-02-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-zones-graph")
	public Object getListZonesGraph(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListZonesGraph(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list zones by id_site
	 * @author long.pham
	 * @since 2025-02-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-data-lighting-graph")
	public Object getListDataLightingGraph(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListDataLightingGraph(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description Get list zones by id_site
	 * @author long.pham
	 * @since 2025-02-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-breaker-alerts")
	public Object getListBreakerAlerts(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListBreakerAlerts(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description Get list zones by id_site
	 * @author long.pham
	 * @since 2025-02-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-zones-alerts")
	public Object getListZonesAlerts(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListZonesAlerts(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list pannel by id_device
	 * @author long.pham
	 * @since 2025-02-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-data-bit-map")
	public Object getListDataBitMap(@RequestBody DevicePanelEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListDataBitMap(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list pannel by id_device
	 * @author long.pham
	 * @since 2025-02-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-breaker-unresponsive")
	public Object getListBreakerUnresponsive(@RequestBody DevicePanelEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListBreakerUnresponsive(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	

	/**
	 * @description Get list pannel by id_device
	 * @author long.pham
	 * @since 2025-02-05
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-data-zone-bit-map")
	public Object getListDataZoneBitMap(@RequestBody DeviceZoneEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListDataZoneBitMap(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get data site generation
	 * @author long.pham
	 * @since 2022-03-03
	 * @param id_site, current_time
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/get-generation")
	public Object getGeneration(@RequestBody SiteDashboardGenerationEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			Object dataObj = service.getGeneration(obj);
			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get device status list by site
	 * @author Hung.Bui
	 * @since 2023-05-05
	 * @param id_site
	 * @return data (status, message, array, total_row
	 */
	
	@PostMapping("/get-list-device-status-by-site")
	public Object getDeviceStatusListBySite(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List dataObj = service.getDeviceStatusListBySite(obj);
			if (dataObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, dataObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list data device for leviton
	 * @author long.pham
	 * @since 2024-07-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-data-device-leviton")
	public Object getListDataDeviceForLeviton(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListDataDeviceForLeviton(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list data charting for leviton
	 * @author long.pham
	 * @since 2024-07-09
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-data-charting-leviton")
	public Object getListDataChartingForLeviton(@RequestBody SitesDevicesEntity obj) {
		try {
			SitesDashboardService service = new SitesDashboardService();
			List data = service.getListDataChartingForLeviton(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
}
