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

import com.nwm.api.entities.VendorEntity;
import com.nwm.api.services.VendorService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/vendor")
public class VendorController extends BaseController {

	/**
	 * @description Get list time zone
	 * @author long.pham
	 * @since 2020-10-30
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-dropdown")
	public Object getList(@RequestBody VendorEntity obj) {
		try {
			VendorService service = new VendorService();
			List data = service.getListDropdown(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
