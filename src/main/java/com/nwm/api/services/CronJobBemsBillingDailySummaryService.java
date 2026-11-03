package com.nwm.api.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.CronJobSchedulerEntity;
import com.nwm.api.utils.FLLogger;

@Service
public class CronJobBemsBillingDailySummaryService extends DB {
  private static final FLLogger log = FLLogger.getLogger("batchjob/"+CronJobBemsBillingDailySummaryService.class.getSimpleName());
  private final AtomicBoolean isRunning = new AtomicBoolean(false);
  @Value ("${cron.bems.billing.daily.summary.maxthread:1}")
	private int MAX_SITE_THREADS;

  @Value("${cron.bems.billing.daily.summary.sites:835}")
  private List<Integer> listSites;
  

  private ThreadPoolExecutor deviceExecutor;
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private final int RECOVER_PAST_DAYS_SIZE = 2;

	private ThreadPoolExecutor createDeviceExecutor() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				MAX_SITE_THREADS, MAX_SITE_THREADS,
				60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());
		executor.allowCoreThreadTimeOut(true);
		return executor;
	}

  private Instant nowInstant;

	@PostConstruct
	public void init() {
		deviceExecutor = createDeviceExecutor();
	}

  public void execute() {
		if (!isRunning.compareAndSet(false, true)) {
			log.info("BEMS Billing Daily Summary is already running. Skipping this execution.");
			return;
		}
		nowInstant = Instant.from(LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC));
		try {
      log.info("Executing BEMS Billing Daily Summary at " + nowInstant);
      Map<String, Object> params = new HashMap<>();
      params.put("list_sites", listSites);
      List<Map<String, Object>> listDevices = queryForList("BemsTenantBilling.getMeterDeviceListBySite", params);
      List<Future<?>> futures = new ArrayList<>();
			for (Map device : listDevices) {
				futures.add(deviceExecutor.submit(() -> {
					Thread t = Thread.currentThread();
					String tName = t.getName();
					t.setName(tName + "-" + device.get("id_device"));
					processForEachDevice(device, nowInstant);
				}));
			}

			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					log.error("Site task interrupted: " + ie.getMessage(), ie);
					break;
				} catch (ExecutionException ee) {
					log.error("Error executing site task: " + ee.getMessage(), ee);
				}catch(Exception e) {
					log.error("Unexpected error executing site task: " + e.getMessage(), e);
				}
			}
    } catch (Exception e) {
			log.error("Error in execute: " + e.getMessage());
			e.printStackTrace();
		} finally {
			isRunning.set(false);
		}
	}

	/**
	 * Execute job recover the past incomplete summaries for BEMS Billing Daily Summary.
	 */
	public void executePastDays() {
		if (!isRunning.compareAndSet(false, true)) {
			log.info("BEMS Billing Daily Summary Past Day is already running. Skipping this execution.");
			return;
		}
		nowInstant = Instant.from(LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC));
		try {
      log.info("Executing BEMS Billing Daily Summary Past Day at " + nowInstant);
      Map<String, Object> params = new HashMap<>();
      params.put("list_sites", listSites);
			params.put("time_execute", formatter.withZone(ZoneOffset.UTC).format(nowInstant));
			params.put("recover_past_days_size", RECOVER_PAST_DAYS_SIZE);
			List<Map<String, Object>> incompleteSummaries = queryForList("BemsTenantBilling.getPastIncompleteSummaries", params);
      List<Future<?>> futures = new ArrayList<>();
			for (Map device : incompleteSummaries) {
				futures.add(deviceExecutor.submit(() -> {
					Thread t = Thread.currentThread();
					String tName = t.getName();
					t.setName(tName + "-" + device.get("id_device"));
					processForEachDevice(device, nowInstant);
				}));
			}

			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					log.error("Site task interrupted: " + ie.getMessage(), ie);
					break;
				} catch (ExecutionException ee) {
					log.error("Error executing site task: " + ee.getMessage(), ee);
				}catch(Exception e) {
					log.error("Unexpected error executing site task: " + e.getMessage(), e);
				}
			}
    } catch (Exception e) {
			log.error("Error in executePastDay: " + e.getMessage());
			e.printStackTrace();
		} finally {
			isRunning.set(false);
		}
	}

  /**
   * Process each device for the BEMS Billing Daily Summary job.
   * @param device
   * @param jobStartInstant
   */
  private void processForEachDevice(Map<String, Object> device, Instant jobStartInstant) {
    log.info("Processing device: " + device.get("id_device") + " at " + jobStartInstant);
    try{
      Map<String, Object> params = new HashMap<>();
			Object recoverDate = device.get("date");
			// if there is a recover date, use it as the time_execute parameter and the job will recover that date; 
			// otherwise, use the job start instant and process the current date
			if(recoverDate != null) {
				params.put("time_execute", recoverDate);
			}else{
				params.put("time_execute", formatter.withZone(ZoneOffset.UTC).format(jobStartInstant));
			}
      params.put("id_device", device.get("id_device"));
      params.put("data_table_name", device.get("data_table_name"));
			log.debug(params);
      insert("BemsTenantBilling.GenerateTenantBillingDailySummary", params);
    }catch(Exception e) {
      log.error("Error processing device: " + e.getMessage(), e);
    }
  }
  /**
	 * @description update job scheduler status
	 * @author chuong.ma
	 * @since 2026-09-25
	 * @param jobCode
	 * @param trigger, trigger can be "START" or "END"
	 */
	public void updateJobSchedulerStatus(String jobCode, String trigger) {
		try {
			String status = "START".equals(trigger) ? "RUNNING" : "COMPLETED";
			CronJobSchedulerEntity jobEntity = (CronJobSchedulerEntity) queryForObject("CronJobScheduler.getJobScheduler", jobCode);
			if(jobEntity == null) {
				jobEntity = new CronJobSchedulerEntity();
				jobEntity.setJob_code(jobCode);
				jobEntity.setJob_name("BEMS Billing Daily Summary");
				jobEntity.setLast_start_time(Date.from(Instant.now()));
				jobEntity.setLast_status(status);
				jobEntity.setDeseription("This job is responsible for generating the BEMS Billing Daily Summary.");
				insert("CronJobScheduler.insertJobScheduler", jobEntity);
			}else{
				if ("START".equals(trigger)) {
					jobEntity.setLast_start_time(Date.from(Instant.now()));
				}
				if ("END".equals(trigger)) {
					jobEntity.setLast_end_time(Date.from(Instant.now()));
				}
				jobEntity.setLast_status(status);
				update("CronJobScheduler.updateJobScheduler", jobEntity);
			}
		} catch (Exception e) {
			log.error("Error updating job scheduler status: " + e.getMessage(), e);
		}
	}
}
