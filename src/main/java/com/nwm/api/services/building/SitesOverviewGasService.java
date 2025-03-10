/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services.building;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.building.ChartConsumptionEntity;
import com.nwm.api.entities.building.SitesOverviewGasConsumptionEntity;
import com.nwm.api.entities.building.SitesOverviewGasEntity;
import com.nwm.api.entities.building.SitesOverviewGasEventEntity;
import com.nwm.api.entities.building.SitesOverviewGasSummaryEntity;
import com.nwm.api.utils.Lib;

public class SitesOverviewGasService extends DB {
	
	/**
	 * @description create date time list
	 * @author Hung.Bui
	 * @since 2025-02-17
	 * @param obj device object
	 * @param start start date time
	 * @param end end date time
	 * @return
	 */
	private List<ChartConsumptionEntity> getDateTimeList(SitesOverviewGasEntity obj) {
		List<ChartConsumptionEntity> dateTimeList = new ArrayList<ChartConsumptionEntity>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse(obj.getEnd_date(), formatter).withHour(0).withMinute(0).withSecond(0);
		LocalDateTime end = start.withHour(23).withMinute(59).withSecond(59);
		
		try {
			DateTimeFormatter timefullFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:00");
			DateTimeFormatter categoryTimeFormat = DateTimeFormatter.ofPattern("HH:00");
			ChronoUnit timeUnit = ChronoUnit.HOURS;
		
			switch (obj.getId_filter()) {
				case "today":
				default:
					break;
				case "this_month":
				case "last_month":
					start = LocalDateTime.parse(obj.getEnd_date(), formatter).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
					end = start.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
					timefullFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
					categoryTimeFormat = DateTimeFormatter.ofPattern("dd. LLL");
					timeUnit = ChronoUnit.DAYS;
					break;
				case "12_month":
					start = LocalDateTime.parse(obj.getEnd_date(), formatter).minusMonths(12).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
					end = LocalDateTime.parse(obj.getEnd_date(), formatter).withHour(23).withMinute(59).withSecond(59);
					timefullFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
					categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
					timeUnit = ChronoUnit.MONTHS;
					break;
				case "lifetime":
					start = LocalDateTime.parse(obj.getCommissioning(), formatter).withHour(0).withMinute(0).withSecond(0);
					end = LocalDateTime.parse(obj.getEnd_date(), formatter).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
					timefullFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
					categoryTimeFormat = DateTimeFormatter.ofPattern("LLL. yyyy");
					timeUnit = ChronoUnit.MONTHS;
					break;
			}
			
			while (!start.isAfter(end)) {
				ChartConsumptionEntity dateTime = new ChartConsumptionEntity();
				dateTime.setTime_full(start.format(timefullFormat));
				dateTime.setCategories_time(start.format(categoryTimeFormat));
				dateTimeList.add(dateTime);
				start = start.plus(1, timeUnit);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return dateTimeList;
	}
	
	/**
	 * @description Get consumption
	 * @author Hung.Bui
	 * @since 2025-02-17
	 * @param SitesOverviewGasEntity
	 * @return SitesOverviewGasConsumptionEntity
	 */
	public SitesOverviewGasConsumptionEntity getConsumption(SitesOverviewGasEntity obj) {
		SitesOverviewGasConsumptionEntity entity = new SitesOverviewGasConsumptionEntity();
		
		try {
			List<DeviceEntity> devices = this.getGasMeters(obj);
			if (devices.size() == 0) return entity;
			obj.setDevices(devices);
			
			List<ChartConsumptionEntity> dataList = queryForList("SitesOverviewGas.getConsumption", obj);
			List<ChartConsumptionEntity> fulfillData = Lib.fulfillData(getDateTimeList(obj), dataList, "time_full");
			if (fulfillData.size() > 0) {
				entity.setData(fulfillData);
				OptionalDouble avg = fulfillData.stream().filter(item -> item.getValue() != null).mapToDouble(item -> item.getValue()).average();
				entity.setAverage(avg.isPresent() ? new BigDecimal(avg.getAsDouble()).setScale(0, RoundingMode.HALF_UP).doubleValue() : null);
			}
		} catch (Exception ex) {
			log.error("SitesOverviewGas.getConsumption", ex);
		}
		
		return entity;
	}
	
	/**
	 * @description Get gas meters
	 * @author Hung.Bui
	 * @since 2025-02-17
	 * @param SitesOverviewGasEntity
	 * @return List<DeviceEntity>
	 */
	public List<DeviceEntity> getGasMeters(SitesOverviewGasEntity obj) {
		try {
			List<DeviceEntity> dataList = queryForList("SitesOverviewGas.getGasMeters", obj);
			if (dataList != null && dataList.size() > 0) return dataList;
		} catch (Exception ex) {
			log.error("SitesOverviewGas.getGasMeters", ex);
		}
		return new ArrayList<DeviceEntity>();
	}
	
	/**
	 * @description Get summary
	 * @author Hung.Bui
	 * @since 2025-02-19
	 * @param SitesOverviewGasEntity
	 * @return List
	 */
	public Map<String, SitesOverviewGasSummaryEntity> getSummary(SitesOverviewGasEntity obj) {
		Map<String, SitesOverviewGasSummaryEntity> map = new HashMap<String, SitesOverviewGasSummaryEntity>();
		
		try {
			List<DeviceEntity> devices = this.getGasMeters(obj);
			if (devices.size() == 0) return map;
			obj.setDevices(devices);
			
			List<ChartConsumptionEntity> dataList = queryForList("SitesOverviewGas.getSummary", obj);
			
			Double currentMonth = dataList.get(0).getValue();
			Double lastMonth = dataList.get(1).getValue();
			Double monthBeforeLastMonth = dataList.get(2).getValue();
			Double currentDay = dataList.get(3).getValue();
			Double lastDay = dataList.get(4).getValue();
			Double currentYear = dataList.get(5).getValue();
			Double lastYear = dataList.get(6).getValue();
			Double life_time = dataList.get(7).getValue();
			map.put("current_month", new SitesOverviewGasSummaryEntity(currentMonth, lastMonth == 0 ? (currentMonth == 0 ? 0 : 100) : new BigDecimal((currentMonth - lastMonth) / lastMonth * 100).setScale(1, RoundingMode.HALF_UP).doubleValue()));
			map.put("last_month", new SitesOverviewGasSummaryEntity(lastMonth, monthBeforeLastMonth == 0 ? (lastMonth == 0 ? 0 : 100) : new BigDecimal((lastMonth - monthBeforeLastMonth) / monthBeforeLastMonth * 100).setScale(1, RoundingMode.HALF_UP).doubleValue()));
			map.put("daily_usage", new SitesOverviewGasSummaryEntity(currentDay, lastDay == 0 ? (currentDay == 0 ? 0 : 100) : new BigDecimal((currentDay - lastDay) / lastDay * 100).setScale(1, RoundingMode.HALF_UP).doubleValue()));
			map.put("yearly_usage", new SitesOverviewGasSummaryEntity(currentYear, lastYear == 0 ? (currentYear == 0 ? 0 : 100) : new BigDecimal((currentYear - lastYear) / lastYear * 100).setScale(1, RoundingMode.HALF_UP).doubleValue()));
			map.put("lifetime", new SitesOverviewGasSummaryEntity(life_time, 0.0));
		} catch (Exception ex) {
			log.error("SitesOverviewGas.getSummary", ex);
		}
		
		return map;
	}
	
	/**
	 * @description Get alerts
	 * @author Hung.Bui
	 * @since 2025-02-20
	 * @param List<SitesOverviewGasEventEntity>
	 * @return List
	 */
	public List<SitesOverviewGasEventEntity> getEvents(SitesOverviewGasEntity obj) {
		List<SitesOverviewGasEventEntity> dataList = new ArrayList<SitesOverviewGasEventEntity>();
		
		try {
			List<DeviceEntity> devices = this.getGasMeters(obj);
			if (devices.size() == 0) return dataList;
			obj.setDevices(devices);
			
			dataList = queryForList("SitesOverviewGas.getEvents", obj);
		} catch (Exception ex) {
			log.error("SitesOverviewGas.getEvents", ex);
		}
		
		return dataList;
	}
	
}
