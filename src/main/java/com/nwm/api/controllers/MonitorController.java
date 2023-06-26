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

import com.nwm.api.entities.AccountEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.ErrorEntity;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.ErrorService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;
import javax.validation.Valid;

@RestController
@ApiIgnore
@RequestMapping("/monitor")
public class MonitorController extends BaseController {

	/**
	 * @description Get list dataloger, cell modem
	 * @author long.pham
	 * @since 2023-06-23
	 * @return data (status, message, array, total_row
	 */
	@PostMapping("/list-ssh-cell-modem-status")
	public Object getList(@RequestBody DeviceEntity obj) {
		try {
			if (obj.getLimit() == 0) {
				obj.setLimit(Constants.MAXRECORD);
			}
			DeviceService service = new DeviceService();
			List data = service.getListSshDataloggerCellModem(obj);
			int totalRecord = service.getTotalSshDataloggerCellModem(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, totalRecord);
		} catch (Exception e) {
			log.error(e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}
