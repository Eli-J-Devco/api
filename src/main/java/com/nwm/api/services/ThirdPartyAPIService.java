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

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEnergyThirdPartyAPIEntity;

@Service
public class ThirdPartyAPIService extends DB {
	
	/**
	 * @description get energy generation whole sites in portfolio for 3rd party, each site must have 3rd party key and access domain origin
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
			List devicesList = queryForList("ThirdPartyAPI.getListDevicesBy3rdParty", map);
			if (devicesList == null) return new ArrayList();
			
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
			return new ArrayList();
		}
	}
	
}
