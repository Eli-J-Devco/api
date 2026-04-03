package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEntity;

import java.util.ArrayList;
import java.util.List;

public class SiteMapService extends DB {
    public Object getSiteInfo(SiteEntity obj) {
        Object dataObj = null;
        try {
            dataObj = queryForObject("SiteMap.getSiteInfo", obj);
            if (dataObj == null)
                return new SiteEntity();
        } catch (Exception ex) {
            return new SiteEntity();
        }
        return dataObj;
    }
}
