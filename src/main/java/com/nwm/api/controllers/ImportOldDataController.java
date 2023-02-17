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

import com.nwm.api.entities.ImportOldDataEntity;
import com.nwm.api.services.ImportOldDataService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/import-old-data")
public class ImportOldDataController extends BaseController {

	/**
	 * @description Get all site by id_employee
	 * @author long.pham
	 * @since 2022-12-21
	 * @return data 
	 */
	@PostMapping("/get-list-all-site-by-employee")
	public Object getDropdownList(@RequestBody ImportOldDataEntity obj) {
		try {
			ImportOldDataService service = new ImportOldDataService();
			List data = service.getAllSiteByEmployeeId(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description Get list site by id_customer
	 * @author long.pham
	 * @since 2020-10-09
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-list-device-by-site-id")
	public Object getList(@RequestBody ImportOldDataEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			ImportOldDataService service = new ImportOldDataService();
			List data = service.getAllDeviceBySiteId(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	

	/**
	 * @description save error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody ImportOldDataEntity obj) {
		try {
			ImportOldDataService service = new ImportOldDataService();
			ImportOldDataEntity data = service.insertImportOldData(obj);
			if (data != null) {
				if (data.getRow() > 0) {
					return this.jsonResult(false, "Error date format at row " + data.getRow(), null, 0);
				}
				return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
}
