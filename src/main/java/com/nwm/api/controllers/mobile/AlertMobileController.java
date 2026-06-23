package com.nwm.api.controllers.mobile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nwm.api.controllers.BaseController;
import com.nwm.api.entities.mobile.alert.AlertMobileEntity;
import com.nwm.api.entities.mobile.alert.GetAlertsDto;
import com.nwm.api.services.mobile.AlertMobileService;
import com.nwm.api.utils.Constants;

import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@ApiIgnore
@RequestMapping("/mobile/alert")
public class AlertMobileController extends BaseController {
    private final AlertMobileService service;

    public AlertMobileController() {
        this.service = new AlertMobileService();
    }

    @PostMapping("/alert-list")
    public Object GetAlertList(@RequestBody GetAlertsDto dto) {
        try {
            List<AlertMobileEntity> alerts = service.GetAlertsByUser(dto);
            Integer count = service.CountAlerts(dto);

            return this.jsonResult(true, "Get Alert List Success", alerts, count);
        } catch (Exception ex) {
            log.error(ex);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, "Get Alert List Fail ", 0);
        }

    }

    @PostMapping("/alert-summary")
    public Object getAlertSummary(@RequestBody GetAlertsDto entity) {
       try {
            Object data = service.GetAlertSummary(entity);

            return this.jsonResult(true, "Get Alert Summary Success", data, 1);
        } catch (Exception ex) {
            log.error(ex);
			return this.jsonResult(false, Constants.GET_ERROR_MSG, "Get Alert Summary Fail", 0);
        }
    }

}
