package com.nwm.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.SiteEntity;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class SolarEdgeService extends DB  {

    private final RestApiService restApiService = new RestApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    private final String DOMAIN_URL = "https://monitoringapi.solaredge.com";

    public Map<String, Object> getSolarEdgeInfo(SiteEntity obj) {
        try {
            SiteEntity siteEntity = (SiteEntity) queryForObject("SolarEdge.getSolarEdgeInfo", obj);
            if (siteEntity == null) {
                return null;
            }
            Map<String, Object> data = new HashMap<>();
            data.put("solar_edge_id", siteEntity.getSolar_edge_id());
            data.put("solar_edge_api_key", siteEntity.getSolar_edge_api_key());
            data.put("id", siteEntity.getId());
            return data;
        } catch (Exception e) {
            this.log.error("SolarEdgeService.getSolarEdgeInfo ", e);
        }
        return null;
    }

    public Map<String, Object> getSolarEdgeSiteInfo(SiteEntity obj) {
        try {
            Long solar_edge_id = obj.getSolar_edge_id();
            String api_key = obj.getSolar_edge_api_key();

            StringBuilder url = new StringBuilder();
            url.append(DOMAIN_URL);
            url.append("/site");
            url.append("/").append(solar_edge_id);
            url.append("/details?api_key=").append(api_key);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            String response = restApiService.callApi(
                    url.toString(),
                    HttpMethod.GET,
                    headers,
                    null
            );

            return mapper.readValue(
                    response,
                    new TypeReference<Map<String, Object>>() {}
            );

        } catch (Exception e) {
            this.log.error("SolarEdgeService.getSolarEdgeSiteInfo ", e);
        }
        return null;
    }

    public Map<String, Object> syncInventory(SiteEntity obj) {
        try {
            Long solar_edge_id = obj.getSolar_edge_id();
            String api_key = obj.getSolar_edge_api_key();

            StringBuilder url = new StringBuilder();
            url.append(DOMAIN_URL);
            url.append("/site");
            url.append("/").append(solar_edge_id);
            url.append("/inventory?api_key=").append(api_key);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");

            String response = restApiService.callApi(
                    url.toString(),
                    HttpMethod.GET,
                    headers,
                    null
            );

            return mapper.readValue(
                    response,
                    new TypeReference<Map<String, Object>>() {}
            );

        } catch (Exception e) {
            this.log.error("SolarEdgeService.syncInventory", e);
        }
        return null;
    }


}
