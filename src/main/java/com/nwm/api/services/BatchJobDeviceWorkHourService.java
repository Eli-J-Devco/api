package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
            while (true) {
                Map<String, Object> params = new HashMap<>();
                params.put("limit", LIMIT);
                params.put("offset", offset);
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
                    String start = startDateTime.format(formatter);
                    String end = endDateTime.format(formatter);
                    Constants.ChartingGranularity chartingGranularity = Constants.ChartingGranularity._1_HOUR;
                    Constants.ChartingFilter chartingFilter = Constants.ChartingFilter.TODAY;
                    if (type.equalsIgnoreCase(Constants.WorkHourFieldEnum.YESTERDAY.getType())) {
                        startDateTime = now.toLocalDate().minusDays(1).atStartOfDay(zoneId);
                        endDateTime = now.toLocalDate().minusDays(1).atTime(23, 59, 59).atZone(zoneId);
                    } else if (type.equalsIgnoreCase(Constants.WorkHourFieldEnum.YESTERDAY_LASTWEEK.getType())) {
                        startDateTime = now.toLocalDate().minusWeeks(1).minusDays(1).atStartOfDay(zoneId);
                        endDateTime = now.toLocalDate().minusDays(1).atTime(23, 59, 59).atZone(zoneId);
                    }

                    Constants.UploadingDataIntervals siteUploadingInterval = Constants.UploadingDataIntervals.fromValue(site.getData_send_time());

                    DevicesByTypeEntity devices = deviceService.getDevicesBySite(site);
                    List<DeviceEntity> inverterDevices = devices.getInverter();
                    List<DeviceEntity> irradianceDevices = devices.getIrradiance();
                    Map<String, double[]> irradianceStatsMap = new HashMap<>();
                    if (irradianceDevices != null) {
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
                            int workHour = 0;//(int) dataIrradiance.stream().filter(item -> item.getNvm_irradiance() != null && item.getNvm_irradiance() > 100).count();

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
                            params = new HashMap<>();
                            params.put("id_device", irradianceDevice.getId());
                            params.put("value", workHour);
                            batchParams.add(params);
//                            insert("DeviceWorkHour.insertDeviceWorkHour", params);
                        }
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
                        params.put("id_device", found.getId());
                        params.put("value", inverterWorkHour);
                        batchParams.add(params);
//                        insert("DeviceWorkHour.insertDeviceWorkHour", params);
                    }
                }
                if (!batchParams.isEmpty()) {
                    Map<String, Object> batchParamsWrapper = new HashMap<>();

                    batchParamsWrapper.put("field", Constants.WorkHourFieldEnum.fromType(type));
                    batchParamsWrapper.put("list",  batchParams);
                    insert("DeviceWorkHour.insertDeviceWorkHour", batchParamsWrapper);
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
