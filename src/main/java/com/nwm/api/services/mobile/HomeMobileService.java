package com.nwm.api.services.mobile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.alert.GetAlertsDto;
import com.nwm.api.entities.mobile.device.GetInverterAvailabilityDto;
import com.nwm.api.entities.mobile.home.CriticalSiteEntity;
import com.nwm.api.entities.mobile.home.GetCriticalSiteDto;
import com.nwm.api.entities.mobile.home.GetCriticalSiteSummaryDto;
import com.nwm.api.entities.mobile.home.GetSummaryDto;
import com.nwm.api.entities.mobile.home.GetWhatChangeTodayDto;
import com.nwm.api.entities.mobile.home.SummaryAcrossSystemEntity;
import com.nwm.api.entities.mobile.home.WhatChangeTodayEntity;
import com.nwm.api.entities.mobile.site.ChartDataEntity;

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

    public List<CriticalSiteEntity> GetCriticalSite(GetCriticalSiteDto dto) {
        try {
            List<CriticalSiteEntity> result = queryForList("HomeMobile.getCriticalSite", dto);

            return result;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());

            return new ArrayList<>();
        }
    }

    private Map<String, ChartDataEntity> MapItems(List<ChartDataEntity> listItem) {
        Map<String, ChartDataEntity> mapItems = new HashMap<>();

        for (ChartDataEntity item : listItem) {
            mapItems.put(item.getTime(), item);
        }

        return mapItems;
    }

    private List<ChartDataEntity> FillGap(Map<String, ChartDataEntity> dataSrc, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outpuTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = LocalDateTime.parse(startDate, formatter).toLocalDate();
        LocalDate end = LocalDateTime.parse(endDate, formatter).toLocalDate();

        List<ChartDataEntity> data = new ArrayList<>();

        while (!start.isAfter(end)) {

            ChartDataEntity existsItem = dataSrc.get(start.format(outpuTimeFormatter));

            if (existsItem != null) {
                data.add(existsItem);
            } else {
                ChartDataEntity newItem = new ChartDataEntity();
                newItem.setTime(start.format(outpuTimeFormatter));
                data.add(newItem);
            }

            start = start.plusDays(1);
        }

        return data;
    }

    public List<ChartDataEntity> GetSummaryAlertBySite(GetCriticalSiteSummaryDto dto) {
        try {
            List<ChartDataEntity> result = queryForList("HomeMobile.getSummaryAlertBySite", dto);

            Map<String, ChartDataEntity> mapItems = MapItems(result);

            List<ChartDataEntity> data = FillGap(mapItems, dto.getStartDate(), dto.getEndDate());

            return data;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());

            return new ArrayList<>();
        }
    }

    public List<ChartDataEntity> GetSummaryInverterAvailBySite(GetCriticalSiteSummaryDto dto) {
        try {
            List<ChartDataEntity> result = queryForList("HomeMobile.getSummaryInverterAvailBySite", dto);

            Map<String, ChartDataEntity> mapItems = MapItems(result);

            List<ChartDataEntity> data = FillGap(mapItems, dto.getStartDate(), dto.getEndDate());

            return data;
        } catch (Exception ex) {
            System.out.print(ex.getMessage());

            return new ArrayList<>();
        }
    }
}
