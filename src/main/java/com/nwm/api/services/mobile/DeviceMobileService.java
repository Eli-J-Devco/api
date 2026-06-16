package com.nwm.api.services.mobile;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.device.DeviceMobileEntity;
import com.nwm.api.entities.mobile.device.GetDeviceDetailsDto;
import com.nwm.api.entities.mobile.device.GetInverterAvailabilityDto;
import com.nwm.api.entities.mobile.device.GetInverterFailuresDto;
import com.nwm.api.entities.mobile.device.GetYieldDto;
import com.nwm.api.entities.mobile.device.InverterAvailabilitySummary;
import com.nwm.api.entities.mobile.device.InverterFailureEntity;
import com.nwm.api.entities.mobile.device.YieldEntity;

public class DeviceMobileService extends DB {

    public List<InverterFailureEntity> GetInverterFailures(GetInverterFailuresDto dto) {
        try {
            List<InverterFailureEntity> data = queryForList("DeviceMobile.getInverterFailures", dto);

            return data;
        } catch (Exception ex) {

            System.err.println(ex.getMessage());

            return new ArrayList<InverterFailureEntity>();
        }
    }

    public Integer CountInverterFailures(GetInverterFailuresDto dto) {
        try {
            Integer data = (Integer) queryForObject("DeviceMobile.countInvertFailures", dto);

            return data != null ? data : 0;
        } catch (Exception ex) {

            System.err.println(ex.getMessage());

            return 0;
        }
    }

    public DeviceMobileEntity GetDeviceDetails(GetDeviceDetailsDto dto) {
        try {
            DeviceMobileEntity data = (DeviceMobileEntity) queryForObject("DeviceMobile.getDeviceDetails", dto);

            if (data.getDataTable() != null) {
                GetYieldDto yielDto = new GetYieldDto(data.getId(), data.getDataTable(), data.getTimeZone());

                YieldEntity yieldEntity = (YieldEntity) queryForObject("DeviceMobile.getYieldByDevice", yielDto);

                data.setYieldToday(yieldEntity.getYieldToday());
                data.setYieldYesterday(yieldEntity.getYieldYesterday());
                data.setYieldLastSevenDays(yieldEntity.getYieldLastSevenDays());
                data.setYieldYTD(yieldEntity.getYieldYTD());
            }

            return data;
        } catch (Exception ex) {

            System.err.println(ex.getMessage());

            return new DeviceMobileEntity();
        }
    }

    public InverterAvailabilitySummary GetInverterStatusSummary(GetInverterAvailabilityDto dto) {
        try {
            InverterAvailabilitySummary data = (InverterAvailabilitySummary) queryForObject("DeviceMobile.getInverterStatusSummary", dto);

            return data;
        } catch (Exception ex) {

            System.out.println(ex.getMessage());

            return new InverterAvailabilitySummary();
        }
    }

    public Object GetInverterAvailabilityList(GetInverterAvailabilityDto dto) {
        try {
            Object data = queryForList("DeviceMobile.getInverterAvailabilityList", dto);

            return data;
        } catch (Exception ex) {

            System.err.println(ex.getMessage());

            return new Object();
        }
    }

    public Double GetPerCentInverterAvailability(GetInverterAvailabilityDto dto) {
        try {
            InverterAvailabilitySummary data = GetInverterStatusSummary(dto);

            if (data.getTotalInverter() == 0) {
                return 0.0;
            }

            double percent = Math.round(((double) data.getInverterOnline()/data.getTotalInverter() )* 100.0);

            return percent;
        } catch (Exception ex) {

            System.err.println(ex.getMessage());

            return 0.0;
        }
    }
}
