package com.nwm.api.controllers;

import java.math.BigDecimal;

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
			return jsonResult(false, Constants.GET_ERROR_MSG, "id_site is required", 0);
		}
		TroubleshootingMainMeterConfigEntity data = new TroubleshootingMainMeterConfigService()
				.getBySite(request.getId_site());
		return jsonResult(true, Constants.GET_SUCCESS_MSG, data, data == null ? 0 : 1);
	}

	@PostMapping("/save")
	public Object save(@RequestBody TroubleshootingMainMeterConfigEntity config) {
		String validationMessage = validate(config);
		if (validationMessage != null) {
			return jsonResult(false, Constants.SAVE_ERROR_MSG, validationMessage, 0);
		}
		boolean saved = new TroubleshootingMainMeterConfigService().save(config);
		return saved
				? jsonResult(true, Constants.SAVE_SUCCESS_MSG, config, 1)
				: jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
	}

	private String validate(TroubleshootingMainMeterConfigEntity config) {
		if (config.getId_site() <= 0 || config.getId_device() <= 0) return "Site and main meter are required";
		if (!hasText(config.getCurrent_phase_a_parameter()) || !hasText(config.getCurrent_phase_b_parameter())
				|| !hasText(config.getCurrent_phase_c_parameter()))
			return "Current phase parameters are required";
		if (!hasText(config.getVoltage_parameter()) || !hasText(config.getVoltage_phase_a_parameter())
				|| !hasText(config.getVoltage_phase_b_parameter()) || !hasText(config.getVoltage_phase_c_parameter()))
			return "Voltage parameters are required";
		if (!hasText(config.getPower_factor_parameter()) || !hasText(config.getPower_factor_phase_a_parameter())
				|| !hasText(config.getPower_factor_phase_b_parameter()) || !hasText(config.getPower_factor_phase_c_parameter()))
			return "Power factor parameters are required";
		if (!inRange(config.getCurrent_issue_threshold(), BigDecimal.ZERO, new BigDecimal("100")))
			return "Current threshold must be between 0 and 100";
		if (!inRange(config.getVoltage_issue_threshold(), BigDecimal.ZERO, new BigDecimal("100")))
			return "Voltage threshold must be between 0 and 100";
		if (!inRange(config.getPower_factor_issue_threshold(), BigDecimal.ZERO, BigDecimal.ONE))
			return "Power factor threshold must be between 0 and 1";
		return null;
	}

	private boolean hasText(String value) { return value != null && !value.trim().isEmpty(); }
	private boolean inRange(BigDecimal value, BigDecimal min, BigDecimal max) {
		return value != null && value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
	}
}
