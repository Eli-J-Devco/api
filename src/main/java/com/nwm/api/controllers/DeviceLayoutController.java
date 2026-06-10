/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.DeviceLayoutEntity;
import com.nwm.api.services.DeviceLayoutService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/device-layout")
public class DeviceLayoutController extends BaseController {
	
	DeviceLayoutService service = new DeviceLayoutService();
	
	/**
	 * @description Get device layout template list
	 * @author Hung.Bui
	 * @since 2024-07-18
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody DeviceLayoutEntity obj) {
		try {
			List data = service.getList(obj);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * @description Get assigned field list
	 * @author Hung.Bui
	 * @since 2024-07-18
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-assigned-field")
	public Object getListAssignedField(@RequestBody DeviceLayoutEntity obj) {
		try {
			List data = service.getListAssignedField(obj);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * @description Get device template list by device group
	 * @author Hung.Bui
	 * @since 2024-07-29
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-device-template-by-device-group")
	public Object getListDeviceTemplateByDeviceGroup(@RequestBody DeviceLayoutEntity obj) {
		try {
			List data = service.getListDeviceTemplateByDeviceGroup(obj);
			
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * @description Save device layout template
	 * @author Hung.Bui
	 * @since 2024-07-18
	 * @param screen_mode = 0:add, 1:edit
	 */
	@PostMapping("/save")
	public Object save(@Valid @RequestBody DeviceLayoutEntity obj) {
		try {
			if (obj.getScreen_mode() == 1) {
				DeviceLayoutEntity data = service.insert(obj);
				if (data == null) return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
			} else if (obj.getScreen_mode() == 2) {
				boolean insert = service.update(obj);
				if (insert == false) return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
			}
			
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
		}
	}
	
	/**
	 * @description Save field assignment for device layout
	 * @author Hung.Bui
	 * @since 2024-07-18
	 */
	@PostMapping("/save-field-assignment")
	public Object saveFieldAssignment(@Valid @RequestBody DeviceLayoutEntity obj) {
		try {
			DeviceLayoutEntity data = service.saveFieldAssignment(obj);
			
			return data == null ?
				this.jsonResult(false, Constants.SAVE_ERROR_MSG, null)
				:
				this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null);
		}
	}
	
}
