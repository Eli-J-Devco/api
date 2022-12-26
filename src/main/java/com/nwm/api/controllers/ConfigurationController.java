/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.ConfigurationEntity;
import com.nwm.api.entities.EmployeeManageEntity;
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.ConfigurationService;
import com.nwm.api.services.CustomerViewService;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.services.ErrorService;
import com.nwm.api.services.SiteService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SendMail;
import com.nwm.api.utils.Translator;

import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/configuration")
public class ConfigurationController extends BaseController {

	
	/**
	 * @description Get detail site 
	 * @author long.pham
	 * @since 2020-10-22
	 * @param id_site
	 * @return data (status, message, object, total_row
	 */

	@PostMapping("/detail")
	public Object getDetail(@RequestBody ConfigurationEntity obj) {
		try {
			ConfigurationService service = new ConfigurationService();
			if(obj.getHash_id() == null) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			
			
			ConfigurationEntity getDetail = service.getDetail(obj);
			if (getDetail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, getDetail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	

	/**
	 * @description save Employee
	 * @author long.pham
	 * @since 2021-01-06
	 * @param screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object saveConfiguration(@Valid @RequestBody ConfigurationEntity obj) {
		try {
			ConfigurationService service = new ConfigurationService();
			
			if(obj.getHash_id() == null) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			
			ConfigurationEntity data = service.insertConfiguration(obj);
			if (data != null) {
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
					
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description update row
	 * @author long.pham
	 * @since 2022-11-08
	 * @param {obj}
	 */

	@PostMapping("/update-row")
	
	public Object updateRowConfiguration(@RequestBody ConfigurationEntity obj) {
		try {
			ConfigurationService service = new ConfigurationService();
			service.updateRowConfiguration(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description delete row connfig energy expectations
	 * @author long.pham
	 * @since 2022-11-08
	 * @param object
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete-row")
	public Object deleteRow(@Valid @RequestBody ConfigurationEntity obj) {
		ConfigurationService service = new ConfigurationService();
		try {
			boolean result = service.deleteRowConfigExpectation(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description delete connfig energy expectations
	 * @author long.pham
	 * @since 2021-11-24
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody ConfigurationEntity obj) {
		ConfigurationService service = new ConfigurationService();
		try {
			boolean result = service.deleteConfigExpectation(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get data run report
	 * @author long.pham
	 * @since 2021-11-26
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/run-report")
	public Object getDataRunReport(@RequestBody ConfigurationEntity obj) {
		
		try {
			ConfigurationService service = new ConfigurationService();
			List dataThisMonthEnergy = service.getDataRunReport(obj);
			obj.setDataGenerated(dataThisMonthEnergy);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	

}
