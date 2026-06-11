package com.nwm.api.services.mobile;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.home.GetSummaryDto;

public class HomeMobileService extends DB {
    public Object GetSummary(GetSummaryDto dto){
        try {
            Object data = queryForObject("HomeMobile.getSummary", dto);

            return data;
        }catch (Exception ex){
            System.out.print(ex.getMessage());

            return 0;
        }
    }
}
