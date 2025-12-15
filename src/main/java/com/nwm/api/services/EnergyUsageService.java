/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Constants;
import com.nwm.api.utils.Lib;

public class EnergyUsageService extends DB {

	/**
	 * @description get chart data energy
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id_site, id_customer
	 */

	public List getChartDataEnergyUsage(SiteEntity obj) {
		try {
			List dataEnergy = new ArrayList<>();
			List<Map<String, Object>> dataListDeviceMeter = queryForList("EnergyUsage.getListDeviceTypeMeter", obj);
			
			if(dataListDeviceMeter.size() > 0 ) {
				List<CompletableFuture<List<ClientMonthlyDateEntity>>> list = new ArrayList<>();
				
				for (Map<String, Object> device : dataListDeviceMeter) {
					CompletableFuture<List<ClientMonthlyDateEntity>> future = CompletableFuture.supplyAsync(() -> {
						try {
							device.put("start_date", obj.getStart_date());
							device.put("end_date", obj.getEnd_date());
							device.put("id_filter", obj.getId_filter());
							device.put("filterBy", obj.getFilterBy());
							DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
							DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
							ChronoUnit timeUnit = ChronoUnit.MINUTES;
							LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
							LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
							
							// get Energy usage
							List<ClientMonthlyDateEntity> dataEnergyUsage = queryForList("EnergyUsage.getDataEnergyUsageV2", device);
							
							switch (obj.getId_filter()) {
								case "hourly":
								default:
									timeUnit = ChronoUnit.HOURS;
									timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
									categoriesTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
									if(!"today".equals(obj.getFilterBy() )) {
										timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
										categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
									}
									if ( dataEnergyUsage != null && !dataEnergyUsage.isEmpty()) {
										start = LocalDateTime.parse(dataEnergyUsage.get(0).getTime_full(), timeFullFormat);
									}
									break;
									
								case "day":
									timeUnit = ChronoUnit.DAYS;
									timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
									categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
									if ( dataEnergyUsage != null && !dataEnergyUsage.isEmpty()) {
										start = LocalDate.parse(dataEnergyUsage.get(0).getTime_full(), timeFullFormat).atStartOfDay();
									}
									break;
									
								case "month":
									timeUnit = ChronoUnit.MONTHS;
									timeFullFormat = DateTimeFormatter.ofPattern("yyyy-MM");
									categoriesTimeFormat = DateTimeFormatter.ofPattern("LLL, yyyy");
									if ( dataEnergyUsage != null && !dataEnergyUsage.isEmpty()) {
										YearMonth ym = YearMonth.parse(dataEnergyUsage.get(0).getTime_full(), timeFullFormat);
										start = ym.atDay(1).atStartOfDay();
									}
									break;
							}
							
							List<ClientMonthlyDateEntity> dateTimeList = new ArrayList<>();
							while (!start.isAfter(end)) {
								ClientMonthlyDateEntity dateTime = new ClientMonthlyDateEntity();
								dateTime.setTime_full(start.format(timeFullFormat));
								dateTime.setCategories_time(start.format(categoriesTimeFormat));
								dateTimeList.add(dateTime);
								start = start.plus(1, timeUnit);
							}
							
							List<ClientMonthlyDateEntity> fulfilledData = Lib.fulfillData(dateTimeList, dataEnergyUsage, "time_full");
							return fulfilledData;
						} catch (Exception e) {
							return new ArrayList<>();
						}
					});
					
					list.add(future);
				}
				
				List<ClientMonthlyDateEntity> res = list.stream()
						.map(future -> future.join())
						.filter(item -> item.size() > 0)
						.reduce(new ArrayList<ClientMonthlyDateEntity>(), (total, curr) -> {
							if (total.size() == 0) {
								total.addAll(curr);
							} else {
								for (int i = 0; i < total.size(); i++) {
									if (Objects.isNull(total.get(i).getChart_energy_kwh()) && Objects.isNull(curr.get(i).getChart_energy_kwh())) continue;
									total.get(i).setChart_energy_kwh(Optional.ofNullable(total.get(i).getChart_energy_kwh()).orElse(0.0) + Optional.ofNullable(curr.get(i).getChart_energy_kwh()).orElse(0.0));
								}
							}
							
							return total;
						});
				res.stream().forEach(item -> item.setChart_energy_kwh(Objects.nonNull(item.getChart_energy_kwh()) ? new BigDecimal(item.getChart_energy_kwh()).setScale(0, RoundingMode.HALF_UP).doubleValue() : null));
				
				Map<String, Object> deviceItem = new HashMap<>();
				deviceItem.put("data_energy", res);
				deviceItem.put("type", "consumption");
				deviceItem.put("devicename", "Consumption");
				dataEnergy.add(deviceItem);
			}
			
			return dataEnergy;
		} catch (Exception ex) {
            return new ArrayList<>();
		}


	}



	/**
	 * @description get chart data energy by device id
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id_device
	 */

	public List getChartDataEnergyByDevice(DeviceEntity obj) {
		try {
			List dataEnergy = new ArrayList<>();
			int interval = 1;
			DateTimeFormatter timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
			DateTimeFormatter categoriesTimeFormat = DateTimeFormatter.ofPattern("HH:mm a");
			ChronoUnit timeUnit = ChronoUnit.MINUTES;
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

			// get Energy usage
			List<ClientMonthlyDateEntity> dataEnergyUsage = new ArrayList<>();
			dataEnergyUsage = queryForList("EnergyUsage.getDataEnergyUsageByDevice", obj);
			switch (Constants.ChartingFilter.fromValue(obj.getFilterBy())) {
                case TODAY:
                    timeUnit = ChronoUnit.HOURS;
                    timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                    categoriesTimeFormat = DateTimeFormatter.ofPattern("hh:mm a");
                    break;
                case THIS_WEEK:
                case LAST_WEEK:
                case THIS_MONTH:
                case LAST_MONTH:
                    timeUnit = ChronoUnit.DAYS;
                    timeFullFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                    categoriesTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                    break;
                case LAST_12_MONTHS:
                case THIS_YEAR:
                case LIFETIME:
                    timeUnit = ChronoUnit.MONTHS;
                    timeFullFormat = DateTimeFormatter.ofPattern("MMM. yyyy");
                    categoriesTimeFormat = DateTimeFormatter.ofPattern("MMM, yyyy");
                    if ( dataEnergyUsage != null && !dataEnergyUsage.isEmpty()) {
                        YearMonth ym = YearMonth.parse(dataEnergyUsage.get(0).getTime_full(), timeFullFormat);
                        start = ym.atDay(1).atStartOfDay();
                    }
                    break;
			}
			
			List<ClientMonthlyDateEntity> dateTimeList = new ArrayList<>();
			while (!start.isAfter(end)) {
				ClientMonthlyDateEntity dateTime = new ClientMonthlyDateEntity();
				dateTime.setTime_full(start.format(timeFullFormat));
				dateTime.setCategories_time(start.format(categoriesTimeFormat));
				dateTimeList.add(dateTime);
				start = start.plus(interval, timeUnit);
			}
			
			
			List<ClientMonthlyDateEntity> fulfilledData = Lib.fulfillData(dateTimeList, dataEnergyUsage, "time_full");
			if (fulfilledData.size() > 0) {
				Map<String, Object> deviceItem = new HashMap<>();
				deviceItem.put("data_energy", fulfilledData);
				deviceItem.put("type", "consumption");
				deviceItem.put("devicename", "Consumption");
				dataEnergy.add(deviceItem);
			}
			
			return dataEnergy;
		} catch (Exception ex) {
			return new ArrayList();
		}

	}

}
