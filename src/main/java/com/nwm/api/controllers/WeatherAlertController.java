package com.nwm.api.controllers;

import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.WeatherAlertConfigurationEntity;
import com.nwm.api.services.WeatherAlertService;
import com.nwm.api.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@ApiIgnore
@RequestMapping("/weather-alert")
public class WeatherAlertController extends BaseController {
    @Autowired
    WeatherAlertService weatherAlertService;

    @PostMapping("/get-weather-alerts")
    public Object getWeatherAlerts(@RequestBody SiteEntity obj) {
        try {
            List data = weatherAlertService.getWeatherAlerts(obj);
            Integer countAlerts = (Integer) weatherAlertService.countTotalAlerts(obj);

            Map<String, Object> response = new HashMap<>();
            response.put("alerts", data);
            response.put("total", countAlerts);

            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, response, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/get-weather-alerts-config")
    public Object getWeatherAlertsConfig(@RequestBody SiteEntity obj) {
        try {
            List data = weatherAlertService.getWeatherAlertsConfig(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }

    @PostMapping("/save-config")
    public Object saveConfig(@RequestBody List<WeatherAlertConfigurationEntity> obj) {
        try {
            Object data = weatherAlertService.saveConfig(obj);

            if(!Objects.isNull(data)) {
                return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, 1);
            } else {
                return this.jsonResult(false, Constants.GET_ERROR_MSG, data, 0);
            }
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }
}
