/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ModelHostMetricsEntity;
import com.nwm.api.utils.Lib;

@Service
public class ModelHostMetricsService extends DB {
	/**
	 * @description set data ModelHostMetrics
	 * @author duy.phan
	 * @since 2026-10-14
	 * @param data
	 */
	
	public ModelHostMetricsEntity setModelHostMetrics(String line) {
		try {
			List<String> words = Lists.newArrayList(Splitter.on(',').split(line));
			if (words.size() > 0) {
				ModelHostMetricsEntity dataModelHostMetrics = new ModelHostMetricsEntity();
							
				dataModelHostMetrics.setTime(words.get(0).replace("'", ""));
				dataModelHostMetrics.setError(Integer.parseInt(!Lib.isBlank(words.get(1)) ? words.get(1) : "0"));
				dataModelHostMetrics.setLow_alarm(Integer.parseInt(!Lib.isBlank(words.get(2)) ? words.get(2) : "0"));
				dataModelHostMetrics.setHigh_alarm(Integer.parseInt(!Lib.isBlank(words.get(3)) ? words.get(3) : "0"));
				
				dataModelHostMetrics.setCpu_usage(Double.parseDouble(!Lib.isBlank(words.get(4)) ? words.get(4) : "0.001"));
				dataModelHostMetrics.setIo_delay(Double.parseDouble(!Lib.isBlank(words.get(5)) ? words.get(5) : "0.001"));
				dataModelHostMetrics.setLoad1(Double.parseDouble(!Lib.isBlank(words.get(6)) ? words.get(6) : "0.001"));
				dataModelHostMetrics.setLoad5(Double.parseDouble(!Lib.isBlank(words.get(7)) ? words.get(7) : "0.001"));
				dataModelHostMetrics.setLoad15(Double.parseDouble(!Lib.isBlank(words.get(8)) ? words.get(8) : "0.001"));
				dataModelHostMetrics.setRam_usage(Double.parseDouble(!Lib.isBlank(words.get(9)) ? words.get(9) : "0.001"));
				dataModelHostMetrics.setRam_used_mb(Double.parseDouble(!Lib.isBlank(words.get(10)) ? words.get(10) : "0.001"));
				dataModelHostMetrics.setRam_total_mb(Double.parseDouble(!Lib.isBlank(words.get(11)) ? words.get(11) : "0.001"));			
				dataModelHostMetrics.setDisk_usage(Double.parseDouble(!Lib.isBlank(words.get(12)) ? words.get(12) : "0.001"));
				dataModelHostMetrics.setDisk_used_mb(Double.parseDouble(!Lib.isBlank(words.get(13)) ? words.get(13) : "0.001"));
				dataModelHostMetrics.setDisk_total_mb(Double.parseDouble(!Lib.isBlank(words.get(14)) ? words.get(14) : "0.001"));
				dataModelHostMetrics.setDisk_free_mb(Double.parseDouble(!Lib.isBlank(words.get(15)) ? words.get(15) : "0.001"));			
				dataModelHostMetrics.setDisk_avail_mb(Double.parseDouble(!Lib.isBlank(words.get(16)) ? words.get(16) : "0.001"));
				dataModelHostMetrics.setSwap_usage(Double.parseDouble(!Lib.isBlank(words.get(17)) ? words.get(17) : "0.001"));
				dataModelHostMetrics.setKsm_sharing_mb(Double.parseDouble(!Lib.isBlank(words.get(18)) ? words.get(18) : "0.001"));		
				dataModelHostMetrics.setNet_rx_bytes_per_sec(Double.parseDouble(!Lib.isBlank(words.get(19)) ? words.get(19) : "0.001"));
				dataModelHostMetrics.setNet_tx_bytes_per_sec(Double.parseDouble(!Lib.isBlank(words.get(20)) ? words.get(20) : "0.001"));
				dataModelHostMetrics.setNet_rx_total_mb(Double.parseDouble(!Lib.isBlank(words.get(21)) ? words.get(21) : "0.001"));
				dataModelHostMetrics.setNet_tx_total_mb(Double.parseDouble(!Lib.isBlank(words.get(22)) ? words.get(22) : "0.001"));
				dataModelHostMetrics.setUptime_seconds(Double.parseDouble(!Lib.isBlank(words.get(23)) ? words.get(23) : "0.001"));
							
				dataModelHostMetrics.setIp_addresses(!Lib.isBlank(words.get(24)) ? words.get(24) : "0.001");
				dataModelHostMetrics.setPrimary_ip(!Lib.isBlank(words.get(25)) ? words.get(25) : "0.001");
				dataModelHostMetrics.setInf1(!Lib.isBlank(words.get(26)) ? words.get(26) : "0.001");
				dataModelHostMetrics.setInf2(!Lib.isBlank(words.get(27)) ? words.get(27) : "0.001");
				dataModelHostMetrics.setInf3(!Lib.isBlank(words.get(28)) ? words.get(28) : "0.001");
				dataModelHostMetrics.setInf4(!Lib.isBlank(words.get(29)) ? words.get(29) : "0.001");
				dataModelHostMetrics.setInf5(!Lib.isBlank(words.get(30)) ? words.get(30) : "0.001");
				
				
				return dataModelHostMetrics;
				
			} else {
				return new ModelHostMetricsEntity();
			}
			
			
		} catch (Exception ex) {
			log.error("insert", ex);
			return new ModelHostMetricsEntity();
		}
	}
	

	/**
	 * @description insert datamodel_host_metrics
	 * @author duy.phan
	 * @since 2026-10-14
	 * @param data from datalogger
	 */
	
	public boolean insertModelHostMetrics(ModelHostMetricsEntity obj) {
		try {
			Object insertId = insert("ModelHostMetrics.insertModelHostMetrics", obj);
	        if(insertId == null ) {
	        	return false;
	        }
	        return true;
		} catch (Exception ex) {
			log.error("insert", ex);
			return false;
		}

	}
}
