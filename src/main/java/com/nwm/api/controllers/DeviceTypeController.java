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

import com.nwm.api.entities.DeviceTypeEntity;
import com.nwm.api.services.DeviceTypeService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/device-type")
public class DeviceTypeController extends BaseController {

	/**
	 * @description Get list device type
	 * @author long.pham
	 * @since 2020-11-06
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-dropdown")
	public Object getList(@RequestBody DeviceTypeEntity obj) {
		try {
			DeviceTypeService service = new DeviceTypeService();
			List data = service.getList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list device type
	 * @author duy.phan
	 * @since 2023-06-13
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-manage")
	public Object getListManage(@RequestBody DeviceTypeEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			DeviceTypeService service = new DeviceTypeService();
			List data = service.getListManage(obj);
			int totalRecord = service.getTotalRecordManage(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete device type
	 * @author duy.phan
	 * @since 2023-06-13
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody DeviceTypeEntity obj) {
		DeviceTypeService service = new DeviceTypeService();
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
	 * @description save device type
	 * @author duy.phan
	 * @since 2023-06-13
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody DeviceTypeEntity obj) {
		try {
			DeviceTypeService service = new DeviceTypeService();
			
			if (obj.getScreen_mode() == 1) {
				DeviceTypeEntity data = service.insertDeviceType(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					boolean insert = service.updateDeviceType(obj);
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
	
	/**
	 * @description update device status
	 * @author long.pham
	 * @since 2021-01-12
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-is-color")
	public Object updateIsColor(@RequestBody DeviceTypeEntity obj) {
		try {
			DeviceTypeService service = new DeviceTypeService();
			service.updateIsColor(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
