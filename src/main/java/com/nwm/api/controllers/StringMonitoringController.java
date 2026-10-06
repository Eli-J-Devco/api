package com.nwm.api.controllers;

import java.util.List;
import java.util.Map;

import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.StringMonitoringConfiguredDeviceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	/** Get selectable devices for the String Monitoring popup. */
	@PostMapping("/get-list-device-by-site")
	public Object getListDeviceBySite(@RequestBody DeviceEntity obj) {
		try {
			List<DeviceEntity> data = service.getListDeviceBySite(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error("StringMonitoring.getListDeviceBySite", e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
		}
	}

	/**
	 * @description get configured devices
	 * @author Hung.Bui
	 * @since 2026-09-30
	 * @param request { hash_id_site }
	 */
	@PostMapping("/configured-devices")
	public Object getConfiguredDevicesBySite(@RequestBody Map<String, Object> request) {
		try {
			List<StringMonitoringConfiguredDeviceResponse> data = service.getConfiguredDevicesBySite(request);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}

	@PostMapping("/save-configuration")
	public Object saveConfiguration(@RequestBody Map<String, Object> request) {
		try {
			boolean saved = service.saveConfiguration(request);
			return this.jsonResult(saved, saved ? Constants.SAVE_SUCCESS_MSG : Constants.SAVE_ERROR_MSG, null, saved ? 1 : 0);
		} catch (Exception e) {
			log.error("StringMonitoring.saveConfiguration", e);
			return this.jsonResult(false, Constants.SAVE_ERROR_MSG, e, 0);
		}
	}

	@PostMapping("/update-configuration")
	public Object updateConfiguration(@RequestBody Map<String, Object> request) {
		try {
			boolean updated = service.updateConfiguration(request);
			return this.jsonResult(updated, updated ? Constants.UPDATE_SUCCESS_MSG : Constants.UPDATE_ERROR_MSG, null, updated ? 1 : 0);
		} catch (Exception e) {
			log.error("StringMonitoring.updateConfiguration", e);
			return this.jsonResult(false, Constants.UPDATE_ERROR_MSG, e, 0);
		}
	}

	@PostMapping("/delete-configuration")
	public Object deleteConfiguration(@RequestBody Map<String, Object> request) {
		try {
			boolean deleted = service.deleteConfiguration(request);
			return this.jsonResult(deleted, deleted ? Constants.DELETE_SUCCESS_MSG : Constants.DELETE_ERROR_MSG, null, deleted ? 1 : 0);
		} catch (Exception e) {
			log.error("StringMonitoring.deleteConfiguration", e);
			return this.jsonResult(false, Constants.DELETE_ERROR_MSG, e, 0);
		}
	}

	@PostMapping("/get-trend-analysis-chart")
	public Object getTrendAnalysisChartData(@RequestBody DeviceEntity obj) {
		try {
			List data = service.getTrendAnalysisChartData(obj);
			return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
		} catch (Exception e) {
			log.error("StringMonitoring.getTrendAnalysisChart", e);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}
}
