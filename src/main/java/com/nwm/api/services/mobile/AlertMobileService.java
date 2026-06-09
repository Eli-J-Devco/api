package com.nwm.api.services.mobile;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.alert.AlertMobileEntity;
import com.nwm.api.entities.mobile.alert.AlertMobileOverviewEntity;
import com.nwm.api.entities.mobile.alert.AlertSummaryEntity;
import com.nwm.api.entities.mobile.alert.GetAlertsDto;

public class AlertMobileService extends DB {
    public Object GetAlertSummary(GetAlertsDto dto) {
        try {
            List<AlertSummaryEntity> data = queryForList("AlertMobile.getAlertSummary", dto);

            AlertMobileOverviewEntity result = new AlertMobileOverviewEntity();

            if (data == null || data.size() == 0)
                return result;

            int count = 0;

            for (int i = 0; i < data.size(); i++) {
                AlertSummaryEntity item = data.get(i);
                int id = item.getId();

                count = count + item.getValue();

                if (id == 1) {
                    result.setLowPriority(item.getValue());

                    continue;
                }
                if (id == 2) {
                    result.setMedPriority(item.getValue());
                    continue;
                }
                if (id == 3) {
                    result.setHighPriority(item.getValue());
                    continue;
                }
            }

            result.setTotal(count);

            return result;
        } catch (Exception ex) {

            System.out.print(ex.getMessage());

            return null;
        }
    }

    public Integer CountAlerts(GetAlertsDto dto) {
        try {
            Integer data = (Integer) queryForObject("AlertMobile.countAlerts", dto);

            return data != null ? data : 0 ;
        } catch (Exception ex) {

            System.out.print(ex.getMessage());

            return null;
        }
    }

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
