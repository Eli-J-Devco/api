package com.nwm.api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.WeatherAlertConfigurationEntity;
import com.nwm.api.entities.WeatherAlertEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.nwm.api.utils.FLLogger;

@Service
public class WeatherAlertService extends DB {
    @Autowired
    BatchJobService batchJobService;

    @Autowired
    RestApiService restApiService;

    final FLLogger log = com.nwm.api.utils.FLLogger.getLogger("service/WeatherAlertService");

    private enum WeatherAlertType {
        HIGH_WINDS("High Winds"),
        FLOOD_WARNING("Flood Warning"),
        EXPECTED_STORM("Expected Storm");

        private final String displayName;

        WeatherAlertType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public void checkWeather3DaysForecast() {
        final String weatherApiUrl = "https://api.open-meteo.com/v1/forecast";
        final String hourlyVariables = "wind_speed_10m,precipitation,cape,weather_code";
        final String forecastDays = "3";
        final String windSpeedUnit = "ms";

        final String WIND_SPEED_10M = "wind_speed_10m";
        final String PRECIPITATION = "precipitation";
        final String CAPE = "cape";
        final String WEATHER_CODE = "weather_code";

//        final String temporalResolution = "hourly_3";

        try {
            List listSite = batchJobService.getListSite(new SiteEntity());

            if (listSite == null || listSite.isEmpty()) {
                log.warn("No site found for weather alert check.");
                return;
            }

            for (Object o : listSite) {
                SiteEntity site = (SiteEntity) o;

                List<WeatherAlertConfigurationEntity> weatherAlertConfig = queryForList("WeatherAlert.getWeatherAlertConfigurationBySiteId", site.getId());

                if (weatherAlertConfig == null || weatherAlertConfig.size() == 0) {
                    continue;
                }

                Map<String, WeatherAlertConfigurationEntity> configMap = weatherAlertConfig.stream()
                        .collect(Collectors.toMap(
                                WeatherAlertConfigurationEntity::getMetric,
                                Function.identity()
                        ));

                double latitude = (double) site.getLat();
                double longitude = (double) site.getLng();
                String timeZoneValue = site.getTime_zone_value();

                if (latitude != 0L && longitude != 0L && site.getId() == 417) {
                    String siteUrl = weatherApiUrl
                            + "?latitude=" + latitude
                            + "&longitude=" + longitude
                            + "&hourly=" + hourlyVariables
                            + "&timezone=" + timeZoneValue
                            + "&forecast_days=" + forecastDays
                            + "&wind_speed_unit=" + windSpeedUnit;
//                            + "&temporal_resolution=" + temporalResolution;

                    String forecastRs = restApiService.callApi(siteUrl, HttpMethod.GET, null, null);

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonForecastRs = objectMapper.readTree(forecastRs);

                    JsonNode hourly = jsonForecastRs.get("hourly");
                    JsonNode units = jsonForecastRs.get("hourly_units");

                    JsonNode times = hourly.get("time");
                    JsonNode windSpeeds = hourly.get("wind_speed_10m");
                    JsonNode precipitations = hourly.get("precipitation");
                    JsonNode capes = hourly.get("cape");
                    JsonNode weatherCodes = hourly.get("weather_code");

                    String windUnit = units.get("wind_speed_10m").asText();
                    String precipitationUnit = units.get("precipitation").asText();
                    String capeUnit = units.get("cape").asText();
                    String weatherCodeUnit = units.get("weather_code").asText();

                    for (WeatherAlertConfigurationEntity config : configMap.values()) {
                        String metric = config.getMetric();

                        boolean alertFound = false;

                        for (int j = 0; j < times.size(); j++) {
                            double value;

                            switch (metric) {
                                case WIND_SPEED_10M:
                                    value = windSpeeds.get(j).asDouble();
                                    break;

                                case PRECIPITATION:
                                    value = precipitations.get(j).asDouble();
                                    break;

                                case CAPE:
                                    value = capes.get(j).asDouble();
                                    break;

                                default:
                                    continue;
                            }

                            if (compareMetric(value, config.getOperator(), config.getThreshold())) {
                                WeatherAlertEntity alert = new WeatherAlertEntity();

                                alert.setIdAlertConfiguration(config.getId());
                                alert.setIdSite(config.getIdSite());

                                alert.setWeatherEvent(WeatherAlertType.valueOf(config.getAlertType()).getDisplayName());

                                alert.setReading(value);
                                alert.setUnit(config.getUnit());
                                alert.setThreshold(config.getDescription());

                                alert.setForecastDate(LocalDateTime.parse(times.get(j).asText()).toLocalDate());

                                alert.setEnabled(true);
                                alertFound = true;

                                Object isInsertSuccess = insert("WeatherAlert.insertOrUpdateWeatherAlert", alert);

                                break;
                            }
                        }

                        if (!alertFound) {
                            update("WeatherAlert.disableWeatherAlert", config.getId());
                        }

                    }

                }
            }

        } catch  (Exception e) {
            log.error("Error in checkWeather3DaysForecast", e);
            e.printStackTrace();
        }
    }

    private boolean compareMetric(double value, String operator, double threshold) {
        switch (operator) {
            case ">=":
                return value >= threshold;
            case ">":
                return value > threshold;
            case "<=":
                return value <= threshold;
            case "<":
                return value < threshold;
            case "=":
                return value == threshold;
            default:
                return false;
        }
    }

    public List getWeatherAlertsBySiteId(Long idSite) {
        try {
            return queryForList("WeatherAlert.getWeatherAlertByIdSite", idSite);
        } catch (Exception e) {
            log.error("Error in getWeatherAlertsBySiteId", e);
            return new ArrayList();
        }

    }
}
