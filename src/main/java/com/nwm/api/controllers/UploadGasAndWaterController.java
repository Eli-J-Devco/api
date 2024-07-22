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

import com.nwm.api.entities.UploadWaterAndGasEntity;
import com.nwm.api.services.UploadWaterAndGasService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/uploads")
public class UploadGasAndWaterController extends BaseController {
	
	/**
	 * @description upload data from N3uron
	 * @author Hung.Bui
	 * @since 2024-05-21
	 * @params obj { serial_number, id_device, parameters: List<DeviceParameterWaterAndGasEntity> }
	 */
	@PostMapping("/water-or-gas")
	public Object n3euronUploads(@RequestBody UploadWaterAndGasEntity obj) {
		try {
			UploadWaterAndGasService service = new UploadWaterAndGasService();
			if (!service.waterAndGasUploads(obj)) return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}