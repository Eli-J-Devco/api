package com.nwm.api.services.mobile;

import java.util.ArrayList;
import java.util.List;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.mobile.alert.ErrorTypeMobileEntity;

public class ErrorTypeMobileService extends DB {
    public List<ErrorTypeMobileEntity> GetErrorTypes(){
        try {
            List<ErrorTypeMobileEntity> data = queryForList("ErrorMobile.getErrorTypes");

            return data;
        } catch (Exception ex){

            return new ArrayList<ErrorTypeMobileEntity>();
        }
    }
}
