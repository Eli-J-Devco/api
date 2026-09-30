package com.nwm.api.controllers;

import java.util.List;

import com.nwm.api.entities.DeviceEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.services.StringMonitoringService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/string-monitoring")
public class StringMonitoringController extends BaseController {
	@Autowired
	private StringMonitoringService service;

	/** Selection for the ConfigurePopup device list and parameter dropdowns. */
	@PostMapping("/devices")
	public Object getDevices(@RequestBody DeviceEntity obj) {
		try {
			List<DeviceEntity> data = service.getInverterDevices(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error("StringMonitoring.getDevices", e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}
}