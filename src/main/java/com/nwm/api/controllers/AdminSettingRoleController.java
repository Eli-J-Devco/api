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

import com.nwm.api.entities.AdminSettingRoleEntity;
import com.nwm.api.entities.RoleScreenMapEntity;
import com.nwm.api.services.AdminSettingRoleService;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/admin-setting-role")
public class AdminSettingRoleController extends BaseController {

	/**
	 * @description Get list role
	 * @author long.pham
	 * @since 2020-12-30
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody AdminSettingRoleEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			AdminSettingRoleService service = new AdminSettingRoleService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}


	/**
	 * @description save role
	 * @author long.pham
	 * @since 2020-12-30
	 * @param name, description, screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object saveRole(@Valid @RequestBody AdminSettingRoleEntity obj) {
		try {
			if (obj.getScreen_mode() == 1) {
				AdminSettingRoleService service = new AdminSettingRoleService();
				AdminSettingRoleEntity data = service.insertRole(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					AdminSettingRoleService service = new AdminSettingRoleService();
					boolean insert = service.updateRole(obj);
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
	 * @description update permission
	 * @author long.pham
	 * @since 2020-12-31
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/update-role-permission")
	public Object updateRolePermissions( @RequestBody RoleScreenMapEntity obj) {
		try {
			AdminSettingRoleService service = new AdminSettingRoleService();
			int data = service.updateRolePermissions(obj);
			if (data == 0) {
				return this.jsonResult(false, Constants.SAVE_SUCCESS_MSG, null, 1);
			} else {
				return this.jsonResult(true, Constants.SAVE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description delete role
	 * @author long.pham
	 * @since 2020-12-30
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody AdminSettingRoleEntity obj) {
		AdminSettingRoleService service = new AdminSettingRoleService();
		try {
			boolean result = service.deleteRole(obj);
			if (result) {
				if (obj.getIs_delete() == 0) {
					return this.jsonResult(true, Constants.RESTORE_SUCCESS_MSG, obj, 1);
				}
				return this.jsonResult(true, Constants.DELETE_SUCCESS_MSG, obj, 1);
			}
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, null, 0);
		} catch (Exception e) {
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get all role
	 * @author long.pham
	 * @since 2021-01-06
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/get-all-role")
	public Object getAllRole(@RequestBody AdminSettingRoleEntity obj) {
		try {
			AdminSettingRoleService service = new AdminSettingRoleService();
			List data = service.getAllRole(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 0);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
}
