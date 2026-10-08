package com.nwm.api.controllers;

import com.nwm.api.entities.SiteEntity;
import com.nwm.api.services.WeatherAlertService;
import com.nwm.api.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@ApiIgnore
@RequestMapping("/weather-alert")
public class WeatherAlertController extends BaseController {
    @Autowired
    WeatherAlertService weatherAlertService;

    @PostMapping("/get-weather-alerts-by-id-site")
    public Object getWeatherAlertsByIdSite(@RequestBody SiteEntity obj) {
        try {
            List data = weatherAlertService.getWeatherAlertsBySiteId(obj);
            return this.jsonResult(true, Constants.GET_SUCCESS_MSG, data, data.size());
        } catch (Exception e) {
            log.error(e);
            return this.jsonResult(false, Constants.GET_ERROR_MSG, e, 0);
        }
    }
}
