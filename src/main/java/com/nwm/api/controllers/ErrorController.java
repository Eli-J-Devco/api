/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.services.AWSService;
import com.nwm.api.services.ErrorService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/error")
public class ErrorController extends BaseController {
	@Autowired
	private AWSService awsService;
	/**
	 * @description Get list error
	 * @author long.pham
	 * @since 2021-01-28
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list")
	public Object getList(@RequestBody ErrorEntity obj) {
		try {
			ErrorService service = new ErrorService();
			List data = service.getList(obj);
			int totalRecord = service.getTotalRecord(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	
	
	
	/**
	 * @description update error status
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-status")
	public Object updateStatus(@RequestBody ErrorEntity obj) {
		try {
			ErrorService service = new ErrorService();
			service.updateStatus(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
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
	public Object delete(@Valid @RequestBody ErrorEntity obj) {
		ErrorService service = new ErrorService();
		try {
			boolean result = service.delete(obj);
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
	public Object save(@Valid @RequestBody ErrorEntity obj) {
		try {
			ErrorService service = new ErrorService();
			List recommendTools = obj.getRecommendTools();
			List dataTools = new ArrayList();
			if (obj.getScreen_mode() == 1) {
				
				
				if(Objects.nonNull(recommendTools) && recommendTools.size() > 0) {
					for(int i = 0; i < recommendTools.size(); i++) {
						Map<String, Object> item = (Map<String, Object>) recommendTools.get(i);
						String fileName = "";
						String saveDir = "";
						if(!Lib.isBlank(item.get("file_upload"))) {
							saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyIcons);
							fileName = randomAlphabetic(16);
							String saveFileName = Lib.uploadFromBase64(item.get("file_upload").toString(), fileName, saveDir);
							String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyIcons) + "/" + saveFileName);
							item.replace("image", filePath);
						}
						
						dataTools.add(item);
					}
					
					obj.setRecommendTools(dataTools);
				}
				
				
				
				ErrorEntity data = service.insertError(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, obj, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				if (obj.getScreen_mode() == 2) {
					
					if(Objects.nonNull(recommendTools) && recommendTools.size() > 0) {
						for(int i = 0; i < recommendTools.size(); i++) {
							Map<String, Object> item = (Map<String, Object>) recommendTools.get(i);
							String fileName = "";
							String saveDir = "";
							if(!Lib.isBlank(item.get("file_upload"))) {
								saveDir = uploadRootPath() +"/"+ Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyIcons);
								fileName = randomAlphabetic(16);
								String saveFileName = Lib.uploadFromBase64(item.get("file_upload").toString(), fileName, saveDir);
								String filePath = awsService.uploadFile(saveDir + "/" + saveFileName, Lib.getReourcePropValue(Constants.appConfigFileName, Constants.uploadFilePathConfigKeyIcons) + "/" + saveFileName);
								item.replace("image", filePath);
							}
							
							dataTools.add(item);
						}
						
						obj.setRecommendTools(dataTools);
					}
					
					boolean update = service.updateError(obj);
					if (update == true) {
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
	 * @description Get list device group
	 * @author long.pham
	 * @since 2021-01-28
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-device-group")
	public Object getListDeviceGroup(@RequestBody ErrorEntity obj) {
		try {
			ErrorService service = new ErrorService();
			List data = service.getListDeviceGroup(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get list error message
	 * @author long.pham
	 * @since 2021-01-28
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-error-message")
	public Object getListErrorMessage(@RequestBody ErrorEntity obj) {
		try {
			ErrorService service = new ErrorService();
			List data = service.getListErrorMessage(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description update permission nw client
	 * @author long.pham
	 * @since 2021-02-26
	 * @param id
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/update-permission-nw-client")
	public Object updatePermissionNwClient(@RequestBody ErrorEntity obj) {
		try {
			ErrorService service = new ErrorService();
			service.updatePermissionNwClient(obj);
			return this.jsonResult(true, Constants.UPDATE_SUCCESS_MSG, obj, 1);
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
	/**
	 * @description Get error detail
	 * @author Long.Pham
	 * @since 2025-08-23
	 * @param id
	 * @return data (status, message, array
	 */
	@PostMapping("/error-detail")
	public Object getErrorDetail(@RequestBody ErrorEntity obj) {
		try {

			ErrorService service = new ErrorService();

			ErrorEntity errorDetail = service.getErrorDetail(obj);
			
			if (errorDetail != null) {
				return this.jsonResult(true, Constants.GET_SUCCESS_MSG, errorDetail, 1);
			} else {
				return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
	
}
