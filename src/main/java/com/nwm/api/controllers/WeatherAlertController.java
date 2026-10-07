package com.nwm.api.controllers;

import com.nwm.api.entities.WeatherAlertEntity;
import com.nwm.api.services.WeatherAlertService;
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
    public List getWeatherAlertsByIdSite(@RequestBody WeatherAlertEntity weatherAlertEntity) {
        return weatherAlertService.getWeatherAlertsBySiteId(weatherAlertEntity.getIdSite());
    }
}
