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

import com.nwm.api.entities.WidgetGroupEntity;
import com.nwm.api.services.WidgetGroupService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/widget-group")
public class WidgetGroupController extends BaseController {
	
	
	
	/**
	 * @description Get all device by id_site
	 * @author long.pham
	 * @since 2024-03-25
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-device-by-id-site")
	public Object getAllDeviceBySite(@RequestBody WidgetGroupEntity obj) {
		try {
			WidgetGroupService service = new WidgetGroupService();
			List data = service.getAllDeviceBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list field by site
	 * @author long.pham
	 * @since 2024-03-25
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-field-by-id-site")
	public Object getListFieldBySite(@RequestBody WidgetGroupEntity obj) {
		try {
			WidgetGroupService service = new WidgetGroupService();
			List data = service.getListFieldBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list widget group
	 * @author long.pham
	 * @since 2024-03-25
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-device-group-by-site")
	public Object getListDeviceGroupBySite(@RequestBody WidgetGroupEntity obj) {
		try {
			WidgetGroupService service = new WidgetGroupService();
			List data = service.getListDeviceGroupBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	

	/**
	 * @description Get list widget group
	 * @author long.pham
	 * @since 2024-03-25
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody WidgetGroupEntity obj) {
		try {
			WidgetGroupService service = new WidgetGroupService();
			List data = service.getList(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list widget parent
	 * @author long.pham
	 * @since 2024-03-25
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-root")
	public Object getListRoot(@RequestBody WidgetGroupEntity obj) {
		try {
			WidgetGroupService service = new WidgetGroupService();
			List data = service.getListRoot(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete widget group
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody WidgetGroupEntity obj) {
		WidgetGroupService service = new WidgetGroupService();
		try {
			boolean result = service.delete(obj);
			if (result) {
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description save widget group
	 * @author long.pham
	 * @since 2021-02-26
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody WidgetGroupEntity obj) {
		try {
			WidgetGroupService service = new WidgetGroupService();
			
			if (obj.getScreen_mode() == 1) {
				WidgetGroupEntity data = service.insertWidgetGroup(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					boolean insert = service.updateWidgetGroup(obj);
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
	 * @description create virtual meter
	 * @author long.pham
	 * @since 2021-02-26
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("create-virtual-meter")
	public Object createVirtualMeter(@Valid @RequestBody WidgetGroupEntity obj) {
		try {
			WidgetGroupService service = new WidgetGroupService();
			
			if (obj.getScreen_mode() == 1) {
				WidgetGroupEntity data = service.createVirtualMeter(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
}
