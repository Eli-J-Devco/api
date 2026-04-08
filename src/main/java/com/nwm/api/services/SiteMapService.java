package com.nwm.api.services;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.SiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List getDeviceList(SiteEntity obj) {
        List deviceList = new ArrayList();
        try {
            deviceList = queryForList("SiteMap.getDeviceList", obj);
            if (deviceList == null)
                return new ArrayList();
        } catch (Exception ex) {
            return new ArrayList();
        }
        return deviceList;
    }

    public List getDeviceDetail(DeviceEntity obj) {
        List<Map<String, Object>> metricList = new ArrayList();
        Map deviceMap = new HashMap();
        List<Map<String, Object>> paramList = new ArrayList<>();
        try {
            deviceMap = (Map) queryForObject("SiteMap.getDeviceTable", obj);

            if (deviceMap == null || deviceMap.get("datatablename") == null) {
                return metricList;
            }

            String datatablename = (String) deviceMap.get("datatablename");
            obj.setDatatablename(datatablename);

            paramList = queryForList("SiteMap.getParamSlugsByDevice", obj);

            List<String> slugList = paramList.stream()
                    .map(map -> (String) map.get("slug"))
                    .collect(Collectors.toList());

            metricList = queryForList("SiteMap.getLatestMetrics", obj);

            Map<String, Object> metricsRow = new HashMap<>();
            if (metricList != null && !metricList.isEmpty()) {
                metricsRow = metricList.get(0);
            }

            List<Map<String, Object>> result = new ArrayList<>();

            for (Map<String, Object> param : paramList) {
                String name = (String) param.get("name");
                String standardName = (String) param.get("standard_name");
                String slug = (String) param.get("slug");
                String unit = (String) param.get("unit");

                Map<String, Object> item = new HashMap<>();

                String displayName = (standardName != null && !standardName.isEmpty())
                        ? standardName
                        : name;

                item.put("name", displayName);
                item.put("slug", slug);
                item.put("value", metricsRow.get(slug));
                item.put("unit", unit);

                result.add(item);
            }

            return result;
        } catch (Exception ex) {
            log.error("SiteMapService", ex);
            return new ArrayList();
        }
    }

    public List getListParameterByDeviceGroup(DeviceParameterEntity obj) {
        List dataList = new ArrayList();
        try {
            dataList = queryForList("SiteMap.getListParameterByDeviceGroup", obj);
            if (dataList == null)
                return new ArrayList();
        } catch (Exception ex) {
            return new ArrayList();
        }
        return dataList;
    }

//    public List insertParameterByDevice(DeviceParameterEntity obj) {
//        List dataList = new ArrayList();
//        try {
//            dataList = queryForList("SiteMap.insertParameterByDevice", obj);
//            if (dataList == null)
//                return new ArrayList();
//        } catch (Exception ex) {
//            return new ArrayList();
//        }
//        return dataList;
//    }
//
//    public List deleteParameterByDevice(DeviceParameterEntity obj) {
//        List dataList = new ArrayList();
//        try {
//            dataList = queryForList("SiteMap.deleteParameterByDevice", obj);
//            if (dataList == null)
//                return new ArrayList();
//        } catch (Exception ex) {
//            return new ArrayList();
//        }
//        return dataList;
//    }
}
