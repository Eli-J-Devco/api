/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *********************************************************/
package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.BatchJobTableEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.FLLogger;
import com.nwm.api.utils.Lib;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CronJobAlertNoProductionsService extends DB {

	private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobAlertNoProduction");

	private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);

	private static final int MAX_SITE_THREADS = 10;
	private final ThreadPoolExecutor siteExecutor = createSiteExecutor();
	private final AtomicBoolean isRunning = new AtomicBoolean(false);


	private final ConcurrentHashMap<String, Boolean> processingDevices = new ConcurrentHashMap<>();

	private static ThreadPoolExecutor createSiteExecutor() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				MAX_SITE_THREADS, MAX_SITE_THREADS,
				60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());
		executor.allowCoreThreadTimeOut(true);
		return executor;
	}

	@Value("${server1.name}") private String serverName1;
	@Value("${server2.name}") private String serverName2;
	@Value("${server1.run_on_id}") private List<Integer> server1RunOnId;
	@Value("${server2.run_on_id}") private List<Integer> server2RunOnId;
	@Value("${server.local.run_on_id}") private List<Integer> serverLocalRunOnId;

	private final Map<String, List<Integer>> hostnameToServerIds = new HashMap<>();

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

	@PostConstruct
	public void init() {
		String localhost = Lib.getPrivateIP();
		log.info("[INIT] localhost IP: " + localhost);
		log.info("[INIT] serverName1: " + serverName1 + " | serverName2: " + serverName2);
		hostnameToServerIds.put(serverName1, server1RunOnId);
		hostnameToServerIds.put(serverName2, server2RunOnId);
		if (localhost != null && !localhost.equals(serverName1) && !localhost.equals(serverName2)) {
			hostnameToServerIds.put(localhost, serverLocalRunOnId);
			log.info("[INIT] Registered local server: " + localhost + " -> " + serverLocalRunOnId);
		}
		log.info("[INIT] hostnameToServerIds map: " + hostnameToServerIds);
	}

	public void runNoProductionCheck() {
		log.info("================================================================");
		log.info("[START] runNoProductionCheck at "
				+ ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DATE_FMT));
		log.info("================================================================");


		if (!isRunning.compareAndSet(false, true)) {
			log.warn("[SKIP] runNoProductionCheck đang chạy dở -> bỏ qua lần này (isRunning=true)");
			return;
		}

		try {
			String hostname = Lib.getPrivateIP();
			log.info("[INFO] Hostname hiện tại: " + hostname);

			List<Integer> serverIds = hostnameToServerIds.get(hostname);
			log.info("[INFO] ServerIds tìm được cho hostname [" + hostname + "]: " + serverIds);

			if (serverIds == null || serverIds.isEmpty()) {
				log.warn("[SKIP] Không tìm thấy serverIds cho hostname: " + hostname + " -> bỏ qua");
				return;
			}

			Map<String, Object> params = new HashMap<>();
			params.put("serverIds", serverIds);

			List<?> rawSites = queryForList("CronJobAlertNoProduction.getListSiteByServer", params);
			log.info("[INFO] rawSites trả về: " + (rawSites != null ? rawSites.size() : 0) + " site(s)");

			List<SiteEntity> listSites = new ArrayList<>();
			if (rawSites != null) {
				for (Object o : rawSites) {
					if (o instanceof SiteEntity) {
						listSites.add((SiteEntity) o);
					}
				}
			}

			log.info("[INFO] listSites hợp lệ: " + listSites.size() + " site(s)");
			if (listSites.isEmpty()) {
				log.warn("[SKIP] Không có site nào để xử lý với serverIds: " + serverIds);
				return;
			}

			List<Future<?>> futures = new ArrayList<>();
			for (SiteEntity site : listSites) {
				log.info("[SUBMIT] Đẩy site id=" + site.getId() + " vào thread pool");
				futures.add(siteExecutor.submit(() -> {
					Thread t = Thread.currentThread();
					String oldName = t.getName();
					t.setName(oldName + "-no-prod-site-" + site.getId());
					try {
						processSite(site);
					} catch (Exception e) {
						log.error("[ERROR] Site " + site.getId() + " lỗi trong siteExecutor: " + e.getMessage(), e);
					} finally {
						t.setName(oldName);
					}
				}));
			}

			log.info("[INFO] Đang chờ " + futures.size() + " future(s) hoàn thành...");
			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (InterruptedException ie) {
					log.error("[ERROR] Bị interrupt khi chờ siteExecutor tasks", ie);
					Thread.currentThread().interrupt();
					break;
				} catch (ExecutionException ee) {
					log.error("[ERROR] ExecutionException trong siteExecutor task: " + ee.getMessage(), ee);
				}
			}

		} catch (Exception e) {
			log.error("[ERROR] runNoProductionCheck exception: " + e.getMessage(), e);
		} finally {
			isRunning.set(false);
			log.info("[END] runNoProductionCheck - isRunning reset về false");
			log.info("================================================================");
		}
	}

	private void processSite(SiteEntity site) {
		log.info("  [SITE-START] site id=" + site.getId() + " name=" + site.getName());
		try {
			String currentTime = ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).format(DATE_FMT);
			log.info("  [SITE] currentTime (UTC): " + currentTime);

			Map<String, Object> siteParam = new HashMap<>();
			siteParam.put("id_site", site.getId());

			List<?> rawDevices = queryForList("CronJobAlertNoProduction.getListMeterAndInverterBySite", siteParam);
			log.info("  [SITE] rawDevices trả về: " + (rawDevices != null ? rawDevices.size() : 0) + " device(s)");

			List<DeviceEntity> devices = new ArrayList<>();
			if (rawDevices != null) {
				for (Object o : rawDevices) {
					if (o instanceof DeviceEntity) {
						devices.add((DeviceEntity) o);
					}
				}
			}

			log.info("  [SITE] devices hợp lệ: " + devices.size());
			if (devices.isEmpty()) {
				log.warn("  [SITE] Không có device nào cho site " + site.getId());
				return;
			}

			for (DeviceEntity device : devices) {
				checkNoProductionByDevice(device, currentTime);
			}

		} catch (Exception e) {
			log.error("  [SITE-ERROR] site " + site.getId() + " lỗi: " + e.getMessage(), e);
		}
		log.info("  [SITE-END] site id=" + site.getId());
	}

	private void checkNoProductionByDevice(DeviceEntity device, String currentTime) {
		log.info("    [DEVICE-START] id=" + device.getId()
				+ " name=" + device.getDevicename()
				+ " table=" + device.getDatatablename());

		if (device == null || device.getId() <= 0 || device.getDatatablename() == null) {
			log.warn("    [DEVICE] Device không hợp lệ -> bỏ qua: id="
					+ (device != null ? device.getId() : "null"));
			return;
		}

		int idError = device.getId_error() != 0 ? device.getId_error() : 1000;
		log.info("    [DEVICE] idError=" + idError
				+ " (device.getId_error()=" + device.getId_error() + ")");


		String lockKey = device.getId() + "_" + idError;
		log.info("    [DEVICE] Thử acquire lock key=[" + lockKey + "]"
				+ " | processingDevices hiện tại: " + processingDevices.keySet());

		if (processingDevices.putIfAbsent(lockKey, Boolean.TRUE) != null) {
			log.warn("    [DEVICE] Lock [" + lockKey + "] đang bị giữ bởi thread khác -> SKIP");
			return;
		}
		log.info("    [DEVICE] Acquired lock: [" + lockKey + "]");

		try {
			// ---- Bước 1: Lấy dữ liệu sản xuất gần nhất ----
			Map<String, Object> params = new HashMap<>();
			params.put("id", device.getId());
			params.put("current_time", currentTime);
			params.put("datatablename", device.getDatatablename());

			log.info("    [DEVICE] Gọi getLastRowItemCheckNoProduction: id=" + device.getId()
					+ " current_time=" + currentTime
					+ " datatablename=" + device.getDatatablename());

			BatchJobTableEntity result = (BatchJobTableEntity) queryForObject(
					"CronJobAlertNoProduction.getLastRowItemCheckNoProduction", params);

			if (result == null) {
				log.warn("    [DEVICE] getLastRowItemCheckNoProduction trả về NULL -> bỏ qua device "
						+ device.getId());
				return;
			}

			int countNoProd = result.getCount_item();
			log.info("    [DEVICE] countNoProd=" + countNoProd
					+ " | nvmActivePower=" + result.getNvmActivePower()
					+ " | ngưỡng alert >= 8");

			AlertEntity alert = new AlertEntity();
			alert.setId_device(device.getId());
			alert.setId_error(idError);

			if (countNoProd >= 8) {

				log.info("    [DEVICE] countNoProd >= 8 -> check alert đã tồn tại chưa..."
						+ " (id_device=" + alert.getId_device()
						+ " id_error=" + alert.getId_error() + ")");

				List<AlertEntity> existAlerts = queryForList(
						"CronJobAlertNoProduction.checkAlertQueueExist", alert);

				log.info("    [DEVICE] checkAlertQueueExist trả về existAlerts.size=" +
						(existAlerts != null ? existAlerts.size() : 0) +
						" | 0=chưa có -> INSERT, >0=đã có -> SKIP");

				if (existAlerts == null || existAlerts.isEmpty()) {
					alert.setStart_date(currentTime);
					alert.setEnd_date(null);
					alert.setAsset(device.getDevicename());
					alert.setCapacity(0.0);

					log.info("    [DEVICE] Tiến hành INSERT alert_queue:"
							+ " id_device=" + alert.getId_device()
							+ " id_error=" + alert.getId_error()
							+ " start_date=" + alert.getStart_date()
							+ " asset=" + alert.getAsset());

					boolean inserted = insertAlert(alert);
					log.info("    [DEVICE] insertAlert kết quả: "
							+ (inserted ? "THÀNH CÔNG id=" + alert.getId() : "THẤT BẠI"));
				} else {
					log.info("    [DEVICE] Alert đã tồn tại (existAlerts.size=" +
							(existAlerts != null ? existAlerts.size() : 0) + ") -> BỎ QUA, không insert thêm");
				}

			} else {
				log.info("    [DEVICE] countNoProd=" + countNoProd
						+ " < 8 -> production bình thường, kiểm tra alert đang mở để đóng...");

				Map<String, Object> checkParam = new HashMap<>();
				checkParam.put("id", device.getId());
				checkParam.put("id_error", idError);

				BatchJobTableEntity existing = (BatchJobTableEntity) queryForObject(
						"CronJobAlertNoProduction.getRowItemAlert", checkParam);

				if (existing != null && existing.getId() > 0) {
					log.info("    [DEVICE] Tìm thấy alert đang mở id=" + existing.getId()
							+ " -> tiến hành ĐÓNG alert với end_date=" + currentTime);

					AlertEntity closeAlert = new AlertEntity();
					closeAlert.setId(existing.getId());
					closeAlert.setId_device(device.getId());
					closeAlert.setEnd_date(currentTime);
					updateCloseAlert(closeAlert);

				} else {
					log.info("    [DEVICE] Không có alert đang mở cho device " + device.getId()
							+ " error " + idError + " -> không cần đóng");
				}
			}

		} catch (Exception e) {
			log.error("    [DEVICE-ERROR] device " + device.getId() + " lỗi: " + e.getMessage(), e);
		} finally {
			// Luôn release lock dù có exception hay không
			processingDevices.remove(lockKey);
			log.info("    [DEVICE] Released lock: [" + lockKey + "]");
			log.info("    [DEVICE-END] id=" + device.getId());
		}
	}

	private boolean insertAlert(AlertEntity obj) {
		try {
			log.info("    [INSERT] Gọi insertAlert: id_device=" + obj.getId_device()
					+ " id_error=" + obj.getId_error());
			int result = (Integer) insert("CronJobAlertNoProduction.insertAlert", obj);
			log.info("    [INSERT] affected rows=" + result + " | generated id=" + obj.getId());
			return result > 0;
		} catch (Exception ex) {
			log.error("    [INSERT-ERROR] insertAlert lỗi: " + ex.getMessage(), ex);
			return false;
		}
	}

	private void updateCloseAlert(AlertEntity obj) {
		try {
			log.info("    [UPDATE] Gọi updateCloseAlert: id=" + obj.getId()
					+ " id_device=" + obj.getId_device()
					+ " end_date=" + obj.getEnd_date());
			update("CronJobAlertNoProduction.updateCloseAlert", obj);
			log.info("    [UPDATE] updateCloseAlert thành công");
		} catch (Exception ex) {
			log.error("    [UPDATE-ERROR] updateCloseAlert lỗi: " + ex.getMessage(), ex);
		}
	}
}