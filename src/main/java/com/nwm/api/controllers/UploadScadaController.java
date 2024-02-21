/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import com.nwm.api.entities.UploadScadaEntity;
import com.nwm.api.utils.Constants;

@RestController
@ApiIgnore
@RequestMapping("/uploads")
public class UploadScadaController extends BaseController {
	public static String message = "";
	/**
	 * @description upload data from IPC and insert data to database
	 * @author long.pham
	 * @since 2024-02-21
	 * @params {}
	 */

//	@SuppressWarnings("unchecked")
	@PostMapping("/data-json")
	@ResponseBody
	public Object getList(@RequestBody UploadScadaEntity obj) {
		try {
			log.error("id_channel: " + obj.getId_channel());
			log.error("id_device: " + obj.getId_device());
			log.error("datetime: " + obj.getDatetime());
			log.error("datas: " + obj.getDatas());
			
			return new UploadScadaEntity();
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}