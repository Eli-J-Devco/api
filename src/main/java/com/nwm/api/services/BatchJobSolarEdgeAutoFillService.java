package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Lib;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class BatchJobSolarEdgeAutoFillService extends DB {

    private static final int MAX_SITE_THREADS = 10;
    private final ThreadPoolExecutor siteExecutor = createSiteExecutor();
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

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

    private static ThreadPoolExecutor createSiteExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                MAX_SITE_THREADS, MAX_SITE_THREADS,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    private final Map<String, List<Integer>> hostnameToServerIds = new HashMap<>();

    @PostConstruct
    public void init() {
        String localhost = Lib.getPrivateIP();
        hostnameToServerIds.put(serverName1, server1RunOnId);
        hostnameToServerIds.put(serverName2, server2RunOnId);

        if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
            hostnameToServerIds.put(localhost, serverLocalRunOnId);
        }
    }

    public void startBatchJobAutoBackfillSolarEdgeAPI() {
        if (!isRunning.compareAndSet(false, true)) {
            log.info("startBatchJobAutoBackfillSolarEdgeAPI is running");
            return;
        }
        try{
            log.info("startBatchJobAutoBackfillSolarEdgeAPI START");
            String hostname = Lib.getPrivateIP();
            List<Integer> serverIds = hostnameToServerIds.get(hostname);
            if (serverIds == null || serverIds.isEmpty()) { return; }
            log.info("startBatchJobAutoBackfillSolarEdgeAPI serverIds" + serverIds.size());
            final int LIMIT = 50;
            int offset = 0;
            SolarEdgeService service = new SolarEdgeService();
            ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
            while (true) {
                Map<String, Object> params = new HashMap<>();
                params.put("limit", LIMIT);
                params.put("offset", offset);
                params.put("serverIds", serverIds);
                List<SiteEntity> listSite = (List<SiteEntity>) queryForList("SolarEdge.getListSiteSolarEdgeAuToBackFill", params);
                log.info("startBatchJobAutoBackfillSolarEdgeAPI listSite" + listSite.size());
                if (listSite == null || listSite.isEmpty()) {
                    break;
                }
                for (SiteEntity site : listSite) {
                    log.info("startBatchJobAutoBackfillSolarEdgeAPI site " + site.getId());
                    Map<String, Object> item = new HashMap<>();
                    ZonedDateTime localTime = nowUtc.withZoneSameInstant(ZoneId.of(site.getTime_zone()));
                    String startTime = localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    String endTime = localTime.plusHours(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    item.put("id", site.getId());
                    item.put("start_time", startTime);
                    item.put("end_time", endTime);
                    service.fillBackData(item);
                }
                offset += LIMIT;
            }
            log.info("startBatchJobAutoBackfillSolarEdgeAPI END");
        } catch (Exception e) {
            log.error("BatchJobService.startBatchJobAutoBackfillSolarEdgeAPI", e);
        } finally {
            isRunning.set(false);
        }
    }
}
