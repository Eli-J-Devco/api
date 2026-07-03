/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.AlertEntity;
import com.nwm.api.entities.ClientMonthlyDateEntity;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.ExpectedBySiteDTO;
import com.nwm.api.entities.PerformanceDataChartItemEntity;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Constants.ChartingFilter;
import com.nwm.api.utils.Constants.ChartingGranularity;
import com.nwm.api.utils.Constants.UploadingDataIntervals;
import com.nwm.api.utils.Lib;
import com.nwm.api.utils.SecretCards;

@Service
public class CustomerViewService extends DB {
	@Autowired
	SitesAnalyticsService sitesAnalyticsService;
	@Autowired
	DeviceService deviceService;
	@Autowired
	SiteService siteService;
	
	private List<ClientMonthlyDateEntity> convertDateTimeFormat(SiteEntity site, ChartingGranularity granularity, ChartingFilter filter, List<ClientMonthlyDateEntity> dataList, LocalDateTime start, LocalDateTime end) {
		try {
			List<Map<String, Object>> data = dataList
					.stream()
					.map(item -> ClientMonthlyDateEntity.convertDateTimeToMap(item))
					.collect(Collectors.toList());
			
			List<ClientMonthlyDateEntity> convertedDateTimeList = sitesAnalyticsService.convertDateTimeFormat(data, start, end, filter, granularity, site.getLocale(), site.getDate_format(), site.getTime_format())
					.stream()
					.map(item -> ClientMonthlyDateEntity.convertDateTimeToEntity(item))
					.collect(Collectors.toList());
			
			for (int i = 0; i < dataList.size(); i++) {
				ClientMonthlyDateEntity item = dataList.get(i);
				ClientMonthlyDateEntity convertedItem = convertedDateTimeList.get(i);
				item.setTime_full(convertedItem.getTime_full());
				item.setCategories_time(convertedItem.getCategories_time());
			}
		} catch (Exception e) {
		}
		
		return dataList;
	}
	
	private List<ClientMonthlyDateEntity> getDateTimeList(SiteEntity obj, LocalDateTime start, LocalDateTime end) {
		try {
			DeviceEntity chartParams = new DeviceEntity();
			chartParams.setData_send_time(obj.getData_send_time());
			chartParams.setFilterBy(obj.getFilterBy());
			
			return sitesAnalyticsService
					.getDateTimeList(chartParams, start, end)
					.stream()
					.map(item -> ClientMonthlyDateEntity.convertDateTimeToEntity(item))
					.collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<ClientMonthlyDateEntity>();
		}
	}
	
	/**
	 * @description get chart data energy
	 * @author long.pham
	 * @since 2020-12-04
	 * @param id_site, id_customer
	 */

	public List<PerformanceDataChartItemEntity> getChartDataPerformance(SiteEntity obj) {
		try {
			Optional<SiteEntity> siteOptional = siteService.getSiteById(obj.getId_site());
			if (!siteOptional.isPresent()) return new ArrayList<>();
			
			SiteEntity site = siteOptional.get();
			List<PerformanceDataChartItemEntity> dataEnergy = new ArrayList<>();
			DevicesByTypeEntity devices = deviceService.getDevicesBySite(obj);
			List<DeviceEntity> meterDevices = devices.getMeter();
			List<DeviceEntity> inverterDevices = devices.getInverter();
			List<DeviceEntity> irradianceDevices = devices.getIrradiance();
			List<DeviceEntity> powerDevices = meterDevices.size() > 0 ? meterDevices : inverterDevices;
			if (powerDevices.size() == 0) return new ArrayList<>();
			
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			ChartingGranularity chartingGranularity = ChartingGranularity.fromValue(obj.getData_send_time());
			ChartingFilter chartingFilter = ChartingFilter.fromValue(obj.getFilterBy());
			UploadingDataIntervals siteUploadingInterval = UploadingDataIntervals.fromValue(site.getData_send_time());
			boolean isFilterEnabled = obj.isFilterEnabled();
			boolean isPower = ChronoUnit.DAYS.between(start, end) < 5;
			
			// get list of time to exclude data from
//			List<Map<String, Object>> hiddenDataList = queryForList("CustomerView.getHiddenDataListBySite", obj);
//			obj.setHidden_data_list(hiddenDataList);
			
			// Show each meter
			if (meterDevices.size() > 1 && site.getIs_show_each_meter() == 1) {
				Map<Integer, List<ClientMonthlyDateEntity>> dataByDevices = getEnergyByDevice(start, end, meterDevices, chartingGranularity, chartingFilter, isFilterEnabled);
				
				dataByDevices.forEach((deviceId, data) -> {
					String deviceName = meterDevices.stream().filter(device -> device.getId() == deviceId).findFirst().map(DeviceEntity::getDevicename).orElse("");
					
					data.forEach(item -> {
						if (Objects.nonNull(item.getChart_energy_kwh())) item.setChart_energy_kwh(BigDecimal.valueOf(item.getChart_energy_kwh()).setScale(1, RoundingMode.HALF_UP).doubleValue());
					});
					
					PerformanceDataChartItemEntity deviceItem = new PerformanceDataChartItemEntity(convertDateTimeFormat(site, chartingGranularity, chartingFilter, data, start, end), "chart_energy_kwh", isPower ? "kW" : "kWh", deviceName, true);
					dataEnergy.add(deviceItem);
				});
			}
			
//			if (obj.getEnable_virtual_device() == 1) {
//				obj.setDatatablename(obj.getTable_data_virtual());
//				List<ClientMonthlyDateEntity> data = getDataByVirtualDevice(obj);
//				if (data.size() > 0) separateDataByType(dataEnergy, obj, data, irradianceDevices, isPower);
//			} else {
				if (powerDevices.size() > 0) {
					Map<Integer, List<ClientMonthlyDateEntity>> dataByDevices = getEnergyByDevice(start, end, powerDevices, chartingGranularity, chartingFilter, isFilterEnabled);
					
					if (!dataByDevices.isEmpty()) {
						List<ClientMonthlyDateEntity> data = dataByDevices
							.values()
							.stream()
							.flatMap(List::stream)
							.collect(Collectors.groupingBy(item -> sitesAnalyticsService.stringToDateTimeByGranularity(item.getTime_full(), chartingGranularity), TreeMap::new, Collectors.toList()))
							.values()
							.stream()
							.map(dataListItem -> {
								Supplier<DoubleStream> dataStream = () -> dataListItem.stream()
									.map(item -> item.getChart_energy_kwh())
									.filter(Objects::nonNull)
									.mapToDouble(Double::doubleValue);
								
								ClientMonthlyDateEntity findAnyItem = dataListItem.stream().findFirst().get();
								ClientMonthlyDateEntity item = new ClientMonthlyDateEntity();
								item.setTime_full(findAnyItem.getTime_full());
								item.setCategories_time(findAnyItem.getCategories_time());
								item.setChart_energy_kwh(dataStream.get().findAny().isPresent() ? BigDecimal.valueOf(dataStream.get().sum()).setScale(1, RoundingMode.HALF_UP).doubleValue() : null);
								
								return item;
							})
							.collect(Collectors.toList());
						
						PerformanceDataChartItemEntity energyData = new PerformanceDataChartItemEntity(convertDateTimeFormat(site, chartingGranularity, chartingFilter, data, start, end), "chart_energy_kwh", isPower ? "kW" : "kWh", isPower ? "Power" : "Energy Output");
						dataEnergy.add(energyData);
					}
				}
				
				if (irradianceDevices.size() > 0) {
					// get expected when the site has multiple POAs
					if (irradianceDevices.size() > 1) {
						List<ClientMonthlyDateEntity> data = getExpectedBySelectedPOA(obj, irradianceDevices);
						PerformanceDataChartItemEntity expectedData = new PerformanceDataChartItemEntity(convertDateTimeFormat(site, chartingGranularity, chartingFilter, data, start, end), isPower ? "expected_power" : "expected_energy", isPower ? "kW" : "kWh", (isPower ? "Expected Power" : "Expected Energy") + (site.getPv_model() == 3 ? " NREL 8760" : ""));
						dataEnergy.add(expectedData);
					}
					
					for (int i = 0; i < irradianceDevices.size(); i++) {
						DeviceEntity item = irradianceDevices.get(i);
						List<ClientMonthlyDateEntity> data = getIrradianceByDevice(start, end, item, chartingGranularity, chartingFilter, isFilterEnabled, siteUploadingInterval);
						
						if (data.size() > 0) {
							if (irradianceDevices.size() == 1)  {
								PerformanceDataChartItemEntity expectedData = new PerformanceDataChartItemEntity(convertDateTimeFormat(site, chartingGranularity, chartingFilter, data, start, end), isPower ? "expected_power" : "expected_energy", isPower ? "kW" : "kWh", (isPower ? "Expected Power" : "Expected Energy") + (site.getPv_model() == 3 ? " NREL 8760" : ""));
								dataEnergy.add(expectedData);
							}
							
							PerformanceDataChartItemEntity irradianceData = new PerformanceDataChartItemEntity(convertDateTimeFormat(site, chartingGranularity, chartingFilter, data, start, end), "nvm_irradiance", "W/m²", irradianceDevices.size() > 1 ? irradianceDevices.get(i).getDevicename() : "Irradiance");
							dataEnergy.add(irradianceData);
						}
					}
				}
//			}

			return dataEnergy;
		} catch (Exception ex) {
			return new ArrayList<>();
		}

	}
	
//	private List<ClientMonthlyDateEntity> getDataByVirtualDevice(SiteEntity obj) {
//		try {
//			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//			
//			List<ClientMonthlyDateEntity> dataList = queryForList("CustomerView.getDataVirtualDevice", obj);
//			return convertDateTimeFormat(obj, Lib.fulfillData(getDateTimeList(obj, start, end), dataList, "time_full"), start, end);
//		} catch (Exception e) {
//			return new ArrayList<>();
//		}
//	}
	
	private Map<Integer, List<ClientMonthlyDateEntity>> getEnergyByDevice(LocalDateTime start, LocalDateTime end, List<DeviceEntity> devices, ChartingGranularity granularity, ChartingFilter filter, boolean isFilterEnabled) {
		try {
			if (devices.size() == 0) return new TreeMap<>();
			
			long diff5Days = ChronoUnit.DAYS.between(start, end) + 1;
			boolean isDiffLessThan5Days = diff5Days <= 5 && diff5Days > 0;
			
			List<CompletableFuture<Map<Integer,List<ClientMonthlyDateEntity>>>> futures = devices.stream().map(device -> CompletableFuture.supplyAsync(() -> {
				try {
					device.setFilterEnabled(isFilterEnabled);
					List<Map<String, Object>> dataList = sitesAnalyticsService.getDeviceData(device, start, end, granularity, filter);
					
					List<DeviceParameterEntity> parameters = device.getParameters();
					Optional<DeviceParameterEntity> powerParameter = parameters.stream().filter(item -> item.is_active_power()).findFirst();
					Optional<DeviceParameterEntity> intervalEnergyParameter = parameters.stream().filter(item -> item.is_energy() && item.is_user_defined()).findFirst();
					
					Map<Integer, List<ClientMonthlyDateEntity>> dataByDeviceMap = new HashMap<>();
					dataByDeviceMap.put(
						device.getId(),
						dataList.stream()
							.map(item -> {
								ClientMonthlyDateEntity entity = new ClientMonthlyDateEntity();
								entity.setTime_full(item.get("time_full").toString());
								entity.setCategories_time(item.get("categories_time").toString());
								entity.setChart_energy_kwh((Double) (((isDiffLessThan5Days && !powerParameter.isPresent()) || (!isDiffLessThan5Days && !intervalEnergyParameter.isPresent())) ? null : isDiffLessThan5Days ? item.get(powerParameter.get().getSlug()) : item.get(intervalEnergyParameter.get().getSlug())));
								
								return entity;
							})
							.collect(Collectors.toList())
					);
					
					return dataByDeviceMap;
				} catch (Exception e) {
					log.error("getEnergyByDevice", e);
					return new HashMap<Integer, List<ClientMonthlyDateEntity>>();
				}
			})).collect(Collectors.toList());
			
			return futures.stream()
				.map(future -> future.join())
				.filter(item -> !item.isEmpty())
				.reduce(new TreeMap<Integer, List<ClientMonthlyDateEntity>>(), (acc, cur) -> {
					cur.forEach((key, value) -> acc.put(key, value));
					return acc;
				});
		} catch (Exception e) {
			return new TreeMap<>();
		}
	}
	
	private List<ClientMonthlyDateEntity> getIrradianceByDevice(LocalDateTime start, LocalDateTime end, DeviceEntity device, ChartingGranularity granularity, ChartingFilter filter, boolean isFilterEnabled, UploadingDataIntervals siteUploadingInterval) {
		try {
			device.setFilterEnabled(isFilterEnabled);
			List<Map<String, Object>> dataList = sitesAnalyticsService.getDeviceData(device, start, end, granularity, filter);
			
			List<DeviceParameterEntity> parameters = device.getParameters();
			Optional<DeviceParameterEntity> irradianceParameter = parameters.stream().filter(item -> item.is_irradiance()).findFirst();
			Optional<DeviceParameterEntity> expectedPowerParameter = parameters.stream().filter(item -> item.getSlug().equals("expected_power")).findFirst();
			
			return dataList.stream()
				.map(item -> {
					String timeFull = item.get("time_full").toString();
					String categoriesTime = item.get("categories_time").toString();
					
					ClientMonthlyDateEntity entity = new ClientMonthlyDateEntity();
					entity.setTime_full(timeFull);
					entity.setCategories_time(categoriesTime);
					irradianceParameter.ifPresent(parameter -> {
						Optional.ofNullable(item.get(parameter.getSlug())).ifPresent(value -> entity.setNvm_irradiance(BigDecimal.valueOf((Double) value).setScale(1, RoundingMode.HALF_UP).doubleValue()));
					});
					expectedPowerParameter.ifPresent(parameter -> {
						Optional.ofNullable(item.get(parameter.getSlug())).ifPresent(value -> {
							String time = item.get("time").toString();
							LocalDateTime dateTime = sitesAnalyticsService.stringToDateTimeFormattingBySiteUploadingInterval(time, siteUploadingInterval);
							double factorByGranularity = sitesAnalyticsService.factorByGranularity(dateTime, granularity, start, end);
							
							entity.setExpected_power(BigDecimal.valueOf((Double) value).setScale(1, RoundingMode.HALF_UP).doubleValue());
							entity.setExpected_energy(BigDecimal.valueOf((Double) value * factorByGranularity).setScale(1, RoundingMode.HALF_UP).doubleValue());
						});
					});
					
					return entity;
				})
				.collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * @description get expected by selected POA
	 * @author Hung Bui
	 * @since 2026-05-27
	 * @param SiteEntity
	 * @return List<ClientMonthlyDateEntity>
	 */
	private List<ClientMonthlyDateEntity> getExpectedBySelectedPOA(SiteEntity obj, List<DeviceEntity> irradiances) {
		try {
			if (Objects.isNull(obj) || obj.getId_site() == 0 || Objects.isNull(irradiances) || irradiances.size() == 0) return new ArrayList<>();
			ExpectedBySiteDTO siteEntity = (ExpectedBySiteDTO) queryForObject("CustomerView.getSelectedPOABySite", obj);
			if (Objects.isNull(siteEntity)) return new ArrayList<>();
			
			LocalDateTime start = LocalDateTime.parse(obj.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime end = LocalDateTime.parse(obj.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
			siteEntity.setData_send_time(obj.getData_send_time());
			siteEntity.setFilterBy(obj.getFilterBy());
			siteEntity.setStart_date(obj.getStart_date());
			siteEntity.setEnd_date(obj.getEnd_date());
			
			String panelTemps = siteEntity.getIds_device_panel_temp();
			if (StringUtils.isNotBlank(panelTemps)) {
				List<Integer> ids = Arrays.asList(panelTemps.split(",")).stream().map(item -> Integer.parseInt(item)).collect(Collectors.toList());
				siteEntity.setPanelTemps(irradiances.stream().filter(item -> ids.contains(item.getId())).collect(Collectors.toList()));
			}
			
			String poas = siteEntity.getIds_device_poa();
			if (StringUtils.isNotBlank(poas)) {
				List<Integer> ids = Arrays.asList(poas.split(",")).stream().map(item -> Integer.parseInt(item)).collect(Collectors.toList());
				siteEntity.setPOAs(irradiances.stream().filter(item -> ids.contains(item.getId())).collect(Collectors.toList()));
			}
			
			if (siteEntity.getPanelTemps().size() == 0 && siteEntity.getPOAs().size() == 0) return new ArrayList<>();
			List<ClientMonthlyDateEntity> dataList = queryForList("CustomerView.getExpectedBySelectedPOA", siteEntity);
			
			return Lib.fulfillData(getDateTimeList(obj, start, end), dataList, "time_full");
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public List<ClientMonthlyDateEntity> getSitePowerChart(SiteEntity site) {
		try {
			DevicesByTypeEntity devices = deviceService.getDevicesBySite(site);
			List<DeviceEntity> meters = devices.getMeter();
			List<DeviceEntity> inverters = devices.getInverter();
			List<DeviceEntity> powerDevices = meters.size() > 0 ? meters : inverters;
			if (powerDevices.size() == 0) return new ArrayList<>();
			
			site.setDevices(powerDevices);
			LocalDateTime start = LocalDateTime.parse(site.getStart_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			LocalDateTime end = LocalDateTime.parse(site.getEnd_date(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
			return Lib.fulfillData(getDateTimeList(site, start, end), queryForList("CustomerView.getSitePowerChart", site), "time_full");
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
//	private void separateDataByType(List<PerformanceDataChartItemEntity> dataEnergy, SiteEntity obj, List<ClientMonthlyDateEntity> data , List<DeviceEntity> irradianceDevices, boolean isPower) {
//		List<ClientMonthlyDateEntity> energy = new ArrayList<>();
//		List<ClientMonthlyDateEntity> expected = new ArrayList<>();
//		List<ClientMonthlyDateEntity> irradiance = new ArrayList<>();
//		
//		for (ClientMonthlyDateEntity item : data) {
//			ClientMonthlyDateEntity energyItem = new ClientMonthlyDateEntity();
//			energyItem.setTime_full(item.getTime_full());
//			energyItem.setCategories_time(item.getCategories_time());
//			energyItem.setChart_energy_kwh(item.getChart_energy_kwh());
//			energyItem.setNvmActivePower(item.getNvmActivePower());
//			energyItem.setNvmActiveEnergy(item.getNvmActiveEnergy());
//			energy.add(energyItem);
//			
//			ClientMonthlyDateEntity expectedItem = new ClientMonthlyDateEntity();
//			expectedItem.setTime_full(item.getTime_full());
//			expectedItem.setCategories_time(item.getCategories_time());
//			expectedItem.setExpected_power(item.getExpected_power());
//			expectedItem.setExpected_energy(item.getExpected_energy());
//			expected.add(expectedItem);
//			
//			ClientMonthlyDateEntity irradianceItem = new ClientMonthlyDateEntity();
//			irradianceItem.setTime_full(item.getTime_full());
//			irradianceItem.setCategories_time(item.getCategories_time());
//			irradianceItem.setNvm_irradiance(item.getNvm_irradiance());
//			irradiance.add(irradianceItem);
//		}
//		
//		PerformanceDataChartItemEntity energyData = new PerformanceDataChartItemEntity(energy, "chart_energy_kwh", isPower ? "kW" : "kWh", isPower ? "Power" : "Energy Output");
//		dataEnergy.add(energyData);
//		
//		if (irradianceDevices.size() > 0) {
//			PerformanceDataChartItemEntity expectedData = new PerformanceDataChartItemEntity(expected, isPower ? "expected_power" : "expected_energy", isPower ? "kW" : "kWh", (isPower ? "Expected Power" : "Expected Energy") + (obj.getPv_model() == 3 ? " NREL 8760" : ""));
//			dataEnergy.add(expectedData);
//			
//			PerformanceDataChartItemEntity irradianceData = new PerformanceDataChartItemEntity(irradiance, "nvm_irradiance", "W/m²", "Irradiance");
//			dataEnergy.add(irradianceData);
//		}
//	}
	
	/**
	 * @description get customer view site info
	 * @author long.pham
	 * @since 2020-12-02
	 * @param id_site, id_customer
	 * @return Object
	 */

	public Object getCustomerViewInfo(SiteEntity obj) {
		try {
			DevicesByTypeEntity devices = deviceService.getDevicesBySite(obj);
			List<DeviceEntity> meterDevices = devices.getMeter();
			List<DeviceEntity> inverterDevices = devices.getInverter();
			List<DeviceEntity> powerDevices = meterDevices.size() > 0 ? meterDevices : inverterDevices;
			obj.setGroupMeter(powerDevices);
			obj.setTotalMeter(meterDevices.size());
	
			return queryForObject("CustomerView.getCustomerViewInfo", obj);
		} catch (Exception ex) {
			return null;
		}
	}
	
	

	/**
	 * @description get list site by id customer
	 * @author long.pham
	 * @since 2020-12-08
	 * @param id_customer
	 */

	public List getList(SiteEntity obj) {

		List dataList = new ArrayList();
		SecretCards secretCard = new SecretCards();
		try {
			List getList = queryForList("CustomerView.getList", obj);
			return getList;
		} catch (Exception ex) {
			return new ArrayList();
		}
	}

	/**
	 * @description get list alert by site
	 * @author long.pham
	 * @since 2021-09-02
	 * @param id_customer, id_site, start_date, end_date
	 */

	public List getListAlertBySite(AlertEntity obj) {
		try {
			List rs = queryForList("CustomerView.getListAlertCustomerView", obj);
			if (rs == null) {
				return new ArrayList<>();
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @description count total alert by site
	 * @author long.pham
	 * @since 2021-03-09
	 * @param id_customer, id_site, start_date, end_date
	 */

	public int getAlertCustomerViewTotalCount(AlertEntity obj) {
		try {
			return (int)queryForObject("CustomerView.countAlertCustomerView", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	


	/**
	 * @description count total notification alert by customer
	 * @author long.pham
	 * @since 2021-03-09
	 * @param id_customer, end_date
	 */

	public int countNotificationAlert(AlertEntity obj) {
		try {
			return (int) queryForObject("CustomerView.countCustomerViewNotificationAlert", obj);
		} catch (Exception ex) {
			return 0;
		}
	}
	
	/**
	 * @description get detail alert
	 * @author long.pham
	 * @since 2021-03-18
	 * @param id_site
	 * @return Object
	 */

	public AlertEntity getAlertSummary(AlertEntity obj) {
		AlertEntity dataObj = new AlertEntity();
		try {
			dataObj = (AlertEntity) queryForObject("CustomerView.getAlertSummary", obj);
			if (dataObj == null)
				return new AlertEntity();
		} catch (Exception ex) {
			return new AlertEntity();
		}
		return dataObj;

	}

}
