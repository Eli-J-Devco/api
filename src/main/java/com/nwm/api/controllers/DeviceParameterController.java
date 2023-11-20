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

import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.services.DeviceParameterService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/device-parameter")
public class DeviceParameterController extends BaseController {

	/**
	 * @description Get categorize data list
	 * @author Hung.Bui
	 * @since 2023-11-14
	 * @param  empty
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-categorize-data")
	public Object getListCategorizeData(@RequestBody DeviceParameterEntity obj) {
		try {
			DeviceParameterService service = new DeviceParameterService();
			List data = service.getListCategorizeData(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get device by device type
	 * @author long.pham
	 * @since 2020-11-06
	 * @param  array id_device
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-by-device")
	public Object getList(@RequestBody DeviceParameterEntity obj) {
		try {
			if(obj.getId_devices().size() < 0) {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
			
			DeviceParameterService service = new DeviceParameterService();
			List data = service.getListByDevice(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list device group
	 * @author Hung.Bui
	 * @since 2023-06-26
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-device-group")
	public Object getListDeviceGroup(@RequestBody DeviceParameterEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			
			DeviceParameterService service = new DeviceParameterService();
			List data = service.getListDeviceGroup(obj);
			int totalRecord = service.getTotalRecordDeviceGroup(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list device group
	 * @author Hung.Bui
	 * @since 2023-06-26
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-parameter-by-device-group")
	public Object getListParameterByDeviceGroup(@RequestBody DeviceParameterEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			
			DeviceParameterService service = new DeviceParameterService();
			List data = service.getListParameterByDeviceGroup(obj);
			int totalRecord = service.getTotalRecordParameterByDeviceGroup(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description save device parameter
	 * @author Hung.Bui
	 * @since 2023-06-26
	 * @param  screen_mode = 0:add, 1:edit
	 */
	@PostMapping("/save")
	public Object save(@Valid @RequestBody DeviceParameterEntity obj) {
		try {
			DeviceParameterService service = new DeviceParameterService();
			
//			if (obj.getScreen_mode() == 1) {
//				DeviceParameterEntity data = service.insertDeviceParameter(obj);
//				if (data != null) {
//					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
//				} else {
//					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
//				}
//			} else {
				if (obj.getScreen_mode() == 2) {
					boolean insert = service.updateDeviceParameter(obj);
					if (insert == true) {
						return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
					} else {
						return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
					}
				} else {
					return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
				}
//			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
}
