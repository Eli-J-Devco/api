/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.ActivityLogsEntity;
import com.nwm.api.entities.AdminSettingGroupEntity;
import com.nwm.api.entities.EmployeeManageEntity;
import com.nwm.api.services.AWSService;
import com.nwm.api.services.AdminSettingGroupService;
import com.nwm.api.services.EmployeeService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/admin-setting-group")
public class AdminSettingGroupController extends BaseController {
	@Autowired
	private AWSService awsService;
	/**
	 * @description Get list error level
	 * @author long.pham
	 * @since 2021-01-28
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/dropdown-list")
	public Object getDropdownList(@RequestBody AdminSettingGroupEntity obj) {
		try {
			AdminSettingGroupService service = new AdminSettingGroupService();
			List data = service.getDropdownList(obj);
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
	@PostMapping("/list")
	public Object getList(@RequestBody AdminSettingGroupEntity obj) {
		try {
			AdminSettingGroupService service = new AdminSettingGroupService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description Get list role
	 * @author long.pham
	 * @since 2021-01-06
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/activity-logs")
	public Object getListActivityLogs(@RequestBody ActivityLogsEntity obj) {
		try {
			AdminSettingGroupService service = new AdminSettingGroupService();
			List data = service.getListActivityLogs(obj);
			int totalRecord = service.getTotalRecordActivityLogs(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody AdminSettingGroupEntity obj) {
		AdminSettingGroupService service = new AdminSettingGroupService();
		try {
			int totalSite = obj.isSubGroup() ? service.getTotalSiteInSubGroupById(obj) : service.getTotalSiteById(obj);
			if(totalSite > 0) {
				return this.jsonResult(false, Constants.USING_SUCCESS_MSG, null, 0);
			}
			boolean result = obj.isSubGroup() ? service.deleteSubGroup(obj) : service.delete(obj);
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
	 * @description save error level
	 * @author long.pham
	 * @since 2021-02-26
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody AdminSettingGroupEntity obj) {
		try {
			AdminSettingGroupService service = new AdminSettingGroupService();
			String fileName = "";
			String saveDir = "";
			if (obj.getScreen_mode() == 1) {
				
				if(!Lib.isBlank(obj.getFile_upload())) {
					saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyAvatar);
					fileName = randomAlphabetic(16);
					String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
					String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyAvatar) + "/" + saveFileName);
					obj.setIcon(filePath);
				}
				
				AdminSettingGroupEntity data = service.insertGroup(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					if(!Lib.isBlank(obj.getFile_upload())) {
						saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyAvatar);
						fileName = randomAlphabetic(16);
						String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
						String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyAvatar) + "/" + saveFileName);
						obj.setIcon(filePath);
					}
					
					boolean insert = service.updateGroup(obj);
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
}
