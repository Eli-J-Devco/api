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

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.PortfolioRankingEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.DeviceOverviewService;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

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
			int totalRecord = service.getTotalRecordByCompany(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get all site by id_employee, id_company
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-sites")
	public Object getSiteByEmployee(@RequestBody SiteEntity site) {
		try {
			DeviceOverviewService service = new DeviceOverviewService();
			List data = service.getSiteByEmployee(site);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get all site group by id_employee, id_company
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-site-groups")
	public Object getListSiteGroups(@RequestBody SiteEntity site) {
		try {
			DeviceOverviewService service = new DeviceOverviewService();
			List data = service.getListSiteGroups(site);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get alert by device id
	 * @author long.pham
	 * @since 2022-01-29
	 * @param id_employee
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-alert-by-device")
	public Object getListAlertByDevice(@RequestBody DeviceEntity site) {
		try {
			DeviceOverviewService service = new DeviceOverviewService();
			List data = service.getListAlertByDevice(site);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	/**
	 * @description Get device detail
	 * @author Duy.Phan
	 * @since 2024-08-12
	 * @param id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/get-device-detail")
	public Object getSiteDetail(@RequestBody DeviceEntity obj) {
		try {

			DeviceOverviewService service = new DeviceOverviewService();

			Object detail = service.getDeviceDetail(obj);
			
			if (detail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get device detail
	 * @author Duy.Phan
	 * @since 2024-08-12
	 * @param id_site
	 * @return data (status, message, array
	 */
	@PostMapping("/get-portfolio-alert-ranking")
	public Object getPortFolioAlertRanking(@RequestBody PortfolioRankingEntity obj, @RequestHeader(name = "Authorization") String authz) {
		try {
			obj.setIsUserNW(Lib.isUserNW(authz));
			(new EmployeeService()).getTableSort(obj);
			DeviceOverviewService service = new DeviceOverviewService();

			Object detail = service.getPortFolioAlertRanking(obj);
			
			if (detail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
}
