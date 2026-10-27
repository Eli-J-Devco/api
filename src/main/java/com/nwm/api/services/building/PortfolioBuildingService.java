package com.nwm.api.services.building;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.entities.building.ElectricalMetricsResponse;
import com.nwm.api.entities.building.PortfolioBuildingEntity;
import com.nwm.api.entities.building.ElectricalMetricsRequest;
import com.nwm.api.services.DeviceService;
import com.nwm.api.services.PortfolioService;
import com.nwm.api.services.SitesAnalyticsService;
import com.nwm.api.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PortfolioBuildingService extends DB {
	@Autowired
	PortfolioService portfolioService;
	@Autowired
	DeviceService deviceService;
	@Autowired
	SitesAnalyticsService sitesAnalyticsService;
	@Autowired
	@Qualifier("siteExecutor")
	Executor siteExecutor;
	@Autowired
	@Qualifier("deviceDataExecutor")
	Executor deviceExecutor;

	/**
	 * @description get list portfolio by array(id_site)
	 * @author duy.phan 
	 * @since 2025-11-20
	 * @param obj id_site
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

	/**
	 * @author Hung.bui
	 * @since 2026-10-26
	 * @param req { filterBy, domain, domain_role, hashGroupId, hashSubGroupId }
	 * @param sitesManagedByUser sites managed by user
	 * @return sites' electrical metrics
	 */
	public List<ElectricalMetricsResponse> getSitesElectricalMetrics(ElectricalMetricsRequest req, List<Integer> sitesManagedByUser) {
		try {
			List<SiteEntity> sites = getSitesByGroup(req.getDomain(), req.getDomain_role(), sitesManagedByUser, req.getHashGroupId(), req.getHashSubGroupId());

			List<CompletableFuture<ElectricalMetricsResponse>> siteMetricsFutures = sites.stream()
					.map(site -> CompletableFuture.supplyAsync(() -> {
						LocalDateTime end = LocalDateTime.now(ZoneId.of(site.getTime_zone_value()));
						LocalDateTime start = getStartDateTimeBasedOnFilter(req.getFilterBy(), end);
						LocalDateTime priorEnd = getPriorEndDateTimeBasedOnFilter(req.getFilterBy(), end);
						LocalDateTime priorStart = getStartDateTimeBasedOnFilter(req.getFilterBy(), priorEnd);

						DeviceEntity params = new DeviceEntity();
						params.setDomain(req.getDomain());
						params.setId_site(site.getId_site());

						DevicesByTypeEntity devices = deviceService.getDevicesBySite(params);
						List<DeviceEntity> powerDevices = !devices.getMeter().isEmpty() ? devices.getMeter() : devices.getInverter();

						List<CompletableFuture<ActualDTO>> deviceActualFutures = powerDevices.stream()
								.map(device -> CompletableFuture.supplyAsync(() -> {
									List<DeviceParameterEntity> parameters = device.getParameters();
									Optional<DeviceParameterEntity> powerParameter = parameters.stream().filter(DeviceParameterEntity::isIs_active_power).findFirst();
									Optional<DeviceParameterEntity> intervalEnergyParameter = parameters.stream().filter(DeviceParameterEntity::isIs_energy).findFirst();

									return sitesAnalyticsService.getDeviceData(device, start, end, Constants.ChartingGranularity._1_YEAR, req.getFilterBy()).stream()
											.map(item -> {
												ActualDTO actual = new ActualDTO();
												actual.setTime_full(item.get("time_full").toString());
												powerParameter.ifPresent(parameter -> actual.setPower((Double) item.get(parameter.getSlug())));
												intervalEnergyParameter.ifPresent(parameter -> actual.setEnergy((Double) item.get(parameter.getSlug())));

												return actual;
											})
											.findFirst()
											.orElse(null);
								}, deviceExecutor))
								.collect(Collectors.toList());

						List<ActualDTO> devicesActual = deviceActualFutures.stream()
								.map(CompletableFuture::join)
								.collect(Collectors.toList());

						// EUI
						Supplier<DoubleStream> deviceEnergyStream = () -> devicesActual.stream()
								.map(ActualDTO::getEnergy)
								.filter(Objects::nonNull)
								.mapToDouble(Double::doubleValue);

						Double currSiteEnergy = deviceEnergyStream.get().findAny().isPresent() ?
								deviceEnergyStream.get().sum()
								:
								null;

						Double eui = currSiteEnergy;

						// Power Average
						Supplier<DoubleStream> devicePowerStream = () -> devicesActual.stream()
								.map(ActualDTO::getPower)
								.filter(Objects::nonNull)
								.mapToDouble(Double::doubleValue);

						Double powerAvg = devicePowerStream.get().findAny().isPresent() ?
								devicePowerStream.get().sum()
								:
								null;

						// Prior period Energy
						List<CompletableFuture<Double>> priorDeviceEnergyFutures = powerDevices.stream()
								.map(device -> CompletableFuture.supplyAsync(() -> {
									List<DeviceParameterEntity> parameters = device.getParameters();
									Optional<DeviceParameterEntity> intervalEnergyParameter = parameters.stream().filter(DeviceParameterEntity::isIs_energy).findFirst();

									return sitesAnalyticsService.getDeviceData(device, priorStart, priorEnd, Constants.ChartingGranularity._1_YEAR, req.getFilterBy()).stream()
											.map(item -> intervalEnergyParameter
													.map(parameter -> (Double) item.get(parameter.getSlug()))
													.orElse(null)
											)
											.filter(Objects::nonNull)
											.findFirst()
											.orElse(null);
								}, deviceExecutor))
								.collect(Collectors.toList());

						List<Double> priorDevicesEnergy = priorDeviceEnergyFutures.stream()
								.map(CompletableFuture::join)
								.filter(Objects::nonNull)
								.collect(Collectors.toList());

						Supplier<DoubleStream> priorDevicesEnergyStream = () -> priorDevicesEnergy.stream().mapToDouble(Double::doubleValue);

						Double priorSiteEnergy = priorDevicesEnergyStream.get().findAny().isPresent() ?
								priorDevicesEnergyStream.get().sum()
								:
								null;

						// Peak Demand
						List<CompletableFuture<List<ActualDTO>>> devicePowerByMinIntervalFutures = powerDevices.stream()
								.map(device -> CompletableFuture.supplyAsync(() -> {
									List<DeviceParameterEntity> parameters = device.getParameters();
									Optional<DeviceParameterEntity> powerParameter = parameters.stream().filter(DeviceParameterEntity::isIs_active_power).findFirst();

									return sitesAnalyticsService.getDeviceData(device, start, end, Constants.ChartingGranularity._1_MINUTE, req.getFilterBy()).stream()
											.map(item -> {
												ActualDTO actual = new ActualDTO();
												actual.setTime_full(item.get("time_full").toString());
												powerParameter.ifPresent(parameter -> actual.setPower((Double) item.get(parameter.getSlug())));

												return actual;
											})
											.collect(Collectors.toList());
								}, deviceExecutor))
								.collect(Collectors.toList());

						Double peakDemand = devicePowerByMinIntervalFutures.stream()
                                .map(CompletableFuture::join)
                                .flatMap(List::stream)
                                .collect(Collectors.groupingBy(ActualDTO::getTime_full, Collectors.toList()))
                                .values()
                                .stream()
                                .map(dataList -> {
                                    Supplier<DoubleStream> powerStream = () -> dataList.stream()
                                            .map(ActualDTO::getPower)
                                            .filter(Objects::nonNull)
                                            .mapToDouble(Double::doubleValue);

                                    return powerStream.get().findAny().isPresent() ? powerStream.get().sum() : null;
                                })
								.filter(Objects::nonNull)
								.max(Double::compare)
								.orElse(null);

						// Load factor
						Double loadFactor = Objects.nonNull(peakDemand) && Objects.nonNull(powerAvg) ? powerAvg / peakDemand : null;

						// Energy period over prior period
						Double pop = Optional.ofNullable(priorSiteEnergy)
								.flatMap(prior -> Optional.ofNullable(currSiteEnergy)
                                        .map(curr -> (curr - prior) / prior * 100))
								.orElse(null);

						return new ElectricalMetricsResponse(site.getId_site(), site.getName(), eui, null, null, peakDemand, loadFactor, pop);
					}, siteExecutor))
					.collect(Collectors.toList());

			List<ElectricalMetricsResponse> sitesMetrics = siteMetricsFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());

			// Portfolio EUI Average
			OptionalDouble portfolioEUIAvg = sitesMetrics.stream()
					.map(ElectricalMetricsResponse::getEui)
					.filter(Objects::nonNull)
					.mapToDouble(Double::doubleValue)
					.average();

			return sitesMetrics.stream()
					.map(siteMetrics -> {
						portfolioEUIAvg.ifPresent(euiAvg -> siteMetrics.setVsPortfolioEui(
								Optional.ofNullable(siteMetrics.getEui())
										.map(siteEUI -> (siteEUI - euiAvg) / euiAvg * 100)
										.orElse(null)
						));

						return siteMetrics;
					})
					.map(siteMetrics -> {
						Optional.ofNullable(siteMetrics.getEui()).ifPresent(value -> siteMetrics.setEui(BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP).doubleValue()));
						Optional.ofNullable(siteMetrics.getVsPortfolioEui()).ifPresent(value -> siteMetrics.setVsPortfolioEui(BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP).doubleValue()));
						Optional.ofNullable(siteMetrics.getPeakDemand()).ifPresent(value -> siteMetrics.setPeakDemand(BigDecimal.valueOf(value).setScale(0, RoundingMode.HALF_UP).doubleValue()));
						Optional.ofNullable(siteMetrics.getLoadFactor()).ifPresent(value -> siteMetrics.setLoadFactor(BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue()));
						Optional.ofNullable(siteMetrics.getPeriodOverPriorPeriod()).ifPresent(value -> siteMetrics.setPeriodOverPriorPeriod(BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP).doubleValue()));

						return siteMetrics;
					})
					.collect(Collectors.toList());
		} catch (Exception ex) {
			log.error("getSitesElectricalMetrics", ex);
			return new ArrayList<>();
		}
	}

	private static LocalDateTime getStartDateTimeBasedOnFilter(Constants.ChartingFilter filter, LocalDateTime now) {
		LocalDateTime dateTime = now.withHour(0).withMinute(0).withSecond(0);

		switch (filter) {
			case TODAY:
				return dateTime;
            case THIS_WEEK:
				return dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
			case LAST_WEEK:
				return dateTime.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            case THIS_MONTH:
				return dateTime.withDayOfMonth(1);
            case LAST_MONTH:
			default:
				return dateTime.minusMonths(1).withDayOfMonth(1);
            case THIS_YEAR:
				return dateTime.withDayOfYear(1);
		}
	}

	private static LocalDateTime getPriorEndDateTimeBasedOnFilter(Constants.ChartingFilter filter, LocalDateTime now) {
		LocalDateTime dateTime = now.withHour(23).withMinute(59).withSecond(59);

		switch (filter) {
			case TODAY:
				return dateTime.minusDays(1);
			case THIS_WEEK:
				return dateTime.minusWeeks(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
			case LAST_WEEK:
				return dateTime.minusWeeks(2).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
			case THIS_MONTH:
				return dateTime.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
			case LAST_MONTH:
			default:
				return dateTime.minusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
			case THIS_YEAR:
				return dateTime.minusYears(1).with(TemporalAdjusters.lastDayOfYear());
		}
	}

	private List<SiteEntity> getSitesByGroup(String domain, String domainRole, List<Integer> sitesManagedByUser, String hashGroupId, String hashSubGroupId) {
		PortfolioEntity params = new PortfolioEntity();
		params.setDomain(domain);
		params.setDomain_role(domainRole);
		params.setId_sites(sitesManagedByUser);
		params.setHash_id_site_group(hashGroupId);
		params.setHash_id_site_sub_group(hashSubGroupId);

		return portfolioService.getSites(params);
	}
}
