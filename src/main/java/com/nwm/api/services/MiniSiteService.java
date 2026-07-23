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
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.DeviceEntity;
import com.nwm.api.entities.DeviceParameterEntity;
import com.nwm.api.entities.DevicesByTypeEntity;
import com.nwm.api.entities.KioskViewTodayEntity;
import com.nwm.api.entities.MiniSiteRequest;
import com.nwm.api.entities.SiteEntity;
import com.nwm.api.utils.Constants.ChartingFilter;
import com.nwm.api.utils.Constants.ChartingGranularity;
import com.nwm.api.utils.Constants.DeviceType;

@Service
public class MiniSiteService extends DB {
	@Autowired
	SitesAnalyticsService sitesAnalyticsService;
	@Autowired
	CustomerViewService customerViewService;
	@Autowired
	DeviceService deviceService;
	@Autowired
	@Qualifier("deviceDataExecutor")
	Executor executor;
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * @description get mini site detail
	 * @author long.pham
	 * @since 2020-11-02
	 * @param id_site
	 * @return Object
	 */

	public Object getMiniSiteInfo(MiniSiteRequest obj) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		SiteEntity siteEntity = mapper.convertValue(obj, SiteEntity.class);
//		siteEntity.setKiosk_view(1);
		
		return customerViewService.getCustomerViewInfo(siteEntity);
	}
	
	/**
	 * @description get monthly report
	 * @author long.pham
	 * @since 2022-08-23
	 * @param id_site, date_from, data_to
	 */

	public Object getChartPerformance(MiniSiteRequest obj) {
		try {
			AtomicReference<LocalDateTime> start = new AtomicReference<>(LocalDateTime.parse(obj.getStart_date(), dateTimeFormatter).withHour(0).withMinute(0).withSecond(0));
			AtomicReference<LocalDateTime> end = new AtomicReference<>(LocalDateTime.parse(obj.getEnd_date(), dateTimeFormatter).withHour(23).withMinute(59).withSecond(59));
			ChartingFilter chartingFilter = ChartingFilter.fromValue(obj.getFilterBy());
			AtomicReference<ChartingGranularity> chartingGranularity = new AtomicReference<>(null);
			
			switch (chartingFilter) {
				case TODAY:
					chartingGranularity.set(ChartingGranularity._1_HOUR);
            		break;
				case THIS_MONTH:
				case LAST_MONTH:
					chartingGranularity.set(ChartingGranularity._1_DAY);
					start.set(start.get().withDayOfMonth(1));
					end.set(end.get().with(TemporalAdjusters.lastDayOfMonth()));
					break;
				case LAST_12_MONTHS:
					chartingGranularity.set(ChartingGranularity._1_MONTH);
					start.set(start.get().withDayOfMonth(1));
					break;
				case LIFETIME:
					chartingGranularity.set(ChartingGranularity._1_YEAR);
					start.set(start.get().withDayOfYear(1));
					break;
				default:
					break;
			}
			
			DevicesByTypeEntity devices = deviceService.getDevicesBySite(obj);
			List<DeviceEntity> meterDevices = devices.getMeter();
			List<DeviceEntity> inverterDevices = devices.getInverter();
			List<DeviceEntity> irradianceDevices = devices.getIrradiance();
			List<DeviceEntity> powerDevices = !meterDevices.isEmpty() ? meterDevices : inverterDevices;
			List<DeviceEntity> inverterAndIrradianceDevices = Stream.of(inverterDevices, irradianceDevices)
					.flatMap(List::stream)
					.sorted(Comparator.comparing(DeviceEntity::getOrder).thenComparing(DeviceEntity::getId))
					.collect(Collectors.toList());
			
			if(obj.getDevice_mode() == 2) {
				List<CompletableFuture<Map<String, Object>>> futures = inverterAndIrradianceDevices.stream()
						.map(device -> CompletableFuture.supplyAsync(() -> {
							List<DeviceParameterEntity> parameters = device.getParameters();
							Optional<DeviceParameterEntity> powerParameter = parameters.stream().filter(item -> item.isIs_active_power()).findFirst();
							Optional<DeviceParameterEntity> intervalEnergyParameter = parameters.stream().filter(item -> item.isIs_energy() && item.isIs_user_defined()).findFirst();
							Optional<DeviceParameterEntity> irradianceParameter = parameters.stream().filter(item -> item.isIs_irradiance()).findFirst();
							DeviceType deviceType = DeviceType.fromValue(device.getId_device_type());
							
							List<KioskViewTodayEntity> data = sitesAnalyticsService.getDeviceData(device, start.get(), end.get(), chartingGranularity.get(), chartingFilter).stream()
									.map(item -> {
										KioskViewTodayEntity entity = new KioskViewTodayEntity();
										entity.setTime_full(item.get("time_full").toString());
										entity.setCategories_time(item.get("categories_time").toString());
										Optional<Double> valueOptional = Optional.ofNullable((Double) (deviceType == DeviceType.PV_SYSTEM_INVERTER ?
												(chartingFilter == ChartingFilter.TODAY ?
														(powerParameter.isPresent() ? item.get(powerParameter.get().getSlug()) : null)
														:
														(intervalEnergyParameter.isPresent() ? item.get(intervalEnergyParameter.get().getSlug()) : null)
												)
												:
												(irradianceParameter.isPresent() ? item.get(irradianceParameter.get().getSlug()) : null)));
										valueOptional.ifPresent(value -> entity.setEnergy(BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP).doubleValue()));
										
										return entity;
									})
									.collect(Collectors.toList());
							
							Map<String, Object> item = new HashMap<>();
							item.put("devicename", device.getDevicename());
							item.put("id_device_type", device.getId_device_type());
							item.put("datas", data);
							
							return item;
						}, executor))
						.collect(Collectors.toList());
				
				List<Map<String, Object>> datas = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
				
				obj.setEnergy(datas);
			} else {
				List<CompletableFuture<List<KioskViewTodayEntity>>> energyFutures = powerDevices.stream()
						.map(device -> CompletableFuture.supplyAsync(() -> {
							List<DeviceParameterEntity> parameters = device.getParameters();
							Optional<DeviceParameterEntity> intervalEnergyParameter = parameters.stream().filter(item -> item.isIs_energy() && item.isIs_user_defined()).findFirst();
							
							return sitesAnalyticsService.getDeviceData(device, start.get(), end.get(), chartingGranularity.get(), chartingFilter).stream()
									.map(item -> {
										KioskViewTodayEntity entity = new KioskViewTodayEntity();
										entity.setTime_full(item.get("time_full").toString());
										entity.setCategories_time(item.get("categories_time").toString());
										entity.setEnergy(intervalEnergyParameter.isPresent() ? (Double) item.get(intervalEnergyParameter.get().getSlug()) : null);
										
										return entity;
									})
									.collect(Collectors.toList());
						}, executor))
						.collect(Collectors.toList());
				
				List<CompletableFuture<List<KioskViewTodayEntity>>> irradianceFutures = irradianceDevices.stream()
						.map(device -> CompletableFuture.supplyAsync(() -> {
							List<DeviceParameterEntity> parameters = device.getParameters();
							Optional<DeviceParameterEntity> irradianceParameter = parameters.stream().filter(item -> item.isIs_irradiance()).findFirst();
							
							return sitesAnalyticsService.getDeviceData(device, start.get(), end.get(), chartingGranularity.get(), chartingFilter).stream()
									.map(item -> {
										KioskViewTodayEntity entity = new KioskViewTodayEntity();
										entity.setTime_full(item.get("time_full").toString());
										entity.setCategories_time(item.get("categories_time").toString());
										entity.setIrradiance(irradianceParameter.isPresent() ? (Double) item.get(irradianceParameter.get().getSlug()) : null);
										
										return entity;
									})
									.collect(Collectors.toList());
						}, executor))
						.collect(Collectors.toList());
						
				List<KioskViewTodayEntity> dataEnergy = energyFutures.stream()
						.map(CompletableFuture::join)
						.filter(item -> !item.isEmpty())
						.flatMap(List::stream)
						.collect(Collectors.groupingBy(item -> sitesAnalyticsService.stringToDateTimeByGranularity(item.getTime_full(), chartingGranularity.get()), TreeMap::new, Collectors.toList()))
						.values()
						.stream()
						.map(dataListItem -> {
							Supplier<DoubleStream> dataStream = () -> dataListItem.stream()
								.map(KioskViewTodayEntity::getEnergy)
								.filter(Objects::nonNull)
								.mapToDouble(Double::doubleValue);
							
							KioskViewTodayEntity findAnyItem = dataListItem.stream().findFirst().get();
							KioskViewTodayEntity item = new KioskViewTodayEntity();
							item.setCategories_time(findAnyItem.getCategories_time());
							if (chartingFilter == ChartingFilter.TODAY) item.setHour_time(findAnyItem.getCategories_time());
							item.setEnergy(dataStream.get().findAny().isPresent() ? BigDecimal.valueOf(dataStream.get().sum()).setScale(1, RoundingMode.HALF_UP).doubleValue() : null);
							
							return item;
						})
						.collect(Collectors.toList());
				
				List<KioskViewTodayEntity> dataIrradiance = irradianceFutures.stream()
						.map(CompletableFuture::join)
						.filter(item -> !item.isEmpty())
						.flatMap(List::stream)
						.collect(Collectors.groupingBy(item -> sitesAnalyticsService.stringToDateTimeByGranularity(item.getTime_full(), chartingGranularity.get()), TreeMap::new, Collectors.toList()))
						.values()
						.stream()
						.map(dataListItem -> {
							KioskViewTodayEntity findAnyItem = dataListItem.stream().findFirst().get();
							KioskViewTodayEntity item = new KioskViewTodayEntity();
							item.setCategories_time(findAnyItem.getCategories_time());
							if (chartingFilter == ChartingFilter.TODAY) item.setHour_time(findAnyItem.getCategories_time());
							dataListItem.stream()
									.map(KioskViewTodayEntity::getIrradiance)
									.filter(Objects::nonNull)
									.mapToDouble(Double::doubleValue)
									.average()
									.ifPresent(value -> item.setIrradiance(BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP).doubleValue()));
							
							return item;
						})
						.collect(Collectors.toList());
				
				List<KioskViewTodayEntity> mergedData = Stream.concat(dataEnergy.stream(), dataIrradiance.stream())
						.collect(Collectors.toMap(
								KioskViewTodayEntity::getCategories_time,
								item -> item,
								(s1, s2) -> {
									s1.setIrradiance(s2.getIrradiance());
									return s1;
								},
								LinkedHashMap::new
						))
						.values()
						.stream()
						.collect(Collectors.toList());
						
				obj.setEnergy(mergedData);
				obj.setHave_poa(!irradianceDevices.isEmpty());
			}
			
			return obj;
		} catch (Exception ex) {
			return null;
		}
	}

	public List<SiteEntity> getListSiteAutoChange(SiteEntity obj) {
        try {
            List<Integer> companyUseAutoChange = Arrays.asList(2, 3, 146, 147);
            SiteEntity entity = (SiteEntity) queryForObject("MiniSite.getCompanyBySiteHash", obj);
            if (entity == null) {
                return new ArrayList<>();
            }
            if (companyUseAutoChange.stream().anyMatch(x -> x == entity.getId_company())) {
                List<SiteEntity> dataList = queryForList("MiniSite.getListSiteAutoChange", entity);
                if (dataList == null) {
                    return new ArrayList<>();
                }
                return dataList;
            }
        } catch (Exception e) {
            log.error("MiniSiteService.getListSiteAutoChange", e);
        }
        return new ArrayList<>();
    }
	
}
