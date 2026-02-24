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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;

@Service
public class SiteExternalAPIService extends DB {

	@Autowired
	private ThirdPartyAPIService thirdPartyAPIService;

    /**
     * @description get site information with devices for external API
     * @author Duc.Pham
     * @since 2025-02-13
     * @param key API security key
     * @param request HTTP request
     * @return List of sites with devices
     */
    public List getSite(String key, Integer page, HttpServletRequest request) {
        try {
            Map<String, Object> param = thirdPartyAPIService.getAPIEndpointParam(key, request);
            final int limit = 50;
            final int offset = (page <= 0) ? 0 : (page - 1) * limit;
            param.put("limit", limit);
            param.put("offset", offset);
            List<Map<String, Object>> siteList = queryForList("SiteExternalAPI.getSite", param);
            if (siteList == null || siteList.isEmpty()) return new ArrayList<>();

            // Get devices for each site
            for (Map<String, Object> site : siteList) {
                Integer idSite = (Integer) site.get("id_site");
                if (idSite != null) {
                    Map<String, Object> deviceParam = new HashMap<>();
                    deviceParam.put("id_site", idSite);
                    List<Map<String, Object>> devices = queryForList("SiteExternalAPI.getDevicesBySite", deviceParam);
                    site.put("devices", devices != null ? devices : new ArrayList<>());
                }
            }

            return siteList;
        } catch (Exception ex) {
            log.error("SiteExternalAPIService.getSite", ex);
            return new ArrayList<>();
        }
    }

    /**
     * @description Check if user can access API endpoint (reuse from ThirdPartyAPIService)
     * @param key API security key
     * @param endpoint API endpoint route
     * @param method HTTP method
     * @return true if user can access, false otherwise
     */
    public boolean checkUserCanAccessEndPoint(String key, String endpoint, String method) {
        return thirdPartyAPIService.checkUserCanAccessEndPoint(key, endpoint, method);
    }

    /**
     * @description Check rate limit for user (reuse from ThirdPartyAPIService)
     * @param key API security key
     * @return true if within limit, false otherwise
     */
    public boolean checkRateLimit(String key) {
        return thirdPartyAPIService.checkRateLimit(key);
    }
}
