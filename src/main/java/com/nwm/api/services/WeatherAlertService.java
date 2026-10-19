package com.nwm.api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.entities.WeatherAlertConfigurationEntity;
import com.nwm.api.entities.WeatherAlertEntity;
import com.nwm.api.utils.Lib;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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

                List<WeatherAlertConfigurationEntity> weatherAlertConfig;

                weatherAlertConfig = queryForList("WeatherAlert.getWeatherAlertsConfig", 417);

                if (weatherAlertConfig == null || weatherAlertConfig.size() == 0) {
                    weatherAlertConfig = queryForList("WeatherAlert.getWeatherAlertsConfig", null);
                }

                //System.out.println("Site id: " + site.getId() + ", " + weatherAlertConfig);

                Map<String, WeatherAlertConfigurationEntity> configMap = weatherAlertConfig.stream()
                    .collect(Collectors.toMap(
                        WeatherAlertConfigurationEntity::getWeatherParameter,
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
                        String metric = config.getWeatherParameter();

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

                            Integer severity = null;

                            if (compareMetric(value, config.getCriticalOperator(), config.getCriticalThreshold())) {
                                severity = 1; //critical
                            } else if (compareMetric(value, config.getWarningOperator(), config.getWarningThreshold())) {
                                severity = 2; //warning
                            }

                            if (severity != null) {
                                WeatherAlertEntity alert = createWeatherAlert(site.getId(), config, value, times.get(j).asText());

                                alert.setSeverity(severity);

                                alertFound = true;

                                Object isInsertSuccess = insert("WeatherAlert.insertOrUpdateWeatherAlert", alert);

                                break;
                            }
                        }

                        if (!alertFound) {
                            Map<String, Object> params = new HashMap<>();
                            params.put("idSite", site.getId());
                            params.put("idAlertConfiguration", config.getId());

                            update("WeatherAlert.disableWeatherAlert", params);
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

    private WeatherAlertEntity createWeatherAlert(Integer siteId, WeatherAlertConfigurationEntity config, Double value, String time) {
        WeatherAlertEntity alert = new WeatherAlertEntity();

        alert.setIdAlertConfiguration(config.getId());
        alert.setIdSite(siteId);
        alert.setWeatherEvent(config.getAlertType().getDisplayName());
        alert.setReading(value);
        alert.setUnit(config.getUnit());
        alert.setForecastDate(LocalDateTime.parse(time).toLocalDate());
        alert.setEnabled(true);

        return alert;
    }

    public List getWeatherAlerts(SiteEntity obj) {
        try {
            if(Lib.isBlank(obj.getId_sites())) {
                List siteList = queryForList("Site.getSiteByEmployee", obj);
                List<Integer> siteIds = new ArrayList<>();
                if(siteList != null && !siteList.isEmpty()) {
                    for(Object s : siteList) {
                        HashMap<String, Object> siteMap = (HashMap<String, Object>) s;
                        siteIds.add((Integer) siteMap.get("id"));
                    }
                }
                obj.setId_sites(siteIds);
            }

            return queryForList("WeatherAlert.getWeatherAlerts", obj);
        } catch (Exception e) {
            log.error("Error in getWeatherAlerts", e);
            return new ArrayList();
        }
    }

    public List getWeatherAlertsConfig(SiteEntity obj) {
        List config;
        try {
            config = queryForList("WeatherAlert.getWeatherAlertsConfig", obj.getId_site());

            if(config == null || config.isEmpty()) {
                config = queryForList("WeatherAlert.getWeatherAlertsConfig", 0);
            }
            return config;
        } catch (Exception e) {
            log.error("Error in getWeatherAlertsConfig", e);
            return new ArrayList();
        }
    }

    public Object saveConfig(List<WeatherAlertConfigurationEntity> obj) {
        if(obj == null || obj.isEmpty()) {return null;}

        try {
            WeatherAlertConfigurationEntity firstItem = obj.get(0);
            Integer idSite = firstItem.getIdSite();
            if(!Objects.isNull(idSite) && idSite != 0) {
                SiteEntity site = new SiteEntity();
                site.setId_site(idSite);

                List<WeatherAlertConfigurationEntity> existConfigs = getWeatherAlertsConfig(site);
                if(!Objects.isNull(existConfigs)) {
                    return insert("WeatherAlert.insertWeatherAlertsConfig", obj);
                }
                return new ArrayList();
            } else {
                return batchUpdateWeatherAlertsConfig(obj);
            }
        } catch (Exception e) {
            log.error("Error in getWeatherAlertsConfig", e);
            e.printStackTrace();
            return new ArrayList();
        }
    }

    protected int batchUpdateWeatherAlertsConfig(List<WeatherAlertConfigurationEntity> configs) {
        try (SqlSession session = sqlMap.openSession(ExecutorType.BATCH)) {
            try {
                for (WeatherAlertConfigurationEntity config : configs) {
                    session.update("WeatherAlert.updateWeatherAlertsConfig",config);
                }

                session.flushStatements();
                session.commit();

                return configs.size();

            } catch (Exception e) {
                session.rollback();
                dbLog.error(e);
            }
        }
        return 0;
    }
}
