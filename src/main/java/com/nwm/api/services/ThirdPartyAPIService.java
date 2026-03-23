/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.nwm.api.entities.APIAccessLoggingDTO;
import com.nwm.api.entities.ApiAccessEntity;
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
						maps.put("device_id", device.get("id"));
						maps.put("device_name", device.get("name"));
						
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
    
    public Map<String, Object> getAPIEndpointParam(String key, HttpServletRequest request) {
    	Map<String, Object> map = new HashMap<>();
    	map.put("key", key);
		map.put("route", request.getRequestURI().substring(request.getContextPath().length()));
		map.put("method", request.getMethod());
		return map;
	}
    /**
     * @description check user can access api
     * @param key
     * @param endpoint
     * @param method
     */
    public boolean checkUserCanAccessEndPoint(String key, String endpoint, String method) {
        try {
            Map<String, Long> result = (Map<String, Long>) queryForObject("ApiAccess.checkUserCanAccessEndPoint", new APIAccessLoggingDTO(endpoint, method, key));
            if (result == null) {
                return false;
            }
            Long totalEndpoint = result.get("total_endpoint");
            Long totalSite     = result.get("total_site");
            if (totalEndpoint == 0 || totalSite == 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * @description check rate limit of user
     */
    public boolean checkRateLimit(ApiAccessEntity entity) {
        try {
            if (entity == null || entity.getRate_limit() == null || entity.getRate_limit() == 0) {
                return false;
            }
            Long total = (Long) queryForObject("ApiAccess.getUserTotalAccessEndPoint", new APIAccessLoggingDTO("", "", entity.getSecurity_key()));
            return total != null && total < entity.getRate_limit();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean checkAccessInMinute(ApiAccessEntity entity) {
        try {
            if (entity == null || entity.getRate_limit_per_min() == null || entity.getRate_limit_per_min() == 0) {
                return false;
            }
            Long total = (Long) queryForObject("ApiAccess.getUserTotalAccessEndPoint", new APIAccessLoggingDTO("", "", entity.getSecurity_key(), 1));
            return total != null && total < entity.getRate_limit_per_min();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * @description validate user security key
     * @param key
     */
    public String checkKey(String key, HttpServletRequest request) {
        try {
            if (Lib.isBlank(key)) {
                return "Key is required.";
            }
            ApiAccessService apiAccessService = new ApiAccessService();
            ApiAccessEntity entity = apiAccessService.getByApiKey(key);
            if (!apiAccessService.validateApiKey(key) || entity == null) {
                return "Key is invalid.";
            }
            String endpoint = request.getRequestURI().substring(request.getContextPath().length());
            String method = request.getMethod();
            if (!checkUserCanAccessEndPoint(key, endpoint, method)) {
                return "Can not access this endpoint";
            }

            if (!checkRateLimit(entity)) {
                return "Rate limit is full this month";
            }
            LocalDate now = LocalDate.now();
            LocalDate nextBillingDate = LocalDate.parse(entity.getNext_billing_date());
            if (now.isAfter(nextBillingDate)) {
                return "You are not payment this term";
            }
            if (!checkAccessInMinute(entity)) {
                // lock user
                Map<String, Object> params = new HashMap<>();
                params.put("security_key", key);
                params.put("status", 2);
                update("ApiAccess.updateConfig", params);
                return "Rate limit is full in 1 minute";
            }
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * @description check site is disable config by device id
     * @param key
     */
    public Map<String, Object> checkSiteDisabled(String key, ThirdPartyAPIEntity params) {
        try {
            if (Lib.isBlank(key) || params == null || Lib.isBlank(params.getDevice_id())) {
                return null;
            }
            Map<String, Object> obj = new HashMap<>();
            obj.put("device_id", params.getDevice_id());
            obj.put("security_key", key);
            Map<String, Object> res = (Map<String, Object>) queryForObject("ApiAccess.checkSiteDisabled", obj);
            return  res;
        } catch (Exception e) {
            return null;
        }
    }
}
