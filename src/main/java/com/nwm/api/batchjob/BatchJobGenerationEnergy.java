/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.batchjob;

import com.nwm.api.services.GenerationEnergyService;
import com.nwm.api.utils.FLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchJobGenerationEnergy {

    @Autowired
    private GenerationEnergyService generationEnergyService;

    private static final FLLogger log = FLLogger.getLogger("batchjob/BatchJobGenerationEnergy");

    /**
     * @description: generation energy today
     * @author: {@link Long} Pham
     * @date: 22-03-2026
     */
    public void generationEnergyData() {
        log.info("Start generation energy today");
        try {
        	generationEnergyService.generationEnergyData();
        } catch (Exception e) {
            log.error("Cron error: ", e);
        } finally {
            log.info("Cron end !!!");
        }
    }

    /**
     * @description Update energy yesterday for all sites.
     *              Called once per day at 00:00:00 UTC.
     *              The service layer guarantees at-most-once execution per UTC day.
     * @author Long Pham
     * @date 26-03-2026
     */
    public void updateEnergyYesterday() {
        log.info("Start updateEnergyYesterday cron job");
        try {
            generationEnergyService.updateEnergyYesterday();
        } catch (Exception e) {
            log.error("updateEnergyYesterday cron error: ", e);
        } finally {
            log.info("updateEnergyYesterday cron end !!!");
        }
    }

    /**
     * @description Update energy last week for all sites.
     *              Called once per week (Monday 01:00 UTC).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyLastWeek() {
        log.info("Start updateEnergyLastWeek cron job");
        try {
            generationEnergyService.updateEnergyLastWeek();
        } catch (Exception e) {
            log.error("updateEnergyLastWeek cron error: ", e);
        } finally {
            log.info("updateEnergyLastWeek cron end !!!");
        }
    }

    /**
     * @description Update energy last month for all sites.
     *              Called once per month (1st of month 02:00 UTC).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyLastMonth() {
        log.info("Start updateEnergyLastMonth cron job");
        try {
            generationEnergyService.updateEnergyLastMonth();
        } catch (Exception e) {
            log.error("updateEnergyLastMonth cron error: ", e);
        } finally {
            log.info("updateEnergyLastMonth cron end !!!");
        }
    }

    /**
     * @description Update energy compare last month (month before last) for all sites.
     *              Called once per month (1st of month 02:30 UTC).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyCompareLastMonth() {
        log.info("Start updateEnergyCompareLastMonth cron job");
        try {
            generationEnergyService.updateEnergyCompareLastMonth();
        } catch (Exception e) {
            log.error("updateEnergyCompareLastMonth cron error: ", e);
        } finally {
            log.info("updateEnergyCompareLastMonth cron end !!!");
        }
    }

    /**
     * @description Update energy last 12 months (last year) for all sites.
     *              Called once per year (Jan 1st 03:00 UTC).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyLast12Months() {
        log.info("Start updateEnergyLast12Months cron job");
        try {
            generationEnergyService.updateEnergyLast12Months();
        } catch (Exception e) {
            log.error("updateEnergyLast12Months cron error: ", e);
        } finally {
            log.info("updateEnergyLast12Months cron end !!!");
        }
    }

    /**
     * @description Update avg daily energy for current 7-day window for all sites.
     *              Called once per week (Monday 01:30 UTC).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyAVGDaily7Days() {
        log.info("Start updateEnergyAVGDaily7Days cron job");
        try {
            generationEnergyService.updateEnergyAVGDaily7Days();
        } catch (Exception e) {
            log.error("updateEnergyAVGDaily7Days cron error: ", e);
        } finally {
            log.info("updateEnergyAVGDaily7Days cron end !!!");
        }
    }

    /**
     * @description Update avg daily energy for previous 7-day window (compare) for all sites.
     *              Called once per week (Monday 01:45 UTC).
     * @author Long Pham
     * @date 30-03-2026
     */
    public void updateEnergyAVGDailyLast7Days() {
        log.info("Start updateEnergyAVGDailyLast7Days cron job");
        try {
            generationEnergyService.updateEnergyAVGDailyLast7Days();
        } catch (Exception e) {
            log.error("updateEnergyAVGDailyLast7Days cron error: ", e);
        } finally {
            log.info("updateEnergyAVGDailyLast7Days cron end !!!");
        }
    }
}
