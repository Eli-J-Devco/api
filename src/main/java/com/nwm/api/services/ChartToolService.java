/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ChartToolEntity;
public class ChartToolService extends DB {

	/**
	 * @description get chart data energy
	 * @author long.pham
	 * @since 2021-05-12
	 * @param id_site, id_customer
	 */

	public List getChartData(ChartToolEntity obj) {
		List dataEnergy = new ArrayList<>();
		try {
			
			// Get device info
			if(obj.getParameter().size() > 0) {
				for (int i = 0; i < obj.getParameter().size(); i++) {
					// Get data chart
					Map<String, Object> item = (Map<String, Object>) obj.getParameter().get(i);
					item.put("start_date", obj.getStart_date());
					item.put("end_date", obj.getEnd_date());
					item.put("filterBy", obj.getFilterBy());
					item.put("setup_send_time", obj.getSetup_send_time());
					item.put("data_send_time", obj.getData_send_time());
					List dataEnergyDevice = queryForList("ChartTool.getChartData", item);
					item.put("data", dataEnergyDevice);
					dataEnergy.add(item);
					
				}
			}
			return dataEnergy;
		} catch (Exception ex) {
			return new ArrayList();
		}

	}
}
