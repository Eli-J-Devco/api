package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJobWeatherAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchConfig_WeatherAlert {
    @Autowired
    BatchJobWeatherAlert batchJobWeatherAlert;

    @Scheduled(cron = "0 0 */3 * * ?")
    public void checkWeather3DaysForecast()  {
        batchJobWeatherAlert.checkWeather3DaysForecast();
    }
}
