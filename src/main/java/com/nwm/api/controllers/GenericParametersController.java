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

import com.nwm.api.entities.GenericParameterEntity;
import com.nwm.api.services.GenericParametersService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/generic-parameters")
public class GenericParametersController extends BaseController {
	
	/**
	 * @description Get generic parameters list
	 * @author Hung.Bui
	 * @since 2024-09-06
	 * @param empty
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list")
	public Object getGenericParametersList(@RequestBody GenericParameterEntity obj) {
		try {
			GenericParametersService service = new GenericParametersService();
			List data = service.getList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get paginated generic parameters list
	 * @author Hung.Bui
	 * @since 2024-09-11
	 * @param { limit, offset }
	 * @return data (status, message, array, total_row)
	 */
	@PostMapping("/list-paginated")
	public Object getPaginatedList(@RequestBody GenericParameterEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			GenericParametersService service = new GenericParametersService();
			List data = service.getPaginatedList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
	
	/**
	 * @description save
	 * @author Hung.Bui
	 * @since 2024-09-11
	 * @param screen_mode = 0:add, 1:edit
	 */
	@PostMapping("/save")
	public Object save(@Valid @RequestBody GenericParameterEntity obj) {
		try {
			GenericParametersService service = new GenericParametersService();
			
			if (obj.getScreen_mode() == 1) {
				GenericParameterEntity data = service.insert(obj);
				return data != null ? this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1) : this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			} else if (obj.getScreen_mode() == 2) {
				boolean insert = service.update(obj);
				return insert ? this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1) : this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
			} else {
				return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete
	 * @author Hung.Bui
	 * @since 2024-09-11
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody GenericParameterEntity obj) {
		GenericParametersService service = new GenericParametersService();
		try {
			boolean result = service.delete(obj);
			return result ? this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1) : this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
}
