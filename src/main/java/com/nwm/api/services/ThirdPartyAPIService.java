/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
	public List getEnergyGeneration(String key, String domain, String startDate, String endDate) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("key", key);
			map.put("domain", domain);
			map.put("id_device_type", new int[] {1,3});
			List devicesList = getDevices(map);
			if (devicesList.size() == 0) return new ArrayList();
			
			map.put("startDateTime", startDate);
			map.put("endDateTime", endDate);
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
	 * @param domain
	 * @param params
	 */
	public List getDeviceData(String key, String domain, ThirdPartyAPIEntity params) {
		List dataList = new ArrayList();
		
		try {
			insert("ThirdPartyAPI.insertDomain", domain);
			
			Map<String, Object> map = new HashMap<>();
			map.put("key", key);
			map.put("domain", domain);
			map.put("id_device", params.getDevice_id());
			List devicesList = getDevices(map);
			if (devicesList.size() == 0) return dataList;
			
			List<CompletableFuture<Map<String, Object>>> list = new ArrayList<CompletableFuture<Map<String, Object>>>();
			
			for(int i = 0; i < devicesList.size(); i++) {
				int k = i;
				
				CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
					Map<String, Object> maps = new HashMap<>();
					
					try {
						Map<String, Object> device = (Map<String, Object>) devicesList.get(k);
						maps.put("id", device.get("id"));
						maps.put("name", device.get("name"));
						maps.put("interval_list", new String[] {"15min", "hour", "day", "month", "year"});
						
						ObjectMapper mapper = new ObjectMapper();
						List<Map<String, String>> parameters = mapper.readValue(device.get("parameters").toString(), new TypeReference<List<Map<String, String>>>(){});
						maps.put("data_type_list", parameters.stream().map(item -> item.get("name")));
						Map<String, String> parameter = parameters.stream().filter(item -> item.get("name").equals(params.getData_type())).findFirst().orElse(null);
						if (parameter == null) return maps;
						
						device.put("startDateTime", params.getStart_date());
						device.put("endDateTime", params.getEnd_date());
						device.put("interval", params.getInterval());
						device.put("data_type", params.getData_type());
						device.put("aggregate_function", parameter.get("aggregate_function"));
						
						List<Map<String, Object>> data = queryForList("ThirdPartyAPI.getDeviceData", device);
						
						maps.put("data_type", parameter.get("name"));
						maps.put("unit", parameter.get("unit"));
						maps.put("data", data);
					} catch (Exception ex) {
						log.error("ThirdPartyAPI.getDeviceData", ex);
					}
					
					return maps;
				});
				
				list.add(future);
			}
			
			CompletableFuture<Void> combinedFutures = CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
			List<Map<String, Object>> deviceDataList = combinedFutures.thenApply(__ -> list.stream().map(future -> future.join()).collect(Collectors.toList())).get();
		    deviceDataList.forEach(data -> dataList.add(data));
			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
}
