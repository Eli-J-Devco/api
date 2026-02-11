/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.nwm.api.utils.Lib;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEnergyThirdPartyAPIEntity;
import com.nwm.api.entities.ThirdPartyAPIEntity;

@Service
public class ThirdPartyAPIService extends DB {
	
	/**
	 * @description get energy generation whole sites in portfolio
	 * @author Hung.Bui
	 * @since 2024-05-02
	 * @param key
	 * @param startDate
	 * @param endDate
	 */
	public List getEnergyGeneration(String key, HttpServletRequest request, ThirdPartyAPIEntity params) {
		try {
			Map<String, Object> map = getAPIEndpointParam(key, request);
			map.put("id_device_type", new int[] {1,3});
			List devicesList = getDevices(map);
			if (devicesList.size() == 0) return new ArrayList();
			
			map.put("startDateTime", params.getStart_date());
			map.put("endDateTime", params.getEnd_date());
			map.put("devicesList", devicesList);
			List<SiteEnergyThirdPartyAPIEntity> dataList = queryForList("ThirdPartyAPI.getEnergyGeneration", map);
			if (dataList == null) return new ArrayList();
			
			ObjectMapper mapper = new ObjectMapper();
			dataList.forEach(item -> {
				try {
					item.setEnergy(mapper.readValue(item.getEnergyJSON(), new TypeReference<List<Map<String, Object>>>(){}));
				} catch (JsonProcessingException e) {
					item.setEnergy(new ArrayList<Map<String, Object>>());
				}
				item.setEnergyJSON(null);
			});
			
			return dataList;
		} catch (Exception ex) {
			log.error("ThirdPartyAPI.getEnergyGeneration", ex);
			return new ArrayList();
		}
	}
	
	/**
	 * @description get devices, each site must have 3rd party key and access domain origin
	 * @author Hung.Bui
	 * @since 2025-02-20
	 */
	private List getDevices(Map<String, Object> obj) {
		try {
			List devices = queryForList("ThirdPartyAPI.getListDevices", obj);
			if (devices == null) return new ArrayList();
			return devices;
		} catch (Exception ex) {
			log.error("ThirdPartyAPI.getListDevices", ex);
			return new ArrayList();
		}
	}
	
	/**
	 * @description get device data
	 * @author Hung.Bui
	 * @since 2025-02-20
	 * @param key
	 * @param params
	 */
	public List getDeviceData(String key, HttpServletRequest request, ThirdPartyAPIEntity params) {
		try {
			Map<String, Object> map = getAPIEndpointParam(key, request);
			map.put("id_device", params.getDevice_id());
            if (!Lib.isBlank(params.getData_type())) {
                List<String> nameList = Arrays.stream(params.getData_type().split(","))
                        .map(String::trim)
                        .filter(s -> !Lib.isBlank(s))
                        .collect(Collectors.toList());
                map.put("name_list", nameList);
            }
			List devicesList = getDevices(map);
			if (devicesList.size() == 0) return new ArrayList();
			
			List<CompletableFuture<Map<String, Object>>> list = new ArrayList<CompletableFuture<Map<String, Object>>>();
			
			for(int i = 0; i < devicesList.size(); i++) {
				int k = i;
				
				CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
					Map<String, Object> maps = new HashMap<>();
					
					try {
						Map<String, Object> device = (Map<String, Object>) devicesList.get(k);
						maps.put("id", device.get("id"));
						maps.put("name", device.get("name"));
						
						ObjectMapper mapper = new ObjectMapper();
						List<Map<String, String>> parameters = mapper.readValue(device.get("parameters").toString(), new TypeReference<List<Map<String, String>>>(){});
						if (parameters.size() == 0) return maps;
						
						device.put("startDateTime", params.getStart_date());
						device.put("endDateTime", params.getEnd_date());
						device.put("interval", params.getInterval());
						device.put("data_types", parameters);
						
						List<Map<String, Object>> data = queryForList("ThirdPartyAPI.getDeviceData", device);
						
						maps.put("data", data);
					} catch (Exception ex) {
						log.error("ThirdPartyAPI.getDeviceData", ex);
					}
					
					return maps;
				});
				
				list.add(future);
			}
			
			return list.stream().map(future -> future.join()).collect(Collectors.toList());
		} catch (Exception ex) {
			return new ArrayList();
		}
	}

    public List getDeviceInfoBySite(String key, HttpServletRequest request) {
        try {
            Map<String, Object> param = getAPIEndpointParam(key, request);

            List<Map<String, Object>> data = queryForList("ThirdPartyAPI.getDeviceInfoBySite", param);

            ObjectMapper mapper = new ObjectMapper();

            for (Map<String, Object> row : data) {
                Object dataType = row.get("data_type");
                if (dataType instanceof String) {
                    List<String> list = mapper.readValue((String) dataType, List.class);
                    row.put("data_type", list);
                }
            }

            return data;
        } catch (Exception ex) {
        	log.error("ThirdPartyAPI.getDeviceInfoBySite", ex);
            return new ArrayList<>();
        }
    }
    
    private Map<String, Object> getAPIEndpointParam(String key, HttpServletRequest request) {
    	Map<String, Object> map = new HashMap<>();
    	map.put("key", key);
		map.put("route", request.getRequestURI().substring(request.getContextPath().length()));
		map.put("method", request.getMethod());
		return map;
    }
}
