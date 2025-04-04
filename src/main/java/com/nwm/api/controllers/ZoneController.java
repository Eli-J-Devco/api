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

import com.nwm.api.entities.ZoneEntity;
import com.nwm.api.services.ZoneService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/zone")
public class ZoneController extends BaseController {

	/**
	 * @description Get list breaker panel
	 * @author long.pham
	 * @since 2025-02-20
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody ZoneEntity obj) {
		try {
			ZoneService service = new ZoneService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecordManage(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list site by id_customer
	 * @author long.pham
	 * @since 2025-02-20
	 * @param id_customer
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-manage")
	public Object getListManage(@RequestBody ZoneEntity obj) {
		try {
			ZoneService service = new ZoneService();
			List data = service.getListManage(obj);
			int totalRecord = service.getTotalRecordManage(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description update error level status
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 * @return data (status, message, array, total_row
	 */
//	@PostMapping("/update-status")
//	public Object updateStatus(@RequestBody ZoneEntity obj) {
//		try {
//			ZoneService service = new ZoneService();
//			service.updateStatus(obj);
//			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
//		} catch (Exception e) {
//			// log error
//			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
//		}
//	}
	
	/**
	 * @description delete error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody ZoneEntity obj) {
		ZoneService service = new ZoneService();
		try {
			boolean result = service.delete(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_ZONE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ZONE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ZONE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description save zone
	 * @author long.pham
	 * @since 2025-04-01
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody ZoneEntity obj) {
		try {
			ZoneService service = new ZoneService();
			
			if (obj.getScreen_mode() == 1) {
				ZoneEntity data = service.insertZone(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_ZONE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ZONE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					boolean insert = service.updateZone(obj);
					if (insert == true) {
						return this.jsonResult(true, Constants.UPDATE_ZONE_SUCCESS_MSG, obj, 1);
					} else {
						return this.jsonResult(false, Constants.UPDATE_ZONE_ERROR_MSG, null, 0);
					}
				} else {
					return this.jsonResult(false, Constants.UPDATE_ZONE_ERROR_MSG, null, 0);
				}
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ZONE_ERROR_MSG, e, 0);
		}
	}
}
