package com.nwm.api.services.mobile;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.alert.AlertMobileEntity;
import com.nwm.api.entities.mobile.alert.GetAlertsDto;

public class AlertMobileService extends DB {
    public List<AlertMobileEntity> GetAlertsByUser(GetAlertsDto dto) {
        try {
            List<AlertMobileEntity> alerts = queryForList("AlertMobile.getListAlertsByUser", dto);

            return alerts;
        } catch (Exception ex) {

            System.out.print(ex.getMessage());

            return new ArrayList<AlertMobileEntity>();
        }
    }
}
