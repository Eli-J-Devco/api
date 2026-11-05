/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nwm.api.services.CronJobTenantBillingSendService;
import com.nwm.api.utils.FLLogger;

@Component
@ConditionalOnProperty(name = "cron.tenant.billing.send.active", havingValue = "true")
public class BatchConfig_TenantBillingSend {

  @Autowired
  private CronJobTenantBillingSendService cronJobTenantBillingSendService;

  private static final FLLogger log = FLLogger.getLogger("batchjob/CronJobTenantBillingSend");

  /**
   * Send the tenant billing CSV by FTP / email (cron expression is configurable via application properties).
   */
  @Scheduled(cron = "${cron.tenant.billing.send.scheduler}")
  public void runTenantBillingSend() {
    log.info("Running Tenant Billing Send job...");
    cronJobTenantBillingSendService.execute();
  }

}
