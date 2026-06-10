/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.EmailSubscribersEntity;
import com.nwm.api.services.EmailSubscribersService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.services.AWSService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/email-subscribers")
public class EmailSubscribersController extends BaseController {
	@Autowired
	private AWSService awsService;
	/**
	 * @description Get list icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-dropdown")
	public Object getListDropdown(@RequestBody EmailSubscribersEntity obj) {
		try {
			EmailSubscribersService service = new EmailSubscribersService();
			List data = service.getListDropdown(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param {}
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody EmailSubscribersEntity obj) {
		try {
			EmailSubscribersService service = new EmailSubscribersService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description update icon status
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-status")
	public Object updateStatus(@RequestBody EmailSubscribersEntity obj) {
		try {
			EmailSubscribersService service = new EmailSubscribersService();
			service.updateStatus(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody EmailSubscribersEntity obj) {
		EmailSubscribersService service = new EmailSubscribersService();
		try {
			boolean result = service.delete(obj);
			if (result) {
				if (obj.getIs_delete() == 0) {
					return this.jsonResult(true, Constants.RESTORE_SUCCESS_MSG, obj, 1);
				}
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description save icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody EmailSubscribersEntity obj) {
		try {
			EmailSubscribersService service = new EmailSubscribersService();
			if (obj.getScreen_mode() == 1) {
				EmailSubscribersEntity data = service.insertEmailSubscribers(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					boolean insert = service.updateEmailSubscribers(obj);
					if (insert == true) {
						return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
					} else {
						return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
					}
				} else {
					return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
				}
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
}
