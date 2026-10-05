package com.nwm.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BatchJobDeviceWorkHourService extends DB {
    private static final int MAX_SITE_THREADS = 10;

    private final ThreadPoolExecutor siteExecutor = createSiteExecutor();
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final Map<String, List<Integer>> hostnameToServerIds = new HashMap<>();
    @Autowired
    DeviceService deviceService;
    @Autowired
    CustomerViewService customerViewService;
    @Autowired
    SitesAnalyticsService sitesAnalyticsService;

    @Value("${server1.name}")
    private String serverName1;

    @Value("${server2.name}")
    private String serverName2;

    @Value("${server1.run_on_id}")
    private List<Integer> server1RunOnId;

    @Value("${server2.run_on_id}")
    private List<Integer> server2RunOnId;

    @Value("${server.local.run_on_id}")
    private List<Integer> serverLocalRunOnId;

    @PostConstruct
    public void init() {
        String localhost = Lib.getPrivateIP();
        hostnameToServerIds.put(serverName1, server1RunOnId);
        hostnameToServerIds.put(serverName2, server2RunOnId);

        if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
            hostnameToServerIds.put(localhost, serverLocalRunOnId);
        }
    }

    private static ThreadPoolExecutor createSiteExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                MAX_SITE_THREADS, MAX_SITE_THREADS,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }
        );
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down siteExecutor thread pool");
        siteExecutor.shutdown();
        try {
            if (!siteExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                log.warn("siteExecutor did not terminate in the specified time.");
                siteExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.error("Interrupted during siteExecutor shutdown", e);
            siteExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void startJob(String type) {
//        startJob1(type);
//        return;
        log.info("===== BatchJobDeviceWorkHourService START =====");
        if (!isRunning.compareAndSet(false, true)) {
            log.info("===== BatchJobDeviceWorkHourService SKIPPED - already running =====");
            return;
        }
        try {
            String hostname = Lib.getPrivateIP();
            log.info("Hostname: " + hostname);

            List<Integer> serverIds = hostnameToServerIds.get(hostname);
            if (serverIds == null || serverIds.isEmpty()) {
                log.info("No serverIds found for hostname: " + hostname + " - SKIP");
                return;
            }
            final int LIMIT = 50;
            int offset = 0;
            log.info("ServerIds: " + serverIds);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            if (Lib.isBlank(type)) {
                type = Constants.WorkHourFieldEnum.TODAY.getType();
            }
            log.info("===== BatchJobDeviceWorkHourService BEGIN PROCESS =====");
//            List<Integer> id_sites = new ArrayList<>();
//            id_sites.add(673);
            while (true) {
                Map<String, Object> params = new HashMap<>();
                params.put("limit", LIMIT);
                params.put("offset", offset);
//                params.put("id_sites", id_sites);
                params.put("serverIds", serverIds);
                List<SiteEntity> listSites = queryForList("DeviceWorkHour.getSites", params);
                if (listSites == null || listSites.isEmpty()) {
                    break;
                }
                List<Map<String, Object>> batchParams = new ArrayList<>();
                double avgIrradianceWorkHour = 0;

                for (SiteEntity site : listSites) {
                    int workHour = 0;
                    String timeZone = site.getTime_zone_value();
                    ZoneId zoneId = ZoneId.of(timeZone);
                    ZonedDateTime now = ZonedDateTime.now(zoneId);
                    ZonedDateTime startDateTime = now.toLocalDate().atStartOfDay(zoneId);
                    ZonedDateTime endDateTime = now;

                    Constants.ChartingGranularity chartingGranularity = Constants.ChartingGranularity._1_HOUR;
                    Constants.ChartingFilter chartingFilter = Constants.ChartingFilter.TODAY;
                    long dayDiff = 1;
                    if (type.equalsIgnoreCase(Constants.WorkHourFieldEnum.YESTERDAY.getType())) {
                        startDateTime = now.toLocalDate().minusDays(1).atStartOfDay(zoneId);
                        endDateTime = now.toLocalDate().minusDays(1).atTime(23, 59, 59).atZone(zoneId);
                    } else if (type.equalsIgnoreCase(Constants.WorkHourFieldEnum.YESTERDAY_LASTWEEK.getType())) {
	                    LocalDate yesterday = ZonedDateTime.now(zoneId).toLocalDate().minusDays(1);
                        startDateTime = yesterday.withDayOfMonth(1).minusMonths(1).atStartOfDay(zoneId);
                        endDateTime = yesterday.atTime(23, 59, 59).atZone(zoneId);
                        dayDiff = ChronoUnit.DAYS.between(startDateTime, endDateTime);
                    }
                    String start = startDateTime.format(formatter);
                    String end = endDateTime.format(formatter);
                    Constants.UploadingDataIntervals siteUploadingInterval = Constants.UploadingDataIntervals.fromValue(site.getData_send_time());

                    DevicesByTypeEntity devices = deviceService.getDevicesBySite(site);
                    List<DeviceEntity> inverterDevices = devices.getInverter();
                    List<DeviceEntity> irradianceDevices = devices.getIrradiance();
                    Map<String, double[]> irradianceStatsMap = new HashMap<>();
                    if (irradianceDevices != null && !irradianceDevices.isEmpty()) {
                        for (DeviceEntity irradianceDevice : irradianceDevices) {
                            List<ClientMonthlyDateEntity> dataIrradiance =
                                    customerViewService.getIrradianceByDevice(
                                            startDateTime.toLocalDateTime(),
                                            endDateTime.toLocalDateTime(),
                                            irradianceDevice,
                                            chartingGranularity,
                                            chartingFilter,
                                            false,
                                            siteUploadingInterval
                                    );

                            if (dataIrradiance == null || dataIrradiance.isEmpty()) {
                                continue;
                            }
                            workHour += calculateIrradianceWorkHour(dataIrradiance, irradianceStatsMap);
                        }
                        avgIrradianceWorkHour = (double) workHour / irradianceDevices.size();
                    } else {
                        List<ClientMonthlyDateEntity> dataIrradiance = getFromMeteo(site, startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        avgIrradianceWorkHour = calculateIrradianceWorkHour(dataIrradiance, irradianceStatsMap);
                    }
                    if (inverterDevices == null) {
                        continue;
                    }
                    for (DeviceEntity inverterDevice : inverterDevices) {
                        List<DeviceParameterEntity> deviceParameterEntities = inverterDevice.getParameters();
                        if (deviceParameterEntities == null || deviceParameterEntities.isEmpty()) {
                            continue;
                        }
                        DeviceParameterEntity deviceParameterEntity =
                                deviceParameterEntities.stream()
                                        .filter(item -> item.isIs_energy() && item.isIs_user_defined())
                                        .findFirst()
                                        .orElse(null);

                        if (deviceParameterEntity != null) {
                            inverterDevice.setParameter_slug(deviceParameterEntity.getSlug());
                        }
                    }
                    DeviceEntity request = new DeviceEntity();

                    request.setDataDevice(inverterDevices);
                    request.setFilterBy(type);
                    request.setStart_date(start);
                    request.setEnd_date(end);

                    request.setData_send_time(Constants.ChartingGranularity._1_HOUR.getValue());

                    List<Map<String, Object>> queryResult = sitesAnalyticsService.getChartParameterDevice(request);
                    if (queryResult == null || queryResult.isEmpty()) {
                        continue;
                    }
                    Map<Integer, DeviceEntity> deviceMap = inverterDevices.stream().collect(Collectors.toMap(DeviceEntity::getId, Function.identity()));

                    for (Map<String, Object> item : queryResult) {
                        Integer deviceId = (Integer) item.get("id");
                        DeviceEntity found = deviceMap.get(deviceId);
                        if (found == null || Lib.isBlank(found.getParameter_slug())) {
                            continue;
                        }
                        List<Map<String, Object>> chartData = (List<Map<String, Object>>) item.get("data");
                        if (chartData == null || chartData.isEmpty()) {
                            continue;
                        }
                        String parameterSlug = found.getParameter_slug();
                        int inverterWorkHour = 0;
                        for (Map<String, Object> chart : chartData) {
                            String timeObject = (String) chart.get("time_full");
                            if (Lib.isBlank(timeObject)) {
                                continue;
                            }
                            double[] irradianceStats = irradianceStatsMap.get(timeObject);

                            if (irradianceStats == null || irradianceStats[1] == 0) {
                                continue;
                            }

                            double avgIrradiance = irradianceStats[0] / irradianceStats[1];
                            if (avgIrradiance <= 100) {
                                continue;
                            }
                            Object valueObject = chart.get(parameterSlug);

                            if (valueObject == null) {
                                continue;
                            }

                            double inverterEnergy = ((Number) valueObject).doubleValue();
                            if (inverterEnergy > 0) {
                                inverterWorkHour++;
                            }
                        }
                        params = new HashMap<>();
                        Double inverterAvailability = null;
                        if (avgIrradianceWorkHour > 0) {
                            inverterAvailability = inverterWorkHour >= avgIrradianceWorkHour ? 1 : inverterWorkHour / avgIrradianceWorkHour;
                        }
                        params.put("id_device", found.getId());
                        params.put("value", inverterAvailability);
                        batchParams.add(params);
//                        insert("DeviceWorkHour.insertDeviceWorkHour", params);
                    }
                }
                if (!batchParams.isEmpty()) {
                    Map<String, Object> batchParamsWrapper = new HashMap<>();

                    batchParamsWrapper.put("field", Constants.WorkHourFieldEnum.fromType(type));
                    batchParamsWrapper.put("list",  batchParams);
                    insert("DeviceWorkHour.insertInverterAvailability", batchParamsWrapper);
//                    insert("DeviceWorkHour.insertDeviceWorkHour", batchParamsWrapper);
                }
                offset += LIMIT;
            }

        } catch (Exception e) {
            log.error("BatchJobDeviceWorkHourService.startJob", e);
        } finally {
            isRunning.set(false);
            log.info("===== BatchJobDeviceWorkHourService END =====");
        }
    }

    private int calculateIrradianceWorkHour(List<ClientMonthlyDateEntity> dataIrradiance, Map<String, double[]> irradianceStatsMap) {
        if (dataIrradiance == null || dataIrradiance.isEmpty() || irradianceStatsMap == null) {
            return 0;
        }
        int workHour = 0;
        for (ClientMonthlyDateEntity item : dataIrradiance) {
            if (Lib.isBlank(item.getTime_full())) {
                continue;
            }
            double irradiance = item.getNvm_irradiance() != null ? item.getNvm_irradiance() : 0;
            double[] stats = irradianceStatsMap.computeIfAbsent(item.getTime_full(), k -> new double[2]);
            stats[0] += irradiance;
            stats[1]++;
            if (irradiance > 100) {
                workHour++;
            }
        }
        return workHour;
    }

    private List<ClientMonthlyDateEntity> getFromMeteo(SiteEntity site, String startDate, String endDate) {
        try {
            if (site == null || site.getLat() == 0.0 || site.getLng() == 0.0 || Lib.isBlank(startDate) || Lib.isBlank(endDate)) {
                return null;
            }
            double latitude = site.getLat();
            double longitude = site.getLng();

            StringBuilder url = new StringBuilder();
            url.append("https://customer-api.open-meteo.com/v1/forecast");
            url.append("?latitude=").append(latitude);
            url.append("&longitude=").append(longitude);
            url.append("&hourly=").append("global_tilted_irradiance");
            url.append("&timezone=").append(site.getTime_zone_value());
            url.append("&start_date=").append(startDate);
            url.append("&end_date=").append(endDate);
            url.append("&apikey=").append("uHFwcW4hseLrXbuT");
            String APIURL = url.toString();
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            RestApiService restApiService = new RestApiService();
            String response = restApiService.callApi(
                    url.toString(),
                    HttpMethod.GET,
                    headers,
                    null
            );
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(
                    response,
                    new TypeReference<Map<String, Object>>() {}
            );

            if (data == null) {
                return null;
            }
            Map<String, Object> hourly = (Map<String, Object>) data.get("hourly");
            if (hourly == null) {
                return null;
            }
            List<String> times = (List<String>) hourly.get("time");
            List<Double> irradiances = (List<Double>) hourly.get("global_tilted_irradiance");
            if (times == null || times.isEmpty() || irradiances == null || irradiances.isEmpty() || irradiances.size() != times.size()) {
                return null;
            }
            List<ClientMonthlyDateEntity> result = new ArrayList<>();
            for (int i = 0; i < times.size(); i++) {
                ClientMonthlyDateEntity entity = new ClientMonthlyDateEntity();

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String time = LocalDateTime.parse(times.get(i), inputFormatter).format(outputFormatter);
                entity.setTime_full(time);
                entity.setNvm_irradiance(irradiances.get(i));
                result.add(entity);
            }
            return result;
        } catch (Exception e) {
            log.error("BatchJobDeviceWorkHourService.getFromMeteo", e);
        }
        return null;
    }
    
    /**
	 * @description Calculate Inverters Availability From first date of last month to Yesterday
	 * @author Duy.Phan
	 * @since 2026-08-07
	 */
    public void startJobInverterAvailabilityYesterdayFirstDateLastMonth() {
        log.info("===== BatchJobDeviceWorkHourService START =====");

        if (!isRunning.compareAndSet(false, true)) {
            log.info("===== BatchJobDeviceWorkHourService SKIPPED - already running =====");
            return;
        }

        try {
            String hostname = Lib.getPrivateIP();
            log.info("Hostname: " + hostname);

            List<Integer> serverIds = hostnameToServerIds.get(hostname);

            if (serverIds == null || serverIds.isEmpty()) {
                log.info("No serverIds found for hostname: " + hostname + " - SKIP");
                return;
            }

            final int LIMIT = 50;
            int offset = 0;

            log.info("===== BatchJobDeviceWorkHourService BEGIN PROCESS =====");

//            List<Integer> id_sites = new ArrayList<>();
//            id_sites.add(673);
            
            while (true) {
                Map<String, Object> params = new HashMap<>();
                params.put("limit", LIMIT);
                params.put("offset", offset);
//                params.put("id_sites", id_sites);
                params.put("serverIds", serverIds);
                List<SiteEntity> listSites = queryForList("DeviceWorkHour.getSites", params);
                if (listSites == null || listSites.isEmpty()) {
                    break;
                }
                List<Map<String, Object>> batchParams = new ArrayList<>();

                for (SiteEntity site : listSites) {
                    String timeZone = site.getTime_zone_value();
                    ZoneId zoneId = ZoneId.of(timeZone);
                    ZonedDateTime now = ZonedDateTime.now(zoneId);
                    ZonedDateTime startDateTime = now.toLocalDate().atStartOfDay(zoneId);
                    ZonedDateTime endDateTime = now;

                    Constants.ChartingGranularity chartingGranularity = Constants.ChartingGranularity._1_HOUR;
                    Constants.ChartingFilter chartingFilter = Constants.ChartingFilter.TODAY;
                    
                    LocalDate yesterday = ZonedDateTime.now(zoneId).toLocalDate().minusDays(1);
                    startDateTime = yesterday.withDayOfMonth(1).minusMonths(1).atStartOfDay(zoneId);
                    endDateTime = yesterday.atTime(23, 59, 59).atZone(zoneId);
                    
                    Constants.UploadingDataIntervals siteUploadingInterval = Constants.UploadingDataIntervals.fromValue(site.getData_send_time());

                    DevicesByTypeEntity devices = deviceService.getDevicesBySite(site);
                    List<DeviceEntity> inverterDevices = devices.getInverter();
                    List<DeviceEntity> irradianceDevices = devices.getIrradiance();
                                      
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                    //WEATHER
                    Map<String, List<Double>> weatherIrradianceByTime = new HashMap<>();             
                    //avg irradiance in hour -> calculate inverter hour work in a day (energy > 0 and irradiance > 100)
                    Map<String, Double> avgWeatherIrradianceByTime = new HashMap<>();
                    //avg irradiance in day
                    Map<LocalDate, Double> avgWeatherWorkHourMap = new HashMap<>();

                    if (irradianceDevices != null && !irradianceDevices.isEmpty()) {
                    	Map<LocalDate, Double> totalWorkHourByDate = new HashMap<>();
                        for (DeviceEntity irradianceDevice : irradianceDevices) {
                            List<ClientMonthlyDateEntity> dataIrradiance =
                                    customerViewService.getIrradianceByDevice(
                                            startDateTime.toLocalDateTime(),
                                            endDateTime.toLocalDateTime(),
                                            irradianceDevice,
                                            chartingGranularity,
                                            chartingFilter,
                                            false,
                                            siteUploadingInterval
                                    );

                            if (dataIrradiance == null
                                    || dataIrradiance.isEmpty()) {
                                continue;
                            }

                            for (ClientMonthlyDateEntity item : dataIrradiance) {
                                if (item.getTime_full() == null || item.getNvm_irradiance() == null) {
                                    continue;
                                }

                                weatherIrradianceByTime.computeIfAbsent(item.getTime_full(), k -> new ArrayList<>())
                                        .add(item.getNvm_irradiance());
                            }

                            // Calculate work hours for THIS weather station
                            Map<LocalDate, Integer> stationWorkHourByDate =new HashMap<>();
                            for (ClientMonthlyDateEntity item : dataIrradiance) {
                                if (item.getTime_full() == null || item.getNvm_irradiance() == null) {
                                    continue;
                                }

                                if (item.getNvm_irradiance() <= 100) {
                                    continue;
                                }

                                LocalDate date = LocalDateTime.parse(item.getTime_full(), dateTimeFormatter).toLocalDate();

                                stationWorkHourByDate.merge(date, 1,Integer::sum);
                            }

                            // Add this station's daily work hours -> calcalate avgWeatherWorkHourMap
                            for (LocalDate date = startDateTime.toLocalDate(); !date.isAfter(endDateTime.toLocalDate()); date = date.plusDays(1)) {
                                double stationWorkHour = stationWorkHourByDate.getOrDefault(date, 0);

                                totalWorkHourByDate.merge(date, stationWorkHour,Double::sum);
                            }
                        }
                        
                        // calcalate avgWeatherIrradianceByTime
                        for (Map.Entry<String, List<Double>> entry : weatherIrradianceByTime.entrySet()) {
                            List<Double> values = entry.getValue();

                            if (values == null || values.isEmpty()) {
                                continue;
                            }

                            double average = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                            avgWeatherIrradianceByTime.put(entry.getKey(),average);
                        }

                        // calcalate avgWeatherWorkHourMap
                        int weatherStationCount = irradianceDevices.size();
                        for (LocalDate date = startDateTime.toLocalDate(); !date.isAfter(endDateTime.toLocalDate()); date = date.plusDays(1)) {
                            double totalWorkHour = totalWorkHourByDate.getOrDefault(date, 0.0);

                            double avgWorkHour = totalWorkHour / weatherStationCount;

                            avgWeatherWorkHourMap.put(date, avgWorkHour);
                        }
                    } else {
                        String meteoStartDate = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        String meteoEndDate = endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        List<ClientMonthlyDateEntity> dataIrradiance = getFromMeteo(site, meteoStartDate, meteoEndDate);
                        
                        if (dataIrradiance != null && !dataIrradiance.isEmpty()) {            	
                            Map<LocalDate, Integer> meteoWorkHourByDate = new HashMap<>();
                            
                            for (ClientMonthlyDateEntity item :dataIrradiance) {
                                if (item.getNvm_irradiance() == null || item.getTime_full() == null) {
                                    continue;
                                }
                                
                                avgWeatherIrradianceByTime.put(item.getTime_full(), item.getNvm_irradiance());

                                if (item.getNvm_irradiance() <= 100) {
                                    continue;
                                }

                                LocalDate date = LocalDateTime.parse(item.getTime_full(), dateTimeFormatter).toLocalDate();

                                meteoWorkHourByDate.merge(date, 1, Integer::sum);
                            }


                            for (LocalDate date = startDateTime.toLocalDate(); !date.isAfter(endDateTime.toLocalDate()); date = date.plusDays(1)) {
                                double meteoWorkHour = meteoWorkHourByDate.getOrDefault(date, 0);

                                avgWeatherWorkHourMap.put(date,meteoWorkHour);
                            }
                        } else {
                            for (LocalDate date = startDateTime.toLocalDate(); !date.isAfter(endDateTime.toLocalDate()); date = date.plusDays(1)) {
                                avgWeatherWorkHourMap.put(date, 0.0);
                            }
                        }
                    }


                    // INVERTER
                    if (inverterDevices == null) {
                        continue;
                    }

                    Map<Integer, DeviceEntity> inverterDeviceMap = inverterDevices.stream().collect(Collectors.toMap(DeviceEntity::getId,device -> device,(first, second) -> first));

                    List<PerformanceDataChartItemEntity> inverterDataList = new ArrayList<>();
                    if (!inverterDevices.isEmpty()) {
                        Map<Integer, List<ClientMonthlyDateEntity>> dataByDevices = customerViewService.getEnergyByDevice(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(),inverterDevices, chartingGranularity, chartingFilter, false);

                        if (dataByDevices != null && !dataByDevices.isEmpty()) {
                            dataByDevices.forEach((deviceId, data) -> {
                                DeviceEntity device = inverterDeviceMap.get(deviceId);

                                String deviceName = device != null ? device.getDevicename() : "";

                                List<ClientMonthlyDateEntity> dataByDevice = data.stream().map(item -> {
        							ClientMonthlyDateEntity entityItem = new ClientMonthlyDateEntity();
        							entityItem.setTime_full(item.getTime_full());
        							entityItem.setChart_energy_kwh(Objects.nonNull(item.getChart_energy_kwh()) ? BigDecimal.valueOf(item.getChart_energy_kwh()).setScale(0, RoundingMode.HALF_UP).doubleValue() : null);
        							
        							return entityItem;
        						}).collect(Collectors.toList());
        						
        						inverterDataList.add(new PerformanceDataChartItemEntity(dataByDevice, deviceId, "Inverter", "kWh", deviceName));
                            });
                        }
                    }

	                 // INVERTER WORK HOURS BY DAY 
	                 Map<Integer, Map<LocalDate, Integer>> inverterWorkHourByDay = new HashMap<>();
	                 for (PerformanceDataChartItemEntity inverterData : inverterDataList) {
	                     Integer deviceId = inverterData.getId_device();
	
	                     if (deviceId == null) {
	                         continue;
	                     }
	
	                     List<ClientMonthlyDateEntity> data = inverterData.getData_energy();
	                     Map<LocalDate, Integer> workHourByDate =new HashMap<>();
	
	                     if (data != null) {
	                         for (ClientMonthlyDateEntity item : data) {
	                             if (item.getTime_full() == null || item.getChart_energy_kwh() == null) {
	                                 continue;
	                             }
	
	                             Double avgWeatherIrradiance = avgWeatherIrradianceByTime.get(item.getTime_full());
	
	                             if (avgWeatherIrradiance == null) {
	                                 continue;
	                             }
	
	                             if (avgWeatherIrradiance > 100 && item.getChart_energy_kwh() > 0) {
	                                 LocalDate date = LocalDateTime.parse(item.getTime_full(),dateTimeFormatter).toLocalDate();
	
	                                 workHourByDate.merge(date,1,Integer::sum);
	                             }
	                         }
	                     }
	
	                     inverterWorkHourByDay.put(deviceId, workHourByDate);
	                 }

                    // DAILY AVAILABILITY OF INVERTERS
	                Map<Integer, List<Double>> availabilityByInverter = new HashMap<>();
                	for (Map.Entry<Integer, Map<LocalDate, Integer>> entry : inverterWorkHourByDay.entrySet()) {
                	    Integer deviceId =entry.getKey();

                	    Map<LocalDate, Integer> workHourByDay = entry.getValue();
                	    List<Double> dailyAvailability = new ArrayList<>();

                	    for (LocalDate date = startDateTime.toLocalDate(); !date.isAfter(endDateTime.toLocalDate()); date = date.plusDays(1)) {

                	        double inverterWorkHour = workHourByDay.getOrDefault(date, 0);
                	        double avgWeatherWorkHour = avgWeatherWorkHourMap.getOrDefault(date, 0.0);

                	        double availability;
                	        if (avgWeatherWorkHour == 0.0) {
                	            availability = 0.0;
                	        } else if (inverterWorkHour >= avgWeatherWorkHour) {
                	            availability = 100.0;
                	        } else {
                	        	availability = (double) Math.round((inverterWorkHour / avgWeatherWorkHour) * 100.0);
                	        }

                	        dailyAvailability.add(availability);
                	    }

                	    availabilityByInverter.put(deviceId, dailyAvailability);
                	}


                    // AVERAGE AVAILABILITY PER INVERTER
                	for (Map.Entry<Integer, List<Double>> entry : availabilityByInverter.entrySet()) {
	                    Integer deviceId = entry.getKey();
	                    List<Double> dailyAvailability = entry.getValue();

	                    if (dailyAvailability == null || dailyAvailability.isEmpty()) {
	                        continue;
	                    }
	
	                    double avgAvailability = dailyAvailability.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
	                    avgAvailability = BigDecimal.valueOf(avgAvailability).setScale(0, RoundingMode.HALF_UP).doubleValue();
	
	                    Map<String, Object> item =new HashMap<>();
	                    item.put("id_device", deviceId);
	                    item.put("value", avgAvailability / 100.0);
	                    batchParams.add(item);
	                }
                }
                if (!batchParams.isEmpty()) {
                    Map<String, Object> batchParamsWrapper = new HashMap<>();

                    batchParamsWrapper.put("field", Constants.WorkHourFieldEnum.YESTERDAY_FIRST_DATE_LAST_MONTH.getField());
                    batchParamsWrapper.put("list",  batchParams);
                    insert("DeviceWorkHour.insertInverterAvailability", batchParamsWrapper);
                }
                offset += LIMIT;
            }

        } catch (Exception e) {
            log.error("BatchJobDeviceWorkHourService.startJob", e);

        } finally {
            isRunning.set(false);
            log.info("===== BatchJobDeviceWorkHourService END =====");
        }
    }
}
