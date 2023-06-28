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

import com.nwm.api.entities.PortfolioEntity;
import com.nwm.api.entities.TablePreferenceEntity;
import com.nwm.api.services.PortfolioService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/portfolio")
public class PortfolioController extends BaseController {

	/**
	 * @description Get list site by employee
	 * @author long.pham
	 * @since 2021-01-20
	 * @param array id_site
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody PortfolioEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			PortfolioService service = new PortfolioService();
			List data = service.getList(obj);
			
			
			int totalRecord = service.getTotalRecord(obj);
			TablePreferenceEntity preference = service.getPreference(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord, preference);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0, null);
		}
	}
	
	/**
	 * @description Get detail alert
	 * @author long.pham
	 * @since 2020-11-24
	 * @param id_site, id_customer, id_alert, current_time
	 * @return data (status, message, array, total_row
	 */

	@PostMapping("/alert-summary")
	public Object getAlertSummary(@RequestBody PortfolioEntity obj) {
		try {
			PortfolioService service = new PortfolioService();
			Object detailObj = service.getAlertSummary(obj);
			if (detailObj != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, detailObj, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update a note of a site
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-note")
	public Object updateIsColor(@RequestBody PortfolioEntity obj) {
		try {
			PortfolioService service = new PortfolioService();
			service.updateNote(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list device by id_site
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id_site
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-device-by-site")
	public Object getListDeviceBySite(@RequestBody PortfolioEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			PortfolioService service = new PortfolioService();
			List data = service.getListDeviceBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update a note of a site
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-default-device")
	public Object updateDefaultDevice(@RequestBody PortfolioEntity obj) {
		try {
			PortfolioService service = new PortfolioService();
			service.updateDefaultDevice(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
