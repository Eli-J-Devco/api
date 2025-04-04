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

import com.nwm.api.entities.BreakerPanelEntity;
import com.nwm.api.services.BreakerPanelService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/breaker-panel")
public class BreakerPanelController extends BaseController {

	/**
	 * @description Get list breaker panel
	 * @author long.pham
	 * @since 2025-02-20
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody BreakerPanelEntity obj) {
		try {
			BreakerPanelService service = new BreakerPanelService();
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
	public Object getListManage(@RequestBody BreakerPanelEntity obj) {
		try {
			BreakerPanelService service = new BreakerPanelService();
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
//	public Object updateStatus(@RequestBody BreakerPanelEntity obj) {
//		try {
//			BreakerPanelService service = new BreakerPanelService();
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
	public Object delete(@Valid @RequestBody BreakerPanelEntity obj) {
		BreakerPanelService service = new BreakerPanelService();
		try {
			boolean result = service.delete(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_BREAKER_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_BREAKER_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_BREAKER_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description save error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody BreakerPanelEntity obj) {
		try {
			BreakerPanelService service = new BreakerPanelService();
			
			if (obj.getScreen_mode() == 1) {
				BreakerPanelEntity data = service.insertBreakerPanel(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_BREAKER_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_BREAKER_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					boolean insert = service.updateBreakerPanel(obj);
					if (insert == true) {
						return this.jsonResult(true, Constants.UPDATE_BREAKER_SUCCESS_MSG, obj, 1);
					} else {
						return this.jsonResult(false, Constants.UPDATE_BREAKER_ERROR_MSG, null, 0);
					}
				} else {
					return this.jsonResult(false, Constants.UPDATE_BREAKER_ERROR_MSG, null, 0);
				}
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_BREAKER_ERROR_MSG, e, 0);
		}
	}
}
