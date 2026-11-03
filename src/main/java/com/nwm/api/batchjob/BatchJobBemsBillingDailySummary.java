/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.CronJobBemsBillingDailySummaryService;
import com.nwm.api.utils.FLLogger;
import org.springframework.stereotype.Component;

/**
 * Batch job component for BEMS Billing Daily Summary.
 * Follows the same pattern as other batch jobs.
 *
 * @author chuong.ma
 * @since 2026-09-25
 */
@Component
public class BatchJobBemsBillingDailySummary {

  private static final String BEMS_BILLING_DAILY_SUMMARY_JOB_CODE = "BEMS_BILLING_DAILY_SUMMARY";
  private static final String BEMS_BILLING_DAILY_SUMMARY_PAST_DAY_JOB_CODE = "BEMS_BILLING_DAILY_SUMMARY_PAST_DAY";
  private static final FLLogger log = FLLogger.getLogger("batchjob/BatchJobBemsBillingDailySummary");
  
  private CronJobBemsBillingDailySummaryService cronJobBemsBillingDailySummaryService;

  public BatchJobBemsBillingDailySummary(CronJobBemsBillingDailySummaryService cronJobBemsBillingDailySummaryService) {
    this.cronJobBemsBillingDailySummaryService = cronJobBemsBillingDailySummaryService;
  }

  /**
   * Entry point called by BatchConfig_BemsBillingDailiSummary.
   * Delegates to the service which handles server splitting + multi-threading.
   */
  public void runBemsBillingDailySummary() {
    log.info("===== BEMS Billing Daily Summary START =====");
    long startTime = System.currentTimeMillis();
    try {
      cronJobBemsBillingDailySummaryService.updateJobSchedulerStatus(BEMS_BILLING_DAILY_SUMMARY_JOB_CODE, "START");
      cronJobBemsBillingDailySummaryService.execute();
      log.info("BEMS Billing Daily Summary completed.");
    } catch (Exception e) {
      log.error("BEMS Billing Daily Summary error: ", e);
    } finally {
      long duration = System.currentTimeMillis() - startTime;
      log.info("===== BEMS Billing Daily Summary END. Total: " + duration / 1000 + " seconds =====");
      cronJobBemsBillingDailySummaryService.updateJobSchedulerStatus(BEMS_BILLING_DAILY_SUMMARY_JOB_CODE, "END");
    }
  }

  /**
   * Entry point called by BatchConfig_BemsBillingDailiSummary for the past day job.
   * Delegates to the service which handles server splitting + multi-threading.
   * The data may be lost and backfilled when the device was not reporting properly.
   * This job ensures that any missing data from the previous day is processed.
   */
  public void runBemsBillingDailySummaryPastDay() {
    log.info("===== BEMS Billing Daily Summary Past Day START =====");
    long startTime = System.currentTimeMillis();
    try {
      cronJobBemsBillingDailySummaryService.updateJobSchedulerStatus(BEMS_BILLING_DAILY_SUMMARY_PAST_DAY_JOB_CODE, "START");
      cronJobBemsBillingDailySummaryService.executePastDays();
      log.info("BEMS Billing Daily Summary Past Day completed.");
    } catch (Exception e) {
      log.error("BEMS Billing Daily Summary Past Day error: ", e);
    } finally {
      long duration = System.currentTimeMillis() - startTime;
      log.info("===== BEMS Billing Daily Summary Past Day END. Total: " + duration / 1000 + " seconds =====");
      cronJobBemsBillingDailySummaryService.updateJobSchedulerStatus(BEMS_BILLING_DAILY_SUMMARY_PAST_DAY_JOB_CODE, "END");
    }
  }

}
