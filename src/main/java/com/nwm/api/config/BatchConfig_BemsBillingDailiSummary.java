/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.config;

import com.nwm.api.batchjob.BatchJobBemsBillingDailySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.nwm.api.utils.FLLogger;

@Component
@ConditionalOnProperty(name = "cron.bems.billing.daily.summary.active", havingValue = "true")
public class BatchConfig_BemsBillingDailiSummary {

  @Value("${cron.bems.billing.daily.summary.active:false}")
  private boolean bemsBillingDailySummaryActive;

  @Value("${cron.bems.billing.daily.summary.scheduler}")
  private String bemsBillingDailySummaryScheduler;

  @Autowired
  private BatchJobBemsBillingDailySummary batchJobBemsBillingDailySummary;

  private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobBemsBillingDailySummary");

  /**
   * Run BEMS Billing Daily Summary job every 30 minutes (cron expression is configurable
   * via application properties).
   */
  @Scheduled(cron = "${cron.bems.billing.daily.summary.scheduler}")
  public void runBemsBillingDailySummary() {
    if (!bemsBillingDailySummaryActive) {
      log.info("BEMS Billing Daily Summary job is disabled.");
      return;
    }
    log.info("Running BEMS Billing Daily Summary job...");
    batchJobBemsBillingDailySummary.runBemsBillingDailySummary();
  }

  @Scheduled(cron = "${cron.bems.billing.daily.summary.endday.scheduler}")
  public void runBemsBillingDailySummaryEndDay() {
    if (!bemsBillingDailySummaryActive) {
      log.info("BEMS Billing Daily Summary job is disabled.");
      return;
    }
    log.info("Running BEMS Billing Daily Summary End Day job...");
    batchJobBemsBillingDailySummary.runBemsBillingDailySummary();
  }

  @Scheduled(cron = "${cron.bems.billing.daily.summary.pastday.scheduler}")
  public void runBemsBillingDailySummaryPastDay() {
    if (!bemsBillingDailySummaryActive) {
      log.info("BEMS Billing Daily Summary job is disabled.");
      return;
    }
    log.info("Running BEMS Billing Daily Summary Past Days job...");
    batchJobBemsBillingDailySummary.runBemsBillingDailySummaryPastDay();
  }

}
