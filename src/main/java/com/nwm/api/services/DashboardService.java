/********************************************************
* Copyright 2020-2021 NEXT WAVE ENERGY MONITORING INC.
* All rights reserved.
* 
*********************************************************/
package com.nwm.api.services;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nwm.api.DBManagers.DB;
import com.nwm.api.entities.*;
import com.nwm.api.utils.Lib;

public class DashboardService extends DB {
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
                case "inverter_availability":
                    SiteEntity entity = new SiteEntity();
                    entity.setId_sites(obj.getId_sites());
                    Map<String, Object> inverterAvailability = getInverterAvailabilityAllSite(entity);
                    res.put("inverter_availability", inverterAvailability != null ? inverterAvailability.get("total_availability_percent") : 0);
                    break;
                case "expected_energy_today":
                case "expected_energy_this_month":
                case "expected_energy_last_week":
                    String idFilter = key.split("expected_energy_")[1];
                    obj.setId_filter(idFilter);
                    List<Map<String, Object>> energy = getKPIData(obj);
                    double totalExpected = 0;
                    for (Map<String, Object> item : energy) {
                        totalExpected += item.get("expected") != null ? (double) item.get("expected") : 0;
                    }
                    res.put("total_expected_" + idFilter, totalExpected);
                    res.put("energy", energy);
                    break;
                case "ac_capacity":
                case "dc_capacity":
                case "active_power":
                    Map<String, Object> power = getTotalPowerAndCapacity(obj);
                    res.put(key, power.get(key));
                    break;
            }

            return res;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, Object> getTimeByFilter(String timeZone, String filter) {
        if (Lib.isBlank(timeZone) || Lib.isBlank(filter)) {
            return null;
        }
        Map<String, Object> res = new HashMap<>();
        try {
            ZoneId zoneId = ZoneId.of(timeZone);
            ZonedDateTime nowLocal = ZonedDateTime.now(zoneId);
            ZonedDateTime localStartTime;
            ZonedDateTime localEndTime;
            if ("today".equalsIgnoreCase(filter)) {
                localStartTime = nowLocal.toLocalDate().atStartOfDay(zoneId);
                localEndTime = nowLocal.toLocalDate().atTime(LocalTime.MAX).atZone(zoneId);
            } else if ("this_week".equalsIgnoreCase(filter)) {
                LocalDate monday = nowLocal.toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate sunday = monday.plusDays(6);
                localStartTime = monday.atStartOfDay(zoneId);
                localEndTime = sunday.atTime(LocalTime.MAX).atZone(zoneId);

            } else if ("this_month".equalsIgnoreCase(filter)) {
                localStartTime = nowLocal.withDayOfMonth(1).toLocalDate().atStartOfDay(zoneId);
                localEndTime = nowLocal.toLocalDate().atTime(LocalTime.MAX).atZone(zoneId);
            } else {
                LocalDate thisWeekMonday = nowLocal.toLocalDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                LocalDate lastWeekMonday = thisWeekMonday.minusWeeks(1);
                LocalDate lastWeekSunday = thisWeekMonday.minusDays(1);
                localStartTime = lastWeekMonday.atStartOfDay(zoneId);
                localEndTime = lastWeekSunday.atTime(LocalTime.MAX).atZone(zoneId);
            }
            ZonedDateTime utcStartTime = localStartTime.withZoneSameInstant(ZoneOffset.UTC);
            ZonedDateTime utcEndTime = localEndTime.withZoneSameInstant(ZoneOffset.UTC);
            Double duration = Duration.between(localStartTime, localEndTime).getSeconds() / 3600.0;
            res.put("duration", duration);
            res.put("start_time", utcStartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            res.put("end_time", utcEndTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return res;
        } catch (Exception e) {

        }
        return null;
    }

//    public List<EnergyEntity> getEnergyExpected(PortfolioEntity obj, boolean needActual) {
//        try {
//            PortfolioService service = new PortfolioService();
//            List<SiteEntity> sites = service.getSites(obj);
//            if (sites.size() == 0) return new ArrayList<>();
//
//            if (obj.getId_employee() == 0)
//                obj.setId_sites(sites.stream().map(SiteEntity::getId_site).collect(Collectors.toList()));
//
//            List<DeviceEntity> allDevices = queryForList("Dashboard.getDevicesBySites", obj);
//            Map<Integer, List<DeviceEntity>> devicesBySite = allDevices.stream().collect(Collectors.groupingBy(item -> item.getId_site()));
//
//            List<CompletableFuture<EnergyEntity>> futureList = new ArrayList<>();
//
//            for (SiteEntity site : sites) {
//                site.setDomain(obj.getDomain());
//                Map<String, Object> localTime = getTimeByFilter(site.getTime_zone_value(), obj.getId_filter());
//                if (localTime == null) {
//                    continue;
//                }
//
//                String startTime = (String) localTime.get("start_time");
//                String endTime = (String) localTime.get("end_time");
//                Double duration = (Double) localTime.get("duration");
//
//                CompletableFuture<EnergyEntity> future = CompletableFuture.supplyAsync(() -> {
//                    EnergyEntity data = new EnergyEntity(site.getId_site(), site.getHash_id(), site.getName());
//                    try {
//                        if (site.getEnable_virtual_device() == 1) {
//                            Map<String, Object> params = new HashMap<>();
//                            params.put("start_time", startTime);
//                            params.put("end_time", endTime);
//                            params.put("duration", duration);
//                            params.put("table_data_virtual", site.getTable_data_virtual());
//                            EnergyEntity energy = (EnergyEntity) queryForObject("Dashboard.getTotalEnergyTodayByVirtualDevice", params);
//                            if (energy == null) return data;
//
//                            data.setActual(energy.getActual());
//                            data.setExpected(energy.getExpected());
//                        } else {
//                            List<DeviceEntity> siteDevices = devicesBySite.get(site.getId_site());
//                            if (siteDevices == null || siteDevices.isEmpty()) return data;
//
//                            // Filter devices by type
//                            List<DeviceEntity> meters = siteDevices.stream()
//                                    .filter(d -> d.getId_device_type() == 3 || d.getId_device_type() == 7 || d.getId_device_type() == 9)
//                                    .filter(d -> !d.isIs_excluded_meter())
//                                    .collect(Collectors.toList());
//
//                            List<DeviceEntity> inverters = siteDevices.stream()
//                                    .filter(d -> d.getId_device_type() == 1)
//                                    .collect(Collectors.toList());
//
//                            List<DeviceEntity> irradiances = siteDevices.stream()
//                                    .filter(d -> (d.getId_device_type() == 4 || d.getId_device_type() == 21) && d.getReverse_poa() == 0)
//                                    .collect(Collectors.toList());
//
//                            List<DeviceEntity> powerDevices = !meters.isEmpty() ? meters : inverters;
//
//                            if (powerDevices != null && !powerDevices.isEmpty() && needActual) {
//                                Map<String, Object> params = new HashMap<>();
//                                params.put("start_time", startTime);
//                                params.put("end_time", endTime);
//                                params.put("list", powerDevices);
//                                Double actual = (Double) queryForObject("Dashboard.getTotalEnergyTodayActualByDevice", params);
//                                data.setActual(actual);
//                            }
//
//                            if (irradiances != null && !irradiances.isEmpty()) {
//                                if (irradiances.size() == 1) {
//                                    Map<String, Object> params = new HashMap<>();
//                                    params.put("start_time", startTime);
//                                    params.put("end_time", endTime);
//                                    params.put("duration", duration);
//                                    params.put("datatablename", irradiances.get(0).getDatatablename());
//                                    Double expected = (Double) queryForObject("Dashboard.getTotalEnergyTodayExpectedByDevice", params);
//                                    data.setExpected(expected);
//                                } else {
//                                    ExpectedBySiteDTO siteEntity = (ExpectedBySiteDTO) queryForObject("CustomerView.getSelectedPOABySite", site);
//                                    String panelTemps = siteEntity != null ? siteEntity.getIds_device_panel_temp() : "";
//                                    String poas = siteEntity != null ? siteEntity.getIds_device_poa() : "";
//                                    if (Lib.isBlank(panelTemps) && Lib.isBlank(poas)) {
//                                        return data;
//                                    }
//                                    if (!Lib.isBlank(panelTemps)) {
//                                        List<Integer> ids = Arrays.asList(panelTemps.split(",")).stream()
//                                                .map(item -> Integer.parseInt(item))
//                                                .collect(Collectors.toList());
//                                        siteEntity.setPanelTemps(irradiances.stream()
//                                                .filter(item -> ids.contains(item.getId()))
//                                                .collect(Collectors.toList()));
//                                    }
//
//                                    if (!Lib.isBlank(poas)) {
//                                        List<Integer> ids = Arrays.asList(poas.split(",")).stream()
//                                                .map(item -> Integer.parseInt(item))
//                                                .collect(Collectors.toList());
//                                        siteEntity.setPOAs(irradiances.stream()
//                                                .filter(item -> ids.contains(item.getId()))
//                                                .collect(Collectors.toList()));
//                                    }
//
//                                    Map<String, Object> params = new HashMap<>();
//                                    params.put("start_time", startTime);
//                                    params.put("end_time", endTime);
//                                    params.put("duration", duration);
//                                    params.put("panelTemps", siteEntity.getPanelTemps());
//                                    params.put("POAs", siteEntity.getPOAs());
//                                    Double expected = (Double) queryForObject("Dashboard.getExpectedBySelectedPOA", params);
//                                    data.setExpected(expected);
//                                }
//                            }
//                        }
//
//                        if (Objects.nonNull(data.getActual()) && Objects.nonNull(data.getExpected()) && data.getExpected() > 0) {
//                            data.setLoss((data.getExpected() - data.getActual()) / data.getExpected());
//                        }
//
//                    } catch (Exception e) {
//                        log.error("GetExpected " + site.getId_site(), e);
//                    }
//
//                    return data;
//                });
//                futureList.add(future);
//            }
//
//            return futureList.stream().map(future -> future.join()).collect(Collectors.toList());
//        } catch (Exception ex) {
//            return new ArrayList<>();
//        }
//    }

    public List<Map<String, Object>> getKPIData(PortfolioEntity obj) {
        try {
            PortfolioService service = new PortfolioService();
            List<SiteEntity> sites = service.getSites(obj);
            if (sites == null || sites.isEmpty()) {
                return null;
            }
            List<DeviceEntity> allDevices = queryForList("Dashboard.getDevicesBySites", obj);
            List<DeviceEntity> meters = allDevices.stream()
                    .filter(d -> d.getId_device_type() == 3 || d.getId_device_type() == 7 || d.getId_device_type() == 9)
                    .filter(d -> !d.isIs_excluded_meter())
                    .collect(Collectors.toList());

            List<DeviceEntity> inverters = allDevices.stream()
                    .filter(d -> d.getId_device_type() == 1)
                    .collect(Collectors.toList());

            List<DeviceEntity> powerDevices = !meters.isEmpty() ? meters : inverters;

            List<SiteEntity> siteVirtualDevices = sites.stream().filter(item -> item.getEnable_virtual_device() > 0).collect(Collectors.toList());
            Map<String, Object> params = new HashMap<>();
            params.put("id_sites", obj.getId_sites());
            params.put("virtual_devices", siteVirtualDevices);
            params.put("power_devices", powerDevices);
            params.put("id_filter", obj.getId_filter());
            List<Map<String, Object>> dataList = queryForList("Dashboard.getKPIData", params);
            if (dataList == null) {
                return null;
            }

            //Get critical, warning for portfolio sites
            Map<String, Object> getAlertParams = new HashMap<>();
            getAlertParams.put("id", null);
            List<Map<String, Object>> alertBySites = queryForList("Dashboard.getPrioritySite", getAlertParams);
            Map<String, Object> inverterAvailableParams = new HashMap<>();
            inverterAvailableParams.put("id_sites", obj.getId_sites());
            inverterAvailableParams.put("sum_all_site", 0);
            List<Map<String, Object>> listInverterAvailable = queryForList("Dashboard.getInverterAvailabilityAllSite", inverterAvailableParams);

            Map<Integer, Map<String, Object>> alertBySiteMap = new HashMap<>();
            Map<Integer, Map<String, Object>> listInverterAvailableMap = new HashMap<>();

            if (alertBySites != null && !alertBySites.isEmpty()) {
                for (Map<String, Object> site : alertBySites) {
                    Integer id = (Integer) site.get("id");
                    alertBySiteMap.put(id, site);
                }
            }
            if (listInverterAvailable != null && !listInverterAvailable.isEmpty()) {
                for (Map<String, Object> item : listInverterAvailable) {
                    Integer id = (Integer) item.get("id");
                    listInverterAvailableMap.put(id, item);
                }
            }

            for (Map<String, Object> item : dataList) {
                Map<String, Object> localTime = getTimeByFilter((String) item.get("time_zone"), obj.getId_filter());
                if (localTime == null) {
                    continue;
                }
                Double duration = (Double) localTime.get("duration");
                double actualEnergy = item.get("actual_energy") != null ? (double) item.get("actual_energy") : 0;
                double expectedEnergy = 0;
                double loss = 0;
                if (duration != null && item.get("expected_power") != null) {
                    expectedEnergy = (double) item.get("expected_power") * duration;
                }
                item.put("expected", expectedEnergy);
                item.put("actual", actualEnergy);

                item.put("performance_ratio", expectedEnergy != 0 ? (actualEnergy / expectedEnergy ) * 100: 0);

                if (expectedEnergy > 0) {
                    loss = (expectedEnergy - actualEnergy) / expectedEnergy;
                }
                item.put("loss", loss);

                if (alertBySiteMap.containsKey(item.get("id"))) {
                    Map<String, Object> siteInfo = alertBySiteMap.get(item.get("id"));

                    item.put("critical_count", siteInfo.get("critical_count"));
                    item.put("warning_count", siteInfo.get("warning_count"));
                }

                if (listInverterAvailableMap.containsKey(item.get("id"))) {
                    Map<String, Object> siteInfo = listInverterAvailableMap.get(item.get("id"));
                    item.put("inverter_availability", siteInfo.get("total_availability_percent"));
                }
            }
            return dataList;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> getTotalPowerAndCapacity(PortfolioEntity obj) {
        try {
            SitesMetricsSummaryEntity data = (SitesMetricsSummaryEntity) queryForObject("Dashboard.getTotalPowerAndCapacity", obj);
            if (data == null) {
                return null;
            }
            Map<String, Object> res = new HashMap<>();
            res.put("ac_capacity", data.getCapacity());
            res.put("dc_capacity", data.getDc_capacity());
            res.put("active_power", data.getActivePower());
            return res;
        } catch (Exception e) {
            log.error("DashboardService.getTotalPowerAndCapacity", e);
        }
        return null;
    }

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

    public Map<String, Object> getInverterAvailabilityAllSite(SiteEntity obj) {
        try {
            if (obj.getId_sites() == null || obj.getId_sites().isEmpty()) {
                return null;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("id_sites", obj.getId_sites());
            params.put("sum_all_site", 1);
            return (Map<String, Object>) queryForObject("Dashboard.getInverterAvailabilityAllSite", params);
        } catch (Exception e) {
            log.error("DashboardService.getSiteDetail", e);
        }
        return null;
    }

    public Map<String, Object> getSiteDetail(SiteEntity obj) {
        try {
            if (obj.getId_site() <= 0) {
                return null;
            }
            Map<String, Object> res = new HashMap<>();
            res.put("site_id", obj.getId_site());
            Map<String, Object> inverterAvailability = (Map<String, Object>) queryForObject("Dashboard.getInverterAvailabilityBySite", obj);
            res.put("inverter_availability", inverterAvailability.get("availability_percent"));

            return res;
        } catch (Exception e) {
            log.error("DashboardService.getSiteDetail", e);
        }
        return null;
    }

    public List<Map<String, Object>> getActualvsExpectedPower(Map<String, Object> obj) {
        try {
            if (obj == null) {
                return  null;
            }
            PortfolioService portfolioService = new PortfolioService();
            PortfolioEntity entity = new PortfolioEntity();
            List idSites = obj.get("id_sites") != null ? (List) obj.get("id_sites") : null;
            entity.setId_sites(idSites);
            List<SiteEntity> sites = portfolioService.getSites(entity);
            if (sites == null || sites.isEmpty()) {
                return null;
            }
            String filterBy = obj.get("filter_by") != null ? (String) obj.get("id_sites") : "today";
            String interval = obj.get("interval") != null ? (String) obj.get("interval") : "1_hour";
            List<CompletableFuture<List<Map<String, Object>>>> futureList = new ArrayList<CompletableFuture<List<Map<String, Object>>>>();

            for (SiteEntity site : sites) {
                Map<String, Object> localTime = getTimeByFilter(site.getTime_zone_value(), filterBy);
                if (localTime == null || Lib.isBlank((String) localTime.get("start_time")) || Lib.isBlank((String) localTime.get("end_time"))) {
                    continue;
                }
                String startTime = (String) localTime.get("start_time");
                String endTime = (String) localTime.get("end_time");
                CompletableFuture<List<Map<String, Object>>> future = CompletableFuture.supplyAsync(() -> {
                    Map<String, Object> params = new HashMap<>();
                    List<Map<String, Object>> chartData = new ArrayList<>();
                    params.put("start_time", startTime);
                    params.put("end_time", endTime);
                    params.put("time_zone_value", site.getTime_zone_value());
                    params.put("interval", interval);
                    try {
                        if (site.getEnable_virtual_device() == 1) {
                            params.put("table_data_virtual", site.getTable_data_virtual());
                            chartData = (List<Map<String, Object>>) queryForList("Dashboard.getPowerByVirtualDevice", params);
                            return chartData == null ? new ArrayList<>() : chartData;
                        } else {
                            CustomerViewService service = new CustomerViewService();
                            DevicesByTypeEntity devices = service.getDevicesBySite(site);
                            List<DeviceEntity> meterDevices = devices.getMeter();
                            List<DeviceEntity> inverterDevices = devices.getInverter();
                            List<DeviceEntity> irradianceDevices = devices.getIrradiance();
                            List<DeviceEntity> powerDevices = !meterDevices.isEmpty() ? meterDevices : inverterDevices;

                            List<Map<String, Object>> actualList = new ArrayList<>();
                            List<Map<String, Object>> expectList = new ArrayList<>();
                            if (powerDevices != null && !powerDevices.isEmpty()) {
                                params.put("devices", powerDevices);
                                actualList = (List<Map<String, Object>>) queryForList("Dashboard.getActualPowerByDevice", params);
                            }
                            if (irradianceDevices != null && !irradianceDevices.isEmpty()) {
                                if (irradianceDevices.size() == 1) {
                                    params.put("datatablename", irradianceDevices.get(0).getDatatablename());
                                    params.put("id_device", irradianceDevices.get(0).getId());
                                    expectList = (List<Map<String, Object>>) queryForList("Dashboard.getExpectPowerByIrradianceDevice", params);
                                } else {
                                    ExpectedBySiteDTO siteEntity = (ExpectedBySiteDTO) queryForObject("CustomerView.getSelectedPOABySite", site);
                                    if (siteEntity == null) {
                                        return new ArrayList<>();
                                    }
                                    String panelTemps = siteEntity.getIds_device_panel_temp();
                                    String poas = siteEntity.getIds_device_poa();
                                    if (Lib.isBlank(poas) && Lib.isBlank(panelTemps)) {
                                        return new ArrayList<>();
                                    }
                                    List<Integer> idsPanel = Arrays.asList(panelTemps.split(",")).stream().map(item -> Integer.parseInt(item)).collect(Collectors.toList());
                                    siteEntity.setPanelTemps(irradianceDevices.stream().filter(item -> idsPanel.contains(item.getId())).collect(Collectors.toList()));

                                    List<Integer> idsPoas = Arrays.asList(poas.split(",")).stream().map(item -> Integer.parseInt(item)).collect(Collectors.toList());
                                    siteEntity.setPOAs(irradianceDevices.stream().filter(item -> idsPoas.contains(item.getId())).collect(Collectors.toList()));

                                    params.put("panelTemps", siteEntity.getPanelTemps());
                                    params.put("POAs", siteEntity.getPOAs());
                                    expectList = (List<Map<String, Object>>) queryForList("Dashboard.getExpectedPowerBySelectedPOA", params);
                                }
                            }
                            Map<String, Map<String, Object>> merged = new LinkedHashMap<>();
                            actualList = actualList == null ? Collections.emptyList() : actualList;
                            expectList = expectList == null ? Collections.emptyList() : expectList;
                            Stream.concat(actualList.stream(), expectList.stream())
                                    .forEach(row -> {
                                        String key = (String) row.get("category_time");
                                        merged.computeIfAbsent(key, k -> new HashMap<>()).putAll(row);
                                    });

                            chartData = new ArrayList<>(merged.values());

                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    return chartData;
                });
                futureList.add(future);
            }

            return futureList.stream().flatMap(future -> future.join().stream()).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("DashboardService.getActualvsExpectedPower", e);
        }
        return null;
    }

    public List<Map<String, Object>> getChartEnergyFlow(Map<String, Object> obj) {
        try {
            if (obj == null) {
                return  null;
            }
            String filterBy = obj.get("filter_by") != null ? (String) obj.get("filter_by") : "today";
            String interval = obj.get("interval") != null ? (String) obj.get("interval") : "1_hour";

            PortfolioService portfolioService = new PortfolioService();
            PortfolioEntity entity = new PortfolioEntity();
            List idSites = obj.get("id_sites") != null ? (List) obj.get("id_sites") : null;
            entity.setId_sites(idSites);
            List<SiteEntity> sites = portfolioService.getSites(entity);
            if (sites == null || sites.isEmpty()) {
                return null;
            }

            if (obj.get("mode").equals(2)) {
                obj.put("id_sites", sites.stream().map(item -> item.getId_site()).collect(Collectors.toList()));
            }

            List<SiteEntity> siteVirtualDevice = sites.stream().filter(item -> item.getEnable_virtual_device() == 1).collect(Collectors.toList());
            List<SiteEntity> siteDevice = sites.stream().filter(item -> item.getEnable_virtual_device() != 1).collect(Collectors.toList());

            List<DeviceEntity> consumeDevices = (List<DeviceEntity>) queryForList("Dashboard.getConsumeDevicesBySites", obj);

            List<Integer> siteIds = siteDevice.stream()
                    .map(item -> item.getId_site()).collect(Collectors.toList());

            entity.setId_sites(siteIds);
            List<DeviceEntity> devices = queryForList("Dashboard.getDevicesBySites", entity);
            List<DeviceEntity> meterDevices = devices.stream().filter(item -> (item.getId_device_type() == 3 || item.getId_device_type() == 7) && !item.isIs_excluded_meter()).collect(Collectors.toList());
            List<DeviceEntity> inverterDevices = devices.stream().filter(item -> (item.getId_device_type() == 1)).collect(Collectors.toList());
            List<DeviceEntity> powerDevices = meterDevices.isEmpty() ? inverterDevices : meterDevices;

            Map<String, Object> params = new HashMap<>();
            params.put("virtualList", siteVirtualDevice);
            params.put("produceList", powerDevices);
            params.put("consumeList", consumeDevices);
            params.put("id_filter", filterBy);
            params.put("interval", interval);
            List<Map<String, Object>> data = queryForList("Dashboard.getChartEnergyFlow", params);
            return data;
        } catch (Exception e) {
            log.error("DashboardService.getChartEnergyFlow", e);
        }
        return null;
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
}
