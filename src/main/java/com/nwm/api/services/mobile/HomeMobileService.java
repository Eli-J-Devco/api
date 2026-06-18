package com.nwm.api.services.mobile;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.alert.GetAlertsDto;
import com.nwm.api.entities.mobile.device.GetInverterAvailabilityDto;
import com.nwm.api.entities.mobile.home.CriticalSiteEntity;
import com.nwm.api.entities.mobile.home.GetCriticalSiteDto;
import com.nwm.api.entities.mobile.home.GetSummaryDto;
import com.nwm.api.entities.mobile.home.GetWhatChangeTodayDto;
import com.nwm.api.entities.mobile.home.SummaryAcrossSystemEntity;
import com.nwm.api.entities.mobile.home.WhatChangeTodayEntity;

public class HomeMobileService extends DB {
    private final AlertMobileService alertService;
    private final DeviceMobileService deviceService;

    public HomeMobileService() {
        this.alertService = new AlertMobileService();
        this.deviceService = new DeviceMobileService();
    }

    public SummaryAcrossSystemEntity GetSummary(GetSummaryDto dto) {
        try {
            GetInverterAvailabilityDto inverterDto = new GetInverterAvailabilityDto(dto);
            GetAlertsDto alertDto = new GetAlertsDto(dto);

            double inverter = deviceService.GetPerCentInverterAvailability(inverterDto);
            double generation = GetGenerationAcrossSystem();
            int totalAlert = alertService.CountAlerts(alertDto);

            SummaryAcrossSystemEntity result = new SummaryAcrossSystemEntity();
            result.setInverterAvailability(inverter);
            result.setTotalAlerts(totalAlert);
            result.setGeneration(generation);

            return result;
        } catch (Exception ex) {
            // System.out.print(ex.getMessage());

            return new SummaryAcrossSystemEntity();
        }
    }

    public WhatChangeTodayEntity GetWhatChangeToday(GetWhatChangeTodayDto dto) {
        try {
            GetAlertsDto aletDto = new GetAlertsDto(dto.getUserId(), dto.getIsSupperAdmin(),
                    dto.getStartDate(), dto.getEndDate(), true);

            int totalInverterFailures = (int) queryForObject("DeviceMobile.countInvertFail", dto);
            int totalAlert = alertService.CountAlerts(aletDto);

            WhatChangeTodayEntity result = new WhatChangeTodayEntity(totalAlert, totalInverterFailures);

            return result;
        } catch (Exception ex) {
            // System.out.print(ex.getMessage());

            return new WhatChangeTodayEntity();
        }
    }

    public Double GetGenerationAcrossSystem() {
        try {
            double result = (double) queryForObject("HomeMobile.getGenerationAcrossSystem", new Object());

            return result;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());

            return 0.0;
        }
    }

    public List<CriticalSiteEntity> GetCriticalSite(GetCriticalSiteDto dto ) {
        try {
            List<CriticalSiteEntity> result = queryForList("HomeMobile.getCriticalSite", dto);

            return result;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());

            return new ArrayList<>();
        }
    }
}
