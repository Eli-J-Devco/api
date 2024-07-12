/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.LevitonOverviewWidgetEntity;
import com.nwm.api.services.LevitonOverviewWidgetService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;
import com.nwm.api.services.AWSService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/leviton-overview-widget")
public class LevitonOverviewWidgetController extends BaseController {
	@Autowired
	private AWSService awsService;
	
	/**
	 * @description Get list icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param {}
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody LevitonOverviewWidgetEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			LevitonOverviewWidgetService service = new LevitonOverviewWidgetService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	/**
	 * @description update icon status
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-status")
	public Object updateStatus(@RequestBody LevitonOverviewWidgetEntity obj) {
		try {
			LevitonOverviewWidgetService service = new LevitonOverviewWidgetService();
			service.updateStatus(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description delete icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/delete")
	public Object delete(@Valid @RequestBody LevitonOverviewWidgetEntity obj) {
		LevitonOverviewWidgetService service = new LevitonOverviewWidgetService();
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
	 * @description save icon
	 * @author long.pham
	 * @since 2021-03-31
	 * @param  screen_mode = 0:add, 1:edit
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody LevitonOverviewWidgetEntity obj) {
		try {
			LevitonOverviewWidgetService service = new LevitonOverviewWidgetService();
			String fileName = "";
			String saveDir = "";
			
			if (obj.getScreen_mode() == 1) {
				if(!Lib.isBlank(obj.getFile_upload())) {
					saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyOverviewWidget);
					fileName = randomAlphabetic(16);
					String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
					String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyOverviewWidget) + "/" + saveFileName);
					obj.setIcon(filePath);
				}
				
				LevitonOverviewWidgetEntity data = service.insertIcon(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					if(!Lib.isBlank(obj.getFile_upload())) {
						saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyIcons);
						fileName = randomAlphabetic(16);
						String saveFileName = Lib.uploadFromBase64(obj.getFile_upload(), fileName, saveDir);
						String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyIcons) + "/" + saveFileName);
						obj.setIcon(filePath);
					}
					
					boolean insert = service.updateIcon(obj);
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
