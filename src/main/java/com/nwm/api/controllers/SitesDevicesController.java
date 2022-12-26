/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SitesDevicesEntity;
import com.nwm.api.services.SitesDevicesService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/sites-devices")
public class SitesDevicesController extends BaseController {
	
	/**
	 * @description Get detail site 
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/detail")
	public Object getDetailSite(@RequestBody SitesDevicesEntity obj) {
		try {			
			SitesDevicesService service = new SitesDevicesService();
			SitesDevicesEntity getDetail = service.getDetail(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
		} catch (Exception e) {
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
			SitesDevicesService service = new SitesDevicesService();
			List data = service.getListDeviceByIdSite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get device detail by id 
	 * @author long.pham
	 * @since 2021-03-16
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/device-detail")
	public Object getDeviceDetail(@RequestBody DeviceEntity obj) {
		try {
			
			SitesDevicesService service = new SitesDevicesService();
			DeviceEntity getDetail = service.getDeviceDetail(obj);
			
			
			if (getDetail.getId() > 0) {
				
				// converting date format for US
				Date date = new Date();
		        SimpleDateFormat sdfAmerica = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
		        TimeZone tzInAmerica = TimeZone.getTimeZone(getDetail.getTimezone());
		        sdfAmerica.setTimeZone(tzInAmerica);
				
				getDetail.setLast_attempt(sdfAmerica.format(date) + " " + getDetail.getAbbreviation_std());
				List listParameters = service.getListParameters(obj);
				getDetail.setList_parameters(listParameters);
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
}
