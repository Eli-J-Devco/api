/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services.building;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.HVACGatewayEntity;
import com.nwm.api.entities.building.ChartConsumptionEntity;
import com.nwm.api.entities.building.HVACMappingPointEntity;
import com.nwm.api.entities.building.SitesOverviewHVACFieldChartEntity;
import com.nwm.api.entities.building.SitesOverviewHVACLayoutMapEntity;
import com.nwm.api.services.SitesAnalyticsService;
import com.nwm.api.utils.Lib;

@Service
public class SitesOverviewHVACService extends DB {
	
	/**
	 * @description Save mapping points
	 * @author Hung.Bui
	 * @since 2025-03-22
	 */
	public boolean saveMappingPoints(SitesOverviewHVACLayoutMapEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			session.delete("SitesOverviewHVAC.deleteMappingPoints", obj);
			session.insert("SitesOverviewHVAC.insertMappingPoints", obj);
			session.commit();
			return true;
	    } catch(Exception ex) {
	    	session.rollback();
	        log.error("SitesOverviewHVAC.saveMappingPoints", ex);
	        return false;
	    } finally {
			session.close();
		}
	}
	
	/**
	 * @description Get mapping points
	 * @author Hung.Bui
	 * @since 2025-03-22
	 * @param SitesOverviewHVACLayoutMapEntity
	 * @return List<HVACMappingPointEntity>
	 */
	public List<HVACMappingPointEntity> getMappingPoints(SitesOverviewHVACLayoutMapEntity obj) {
		try {
			List<HVACMappingPointEntity> dataList = queryForList("SitesOverviewHVAC.getMappingPoints", obj);
			if (dataList != null && dataList.size() > 0) return dataList;
		} catch (Exception ex) {
			log.error("SitesOverviewHVAC.getMappingPoints", ex);
		}
		return new ArrayList<HVACMappingPointEntity>();
	}
	
	/**
	 * @description Save config points
	 * @author Hung.Bui
	 * @since 2025-03-31
	 */
	public boolean saveConfigPoints(SitesOverviewHVACLayoutMapEntity obj) {
		SqlSession session = this.beginTransaction();
		try {
			session.delete("SitesOverviewHVAC.deleteConfigPoints", obj);
			session.insert("SitesOverviewHVAC.insertConfigPoints", obj);
			session.commit();
			return true;
	    } catch(Exception ex) {
	    	session.rollback();
	        log.error("SitesOverviewHVAC.saveConfigPoints", ex);
	        return false;
	    } finally {
			session.close();
		}
	}
	
	/**
	 * @description Get config points
	 * @author Hung.Bui
	 * @since 2025-03-31
	 * @param SitesOverviewHVACLayoutMapEntity
	 * @return List<HVACConfigPointEntity>
	 */
	public List<String> getConfigPoints(SitesOverviewHVACLayoutMapEntity obj) {
		try {
			List<String> dataList = queryForList("SitesOverviewHVAC.getConfigPoints", obj);
			if (dataList != null && dataList.size() > 0) return dataList;
		} catch (Exception ex) {
			log.error("SitesOverviewHVAC.getConfigPoints", ex);
		}
		return new ArrayList<String>();
	}
	
	/**
	 * Get field chart
	 * @author Hung.Bui
	 * @since 2025-04-11
	 * @param obj
	 * @return List<ChartConsumptionEntity>
	 */
	public List<ChartConsumptionEntity> getFieldChart(SitesOverviewHVACFieldChartEntity obj) {
		try {
			SitesAnalyticsService sitesAnalyticsService = new SitesAnalyticsService();
			DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
			DateTimeFormatter isoDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime startDate = LocalDateTime.parse(obj.getStart_date(), inputDateFormat).withHour(0).withMinute(0).withSecond(0);
			LocalDateTime endDate = LocalDateTime.parse(obj.getEnd_date(), inputDateFormat).withHour(23).withMinute(59).withSecond(59);
			
			obj.setStart_date(startDate.format(isoDateFormat));
			obj.setEnd_date(endDate.format(isoDateFormat));
			List<ChartConsumptionEntity> dataList = queryForList("SitesOverviewHVAC.getFieldChart", obj);
			
			DeviceEntity device = new DeviceEntity();
			device.setData_send_time(obj.getData_send_time());
			device.setFilterBy(obj.getId_filter());
			List<Map<String, Object>> dateTimeList = sitesAnalyticsService.getDateTimeList(device, startDate, endDate);
			List<ChartConsumptionEntity> convertedDateTimeList = new ArrayList<ChartConsumptionEntity>();
			for (Map<String, Object> map : dateTimeList) {
				ChartConsumptionEntity item = new ChartConsumptionEntity();
				item.setTime_full(map.get("time_full").toString());
				item.setCategories_time(map.get("categories_time").toString());
				item.setValue((Double)map.get("value"));
				convertedDateTimeList.add(item);
			}
			
			return Lib.fulfillData(convertedDateTimeList, dataList, "time_full");
		} catch (Exception ex) {
			log.error("SitesOverviewHVAC.getFieldChart", ex);
		}
		return new ArrayList<ChartConsumptionEntity>();
	}
	
	/**
	 * Get gateway list.
	 * @author Hung.Bui
	 * @since 2025-04-08
	 * @return list of gateway
	 */
	public List<HVACGatewayEntity> getGatewayList() {
		try {
			List<HVACGatewayEntity> dataList = queryForList("SitesOverviewHVAC.getGatewayList", null);
			if (dataList != null && dataList.size() > 0) return dataList;
		} catch (Exception ex) {
			log.error("SitesOverviewHVAC.getGatewayList", ex);
		}
		return new ArrayList<>();
	}
	
	private static final Map<String, Map<String, String>> fieldCache = new HashMap<>();
	private static final List<Map<String, String>> updatingFieldList = new ArrayList<>();
	
	public Map<String, Object> getCacheStatistics() {
		Map<String, Object> stats = new HashMap<>();
		stats.put("fieldCacheSize", fieldCache.size());
		stats.put("updatingFieldListSize", updatingFieldList.size());
		
		Runtime runtime = Runtime.getRuntime();
		long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
		stats.put("usedMemoryMB", usedMemory);
		
		return stats;
	}
	
	/**
	 * Force clear all cache
	 * @author Hung.Bui  
	 * @since 2025-11-12
	 */
	public Map<String, Object> forceClearCache() {
		int originalFieldCacheSize = fieldCache.size();
		int originalUpdatingListSize = updatingFieldList.size();
		
		fieldCache.clear();
		updatingFieldList.clear();
		
		System.gc(); // Suggest garbage collection
		
		Map<String, Object> result = new HashMap<>();
		result.put("clearedFieldCacheEntries", originalFieldCacheSize);
		result.put("clearedUpdatingListEntries", originalUpdatingListSize);
		result.put("message", "Cache cleared and GC suggested");
		

		
		return result;
	}
	
	/**
	 * Clear old cache entries - SIMPLE and SAFE
	 * @author Hung.Bui
	 * @since 2025-11-12
	 */
	public static void clearOldCache() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		int minutesToCache = 15;
		
		fieldCache.entrySet().removeIf(entry -> {
			try {
				String timestamp = entry.getValue().get("ts");
				if (timestamp == null) return true;
				LocalDateTime entryTime = LocalDateTime.parse(timestamp, formatter);
				
				// if value in cache is no longer updated for more than __ minutes, remove it
				return entryTime.isBefore(LocalDateTime.now().minusMinutes(minutesToCache));
			} catch (Exception e) {
				return true;
			}
		});

	}
	
	/**
	 * Save field data (cache data for 5 minutes then saving to database).
	 * @author Hung.Bui
	 * @since 2025-04-08
	 * @param message MQTT message
	 */
	public void saveFieldData(Message<?> message) {
		try {
			int maxBatchSize = 500;
			int minutesToCache = 5; 
			ObjectMapper mapper = new ObjectMapper();
			
			// Cache cleanup to prevent memory leak
			if (!fieldCache.isEmpty()) {
				clearOldCache();
			}
			
			String decompressedPayload = decompressGzip(message.getPayload());
			if (decompressedPayload == null) return;
			List<Map<String, Object>> payload = mapper.readValue(decompressedPayload, new TypeReference<List<Map<String, Object>>>(){});
			if (payload == null || payload.size() == 0) return;
			String id_gateway = message.getHeaders().get("mqtt_receivedTopic").toString().split("/")[3];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			for (Map<String, Object> item : payload) {
				String id = item.get("itemId").toString();
				List<Map<String, Object>> telemetry = (List<Map<String, Object>>) item.get("telemetry");
				if (telemetry == null || telemetry.size() == 0) continue;
				List<Map<String, String>> values = (List<Map<String, String>>) telemetry.get(0).get("values");
				if (values == null || values.size() == 0) continue;
				
				Map<String, String> value = (Map<String, String>) values.get(0);
				LocalDateTime updatedDateTime = LocalDateTime.parse(value.get("ts").toString(), DateTimeFormatter.ISO_DATE_TIME);
				value.put("id", id);
				value.put("id_gateway", id_gateway);
				value.put("ts", updatedDateTime.format(formatter));
				
				if (fieldCache.get(id) != null) {
					LocalDateTime lastDateTime = LocalDateTime.parse(((Map<String, String>) fieldCache.get(id)).get("ts").toString(), formatter);
					if (ChronoUnit.MINUTES.between(lastDateTime, updatedDateTime) < minutesToCache) continue;
				}
				
				fieldCache.put(id, value);
				updatingFieldList.add(value);
			}
			
			if (updatingFieldList.size() > maxBatchSize) {
				List<Map<String, String>> insertData = new ArrayList<>(updatingFieldList);
				CompletableFuture.runAsync(() -> {
					SqlSession session = sqlMap.openSession(ExecutorType.BATCH, false);
					
					try {
						List<Map<String, String>> dataList = session.selectList("SitesOverviewHVAC.getLastestFieldData", insertData);
						for (int i = 0; i < dataList.size(); i++) {
							session.insert("SitesOverviewHVAC.insertFieldData", dataList.get(i));
							if (i % 100 == 0) session.flushStatements();
						}
						session.commit();
					} catch (Exception ex) {
						log.error("SitesOverviewHVAC.saveFieldData", ex);
						session.rollback();
					} finally {
						session.close();
					}
				});
				updatingFieldList.clear();
			}
		} catch (Exception ex) {
			log.error("SitesOverviewHVAC.saveFieldData", ex);
		}
	}
	
        private String decompressGzip(Object payload) {
            try (
                ByteArrayInputStream bis = new ByteArrayInputStream((byte[]) payload);
                GZIPInputStream gis = new GZIPInputStream(bis);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ) {
                byte[] buffer = new byte[1024];
                int len;

                while ((len = gis.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }

                return new String(bos.toByteArray());
            } catch (Exception e) {
                return null;
            }
    }
	
}
