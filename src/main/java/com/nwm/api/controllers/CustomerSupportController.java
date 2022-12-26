/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.controllers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.entities.CustomerSupportEntity;
import com.nwm.api.services.CustomerSupportService;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/customer-support")
public class CustomerSupportController extends BaseController {

	/**
	 * @description save customer support
	 * @author long.pham
	 * @since 2021-12-20
	 * @param object
	 */

	@PostMapping("/save")
	public Object save(@Valid @RequestBody CustomerSupportEntity obj) {
		try {
			CustomerSupportService service = new CustomerSupportService();
			String fileName = "";
			String saveDir = "";

			if (obj.getScreen_mode() == 1) {
				List fileUploads = obj.getFileUploads();
				if (fileUploads.size() > 0) {
					for (int i = 0; i < fileUploads.size(); i++) {
						Map<String, Object> objFile = (Map<String, Object>) fileUploads.get(i);
						saveDir = uploadRootPath() + "/" + Lib.getReourcePropValue(Constants.appConfigFileName,
								Constants.uploadFilePathConfigKeySupport);
						fileName = randomAlphabetic(16) + "-" + new Date().getTime();
						String saveFileName = Lib.uploadFromBase64(objFile.get("file_upload").toString(), fileName,
								saveDir);
						objFile.put("file_name", saveFileName);
					}
				}
				obj.setFileUploads(fileUploads);
				CustomerSupportEntity data = service.insertCustomerSupport(obj);
				if (data != null) {
					return this.jsonResult(true, Constants.SAVE_SUCCESS_MSG, data, 1);
				} else {
					return this.jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
				}
			} else {
				return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, null, 0);
			}
		} catch (Exception e) {
			// log error
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}

}
