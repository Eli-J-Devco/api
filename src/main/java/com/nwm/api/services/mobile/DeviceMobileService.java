package com.nwm.api.services.mobile;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.device.DeviceMobileEntity;
import com.nwm.api.entities.mobile.device.GetDeviceDetailsDto;
import com.nwm.api.entities.mobile.device.GetYieldDto;
import com.nwm.api.entities.mobile.device.YieldEntity;

public class DeviceMobileService extends DB {
    public DeviceMobileEntity GetDeviceDetails(GetDeviceDetailsDto dto){
        try {
            DeviceMobileEntity data = (DeviceMobileEntity) queryForObject("DeviceMobile.getDeviceDetails", dto);

            if(data.getDataTable() != null){
                GetYieldDto yielDto = new GetYieldDto(data.getId(), data.getDataTable(), data.getTimeZone());

                YieldEntity yieldEntity = (YieldEntity) queryForObject("DeviceMobile.getYieldByDevice", yielDto);

                data.setYieldToday(yieldEntity.getYieldToday());
                data.setYieldYesterday(yieldEntity.getYieldYesterday());
                data.setYieldLastSevenDays(yieldEntity.getYieldLastSevenDays());
                data.setYieldYTD(yieldEntity.getYieldYTD());
            }

            return data;
        }catch (Exception ex){
            
            System.err.println(ex.getMessage());

            return new DeviceMobileEntity();
        }
    }
}
