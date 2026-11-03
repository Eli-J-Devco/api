package com.nwm.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nwm.api.entities.TroubleshootingConfigEntity;
import com.nwm.api.entities.TroubleshootingConfigEntity.MainMeter;
import com.nwm.api.entities.TroubleshootingConfigEntity.PqMeter;
import com.nwm.api.entities.TroubleshootingConfigEntity.SubMeterConfig;
import com.nwm.api.services.TroubleshootingConfigService;
import com.nwm.api.services.TroubleshootingConfigService.MeterType;
import com.nwm.api.utils.Constants;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/troubleshooting")
public class TroubleshootingConfigController extends BaseController {
	@PostMapping("/main-meter-config/get")
	public Object getMainMeter(@RequestBody TroubleshootingConfigEntity request) {
		return get(request.getId_site(), MeterType.MAIN);
	}

	@PostMapping("/pq-meter-config/get")
	public Object getPqMeter(@RequestBody TroubleshootingConfigEntity request) {
		return get(request.getId_site(), MeterType.PQ);
	}

	@PostMapping("/main-meter-config/save")
	public Object saveMainMeter(@RequestBody MainMeter config) {
		return save(config, MeterType.MAIN);
	}

	@PostMapping("/pq-meter-config/save")
	public Object savePqMeter(@RequestBody PqMeter config) {
		return save(config, MeterType.PQ);
	}

	@PostMapping("/sub-meter-config/get")
	public Object getSubMeter(@RequestBody TroubleshootingConfigEntity request) {
		return get(request.getId_site(), MeterType.SUB);
	}

	@PostMapping("/sub-meter-config/save")
	public Object saveSubMeter(@RequestBody SubMeterConfig config) {
		return save(config, MeterType.SUB);
	}

	private Object get(int idSite, MeterType type) {
		if (idSite <= 0) return jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		try {
			TroubleshootingConfigEntity data = new TroubleshootingConfigService().getBySite(idSite, type);
			return jsonResult(true, Constants.GET_SUCCESS_MSG, data, data == null ? 0 : 1);
		} catch (Exception ex) {
			return jsonResult(false, Constants.GET_ERROR_MSG, null, 0);
		}
	}

	private Object save(TroubleshootingConfigEntity config, MeterType type) {
		boolean saved = new TroubleshootingConfigService().save(config, type);
		return saved ? jsonResult(true, Constants.SAVE_SUCCESS_MSG, config, 1)
				: jsonResult(false, Constants.SAVE_ERROR_MSG, null, 0);
	}
}
