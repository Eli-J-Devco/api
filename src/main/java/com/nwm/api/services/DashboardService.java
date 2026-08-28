/********************************************************
 * Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
 * All rights reserved.
 *
 *********************************************************/
package com.nwm.api.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nwm.api.utils.Constants;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.utils.Lib;

@Service
public class DashboardService extends DB {
    @Autowired
    CustomerViewService customerViewService;
    @Autowired
    PortfolioService portfolioService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    SitesAnalyticsService sitesAnalyticsService;

    /**
     * @description get list alert by site
     * @author long.pham
     * @since 2020-11-16
     * @param id_customer, id_site, start_date, end_date
     */

    public List getList(AlertEntity obj) {
        try {
            List rs = queryForList("Dashboard.getList", obj);
            if (rs == null) {
                return new ArrayList<>();
            }
            return rs;
        } catch (Exception ex) {
            return null;
        }
    }




    /**
     * @description get list alert by site
     * @author long.pham
     * @since 2020-11-16
     * @param id_customer, id_site, start_date, end_date
     */

    public List getListActualvsExpected(DashboardEntity obj) {
        try {
            List rs = queryForList("Dashboard.getListActualvsExpected", obj);
            if (rs == null) {
                return new ArrayList<>();
            }
            return rs;
        } catch (Exception ex) {
            return null;
        }
    }

//
//	
//	
//	/**
//	 * @description get list site by id_sites
//	 * @author long.pham
//	 * @since 2021-02-02
//	 * @param arr id_sites
//	 */
//
//	public List getListIdSites(AlertEntity obj) {
//		try {
//			List rs = queryForList("Alert.getListIdSites", obj);
//			if (rs == null) {
//				return new ArrayList<>();
//			}
//			return rs;
//		} catch (Exception ex) {
//			return null;
//		}
//	}
//
//	/**
//	 * @description count total alert by site
//	 * @author long.pham
//	 * @since 2020-11-16
//	 * @param id_customer, id_site, start_date, end_date
//	 */
//
//	public int getListTotalCount(AlertEntity obj) {
//		try {
//			AlertEntity totalRecord = (AlertEntity) queryForObject("Alert.getTotal", obj);
//			return totalRecord.getTotalRecord();
//		} catch (Exception ex) {
//			return 0;
//		}
//	}
//
//	/**
//	 * @description get detail alert
//	 * @author long.pham
//	 * @since 2020-11-24
//	 * @param id_site, id_alert, id_customer, current_time
//	 * @return Object
//	 */
//
//	public Object getDetailAlert(AlertEntity obj) {
//		Object dataObj = null;
//		try {
//			dataObj = queryForObject("Alert.getDetailAlert", obj);
//			if (dataObj == null)
//				return new AlertEntity();
//		} catch (Exception ex) {
//			return new AlertEntity();
//		}
//		return dataObj;
//
//	}
//
//	/**
//	 * @description get alert Exists
//	 * @author long.pham
//	 * @since 2021-01-29
//	 * @param error_code, time
//	 */
//	public boolean checkAlertExist(AlertEntity dataE) {
//		try {
//			return (int) queryForObject("Alert.checkAlertlExist", dataE) > 0;
//		} catch (Exception e) {
//
//		}
//		return true;
//	}
//
//	/**
//	 * @description insert alert
//	 * @author long.pham
//	 * @since 2021-01-29
//	 * @param id
//	 */
//	public AlertEntity insertAlert(AlertEntity obj) {
//		try {
//			Object insertId = insert("Alert.insertAlert", obj);
//			if (insertId != null && insertId instanceof Integer) {
//				return obj;
//			} else {
//				return null;
//			}
//		} catch (Exception ex) {
//			log.error("Alert.insertAlert", ex);
//			return null;
//		}
//	}
//
//	/**
//	 * @description get list alert by site
//	 * @author long.pham
//	 * @since 2021-03-18
//	 * @param id_customer, id_site, start_date, end_date
//	 */
//
//	public List getListBySiteAdmin(AlertEntity obj) {
//		try {
//			List rs = queryForList("Alert.getListBySiteAdmin", obj);
//			if (rs == null) {
//				return new ArrayList<>();
//			}
//			return rs;
//		} catch (Exception ex) {
//			return null;
//		}
//	}
//
//	/**
//	 * @description get detail alert
//	 * @author long.pham
//	 * @since 2021-03-18
//	 * @param id_site
//	 * @return Object
//	 */
//
//	public SiteEntity getSiteDetail(AlertEntity obj) {
//		SiteEntity dataObj = new SiteEntity();
//		try {
//			dataObj = (SiteEntity) queryForObject("Alert.getSiteDetail", obj);
//			if (dataObj == null)
//				return new SiteEntity();
//		} catch (Exception ex) {
//			return new SiteEntity();
//		}
//		return dataObj;
//
//	}
//
//	/**
//	 * @description update error level status
//	 * @author long.pham
//	 * @since 2021-05-18
//	 * @param id
//	 */
//	public boolean updateStatus(AlertEntity obj) {
//		try {
//			return update("Alert.updateStatus", obj) > 0;
//		} catch (Exception ex) {
//			log.error("Alert.updateStatus", ex);
//			return false;
//		}
//	}
//
//	/**
//	 * @description update ack
//	 * @author long.pham
//	 * @since 2021-11-04
//	 * @param id
//	 */
//	public boolean updateACK(AlertHistoryEntity obj) {
//		try {
//			AlertHistoryEntity dataObj = new AlertHistoryEntity();
//			dataObj = (AlertHistoryEntity) queryForObject("Alert.getACKByEmplyee", obj);
//			if (dataObj == null) {
//				Object insertId = insert("Alert.insertAlertHistory", obj);
//				if (insertId != null && insertId instanceof Integer) {
//					return true;
//				} else {
//					return false;
//				}
//			} else {
//				// update time
//				return update("Alert.updateAlertHistory", obj) > 0;
//			}
//
//		} catch (Exception ex) {
//			log.error("Alert.updateStatus", ex);
//			return false;
//		}
//	}
//	
//	
//	/**
//	 * @description update alert
//	 * @author long.pham
//	 * @since 2021-11-05
//	 * @param id
//	 */
//	public boolean updateAlert(AlertEntity obj){
//		try{
//			return update("Alert.updateAlert", obj)>0;
//		}catch (Exception ex) {
//			log.error("Alert.updateAlert", ex);
//			return false;
//		}
//	}
//	

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
            dataObj = (AlertEntity) queryForObject("Dashboard.getAlertSummary", obj);
            if (dataObj == null)
                return new AlertEntity();
        } catch (Exception ex) {
            return new AlertEntity();
        }
        return dataObj;

    }

    public Map<String, Object> getKPIDataByKey(PortfolioEntity obj, String key) {
        try {
            Map<String, Object> res = new HashMap<>();
            switch (key) {
                case "expected_energy_today":
                case "expected_energy_this_month":
                case "expected_energy_last_week":
                    String idFilter = key.split("expected_energy_")[1];
                    obj.setId_filter(idFilter);
                    List<Map<String, Object>> energy = getKPIData(obj);
                    Double totalExpected = null;
                    String expectedEnergySuffix = !"today".equalsIgnoreCase(obj.getId_filter()) ? ("_" + obj.getId_filter()) : "";
                    String expectedEnergyKey = "expected_energy" + expectedEnergySuffix;
                    for (Map<String, Object> item : energy) {
                        Object expectedEnergy = item.get(expectedEnergyKey);
                        if (expectedEnergy != null) {
                            double value = ((Number) expectedEnergy).doubleValue();
                            totalExpected = totalExpected == null ? value : totalExpected + value;
                        }
                    }
                    res.put("total_expected_" + idFilter, totalExpected);
                    res.put("energy", energy);
                    break;
            }

            return res;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> getActualExpectLastWeek(PortfolioEntity obj) {
        try {
            List<SiteEntity> sites = portfolioService.getSites(obj);
            if (sites == null || sites.isEmpty()) {
                return null;
            }

            Constants.ChartingFilter chartingFilter = Constants.ChartingFilter.fromValue(obj.getId_filter());
            Constants.ChartingGranularity chartingGranularity =  Constants.ChartingGranularity._1_DAY;
            ZonedDateTime startDateTime;
            ZonedDateTime endDateTime;
            double performanceRatioLastWeek = 0;
//            double expected = 0;
            for (SiteEntity site : sites) {
                double actual = 0;
                double nvmIrradiance = 0;
                double dcCapacity = site.getDc_capacity();
                DevicesByTypeEntity devices = deviceService.getDevicesBySite(site);
                List<DeviceEntity> powerDevices = !devices.getMeter().isEmpty() ? devices.getMeter() : devices.getInverter();
                List<DeviceEntity> irradianceDevices = devices.getIrradiance();

                String timeZone = site.getTime_zone_value();
                ZoneId zoneId = ZoneId.of(timeZone);
                ZonedDateTime now = ZonedDateTime.now(zoneId);

                startDateTime = now.minusWeeks(1).toLocalDate().atStartOfDay(zoneId);
                endDateTime = now.minusWeeks(1).toLocalDate().atTime(23, 59, 59).atZone(zoneId);

//                List<ClientMonthlyDateEntity> expectedList = null;
                if (irradianceDevices != null && !irradianceDevices.isEmpty()) {
                    DeviceEntity mainIrradiance = null;
                    Constants.UploadingDataIntervals siteUploadingInterval = Constants.UploadingDataIntervals.fromValue(site.getData_send_time());
                    if (irradianceDevices.size() == 1) {
//                        expectedList = customerViewService.getIrradianceByDevice(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), irradianceDevices.get(0), chartingGranularity, chartingFilter, false, siteUploadingInterval);
                        mainIrradiance = irradianceDevices.get(0);

                    } else {
//                        expectedList = customerViewService.getExpectedBySelectedPOA(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), site.getId_site(), chartingGranularity, chartingFilter, irradianceDevices);
                        ExpectedBySiteDTO siteEntity = (ExpectedBySiteDTO) queryForObject("CustomerView.getSelectedPOABySite", site.getId_site());
                        if (siteEntity != null) {
                            String poas = siteEntity.getIds_device_poa();
                            if (!Lib.isBlank(poas)) {
                                List<Integer> ids = Arrays.asList(poas.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
                                mainIrradiance = irradianceDevices.stream().filter(i -> ids.contains(i.getId())).findFirst().orElse(null);
                            }
                        }

                    }
                    List<ClientMonthlyDateEntity> dataIrradiance = customerViewService.getIrradianceByDevice(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), mainIrradiance, Constants.ChartingGranularity._1_DAY, Constants.ChartingFilter.TODAY, false, siteUploadingInterval);
                    if (dataIrradiance != null && !dataIrradiance.isEmpty()) {
                        nvmIrradiance = dataIrradiance.get(0).getNvm_irradiance() != null ? dataIrradiance.get(0).getNvm_irradiance() : 0;
                    }

//                    if(expectedList != null) {
//                        for (ClientMonthlyDateEntity item : expectedList) {
//                            expected += item.getExpected_energy() != null ? item.getExpected_energy() : 0;
//                        }
//                    }

                    Map<Integer, List<ClientMonthlyDateEntity>> actualEnergyList = customerViewService.getEnergyByDevice(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), powerDevices, chartingGranularity, chartingFilter, false);
                    if (actualEnergyList != null) {
                        for (DeviceEntity device : powerDevices) {
                            List<ClientMonthlyDateEntity> energyData = actualEnergyList.get(device.getId());
                            if (energyData != null && !energyData.isEmpty()) {
                                actual = energyData.get(0).getEnergy_today() != null ? energyData.get(0).getEnergy_today() : 0;
                            }
                        }
                    }

                    if (dcCapacity != 0 && nvmIrradiance!= 0) {
                        performanceRatioLastWeek += ((actual / dcCapacity) / ((nvmIrradiance * 24) / 1000)) * 100;
                    }
                }
            }
//            double performanceRatioLastWeek = expected > 0 ? (actual / expected) : 0;
            Map<String, Object> res = new HashMap<>();
//            res.put("actual_last_week", actual);
//            res.put("expected_last_week", expected);
            res.put("performance_ratio_last_week", performanceRatioLastWeek / sites.size());
            return res;
        } catch (Exception e) {
            log.error("getActualExpectLastWeek", e);
        }
        return null;
    }

    public List<Map<String, Object>> getKPIData(PortfolioEntity obj) {
        try {
            List<SiteEntity> sites = portfolioService.getSites(obj);
            if (sites == null || sites.isEmpty()) {
                return null;
            }
            List<DeviceEntity> powerDevices = new ArrayList<>();
//            List<DeviceEntity> irradianceDevices = new ArrayList<>();

            ZonedDateTime startDateTime = null;
            ZonedDateTime endDateTime = null;
            Constants.ChartingFilter chartingFilter = Constants.ChartingFilter.fromValue(obj.getId_filter());
            Constants.ChartingGranularity chartingGranularity = Constants.ChartingGranularity._1_DAY;
            List<SiteEnergyEntity> siteEnergyEntities = new ArrayList<>();
            for (SiteEntity site : sites) {
                String timeZone = site.getTime_zone_value();
                ZoneId zoneId = ZoneId.of(timeZone);
                ZonedDateTime now = ZonedDateTime.now(zoneId);
                if ("this_month".equalsIgnoreCase(obj.getId_filter())) {
                    startDateTime = now.withDayOfMonth(1).toLocalDate().atStartOfDay(zoneId);
//                endDateTime = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59).atZone(zoneId);
                    endDateTime = now.toLocalDate().atTime(23, 59, 59).atZone(zoneId);
                    chartingGranularity = Constants.ChartingGranularity._1_MONTH;
                } else if ("last_week".equalsIgnoreCase(obj.getId_filter())) {
                    startDateTime = now.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay(zoneId);
                    endDateTime = now.minusWeeks(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59, 59).atZone(zoneId);
                    chartingGranularity = Constants.ChartingGranularity._7_DAYS;
                } else {
                    // today
                    startDateTime = now.toLocalDate().atStartOfDay(zoneId);
                    endDateTime = now;
                }

                double expectPower = 0;
                Map<String, Object> firstValidTemp = null;
                SiteEnergyEntity siteEnergyEntity = new SiteEnergyEntity();
                siteEnergyEntity.setName(site.getName());
                siteEnergyEntity.setId(site.getId_site());
                siteEnergyEntity.setHash_id(site.getHash_id());
                siteEnergyEntity.setLast_updated(site.getLast_updated());
                siteEnergyEntity.setOverPerformingActualExpected(site.getOverPerformingActualExpected());
                siteEnergyEntity.setOnTargetBetweenActualExpected(site.getOnTargetBetweenActualExpected());
                siteEnergyEntity.setOnTargetAndActualExpected(site.getOnTargetAndActualExpected());
                siteEnergyEntity.setUnderPerformingActualExpected(site.getUnderPerformingActualExpected());
                siteEnergyEntity.setDcCapacity(site.getDc_capacity());

                DevicesByTypeEntity devices = deviceService.getDevicesBySite(site);
                List<DeviceEntity> inverterDevices = devices.getInverter();
                List<DeviceEntity> meterDevices = devices.getMeter();
                List<DeviceEntity>  irradianceDevices = devices.getIrradiance();

                if (inverterDevices != null && !inverterDevices.isEmpty() && irradianceDevices != null && !irradianceDevices.isEmpty()) {
                    Map<String, Object> inverterAvailableParams = new HashMap<>();
                    inverterAvailableParams.put("inverterDevices", inverterDevices);
                    inverterAvailableParams.put("irradianceDevices", irradianceDevices);
                    Double inverterAvailability = (Double) queryForObject("Dashboard.getInverterAvailabilityAllSite", inverterAvailableParams);
                    siteEnergyEntity.setInverterAvailability(inverterAvailability != null ? inverterAvailability / inverterDevices.size() : null);
                }

                powerDevices.addAll(!meterDevices.isEmpty() ? meterDevices : inverterDevices);

                List<ClientMonthlyDateEntity> expected = null;
                if (irradianceDevices != null && !irradianceDevices.isEmpty()) {
                    Constants.UploadingDataIntervals siteUploadingInterval = Constants.UploadingDataIntervals.fromValue(site.getData_send_time());
                    if (irradianceDevices.size() == 1) {
                        expected = customerViewService.getIrradianceByDevice(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), irradianceDevices.get(0), chartingGranularity, chartingFilter, false, siteUploadingInterval);
                    } else {
                        expected = customerViewService.getExpectedBySelectedPOA(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), site.getId_site(), chartingGranularity, chartingFilter, irradianceDevices);
                    }
                    Map<String, Object> moduleTempParams = new HashMap<>();
                    moduleTempParams.put("devices", irradianceDevices);
                    moduleTempParams.put("time_zone", site.getTime_zone_value());
                    List<Map<String, Object>> moduleTempList = (List<Map<String, Object>>) queryForList("Dashboard.getModuleTemp", moduleTempParams);
                    if (moduleTempList != null && !moduleTempList.isEmpty()) {
                        firstValidTemp = moduleTempList.stream()
                                .filter(e -> {
                                    Object value = e != null ? e.get("module_temp") : null;
                                    return value != null && ((Number) value).doubleValue() > 0;
                                })
                                .findFirst()
                                .orElse(null);
                    }

                    DeviceEntity mainIrradiance = null;
                    if (irradianceDevices.size() == 1) {
                        mainIrradiance = irradianceDevices.get(0);
                    }
                    if (irradianceDevices.size() > 1) {
                        ExpectedBySiteDTO siteEntity = (ExpectedBySiteDTO) queryForObject("CustomerView.getSelectedPOABySite", site.getId_site());
                        if (siteEntity != null) {
                            String poas = siteEntity.getIds_device_poa();
                            if (!Lib.isBlank(poas)) {
                                List<Integer> ids = Arrays.asList(poas.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
                                mainIrradiance = irradianceDevices.stream().filter(i -> ids.contains(i.getId())).findFirst().orElse(null);
                            }
                        }
                    }
                    boolean hasVirtualWeather = irradianceDevices.stream().filter(e -> e.getId_device_type() == 21).findFirst().isPresent();

                    if (mainIrradiance != null) {
                        Constants.ChartingGranularity granularity = Constants.ChartingGranularity._1_MINUTE;
                        if (hasVirtualWeather) {
                            granularity = Constants.ChartingGranularity._15_MINUTES;
                        } else {
                            if (site.getData_send_time() == 1) {
                                granularity = Constants.ChartingGranularity._5_MINUTES;
                            } else if (site.getData_send_time() == 2) {
                                granularity = Constants.ChartingGranularity._15_MINUTES;
                            }
                        }
                        List<Map<String, Object>> irradianceData = sitesAnalyticsService.getDeviceData(mainIrradiance, startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), granularity, Constants.ChartingFilter.TODAY);
                        if (irradianceData != null && !irradianceData.isEmpty()) {
                            Map<String, Object> last = irradianceData.get(irradianceData.size() - 1);
                            expectPower = last.get("expected_power") != null ? ((Number) last.get("expected_power")).doubleValue() : 0;;
                        }
                        Map<String, Object> inverterRatioParams = new HashMap<>();
                        inverterRatioParams.put("id_site", site.getId_site());
                        inverterRatioParams.put("expected_power", expectPower);
                        Map<String, Object> listInverterRatio = (Map<String, Object>) queryForObject("Dashboard.getInverterRatioAllSite", inverterRatioParams);
                        String devicesList = (String) listInverterRatio.get("devices_list");
                        Double ivtRatio = null;
                        int totalIvt = 1;
                        JSONParser parse = new JSONParser();
                        List<Map<String, Object>> jsonArray = (JSONArray) parse.parse(devicesList);
                        if (jsonArray != null && !jsonArray.isEmpty()) {
                            jsonArray = jsonArray.stream().filter(e -> Integer.parseInt(e.get("id_device_type").toString()) == 1).collect(Collectors.toList());
                            totalIvt = jsonArray.size();
                            for (Map<String, Object> json : jsonArray) {
                                Object ratio = json.get("comparison_ratio");
                                if (ratio != null) {
                                    ivtRatio = ivtRatio == null ? (Double) ratio : ivtRatio + (Double) ratio;
                                }
//                                ivtRatio += json.get("comparison_ratio") == null ? 0 : Double.parseDouble(json.get("comparison_ratio").toString());
                            }
                        }

                        if (ivtRatio != null) {
                            siteEnergyEntity.setInverterRatio(ivtRatio / totalIvt);
                        }

                        List<ClientMonthlyDateEntity> dataIrradiance = customerViewService.getIrradianceByDevice(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), mainIrradiance, Constants.ChartingGranularity._1_DAY, Constants.ChartingFilter.TODAY, false, siteUploadingInterval);
                        if (dataIrradiance != null && !dataIrradiance.isEmpty()) {
                            siteEnergyEntity.setIrradiance(dataIrradiance.get(0).getNvm_irradiance());
                        }
                    }

                }
                if(expected != null && !expected.isEmpty()) {
//                    expected.stream().findAny().ifPresent(item -> siteEnergyEntity.setExpectedEnergy(item.getExpected_energy()));
                    Double totalExpected = expected.stream()
                            .map(item -> item.getExpected_energy())
                            .filter(Objects::nonNull)
                            .mapToDouble(Double::doubleValue)
                            .sum();

                    boolean hasExpected = expected.stream().anyMatch(item -> item.getExpected_energy() != null);

                    if (hasExpected) {
                        siteEnergyEntity.setExpectedEnergy(totalExpected);
                    }
                }
                if (firstValidTemp != null) {
                    siteEnergyEntity.setModuleTemp((Double) firstValidTemp.get("module_temp"));
                }
                siteEnergyEntities.add(siteEnergyEntity);
            }

            Map<Integer, List<ClientMonthlyDateEntity>> actualEnergyList = customerViewService.getEnergyByDevice(startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), powerDevices, chartingGranularity, chartingFilter, false);
            Map<Integer, SiteEnergyEntity> siteMap = siteEnergyEntities.stream()
                    .collect(Collectors.toMap(
                            SiteEnergyEntity::getId,
                            Function.identity()
                    ));
            if (actualEnergyList != null) {
                for (DeviceEntity device : powerDevices) {
                    List<ClientMonthlyDateEntity> energyData = actualEnergyList.get(device.getId());
                    SiteEnergyEntity site = siteMap.get(device.getId_site());
                    if (energyData != null && !energyData.isEmpty() && site != null) {
                        Double energy = energyData.stream()
                                .map(ClientMonthlyDateEntity::getEnergy_today)
                                .filter(Objects::nonNull)
                                .mapToDouble(Double::doubleValue)
                                .sum();
                        boolean hasEnergy = energyData.stream().anyMatch(item -> item.getEnergy_today() != null);
                        if (!hasEnergy) {
                            continue;
                        }
                        Double current = site.getActualEnergy();
                        site.setActualEnergy(current == null ? energy : current + energy);
                    }
                }
            }

            Map<String, Object> getAlertParams = new HashMap<>();
            getAlertParams.put("id", null);
            List<Map<String, Object>> alertBySites = queryForList("Dashboard.getPrioritySite", getAlertParams);
            Map<Integer, Map<String, Object>> alertBySiteMap = new HashMap<>();

            if (alertBySites != null && !alertBySites.isEmpty()) {
                for (Map<String, Object> site : alertBySites) {
                    Integer id = (Integer) site.get("id");
                    alertBySiteMap.put(id, site);
                }
            }

            List<SitesMetricsSummaryEntity> power =  queryForList("Dashboard.getTotalPowerAndCapacity", obj);
            Map<Integer, SitesMetricsSummaryEntity> powerMap = new HashMap<>();
            if (power != null && !power.isEmpty()) {
                for (SitesMetricsSummaryEntity site : power) {
                    Integer id = site.getId();
                    powerMap.put(id, site);
                }
            }

            List<Map<String, Object>> energy = new ArrayList<>();
            String expectedEnergySuffix = !"today".equalsIgnoreCase(obj.getId_filter()) ? ("_" + obj.getId_filter()) : "";
            for (SiteEnergyEntity data : siteEnergyEntities) {
                Map<String, Object> item = new HashMap<>();

//                List<ClientMonthlyDateEntity> energyList = new ArrayList<>();
//                for (DeviceEntity device : powerDevices) {
//                    List<ClientMonthlyDateEntity> energyData = actualEnergyList.get(device.getId());
//                    SiteEnergyEntity site = siteMap.get(device.getId_site());
//                    if (site.getId() == data.getId()) {
//                        energyList.addAll(energyData);
//                    }
//                }
//                item.put("actualEnergyList", energyList);

                Double actual = data.getActualEnergy();
                Double expected = data.getExpectedEnergy();
                Double loss = (actual != null && expected != null) ? expected - actual : null;
//                double AE = (expected > 0) ? (actual / expected) : 0;
                double variance = (actual != null && expected != null && expected > 0)  ? ((actual - expected) / expected) : 0;
                double dcCapacity = data.getDcCapacity() != null ? data.getDcCapacity() : 0;
                Double PR = null;
                double hPoa = data.getIrradiance() != null ? data.getIrradiance() * 24 : 0;
                if (dcCapacity != 0 && hPoa != 0) {
                    PR =  (actual / data.getDcCapacity()) / (hPoa / 1000);
                }

                item.put("module_temp", data.getModuleTemp());
                item.put("actual_energy", actual);
                item.put("expected_energy" + expectedEnergySuffix , expected);
                item.put("loss", loss != null ? Math.max(0, loss) : null);
                item.put("name", data.getName());
                item.put("id", data.getId());
                item.put("hash_id", data.getHash_id());
//                item.put("performance_ratio", AE * 100);
                item.put("performance_ratio", PR != null ? PR * 100 : null);
                item.put("overPerformingActualExpected", data.getOverPerformingActualExpected());
                item.put("onTargetBetweenActualExpected", data.getOnTargetBetweenActualExpected());
                item.put("onTargetAndActualExpected", data.getOnTargetAndActualExpected());
                item.put("underPerformingActualExpected", data.getUnderPerformingActualExpected());
                item.put("actualEnergy", actual);
                item.put("expectedEnergy", expected);
//                item.put("ae", AE);
                item.put("ae", PR);
                item.put("variance", PR);
                item.put("inverter_ratio", data.getInverterRatio());
                item.put("inverter_availability", data.getInverterAvailability() != null ? data.getInverterAvailability() * 100 : null);

                if (alertBySiteMap.containsKey(data.getId())) {
                    Map<String, Object> siteInfo = alertBySiteMap.get(data.getId());
                    item.put("critical_count", siteInfo.get("critical_count"));
                    item.put("warning_count", siteInfo.get("warning_count"));
                    item.put("info_device_count", siteInfo.get("info_device_count"));
                    item.put("fatal_device_count", siteInfo.get("fatal_device_count"));
                    item.put("error_device_count", siteInfo.get("error_device_count"));
                    item.put("warning_device_count", siteInfo.get("warning_device_count"));
                    item.put("debug_device_count", siteInfo.get("debug_device_count"));
                    item.put("no_production_device_count", siteInfo.get("no_production_device_count"));
                    item.put("no_comm_device_count", siteInfo.get("no_comm_device_count"));
                    item.put("power_factor_device_count", siteInfo.get("power_factor_device_count"));
                    item.put("grid_frequency_device_count", siteInfo.get("grid_frequency_device_count"));
                    item.put("zone_device_count", siteInfo.get("zone_device_count"));
                    item.put("breaker_device_count", siteInfo.get("breaker_device_count"));
                    item.put("hvac_alert_device_count", siteInfo.get("hvac_alert_device_count"));
                    item.put("custom_alert_device_count", siteInfo.get("custom_alert_device_count"));
                }
                if (powerMap.containsKey(data.getId())) {
                    SitesMetricsSummaryEntity siteInfo = powerMap.get(data.getId());
                    item.put("active_power", siteInfo.getActivePower());
                    item.put("ac_capacity", siteInfo.getCapacity());
                    item.put("dc_capacity", siteInfo.getDc_capacity());
                }

                energy.add(item);
            }
            return energy;
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

//    public List<Map<String, Object>> getKPIData(PortfolioEntity obj) {
//        try {
//            List<SiteEntity> sites = portfolioService.getSites(obj);
//            if (sites == null || sites.isEmpty()) {
//                return null;
//            }
//            String timeZone = sites.get(0).getTime_zone_value();
//            ZoneId zoneId = ZoneId.of(timeZone);
//            ZonedDateTime now = ZonedDateTime.now(zoneId);
//            ZonedDateTime startDateTime;
//            ZonedDateTime endDateTime;
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            if ("this_month".equalsIgnoreCase(obj.getId_filter())) {
//                startDateTime = now.withDayOfMonth(1).toLocalDate().atStartOfDay(zoneId);
//                endDateTime = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59).atZone(zoneId);
//            } else if ("last_week".equalsIgnoreCase(obj.getId_filter())) {
//                startDateTime = now.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay(zoneId);
//                endDateTime = now.minusWeeks(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59, 59).atZone(zoneId);
//            } else {
//                // today
//                startDateTime = now.toLocalDate().atStartOfDay(zoneId);
//                endDateTime = now;
//            }
//            String start = startDateTime.format(formatter);
//            String end = endDateTime.format(formatter);
//            obj.setStart_date(start);
//            obj.setEnd_date(end);
//            List<SiteEnergyEntity> list = portfolioService.getSitesMetricsActualVsExpected(obj);
//
//            //Get critical, warning for portfolio sites
//            Map<String, Object> getAlertParams = new HashMap<>();
//            getAlertParams.put("id", null);
//            List<Map<String, Object>> alertBySites = queryForList("Dashboard.getPrioritySite", getAlertParams);
//            Map<String, Object> inverterAvailableParams = new HashMap<>();
//            inverterAvailableParams.put("id_sites", obj.getId_sites());
//            inverterAvailableParams.put("time_zone", timeZone);
//            List<Map<String, Object>> listInverterAvailable = queryForList("Dashboard.getInverterRatioAllSite", inverterAvailableParams);
//            List<SitesMetricsSummaryEntity> power =  queryForList("Dashboard.getTotalPowerAndCapacity", obj);
//
//            Map<Integer, Map<String, Object>> alertBySiteMap = new HashMap<>();
//            Map<Integer, Map<String, Object>> listInverterAvailableMap = new HashMap<>();
//            Map<Integer, SitesMetricsSummaryEntity> powerMap = new HashMap<>();
//
//            if (alertBySites != null && !alertBySites.isEmpty()) {
//                for (Map<String, Object> site : alertBySites) {
//                    Integer id = (Integer) site.get("id");
//                    alertBySiteMap.put(id, site);
//                }
//            }
//            if (listInverterAvailable != null && !listInverterAvailable.isEmpty()) {
//                for (Map<String, Object> item : listInverterAvailable) {
//                    Integer id = (Integer) item.get("id");
//                    listInverterAvailableMap.put(id, item);
//                }
//            }
//            if (power != null && !power.isEmpty()) {
//                for (SitesMetricsSummaryEntity site : power) {
//                    Integer id = site.getId();
//                    powerMap.put(id, site);
//                }
//            }
//            String expectedEnergySuffix = !"today".equalsIgnoreCase(obj.getId_filter()) ? ("_" + obj.getId_filter()) : "";
//            List<Map<String, Object>> energy = new ArrayList<>();
//            for (SiteEnergyEntity data : list) {
//                double expectPower = 0;
//                Map<String, Object> item = new HashMap<>();
//                Map<String, Object> firstValidTemp = null;
//                double actual = data.getActualEnergy() != null ? data.getActualEnergy() : 0;
//                double expected = data.getExpectedEnergy() != null ? data.getExpectedEnergy() : 0;
//                double loss = expected - actual;
//
//                DevicesByTypeEntity devices = deviceService.getDevicesBySite(data);
//                List<DeviceEntity> irradianceDevices = devices.getIrradiance();
//                if (irradianceDevices != null && !irradianceDevices.isEmpty()) {
//                    Map<String, Object> moduleTempParams = new HashMap<>();
//                    moduleTempParams.put("devices", irradianceDevices);
//                    List<Map<String, Object>> moduleTempList = (List<Map<String, Object>>) queryForList("Dashboard.getModuleTemp", moduleTempParams);
//                    if (moduleTempList != null && !moduleTempList.isEmpty()) {
//                        firstValidTemp = moduleTempList.stream()
//                                .filter(e -> {
//                                    Object value = e.get("module_temp");
//                                    return value != null && ((Number) value).doubleValue() > 0;
//                                })
//                                .findFirst()
//                                .orElse(null);
//                    }
//
//                    DeviceEntity mainIrradiance = null;
//                    if (irradianceDevices.size() == 1) {
//                        mainIrradiance = irradianceDevices.get(0);
//                    }
//                    if (irradianceDevices.size() > 1) {
//                        ExpectedBySiteDTO siteEntity = (ExpectedBySiteDTO) queryForObject("CustomerView.getSelectedPOABySite", data.getId());
//                        if (siteEntity != null) {
//                            String poas = siteEntity.getIds_device_poa();
//                            if (!Lib.isBlank(poas)) {
//                                List<Integer> ids = Arrays.asList(poas.split(",")).stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
//                                mainIrradiance = irradianceDevices.stream().filter(i -> ids.contains(i.getId())).findFirst().orElse(null);
//                            }
//                        }
//                    }
//                    boolean hasVirtualWeather = irradianceDevices.stream().filter(e -> e.getId_device_type() == 21).findFirst().isPresent();
//
//                    if (mainIrradiance != null) {
//                        Constants.ChartingGranularity granularity = Constants.ChartingGranularity._1_MINUTE;
//                        if (hasVirtualWeather) {
//                            granularity = Constants.ChartingGranularity._15_MINUTES;
//                        } else {
//                            if (sites.get(0).getData_send_time() == 1) {
//                                granularity = Constants.ChartingGranularity._5_MINUTES;
//                            } else if (sites.get(0).getData_send_time() == 2) {
//                                granularity = Constants.ChartingGranularity._15_MINUTES;
//                            }
//                        }
//                        List<Map<String, Object>> irradianceData = sitesAnalyticsService.getDeviceData(mainIrradiance, startDateTime.toLocalDateTime(), endDateTime.toLocalDateTime(), granularity, Constants.ChartingFilter.TODAY);
//                        if (irradianceData != null && !irradianceData.isEmpty()) {
//                            Map<String, Object> last = irradianceData.get(irradianceData.size() - 1);
//                            expectPower = last.get("expected_power") != null ? ((Number) last.get("expected_power")).doubleValue() : 0;;
//                        }
//
//                    }
//                }
//
//                item.put("module_temp", firstValidTemp != null ? firstValidTemp.get("module_temp") : 0);
//                item.put("actual_energy", data.getActualEnergy());
//                item.put("expected_energy" + expectedEnergySuffix , data.getExpectedEnergy() != null ? data.getExpectedEnergy() : 0);
//                item.put("loss", loss);
//                item.put("name", data.getName());
//                item.put("id", data.getId());
//                item.put("hash_id", data.getHash_id());
//                item.put("performance_ratio", data.getAe() != null ? data.getAe() * 100 : 0);
//                if (alertBySiteMap.containsKey(data.getId())) {
//                    Map<String, Object> siteInfo = alertBySiteMap.get(data.getId());
//
//                    item.put("critical_count", siteInfo.get("critical_count"));
//                    item.put("warning_count", siteInfo.get("warning_count"));
//
//                    item.put("info_device_count", siteInfo.get("info_device_count"));
//                    item.put("fatal_device_count", siteInfo.get("fatal_device_count"));
//                    item.put("error_device_count", siteInfo.get("error_device_count"));
//                    item.put("warning_device_count", siteInfo.get("warning_device_count"));
//                    item.put("debug_device_count", siteInfo.get("debug_device_count"));
//                    item.put("no_production_device_count", siteInfo.get("no_production_device_count"));
//                    item.put("no_comm_device_count", siteInfo.get("no_comm_device_count"));
//                    item.put("power_factor_device_count", siteInfo.get("power_factor_device_count"));
//                    item.put("grid_frequency_device_count", siteInfo.get("grid_frequency_device_count"));
//                    item.put("zone_device_count", siteInfo.get("zone_device_count"));
//                    item.put("breaker_device_count", siteInfo.get("breaker_device_count"));
//                    item.put("hvac_alert_device_count", siteInfo.get("hvac_alert_device_count"));
//                    item.put("custom_alert_device_count", siteInfo.get("custom_alert_device_count"));
//                }
//                if (powerMap.containsKey(data.getId())) {
//                    SitesMetricsSummaryEntity siteInfo = powerMap.get(data.getId());
//                    item.put("active_power", siteInfo.getActivePower());
//                    item.put("ac_capacity", siteInfo.getCapacity());
//                    item.put("dc_capacity", siteInfo.getDc_capacity());
//                }
//
//                if (listInverterAvailableMap.containsKey(data.getId())) {
//                    Map<String, Object> siteInfo = listInverterAvailableMap.get(data.getId());
//                    String devicesList = (String) siteInfo.get("devices_list");
//                    double ivtRatio = 0;
//                    int totalIvt = 1;
//                    JSONParser parse = new JSONParser();
//                    List<Map<String, Object>> jsonArray = (JSONArray) parse.parse(devicesList);
//                    if (jsonArray != null && !jsonArray.isEmpty()) {
//                        jsonArray = jsonArray.stream().filter(e -> Integer.parseInt(e.get("id_device_type").toString()) == 1).collect(Collectors.toList());
//                        totalIvt = jsonArray.size();
//                        for (Map<String, Object> json : jsonArray) {
//                            ivtRatio += json.get("comparison_ratio") == null ? 0 : Double.parseDouble(json.get("comparison_ratio").toString());
//                        }
//                    }
//                    item.put("inverter_availability", ivtRatio / totalIvt);
//                }
//                energy.add(item);
//            }
//            return energy;
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("getKPIData", e);
//            return null;
//        }
//    }

    public List<Map<String, Object>> getSiteMapData(Map<String, Object> obj) {
        try {
            List<SiteEntity> dataList = (List<SiteEntity>) queryForList("Dashboard.getSiteMapData", obj);
            if (dataList == null || dataList.isEmpty()) {
                return null;
            }
            return dataList.stream()
                    .map(site -> {
                        Map<String, Object> item = new HashMap<>();
                        item.put("id", site.getId());
                        item.put("name", site.getName());
                        item.put("lat", site.getLat());
                        item.put("lng", site.getLng());
                        return item;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("DashboardService.getSiteMapData", e);
        }
        return null;

    }

    public List<Map<String, Object>> getSiteDetail(Map<String, Object> obj) {
        try {
            Integer idSite = (Integer) obj.get("idSite");
            if (idSite == null || idSite == 0) {
                return null;
            }
            List<Integer> siteIds = new ArrayList<>();
            siteIds.add(idSite);
            Map<String, Object> params = new HashMap<>();
            params.put("id_sites", siteIds);
            List<Map<String, Object>> data = getChartDataPerformance(params);
            return data;
        } catch (Exception e) {
            log.error("DashboardService.getSiteDetail", e);
        }
        return null;
    }

    private Map<String, Object> prepareDataChart(Map<String, Object> obj, List<DeviceEntity> productionDevice, List<DeviceEntity> irradianceDevice, List<DeviceEntity> consumeDevice, boolean isEnergy) {
        try {
            if (obj == null) {
                return  null;
            }
            String filterBy = obj.get("filter_by") != null ? (String) obj.get("filter_by") : "today";
            String interval = obj.get("interval") != null ? (String) obj.get("interval") : "1_hour";

            PortfolioEntity entity = new PortfolioEntity();
            List idSites = obj.get("id_sites") != null ? (List) obj.get("id_sites") : null;
            entity.setId_sites(idSites);
            List<SiteEntity> sites = portfolioService.getSites(entity);
            if (sites == null || sites.isEmpty()) {
                return null;
            }
            List<DeviceEntity> meterDevices = new ArrayList<>();
            List<DeviceEntity> inverterDevices = new ArrayList<>();
            for (SiteEntity site : sites) {
                DevicesByTypeEntity devices = deviceService.getDevicesBySite(site);
//                meterDevices.addAll(devices.getMeter().stream().filter(device -> Constants.DeviceType.CONSUMPTION_METER != Constants.DeviceType.fromValue(device.getId_device_type())).collect(Collectors.toList()));
                meterDevices.addAll(devices.getMeter().isEmpty() ? devices.getInverter() : devices.getMeter());
                inverterDevices.addAll(devices.getInverter());
                if (consumeDevice != null) {
                    consumeDevice.addAll(devices.getMeter().stream().filter(device -> Constants.DeviceType.CONSUMPTION_METER == Constants.DeviceType.fromValue(device.getId_device_type())).collect(Collectors.toList()));
                }
                if (irradianceDevice != null) {
                    List<DeviceEntity> irradianceList = devices.getIrradiance();
                    DeviceEntity mainIrradiance = null;
                    if (irradianceList.size() == 1) {
                        mainIrradiance = irradianceList.get(0);
                    }
                    if (irradianceList.size() > 1) {
                        ExpectedBySiteDTO siteEntity = (ExpectedBySiteDTO) queryForObject("CustomerView.getSelectedPOABySite", site.getId_site());
                        if (siteEntity != null) {
                            String poas = siteEntity.getIds_device_poa();
                            if (!Lib.isBlank(poas)) {
                                List<Integer> ids = Arrays.asList(poas.split(",")).stream().map(item -> Integer.parseInt(item)).collect(Collectors.toList());
                                mainIrradiance = irradianceList.stream().filter(i -> ids.contains(i.getId())).findFirst().orElse(null);

                            }
                        }
                    }
                    if (mainIrradiance != null) {
                        irradianceDevice.add(mainIrradiance);
                    }

                }
            }
            if (productionDevice == null) {
                productionDevice = new ArrayList<>();
            }
            productionDevice.addAll(!meterDevices.isEmpty() ? meterDevices : inverterDevices);
            String timeZone = ((String) obj.get("time_zone")) != null ? (String) obj.get("time_zone") : sites.get(0).getTime_zone_value();
            ZoneId zoneId = ZoneId.of(timeZone);
            ZonedDateTime now = ZonedDateTime.now(zoneId);
            ZonedDateTime startDateTime;
            ZonedDateTime endDateTime;
            if ("this_month".equalsIgnoreCase(filterBy)) {
                startDateTime = now.withDayOfMonth(1).toLocalDate().atStartOfDay(zoneId);
                endDateTime = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59).atZone(zoneId);
            } else if ("this_week".equalsIgnoreCase(filterBy)) {
                startDateTime = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate().atStartOfDay(zoneId);
                endDateTime = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).toLocalDate().atTime(23, 59, 59).atZone(zoneId);
            } else {
                // today
                startDateTime = now.toLocalDate().atStartOfDay(zoneId);
                endDateTime = now;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            String start = startDateTime.format(formatter);
            String end = endDateTime.format(formatter);
            int dataSendTime;
            if (isEnergy) {
                if ("1_hour".equalsIgnoreCase(interval)) {
                    dataSendTime = Constants.ChartingGranularity._1_HOUR.getValue();
                } else if ("15_min".equalsIgnoreCase(interval)) {
                    dataSendTime = Constants.ChartingGranularity._15_MINUTES.getValue();
                } else {
                    dataSendTime = Constants.ChartingGranularity._1_DAY.getValue();
                }
            } else {
                boolean hasVirtualWeather = irradianceDevice.stream().filter(item -> item.getId_device_type() == 21).findFirst().isPresent();
                if (hasVirtualWeather) {
                    dataSendTime = Constants.ChartingGranularity._15_MINUTES.getValue();
                } else {
                    if (sites.get(0).getData_send_time() == 1) {
                        dataSendTime = Constants.ChartingGranularity._5_MINUTES.getValue();
                    } else if (sites.get(0).getData_send_time() == 2) {
                        dataSendTime = Constants.ChartingGranularity._15_MINUTES.getValue();
                    } else {
                        dataSendTime = Constants.ChartingGranularity._1_MINUTE.getValue();
                    }
                }
            }

            Map<String, Object> data = new HashMap<>();
            data.put("dataSendTime", dataSendTime);
            data.put("start", start);
            data.put("end", end);
            data.put("filterBy", filterBy);
            data.put("interval", interval);
            return data;
        } catch (Exception e) {
            log.error("prepareData", e);
        }
        return null;
    }

    public List<Map<String, Object>> getChartDataPerformance(Map<String, Object> obj) {
        try {
            if (obj == null) {
                return  null;
            }
            List<DeviceEntity> productionDevice = new ArrayList<>();
            List<DeviceEntity> irradianceDevice = new ArrayList<>();
            Map<String, Object> data = prepareDataChart(obj, productionDevice, irradianceDevice, null, false);
            if (data == null) {
                return null;
            }
            String filterBy = (String) data.get("filterBy");
            String start = (String) data.get("start");
            String end = (String) data.get("end");
            int dataSendTime = (Integer) data.get("dataSendTime");
            String locale = (String) obj.get("locale");
            Map<String, Double> actualMap = calculateDataByTime(productionDevice, filterBy, start, end, dataSendTime, locale, false);
            Map<String, Double> expectMap = calculateDataByTime(irradianceDevice, filterBy, start, end, dataSendTime, locale, false);
            Map<String, Map<String, Object>> groupedData = new LinkedHashMap<>();

            Set<String> allTimes = new TreeSet<>();
            allTimes.addAll(actualMap.keySet());
            allTimes.addAll(expectMap.keySet());

            for (String categoryTime : allTimes) {
                double actual = actualMap.getOrDefault(categoryTime, 0D);
                double expect = expectMap.getOrDefault(categoryTime, 0D);

                Map<String, Object> item = new HashMap<>();
                item.put("category_time", categoryTime);
                item.put("actual_power", actual);
                item.put("expect_power", expect);

                groupedData.put(categoryTime, item);
            }

            return new ArrayList<>(groupedData.values());
        } catch (Exception e) {
            log.error("DashboardService.getChartDataPerformance", e);
        }
        return null;
    }

    public List<Map<String, Object>> getChartEnergyFlow(Map<String, Object> obj) {
        try {
            if (obj == null) {
                return  null;
            }
            List<DeviceEntity> productionDevice = new ArrayList<>();
            List<DeviceEntity> consumeDevice = new ArrayList<>();
            Map<String, Object> data = prepareDataChart(obj, productionDevice, null, consumeDevice, true);
            if (data == null) {
                return null;
            }
            String filterBy = (String) data.get("filterBy");
            String start = (String) data.get("start");
            String end = (String) data.get("end");
            int dataSendTime = (Integer) data.get("dataSendTime");
            String locale = (String) obj.get("locale");
            Map<String, Double> produceMap = calculateDataByTime(productionDevice, filterBy, start, end, dataSendTime, locale, true);
            Map<String, Double> consumeMap = calculateDataByTime(consumeDevice, filterBy, start, end, dataSendTime, locale, true);

            Map<String, Map<String, Object>> groupedData = new LinkedHashMap<>();

            Set<String> allTimes = new TreeSet<>();
            allTimes.addAll(produceMap.keySet());
            allTimes.addAll(consumeMap.keySet());

            for (String categoryTime : allTimes) {
                double produceData = produceMap.getOrDefault(categoryTime, 0D);
                Double consumeData = consumeMap.getOrDefault(categoryTime, null);

                Map<String, Object> item = new HashMap<>();
                item.put("category_time", categoryTime);
                item.put("produce_data", produceData);
                item.put("consume_data", consumeData);
                item.put("exported_data", consumeData != null ?  (produceData - consumeData) : null);

                groupedData.put(categoryTime, item);
            }

            return new ArrayList<>(groupedData.values());
        } catch (Exception e) {
            log.error("DashboardService.getChartEnergyFlow", e);
        }
        return null;
    }

    private Map<String, Double> calculateDataByTime(List<DeviceEntity> devices, String filterBy, String start, String end, int dataSendTime, String locale, boolean isEnergy) {
        Map<String, Double> resultMap = new HashMap<>();
        try {
            if (devices == null || devices.isEmpty()) {
                return resultMap;
            }

            for (DeviceEntity device : devices) {
                List<DeviceParameterEntity> deviceParameterEntities = device.getParameters();
                if (deviceParameterEntities == null || deviceParameterEntities.isEmpty()) {
                    continue;
                }
                if (isEnergy) {
                    DeviceParameterEntity deviceParameterEntity = deviceParameterEntities.stream()
                            .filter(item -> item.isIs_energy() && item.isIs_user_defined())
                            .findFirst()
                            .orElse(null);
                    if (deviceParameterEntity != null) {
                        device.setParameter_slug(deviceParameterEntity.getSlug());
                    }
                } else {
                    DeviceParameterEntity actualPowerParam = deviceParameterEntities.stream()
                            .filter(d -> d.isIs_active_power())
                            .findFirst()
                            .orElse(null);
                    if (actualPowerParam != null) {
                        device.setParameter_slug(actualPowerParam.getSlug());
                    } else {
                        device.setParameter_slug("expected_power");
                    }
                }
            }

            DeviceEntity request = new DeviceEntity();
            request.setDataDevice(devices);
            request.setFilterBy(filterBy);
            request.setStart_date(start);
            request.setEnd_date(end);
            request.setLocale(locale);
            request.setData_send_time(dataSendTime);

            List<Map<String, Object>> queryResult = sitesAnalyticsService.getChartParameterDevice(request);
            if (queryResult == null || queryResult.isEmpty()) {
                return resultMap;
            }

            Map<Integer, DeviceEntity> deviceMap = devices.stream()
                    .collect(Collectors.toMap(
                            DeviceEntity::getId,
                            Function.identity()));

            for (Map<String, Object> item : queryResult) {
                Integer deviceId = (Integer) item.get("id");
                DeviceEntity found = deviceMap.get(deviceId);
                if (found == null || Lib.isBlank(found.getParameter_slug())) {
                    continue;
                }
                List<Map<String, Object>> chartData = (List<Map<String, Object>>) item.get("data");
                if (chartData == null) {
                    continue;
                }
                for (Map<String, Object> chart : chartData) {
                    String categoriesTime = (String) chart.get("categories_time");
                    String timeFull = (String) chart.get("time_full");
                    Object value = chart.get(found.getParameter_slug());
//                    if (value == null) {
//                        continue;
//                    }
                    double energy = value != null ? ((Number) value).doubleValue() : 0D;
                    resultMap.merge(timeFull, Math.max(0, energy), Double::sum);
                }
            }
        } catch (Exception e) {
            log.error("DashboardService.aggregateEnergyByTime", e);
        }
        return resultMap;
    }

    public Map<String, Object> getTopPrioritySite(SiteEntity obj) {
        try {
            if (obj == null) {
                return  null;
            }
            Map<String, Object> res = (Map<String, Object>) queryForObject("Dashboard.getPrioritySite", obj);
            return res;
        } catch (Exception e) {
            log.error("DashboardService.getTopPrioritySite", e);
        }
        return null;
    }

    public List<Map<String, Object>> getTopDeviceAlert(Map<String, Object> obj) {
        try {
            if (obj == null) {
                return  null;
            }

            obj.put("id_sites", obj.get("id_sites"));
            obj.put("limit", 5);
            List<Map<String, Object>> res = queryForList("Dashboard.getTopDeviceAlert", obj);

            return res;

        } catch (Exception e) {
            log.error("DashboardService.getTopDeviceAlert", e);
        }

        return null;
    }

    public List<Map<String, Object>> getListCompanyOfUser(Map<String, Object> obj) {
        try {
            List sites = (List) obj.get("id_sites");
            if (sites == null || sites.isEmpty()) {
                return null;
            }
            List<Map<String, Object>> data = (List<Map<String, Object>>) queryForList("Dashboard.getCompanyWithTimeZones", sites);
            if (data == null) {
                return null;
            }
            for (Map<String, Object> row : data) {
                String timeZonesJson = (String) row.get("time_zones");
                if (Lib.isBlank(timeZonesJson)) {
                    continue;
                }
                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, Object>> timeZones = mapper.readValue(timeZonesJson, new TypeReference<List<Map<String, Object>>>() {});
                row.put("time_zones", timeZones);
            }
            return data;
        } catch (Exception e) {
            log.error("DashboardService.getListCompanyOfUser", e);
        }
        return null;
    }

}
