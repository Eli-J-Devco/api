/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.UploadN3uronEntity;
import com.nwm.api.services.UploadJSONService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/uploads")
public class UploadJSONController extends BaseController {
	
	/**
	 * @description upload data from N3uron
	 * @author Hung.Bui
	 * @since 2024-05-21
	 * @params obj { serial_number, id_device, parameters: List<DeviceParameterN3uronEntity> }
	 */
	@PostMapping("/n3uron")
	public Object n3euronUploads(@RequestBody UploadN3uronEntity obj) {
		try {
			UploadJSONService service = new UploadJSONService();
			if (!service.n3euronUploads(obj)) return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}