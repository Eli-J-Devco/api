package com.nwm.api.services.building;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.building.PortfolioBuildingEntity;


public class PortfolioBuildingService extends DB {
	/**
	 * @description get list portfolio by array(id_site)
	 * @author duy.phan 
	 * @since 2025-11-20
	 * @param arr id_site
	 */

	public List getList(PortfolioBuildingEntity obj) {
		List dataList = new ArrayList();
		try {
			if(obj.getId_sites() == null || obj.getId_sites().isEmpty()) {
				List sites = getSites(obj);
				if (sites.size() == 0) return new ArrayList<>();			
				obj.setList_site(sites);
			} else {
				obj.setList_site(obj.getId_sites());
			}
//			List sites = getSites(obj);
//			if (sites.size() == 0) return new ArrayList<>();			
//			obj.setList_site(sites);		
			dataList = queryForList("PortfolioBuilding.getList", obj);			
			return dataList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}
	
	/**
	 * @description Get site detail list
	 * @author duy.phan 
	 * @since 2025-11-20
	 * @param obj
	 */
	public List<Map<String, Object>> getSites(PortfolioBuildingEntity obj) {
		try {
			List<Map<String, Object>> dataList = queryForList("PortfolioBuilding.getSites", obj);
			if (dataList == null) return new ArrayList<>();
			return dataList;
		} catch (Exception ex) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * @description get list site for page employee manage site
	 * @author duy.phan 
	 * @since 2025-11-20
	 */

	public List getListSiteByEmployee(PortfolioBuildingEntity obj) {		
		try {			
				List dataList = queryForList("PortfolioBuilding.getAllSite", obj);
				if (dataList == null) return new ArrayList();
				
				ObjectMapper mapper = new ObjectMapper();
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, Object> item = (Map<String, Object>) dataList.get(i);
					
					try {
						List<Map<String, Object>> sites = mapper.readValue(item.get("options").toString(), new TypeReference<List<Map<String, Object>>>(){});
						sites.sort((s1, s2) -> s1.get("text").toString().compareTo(s2.get("text").toString()));
						item.put("options", sites);
					} catch (JsonProcessingException e) {
						item.put("options", new ArrayList<Map<String, Object>>());
					}
				}
				return dataList;
			} catch (Exception ex) {
				return new ArrayList();
			}
		}
}
