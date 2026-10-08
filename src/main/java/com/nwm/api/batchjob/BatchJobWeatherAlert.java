package com.nwm.api.batchjob;

import com.nwm.api.services.WeatherAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobWeatherAlert {
    @Autowired
    private WeatherAlertService weatherAlertService;

    public void checkWeather3DaysForecast() {
        weatherAlertService.checkWeather3DaysForecast();
    }

}
