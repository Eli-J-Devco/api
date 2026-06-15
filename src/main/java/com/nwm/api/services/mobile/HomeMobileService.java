package com.nwm.api.services.mobile;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.alert.GetAlertsDto;
import com.nwm.api.entities.mobile.home.GetSummaryDto;
import com.nwm.api.entities.mobile.home.GetWhatChangeTodayDto;
import com.nwm.api.entities.mobile.home.WhatChangeTodayEntity;

public class HomeMobileService extends DB {
    private final AlertMobileService alertService;

    public HomeMobileService(){
         this.alertService = new AlertMobileService();
    }

    public Object GetSummary(GetSummaryDto dto){
        try {
            Object data = queryForObject("HomeMobile.getSummary", dto);

            return data;
        }catch (Exception ex){
            System.out.print(ex.getMessage());

            return 0;
        }
    }

    public WhatChangeTodayEntity GetWhatChangeToday(GetWhatChangeTodayDto dto){
        try {
            WhatChangeTodayEntity result = new WhatChangeTodayEntity();

            int totalInverterFailures = (int) queryForObject("DeviceMobile.countInvertFail", dto);
            result.setInverterFailures(totalInverterFailures);

            GetAlertsDto aletDto = new GetAlertsDto(dto.getUserId(), dto.getIsSupperAdmin(), dto.getStartDate(), dto.getEndDate());

            int totalAlert = alertService.CountAlerts(aletDto);
            result.setTotalAlert(totalAlert);

            return result;
        }catch (Exception ex){
            System.out.print(ex.getMessage());

            return new WhatChangeTodayEntity();
        }
    }
}
