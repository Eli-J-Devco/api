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

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteQueryEntity;
import com.nwm.api.services.SiteQueryService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/site-query")
public class SiteQueryController extends BaseController {
	
	/**
	 * @description Get detail site 
	 * @author long.pham
	 * @since 2021-03-12
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/detail")
	public Object getDetailSite(@RequestBody SiteQueryEntity obj) {
		try {
			if(obj.getHash_id() != null) {
				obj.setId_site(Integer.parseInt(secretCard.decrypt(obj.getHash_id())));
			}
			
			SiteQueryService service = new SiteQueryService();
			SiteQueryEntity getDetail = service.getDetail(obj);
			
			
			
			if (getDetail.getId() > 0) {
				getDetail.setHash_id_site(secretCard.encrypt(obj.getHash_id()));
				List listDevice = service.getListDeviceByIdSite(obj);
				getDetail.setList_device(listDevice);
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
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
			if(obj.getHash_id()  == null) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			obj.setId(Integer.parseInt(secretCard.decrypt(obj.getHash_id())));
			
			SiteQueryService service = new SiteQueryService();
			DeviceEntity getDetail = service.getDeviceDetail(obj);
			
			
			if (getDetail.getId() > 0) {
				String idSite =  String.valueOf(getDetail.getId_site());
				getDetail.setHash_site_id(secretCard.encrypt(idSite).toLowerCase());
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss a");
				Date date = new Date();
				Calendar calendar = Calendar.getInstance(); 
				calendar.setTime(date);
				getDetail.setLast_attempt(sdf.format(calendar.getTime()));
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
