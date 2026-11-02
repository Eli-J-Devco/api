package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.entities.TroubleshootingMainMeterConfigEntity;
import com.nwm.api.services.TroubleshootingMainMeterConfigService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/troubleshooting/main-meter-config")
public class TroubleshootingMainMeterConfigController extends BaseController {
	@PostMapping("/get")
	public Object get(@RequestBody TroubleshootingMainMeterConfigEntity request) {
		if (request.getId_site() <= 0) {
			return jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
		TroubleshootingMainMeterConfigEntity data = new TroubleshootingMainMeterConfigService()
				.getBySite(request.getId_site());
		return jsonResult(true, Constants.GET_SUCCESS_MSG, data, data == null ? 0 : 1);
	}

	@PostMapping("/save")
	public Object save(@RequestBody TroubleshootingMainMeterConfigEntity config) {
		boolean saved = new TroubleshootingMainMeterConfigService().save(config);
		return saved
				? jsonResult(true, Constants.SAVE_SUCCESS_MSG, config, 1)
				: jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
	}
}
