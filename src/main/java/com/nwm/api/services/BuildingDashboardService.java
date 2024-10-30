/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceGroupEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Constants;

public class BuildingDashboardService extends DB {

	/**
	 * @description get list load meter device
	 * @author long.pham
	 * @since 2024-10-29
	 * @returns array
	 */
	
	public List getListLoadMeterDevices(SiteEntity obj) {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BuildingDashboard.getListLoadMeterDevices", obj);
			if (dataList == null)
				return new ArrayList();
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	
	/**
	 * @description get list data field
	 * @author long.pham
	 * @since 2024-10-29
	 * @returns array
	 */
	
	@SuppressWarnings("unchecked")
	public List getListDeviceDataField(DeviceEntity obj) throws JsonProcessingException {
		List dataList = new ArrayList();
		try {
			dataList = queryForList("BuildingDashboard.getListDeviceDataField", obj);
			if (dataList == null)
				return new ArrayList();
			
			// Get last data
			Map<String, Object> dataLastValueField = (Map<String, Object>) queryForObject("BuildingDashboard.getLastDataField", obj);
			dataList.forEach((item) -> {
				try {
					List dataListGroupField = new ArrayList();
					List dataListField = queryForList("BuildingDashboard.getListField", item);
					if(dataListField.size() > 0) {
						for(int i = 0; i< dataListField.size(); i ++) {
							Map<String, Object> itemField = (Map<String, Object>) dataListField.get(i);
							String keyField = (String) itemField.get("slug");
							if(keyField != "") {
								Object valueField = dataLastValueField.get(keyField);
								itemField.put("value", valueField);
							} else {
								itemField.put("value", 0);
							}
							dataListGroupField.add(itemField);
						}
					}
					((Map<String, Object>) item).put("fields", dataListGroupField);
					
				} catch (SQLException e) {
					((Map<String, Object>) item).put("fields", new ArrayList<Map<String, Object>>());
				}
			});
		} catch (Exception ex) {
			return new ArrayList();
		}
		return dataList;
	}
	
	
	
	
	/**
	 * @description get dashboard chart data energy
	 * @author long.pham
	 * @since 2024-10-30
	 * @param {}
	 */

	public List getDashboardChartData(SiteEntity obj) {
		try {
			List dataEnergy = new ArrayList<>();
			return dataEnergy;
		} catch (Exception ex) {
			return new ArrayList();
		}

	}
	
	
}
